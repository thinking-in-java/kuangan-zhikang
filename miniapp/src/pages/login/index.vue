<script setup lang="ts">
import {ref} from 'vue'
import {onShow} from '@dcloudio/uni-app'
import AppIcon from '../../components/AppIcon.vue'
import {authApi} from '../../api/auth'
import type {LoginConfig} from '../../types/ehr'
import {useUserStore} from '../../stores/user'
import {landing,go} from '../../utils/navigation'
import {toast,errorText} from '../../utils/format'
const store=useUserStore(),consent=ref(false),busy=ref(false),error=ref(''),loadingConfig=ref(true),loginConfig=ref<LoginConfig>()
async function loadConfig(){
 loadingConfig.value=true;error.value=''
 try{
  const cfg=await authApi.config()
  if(typeof cfg.wechatConfigured!=='boolean'||typeof cfg.consentVersion!=='string'||!cfg.consentVersion.trim())throw new Error('后台登录配置不完整，请联系管理员')
  if(loginConfig.value?.consentVersion!==cfg.consentVersion)consent.value=false
  loginConfig.value=cfg
  if(!cfg.wechatConfigured)error.value='后台尚未配置微信登录参数，请联系管理员启用后刷新服务状态'
  return cfg
 }catch(e){loginConfig.value=undefined;consent.value=false;error.value=errorText(e);return undefined}
 finally{loadingConfig.value=false}
}
onShow(async()=>{if(busy.value)return;loadingConfig.value=true;await store.restore();if(store.profile){uni.reLaunch({url:landing(store.profile)});return}await loadConfig();if(store.restoreError&&!error.value)error.value=store.restoreError})
async function login(){
 if(busy.value||loadingConfig.value)return
 if(!consent.value){toast('请先阅读并同意用户协议和隐私政策');return}
 busy.value=true;error.value=''
 try{
  // Recheck the current agreement before obtaining a one-use WeChat code.
  const cfg=await loadConfig()
  if(!cfg?.wechatConfigured)return
  if(!consent.value){error.value='协议版本已更新，请重新阅读并勾选同意后登录';return}
  const code=await new Promise<string>((resolve,reject)=>uni.login({provider:'weixin',timeout:15000,success:res=>res.code?resolve(res.code):reject(new Error('未获取到微信登录凭证，请在微信小程序中重试')),fail:()=>reject(new Error('微信登录未完成，请在微信小程序中稍后重试'))}))
  const result=await authApi.login(code,cfg.consentVersion)
  await store.acceptLogin(result)
  if(!store.profile)throw new Error('登录状态未返回，请重试')
  await uni.reLaunch({url:landing(store.profile)})
 }catch(e){error.value=errorText(e)}finally{busy.value=false}
}
</script>
<template><view class="welcome-page"><view class="welcome-scene"><image src="/static/mine-scene.png" class="mine-scene" mode="aspectFill"/><view class="brand"><view class="brand-logo"><AppIcon name="mountain" color="white" :size="43"/></view><text class="brand-name">矿安智康</text><text class="brand-tagline">让每一份辛勤，都有健康守护</text><text class="brand-en">OCCUPATIONAL HEALTH CARE</text></view></view><view class="welcome-body"><view class="card login-card"><text class="eyebrow">WELCOME TO KUANGAN</text><text class="welcome-title">欢迎，矿山的奋斗者</text><text class="welcome-description">登录后验证企业职工身份，开启您的专属健康档案</text><view class="service-points"><view><AppIcon name="folder-heart" :size="25"/><text>健康档案</text></view><view><AppIcon name="shield-check" :size="25"/><text>职业守护</text></view><view><AppIcon name="bot" :size="25"/><text>AI 健康助手</text></view></view><view class="consent"><view class="checkbox" :class="{checked:consent}" @tap="!busy&&(consent=!consent)"><AppIcon v-if="consent" name="check" color="white" :size="12"/></view><view>我已阅读并同意 <text @tap="go('/pages/common/legal/index?type=terms')">用户协议</text> 和 <text @tap="go('/pages/common/legal/index?type=privacy')">隐私政策</text></view></view><view v-if="error" class="alert login-error">{{error}}</view><button class="btn login-btn" :disabled="busy||loadingConfig||!loginConfig?.wechatConfigured" :loading="busy" @tap="login"><AppIcon name="message-circle" color="white" :size="24"/>{{busy?'正在登录…':'微信一键登录'}}</button><view class="login-service"><text>{{loadingConfig?'正在获取登录配置…':'微信身份与企业档案分别验证'}}</text><text v-if="!busy&&!loadingConfig" class="green-text" @tap="loadConfig">刷新服务状态</text></view></view><view class="welcome-footer"><AppIcon name="shield-check" color="gray" :size="16"/><text>用心守护每一位矿山人</text></view></view></view></template>
<style scoped>.welcome-page{min-height:100vh;background:#f5faf7;padding-bottom:env(safe-area-inset-bottom)}.welcome-scene{height:660rpx;position:relative;overflow:hidden;background:linear-gradient(#f1faf4,#d6ece0)}.mine-scene{width:100%;height:340rpx;position:absolute;bottom:0;left:0}.brand{display:flex;align-items:center;flex-direction:column;position:relative;padding-top:120rpx}.brand-logo{height:120rpx;width:120rpx;background:#008e54;border-radius:38rpx;display:flex;align-items:center;justify-content:center;box-shadow:0 16rpx 35rpx #008d5425}.brand-name{font-size:60rpx;font-weight:750;letter-spacing:8rpx;color:#173e2b;margin-top:26rpx}.brand-tagline{font-size:24rpx;color:#668772;margin-top:15rpx;letter-spacing:2rpx}.brand-en{font-size:16rpx;color:#8fab99;letter-spacing:4rpx;margin-top:18rpx}.welcome-body{padding:0 34rpx;position:relative;margin-top:-90rpx}.login-card{padding:38rpx 32rpx}.welcome-title{font-size:40rpx;font-weight:700;display:block;margin-top:15rpx}.welcome-description{display:block;font-size:24rpx;color:#84988a;line-height:1.8;margin-top:15rpx}.service-points{display:flex;padding:35rpx 0;margin:12rpx 0 22rpx;border-bottom:1px solid #edf2ee}.service-points>view{flex:1;display:flex;align-items:center;flex-direction:column;gap:15rpx;font-size:22rpx;color:#6f8978}.consent{display:flex;gap:12rpx;align-items:flex-start;font-size:21rpx;line-height:1.8;color:#8a9c90}.consent text{color:#1c8d5c}.checkbox{width:30rpx;height:30rpx;border:1px solid #aac7b3;border-radius:7rpx;display:flex;align-items:center;justify-content:center;flex-shrink:0;margin-top:3rpx}.checked{background:#009358;border-color:#009358}.login-btn{margin-top:26rpx;min-height:100rpx}.switch-role{display:flex;align-items:center;justify-content:center;gap:14rpx;font-size:23rpx;color:#93a399;margin-top:28rpx}.demo-note{font-size:20rpx;color:#96a79b;line-height:1.75;margin-top:28rpx}.login-error{margin:20rpx 0 0}.welcome-footer{display:flex;align-items:center;justify-content:center;gap:12rpx;font-size:22rpx;color:#96ab9c;letter-spacing:2rpx;padding:20rpx 0 42rpx}.login-service{display:flex;flex-direction:column;align-items:center;gap:16rpx;font-size:22rpx;color:#8c9d92;margin-top:25rpx}</style>
