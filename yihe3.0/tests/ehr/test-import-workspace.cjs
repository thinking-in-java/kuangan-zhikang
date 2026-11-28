// Pure validation tests. No real service, file upload, or employee record is used.
const fs=require('node:fs'),vm=require('node:vm'),path=require('node:path'),assert=require('node:assert/strict');
const source=process.argv[2]||path.resolve(__dirname,'../../yihe-admin/src/main/resources/static/assets/js/employee-imports.js');
const box={window:{addEventListener(){}},Date};vm.runInNewContext(fs.readFileSync(source,'utf8').replace('window.EmployeeImports={render};','window.importTests={validation,autoMap,birthValue,validDate};window.EmployeeImports={render};'),box);
const {validation,autoMap,birthValue,validDate}=box.window.importTests;
const sample=(rows,mapping=['name','code','org','phone','birth'])=>({rows,mapping,edits:{},rowNumbers:rows.map((_,i)=>i+3)});
let data=sample([[' 测试甲 ','QA01',' 测试单位 ','13800000001','1988/2/3'],['测试乙','QA01','测试单位','13800000002','1988-02-03']]);
let result=validation(data,[],true);assert.equal(result.valid.length,0);assert.equal(result.errors.length,2);assert.ok(result.rows.every(r=>r.issues.some(x=>x.kind==='code')));assert.equal(result.rows[0].body.org,'测试单位');assert.equal(result.rows[0].body.birth,'1988-02-03');assert.equal(result.rows[0].sourceRow,3);
data.edits[1]={code:'QA02'};result=validation(data,[],true);assert.equal(result.valid.length,2);assert.equal(result.rows[1].body.code,'QA02');
result=validation(data,[{code:'QA02'}],true);assert.equal(result.valid.length,1);assert.match(result.errors[0].issues[0].text,/已在企业档案/);
result=validation(sample([['甲','A1','测试单位','138 0000 0001'],['乙','A2','测试单位','13800000001']]),[],true);assert.equal(result.errors.length,2);assert.ok(result.errors.every(r=>r.issues.some(x=>x.kind==='phone')));
result=validation(sample([['甲','A1','单位',''],['乙','A2','单位','']]),[],true);assert.equal(result.valid.length,2);
result=validation(sample([['甲','A1']],['name','code']),[],true);assert.equal(result.valid.length,0);assert.match(result.global.join(),/所属单位/);
result=validation(sample([['甲','A1','单位']],['name','code','code']),[],true);assert.match(result.global.join(),/不能匹配多列/);
result=validation(sample([['甲','A1','单位']]),[],false);assert.equal(result.valid.length,0);assert.match(result.global.join(),/真实职工/);
assert.equal(validDate('2024-02-30'),false);assert.equal(validDate('2024-02-29'),true);assert.equal(validDate('2099-01-01'),false);assert.equal(birthValue('1988年2月3日'),'1988-02-03');
data=sample([['甲','A1','单位']],['name','code','org']);data.edits[0]={phone:'13800000008',birth:'1990-01-02'};result=validation(data,[],true);assert.equal(result.valid[0].body.phone,'13800000008');assert.equal(result.valid[0].body.birth,'1990-01-02');
assert.equal(JSON.stringify(autoMap([' 姓名 *','职工编号','所属单位','未知列'])),JSON.stringify(['name','code','org','']));
console.log('PASS: both-side duplicate detection, existing employee protection, normalized phone duplicates, optional blanks, required mappings, duplicate mappings, real-roster guard, leap dates, original row numbers, unmapped-field corrections, header matching');
// Regression: retain upstream identity-number support in the new import workspace.
assert.equal(autoMap(['身份证号码'])[0],'identityNumber');
const identityData=sample([['测试职工','ID-IMPORT-01','测试单位','11010519491231002x']],['name','code','org','identityNumber']);
const identityResult=validation(identityData,[],true);
assert.equal(identityResult.valid.length,1);
assert.equal(identityResult.rows[0].body.identityNumber,'11010519491231002X');
identityData.rows[0][3]='invalid-id';
assert.ok(validation(identityData,[],true).rows[0].issues.some(issue=>issue.kind==='format'&&issue.text.includes('身份证')));
console.log('PASS: merged identity-number header mapping, value preservation, normalization and malformed-value rejection');
