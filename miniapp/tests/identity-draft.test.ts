import {it,expect} from 'vitest'
import {identityDraft,identityUpdates} from '../src/utils/identity'
import {worker} from '../src/mock/data'
it('sends only edited values and never reuses the masked phone as a full phone',()=>{
 const p={...worker,phoneMasked:'138****0000'};const d=identityDraft(p)
 expect(d.phone).toBe('');expect(identityUpdates(p,d)).toEqual({})
 d.team=' 新班组 ';expect(identityUpdates(p,d)).toEqual({team:'新班组'})
 d.phone='123';expect(()=>identityUpdates(p,d)).toThrow('11位')
 d.phone='';d.birthDate='2025-02-30';expect(()=>identityUpdates(p,d)).toThrow('出生日期')
})
