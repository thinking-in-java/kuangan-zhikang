<script setup lang="ts">
import {nextTick,onMounted,onBeforeUnmount,getCurrentInstance,watch,ref} from 'vue'
import * as echarts from 'echarts/core'
import {LineChart} from 'echarts/charts'
import {GridComponent} from 'echarts/components'
import {CanvasRenderer} from 'echarts/renderers'
import type {Trend} from '../types'
echarts.use([LineChart,GridComponent,CanvasRenderer])
const props=defineProps<{trend:Trend}>()
const instance=getCurrentInstance()
const canvasId='trend'+Math.random().toString(36).slice(2,8)
let chart:ReturnType<typeof echarts.init>|undefined
const failed=ref(false)
function option(){return {animation:false,grid:{top:24,bottom:30,left:42,right:20},xAxis:{type:'category',boundaryGap:false,data:props.trend.dates,axisLine:{lineStyle:{color:'#e3ebe6'}},axisTick:{show:false},axisLabel:{color:'#819187',fontSize:11}},yAxis:{type:'value',scale:true,splitNumber:3,axisLabel:{color:'#819187',fontSize:10},splitLine:{lineStyle:{color:'#edf2ef'}}},series:[{type:'line',data:props.trend.values,smooth:.25,symbolSize:7,itemStyle:{color:'#009858',borderColor:'#fff',borderWidth:2},lineStyle:{width:3,color:'#009858'},areaStyle:{color:'#d8f0e1',opacity:.65},label:{show:true,color:'#00844e',fontSize:11,position:'top'}}]}}
async function draw(){
 await nextTick()
 try{
 // #ifdef H5
 const el=document.getElementById(canvasId)?.querySelector('canvas')||document.getElementById(canvasId)
 if(el){const rect=document.getElementById(canvasId)!.getBoundingClientRect();chart?.dispose();chart=echarts.init(el as HTMLCanvasElement,undefined,{width:rect.width,height:rect.height,devicePixelRatio:window.devicePixelRatio||1});chart.setOption(option())}
 // #endif
 // #ifdef MP-WEIXIN
 uni.createSelectorQuery().in(instance?.proxy).select('#'+canvasId).fields({node:true,size:true},(res:any)=>{
  if(!res?.node){failed.value=true;return}
  const canvas=res.node;const dpr=uni.getSystemInfoSync().pixelRatio||2
  canvas.width=res.width*dpr;canvas.height=res.height*dpr
  echarts.setPlatformAPI({createCanvas:()=>canvas})
  chart?.dispose();chart=echarts.init(canvas,undefined,{width:res.width,height:res.height,devicePixelRatio:dpr});chart.setOption(option())
 }).exec()
 // #endif
 }catch{failed.value=true}
}
onMounted(draw);watch(()=>props.trend,()=>{if(chart)chart.setOption(option(),true);else draw()});onBeforeUnmount(()=>chart?.dispose())
</script>
<template><view><canvas v-if="!failed" :id="canvasId" type="2d" class="trend-canvas"/><view v-else class="trend-fallback"><view v-for="(date,i) in trend.dates" :key="date"><text>{{trend.values[i]}} {{trend.unit}}</text><text class="muted">{{date}}</text></view></view><text class="chart-note">历史数据仅供参考，具体解读请咨询职业健康医师</text></view></template>
<style scoped>.trend-canvas{width:100%;height:340rpx}.chart-note{display:block;font-size:20rpx;color:#94a299;text-align:center;padding:8rpx 0}.trend-fallback{display:flex;justify-content:space-around;padding:50rpx 0}.trend-fallback>view{display:flex;flex-direction:column;gap:15rpx;font-size:24rpx;color:#008f56}</style>
