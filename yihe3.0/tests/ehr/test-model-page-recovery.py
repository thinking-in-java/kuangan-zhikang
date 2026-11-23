"""Offline schema recovery and per-job caching; no model requests or clinical DB writes."""
import copy,json,sys,tempfile,unittest
from pathlib import Path
from concurrent.futures import ThreadPoolExecutor
from unittest.mock import patch
ROOT=Path(__file__).resolve().parents[2];sys.path.insert(0,str(ROOT/'scripts'))
import model_recognition_worker as m

SOURCE='44444444-4444-4444-8444-444444444444'
INPUT={'job_id':'33333333-3333-4333-8333-333333333333','scope_code':'org:RECOVERY_FIXTURE','data_mode':'synthetic','report_id':'11111111-1111-4111-8111-111111111111',
       'revision':1,'record_id':'22222222-2222-4222-8222-222222222222','report_kind':'individual','prior_revision':None,
       'source_files':[{'source_file_id':SOURCE,'sha256':'0'*64,'page_count':2}],'files':[{'id':SOURCE,'mimeType':'application/pdf'}]}
D={'standardFields':[{'field_key':'full_name','field_name':'姓名'}],'medicalItems':[]}
C={'model':'fixture','parallelRequests':2,'pageRepairAttempts':1,'apiKey':'secret-must-not-be-cached'}
PAGES=[(1,{'text':'PAGE1\nBP 120/80','native_text':'BP 120/80','method':'native_text'}),(2,{'text':'PAGE2\nGlucose 8.2','native_text':'Glucose 8.2','method':'native_text'})]
def result(name='BP',value='120/80'):
    return {'report_kind':'individual','metadata':{},'fields':[],'items':[{'name':name,'result_raw':value,'unit_raw':'raw-unit'}],'conclusions':[],'advice':[]}

class Checks(unittest.TestCase):
    def test_explicit_name_aliases_preserve_raw_strings(self):
        for key in ['item_name','original_field_name','项目名称']:
            row={'name':None,key:'丙氨酸氨基转移酶','result_raw':' 42.0 ','unit_raw':' U/L '}
            output=m.normalize_item_names({'items':[row]})['items'][0]
            self.assertEqual(output['name'],row[key]);self.assertEqual(output['result_raw'],' 42.0 ');self.assertEqual(output['unit_raw'],' U/L ')
    def test_only_completely_empty_placeholder_rows_removed(self):
        output=m.normalize_item_names({'items':[{}, {'name':None,'result_raw':None}, {'name':' ','result_raw':''}, {'name':'BP','result_raw':'120/80'}]})
        self.assertEqual(len(output['items']),1)
    def test_result_or_abbreviation_never_used_as_name(self):
        for row in [{'result_raw':'8.2'},{'name':'','abbreviation_raw':'ALT','result_raw':'42'},{'name':None,'item_name':'A','original_field_name':'B','result_raw':'42'}]:
            with self.assertRaises(m.AdapterError):m.normalize_item_names({'items':[row]})
    def test_named_unreadable_result_still_preserved(self):
        self.assertEqual(m.normalize_item_names({'items':[{'name':'BP','result_raw':None}]})['items'][0]['name'],'BP')
    def test_only_invalid_page_is_repaired_and_good_pages_cached(self):
        with tempfile.TemporaryDirectory() as directory:
            path=Path(directory)/'job.pages.json';cache=m.PageResultCache(path,INPUT,D,C);calls={1:0,2:0}
            def recognize(content):
                page=1 if content['text'].startswith('PAGE1') else 2;calls[page]+=1
                if page==2 and calls[page]==1:return result(None,'8.2')
                if page==2:self.assertIn('每条有结果',content['page_hint'])
                return result('BP' if page==1 else 'Glucose','120/80' if page==1 else '8.2')
            with patch.object(m,'prepare_pages',return_value=iter(copy.deepcopy(PAGES))):
                output,timing=m.extract_document(INPUT,D,C,lambda f,s:b'fixture',recognizer=recognize,page_cache=cache)
            self.assertEqual(calls,{1:1,2:2});self.assertEqual([i['original_field_name'] for i in output['items']],['BP','Glucose'])
            self.assertEqual([i['result_raw'] for i in output['items']],['120/80','8.2']);self.assertEqual(timing['pageTimings'][1]['repairAttempts'],1)
            self.assertEqual(timing['cachedPages'],0);self.assertTrue(list(Path(directory).glob('*.repair-0.json')))
            cache=m.PageResultCache(path,INPUT,D,C)
            with patch.object(m,'prepare_pages',return_value=iter(copy.deepcopy(PAGES))):
                _,timing=m.extract_document(INPUT,D,C,lambda f,s:b'fixture',recognizer=lambda c:self.fail('valid pages must be reused'),page_cache=cache)
            self.assertEqual(timing['cachedPages'],2);self.assertTrue(all(p['repairAttempts']==0 for p in timing['pageTimings']))
            self.assertNotIn(C['apiKey'],path.read_text())
    def test_exhausted_page_keeps_good_cache_and_reports_page_number(self):
        with tempfile.TemporaryDirectory() as directory:
            path=Path(directory)/'job.pages.json';cache=m.PageResultCache(path,INPUT,D,C);calls={1:0,2:0}
            def recognize(content):
                page=1 if content['text'].startswith('PAGE1') else 2;calls[page]+=1
                return result('BP','120/80') if page==1 else result(None,'8.2')
            with patch.object(m,'prepare_pages',return_value=iter(copy.deepcopy(PAGES))):
                with self.assertRaises(m.AdapterError) as error:m.extract_document(INPUT,D,C,lambda f,s:b'fixture',recognizer=recognize,page_cache=cache)
            self.assertIn('第2页',error.exception.message);self.assertIn('重试1次',error.exception.message);self.assertEqual(calls,{1:1,2:2})
            self.assertIsNotNone(cache.get(SOURCE,1));self.assertIsNone(cache.get(SOURCE,2))
            recovered=[]
            def fix(content):recovered.append(content['text']);return result('Glucose','8.2')
            with patch.object(m,'prepare_pages',return_value=iter(copy.deepcopy(PAGES))):
                output,timing=m.extract_document(INPUT,D,C,lambda f,s:b'fixture',recognizer=fix,page_cache=m.PageResultCache(path,INPUT,D,C))
            self.assertEqual(recovered,[PAGES[1][1]['text']]);self.assertEqual(timing['cachedPages'],1);self.assertEqual(len(output['items']),2)
    def test_cache_bound_to_original_job_and_model(self):
        with tempfile.TemporaryDirectory() as directory:
            path=Path(directory)/'job.pages.json';cache=m.PageResultCache(path,INPUT,D,C);cache.save(SOURCE,1,result())
            self.assertIsNone(m.PageResultCache(path,{**INPUT,'job_id':'55555555-5555-4555-8555-555555555555'},D,C).get(SOURCE,1))
            changed=copy.deepcopy(INPUT);changed['source_files'][0]['sha256']='f'*64
            self.assertIsNone(m.PageResultCache(path,changed,D,C).get(SOURCE,1))
            self.assertIsNone(m.PageResultCache(path,INPUT,D,{**C,'model':'another'}).get(SOURCE,1))
    def test_cache_checksum_rejects_corruption(self):
        with tempfile.TemporaryDirectory() as directory:
            path=Path(directory)/'job.pages.json';cache=m.PageResultCache(path,INPUT,D,C);cache.save(SOURCE,1,result())
            raw=json.loads(path.read_text());raw['pages'][SOURCE+':1']['result']['items'][0]['result_raw']='corrupt';path.write_text(json.dumps(raw))
            self.assertIsNone(m.PageResultCache(path,INPUT,D,C).get(SOURCE,1))
    def test_parallel_cache_updates_are_atomic(self):
        with tempfile.TemporaryDirectory() as directory:
            path=Path(directory)/'job.pages.json';cache=m.PageResultCache(path,INPUT,D,C)
            with ThreadPoolExecutor(max_workers=8) as pool:list(pool.map(lambda n:cache.save(SOURCE,n,result('Item'+str(n),str(n))),range(1,17)))
            loaded=m.PageResultCache(path,INPUT,D,C);self.assertTrue(all(loaded.get(SOURCE,n) is not None for n in range(1,17)))
    def test_http_errors_are_not_repeated_as_format_repairs(self):
        calls=[]
        def recognize(content):calls.append(1);raise m.AdapterError('MODEL_HTTP_429','Synthetic rate limit',True)
        one={**INPUT,'source_files':[{**INPUT['source_files'][0],'page_count':1}]}
        with patch.object(m,'prepare_pages',return_value=iter(copy.deepcopy(PAGES[:1]))):
            with self.assertRaises(m.AdapterError):m.extract_document(one,D,C,lambda f,s:b'fixture',recognizer=recognize)
        self.assertEqual(len(calls),1)

if __name__=='__main__':unittest.main(verbosity=2)
