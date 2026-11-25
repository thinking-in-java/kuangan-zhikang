import type {ReportStatus} from '../types'
export const statuses:Record<ReportStatus,{text:string;tone:string}>={unknown:{text:'状态待确认',tone:'blue'},processing:{text:'处理中',tone:'blue'},pending:{text:'待医生审核',tone:'amber'},approved:{text:'已审核',tone:'green'},returned:{text:'已退回',tone:'red'},failed:{text:'处理失败',tone:'red'}}
export const today=()=>new Date().toLocaleDateString('sv-SE')
export const uid=()=> 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,c=>{const n=Math.floor(Math.random()*16);return (c==='x'?n:(n&3)|8).toString(16)})
export const fileSize=(v:number)=>v<1024*1024?(v/1024).toFixed(0)+' KB':(v/1024/1024).toFixed(1)+' MB'
export function errorText(e:unknown){return e instanceof Error?e.message:'操作未完成，请重试'}
export function toast(message:string){uni.showToast({title:message,icon:'none'})}
