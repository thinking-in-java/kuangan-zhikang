// Offline regression: renders the real candidate form without DB writes or OCR jobs.
const fs=require('node:fs'),vm=require('node:vm'),assert=require('node:assert/strict');
const source=fs.readFileSync(require('node:path').join(__dirname,'../../yihe-admin/src/main/resources/static/assets/health-archive/live.js'),'utf8');
const helpers=source.slice(source.indexOf('function normalizeExamDate('),source.indexOf('function metricRow('));
const candidate=source.slice(source.indexOf('async function candidate('),source.indexOf('async function action('));
let report,html,checks=0;
const context={Date,Set,Number,String,editor:null,esc:s=>String(s??'').replaceAll('&','&amp;').replaceAll('<','&lt;').replaceAll('"','&quot;'),
 api:async(path,method)=>{assert.equal(method,undefined,'Opening the form must not write');return report;},
 modal:(_,body)=>{html=body;},formHandler:()=>{},valueOf:()=>'',metricRow:()=>'',button:()=>'',
 field:(name,label,value,type,required)=>`<input name="${name}" type="${type||'text'}" value="${context.esc(value)}" ${required?'required':''}>`};
vm.createContext(context);vm.runInContext(helpers+'\n'+candidate,context);
function check(v){assert.ok(v);checks++;}
async function render(overrides){report={fieldValues:[],items:[],conclusions:[],advice:[],...overrides};await context.candidate('fixture',1);return html;}
(async()=>{
 let body=await render({examDate:null,examDateRaw:'2026-09-20'});
 check(body.includes('name="examDate" type="date" value="2026-09-20" required'));
 check(body.includes('尚未核定或归档'));
 check(report.examDate===null); // Displaying a candidate never promotes its status.
 body=await render({examDate:'2026-09-21',examDateRaw:'2026-09-20'});
 check(body.includes('name="examDate" type="date" value="2026-09-21"'));
 body=await render({examDateRaw:'2026 年09 月20 日'});
 check(body.includes('name="examDate" type="date" value="2026-09-20"'));
 body=await render({fieldValues:[{field_key:'exam_date',value_raw:'2026-09-20'}]});
 check(body.includes('name="examDate" type="date" value="2026-09-20"'));
 body=await render({fieldValues:[{field_key:'check_date',value_raw:'2026-09-18'}]});
 check(body.includes('name="examDate" type="date" value=""'));
 check(context.draftExamDate({fieldValues:[{field_key:'exam_date',value_raw:'2026-09-18'},{field_key:'exam_date',value_raw:'2026-09-20'}]})==='');
 check(context.normalizeExamDate('2026-02-29')==='');
 check(context.normalizeExamDate('2024-02-29')==='2024-02-29');
 check(context.normalizeExamDate('2026-04-31')==='');
 check(context.normalizeExamDate('09/20/2026')==='');
 check(context.normalizeExamDate('2024/07/10')==='2024-07-10');
 check(context.normalizeExamDate('体检日期：2024/07/10')==='2024-07-10');
 check(context.normalizeExamDate('总检日期：2024/07/10')==='');
 check(context.draftExamDate({examDateRaw:null,fieldValues:[{field_key:'exam_date',value_raw:'2024/07/10'},{field_key:'exam_date',value_raw:'2024-07-10'},{field_key:'exam_date',value_raw:'总检日期：2024/07/10'}]})==='2024-07-10');
 check(context.draftExamDate({fieldValues:[{field_key:'exam_date',original_field_name:'总检日期',value_raw:'2024/07/10'}]})==='');
 check(context.draftExamDate({fieldValues:[{field_key:'exam_date',original_field_name:'下次体检日期',value_raw:'2025/07/10'}]})==='');
 check(context.draftExamDate({fieldValues:[{field_key:'exam_date',value_raw:'2024/07/10'},{field_key:'exam_date',value_raw:'2024/07/11'}]})==='');
 const evidenceReport={fieldValues:[{field_key:'exam_date',value_raw:'2022-11-08',evidence_ids:['cover']},{field_key:'exam_date',value_raw:'2022-11-17',evidence_ids:['total']}],fieldEvidence:[{id:'cover',page:1,quote:'体检日期：2022-11-08'},{id:'total',page:5,quote:'总检日期：2022-11-17'}]};
 check(context.draftExamDate(evidenceReport)==='2022-11-08');
 const org={institution:null,fieldValues:[{field_key:'institution',value_raw:'TEST Hospital Health Center',evidence_ids:['cover']},{field_key:'institution',value_raw:'TEST Hospital',evidence_ids:['attached']}],fieldEvidence:[{id:'cover',page:1},{id:'attached',page:8}]};
 check(context.draftInstitution(org)==='TEST Hospital Health Center');
 check(context.draftInstitution({...org,fieldEvidence:[]})==='');
 check(context.formalConclusions({conclusions:[{label:'总检结论与建议',page:4,text:'Full first part'},{label:'完整体检结论',page:5,text:'continued'},{label:'各检查项目异常结果汇总',page:6,text:'BMI'},{label:'诊断意见',page:15,text:'image finding'},{text:':'}]}).length===2);
 body=await render({...evidenceReport,...org,conclusions:[{label:'总检结论与建议',page:4,text:'Full first part'},{label:'完整体检结论',page:5,text:'continued'},{label:'检查意见',page:14,text:'Do not promote'}]});
 check(body.includes('Full first part\ncontinued')&&!body.includes('Do not promote'));
 check(body.includes('name="conclusionPage" type="number" value="4"'));
 body=await render({examDateRaw:'<img src=x onerror=alert(1)>'});
 check(!body.includes('<img src=x')&&body.includes('&lt;img'));
 console.log(`Candidate date UI: ${checks} checks passed; no API writes, uploads or OCR claims.`);
})().catch(e=>{console.error(e);process.exitCode=1;});
