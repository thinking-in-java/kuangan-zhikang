import {beforeAll,describe,it,expect,vi} from 'vitest'
import {mockRequest,mockUpload} from '../src/mock/server'
import {validateFiles} from '../src/utils/upload'
import type {Profile,Report,SelectedFile,Archive} from '../src/types'
const workerToken='demo-worker',doctorToken='demo-doctor'
beforeAll(async()=>{await mockRequest('/auth/bind-worker','POST',{name:'演示职工',employeeNo:'MK000128',verificationCode:'123456'},workerToken);await mockRequest('/auth/confirm-worker','POST',{profileVersion:'mock-worker-v1',confirmed:true},workerToken)})
describe('authorization boundaries',()=>{
 it('rejects unauthenticated users and pending doctors',async()=>{
  await expect(mockRequest('/me/profile','GET',undefined,'fake')).rejects.toThrow('登录')
  await expect(mockRequest('/doctor/dashboard','GET',undefined,'demo-pending')).rejects.toThrow('身份')
 })
 it('rejects worker access to doctor APIs',async()=>{
  await expect(mockRequest('/doctor/reviews','GET',undefined,workerToken)).rejects.toThrow('医生权限')
 })
 it('isolates workers and unauthorized departments',async()=>{
  await expect(mockRequest('/reports/R20261113003','GET',undefined,workerToken)).rejects.toThrow('无访问权限')
  await expect(mockRequest('/doctor/workers/out-of-scope','GET',undefined,doctorToken)).rejects.toThrow('授权')
  const archive=await mockRequest<Archive>('/me/health-archive','GET',undefined,workerToken)
  expect(archive.reports.every(r=>r.workerId==='w-001')).toBe(true)
 })
 it('rejects a report outside the chat user scope',async()=>{
  await expect(mockRequest('/assistant/chat','POST',{message:'解读',reportId:'R20261113003',requestId:'chat-invalid'},workerToken)).rejects.toThrow('无权')
 })
})
describe('report and review integrity',()=>{
 it('enforces count, size and PDF validation',()=>{
  const f:SelectedFile={id:'test',name:'report.pdf',path:'blob:test',size:1024,type:'pdf',progress:0}
  expect(()=>validateFiles([])).toThrow('至少')
  expect(()=>validateFiles(Array(10).fill(f))).toThrow('最多')
  expect(()=>validateFiles([{...f,size:21*1024*1024}])).toThrow('20 MB')
  expect(()=>validateFiles([{...f,name:'bad.exe'}])).toThrow('PDF')
  expect(()=>validateFiles([f])).not.toThrow()
 })
 it('keeps upload submission idempotent and links only own files',async()=>{
  const file:SelectedFile={id:'test-upload',name:'report.pdf',path:'blob:test',size:1024,type:'pdf',progress:0}
  const fileId=await mockUpload(file,workerToken,()=>{})
  const payload={fileIds:[fileId],idempotencyKey:'test-idempotency',type:'职业健康检查',date:'2026-11-12',institution:'演示医院'}
  const a=await mockRequest<Report>('/reports','POST',payload,workerToken)
  const b=await mockRequest<Report>('/reports','POST',payload,workerToken)
  expect(a.id).toBe(b.id)
  await expect(mockRequest('/reports','POST',{...payload,idempotencyKey:'bad-file',fileIds:['not-owned']},workerToken)).rejects.toThrow('无访问权限')
 })
 it('requires confirmation and preserves original conclusion on OCR correction',async()=>{
  const path='/doctor/reviews/R20261113002/approve'
  const before=await mockRequest<Report>('/reports/R20261113002','GET',undefined,doctorToken)
  await expect(mockRequest(path,'POST',{note:'已核对',corrections:[],confirmedFields:[]},doctorToken)).rejects.toThrow('人工确认')
  const after=await mockRequest<Report>(path,'POST',{note:'已核对原件',corrections:[{key:'hearing',value:'25 / 28'}],confirmedFields:['hearing']},doctorToken)
  expect(after.conclusion).toBe(before.conclusion)
  expect(after.status).toBe('approved')
  expect(after.fields.find(f=>f.key==='hearing')?.sourceValue).toBe('25 / 30')
  await expect(mockRequest(path,'POST',{note:'重复',corrections:[],confirmedFields:['hearing']},doctorToken)).rejects.toThrow('已处理')
 })
 it('rejects creation of a followup for an unauthorized worker',async()=>{
  await expect(mockRequest('/doctor/followups','POST',{workerId:'not-authorized',title:'任务',content:'测试',dueDate:'2026-11-20'},doctorToken)).rejects.toThrow('授权')
 })
})
