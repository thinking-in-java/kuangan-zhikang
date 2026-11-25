import {uploadURL,uploadHeaders,displayResponse} from '../utils/report-upload-config'
import {parseEnvelope,invalidateSession} from './request'
import {readOcrTask} from '../utils/ocr-task'
import type {SelectedFile,OcrTask} from '../types'
export interface UploadReceipt {httpStatus:number;reportId:string;revision:number;jobId:string;status:string;raw:string;ocrTask?:OcrTask}
export class UploadFailure extends Error {constructor(message:string,public httpStatus:number,public raw:string){super(message)}}
export function uploadReport(file:SelectedFile,key:string,onProgress:(n:number)=>void,onTask?:(task:UniApp.UploadTask)=>void):Promise<UploadReceipt>{
 return new Promise((resolve,reject)=>{
 const task=uni.uploadFile({url:uploadURL(),filePath:file.path,name:'file',header:uploadHeaders(key),timeout:90000,
 success(res){const raw=displayResponse(res.data);let body:any;try{body=JSON.parse(res.data)}catch{}
  if(res.statusCode===401)invalidateSession()
  try{const data=parseEnvelope<any>(res.statusCode,body);if(typeof data?.id!=='string'||!Number.isInteger(data.revision)||data.revision<1)throw new Error('后台已响应，但缺少有效报告编号或版本，请核对后台结果后重试');resolve({httpStatus:res.statusCode,reportId:data.id,revision:data.revision,jobId:typeof data.jobId==='string'?data.jobId:typeof data.ocrTask?.jobId==='string'?data.ocrTask.jobId:'',ocrTask:readOcrTask(data.ocrTask,typeof data.jobId==='string'?data.jobId:''),status:typeof data.status==='string'?data.status:'',raw})}catch(e){reject(new UploadFailure(e instanceof Error?e.message:'上传失败',res.statusCode,raw))}
 },fail(e){reject(new UploadFailure(displayResponse(e.errMsg||'上传中断，请重试'),0,''))}})
 task.onProgressUpdate(e=>onProgress(e.progress));onTask?.(task)
 })
}
