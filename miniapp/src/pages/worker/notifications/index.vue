<script setup lang="ts">
import {ref} from 'vue'
import PageShell from '../../../components/PageShell.vue'
import AppIcon from '../../../components/AppIcon.vue'
import StatePanel from '../../../components/StatePanel.vue'
import {healthApi} from '../../../api/health'
import {usePageData} from '../../../utils/usePageData'
import {go} from '../../../utils/navigation'
import type {Notice} from '../../../types'
const data=ref<Notice[]>([])
const {loading,error,load}=usePageData('worker',async()=>{data.value=await healthApi.notifications()})
</script>
<template><PageShell title="消息通知"><view class="content"><StatePanel :loading="loading" :error="error" :empty="!loading&&!error&&!data.length" @retry="load"/><view v-for="n in data" :key="n.id" class="card"><view class="flex gap"><AppIcon name="bell"/><text class="section-title">{{n.title}}</text></view><text class="source" style="display:block">{{n.date}}</text><text class="paragraph" style="display:block;margin-top:16rpx">{{n.content}}</text></view><button class="btn btn-soft" @tap="go('/pages/worker/followups/index')">查看我的复查安排</button></view></PageShell></template>
