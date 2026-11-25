export type ReportTab='all'|'approved'|'pending'
export interface ReportListItem {
 reportId:string;revision:number;title:string;fileName?:string;status:string;statusText:string;
 approved:boolean;published:boolean;candidate:boolean;uploadedAt?:string|null;reviewedAt?:string|null;
 examDateRaw?:string|null;examDateVerified?:string|null;institutionRaw?:string|null;
 jobId?:string|null;errorCode?:string|null;errorMessage?:string|null;detailPath?:string;
}
export interface ReportListPage {status:ReportTab;page:number;pageSize:number;total:number;hasMore:boolean;counts:Record<ReportTab,number>;items:ReportListItem[]}
