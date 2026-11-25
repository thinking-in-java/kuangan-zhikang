import { request } from './request'
import type { Dashboard,Report,Profile,Archive,ReviewDecision,Followup } from '../types'
export const doctorApi = {
 dashboard:()=>request<Dashboard>('/doctor/dashboard'),
 reviews:()=>request<Report[]>('/doctor/reviews'),
 workers:()=>request<Profile[]>('/doctor/workers'),
 worker:(id:string)=>request<Archive>('/doctor/workers/'+encodeURIComponent(id)),
 approve:(id:string,data:ReviewDecision)=>request<Report>('/doctor/reviews/'+encodeURIComponent(id)+'/approve','POST',data),
 reject:(id:string,data:ReviewDecision)=>request<Report>('/doctor/reviews/'+encodeURIComponent(id)+'/reject','POST',data),
 followups:()=>request<Followup[]>('/doctor/followups'),
 createFollowup:(data:{workerId:string;title:string;dueDate:string;content:string})=>request<Followup>('/doctor/followups','POST',data),
}
