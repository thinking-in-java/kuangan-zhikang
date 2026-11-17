"""Reproduce the physical a_ schema and seeds from the supplied v2.0.0 workbook.

The referenced frozen SQL/JSON Schema files were NOT supplied. Sheet 08 is the
column/index/FK baseline; additional checks are local, explicitly documented.
Never edit the source XLSX. This generator consumes its read-only JSON export.
"""
from collections import OrderedDict, Counter, defaultdict
from pathlib import Path
import csv
import hashlib
import json
import re
import unicodedata

BASE = Path(__file__).resolve().parent
REF = BASE/'reference/v2.0.0'
VERSION = '2.0.0'
ASCII = 'CHAR(36) CHARACTER SET ascii COLLATE ascii_bin'
CODE = 'VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin'
MODE = "ENUM('real','synthetic')"
REVIEW = "ENUM('unreviewed','needs_review','confirmed','rejected')"
QSTATUS = "ENUM('passed','failed','needs_review')"
DECISION = "ENUM('return','reject','confirm')"

def rows(n):
    a = json.loads((REF/f'{n:02}.json').read_text(encoding='utf-8'))
    h = a[2]['cells']
    return [dict(zip(h,r['cells']), _row=r['row']) for r in a[3:]]

def literal(v):
    if v is None:
        return 'NULL'
    if isinstance(v, (int,float)):
        return str(v)
    return "'"+str(v).replace('\\','\\\\').replace("'","''").replace('\x00','\\0')+"'"

def js(v):
    return json.dumps(v,ensure_ascii=False,separators=(',',':'))

def table_name(n):
    return 'a_'+n

def main():
    tables = OrderedDict()
    fks = []
    comments = {
      'employees':'授权职工身份索引；不存储报告抽取的身份覆盖值',
      'health_records':'个人健康档案主记录；每个授权职工一份',
      'report_sources':'PDF或照片原始文件；按组织和模式去重',
      'report_series':'逻辑报告；current_revision不是正式发布标记',
      'health_reports':'报告版本；报告中的身份信息为原始快照',
      'report_revision_sources':'报告版本与多份原文件的关联',
      'report_evidence':'版本内原页、坐标及提取证据',
      'extraction_jobs':'PDF解析及OCR任务；与审核状态独立',
      'health_items':'动态检查项目结果；保留原值及原单位',
      'occupational_profiles':'职业健康字段观察及授权职业资料',
      'health_conclusions':'医生正式结论；区别于平台风险等级',
      'health_advice':'报告原文建议或医生确认建议',
      'followup_tasks':'由医生建议产生的随访任务',
      'report_template_mapping':'模板映射；没有原始模板包则不编造种子',
      'medical_item_dictionary':'655种检查项目；词汇映射不等于医学语义确认',
      'medical_field_mapping':'机构字段及检查项目别名；允许同名多候选',
      'medical_unit_mapping':'单位键目录；未批准转换不得生成标准值',
      'review_records':'医生审核决策历史；退回与驳回分别记录',
      'report_sync_outbox':'正式发布后的事务消息；消费者须幂等处理',
    }
    def ensure(n, core=False):
        return tables.setdefault(n,dict(name=table_name(n),core=core,columns=[],indexes=OrderedDict(),checks=[],comment=comments.get(n,n)))
    def col(n,c,t,nullable=False,default=None,description=None):
        ensure(n)['columns'].append(dict(name=c,type=t,nullable=nullable,default=default,comment=description or c))
    def index(n,name,columns,unique=False,primary=False):
        ensure(n)['indexes'][name]=dict(name=name,columns=list(columns),unique=unique,primary=primary)
    def fk(n,columns,target,remote):
        edge=(n,tuple(columns),target,tuple(remote))
        if edge not in fks:
            fks.append(edge)
    def check(n,expr):
        ensure(n)['checks'].append(expr)
    positions=defaultdict(dict)
    for r in rows(8):
        n,c=r['table_name'],r['column_name']
        ensure(n,True)
        col(n,c,r['mysql_type'],r['nullable']=='是',r['default_value'],r['description'])
        for spec in (r['index_name'] or '').split('｜'):
            if not spec:
                continue
            m=re.fullmatch(r'(\w+) \[(\d+)/(\d+)\]',spec)
            assert m,spec
            positions[(n,m[1])][int(m[2])]=c
        for spec in (r['foreign_key'] or '').split('｜'):
            if not spec:
                continue
            m=re.fullmatch(r'(?:\w+ )?\(([^)]+)\) → (\w+)\(([^)]+)\)',spec)
            assert m,spec
            fk(n,[s.strip() for s in m[1].split(',')],m[2],[s.strip() for s in m[3].split(',')])
    for (n,k),parts in positions.items():
        assert sorted(parts)==list(range(1,len(parts)+1))
        index(n,k,[parts[i] for i in sorted(parts)],k.startswith('uq_'),k=='PRIMARY')
    assert sum(t['core'] for t in tables.values())==19
    # Explicit product requirements / supporting unique FK targets.
    index('health_records','uq_record_employee',['scope_code','data_mode','employee_id'],True)
    index('health_records','uq_record_employee_id',['scope_code','data_mode','record_id','employee_id'],True)
    index('health_reports','uq_report_record_review',['scope_code','data_mode','report_id','revision','record_id','review_status'],True)
    index('review_records','uq_review_decision_digest',['report_id','revision','review_id','decision','reviewed_digest'],True)
    index('health_reports','ix_report_review',['scope_code','data_mode','review_status','created_at'])
    check('report_sources','page_count > 0')
    check('health_reports',"JSON_TYPE(field_values) = 'ARRAY'")
    check('health_reports','(revision = 1 AND prior_revision IS NULL) OR (revision > 1 AND prior_revision = revision - 1)')
    check('health_reports',"review_status <> 'confirmed' OR (reviewed_snapshot_ref IS NOT NULL AND (report_kind <> 'individual' OR (record_id IS NOT NULL AND exam_date_verified IS NOT NULL)))")
    check('report_evidence','page_number > 0')
    check('report_evidence','page_rotation IN (0,90,180,270)')
    check('report_evidence',"(coordinate_system = 'object_only' AND bbox IS NULL) OR (coordinate_system <> 'object_only' AND bbox IS NOT NULL AND JSON_TYPE(bbox) = 'ARRAY' AND JSON_LENGTH(bbox) = 4)")
    check('report_evidence','ocr_confidence IS NULL OR (ocr_confidence >= 0 AND ocr_confidence <= 1)')
    check('report_evidence',"extraction_method = 'ocr' OR ocr_confidence IS NULL")
    check('report_evidence',"coordinate_system <> 'image_pixels_top_left' OR (render_dpi IS NOT NULL AND render_dpi > 0)")
    for n in ['health_items','health_conclusions']:
        check(n,"JSON_TYPE(evidence_ids) = 'ARRAY' AND JSON_LENGTH(evidence_ids) > 0")
        check(n,'JSON_CONTAINS(evidence_ids, JSON_QUOTE(primary_evidence_id))')
    check('health_items','ordinal > 0')
    check('health_items',"JSON_TYPE(field_values) = 'ARRAY'")
    for n in ['health_items','medical_field_mapping','medical_unit_mapping']:
        check(n,'(item_code IS NULL) = (dictionary_version IS NULL)')
    check('medical_field_mapping','(template_key IS NULL) = (template_version IS NULL)')
    check('medical_field_mapping',"(entry_kind = 'field' AND field_key IS NOT NULL AND item_code IS NULL) OR (entry_kind = 'item' AND field_key IS NULL AND item_code IS NOT NULL)")
    check('medical_unit_mapping',"status <> 'confirmed' OR (unit_normalized IS NOT NULL AND rule_ref IS NOT NULL AND item_code IS NOT NULL)")
    check('health_items',"mapping_status <> 'confirmed' OR item_code IS NOT NULL")
    check('health_items','(unit_mapping_key IS NULL) = (unit_mapping_version IS NULL)')
    check('health_items','unit_normalized IS NULL OR (unit_mapping_key IS NOT NULL AND normalization_rule_version IS NOT NULL)')
    check('health_items','result_normalized IS NULL OR normalization_rule_version IS NOT NULL')
    check('health_conclusions',"standard_category IS NULL OR (completeness = 'complete' AND review_status = 'confirmed' AND original_text IS NOT NULL)")
    check('health_advice',"(source_kind = 'pdf' AND source_evidence_id IS NOT NULL) OR (source_kind = 'clinician' AND review_id IS NOT NULL AND author_ref IS NOT NULL)")
    check('occupational_profiles','(report_id IS NULL) = (revision IS NULL)')
    check('occupational_profiles','(report_id IS NOT NULL AND evidence_id IS NOT NULL) OR (employee_id IS NOT NULL AND authorized_source_ref IS NOT NULL)')
    check('review_records',"JSON_TYPE(issue_disposition) IN ('ARRAY','OBJECT')")

    def base(n,pk,scoped=False,comment=''):
        ensure(n)['comment']=comment or n
        col(n,pk,ASCII)
        index(n,'PRIMARY',[pk],primary=True)
        if scoped:
            col(n,'scope_code',CODE)
            col(n,'data_mode',MODE)
            index(n,'uq_'+n+'_scope',['scope_code','data_mode',pk],True)
    def timestamp(n,c='created_at'):
        col(n,c,'DATETIME(6)',default='CURRENT_TIMESTAMP(6)',description='UTC时间')
    def scoped_record(n):
        col(n,'record_id',ASCII)
        fk(n,['scope_code','data_mode','record_id'],'health_records',['scope_code','data_mode','record_id'])

    base('report_upload_metadata','upload_id',True,'每次上传的渠道及元数据；同一原件可有多次上传')
    col('report_upload_metadata','source_file_id',ASCII)
    fk('report_upload_metadata',['scope_code','data_mode','source_file_id'],'report_sources',['scope_code','data_mode','source_file_id'])
    for c,t,null in [('request_key','VARCHAR(96) CHARACTER SET ascii COLLATE ascii_bin',False),('upload_channel',"ENUM('miniapp','phone_photo','backend_file','batch_import','legacy_import')",False),('original_file_name','VARCHAR(512)',False),('mime_type','VARCHAR(127)',False),('file_size','BIGINT UNSIGNED',False),('uploader_ref','VARBINARY(256)',False)]:
        col('report_upload_metadata',c,t,null)
    timestamp('report_upload_metadata','uploaded_at')
    index('report_upload_metadata','uq_upload_request',['scope_code','data_mode','request_key'],True)
    index('report_upload_metadata','ix_upload_channel_time',['scope_code','data_mode','upload_channel','uploaded_at'])

    n='standard_field_dictionary'
    ensure(n)['comment']='01表67个字段的版本化定义；必填键不等于报告必有非空值'
    for c,t in [('field_key',CODE),('dictionary_version',CODE),('field_name','VARCHAR(255)'),('field_category','VARCHAR(100)'),('target_table','VARCHAR(64)'),('target_column','VARCHAR(64)'),('json_path','VARCHAR(255)'),('definition','JSON'),('source_row','SMALLINT UNSIGNED')]:
        col(n,c,t)
    index(n,'PRIMARY',['field_key','dictionary_version'],primary=True)
    fk('medical_field_mapping',['field_key','mapping_version'],n,['field_key','dictionary_version'])

    n='dictionary_source_rows'
    ensure(n)['comment']='Excel十二张表的逐行原样快照；校验规则及待确认记录可追溯'
    for c,t in [('workbook_sha256','BINARY(32)'),('sheet_index','TINYINT UNSIGNED'),('source_row','SMALLINT UNSIGNED'),('sheet_name','VARCHAR(100)'),('payload','LONGTEXT'),('payload_sha256','BINARY(32)')]:
        col(n,c,t)
    index(n,'PRIMARY',['workbook_sha256','sheet_index','source_row'],primary=True)
    check(n,'JSON_VALID(payload)')

    n='medical_unit_observations'
    ensure(n)['comment']='390条项目与原单位观察；81个重复单位键无损关联，不是批准规则'
    for c,t in [('dictionary_version',CODE),('source_row','SMALLINT UNSIGNED'),('item_code',CODE),('unit_mapping_key',CODE),('unit_mapping_version',CODE),('unit_raw','VARCHAR(255)'),('approval_status','VARCHAR(30)')]:
        col(n,c,t)
    index(n,'PRIMARY',['dictionary_version','source_row'],primary=True)
    fk(n,['item_code','dictionary_version'],'medical_item_dictionary',['item_code','dictionary_version'])
    fk(n,['unit_mapping_key','unit_mapping_version'],'medical_unit_mapping',['unit_mapping_key','mapping_version'])

    base('report_quality_checks','quality_check_id',True,'报告版本质量校验结果；按快照摘要和规则版本留痕')
    for c,t,null in [('report_id',ASCII,False),('revision','INT UNSIGNED',False),('status',QSTATUS,False),('report_digest','BINARY(32)',False),('identity_status',"ENUM('matched','conflict','ambiguous','unmatched')",False),('issues','JSON',False),('rule_version',CODE,False),('checked_by_ref','VARBINARY(256)',False)]:
        col('report_quality_checks',c,t,null)
    timestamp('report_quality_checks','checked_at')
    fk('report_quality_checks',['scope_code','data_mode','report_id','revision'],'health_reports',['scope_code','data_mode','report_id','revision'])
    index('report_quality_checks','uq_quality_release',['scope_code','data_mode','report_id','revision','quality_check_id','status','report_digest'],True)
    check('report_quality_checks',"status <> 'passed' OR identity_status = 'matched'")
    check('report_quality_checks',"JSON_TYPE(issues) = 'ARRAY'")

    base('report_publications','publication_id',True,'正式归档凭据；仅允许同摘要的质量通过与医生确认版本')
    for c,t in [('report_id',ASCII),('revision','INT UNSIGNED'),('record_id',ASCII),('review_id',ASCII),('review_decision',DECISION),('report_review_status',REVIEW),('quality_check_id',ASCII),('quality_status',QSTATUS),('reviewed_digest','BINARY(32)'),('published_by_ref','VARBINARY(256)')]:
        col('report_publications',c,t)
    timestamp('report_publications','published_at')
    index('report_publications','uq_publication_version',['report_id','revision'],True)
    index('report_publications','uq_publication_record',['scope_code','data_mode','record_id','report_id','revision'],True)
    check('report_publications',"review_decision = 'confirm' AND report_review_status = 'confirmed' AND quality_status = 'passed'")
    fk('report_publications',['scope_code','data_mode','report_id','revision','record_id','report_review_status'],'health_reports',['scope_code','data_mode','report_id','revision','record_id','review_status'])
    fk('report_publications',['report_id','revision','review_id','review_decision','reviewed_digest'],'review_records',['report_id','revision','review_id','decision','reviewed_digest'])
    fk('report_publications',['scope_code','data_mode','report_id','revision','quality_check_id','quality_status','reviewed_digest'],'report_quality_checks',['scope_code','data_mode','report_id','revision','quality_check_id','status','report_digest'])
    fk('report_sync_outbox',['report_id','revision'],'report_publications',['report_id','revision'])

    base('health_risk_assessments','assessment_id',True,'平台风险评估；不替代职业健康正式医学结论')
    scoped_record('health_risk_assessments')
    for c,t,null in [('employee_id',ASCII,False),('assessment_time','DATETIME(6)',False),('risk_score','DECIMAL(12,4)',True),('risk_level',"ENUM('unknown','low','medium','high','critical')",False),('assessment_type',"ENUM('rule','clinician','ai_assisted')",False),('rule_version',CODE,False),('assessment_status',"ENUM('draft','needs_review','confirmed','superseded')",False),('reviewer_ref','VARBINARY(256)',True),('reviewed_at','DATETIME(6)',True)]:
        col('health_risk_assessments',c,t,null)
    fk('health_risk_assessments',['scope_code','data_mode','record_id','employee_id'],'health_records',['scope_code','data_mode','record_id','employee_id'])
    index('health_risk_assessments','uq_assessment_record',['scope_code','data_mode','record_id','assessment_id'],True)
    index('health_risk_assessments','ix_risk_latest',['scope_code','data_mode','record_id','assessment_status','assessment_time'])
    check('health_risk_assessments',"assessment_status <> 'confirmed' OR (reviewer_ref IS NOT NULL AND reviewed_at IS NOT NULL AND risk_level <> 'unknown')")

    n='health_risk_report_sources'
    ensure(n)['comment']='风险评估所依据的已发布报告版本；保证同一职工与档案'
    for c,t in [('scope_code',CODE),('data_mode',MODE),('record_id',ASCII),('assessment_id',ASCII),('report_id',ASCII),('revision','INT UNSIGNED')]:
        col(n,c,t)
    index(n,'PRIMARY',['assessment_id','report_id','revision'],primary=True)
    fk(n,['scope_code','data_mode','record_id','assessment_id'],'health_risk_assessments',['scope_code','data_mode','record_id','assessment_id'])
    fk(n,['scope_code','data_mode','record_id','report_id','revision'],'report_publications',['scope_code','data_mode','record_id','report_id','revision'])

    base('health_risk_factors','factor_id',False,'可解释的风险因素；追溯已发布报告、指标和原页证据')
    for c,t,null in [('assessment_id',ASCII,False),('report_id',ASCII,False),('revision','INT UNSIGNED',False),('item_id',ASCII,True),('evidence_id',ASCII,False),('factor_name','VARCHAR(255)',False),('result_raw','MEDIUMTEXT',True),('reason','TEXT',False),('contribution','DECIMAL(12,4)',True)]:
        col('health_risk_factors',c,t,null)
    fk('health_risk_factors',['assessment_id','report_id','revision'],'health_risk_report_sources',['assessment_id','report_id','revision'])
    fk('health_risk_factors',['report_id','revision','item_id'],'health_items',['report_id','revision','item_id'])
    fk('health_risk_factors',['report_id','revision','evidence_id'],'report_evidence',['report_id','revision','evidence_id'])

    base('health_record_completeness','snapshot_id',True,'完整度规则计算快照；不是样本PDF字段覆盖率')
    scoped_record('health_record_completeness')
    for c,t in [('required_count','INT UNSIGNED'),('completed_count','INT UNSIGNED'),('missing_items','JSON'),('rule_version',CODE),('basis','JSON')]:
        col('health_record_completeness',c,t)
    col('health_record_completeness','completeness_rate','DECIMAL(7,4)',True,description='0至1；required_count为0时NULL')
    timestamp('health_record_completeness','calculated_at')
    index('health_record_completeness','ix_completeness_latest',['scope_code','data_mode','record_id','calculated_at'])
    check('health_record_completeness','completed_count <= required_count')
    check('health_record_completeness','(required_count = 0 AND completeness_rate IS NULL) OR (required_count > 0 AND completeness_rate IS NOT NULL AND completeness_rate = ROUND(completed_count / required_count,4))')
    check('health_record_completeness',"JSON_TYPE(missing_items) = 'ARRAY' AND JSON_LENGTH(missing_items) = required_count - completed_count")

    base('health_archive_audit_logs','audit_id',True,'档案业务审计补充；不替换旧平台日志，避免记录明文隐私')
    for c,t,null in [('record_id',ASCII,True),('report_id',ASCII,True),('revision','INT UNSIGNED',True),('actor_ref','VARBINARY(256)',False),('action','VARCHAR(64)',False),('request_key','VARCHAR(96) CHARACTER SET ascii COLLATE ascii_bin',False),('reason','TEXT',True),('redacted_diff','JSON',True)]:
        col('health_archive_audit_logs',c,t,null)
    timestamp('health_archive_audit_logs','occurred_at')
    fk('health_archive_audit_logs',['scope_code','data_mode','record_id'],'health_records',['scope_code','data_mode','record_id'])
    fk('health_archive_audit_logs',['scope_code','data_mode','report_id','revision'],'health_reports',['scope_code','data_mode','report_id','revision'])
    check('health_archive_audit_logs','(report_id IS NULL) = (revision IS NULL)')
    index('health_archive_audit_logs','uq_audit_request',['scope_code','data_mode','request_key','action'],True)

    n='health_schema_migration'
    ensure(n)['comment']='本模块结构迁移版本及输入校验摘要'
    for c,t in [('version','VARCHAR(64)'),('checksum','CHAR(64) CHARACTER SET ascii COLLATE ascii_bin'),('source_sha256','CHAR(64) CHARACTER SET ascii COLLATE ascii_bin'),('description','VARCHAR(500)'),('applied_by','VARCHAR(255)')]:
        col(n,c,t)
    timestamp(n,'applied_at')
    index(n,'PRIMARY',['version'],primary=True)

    sql=['-- EHR v2.0.0 local physical schema; reconstructed from supplied workbook sheet 08.',
         '-- All new tables use a_. Never DROP or modify legacy tables.',
         '-- Source workbook SHA256: '+json.loads((REF/'manifest.json').read_text(encoding='utf-8'))['sha256'],
         '-- Core columns/types retained. Local indexes/checks/extensions documented in docs/ehr-database-architecture.md.']
    for n,t in tables.items():
        defs=[]
        for c in t['columns']:
            s=c['name']+' '+c['type']+(' NULL' if c['nullable'] else ' NOT NULL')
            if c['default'] is not None:
                s+=' DEFAULT '+c['default']
            s+=' COMMENT '+literal(c['comment'])
            defs.append(s)
        for k,v in t['indexes'].items():
            s='PRIMARY KEY' if v['primary'] else ('UNIQUE KEY ' if v['unique'] else 'KEY ')+k
            defs.append(s+' ('+', '.join(v['columns'])+')')
        for i,expr in enumerate(t['checks']):
            defs.append('CONSTRAINT ck_a_'+n+'_'+str(i+1)+' CHECK ('+expr+')')
        sql.append('CREATE TABLE '+t['name']+' (\n  '+',\n  '.join(defs)+'\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='+literal(t['comment'])+';')
    for n,cs,target,rs in fks:
        name='fk_a_'+hashlib.sha1(js([n,cs,target,rs]).encode()).hexdigest()[:18]
        sql.append('ALTER TABLE '+table_name(n)+' ADD CONSTRAINT '+name+' FOREIGN KEY ('+', '.join(cs)+') REFERENCES '+table_name(target)+' ('+', '.join(rs)+') ON UPDATE RESTRICT ON DELETE RESTRICT;')
    seed_counts=Counter()
    seed_values=defaultdict(list)
    def seed(n,cols,values):
        seed_values[(n,tuple(cols))].append(values)
        seed_counts[table_name(n)]+=1
    manifest=json.loads((REF/'manifest.json').read_text(encoding='utf-8'))
    for sheet in manifest['sheets']:
        s=manifest['sheets'].index(sheet)
        for r in rows(s):
            row=r['_row']; payload={k:v for k,v in r.items() if k!='_row'}
            raw=js(payload)
            seed('dictionary_source_rows',['workbook_sha256','sheet_index','source_row','sheet_name','payload','payload_sha256'],[bytes.fromhex(manifest['sha256']),s,row,sheet['name'],raw,hashlib.sha256(raw.encode()).digest()])
    for r in rows(1):
        seed('standard_field_dictionary',['field_key','dictionary_version','field_name','field_category','target_table','target_column','json_path','definition','source_row'],[r['field_code'],VERSION,r['field_name'],r['field_category'],table_name(r['db_table']),r['db_column'],r['json_path'],js({k:v for k,v in r.items() if k!='_row'}),r['_row']])
    for r in rows(2):
        seed('medical_item_dictionary',['item_code','dictionary_version','standard_name','semantic_scope','status','validation_ref'],[r['item_code'],VERSION,r['standard_item_name'],r['remark'],r['mapping_status'],f"xlsx:02:row:{r['_row']} (not clinical approval)".encode()])
    for r in rows(3):
        isfield=r['entry_kind']=='field'
        lookup=re.sub(r'\s+','',unicodedata.normalize('NFKC',r['original_field_name']))
        seed('medical_field_mapping',['mapping_key','mapping_version','entry_kind','institution_key','original_label','normalized_lookup','field_key','item_code','dictionary_version','status','validation_ref'],[r['mapping_key'],r['mapping_version'],r['entry_kind'],r['institution_name'],r['original_field_name'],lookup,r['target_code'] if isfield else None,None if isfield else r['target_code'],None if isfield else VERSION,r['mapping_status'],f"xlsx:03:row:{r['_row']}".encode()])
    seen={}
    for r in rows(4):
        k=r['unit_mapping_key']
        if k not in seen:
            seed('medical_unit_mapping',['unit_mapping_key','mapping_version','unit_raw','status'],[k,VERSION,r['original_unit'],'observed_raw_only'])
            seen[k]=r['original_unit']
        assert seen[k]==r['original_unit']
        assert r['approval_status']=='未批准' and r['conversion_rule'] is None
        seed('medical_unit_observations',['dictionary_version','source_row','item_code','unit_mapping_key','unit_mapping_version','unit_raw','approval_status'],[VERSION,r['_row'],r['item_code'],k,VERSION,r['original_unit'],r['approval_status']])
    # Seeds must respect dependencies: source rows, standard fields, items, aliases, units, observations.
    for (n,cols),values in seed_values.items():
        for start in range(0,len(values),40):
            def val(v):
                return '0x'+v.hex() if isinstance(v,bytes) else literal(v)
            sql.append('INSERT INTO '+table_name(n)+' ('+', '.join(cols)+') VALUES\n'+',\n'.join('('+', '.join(val(v) for v in row)+')' for row in values[start:start+40])+';')
    # Local guards are not claimed to be present in the unprovided frozen SQL.
    # They protect reviewed versions and prohibit using observed-only unit keys.
    triggers=[]
    def trigger(n,operation,body,label):
        name='a_ehr_'+hashlib.sha1((n+operation+label).encode()).hexdigest()[:18]
        triggers.append(dict(name=name,table=table_name(n),operation=operation,purpose=label))
        sql.append('CREATE TRIGGER '+name+' BEFORE '+operation+' ON '+table_name(n)+' FOR EACH ROW\nBEGIN\n'+body+'\nEND$$')
    signal="SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';"
    sql.append('DELIMITER $$')
    for n in ['report_sources','review_records','report_quality_checks','report_publications']:
        for op in ['UPDATE','DELETE']:
            trigger(n,op,signal,'append_only')
    for n in ['report_evidence','health_items','health_conclusions','health_advice','occupational_profiles','report_revision_sources']:
        for op in ['INSERT','UPDATE','DELETE']:
            ref='OLD' if op=='DELETE' else 'NEW'
            cond=f'EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id={ref}.report_id AND r.revision={ref}.revision)'
            if op=='UPDATE':
                cond+= ' OR EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision)'
            body='IF '+cond+' THEN\n'+signal+'\nEND IF;'
            if n=='health_items' and op!='DELETE':
                body+="\nIF NEW.unit_normalized IS NOT NULL AND NOT EXISTS (SELECT 1 FROM a_medical_unit_mapping m WHERE m.unit_mapping_key=NEW.unit_mapping_key AND m.mapping_version=NEW.unit_mapping_version AND m.status='confirmed' AND m.item_code=NEW.item_code AND m.dictionary_version=NEW.dictionary_version) THEN\nSIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR unit conversion requires approved item-specific mapping';\nEND IF;"
            trigger(n,op,body,'freeze_reviewed_version')
    cols=[c['name'] for c in tables['health_reports']['columns'] if c['name'] not in ['review_status','reviewed_snapshot_ref']]
    changes=' OR '.join('NOT (NEW.'+c+' <=> OLD.'+c+')' for c in cols)
    body="IF OLD.review_status IN ('confirmed','rejected') OR (EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) AND ("+changes+')) THEN\n'+signal+'\nEND IF;'
    trigger('health_reports','UPDATE',body,'freeze_reviewed_payload')
    trigger('health_reports','DELETE','IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN\n'+signal+'\nEND IF;','freeze_reviewed_payload')
    trigger('report_sync_outbox','INSERT',"IF NOT EXISTS (SELECT 1 FROM a_report_publications p WHERE p.report_id=NEW.report_id AND p.revision=NEW.revision AND p.review_id=NEW.review_id) THEN\nSIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR outbox must reference the confirming publication review';\nEND IF;",'publication_review_match')
    sql.append('DELIMITER ;')
    out=BASE/'migrations/V001__a_ehr_v2_0_0.sql'
    out.write_text('\n\n'.join(sql)+'\n',encoding='utf-8')
    spec=dict(source=manifest,core_table_count=19,table_count=len(tables),tables=list(tables.values()),foreign_keys=[dict(table=table_name(n),columns=cs,target=table_name(t),target_columns=rs) for n,cs,t,rs in fks],triggers=triggers,seed_counts=dict(seed_counts))
    (BASE/'ehr-schema-manifest.json').write_text(json.dumps(spec,ensure_ascii=False,indent=2),encoding='utf-8')
    with (BASE/'reference/v2.0.0/standard-fields-physical-mapping.csv').open('w',encoding='utf-8-sig',newline='') as f:
        w=csv.writer(f); w.writerow(['source_row','field_key','field_name','category','physical_table','column','json_path','nullable','note'])
        for r in rows(1):
            w.writerow([r['_row'],r['field_code'],r['field_name'],r['field_category'],table_name(r['db_table']),r['db_column'],r['json_path'],r['nullable'],r['remark']])
    print(json.dumps(dict(tables=len(tables),core=19,fks=len(fks),seed_counts=dict(seed_counts),sql_bytes=out.stat().st_size),ensure_ascii=False))

if __name__=='__main__':
    main()
