# 报告上传中心：上传、OCR 与职工匹配

入口：`/assets/report-center/index.html`，沿用现有平台菜单和登录权限。

## 处理流程

- 单份、批量选择或拖入 PDF/JPEG/PNG 后打开上传匹配窗口，每份不超过 20 MB，每批不超过 50 份。浏览器最多同时保存 4 份原件，OCR 继续使用原有持久队列与服务器配置的并发上限（当前部署为 8 份）。
- 新接口 `POST /ehr/api/report-center/uploads` 接收 `file`、`channel`，要求现有 CSRF 和 UUID `Idempotency-Key`。先保存 `record_id=NULL` 的报告与原件，并写入 `report_intake_uploaded` 审计记录；不创建虚拟职工或修改花名册。
- 未匹配报告仅在所属单位、相同数据域的后台可读；个人端无法读取。原件下载、队列领取、OCR 回调均复用原有接口和数据契约。已移除报告不再属于可访问的待匹配队列。
- OCR 完成后，通过现有 `/report-center/matching/{id}/{revision}` 获取姓名、工号等字段的规则候选。界面显示的是规则得分，不伪装成模型置信度。
- 保存草稿只保存选择；确认匹配须核验身份，有冲突时须补充核验说明。已绑定报告改绑继续要求医生权限和明确确认。职业接触证据随确认关联到目标职工。
- 确认后进入现有 OCR 核对、医生审核、归档流程，禁止直接自动审核或发布。未匹配报告在 OCR 工作台点击提交时先进入匹配页面。
- 关闭窗口不会取消已保存的任务。在报告列表点击“上传处理详情 / 匹配职工”可恢复。失败任务支持原队列重试；原件与错误信息不会被丢弃。

档案管理中的拍照上传、小程序同步、新增档案、资料更正、批量导入、识别任务与进度等入口由上传中心工具栏调用原业务组件。原页面保留，便于已有业务继续使用。

## 验证

`tests/ehr/java/ReportIntakeChecks.java` 在 `shayu_3_preview` 执行真实 SQL 与 OCR 回调契约测试；测试数据全部事务回滚，测试文件随回滚清理，不调用外部模型。

`tests/ehr/test-report-upload-wizard.cjs` 使用 Playwright，需设置 `EHR_TEST_PASSWORD`。可选配置：`EHR_TEST_BASE`、`PLAYWRIGHT_MODULE`、`EHR_CHROME`、`EHR_TEST_OUTPUT`。浏览器测试拦截全部业务写请求，以夹具验证自动上传、草稿、身份确认、重试、恢复、批量与不同分辨率；不向真实档案写入测试报告。
