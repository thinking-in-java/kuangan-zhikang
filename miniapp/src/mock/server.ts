import { worker,secondWorker,doctor,seedReports,trends,seedAdvice,seedFollowups,articles } from './data'
import type {Profile,Report,Followup,Session,Message,ReviewDecision,SelectedFile} from '../types'
const clone=<T>(v:T):T=>JSON.parse(JSON.stringify(v))
const wait=()=>new Promise(resolve=>setTimeout(resolve,180))
function fail(message:string):never {throw new Error(message)}
let bound=false
let identityConfirmed=false
let workerEdits:Partial<Profile>={}
let profileVersion="mock-worker-v1"
const reports:Report[]=clone(seedReports)
reports.push({...clone(seedReports[0]),id:'R20261113002',status:'pending'})
reports.push({...clone(seedReports[0]),id:'R20261113003',status:'pending',workerId:secondWorker.id,workerName:secondWorker.name,employeeNo:secondWorker.employeeNo})
const followups:Followup[]=clone(seedFollowups)
const sessions:Record<string,Session[]>={}
const uploads=new Map<string,{owner:string;file:SelectedFile}>()
const submissions=new Map<string,Report>()
const processing=new Map<string,number>()
const authorized=[worker.id,secondWorker.id]
function identity(token:string):Profile {
 if(token==='demo-worker')return {...worker,...workerEdits,bound,identityConfirmed,profileVersion}
 if(token==='demo-doctor')return doctor
 if(token==='demo-pending')return {...doctor,role:'pending'}
 return fail('演示登录已过期，请重新登录')
}
function doctorOnly(p:Profile){if(p.role!=='doctor')fail('无医生权限')}
function canRead(p:Profile,r:Report){return p.role==='worker'?p.id===r.workerId:p.role==='doctor'&&authorized.includes(r.workerId)}
function archive(id:string){return {profile:id===worker.id?{...worker,...workerEdits,bound,identityConfirmed,profileVersion}:secondWorker,reports:reports.filter(r=>r.workerId===id),trends,advice:seedAdvice,followups:followups.filter(f=>f.workerId===id)}}
export async function mockUpload(file:SelectedFile,token:string,onProgress:(n:number)=>void) {
 const p=identity(token);if(p.role!=='worker'||(!p.bound||!identityConfirmed))fail('请完成职工身份绑定和信息确认')
 for(const n of [25,60,100]){await wait();onProgress(n)}
 const id='file-'+Date.now()+'-'+file.id;uploads.set(id,{owner:p.id,file:clone(file)});return id
}
export async function mockRequest<T>(path:string,method:string='GET',raw?:unknown,token=''):Promise<T>{
 await wait();const data=(raw||{}) as any
 if(path==='/auth/wechat/login') {
  const role=data.roleIntent==='doctor'?'doctor':data.roleIntent==='pending'?'pending':'worker'
  return clone({token:'demo-'+role,profile:identity('demo-'+role)}) as T
 }
 const p=identity(token)
 if(path==='/me/profile')return clone(p) as T
 if(path==='/auth/bind-worker'){
  if(p.role!=='worker')fail('仅职工可以绑定')
  if(!data.employeeNo||!data.name||data.verificationCode!=='123456')fail('演示验证码为 123456，请检查填写信息')
  bound=true;identityConfirmed=false;return clone({...worker,...workerEdits,bound,identityConfirmed,profileVersion}) as T
 }
 if(path==='/auth/confirm-worker'){
  if(p.role!=='worker'||!p.bound)fail('请先完成职工身份绑定')
  if(data.profileVersion!==profileVersion)fail('档案信息已更新，请刷新后重新核对')
  if(data.confirmed!==true)fail('请先核对并确认身份信息')
  const updates=data.updates||{}
  const allowed=['name','employeeNo','gender','birthDate','organization','team','job','phone']
  if(typeof updates!=='object'||Array.isArray(updates)||Object.keys(updates).some(k=>!allowed.includes(k)))fail('包含不可修改的字段')
  for(const [key,value] of Object.entries(updates)){
   if(typeof value!=='string'||value.length>80)fail('信息格式不正确')
   if((key==='name'||key==='employeeNo')&&!value.trim())fail('姓名和工号不能为空')
  }
  if(updates.phone&&!/^1\d{10}$/.test(updates.phone))fail('手机号码格式不正确')
  const next={...updates}
  if(next.phone){next.phoneMasked=next.phone.slice(0,3)+'****'+next.phone.slice(-4)}
  delete next.phone
  workerEdits={...workerEdits,...next}
  if(Object.keys(updates).length)profileVersion='mock-worker-'+Date.now()

  identityConfirmed=true;return clone({...worker,...workerEdits,bound,identityConfirmed,profileVersion}) as T
 }
 if(p.role==='pending'||(p.role==='worker'&&(!p.bound||!identityConfirmed)))fail('身份尚未完成绑定和信息确认')
 if(path.startsWith('/doctor/'))doctorOnly(p)
 if(path.startsWith('/me/')&&p.role!=='worker')fail('当前身份不能访问职工个人接口')
 if(path==='/me/health-archive')return clone(archive(p.id)) as T
 if(path==='/me/reports')return clone(reports.filter(r=>r.workerId===p.id)) as T
 if(path==='/me/advice')return clone(seedAdvice) as T
 if(path==='/me/followups')return clone(followups.filter(f=>f.workerId===p.id)) as T
 if(path==='/me/notifications')return clone([{id:'n1',title:'您有一项复查提醒',content:'请在复查计划中查看时间与要求，并按医生要求安排复查。',date:'2026-11-14 09:20'},{id:'n2',title:'体检报告审核完成',content:'您的演示体检报告已完成核对，可在健康档案中查看。',date:'2026-11-13 16:30'}]) as T
 if(path==='/knowledge/articles')return clone(articles) as T
 if(path==='/reports'&&method==='POST'){
  if(p.role!=='worker')fail('仅职工可以提交本人报告')
  const cacheKey=p.id+':'+data.idempotencyKey
  if(submissions.has(cacheKey))return clone(submissions.get(cacheKey)) as T
  if(!Array.isArray(data.fileIds)||!data.fileIds.length)fail('请先上传文件')
  const files=data.fileIds.map((id:string)=>{const u=uploads.get(id);if(!u||u.owner!==p.id)fail('文件无访问权限');return {id,name:u.file.name,url:u.file.path,type:u.file.type}})
  const followup=data.followupId?followups.find(f=>f.id===data.followupId&&f.workerId===p.id):undefined
  if(data.followupId&&!followup)fail('无权关联该复查任务')
  const r:Report={id:'R'+Date.now(),title:data.type,date:data.date,institution:data.institution,type:data.type,status:'processing',workerId:p.id,workerName:p.name,employeeNo:p.employeeNo,files,fields:[],conclusion:'待后端处理与医生核对',analysis:'待后端生成辅助分析'}
  reports.unshift(r);processing.set(r.id,Date.now());submissions.set(cacheKey,r)
  if(followup){followup.status='submitted';followup.reportId=r.id}
  return clone(r) as T
 }
 const match=path.match(/^\/reports\/([^/]+)(\/status)?$/)
 if(match){
  const r=reports.find(r=>r.id===decodeURIComponent(match[1]));if(!r||!canRead(p,r))fail('报告不存在或无访问权限')
  const started=processing.get(r.id)
  if(started&&Date.now()-started>2500){r.status='pending';r.fields=clone(seedReports[0].fields);r.analysis='模拟处理完成：以下字段为预设示例，不是对您上传文件的真实识别结果。';processing.delete(r.id)}
  return clone(match[2]?{status:r.status}:r) as T
 }
 if(path==='/assistant/sessions')return clone(sessions[p.id]||[]) as T
 if(path==='/assistant/chat'){
  if(p.role!=='worker')fail('当前身份不能访问此会话')
  if(data.reportId&&!reports.some(r=>r.id===data.reportId&&canRead(p,r)))fail('无权访问所选报告')
  const list=sessions[p.id]||(sessions[p.id]=[])
  let s=list.find(s=>s.id===data.sessionId)
  if(data.sessionId&&!s)fail('会话不存在或无权限')
  if(!s){s={id:'s'+Date.now(),title:data.message.slice(0,18),messages:[]};list.unshift(s)}
  const cached=s.messages.find(m=>m.role==='assistant'&&m.requestId===data.requestId)
  if(cached)return clone({sessionId:s.id,message:cached}) as T
  s.messages.push({id:'u'+Date.now(),role:'user',content:data.message,status:'sent',requestId:data.requestId})
  const msg:Message={id:'a'+Date.now(),role:'assistant',status:'sent',requestId:data.requestId,reportId:data.reportId,content:'【演示回答】收到您的问题。'+(data.reportId?'本次仅关联您主动选择的报告 '+data.reportId+'。':'本次未向助手提供任何个人报告。')+`

正式接入后，后端将结合已授权的信息与知识库返回回答。您可以在健康档案中查看报告，在复查提醒中查看医生安排。

健康提示：AI内容仅供参考，不替代职业健康医师的诊断，也不决定上岗资格。`,sources:['演示服务说明（非医学文献）']}
  s.messages.push(msg);return clone({sessionId:s.id,message:msg}) as T
 }
 if(path==='/doctor/dashboard')return clone({workers:authorized.length,pendingReviews:reports.filter(r=>authorized.includes(r.workerId)&&r.status==='pending').length,followups:followups.filter(f=>authorized.includes(f.workerId)&&f.status==='pending').length,alerts:0,tasks:[{title:'待审核体检报告',count:reports.filter(r=>r.status==='pending'&&authorized.includes(r.workerId)).length,url:'/pages/doctor/reviews/index'},{title:'待处理随访任务',count:followups.filter(f=>f.status==='pending').length,url:'/pages/doctor/followups/index'}]}) as T
 if(path==='/doctor/reviews')return clone(reports.filter(r=>authorized.includes(r.workerId))) as T
 if(path==='/doctor/workers')return clone([worker,secondWorker]) as T
 const wm=path.match(/^\/doctor\/workers\/([^/]+)$/)
 if(wm){if(!authorized.includes(wm[1]))fail('该职工不在授权范围内');return clone(archive(wm[1])) as T}
 const rm=path.match(/^\/doctor\/reviews\/([^/]+)\/(approve|reject)$/)
 if(rm){
  const r=reports.find(r=>r.id===rm[1]);if(!r||!authorized.includes(r.workerId))fail('报告不在授权范围内')
  if(r.status!=='pending')fail('报告已处理或尚未进入待审核状态，请刷新列表')
  const decision=data as ReviewDecision
  if(!decision.note?.trim())fail('请填写审核备注')
  if(rm[2]==='approve'&&(!r.fields.length||r.fields.some(f=>f.needsConfirm&&!decision.confirmedFields?.includes(f.key))))fail('请核对并确认需要人工确认的字段')
  for(const c of decision.corrections||[]){if(!r.fields.some(f=>f.key===c.key)||!c.value?.trim())fail('无效修正字段')}
  for(const c of decision.corrections||[]){const field=r.fields.find(f=>f.key===c.key)!;if(!field.sourceValue)field.sourceValue=field.value;field.value=c.value}
  for(const f of r.fields){if(decision.confirmedFields?.includes(f.key))f.needsConfirm=false}
  r.status=rm[2]==='approve'?'approved':'returned';r.reviewNote=decision.note;return clone(r) as T
 }
 if(path==='/doctor/followups'&&method==='GET')return clone(followups.filter(f=>authorized.includes(f.workerId))) as T
 if(path==='/doctor/followups'&&method==='POST'){
  if(!authorized.includes(data.workerId))fail('职工不在授权范围内')
  if(!data.title?.trim()||!data.content?.trim()||!/^\d{4}-\d{2}-\d{2}$/.test(data.dueDate))fail('请完整填写随访信息')
  const f:Followup={...data,id:'f'+Date.now(),status:'pending',workerName:data.workerId===worker.id?worker.name:secondWorker.name};followups.unshift(f);return clone(f) as T
 }
 return fail('演示接口尚未实现：'+path)
}
