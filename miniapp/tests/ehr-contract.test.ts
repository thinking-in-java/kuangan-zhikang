import {beforeEach,afterEach,it,expect,vi} from 'vitest'
beforeEach(()=>{vi.resetModules();vi.stubEnv('VITE_EHR_BASE_URL','http://ehr.test:18080');vi.stubEnv('VITE_ALLOW_HTTP_LOCAL','true')})
afterEach(()=>{vi.unstubAllEnvs();vi.unstubAllGlobals()})
it('reads dynamic consent config and sends only the documented login fields',async()=>{
 const calls:any[]=[];vi.stubGlobal('uni',{request:(o:any)=>{calls.push(o);queueMicrotask(()=>o.success({statusCode:200,data:{code:0,data:o.url.endsWith('/config')?{wechatConfigured:false,consentVersion:'server-version'}:{miniUserId:'mini',bindingState:'UNBOUND',token:'fixture'}}}))}})
 const {authApi}=await import('../src/api/auth');expect((await authApi.config()).wechatConfigured).toBe(false)
 await authApi.login('one-use-fixture-code','server-version');expect(calls[1].data).toEqual({code:'one-use-fixture-code',privacyAccepted:true,consentVersion:'server-version'});expect(calls[1].header.Authorization).toBeUndefined()
})
it('handles backend msg errors and has no mock fallback',async()=>{
 const {parseEnvelope,request}=await import('../src/api/request');expect(()=>parseEnvelope(503,{code:503,msg:'微信参数未配置'})).toThrow('微信参数未配置');expect(()=>parseEnvelope(200,{code:422,msg:'个人验证码无效'})).toThrow('个人验证码无效');await expect(request('/assistant/chat')).rejects.toMatchObject({status:501})
})
it('maps only actual report data and preserves candidate/revision and unknown statuses',async()=>{
 const {employeeProfile}=await import('../src/utils/ehr-profile');const p=employeeProfile({bindingState:'UNBOUND'},'mini')
 const {reportFromEhr,archiveFromEhr}=await import('../src/utils/ehr-reports');const report=reportFromEhr({id:'report',revision:3,status:'uploaded',candidate:true,files:[{id:'file',name:'fixture.pdf'}]},p)
 expect(report.status).toBe('processing');expect(report.revision).toBe(3);expect(report.candidate).toBe(true);expect(report.conclusion).toBe('');expect(report.fields).toEqual([]);expect(report.files[0].url).toContain('/ehr/miniapp/files/file')
 expect(reportFromEhr({id:'report',revision:3,status:'new-state'},p).status).toBe('unknown');expect(()=>archiveFromEhr({},p)).toThrow('reports');expect(archiveFromEhr({reports:[]},p).trends).toEqual([])
})

it('ignores an old session 401 after a new session has been established',async()=>{
 const pending:any[]=[];vi.stubGlobal('uni',{request:(o:any)=>pending.push(o)})
 const {ehrRequest,setToken,getToken,setUnauthorizedHandler}=await import('../src/api/request');const expired=vi.fn();setUnauthorizedHandler(expired)
 setToken('old-fixture');const oldRequest=ehrRequest('/me');setToken('new-fixture')
 pending[0].success({statusCode:401,data:{code:401,msg:'旧会话过期'}})
 await expect(oldRequest).rejects.toMatchObject({status:401});expect(getToken()).toBe('new-fixture');expect(expired).not.toHaveBeenCalled()
 const current=ehrRequest('/me');pending[1].success({statusCode:401,data:{code:401,msg:'当前会话过期'}})
 await expect(current).rejects.toMatchObject({status:401});expect(getToken()).toBe('');expect(expired).toHaveBeenCalledOnce()
})

it('uses personal-agent endpoints with user auth and strips identity/model/history fields',async()=>{
 const calls:any[]=[];vi.stubGlobal('uni',{request:(o:any)=>{calls.push(o);queueMicrotask(()=>o.success({statusCode:o.url.endsWith('/chat')?202:200,data:{code:0,data:{conversationId:'c',messageId:'m',status:'queued'}}}))}})
 const {setToken}=await import('../src/api/request');setToken('fixture-agent-token');const {assistantApi}=await import('../src/api/assistant')
 await assistantApi.context();await assistantApi.create();await assistantApi.conversations(2,20);await assistantApi.history('c',2,10)
 await assistantApi.chat({conversationId:'c',requestId:'fixture-request-1',message:'问题',employeeId:'forbidden',model:'forbidden',messages:[],context:{reportId:'r',revision:2,employeeId:'forbidden'}} as any)
 await assistantApi.message('m');await assistantApi.remove('c')
 expect(calls.every(c=>c.header.Authorization==='Bearer fixture-agent-token')).toBe(true)
 expect(calls.map(c=>c.url.split('/ehr/miniapp')[1])).toEqual(['/agent/context','/agent/conversations','/agent/conversations?page=2&pageSize=20','/agent/conversations/c?page=2&pageSize=10','/agent/chat','/agent/messages/m','/agent/conversations/c'])
 expect(calls[4].data).toEqual({conversationId:'c',requestId:'fixture-request-1',message:'问题',context:{reportId:'r',revision:2}});expect(calls[6].method).toBe('DELETE')
})
