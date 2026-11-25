import {ref} from 'vue'
import {onShow} from '@dcloudio/uni-app'
import {requireRole} from './navigation'
import {errorText} from './format'
import type {Role} from '../types'
export function usePageData(role:Role|undefined,fetcher:()=>Promise<void>){
 const loading=ref(false),error=ref('')
 async function load(){if(!await requireRole(role))return;loading.value=true;error.value='';try{await fetcher()}catch(e){error.value=errorText(e)}finally{loading.value=false}}
 onShow(load)
 return {loading,error,load}
}
