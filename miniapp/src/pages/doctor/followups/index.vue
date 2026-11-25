<script setup lang="ts">
import {ref} from 'vue'
import {onLoad} from '@dcloudio/uni-app'
import PageShell from '../../../components/PageShell.vue'
import StatePanel from '../../../components/StatePanel.vue'
import {doctorApi} from '../../../api/doctor'
import {usePageData} from '../../../utils/usePageData'
import {today,toast,errorText} from '../../../utils/format'
import type {Followup,Profile} from '../../../types'
const data=ref<Followup[]>([]),workers=ref<Profile[]>([]),workerId=ref(''),title=ref(''),dueDate=ref(today()),content=ref(''),formOpen=ref(false),busy=ref(false)
onLoad(q=>{if(q?.workerId){workerId.value=q.workerId;formOpen.value=true}})
const {loading,error,load}=usePageData('doctor',async()=>{[data.value,workers.value]=await Promise.all([doctorApi.followups(),doctorApi.workers()])})
async function submit(){if(!workerId.value||!title.value.trim()||!content.value.trim()){toast('请完整填写随访信息');return}if(dueDate.value<today()){toast('随访日期不能早于今天');return}busy.value=true;try{await doctorApi.createFollowup({workerId:workerId.value,title:title.value.trim(),dueDate:dueDate.value,content:content.value.trim()});formOpen.value=false;title.value='';content.value='';await load();toast('随访任务已创建')}catch(e){toast(errorText(e))}finally{busy.value=false}}
</script>
<template><PageShell title="随访任务管理"><view class="content"><view class="between" style="margin-bottom:25rpx"><text class="title">持续关注每一份健康</text><text class="tag" @tap="formOpen=!formOpen">{{formOpen?'收起':'＋ 新建'}}</text></view><view v-if="formOpen" class="card"><text class="section-title">创建随访任务</text><text class="label" style="margin-top:25rpx">选择授权职工</text><picker :range="workers.map(w=>w.name+' · '+w.employeeNo)" @change="workerId=workers[Number(($event as any).detail.value)].id"><view class="field">{{workers.find(w=>w.id===workerId)?.name||'请选择职工'}} <text class="more">⌄</text></view></picker><text class="label">任务名称</text><input v-model="title" class="field" maxlength="60" placeholder="例如：年度职业健康随访"/><text class="label">计划日期</text><picker mode="date" :value="dueDate" :start="today()" @change="dueDate=($event as any).detail.value"><view class="field">{{dueDate}}</view></picker><text class="label">随访内容 / 复查要求</text><textarea v-model="content" class="field" :maxlength="1000" placeholder="请填写联系事项与复查要求" style="height:220rpx"/><button class="btn" :loading="busy" :disabled="busy" @tap="submit">创建随访任务</button></view><StatePanel :loading="loading" :error="error" :empty="!loading&&!error&&!data.length" @retry="load"/><view v-for="f in data" :key="f.id" class="card"><view class="between"><text class="section-title">{{f.title}}</text><text class="tag" :class="{'tag-amber':f.status==='pending'}">{{f.status==='pending'?'待处理':f.status==='submitted'?'已提交报告':'已完成'}}</text></view><view class="help" style="margin:15rpx 0">{{f.workerName}} · 计划日期 {{f.dueDate}}</view><text class="paragraph">{{f.content}}</text></view></view></PageShell></template>
