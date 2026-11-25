import { defineConfig } from 'vite'
import uni from '@dcloudio/vite-plugin-uni'
const isWeixin = process.env.UNI_PLATFORM === 'mp-weixin'
export default defineConfig({
 plugins: [uni()],
 build: { target: isWeixin ? 'es2018' : 'esnext' },
 // Preserve import.meta.hot for browser dev; lower newer syntax for WeChat.
 esbuild: isWeixin ? {
  target: 'es2018',
  supported: {'nullish-coalescing': false, 'optional-chain': false, 'logical-assignment': false},
 } : undefined,
})
