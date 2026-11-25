# 服务知识库（第四页）

入口：沟通与服务中心 → 服务知识库，页面 `/manage/communication/knowledge`。原聊天管理、自动回复规则与既有健康档案保留。

## 内容交付与来源

默认库共 **390 篇、7 类**：企业健康管理制度42、体检流程18、体检项目说明56、常见健康知识128、职业健康知识86、复查流程24、企业健康服务规定36。

资料为根据列示公开来源编写的主题摘要及起草参考，非390份网上原文的全文转载，也不是390份本企业已经生效的正式文件。每篇有独立标题、主题要点、操作建议、使用边界和来源列表。来源链接展示在阅读器及导出文件内。

- **312篇公开参考摘要**：默认发布并允许AI作为通用解释上下文检索；不能作个人诊断、处方或职业禁忌判断。
- **78篇企业参考模板**：42篇制度＋36篇服务规范，默认未采纳、草稿、不供AI引用。企业核实、补齐实际职责和资源并勾选采纳后，才允许发布。没有编造企业电话、收费、预约记录或值守承诺。
- 普通体检与职业健康检查的适用范围有别。当前职业健康技术版本注明 GBZ188—2025（2026-08-01实施），没有继续把GBZ188—2014写成现行要求。实际监护项目、周期和个人结论由相应专业机构确定。
- 搜索与检索测试不增加引用量，排行榜和引用比例使用真实成功生成记录，无数据时显示空状态。

主要公开出处：

1. [国家卫生健康委：职业健康监护技术规范GBZ188—2025及实施时间](https://www.nhc.gov.cn/wjw/pyl/202509/1e939d64e61a4ac6b9d31191c5569c34.shtml)
2. [国家卫生健康委：进一步规范职业健康检查和职业病诊断管理](https://www.nhc.gov.cn/zyjks/c100153/202307/3f0ac690fb3846fdb8d0cd1c232f7428.shtml)
3. [国家卫生健康委：中国公民健康素养释义2024版](https://www.nhc.gov.cn/xcs/c100122/202405/f251e896a50a49ff8c632b4b3da93126/files/1734001844015_12363.pdf)
4. [中国疾控中心：职业健康保护知识](https://www.chinacdc.cn/jkyj/hjwsyzdkz/jswj3/202203/t20220309_295926.html)
5. [中国疾控中心：煤矿粉尘综合防尘措施](https://www.chinacdc.cn/jkkp/zyjk/zyjk/zybfzzs/201604/t20160425_301137.html)
6. [全国爱卫办：睡眠健康核心信息及释义](https://www.nhc.gov.cn/guihuaxxs/c100133/202503/70d5836afe804a858b899ee951a24a13.shtml)
7. [国家卫生健康委：高血压等慢性病营养和运动指导原则2024版](https://www.nhc.gov.cn/ylyjs/gzdt/202407/256b4eb8398440a8811344c7be50a333.shtml)
8. [中国疾控中心职业卫生所：健康企业建设评估技术指南](https://niohp.chinacdc.cn/zyjkcbzyk/twl/202104/W020210416596654406629.pdf)
9. [北京市体检中心：公开体检项目说明](https://www.bjtjzx.cn/tjzxmodule/tjfw/tjxmzh/index.shtml?id=6)

确定性内容构建脚本 `database/build_service_knowledge_seed.py`，完整数据 `yihe-framework/src/main/resources/ehr/service-knowledge-seed.json`。脚本校验分类数、标题与正文唯一性、最小正文长度、来源存在性。平台“导出”提供JSON完整资料或带BOM的CSV清单与正文；CSV防止以等号等开头的内容被当成公式执行。

## 页面功能

- 知识库文档：全文/标题/关键词搜索，分类、状态、资料性质筛选，分页、多选、新增、编辑、阅读、复制、发布、停用归档、删除、JSON导入与JSON/CSV导出。
- 知识分类管理：新增、更名、排序、删除空分类；有历史资料关系的分类不能删除，可更名保留历史。
- AI检索配置：总开关、TopK（1～8）、匹配分阈值（1～100）、分类范围与无副作用检索测试。使用本地词法匹配，不冒充向量语义检索；匹配分不代表临床置信度。
- 知识更新与维护：未采纳模板、未发布修改、过期资料、草稿清单，查看原文来源，查看版本操作记录并恢复历史内容。
- 侧栏：总量、分类数、近30日更新、累计真实上下文使用、近30日分类使用占比及TOP5。

## 后台 API

基础地址 `/ehr/api/communication/knowledge`。后台登录Cookie＋`/ehr/api/context`提供的`X-CSRF-Token`（非GET请求）；所有内容、设置、历史和使用统计按登录单位及data_mode隔离，小程序账号不能管理知识库。响应 `{code:0,data:...}`，错误使用既有HTTP状态及`msg`。

| 方法 | 路径 | 参数与功能 |
|---|---|---|
| GET | 基础地址 | search/categoryId/status/kind/page/size；列表、分类与概览统计 |
| POST | /initialize | 幂等补齐390篇默认资料，不覆盖修改或复活已删除种子 |
| POST | /documents | 新建草稿 |
| GET | /documents/{id} | 编辑稿正文、来源、当前发布快照、版本信息 |
| PUT | /documents/{id} | 传version更新编辑稿，旧发布快照保持不变 |
| POST | /documents/{id}/action | `{version,action:publish/archive/delete/copy}` |
| GET | /documents/{id}/history | 最近100条版本与操作记录 |
| POST | /documents/{id}/restore | `{version,versionId}` 恢复为编辑稿，不直接重发线上 |
| POST | /batch | `{action,items:[{id,version}]}`，最多500项，同一事务 |
| POST | /import | `{documents:[...]}`，最多500篇，全校验、全事务、均为草稿 |
| GET | /export | 最多5000篇完整资料，不导出其他单位数据 |
| POST | /categories | `{name,sort}` 新增分类 |
| PUT | /categories/{id} | `{name,sort,version}` |
| DELETE | /categories/{id} | `{version}`，仅无文档和历史关联时允许 |
| GET/PUT | /settings | enabled/topK/minScore/categoryIds/version |
| POST | /retrieve | `{question}`，返回有效发布片段、来源、修订号、词法评分，不调模型不记引用 |

资料编辑/导入示例：

```json
{
  "version": 2,
  "categoryId": "当前单位分类UUID",
  "title": "呼吸防护用品使用前检查",
  "keywords": ["呼吸防护", "防护用品"],
  "body": "填写经过核实的知识正文、适用对象、使用建议及边界，至少30字。",
  "sources": [{"name":"来源机构与文章名称","url":"https://权威来源/原文","date":"2026-11-25"}],
  "kind":"reference",
  "aiEnabled":true,
  "adopted":false,
  "expiresOn":""
}
```

`kind`为reference/template/platform；导入可用`categoryCode`映射当前单位分类（完整导出中已带code）。正文最多30000字，标题180字、20个关键词、最多8个来源；来源URL须HTTPS，内部平台流程可留URL空白但不能省来源名称。到期日可空，否则YYYY-MM-DD。传旧version返回409，不覆盖他人修改。修改后需要再发布，才更新AI引用内容。

## 检索及业务联动

AI只检索：当前单位与模式、未删除、状态published、存在已发布快照、快照aiEnabled=true、快照未过期、满足已配置分类范围和最低匹配分的资料。即使编辑稿后来改变，旧发布快照仍稳定，停用/删除立即退出检索。

- 自动回复：已匹配的AI知识规则追加知识库候选上下文，固定回复及医学转人工不会因此变成自动模型诊断；真正生成并保存消息后才记录使用事件，任务重复完成不重复计数。
- 职工会话AI辅助：生成回复草稿时带入检索资料，草稿成功保存后记录上下文使用并返回references；仍需人工核对发送。
- 引用统计表示资料作为上下文参与成功生成，不声称模型逐句引用了其中所有文字。未配置模型时检索测试、知识管理及固定回复可用，模型生成继续返回明确不可用信息。
- 检索来源文本始终视作资料而非指令，不自动执行内容中的链接、SQL或工具指令。

## 数据表、迁移与验证

全部使用`a_`前缀：`a_service_kb_categories`、`a_service_kb_documents`、`a_service_kb_versions`、`a_service_kb_settings`、`a_service_kb_usage`。正文软删除、历史记录保留，发布快照及使用修订号可追溯。

`python database/communication_knowledge_migration.py --apply`执行V019，先检查迁移账本校验和与同名表，使用共享schema锁；已有表不盲目重建。

`python tests/ehr/test-knowledge.py`使用真实数据库、随机隔离synthetic单位、全事务回滚，测试导入、版本、权限、快照、模板采纳、检索、引用幂等和两种AI联动（替身模型，不调用外部服务）。

`python tests/ehr/test-knowledge-layout.py`使用真实Chrome与独立本地假数据，验证四种屏宽及页面操作。部署后另做实际登录、接口与页面只读/隔离联调。原自动回复、会话与聊天记录继续回归。
