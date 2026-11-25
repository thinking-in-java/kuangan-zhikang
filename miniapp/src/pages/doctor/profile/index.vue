<script setup lang="ts">
import ProfilePanel from '../../../components/ProfilePanel.vue'
import {usePageData} from '../../../utils/usePageData'
usePageData('doctor',async()=>{})
</script>
<template><ProfilePanel :doctor="true"/></template>
