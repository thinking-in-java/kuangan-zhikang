const assert=require('node:assert/strict'),fs=require('node:fs'),vm=require('node:vm'),{webcrypto}=require('node:crypto'),path=require('node:path');
const source=fs.readFileSync(path.join(__dirname,'../../yihe-admin/src/main/resources/static/assets/assessment/occupational-risk.js'),'utf8');
const helper=source.slice(source.indexOf('function requestId(){'),source.indexOf('function toast('));
const uuid=/^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/;
const fallback=vm.createContext({window:{crypto:{getRandomValues:values=>webcrypto.getRandomValues(values)}}});vm.runInContext(helper,fallback);
const generated=Array.from({length:1000},()=>vm.runInContext('requestId()',fallback));assert.ok(generated.every(value=>uuid.test(value)));assert.equal(new Set(generated).size,1000);console.log('PASS LAN_HTTP_fallback_UUID_v4_and_1000_unique_requests');
const native=vm.createContext({window:{crypto:{randomUUID:()=> 'native-id'}}});vm.runInContext(helper,native);assert.equal(vm.runInContext('requestId()',native),'native-id');console.log('PASS native_UUID_still_used_when_available');
const absent=vm.createContext({window:{}});vm.runInContext(helper,absent);assert.throws(()=>vm.runInContext('requestId()',absent),/请更新浏览器/);console.log('PASS unavailable_crypto_has_actionable_error');
assert.equal((source.match(/requestKey:requestId\(\)/g)||[]).length,3);assert.ok(!source.includes('requestKey:crypto.randomUUID()'));console.log('PASS batch_review_and_cancel_share_LAN_compatible_generator');
