import {it,expect,vi,afterEach} from 'vitest'
import {createPersonalAgent,personalAgent} from '../src/utils/personal-agent'
import {ApiError,setToken} from '../src/api/request'
const cfg={enabled:true,bindingState:'BOUND',personalOnly:true,canModify:false,assistantName:'合成健康师',suggestions:[],reportCounts:{total:1,approved:1}}
const page={items:[],page:1,pageSize:20,total:0,hasMore:false}
const accepted={conversationId:'c',messageId:'m',status:'queued' as const,pollAfterMs:500}
function api(){return {context:vi.fn().mockResolvedValue(cfg),create:vi.fn().mockResolvedValue({id:'c',title:'合成对话'}),conversations:vi.fn().mockResolvedValue(page),history:vi.fn().mockResolvedValue({...page,id:'c',title:'合成历史'}),remove:vi.fn().mockResolvedValue({id:'c',deleted:true}),chat:vi.fn().mockResolvedValue(accepted),message:vi.fn().mockResolvedValue({...accepted,status:'completed',answer:'合成回复'})}}
afterEach(()=>{vi.useRealTimers();vi.restoreAllMocks()})
it('creates, sends exact report context, polls and stops at completed',async()=>{
 vi.useFakeTimers();const a=api(),c=createPersonalAgent(a);await c.initialize();await c.send('合成问题',{reportId:'r',revision:2});await vi.waitFor(()=>expect(c.turns.value[0].status).toBe('completed'))
 expect(a.chat.mock.calls[0][0]).toMatchObject({conversationId:'c',message:'合成问题',context:{reportId:'r',revision:2}});expect(c.busy.value).toBe(false)
 const n=a.message.mock.calls.length;await vi.advanceTimersByTimeAsync(5000);expect(a.message).toHaveBeenCalledTimes(n);c.reset()
})
it('retries uncertain transport using the same requestId without creating another conversation',async()=>{
 const a=api();a.chat.mockRejectedValueOnce(new ApiError('网络中断'));const c=createPersonalAgent(a);await c.initialize();c.hide();await c.send('同一问题');const first=a.chat.mock.calls[0][0]
 await c.send('',undefined,true);expect(a.chat.mock.calls[1][0]).toEqual(first);expect(a.create).toHaveBeenCalledOnce();expect(c.pending.value?.messageId).toBe('m');c.reset()
})
it('does not poll while hidden and resumes GET without reposting a question',async()=>{
 vi.useFakeTimers();const a=api();a.message.mockResolvedValue({...accepted,status:'running'});const c=createPersonalAgent(a);await c.initialize();c.hide();await c.send('问题')
 expect(a.message).not.toHaveBeenCalled();c.resume();await vi.waitFor(()=>expect(a.message).toHaveBeenCalledOnce());c.hide();await vi.advanceTimersByTimeAsync(4000);expect(a.message).toHaveBeenCalledOnce();c.resume();await vi.waitFor(()=>expect(a.message).toHaveBeenCalledTimes(2));expect(a.chat).toHaveBeenCalledOnce();c.reset()
})
it('treats HTTP200 failed status as failure and a deliberate model retry as a new request',async()=>{
 const a=api();a.chat.mockResolvedValue({...accepted,status:'failed',message:'模型超时',errorCode:'MODEL_TIMEOUT'} as any);const c=createPersonalAgent(a);await c.initialize();await c.send('问题');expect(c.turns.value[0].status).toBe('failed');expect(c.busy.value).toBe(false)
 await c.send('问题');expect(a.chat.mock.calls[0][0].requestId).not.toBe(a.chat.mock.calls[1][0].requestId);c.reset()
})
it('restores active history by querying the message; paginates older turns in front',async()=>{
 const a=api();a.history.mockResolvedValueOnce({...page,id:'c',title:'历史',items:[{...accepted,question:'历史问题'}],hasMore:true} as any);const c=createPersonalAgent(a);await c.initialize();c.hide();await c.open('c');expect(c.pending.value?.messageId).toBe('m');expect(a.chat).not.toHaveBeenCalled();c.reset()
 const d=createPersonalAgent(a);a.history.mockResolvedValueOnce({...page,id:'c',title:'历史',items:[{...accepted,status:'completed',messageId:'new'}]} as any).mockResolvedValueOnce({...page,id:'c',title:'历史',items:[{...accepted,status:'completed',messageId:'old'}]} as any);await d.open('c');await d.open('c',true);expect(d.turns.value.map(t=>t.messageId)).toEqual(['old','new']);d.reset()
})
it('clears messages on binding errors and token change, ignores in-flight replies after reset',async()=>{
 const a=api();a.message.mockRejectedValue(new ApiError('身份已变化',403,'EMPLOYEE_BINDING_CHANGED'));const c=createPersonalAgent(a);await c.initialize();await c.send('问题');await vi.waitFor(()=>expect(c.context.value).toBeUndefined());expect(c.turns.value).toEqual([])
 personalAgent.turns.value=[{...accepted,question:'不能跨账号保留'}];setToken('different-fixture-token');expect(personalAgent.turns.value).toEqual([])
 let finish:any;a.chat.mockImplementation(()=>new Promise(resolve=>finish=resolve));await c.initialize();const task=c.send('迟到问题');await vi.waitFor(()=>expect(finish).toBeTypeOf('function'));c.reset();finish(accepted);await task;expect(c.turns.value).toEqual([])
})
it('blocks deletion while processing and deletes completed conversations through the server',async()=>{
 const a=api(),c=createPersonalAgent(a);await c.initialize();c.hide();await c.send('问题');await expect(c.remove('c')).rejects.toThrow('处理中');expect(a.remove).not.toHaveBeenCalled();c.pending.value=undefined;await c.remove('c');expect(a.remove).toHaveBeenCalledWith('c');expect(c.conversationId.value).toBe('');c.reset()
})
it('recovers MESSAGE_IN_PROGRESS through history without a second model submission',async()=>{
 const a=api();a.chat.mockRejectedValue(new ApiError('已有问题处理中',409,'MESSAGE_IN_PROGRESS'));a.history.mockResolvedValue({...page,id:'c',items:[{...accepted,question:'原问题'}]} as any);const c=createPersonalAgent(a);await c.initialize();c.hide();await c.send('新问题');expect(c.pending.value?.messageId).toBe('m');expect(a.chat).toHaveBeenCalledOnce();c.reset()
})
