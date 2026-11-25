(function(){
 'use strict';
 let last='',queued=false;
 function update(){queued=false;const frame=document.querySelector('.layui-body .layui-tab-item.layui-show iframe.admin-iframe, .layui-body > div > iframe.admin-iframe');if(!frame)return;let path='';try{path=frame.contentWindow.location.pathname;}catch(error){return;}if(!/^\/manage\/(workbench|employees|intervention|analytics|system-center)(\/|$)/.test(path)){last='';return;}const root=document.querySelector('.dh-floating-root');if(!root||path===last)return;last=path;if(!root.classList.contains('dh-widget-compact'))root.querySelector('.dh-widget-minimize')?.click();}
 function schedule(){if(!queued){queued=true;requestAnimationFrame(update);}}
 const observer=new MutationObserver(schedule);observer.observe(document.body,{childList:true,subtree:true,attributes:true,attributeFilter:['class','src']});document.addEventListener('load',schedule,true);schedule();window.addEventListener('pagehide',()=>{observer.disconnect();document.removeEventListener('load',schedule,true);},{once:true});
})();
