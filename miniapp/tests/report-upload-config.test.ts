import {beforeEach,afterEach,it,expect,vi} from 'vitest'
beforeEach(()=>{vi.resetModules();vi.stubEnv('VITE_EHR_BASE_URL','http://ehr.test:18080');vi.stubEnv('VITE_ALLOW_HTTP_LOCAL','true')})
afterEach(()=>vi.unstubAllEnvs())
it('uses the miniapp route and the actual user session, never an environment upload token',async()=>{
 const req=await import('../src/api/request');const cfg=await import('../src/utils/report-upload-config')
 expect(cfg.uploadURL()).toBe('http://ehr.test:18080/ehr/miniapp/reports')
 expect(()=>cfg.uploadHeaders('uuid')).toThrow('登录')
 req.setToken('fixture-user-session');expect(cfg.uploadHeaders('uuid')).toEqual({'Idempotency-Key':'uuid',Authorization:'Bearer fixture-user-session'})
 expect(cfg.displayResponse('fixture-user-session')).not.toContain('fixture-user-session')
})
