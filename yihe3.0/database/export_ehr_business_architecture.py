"""Render the current business table groups from a freshly exported live catalog."""
from pathlib import Path
import json
from html import escape
B=Path(__file__).resolve().parent;D=B.parent/'docs'
c=json.loads((B/'ehr-live-schema-catalog.json').read_text(encoding='utf-8'))
groups={
'身份与档案':['employees','employee_roster_profiles','health_records','health_record_completeness'],
'原件与多版本报告':['report_sources','report_upload_metadata','report_series','health_reports','report_revision_sources'],
'报告内容与证据':['report_evidence','health_items','occupational_profiles','health_conclusions','health_advice'],
'识别、质量与审核':['extraction_jobs','ocr_dispatch_tasks','ocr_dispatch_attempts','ehr_ocr_receipts','report_quality_checks','review_records','report_review_snapshots'],
'归档与后续服务':['report_publications','report_sync_outbox','health_risk_assessments','health_risk_report_sources','health_risk_factors','followup_tasks'],
'字典、映射及溯源':['standard_field_dictionary','medical_item_dictionary','medical_field_mapping','report_template_mapping','medical_unit_mapping','medical_unit_observations','dictionary_source_rows'],
'微信登录与确认绑定':['miniapp_users','miniapp_sessions','employee_binding_codes','miniapp_binding_challenges','miniapp_employee_bindings','miniapp_rate_limits','miniapp_correction_requests'],
'鉴权、操作及迁移':['ehr_mobile_sessions','health_archive_audit_logs','health_schema_migration']}
assert sorted('a_'+n for a in groups.values() for n in a)==sorted(t['TABLE_NAME'] for t in c['tables'])
tables={t['TABLE_NAME']:t for t in c['tables']}
md='''# 健康档案当前业务架构（V001 + V002 + V003 + V004 + V005 + V006）

当前实际库共 **44 张 a_ 表、449 个物理字段**。原31张表保留，业务接入先补充4张支撑表，微信登录和绑定另补充6张独立表，V005增加1张绑定前资料更正申请表，V006增加2张OCR持久调度表。

完整字段、类型、默认值、全部DDL、CHECK、索引、复合外键及写保护触发器见 [逐字段字典](ehr-data-dictionary.html)。接口见 [API文档](ehr-api.md)。原V001架构文件作为初始迁移记录保留，下面是当前运行结构。

```mermaid
flowchart TD
 E[a_employees 授权职工索引] --> P[a_employee_roster_profiles 授权资料]
 E --> R[a_health_records 个人主档案]
 R --> S[a_report_series 逻辑报告]
 F[a_report_sources 原始文件] --> V[a_report_revision_sources 版本与原件]
 U[a_report_upload_metadata 上传来源与幂等] --> F
 S --> H[a_health_reports 多版本候选]
 V --> H
 H --> I[a_health_items 动态检查明细]
 H --> EV[a_report_evidence 原页证据]
 I --> D[a_medical_item_dictionary 655项字典]
 H --> OCC[a_occupational_profiles 职业字段原文]
 H --> CO[a_health_conclusions 完整医生结论]
 H --> AD[a_health_advice 原报告建议]
 H --> J[a_extraction_jobs 外部OCR任务]
 J --> DQ[a_ocr_dispatch_tasks 持久队列与租约]
 DQ --> AT[a_ocr_dispatch_attempts 每次处理历史]
 DQ --> B[电脑B工作服务 HTTP领取与回传]
 B --> J
 J --> RC[a_ehr_ocr_receipts 不可变原始回调]
 H --> Q[a_report_quality_checks 身份与质量校验]
 Q --> RE[a_review_records 医生决策]
 RE --> SN[a_report_review_snapshots 不可变内容快照]
 RE --> PUB[a_report_publications 正式发布]
 PUB --> OB[a_report_sync_outbox 同步事件]
 PUB --> VIEW[智能档案 / 统计页面实时查询]
 R --> C[a_health_record_completeness 9项资料完整度]
 R --> RA[a_health_risk_assessments 独立已确认风险]
 RA --> RF[a_health_risk_factors 风险因素]
 H --> RS[a_health_risk_report_sources 评估依据]
 RS --> RA
 WX[微信 code2Session] --> MU[a_miniapp_users 独立微信用户]
 MU --> MS[a_miniapp_sessions 两小时会话]
 R --> BC[a_employee_binding_codes 每人一次性验证码]
 BC --> CH[a_miniapp_binding_challenges 脱敏资料确认凭据]
 MU --> CH
 CH --> CR[a_miniapp_correction_requests 资料更正与人工审核]
 CR --> P
 CR -.重新核对.-> CH
 CH --> MB[a_miniapp_employee_bindings 确认后的唯一绑定]
 MB --> R
 RL[a_miniapp_rate_limits 独立事务限流] -.-> CH
```

## 运行边界

- 身份、报告候选、审核事实和正式发布分别保存；临时草稿不等于个人正式档案。
- 同一逻辑报告可以同时保留已发布 V1 和待修订 V2；所有明细与证据按 report_id + revision 关联。
- 文件存储与数据库分开：MySQL保存受限引用、哈希和元信息；PDF/照片字节在私有目录，下载经过账号或OCR任务鉴权。
- OCR提供方只写候选，不能写正式分类、归档事实或自动生成风险。人工确认是独立动作。
- 归档与outbox在同事务提交；本轮页面直接读取发布事实，后续外部消费者需单独实现。
- 所有新表均以a_开头，不重建旧业务表。scope_code与data_mode作为数据边界。

## 全部表分组

'''
svg=['<svg xmlns="http://www.w3.org/2000/svg" width="1800" height="1040" viewBox="0 0 1800 1040"><style>text{font-family:Microsoft YaHei,Arial,sans-serif}.title{font-size:29px;font-weight:700;fill:#07495a}.sub{font-size:15px;fill:#54798b}.group{font-size:21px;font-weight:700;fill:#006c6d}.table{font-size:17px;fill:#275768}</style><rect width="1800" height="1040" rx="20" fill="#edf8fa"/><text x="45" y="54" class="title">矿安智康 · 健康档案当前数据架构</text><text x="45" y="88" class="sub">44 张 a_ 表 · 字段见逐字段字典 · V001 + V002 + V003 + V004 + V005 + V006 · 点击表名查看完整字段</text>']
for i,(title,ns) in enumerate(groups.items()):
 md+='### '+title+'\n\n'+'\n'.join('- `a_'+n+'`：'+tables['a_'+n]['TABLE_COMMENT'] for n in ns)+'\n\n'
 x=40+(i%3)*590;y=120+(i//3)*290;svg.append(f'<rect x="{x}" y="{y}" width="565" height="270" rx="16" fill="white" stroke="#acdbe3"/><text x="{x+22}" y="{y+38}" class="group">{i+1}. {escape(title)}</text>')
 for j,n in enumerate(ns):svg.append(f'<a href="ehr-data-dictionary.html#a_{n}"><text x="{x+22}" y="{y+73+j*27}" class="table">a_{n}</text></a>')
svg.append('<text x="45" y="1020" class="sub">授权身份 → 原件 → 多版本报告/证据 → 外部识别候选 → 医生审核 → 正式归档 → 个人展示与企业统计</text></svg>')
(D/'ehr-business-architecture.svg').write_text(''.join(svg),encoding='utf-8')
(D/'ehr-business-architecture.md').write_text(md,encoding='utf-8')
print('Exported current architecture:',len(tables),'tables')
