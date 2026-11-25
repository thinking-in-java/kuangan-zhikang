<script setup lang="ts">
import {ref} from 'vue'
import {onLoad} from '@dcloudio/uni-app'
import PageShell from '../../../components/PageShell.vue'
import AppIcon from '../../../components/AppIcon.vue'
import StatePanel from '../../../components/StatePanel.vue'
import {healthApi} from '../../../api/health'
import {usePageData} from '../../../utils/usePageData'
import type {Article} from '../../../types'
const data=ref<Article[]>([]),expanded=ref('')
const {loading,error,load}=usePageData('worker',async()=>{data.value=await healthApi.articles()})
onLoad(q=>expanded.value=q?.id||'')
</script>
<template><PageShell title="职业健康科普"><view class="content"><text class="eyebrow">HEALTH NOTES</text><text class="title" style="display:block;margin:10rpx 0 28rpx">多一分了解，多一分守护</text><StatePanel :loading="loading" :error="error" @retry="load"/><view v-for="a in data" :key="a.id" class="card"><view class="between" @tap="expanded=expanded===a.id?'':a.id"><view class="grow"><text class="tag">{{a.category}}</text><text class="article-heading">{{a.title}}</text><text class="help">{{a.summary}}</text></view><AppIcon :name="expanded===a.id?'chevron-down':'chevron-right'" color="gray" :size="18"/></view><template v-if="expanded===a.id"><view class="divider"/><text class="paragraph">{{a.body}}</text><text class="source">{{a.source}}</text></template></view></view></PageShell></template>
<style scoped>.article-heading{display:block;font-size:31rpx;font-weight:650;margin:20rpx 0 13rpx}</style>
