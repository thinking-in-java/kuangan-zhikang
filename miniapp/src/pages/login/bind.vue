<script setup lang="ts">
import {ref} from 'vue'
import {onShow} from '@dcloudio/uni-app'
import PageShell from '../../components/PageShell.vue'
import AppIcon from '../../components/AppIcon.vue'
import {authApi} from '../../api/auth'
import {useUserStore} from '../../stores/user'
import {toast,errorText} from '../../utils/format'
import {landing} from '../../utils/navigation'
const store=useUserStore(),name=ref(''),employeeNo=ref(''),verificationCode=ref(''),busy=ref(false),error=ref('')
onShow(async()=>{await store.restore();if(!store.profile)uni.reLaunch({url:'/pages/login/index'});else if(store.profile.bound||store.profile.bindingState==='CORRECTION_PENDING'||store.profile.nextAction==='WAIT_FOR_REVIEW'||store.validChallenge())uni.reLaunch({url:landing(store.profile)})})
async function bind(){
 if(busy.value)return
 const code=verificationCode.value.replace(/[-\s]/g,'').toUpperCase()
 if(!name.value.trim()||!employeeNo.value.trim()||!code){toast('请完整填写身份信息');return}
 if(code.length!==16){toast('请输入企业提供的16位个人专属验证码');return}
 busy.value=true;error.value=''
 try{const challenge=await authApi.verify({name:name.value.trim(),employeeNo:employeeNo.value.trim(),verificationCode:code});verificationCode.value='';store.setChallenge(challenge);uni.reLaunch({url:'/pages/login/confirm'})}catch(e){error.value=errorText(e)}finally{busy.value=false}
}
</script>
<template><PageShell title="职工身份绑定"><view class="content"><view class="binding-intro"><view class="binding-icon"><AppIcon name="badge-check" :size="40"/></view><text class="title">认识您，才能更好地守护</text><text class="subtitle">绑定企业职工身份，开启您的专属健康档案</text></view><view class="card"><text class="label">姓名</text><input v-model="name" class="field" placeholder="请输入企业登记姓名" maxlength="100"/><text class="label">职工工号</text><input v-model="employeeNo" class="field" placeholder="请输入您的工号" maxlength="96"/><text class="label">企业身份验证码</text><input v-model="verificationCode" class="field" placeholder="例如：ABCD-EFGH-JKLM-NPQR" maxlength="40"/><view class="hint">请输入企业后台为您单独签发的16位验证码。姓名、工号和验证码须对应同一份档案；此步骤只校验信息，下一步确认后才完成绑定。</view><view v-if="error" class="alert" style="margin-top:24rpx">{{error}}</view><button class="btn" style="margin-top:32rpx" :loading="busy" :disabled="busy" @tap="bind">验证身份并核对资料</button><button class="btn btn-soft" style="margin-top:24rpx" :disabled="busy" @tap="store.logout">退出当前登录</button></view></view></PageShell></template>
<style scoped>.binding-intro{padding:35rpx 0 45rpx}.binding-icon{background:#e5f4e9;display:inline-flex;padding:25rpx;border-radius:28rpx;margin-bottom:30rpx}.binding-intro .title{display:block;font-size:37rpx}.binding-intro .subtitle{display:block;line-height:1.8}</style>
