<script setup lang="ts">
import {ref} from 'vue'
import {onLoad,onShow,onHide,onUnload} from '@dcloudio/uni-app'
import PageShell from '../../../components/PageShell.vue'
import AppIcon from '../../../components/AppIcon.vue'
import OcrProgress from '../../../components/OcrProgress.vue'
import {createReportPoller} from '../../../utils/report-poller'
import StatusBadge from '../../../components/StatusBadge.vue'
import StatePanel from '../../../components/StatePanel.vue'
import {reportsApi} from '../../../api/reports'
import {requireRole,go} from '../../../utils/navigation'
import {previewFile} from '../../../utils/upload'
import {errorText,toast} from '../../../utils/format'
import type {Report} from '../../../types'
const report=ref<Report>(),revision=ref<number>(),rawExpanded=ref(false),id=ref(''),loading=ref(false),error=ref(''),uploaded=ref(false)
const pollingPaused=ref(false)
let visible=false,loadGeneration=0
const poller=createReportPoller(()=>reportsApi.detail(id.value,revision.value),{
 onData(r){report.value=r;loading.value=false;error.value=''},
 onError(e){loading.value=false;error.value=errorText(e)},
 onLimit(){pollingPaused.value=true},
})
async function load(){
 const run=++loadGeneration;poller.stop()
 if(!visible||!id.value)return
 if(!await requireRole('worker')||!visible||run!==loadGeneration)return
 loading.value=true;error.value='';pollingPaused.value=false;poller.start()
}
function hide(){visible=false;loadGeneration++;poller.stop();loading.value=false}
onLoad(q=>{id.value=q?.id||'';revision.value=q?.revision?Number(q.revision):undefined;uploaded.value=q?.uploaded==='1'})
onShow(()=>{visible=true;load()});onHide(hide);onUnload(hide)
</script>
<template><PageShell title="体检报告详情"><view class="content"><view v-if="uploaded" class="hint" style="margin-bottom:24rpx">报告已成功提交，您可在此查看处理进度。</view><StatePanel :loading="loading" :error="error" @retry="load"/><template v-if="report"><view class="card"><view class="between"><AppIcon name="file-text" :size="34"/><StatusBadge :status="report.status"/></view><text class="title" style="display:block;font-size:35rpx;margin-top:24rpx">{{report.title}}</text><text class="help">报告编号 {{report.id}} · 版本 {{report.revision}}</text><view class="divider"/><view class="info-line"><text>体检日期</text><text>{{report.date||'后台未提供'}}</text></view><view class="info-line"><text>体检机构</text><text>{{report.institution||'后台未提供'}}</text></view><view class="info-line"><text>体检类型</text><text>{{report.type||'后台未提供'}}</text></view></view><view class="card"><text class="section-title">报告审核状态</text><view v-if="report.candidate" class="alert" style="margin-top:20rpx">候选报告，尚未正式归档。医生审核归档后才进入正式健康档案。</view><text v-if="report.rawStatus" class="help" style="display:block;margin-top:15rpx">后台业务状态：{{report.rawStatus}}</text></view><view class="card"><OcrProgress :task="report.ocrTask" :job-id="report.jobId"/><view v-if="pollingPaused" class="hint" style="margin-top:20rpx">自动刷新已暂停，可点击下方按钮继续查看。</view><view v-if="error" class="alert" style="margin-top:20rpx">自动刷新失败，请检查网络后重试。</view><button class="btn btn-soft small" style="margin-top:24rpx" @tap="load">刷新识别状态</button></view><view class="card"><text class="section-title">原始报告</text><text v-if="!report.files.length" class="help" style="display:block;margin-top:15rpx">后台尚未返回原件信息</text><view v-for="f in report.files" :key="f.id" class="row" @tap="previewFile(f.url,f.type,report.files.filter(x=>x.type==='image').map(x=>x.url)).catch(e=>toast(errorText(e)))"><AppIcon :name="f.type==='image'?'image':'file-text'"/><text class="row-label">{{f.name}}</text><AppIcon name="external-link" color="gray" :size="17"/></view></view><view class="card"><text class="section-title">体检机构原始结论</text><text class="paragraph" style="display:block;margin-top:20rpx">{{report.conclusion||'后台尚未提供正式结论'}}</text><text class="source">正式结论以原始报告为准，平台不会覆盖机构出具的结论。</text></view><view v-if="report.reviewNote" class="card"><text class="section-title">医生审核意见</text><text class="paragraph" style="display:block">{{report.reviewNote}}</text></view><view v-if="report.status==='approved'" class="card"><text class="section-title">核对后指标</text><view v-for="f in report.fields" :key="f.key" class="info-line"><text>{{f.label}}</text><text>{{f.value}} {{f.unit}}</text></view></view><view class="card"><view class="between" @tap="rawExpanded=!rawExpanded"><text class="section-title">报告详细数据</text><text class="more">{{rawExpanded?'收起':'展开'}} ⌄</text></view><text v-if="rawExpanded" selectable class="raw-data">{{JSON.stringify(report.raw,null,2)}}</text></view></template></view></PageShell></template>
<style scoped>.process{display:flex;justify-content:space-between;padding:32rpx 0}.process>view{display:flex;align-items:center;flex-direction:column;gap:15rpx;font-size:21rpx;color:#a2ada6;flex:1}.step-dot{width:48rpx;height:48rpx;border-radius:50%;background:#eef2ef;display:flex;align-items:center;justify-content:center}.done{color:#008c53!important}.done .step-dot{background:#e0f3e6;color:#008c53}.raw-data{display:block;white-space:pre-wrap;word-break:break-all;font-size:23rpx;line-height:1.8;margin-top:20rpx}</style>
