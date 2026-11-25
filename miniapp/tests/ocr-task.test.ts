import {it,expect} from 'vitest'
import {readOcrTask,shouldPollReport} from '../src/utils/ocr-task'
import {reportFromEhr} from '../src/utils/ehr-reports'
import type {Report,Profile} from '../src/types'
const report=(task:any,status='processing')=>({status,ocrTask:readOcrTask(task)} as Report)
it('preserves backend percent, stage, attempts and failure text',()=>{
 expect(readOcrTask({jobId:'j',state:'running',stage:'field_extraction',percent:60,attemptCount:2})).toMatchObject({jobId:'j',percent:60,stage:'field_extraction',attemptCount:2})
 expect(readOcrTask({state:'retry_wait',stage:'ocr',percent:0,attemptCount:3,errorCode:'ENGINE_UNAVAILABLE',message:'服务暂不可用'},'j')).toMatchObject({jobId:'j',percent:0,attemptCount:3,errorCode:'ENGINE_UNAVAILABLE',message:'服务暂不可用'})
})
it('does not fabricate missing or invalid numeric progress',()=>{
 expect(readOcrTask(undefined)).toBeUndefined();expect(readOcrTask({state:'running'})?.percent).toBeNull()
 expect(readOcrTask({percent:120,attemptCount:-1})).toMatchObject({percent:null,attemptCount:null})
 expect(readOcrTask({percent:'60'})?.percent).toBeNull()
 expect(readOcrTask({state:'failed',lastError:{code:'BAD_PDF',message:'原件无法解析'}})).toMatchObject({errorCode:'BAD_PDF',message:'原件无法解析'})
})
it('polls only active OCR states and does not equate OCR success with publication',()=>{
 for(const state of ['queued','running','retry_wait'])expect(shouldPollReport(report({state}))).toBe(true)
 for(const state of ['succeeded','failed','cancelled','new_unknown_state'])expect(shouldPollReport(report({state}))).toBe(false)
 expect(shouldPollReport(report({state:'running'},'approved'))).toBe(false)
 const p={employeeId:'employee',name:'合成',employeeNo:'TEST'} as Profile
 const r=reportFromEhr({id:'report',revision:1,status:'pending_review',candidate:true,jobId:'j',ocrTask:{jobId:'j',state:'succeeded',stage:'finished',percent:100,attemptCount:1}},p)
 expect(r.status).toBe('pending');expect(r.candidate).toBe(true);expect(r.jobId).toBe('j');expect(r.ocrTask?.state).toBe('succeeded')
})
