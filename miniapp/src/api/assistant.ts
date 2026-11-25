import {ehrRequest,ApiError} from './request'
import type {AgentContext,AgentConversation,AgentPage,AgentHistory,AgentQuestion,AgentTurn} from '../types/agent'
const id=(v:string)=>{if(!v)throw new ApiError('缺少对话或消息编号');return encodeURIComponent(v)}
function paging(page:number,size:number,max:number){if(!Number.isInteger(page)||page<1||!Number.isInteger(size)||size<1||size>max)throw new ApiError('对话分页参数不正确',422);return '?page='+page+'&pageSize='+size}
export const assistantApi={
 context:()=>ehrRequest<AgentContext>('/agent/context'),
 create:()=>ehrRequest<AgentConversation>('/agent/conversations','POST',{}),
 conversations:(page=1,size=20)=>ehrRequest<AgentPage<AgentConversation>>('/agent/conversations'+paging(page,size,50)),
 history:(conversationId:string,page=1,size=10)=>ehrRequest<AgentHistory>('/agent/conversations/'+id(conversationId)+paging(page,size,30)),
 remove:(conversationId:string)=>ehrRequest<{id:string;deleted:boolean}>('/agent/conversations/'+id(conversationId),'DELETE'),
 chat:(data:AgentQuestion)=>{
  if(!data.message.trim()||data.message.length>3000||!/^[a-zA-Z0-9_-]{8,96}$/.test(data.requestId))throw new ApiError('问题或请求标识不正确',422)
  if(data.context&&(!data.context.reportId||!Number.isInteger(data.context.revision)||data.context.revision<1))throw new ApiError('请选择有效的报告版本',422)
  // Whitelist fields: never forward employee identity, client history or model settings.
  return ehrRequest<AgentTurn>('/agent/chat','POST',{conversationId:data.conversationId,requestId:data.requestId,message:data.message,...(data.context?{context:{reportId:data.context.reportId,revision:data.context.revision}}:{})})
 },
 message:(messageId:string)=>ehrRequest<AgentTurn>('/agent/messages/'+id(messageId)),
}
