window.calls=[];window.confirm=()=>true;
let cats=SEED.categories.map((c,i)=>({...c,id:'cat'+i,version:1,count:c.target}));
let docs=SEED.documents.map((d,i)=>({...d,id:'doc'+i,categoryId:cats.find(c=>c.code===d.categoryCode).id,version:d.kind==='template'?1:2,status:d.kind==='template'?'draft':'published',updatedAt:'2026-11-25 01:00:00',published:d.kind==='template'?null:{...d,categoryId:cats.find(c=>c.code===d.categoryCode).id,revision:2}}));
let setting={version:1,enabled:true,topK:4,minScore:18,categoryIds:[]};
function stats(){cats.forEach(c=>c.count=docs.filter(d=>d.categoryId===c.id).length);return {total:docs.length,published:docs.filter(d=>d.status==='published').length,drafts:docs.filter(d=>d.status==='draft').length,archived:0,recent:docs.length,citations:0,usage:[],top:[]};}
window.fetch=async (url,o={})=>{let u=new URL(url,location.origin),p=u.pathname,b=o.body?JSON.parse(o.body):{};calls.push({path:p,method:o.method||'GET',body:b,query:Object.fromEntries(u.searchParams)});let data={},error=null;
if(p==='/ehr/api/context')data={csrfToken:'fixture'};
else if(p.endsWith('/settings')){if(o.method){setting={...b,version:setting.version+1};}data=setting;}
else if(p.endsWith('/retrieve'))data={items:docs.filter(d=>d.status==='published').slice(0,2).map(d=>({...d,score:75,content:d.body,revision:d.version}))};
else if(p.endsWith('/export'))data={documents:docs};
else if(p.endsWith('/history'))data=[{id:'v1',revision:1,action:'save',actor:'test',time:'2026-11-25 01:00:00'}];
else if(p.endsWith('/restore'))data={};
else if(p.endsWith('/action')){let id=p.split('/').slice(-2)[0],d=docs.find(d=>d.id===id);if(b.action==='publish'){if(d.kind==='template'&&!d.adopted)error='参考模板须经企业核实采纳后才能发布';else {d.status='published';d.version++;d.published={...d,revision:d.version};}}else if(b.action==='archive'){d.status='archived';d.version++;}else if(b.action==='delete')docs=docs.filter(x=>x.id!==id);else docs.push({...d,id:crypto.randomUUID(),status:'draft',published:null});data=d;}
else if(p.endsWith('/import')){b.documents.forEach(d=>docs.push({...d,id:crypto.randomUUID(),categoryId:cats[0].id,status:'draft',version:1,updatedAt:'2026-11-25 01:00:00'}));data={count:b.documents.length};}
else if(p.endsWith('/categories')&&o.method){cats.push({id:crypto.randomUUID(),name:b.name,code:'custom',sort:b.sort,version:1,count:0});}
else if(p.includes('/documents/')){let d=docs.find(x=>x.id===p.split('/').pop());if(o.method){Object.assign(d,b,{version:d.version+1,hasUnpublishedChanges:!!d.published});}data=d;}
else if(p.endsWith('/documents')&&o.method){let d={...b,id:crypto.randomUUID(),version:1,status:'draft',updatedAt:'2026-11-25 01:00:00'};docs.push(d);data=d;}
else {let st=stats(),q=u.searchParams,filtered=docs.filter(d=>(!q.get('categoryId')||d.categoryId===q.get('categoryId'))&&(!q.get('status')||d.status===q.get('status'))&&(!q.get('kind')||d.kind===q.get('kind'))&&(!q.get('search')||d.title.includes(q.get('search')))),page=Number(q.get('page')||1),size=Number(q.get('size')||10);data={items:filtered.slice((page-1)*size,page*size),total:filtered.length,categories:cats,statistics:st};}
return new Response(JSON.stringify(error?{code:422,msg:error}:{code:0,data}),{status:error?422:200,headers:{'Content-Type':'application/json'}});};
