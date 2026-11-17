# report_kind=unknown 的处理

本次失败任务：`edfd06f6-ae9e-4f96-85b5-14ec4ebf7887`。A端任务为individual，B端回传unknown。unknown是冻结字典允许的值，表示类型无法确认；先前A端把unknown也按明确的类型冲突拒绝，规则过于严格。

修正后的行为：

1. scope_code、data_mode、报告ID、版本、租约、字段与证据等校验继续生效。
2. individual任务收到unknown时允许保存待审核候选；A端上传时指定的档案归属保持原状，B端原始unknown及其原报文保存在回传收据中。
3. 增加REPORT_KIND_UNCERTAIN质量问题，报告详情返回requiresReportKindConfirmation=true，补录窗口显示类型待核实提示。
4. 医生审核时须勾选“确认这是该职工个人体检报告，非机构汇总或多人报告”；API的approve动作须提交reportKindVerified=true。缺少明确确认返回422，批量审核同样检查。
5. 该确认写入审核记录。只有医生确认、随后正式归档的报告才会发布；OCR不会自动完成这些动作。
6. B端明确返回organization或非法类型时仍拒绝，不能把机构汇总归入个人档案。

INVALID_RESULT现保留具体校验原因，供A端页面和B端联调定位。错误说明最多600字符，不保存密钥、租约或整个失败报文。

相关接口详见 [ocr-worker-api.md](ocr-worker-api.md)。回归结果见 `database/ocr-worker-queue-verification.json`，实际任务状态见 `database/ocr-invalid-result-diagnosis.json`。

## 正式任务复核结果

修正后同一正式任务已由B端重新识别并回传成功，A端状态succeeded、100%，当前错误码为空，正式上下文org:10000 / real。

实际接收40个标准字段条目、83个检查项目、123条证据，结论和建议数组均为空。这些计数只表示此次识别回传量，不表示67项标准字段均齐全。姓名身份匹配状态仍为unmatched，报告类型仍为unknown；候选保留IDENTITY_REVIEW_REQUIRED、REPORT_KIND_UNCERTAIN和DOCTOR_REVIEW_REQUIRED提示。

请在“核对报告结果”中对照原件补齐受检者姓名、核定日期、完整结论及必要建议，再提交医生审核；类型不确定时另须明确确认个人报告属性。正式归档和发布尚未执行。

57项OCR调度及业务契约回归检查通过，另有11项正式服务只读就绪检查通过。实时回传核验见 `database/ocr-unknown-kind-live-verification.json`。
