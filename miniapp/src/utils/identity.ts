import type {Profile,IdentityUpdates} from '../types'
export interface IdentityDraft {name:string;employeeNo:string;gender:string;birthDate:string;organization:string;team:string;job:string;phone:string}
export function identityDraft(p:Profile):IdentityDraft {
 return {name:p.name||'',employeeNo:p.employeeNo||'',gender:p.gender||'',birthDate:p.birthDate||'',organization:p.organization||p.mine||'',team:p.team||'',job:p.job||'',phone:''}
}
export function identityUpdates(p:Profile,draft:IdentityDraft):IdentityUpdates {
 const original=identityDraft(p),updates:IdentityUpdates={}
 if(!draft.name.trim())throw new Error('请填写职工姓名')
 if(!draft.employeeNo.trim())throw new Error('请填写职工工号')
 if(draft.birthDate){
  const d=new Date(draft.birthDate+'T00:00:00')
  const [year,month,day]=draft.birthDate.split('-').map(Number)
  if(!/^\d{4}-\d{2}-\d{2}$/.test(draft.birthDate)||Number.isNaN(d.getTime())||d.getFullYear()!==year||d.getMonth()+1!==month||d.getDate()!==day||d>new Date())throw new Error('请填写有效且不晚于今天的出生日期')
 }
 if(draft.phone.trim()&&!/^1\d{10}$/.test(draft.phone.trim()))throw new Error('请填写11位手机号码')
 for(const key of ['name','employeeNo','gender','birthDate','organization','team','job'] as const){
  const value=draft[key].trim()
  if(value.length>(key==='employeeNo'?32:80))throw new Error('填写的信息过长，请检查')
  if(value!==original[key])updates[key]=value
 }
 if(draft.phone.trim())updates.phone=draft.phone.trim()
 return updates
}
