"""PDF upload regression with synthetic fixtures; no formal employee data changes."""
from pathlib import Path
import hashlib, io, json, os, sys, uuid
import requests
from pypdf import PdfReader, PdfWriter
from reportlab.pdfgen import canvas

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / 'database'))
from health_archive_db import connect

BASE = os.getenv('EHR_TEST_URL', 'http://localhost:18081')
STORAGE = Path('F:/workstation/.tools/runtime/ehr-test-private')
s = requests.Session()
s.trust_env = False
s.get(BASE + '/login', timeout=15)
assert s.post(BASE + '/login', data={
    'username': '\u533b\u751f\u533b\u751f', 'password': os.environ['EHR_TEST_PASSWORD'],
    'rememberMe': 'false', 'code': '123465'
}, timeout=25).json()['code'] == 200
ctx = s.get(BASE + '/ehr/api/context', timeout=15).json()['data']
assert ctx['dataMode'] == 'synthetic'
headers = {'X-CSRF-Token': ctx['csrfToken']}
checks = []
record = None
extra_records = []

def check(name, value=True):
    assert value, name
    checks.append(name)
    print('PASS', name, flush=True)

def api(method, path, body=None):
    r = s.request(method, BASE + '/ehr/api' + path, json=body, headers=headers, timeout=30)
    assert r.status_code == 200, (r.status_code, r.text[:200])
    return r.json()['data']

def count_sources():
    c = connect()
    try:
        with c.cursor() as cur:
            cur.execute('SELECT COUNT(*) AS n FROM a_report_sources WHERE scope_code=%s AND data_mode=%s',
                        (ctx['scope'], 'synthetic'))
            return cur.fetchone()['n']
    finally:
        c.close()

def post(raw, name='fixture.pdf', key=None, expected=200, target=None):
    pending = set(STORAGE.glob('pending-*.bin'))
    r = s.post(BASE + '/ehr/api/records/' + (target or record['id']) + '/reports',
               headers=dict(headers, **{'Idempotency-Key': key or str(uuid.uuid4())}),
               files={'file': (name, raw, 'application/pdf')}, timeout=40)
    assert r.status_code == expected, (r.status_code, r.text[:300])
    assert set(STORAGE.glob('pending-*.bin')) == pending, 'staging file leaked'
    return r.json().get('data') if expected == 200 else r.json()

def fixture(label, user=None):
    buf = io.BytesIO()
    c = canvas.Canvas(buf)
    c.drawString(30, 740, 'SYNTHETIC UPLOAD ' + uuid.uuid4().hex + label)
    c.showPage()
    c.drawString(30, 740, 'SECOND SYNTHETIC PAGE')
    c.showPage()
    c.save()
    if user is None:
        return buf.getvalue()
    w = PdfWriter()
    w.append_pages_from_reader(PdfReader(io.BytesIO(buf.getvalue())))
    w.encrypt(user_password=user, owner_password=uuid.uuid4().hex, algorithm='AES-128')
    out = io.BytesIO()
    w.write(out)
    return out.getvalue()

try:
    record = api('POST', '/records', {'name': 'PDF upload fixture', 'code': 'PDF_' + uuid.uuid4().hex[:15],
                                    'sex': '\u672a\u586b\u5199', 'org': 'Synthetic PDF validation',
                                    'team': 'Fixture', 'job': 'Fixture'})
    raw = fixture('plain')
    key = str(uuid.uuid4())
    plain = post(raw, key=key)
    check('plain_pdf_upload_creates_queue', plain['ocrTask']['state'] == 'queued')
    check('plain_pdf_two_pages', plain['files'][0]['pageCount'] == 2)
    check('same_idempotency_key_reuses_report', post(raw, key=key)['id'] == plain['id'])
    owner = fixture('owner-encrypted', '')
    check('owner_permission_fixture_is_encrypted', PdfReader(io.BytesIO(owner)).is_encrypted)
    owner_key = str(uuid.uuid4())
    encrypted = post(owner, key=owner_key)
    check('password_free_encrypted_pdf_upload_succeeds', encrypted['ocrTask']['state'] == 'queued')
    check('encrypted_pdf_page_count', encrypted['files'][0]['pageCount'] == 2)
    r = s.get(BASE + '/ehr/api/files/' + encrypted['files'][0]['id'], timeout=20)
    check('original_encrypted_bytes_are_preserved', r.status_code == 200 and r.content == owner)
    before = count_sources()
    source_files = set(STORAGE.glob('*.source'))
    bad = post(fixture('password-required', 'not-an-empty-password'), expected=422)
    check('password_required_pdf_has_explicit_error', '\u6253\u5f00\u5bc6\u7801' in bad['msg'])
    post(b'%PDF-1.7\ninvalid document', expected=422)
    check('malformed_pdf_rejected_without_staging_leak')
    post(b'not a real pdf', expected=422)
    check('fake_extension_rejected_without_staging_leak')
    # Rejection after the file has moved must also roll back the source and queue.
    post(fixture('transaction-rollback'), name='x' * 501 + '.pdf', expected=422)
    check('rollback_removes_moved_original', set(STORAGE.glob('*.source')) == source_files)
    check('rejected_uploads_create_no_source_rows', count_sources() == before)
    check('upload_does_not_publish_or_approve', not encrypted['published'] and encrypted['reviewStatus'] == 'unreviewed')
    # Reproduce the approved maintenance removal without deleting immutable history.
    c = connect()
    try:
        with c.cursor() as q:
            q.execute("UPDATE a_ocr_dispatch_tasks SET dispatch_state='cancelled',lease_until=NULL WHERE job_id=%s AND data_mode='synthetic'", (encrypted['jobId'],))
            q.execute("UPDATE a_report_series SET current_revision=NULL WHERE report_id=%s AND data_mode='synthetic'", (encrypted['id'],))
            assert q.rowcount == 1
            q.execute("UPDATE a_health_reports SET record_id=NULL WHERE report_id=%s AND revision=1 AND data_mode='synthetic'", (encrypted['id'],))
            assert q.rowcount == 1
            q.execute("INSERT INTO a_health_archive_audit_logs (audit_id,scope_code,data_mode,record_id,actor_ref,action,request_key,reason,redacted_diff,occurred_at) VALUES (%s,%s,'synthetic',%s,%s,'misassigned_report_unlinked',%s,'Synthetic removal fixture',%s,UTC_TIMESTAMP(6))",
                      (str(uuid.uuid4()),ctx['scope'],record['id'],b'synthetic-test',str(uuid.uuid4()),json.dumps({'removedReportId':encrypted['id']})))
        c.commit()
    finally:
        c.close()
    other = api('POST', '/records', {'name':'PDF reupload fixture','code':'PDFR_'+uuid.uuid4().hex[:14],
                 'sex':'\u672a\u586b\u5199','org':'Synthetic PDF validation','team':'Fixture','job':'Fixture'})
    extra_records.append(other)
    new_key = str(uuid.uuid4())
    fresh = post(owner,key=new_key,target=other['id'])
    check('removed_original_can_be_uploaded_to_another_employee', fresh['recordId']==other['id'])
    check('reupload_creates_fresh_revision_and_queue',fresh['id']==encrypted['id'] and fresh['revision']==2 and fresh['jobId']!=encrypted['jobId'] and fresh['ocrTask']['state']=='queued')
    check('reupload_does_not_reuse_previous_candidate_results',fresh['items']==[] and fresh['fieldValues']==[] and not fresh['published'])
    again=post(owner,key=new_key,target=other['id'])
    check('reupload_idempotency_resolves_new_revision',again['revision']==2 and again['jobId']==fresh['jobId'])
    post(owner,key=owner_key,target=other['id'],expected=409)
    check('removed_upload_key_cannot_rebind_historical_revision')
    post(owner,expected=409)
    check('active_other_employee_binding_still_protected')
    response=s.get(BASE+'/ehr/api/files/'+fresh['files'][0]['id'],timeout=20)
    check('reuploaded_original_download_ignores_unlinked_old_revision',response.status_code==200 and response.content==owner)
    result = {'checks': checks, 'count': len(checks), 'mode': 'synthetic',
              'realEmployeeModified': False, 'ocrEngineCalled': False}
    (ROOT / 'database/pdf-upload-verification.json').write_text(json.dumps(result, indent=2), encoding='utf-8')
finally:
    for created in ([record] if record else []) + extra_records:
        current = api('GET', '/records/' + created['id'])
        api('DELETE', '/records/' + created['id'], {'rowVersion': current['rowVersion'],
                                                'reason': 'Synthetic PDF upload regression complete'})
