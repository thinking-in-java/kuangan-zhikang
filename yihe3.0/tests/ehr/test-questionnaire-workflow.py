"""Questionnaire real workflow checks. All writes run in a rolled-back preview transaction.
Apply V024 and V036 first; supply EHR_JDBC_URL, EHR_DB_USER, EHR_DB_PASSWORD,
and EHR_JDK_BIN. Never points at the live business database.
"""
import os
import subprocess
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
for key in ('EHR_JDBC_URL', 'EHR_DB_USER', 'EHR_DB_PASSWORD', 'EHR_JDK_BIN'):
    if not os.environ.get(key):
        raise RuntimeError('Missing ' + key)
if '/shayu_3_preview?' not in os.environ['EHR_JDBC_URL']:
    raise RuntimeError('This runner requires the isolated preview database')
classes = ROOT / 'yihe-framework/target/questionnaire-test-classes'
classes.mkdir(exist_ok=True)
cp = ';'.join(str(ROOT / p) for p in (
    'yihe-framework/target/classes', 'yihe-system/target/classes',
    'yihe-common/target/classes', 'yihe-admin/target/yihe-admin/WEB-INF/lib/*'))
names = ['HealthAgentChecks', 'OcrRosterIdentityChecks', 'ArchiveReviewChecks',
         'QuestionnaireChecks', 'QuestionnaireWorkflowChecks']
jdk = Path(os.environ['EHR_JDK_BIN'])
subprocess.run([str(jdk / 'javac.exe'), '-encoding', 'UTF-8', '-cp', cp,
                '-d', str(classes)] + [str(ROOT / ('tests/ehr/java/' + n + '.java')) for n in names], check=True)
for name in ['QuestionnaireChecks', 'QuestionnaireWorkflowChecks']:
    subprocess.run([str(jdk / 'java.exe'), '-Dfile.encoding=UTF-8', '-cp',
                    str(classes) + ';' + cp, 'com.yihe.framework.ehr.' + name], check=True)
