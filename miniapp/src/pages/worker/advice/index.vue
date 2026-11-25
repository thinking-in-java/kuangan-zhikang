<script setup lang="ts">
import {ref} from 'vue'
import PageShell from '../../../components/PageShell.vue'
import AppIcon from '../../../components/AppIcon.vue'
import StatePanel from '../../../components/StatePanel.vue'
import {healthApi} from '../../../api/health'
import {usePageData} from '../../../utils/usePageData'
import {go} from '../../../utils/navigation'
import type {Advice} from '../../../types'
const data=ref<Advice[]>([]),active=ref(0)
const categories=['饮食建议','作息建议','生活习惯','职业防护'],icons=['salad','moon','activity','shield-check']
const {loading,error,load}=usePageData('worker',async()=>{data.value=await healthApi.advice()})
</script>
<template><PageShell title="健康建议"><view class="content"><view class="advice-banner"><text class="eyebrow">A LITTLE CARE, EVERY DAY</text><text class="title">今天，也好好照顾自己</text><text class="subtitle">让健康成为日常，而不只是一份报告</text><AppIcon name="heart-pulse" :size="60"/></view><view class="category-grid"><view v-for="(c,i) in categories" :key="c" :class="{selected:active===i}" @tap="active=i"><AppIcon :name="icons[i]" :size="25"/><text>{{c}}</text></view></view><StatePanel :loading="loading" :error="error" :empty="!loading&&!error&&!data.length" @retry="load"/><view v-for="a in data.filter(a=>a.category===categories[active])" :key="a.id" class="card"><view class="between"><text class="tag">{{a.category}}</text><text class="help">{{a.date}}</text></view><text class="advice-title">{{a.title}}</text><text class="paragraph">{{a.content}}</text><view class="basis"><AppIcon name="clipboard-check" :size="17"/><text>数据依据：{{a.basis}}</text></view><text class="source">知识来源：{{a.source}}</text></view><view class="card followup-entry" @tap="go('/pages/worker/followups/index')"><view class="list-icon"><AppIcon name="calendar-check" :size="28"/></view><view class="grow"><text class="bold">别忘了，您的复查计划</text><view class="help">查看医生安排的复查与随访事项</view></view><AppIcon name="chevron-right" color="gray" :size="18"/></view><text class="help">建议仅用于健康教育。涉及疾病诊断、用药或工作适应性的问题，请咨询职业健康医师。</text></view></PageShell></template>
<style scoped>.advice-banner{padding:22rpx 0 38rpx;position:relative}.advice-banner .title{display:block;font-size:37rpx;margin-top:18rpx}.advice-banner .subtitle{font-size:23rpx}.advice-banner>image{position:absolute;right:0;top:10rpx;opacity:.13}.category-grid{display:flex;gap:12rpx;margin-bottom:27rpx}.category-grid>view{display:flex;flex:1;flex-direction:column;align-items:center;gap:15rpx;background:#fff;border:1px solid #eaf0ec;border-radius:20rpx;padding:25rpx 0;font-size:23rpx;color:#718877}.category-grid>view.selected{background:#e8f6ed;border-color:#81c69b;color:#008d55;font-weight:600}.advice-title{font-size:32rpx;font-weight:650;display:block;margin:25rpx 0 20rpx}.basis{margin-top:30rpx;padding:20rpx;background:#f5f8f5;border-radius:13rpx;display:flex;gap:12rpx;font-size:22rpx;color:#7e9383;align-items:flex-start}.followup-entry{display:flex;align-items:center;gap:20rpx}</style>
