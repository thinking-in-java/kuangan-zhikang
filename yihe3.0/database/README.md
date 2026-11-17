# 矿安智康 EHR 数据库交付

当前有效迁移：V001、V002、V003、V004、V005、V006。共44张表、449个物理字段，所有新表使用 `a_` 前缀。旧通用方案位于 `retracted/`，未应用到项目数据库，不再执行。

- [当前44张表、449个字段完整说明](../docs/ehr-data-dictionary.html)：逐字段中文解释、类型、默认值、索引、外键、完整DDL、CHECK及触发器。
- [当前业务架构](../docs/ehr-business-architecture.md) / [架构SVG](../docs/ehr-business-architecture.svg)。
- [已实现业务接口及小程序/OCR接入](../docs/ehr-api.md)。
- [可重复的隔离集成测试](../tests/ehr/README.md)。

以下31张表的架构文件对应原始V001快照；最新结构以以上44张表字典为准。V004另外增加6张微信用户、会话、验证码、确认凭据、绑定与限流表，见 [微信绑定接口](../docs/miniapp-wechat-binding-api.md)。

- [架构浏览器](../docs/ehr-database-architecture.html)：可搜索 31 张表和字段，查看全部索引、外键和保护触发器。
- [架构图 SVG](../docs/ehr-database-architecture.svg)
- [详细设计及字段清单](../docs/ehr-database-architecture.md)
- [完整 ER 图](../docs/ehr-database-er.mmd)
- [67 个字段的物理映射 CSV](reference/v2.0.0/standard-fields-physical-mapping.csv)
- [实际执行验证报告](ehr-live-verification.json)

`reference/v2.0.0/` 保存用户原始 Excel 副本、TXT 参考、12 张工作表的只读 JSON 导出和输入哈希。原 Excel 未被修改。没有把样本报告的字段覆盖率当成业务完整度，也没有批准任何单位转换。

执行器 `health_archive_db.py` 从项目现有数据源配置读取连接信息；先在独立临时库验证，再以命名锁应用到项目库。验证业务数据会回滚；仅字典初始化数据和迁移记录永久保留。已有版本相同可复验，结构或校验和冲突会停止。

```powershell
python database/build_ehr_schema.py
python database/health_archive_db.py --validate --report database/validation.json
python database/health_archive_db.py --apply --report database/ehr-live-verification.json
python database/generate_ehr_docs.py
```

MySQL 客户端手动运行 SQL 时，应选择目标库，并使用 UTF-8 与 UTC 会话。SQL 内包含 `DELIMITER` 和本地触发器；不可把文件按所有分号直接切分。推荐使用执行器保留校验和与验证报告。

当前健康档案页面已接入真实数据库、上传、候选补录、医生审核与统计接口。OCR通过外部API接入；微信真实参数尚待配置，身份绑定已通过隔离环境契约联调。完整的外部冻结 SQL / 五份 JSON Schema 未随附件提供，当前实现的依据与本地补充项在详细设计中逐项说明。

V005新增 `a_miniapp_correction_requests`，用于绑定前职工资料更正申请及医生审核；[接口文档](../docs/miniapp-correction-api.md)。

V006新增 `a_ocr_dispatch_tasks` 和 `a_ocr_dispatch_attempts`；[A端上传和OCR工作服务接口](../docs/ocr-worker-api.md)。真实原件根目录为F:\upload，B通过受保护HTTP下载，不直接连接数据库。
