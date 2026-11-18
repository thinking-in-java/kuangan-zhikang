"""Real Responses function calls via A's HTTP API. Synthetic-only fixtures; no production writes."""
from pathlib import Path
import os,sys,json,uuid,requests,hashlib,io,time
from datetime import datetime,timezone
from PIL import Image
ROOT=Path(__file__).resolve().parents[2];sys.path.insert(0,str(ROOT/'database'))
from health_archive_db import connect
BASE=os.environ.get('EHR_AGENT_TEST_URL','http://localhost:18083')
s=requests.Session();s.trust_env=False
assert s.get(BASE+'/ehr/api/agent/context',timeout=10).status_code==401
s.get(BASE+'/login',timeout=10)
assert s.post(BASE+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'rememberMe':'false','code':'123465'},timeout=30).json()['code']==200
ctx=s.get(BASE+'/ehr/api/context',timeout=20).json()['data'];assert ctx['dataMode']=='synthetic','Refuse production test'
h={'X-CSRF-Token':ctx['csrfToken']};conversations=[];record=None;file=None;checks=[]
def api(method,path,body=None,expect=200):
 r=s.request(method,BASE+'/ehr/api'+path,json=body,headers=h,timeout=180)
 assert r.status_code==expect,(path,r.status_code,r.text[:500])
 return r.json()['data'] if expect==200 else r
def check(label,condition):
 assert condition,label
 checks.append(label);print('PASS',label,flush=True)
def uid():return str(uuid.uuid4())
def conversation():
 c=api('POST','/agent/conversations',{})['id'];conversations.append(c);return c
def chat(cid,message,selected='',request=None):
 return api('POST','/agent/chat',{'conversationId':cid,'requestId':request or uid(),'message':message,'recordId':selected})
try:
 run=uuid.uuid4().hex[:10];code='AGENTLIVE_'+run
 p=api('POST','/records',{'name':'智能体联调职工'+run,'code':code,'sex':'未填写','org':'智能体隔离测试单位','team':'智能体隔离班组','job':'测试岗位'})
 record=p['id'];rid,src,ev=uid(),uid(),uid();stamp=datetime.now(timezone.utc).strftime('%Y-%m-%d %H:%M:%S.%f')
 storage=Path(os.environ.get('EHR_AGENT_TEST_STORAGE','F:/workstation/.tools/runtime/agent-test-files'));storage.mkdir(parents=True,exist_ok=True)
 buf=io.BytesIO();Image.new('RGB',(20,20),(18,169,145)).save(buf,format='PNG');raw=buf.getvalue();file=storage/(src+'.source');file.write_bytes(raw)
 c=connect()
 try:
  def insert(table,**fields):
   with c.cursor() as q:q.execute('INSERT INTO '+table+' ('+','.join(fields)+') VALUES ('+','.join(['%s']*len(fields))+')',tuple(fields.values()))
  scoped={'scope_code':ctx['scope'],'data_mode':'synthetic'}
  insert('a_report_sources',**scoped,source_file_id=src,sha256=hashlib.sha256(raw).digest(),object_ref=file.name.encode(),page_count=1,created_at=stamp)
  insert('a_report_series',**scoped,report_id=rid,source_file_id=src,created_at=stamp)
  insert('a_health_reports',**scoped,report_id=rid,revision=1,record_id=record,report_kind='individual',field_values='[]',dictionary_version='2.0.0',review_status='needs_review',exam_date_raw='2026-11-16',institution_raw='智能体联调测试医院',created_at=stamp)
  insert('a_report_revision_sources',**scoped,report_id=rid,revision=1,source_file_id=src)
  with c.cursor() as q:q.execute('UPDATE a_report_series SET current_revision=1 WHERE report_id=%s',(rid,))
  insert('a_report_upload_metadata',**scoped,upload_id=uid(),source_file_id=src,request_key=uid(),upload_channel='backend_file',original_file_name='synthetic-agent-fixture.png',mime_type='image/png',file_size=len(raw),uploader_ref=ctx['user'].encode(),uploaded_at=stamp)
  insert('a_report_evidence',report_id=rid,revision=1,evidence_id=ev,source_file_id=src,page_number=1,coordinate_system='object_only',page_rotation=0,original_label='隔离测试原文',raw_fragment='测试血糖8.2 mmol/L，参考3.9-6.1，H；仅测试，建议核对。',extraction_method='manual_transcription',extractor_version='agent-test',review_status='needs_review')
  insert('a_health_items',report_id=rid,revision=1,item_id=uid(),ordinal=1,primary_evidence_id=ev,evidence_ids=json.dumps([ev]),original_field_name='测试空腹血糖',result_raw='8.2',unit_raw='mmol/L',reference_range_raw='3.9-6.1',abnormal_flag_raw='H',field_values='[]',mapping_status='unmapped',review_status='needs_review')
  insert('a_health_conclusions',report_id=rid,revision=1,conclusion_id=uid(),primary_evidence_id=ev,evidence_ids=json.dumps([ev]),original_field_name='体检结论',original_text='测试血糖偏高。仅供接口隔离测试，不构成真实医学结论。',completeness='complete',review_status='needs_review')
  insert('a_health_advice',report_id=rid,revision=1,advice_id=uid(),source_kind='pdf',source_evidence_id=ev,original_field_name='医生建议',text_raw='建议核对原件，必要时复查；仅测试。',review_status='needs_review')
  c.commit()
 finally:c.close()
 cid=conversation();key=uid()
 r=chat(cid,'请查找工号'+code+'的职工，告诉我他的姓名和班组。',request=key)
 check('real_model_search_tool',any(t['name']=='search_employees' and t['status']=='ok' for t in r['tools']) and p['name'] in r['text'])
 again=chat(cid,'请查找工号'+code+'的职工，告诉我他的姓名和班组。',request=key)
 check('http_chat_idempotent',again['turnId']==r['turnId'])
 analysis=chat(cid,'查看并分析这个职工的体检报告，列出日期、机构、血糖原值单位与参考范围、审核状态和证据页码，不要直接确诊基础病。',record)
 check('real_model_report_analysis',any(t['name']=='get_exam_report' and t['status']=='ok' for t in analysis['tools']) and '8.2' in analysis['text'] and bool(analysis['sources']))
 check('original_evidence_download',s.get(BASE+'/ehr/api/files/'+src,timeout=15).content==raw)
 update=chat(cid,'把工号'+code+'的班组修改为智能体更正班组，先给我预览。',record)
 check('model_update_preview',bool(update['actions']) and any(a['type']=='update_employee' for a in update['actions']))
 action=next(a for a in update['actions'] if a['type']=='update_employee')
 check('update_not_automatic',api('GET','/records/'+record)['team']=='智能体隔离班组')
 api('POST','/agent/actions/'+action['id']+'/resolve',{'confirm':True,'employeeCode':code})
 check('confirmed_update_via_http',api('GET','/records/'+record)['team']=='智能体更正班组')
 delete=chat(cid,'删除工号'+code+'的职工档案，原因是隔离联调完成。请先展示预览。',record)
 action=next(a for a in delete['actions'] if a['type']=='delete_employee')
 check('delete_not_automatic',len(api('GET','/records/'+record)['reports'])==1)
 api('POST','/agent/actions/'+action['id']+'/resolve',{'confirm':True,'employeeCode':'wrong'},expect=422)
 result=api('POST','/agent/actions/'+action['id']+'/resolve',{'confirm':True,'employeeCode':code})
 check('delete_confirmed_http',result['status']=='confirmed')
 api('GET','/records/'+record,expect=404)
 check('repeat_confirmation_idempotent',api('POST','/agent/actions/'+action['id']+'/resolve',{'confirm':True,'employeeCode':code})==result)
 report={'checks':checks,'count':len(checks),'realModelCalls':True,'mode':'synthetic','reportAnalysisMs':analysis['elapsedMs'],'productionDataModified':False}
 (ROOT/'database/agent-live-verification.json').write_text(json.dumps(report,ensure_ascii=False,indent=2),encoding='utf-8')
 print(json.dumps(report,ensure_ascii=True),flush=True)
finally:
 if record:
  r=s.get(BASE+'/ehr/api/records/'+record,timeout=20)
  if r.status_code==200:api('DELETE','/records/'+record,{'rowVersion':r.json()['data']['rowVersion'],'reason':'数字健康师隔离联调清理'})
 if conversations:
  c=connect()
  try:
   with c.cursor() as q:
    for cid in conversations:
     q.execute('DELETE l FROM a_agent_tool_logs l JOIN a_agent_turns t ON t.turn_id=l.turn_id WHERE t.conversation_id=%s',(cid,))
     q.execute('DELETE FROM a_agent_actions WHERE conversation_id=%s',(cid,));q.execute('DELETE FROM a_agent_turns WHERE conversation_id=%s',(cid,));q.execute('DELETE FROM a_agent_conversations WHERE conversation_id=%s',(cid,))
   c.commit()
  finally:c.close()
 if file:file.unlink(missing_ok=True)
