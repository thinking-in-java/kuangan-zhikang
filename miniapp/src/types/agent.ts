export interface AgentContext {enabled:boolean;assistantName:string;bindingState:string;personalOnly:boolean;canModify:boolean;reportCounts:{total:number;approved:number};suggestions:string[]}
export interface AgentSource {key?:string;reportId:string;revision:number;reviewStatus:string;published:boolean;candidate:boolean;examDate?:string;institution?:string;pageNumbers?:number[]}
export interface AgentTurn {conversationId:string;messageId:string;question?:string;status:'queued'|'running'|'completed'|'failed';answer?:string;sources?:AgentSource[];errorCode?:string;message?:string;pollAfterMs?:number;dataAsOf?:string}
export interface AgentConversation {id:string;title:string;createdAt?:string;updatedAt?:string}
export interface AgentPage<T>{items:T[];page:number;pageSize:number;total:number;hasMore:boolean}
export interface AgentHistory extends AgentPage<AgentTurn>{id:string;title:string}
export interface AgentQuestion {conversationId:string;requestId:string;message:string;context?:{reportId:string;revision:number}}
