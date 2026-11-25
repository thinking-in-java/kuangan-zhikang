import {ApiError} from '../api/request'
import type {ReportListPage,ReportListItem,ReportTab} from '../types/report-list'
export function parseReportList(raw:any,tab:ReportTab,page:number,pageSize:number):ReportListPage{
 const count=(v:any)=>Number.isInteger(v)&&v>=0
 if(!raw||raw.status!==tab||raw.page!==page||raw.pageSize!==pageSize||!count(raw.total)||typeof raw.hasMore!=='boolean'||!Array.isArray(raw.items)||!raw.counts||!['all','approved','pending'].every(k=>count(raw.counts[k])))throw new ApiError('报告列表响应格式不完整，请联系后台核对')
 if(raw.counts.all!==raw.counts.approved+raw.counts.pending||raw.total!==raw.counts[tab])throw new ApiError('报告列表数量不一致，请刷新重试')
 for(const r of raw.items){if(!r||typeof r.reportId!=='string'||!r.reportId||!Number.isInteger(r.revision)||r.revision<1||typeof r.statusText!=='string'||typeof r.status!=='string'||typeof r.approved!=='boolean'||typeof r.published!=='boolean'||typeof r.candidate!=='boolean')throw new ApiError('报告列表条目缺少编号、版本或状态')}
 return raw as ReportListPage
}
export function listReportLink(r:ReportListItem){return '/pages/worker/report-detail/index?id='+encodeURIComponent(r.reportId)+'&revision='+r.revision}
// API timestamps without a timezone are UTC, not the device's local time.
export function beijingReportTime(value?:string|null){
 if(!value)return '未提供'
 let normalized=value.trim().replace(' ','T').replace(/(\.\d{3})\d+/,'$1')
 if(!/(Z|[+-]\d{2}:?\d{2})$/i.test(normalized))normalized+='Z'
 const timestamp=Date.parse(normalized)
 if(!Number.isFinite(timestamp))return '时间待核对'
 const date=new Date(timestamp+8*60*60*1000),pad=(v:number)=>String(v).padStart(2,'0')
 return date.getUTCFullYear()+'-'+pad(date.getUTCMonth()+1)+'-'+pad(date.getUTCDate())+' '+pad(date.getUTCHours())+':'+pad(date.getUTCMinutes())
}
