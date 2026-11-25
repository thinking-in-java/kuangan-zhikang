<script setup lang="ts">
import {statuses} from '../utils/format'
import type {ReportStatus} from '../types'
defineProps<{status:ReportStatus}>()
</script>
<template><text class="status" :class="statuses[status]?.tone">{{statuses[status]?.text||status}}</text></template>
<style scoped>.status{font-size:21rpx;display:inline-flex;align-items:center;white-space:nowrap;border-radius:10rpx;padding:6rpx 13rpx}.green{color:#008751;background:#e5f5eb}.amber{color:#bd7d16;background:#fff3dc}.red{color:#c25c4a;background:#fff0ec}.blue{color:#408aac;background:#eaf6fb}</style>
