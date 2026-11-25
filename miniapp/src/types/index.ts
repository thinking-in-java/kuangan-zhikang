export type Role = 'worker' | 'doctor' | 'pending'
export interface Profile { id:string; name:string; role:Role; employeeNo:string; bound:boolean; mine:string; department:string; team:string; job:string; years:number|null; age:number|null; hazards:string[]; institution?:string; gender?:string; birthDate?:string; organization?:string; phoneMasked?:string; identityConfirmed?:boolean; profileVersion?:string; miniUserId?:string; employeeId?:string; recordId?:string; bindingState?:'UNBOUND'|'PENDING_CONFIRMATION'|'CORRECTION_PENDING'|'BOUND';canEnterHome?:boolean;nextAction?:string; confirmationReady?:boolean }
export interface AuthResult { token:string; profile:Profile }
export type ReportStatus = 'processing' | 'pending' | 'approved' | 'returned' | 'failed' | 'unknown'
export interface ReportFile { id:string; name:string; url:string; type:'image'|'pdf' }
export interface Report { id:string; title:string; date:string; institution:string; type:string; status:ReportStatus; files:ReportFile[]; workerId:string; workerName:string; employeeNo:string; conclusion:string; fields:ReportField[]; analysis:string; reviewNote?:string; revision?:number; candidate?:boolean; rawStatus?:string; raw?:unknown; jobId?:string; ocrTask?:OcrTask }
export interface ReportField { key:string; label:string; value:string; unit?:string; confidence:number; needsConfirm:boolean; sourceValue?:string; abnormal?:boolean }
export interface Trend { key:string; label:string; unit:string; dates:string[]; values:number[] }
export interface Advice { id:string; category:string; title:string; content:string; date:string; basis:string; source:string }
export interface Followup { id:string; workerId:string; workerName:string; title:string; dueDate:string; content:string; status:'pending'|'submitted'|'completed'; reportId?:string; note?:string }
export interface Archive { profile:Profile; reports:Report[]; trends:Trend[]; advice:Advice[]; followups:Followup[] }
export interface Notice { id:string; title:string; content:string; date:string }
export interface Article { id:string; title:string; category:string; summary:string; body:string; source:string }
export interface Message { id:string; role:'user'|'assistant'; content:string; status?:'sending'|'sent'|'failed'; sources?:string[]; reportId?:string; requestId?:string }
export interface Session { id:string; title:string; messages:Message[] }
export interface Dashboard { workers:number; pendingReviews:number; followups:number; alerts:number; tasks:{title:string; count:number; url:string}[] }
export interface SelectedFile { id:string; name:string; path:string; size:number; type:'image'|'pdf'; uploadedId?:string; progress:number }
export interface UploadMetadata { type:string; date:string; institution:string; followupId?:string }
export interface ReviewDecision { note:string; corrections:{key:string; value:string}[]; confirmedFields:string[] }

export interface IdentityUpdates { name?:string; employeeNo?:string; gender?:string; birthDate?:string; organization?:string; team?:string; job?:string; phone?:string }

export interface OcrTask { jobId:string; state:string; stage:string; percent:number|null; attemptCount:number|null; errorCode:string; message:string }
