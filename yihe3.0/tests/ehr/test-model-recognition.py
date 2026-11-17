"""Offline adapter contract tests. No external requests, task claims or DB writes."""
import importlib.util
import io
import json
import time
import threading
import unittest
from pathlib import Path
from PIL import Image

ROOT=Path(__file__).resolve().parents[2]
spec=importlib.util.spec_from_file_location('adapter', ROOT/'scripts/model_recognition_worker.py')
m=importlib.util.module_from_spec(spec);spec.loader.exec_module(m)
D={'standardFields':[{'field_key':x,'field_name':x} for x in ['full_name','exam_date','institution','check_date','section_conclusion','section_findings']],
   'medicalItems':[{'item_code':'test-code','standard_name':'Exact item','status':'lexical_only'}]}
I={'scope_code':'org:10000','data_mode':'synthetic','report_id':'11111111-1111-4111-8111-111111111111','revision':3,'prior_revision':2,
   'record_id':'22222222-2222-4222-8222-222222222222','job_id':'33333333-3333-4333-8333-333333333333','report_kind':'individual',
   'source_files':[{'source_file_id':'44444444-4444-4444-8444-444444444444','sha256':'0'*64,'page_count':2}]}
SOURCE=I['source_files'][0]['source_file_id']

def page(**kw):
    return {'report_kind':'individual','metadata':{},'fields':[{'field_key':'full_name','label':'Name','value_raw':'SYNTHETIC','quote':'Name: SYNTHETIC'}],
            'items':[],'conclusions':[],'advice':[],**kw}

class Checks(unittest.TestCase):
    def test_same_hospital_exam_center_selects_existing_full_name(self):
        b=self.builder();b.add_page(SOURCE,1,page(metadata={'institution_raw':'神木市中医医院体检中心'}));b.add_page(SOURCE,2,page(metadata={'institution_raw':'神木市中医医院'}));out=b.finish()
        self.assertEqual(out['institution_raw'],'神木市中医医院体检中心')
        self.assertEqual({f['value_raw'] for f in out['field_values'] if f['field_key']=='institution'},{'神木市中医医院','神木市中医医院体检中心'})
    def test_institution_fields_supply_missing_metadata(self):
        b=self.builder();b.add_page(SOURCE,1,page(fields=[{'field_key':'institution','label':'体检机构','value_raw':'测试医院体检中心','quote':'测试医院体检中心'}]));self.assertEqual(b.finish()['institution_raw'],'测试医院体检中心')
    def test_different_hospitals_and_departments_stay_unresolved(self):
        for values in [('第一人民医院','第二人民医院'),('测试医院体检中心','测试医院健康管理中心'),('测试医院','测试医院分院'),('测试医院','另一测试医院体检中心')]:
            self.assertIsNone(m.resolve_institution(values),values)
    def test_institution_selection_keeps_literal_spacing(self):
        self.assertEqual(m.resolve_institution(['测试 医院','测试 医院体检中心']),'测试 医院体检中心')
    def test_ultrafast_is_sent_and_actual_tier_is_preserved(self):
        class Response:
            status_code=200;content=b'{}'
            def json(self):return {'status':'completed','service_tier':'ultrafast','output':[{'content':[{'type':'output_text','text':'{"fields":[]}'}]}]}
        class Session:
            def post(self,*args,**kwargs):self.payload=kwargs['json'];return Response()
        session=Session();out=m.recognize_page(session,{'model':'gpt-6.1-sol','apiKey':'test','baseUrl':'https://example.invalid','apiStyle':'responses','serviceTier':'ultrafast','reasoningEffort':'none'},{'text':'synthetic'},D)
        self.assertEqual(session.payload['service_tier'],'ultrafast')
        self.assertEqual(session.payload['reasoning']['effort'],'none')
        self.assertEqual(out['_served_service_tier'],'ultrafast')
        self.assertNotIn('speed',session.payload)
    def test_relay_wrong_charset_does_not_corrupt_chinese_json(self):
        import requests
        response=requests.Response();response.status_code=200;response.encoding='ISO-8859-1'
        response._content=json.dumps({'choices':[{'finish_reason':'stop','message':{'content':json.dumps({'metadata':{'institution_raw':'联调测试医院'},'fields':[]},ensure_ascii=False)}}]},ensure_ascii=False).encode('utf8')
        class Session:
            def post(self,*args,**kwargs):return response
        out=m.recognize_page(Session(),{'model':'glm-5.3-flash','apiStyle':'chat_completions','apiKey':'test','baseUrl':'https://example.invalid'},{'text':'synthetic'},D)
        self.assertEqual(out['metadata']['institution_raw'],'联调测试医院')
        self.assertEqual(out['_request_metrics']['declaredEncoding'],'ISO-8859-1')
    def builder(self):return m.CandidateBuilder(I,D,'probe-model')
    def test_context_is_owned_by_a(self):
        b=self.builder();b.add_page(SOURCE,1,page(report_id='malicious',revision=99,record_id='other'));o=b.finish()
        for k in ['report_id','revision','record_id','job_id','scope_code','data_mode']:self.assertEqual(o[k],I[k])
    def test_metadata_is_candidate_only(self):
        b=self.builder();b.add_page(SOURCE,1,page(metadata={'exam_date_raw':'2026-11-16','institution_raw':'TEST'}));o=b.finish()
        self.assertEqual(o['exam_date_raw'],'2026-11-16');self.assertIsNone(o['exam_date_verified'])
        self.assertEqual(o['institution_raw'],'TEST')
    def test_conflicting_dates_stay_unresolved(self):
        b=self.builder();b.add_page(SOURCE,1,page(metadata={'exam_date_raw':'2026-11-16'}));b.add_page(SOURCE,2,page(metadata={'exam_date_raw':'2026-11-17'}))
        self.assertIsNone(b.finish()['exam_date_raw'])
    def test_date_spellings_merge_without_approval(self):
        b=self.builder();b.add_page(SOURCE,1,page(metadata={'exam_date_raw':'2024/07/10'}));b.add_page(SOURCE,2,page(metadata={'exam_date_raw':'2024-07-10'}))
        self.assertEqual(m.report_date_key(b.finish()['exam_date_raw']),'2024-07-10');self.assertIsNone(b.finish()['exam_date_verified'])
    def test_total_exam_label_is_not_an_exam_date(self):
        self.assertIsNone(m.report_date_key('总检日期：2024/07/10'))
        b=self.builder();b.add_page(SOURCE,1,page(metadata={'exam_date_raw':'2024/07/10'}));b.add_page(SOURCE,2,page(metadata={'exam_date_raw':'总检日期：2024/07/10'}))
        self.assertEqual(b.finish()['exam_date_raw'],'2024/07/10')
    def test_native_date_only_uses_explicit_label(self):
        result=m.native_exam_date(page(), '检查日期：2024/07/09\n总检日期：2024/07/11\n体检日期：2024/07/10')
        self.assertEqual(result['metadata']['exam_date_raw'],'2024/07/10')
        self.assertEqual(m.report_date_key('2024 年07 月10 日'),'2024-07-10')
    def test_next_exam_date_is_not_current(self):
        result=m.native_exam_date(page(), '下次体检日期：2025/07/10\n预约体检日期：2025/07/11')
        self.assertNotIn('exam_date_raw',result['metadata'])
    def test_total_date_model_guess_cannot_conflict_with_explicit_cover(self):
        b=self.builder()
        cover=m.native_report_anchors(page(metadata={'exam_date_raw':'2022-11-08'}),
            '榆林市第二医院健康管理中心\n体检日期：2022-11-08',1)
        total=m.native_report_anchors(page(metadata={'exam_date_raw':'2022-11-17'},
            fields=[{'field_key':'exam_date','value_raw':'2022-11-17','label':'体检日期','quote':'总检日期：2022-11-17'}]),
            '总检医生：TEST 总检日期：2022-11-17',2)
        b.add_page(SOURCE,1,cover);b.add_page(SOURCE,2,total);out=b.finish()
        self.assertEqual(out['exam_date_raw'],'2022-11-08');self.assertIsNone(out['exam_date_verified'])
        self.assertFalse(any(f['value_raw']=='2022-11-17' for f in out['field_values']))
    def test_cover_institution_not_lost_to_short_attachment_name(self):
        b=self.builder();b.add_page(SOURCE,1,m.native_report_anchors(page(),
            '榆林市第二医院健康管理中心\n体检日期：2022-11-08',1))
        b.add_page(SOURCE,2,page(metadata={'institution_raw':'榆林市第二医院'}))
        self.assertEqual(b.finish()['institution_raw'],'榆林市第二医院健康管理中心')
    def test_model_cannot_fabricate_native_anchor(self):
        p=m.native_report_anchors(page(_native_exam_date='2022-11-08',_native_institution='fake'),'',2)
        self.assertNotIn('_native_exam_date',p);self.assertNotIn('_native_institution',p)
    def test_departmental_summary_preserved_separately(self):
        b=self.builder();b.add_page(SOURCE,1,page(conclusions=[
            {'label':'各检查项目异常结果汇总','text':'物理检查 BMI 26.89','completeness':'complete'},
            {'label':'总检结论与建议','text':'Literal overall conclusion','completeness':'partial'},
            {'label':'完整体检结论','text':':','completeness':'complete'}]))
        out=b.finish();self.assertEqual(len(out['conclusions']),1)
        self.assertEqual(out['conclusions'][0]['completeness'],'partial')
        self.assertTrue(any(f['field_key']=='section_conclusion' for f in out['field_values']))
    def test_service_tier_is_requested_and_actual_tier_recorded(self):
        class Response:
            status_code=200;content=b'{}'
            def json(self):return {'status':'completed','service_tier':'default','output':[{'content':[{'type':'output_text','text':'{"fields":[]}'}]}]}
        class Session:
            def post(self,*args,**kw):self.payload=kw['json'];return Response()
        s=Session();out=m.recognize_page(s,{'model':'probe','apiKey':'not-a-secret','baseUrl':'https://example.invalid','serviceTier':'priority'}, {'text':'SYNTHETIC'}, D)
        self.assertEqual(s.payload['service_tier'],'priority');self.assertNotIn('speed',s.payload)
        self.assertEqual(out['_served_service_tier'],'default')
        self.assertEqual(out['_request_metrics']['attempts'],1)
        self.assertEqual(out['_request_metrics']['httpStatuses'],[200])
        self.assertNotIn('not-a-secret',json.dumps(out))
    def test_ordered_item_fields_are_recovered_without_duplicate_or_conversion(self):
        p=page(fields=[{'field_key':'item_name','value_raw':'Exact item'}, {'field_key':'item_result','value_raw':'5.10'},
            {'field_key':'item_unit','value_raw':'original unit'}, {'field_key':'item_reference','value_raw':'4-6'},
            {'field_key':'item_name','value_raw':'Next'}, {'field_key':'item_result','value_raw':'negative'}],
            items=[{'name':'Exact item','result_raw':'5.10','unit_raw':'original unit','reference_range_raw':'4-6'}])
        out=m.complete_page_arrays(p,{})
        self.assertEqual(len(out['items']),2);self.assertEqual(out['items'][1]['result_raw'],'negative')
        self.assertNotIn('result_normalized',out['items'][1])
    def test_ambiguous_item_fields_not_guessed(self):
        p=page(fields=[{'field_key':'item_name','value_raw':'A'},{'field_key':'item_result','value_raw':'1'},{'field_key':'item_result','value_raw':'2'}])
        self.assertFalse(m.complete_page_arrays(p,{})['items'])
    def test_only_explicit_report_continuation_promotes_section_text(self):
        p=page(fields=[{'field_key':'section_conclusion','value_raw':'Literal continuation'}])
        self.assertFalse(m.complete_page_arrays(p,{})['conclusions'])
        self.assertEqual(m.complete_page_arrays(p,{'conclusion_continuation':True})['conclusions'][0]['text'],'Literal continuation')
    def test_transient_upstream_error_retries_only_the_page(self):
        class Response:
            content=b'{}'
            def __init__(self,status):self.status_code=status
            def json(self):return {'status':'completed','output':[{'content':[{'type':'output_text','text':'{}'}]}]}
        class Session:
            calls=0
            def post(self,*args,**kw):self.calls+=1;return Response(502 if self.calls==1 else 200)
        s=Session()
        from unittest.mock import patch
        with patch.object(m.time,'sleep'):
            out=m.recognize_page(s,{'model':'probe','apiKey':'fixture','baseUrl':'https://example.invalid'},{'text':'synthetic'},D)
        self.assertEqual(s.calls,2)
        self.assertEqual(out['_request_metrics']['httpStatuses'],[502,200])
        self.assertEqual(out['_request_metrics']['attempts'],2)
    def test_native_layout_preserves_overall_continuation(self):
        import pymupdf
        doc=pymupdf.open()
        for content in ['总检结论与建议\nLiteral first part','Literal continued\n总检医生：TEST 总检日期：2022-11-17']:
            p=doc.new_page();p.insert_text((50,60),content,fontname='china-s',fontsize=14)
        raw=doc.tobytes();doc.close()
        pages=list(m.prepare_pages(raw,'application/pdf',2,{'nativeTextMinChars':0}))
        self.assertFalse(pages[0][1]['conclusion_continuation'])
        self.assertTrue(pages[1][1]['conclusion_continuation'])
    def test_catalog_exam_status_is_not_a_clinical_result(self):
        b=self.builder();b.add_page(SOURCE,1,page(items=[{'name':'X-ray','result_raw':'已检'}, {'name':'Glucose','result_raw':'negative'}]))
        out=b.finish();self.assertEqual(len(out['items']),1)
        self.assertEqual(out['items'][0]['original_field_name'],'Glucose')
        self.assertTrue(any(f['field_key']=='section_findings' for f in out['field_values']))
    def test_native_overall_text_is_not_paraphrased(self):
        p=page(conclusions=[{'text':'shortened'}])
        out=m.complete_page_arrays(p,{'overall_text':'The exact source\nwith punctuation: preserved.'})
        self.assertEqual(out['conclusions'][0]['text'],'The exact source\nwith punctuation: preserved.')
        self.assertEqual(out['conclusions'][0]['completeness'],'partial')
    def test_compact_transport_preserves_raw_columns_and_evidence(self):
        p=m.expand_page_arrays({'report_kind':'individual','metadata':{},'fields':[['full_name','姓名','SYNTHETIC','姓名：SYNTHETIC']],
            'items':[['血压','135/88','mmHg',None,'↑',None,'血压 135/88 mmHg']],
            'conclusions':[['总检结论','Literal source','partial']],'advice':[['建议','Literal advice']]})
        b=self.builder();b.add_page(SOURCE,2,p);o=b.finish()
        self.assertEqual(o['items'][0]['result_raw'],'135/88');self.assertEqual(o['items'][0]['unit_raw'],'mmHg')
        self.assertEqual(o['items'][0]['abnormal_flag_raw'],'↑');self.assertIsNone(o['items'][0]['result_normalized'])
        self.assertTrue(any(e['raw_fragment']=='血压 135/88 mmHg' and e['page_number']==2 for e in o['evidence']))
    def test_compact_malformed_rows_do_not_shift_columns(self):
        for row in [['name'],['name',123],['name','raw',None,None,None,None,None,'extra']]:
            with self.assertRaises(m.AdapterError):m.expand_page_arrays({'items':[row]})
    def test_eight_concurrent_pages_still_merge_in_order(self):
        import pymupdf
        doc=pymupdf.open()
        for _ in range(8):doc.new_page().insert_text((50,50),'SYNTHETIC ONLY')
        raw=doc.tobytes();doc.close();source={**I['source_files'][0],'page_count':8}
        inp={**I,'source_files':[source],'files':[{'id':SOURCE,'mimeType':'application/pdf'}]}
        barrier=threading.Barrier(8)
        def recognize(content):barrier.wait(timeout=5);return page(items=[{'name':'Test','result_raw':'raw'}])
        out,stats=m.extract_document(inp,D,{'model':'test','parallelRequests':8,'maxPages':40},lambda f,s:raw,recognizer=recognize)
        self.assertEqual(stats['parallelRequests'],8)
        self.assertEqual([e['page_number'] for e in out['evidence'] if e['original_label']=='Test'],list(range(1,9)))
    def test_native_evidence_is_a_literal_unique_line(self):
        row={'name':'血压','result_raw':'135/88','unit_raw':'mmHg'}
        out=m.attach_native_evidence({'items':[row],'fields':[]},'血压    135/88   mmHg\n血糖 5.2 mmol/L')
        self.assertEqual(out['items'][0]['quote'],'血压    135/88   mmHg')
        self.assertEqual(out['items'][0]['result_raw'],'135/88')
    def test_ambiguous_or_absent_native_quote_is_not_fabricated(self):
        p={'items':[{'name':'血压','result_raw':'135/88'}],'fields':[]}
        self.assertIsNone(m.attach_native_evidence(p,'血压 135/88 上午\n血压 135/88 下午')['items'][0]['quote'])
        self.assertIsNone(m.attach_native_evidence(p,'血压 120/80')['items'][0]['quote'])
    def test_dense_scheduling_keeps_all_pages_and_original_output_order(self):
        from unittest.mock import patch
        inp={**I,'files':[{'id':SOURCE,'mimeType':'application/pdf'}]}
        entries=[(1,{'text':'short','native_text':'','method':'native_text'}),(2,{'text':'long'*100,'native_text':'','method':'native_text'})]
        seen=[]
        def recognize(content):seen.append(len(content['text']));return page(items=[{'name':'Test','result_raw':'raw'}])
        with patch.object(m,'prepare_pages',return_value=iter(entries)):
            out,stats=m.extract_document(inp,D,{'model':'test','parallelRequests':1,'prioritizeDensePages':True},lambda f,s:b'fixture',recognizer=recognize)
        self.assertEqual(seen,[400,5]);self.assertEqual(stats['pages'],2)
        self.assertEqual([e['page_number'] for e in out['evidence'] if e['original_label']=='Test'],[1,2])
    def test_fields_can_supply_report_date(self):
        b=self.builder();b.add_page(SOURCE,1,page(fields=[{'field_key':'exam_date','label':'体检日期','value_raw':'2024/07/10'}]))
        self.assertEqual(b.finish()['exam_date_raw'],'2024/07/10')
    def test_check_date_is_not_report_date(self):
        b=self.builder();b.add_page(SOURCE,1,page(fields=[{'field_key':'check_date','value_raw':'2026-11-15'}]));self.assertIsNone(b.finish()['exam_date_raw'])
    def test_derived_values_not_promoted(self):
        b=self.builder();p=page();p['fields'][0].update(value_normalized='BAD',normalization_rule_version='BAD');b.add_page(SOURCE,1,p)
        self.assertIsNone(b.finish()['field_values'][0]['value_normalized'])
    def test_unknown_field_rejected(self):
        with self.assertRaises(m.AdapterError):self.builder().add_page(SOURCE,1,page(fields=[{'field_key':'not_in_dictionary','value_raw':'x'}]))
    def test_only_exact_unique_dictionary_match(self):
        b=self.builder();b.add_page(SOURCE,1,page(items=[{'name':'Exact item','result_raw':'5.0','unit_raw':'raw-unit'},{'name':'Guess','result_raw':'6'}]));o=b.finish()
        self.assertEqual(o['items'][0]['item_code'],'test-code');self.assertIsNone(o['items'][1]['item_code']);self.assertEqual(o['items'][0]['unit_raw'],'raw-unit')
        self.assertIsNone(o['items'][0]['result_normalized']);self.assertIsNone(o['items'][0]['unit_conversion'])
    def test_evidence_associations_and_no_fake_bbox(self):
        b=self.builder();b.add_page(SOURCE,2,page(items=[{'name':'X','result_raw':'1'}],conclusions=[{'text':'Actual text','completeness':'partial'}],advice=[{'text':'Actual advice'}]));o=b.finish();ids={e['evidence_id'] for e in o['evidence']}
        for e in o['evidence']:self.assertEqual(e['source_file_id'],SOURCE);self.assertEqual(e['page_number'],2);self.assertIsNone(e['bbox'])
        self.assertIn(o['items'][0]['primary_evidence_id'],ids);self.assertIn(o['advice'][0]['source_evidence_id'],ids)
        self.assertEqual(o['conclusions'][0]['completeness'],'partial');self.assertIsNone(o['conclusions'][0]['standard_category'])
    def test_organization_report_rejected(self):
        b=self.builder();b.add_page(SOURCE,1,page(report_kind='organization'))
        with self.assertRaises(m.AdapterError):b.finish()
    def test_uncertain_kind_needs_doctor_confirmation(self):
        b=self.builder();b.add_page(SOURCE,1,page(report_kind='unknown'));self.assertEqual(b.finish()['report_kind'],'unknown')
    def test_empty_result_rejected(self):
        b=self.builder();b.add_page(SOURCE,1,page(fields=[]))
        with self.assertRaises(m.AdapterError):b.finish()
    def test_bad_array_and_typed_values_rejected(self):
        with self.assertRaises(m.AdapterError):self.builder().add_page(SOURCE,1,page(items='wrong'))
        with self.assertRaises(m.AdapterError):self.builder().add_page(SOURCE,1,page(items=[{'name':'x','result_raw':5}]))
    def test_responses_json_parser(self):
        j={'status':'completed','output':[{'content':[{'type':'output_text','text':'{"ok":true}'}]}]};self.assertTrue(m.response_object(j)['ok'])
    def test_truncated_and_refused_responses_not_saved(self):
        for j in [{'status':'incomplete','output':[]},{'choices':[{'finish_reason':'length','message':{'content':'{}'}}]},{'status':'completed','output':[]}]:
            with self.assertRaises(m.AdapterError):m.response_object(j)
    def test_png_render_is_bounded(self):
        image=Image.new('RGB',(100,100),'white');buf=io.BytesIO();image.save(buf,format='PNG');raw=buf.getvalue()
        pages=list(m.render_pages(raw,'image/png',1));self.assertEqual(pages[0][0],1);self.assertTrue(pages[0][1].startswith(b'\x89PNG'));self.assertEqual(buf.getvalue(),raw)
    def test_bad_file_rejected(self):
        with self.assertRaises(m.AdapterError):list(m.render_pages(b'broken','application/pdf',1))
    def test_raw_output_keeps_unicode(self):
        b=self.builder();b.add_page(SOURCE,1,page(items=[{'name':'血压','result_raw':'135/88','unit_raw':'mmHg'}]));self.assertIn('135/88',json.dumps(b.finish(),ensure_ascii=False))
    def test_parallel_pages_merge_in_page_order(self):
        import pymupdf
        doc=pymupdf.open()
        for _ in range(2):
            p=doc.new_page();p.insert_text((50,50),'SYNTHETIC ONLY')
        raw=doc.tobytes();doc.close();inp={**I,'files':[{'id':SOURCE,'mimeType':'application/pdf'}]}
        active=0;peak=0;lock=threading.Lock()
        def recognize(content):
            nonlocal active,peak
            with lock:active+=1;peak=max(peak,active)
            time.sleep(.05)
            with lock:active-=1
            return page(items=[{'name':'Test','result_raw':'raw'}])
        out,stats=m.extract_document(inp,D,{'model':'test','parallelRequests':2,'maxPages':40},lambda f,s:raw,recognizer=recognize)
        self.assertEqual(peak,2);self.assertEqual(stats['pages'],2);self.assertEqual([x['ordinal'] for x in out['items']],[1,2])
        self.assertEqual([e['page_number'] for e in out['evidence'] if e['original_label']=='Test'],[1,2])

if __name__=='__main__':unittest.main(verbosity=2)
