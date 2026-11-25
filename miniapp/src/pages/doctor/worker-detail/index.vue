<script setup lang="ts">
import {ref} from 'vue'
import {onLoad} from '@dcloudio/uni-app'
import PageShell from '../../../components/PageShell.vue'
import ReportCard from '../../../components/ReportCard.vue'
import StatePanel from '../../../components/StatePanel.vue'
import {doctorApi} from '../../../api/doctor'
import {usePageData} from '../../../utils/usePageData'
import {go} from '../../../utils/navigation'
import type {Archive} from '../../../types'
const id=ref(''),data=ref<Archive>(),active=ref(0)
onLoad(q=>id.value=q?.id||'')
const {loading,error,load}=usePageData('doctor',async()=>{if(!id.value)throw new Error('缺少职工编号');data.value=await doctorApi.worker(id.value)})
</script>
<template><PageShell title="授权职工档案"><view class="content"><StatePanel :loading="loading" :error="error" @retry="load"/><template v-if="data"><view class="card"><view class="flex gap"><image class="avatar" src="/static/miner.png"/><view><text class="title">{{data.profile.name}}</text><view class="help">{{data.profile.employeeNo}} · {{data.profile.age}} 岁</view><text class="tag">已获授权</text></view></view><view class="divider"/><view v-for="(v,k) in {'所属矿区':data.profile.mine,'部门班组':data.profile.department+' / '+data.profile.team,'岗位工龄':data.profile.job+' · '+data.profile.years+'年','职业接触':data.profile.hazards.join('、')}" :key="k" class="info-line"><text>{{k}}</text><text>{{v}}</text></view></view><view class="card"><view class="tabs"><view :class="{active:active===0}" @tap="active=0">历年体检报告</view><view :class="{active:active===1}" @tap="active=1">随访与复查记录</view></view><template v-if="active===0"><ReportCard v-for="r in data.reports" :key="r.id" :report="r" @select="go('/pages/doctor/review-detail/index?id='+r.id)"/></template><template v-else><view v-for="f in data.followups" :key="f.id" class="row"><view class="grow"><text class="bold">{{f.title}}</text><view class="help">{{f.dueDate}}</view><text class="paragraph">{{f.content}}</text></view><text class="tag">{{f.status==='pending'?'待处理':f.status==='submitted'?'已提交报告':'已完成'}}</text></view><text v-if="!data.followups.length" class="help">暂无随访记录</text></template></view><button class="btn" @tap="go('/pages/doctor/followups/index?workerId='+data.profile.id)">为该职工创建随访任务</button></template></view></PageShell></template>
