<script setup lang="ts">
import {ref,computed} from 'vue'
import PageShell from '../../../components/PageShell.vue'
import AppIcon from '../../../components/AppIcon.vue'
import StatePanel from '../../../components/StatePanel.vue'
import {healthApi} from '../../../api/health'
import {usePageData} from '../../../utils/usePageData'
import {go} from '../../../utils/navigation'
import type {Followup} from '../../../types'
const list=ref<Followup[]>([]),active=ref(0)
const {loading,error,load}=usePageData('worker',async()=>{list.value=await healthApi.followups()})
const shown=computed(()=>list.value.filter(f=>active.value===0?f.status==='pending':f.status!=='pending'))
</script>
<template><PageShell title="复查与随访"><view class="content"><text class="title">健康的事，记在心上</text><view class="subtitle" style="margin-bottom:25rpx">按时复查，让关心持续发生</view><view class="tabs"><view :class="{active:active===0}" @tap="active=0">待复查</view><view :class="{active:active===1}" @tap="active=1">已提交 / 已完成</view></view><StatePanel :loading="loading" :error="error" :empty="!loading&&!error&&!shown.length" text="暂无相关复查计划" @retry="load"/><view v-for="f in shown" :key="f.id" class="card"><view class="between"><view class="flex gap"><AppIcon name="calendar-check"/><text class="section-title">{{f.title}}</text></view><text class="tag" :class="{'tag-amber':f.status==='pending'}">{{f.status==='pending'?'待复查':f.status==='submitted'?'报告已提交':'已完成'}}</text></view><view class="due-date">{{f.dueDate}} <text>建议复查日期</text></view><text class="paragraph">{{f.content}}</text><button v-if="f.status==='pending'" class="btn btn-soft small" style="margin-top:26rpx" @tap="go('/pages/worker/upload/index?followupId='+f.id)">上传复查报告</button><button v-else-if="f.reportId" class="btn btn-soft small" style="margin-top:26rpx" @tap="go('/pages/worker/report-detail/index?id='+f.reportId)">查看已提交报告</button></view></view></PageShell></template>
<style scoped>.due-date{margin:25rpx 0;font-size:36rpx;color:#a68137;font-weight:600}.due-date text{font-size:22rpx;color:#99a59d;font-weight:400;display:block;margin-top:6rpx}</style>
