import type {OcrTask,Report} from '../types'
const text=(v:unknown)=>typeof v==='string'?v:''
export function readOcrTask(value:unknown,jobId=''):OcrTask|undefined {
 if(!value||typeof value!=='object'||Array.isArray(value))return undefined
 const t=value as Record<string,any>
 const percent=typeof t.percent==='number'&&Number.isFinite(t.percent)&&t.percent>=0&&t.percent<=100?t.percent:null
 const attempts=Number.isInteger(t.attemptCount)&&t.attemptCount>=0?t.attemptCount:null
 return {jobId:text(t.jobId)||jobId,state:text(t.state),stage:text(t.stage),percent,attemptCount:attempts,errorCode:text(t.errorCode)||text(t.lastErrorCode)||text(t.error?.errorCode)||text(t.error?.code)||text(t.lastError?.errorCode)||text(t.lastError?.code),message:text(t.message)||text(t.errorMessage)||text(t.lastErrorMessage)||text(t.failureReason)||text(t.error?.message)||text(t.lastError?.message)||text(t.lastError)}
}
export const ocrStates:Record<string,string>={queued:'排队等待识别',running:'正在识别',retry_wait:'等待后台重试',succeeded:'识别完成，待核对',failed:'识别失败',cancelled:'识别已停止'}
export const ocrStages:Record<string,string>={queued:'等待工作服务领取',pdf_parse:'解析原件',ocr:'文字识别',field_extraction:'提取体检字段',item_matching:'匹配标准项目',validation:'校验并保存候选结果',finished:'识别结果已保存'}
export function shouldPollReport(report:Report):boolean {
 if(report.status==='approved')return false
 if(report.ocrTask)return ['queued','running','retry_wait'].includes(report.ocrTask.state)
 return report.status==='processing'
}
