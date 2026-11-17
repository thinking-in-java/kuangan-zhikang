# EHR 集成测试

仅对 `ehr.data-mode=synthetic` 的隔离实例运行，脚本会检查上下文并拒绝 real 模式。默认地址 http://localhost:18081，测试需要可登录的医生账号；当前脚本的登录名为医生医生，密码从环境变量读取。请勿把密码或 OCR 密钥写入仓库。

依赖：Python 3、requests、reportlab、Pillow；浏览器测试另需 websocket-client 和 Windows 上的 Chrome。接口测试和页面测试依次运行，不要并发修改同一组织的测试数据，以免统计恢复断言互相干扰。

```powershell
# 先设置 EHR_TEST_PASSWORD（医生密码）和 EHR_OCR_CALLBACK_TOKEN（隔离实例的回调专用密钥）。
$env:EHR_TEST_URL='http://localhost:18081'
python tests/ehr/test-ehr-api.py
python tests/ehr/test-ehr-mobile-ocr.py
python tests/ehr/test-ehr-pages.py
python tests/ehr/test-ehr-operations.py
```

页面测试经 Chrome DevTools 在实际浏览器点击、填写、上传和提交表单，验证数据库持久化、原值补录、送审、医生确认、归档、个人已发布报告、图表和软删除。它会启动使用独立配置目录的无头 Chrome，并在结束时关闭自己的进程。

测试不连接真实 OCR 提供方，只验证本系统的原件读取和回调契约；真实服务验收需要其地址与请求/响应样例。测试产生的 synthetic 原件、版本和审核记录会保留，职工测试条目使用业务软删除；不向 real 数据写假报告。

## 微信登录与职工绑定

`test-miniapp-wechat-binding.py` 必须在额外配置了本机微信契约服务的 synthetic 实例运行。设置测试用 `EHR_WECHAT_APP_ID` / `EHR_WECHAT_APP_SECRET` 和 `EHR_WECHAT_TEST_URL=http://127.0.0.1:19095/jscode2session` 后启动隔离实例；这些不是实际微信参数，不允许放入 real 进程。

本轮隔离验证为41项微信接口检查、26项原档案接口回归、15项实际浏览器操作（包括生成、重新查看及撤销个人验证码），合计82项。结果分别保存在 `database/miniapp-binding-verification.json`、`database/ehr-api-integration-verification.json`、`database/ehr-page-verification.json`。真实微信登录仍需配置该小程序实际参数并配合其前端验收；本机契约测试不能代替微信真实认证。

未配置真实微信参数时，可运行 `python tests/ehr/test-miniapp-unconfigured.py` 检查正式端点返回明确503及匿名权限，默认地址18080。该脚本不创建微信用户、不改职工档案（登录请求会正常计入限流桶）。

## 绑定前资料更正申请

在同一微信契约隔离实例运行 `python tests/ehr/test-miniapp-corrections.py`，结果为 `database/miniapp-correction-verification.json`。本次新增34项检查：字段白名单、本人确认凭据、过期/撤销、重复和并发幂等、待审不绑定/不删除职工、本人申请查询、后台CSRF、资料版本冲突、实际浏览器通过与驳回，以及审核后重新核验绑定。真实职工资料不参与这些写入验证。

## OCR持久队列与电脑B契约

使用独立synthetic实例，配置测试专用 `EHR_OCR_WORKER_TOKEN`（不少于32字符）；如果同时验证旧回调，配置独立 `EHR_OCR_CALLBACK_TOKEN`。不要把正式工作服务密钥写进测试源码或公开报告。

`python tests/ehr/test-ocr-worker-queue.py` 验证自动入队、下载原字节、心跳进度、幂等、字段/证据校验、身份冲突、失败退避、租约超时回收、有限重试、并发领取、旧租约拦截、人工修改保护和医生审核归档。结果位于 `database/ocr-worker-queue-verification.json`。测试使用合成报告调用A端接口，不执行真实OCR。

设置测试专用worker密钥后，`test-ehr-pages.py`还会模拟服务上报60%并验证页面自动刷新进度，截图保存在工作区 `.tools/runtime/ocr-upload-dialog.png` 和 `ocr-upload-progress.png`。

本轮A端交付验证合计107项：39项Worker队列、26项档案主流程、19项旧小程序/OCR兼容、16项浏览器页面、7项导入导出及边界。另有正式服务只读就绪检查。汇总见 `database/ocr-worker-delivery-verification.json`；真实B端OCR引擎尚待接入。
