import {beforeEach,afterEach,it,expect,vi} from 'vitest'
beforeEach(()=>{vi.resetModules();vi.stubEnv('VITE_EHR_BASE_URL','http://ehr.test:18080');vi.stubEnv('VITE_ALLOW_HTTP_LOCAL','true')})
afterEach(()=>{vi.unstubAllGlobals();vi.unstubAllEnvs()})
const file={id:'fixture',name:'fixture.pdf',path:'tmp/fixture.pdf',size:1000,type:'pdf' as const,progress:0}
it('accepts new HTTP200/code0 report revision response and sends no recordId',async()=>{
 const req=await import('../src/api/request');req.setToken('fixture-user-session')
 vi.stubGlobal('uni',{uploadFile:(o:any)=>{expect(o.url).toBe('http://ehr.test:18080/ehr/miniapp/reports');expect(o.header.Authorization).toBe('Bearer fixture-user-session');expect(o.formData).toBeUndefined();expect(o.name).toBe('file');queueMicrotask(()=>o.success({statusCode:200,data:JSON.stringify({code:0,data:{id:'report-uuid',revision:2,status:'uploaded',jobId:'ocr-job',ocrTask:{jobId:'ocr-job',state:'queued',stage:'queued',percent:0,attemptCount:0},files:[]}})}));return{onProgressUpdate:(fn:any)=>fn({progress:100})}}})
 const {uploadReport}=await import('../src/api/report-upload');const p=vi.fn();expect(await uploadReport(file,'uuid',p)).toMatchObject({httpStatus:200,reportId:'report-uuid',revision:2,status:'uploaded',jobId:'ocr-job',ocrTask:{state:'queued',percent:0}});expect(p).toHaveBeenCalledWith(100)
})
it('shows backend msg and invalidates a revoked session on401',async()=>{
 const req=await import('../src/api/request');req.setToken('fixture-user-session');const invalid=vi.fn();req.setUnauthorizedHandler(invalid)
 vi.stubGlobal('uni',{uploadFile:(o:any)=>{queueMicrotask(()=>o.success({statusCode:401,data:'{"code":401,"msg":"会话已撤销"}'}));return{onProgressUpdate:()=>{}}}})
 const {uploadReport}=await import('../src/api/report-upload');await expect(uploadReport(file,'uuid',()=>{})).rejects.toMatchObject({httpStatus:401,message:'会话已撤销'});expect(invalid).toHaveBeenCalledOnce()
})
