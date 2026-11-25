<script setup lang="ts">
import { computed } from 'vue'
import AppIcon from './AppIcon.vue'
import {tab} from '../utils/navigation'
const props=defineProps<{active:string;doctor?:boolean}>()
const items=computed(()=>props.doctor?[
 {key:'dashboard',label:'工作台',icon:'layout-dashboard',url:'doctor/dashboard'}, {key:'reviews',label:'报告审核',icon:'clipboard-check',url:'doctor/reviews'}, {key:'workers',label:'职工管理',icon:'users',url:'doctor/workers'}, {key:'profile',label:'我的',icon:'user',url:'doctor/profile'}
]:[{key:'home',label:'首页',icon:'house',url:'worker/home'},{key:'archive',label:'健康档案',icon:'folder-heart',url:'worker/archive'},{key:'assistant',label:'AI助手',icon:'message-circle',url:'worker/assistant'},{key:'profile',label:'我的',icon:'user',url:'worker/profile'}])
</script>
<template><view class="bottom-nav"><view v-for="item in items" :key="item.key" class="nav-item" :class="{selected:active===item.key}" @tap="active!==item.key&&tab('/pages/'+item.url+'/index')"><view class="nav-icon" :class="{chosen:active===item.key}"><AppIcon :name="item.icon" :color="active===item.key?'green':'gray'" :size="24"/></view><text>{{item.label}}</text></view></view></template>
<style scoped>.bottom-nav{position:fixed;bottom:0;left:0;right:0;z-index:30;display:flex;background:rgba(255,255,255,.98);border-top:1px solid #edf1ee;padding:12rpx 16rpx calc(12rpx + env(safe-area-inset-bottom))}.nav-item{flex:1;text-align:center;display:flex;flex-direction:column;align-items:center;gap:4rpx;font-size:22rpx;color:#84928b}.selected{color:#008c53;font-weight:600}.nav-icon{padding:5rpx 28rpx;border-radius:30rpx}.chosen{background:#edf8f1}</style>
