import {it,expect} from 'vitest'
import {mockRequest} from '../src/mock/server'
import type {Profile} from '../src/types'
it('validates version and updates editable fields atomically without changing identity or role',async()=>{
 const token='demo-worker'
 const bound=await mockRequest<Profile>('/auth/bind-worker','POST',{name:'示例',employeeNo:'TEST',verificationCode:'123456'},token)
 await expect(mockRequest('/me/reports','GET',undefined,token)).rejects.toThrow('信息确认')
 await expect(mockRequest('/auth/confirm-worker','POST',{profileVersion:'old',confirmed:true},token)).rejects.toThrow('已更新')
 await expect(mockRequest('/auth/confirm-worker','POST',{profileVersion:bound.profileVersion,confirmed:false},token)).rejects.toThrow('核对')
 await expect(mockRequest('/auth/confirm-worker','POST',{profileVersion:bound.profileVersion,confirmed:true,updates:{role:'doctor'}},token)).rejects.toThrow('不可修改')
 const p=await mockRequest<Profile>('/auth/confirm-worker','POST',{profileVersion:bound.profileVersion,confirmed:true,updates:{name:'修改后姓名',team:'更新班组',phone:'13800000000'}},token)
 expect(p.identityConfirmed).toBe(true);expect(p.name).toBe('修改后姓名');expect(p.team).toBe('更新班组');expect(p.phoneMasked).toBe('138****0000');expect((p as any).phone).toBeUndefined();expect(p.id).toBe(bound.id);expect(p.role).toBe('worker')
 expect((await mockRequest<Profile>('/me/profile','GET',undefined,token)).name).toBe('修改后姓名')
 await expect(mockRequest('/auth/confirm-worker','POST',{profileVersion:bound.profileVersion,confirmed:true},token)).rejects.toThrow('已更新')
 await expect(mockRequest('/auth/confirm-worker','POST',{profileVersion:p.profileVersion,confirmed:true},'demo-doctor')).rejects.toThrow('绑定')
})
