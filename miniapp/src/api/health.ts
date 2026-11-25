import {ehrRequest,ApiError} from './request'
import {useUserStore} from '../stores/user'
import {archiveFromEhr} from '../utils/ehr-reports'
import type {Archive,Advice,Followup,Notice,Article} from '../types'
const unavailable=<T>():Promise<T>=>Promise.reject(new ApiError('后台尚未提供此服务的小程序接口',501))
export const healthApi={
 archive:async():Promise<Archive>=>{const p=useUserStore().profile;if(!p?.bound)throw new ApiError('请先完成职工绑定',403);return archiveFromEhr(await ehrRequest('/health-record'),p)},
 advice:()=>unavailable<Advice[]>(),followups:()=>unavailable<Followup[]>(),notifications:()=>unavailable<Notice[]>(),articles:()=>unavailable<Article[]>(),
}
