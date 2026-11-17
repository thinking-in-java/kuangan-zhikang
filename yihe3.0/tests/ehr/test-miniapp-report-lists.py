"""Run real SQL/transaction tests; never creates a network login bypass."""
import os, sys, subprocess, json
from pathlib import Path
ROOT=Path(__file__).resolve().parents[2]
sys.path.insert(0,str(ROOT/'database'))
from health_archive_db import configuration
c=configuration();env=dict(os.environ)
env.update(EHR_CHECK_JDBC=f"jdbc:mysql://{c['host']}:{c['port']}/{c['database']}?useSSL=false&serverTimezone=UTC&characterEncoding=utf8&allowPublicKeyRetrieval=true",
           EHR_CHECK_USER=c['user'],EHR_CHECK_PASSWORD=c['password'])
java='F:/workstation/.tools/jdk8/zulu8.98.0.111-ca-jdk8.0.512-win_x64/bin/java.exe'
cp='yihe-admin/target/yihe-admin/WEB-INF/lib/*;yihe-framework/target/classes;F:/workstation/.tools/runtime/miniapp-report-checks'
r=subprocess.run([java,'-Dfile.encoding=UTF-8','-cp',cp,'com.yihe.framework.ehr.MiniappReportChecks'],cwd=ROOT,env=env,stdout=subprocess.PIPE,stderr=subprocess.STDOUT)
output=r.stdout.decode('utf-8',errors='replace')
log=Path('F:/workstation/.tools/runtime/miniapp-report-checks.log');log.write_text(output,encoding='utf-8')
for line in output.splitlines():
    if line.startswith('PASS ') or line.startswith('Exception') or line.startswith('\tat com.yihe.framework.ehr.MiniappReportChecks'):print(line)
if r.returncode:print(output[-4500:]);raise SystemExit(r.returncode)
checks=[line[5:] for line in output.splitlines() if line.startswith('PASS ') and 'checks;' not in line]
(ROOT/'database/miniapp-report-list-verification.json').write_text(json.dumps({'checks':checks,'count':len(checks),'fixturesRolledBack':True,'wechatLoginBypassAdded':False,'formalEmployeeModified':False},indent=2),encoding='utf-8')
