import {ehrRequest} from './request'
import type {LoginConfig,MiniLogin,MiniState,BindingChallenge} from '../types/ehr'
export const authApi={
 config:()=>ehrRequest<LoginConfig>('/config','GET',undefined,false),
 login:(code:string,consentVersion:string)=>ehrRequest<MiniLogin>('/wechat/login','POST',{code,privacyAccepted:true,consentVersion},false),
 me:()=>ehrRequest<MiniState>('/me'),
 verify:(data:{name:string;employeeNo:string;verificationCode:string})=>ehrRequest<BindingChallenge>('/binding/verify','POST',data),
 confirm:(confirmationToken:string)=>ehrRequest<MiniState>('/binding/confirm','POST',{confirmationToken,confirmed:true}),
 logout:()=>ehrRequest<{loggedOut:boolean}>('/session','DELETE'),
}
