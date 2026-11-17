# B端完整JSON回传对接（2.0.0）

A端：`http://192.168.1.105:18080`。继续使用现有Worker密钥、领取接口及X-OCR-Lease。

本次样例有68条字段记录（16种field_key）、102个项目、458条证据、1条结论、7条建议。所有字段键和项目编码均在当前2.0.0字典内。68条记录不等于68个不同标准字段，重复的日期等字段按原记录保留。

## 领取后的输入

读取task.inputPath，input现在额外包含：

```json
{
  "scope_code":"org:10000","data_mode":"real",
  "report_id":"A端报告UUID","revision":1,"prior_revision":null,
  "record_id":"A端档案UUID","dictionary_version":"2.0.0",
  "source_files":[{"source_file_id":"A端原件UUID","sha256":"64位哈希","page_count":12}]
}
```

files保留原有downloadPath并增加sha256。下载继续携带有效租约。

## 独立导出与任务绑定

样例中的scope_code=DEMO_ONLY、报告UUID、revision=2和原件UUID属于B端离线环境，不能直接回传正式任务。生成业务结果时必须取当前A端input：

- 顶层和items/conclusions/advice内的scope_code、data_mode、report_id、revision全部使用同一任务上下文。
- record_id、prior_revision取A端输入；B端不能自行增加A端版本或改绑职工。
- source_file_id按SHA256和页数匹配到A端原件，并同步修改每条evidence.source_file_id。
- evidence_id、item_id、conclusion_id、advice_id保留B端UUID；items.ordinal保留正整数序号，不得重复。
- 原始医学值、单位、参考范围、原文及坐标不随上下文绑定改写。
- report_kind保留B的实际判断；unknown进入待核实候选，organization仍不能写入个人报告任务。

可直接复制到B端使用的纯函数：[bind_ocr_result.py](examples/bind_ocr_result.py)。它不访问数据库、不发网络请求、不写文件，只核对文件SHA256/页数并绑定上下文，且不修改原导出对象。

```python
from bind_ocr_result import bind_to_task
payload = bind_to_task(extracted_report_json, task_input)
# headers包含现有Authorization及本次X-OCR-Lease
response = requests.post(base_url + task['resultPath'], headers=headers, json=payload, timeout=60)
response.raise_for_status()
```

task_input必须由本次有效租约获取。不同文件哈希或页数不匹配时函数会拒绝，A端也独立复核。

## A端接收

`POST /ehr/worker/tasks/{jobId}/result`，body直接为完整2.0.0对象，不额外包data/result。

A兼容完整source_files、evidence、field_values、items、conclusions、advice；复核原件UUID/哈希/页数、顶层及嵌套上下文、非空record_id，保留B端ID和序号。为兼容旧调用，省略source_files仍支持；新B端格式应携带。

advice.author_ref/review_id应为null，不能由OCR冒充医生审核。归一化、单位换算和正式分类仍须获准规则，本次样例这些字段均为null。A统一保存为needs_review，日期、身份、类型、正式结论及发布仍需人工核验。

同任务相同JSON回传幂等，不同内容不能覆盖已接收结果；需要更新时，后台重新识别或建立修订版本并获取新任务。

## 验证

本次使用同一PDF及完整JSON在synthetic隔离实例回放，未覆盖正式档案。结果见 `database/b-ocr-replay-verification.json`：数据库证据、项目、结论与建议ID、序号、原始值及完整收据均做核对；错误上下文、文件哈希及重复ID拒绝后验证整批回滚。

该离线文件带DEMO_ONLY，本次没有直接作为正式报告覆盖导入。实际业务由B端领取正式任务后按上述上下文回传。
