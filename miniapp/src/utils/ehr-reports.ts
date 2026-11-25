import type {Report,ReportStatus,Archive,Profile} from '../types'
import {readOcrTask} from './ocr-task'
import {ApiError,ehrURL} from '../api/request'
const text=(v:unknown)=>typeof v==='string'?v:''
export function reportFromEhr(raw:any,p:Profile,archived=false):Report {
 if(!raw||typeof raw.id!=='string'||!Number.isInteger(raw.revision)||raw.revision<1)throw new ApiError('报告响应缺少有效的 id/revision，请核对后台数据格式')
 const rawStatus=text(raw.status)
 const states:Record<string,ReportStatus>={uploaded:'processing',ocr_complete:'processing',extracted:'processing',standardized:'processing',identity_checked:'processing',quality_checked:'processing',pending_review:'pending',failed:'failed',published:'approved',approved:'approved',archived:'approved',queued:'processing',processing:'processing',returned:'returned',cancelled:'failed',rejected:'returned'}
 const files=Array.isArray(raw.files)?raw.files.filter((f:any)=>typeof f?.id==='string').map((f:any)=>({id:f.id,name:text(f.name)||text(f.originalName)||text(f.fileName)||'报告原件',url:ehrURL('/files/'+encodeURIComponent(f.id)),type:(/pdf/i.test(text(f.contentType)||text(f.mimeType)||text(f.type)||text(f.name)||text(f.originalName)||text(f.fileName))?'pdf':'image') as 'pdf'|'image'})):[]
 const fields=Array.isArray(raw.fields)?raw.fields.filter((f:any)=>typeof f?.label==='string'&&f.value!==undefined).map((f:any,i:number)=>({key:text(f.key)||String(i),label:f.label,value:String(f.value),unit:text(f.unit),confidence:typeof f.confidence==='number'?f.confidence:NaN,needsConfirm:f.needsConfirm===true})):[]
 return {id:raw.id,revision:raw.revision,title:text(raw.title)||text(raw.reportType)||'体检报告',date:text(raw.examDate)||text(raw.examinationDate)||text(raw.reportDate),institution:text(raw.institution)||text(raw.hospital),type:text(raw.reportType),status:archived?'approved':states[rawStatus]||'unknown',rawStatus,jobId:text(raw.jobId)||text(raw.ocrTask?.jobId),ocrTask:readOcrTask(raw.ocrTask,text(raw.jobId)),candidate:!archived&&(raw.candidate===true||(raw.candidate===undefined&&!['published','archived'].includes(rawStatus))),workerId:p.employeeId||'',workerName:p.name,employeeNo:p.employeeNo,files,fields,conclusion:text(raw.conclusion),analysis:text(raw.analysis),raw}
}
export function archiveFromEhr(raw:any,p:Profile):Archive {
 if(!raw||!Array.isArray(raw.reports))throw new ApiError('档案接口未返回 reports 数组，请核对后台数据格式')
 return {profile:p,reports:raw.reports.map((v:any)=>reportFromEhr(v,p,true)),trends:[],advice:[],followups:[]}
}
export function reportLink(r:Report){return '/pages/worker/report-detail/index?id='+encodeURIComponent(r.id)+'&revision='+r.revision}
