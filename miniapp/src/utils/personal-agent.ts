import {ref,computed} from 'vue'
import {assistantApi} from '../api/assistant'
import {ApiError,onTokenChange} from '../api/request'
import {uid,errorText} from './format'
import type {AgentContext,AgentConversation,AgentQuestion,AgentTurn} from '../types/agent'
export function createPersonalAgent(api:typeof assistantApi=assistantApi){
 const context=ref<AgentContext>(),conversations=ref<AgentConversation[]>([]),conversationId=ref(''),title=ref('新的健康对话'),turns=ref<AgentTurn[]>([]),error=ref(''),loading=ref(false),pending=ref<{payload:AgentQuestion;messageId?:string}>(),hasMore=ref(false),historyMore=ref(false),paused=ref(false)
 let epoch=0,pollEpoch=0,visible=false,timer:ReturnType<typeof setTimeout>|undefined,listPage=0,historyPage=0,started=0
 const busy=computed(()=>!!pending.value)
 function stop(){pollEpoch++;if(timer)clearTimeout(timer);timer=undefined}
 function hide(){visible=false;stop()}
 function reset(){epoch++;hide();context.value=undefined;conversations.value=[];turns.value=[];conversationId.value='';title.value='新的健康对话';pending.value=undefined;error.value='';loading.value=false;hasMore.value=false;historyMore.value=false;paused.value=false;listPage=historyPage=0}
 function fail(e:unknown){error.value=errorText(e);if(e instanceof ApiError&&[401,403].includes(e.status)){const text=error.value;reset();error.value=text}else if(e instanceof ApiError&&e.status===404){pending.value=undefined;stop()}}
 function apply(turn:AgentTurn,question?:string){
  if(!turn.messageId||!['queued','running','completed','failed'].includes(turn.status))throw new Error('后台消息状态不完整，请刷新查询')
  const at=turns.value.findIndex(t=>t.messageId===turn.messageId)
  const old=at<0?undefined:turns.value[at],value={...old,...turn,question:turn.question||question||old?.question}
  if(at<0)turns.value.push(value);else turns.value.splice(at,1,value)
  if(['completed','failed'].includes(turn.status)){pending.value=undefined;stop()}
 }
 async function query(){
  if(!visible||!pending.value?.messageId)return
  const generation=epoch,run=++pollEpoch,current=pending.value,mid=current.messageId!
  try{
   const turn=await api.message(mid)
   if(generation!==epoch||run!==pollEpoch||!visible)return
   if(turn.messageId!==mid||turn.conversationId!==conversationId.value)throw new Error('后台返回的消息不属于当前对话')
   apply(turn,current.payload.message);error.value=''
   if(pending.value&&visible){if(Date.now()-started>16*60*1000){paused.value=true;error.value='自动查询已暂停，请点击继续查询';return}const delay=Math.min(30000,Math.max(500,Number.isFinite(turn.pollAfterMs)?turn.pollAfterMs!:1500));timer=setTimeout(query,delay)}
  }catch(e){if(generation===epoch&&run===pollEpoch){paused.value=true;fail(e)}}
 }
 function resume(){visible=true;paused.value=false;stop();started=Date.now();if(pending.value?.messageId)void query()}
 async function list(more=false){const generation=epoch;const page=more?listPage+1:1;const data=await api.conversations(page);if(generation!==epoch)return;if(!Array.isArray(data.items))throw new Error('后台未返回对话列表');conversations.value=more?[...conversations.value,...data.items]:data.items;listPage=page;hasMore.value=data.hasMore}
 async function open(id:string,more=false){
  if(busy.value&&!more)return
  const generation=epoch;loading.value=true;error.value=''
  try{const page=more?historyPage+1:1,data=await api.history(id,page);if(generation!==epoch)return
   if(data.id!==id||!Array.isArray(data.items))throw new Error('对话历史格式不正确')
   conversationId.value=id;title.value=data.title;turns.value=more?[...data.items,...turns.value]:data.items;historyPage=page;historyMore.value=data.hasMore
   const active=turns.value.find(t=>['queued','running'].includes(t.status))
   if(active){pending.value={messageId:active.messageId,payload:{conversationId:id,requestId:'history-query-only',message:active.question||''}};if(visible)resume()}
  }catch(e){if(generation===epoch)fail(e)}finally{if(generation===epoch)loading.value=false}
 }
 async function initialize(){visible=true;const generation=epoch;loading.value=true;error.value='';try{
  const data=await api.context();if(generation!==epoch)return
  if(data.bindingState!=='BOUND'||data.personalOnly!==true||data.canModify!==false)throw new Error('当前个人健康师权限不正确，请重新验证身份')
  context.value=data;await list();if(generation!==epoch)return
  if(pending.value){if(visible)resume();return}
  if(conversationId.value)await open(conversationId.value)
 }catch(e){if(generation===epoch)fail(e)}finally{if(generation===epoch)loading.value=false}}
 function newChat(){if(busy.value||loading.value)return;stop();conversationId.value='';turns.value=[];title.value='新的健康对话';historyPage=0;historyMore.value=false;error.value=''}
 async function send(message:string,report?:AgentQuestion['context'],retry=false){
  if(!context.value?.enabled||loading.value)return
  if(!retry&&busy.value)return
  const generation=epoch;loading.value=true;error.value='';stop()
  try{
   if(!retry){if(!message.trim()||message.length>3000)throw new Error('问题需为1至3000字')
    if(!conversationId.value){const c=await api.create();if(generation!==epoch)return;if(!c.id)throw new Error('后台未返回对话编号');conversationId.value=c.id;title.value=c.title}
    pending.value={payload:{conversationId:conversationId.value,requestId:uid(),message:message.trim(),...(report?{context:{...report}}:{})}}
   }
   const current=pending.value;if(!current)return
   // Once a message ID is known, recovery only queries it. Never POST again automatically.
   if(current.messageId){resume();return}
   const receipt=await api.chat(current.payload);if(generation!==epoch)return
   if(receipt.conversationId!==conversationId.value)throw new Error('后台对话编号不一致')
   current.messageId=receipt.messageId;apply(receipt,current.payload.message)
   if(pending.value&&visible)resume()
   void list().catch(()=>{})
  }catch(e){if(generation===epoch){paused.value=true
   if(e instanceof ApiError&&e.status===409&&e.errorCode==='MESSAGE_IN_PROGRESS'){
    try{const data=await api.history(conversationId.value);if(generation!==epoch)return;const active=data.items.find(t=>['queued','running'].includes(t.status));if(active){pending.value={messageId:active.messageId,payload:{conversationId:conversationId.value,requestId:'history-query-only',message:active.question||''}};apply(active);if(visible)resume();return}}catch{}
   }
   if(e instanceof ApiError&&([422,429].includes(e.status)||e.errorCode==='REQUEST_ID_CONFLICT'||e.errorCode==='AGENT_UNAVAILABLE'))pending.value=undefined
   if(e instanceof ApiError&&e.errorCode==='AGENT_UNAVAILABLE'&&context.value)context.value.enabled=false
   fail(e)
  }}finally{if(generation===epoch)loading.value=false}
 }
 async function remove(id:string){if(busy.value)throw new Error('有问题处理中，请完成后再删除');const generation=epoch;const result=await api.remove(id);if(generation!==epoch)return;if(!result.deleted)throw new Error('后台未确认删除');if(conversationId.value===id)newChat();await list()}
 return {context,conversations,conversationId,title,turns,error,loading,pending,busy,hasMore,historyMore,paused,initialize,list,open,newChat,send,remove,resume,hide,reset}
}
// Memory only. Token changes clear both displayed content and unfinished-message recovery state.
export const personalAgent=createPersonalAgent()
onTokenChange(()=>personalAgent.reset())
