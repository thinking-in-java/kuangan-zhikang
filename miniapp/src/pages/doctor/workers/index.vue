<script setup lang="ts">
import {ref,computed} from 'vue'
import PageShell from '../../../components/PageShell.vue'
import AppIcon from '../../../components/AppIcon.vue'
import StatePanel from '../../../components/StatePanel.vue'
import {doctorApi} from '../../../api/doctor'
import {usePageData} from '../../../utils/usePageData'
import {go} from '../../../utils/navigation'
import type {Profile} from '../../../types'
const data=ref<Profile[]>([]),query=ref('')
const {loading,error,load}=usePageData('doctor',async()=>{data.value=await doctorApi.workers()})
const shown=computed(()=>data.value.filter(w=>w.name.includes(query.value.trim())||w.employeeNo.toLowerCase().includes(query.value.trim().toLowerCase())))
</script>
<template><PageShell title="职工管理" tab="workers" doctor :back="false"><view class="content"><view class="between" style="margin-bottom:28rpx"><view><text class="title">授权职工</text><view class="subtitle">在您的管理范围内，关心每一位职工</view></view><text class="tag">{{data.length}} 人</text></view><view class="search"><AppIcon name="search" color="gray" :size="20"/><input v-model="query" placeholder="搜索姓名 / 工号" maxlength="40"/></view><StatePanel :loading="loading" :error="error" :empty="!loading&&!error&&!shown.length" @retry="load"/><view v-for="w in shown" :key="w.id" class="card worker" @tap="go('/pages/doctor/worker-detail/index?id='+w.id)"><view class="flex gap"><image class="avatar" src="/static/miner.png"/><view class="grow"><text class="worker-name">{{w.name}}</text><text class="help">{{w.employeeNo}} · {{w.age}} 岁</text><view class="help">{{w.department}} · {{w.job}}</view></view><AppIcon name="chevron-right" color="gray" :size="18"/></view><view class="worker-hazards"><text class="help">职业接触</text><text v-for="h in w.hazards" :key="h" class="tag tag-amber">{{h}}</text></view></view><view class="hint">职工与部门授权由后端管理。搜索仅在您已获授权的数据范围内进行。</view></view></PageShell></template>
<style scoped>.worker .avatar{height:88rpx;width:88rpx}.worker-name{display:block;font-size:32rpx;font-weight:650;margin-bottom:8rpx}.worker-hazards{display:flex;align-items:center;gap:14rpx;border-top:1px solid #f0f3ef;margin-top:23rpx;padding-top:20rpx}</style>
