"""Build inspectable architecture artifacts from the exact migration manifest."""
from pathlib import Path
from html import escape
import hashlib
import json
import shutil

BASE=Path(__file__).resolve().parent
DOC=BASE.parent/'docs'
SPEC=json.loads((BASE/'ehr-schema-manifest.json').read_text(encoding='utf-8'))
DOC.mkdir(exist_ok=True)

GROUPS=[
('职工与主档案',['employees','health_records']),
('原件、报告与识别',['report_sources','report_upload_metadata','report_series','health_reports','report_revision_sources','report_evidence','extraction_jobs']),
('结构化体检数据',['health_items','occupational_profiles','health_conclusions','health_advice','followup_tasks']),
('标准与映射字典',['report_template_mapping','medical_item_dictionary','medical_field_mapping','medical_unit_mapping','medical_unit_observations','standard_field_dictionary','dictionary_source_rows']),
('校验、审核与发布',['report_quality_checks','review_records','report_publications','report_sync_outbox']),
('档案分析与审计',['health_risk_assessments','health_risk_report_sources','health_risk_factors','health_record_completeness','health_archive_audit_logs','health_schema_migration'])]

def diagram():
    cards=[
      (40,130,420,180,'01 · 职工身份与主档案',['a_employees → a_health_records','一人一份主档案 · 授权花名册关联','scope_code + data_mode 隔离']),
      (40,380,420,200,'02 · 原件与上传渠道',['a_report_sources','a_report_upload_metadata','PDF / 照片保留摘要及受限存储引用','上传渠道与医学数据来源分开']),
      (550,130,490,180,'03 · 逻辑报告与版本',['a_report_series → a_health_reports','a_report_revision_sources','修改追加新 revision，不覆盖已审核数据']),
      (550,380,490,250,'04 · 结构化结果与证据',['a_health_items · 655 种动态项目','a_occupational_profiles · 13 个职业字段','a_health_conclusions · 医生正式结论','a_health_advice · 医生建议','a_report_evidence · 页码 / 坐标 / 原文']),
      (1130,130,430,180,'05 · 版本化字典',['a_medical_item_dictionary · 655','a_medical_field_mapping · 1,589','a_medical_unit_mapping · 81 原单位键']),
      (1130,380,430,250,'06 · 字典补充与校验',['a_standard_field_dictionary · 67','a_medical_unit_observations · 390','a_dictionary_source_rows · 3,739 原始行','a_report_template_mapping · 暂无模板种子','单位转换批准数为 0']),
      (550,710,490,190,'07 · 校验、医生审核',['a_extraction_jobs · 处理状态','a_report_quality_checks · 身份与质量校验','a_review_records · return / reject / confirm','退回补录后生成新版本，再送审核']),
      (550,980,490,190,'08 · 正式归档与同步',['a_report_publications · 正式发布凭据','a_report_sync_outbox · 事务消息','质量 passed + 医生 confirm + 同一快照摘要','发布和 outbox 必须在同一业务事务写入']),
      (40,710,420,190,'09 · 医生建议后续任务',['a_health_advice → a_followup_tasks','随访任务与报告处理状态分别维护','随访完成不等于报告审核通过']),
      (1130,710,430,230,'10 · 个人档案与企业分析',['a_health_risk_assessments','a_health_risk_report_sources → 已发布版本','a_health_risk_factors → 指标与证据','a_health_record_completeness · 规则快照','正式医学结论 ≠ 平台低 / 中 / 高风险']),
      (1130,1010,430,160,'11 · 运维与审计',['a_health_archive_audit_logs · 脱敏差异','a_health_schema_migration · SQL 版本','旧平台职工表及业务表保持原结构']),
    ]
    a=['<svg xmlns="http://www.w3.org/2000/svg" width="1600" height="1300" viewBox="0 0 1600 1300">',
       '<defs><marker id="arrow" markerWidth="9" markerHeight="9" refX="8" refY="4" orient="auto"><path d="M0 0L8 4L0 8" fill="#14a995"/></marker></defs>',
       '<rect width="1600" height="1300" fill="#f2f8fb"/><g font-family="Microsoft YaHei,Arial,sans-serif">',
       '<text x="45" y="58" fill="#123a4c" font-size="30" font-weight="700">矿安智康 · 电子健康档案数据库架构</text>',
       '<text x="45" y="90" fill="#547083" font-size="16">31 张 a_ 表 = 19 张字典核心表 + 12 张业务支撑表 · EHR v2.0.0 本地物理实现</text>']
    for path in ['M460 220H550','M250 310V380','M460 475H510V265H550','M795 310V380','M1130 220H1040','M1130 505H1040','M795 630V710','M795 900V980','M1040 1070H1080V840H1130','M550 805H460']:
        a.append('<path d="'+path+'" stroke="#14a995" stroke-width="2.5" fill="none" marker-end="url(#arrow)"/>')
    for x,y,w,h,title,lines in cards:
        a.extend([f'<rect x="{x}" y="{y}" width="{w}" height="{h}" rx="16" fill="white" stroke="#c6e0e8"/>',f'<rect x="{x}" y="{y}" width="{w}" height="48" rx="16" fill="#0c6970"/>',f'<text x="{x+18}" y="{y+32}" font-size="20" fill="white" font-weight="700">{escape(title)}</text>'])
        for i,line in enumerate(lines):
            a.append(f'<text x="{x+18}" y="{y+80+i*32}" font-size="16" fill="#315062">{escape(line)}</text>')
    a.append('<text x="45" y="1230" font-size="18" fill="#315062">数据主线：原件 → 提取与证据 → 身份 / 质量校验 → 医生审核 → 正式发布 → 个人档案 / 企业统计</text>')
    a.append('<text x="45" y="1265" font-size="15" fill="#62798a">说明：这是数据层架构；OCR、大模型、审核接口及四个前端页面的数据对接需要后续开发。</text></g></svg>')
    return ''.join(a)

def main():
    svg=diagram()
    (DOC/'ehr-database-architecture.svg').write_text(svg,encoding='utf-8')
    tables=SPEC['tables']; edges=SPEC['foreign_keys']
    mermaid=['erDiagram']
    for e in edges:
        mermaid.append('    '+e['target']+' ||--o{ '+e['table']+' : "'+','.join(e['columns'])+'"')
    (DOC/'ehr-database-er.mmd').write_text('\n'.join(mermaid)+'\n',encoding='utf-8')
    overview='''# 矿安智康电子健康档案数据库架构

本设计依据用户提供的 Excel v2.0.0、TXT 业务分析及上传—提取—审核—归档流程图。所有新增表使用 `a_`；原职工、健康档案及其他旧模块均不迁移、不覆盖。

## 输入与契约边界

- 原始工作簿 SHA-256：`'''+SPEC['source']['sha256']+'''`。副本和十二张表的只读 JSON 导出位于 `database/reference/v2.0.0/`。
- 核对 01 的 67 个标准字段、02 的 655 个检查项目、03 的 1,589 条别名、04 的 390 条项目单位观察，以及 05/07/08/09 的枚举、校验规则、187 个数据库列定义、126 个接口路径。
- 用户提供的工作簿引用了冻结 SQL 和五个完整 JSON Schema，但这些原文件未随附件提供。本次依据 08 重建核心列、类型、可空性、主外键及索引，依据 09 保留路径语义；**不声称已复原未提供的完整 Schema allOf 条件或与冻结 SQL 字节一致**。本地增加的约束和扩展如下。原项目继续使用 Java，并不因工作簿提及 FastAPI 而更换后端。
- 01 的 `nullable=否` 对应 JSON 容器/键要求时，不代表所有报告都必须识别出非空医学值。缺失内容保留 null，不能补零或编造“正常”。

## 关系概览

![数据库架构](ehr-database-architecture.svg)

```mermaid
flowchart TD
  E[a_employees 授权职工身份] --> R[a_health_records 一人一份主档案]
  S[a_report_sources 原始文件] --> L[a_report_series 逻辑报告]
  L --> V[a_health_reports 多版本报告]
  R --> V
  V --> I[a_health_items 动态检查明细]
  V --> O[a_occupational_profiles 职业信息]
  V --> C[a_health_conclusions 医生正式结论]
  V --> A[a_health_advice 医生建议]
  V --> P[a_report_evidence 页码和证据]
  I --> D[a_medical_item_dictionary 655种检查项目]
  V --> Q[a_report_quality_checks 质量与身份校验]
  V --> U[a_review_records 医生审核历史]
  Q --> B[a_report_publications 正式发布凭据]
  U --> B
  B --> X[a_report_sync_outbox 事务消息]
  B --> H[a_health_risk_assessments 平台风险评估]
  H --> F[a_health_risk_factors 风险因素]
  R --> K[a_health_record_completeness 完整度规则快照]
```

完整 45 条物理外键见 [ER Mermaid 源文件](ehr-database-er.mmd)，每张表的列、类型、索引和关系可在 [架构浏览器](ehr-database-architecture.html) 中检索。

## 67 个字段如何落地

| 字段分类 | 数量 | 存储方式 |
|---|---:|---|
| 职工基本信息 | 11 | `a_health_reports.field_values` 保留报告快照；年龄另有 age_raw 镜像 |
| 体检报告信息 | 21 | 报告列与 field_values；原日期保留字符串，核定日期单独保存 |
| 医生正式结论 | 5 | 分项所见/结论保留 field_values；正式结论和建议分别在 conclusions / advice |
| 检查项目明细 | 6 | `a_health_items` 每条检查项目一行，保留原项目名、结果、单位、参考范围、标记和缩写 |
| 原始报告证据 | 11 | evidence 与 field_values；签章标签不是已完成数字签名的声明 |
| 职业健康信息 | 13 | `a_occupational_profiles` 按 field_key 逐项保存；保留报告或授权来源 |

完整逐字段对应关系见 `database/reference/v2.0.0/standard-fields-physical-mapping.csv`。655 种项目是字典行，不能建成 655 个固定结果列；重复字段、多次测量、多版本报告分别保存。

## 12 张支撑表及本地调整

1. `a_report_upload_metadata`：每次上传的渠道、文件名、大小和操作者，支持同原件多次上传记录。五种渠道与医学数据来源分别管理。
2. `a_standard_field_dictionary`：67 个标准字段的版本化元数据，不修改原 JSON 路径。
3. `a_dictionary_source_rows`：保存全部 3,739 条原始字典行及哈希。payload 使用有 JSON_VALID 约束的 LONGTEXT，避免 MySQL JSON 数字规范化改变原始样本覆盖率的小数表示；业务 JSON 仍使用 JSON 列。
4. `a_medical_unit_observations`：390 条项目—原单位观察。04 的 390 行只有 81 个不同 unit_mapping_key；不覆盖、不丢弃重复键。81 行基础单位键暂不绑定某一个项目，390 行观察表保留具体项目。后续获批准的项目级转换须新增版本/专用映射，不能把通用原单位键直接当作批准规则。
5. `a_report_quality_checks`：身份匹配、问题清单、规则版本和报告摘要。身份冲突不能记为通过。
6. `a_report_publications`：明确正式发布事实，要求报告 confirmed、医生决策 confirm、质量 passed，三者报告版本及摘要一致。
7. `a_health_risk_assessments`：平台风险等级及规则版本，与原报告医生正式结论分开。
8. `a_health_risk_report_sources`：风险评估关联同档案的已发布报告版本，避免跨人或混入草稿。
9. `a_health_risk_factors`：风险因素与原始检查值、判定理由、证据关联。
10. `a_health_record_completeness`：规则计算快照。完整度为 completed/required；分母为 0 时 NULL，不默认 100%。所缺材料数须与 missing_items 一致。
11. `a_health_archive_audit_logs`：补充档案细粒度脱敏业务审计，不替换旧平台日志。
12. `a_health_schema_migration`：SQL、来源文件哈希和执行版本。

此外为 health_records 增加 `(scope_code,data_mode,employee_id)` 唯一约束，落实一人一主档案；增加支持发布外键的复合唯一索引、JSON 容器和版本链等 CHECK。标准字段别名的 mapping_version 在本地实现中与 standard_field_dictionary.dictionary_version 同版发布，并有复合外键；今后若需让这两个版本独立演进，应通过显式迁移增加独立字段字典版本引用，不能直接改原版本号。

所有原文件、审核、质量校验及发布凭据仅允许追加；报告及其结果/证据在产生医生审核记录后冻结，退回或修正必须追加下一 revision。冻结由本地触发器保护，不能先写审核记录再补该版本的体检明细。报告镜像字段与 field_values 的一致性、所有 evidence_ids 的有效性及原页范围等仍须由业务校验器核对。

## 审核、退回与正式发布

1. 文件先落受限存储，登记 report_sources / upload_metadata；报告建立 series，识别任务建立 extraction_jobs。
2. 候选版本保存 67 类字段观察、动态指标、结论/建议、职业信息和原页证据，不覆盖企业身份资料。
3. 对同一候选快照执行身份匹配、JSON 校验和 DQ01—DQ13 规则，形成质量记录；失败进入人工补录，不发布。
4. 医生核对原件及快照，追加 review_records。`return` 要求补充后建立新版本；`reject` 保留驳回记录；`confirm` 才能进入发布。
5. **一个数据库事务内**核验 current_revision/CAS 和快照摘要、写医生确认与 reviewed_snapshot_ref、插入 publications 和 outbox。数据库保证发布记录引用确认决策及相同版本/摘要的质量通过记录；服务负责锁、摘要重算、操作者权限和整段事务。
6. 幂等 outbox 消费者更新个人档案投影、完整度/风险快照及统计；消费成功更新 sent。当前 outbox 的 pending/sent 遵循原字典，重试/锁/失败处理需由消费者实现。

`current_revision` 仅为逻辑报告当前候选指针，不能单凭它认定已归档。个人正式档案应以 publications 中每个 report_id 最新已发布 revision 为准；有新草稿时保留上一已发布版本。统计先按员工或逻辑报告去重，不能把多份附件、历史版本当作人数。

四个状态维度分别读取处理任务、报告审核、完整度快照、风险评估。高风险和待审核可以同时存在，不建混合 archive_status。看板“待审核人数”按员工去重；“待审核报告份数”按报告版本计数，标签要明确。完整人数、高风险人数统一使用同一组织、data_mode、时间范围和快照口径。

## 字典初始化与来源保留

| 数据 | 入库数 | 语义 |
|---|---:|---|
| 标准报告字段 | 67 | 来源 01，包含原始元数据 |
| 检查项目 | 655 | 532 lexical_only，123 needs_confirmation；未自动确认 |
| 字段/项目别名 | 1,589 | 522 field，1,067 item；保留机构和多候选，不自动解决同名冲突 |
| 基础原单位键 | 81 | observed_raw_only，无批准转换 |
| 项目—原单位观察 | 390 | 全量保留原单位，批准数 0 |
| 原始字典行 | 3,739 | 12 张工作表逐行快照，包括 536 条待确认记录、规则与来源哈希 |

模板映射原始文件只在 11 中列出了文件名和哈希，未提供实际模板记录，因此 a_report_template_mapping 初始化为空。11 中的外部文件哈希仅作为原文引用，未声称已验证外部文件。字典 validation_ref 为本工作簿行引用，不代表临床批准或原件级验证。

## 对现有项目的衔接

- 旧库 `yh_user_reginfo.user_code`、`yh_org_tree.Org_Code` 等继续提供授权职工和组织资料，使用 roster_employee_key / roster_source_ref 关联；不复制报告姓名去覆盖旧表。
- 老表使用不同字符集和旧业务语义，本次不跨旧表添加强制外键，也不批量建立员工身份映射；新 UUID 与旧 user_code 由后续授权适配器管理。
- 项目原 MySQL Connector/J 8.0.13 读取新表时，实测出现中文结果解码异常；两份源 application-druid.yml 已补充 `characterSetResults=UTF-8`，使用该项目原驱动按新配置读取“姓名”、`kg/m²` 和 655 项字典均通过。源配置修改需在应用下次启动后生效，本次没有重启正在运行的旧模块。参数作用参见 [MySQL 官方会话配置说明](https://dev.mysql.com/doc/connector-j/en/connector-j-connp-props-session.html)。
- 上传目录/对象存储、原件访问鉴权、身份令牌与加密实现、字典批准治理、临床规则及完整度规则须在接口开发时落实。本次不凭空设医学阈值或生产风险分数。
- 时间列约定 UTC；执行器已设置连接会话 `time_zone='+00:00'`。后续 Java 数据源也须配置 UTC 会话和一致的日期序列化，再在页面转换为北京时间。
- 四个页面后续分别对接：智能档案读取已发布个人报告；统计分析读取同口径投影/快照；信息管理处理上传和草稿；审核归档核对证据并执行医生决策。
- **当前前端仍为既有本地演示数据。本次创建数据库结构和字典，不表示 OCR/AI、接口或页面数据联调已完成。**

## 可重复执行与验证

```powershell
python database/build_ehr_schema.py
python database/health_archive_db.py --validate --report database/validation.json
python database/health_archive_db.py --apply --report database/ehr-live-verification.json
python database/generate_ehr_docs.py
```

连接凭据只从项目现有 application-druid.yml 读取，不写进交付文档。执行器先在随机独立库验证，再持命名锁应用到项目库。若发现同名无版本表或校验和冲突则停止，不覆盖已有表。MySQL DDL 不能事务回滚，异常中途留下的 a_ 表需依据执行日志专门处理，不能直接盲目重跑或 DROP 旧表。测试业务记录全部回滚，独立测试库由工具清理。旧通用方案仅在临时库测试过，未应用到项目库；SQL 已移至 database/retracted 并标记 NOT_APPLIED。

验证包括字典与工作簿逐行一致、实际增改查和回滚、组织/模式隔离、唯一主档案、版本链、原件摘要去重、证据版本关联、单位批准门槛、医生确认和质量摘要发布约束、不可修改已审核版本，以及旧表结构指纹比对。

## 表与字段清单

'''
    by={t['name'][2:]:t for t in tables}
    for group,names in GROUPS:
        overview+='### '+group+'\n\n'
        for n in names:
            t=by[n]
            overview+='#### `'+t['name']+'`'+('（核心）' if t['core'] else '（支撑）')+'\n\n'+t['comment']+'。\n\n| 列 | MySQL类型 | 可空 | 说明 |\n|---|---|---|---|\n'
            for c in t['columns']:
                overview+='| '+c['name']+' | '+c['type'].replace('|','\\|')+' | '+('是' if c['nullable'] else '否')+' | '+c['comment']+' |\n'
            overview+='\n索引：'+'；'.join(('PK' if v['primary'] else 'UNIQUE' if v['unique'] else 'INDEX')+'('+', '.join(v['columns'])+')' for v in t['indexes'].values())+'。\n\n'
    live=BASE/'ehr-live-verification.json'
    if live.exists():
        v=json.loads(live.read_text(encoding='utf-8'))
        overview+='## 本次实际执行结果\n\n'+f"- 执行时间：{v['verified_at']}\n- 项目库：{v['connection']['host']}:{v['connection']['port']} / {v['connection']['db']}\n- applied：{v['applied']}；a_ 表：{len(v['tables'])}；触发器：{v['trigger_count']}；旧表：{v['legacy_table_count']}；全库表数：{v['total_table_count']}\n- 旧表结构保持一致：{v['legacy_structure_unchanged']}；验证项目：{len(v['live_checks'])} 项。\n"
        if v.get('recovered_verified_partial_run'):
            overview+='- 首次执行在登记版本时漏传 source_sha256；DDL 与字典已经落库。修复脚本后，按独立库的结构/触发器摘要与逐行字典核对采用恢复登记，未重新创建或删除业务表。期间网络断线后的核对已恢复完成。旧表结构指纹是在恢复登记及最终验证前后比对；首次失败执行的 SQL 也仅作用于本迁移的 a_ 表。\n'
    (DOC/'ehr-database-architecture.md').write_text(overview,encoding='utf-8')
    data=json.dumps(SPEC,ensure_ascii=False).replace('</','<\/')
    html='''<!doctype html><html lang="zh-CN"><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>矿安智康 · 数据库架构</title><style>
*{box-sizing:border-box}body{margin:0;background:#f1f7fa;color:#173f51;font:14px/1.6 system-ui,"Microsoft YaHei",sans-serif}header{padding:28px 40px;background:#073946;color:white}h1{margin:0;font-size:28px}header p{color:#b5d8de;margin:8px 0 0}nav{padding:16px 40px;background:white;display:flex;gap:18px;align-items:center;position:sticky;top:0;z-index:2;border-bottom:1px solid #d7e7ed}nav a{color:#008d83;text-decoration:none}input{padding:11px;border:1px solid #c6dce4;border-radius:8px;width:370px}main{max-width:1650px;margin:auto;padding:28px}.tip{background:#e1f6ef;color:#246a57;padding:16px 22px;border-radius:12px}.diagram{margin:24px 0;background:#f2f8fb;border:1px solid #d9e8ec;border-radius:14px;overflow:auto}.diagram svg{width:100%;height:auto;min-width:850px}.layout{display:grid;grid-template-columns:340px 1fr;gap:20px;align-items:start}.panel{background:white;border:1px solid #d6e5eb;border-radius:14px;padding:20px}.table-button{width:100%;background:none;border:0;text-align:left;padding:9px;color:#345767;cursor:pointer;border-radius:6px;font:inherit}.table-button:hover,.table-button.active{background:#dcf5ee;color:#008d7e}.small{font-size:12px;color:#6b8998}.pill{padding:3px 9px;background:#e4f7f0;border-radius:20px;color:#008d7e}.panel h2{margin-top:0}table{width:100%;border-collapse:collapse}td,th{text-align:left;border-bottom:1px solid #e5eff3;padding:10px;vertical-align:top;word-break:break-word}th{background:#eef7fa}code{font-size:12px}li{margin:8px 0}details{padding:10px 0}.empty{padding:40px}footer{padding:30px;text-align:center;color:#7293a1}@media(max-width:850px){.layout{grid-template-columns:1fr}nav{position:static;flex-wrap:wrap;padding:12px}main{padding:12px}header{padding:24px}input{width:100%}}</style>
<header><h1>矿安智康 · 电子健康档案数据库架构</h1><p>依据 Excel v2.0.0 / TXT / 业务流程图 · 19 张核心表 + 12 张支撑表 · 全部 a_ 前缀</p></header><nav><a href="#overview">架构概览</a><a href="#inspect">表与字段浏览</a><a href="ehr-database-architecture.svg">打开 SVG 图</a><a href="ehr-database-er.mmd">完整 ER 图源文件</a><input id="search" aria-label="搜索表或字段" placeholder="搜索表名、字段名或中文说明…"></nav>
<main><div class="tip">原始报告与授权身份分开；动态项目逐行保存；校验和医生审核通过后正式发布。655 个项目保留原字典状态，单位转换批准数为 0。当前交付为数据层，前端仍是本地演示数据。</div><section class="diagram" id="overview">'''+svg+'''</section><div class="layout" id="inspect"><aside class="panel" id="list"></aside><section class="panel" id="detail"></section></div></main><footer>技术支持：三个臭皮匠 · 数据表关系由本次迁移清单生成</footer><script>
const S='''+data+''';const groups='''+json.dumps(GROUPS,ensure_ascii=False)+''';const esc=v=>String(v??'').replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));let active='a_health_reports';
function show(name){active=name;render();const t=S.tables.find(x=>x.name===name);let edges=S.foreign_keys.filter(e=>e.table===name||e.target===name);document.querySelector('#detail').innerHTML=`<h2>${esc(name)} <span class="pill">${t.core?'字典核心表':'业务支撑表'}</span></h2><p>${esc(t.comment)}</p><p class="small">${t.columns.length} 列 · ${t.checks.length} 个 CHECK · ${edges.length} 条关联</p><table><tr><th>列名</th><th>类型</th><th>可空</th><th>说明</th></tr>${t.columns.map(c=>`<tr><td><b>${esc(c.name)}</b></td><td><code>${esc(c.type)}</code></td><td>${c.nullable?'是':'否'}</td><td>${esc(c.comment)}</td></tr>`).join('')}</table><h3>索引</h3><ul>${Object.values(t.indexes).map(i=>`<li><b>${i.primary?'PRIMARY':i.unique?'UNIQUE':'INDEX'}</b> ${esc(i.name)} <code>(${esc(i.columns.join(', '))})</code></li>`).join('')}</ul><h3>关联关系</h3><ul>${edges.map(e=>`<li><a href="#inspect" onclick="show('${e.table===name?e.target:e.table}');return false">${esc(e.table)} → ${esc(e.target)}</a><br><code>(${esc(e.columns.join(', '))}) → (${esc(e.target_columns.join(', '))})</code></li>`).join('')||'<li>无物理外键关联</li>'}</ul><details><summary>CHECK 条件</summary><ul>${t.checks.map(x=>`<li><code>${esc(x)}</code></li>`).join('')||'<li>无附加 CHECK</li>'}</ul></details><details><summary>本地写入保护触发器</summary><ul>${S.triggers.filter(x=>x.table===name).map(x=>`<li>${esc(x.operation)} · ${esc(x.purpose)}</li>`).join('')||'<li>无本地触发器</li>'}</ul></details>`}
function render(){let q=document.querySelector('#search').value.trim().toLowerCase();document.querySelector('#list').innerHTML=groups.map(([g,ns])=>{let ts=ns.map(n=>S.tables.find(t=>t.name==='a_'+n)).filter(t=>!q||JSON.stringify(t).toLowerCase().includes(q));return ts.length?`<h3>${esc(g)}</h3>${ts.map(t=>`<button class="table-button ${t.name===active?'active':''}" data-name="${t.name}">${esc(t.name)} <span class="small">${t.columns.length}</span></button>`).join('')}`:''}).join('')||'<p class="empty">没有匹配的表或字段</p>';document.querySelectorAll('[data-name]').forEach(b=>b.onclick=()=>show(b.dataset.name))};document.querySelector('#search').oninput=render;show(active);
</script></html>'''
    (DOC/'ehr-database-architecture.html').write_text(html,encoding='utf-8')
    (BASE/'retracted/README.md').write_text('旧通用设计已撤回，未应用到 shayu_3.0。仅在随机独立校验库执行，校验库已删除。当前有效 SQL 为 migrations/V001__a_ehr_v2_0_0.sql。此目录文件仅留作过程追溯，不再执行。\n',encoding='utf-8')
    print('Generated architecture MD, HTML, SVG and full ER Mermaid for',len(tables),'tables')

if __name__=='__main__':
    main()
