# 上传功能接入说明

真实上传已迁移至职工端“上传体检报告”，独立上传测试页和路由已移除。

上传始终调用真实 POST /api/v1/reports/upload，不受其他页面 Mock 开关影响。每个文件独立生成报告，按顺序上传；同一文件重试沿用幂等键，已成功文件跳过。多页同一报告请先合并为 PDF。后台原始错误与真实 HTTP/report_id/job_id 回执在正式上传页展示。

本地配置优先使用 VITE_REPORT_UPLOAD_BASE_URL、VITE_REPORT_UPLOAD_TOKEN，兼容已有 VITE_UPLOAD_TEST_BASE_URL、VITE_UPLOAD_TEST_TOKEN，因此当前配置无需重新填写。配置只从本地忽略提交的 .env.local 读取；更换凭证后需重新打包。页面不显示凭证。正式页面接入不等同于已部署企业生产服务；当前仍是本地合成环境。

当前后端 multipart 仅支持 file，不支持统一合并多个文件、体检类型/日期/机构字段或复查任务关联。因此移除未真正提交的元数据表单，不调用旧 Mock POST /reports，不跳转至 Mock 详情、不声称已将回执写入模拟档案。OCR进度与真实报告列表仍待后续对接。其他档案和医生功能保留原有演示模式。
