package com.yihe.framework.ehr;
import java.util.*;import static com.yihe.framework.ehr.EhrAccess.*;import static com.yihe.framework.ehr.EhrService.*;
/** Read-only registry context. Publication never edits report data or executes expressions. */
@SuppressWarnings("unchecked") public class AssessmentRuleRuntime {
 final EhrRepository db;final EhrService ehr;public AssessmentRuleRuntime(EhrRepository db,EhrService ehr){this.db=db;this.ehr=ehr;}
 public List<Map<String,Object>> context(EhrActor a,String module){List<Map<String,Object>>result=new ArrayList<>();for(Map<String,Object>r:db.all("SELECT h.rule_id id,h.rule_kind kind,h.active_revision revision,v.document_json document FROM a_assessment_rule_heads h JOIN a_assessment_rule_versions v ON v.rule_id=h.rule_id AND v.revision=h.active_revision AND v.scope_code=h.scope_code AND v.data_mode=h.data_mode WHERE h.scope_code=? AND h.data_mode=? AND h.enabled=1 AND v.published_at IS NOT NULL ORDER BY h.rule_id",a.scope,a.mode)){Map<String,Object>d=(Map<String,Object>)ehr.decode(r.get("document"));if(((List<?>)d.get("modules")).contains(module)){r.put("document",d);result.add(r);}}require(result.size()<=500,422,"该评估模块生效规则超过500条，请先整理适用范围");return result;}
 public String digest(EhrActor a,String module){List<Map<String,Object>>rules=context(a,module);return rules.isEmpty()?"":hex(EhrAccess.digest(bytes(ehr.encode(rules))));}
 public Map<String,Object> attach(EhrActor a,String module,Map<String,Object>source){List<Map<String,Object>>rules=context(a,module);if(!rules.isEmpty()){source.put("assessmentRules",rules);source.put("assessmentRuleDigest",hex(EhrAccess.digest(bytes(ehr.encode(rules)))));}return source;}
}
