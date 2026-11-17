"""Meaningful InnoDB integration checks. All business fixtures are rolled back."""
import hashlib
import json
import uuid
from pathlib import Path
import pymysql

BASE=Path(__file__).resolve().parent

def seed_checks(c):
    spec=json.loads((BASE/'ehr-schema-manifest.json').read_text(encoding='utf-8'))
    with c.cursor() as q:
        for table,expected in spec['seed_counts'].items():
            q.execute('SELECT COUNT(*) n FROM '+table)
            assert q.fetchone()['n']==expected,(table,expected)
        q.execute('SELECT status,COUNT(*) n FROM a_medical_item_dictionary GROUP BY status')
        assert {r['status']:r['n'] for r in q.fetchall()}=={'lexical_only':532,'needs_confirmation':123}
        q.execute("SELECT COUNT(*) n FROM a_medical_unit_mapping WHERE status='confirmed' OR rule_ref IS NOT NULL OR unit_normalized IS NOT NULL")
        assert q.fetchone()['n']==0
        q.execute('SELECT sheet_index,source_row,payload,payload_sha256 FROM a_dictionary_source_rows')
        actual=q.fetchall()
        by_sheet={}
        for s in spec['source']['sheets']:
            a=json.loads((BASE/'reference/v2.0.0'/s['file']).read_text(encoding='utf-8'))
            by_sheet[spec['source']['sheets'].index(s)]={r['row']:dict(zip(a[2]['cells'],r['cells'])) for r in a[3:]}
        for r in actual:
            expected=by_sheet[r['sheet_index']][r['source_row']]
            assert json.loads(r['payload'])==expected,(r['sheet_index'],r['source_row'])
            raw=json.dumps(expected,ensure_ascii=False,separators=(',',':')).encode()
            assert r['payload_sha256']==hashlib.sha256(raw).digest()
        q.execute('SELECT DISTINCT target_table,target_column FROM a_standard_field_dictionary')
        for row in q.fetchall():
            q.execute('SELECT COUNT(*) n FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=%s AND COLUMN_NAME=%s',(row['target_table'],row['target_column']))
            assert q.fetchone()['n']==1,row
    return ['dictionary_counts_and_pending_statuses','all_3739_source_rows_equal_workbook','67_physical_field_targets_exist','zero_approved_unit_conversions']

def smoke(c):
    c.rollback()
    passed=[]
    u=lambda:str(uuid.uuid4())
    org='__ehr_schema_test_'+uuid.uuid4().hex[:12]
    employee,record,source,report,job=[u() for _ in range(5)]
    actor=b'schema-validation-only'
    def ex(sql,args=()):
        with c.cursor() as q:
            q.execute(sql,args)
            return q.lastrowid
    def one(sql,args=()):
        with c.cursor() as q:
            q.execute(sql,args)
            return q.fetchone()
    def ins(table,**kw):
        return ex('INSERT INTO a_'+table+' ('+','.join(kw)+') VALUES ('+','.join(['%s']*len(kw))+')',tuple(kw.values()))
    def reject(label,fn,code):
        try:
            fn()
        except pymysql.MySQLError as e:
            assert e.args[0]==code,(label,e.args)
            passed.append(label)
        else:
            raise AssertionError('Expected DB rejection: '+label)
    def scoped(**kw):
        return dict(scope_code=org,data_mode='synthetic',**kw)
    try:
        ins('employees',**scoped(employee_id=employee,roster_employee_key='fixture-user',identity_ref=b'authorized-identity-token',roster_source_ref=b'legacy:yh_user_reginfo:fixture-only'))
        ins('health_records',**scoped(record_id=record,employee_id=employee))
        reject('one_main_record_per_employee',lambda:ins('health_records',**scoped(record_id=u(),employee_id=employee)),1062)
        reject('tenant_isolation',lambda:ins('health_records',record_id=u(),scope_code=org+'_other',data_mode='synthetic',employee_id=employee),1452)
        reject('real_synthetic_isolation',lambda:ins('health_records',record_id=u(),scope_code=org,data_mode='real',employee_id=employee),1452)
        ins('report_sources',**scoped(source_file_id=source,sha256=hashlib.sha256(org.encode()).digest(),object_ref=b'validation-only/not-a-real-file',page_count=2))
        reject('sha256_dedup_scoped',lambda:ins('report_sources',**scoped(source_file_id=u(),sha256=hashlib.sha256(org.encode()).digest(),object_ref=b'duplicate',page_count=2)),1062)
        ins('report_upload_metadata',**scoped(upload_id=u(),source_file_id=source,request_key=org,upload_channel='phone_photo',original_file_name='validation-only.pdf',mime_type='application/pdf',file_size=42,uploader_ref=actor))
        ins('report_series',**scoped(report_id=report,source_file_id=source,current_revision=None))
        ins('extraction_jobs',**scoped(job_id=job,report_id=report,request_key=org,pipeline_version='validation-only',status='uploaded'))
        digest=hashlib.sha256(b'validation-snapshot-v3').digest()
        reviews={}
        evidences={}
        review_args={}
        for rev,decision in [(1,'return'),(2,'reject'),(3,'confirm')]:
            evidence=u(); evidences[rev]=evidence
            fields=json.dumps([dict(field_key='full_name',original_field_name='姓名',value_raw='报告姓名快照（测试）',value_normalized=None,normalization_rule_version=None,evidence_ids=[evidence],review_status='needs_review')],ensure_ascii=False)
            ins('health_reports',**scoped(report_id=report,revision=rev,prior_revision=rev-1 if rev>1 else None,record_id=record,report_kind='individual',exam_date_raw='2026年11月14日',exam_date_verified='2026-11-14',age_raw='45岁',field_values=fields,dictionary_version='2.0.0',review_status='needs_review'))
            ins('report_revision_sources',**scoped(report_id=report,revision=rev,source_file_id=source))
            ins('report_evidence',report_id=report,revision=rev,evidence_id=evidence,source_file_id=source,page_number=1,bbox='[10,20,100,40]',coordinate_system='pdf_points_unrotated',page_rotation=0,original_label='体检结论',raw_fragment='仅用于事务测试',extraction_method='native_text',extractor_version='validation-only',review_status='needs_review')
            review=u(); reviews[rev]=review
            review_args[rev]=dict(review_id=review,report_id=report,revision=rev,reviewer_ref=actor,decision=decision,reviewed_digest=digest,decision_key=org+str(rev),issue_disposition='[]')
            if rev<3:
                ins('review_records',**review_args[rev])
        reject('version_chain_no_skips',lambda:ins('health_reports',**scoped(report_id=report,revision=5,prior_revision=3,record_id=record,report_kind='individual',field_values='[]',dictionary_version='2.0.0',review_status='unreviewed')),3819)
        ex('UPDATE a_report_series SET current_revision=3 WHERE report_id=%s',(report,))
        reject('current_revision_must_exist',lambda:ex('UPDATE a_report_series SET current_revision=99 WHERE report_id=%s',(report,)),1452)
        reject('outbox_requires_publication',lambda:ins('report_sync_outbox',event_id=u(),report_id=report,revision=3,review_id=reviews[3],status='pending'),1644)
        evidence=evidences[3]
        item=u()
        # Composite and qualitative strings must be stored verbatim, without implicit numeric conversion.
        iargs=dict(report_id=report,revision=3,item_id=item,ordinal=1,primary_evidence_id=evidence,evidence_ids=json.dumps([evidence]),original_field_name='血压',result_raw='＜120/80（原文）',unit_raw='mmHg',reference_range_raw='详见原页',abnormal_flag_raw='↑?',field_values='[]',mapping_status='unmapped',review_status='needs_review')
        ins('health_items',**iargs)
        saved=one('SELECT result_raw,unit_raw,reference_range_raw,result_normalized,unit_normalized FROM a_health_items WHERE report_id=%s AND revision=3 AND item_id=%s',(report,item))
        assert saved==dict(result_raw='＜120/80（原文）',unit_raw='mmHg',reference_range_raw='详见原页',result_normalized=None,unit_normalized=None)
        reject('normalization_requires_rule_version',lambda:ex("UPDATE a_health_items SET result_normalized='{}' WHERE report_id=%s AND revision=3 AND item_id=%s",(report,item)),3819)
        reject('unit_conversion_requires_mapping_version',lambda:ex("UPDATE a_health_items SET unit_normalized='kPa' WHERE report_id=%s AND revision=3 AND item_id=%s",(report,item)),1644)
        reject('item_evidence_cannot_cross_version',lambda:ins('health_items',**dict(iargs,item_id=u(),ordinal=2,primary_evidence_id=evidences[2],evidence_ids=json.dumps([evidences[2]]))),1452)
        reject('unapproved_unit_rule_cannot_be_confirmed',lambda:ex("UPDATE a_medical_unit_mapping SET status='confirmed' WHERE unit_mapping_key=(SELECT k FROM (SELECT MIN(unit_mapping_key) k FROM a_medical_unit_mapping) x)"),3819)
        ins('occupational_profiles',**scoped(profile_id=u(),report_id=report,revision=3,employee_id=employee,evidence_id=evidence,field_key='job_type',original_field_name='工种',value_raw='测试岗位',review_status='needs_review'))
        ins('health_conclusions',report_id=report,revision=3,conclusion_id=u(),primary_evidence_id=evidence,evidence_ids=json.dumps([evidence]),original_field_name='体检结论',original_text='仅用于结构校验，不构成医学结论',completeness='unknown',review_status='needs_review')
        advice=u()
        ins('health_advice',report_id=report,revision=3,advice_id=advice,source_kind='pdf',source_evidence_id=evidence,original_field_name='医师建议',text_raw='测试建议',review_status='needs_review')
        ins('followup_tasks',task_id=u(),report_id=report,revision=3,advice_id=advice,status='pending')
        ins('review_records',**review_args[3])
        quality=u()
        qargs=scoped(quality_check_id=quality,report_id=report,revision=3,status='passed',report_digest=digest,identity_status='matched',issues='[]',rule_version='validation-only',checked_by_ref=actor)
        reject('identity_conflict_cannot_pass',lambda:ins('report_quality_checks',**dict(qargs,quality_check_id=u(),identity_status='conflict')),3819)
        ins('report_quality_checks',**qargs)
        pubargs=scoped(publication_id=u(),report_id=report,revision=3,record_id=record,review_id=reviews[3],review_decision='confirm',report_review_status='confirmed',quality_check_id=quality,quality_status='passed',reviewed_digest=digest,published_by_ref=actor)
        reject('unconfirmed_report_cannot_publish',lambda:ins('report_publications',**pubargs),1452)
        reject('confirmation_requires_reviewed_snapshot',lambda:ex("UPDATE a_health_reports SET review_status='confirmed' WHERE report_id=%s AND revision=3",(report,)),3819)
        ex("UPDATE a_health_reports SET review_status='confirmed',reviewed_snapshot_ref=%s WHERE report_id=%s AND revision=3",(b'validation-snapshot-ref',report))
        reject('return_is_not_confirm',lambda:ins('report_publications',**dict(pubargs,review_decision='return')),3819)
        reject('review_cannot_cross_version',lambda:ins('report_publications',**dict(pubargs,review_id=reviews[2])),1452)
        reject('quality_review_digest_must_match',lambda:ins('report_publications',**dict(pubargs,reviewed_digest=b'\x00'*32)),1452)
        ins('report_publications',**pubargs)
        ins('report_sync_outbox',event_id=u(),report_id=report,revision=3,review_id=reviews[3],status='pending')
        reject('publication_idempotency',lambda:ins('report_publications',**dict(pubargs,publication_id=u())),1062)
        reject('outbox_idempotency',lambda:ins('report_sync_outbox',event_id=u(),report_id=report,revision=3,review_id=reviews[3],status='pending'),1062)
        passed.append('doctor_confirm_quality_pass_publication_and_outbox')
        assessment=u()
        ins('health_risk_assessments',**scoped(assessment_id=assessment,record_id=record,employee_id=employee,assessment_time='2026-11-14 00:00:00',risk_level='unknown',assessment_type='rule',rule_version='validation-only-no-clinical-rules',assessment_status='draft'))
        ins('health_risk_report_sources',**scoped(record_id=record,assessment_id=assessment,report_id=report,revision=3))
        ins('health_risk_factors',factor_id=u(),assessment_id=assessment,report_id=report,revision=3,item_id=item,evidence_id=evidence,factor_name='结构校验因素',result_raw=iargs['result_raw'],reason='不执行临床评分')
        ins('health_record_completeness',**scoped(snapshot_id=u(),record_id=record,required_count=4,completed_count=3,missing_items='["test_missing_document"]',rule_version='validation-only',basis='{}',completeness_rate='0.75'))
        reject('completeness_not_sample_frequency',lambda:ins('health_record_completeness',**scoped(snapshot_id=u(),record_id=record,required_count=4,completed_count=3,missing_items='["test"]',rule_version='validation-only',basis='{}',completeness_rate='0.92')),3819)
        ins('health_archive_audit_logs',**scoped(audit_id=u(),record_id=record,report_id=report,revision=3,actor_ref=actor,action='schema_validation',request_key=org,redacted_diff='{}'))
        unchanged=one('SELECT identity_ref,roster_employee_key FROM a_employees WHERE employee_id=%s',(employee,))
        assert unchanged==dict(identity_ref=b'authorized-identity-token',roster_employee_key='fixture-user')
        assert one('SELECT COUNT(*) n FROM a_review_records WHERE report_id=%s',(report,))['n']==3
        reject('reviewed_payload_immutable',lambda:ex("UPDATE a_health_reports SET age_raw='99' WHERE report_id=%s AND revision=3",(report,)),1644)
        reject('reviewed_item_immutable',lambda:ex("UPDATE a_health_items SET result_raw='999' WHERE report_id=%s AND revision=3 AND item_id=%s",(report,item)),1644)
        reject('review_history_append_only',lambda:ex("DELETE FROM a_review_records WHERE review_id=%s",(reviews[1],)),1644)
        reject('original_file_immutable',lambda:ex("UPDATE a_report_sources SET object_ref='changed' WHERE source_file_id=%s",(source,)),1644)
        passed.extend(['raw_strings_nulls_and_report_identity_snapshot_preserved','return_reject_confirm_history_preserved','risk_formal_conclusion_separate_and_provenance_linked','upload_channels_completeness_and_audit_crud'])
    finally:
        c.rollback()
    assert one('SELECT COUNT(*) n FROM a_employees WHERE scope_code=%s',(org,))['n']==0
    passed.append('all_business_test_fixtures_rolled_back')
    return passed
