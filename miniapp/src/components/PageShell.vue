<script setup lang="ts">
import {ref} from 'vue'
import {config} from '../config'
import BottomNavigation from './BottomNavigation.vue'
import AppIcon from './AppIcon.vue'
withDefaults(defineProps<{title:string;back?:boolean;tab?:string;doctor?:boolean;hero?:boolean;live?:boolean}>(),{back:true})
const top=ref(uni.getSystemInfoSync().statusBarHeight||20)
function backPage(){if(getCurrentPages().length>1)uni.navigateBack();else uni.reLaunch({url:'/pages/login/index'})}
</script>
<template>
 <view class="page-shell" :class="{'has-tab':!!tab,'hero-shell':hero}">
  <view class="app-header" :style="{paddingTop:top+'px'}">
   <view class="header-inner"><view class="header-back" @tap="back&&backPage()"><AppIcon v-if="back" name="chevron-left" color="white" :size="24"/><AppIcon v-else name="mountain" color="white" :size="27"/></view><text class="header-title">{{title}}</text><view class="header-right" /></view>
  </view>
  <view v-if="config.useMock&&!live" class="demo-strip"><view class="demo-dot"/>演示空间 · 所有健康数据均为模拟</view>
  <slot/>
  <BottomNavigation v-if="tab" :active="tab" :doctor="doctor"/>
 </view>
</template>
<style scoped lang="scss">
.page-shell{min-height:100vh;padding-bottom:36rpx}.has-tab{padding-bottom:calc(154rpx + env(safe-area-inset-bottom))}.app-header{background:#008653;color:white}.header-inner{height:92rpx;display:flex;align-items:center;padding:0 30rpx}.header-back,.header-right{width:150rpx;display:flex;align-items:center}.header-title{font-size:34rpx;font-weight:650;flex:1;text-align:center;white-space:nowrap}.demo-strip{padding:12rpx 30rpx;font-size:20rpx;color:#66857a;background:#edf6f0;display:flex;align-items:center;gap:10rpx;letter-spacing:1rpx}.demo-dot{width:9rpx;height:9rpx;background:#57a57f;border-radius:50%}
</style>
