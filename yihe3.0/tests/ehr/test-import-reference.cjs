// DOM-free rendering checks. Never opens a browser or calls a real service.
const fs=require('node:fs'),vm=require('node:vm'),path=require('node:path'),assert=require('node:assert/strict');
const source=process.argv[2]||path.resolve(__dirname,'../../yihe-admin/src/main/resources/static/assets/js/employee-imports.js');
const elements=new Map();class Element{constructor(){this.innerHTML='';this.classList={add(){},remove(){}};}querySelector(){return new Element();}addEventListener(){}}
const get=id=>{if(!elements.has(id))elements.set(id,new Element());return elements.get(id);};
const document={body:{classList:{add(){}}},querySelector:()=>null,getElementById:get};
const box={window:{addEventListener(){}},document,Date,URLSearchParams,clearTimeout(){},setTimeout(){return 1;}};
const code=fs.readFileSync(source,'utf8').replace('window.EmployeeImports={render};',`window.referenceTests={render,countsOf,queryString,seed(meta){recordMeta=meta;statsMeta=meta;}};window.EmployeeImports={render};`);
vm.runInNewContext(code,box);const test=box.window.referenceTests;
const meta={total:3,actors:[{id:'operator-1',name:'真实账号名'}],counts:[{status:'success',count:1},{status:'failed',count:1},{status:'processing',count:1}],items:[{batchId:'real-receipt-id',filename:'文件<script>.xlsx',time:'2026-11-27T00:00:00Z',totalRows:8,imported:8,actor:'operator-1',actorName:'真实账号名',status:'success'}]};
test.seed(meta);test.render({people:[],realPeople:true,cfg:{history:[]},onRender(){},api(){throw Error('render must use the supplied actual metadata');}});
const html=get('app').innerHTML;
assert.equal((html.match(/data-im="step:\d"/g)||[]).length,6);
assert.match(html,/ir-top-grid/);assert.match(html,/ir-bottom-grid/);assert.match(html,/ir-records/);assert.match(html,/ir-statistics/);
assert.doesNotMatch(html,/ec-kpis|im-preview-empty|im-layout/);
assert.match(html,/导入记录（共 3 条）/);assert.match(html,/文件&lt;script&gt;.xlsx/);assert.doesNotMatch(html,/<script>/);
assert.match(html,/真实账号名/);assert.match(html,/data-ir-tab="failed"/);assert.match(html,/data-im="export-records"/);assert.match(html,/data-im="instructions"/);
assert.equal(test.countsOf(meta).processing,1);assert.equal(test.countsOf(meta).failed,1);
const qs=new URLSearchParams(test.queryString({search:'A&B.xlsx',actor:'operator-1'},2,100));assert.equal(qs.get('search'),'A&B.xlsx');assert.equal(qs.get('page'),'2');assert.equal(qs.get('limit'),'100');
console.log('PASS: six real steps, three-card reference composition, record/statistics panels, exact batch counts, real operator names, escaping, record export and filter serialization; no browser/service started');
