"""Export the live schema with human field semantics; no patient records read."""
from pathlib import Path
from html import escape
from collections import defaultdict
import json
from datetime import datetime
from health_archive_db import connect

BASE=Path(__file__).resolve().parent;DOC=BASE.parent/'docs'
MEANINGS={
'employee_id':'职工的内部UUID。与旧系统user_code不同；授权身份、主档案和业务花名册通过它关联。',
'scope_code':'组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。',
'data_mode':'real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。',
'roster_employee_key':'授权花名册中的稳定标识。已关联旧用户时使用旧user_code；后台新建职工使用ehr:UUID。',
'identity_ref':'受限身份标识/令牌的二进制引用；本期使用授权标识摘要，不直接存放报告中的身份证号。',
'roster_source_ref':'授权身份资料的来源引用。用于说明是谁、基于什么授权资料建档；不是报告抽取值。',
'record_id':'个人健康档案主记录UUID。同一组织和数据模式内，一个employee_id仅有一份主档案。',
'source_file_id':'原始PDF或照片UUID。相同组织和模式按文件SHA256去重；正文存于受限文件目录。',
'sha256':'原始文件字节的SHA256，32字节二进制；用于完整性和精确重复判断，不是文件名摘要。',
'object_ref':'原件的受限存储引用，当前为私有目录内随机文件名。不能作为未鉴权静态URL公开。',
'page_count':'实际读取的文件页数，PDF从文件解析，单张图片为1；证据页码必须落在此范围。',
'report_id':'逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。',
'current_revision':'逻辑报告当前候选版本指针。它不是正式归档标记；发布事实以publications为准。',
'revision':'报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。',
'prior_revision':'上一版本号；首版为NULL。历史版本不能覆盖，退回和修订会建立下一版本。',
'template_key':'报告版式/模板稳定编码。用于机构布局识别，不代表一个检查项目或报告实例。',
'mapping_version':'映射目录的发布版本。不能把未经确认的旧规则直接修改成新语义；应新版本发布。',
'template_kind':'text_layout为文本布局，scan_appearance为扫描外观模板。',
'institution_key':'机构匹配上下文。相同原标签在不同机构可能含义不同，不应只按标签名自动确认。',
'observed_layout':'观察到的报告布局描述；是模板证据，不是医疗诊断。',
'status':'当前实体自己的状态，具体枚举和含义见本表状态说明；不同表的status不能混用。',
'validation_ref':'验证资料或来源行的受限引用。本次种子中的xlsx行引用不等于临床确认或原件验证。',
'item_code':'医学检查项目字典的稳定编码。可以为NULL表示暂未匹配；不能仅凭同名宣称语义一致。',
'dictionary_version':'检查项目或字段字典的发布版本，例如2.0.0；结果应保留匹配时使用的版本。',
'standard_name':'字典中的标准项目名称。当前大部分仅为词汇级整理，不表示医学语义已核定。',
'semantic_scope':'项目语义适用范围与注意事项，标本、侧别、方法等仍可能需要原件复核。',
'mapping_key':'机构字段/项目别名映射的稳定编码。用于保留来源、映射版本和多候选关系。',
'entry_kind':'field对应67类标准报告字段，item对应动态检查项目；两种目标字段互斥。',
'template_version':'字段映射所依赖的模板发布版本；与template_key同时为空或同时提供。',
'original_label':'原件上看到的标签，不应把它改成标准名称后丢掉原文；签章标签也不是签章真实性证明。',
'normalized_lookup':'仅用于检索匹配的标签形式（NFKC/空白整理），不得据此修改原医学结果或自动解决语义冲突。',
'field_key':'标准报告字段编码，例如full_name、exam_date、hazard_factors。共享JSON数组依赖此键定位观察。',
'field_name':'标准字段的中文名称，例如姓名、体检日期。不是职工实际姓名或实际检查值。',
'field_category':'标准字段类别：职工基本、报告信息、正式结论、项目明细、原始证据、职业信息。',
'unit_mapping_key':'单位映射目录编码。04表390行只有81个不同原单位键，具体项目关系保存在observations。',
'unit_raw':'报告上的原始单位字符串，保留大小写、指数、符号和空格；不能擅自转换。',
'unit_normalized':'经批准规则转换后的单位。当前批准数为0，业务入库应为NULL。',
'rule_ref':'经过批准的项目级转换规则引用；只有observed_raw_only的单位观察没有此批准依据。',
'report_kind':'individual个人报告、organization组织报告或unknown。个人归档需要明确职工主档案。',
'institution_raw':'报告原文中的体检机构；区别于企业所属单位，保留报告当时的原始记录。',
'exam_date_raw':'报告原文体检日期字符串，可含中文年月日或不完整日期，不应直接强制转换。',
'exam_date_verified':'经人工核定的有效公历体检日期。OCR回调不会自动核定此字段，个人报告发布前必需。',
'report_date_raw':'报告出具日期的原始字符串，与体检日期、检验日期和上传日期分别保存。',
'exam_type_raw':'原文体检类别/检查类型，不直接代替职业健康分类或风险等级。',
'exam_number_raw':'机构给出的体检号原文，可能含前导零和字母；不是系统report_id。',
'age_raw':'报告上的年龄原文镜像，不回写企业出生日期，也不默认为当前年龄。',
'field_values':'版本内字段观察JSON数组。同一field_key可重复出现；每项保存原字段名、原值、证据ID和复核状态。',
'review_status':'unreviewed未核对、needs_review待复核、confirmed医生已确认、rejected已驳回。与识别任务状态分开。',
'reviewed_snapshot_ref':'医生已审核内容的受限快照引用。本期指向review_snapshots表的报告ID和版本。',
'evidence_id':'报告版本内的证据UUID，用于连接原页、字段观察和检查项目；不得跨版本引用。',
'page_number':'原件页码，从1开始。必须核对具体source_file_id和该文件实际页数。',
'bbox':'原页定位框JSON，顺序[x1,y1,x2,y2]。PDF点、图像像素和对象级证据不能混用。',
'coordinate_system':'pdf_points_unrotated为未旋转PDF点；image_pixels_top_left为左上原点像素；object_only仅指向对象/页且bbox为NULL。',
'page_rotation':'原页旋转角度，只允许0、90、180、270；用于正确展示定位框。',
'render_dpi':'PDF渲染成图像后的分辨率。像素坐标证据必须记录DPI，不能与PDF点坐标直接比较。',
'raw_fragment':'对应原页的原始片段/抄录原文。补录为manual_transcription，不伪称OCR自动识别。',
'extraction_method':'native_text原生文本、ocr图像识别、pdf_object原对象、manual_transcription人工抄录。',
'extractor_version':'外部识别器或人工补录适配器的版本标识，用于追溯识别来源。',
'ocr_confidence':'OCR模型输出的0至1置信度。原生文本不得编造OCR置信度，未提供时保留NULL。',
'profile_id':'一条职业健康字段观察的UUID，不是个人主档案record_id。',
'authorized_source_ref':'职业资料来自企业授权来源时的引用；报告来源则必须有报告版本和原页证据。',
'original_field_name':'报告原始字段/项目名称，例如原文检查名；应与标准名称分别保留。',
'value_raw':'标准字段或职业字段的原始字符串。缺失保留NULL，禁止通过空值补零或改成“正常”。',
'value_normalized':'经批准规则得到的标准化值。没有规则版本和批准依据时不能填写。',
'item_id':'报告版本内一条检查结果的UUID；同项目多次测量可有多行，不能按item_code覆盖。',
'ordinal':'该版本项目明细的行顺序，从1开始；同版本唯一，用于还原报告阅读顺序。',
'primary_evidence_id':'该条项目/结论的主要原页证据ID，必须包含在evidence_ids数组中。',
'evidence_ids':'关联本报告版本的证据UUID数组；用于多行、跨页或多模型证据并列。',
'standard_field_name':'匹配到的标准项目名，原项目名仍保留；未匹配时可以为空。',
'abbreviation_raw':'项目英文缩写原文，例如报告提供的缩写，不从中文名称自行生成。',
'result_raw':'检查结果原文，可为复合值、定性文字、滴度、半定量或数值字符串；不得强制转Double。',
'result_normalized':'有批准规则和版本依据后的结构化标准结果JSON，当前OCR接收层拒绝无批准的派生结果。',
'unit_mapping_version':'该结果采用的单位映射发布版本，与unit_mapping_key配对。',
'normalization_rule_version':'归一化/单位转换规则版本；不能仅填一个字符串就宣称已取得医学批准。',
'reference_range_raw':'原报告参考范围字符串，保留分组和单位上下文；不得套用一套通用正常值。',
'abnormal_flag_raw':'原报告异常标记，例如箭头或文字；不直接把它当作平台风险等级。',
'mapping_status':'unmapped未匹配、lexical_only词汇级、needs_confirmation待确认、confirmed已验证。医生确认原值不等于自动确认字典语义。',
'conclusion_id':'一段医生正式结论的版本内UUID，支持一份报告多段结论。',
'original_text':'完整医生正式结论原文，不能拿分项小结或签名栏代替完整结论。',
'completeness':'这一段结论的完整性：complete、partial、unknown；不是个人主档案完整度百分比。',
'standard_category':'医生核对后对正式职业健康结论的分类：目前未见异常、疑似职业病、职业禁忌证、其他疾病或异常。不是平台高/中/低风险。',
'job_id':'外部OCR/PDF识别任务UUID，绑定逻辑报告和候选版本；回调必须关联该任务。',
'request_key':'客户端或任务幂等键。相同键应对应同一身份/原件/业务操作，不允许重用为其他内容。',
'pipeline_version':'识别处理管线/适配器版本，不代表模型一定运行成功。',
'review_id':'一条人工审核决策UUID，关联精确报告版本和已核对摘要。',
'reviewer_ref':'审核人员受限标识，当前由登录医生账号确定，不从前端提交审核人ID。',
'decision':'return退回补充、reject驳回、confirm确认。三者分别留痕，不能互相冒充。',
'reviewed_digest':'规范化已核对内容的SHA256（32字节），质量校验、医生审核和发布必须相同。',
'decision_key':'审核决策幂等键，同一报告版本内唯一，防止网络重试产生重复决策。',
'issue_disposition':'JSON问题处置与说明，包括身份已人工核验、审核备注；不应存放访问令牌或密码。',
'advice_id':'一条报告原文建议或医生建议的UUID，用于后续任务追溯。',
'source_kind':'建议来源：pdf为报告原文，clinician为医生补充，后者必须有医生作者与审核记录。',
'source_evidence_id':'建议原文对应的证据ID；来自PDF时必须能够定位原页。',
'author_ref':'医生补充建议的作者标识，不能把模型当作持证医生。',
'text_raw':'原始建议/处理意见文字，保留来源，不自动生成用药或诊疗方案。',
'task_id':'随访任务UUID，来自一个确定的报告版本和建议；不能代替识别任务job_id。',
'due_at':'任务计划到期UTC时间；无明确计划时为NULL。',
'event_id':'一条发布后同步事件UUID；消费者按事件和版本幂等处理。',
'upload_id':'一次实际上传的UUID，区别于去重后的source_file_id。',
'upload_channel':'miniapp小程序、phone_photo拍照、backend_file后台文件、batch_import批量、legacy_import历史导入；不是医学证据提取方式。',
'original_file_name':'客户端文件原名，仅作展示与下载名，不能用作服务器存储路径。',
'mime_type':'经实际文件内容确认的类型，不只相信扩展名或客户端Content-Type。',
'file_size':'原文件字节数。当前接口限制单文件20 MB。',
'uploader_ref':'真实上传人受限标识；后台取登录账号，小程序取专用令牌绑定身份。',
'target_table':'标准字段的a_物理目标表；不意味着字段值一定是固定列。',
'target_column':'标准字段物理目标列，可能是共享field_values JSON列。',
'json_path':'数据契约中的逻辑JSON路径；同一数组还需按field_key筛选定位。',
'definition':'01表该标准字段的完整元数据JSON，包括证据、敏感级别与来源等。',
'source_row':'输入工作簿中的原始行号，用于复核本行字典来源。',
'workbook_sha256':'原始Excel字节摘要，防止混用不同输入版本。',
'sheet_index':'工作表索引，从0开始，对应00至11十二张表。',
'sheet_name':'工作表原名称，用于展示和追溯。',
'payload':'源工作表一行的完整JSON文本。使用LONGTEXT避免JSON数字规范化改变样本统计原始表示。',
'payload_sha256':'该行规范序列化文本的SHA256，与源行逐项复核。',
'approval_status':'单位观察的批准状态；目前390条均为未批准，不应据此做单位换算。',
'quality_check_id':'一次报告版本质量核验UUID，包括身份、规则版本和快照摘要。',
'report_digest':'被质量核验的报告内容摘要，发布时必须与医生核对摘要一致。',
'identity_status':'matched已依据授权资料核定；conflict冲突、ambiguous多候选、unmatched未匹配。仅同名不能自动确认身份。',
'issues':'JSON质量问题数组。原始冲突和缺失应保留，不能静默丢弃。',
'rule_version':'质量、风险或完整度规则的明确版本。不同业务的规则含义独立，不要混用。',
'checked_by_ref':'执行质量核验的人员或受控服务标识。',
'publication_id':'一条正式归档/发布凭据UUID。个人正式档案读取已发布版本，而不是任意候选。',
'review_decision':'发布关联的人工审核决策，数据库限制必须为confirm。',
'report_review_status':'发布关联版本的审核状态，数据库限制必须为confirmed。',
'quality_status':'发布关联质量核验状态，数据库限制必须为passed。',
'published_by_ref':'执行归档的已授权账号标识。',
'assessment_id':'独立的平台风险评估UUID；与原报告医生正式结论分开。',
'assessment_time':'本次风险评估所对应的UTC时间，不是报告上传时间。',
'risk_score':'按已批准规则得到的风险评分；没有规则时为空，不编造默认分数。',
'risk_level':'unknown未评估，low低、medium中、high高、critical极高风险；未评估不得默认低风险。',
'assessment_type':'rule规则评估、clinician人工评估、ai_assisted模型辅助；均需保留规则和复核来源。',
'assessment_status':'draft草稿、needs_review待核对、confirmed已确认、superseded已被后续评估替代。',
'factor_id':'一条可解释风险因素UUID，与具体评估关联。',
'factor_name':'该风险因素的名称，不是直接从异常标记推断出的诊断。',
'reason':'业务处理或风险判定的具体理由；内容应与原件、规则和权限一致。',
'contribution':'风险规则给定的贡献值；没有批准规则时保持NULL。',
'snapshot_id':'一次档案完整度快照UUID，允许按时间保留历史计算结果。',
'required_count':'当前完整度规则要求的材料/信息项数，不是655个项目数或样本字段出现频次。',
'completed_count':'该规则下实际已完成的项数，应不大于required_count。',
'missing_items':'缺失材料JSON数组，长度须等于应有项数减已完成项数。',
'basis':'计算完整度使用的报告版本及输入上下文JSON，供审计复算。',
'completeness_rate':'0至1的完整度比率，页面再乘100显示；分母为0时NULL，不能默认100%。',
'audit_id':'一条不可混淆的档案业务审计UUID。',
'actor_ref':'实际执行操作的账号/服务受限标识，不使用前端传入的任意操作者。',
'action':'审计动作代码，例如profile_saved、report_uploaded、report_approve、report_archive。',
'redacted_diff':'经过脱敏的变更差异JSON，不能把完整隐私、密码、令牌写进日志。',
'version':'数据库迁移版本标识，与报告revision和字典版本分别管理。',
'checksum':'实际执行SQL文件的SHA256，重跑时必须一致，不允许无声替换。',
'source_sha256':'该迁移引用的原始字段工作簿SHA256。',
'description':'迁移用途说明。',
'applied_by':'执行数据库迁移的数据库账号，用于运维追溯。',
'employee_name':'企业授权资料中的姓名；报告姓名保存在版本快照，不能自动覆盖本列。',
'employee_no':'组织内唯一工号；软删除仍占用编号，避免重新建档割裂历史。',
'gender':'授权身份性别：男、女、未填写。',
'birth_date':'授权出生日期，用于当前年龄统计；报告原年龄仍独立保留。',
'phone':'授权联系电话，权限与导出均按组织边界控制。',
'organization_name':'单位显示名称，仅用于业务展示；实际访问边界是scope_code。',
'team_name':'职工班组，供检索和班组统计。',
'position_name':'岗位或工种名称，供授权职业资料展示，不替代报告中的job_type原文。',
'exposure_history':'企业授权填报的职业危害与接触史；不自动转成健康风险诊断。',
'legacy_user_code':'同组织旧系统用户编码，用于小程序本人绑定。新建档案不会自动创建登录账号。',
'row_version':'授权档案的乐观锁版本；编辑/删除必须携带旧版本，冲突返回409。',
'deleted_at':'UTC软删除时间，NULL表示在档；不删除原件、报告、审核及历史。',
'delete_reason':'软删除必填原因，留作审计。',
'snapshot_json':'完整审核内容的不可变JSON快照，后续归档会重新核对内容摘要。',
'session_id':'健康档案小程序专用会话UUID，不使用可猜测的工号作为令牌。',
'token_hash':'32字节随机Bearer令牌的SHA256。数据库不存放明文令牌。',
'expires_at':'短期会话的UTC失效时间，默认签发后两小时。',
'revoked':'0有效、1已注销；还会检查用户锁定状态与当前组织归属。',
'response_json':'外部OCR成功回调的候选JSON快照，未审核，不代表正式健康档案。',
'uploaded_at':'UTC实际上传时间。','checked_at':'UTC质量核验完成时间。','reviewed_at':'UTC医生确认时间。','published_at':'UTC正式归档时间。','calculated_at':'UTC完整度计算时间。','occurred_at':'UTC业务动作发生时间。','applied_at':'UTC迁移完成时间。','received_at':'UTC识别结果成功接收时间。','created_at':'UTC记录建立时间；不是体检日期、报告日期或事件发生日期。','updated_at':'UTC记录最后更新时间，任务更新由接口显式写UTC。',
}
MEANINGS.update({'mini_user_id': '微信小程序独立用户UUID，不是职工ID。未绑定时也可存在，不能据此读取职工档案。', 'app_id': '服务端配置的微信小程序AppID；与OpenID、数据模式组成唯一身份。', 'open_id': '服务端从微信code2Session核验得到的OpenID；客户端不能指定，不在登录响应中下发。', 'union_id': '微信实际返回时保存的UnionID，可空；不据客户端值跨用户合并。', 'user_status': 'active有效、disabled停用；所有会话请求重新检查此状态。', 'consent_version': '用户明确接受的用户协议/隐私政策版本，必须等于服务端当前版本。', 'consent_at': 'UTC用户最近明确接受该协议版本的时间。', 'last_login_at': 'UTC小程序用户最近成功微信登录时间。', 'mini_session_id': '小程序独立会话UUID；令牌仅保存摘要，未绑定会话不授予健康数据权限。', 'binding_code_id': '个人企业身份验证码签发记录UUID；后续预览、确认和绑定均精确关联此签发。', 'code_hash': '规范化个人验证码的SHA256；随机16字符约80位熵，不保存可重复查询的明文。', 'code_status': 'issued已签发、consumed已确认消费、revoked已撤销；过期还须结合expires_at判断。', 'issue_request_key': '后台签发幂等键，同企业和模式内唯一；重复不旋转验证码，也不重返明文。', 'issued_by_ref': '后台签发人标识的二进制引用，不能作为职工身份验证码。', 'issued_at': 'UTC个人验证码签发时间。', 'consumed_by': '实际勾选确认并完成绑定的小程序用户UUID；仅预览不会填此列。', 'consumed_at': 'UTC确认成功后消费验证码的时间，未消费为NULL。', 'revoked_at': 'UTC验证码撤销或微信绑定解除时间；保留原记录以便追溯。', 'active_record_id': '自动生成列：仅在验证码为issued或绑定为active时保留record_id，用唯一索引限制一职工一个当前状态；不能由客户端写入。', 'challenge_id': '已验证企业身份后生成的短期个人信息确认挑战UUID。', 'challenge_hash': '随机确认凭据的SHA256，只允许同一小程序用户在有效期内确认。', 'profile_row_version': '身份核验返回资料时的花名册版本，后台修改后旧确认请求会被拒绝。', 'profile_digest': '实际展示给职工的资料摘要，包括脱敏手机号；防止确认与所见资料不一致。', 'used_at': 'UTC个人信息确认成功时间，NULL为尚未确认。', 'binding_id': '正式微信用户与职工档案关联的UUID；解除后保留历史。', 'binding_status': 'active为有效绑定、revoked为后台核对后解除。', 'confirmed_digest': '职工明确确认的后台资料摘要，不是自动计算的健康风险。', 'confirmed_at': 'UTC职工确认本人信息并完成绑定的时间。', 'revoke_reason': '后台人工解除微信绑定的必填核对说明。', 'active_user_id': '自动生成列：只有有效绑定才填mini_user_id，唯一索引限制一个微信用户只能有一个当前职工身份。', 'bucket_key': '操作、模式和用户/IP的摘要，存储限流桶键，不保存明文IP。', 'window_start': 'UTC固定限流窗口起点；新窗口首次请求重置计数。', 'attempt_count': '当前窗口尝试次数，独立事务累加；验证失败回滚不能撤销计数。'})

MEANINGS.update({'correction_id': '绑定前资料更正申请UUID；用于查询和医生审核，不是职工ID。', 'before_snapshot': '申请时职工已核验的资料快照JSON，联系电话脱敏；保留原资料便于审核。', 'requested_changes': '仅保存白名单字段的申请值JSON，不会在提交时修改正式资料。', 'request_reason': '职工说明资料错误及申请更正的原因，必填1至600字符。', 'request_status': 'pending待审核、approved已通过、rejected已驳回。通过后仍须重新验证并本人确认绑定。', 'reviewed_by_ref': '处理更正申请的后台医生账号引用，由服务端取当前登录人。', 'reviewed_at': 'UTC人工审核更正申请的时间；待审为NULL。', 'review_note': '医生核对依据或驳回原因，必填；不会作为医学结论。', 'review_request_key': '医生审核幂等键，同结论同审核人可重试，不能覆盖既有处理结果。', 'after_snapshot': '审核通过后当前名册的脱敏资料快照JSON；驳回或待审时NULL。', 'pending_user_id': '生成列：待审时为mini_user_id，处理后NULL；唯一索引限制一用户一份待审申请。', 'pending_record_id': '生成列：待审时为record_id，处理后NULL；唯一索引限制一职工一份待审申请。'})
MEANINGS.update({'dispatch_state': 'OCR调度状态：queued排队、running租约处理中、retry_wait等待重试、succeeded已收到候选、failed停止重试、cancelled人工或版本变更停止。', 'max_attempts': '允许累计领取的次数预算，默认3；后台重试增加3次，原有尝试历史保留。', 'available_at': 'UTC任务下一次可领取时间；失败退避期间不能立即领取。', 'current_attempt_id': '当前或最近一次领取尝试UUID；接管后旧租约失效。', 'worker_id': 'OCR工作服务实例标识，仅表示处理程序，不是职工或医生身份。', 'lease_token_hash': '当前任务租约凭据的SHA256摘要，不在后台页面显示。', 'lease_until': 'UTC租约到期时间，B端心跳续期，过期后回收或失败。', 'progress_stage': 'B端报告的解析、OCR、提取、项目匹配或校验阶段；finished只由A端接收成功后写入。', 'progress_percent': '任务进度0至100；B只能上报0至95，100由A端完成事务后设置。', 'last_error_code': '最近失败、超时或人工停止的错误码，用于页面提示和排查。', 'last_error_message': '脱敏错误说明，不应包含密钥、完整报告文本等。', 'last_retry_key': '后台手动重试UUID幂等键，同键重试不增加额外预算。', 'attempt_id': '一次实际领取处理尝试UUID；与业务job_id分开。', 'attempt_number': '同任务内的递增领取序号，超时后再次领取另增一条记录。', 'claim_request_key': 'B端领取请求UUID，网络重试沿用；当前租约有效时返回原领取结果。', 'outcome': '本次尝试结果：running、succeeded、failed、expired、cancelled。超时历史不会覆盖。', 'heartbeat_at': 'UTC本次尝试最近一次心跳时间，B处理长报告时也须续期。', 'finished_at': 'UTC本次尝试成功、失败、超时或取消时间；处理中为空。', 'error_code': '本次OCR尝试的脱敏错误代码。', 'error_message': '本次OCR尝试的脱敏错误说明。'})
MEANINGS['started_at']='UTC本次OCR领取处理尝试开始时间。'
TABLE_NOTES={
'a_health_reports':'主键(report_id,revision)；身份字段为报告快照。已审核版本冻结，修改须追加下一revision。',
'a_health_items':'每行一个实际检查项目。655个项目是字典记录，不是655个固定数据库结果列。',
'a_employees':'身份根索引；授权花名册资料由旧系统关联及a_employee_roster_profiles承接。',
'a_report_series':'逻辑报告与当前候选指针；正式发布记录与当前草稿可以同时存在。',
'a_report_evidence':'来源、页码、坐标、提取方式和复核状态共同组成证据，不可只保留一段AI文字。',
'a_health_conclusions':'医学正式结论独立存储，不与平台风险等级混为一列。',
'a_medical_unit_mapping':'81个原单位键。当前无已批准转换；390条项目关联观察另见a_medical_unit_observations。',
'a_report_sync_outbox':'pending待投影/同步，sent已处理。发布与事件必须处于同一个数据库事务，外部消费需幂等。',
'a_extraction_jobs':'uploaded→ocr_complete/extracted→standardized/identity_checked/quality_checked→pending_review→published，异常为failed。状态表示所完成的阶段，不能伪造OCR成功。',
'a_review_records':'决策return/reject/confirm分别留痕，审核摘要精确绑定版本；原记录只能追加。',
'a_employee_roster_profiles':'为尚未创建登录账号的职工提供授权档案维护，并支持与旧用户绑定；不改旧职工表，也不接收OCR覆盖。',
'a_ehr_mobile_sessions':'新EHR独立短期随机令牌，不能用旧工号token直接访问此模块。',
}

def main():
    c=connect();tables=[]
    with c.cursor() as q:
        q.execute("SELECT TABLE_NAME,TABLE_COMMENT,ENGINE,TABLE_COLLATION FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND LEFT(TABLE_NAME,2)='a_' ORDER BY TABLE_NAME")
        for t in q.fetchall():
            name=t['TABLE_NAME'];q.execute('SELECT COLUMN_NAME,COLUMN_TYPE,IS_NULLABLE,COLUMN_DEFAULT,COLUMN_COMMENT,EXTRA FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=%s ORDER BY ORDINAL_POSITION',(name,));t['columns']=q.fetchall()
            q.execute('SELECT INDEX_NAME,NON_UNIQUE,SEQ_IN_INDEX,COLUMN_NAME FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=%s ORDER BY INDEX_NAME,SEQ_IN_INDEX',(name,));t['indexes']=q.fetchall()
            q.execute('SELECT CONSTRAINT_NAME,COLUMN_NAME,REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME,ORDINAL_POSITION FROM information_schema.KEY_COLUMN_USAGE WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=%s AND REFERENCED_TABLE_NAME IS NOT NULL ORDER BY CONSTRAINT_NAME,ORDINAL_POSITION',(name,));t['foreign_keys']=q.fetchall();q.execute('SHOW CREATE TABLE `'+name+'`');t['ddl']=list(q.fetchone().values())[1];q.execute('SELECT TRIGGER_NAME,ACTION_TIMING,EVENT_MANIPULATION,ACTION_STATEMENT FROM information_schema.TRIGGERS WHERE TRIGGER_SCHEMA=DATABASE() AND EVENT_OBJECT_TABLE=%s ORDER BY TRIGGER_NAME',(name,));t['triggers']=q.fetchall();tables.append(t)
    c.close()
    count=sum(len(t['columns']) for t in tables);timestamp=datetime.now().astimezone().isoformat()
    md=f'# 矿安智康健康档案：逐表逐字段说明\n\n实际数据库：`192.168.1.109 / shayu_3.0`。生成时间：{timestamp}。\n\n共 **{len(tables)} 张 a_ 表、{count} 个物理列**。其中V001为先前31张（19核心+12支撑），V002补充3张业务表，V003补充OCR回调收据，V004补充6张微信用户、会话和个人验证码绑定表，V005增加绑定前资料更正申请，V006增加持久OCR调度和处理尝试记录。下列内容从实际库读取类型、空值、默认值、主键、索引和外键，仅查询结构，不读取患者记录。\n\n'
    md+='## 数据主线与关键边界\n\n授权职工 → 一人一份主档案 → 原件/上传渠道 → 逻辑报告 → 多版本候选 → 原页证据/动态项目/职业信息/正式结论/建议 → 质量核验 → 医生审核 → 发布凭据与同步事件 → 个人档案和企业统计。\n\n- 授权身份与报告快照分开，报告姓名不能自动覆盖职工姓名。\n- 原始医学值、单位、参考范围保留字符串；无批准规则不进行换算或派生。\n- 高风险与待审核可重叠；未评估不会被当作低风险。\n- 当前前端已使用真实API；空库显示空状态，不再生成2436条演示档案。\n- 已审核版本不可覆盖；退回保留处理记录并建立下一版本。\n- 表中所有时间按UTC约定处理，前端可转换为北京时间。\n\n## 表目录\n\n'
    for t in tables:md+=f"- [{t['TABLE_NAME']}](#{t['TABLE_NAME']})：{t['TABLE_COMMENT']}（{len(t['columns'])}列）\n"
    sections=[];unmapped=set()
    for t in tables:
        n=t['TABLE_NAME'];intro=TABLE_NOTES.get(n,t['TABLE_COMMENT'])
        fk=defaultdict(list)
        for f in t['foreign_keys']:fk[f['COLUMN_NAME']].append(f"{f['REFERENCED_TABLE_NAME']}.{f['REFERENCED_COLUMN_NAME']}")
        indexed=defaultdict(list)
        for i in t['indexes']:indexed[i['COLUMN_NAME']].append(i['INDEX_NAME'])
        content=f'\n## {n}\n\n**用途：** {t["TABLE_COMMENT"]}。\n\n{intro}\n\n引擎：{t["ENGINE"]}；默认排序规则：{t["TABLE_COLLATION"]}。\n\n| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |\n|---|---|---|---|---|\n'
        htmlrows=[]
        for col in t['columns']:
            k=col['COLUMN_NAME'];meaning=MEANINGS.get(k)
            if n=='a_ocr_dispatch_tasks' and k=='attempt_count':meaning='已实际领取的处理次数，不因手动重试归零；历史保留在a_ocr_dispatch_attempts。'
            if n=='a_miniapp_correction_requests' and k=='request_key':meaning='更正申请按微信用户和模式唯一的幂等键，同键不能变更提交内容。'
            if n=='a_miniapp_correction_requests' and k=='row_version':meaning='更正申请处理版本；待审为1，审核后递增，防止重复或并发处理覆盖。'
            if k=='expires_at' and n.startswith(('a_miniapp_','a_employee_binding_')):meaning=col['COLUMN_COMMENT']+'。会话默认2小时、确认凭据最多10分钟、个人码默认24小时，具体以本记录为准。'
            if meaning is None:unmapped.add(k);meaning=col['COLUMN_COMMENT'] or k
            if k=='payload_sha256' and n=='a_ehr_ocr_receipts':meaning='对外部OCR候选JSON规范序列化后的SHA256，用于相同job_id的重复回调比对；不是文件SHA256。'
            if k=='status' and n=='a_medical_item_dictionary':meaning='lexical_only词汇级，needs_confirmation待确认，confirmed已验证，retired已退役；种子532条词汇级、123条待确认。'
            null='可空' if col['IS_NULLABLE']=='YES' else '不可空';default='NULL/未设置' if col['COLUMN_DEFAULT'] is None else str(col['COLUMN_DEFAULT']);relation='；'.join(['外键→'+r for r in fk[k]]+['索引:'+x for x in indexed[k]]) or '无独立索引/外键；通过所属记录关联'
            values=[k,col['COLUMN_TYPE'],null+'；默认 '+default,meaning,relation];content+='| '+' | '.join(str(v).replace('|','\\|').replace('\n',' ') for v in values)+' |\n';htmlrows.append('<tr>'+''.join('<td>'+escape(str(v))+'</td>' for v in values)+'</tr>')
        content+='\n复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。\n';content+='\n实际DDL（包含CHECK约束与完整复合索引）：\n\n```sql\n'+t['ddl']+';\n```\n';content+='\n写入保护触发器：\n'+('\n'.join('- '+x['TRIGGER_NAME']+'：'+x['ACTION_TIMING']+' '+x['EVENT_MANIPULATION']+'\n\n```sql\n'+x['ACTION_STATEMENT']+'\n```' for x in t['triggers']) or '无表级触发器，仍需遵守外键、CHECK及接口事务校验。')+'\n';md+=content
        sections.append(f'<section id="{n}" data-search="{escape(n+" "+t["TABLE_COMMENT"]+" "+" ".join(c["COLUMN_NAME"] for c in t["columns"]))}"><h2>{n} <small>{len(t["columns"])}列</small></h2><p>{escape(t["TABLE_COMMENT"])}。{escape(intro)}</p><div class="scroll"><table><thead><tr><th>字段</th><th>MySQL类型</th><th>空值 / 默认值</th><th>逐字段业务解释</th><th>关联与索引</th></tr></thead><tbody>'+''.join(htmlrows)+'</tbody></table></div><details><summary>查看实际建表DDL、CHECK及触发器</summary><pre>'+escape(t['ddl'])+'</pre>'+''.join('<h4>'+escape(x['TRIGGER_NAME'])+'</h4><pre>'+escape(x['ACTION_STATEMENT'])+'</pre>' for x in t['triggers'])+'</details></section>')
    assert not unmapped,'Missing human field meanings: '+str(unmapped)
    (DOC/'ehr-data-dictionary.md').write_text(md,encoding='utf-8')
    (BASE/'ehr-live-schema-catalog.json').write_text(json.dumps(dict(generated_at=timestamp,table_count=len(tables),column_count=count,tables=tables),ensure_ascii=False,indent=2,default=str),encoding='utf-8')
    html='''<!doctype html><html lang="zh-CN"><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>矿安智康 · 完整数据字典</title><style>*{box-sizing:border-box}body{margin:0;background:#f1f7fa;color:#244b5e;font:14px/1.7 system-ui,"Microsoft YaHei",sans-serif}header{background:#084c58;color:#fff;padding:30px}header p{color:#b9e0e1}main{max-width:1700px;margin:auto;padding:24px}nav{position:sticky;top:0;background:#fff;padding:15px 25px;border-bottom:1px solid #d8e8ef;z-index:2;display:flex;gap:18px;align-items:center}input{padding:11px;border:1px solid #bcd7df;border-radius:9px;min-width:330px}a{color:#009e8d}section{background:#fff;border:1px solid #d8e8ef;border-radius:14px;margin:20px 0;padding:23px;scroll-margin-top:80px}h2{color:#006b70;font-size:22px}small{font-size:12px;color:#95abb7}.scroll{overflow:auto}table{border-collapse:collapse;width:100%;min-width:1000px}td,th{padding:12px;border:1px solid #e0ebf0;text-align:left;vertical-align:top}th{background:#edf7f7}td:first-child{font-weight:600;min-width:150px}td:nth-child(2){font:12px/1.6 monospace;min-width:200px}td:nth-child(4){min-width:270px}td:last-child{font-size:12px;min-width:220px}pre{white-space:pre-wrap;overflow-wrap:anywhere;background:#f1f7fa;padding:18px;font:12px/1.7 monospace}#directory{display:grid;grid-template-columns:repeat(3,1fr);gap:8px}#directory a{overflow-wrap:anywhere}.notice{padding:18px;border-radius:12px;background:#ddf4ec}@media(max-width:800px){main{padding:10px}#directory{grid-template-columns:1fr}nav{position:static;flex-wrap:wrap}}</style><header><h1>矿安智康 · 完整数据库逐字段说明</h1><p>'''+str(len(tables))+''' 张 a_ 表 · '''+str(count)+''' 个字段 · 包含实际类型、默认值、索引、外键与中文业务解释</p></header><nav><input id="search" aria-label="搜索表或字段" placeholder="搜索表名、字段或表用途…"><a href="#directory">表目录</a><a href="ehr-data-dictionary.md">Markdown完整说明</a><a href="ehr-api.md">接口与联调说明</a></nav><main><div class="notice">原件、报告版本、检查结果、医生结论、风险评估分别保存。未批准的单位转换保持禁用；空库不填演示人数；软删除保留原件与审核历史。</div><section><h2>表目录</h2><div id="directory">'''+''.join(f'<a href="#{t["TABLE_NAME"]}">{t["TABLE_NAME"]}</a>' for t in tables)+'''</div></section>'''+''.join(sections)+'''</main><script>document.querySelector('#search').oninput=e=>{let q=e.target.value.toLowerCase().trim();document.querySelectorAll('section[data-search]').forEach(s=>s.hidden=!s.dataset.search.toLowerCase().includes(q))}</script></html>'''
    (DOC/'ehr-data-dictionary.html').write_text(html,encoding='utf-8');print('Exported',len(tables),'tables',count,'fully explained columns')
if __name__=='__main__':main()
