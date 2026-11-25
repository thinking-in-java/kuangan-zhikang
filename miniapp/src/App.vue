<script setup lang="ts">
import { onLaunch } from '@dcloudio/uni-app'
import { useUserStore } from './stores/user'
onLaunch(() => { void useUserStore().restore() })
</script>
<style lang="scss">
@import './styles/theme.scss';
</style>
