# 矿安智康

uni-app / Vue3 / TypeScript 微信小程序，已迁移至网页系统 /ehr/miniapp 接口。

**目前后端微信参数尚未配置，登录会显示不可用。配置 AppID/AppSecret 并重启后，才能做真实微信验收。**

详细的接入范围、测试边界、剩余工作和配置步骤见 [EHR接入记录](docs/EHR-INTEGRATION.md)。

## 运行

```powershell
npm install
npm run type-check
npm test
npm run build:mp-weixin
```

微信开发者工具导入项目根目录（已指向 dist/build/mp-weixin）或直接导入该编译目录，重新编译。开发监听命令 npm run dev:mp-weixin 生成 dist/dev/mp-weixin，使用该目录时请注意与发行编译目录区分。浏览器预览 npm run dev:h5 不能替代真实微信身份验收。

本地 .env.local 设置 VITE_EHR_BASE_URL 与 VITE_ALLOW_HTTP_LOCAL。正式发布只使用HTTPS根地址和真实用户登录会话；不依赖旧固定测试Token。后台原件和健康数据不长期保存到小程序本地。

个人信息确认现为只读核对：真实接口禁止前端覆盖企业身份信息。AI、医生小程序权限、随访等本次未提供接口的功能明确显示未接入，不再输出模拟结果。旧 Mock 服务仅保留供隔离单元测试使用，不进入运行包。
