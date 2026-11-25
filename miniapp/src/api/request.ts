import {config} from '../config'
export class ApiError extends Error {constructor(message:string,public status=0,public errorCode=''){super(message)}}
let token='',onUnauthorized=()=>{}
const tokenListeners=new Set<()=>void>()
export function onTokenChange(fn:()=>void){tokenListeners.add(fn);return ()=>tokenListeners.delete(fn)}
export function setToken(value:string){if(token!==value){token=value;tokenListeners.forEach(fn=>fn())}}
export function getToken(){return token}
export function setUnauthorizedHandler(fn:()=>void){onUnauthorized=fn}
export function invalidateSession(){setToken('');onUnauthorized()}
export function ehrURL(path:string){
 if(!/^https:\/\/[^\s/?#]+(?::\d+)?$/.test(config.baseURL)&&!(config.allowHttpLocal&&/^http:\/\/[^\s/?#]+(?::\d+)?$/.test(config.baseURL)))throw new ApiError('后端地址未配置，或未启用本地 HTTP 联调')
 if(!/^\/[a-zA-Z0-9/_?=&.%:-]*$/.test(path)||path.startsWith('//'))throw new ApiError('接口路径不正确')
 return config.baseURL+'/ehr/miniapp'+path
}
export function parseEnvelope<T>(status:number,body:any):T{
 if(status<200||status>=300||!body||body.code!==0){
  const fallback=status===503?'微信登录服务尚未配置或暂不可用':status===429?'操作过于频繁，请稍后再试':status===409?'身份资料或绑定状态已变化，请重新验证':status===401?'登录已失效，请重新登录':'请求未完成，请稍后重试'
  throw new ApiError(typeof body?.msg==='string'?body.msg:fallback,status,typeof body?.errorCode==='string'?body.errorCode:'')
 }
 return body.data as T
}
export async function ehrRequest<T>(path:string,method:'GET'|'POST'|'DELETE'='GET',data?:unknown,authenticated=true):Promise<T>{
 const url=ehrURL(path),credential=token
 if(authenticated&&!credential)throw new ApiError('请先使用微信登录',401)
 return new Promise((resolve,reject)=>uni.request({url,method,data:data as any,timeout:config.timeout,header:{'Content-Type':'application/json',...(authenticated?{Authorization:'Bearer '+credential}:{})},
  success(res){if(res.statusCode===401&&authenticated&&credential===getToken())invalidateSession();try{resolve(parseEnvelope<T>(res.statusCode,res.data))}catch(e){reject(e)}},
  fail(){reject(new ApiError('无法连接后台，请检查网络、后端地址及微信合法域名设置'))}
 }))
}
// Legacy APIs have no counterpart in the supplied miniapp contract. Never fall back to mock data.
export async function request<T>(_path:string,_method:'GET'|'POST'='GET',_data?:unknown):Promise<T>{throw new ApiError('此功能的真实小程序接口尚未提供，请稍后使用',501)}
