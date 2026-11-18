"""Isolated synthetic instance + real Responses provider; only ephemeral fictional profiles.

Requires the synthetic instance at 18084 with the test-only WeChat AppID. This script
does not bypass login in the product: it provisions opaque fixture sessions directly
in the test DB and removes every owned row in finally. Never prints tokens or keys.
"""
from pathlib import Path
import os,sys,time,uuid,json,hashlib,secrets
from datetime import datetime,timedelta,timezone
import requests

ROOT=Path(__file__).resolve().parents[2];sys.path.insert(0,str(ROOT/'database'))
from health_archive_db import connect
BASE=os.environ.get('EHR_MINI_AGENT_TEST_URL','http://localhost:18084');HTTP=requests.Session();HTTP.trust_env=False
run=uuid.uuid4().hex[:10];scope='org:MINI_HTTP_'+run;people=[];checks=[];conversations=[]
now=lambda:datetime.now(timezone.utc).replace(tzinfo=None)
uid=lambda:str(uuid.uuid4())
def check(name,value):
    assert value,name
    checks.append(name);print('PASS',name,flush=True)
def req(method,path,token=None,data=None,expected=200):
    r=HTTP.request(method,BASE+'/ehr/miniapp/agent'+path,headers={'Authorization':'Bearer '+token} if token else {},json=data,timeout=20)
    check(method+' '+path+' HTTP '+str(expected),r.status_code==expected)
    return r.json()
def seed(label,bound=True):
    p={'user':uid(),'employee':uid(),'record':uid(),'code':uid(),'binding':uid(),'token':secrets.token_urlsafe(32),'session':uid(),'label':label}
    people.append(p);c=connect()
    try:
        with c.cursor() as q:
            q.execute('INSERT INTO a_miniapp_users(mini_user_id,data_mode,app_id,open_id,consent_version,consent_at,created_at,last_login_at) VALUES(%s,\'synthetic\',%s,%s,\'test\',%s,%s,%s)',(p['user'],'wx0000000000000000','synthetic-mini-agent-'+p['user'],now(),now(),now()))
            q.execute('INSERT INTO a_miniapp_sessions(mini_session_id,mini_user_id,data_mode,token_hash,expires_at,created_at) VALUES(%s,%s,\'synthetic\',%s,%s,%s)',(p['session'],p['user'],hashlib.sha256(p['token'].encode()).digest(),now()+timedelta(hours=1),now()))
            if bound:
                q.execute('INSERT INTO a_employees(employee_id,scope_code,data_mode,roster_employee_key,identity_ref,roster_source_ref,created_at) VALUES(%s,%s,\'synthetic\',%s,%s,%s,%s)',(p['employee'],scope,'mini-agent-http:'+p['employee'],hashlib.sha256(p['employee'].encode()).digest(),b'synthetic-agent-http',now()))
                q.execute('INSERT INTO a_health_records(record_id,employee_id,scope_code,data_mode,created_at) VALUES(%s,%s,%s,\'synthetic\',%s)',(p['record'],p['employee'],scope,now()))
                q.execute('INSERT INTO a_employee_roster_profiles(employee_id,scope_code,data_mode,employee_name,employee_no,gender,organization_name,team_name,position_name,created_at,updated_at) VALUES(%s,%s,\'synthetic\',%s,%s,\'未填写\',\'合成测试单位\',%s,%s,%s,%s)',(p['employee'],scope,'合成'+label+'职工','TEST_'+label+'_'+run,'合成'+label+'班组','合成'+label+'岗位',now(),now()))
                q.execute('INSERT INTO a_employee_binding_codes(binding_code_id,scope_code,data_mode,record_id,code_hash,code_status,issue_request_key,issued_by_ref,issued_at,expires_at,consumed_by,consumed_at) VALUES(%s,%s,\'synthetic\',%s,%s,\'consumed\',%s,%s,%s,%s,%s,%s)',(p['code'],scope,p['record'],hashlib.sha256(p['code'].encode()).digest(),uid(),b'synthetic-agent-http',now(),now()+timedelta(hours=1),p['user'],now()))
                q.execute('INSERT INTO a_miniapp_employee_bindings(binding_id,mini_user_id,scope_code,data_mode,record_id,binding_code_id,confirmed_digest,confirmed_at) VALUES(%s,%s,%s,\'synthetic\',%s,%s,%s,%s)',(p['binding'],p['user'],scope,p['record'],p['code'],b'x'*32,now()))
        c.commit();return p
    finally:c.close()
try:
    for _ in range(60):
        try:
            if HTTP.get(BASE+'/login',timeout=3).status_code==200:break
        except requests.RequestException:pass
        time.sleep(1)
    a,b,u=seed('甲'),seed('乙'),seed('未绑定',False)
    req('GET','/context',expected=401);req('GET','/context',u['token'],expected=403)
    ctx=req('GET','/context',a['token'])['data'];check('real_provider_enabled_on_personal_api',ctx['enabled'] and ctx['personalOnly'] and not ctx['canModify'])
    req('POST','/conversations',a['token'],{'recordId':b['record']},422)
    for p in (a,b):
        p['cid']=req('POST','/conversations',p['token'],{})['data']['id'];conversations.append(p['cid'])
    req('GET','/conversations/'+a['cid'],b['token'],expected=404)
    req('DELETE','/conversations/'+a['cid'],b['token'],expected=404)
    req('POST','/chat',a['token'],{'conversationId':a['cid'],'requestId':uid(),'message':'查资料','recordId':b['record']},422)
    for p in (a,b):
        p['question']={'conversationId':p['cid'],'requestId':uid(),'message':'请必须使用工具查询我本人的班组和岗位，然后简短告诉我查询到的这两个名称。不要查询其他职工。'}
        p['mid']=req('POST','/chat',p['token'],p['question'],202)['data']['messageId']
        duplicate=HTTP.post(BASE+'/ehr/miniapp/agent/chat',headers={'Authorization':'Bearer '+p['token']},json=p['question'],timeout=20)
        check('duplicate_request_reuses_message_'+p['label'],duplicate.status_code in (200,202) and duplicate.json()['data']['messageId']==p['mid'])
    req('GET','/messages/'+a['mid'],b['token'],expected=404)
    started=time.monotonic()
    for p in (a,b):
        while time.monotonic()-started<210:
            r=HTTP.get(BASE+'/ehr/miniapp/agent/messages/'+p['mid'],headers={'Authorization':'Bearer '+p['token']},timeout=20);assert r.status_code==200
            result=r.json()['data']
            if result['status'] not in ('queued','running'):break
            time.sleep(1.5)
        check('real_model_reply_completed_'+p['label'],result['status']=='completed')
        check('model_reply_uses_own_profile_'+p['label'],'合成'+p['label']+'岗位' in result['answer'] and '合成'+('乙' if p is a else '甲')+'岗位' not in result['answer'])
        c=connect()
        try:
            with c.cursor() as q:
                q.execute("SELECT COUNT(*) n FROM a_agent_tool_logs WHERE turn_id=%s AND tool_name='get_my_profile' AND outcome='ok'",(p['mid'],));check('real_function_call_executed_'+p['label'],q.fetchone()['n']>0)
        finally:c.close()
        req('GET','/conversations/'+p['cid'],p['token'])
        again=req('POST','/chat',p['token'],p['question'])['data'];check('completed_retry_returns_cached_answer_'+p['label'],again['status']=='completed' and again['messageId']==p['mid'])
        req('DELETE','/conversations/'+p['cid'],p['token']);req('GET','/messages/'+p['mid'],p['token'],expected=404)
    report={'checks':checks,'count':len(checks),'syntheticOnly':True,'realProviderFunctionCalls':True,'noRealEmployeeDataUsed':True,'elapsedSeconds':round(time.monotonic()-started,2)}
    (ROOT/'database/miniapp-agent-live-verification.json').write_text(json.dumps(report,ensure_ascii=False,indent=2),encoding='utf8')
    print('PASS synthetic HTTP + actual model checks:',len(checks),flush=True)
finally:
    c=connect()
    try:
        with c.cursor() as q:
            q.execute("SELECT conversation_id FROM a_agent_conversations WHERE client_type='miniapp' AND scope_code=%s AND data_mode='synthetic'",(scope,))
            for row in q.fetchall():
                cid=row['conversation_id'];q.execute('DELETE l FROM a_agent_tool_logs l JOIN a_agent_turns t ON t.turn_id=l.turn_id WHERE t.conversation_id=%s',(cid,));q.execute('DELETE FROM a_agent_actions WHERE conversation_id=%s',(cid,));q.execute('DELETE FROM a_agent_turns WHERE conversation_id=%s',(cid,));q.execute('DELETE FROM a_agent_conversations WHERE conversation_id=%s',(cid,))
            for p in people:
                for bucket in ['day:', 'minute:']:
                    q.execute('DELETE FROM a_miniapp_rate_limits WHERE bucket_key=%s',(hashlib.sha256(('mini-agent:synthetic:'+bucket+p['user']).encode()).digest(),))
                q.execute('DELETE FROM a_miniapp_sessions WHERE mini_user_id=%s',(p['user'],));q.execute('DELETE FROM a_miniapp_employee_bindings WHERE mini_user_id=%s',(p['user'],));q.execute('DELETE FROM a_employee_binding_codes WHERE binding_code_id=%s',(p['code'],));q.execute('DELETE FROM a_miniapp_users WHERE mini_user_id=%s',(p['user'],));q.execute('DELETE FROM a_employee_roster_profiles WHERE employee_id=%s',(p['employee'],));q.execute('DELETE FROM a_health_records WHERE record_id=%s',(p['record'],));q.execute('DELETE FROM a_employees WHERE employee_id=%s',(p['employee'],))
        c.commit();print('PASS ephemeral HTTP fixtures removed',flush=True)
    finally:c.close()
