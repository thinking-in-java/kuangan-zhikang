<script setup lang="ts">
import type {Report} from '../types'
import StatusBadge from './StatusBadge.vue'
import AppIcon from './AppIcon.vue'
defineProps<{report:Report;doctor?:boolean}>()
defineEmits<{select:[report:Report]}>()
</script>
<template><view class="report-row" @tap="$emit('select',report)"><view class="report-icon"><AppIcon name="file-text" :size="25"/></view><view class="report-info"><text class="report-title">{{doctor?report.workerName:report.title}}</text><text class="muted caption">{{doctor?report.employeeNo+' · ':''}}{{report.date}}</text></view><StatusBadge :status="report.status"/><AppIcon name="chevron-right" color="gray" :size="16"/></view></template>
<style scoped>.report-row{display:flex;align-items:center;gap:16rpx;padding:29rpx 0;border-bottom:1px solid #f0f3f1}.report-row:last-child{border-bottom:0}.report-icon{background:#edf8f1;padding:15rpx;border-radius:18rpx;display:flex;align-items:center}.report-info{flex:1;min-width:0;display:flex;flex-direction:column;gap:10rpx}.report-title{font-weight:550;font-size:27rpx}</style>
