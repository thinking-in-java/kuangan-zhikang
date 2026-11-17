import * as THREE from '../vendor/three/three.module.js';

const smooth=(a,b,x)=>{const t=THREE.MathUtils.clamp((x-a)/(b-a),0,1);return t*t*(3-2*t);};
const pivot=(x,y)=>new THREE.Vector3(x*2-1,(1-y)*3,0);
const armOutlines={
    female:[[.390,.335],[.426,.377],[.410,.44],[.365,.482],[.29,.490],[.232,.452],[.202,.405],[.199,.369],[.13,.358],[.092,.33],[.11,.308],[.19,.304],[.238,.318],[.285,.345],[.318,.365],[.338,.352]],
    male:[[.400,.345],[.437,.40],[.416,.48],[.353,.51],[.27,.49],[.20,.465],[.18,.450],[.105,.438],[.070,.414],[.080,.383],[.14,.350],[.20,.370],[.26,.397],[.32,.40],[.36,.36]]
};
function insidePolygon(x,y,poly){let inside=false;for(let i=0,j=poly.length-1;i<poly.length;j=i++){const a=poly[i],b=poly[j];if(((a[1]>y)!==(b[1]>y))&&(x<(b[0]-a[0])*(y-a[1])/(b[1]-a[1])+a[0]))inside=!inside;}return inside;}
function armMask(u,v,poly){
    const x=u,y=1-v;
    let distance=10;
    for(let i=0;i<poly.length;i++){const a=poly[i],b=poly[(i+1)%poly.length],dx=b[0]-a[0],dy=b[1]-a[1];const t=THREE.MathUtils.clamp(((x-a[0])*dx+(y-a[1])*dy)/(dx*dx+dy*dy),0,1);distance=Math.min(distance,Math.hypot(x-a[0]-dx*t,y-a[1]-dy*t));}
    return insidePolygon(x,y,poly)?smooth(0,.004,distance):0;
}

// Corrective poses, not a mirrored hand: rest -> lowered open hand -> greeting.
// All points describe IMAGE LEFT (the doctor's own right arm). The tablet hand stays still.
function createArmSurface(gender,textures){
    const female=Number(gender)===2, lift={value:0};
    const poses=female?[
        {points:[[.389,.350],[.335,.489],[.294,.592],[.276,.690]],widths:[.055,.060,.055,.048]},
        {points:[[.389,.350],[.351,.466],[.258,.518],[.118,.560]],widths:[.055,.060,.075,.060]},
        {points:[[.389,.350],[.315,.442],[.251,.375],[.110,.326]],widths:[.055,.065,.080,.065]}
    ]:[
        {points:[[.388,.358],[.331,.495],[.290,.608],[.285,.698]],widths:[.055,.060,.055,.048]},
        {points:[[.388,.358],[.340,.474],[.254,.521],[.115,.567]],widths:[.055,.060,.075,.060]},
        {points:[[.388,.358],[.322,.470],[.245,.437],[.088,.398]],widths:[.055,.065,.080,.065]}
    ];
    const restOutline=[[.39,.333],[.434,.38],[.417,.46],[.383,.54],[.347,.605],[.34,.655],[.32,.71],[.278,.716],[.242,.672],[.24,.615],[.26,.563],[.30,.45],[.337,.37]];
    const midOutline=[[.39,.333],[.434,.38],[.416,.447],[.38,.50],[.323,.55],[.273,.563],[.222,.583],[.12,.59],[.102,.568],[.103,.542],[.132,.52],[.162,.495],[.215,.499],[.296,.46],[.332,.40],[.343,.355]];
    const outlines=[restOutline,midOutline,female?armOutlines.female:armOutlines.male];
    const samples=96,across=24,uvs=[[],[],[]],positions=[[],[],[]],masks=[],indices=[];
    function at(pose,s,r){
        const segment=Math.min(2,Math.floor(s)),t=s-segment;
        const p=pose.points[segment],q=pose.points[segment+1];
        // Smooth tangent around the elbow and wrist while retaining the reference silhouette.
        const raw=i=>{const a=pose.points[Math.max(0,i)],b=pose.points[Math.min(3,i+1)];return new THREE.Vector2((b[0]-a[0])*2,(b[1]-a[1])*3).normalize();};
        const tangent=raw(segment);
        if(t<.20&&segment>0)tangent.lerp(raw(segment-1).add(raw(segment)).normalize(),1-t/.20).normalize();
        if(t>.80&&segment<2)tangent.lerp(raw(segment).add(raw(segment+1)).normalize(),(t-.80)/.20).normalize();
        const w=THREE.MathUtils.lerp(pose.widths[segment],pose.widths[segment+1],t)*2;
        return [THREE.MathUtils.lerp(p[0],q[0],t)-tangent.y*w*r/2,THREE.MathUtils.lerp(p[1],q[1],t)+tangent.x*w*r/3];
    }
    for(let row=0;row<=samples;row++)for(let col=0;col<=across;col++){
        const s=row/samples*3,r=col/across*2-1;
        for(let p=0;p<3;p++){const xy=at(poses[p],s,r);uvs[p].push(xy[0],1-xy[1]);positions[p].push(xy[0]*2-1,(1-xy[1])*3,0);masks.push(armMask(xy[0],1-xy[1],outlines[p]));}
        if(row<samples&&col<across){const i=row*(across+1)+col;indices.push(i,i+1,i+across+1,i+1,i+across+2,i+across+1);}
    }
    const geometry=new THREE.BufferGeometry();geometry.setIndex(indices);
    geometry.setAttribute('position',new THREE.Float32BufferAttribute(positions[0],3));
    geometry.setAttribute('uv',new THREE.Float32BufferAttribute(uvs[0],2));
    geometry.setAttribute('dhMidUv',new THREE.Float32BufferAttribute(uvs[1],2));geometry.setAttribute('dhRaisedUv',new THREE.Float32BufferAttribute(uvs[2],2));
    geometry.setAttribute('dhPoseMask',new THREE.Float32BufferAttribute(masks,3));
    const material=new THREE.MeshBasicMaterial({map:textures[0],transparent:true,depthWrite:false,side:THREE.DoubleSide,toneMapped:false});
    material.onBeforeCompile=shader=>{
        shader.uniforms.dhMid={value:textures[1]};shader.uniforms.dhRaised={value:textures[2]};shader.uniforms.dhLift=lift;
        shader.vertexShader=shader.vertexShader.replace('void main() {',`attribute vec2 dhMidUv; attribute vec2 dhRaisedUv; attribute vec3 dhPoseMask;
varying vec2 vDhMidUv; varying vec2 vDhRaisedUv; varying vec3 vDhPoseMask;
void main() { vDhMidUv=dhMidUv; vDhRaisedUv=dhRaisedUv; vDhPoseMask=dhPoseMask;`);
        shader.fragmentShader=shader.fragmentShader.replace('void main() {',`uniform sampler2D dhMid; uniform sampler2D dhRaised; uniform float dhLift;
varying vec2 vDhMidUv; varying vec2 vDhRaisedUv; varying vec3 vDhPoseMask;
void main() {`);
        shader.fragmentShader=shader.fragmentShader.replace('#include <map_fragment>',`
vec4 restColor=texture2D(map,vMapUv),midColor=texture2D(dhMid,vDhMidUv),raisedColor=texture2D(dhRaised,vDhRaisedUv);
restColor.a*=vDhPoseMask.x;midColor.a*=vDhPoseMask.y;raisedColor.a*=vDhPoseMask.z;
restColor.rgb*=restColor.a;midColor.rgb*=midColor.a;raisedColor.rgb*=raisedColor.a;
float armPhase=clamp(dhLift*2.0,0.0,2.0);
vec4 armColor=armPhase<1.0?mix(restColor,midColor,armPhase):mix(midColor,raisedColor,armPhase-1.0);
if(armColor.a<.005)discard;armColor.rgb/=armColor.a;diffuseColor*=armColor;
`);
    };
    const mesh=new THREE.Mesh(geometry,material);mesh.frustumCulled=false;mesh.renderOrder=2;
    function update(amount,wristAngle=0){
        lift.value=THREE.MathUtils.clamp(amount,0,1);const phase=lift.value*2,lo=Math.min(1,Math.floor(phase)),t=phase-lo,dest=geometry.attributes.position.array;
        const wrist=poses[lo].points[2].map((x,i)=>THREE.MathUtils.lerp(x,poses[lo+1].points[2][i],t)),wx=wrist[0]*2-1,wy=(1-wrist[1])*3;
        for(let i=0;i<dest.length;i+=3){
            let x=THREE.MathUtils.lerp(positions[lo][i],positions[lo+1][i],t),y=THREE.MathUtils.lerp(positions[lo][i+1],positions[lo+1][i+1],t);
            const s=Math.floor(i/3/(across+1))/samples*3,angle=wristAngle*smooth(1.85,2.3,s),cs=Math.cos(angle),sn=Math.sin(angle);
            dest[i]=wx+(x-wx)*cs-(y-wy)*sn;dest[i+1]=wy+(x-wx)*sn+(y-wy)*cs;dest[i+2]=0;
        }
        geometry.attributes.position.needsUpdate=true;
    }
    return {mesh,geometry,material,lift,poses,positions,update};
}

export function createPortraitRig(texture,gender,cleanTexture=texture,poseTextures={}){
    const female=Number(gender)===2;
    const geometry=new THREE.PlaneGeometry(2,3,96,144);geometry.translate(0,1.5,0);
    const material=new THREE.MeshBasicMaterial({map:texture,transparent:true,depthWrite:false,side:THREE.DoubleSide,toneMapped:false});
    const mesh=new THREE.SkinnedMesh(geometry,material);
    const arm=createArmSurface(gender,[poseTextures.rest||texture,poseTextures.mid||texture,texture]),armMesh=arm.mesh,armGeometry=arm.geometry;
    mesh.frustumCulled=false;mesh.renderOrder=1;
    const root=new THREE.Group();root.add(mesh,armMesh);
    const list=[],bones={};
    function add(name,parent,point){const b=new THREE.Bone();b.name=name;const p=pivot(...point);if(parent){const wp=new THREE.Vector3();parent.getWorldPosition(wp);b.position.copy(p.sub(wp));parent.add(b);}else{b.position.copy(p);mesh.add(b);}mesh.updateMatrixWorld(true);bones[name]=b;list.push(b);return b;}
    const body=add('body',null,[.5,.58]);
    const head=add('head',body,[.515,.337]);
    // Greeting limb is on image-left. Never mirror it to the tablet-holding limb.
    const tablet=add('tabletArm',body,[.665,.41]);
    const skinIndex=[],skinWeight=[],mask=[],uv=geometry.attributes.uv;
    const outline=female?armOutlines.female:armOutlines.male;
    function store(weights,index,weight){const ranked=weights.map((w,i)=>({w,i})).sort((a,b)=>b.w-a.w).slice(0,4),total=ranked.reduce((s,x)=>s+x.w,0)||1;ranked.forEach(x=>{index.push(x.i);weight.push(x.w/total);});}
    for(let i=0;i<uv.count;i++){
        const u=uv.getX(i),v=uv.getY(i);
        const h=smooth(.65,.705,v)*Math.max(smooth(.25,.34,u),smooth(.70,.74,v));
        const tabletW=smooth(.625,.69,u)*smooth(.40,.455,v)*(1-smooth(.63,.665,v))*(1-h);
        store([Math.max(0,1-h-tabletW),h,tabletW,0],skinIndex,skinWeight);
        mask.push(armMask(u,v,outline));
    }
    geometry.setAttribute('skinIndex',new THREE.Uint16BufferAttribute(skinIndex,4));geometry.setAttribute('skinWeight',new THREE.Float32BufferAttribute(skinWeight,4));
    geometry.setAttribute('dhArmMask',new THREE.Float32BufferAttribute(mask,1));
    const skeleton=new THREE.Skeleton(list);mesh.bind(skeleton);
    const blink={value:0},smile={value:.08},gaze={value:new THREE.Vector2()};
    const eyeA=female?new THREE.Vector2(427.5/1024,1-328/1536):new THREE.Vector2(443/1024,1-330/1536);
    const eyeB=female?new THREE.Vector2(609/1024,1-292.5/1536):new THREE.Vector2(612/1024,1-307/1536);
    function armVarying(shader){
        shader.vertexShader=shader.vertexShader.replace('void main() {','attribute float dhArmMask; varying float vDhArmMask;\nvoid main() { vDhArmMask=dhArmMask;');
        shader.fragmentShader=shader.fragmentShader.replace('void main() {','varying float vDhArmMask;\nvoid main() {');
    }
    material.onBeforeCompile=shader=>{
        armVarying(shader);
        shader.uniforms.dhBlink=blink;shader.uniforms.dhSmile=smile;shader.uniforms.dhGaze=gaze;
        shader.uniforms.dhEyeA={value:eyeA};shader.uniforms.dhEyeB={value:eyeB};shader.uniforms.dhClean={value:cleanTexture};
        shader.fragmentShader=shader.fragmentShader.replace('void main() {',`uniform float dhBlink; uniform float dhSmile; uniform vec2 dhGaze; uniform vec2 dhEyeA; uniform vec2 dhEyeB; uniform sampler2D dhClean;
void main() {`);
        shader.fragmentShader=shader.fragmentShader.replace('#include <map_fragment>',`
#include <map_fragment>
// Use the clean coat plate only in the removed arm region. Face and hair stay original.
float headKeep=smoothstep(.65,.705,vMapUv.y)*max(smoothstep(.25,.34,vMapUv.x),smoothstep(.70,.74,vMapUv.y));
float coatBlend=(1.0-smoothstep(.390,.435,vMapUv.x))*smoothstep(.46,.49,vMapUv.y)*(1.0-smoothstep(.69,.714,vMapUv.y))*(1.0-headKeep);
coatBlend=max(coatBlend,vDhArmMask);
diffuseColor=mix(diffuseColor,texture2D(dhClean,vMapUv),coatBlend);
vec2 mouthCenter=vec2(.521,.747);
vec2 mq=(vMapUv-mouthCenter)/vec2(.070,.025);
float mouthArea=(1.0-smoothstep(.75,1.0,length(mq)))*dhSmile;
vec2 mouthUv=vMapUv;mouthUv.y+=.006*mouthArea*(mq.x*mq.x-.45);mouthUv.x-=.001*dhSmile*mq.x*mouthArea;
diffuseColor.rgb=mix(diffuseColor.rgb,texture2D(map,mouthUv).rgb,mouthArea);
for(int eye=0;eye<2;eye++){
    vec2 center=eye==0?dhEyeA:dhEyeB;
    vec2 q=(vMapUv-center)/${female?'vec2(.057,.030)':'vec2(.047,.0255)'};
    float inside=1.0-smoothstep(.83,1.04,length(q));
    float expressionBlink=max(dhBlink,dhSmile*.15);
    float cover=smoothstep(1.15-expressionBlink*2.35,1.22-expressionBlink*2.35,q.y)*inside;
    vec3 lidColor=texture2D(map,center+vec2(q.x*.050,-${female?'.042':'.064'}+q.y*.003)).rgb;
    diffuseColor.rgb=mix(diffuseColor.rgb,lidColor*(1.0+q.y*.008),cover);
    float lash=(1.0-smoothstep(.045,.13,abs(q.y-(-.10+.23*q.x*q.x))))*(1.0-smoothstep(.65,.91,abs(q.x)))*inside*dhBlink*dhBlink;
    diffuseColor.rgb=mix(diffuseColor.rgb,vec3(.13,.075,.07),lash*.85);
}
`);
    };
    let elapsed=0,blinkAt=2.4,action={type:'idle',at:0,duration:0};
    const q=new THREE.Quaternion(),e=new THREE.Euler(),headRest=head.position.clone();
    const rot=(b,z,alpha)=>{e.set(0,0,z);q.setFromEuler(e);b.quaternion.slerp(q,alpha);};
    arm.update(0);root.updateMatrixWorld(true);
    function play(type){if(action.type===type&&elapsed-action.at<action.duration)return;action={type,at:elapsed,duration:type==='wave'?5.0:type==='nod'?2.0:3.2};}
    function update(delta,input={}){
        elapsed+=Math.min(delta,.08);const t=elapsed,p=t-action.at,on=p<action.duration;
        const envelope=on?Math.min(smooth(0,.5,p),1-smooth(action.duration-.7,action.duration,p)):0;
        const wave=on&&action.type==='wave'?Math.min(smooth(0,1.05,p),1-smooth(3.35,5.0,p)):0;
        const nod=on&&action.type==='nod'?envelope:0,listen=on&&action.type==='listen'?envelope:0;
        const smiling=(on&&action.type==='smile'?envelope:0)+wave*.55;
        const idle=input.reduced?0:1,a=1-Math.exp(-Math.min(delta,.08)*8);
        rot(body,Math.sin(t*.75)*.003*idle,a);body.scale.y=1+Math.sin(t*1.6)*.0015*idle;
        rot(head,(input.lookX||0)*-.025+Math.sin(t*.6)*.006*idle+Math.sin(p*5.3)*.025*nod+.065*listen,a);
        head.position.y=headRest.y-Math.sin(p*5.3)*.025*nod;
        head.scale.y=1-Math.max(0,Math.sin(p*5.3))*.015*nod;
        // Pose-corrected hand anatomy through the entire lift; no 180-degree palm rotation.
        arm.update(wave,Math.sin(p*5.2)*.07*smooth(.9,1,wave));armMesh.position.y=body.position.y-1.26;
        armMesh.rotation.z=body.rotation.z;armMesh.scale.y=body.scale.y;
        rot(tablet,Math.sin(t*1.1+.5)*.003*idle,a);
        smile.value=THREE.MathUtils.lerp(smile.value,.08+smiling*.78,a);
        const phase=t-blinkAt;blink.value=phase>=0&&phase<.23?Math.sin(phase/.23*Math.PI):0;
        if(phase>=.23)blinkAt=t+3.8+Math.abs(Math.sin(t))*2.1;
        gaze.value.set(input.lookX||0,input.lookY||0);root.updateMatrixWorld(true);
        return {blink:blink.value,smile:smile.value,armRaised:wave,action:on?action.type:'idle'};
    }
    return {root,mesh,armMesh,arm,bones,geometry,armGeometry,blink,smile,play,update,dispose(){geometry.dispose();armGeometry.dispose();material.dispose();arm.material.dispose();skeleton.dispose();}};
}

export async function mountDoctor(host,gender){
    const sex=Number(gender)===1?'male':Number(gender)===2?'female':null;if(!sex)throw new Error('No reference portrait for unknown gender');
    const loader=new THREE.TextureLoader();
    const loaded=await Promise.all(['','-clean','-rest','-mid'].map(suffix=>loader.loadAsync('/assets/images/digital-doctor-'+sex+suffix+'.png')));
    loaded.forEach(t=>t.colorSpace=THREE.SRGBColorSpace);const [texture,clean,rest,mid]=loaded;
    const rig=createPortraitRig(texture,gender,clean,{rest,mid}),scene=new THREE.Scene();scene.add(rig.root);let renderer;
    try{renderer=new THREE.WebGLRenderer({alpha:true,antialias:true,powerPreference:'low-power'});}catch(error){rig.dispose();loaded.forEach(t=>t.dispose());throw error;}
    renderer.setClearColor(0,0);renderer.setPixelRatio(Math.min(window.devicePixelRatio||1,2));renderer.outputColorSpace=THREE.SRGBColorSpace;renderer.toneMapping=THREE.NoToneMapping;
    const camera=new THREE.OrthographicCamera(-1.3,1.3,1.6,-1.6,.1,10);camera.position.set(0,1.5,5);camera.lookAt(0,1.5,0);
    renderer.domElement.className='dh-doctor-canvas';renderer.domElement.setAttribute('aria-hidden','true');host.appendChild(renderer.domElement);
    const input={lookX:0,lookY:0,reduced:window.matchMedia('(prefers-reduced-motion: reduce)').matches};
    let disposed=false,frame,last=performance.now(),renderAt=0,nextAction=last+12000,turn=0;
    function resize(){const width=host.clientWidth||124,height=host.clientHeight||144;renderer.setSize(width,height,false);const ratio=width/height;camera.left=-1.59*ratio;camera.right=1.59*ratio;camera.updateProjectionMatrix();}
    const observer=new ResizeObserver(resize);observer.observe(host);resize();
    function pointer(event){const r=host.getBoundingClientRect();input.lookX=THREE.MathUtils.clamp((event.clientX-r.left-r.width/2)/250,-1,1);input.lookY=THREE.MathUtils.clamp((event.clientY-r.top-r.height/2)/250,-1,1);}
    window.addEventListener('pointermove',pointer,{passive:true});
    function draw(now){if(disposed)return;frame=requestAnimationFrame(draw);if(now-renderAt<33)return;const dt=Math.min((now-last)/1000,.08);last=now;renderAt=now;if(document.hidden||!host.isConnected||host.closest('[hidden]'))return;
        if(now>nextAction&&!input.reduced){rig.play(['listen','smile','nod'][turn++%3]);nextAction=now+14000;}
        rig.update(dt,input);renderer.render(scene,camera);
    }
    renderer.debug.onShaderError=()=>{throw new Error('Doctor shader compilation failed');};
    try{renderer.render(scene,camera);}catch(error){observer.disconnect();window.removeEventListener('pointermove',pointer);renderer.dispose();rig.dispose();loaded.forEach(t=>t.dispose());renderer.domElement.remove();throw error;}
    frame=requestAnimationFrame(draw);
    const controller={play(type){rig.play(type);},dispose(){if(disposed)return;disposed=true;cancelAnimationFrame(frame);observer.disconnect();window.removeEventListener('pointermove',pointer);renderer.dispose();rig.dispose();loaded.forEach(t=>t.dispose());renderer.domElement.remove();}};
    window.addEventListener('pagehide',()=>controller.dispose(),{once:true});return controller;
}
