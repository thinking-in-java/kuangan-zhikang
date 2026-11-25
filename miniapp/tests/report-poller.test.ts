import {it,expect,vi,afterEach} from 'vitest'
import {createReportPoller} from '../src/utils/report-poller'
import type {Report} from '../src/types'
const sample=(state:string)=>({status:'processing',ocrTask:{state}} as Report)
afterEach(()=>vi.useRealTimers())
it('polls snapshots to a terminal state and stops',async()=>{
 vi.useFakeTimers();const fetch=vi.fn().mockResolvedValueOnce(sample('queued')).mockResolvedValueOnce(sample('running')).mockResolvedValueOnce(sample('succeeded'));const onData=vi.fn(),onError=vi.fn()
 const p=createReportPoller(fetch,{onData,onError,onLimit:vi.fn(),interval:10});p.start();await vi.advanceTimersByTimeAsync(100)
 expect(fetch).toHaveBeenCalledTimes(3);expect(onData).toHaveBeenCalledTimes(3);expect(onError).not.toHaveBeenCalled()
})
it('ignores in-flight responses after leaving the page and prevents stale runs',async()=>{
 vi.useFakeTimers();let resolve!:(r:Report)=>void
 const fetch=vi.fn().mockImplementationOnce(()=>new Promise<Report>(r=>resolve=r)).mockResolvedValue(sample('failed'));const onData=vi.fn()
 const p=createReportPoller(fetch,{onData,onError:vi.fn(),onLimit:vi.fn(),interval:10});p.start();p.stop();p.start();await vi.advanceTimersByTimeAsync(0);resolve(sample('running'));await vi.advanceTimersByTimeAsync(100)
 expect(fetch).toHaveBeenCalledTimes(2);expect(onData).toHaveBeenCalledTimes(1);expect(onData.mock.calls[0][0].ocrTask.state).toBe('failed')
})
it('bounds polling and surfaces network/authentication failures once',async()=>{
 vi.useFakeTimers();const onLimit=vi.fn(),fetch=vi.fn().mockResolvedValue(sample('retry_wait'))
 const p=createReportPoller(fetch,{onData:vi.fn(),onError:vi.fn(),onLimit,interval:10,maxAttempts:2});p.start();await vi.advanceTimersByTimeAsync(100);expect(fetch).toHaveBeenCalledTimes(2);expect(onLimit).toHaveBeenCalledOnce()
 const onError=vi.fn(),bad=vi.fn().mockRejectedValue(new Error('会话失效'));const q=createReportPoller(bad,{onData:vi.fn(),onError,onLimit:vi.fn(),interval:10});q.start();await vi.advanceTimersByTimeAsync(100);expect(bad).toHaveBeenCalledOnce();expect(onError).toHaveBeenCalledOnce()
})
