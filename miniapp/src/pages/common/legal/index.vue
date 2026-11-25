<script setup lang="ts">
import {ref,computed} from 'vue'
import {onLoad} from '@dcloudio/uni-app'
import PageShell from '../../../components/PageShell.vue'
const type=ref('privacy')
onLoad(q=>type.value=q?.type||'privacy')
const title=computed(()=>type.value==='privacy'?'隐私政策与数据授权':type.value==='terms'?'用户服务协议':'关于矿安智康')
const sections=computed(()=>type.value==='privacy'?[
 ['我们处理哪些信息','经您授权，处理身份绑定信息、您主动上传的体检报告、健康档案、复查记录与问答内容，用于职业健康管理服务。'],
 ['谁可以访问','职工只能查询本人档案。医生需经后台审核，且只能查询其获授权管理的职工信息。前端角色选择不会授予医生权限。'],
 ['报告与AI使用','原始文件经统一后端上传、识别与管理。AI对话仅关联您主动选择的一份报告，不默认发送全部健康档案。识别与AI内容需医生核对。'],
 ['本地保存与退出','本地仅缓存有效期内的登录凭证，不长期存储完整报告或对话。退出后清除登录凭证。临时预览文件按微信平台机制清理。'],
 ['授权、撤回与联系','生产上线前，运营方应在此补充真实主体名称、联系方式、信息保存期限、删除与撤回申请渠道、第三方处理方清单，并在微信后台配置隐私保护指引。此处为开发阶段政策模板。']
]:type.value==='terms'?[
 ['服务范围','矿安智康提供职业健康信息管理、报告上传、档案查询和辅助问答服务。'],
 ['身份与权限','请使用本人身份信息。医生与管理人员须由企业后台核验资格并授予访问范围。'],
 ['医学信息边界','OCR与AI不构成正式医学诊断，不决定上岗资格。体检机构的原始结论不会被平台审核意见覆盖。'],
 ['演示说明','演示空间中的人物、报告、指标、AI回答均为模拟数据，不代表任何真实健康情况。正式服务需接入后端并经运营方完善本协议。']
]:[
 ['矿安智康','煤矿职工职业健康管理平台。让每一份辛勤，都有健康守护。'],
 ['版本信息','v1.0.0 · 微信小程序前端。基于 uni-app、Vue 3、TypeScript 与 Pinia。'],
 ['服务支持','正式服务联系方式由运营方上线前配置。当前为开发演示版本。']])
</script>
<template><PageShell :title="title"><view class="content"><view class="card"><text class="title" style="font-size:36rpx">{{title}}</text><text class="source" style="display:block;margin-bottom:30rpx">开发模板 · 版本 2026-11-v1</text><view v-for="(s,i) in sections" :key="s[0]" style="margin-bottom:36rpx"><text class="section-title">{{i+1}}. {{s[0]}}</text><text class="paragraph" style="display:block;margin-top:15rpx">{{s[1]}}</text></view></view></view></PageShell></template>
