import {beforeEach,afterEach,it,expect,vi} from 'vitest'
import {createPinia,setActivePinia} from 'pinia'
import {parseReportList,beijingReportTime,listReportLink} from '../src/utils/report-list'
import {useReportList} from '../src/utils/useReportList'
import type {ReportListPage,ReportTab} from '../src/types/report-list'
const item=(id='fixture',revision=1)=>({reportId:id,revision,title:'合成报告',status:'approved',statusText:'已审核·待归档',approved:true,published:false,candidate:true})
const result=(tab:ReportTab='all',page=1,items=[item()],more=false):ReportListPage=>({status:tab,page,pageSize:10,total:tab==='pending'?0:1,hasMore:more,counts:{all:1,approved:1,pending:0},items})
beforeEach(()=>{setActivePinia(createPinia());vi.stubEnv('VITE_EHR_BASE_URL','http://ehr.test:18080');vi.stubEnv('VITE_ALLOW_HTTP_LOCAL','true')})
afterEach(()=>{vi.unstubAllGlobals();vi.unstubAllEnvs()})
it('keeps backend review wording, publication flag and empty results',()=>{
 const data=parseReportList(result(),'all',1,10);expect(data.items[0].statusText).toBe('已审核·待归档');expect(data.items[0].published).toBe(false)
 expect(parseReportList(result('pending',1,[]),'pending',1,10).items).toEqual([])
 expect(()=>parseReportList({...result(),counts:{all:5,approved:1,pending:0}},'all',1,10)).toThrow('数量')
 expect(()=>parseReportList({...result(),items:[{...item(),revision:0}]},'all',1,10)).toThrow('版本')
})
it('converts UTC upload times to Beijing without modifying examination dates or trusting detail URLs',()=>{
 expect(beijingReportTime('2026-11-15 23:23:47.717000')).toBe('2026-11-16 07:23')
 expect(beijingReportTime('2026-11-15T23:23:47Z')).toBe('2026-11-16 07:23')
 expect(beijingReportTime(null)).toBe('未提供');expect(beijingReportTime('invalid')).toBe('时间待核对')
 expect(listReportLink({...item('report/a',3),detailPath:'https://other.example'})).toBe('/pages/worker/report-detail/index?id=report%2Fa&revision=3')
})
it('uses the three authenticated endpoints without employee identifiers and validates paging',async()=>{
 vi.resetModules();const calls:any[]=[]
 vi.stubGlobal('uni',{request:(o:any)=>{calls.push(o);const tab=o.url.includes('/approved?')?'approved':o.url.includes('/pending?')?'pending':'all';queueMicrotask(()=>o.success({statusCode:200,data:{code:0,data:result(tab,1,tab==='pending'?[]:[item()])}}))}})
 const {useUserStore}=await import('../src/stores/user');useUserStore().profile={bound:true,canEnterHome:true} as any
 const {setToken}=await import('../src/api/request');setToken('fixture-session')
 const {reportsApi}=await import('../src/api/reports')
 for(const t of ['all','approved','pending'] as const)await reportsApi.paginatedList(t)
 expect(calls.map(o=>o.url.split('/ehr/miniapp')[1])).toEqual(['/reports?page=1&pageSize=10','/reports/approved?page=1&pageSize=10','/reports/pending?page=1&pageSize=10'])
 expect(calls.every(o=>o.header.Authorization==='Bearer fixture-session'&&o.data===undefined)).toBe(true)
 await expect(reportsApi.paginatedList('all',0)).rejects.toMatchObject({status:422});expect(calls).toHaveLength(3)
})
it('ignores late responses after switching tabs or hiding the page',async()=>{
 const pending:{resolve:(v:ReportListPage)=>void}[]=[]
 const list=useReportList(()=>new Promise(resolve=>pending.push({resolve})))
 const old=list.load();const current=list.select('approved');pending[1].resolve(result('approved'));await current
 pending[0].resolve(result('all',1,[item('old')]));await old;expect(list.items.value[0].reportId).toBe('fixture');expect(list.tab.value).toBe('approved')
 const hidden=list.load();list.cancel();pending[2].resolve(result('approved',1,[item('hidden')]));await hidden;expect(list.items.value).toEqual([])
})
it('retries the failed next page and merges repeated report IDs without losing loaded rows',async()=>{
 const fetch=vi.fn().mockResolvedValueOnce(result('all',1,[item('a')],true)).mockRejectedValueOnce(new Error('合成网络故障')).mockResolvedValueOnce(result('all',2,[item('a',2),item('b')]))
 const list=useReportList(fetch);await list.load();await list.load(true)
 expect(list.items.value.map(v=>v.reportId)).toEqual(['a']);expect(list.page.value).toBe(1);expect(list.error.value).toContain('网络故障')
 await list.retry();expect(fetch.mock.calls[2]).toEqual(['all',2,10]);expect(list.items.value.map(v=>v.reportId)).toEqual(['a','b']);expect(list.items.value[0].revision).toBe(2)
 expect(list.hasMore.value).toBe(false)
})
