import type {Report} from '../types'
import {shouldPollReport} from './ocr-task'
export function createReportPoller(fetch:()=>Promise<Report>,options:{onData:(r:Report)=>void;onError:(e:unknown)=>void;onLimit:()=>void;interval?:number;maxAttempts?:number}){
 let timer:ReturnType<typeof setTimeout>|undefined,generation=0,active=false,attempts=0
 function stop(){active=false;generation++;if(timer)clearTimeout(timer);timer=undefined}
 async function tick(run:number){
  try{const report=await fetch();if(!active||run!==generation)return;attempts++;options.onData(report)
   if(shouldPollReport(report)){if(attempts>=(options.maxAttempts||120)){active=false;options.onLimit()}else timer=setTimeout(()=>tick(run),options.interval||3000)}else active=false
  }catch(e){if(active&&run===generation){active=false;options.onError(e)}}
 }
 function start(){stop();active=true;attempts=0;void tick(generation)}
 return {start,stop}
}
