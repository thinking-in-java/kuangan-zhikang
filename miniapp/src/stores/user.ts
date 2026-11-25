import {config} from '../config'
import {defineStore} from 'pinia'
import {ref} from 'vue'
import {authApi} from '../api/auth'
import {getToken,setToken,setUnauthorizedHandler,ApiError} from '../api/request'
import type {Profile} from '../types'
import type {MiniLogin,MiniState,BindingChallenge} from '../types/ehr'
import {employeeProfile} from '../utils/ehr-profile'
const SESSION='kazk-ehr-session-v1'
export const useUserStore=defineStore('user',()=>{
 const profile=ref<Profile|null>(null),challenge=ref<BindingChallenge|null>(null),restoreError=ref('')
 let expires=0,miniUserId='',restoreTask:Promise<void>|undefined,generation=0
 function clear(){generation++;profile.value=null;challenge.value=null;expires=0;miniUserId='';setToken('');uni.removeStorageSync(SESSION);uni.removeStorageSync('kazk-session')}
 function expire(){clear();uni.reLaunch({url:'/pages/login/index'})}
 setUnauthorizedHandler(expire)
 function validChallenge(){return !!challenge.value&&Date.parse(challenge.value.expiresAt)>Date.now()}
 function apply(state:MiniState){
  const p=employeeProfile(state,state.miniUserId||miniUserId,validChallenge()?challenge.value?.employee:undefined)
  if(state.bindingState==='PENDING_CONFIRMATION')p.confirmationReady=validChallenge()
  if(state.bindingState==='BOUND')challenge.value=null
  profile.value=p
 }
 async function refresh(){const state=await authApi.me();apply(state);return state}
 async function acceptLogin(result:MiniLogin){
  clear();restoreError.value=''
  const current=generation
  try{
   if(typeof result.token!=='string'||!result.token.trim()||!result.miniUserId||result.tokenType!=='Bearer'||!Number.isFinite(result.expiresIn)||result.expiresIn<=0)throw new ApiError('登录响应缺少有效会话信息')
   const at=Date.parse(result.expiresAt)
   if(!Number.isFinite(at))throw new ApiError('后台未返回有效的会话过期时间')
   expires=Math.min(Date.now()+result.expiresIn*1000,at)
   if(expires<=Date.now())throw new ApiError('后台返回的登录会话已过期')
   miniUserId=result.miniUserId;setToken(result.token)
   const state=result.bindingState==='BOUND'&&!result.employee?await authApi.me():result
   if(current!==generation)throw new ApiError('登录状态已变化，请重新登录')
   apply(state)
   uni.setStorageSync(SESSION,{token:result.token,expires,miniUserId,backend:config.baseURL})
  }catch(e){if(current===generation)clear();throw e}
 }
 function setChallenge(value:BindingChallenge){
  if(!value.confirmationToken||!value.employee?.employeeId||!Number.isFinite(Date.parse(value.expiresAt))||Date.parse(value.expiresAt)<=Date.now())throw new ApiError('身份校验凭据不完整或已过期，请重新验证')
  challenge.value=value;apply({bindingState:'PENDING_CONFIRMATION',employee:value.employee})
 }
 function clearChallenge(){challenge.value=null;if(profile.value?.bindingState==='PENDING_CONFIRMATION')profile.value.confirmationReady=false}
 async function confirmBinding(){
  if(!validChallenge()){clearChallenge();throw new ApiError('确认凭据已过期，请重新验证身份',409)}
  const token=challenge.value!.confirmationToken
  try{const result=await authApi.confirm(token);if(result.bindingState!=='BOUND')throw new ApiError('后台尚未完成绑定，请重试');apply(result);return result}catch(e){if(e instanceof ApiError&&[409,422,404].includes(e.status))clearChallenge();throw e}
 }
 async function restore(){
  if(profile.value&&expires>Date.now())return
  if(restoreTask)return restoreTask
  restoreTask=(async()=>{restoreError.value='';const saved=uni.getStorageSync(SESSION)
   uni.removeStorageSync('kazk-session')
   if(typeof saved?.token!=='string'||!saved.token.trim()||!Number.isFinite(saved.expires)||saved.expires<=Date.now()||typeof saved.miniUserId!=='string'||!saved.miniUserId||saved.backend!==config.baseURL){clear();return}
   expires=saved.expires;miniUserId=saved.miniUserId;setToken(saved.token);const current=generation
   try{const state=await authApi.me();if(current===generation)apply(state)}catch(e){restoreError.value=e instanceof Error?e.message:'登录状态查询失败';if(e instanceof ApiError&&e.status===401)clear()}
  })()
  try{await restoreTask}finally{restoreTask=undefined}
 }
 async function logout(){
  let message=''
  try{if(getToken())await authApi.logout()}catch{message='已退出本机，后台会话注销未完成，请稍后重试或等待会话过期'}finally{clear();uni.reLaunch({url:'/pages/login/index'});if(message)uni.showToast({title:message,icon:'none'})}
 }
 return {profile,challenge,restoreError,restore,acceptLogin,refresh,setChallenge,clearChallenge,validChallenge,confirmBinding,logout,clear}
})
