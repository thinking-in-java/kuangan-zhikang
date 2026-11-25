<script setup lang="ts">
import {onShow,onHide,onUnload,onReachBottom} from '@dcloudio/uni-app'
import PageShell from '../../../components/PageShell.vue'
import AppIcon from '../../../components/AppIcon.vue'
import StatePanel from '../../../components/StatePanel.vue'
import {reportsApi} from '../../../api/reports'
import {errorText} from '../../../utils/format'
import {ApiError} from '../../../api/request'
import {useUserStore} from '../../../stores/user'
import {requireRole,landing,go} from '../../../utils/navigation'
import {useReportList} from '../../../utils/useReportList'
import {beijingReportTime,listReportLink} from '../../../utils/report-list'
import type {ReportTab} from '../../../types/report-list'
const tabs:{key:ReportTab;label:string}[]=[{key:'all',label:'全部报告'},{key:'approved',label:'已审核'},{key:'pending',label:'处理中／待审核'}]
let visible=false
const store=useUserStore()
const {tab,items,counts,total,hasMore,loaded,loading,error,load,select,cancel,retry}=useReportList(async(t,page,size)=>{
 try{return await reportsApi.paginatedList(t,page,size)}catch(e){
  if(e instanceof ApiError&&[403,409].includes(e.status)&&visible){await store.refresh();if(visible&&store.profile&&landing(store.profile)!=='/pages/worker/home/index')uni.reLaunch({url:landing(store.profile)})}
  throw e
 }
})
onShow(async()=>{visible=true;try{if(await requireRole('worker')&&visible)await load()}catch(e){if(visible){loading.value=false;error.value=errorText(e)}}})
function hide(){visible=false;cancel()}
onHide(hide);onUnload(hide);onReachBottom(()=>{if(visible&&!error.value)load(true)})
</script>
<template><PageShell title="我的体检报告"><view class="content">
 <view class="between" style="margin-bottom:25rpx"><view><text class="title">每一年的健康记录</text><view class="subtitle">本人报告 · 上传、审核与归档状态</view></view><text class="more" @tap="load()">刷新</text></view>
 <view class="card"><view class="tabs report-tabs"><view v-for="t in tabs" :key="t.key" :class="{active:tab===t.key}" @tap="select(t.key)"><text>{{t.label}}</text><text class="count">{{loaded?counts[t.key]:'—'}}</text></view></view>
 <text v-if="loaded" class="help list-total">当前分类共 {{total}} 份报告</text>
 <StatePanel :loading="loading&&!items.length" :error="!items.length?error:''" :empty="loaded&&!loading&&!error&&!items.length" text="此分类暂无报告" @retry="retry"/>
 <view v-for="r in items" :key="r.reportId" class="report-list-row" @tap="go(listReportLink(r))">
  <view class="list-heading"><text class="report-title">{{r.title||r.fileName||'体检报告'}}</text><text class="report-state" :class="{approved:r.approved,failed:['failed','rejected','returned','cancelled'].includes(r.status)}">{{r.statusText}}</text></view>
  <text class="report-meta">上传时间：{{beijingReportTime(r.uploadedAt)}}（北京时间）</text>
  <text class="report-meta">体检日期：{{r.examDateVerified||r.examDateRaw||'待核对／未识别'}}</text>
  <text class="report-meta">体检机构：{{r.institutionRaw||'待核对／未识别'}}</text>
  <view class="list-heading"><text class="report-meta">版本 {{r.revision}} · {{r.published?'已归档发布':r.approved?'审核通过，待归档':'尚未审核通过'}}</text><AppIcon name="chevron-right" color="gray" :size="16"/></view>
  <text v-if="r.errorMessage" class="failure-reason">{{r.errorMessage}}</text>
 </view>
 <view v-if="items.length" class="list-footer"><text v-if="error" class="failure-reason">{{error}}</text><button v-if="error" class="btn btn-soft small" @tap="retry">重试加载</button><text v-else-if="loading">正在加载…</text><button v-else-if="hasMore" class="btn btn-soft small" @tap="load(true)">加载更多</button><text v-else>已显示全部 {{total}} 份报告</text></view>
 </view><button class="btn" @tap="go('/pages/worker/upload/index')">上传新的体检报告</button>
 </view></PageShell></template>
<style scoped>.report-tabs>view{display:flex;flex-direction:column;align-items:center;gap:8rpx;font-size:24rpx}.count{font-size:22rpx}.list-total{display:block;margin:20rpx 0}.report-list-row{padding:28rpx 0;border-bottom:1px solid #edf2ef;display:flex;flex-direction:column;gap:14rpx}.list-heading{display:flex;align-items:center;justify-content:space-between;gap:15rpx}.report-title{font-size:28rpx;font-weight:600;word-break:break-all;flex:1}.report-state{font-size:22rpx;background:#f1f4f2;color:#647a6d;padding:8rpx 12rpx;border-radius:12rpx;flex-shrink:0}.approved{background:#e1f5e8;color:#008a53}.failed{background:#fff0e9;color:#ae5637}.report-meta{font-size:23rpx;color:#819288}.list-footer{padding:28rpx 0;display:flex;align-items:center;flex-direction:column;gap:18rpx;color:#8a9b91;font-size:24rpx}.failure-reason{font-size:23rpx;color:#b45d43;word-break:break-all}</style>
