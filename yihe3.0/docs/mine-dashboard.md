# 矿安智康健康驾驶舱

登录后的默认页面为 `/manage/visualization`，沿用原有 Spring MVC 路由及登录会话。

## 设计参考

- [DataV](https://github.com/DataV-Team/DataV)：三列大屏、装饰边框与数据卡片的视觉参考。
- [iDataV](https://github.com/yyhsong/iDataV)：ECharts 折线、环形与柱形图布局参考。

本页为适配现有 Thymeleaf / Layui 项目的独立实现，图表使用项目已有的本地 ECharts。未引入 Vue 构建链，也没有复制参考仓库的业务数据。

## 数据模式

默认使用前端模拟数据，顶部和底部均明确标注；不会写入数据库或下发任何任务。30 秒刷新只更新展示，不自动变造统计数值。

“切换真实数据”读取当前账号可访问的原有接口：

- `/newRegController/findDoctorAllInfo`：在管人数、年龄、性别、评估等统计。
- `POST /YhUserMakeCard/regInfoByTimeType`：datatype 2 为健康评估记录，8 为健康计划记录，timetype 为 day。
- `/YhHeThresholdAlarm/findThresholdList`：当前账号最新预警。

真实模式采用接口实际统计口径，缺失的职业暴露、复查闭环、AI 随访等指标显示 `—`，不会用模拟数据补齐。接口失败时显示提示；主要统计接口失败时明确返回演示模式。

## 默认主页与容错

`index.html` 在聊天组件初始化之前加载驾驶舱，并关闭旧标签缓存恢复。聊天和消息连接的同步异常单独捕获，避免影响主页与菜单。左侧保留“健康驾驶舱”入口，既有权限菜单及业务路由继续沿用。

大屏支持窗口缩放、手机堆叠布局、减少动态效果偏好、手动刷新、全屏以及预警/建议详情。顶部与底部管理框架统一为深蓝背景。

## 页眉素材

`static/assets/images/mine-dashboard-header.png` 为本项目生成的矿井井架与矿工横幅，保留于项目资源目录。左右各通过 CSS 背景定位展示同一张图片，中间保留标题空间，不拉伸人物和井架。小屏通过渐隐降低装饰图对标题的干扰。

## 主要文件

- `yihe-admin/src/main/resources/templates/visualization/index.html`
- `yihe-admin/src/main/resources/static/assets/css/mine-dashboard.css`
- `yihe-admin/src/main/resources/static/assets/js/mine-dashboard.js`
- `yihe-admin/src/main/resources/templates/index.html`
- `yihe-admin/src/main/resources/static/assets/css/mine-admin.css`
