import {beforeEach,afterEach,it,expect,vi} from 'vitest'
import {createPinia,setActivePinia} from 'pinia'
const employee={employeeId:'employee-uuid',recordId:'record-uuid',name:'合成职工',employeeNo:'106114',gender:'女',birthDate:'1985-03-02',organization:'合成企业',team:'合成班组',position:'合成岗位',phoneMasked:'183****1314',rowVersion:1}
let storage:Map<string,any>,state:string,requests:any[],response409:boolean
beforeEach(()=>{vi.resetModules();setActivePinia(createPinia());storage=new Map();state='UNBOUND';requests=[];response409=false;vi.stubEnv('VITE_EHR_BASE_URL','http://ehr.test:18080');vi.stubEnv('VITE_ALLOW_HTTP_LOCAL','true');vi.stubGlobal('uni',{
 getStorageSync:(k:string)=>storage.get(k),setStorageSync:(k:string,v:any)=>storage.set(k,v),removeStorageSync:(k:string)=>storage.delete(k),reLaunch:vi.fn(),showToast:vi.fn(),
 request:(o:any)=>{requests.push(o);const path=o.url.split('/ehr/miniapp')[1];let data:any,status=200,code=0,msg='';
 if(path==='/me')data={miniUserId:'mini-user',bindingState:state,...(state==='BOUND'?{employee}:{})}
 else if(path==='/binding/confirm'){if(response409){status=409;code=409;msg='资料已变更'}else{state='BOUND';data={bindingState:'BOUND',employee}}}
 else if(path==='/session')data={loggedOut:true}
 else throw new Error('Unexpected fixture request')
 queueMicrotask(()=>o.success({statusCode:status,data:{code,data,msg}}))
 }
})})
afterEach(()=>{vi.unstubAllGlobals();vi.unstubAllEnvs()})
const login=()=>({miniUserId:'mini-user',bindingState:'UNBOUND' as const,token:'fixture-user-session',tokenType:'Bearer',expiresIn:7200,expiresAt:new Date(Date.now()+7200000).toISOString()})
it('keeps unbound users unbound until challenge confirmation; persists no challenge or employee info',async()=>{
 const {useUserStore}=await import('../src/stores/user');const store=useUserStore();await store.acceptLogin(login());expect(store.profile?.bound).toBe(false)
 store.setChallenge({bindingState:'PENDING_CONFIRMATION',confirmationToken:'fixture-confirmation-secret',expiresAt:new Date(Date.now()+600000).toISOString(),employee})
 expect(store.profile?.bound).toBe(false);expect(store.profile?.confirmationReady).toBe(true)
 expect(JSON.stringify([...storage.values()])).not.toContain('fixture-confirmation-secret');expect(JSON.stringify([...storage.values()])).not.toContain('合成职工')
 await store.confirmBinding();expect(store.profile?.bound).toBe(true);expect(store.challenge).toBeNull()
 const confirm=requests.find(o=>o.url.endsWith('/binding/confirm'));expect(confirm.data).toEqual({confirmationToken:'fixture-confirmation-secret',confirmed:true});expect(confirm.data.updates).toBeUndefined()
 expect(confirm.header.Authorization).toBe('Bearer fixture-user-session')
 await store.logout();expect(requests.at(-1).method).toBe('DELETE');expect(storage.size).toBe(0)
})
it('drops409 confirmation credentials and requires verification again',async()=>{
 const {useUserStore}=await import('../src/stores/user');const store=useUserStore();await store.acceptLogin(login());store.setChallenge({bindingState:'PENDING_CONFIRMATION',confirmationToken:'fixture-secret',expiresAt:new Date(Date.now()+600000).toISOString(),employee});response409=true
 await expect(store.confirmBinding()).rejects.toMatchObject({status:409});expect(store.challenge).toBeNull();expect(store.profile?.bound).toBe(false)
})
it('restores a pending session without inventing the lost confirmation token',async()=>{
 storage.set('kazk-ehr-session-v1',{token:'fixture-user-session',miniUserId:'mini-user',expires:Date.now()+7200000,backend:'http://ehr.test:18080'});state='PENDING_CONFIRMATION'
 const {useUserStore}=await import('../src/stores/user');const store=useUserStore();await store.restore();expect(store.profile?.confirmationReady).toBe(false)
 const {landing}=await import('../src/utils/navigation');expect(landing(store.profile!)).toBe('/pages/login/bind')
})

it('does not persist a partially accepted login when bound employee details fail to load',async()=>{
 const {useUserStore}=await import('../src/stores/user');const store=useUserStore()
 vi.stubGlobal('uni',{...(globalThis as any).uni,request:(o:any)=>queueMicrotask(()=>o.success({statusCode:503,data:{code:503,msg:'档案服务暂不可用'}}))})
 await expect(store.acceptLogin({...login(),bindingState:'BOUND'})).rejects.toThrow('档案服务暂不可用')
 const {getToken}=await import('../src/api/request');expect(getToken()).toBe('');expect(store.profile).toBeNull();expect(storage.size).toBe(0)
})
it('fetches the real bound employee before committing a bound login without employee data',async()=>{
 state='BOUND';const {useUserStore}=await import('../src/stores/user');const store=useUserStore()
 await store.acceptLogin({...login(),bindingState:'BOUND'});expect(store.profile?.employeeId).toBe(employee.employeeId)
 expect(requests[0].url).toContain('/me');expect(storage.size).toBe(1)
})
it('rejects malformed expiry and missing binding state without leaving an authenticated session',async()=>{
 const {useUserStore}=await import('../src/stores/user');const store=useUserStore()
 await expect(store.acceptLogin({...login(),expiresAt:'invalid'})).rejects.toThrow('过期时间')
 await expect(store.acceptLogin({...login(),bindingState:'INVALID' as any})).rejects.toThrow('绑定状态')
 const {getToken}=await import('../src/api/request');expect(getToken()).toBe('');expect(storage.size).toBe(0)
})
it('discards a corrupted cached expiry without making an authenticated request',async()=>{
 storage.set('kazk-ehr-session-v1',{token:'fixture-user-session',miniUserId:'mini-user',backend:'http://ehr.test:18080'})
 const {useUserStore}=await import('../src/stores/user');const store=useUserStore();await store.restore()
 expect(store.profile).toBeNull();expect(requests).toHaveLength(0);expect(storage.size).toBe(0)
})

it('routes existing unbound WeChat users to verification instead of treating registration as binding',async()=>{
 const {useUserStore}=await import('../src/stores/user');const {landing}=await import('../src/utils/navigation');const store=useUserStore()
 await store.acceptLogin({...login(),isNewUser:false,canEnterHome:false,needIdentityVerification:true,nextAction:'VERIFY_EMPLOYEE'})
 expect(landing(store.profile!)).toBe('/pages/login/bind');expect(store.profile?.canEnterHome).toBe(false)
})
it('routes bound users directly home only when the backend permits it',async()=>{
 const {useUserStore}=await import('../src/stores/user');const {landing}=await import('../src/utils/navigation');const store=useUserStore()
 await store.acceptLogin({...login(),bindingState:'BOUND',employee,canEnterHome:true,needIdentityVerification:false,nextAction:'ENTER_HOME',isNewUser:false})
 expect(landing(store.profile!)).toBe('/pages/worker/home/index')
 await expect(store.acceptLogin({...login(),bindingState:'BOUND',employee,canEnterHome:false,nextAction:'VERIFY_EMPLOYEE'})).rejects.toThrow('尚未允许')
 expect(store.profile).toBeNull()
})
it('keeps correction-review users outside health pages and restores their review state',async()=>{
 const {useUserStore}=await import('../src/stores/user');const {landing,requireRole}=await import('../src/utils/navigation');const store=useUserStore()
 await store.acceptLogin({...login(),bindingState:'CORRECTION_PENDING',canEnterHome:false,nextAction:'WAIT_FOR_REVIEW'})
 expect(landing(store.profile!)).toBe('/pages/login/pending');expect(await requireRole('worker')).toBe(false)
 state='CORRECTION_PENDING';setActivePinia(createPinia());const restored=useUserStore();await restored.restore()
 expect(landing(restored.profile!)).toBe('/pages/login/pending')
})
it('rejects a contradictory home permission for an unbound user',async()=>{
 const {useUserStore}=await import('../src/stores/user');const store=useUserStore()
 await expect(store.acceptLogin({...login(),canEnterHome:true,nextAction:'ENTER_HOME'})).rejects.toThrow('不一致')
 expect(store.profile).toBeNull();expect(storage.size).toBe(0)
})
