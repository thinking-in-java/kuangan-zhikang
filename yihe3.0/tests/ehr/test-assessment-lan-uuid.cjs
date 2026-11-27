const assert=require('node:assert/strict'),fs=require('node:fs'),vm=require('node:vm'),{webcrypto}=require('node:crypto'),path=require('node:path');
const uuid=/^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/;
for(const name of ['assessment-rules','comprehensive','psychological','questionnaire-answer','questionnaires']){
 const source=fs.readFileSync(path.join(__dirname,'../../yihe-admin/src/main/resources/static/assets/assessment/'+name+'.js'),'utf8');
 const helper=source.match(/^function lanRequestId\(\)\{[\s\S]*?\n\}/)[0];
 const fallback=vm.createContext({window:{crypto:{getRandomValues:values=>webcrypto.getRandomValues(values)}}});vm.runInContext(helper,fallback);
 const generated=Array.from({length:1000},()=>vm.runInContext('lanRequestId()',fallback));assert.ok(generated.every(value=>uuid.test(value)));assert.equal(new Set(generated).size,1000);
 const native=vm.createContext({window:{crypto:{randomUUID:()=> 'native-id'}}});vm.runInContext(helper,native);assert.equal(vm.runInContext('lanRequestId()',native),'native-id');
 const absent=vm.createContext({window:{}});vm.runInContext(helper,absent);assert.throws(()=>vm.runInContext('lanRequestId()',absent),/请更新浏览器/);
 assert.ok(!source.includes('crypto.randomUUID()'));new vm.Script(source);console.log('PASS '+name+' HTTP fallback, native UUID, unavailable crypto and syntax');
}
