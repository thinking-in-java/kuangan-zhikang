<script setup lang="ts">
import AppIcon from './AppIcon.vue'
defineProps<{loading?:boolean;error?:string;empty?:boolean;text?:string}>()
defineEmits<{retry:[]}>()
</script>
<template><view v-if="loading||error||empty" class="state-panel"><uni-load-more v-if="loading" status="loading"/><template v-else><AppIcon :name="error?'circle-alert':'inbox'" color="gray" :size="40"/><text>{{error||text||'暂无记录'}}</text><button v-if="error" class="btn btn-soft small" @tap="$emit('retry')">重新加载</button></template></view></template>
<style scoped>.state-panel{padding:60rpx 25rpx;display:flex;align-items:center;flex-direction:column;gap:24rpx;color:#7b8b83;text-align:center;font-size:26rpx}</style>
