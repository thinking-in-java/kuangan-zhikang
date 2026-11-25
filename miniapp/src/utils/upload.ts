import { config } from '../config'
import { getToken,invalidateSession } from '../api/request'
import type { SelectedFile } from '../types'
import { uid } from './format'
export function validateFiles(files:SelectedFile[]) {
 if(!files.length)throw new Error('请至少选择一个文件')
 if(files.length>config.maxFiles)throw new Error('最多上传 9 个文件，请分批提交')
 for(const f of files){if(f.size<=0||f.size>config.maxFileSize)throw new Error(f.name+' 需小于 20 MB 且不能为空');if(f.type==='pdf'&&!/\.pdf$/i.test(f.name))throw new Error('只支持 PDF 文件')}
}
export async function chooseImages(source:'camera'|'album'):Promise<SelectedFile[]>{
 // #ifdef MP-WEIXIN
 return new Promise((resolve,reject)=>uni.chooseMedia({count:9,mediaType:['image'],sourceType:[source],sizeType:['compressed'],success:res=>resolve(res.tempFiles.map((f,i)=>({id:uid(),path:f.tempFilePath,name:'体检报告_'+(i+1)+'.jpg',size:f.size,type:'image',progress:0}))),fail:reject}))
 // #endif
 // #ifndef MP-WEIXIN
 return new Promise((resolve,reject)=>uni.chooseImage({count:9,sourceType:[source],sizeType:['compressed'],success:res=>resolve((res.tempFiles as UniApp.ChooseImageSuccessCallbackResultFile[]).map((f,i)=>({id:uid(),path:f.path,name:'体检报告_'+(i+1)+'.jpg',size:f.size,type:'image',progress:0}))),fail:reject}))
 // #endif
}
export function choosePDF():Promise<SelectedFile[]> {
 return new Promise((resolve,reject)=>{
 // #ifdef MP-WEIXIN
 wx.chooseMessageFile({count:9,type:'file',extension:['pdf'],success:r=>resolve(r.tempFiles.map(f=>({id:uid(),path:f.path,name:f.name,size:f.size,type:'pdf',progress:0}))),fail:reject})
 // #endif
 // #ifdef H5
 uni.chooseFile({count:9,type:'all',extension:['.pdf'],success:(r:any)=>resolve(r.tempFiles.map((f:any)=>({id:uid(),path:f.path,name:f.name,size:f.size,type:'pdf',progress:0}))),fail:reject})
 // #endif
 })
}
export async function previewFile(url:string,type:'image'|'pdf',_all:string[]=[]){
 const allowed=config.baseURL+'/ehr/miniapp/files/'
 if(!url.startsWith(allowed))throw new Error('原件地址无效，请重新打开报告')
 const token=getToken();if(!token)throw new Error('请先登录')
 const res=await uni.downloadFile({url,header:{Authorization:'Bearer '+token}})
 if(res.statusCode===401){invalidateSession();throw new Error('登录已失效')}
 if(res.statusCode!==200)throw new Error('无法读取本人报告原件，请检查权限或稍后重试')
 if(type==='image'){uni.previewImage({urls:[res.tempFilePath]});return}
 // #ifdef MP-WEIXIN
 await uni.openDocument({filePath:res.tempFilePath,fileType:'pdf',showMenu:false})
 // #endif
 // #ifdef H5
 window.open(res.tempFilePath,'_blank','noopener,noreferrer')
 // #endif
}

export async function previewSelectedFile(file:SelectedFile){
 if(file.type==='image'){uni.previewImage({urls:[file.path]});return}
 // #ifdef MP-WEIXIN
 await uni.openDocument({filePath:file.path,fileType:'pdf',showMenu:false})
 // #endif
 // #ifdef H5
 window.open(file.path,'_blank','noopener,noreferrer')
 // #endif
}
