import {ref} from 'vue'
import type {ReportListPage,ReportListItem,ReportTab} from '../types/report-list'
import {errorText} from './format'
export function useReportList(fetchPage:(tab:ReportTab,page:number,pageSize:number)=>Promise<ReportListPage>){
 const tab=ref<ReportTab>('all'),items=ref<ReportListItem[]>([]),counts=ref<Record<ReportTab,number>>({all:0,approved:0,pending:0}),loaded=ref(false),total=ref(0),page=ref(0),hasMore=ref(false),loading=ref(false),error=ref('')
 let generation=0,lastAppend=false
 function cancel(){generation++;loading.value=false}
 async function load(append=false){
  if(append&&(loading.value||!hasMore.value))return
  const current=++generation,selected=tab.value,next=append?page.value+1:1;lastAppend=append
  loading.value=true;error.value=''
  if(!append){items.value=[];page.value=0;hasMore.value=false;loaded.value=false}
  try{
   const result=await fetchPage(selected,next,10)
   if(current!==generation)return
   const rows=append?[...items.value,...result.items]:result.items
   items.value=Array.from(new Map(rows.map(r=>[r.reportId,r])).values())
   counts.value=result.counts;total.value=result.total;page.value=result.page;hasMore.value=result.hasMore;loaded.value=true
  }catch(e){if(current===generation)error.value=errorText(e)}finally{if(current===generation)loading.value=false}
 }
 async function select(value:ReportTab){if(tab.value===value)return;tab.value=value;await load()}
 return {tab,items,counts,total,page,hasMore,loaded,loading,error,load,select,cancel,retry:()=>load(lastAppend)}
}
