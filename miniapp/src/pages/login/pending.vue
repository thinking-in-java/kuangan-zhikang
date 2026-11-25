<script setup lang="ts">
import {ref} from 'vue'
import PageShell from '../../components/PageShell.vue'
import AppIcon from '../../components/AppIcon.vue'
import {useUserStore} from '../../stores/user'
import {onShow} from '@dcloudio/uni-app'
import {landing} from '../../utils/navigation'
import {errorText,toast} from '../../utils/format'
const store=useUserStore(),busy=ref(false)
onShow(async()=>{await store.restore();if(!store.profile){uni.reLaunch({url:'/pages/login/index'});return}if(landing(store.profile)!=='/pages/login/pending')uni.reLaunch({url:landing(store.profile)})})
async function refresh(){busy.value=true;try{await store.refresh();if(store.profile){const target=landing(store.profile);if(target!=='/pages/login/pending')uni.reLaunch({url:target});else toast('资料仍在审核中，请稍后查询')}else uni.reLaunch({url:'/pages/login/index'})}catch(e){toast(errorText(e))}finally{busy.value=false}}
</script>
<template><PageShell title="职工资料审核" :back="false"><view class="content"><view class="card pending"><AppIcon name="shield-check" :size="65"/><text class="title">您的资料更正正在审核中</text><text class="paragraph">企业管理员正在核对您的职工资料。审核通过并完成身份绑定后，才能进入个人健康档案。</text><view class="hint">审核结果以企业后台返回为准，当前无法访问个人健康数据。</view><button class="btn" :loading="busy" :disabled="busy" @tap="refresh">刷新审核状态</button><button class="btn btn-soft" @tap="store.logout">退出登录</button></view></view></PageShell></template>
<style scoped>.pending{padding:70rpx 35rpx;display:flex;align-items:center;flex-direction:column;gap:36rpx;text-align:center}.pending .title{font-size:36rpx}.pending button{width:100%}</style>
