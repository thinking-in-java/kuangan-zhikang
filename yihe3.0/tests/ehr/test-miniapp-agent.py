"""Compile and run two-person MySQL isolation checks. All business fixtures roll back."""
from pathlib import Path
import os,sys,subprocess
ROOT=Path(__file__).resolve().parents[2];sys.path.insert(0,str(ROOT/'database'))
from health_archive_db import configuration
c=configuration();jdk=Path(os.environ.get('EHR_JDK_BIN','F:/workstation/.tools/jdk8/zulu8.98.0.111-ca-jdk8.0.512-win_x64/bin'))
cp=';'.join(str(ROOT/p) for p in ['yihe-framework/target/classes','yihe-system/target/classes','yihe-common/target/classes','yihe-admin/target/yihe-admin/WEB-INF/lib/*'])
out=ROOT/'yihe-framework/target/miniapp-agent-test-classes';out.mkdir(parents=True,exist_ok=True)
subprocess.run([str(jdk/'javac.exe'),'-encoding','UTF-8','-cp',cp,'-d',str(out),str(ROOT/'tests/ehr/java/HealthAgentChecks.java'),str(ROOT/'tests/ehr/java/MiniappAgentChecks.java')],check=True)
env=dict(os.environ,EHR_JDBC_URL=f"jdbc:mysql://{c['host']}:{c['port']}/{c['database']}?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&characterSetResults=UTF-8",EHR_DB_USER=c['user'],EHR_DB_PASSWORD=c['password'])
subprocess.run([str(jdk/'java.exe'),'-Dfile.encoding=UTF-8','-cp',str(out)+';'+cp,'com.yihe.framework.ehr.MiniappAgentChecks'],env=env,check=True)
