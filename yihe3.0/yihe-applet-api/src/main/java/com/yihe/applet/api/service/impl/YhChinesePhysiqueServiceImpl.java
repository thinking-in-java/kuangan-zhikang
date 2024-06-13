package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.controller.Yhhealthplan1;
import com.yihe.applet.api.controller.Yhhealthplan2;
import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.mapper.YhChinesePhysiqueMapper;
import com.yihe.applet.api.service.YhChinesePhysiqueService;
import com.yihe.system.domain.YhUserHealthyPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class YhChinesePhysiqueServiceImpl implements YhChinesePhysiqueService {
    @Autowired
    YhChinesePhysiqueMapper yhChinesePhysiqueMapper;
    @Override
    public List<YhChinesePhysique> selectallinfo(String Usercode) {
        //首先查出是男是女
        int sex=yhChinesePhysiqueMapper.selectsex(Usercode);
        List<YhChinesePhysique> allinfo=yhChinesePhysiqueMapper.selectallinfo(sex);
        return allinfo;
    }

    @Override
    public int addhinesePhysique(Integer type, String list, String userCode) {
        YhChineseAnwser yhChineseAnwser=new YhChineseAnwser();
        yhChineseAnwser.setChooselist(list);
        yhChineseAnwser.setUserCode(userCode);
        yhChineseAnwser.setState(type);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        yhChineseAnwser.setTime(df.format(new Date()));
        list=list.substring(1,list.length()-1);
        String arr[]=list.split(",");
        System.out.println(arr);
        if(arr.length==33){
            int A=0;
            A=A+Integer.valueOf(arr[0]);
            A=A+6-Integer.valueOf(arr[1]);
            A=A+6-Integer.valueOf(arr[3]);
            A=A+6-Integer.valueOf(arr[4]);
            A=A+6-Integer.valueOf(arr[12]);
            int B=0;
            B=B+Integer.valueOf(arr[1]);
            B=B+Integer.valueOf(arr[2]);
            B=B+Integer.valueOf(arr[3]);
            B=B+Integer.valueOf(arr[13]);
            int C=0;
            C=C+Integer.valueOf(arr[10]);
            C=C+Integer.valueOf(arr[11]);
            C=C+Integer.valueOf(arr[12]);
            C=C+Integer.valueOf(arr[28]);
            int D=0;
            D=D+Integer.valueOf(arr[9]);
            D=D+Integer.valueOf(arr[20]);
            D=D+Integer.valueOf(arr[25]);
            D=D+Integer.valueOf(arr[30]);
            int E=0;
            E=E+Integer.valueOf(arr[8]);
            E=E+Integer.valueOf(arr[15]);
            E=E+Integer.valueOf(arr[27]);
            E=E+Integer.valueOf(arr[31]);
            int F=0;
            F=F+Integer.valueOf(arr[22]);
            F=F+Integer.valueOf(arr[24]);
            F=F+Integer.valueOf(arr[26]);
            F=F+Integer.valueOf(arr[29]);
            int G=0;
            G=G+Integer.valueOf(arr[18]);
            G=G+Integer.valueOf(arr[21]);
            G=G+Integer.valueOf(arr[23]);
            G=G+Integer.valueOf(arr[32]);
            int H=0;
            H=H+Integer.valueOf(arr[4]);
            H=H+Integer.valueOf(arr[5]);
            H=H+Integer.valueOf(arr[6]);
            H=H+Integer.valueOf(arr[7]);
            int I=0;
            I=I+Integer.valueOf(arr[14]);
            I=I+Integer.valueOf(arr[16]);
            I=I+Integer.valueOf(arr[17]);
            I=I+Integer.valueOf(arr[19]);
            yhChineseAnwser.setaScore(A);
            yhChineseAnwser.setbScore(B);
            yhChineseAnwser.setcScore(C);
            yhChineseAnwser.setdScore(D);
            yhChineseAnwser.seteScore(E);
            yhChineseAnwser.setfScore(F);
            yhChineseAnwser.setgScore(G);
            yhChineseAnwser.sethScore(H);
            yhChineseAnwser.setiScore(I);
            if(A>=17&&B<10&&C<10&&D<10&&E<10&&F<10&&G<10&&H<10&&I<10){
                if(B<8&&C<8&&D<8&&E<8&&F<8&&G<8&&H<8&&I<8){
                    yhChineseAnwser.setaIstype(1);
                    yhChineseAnwser.setbIstype(3);
                    yhChineseAnwser.setcIstype(3);
                    yhChineseAnwser.setdIstype(3);
                    yhChineseAnwser.seteIstype(3);
                    yhChineseAnwser.setfIstype(3);
                    yhChineseAnwser.setgIstype(3);
                    yhChineseAnwser.sethIstype(3);
                    yhChineseAnwser.setiIstype(3);
                }else if(B<10&&C<10&&D<10&&E<10&&F<10&&G<10&&H<10&&I<10){
                    yhChineseAnwser.setaIstype(2);
                    if(B>=9){
                        yhChineseAnwser.setbIstype(2);
                    }else{
                        yhChineseAnwser.setbIstype(3);
                    }
                    if(C>=9){
                        yhChineseAnwser.setcIstype(2);
                    }else{
                        yhChineseAnwser.setcIstype(3);
                    }if(D>=9){
                        yhChineseAnwser.setdIstype(2);
                    }else{
                        yhChineseAnwser.setdIstype(3);
                    }if(E>=9){
                        yhChineseAnwser.seteIstype(2);
                    }else{
                        yhChineseAnwser.seteIstype(3);
                    }if(F>=9){
                        yhChineseAnwser.setfIstype(2);
                    }else{
                        yhChineseAnwser.setfIstype(3);
                    }if(G>=9){
                        yhChineseAnwser.setgIstype(2);
                    }else{
                        yhChineseAnwser.setgIstype(3);
                    }if(H>=9){
                        yhChineseAnwser.sethIstype(2);
                    }else{
                        yhChineseAnwser.sethIstype(3);
                    }if(I>=9){
                        yhChineseAnwser.setiIstype(2);
                    }else{
                        yhChineseAnwser.setiIstype(3);
                    }
                }
            }else{
                yhChineseAnwser.setaIstype(3);
                if(B>=11){
                    yhChineseAnwser.setbIstype(1);
                }else if(B>=9&&B<=10){
                    yhChineseAnwser.setbIstype(2);
                }else{
                    yhChineseAnwser.setbIstype(3);
                }

                if(C>=11){
                    yhChineseAnwser.setcIstype(1);
                }else if(C>=9&&C<=10){
                    yhChineseAnwser.setcIstype(2);
                }else{
                    yhChineseAnwser.setcIstype(3);
                }

                if(D>=11){
                    yhChineseAnwser.setdIstype(1);
                }else if(D>=9&&D<=10){
                    yhChineseAnwser.setdIstype(2);
                }else{
                    yhChineseAnwser.setdIstype(3);
                }

                if(E>=11){
                    yhChineseAnwser.seteIstype(1);
                }else if(E>=9&&E<=10){
                    yhChineseAnwser.seteIstype(2);
                }else{
                    yhChineseAnwser.seteIstype(3);
                }

                if(F>=11){
                    yhChineseAnwser.setfIstype(1);
                }else if(F>=9&&F<=10){
                    yhChineseAnwser.setfIstype(2);
                }else{
                    yhChineseAnwser.setfIstype(3);
                }

                if(G>=11){
                    yhChineseAnwser.setgIstype(1);
                }else if(G>=9&&G<=10){
                    yhChineseAnwser.setgIstype(2);
                }else{
                    yhChineseAnwser.setgIstype(3);
                }

                if(H>=11){
                    yhChineseAnwser.sethIstype(1);
                }else if(H>=9&&H<=10){
                    yhChineseAnwser.sethIstype(2);
                }else{
                    yhChineseAnwser.sethIstype(3);
                }

                if(I>=11){
                    yhChineseAnwser.setiIstype(1);
                }else if(I>=9&&I<=10){
                    yhChineseAnwser.setiIstype(2);
                }else{
                    yhChineseAnwser.setiIstype(3);
                }
            }
        }
        return yhChinesePhysiqueMapper.addhinesePhysique(yhChineseAnwser);
    }

    @Override
    public YhChineseAnwser2 selectanwser(String Usercode) {
        YhChineseAnwser2 yhChineseAnwser2=new YhChineseAnwser2();
        YhChineseAnwser yhChineseAnwser=yhChinesePhysiqueMapper.selectanwser(Usercode);
        List <YhChineseAnwser3> anwser3s=new ArrayList<>();
        List<YhChineseAnwser4> anwser4s=new ArrayList<>();
        if(yhChineseAnwser.getaIstype()==1){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("平和质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getbIstype()==1){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("气虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getcIstype()==1){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("阳虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getdIstype()==1){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("阴虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.geteIstype()==1){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("痰湿质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getfIstype()==1){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("湿热质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getgIstype()==1){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("血瘀质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.gethIstype()==1){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("气郁质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getiIstype()==1){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("特禀质");
            anwser3s.add(yhChineseAnwser3);
        }

        if(yhChineseAnwser.getaIstype()==2){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("平和质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getbIstype()==2){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("气虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getcIstype()==2){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("阳虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getdIstype()==2){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("阴虚质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.geteIstype()==2){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("痰湿质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getfIstype()==2){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("湿热质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getgIstype()==2){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("血瘀质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.gethIstype()==2){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("气郁质");
            anwser3s.add(yhChineseAnwser3);
        }
        if(yhChineseAnwser.getiIstype()==2){
            YhChineseAnwser3 yhChineseAnwser3=new YhChineseAnwser3();
            yhChineseAnwser3.setName("特禀质");
            anwser3s.add(yhChineseAnwser3);
        }
        yhChineseAnwser2.setZonghe(anwser3s);
        List<YhChineseAnwser4> yhChineseAnwser4s=new ArrayList<>();
        for (int i=0;i<anwser3s.size();i++){
            if(anwser3s.get(i).getName().equals("平和质")){
                YhChineseAnwser4 yhChineseAnwser4=new YhChineseAnwser4();
                yhChineseAnwser4.setName("平和质");
                yhChineseAnwser4.setTizhi("正常体质");
                if(yhChineseAnwser.getaIstype()==1){
                    yhChineseAnwser4.setState("是");
                }else if(yhChineseAnwser.getaIstype()==2){
                    yhChineseAnwser4.setState("基本是");
                }
                List<YhChineseAnwser5> yhChineseAnwser5s=new ArrayList<>();

                YhChineseAnwser5 yhChineseAnwser5=new YhChineseAnwser5();
                yhChineseAnwser5.setTitle("总体特征");
                yhChineseAnwser5.setTxt("阴阳气血调和，以体态适中、面色红润、精力充沛等为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser5);

                YhChineseAnwser5 yhChineseAnwser51=new YhChineseAnwser5();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("体形匀称健壮。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5 yhChineseAnwser57=new YhChineseAnwser5();
                yhChineseAnwser57.setTitle("常见表现");
                yhChineseAnwser57.setTxt("面色、肤色润泽，头发稠密有光泽，目光有神，鼻色明润，嗅觉通利，唇色红润，不易疲劳， 精力充沛，耐受寒热，睡眠良好，胃纳佳，二便正常，舌色淡红，苔薄白，脉和缓有力。");
                yhChineseAnwser5s.add(yhChineseAnwser57);

                YhChineseAnwser5 yhChineseAnwser53=new YhChineseAnwser5();
                yhChineseAnwser53.setTitle("心理特征");
                yhChineseAnwser53.setTxt("性格随和开朗。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5 yhChineseAnwser54=new YhChineseAnwser5();
                yhChineseAnwser54.setTitle("发病倾向");
                yhChineseAnwser54.setTxt("平素患病较少");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5 yhChineseAnwser55=new YhChineseAnwser5();
                yhChineseAnwser55.setTitle("对外界环境适应能力");
                yhChineseAnwser55.setTxt("对自然环境和社会环境适应能力较强。");
                yhChineseAnwser5s.add(yhChineseAnwser55);
                yhChineseAnwser4.setZong(yhChineseAnwser5s);

                List<YhChineseAnwser5> yhChineseAnwser52=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser60=new YhChineseAnwser5();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。宜保持开朗舒畅的情绪。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5 yhChineseAnwser61=new YhChineseAnwser5();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜丰富饮食的种类，形成多样化的饮食习惯，多吃五谷杂粮、蔬菜瓜果，少食过于油腻及辛辣之物。\n" +
                        "建议选择具有健脾、滋肾作用的饮食，如小麦、黄豆、山药、豆腐、木耳、苹果等。\n" +
                        "参考食疗药膳：山药扁豆粥");
                yhChineseAnwser52.add(yhChineseAnwser55);

                YhChineseAnwser5 yhChineseAnwser62=new YhChineseAnwser5();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("应持之以恒地保持良好的生活起居习惯。\n" +
                        "保持充足的睡眠时间，不宜食后即睡。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5 yhChineseAnwser63=new YhChineseAnwser5();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("建议形成良好的运动习惯，每日进行半小时～1小时的有氧运动。推荐保健运动为八段锦、太极剑以及太极拳。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5 yhChineseAnwser64=new YhChineseAnwser5();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：足三里、气海；\n" +
                        "定位：足三里穴位于外膝眼下三寸，胫骨前嵴外1横指处；气海穴位于前正中线上，脐下1.5寸。\n" +
                        "操作：点按法，用大拇指或中指按压足三里、气海穴，足三里穴可以两侧穴位同时操作。每次按压操作5～10分钟，每日两次，10天1个疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if(anwser3s.get(i).getName().equals("气虚质")){
                YhChineseAnwser4 yhChineseAnwser4=new YhChineseAnwser4();
                yhChineseAnwser4.setName("气虚质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if(yhChineseAnwser.getbIstype()==1){
                    yhChineseAnwser4.setState("是");
                }else if(yhChineseAnwser.getbIstype()==2){
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5> yhChineseAnwser5s=new ArrayList<>();

                YhChineseAnwser5 yhChineseAnwser50=new YhChineseAnwser5();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("元气不足，以疲乏、气短、自汗等气虚表现为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5 yhChineseAnwser51=new YhChineseAnwser5();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("肌肉松软不实。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5 yhChineseAnwser59=new YhChineseAnwser5();
                yhChineseAnwser59.setTitle("常见表现");
                yhChineseAnwser59.setTxt("平素语音低弱，气短懒言，容易疲乏，精神不振，易出汗，舌淡红，舌边有齿痕，脉弱。");
                yhChineseAnwser5s.add(yhChineseAnwser59);

                YhChineseAnwser5 yhChineseAnwser53=new YhChineseAnwser5();
                yhChineseAnwser53.setTitle("心理特征");
                yhChineseAnwser53.setTxt("性格内向，不喜冒险。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5 yhChineseAnwser54=new YhChineseAnwser5();
                yhChineseAnwser54.setTitle("发病倾向");
                yhChineseAnwser54.setTxt("易患感冒、内脏下垂等病;病后康复缓慢。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5 yhChineseAnwser55=new YhChineseAnwser5();
                yhChineseAnwser55.setTitle("对外界环境适应能力");
                yhChineseAnwser55.setTxt("不耐受风、寒、暑、湿邪。");
                yhChineseAnwser5s.add(yhChineseAnwser55);
                yhChineseAnwser4.setZong(yhChineseAnwser5s);


                List<YhChineseAnwser5> yhChineseAnwser52=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser60=new YhChineseAnwser5();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。\n" +
                        "宜多与别人交谈、沟通，以积极进取的态度面对生活。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5 yhChineseAnwser61=new YhChineseAnwser5();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜多食益气健脾作用的食物，如粳米、小米、黄米、大麦、黄豆、白扁豆、豇豆、蚕豆、豌豆、土豆、白薯、红薯、山药、胡萝卜、香菇、鲫鱼、鹌鹑、鹅肉、羊心、羊肚、莲子、蘑菇、芡实、栗子、人参等。\n" +
                        "少吃具有耗气作用的食物，如槟榔、空心菜等。\n" +
                        "参考食疗药膳：黄芪童子鸡、山药莲子粥、人参桂圆母鸡汤、香菇豆腐皮排骨汤、灵芝红枣茶、牛奶仔汤、高枝杜梨汤（鸭脚茶）等。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5 yhChineseAnwser62=new YhChineseAnwser5();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("气虚质者卫阳不足，易于感受外邪，应注意保暖，不要劳汗当风，防止外邪侵袭。\n" +
                        "避免劳累：劳则气耗，气虚质者尤当注意不可过于劳作，以免更伤正气。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5 yhChineseAnwser63=new YhChineseAnwser5();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("应避免剧烈的体育活动，太极拳和八段锦比较适合这类群体。\n" +
                        "推荐：呼气提肛法。明代“养生十六宜”指出“谷道宜常撮”，谷道指肛门。首先吸气收腹，收缩并提升肛门，停顿2～3秒之后，再缓慢放松呼气，如此反复10～15次。八段锦的“两手攀足固肾腰”和“攒拳怒目增力气”加做1～3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5 yhChineseAnwser64=new YhChineseAnwser5();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：足三里、关元、气海、神阙；\n" +
                        "定位：关元穴位于前正中线上，脐下3寸；气海穴位于前正中线上，脐下1.5寸；神阙穴位于脐窝中央。\n" +
                        "操作：艾灸法\n" +
                        "平躺，借助温灸盒，对每个穴位进行温灸，每个穴位时间10分钟，隔日一次，10天为1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if(anwser3s.get(i).getName().equals("阳虚质")){
                YhChineseAnwser4 yhChineseAnwser4=new YhChineseAnwser4();
                yhChineseAnwser4.setName("阳虚质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if(yhChineseAnwser.getcIstype()==1){
                    yhChineseAnwser4.setState("是");
                }else if(yhChineseAnwser.getcIstype()==2){
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5> yhChineseAnwser5s=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser50=new YhChineseAnwser5();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("阳气不足，以畏寒怕冷、手足不温等虚寒表现为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5 yhChineseAnwser51=new YhChineseAnwser5();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("肌肉松软不实。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5 yhChineseAnwser53=new YhChineseAnwser5();
                yhChineseAnwser53.setTitle("常见表现");
                yhChineseAnwser53.setTxt("平素畏冷，手足不温，喜热饮食，精神不振，舌淡胖嫩，脉沉迟。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5 yhChineseAnwser54=new YhChineseAnwser5();
                yhChineseAnwser54.setTitle("心理特征");
                yhChineseAnwser54.setTxt("性格多沉静、内向。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5 yhChineseAnwser55=new YhChineseAnwser5();
                yhChineseAnwser55.setTitle("发病倾向");
                yhChineseAnwser55.setTxt("易患痰饮、肿胀、泄泻等病；感邪易从寒化。");
                yhChineseAnwser5s.add(yhChineseAnwser55);

                YhChineseAnwser5 yhChineseAnwser56=new YhChineseAnwser5();
                yhChineseAnwser56.setTitle("对外界环境适应能力");
                yhChineseAnwser56.setTxt("耐夏不耐冬；易感风、寒、湿邪。");
                yhChineseAnwser5s.add(yhChineseAnwser56);


                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5> yhChineseAnwser52=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser60=new YhChineseAnwser5();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。\n" +
                        "宜加强精神调养，善于调节自己的情感，去忧悲、防惊恐、和喜怒、消除不良情绪的影响。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5 yhChineseAnwser61=new YhChineseAnwser5();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜多吃甘温益气的食物，比如牛羊狗肉、葱、姜、蒜、花椒、鳝鱼、韭菜、辣椒、胡椒等。\n" +
                        "少食生冷寒凉食物，如黄瓜、藕、梨、西瓜等。\n" +
                        "参考食疗药膳：当归生姜羊肉汤、小米芝麻粥、银耳百合汤、黄精石斛老鸭汤、石橄榄汤等。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5 yhChineseAnwser62=new YhChineseAnwser5();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("阳虚质者耐春夏不耐秋冬，秋冬季节要适当暖衣温食以养护阳气，尤其要注意腰部和下肢保暖，每天以热水泡脚为宜。夏季暑热多汗，也易导致阳气外泄，使阳气虚于内。\n" +
                        "建议尽量避免强力劳作和大汗，也不可恣意贪凉饮冷。\n" +
                        "多在阳光充足的情况下适当进行户外活动，不可在阴暗潮湿寒冷的环境下长期工作和生活。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5 yhChineseAnwser63=new YhChineseAnwser5();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("在运动中应注意避风寒，不宜大汗，适合做一些温和的有氧运动如慢走、太极剑、太极拳等。\n" +
                        "八段锦的“背后七颠百病消”和“两手攀足固肾腰”加做1～3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5 yhChineseAnwser64=new YhChineseAnwser5();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：足三里、命门、肾俞。\n" +
                        "定位：命门穴位于后正中线上，第2腰椎棘突下凹陷中；肾俞穴位于第2腰椎棘突下，旁开1.5寸。\n" +
                        "操作：艾灸法\n" +
                        "俯卧，借助温灸盒，对穴位进行温灸，时间10～15分钟，隔日一次，10天为1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);

                YhChineseAnwser5 yhChineseAnwser65=new YhChineseAnwser5();
                yhChineseAnwser65.setTitle("其它");
                yhChineseAnwser65.setTxt("推拿保健：采用摩擦腰肾法\n" +
                        "以两手平掌的鱼际、掌根或两手虚拳的拳眼，拳背着力，同时做上下左右摩擦两侧腰骶部。每次15分钟，每天2次，10天1疗程。\n" +
                        "做坐式八段锦的“闭气搓手热，背后摩精门，左右辘轳转，两脚放舒伸。翻掌向上托，弯腰攀足频”。");
                yhChineseAnwser52.add(yhChineseAnwser65);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if(anwser3s.get(i).getName().equals("阴虚质")){
                YhChineseAnwser4 yhChineseAnwser4=new YhChineseAnwser4();
                yhChineseAnwser4.setName("阴虚质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if(yhChineseAnwser.getdIstype()==1){
                    yhChineseAnwser4.setState("是");
                }else if(yhChineseAnwser.getdIstype()==2){
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5> yhChineseAnwser5s=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser50=new YhChineseAnwser5();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("阴液亏少，以口燥咽干、手足心热等虚热表现为主要特征。 ");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5 yhChineseAnwser51=new YhChineseAnwser5();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("体形偏瘦。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5 yhChineseAnwser59=new YhChineseAnwser5();
                yhChineseAnwser59.setTitle("常见表现");
                yhChineseAnwser59.setTxt("手足心热，口燥咽干，鼻微干，喜冷饮，大便干燥，舌红少津，脉细数。");
                yhChineseAnwser5s.add(yhChineseAnwser59);

                YhChineseAnwser5 yhChineseAnwser53=new YhChineseAnwser5();
                yhChineseAnwser53.setTitle("心理特征");
                yhChineseAnwser53.setTxt("性情急躁，外向好动，活泼。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5 yhChineseAnwser54=new YhChineseAnwser5();
                yhChineseAnwser54.setTitle("发病倾向");
                yhChineseAnwser54.setTxt("易患虚劳、失精、不寐等病；感邪易从热化。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5 yhChineseAnwser55=new YhChineseAnwser5();
                yhChineseAnwser55.setTitle("对外界环境适应能力");
                yhChineseAnwser55.setTxt("耐冬不耐夏；不耐受暑、热、燥邪。");
                yhChineseAnwser5s.add(yhChineseAnwser55);


                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5> yhChineseAnwser52=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser60=new YhChineseAnwser5();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。\n" +
                        "宜应遵循《黄帝内经》中“恬淡虚无”、“精神内守”之养神大法，少与人争，以减少激怒。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5 yhChineseAnwser61=new YhChineseAnwser5();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜吃甘凉滋润的食物，比如黑大豆、黑芝麻、蚌肉、兔肉、鸭肉、百合、豆腐、豆浆、猪头、猪髓、燕窝、银耳、木耳、甲鱼、牡蛎肉、鱼翅、干贝、麻油、番茄、葡萄、柑橘、荸荠、香蕉、梨、苹果、桑葚、柿子、甘蔗等。\n" +
                        "少吃羊肉、狗肉、辣椒、葱、蒜等性温燥烈之品。\n" +
                        "参考食疗药膳：莲子百合煲瘦肉、韭菜炒鸡蛋、板栗核桃粥、乌根汤。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5 yhChineseAnwser62=new YhChineseAnwser5();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("熬夜、剧烈运动、高温酷暑的工作生活环境等能加重阴虚倾向，应尽量避免。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5 yhChineseAnwser63=new YhChineseAnwser5();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("对于阴虚体质的老年人应保证每天半小时～1小时的有氧运动，如慢走、游泳、太极拳等。\n" +
                        "可做搅海、漱津，即齿常叩 ,津常咽。\n" +
                        "八段锦的“五劳七伤往后瞧”和“两手攀足固肾腰”加做1～3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5 yhChineseAnwser64=new YhChineseAnwser5();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：三阴交、太溪；\n" +
                        "定位：三阴交穴位于内踝尖上三寸，胫骨后缘；太溪穴位于足内侧，内踝后方，内踝尖与跟腱之间的凹陷处。\n" +
                        "操作方法：用大拇指或中指按压三阴交和太溪穴，两侧穴位同时操作。每次按压操作5～10分钟。每日2次，10天1个疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if(anwser3s.get(i).getName().equals("痰湿质")){
                YhChineseAnwser4 yhChineseAnwser4=new YhChineseAnwser4();
                yhChineseAnwser4.setName("痰湿质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if(yhChineseAnwser.geteIstype()==1){
                    yhChineseAnwser4.setState("是");
                }else if(yhChineseAnwser.geteIstype()==2){
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5> yhChineseAnwser5s=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser50=new YhChineseAnwser5();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("痰湿凝聚，以形体肥胖、腹部肥满、口黏苔腻等痰湿表现为主要特征。 ");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5 yhChineseAnwser51=new YhChineseAnwser5();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("体形肥胖，腹部肥满松软。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5 yhChineseAnwser53=new YhChineseAnwser5();
                yhChineseAnwser53.setTitle("常见表现");
                yhChineseAnwser53.setTxt("面部皮肤油脂较多，多汗且黏，胸闷，痰多，口黏腻或甜，喜食肥甘甜黏，苔腻，脉滑。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5 yhChineseAnwser54=new YhChineseAnwser5();
                yhChineseAnwser54.setTitle("心理特征");
                yhChineseAnwser54.setTxt("性格偏温和、稳重，多善于忍耐。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5 yhChineseAnwser55=new YhChineseAnwser5();
                yhChineseAnwser55.setTitle("发病倾向");
                yhChineseAnwser55.setTxt("易患消渴、中风、胸痹等病。");
                yhChineseAnwser5s.add(yhChineseAnwser55);

                YhChineseAnwser5 yhChineseAnwser56=new YhChineseAnwser5();
                yhChineseAnwser56.setTitle("对外界环境适应能力");
                yhChineseAnwser56.setTxt("对梅雨季节及湿重环境适应能力差。");
                yhChineseAnwser5s.add(yhChineseAnwser56);


                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5> yhChineseAnwser52=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser60=new YhChineseAnwser5();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。\n" +
                        "宜欣赏激进、振奋的音乐，如二胡《赛马》等。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5 yhChineseAnwser61=new YhChineseAnwser5();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜选用健脾助运、祛湿化痰的食物，如冬瓜、白萝卜、薏苡仁、赤小豆、荷叶、山楂、生姜、荠菜、紫菜、海带、鲫鱼、鲤鱼、鲈鱼、文蛤等。\n" +
                        "少食肥、甜、油、黏（腻）的食物。\n" +
                        "参考食疗药膳：荷叶粥、冬瓜海带薏米排骨汤、薏米燕麦粥、香菇竹笋汤、紫菜白萝卜汤、鲫鱼豆腐汤、绞股蓝玉米须茶、盐肤木根汤（蒲芦盐根汤）。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5 yhChineseAnwser62=new YhChineseAnwser5();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("居住环境宜干燥，不宜潮湿，穿衣面料以棉、麻、丝等透气散湿的天然纤维为佳，尽量保持宽松，有利于汗液蒸发，祛除体内湿气。 \n" +
                        "晚上睡觉枕头不宜过高，防止打鼾加重；早睡早起，不要过于安逸，勿贪恋沙发和床榻。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5 yhChineseAnwser63=new YhChineseAnwser5();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("坚持长期运动锻炼，强度应根据自身的状况循序渐进。不宜在阴雨季节、天气湿冷的气候条件下运动。 \n" +
                        "可选择快走、武术以及打羽毛球等，使松弛的肌肉逐渐变得结实、致密。如果体重过重、膝盖受损，可选择游泳。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5 yhChineseAnwser64=new YhChineseAnwser5();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：丰隆、足三里。\n" +
                        "定位：足三里（位置见气虚质）。丰隆位于小腿前外侧，当外踝尖上8寸，条口外，距胫骨前缘二横指处（见图6）。\n" +
                        "操作：采用指揉法，用大拇指或中指按压，足三里穴可以两侧穴位同时操作。每次按压操作5～10分钟，每日两次，10天1个疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if(anwser3s.get(i).getName().equals("湿热质")){
                YhChineseAnwser4 yhChineseAnwser4=new YhChineseAnwser4();
                yhChineseAnwser4.setName("湿热质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if(yhChineseAnwser.getfIstype()==1){
                    yhChineseAnwser4.setState("是");
                }else if(yhChineseAnwser.getfIstype()==2){
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5> yhChineseAnwser5s=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser50=new YhChineseAnwser5();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("湿热内蕴，以面垢油光、口苦、苔黄腻等湿热表现为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5 yhChineseAnwser51=new YhChineseAnwser5();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("形体中等或偏瘦。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5 yhChineseAnwser53=new YhChineseAnwser5();
                yhChineseAnwser53.setTitle("常见表现");
                yhChineseAnwser53.setTxt("面垢油光，易生痤疮，口苦口干，身重困倦，大便黏滞不畅或燥结，小便短黄，男性易阴囊潮湿， 女性易带下增多， 舌质偏红，苔黄腻，脉滑数。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5 yhChineseAnwser54=new YhChineseAnwser5();
                yhChineseAnwser54.setTitle("心理特征");
                yhChineseAnwser54.setTxt("容易心烦急躁。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5 yhChineseAnwser55=new YhChineseAnwser5();
                yhChineseAnwser55.setTitle("发病倾向");
                yhChineseAnwser55.setTxt("易患疮疖、黄疸、热淋等病。");
                yhChineseAnwser5s.add(yhChineseAnwser55);

                YhChineseAnwser5 yhChineseAnwser56=new YhChineseAnwser5();
                yhChineseAnwser56.setTitle("对外界环境适应能力");
                yhChineseAnwser56.setTxt("对夏末秋初湿热气候，湿重或气温偏高环境较难适应。");
                yhChineseAnwser5s.add(yhChineseAnwser56);
                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5> yhChineseAnwser52=new ArrayList<>();


                YhChineseAnwser5 yhChineseAnwser60=new YhChineseAnwser5();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。\n" +
                        "宜注意精神调节，保持心情开朗舒畅。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5 yhChineseAnwser61=new YhChineseAnwser5();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("提倡饮食清淡，多吃甘寒、甘平、清利湿热的食物，如薏苡仁、莲子、茯苓、红小豆、绿豆、冬瓜、丝瓜、葫芦、苦瓜、黄瓜、西瓜、白菜、芹菜、卷心菜、莲藕、空心菜、苋菜等。\n" +
                        "少吃胡桃仁、鹅肉、羊肉、狗肉、鳝鱼、香菜、辣椒、花椒、酒、饴糖、胡椒、蜂蜜等甘酸滋腻之品及火锅、烹炸、烧烤等辛温助热食品。\n" +
                        "参考食疗药膳：薏米绿豆粥、绿豆玉米粥、冬瓜排骨汤、海带排骨汤、莲藕排骨汤等。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5 yhChineseAnwser62=new YhChineseAnwser5();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("不宜熬夜，或过度疲劳。\n" +
                        "要保持二便通畅，防止湿热郁聚。\n" +
                        "注意个人卫生，预防皮肤病变。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5 yhChineseAnwser63=new YhChineseAnwser5();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("对于湿热体质的老年人每天应有规律地有氧运动，如游泳、爬山、慢走、太极拳、八段锦等。\n" +
                        "八段锦的“摇头摆尾去心火”和“调理脾胃须单举”加做1～3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5 yhChineseAnwser64=new YhChineseAnwser5();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：阴陵泉、阳陵泉。\n" +
                        "定位：阴陵泉穴位于胫骨内侧踝下方凹陷处；阳陵泉穴位于小腿外侧，当腓骨小头前下方凹陷处。\n" +
                        "操作方法：用大拇指或中指按压阴陵泉穴和阳陵泉穴，两侧穴位同时操作。每次按压操作5～10分钟。每日2次，10天1个疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if(anwser3s.get(i).getName().equals("血瘀质")){
                YhChineseAnwser4 yhChineseAnwser4=new YhChineseAnwser4();
                yhChineseAnwser4.setName("血瘀质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if(yhChineseAnwser.getgIstype()==1){
                    yhChineseAnwser4.setState("是");
                }else if(yhChineseAnwser.getgIstype()==2){
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5> yhChineseAnwser5s=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser50=new YhChineseAnwser5();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("血行不畅，以肤色晦黯、舌质紫黯等血瘀表现为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5 yhChineseAnwser51=new YhChineseAnwser5();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("胖瘦均见。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5 yhChineseAnwser59=new YhChineseAnwser5();
                yhChineseAnwser59.setTitle("常见表现");
                yhChineseAnwser59.setTxt("肤色晦黯，色素沉着，容易出现瘀斑，口唇黯淡，舌黯或有瘀点，舌下络脉紫黯或增粗，脉涩。");
                yhChineseAnwser5s.add(yhChineseAnwser59);

                YhChineseAnwser5 yhChineseAnwser53=new YhChineseAnwser5();
                yhChineseAnwser53.setTitle("心理特征");
                yhChineseAnwser53.setTxt("易烦，健忘。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5 yhChineseAnwser54=new YhChineseAnwser5();
                yhChineseAnwser54.setTitle("发病倾向");
                yhChineseAnwser54.setTxt("易患症瘕及痛证、血证等。");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5 yhChineseAnwser55=new YhChineseAnwser5();
                yhChineseAnwser55.setTitle("对外界环境适应能力");
                yhChineseAnwser55.setTxt("不耐受寒邪。");
                yhChineseAnwser5s.add(yhChineseAnwser55);


                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5> yhChineseAnwser52=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser60=new YhChineseAnwser5();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。精神调养上，要培养乐观的情绪。精神愉快则气血和畅，营卫流通，有利血瘀体质的改善。反之，苦闷、忧郁则可加重血瘀倾向。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5 yhChineseAnwser61=new YhChineseAnwser5();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("宜多吃具有活血化瘀的食物如黑豆、黄豆、香菇、茄子、油菜、羊血、芒果、木瓜、海藻、海带、紫菜、萝卜、胡萝卜、金橘、橙子、柚子、桃子、李子、山楂、醋、玫瑰花、绿茶、红糖、黄酒、葡萄糖、白酒等具有活血、散结、行气、疏肝解郁作用的食物。\n" +
                        "少吃肥猪肉等滋腻之品。应解除烟酒。\n" +
                        "参考食疗药膳：黑豆川芎粥、黑豆粥、田七鸡肉煲、醋黑豆、山楂红枣茶、黑木耳炒大白菜、洋葱炒羊肉、乌根汤。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5 yhChineseAnwser62=new YhChineseAnwser5();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("血得温则行，得寒则凝。血瘀质者要避免寒冷刺激。\n" +
                        "日常生活中应注意动静结合，不可贪图安逸，加重气血郁滞。气为血帅，故亦需注意情志舒畅，勿恼怒郁愤。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5 yhChineseAnwser63=new YhChineseAnwser5();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("每天应有规律的有氧运动，避免剧烈以及过量的体育运动。可采用“步行健身法”，通过步行运动，促进全身血液的运行，有活血化瘀的功效。\n" +
                        "八段锦的“左右开弓似射雕”和“双手托天理三焦”加做1—3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5 yhChineseAnwser64=new YhChineseAnwser5();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：血海、内关\n" +
                        "定位：屈膝，在髌骨内上缘上2寸，当股四头肌内侧头的隆起处。内关穴位于腕横纹上2寸，掌长肌腱与桡侧曲肌腱之间。\n" +
                        "操作方法：用大拇指或中指按压血海穴及内关穴，两侧穴位同时操作。每次按压操作5—10分钟。每日2次，10天1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if(anwser3s.get(i).getName().equals("气郁质")){
                YhChineseAnwser4 yhChineseAnwser4=new YhChineseAnwser4();
                yhChineseAnwser4.setName("气郁质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if(yhChineseAnwser.gethIstype()==1){
                    yhChineseAnwser4.setState("是");
                }else if(yhChineseAnwser.gethIstype()==2){
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5> yhChineseAnwser5s=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser50=new YhChineseAnwser5();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("气机郁滞，以神情抑郁、忧虑脆弱等气郁表现为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5 yhChineseAnwser51=new YhChineseAnwser5();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("形体瘦者为多。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5 yhChineseAnwser53=new YhChineseAnwser5();
                yhChineseAnwser53.setTitle("常见表现");
                yhChineseAnwser53.setTxt("神情抑郁，情感脆弱，烦闷不乐，舌淡红，苔薄白，脉弦。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5 yhChineseAnwser54=new YhChineseAnwser5();
                yhChineseAnwser54.setTitle("心理特征");
                yhChineseAnwser54.setTxt("性格内向不稳定、敏感多虑。 ");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5 yhChineseAnwser55=new YhChineseAnwser5();
                yhChineseAnwser55.setTitle("发病倾向");
                yhChineseAnwser55.setTxt("易患脏躁、梅核气、百合病及郁证等。");
                yhChineseAnwser5s.add(yhChineseAnwser55);

                YhChineseAnwser5 yhChineseAnwser56=new YhChineseAnwser5();
                yhChineseAnwser56.setTitle("对外界环境适应能力");
                yhChineseAnwser56.setTxt("对精神刺激适应能力较差；不适应阴雨天气。");
                yhChineseAnwser5s.add(yhChineseAnwser56);
                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5> yhChineseAnwser52=new ArrayList<>();


                YhChineseAnwser5 yhChineseAnwser60=new YhChineseAnwser5();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜主动寻求快乐，多参加社会活动、集体文娱活动，常看喜剧、滑稽剧、听相声，以及富有鼓励、激励意义的电影、电视，勿看悲剧、苦剧。\n" +
                        "多听轻快、开朗、激动的音乐，多读积极的、鼓励的、富有乐趣的、展现美好生活前景的书籍，以培养开朗、豁达的意识，在名利上不计较得失，知足常乐。\"");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5 yhChineseAnwser61=new YhChineseAnwser5();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("\"宜多吃小麦、高粱、蒿子秆、香菜、葱、蒜、萝卜、洋葱、苦瓜、黄花菜、海带、海藻、橘子、柚子、槟榔、玫瑰花、梅花等行气、解郁、消食、醒神之品。\n" +
                        "睡前避免饮茶、咖啡等提神醒脑的饮料。\n" +
                        "参考食疗药膳：黄花菜香菇排骨汤、三花茶、糖醋卷心菜、金针菜汤。\n" +
                        "菊花玫瑰茶：杭白菊4朵，玫瑰花2朵，90℃水沏，可以经常服用。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5 yhChineseAnwser62=new YhChineseAnwser5();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("气郁日久易致血行不畅，衣着方面宜选择宽松透气性好的款式，还应注意鞋袜也不宜约束过紧，否则易影响气血运行，出现肢体麻木或发凉等症状。\n" +
                        "居室环境宽敞明亮，温度、湿度适宜。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5 yhChineseAnwser63=new YhChineseAnwser5();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("气郁体质的老年人每天有半小时～1小时的有氧运动。\n" +
                        "可选择下棋、打牌、瑜珈等体娱游戏，以闲情怡志，促进人际交流。\n" +
                        "八段锦的“左右开弓似射雕”和“双手托天理三焦”加做1～3遍。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5 yhChineseAnwser64=new YhChineseAnwser5();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：太冲、膻中。\n" +
                        "定位：太冲穴位于足背，第1、2跖骨结合部之前凹陷中；膻中穴位于胸部,当前正中线上,平第四肋间,两乳头连线的中点。\n" +
                        "操作方法：用大拇指或中指按压太冲穴和膻中穴，太冲穴两侧穴位同时操作。每次按压操作5～10分钟。每日2次，10天1个疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);

                YhChineseAnwser5 yhChineseAnwser65=new YhChineseAnwser5();
                yhChineseAnwser65.setTitle("其它");
                yhChineseAnwser65.setTxt("经络按摩：选取足厥阴肝经的循行路线，进行经络敲打，每次敲打1个来回，每日2次，10天1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser65);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }

            if(anwser3s.get(i).getName().equals("特禀质")){
                YhChineseAnwser4 yhChineseAnwser4=new YhChineseAnwser4();
                yhChineseAnwser4.setName("特禀质");
                yhChineseAnwser4.setTizhi("偏颇体质");
                if(yhChineseAnwser.getiIstype()==1){
                    yhChineseAnwser4.setState("是");
                }else if(yhChineseAnwser.getiIstype()==2){
                    yhChineseAnwser4.setState("倾向是");
                }
                List<YhChineseAnwser5> yhChineseAnwser5s=new ArrayList<>();
                YhChineseAnwser5 yhChineseAnwser50=new YhChineseAnwser5();
                yhChineseAnwser50.setTitle("总体特征");
                yhChineseAnwser50.setTxt("先天失常，以生理缺陷、过敏反应等为主要特征。");
                yhChineseAnwser5s.add(yhChineseAnwser50);

                YhChineseAnwser5 yhChineseAnwser51=new YhChineseAnwser5();
                yhChineseAnwser51.setTitle("形体特征");
                yhChineseAnwser51.setTxt("过敏体质者一般无特殊；先天禀赋异常者或有畸形，或有生理缺陷。");
                yhChineseAnwser5s.add(yhChineseAnwser51);

                YhChineseAnwser5 yhChineseAnwser53=new YhChineseAnwser5();
                yhChineseAnwser53.setTitle("常见表现");
                yhChineseAnwser53.setTxt("过敏体质者常见哮喘、风团、咽痒、鼻塞、喷嚏等；患遗传性疾病者有垂直遗传、先天性、 家族性特征；患胎传性疾病者具有母体影响胎儿个体生长发育及相关疾病特征。");
                yhChineseAnwser5s.add(yhChineseAnwser53);

                YhChineseAnwser5 yhChineseAnwser54=new YhChineseAnwser5();
                yhChineseAnwser54.setTitle("心理特征");
                yhChineseAnwser54.setTxt("随禀质不同情况各异。 ");
                yhChineseAnwser5s.add(yhChineseAnwser54);

                YhChineseAnwser5 yhChineseAnwser55=new YhChineseAnwser5();
                yhChineseAnwser55.setTitle("发病倾向");
                yhChineseAnwser55.setTxt("过敏体质者易患哮喘、荨麻疹、花粉症及药物过敏等；遗传性疾病如血友病、先天愚型等；胎传性疾病如五迟（立迟、行迟、发迟、齿迟和语迟）、五软（头软、项软、手足软、肌肉软、口软）、解颅、 胎惊等。");
                yhChineseAnwser5s.add(yhChineseAnwser55);

                YhChineseAnwser5 yhChineseAnwser56=new YhChineseAnwser5();
                yhChineseAnwser56.setTitle("对外界环境适应能力");
                yhChineseAnwser56.setTxt("适应能力差，如过敏体质者对易致过敏季节适应能力差，易引发宿疾。");
                yhChineseAnwser5s.add(yhChineseAnwser56);
                yhChineseAnwser4.setZong(yhChineseAnwser5s);
                List<YhChineseAnwser5> yhChineseAnwser52=new ArrayList<>();


                YhChineseAnwser5 yhChineseAnwser60=new YhChineseAnwser5();
                yhChineseAnwser60.setTitle("情志调摄");
                yhChineseAnwser60.setTxt("宜多参加社会活动，培养广泛的兴趣爱好。");
                yhChineseAnwser52.add(yhChineseAnwser60);

                YhChineseAnwser5 yhChineseAnwser61=new YhChineseAnwser5();
                yhChineseAnwser61.setTitle("饮食调养");
                yhChineseAnwser61.setTxt("饮食宜清淡、均衡、粗细搭配适当、荤素配伍合理。\n" +
                        "少吃荞麦、蚕豆、白扁豆、牛肉、鹅肉、鲤鱼、虾、蟹、茄子、酒、辣椒、浓茶、咖啡等辛辣之品、腥发及含致敏物质的食品。\n" +
                        "参考食疗药膳：黄芪山药粥、黑米花生粥、灵芝红枣茶。");
                yhChineseAnwser52.add(yhChineseAnwser61);

                YhChineseAnwser5 yhChineseAnwser62=new YhChineseAnwser5();
                yhChineseAnwser62.setTitle("起居调摄");
                yhChineseAnwser62.setTxt("避免过敏原的刺激，生活环境中接触的物品如枕头、棉被、床垫、地毯、窗帘、衣橱易附有尘螨，可引起过敏，应常清洗、日晒。\n" +
                        "外出也要避免处在花粉及粉刷油漆的空气中，以免刺激而诱发过敏病症。");
                yhChineseAnwser52.add(yhChineseAnwser62);

                YhChineseAnwser5 yhChineseAnwser63=new YhChineseAnwser5();
                yhChineseAnwser63.setTitle("运动保健");
                yhChineseAnwser63.setTxt("特禀体质的老年人每天有半小时～1小时的有氧运动。\n" +
                        "注意避风寒。");
                yhChineseAnwser52.add(yhChineseAnwser63);

                YhChineseAnwser5 yhChineseAnwser64=new YhChineseAnwser5();
                yhChineseAnwser64.setTitle("穴位保健");
                yhChineseAnwser64.setTxt("选穴：足三里、关元、神阙、肾俞。\n" +
                        "定位：足三里位于外膝眼下三寸，胫骨前嵴外1横指处；关元穴位于前正中线上，脐下3寸；神阙穴位于脐窝中央；肾俞穴位于第2腰椎棘突下，旁开1.5寸。\n" +
                        "操作：点按法\n" +
                        "用大拇指或中指按压足三里穴，两侧穴位同时操作，每次按压操作5～10分钟，每日两次，10天1个疗程。\n" +
                        "操作：艾灸法\n" +
                        "对足三里穴、关元穴、神阙穴、肾俞穴进行温灸，可以借助温灸器，每次时间10～15分钟即可，隔日一次，10天为1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser64);

                YhChineseAnwser5 yhChineseAnwser65=new YhChineseAnwser5();
                yhChineseAnwser65.setTitle("其它");
                yhChineseAnwser65.setTxt("经络按摩：选取足少阴肾经的循行路线，进行经络敲打，每次敲打1个来回，每日2次，10天1疗程。");
                yhChineseAnwser52.add(yhChineseAnwser65);
                yhChineseAnwser4.setZhong(yhChineseAnwser52);
                yhChineseAnwser4s.add(yhChineseAnwser4);
            }
        }
        yhChineseAnwser2.setTabData(yhChineseAnwser4s);

        return yhChineseAnwser2;
    }

    @Override
    public Boolean isfirstzy(String Usercode) {
        int count=yhChinesePhysiqueMapper.isfirstzy(Usercode);
        if(count==0){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public YhChineseAnwser2 isfirstzy2(String Usercode) {
        YhChineseAnwser2 yhChineseAnwser2=new YhChineseAnwser2();
        YhChineseAnwser yhChineseAnwser=yhChinesePhysiqueMapper.selectanwser(Usercode);
        int count=yhChinesePhysiqueMapper.alltims();
        YhChineseAnwser2 yhChineseAnwser21=new YhChineseAnwser2();
        yhChineseAnwser21.setNum1(count+"");
        if(yhChineseAnwser!=null){
        int countss=0;
        if(yhChineseAnwser.getaIstype()==1){
            countss++;
        }
        if(yhChineseAnwser.getbIstype()==1){
            countss++;
        }
        if(yhChineseAnwser.getcIstype()==1){
            countss++;
        }
        if(yhChineseAnwser.getdIstype()==1){
            countss++;
        }
        if(yhChineseAnwser.geteIstype()==1){
            countss++;
        }
        if(yhChineseAnwser.getfIstype()==1){
            countss++;
        }
        if(yhChineseAnwser.getgIstype()==1){
            countss++;
        }
        if(yhChineseAnwser.gethIstype()==1){
            countss++;
        }
        if(yhChineseAnwser.getiIstype()==1){
            countss++;
        }

        if(yhChineseAnwser.getaIstype()==2){
            countss++;
        }
        if(yhChineseAnwser.getbIstype()==2){
            countss++;
        }
        if(yhChineseAnwser.getcIstype()==2){
            countss++;
        }
        if(yhChineseAnwser.getdIstype()==2){
            countss++;
        }
        if(yhChineseAnwser.geteIstype()==2){
            countss++;
        }
        if(yhChineseAnwser.getfIstype()==2){
            countss++;
        }
        if(yhChineseAnwser.getgIstype()==2){
            countss++;
        }
        if(yhChineseAnwser.gethIstype()==2){
            countss++;
        }
        if(yhChineseAnwser.getiIstype()==2){
            countss++;
        }


        yhChineseAnwser21.setNumbers(countss+"");
        yhChineseAnwser21.setRusult3(CalculateTime(yhChineseAnwser.getTime()));

        }
        return yhChineseAnwser21;
    }

    @Override
    public List<Yhhealthplan1> getallhelplan(String Usercode) {
        List<Yhhealthplan1> yhhealthplan1s=new ArrayList<>();
        YhUserHealthyPlan yhUserHealthyPlan=yhChinesePhysiqueMapper.getmaxplan(Usercode);
        if(yhUserHealthyPlan!=null){
            if(yhUserHealthyPlan.getPlanDay()==28){
                List<YhHealthyPlandays> days=yhChinesePhysiqueMapper.selectplandays(28,yhUserHealthyPlan.getPlanId());
                for (int i=0;i<days.size();i++){
                    Yhhealthplan1 yhhealthplan1=new Yhhealthplan1();
                    yhhealthplan1.setDay(days.get(i).getPlanday().toString());
                    yhhealthplan1.setNum(days.get(i).getPlan_num().toString());
                    String [] tailid=days.get(i).getPlandetailId().split(";");
                    List<Yhhealthplan2> yhhealthplan2List=new ArrayList<>();
                    List<YhUserHealthyPlan> yhUserHealthyPlan1=new ArrayList<>();
                    if(i==0){
                        //如果是今天的话查询这一天该用户完成了多少任务
                        yhUserHealthyPlan1=yhChinesePhysiqueMapper.getyhuserplan(Usercode,days.get(i).getPlanday(),days.get(i).getPlanId());

                    }
                    for (int j=0;j<tailid.length;j++){
                        Yhhealthplan2 yhhealthplan2=new Yhhealthplan2();
                        YhHealthyPlandetail yhHealthyPlandetail=yhChinesePhysiqueMapper.getplandetail(tailid[j]);
                        yhhealthplan2.setUrl(tailid[j]);
                        yhhealthplan2.setTaskType(yhHealthyPlandetail.getPlandetailType().toString());
                        yhhealthplan2.setPlanday(days.get(i).getPlanday().toString());
                        yhhealthplan2.setPlanids(yhUserHealthyPlan.getPlanId().toString());
                        if(i==0){
                            yhhealthplan2.setOver("3");
                        }else{
                            yhhealthplan2.setOver("2");
                        }
                        if(i==0){
                            Boolean b=false;
                            //如果是今天的话查询这一天该用户完成了多少任务
                            for (int u=0;u<yhUserHealthyPlan1.size();u++){
                                if(yhUserHealthyPlan1.get(u).getPlandetailId()!=null){
                                    String popo[]=yhUserHealthyPlan1.get(u).getPlandetailId().split(";");
                                    for (int y=0;y<popo.length;y++){
                                        if(popo[y].equals(tailid[j])){
                                            b=true;
                                        }
                                    }
                                }

                            }
                            if(b){
                                yhhealthplan2.setOver("2");
                            }
                        }
                        yhhealthplan2.setTitle(yhHealthyPlandetail.getPlandetailTitle());
                        yhhealthplan2List.add(yhhealthplan2);
                    }
                    yhhealthplan1.setRenwuList(yhhealthplan2List);
                    yhhealthplan1s.add(yhhealthplan1);
                }
            }else{
                List<YhHealthyPlandays> days=yhChinesePhysiqueMapper.selectplandays(yhUserHealthyPlan.getPlanDay()+1,yhUserHealthyPlan.getPlanId());
                for (int i=0;i<days.size();i++){
                    Yhhealthplan1 yhhealthplan1=new Yhhealthplan1();
                    yhhealthplan1.setDay(days.get(i).getPlanday().toString());
                    yhhealthplan1.setNum(days.get(i).getPlan_num().toString());
                    String [] tailid=days.get(i).getPlandetailId().split(";");
                    List<Yhhealthplan2> yhhealthplan2List=new ArrayList<>();
                    List<YhUserHealthyPlan> yhUserHealthyPlan1=new ArrayList<>();
                    if(i==1){
                        //如果是今天的话查询这一天该用户完成了多少任务
                         yhUserHealthyPlan1=yhChinesePhysiqueMapper.getyhuserplan(Usercode,days.get(i).getPlanday(),days.get(i).getPlanId());

                    }
                    for (int j=0;j<tailid.length;j++){
                        Yhhealthplan2 yhhealthplan2=new Yhhealthplan2();
                        YhHealthyPlandetail yhHealthyPlandetail=yhChinesePhysiqueMapper.getplandetail(tailid[j]);
                        yhhealthplan2.setTaskType(yhHealthyPlandetail.getPlandetailType().toString());
                        yhhealthplan2.setPlanday(days.get(i).getPlanday().toString());
                        yhhealthplan2.setPlanids(yhUserHealthyPlan.getPlanId().toString());
                        if(i==0){
                            yhhealthplan2.setUrl("-999");
                            yhhealthplan2.setOver("1");
                        }else{
                            yhhealthplan2.setUrl(tailid[j]);
                            yhhealthplan2.setOver("3");
                            if(i==1){
                                Boolean b=false;
                                //如果是今天的话查询这一天该用户完成了多少任务
                                for (int u=0;u<yhUserHealthyPlan1.size();u++){
                                    if(yhUserHealthyPlan1.get(u).getPlandetailId()!=null){
                                        String popo[]=yhUserHealthyPlan1.get(u).getPlandetailId().split(";");
                                        for (int y=0;y<popo.length;y++){
                                            if(popo[y].equals(tailid[j])){
                                                b=true;
                                            }
                                        }
                                    }

                                }
                                if(b){
                                    yhhealthplan2.setOver("2");
                                }
                            }

                        }

                        yhhealthplan2.setTitle(yhHealthyPlandetail.getPlandetailTitle());
                        yhhealthplan2List.add(yhhealthplan2);
                    }
                    yhhealthplan1.setRenwuList(yhhealthplan2List);
                    yhhealthplan1s.add(yhhealthplan1);
                }

            }

        }else {
            yhUserHealthyPlan=yhChinesePhysiqueMapper.getbeginplan(Usercode);
            List<YhHealthyPlandays> days=yhChinesePhysiqueMapper.selectplandays(yhUserHealthyPlan.getPlanDay(),yhUserHealthyPlan.getPlanId());
            for (int i=0;i<days.size();i++){
                Yhhealthplan1 yhhealthplan1=new Yhhealthplan1();
                yhhealthplan1.setDay(days.get(i).getPlanday().toString());
                yhhealthplan1.setNum(days.get(i).getPlan_num().toString());
                String [] tailid=days.get(i).getPlandetailId().split(";");
                List<Yhhealthplan2> yhhealthplan2List=new ArrayList<>();
                for (int j=0;j<tailid.length;j++){
                    Yhhealthplan2 yhhealthplan2=new Yhhealthplan2();
                    YhHealthyPlandetail yhHealthyPlandetail=yhChinesePhysiqueMapper.getplandetail(tailid[j]);
                    yhhealthplan2.setUrl("-999");
                    yhhealthplan2.setOver("1");
                    yhhealthplan2.setTitle(yhHealthyPlandetail.getPlandetailTitle());
                    yhhealthplan2List.add(yhhealthplan2);
                }
                yhhealthplan1.setRenwuList(yhhealthplan2List);
                yhhealthplan1s.add(yhhealthplan1);
            }
        }
        return yhhealthplan1s;
    }

    public static String CalculateTime(String time) {
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        String msg = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 指定时间格式
        Date setTime = null; // 指定时间
        try {
            setTime = sdf.parse(time); // 将字符串转换为指定的时间格式
        } catch (ParseException e) {

            e.printStackTrace();
        }
        long reset = setTime.getTime(); // 获取指定时间的毫秒数
        long dateDiff = nowTime - reset;
        if (dateDiff < 0) {
            msg = "输入的时间不对";
        } else {
            long dateTemp1 = dateDiff / 1000; // 秒
            long dateTemp2 = dateTemp1 / 60; // 分钟
            long dateTemp3 = dateTemp2 / 60; // 小时
            long dateTemp4 = dateTemp3 / 24; // 天数
            long dateTemp5 = dateTemp4 / 30; // 月数
            long dateTemp6 = dateTemp5 / 12; // 年数
            if (dateTemp6 > 0) {
                msg = dateTemp6 + "年前";
            } else if (dateTemp5 > 0) {
                msg = dateTemp5 + "个月前";
            } else if (dateTemp4 > 0) {
                msg = dateTemp4 + "天前";
            } else if (dateTemp3 > 0) {
                msg = dateTemp3 + "小时前";
            } else if (dateTemp2 > 0) {
                msg = dateTemp2 + "分钟前";
            } else if (dateTemp1 > 0) {
                msg = "不久前";
            }
        }
        return msg;
    }

}
