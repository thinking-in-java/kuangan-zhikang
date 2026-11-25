<script setup lang="ts">
import {ref,computed} from 'vue'
import PageShell from '../../../components/PageShell.vue'
import AppIcon from '../../../components/AppIcon.vue'
import StatePanel from '../../../components/StatePanel.vue'
import ReportCard from '../../../components/ReportCard.vue'
import {doctorApi} from '../../../api/doctor'
import {usePageData} from '../../../utils/usePageData'
import {go} from '../../../utils/navigation'
import type {Report} from '../../../types'
const data=ref<Report[]>([]),query=ref(''),active=ref(0)
const {loading,error,load}=usePageData('doctor',async()=>{data.value=await doctorApi.reviews()})
const shown=computed(()=>data.value.filter(r=>(active.value===0?r.status==='pending':active.value===1?r.status==='approved':r.status==='returned')&&(r.workerName.includes(query.value.trim())||r.employeeNo.toLowerCase().includes(query.value.trim().toLowerCase()))))
</script>
<template><PageShell title="报告审核" tab="reviews" doctor :back="false"><view class="content"><text class="title">认真核对，安心守护</text><view class="subtitle" style="margin-bottom:20rpx">仅显示您有权审核的报告</view><view class="tabs"><view v-for="(t,i) in ['待审核','已审核','退回修改']" :key="t" :class="{active:active===i}" @tap="active=i">{{t}}<text v-if="i===0" class="tab-count">{{data.filter(r=>r.status==='pending').length}}</text></view></view><view class="search"><AppIcon name="search" color="gray" :size="20"/><input v-model="query" placeholder="搜索职工姓名 / 工号" maxlength="40"/><view v-if="query" @tap="query=''"><AppIcon name="circle-x" color="gray" :size="18"/></view></view><StatePanel :loading="loading" :error="error" :empty="!loading&&!error&&!shown.length" text="暂无符合条件的报告" @retry="load"/><view v-for="r in shown" :key="r.id" class="card" style="padding:0 24rpx"><ReportCard :report="r" doctor @select="go('/pages/doctor/review-detail/index?id='+r.id)"/></view></view></PageShell></template>
<style scoped>.tab-count{font-size:20rpx;background:#e1f3e7;color:#008d55;padding:2rpx 10rpx;border-radius:15rpx;margin-left:9rpx}</style>
