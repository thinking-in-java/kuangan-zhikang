import type {Profile} from '../types'
import type {MiniState,Employee} from '../types/ehr'
import {ApiError} from '../api/request'
export function employeeProfile(state:MiniState,userId:string,preview?:Employee):Profile {
 if(!['UNBOUND','PENDING_CONFIRMATION','CORRECTION_PENDING','BOUND'].includes(state.bindingState))throw new ApiError('后台返回了无法识别的绑定状态')
 if(state.canEnterHome!==undefined&&typeof state.canEnterHome!=='boolean')throw new ApiError('后台返回的首页访问状态不正确')
 if(state.canEnterHome===true&&state.bindingState!=='BOUND')throw new ApiError('后台登录状态不一致，请重新登录')
 if(state.bindingState==='BOUND'&&(state.canEnterHome===false||(state.nextAction&&state.nextAction!=='ENTER_HOME')))throw new ApiError('后台尚未允许进入首页，请刷新登录状态')
 const e=state.employee||preview
 if(state.bindingState==='BOUND'&&(!e?.employeeId||!e.recordId))throw new ApiError('已绑定账号缺少后台职工资料，请联系管理员')
 return {id:userId,miniUserId:userId,role:'worker',canEnterHome:state.bindingState==='BOUND'&&state.canEnterHome!==false,nextAction:state.nextAction,name:e?.name||'',employeeNo:e?.employeeNo||'',bound:state.bindingState==='BOUND',identityConfirmed:state.bindingState==='BOUND',bindingState:state.bindingState,confirmationReady:false,employeeId:e?.employeeId,recordId:e?.recordId,mine:e?.organization||'',organization:e?.organization||'',department:'',team:e?.team||'',job:e?.position||'',years:null,age:null,hazards:[],gender:e?.gender||'',birthDate:e?.birthDate||'',phoneMasked:e?.phoneMasked||'',profileVersion:e?.rowVersion===undefined?undefined:String(e.rowVersion)}
}
