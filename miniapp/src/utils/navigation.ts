import type {Profile,Role} from '../types'
import {useUserStore} from '../stores/user'
export function landing(p:Profile){
 if(p.role!=='worker')return '/pages/login/index'
 if(p.bindingState==='CORRECTION_PENDING'||p.nextAction==='WAIT_FOR_REVIEW')return '/pages/login/pending'
 if(p.bindingState==='BOUND'&&p.bound&&p.canEnterHome!==false)return '/pages/worker/home/index'
 if(p.bindingState==='PENDING_CONFIRMATION'&&p.confirmationReady)return '/pages/login/confirm'
 return '/pages/login/bind'
}
export function go(url:string){uni.navigateTo({url})}
export function tab(url:string){uni.reLaunch({url})}
export async function requireRole(role?:Role){const store=useUserStore();await store.restore();const p=store.profile
 if(!p){uni.reLaunch({url:'/pages/login/index'});return false}
 if((role&&p.role!==role)||!p.bound||p.bindingState!=='BOUND'||p.canEnterHome===false){uni.reLaunch({url:landing(p)});return false}
 return true
}
