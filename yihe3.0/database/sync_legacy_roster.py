"""Idempotent, insert-only synchronization of an authorized legacy enterprise roster.

Dry run is default. No legacy records or clinical reports are changed.
"""
from pathlib import Path
from datetime import datetime,timezone,date
import argparse,hashlib,json,re,uuid
from health_archive_db import connect
B=Path(__file__).resolve().parent
def dump(x):return json.dumps(x,ensure_ascii=False,sort_keys=True,default=str,separators=(',',':'))
def text(x):return '' if x is None else str(x).strip()
def descendants(rows,owner):
 allowed={owner}
 for _ in range(len(rows)+1):
  new={str(r['Org_Code']) for r in rows if str(r['Super_Org_Code']) in allowed and r['Is_Del'] in (None,0)}-allowed
  if not new:return allowed
  allowed|=new
 raise ValueError('Organization hierarchy is cyclic')
def main():
 p=argparse.ArgumentParser();p.add_argument('--apply',action='store_true');p.add_argument('--owner-org',default='10000');p.add_argument('--actor',default='DA000087');p.add_argument('--report',default='legacy-roster-sync-preview.json');a=p.parse_args()
 scope='org:'+a.owner_org;run=str(uuid.uuid4());c=connect();report={'apply':a.apply,'runId':run,'scope':scope,'mode':'real','source':'new_reg','policy':'insert only; preserve existing profiles; no clinical reports','sourceRows':0,'plannedNew':0,'existing':0,'linkedExisting':0,'skipped':[],'warnings':[],'newRecords':[]}
 try:
  with c.cursor() as q:
   q.execute("SELECT GET_LOCK('a_ehr_legacy_roster_sync',10) locked");assert q.fetchone()['locked']==1
   q.execute('SELECT Org_Code,User_Type FROM yh_user_reginfo WHERE user_code=%s',(a.actor,));actor=q.fetchone();assert actor and actor['Org_Code']==a.owner_org and actor['User_Type'] in (1,4),'Actor must administer target enterprise'
   q.execute('SELECT Org_Code,Super_Org_Code,Is_Del FROM yh_org_tree');allowed=descendants(q.fetchall(),a.owner_org)
   q.execute('SELECT * FROM new_reg ORDER BY id');source=q.fetchall();report['sourceRows']=len(source);before=hashlib.sha256(dump(source).encode()).hexdigest()
   q.execute('SELECT user_code,Org_Code FROM yh_user_reginfo');users={r['user_code']:r['Org_Code'] for r in q.fetchall()}
   q.execute("SELECT p.*,r.record_id FROM a_employee_roster_profiles p JOIN a_health_records r ON r.employee_id=p.employee_id WHERE p.scope_code=%s AND p.data_mode='real'",(scope,));existing=q.fetchall();codes={r['employee_no']:r for r in existing};links={r['legacy_user_code']:r for r in existing if r['legacy_user_code']}
   q.execute("SELECT COUNT(*) n FROM a_health_reports WHERE data_mode='real'");reports_before=q.fetchone()['n'];now=datetime.now(timezone.utc).replace(tzinfo=None);plans=[]
   bindings=[]
   for r in source:
    key=text(r['user_code']);name=text(r['name']);legacy_org=users.get(key);reason=None
    if not key or not name:reason='缺少姓名或旧用户编码，不能建立可信身份'
    elif not legacy_org or legacy_org not in allowed:reason='旧账号缺少组织关联或不在本企业授权组织树'
    if reason:report['skipped'].append({'legacyId':r['id'],'reason':reason});continue
    if key in codes or key in links:
     old=codes.get(key) or links[key]
     if old.get('legacy_user_code')!=key:
      same_name=text(old.get('employee_name'))==name
      same_birth=not old.get('birth_date') or not r['birth'] or str(old.get('birth_date'))[:10]==str(r['birth'])[:10]
      if old.get('legacy_user_code') is not None or not same_name or not same_birth or old.get('deleted_at'):
       report['skipped'].append({'legacyId':r['id'],'reason':'新库工号已存在但身份不能安全合并，保留原记录待人工核对'});continue
      bindings.append((r,key,old));report['linkedExisting']+=1
     report['existing']+=1;continue
    unit=text(r['work_unit']);assert unit,'Missing work unit for valid identity'
    team=text(r['v888']);phone=text(r['phone']);birth=r['birth'];warnings=[]
    if phone and (len(phone)>30 or not re.fullmatch(r'[+0-9 ()-]{5,30}',phone)):phone='';warnings.append('旧联系电话格式异常，新表留空，原值保留在旧表')
    if birth is not None:
     try:birth=date.fromisoformat(str(birth)[:10]);assert birth<=date.today()
     except Exception:birth=None;warnings.append('旧出生日期异常，新表留空')
    assert len(name)<=100 and len(key)<=50 and len(unit)<=255 and len(team)<=100
    employee=str(uuid.uuid5(uuid.NAMESPACE_URL,scope+':real:legacy-user:'+key));record=str(uuid.uuid5(uuid.NAMESPACE_URL,scope+':real:health-record:'+key))
    missing=([] if team else ['班组'])+['岗位','报告原件','核定体检日期','检查项目原值','完整体检结论'];completed=9-len(missing)
    source_meta={'sourceTable':'new_reg','sourceId':r['id'],'legacyOrgCode':legacy_org,'departmentRaw':r['department'],'legacyCreatedAtRaw':str(r['insert_date']) if r['insert_date'] else None,'teamSource':'v888','importRunId':run,'warnings':warnings}
    report['warnings'] += [{'legacyId':r['id'],'reason':v} for v in warnings]
    plans.append((r,key,name,unit,team,phone,birth,employee,record,missing,completed,source_meta))
    codes[key]={'legacy_user_code':key};links[key]=codes[key]
   report['plannedNew']=len(plans);report['authorizedOrgCount']=len(allowed)
   if a.apply:
    for r,key,old in bindings:
     q.execute('UPDATE a_employee_roster_profiles SET legacy_user_code=%s,row_version=row_version+1,updated_at=%s WHERE employee_id=%s AND legacy_user_code IS NULL',(key,now,old['employee_id']));assert q.rowcount==1
     q.execute('INSERT INTO a_health_archive_audit_logs(audit_id,scope_code,data_mode,record_id,actor_ref,action,request_key,reason,redacted_diff,occurred_at) VALUES(%s,%s,\'real\',%s,%s,\'legacy_identity_linked\',%s,%s,%s,%s)',(str(uuid.uuid4()),scope,old['record_id'],a.actor.encode(),str(uuid.uuid4()),'按相同工号、姓名和无冲突出生日期关联旧身份，保留所有现有资料',dump({'sourceTable':'new_reg','sourceId':r['id'],'importRunId':run}),now))
    for r,key,name,unit,team,phone,birth,employee,record,missing,completed,meta in plans:
     q.execute('INSERT INTO a_employees(employee_id,scope_code,data_mode,roster_employee_key,identity_ref,roster_source_ref,created_at) VALUES(%s,%s,\'real\',%s,%s,%s,%s)',(employee,scope,key,hashlib.sha256((scope+':'+key).encode()).digest(),('legacy:new_reg:'+str(r['id'])).encode(),now))
     q.execute('INSERT INTO a_health_records(record_id,scope_code,data_mode,employee_id,created_at) VALUES(%s,%s,\'real\',%s,%s)',(record,scope,employee,now))
     q.execute('INSERT INTO a_employee_roster_profiles(employee_id,scope_code,data_mode,employee_name,employee_no,gender,birth_date,phone,organization_name,team_name,position_name,exposure_history,legacy_user_code,created_at,updated_at) VALUES(%s,%s,\'real\',%s,%s,%s,%s,%s,%s,%s,\'\',%s,%s,%s,%s)',(employee,scope,name,key,{1:'男',2:'女'}.get(r['sex'],'未填写'),birth,phone,unit,team,text(r['exposure_history']),key,now,now))
     q.execute('INSERT INTO a_health_archive_audit_logs(audit_id,scope_code,data_mode,record_id,actor_ref,action,request_key,reason,redacted_diff,occurred_at) VALUES(%s,%s,\'real\',%s,%s,\'legacy_roster_imported\',%s,%s,%s,%s)',(str(uuid.uuid4()),scope,record,a.actor.encode(),str(uuid.uuid4()),'旧职工基础资料同步；岗位未提供，体检与风险未生成',dump(meta),now))
     q.execute('INSERT INTO a_health_record_completeness(snapshot_id,scope_code,data_mode,record_id,required_count,completed_count,missing_items,rule_version,basis,completeness_rate,calculated_at) VALUES(%s,%s,\'real\',%s,9,%s,%s,\'ehr-required-documents-v1\',%s,%s,%s)',(str(uuid.uuid4()),scope,record,completed,dump(missing),dump({'source':'legacy_roster','importRunId':run,'reportId':None,'revision':None}),round(completed/9,4),now))
     report['newRecords'].append({'legacyId':r['id'],'recordId':record,'employeeId':employee})
    q.execute('SELECT * FROM new_reg ORDER BY id');assert hashlib.sha256(dump(q.fetchall()).encode()).hexdigest()==before,'Legacy source changed during synchronization; rolled back'
    q.execute("SELECT COUNT(*) n FROM a_health_reports WHERE data_mode='real'");assert q.fetchone()['n']==reports_before,'Unexpected clinical report write'
    c.commit()
   else:c.rollback()
   report.update(sourceRowsSha256=before,legacyUnchanged=True,clinicalReportsCreated=0,verifiedAt=datetime.now(timezone.utc).isoformat())
   path=B/a.report;path.write_text(json.dumps(report,ensure_ascii=False,indent=2),encoding='utf-8')
   print(dump({k:v for k,v in report.items() if k not in ['newRecords','warnings']}));print('Warnings:',len(report['warnings']))
 finally:
  c.rollback()
  with c.cursor() as q:q.execute("SELECT RELEASE_LOCK('a_ehr_legacy_roster_sync')")
  c.close()
if __name__=='__main__':main()
