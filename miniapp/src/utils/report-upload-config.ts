import {config} from '../config'
import {getToken,ehrURL,ApiError} from '../api/request'
export const reportUploadConfig={get baseURL(){return config.baseURL}}
export function uploadURL(_base?:string){return ehrURL('/reports')}
export function responseIds(body:any):{taskId:string;reportId:string}{const d=body?.data??body;return {taskId:'',reportId:typeof d?.id==='string'?d.id:''}}
export function displayResponse(raw:string){const token=getToken();let text=raw||'（空响应）';try{text=JSON.stringify(JSON.parse(text),null,2)}catch{}return token?text.split(token).join('[认证信息已隐藏]'):text}
export function uploadHeaders(key:string){if(!getToken())throw new ApiError('请先使用微信登录',401);return {'Idempotency-Key':key,Authorization:'Bearer '+getToken()}}
