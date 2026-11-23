"""Report/process and page/thread concurrency against a local fixture API only."""
import hashlib
import io
import json
import multiprocessing
import os
import queue
import signal
import sys
import tempfile
import threading
import time
import unittest
import uuid
from concurrent.futures import ThreadPoolExecutor
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from unittest.mock import patch

ROOT=Path(__file__).resolve().parents[2]
sys.path.insert(0,str(ROOT/'scripts'))
import model_recognition_worker as m


def fixture_worker(base,config,token,owner,budget,events,fail):
    def recognize(session,settings,content,dictionary):
        raw=content['text'].strip()
        events.put(('start',time.monotonic(),os.getpid()))
        try:
            time.sleep(.15)
            if fail and 'FIXTURE B' in raw:
                raise m.AdapterError('MODEL_FIXTURE_FAILED','Synthetic failure',False)
            return {'report_kind':'individual','metadata':{},'fields':[{'field_key':'full_name','label':'姓名','value_raw':raw.splitlines()[0],'quote':raw}],
                    'items':[{'name':'Synthetic item','result_raw':raw,'quote':raw}],'conclusions':[],'advice':[]}
        finally:
            events.put(('end',time.monotonic(),os.getpid()))
    m.recognize_page=recognize
    worker=m.Worker(base,'synthetic',config,token,worker_id=owner,request_limiter=m.RequestLimiter(budget,owner))
    try:worker.run(once=True)
    finally:worker.session.close();worker.model_session.close()


class FixtureAPI:
    def __init__(self):
        self.jobs={};self.claims=[];self.results={};self.failures={};self.progress={}
        self.lock=threading.Lock();self.barrier=threading.Barrier(3);self.peak=0
        for label in 'ABC':
            doc=m.fitz.open()
            for n in range(2):doc.new_page().insert_text((50,50),'FIXTURE '+label+'\nPAGE '+str(n+1))
            raw=doc.tobytes();doc.close();jid=str(uuid.uuid4());source=str(uuid.uuid4())
            self.jobs[jid]={'raw':raw,'source':source,'report':str(uuid.uuid4()),'lease':str(uuid.uuid4()),'claimed':False,'finished':False}
        fixture=self
        class Handler(BaseHTTPRequestHandler):
            def log_message(self,*args):pass
            def send_json(self,data):
                raw=json.dumps({'code':0,'data':data}).encode();self.send_response(200);self.send_header('Content-Type','application/json');self.send_header('Content-Length',str(len(raw)));self.end_headers();self.wfile.write(raw)
            def do_GET(self):
                if self.path.endswith('/dictionary'):
                    return self.send_json({'standardFields':[{'field_key':'full_name','field_name':'姓名'}],'medicalItems':[]})
                parts=self.path.split('/');jid=parts[4];job=fixture.jobs[jid]
                assert self.headers['X-OCR-Lease']==job['lease']
                if self.path.endswith('/input'):
                    return self.send_json({'job_id':jid,'scope_code':'org:CONCURRENCY_FIXTURE','data_mode':'synthetic','record_id':str(uuid.uuid4()),
                        'report_id':job['report'],'revision':1,'report_kind':'individual','prior_revision':None,
                        'source_files':[{'source_file_id':job['source'],'sha256':hashlib.sha256(job['raw']).hexdigest(),'page_count':2}],
                        'files':[{'id':job['source'],'mimeType':'application/pdf','downloadPath':'/ehr/worker/tasks/'+jid+'/files/'+job['source']}]})
                if '/files/' in self.path:
                    self.send_response(200);self.send_header('Content-Length',str(len(job['raw'])));self.end_headers();return self.wfile.write(job['raw'])
                if self.path.endswith('/receipt'):return self.send_json({'accepted':jid in fixture.results})
                raise AssertionError(self.path)
            def do_POST(self):
                body=json.loads(self.rfile.read(int(self.headers['Content-Length'])))
                if self.path.endswith('/claim'):
                    with fixture.lock:
                        jid=next(j for j,row in fixture.jobs.items() if not row['claimed']);job=fixture.jobs[jid];job['claimed']=True
                        fixture.claims.append((jid,body['workerId'],body['requestKey']))
                        fixture.peak=max(fixture.peak,sum(row['claimed'] and not row['finished'] for row in fixture.jobs.values()))
                    fixture.barrier.wait(timeout=20)
                    paths={key:'/ehr/worker/tasks/'+jid+'/'+suffix for key,suffix in [('inputPath','input'),('progressPath','heartbeat'),('resultPath','result'),('failurePath','failure')]}
                    return self.send_json({'task':{'jobId':jid,'leaseToken':job['lease'],**paths}})
                jid=self.path.split('/')[4];job=fixture.jobs[jid];assert self.headers['X-OCR-Lease']==job['lease']
                with fixture.lock:
                    if self.path.endswith('/heartbeat'):
                        assert body['percent']>=fixture.progress.get(jid,0);fixture.progress[jid]=body['percent']
                    elif self.path.endswith('/result'):
                        assert body['job_id']==jid and body['report_id']==job['report']
                        assert all(e['source_file_id']==job['source'] for e in body['evidence'])
                        fixture.results[jid]=body;job['finished']=True
                    elif self.path.endswith('/failure'):
                        fixture.failures[jid]=body;job['finished']=True
                    else:raise AssertionError(self.path)
                return self.send_json({})
        self.server=ThreadingHTTPServer(('127.0.0.1',0),Handler)
        self.thread=threading.Thread(target=self.server.serve_forever,daemon=True);self.thread.start()
    def close(self):self.server.shutdown();self.server.server_close();self.thread.join(timeout=3)


class Checks(unittest.TestCase):
    def test_default_and_bounded_pool_limits(self):
        self.assertEqual(m.concurrency_limits({}),(3,12))
        self.assertEqual(m.concurrency_limits({'concurrentReports':99,'maxTotalRequests':999}),(8,24))
        self.assertEqual(m.concurrency_limits({'concurrentReports':0,'maxTotalRequests':-5}),(1,1))
        self.assertEqual(m.concurrency_limits({'concurrentReports':True,'maxTotalRequests':'bad'}),(3,12))
    def test_budget_caps_threads_across_owners(self):
        budget=m.RequestBudget(2);owners=['owner-'+str(i) for i in range(3)]
        for owner in owners:budget.activate(owner)
        peak=0;lock=threading.Lock()
        def call(i):
            nonlocal peak
            with m.request_slot(m.RequestLimiter(budget,owners[i%3])):
                with lock:peak=max(peak,budget.status()['activeRequests'])
                time.sleep(.02)
        with ThreadPoolExecutor(max_workers=12) as pool:list(pool.map(call,range(24)))
        self.assertEqual(peak,2);self.assertEqual(budget.status()['activeRequests'],0)
    def test_error_always_returns_request_capacity(self):
        budget=m.RequestBudget(1);budget.activate('one')
        with self.assertRaises(ValueError):
            with m.request_slot(m.RequestLimiter(budget,'one')):raise ValueError('fixture')
        self.assertEqual(budget.status()['activeRequests'],0)
    def test_dead_owner_cannot_leak_permits_or_reacquire(self):
        budget=m.RequestBudget(1);budget.activate('dead');budget.activate('live');self.assertTrue(budget.acquire('dead'))
        budget.deactivate('dead');self.assertTrue(budget.acquire('live',.1));budget.release('dead')
        self.assertEqual(budget.status()['activeRequests'],1);self.assertFalse(budget.acquire('dead',.01));budget.release('live')
        self.assertEqual(budget.status()['activeRequests'],0)
    def test_cancelled_wait_does_not_call_model(self):
        budget=m.RequestBudget(1);budget.activate('busy');budget.activate('cancelled');budget.acquire('busy');cancelled=threading.Event();cancelled.set()
        with self.assertRaises(m.AdapterError) as caught:
            with m.request_slot(m.RequestLimiter(budget,'cancelled'),cancelled):self.fail('must not enter')
        self.assertEqual(caught.exception.code,'MODEL_LEASE_LOST');budget.release('busy')
    def test_reducing_budget_does_not_interrupt_inflight_requests(self):
        budget=m.RequestBudget(2);budget.activate('one');budget.acquire('one');budget.acquire('one');budget.set_limit(1)
        self.assertFalse(budget.acquire('one',.01));budget.release('one');self.assertFalse(budget.acquire('one',.01));budget.release('one');self.assertTrue(budget.acquire('one',.01));budget.release('one')
    def test_claim_network_retry_reuses_same_idempotency_key(self):
        with tempfile.TemporaryDirectory() as directory:
            config=Path(directory)/'config.json';token=Path(directory)/'token';config.write_text(json.dumps({'enabled':True,'baseUrl':'https://example.invalid','model':'fixture','apiKey':'fixture'}));token.write_text('fixture-token')
            worker=m.Worker('http://127.0.0.1:1','synthetic',config,token);keys=[]
            def api(path,body=None):
                if path.endswith('/dictionary'):return {}
                keys.append(body['requestKey'])
                if len(keys)==1:raise ConnectionError('fixture ambiguous response')
                return {'task':None}
            worker.api=api
            try:
                with self.assertRaises(ConnectionError):worker.run(True)
                worker.run(True);self.assertEqual(keys[0],keys[1])
            finally:worker.session.close();worker.model_session.close()
    def test_retiring_worker_does_not_claim_new_report(self):
        with tempfile.TemporaryDirectory() as directory:
            config=Path(directory)/'config.json';token=Path(directory)/'token';config.write_text(json.dumps({'enabled':True,'baseUrl':'https://example.invalid','model':'fixture','apiKey':'fixture','concurrentReports':1}));token.write_text('fixture-token')
            worker=m.Worker('http://127.0.0.1:1','synthetic',config,token,slot=1);calls=[];worker.api=lambda path:calls.append(path) or {}
            try:worker.run(True);self.assertEqual(calls,['/ehr/worker/dictionary'])
            finally:worker.session.close();worker.model_session.close()
    def run_process_case(self,fail):
        fixture=FixtureAPI();ctx=multiprocessing.get_context('spawn');manager=m.BudgetManager(ctx=ctx);manager.start();budget=manager.RequestBudget(2);events=ctx.Queue();processes=[]
        try:
            with tempfile.TemporaryDirectory() as directory:
                config=Path(directory)/'config.json';token=Path(directory)/'token'
                config.write_text(json.dumps({'enabled':True,'baseUrl':'https://example.invalid','model':'fixture','apiKey':'fixture','parallelRequests':2,'nativeTextMinChars':1}));token.write_text('fixture-token')
                for i in range(3):
                    owner='fixture-report-'+str(i);budget.activate(owner);p=ctx.Process(target=fixture_worker,args=('http://127.0.0.1:'+str(fixture.server.server_port),str(config),str(token),owner,budget,events,fail));p.start();processes.append(p)
                for p in processes:p.join(timeout=35);self.assertFalse(p.is_alive());self.assertEqual(p.exitcode,0)
                self.assertEqual(fixture.peak,3);self.assertEqual(len({w for _,w,_ in fixture.claims}),3)
                self.assertEqual(len(fixture.results),2 if fail else 3);self.assertEqual(len(fixture.failures),1 if fail else 0)
                for jid,payload in fixture.results.items():
                    self.assertEqual([row['ordinal'] for row in payload['items']],[1,2]);self.assertIsNone(payload['exam_date_verified']);self.assertEqual(fixture.progress[jid],95)
                    self.assertEqual({e['page_number'] for e in payload['evidence']},{1,2})
                self.assertEqual(budget.status()['activeRequests'],0)
                recorded=[]
                while True:
                    try:recorded.append(events.get(timeout=.2))
                    except queue.Empty:break
                active=0;peak=0
                for kind,when,pid in sorted(recorded,key=lambda event:event[1]):active+=1 if kind=='start' else -1;peak=max(peak,active)
                self.assertEqual(active,0);self.assertEqual(peak,2)
                self.assertEqual(len(list((Path(directory)/'model-results').glob('*.candidate.json'))),len(fixture.results))
        finally:
            for p in processes:
                if p.is_alive():p.terminate()
                p.join(timeout=3)
            manager.shutdown();events.close();fixture.close()
    def test_three_reports_run_concurrently_with_shared_two_request_cap(self):self.run_process_case(False)
    def test_failed_report_does_not_block_other_reports(self):self.run_process_case(True)
    def test_pool_respawns_idle_worker_resizes_and_stops(self):
        class IdleAPI(BaseHTTPRequestHandler):
            def log_message(self,*args):pass
            def reply(self,data):
                raw=json.dumps({'code':0,'data':data}).encode();self.send_response(200);self.send_header('Content-Length',str(len(raw)));self.end_headers();self.wfile.write(raw)
            def do_GET(self):self.reply({'standardFields':[],'medicalItems':[]})
            def do_POST(self):self.rfile.read(int(self.headers.get('Content-Length',0)));self.reply({'task':None,'retryAfterSeconds':1})
        server=ThreadingHTTPServer(('127.0.0.1',0),IdleAPI);thread=threading.Thread(target=server.serve_forever,daemon=True);thread.start()
        process=None
        try:
            with tempfile.TemporaryDirectory() as directory:
                config=Path(directory)/'config.json';token=Path(directory)/'token';token.write_text('fixture-token')
                settings={'enabled':True,'baseUrl':'https://example.invalid','model':'fixture','apiKey':'fixture','concurrentReports':3,'maxTotalRequests':2}
                def save():
                    temporary=config.with_suffix('.tmp');temporary.write_text(json.dumps(settings));temporary.replace(config)
                save();ctx=multiprocessing.get_context('spawn');process=ctx.Process(target=m.run_report_pool,args=('http://127.0.0.1:'+str(server.server_port),'synthetic',str(config),str(token)));process.start()
                path=Path(directory)/'model-logs/pool-synthetic.json'
                def wait_for(predicate):
                    deadline=time.monotonic()+20
                    while time.monotonic()<deadline:
                        self.assertTrue(process.is_alive())
                        try:
                            status=json.loads(path.read_text())
                            if predicate(status):return status
                        except (FileNotFoundError,ValueError,PermissionError):pass
                        time.sleep(.05)
                    self.fail('report pool did not reach expected topology')
                first=wait_for(lambda s:len(s['workerPids'])==3);killed=first['workerPids'][0];os.kill(killed,signal.SIGTERM)
                repaired=wait_for(lambda s:len(s['workerPids'])==3 and killed not in s['workerPids']);self.assertEqual(repaired['activeRequests'],0)
                settings['concurrentReports']=1;save();wait_for(lambda s:s['reportConcurrency']==1 and len(s['workerPids'])==1)
                settings['concurrentReports']=3;settings['maxTotalRequests']=1;save();wait_for(lambda s:s['reportConcurrency']==3 and len(s['workerPids'])==3 and s['maxTotalRequests']==1)
                settings['enabled']=False;save();process.join(timeout=15);self.assertFalse(process.is_alive());self.assertEqual(process.exitcode,0)
        finally:
            if process is not None:
                if process.is_alive():process.terminate()
                process.join(timeout=5)
            server.shutdown();server.server_close();thread.join(timeout=3)


if __name__=='__main__':multiprocessing.freeze_support();unittest.main(verbosity=2)
