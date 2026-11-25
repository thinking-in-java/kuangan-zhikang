<script setup lang="ts">
import type {Report} from '../types'
import type {ReportListItem} from '../types/report-list'
import AppIcon from './AppIcon.vue'
import StatusBadge from './StatusBadge.vue'
import {go} from '../utils/navigation'
defineProps<{report?:Report;latestUpload?:ReportListItem;listError?:string}>()
</script>
<template><view class="card health-card"><view class="section-head"><text class="section-title">我的健康档案</text><text class="more" @tap="go('/pages/worker/archive/index')">查看档案 ›</text></view><view class="health-summary"><view class="folder"><AppIcon name="clipboard-plus" :size="38"/></view><view class="grow"><view class="summary-date">最近体检 <text>{{report?.date||'尚无归档报告'}}</text></view><view class="summary-status">报告状态 <StatusBadge v-if="report" :status="report.status"/><text v-else class="muted">{{latestUpload?latestUpload.statusText:'暂无已归档报告'}}</text></view></view><view class="decoration-plus">+</view></view><view v-if="!report||latestUpload?.candidate" class="hint" style="margin-bottom:24rpx">{{latestUpload?'已上传报告：'+latestUpload.statusText+'。':''}}上传和识别成功不等于归档；报告需在网页版医生后台审核并归档后，才进入正式健康档案。<text class="green-text" @tap="go('/pages/worker/reports/index')"> 查看全部报告 ›</text></view><text v-if="listError" class="help">上传报告状态暂未获取，请进入全部报告刷新。</text><button class="btn" @tap="go('/pages/worker/upload/index')"><AppIcon name="cloud-upload" color="white" :size="22"/>上传体检报告</button></view></template>
<style scoped>.health-card{box-shadow:0 10rpx 35rpx #004c4310}.health-summary{display:flex;align-items:center;gap:22rpx;padding:28rpx 0 32rpx}.folder{background:#ebf8ee;border-radius:20rpx;padding:17rpx;display:flex}.summary-date,.summary-status{font-size:25rpx;display:flex;align-items:center;gap:17rpx;color:#657b6e}.summary-date text{color:#233c30;font-weight:550}.summary-status{margin-top:12rpx}.decoration-plus{font-size:90rpx;color:#dcefe1;font-weight:300;margin-right:6rpx}</style>
