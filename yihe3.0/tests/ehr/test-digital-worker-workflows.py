"""Run scoped, rolled-back workflow checks. Requires an isolated preview database and JDK8.

Set EHR_JDBC_URL, EHR_DB_USER, EHR_DB_PASSWORD, EHR_JDK_BIN.
EHR_WORKFLOW_FIXTURES optionally writes synthetic UI fixtures outside the repository.
"""
import os
from pathlib import Path
import subprocess

ROOT = Path(__file__).resolve().parents[2]
for key in ('EHR_JDBC_URL', 'EHR_DB_USER', 'EHR_DB_PASSWORD', 'EHR_JDK_BIN'):
    if not os.environ.get(key):
        raise RuntimeError(f'Missing {key}; use an isolated preview database')
if '/shayu_3_preview?' not in os.environ['EHR_JDBC_URL']:
    raise RuntimeError('This runner requires the isolated shayu_3_preview database')
JDK = Path(os.environ['EHR_JDK_BIN'])
CLASSES = ROOT / 'yihe-framework/target/agent-test-classes'
CLASSES.mkdir(exist_ok=True)
CP = ';'.join(str(ROOT / path) for path in (
    'yihe-framework/target/classes', 'yihe-system/target/classes',
    'yihe-common/target/classes', 'yihe-admin/target/yihe-admin/WEB-INF/lib/*'))
NAMES = ['HealthAgentChecks', 'MiniappAgentChecks', 'DigitalWorkerArchiveChecks',
         'DigitalWorkerOriginalChecks', 'DigitalWorkerWorkflowChecks', 'DigitalWorkerHubChecks', 'DigitalWorkerSettingsChecks', 'DigitalWorkerModelIsolationChecks', 'OcrRosterIdentityChecks']
if os.environ.get('EHR_UPLOAD_TEST_ROOT'):
    NAMES.append('UploadStorageChecks')
subprocess.run([str(JDK / 'javac.exe'), '-encoding', 'UTF-8', '-cp', CP, '-d', str(CLASSES)] +
               [str(ROOT / f'tests/ehr/java/{name}.java') for name in NAMES], check=True)
for name in NAMES:
    subprocess.run([str(JDK / 'java.exe'), '-Dfile.encoding=UTF-8', '-cp',
                    str(CLASSES) + ';' + CP, 'com.yihe.framework.ehr.' + name], check=True)
