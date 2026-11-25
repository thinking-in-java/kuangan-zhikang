import type {ReportTab} from '../types/report-list'
import {parseReportList} from '../utils/report-list'
import {ehrRequest,ApiError} from './request'
import {healthApi} from './health'
import {useUserStore} from '../stores/user'
import {reportFromEhr} from '../utils/ehr-reports'
import type {Report,ReportStatus} from '../types'
async function detail(id:string,revision?:number):Promise<Report>{
 if(!id||!Number.isInteger(revision)||revision!<1)throw new ApiError('缺少报告版本，请从报告列表或上传回执重新打开')
 const p=useUserStore().profile;if(!p?.bound)throw new ApiError('请先完成职工绑定',403)
 return reportFromEhr(await ehrRequest('/reports/'+encodeURIComponent(id)+'/'+revision),p)
}
export const reportsApi={paginatedList:async(tab:ReportTab='all',page=1,pageSize=10)=>{
 if(!['all','approved','pending'].includes(tab)||!Number.isInteger(page)||page<1||page>100000||!Number.isInteger(pageSize)||pageSize<1||pageSize>50)throw new ApiError('报告列表分页参数不正确',422)
 const p=useUserStore().profile;if(!p?.bound||p.canEnterHome===false)throw new ApiError('请先完成职工绑定',403)
 const path='/reports'+(tab==='all'?'':'/'+tab)+'?page='+page+'&pageSize='+pageSize
 return parseReportList(await ehrRequest(path),tab,page,pageSize)
},list:async()=>(await healthApi.archive()).reports,detail,status:async(id:string,revision?:number):Promise<{status:ReportStatus}>=>({status:(await detail(id,revision)).status})}
