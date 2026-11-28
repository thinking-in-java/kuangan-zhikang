"""Reproducible sourced questionnaires; no employee records or responses are generated."""
import copy
import hashlib
import json
from collections import Counter
from pathlib import Path
from questionnaire_management_rules import apply as apply_management_rules

ROOT = Path(__file__).resolve().parents[1]
DEST = ROOT / 'yihe-framework/src/main/resources'
VERSION = '2026.11.27'
SOURCES = {
    'occupational': {'title': '中国劳动者职业健康素养：基本知识和技能（2022年版）', 'url': 'https://www.chinacdc.cn/jkyj/hjwsyzdkz/jswj3/202203/t20220309_295926.html', 'use': '参考主题，调查题为本项目原创，不复制国家监测问卷或其评分算法'},
    'literacy': {'title': '中国公民健康素养：基本知识与技能（2024年版）', 'url': 'https://wjw.gz.gov.cn/zmhd/cjwt/zczy/content/post_9687151.html', 'use': '参考健康教育主题；原创需求调查，不作为诊断'},
    'wellbq': {'title': 'NIOSH Worker Well-Being Questionnaire：领域框架', 'url': 'https://www.cdc.gov/niosh/twh/php/wellbq/', 'use': '仅参考工作体验、组织支持、工作环境、健康、家庭社会五领域，不复制受单项使用条件限制的题目'},
    'phq': {'title': 'PHQ and GAD-7 Measures: Author Instruction Manual', 'url': 'https://www.uab.edu/medicine/pcp-sci/images/SCIMS/PHQ-9_Instruction_Manual.pdf', 'use': '作者说明为公共领域；保留题目、时间窗、原始分值与独立计分，中文参考呈现需专业核对'},
    'dass': {'title': 'DASS：UNSW 官方许可与使用说明', 'url': 'https://www2.psy.unsw.edu.au/dass/DASSFAQ.htm', 'use': 'Public domain；题目与计分键可复制，不修改或单独售卖量表；由专业人员解释，不向职工自动给出分数解释'},
    'dass_zh': {'title': 'DASS-42：Zuo & Chang 简体中文译本', 'url': 'https://www2.psy.unsw.edu.au/dass/Chinese/DASS42%20simplified%20Chinese.pdf', 'use': '逐字保留译文；21题版按英文短版与42题原题对应关系抽取，明确为参考呈现'},
    'dass21': {'title': 'DASS-21：英文原表与计分键', 'url': 'https://www2.psy.unsw.edu.au/dass/Download%20files/Dass21.pdf', 'use': '抑郁、焦虑、压力分别求和，分量表乘2；不输出混合诊断总分'},
}

# Each row names a concrete survey subject and an observable action. These are
# original service-needs questions, not copied or psychometrically validated scales.
DOMAINS = [
 ('dust','粉尘防护与呼吸健康','occupational','防尘与呼吸健康需求调查',[
 ('防尘口罩选用','核对口罩与岗位粉尘防护要求是否相符'),('防尘口罩佩戴','佩戴后检查面部贴合情况'),('口罩日常维护','按说明检查口罩和过滤部件'),('湿式降尘配合','按岗位要求配合使用湿式降尘措施'),('局部通风观察','按岗位职责关注作业点通风状态'),('粉尘检测结果理解','查看本人岗位粉尘检测结果的说明'),('粉尘接触史记录','如实记录接触粉尘的岗位和时间'),('咳嗽情况记录','记录反复咳嗽或咳痰的发生情况'),('肺功能复查安排','按医嘱安排肺功能复查'),('胸部检查资料整理','保存历次胸部检查报告以便医生对照')]),
 ('noise','噪声防护与听力健康','occupational','噪声与听力健康需求调查',[
 ('耳塞选用','核对耳塞是否符合岗位噪声防护要求'),('耳塞规范佩戴','按照培训方法检查耳塞佩戴情况'),('耳罩日常维护','检查耳罩的密封垫和清洁状态'),('噪声岗位告知','查看本人岗位噪声危害告知信息'),('听力检查准备','按体检机构说明做好听力检查准备'),('听力报告理解','请专业人员解释本人听力检查结果'),('耳鸣情况记录','记录耳鸣的发生时间及相关情况'),('沟通与听力困难','记录工作沟通中听不清的具体情境'),('噪声接触史补录','补全历次噪声岗位与接触时间'),('耳部不适咨询','出现持续耳部不适时向医务人员咨询')]),
 ('heat','高温与补水支持','occupational','高温岗位健康支持调查',[
 ('高温风险告知','了解本岗位的高温风险提示'),('班前补水准备','按岗位安排准备可用的饮水条件'),('工间休息落实','按照工作安排落实工间休息'),('降温用品可及性','检查本人能否方便取得降温用品'),('中暑征象认识','参加中暑表现与求助流程的培训'),('同伴热不适观察','发现同伴不适时及时报告'),('高温工作记录','记录高温作业时段与身体感受'),('防护服热负担','反馈防护服影响散热的实际困难'),('恢复区域使用','按安排使用通风或降温休息区域'),('高温不适后复岗沟通','热不适后与医务人员沟通复岗注意事项')]),
 ('ergonomic','肌骨负担与工效支持','occupational','肌骨健康与工效需求调查',[
 ('搬运负荷','按照培训要求评估搬运任务负荷'),('弯腰作业姿势','记录需要长时间弯腰的工作环节'),('重复性操作','关注重复性操作后的身体感受'),('长时间站立','在条件允许时按安排变换站立姿势'),('久坐驾驶','按工作安排进行驾驶间歇休息'),('工具握持','反馈工具握持造成的不适'),('肩颈不适记录','记录肩颈不适的时段与诱因'),('腰背不适记录','记录腰背不适对工作的影响'),('膝关节负担','反馈上下台阶或蹲跪作业的困难'),('工效改进建议','向班组提出降低肌骨负担的改进意见')]),
 ('chemical','化学因素与个体防护','occupational','化学因素防护需求调查',[
 ('有害气体警示','识别岗位有害气体警示标识'),('化学品标签理解','阅读接触化学品的标签说明'),('安全数据说明获取','按工作需要获取化学品安全说明'),('通风异常报告','发现通风异常时按规定报告'),('泄漏情况报告','发现疑似泄漏时报告并遵守现场指令'),('防护手套选用','核对手套与接触物质是否匹配'),('护目用品使用','按岗位要求佩戴护目用品'),('皮肤接触记录','记录工作中皮肤接触化学品的情况'),('清洗设施知晓','了解现场清洗设施与使用方法'),('暴露后就医资料','就医时向医务人员提供接触物质信息')]),
 ('exam','体检准备与报告理解','occupational','体检与复查服务需求调查',[
 ('上岗前体检安排','按通知了解上岗前职业健康检查安排'),('在岗体检参与','按通知参加在岗期间职业健康检查'),('离岗体检安排','离岗时咨询职业健康检查安排'),('体检项目核对','核对体检项目与岗位接害信息'),('体检前准备','按照体检机构说明准备检查'),('原始报告保存','保存本人原始体检报告'),('异常结果咨询','对异常结果向医生提出具体问题'),('复查时间落实','按照医嘱记录并安排复查时间'),('不同年度报告对照','向医生提供不同年度的体检报告'),('报告错误更正','发现报告身份或数值疑问时申请核实')]),
 ('sleep','轮班睡眠与恢复','literacy','轮班睡眠支持需求调查',[
 ('夜班后睡眠安排','提前安排夜班后的休息时间'),('睡眠环境','反馈宿舍光线或噪声对睡眠的影响'),('入睡困难记录','记录入睡困难出现的时段'),('夜间觉醒记录','记录睡眠中反复醒来的情况'),('白天困倦','记录白天困倦对活动的影响'),('班前疲劳沟通','明显困倦时按流程与班组沟通'),('轮班交接恢复','按排班计划安排交接期间休息'),('睡前放松','选择适合自己的睡前放松方式'),('助眠用品咨询','使用助眠用品前向医务人员咨询'),('打鼾情况沟通','将持续打鼾及憋醒情况告知医生')]),
 ('stress','工作压力与情绪支持','wellbq','工作压力支持需求调查',[
 ('任务负荷沟通','及时沟通超出当前能力的任务负荷'),('工作节奏调整','在允许范围内讨论工作节奏调整'),('紧张感识别','注意并记录自己感到紧张的情境'),('担忧表达','向可信任的人表达近期担忧'),('工作后放松','在工作结束后安排适当放松'),('困难求助','遇到难以处理的压力时主动求助'),('压力源梳理','梳理近期主要压力来源'),('突发任务应对','就突发任务寻求清晰的安排说明'),('角色职责明确','向负责人确认不清楚的岗位职责'),('心理咨询渠道','了解企业或医疗机构的心理咨询渠道')]),
 ('mood','情绪变化与专业求助','literacy','情绪支持服务需求调查',[
 ('情绪变化觉察','留意近期情绪变化及持续时间'),('兴趣活动保留','在条件允许时保留喜欢的日常活动'),('低落情绪沟通','情绪持续低落时向可信任的人沟通'),('焦虑困扰咨询','焦虑持续影响生活时咨询专业人员'),('自责想法表达','对反复自责的感受寻求支持'),('易怒情境记录','记录容易烦躁的具体情境'),('注意力困扰','向医务人员说明持续注意力困难'),('心理服务预约','了解心理服务的预约步骤'),('心理健康信息判断','核实心理健康信息的可靠来源'),('就诊材料准备','就诊时准备近期情绪与睡眠记录')]),
 ('support','班组支持与人际沟通','wellbq','班组支持与沟通需求调查',[
 ('同伴帮助','在工作困难时向同伴请求帮助'),('班组反馈','向班组反馈本人遇到的健康困难'),('管理者沟通','与负责人讨论影响工作的健康问题'),('新职工适应','向带教人员反馈新岗位适应情况'),('冲突沟通','发生分歧时寻求合适的沟通方式'),('尊重与接纳','反馈受到不尊重或排斥的经历'),('服务隐私保护','了解健康咨询信息的保密安排'),('家庭沟通','与家人沟通轮班对生活的影响'),('同伴心理支持','倾听同伴困扰并建议寻求合适帮助'),('健康关怀反馈','向服务人员反馈关怀措施是否适合自己')]),
 ('resilience','工作适应与恢复资源','wellbq','工作适应与恢复需求调查',[
 ('岗位变化适应','岗位变化时主动了解新的工作要求'),('返岗支持','返岗前沟通必要的健康支持'),('挫折后的支持','遇到工作挫折后寻找可用支持'),('个人优势识别','识别本人处理工作困难时的优势'),('问题解决资源','遇到新问题时寻找可靠的指导'),('恢复活动安排','安排有助于自己恢复精力的活动'),('小目标设定','为可改变的困难设定可实现的小目标'),('服务参与意愿','表达参与健康支持活动的意愿'),('学习机会获取','了解岗位健康学习机会'),('健康变化适应','身体状况变化时沟通适当调整需求')]),
 ('fatigue','疲劳管理与安全心理','occupational','疲劳与安全支持需求调查',[
 ('班前状态自查','班前留意自己的精神和身体状态'),('疲劳报告渠道','了解明显疲劳时的报告渠道'),('注意力分散情境','记录工作中注意力分散的情境'),('长工时恢复','与负责人沟通长工时后的恢复安排'),('连续夜班感受','反馈连续夜班对身体感受的影响'),('通勤疲劳','安排下班后安全的通勤方式'),('安全担忧表达','主动表达自己对工作安全的担忧'),('险情后的心理支持','经历险情后了解可用的心理支持'),('工作中休息条件','反馈工作中休息条件是否满足需要'),('身体不适停工沟通','明显不适时按照流程报告并寻求评估')]),
 ('diet','饮食营养与体重管理','literacy','饮食营养服务需求调查',[
 ('规律进餐','根据班次安排规律进餐'),('蔬菜选择','在可用餐食中选择适量蔬菜'),('水果获取','安排方便获取水果的方式'),('低盐餐食','了解食堂可提供的少盐餐食选择'),('含糖饮料','记录本人饮用含糖饮料的情况'),('饮水安排','工作期间安排适当饮水'),('夜班餐食','选择适合夜班的餐食安排'),('体重记录','按照个人健康计划记录体重变化'),('营养标签阅读','查看预包装食品的营养标签'),('膳食咨询','存在饮食疑问时咨询营养或医务人员')]),
 ('activity','身体活动与戒烟限酒','literacy','生活方式支持需求调查',[
 ('适宜运动选择','根据身体状况选择合适的活动方式'),('运动计划安排','将适宜活动安排进本人可用时段'),('活动不适记录','记录活动时出现的不适并咨询医生'),('运动场地获取','了解本人可使用的运动场地'),('久坐打断','在工作允许时适当打断久坐'),('吸烟情况记录','如实记录本人吸烟情况'),('戒烟服务','了解可用的戒烟咨询服务'),('二手烟接触','反馈休息场所二手烟接触情况'),('饮酒情况记录','如实记录本人近期饮酒情况'),('饮酒相关困扰','对饮酒造成的困扰寻求专业支持')]),
 ('chronic','慢病自我管理支持','literacy','慢病管理服务需求调查',[
 ('家庭血压记录','按照医生指导记录家庭血压'),('血糖监测记录','按照本人医嘱记录血糖'),('血脂复查安排','按照医嘱安排血脂复查'),('药物清单整理','整理本人正在使用的药物清单'),('处方问题咨询','对处方疑问向开方医生或药师咨询'),('用药提醒','根据医嘱设置适合自己的用药提醒'),('慢病复诊准备','复诊时携带近期监测和用药记录'),('体重变化沟通','将明显体重变化告知医务人员'),('慢病与工作安排','与健康服务人员沟通慢病对工作的影响'),('多机构资料衔接','就诊时提供不同机构的相关报告')]),
 ('service','档案与数字健康服务','wellbq','数字健康服务需求调查',[
 ('身份资料确认','核对系统中的本人身份资料'),('接害史补录','核对并补全本人职业危害接触史'),('报告上传','按说明上传清晰完整的本人报告'),('原始报告查看','查找系统中保存的原始体检报告'),('审核状态理解','区分识别结果与医生审核后的内容'),('更正申请','发现档案错误时提交更正申请'),('随访消息查看','查看属于本人的随访提醒'),('问卷知情同意','填写问卷前阅读用途与隐私说明'),('账号安全','妥善保管本人登录与绑定信息'),('人工服务联系','需要人工帮助时找到相应联系入口')]),
 ('prevention','卫生习惯与日常预防','literacy','日常预防服务需求调查',[
 ('手部卫生','根据需要落实手部清洁'),('呼吸道症状沟通','出现持续呼吸道症状时咨询医务人员'),('疫苗信息核对','向医务人员核对本人适用的疫苗信息'),('宿舍通风','在条件允许时安排宿舍通风'),('饮食卫生','关注餐食与饮水卫生条件'),('口腔保健','安排本人日常口腔清洁和检查'),('视力与照明','反馈工作照明或视力方面的困难'),('皮肤防护','关注工作接触后皮肤的变化'),('急救培训参与','参加企业组织的急救知识培训'),('就医渠道知晓','了解需要医疗帮助时可使用的就医渠道')]),
 ('followup','随访复查与服务体验','wellbq','随访与健康服务体验调查',[
 ('复查预约','按照医嘱了解复查预约步骤'),('检查前材料准备','复查前整理所需报告与资料'),('就医交通安排','提前安排到院复查的交通'),('轮班与预约协调','将轮班冲突告知预约或随访人员'),('随访内容理解','向随访人员确认不清楚的问题'),('健康计划反馈','反馈本人健康计划的执行困难'),('服务时间安排','向服务人员表达合适的联系时段'),('服务沟通清晰度','遇到不理解的服务说明时再次询问'),('投诉与建议','通过合适渠道提交健康服务建议'),('后续支持衔接','在本次服务后确认后续联系安排')]),
]

def opts(labels, scores=None):
    return [{'label': label, 'score': scores[i] if scores else 0} for i, label in enumerate(labels)]

def question(qid, text, options, **kw):
    value = dict(id=qid, code=qid, text=text, type='single', status='启用', required=False,
                 options='；'.join(o['label'] for o in options), optionItems=options)
    value.update(kw)
    return value

def original_bank():
    questions, scales, categories = [], [], []
    for di, (slug, category, source, title, topics) in enumerate(DOMAINS):
        cid='BANK-'+slug
        categories.append(dict(id=cid, name=category, parent='custom', enabled=True, order=(di+1)*10,
                               description='原创调查题；按主题选择，不建议将本分类所有题目一次性发放。'))
        # Twelve short, unscored support surveys; the remaining categories are bank-only.
        scale_name='MINE-SUPPORT-'+slug.upper() if di < 12 else ''
        if scale_name:
            scales.append(dict(id=scale_name, name=scale_name, title=title+'（自编）',
                displayName=title+'（自编，非诊断）', category='职业心理与支持需求', count=18,
                version=VERSION, status='已启用', scaleType='custom', standard=False,
                source=SOURCES[source]['url'], license='本项目原创；来源仅提供主题依据',
                description='18题支持需求调查，包含行为、理解、条件、困难及求助意愿；非标准心理量表，未经信效度验证，不设置临床阈值。',
                scoringMethod='total', itemMax=0, riskMode='custom', riskBands=[], aiEnabled=False,
                scenario='职业心理与健康服务需求', reviewStatus='内容草稿，使用前核对适用性'))
        for ti,(topic,action) in enumerate(topics):
            suffix='；如不适用或不愿回答，可选相应选项。'
            common=dict(categoryId=cid, scale='', standard=False, source=SOURCES[source]['url'],
                version=VERSION, provenance='参考公开主题后原创编写', clinicalUse=False,
                description=f'主题：{category} / {topic}。仅作服务需求调查，所有选项不计分，不推断疾病风险。'+suffix,
                tags=[category,topic], reviewStatus='待本单位专业人员校对')
            entries=[
              (f'过去4周，在适用的情况下，您是否能够{action}？', ['一直能够','大多数时候能够','有时能够','尚不能够','不适用','不愿回答']),
              (f'关于“{topic}”，您对相关健康信息的理解程度如何？', ['能够清楚理解','大致理解','仅了解少量','尚不了解','不适用','不愿回答']),
              (f'当需要{action}时，您对自己完成这件事的把握如何？', ['很有把握','比较有把握','不太有把握','没有把握','不适用','不愿回答']),
              (f'过去12个月，您是否接受过“{topic}”相关的说明或指导？', ['接受过且能够理解','接受过但仍有疑问','未接受过','记不清','不适用','不愿回答']),
              (f'在“{topic}”方面，您目前具备哪些可用条件？（可多选）', ['有清晰的说明','有合适的时间安排','有所需用品或设施','能联系到服务人员','目前条件不足','不适用','不愿回答']),
              (f'哪些因素会妨碍您{action}？（可多选）', ['不清楚具体做法','时间安排困难','所需资源不足','不知道向谁求助','担心个人信息泄露','目前没有困难','不适用','不愿回答']),
              (f'如果对“{topic}”有疑问，您是否知道向谁咨询？', ['知道且方便联系','知道但联系不方便','暂不知道','不适用','不愿回答']),
              (f'就“{topic}”而言，您对目前得到的支持是否满意？', ['满意','基本满意','一般','不满意','尚未获得相关服务','不适用','不愿回答']),
              (f'未来一个月，您是否希望获得“{topic}”方面的帮助？', ['希望尽快获得帮助','希望了解后再决定','暂不需要','不确定','不适用','不愿回答']),
              (f'关于“{topic}”，哪些支持形式更适合您？（可多选）', ['图文说明','短视频演示','现场讲解','一对一沟通','班组讨论','不需要额外支持','不适用','不愿回答']),
              (f'对于您提出的“{topic}”问题，是否同意健康服务人员后续联系您？', ['同意通过系统留言联系','同意先通过系统预约再联系','暂不同意联系','不适用','不愿回答']),
              (f'关于“{topic}”，您还有哪些具体困难或改进建议？请勿填写姓名、身份证或其他人员的隐私。', []),
            ]
            for fi,(text,labels) in enumerate(entries):
                q=question(f'KB27-{slug}-{ti+1:02}-{fi+1:02}',text,opts(labels),**copy.deepcopy(common))
                q['type']='multiple' if fi in (4,5,9) else 'text' if fi==11 else 'single'
                q['order']=(di*120+ti*12+fi+1)*10
                # Three subjects x six complementary facets = an 18-item short survey.
                if scale_name and ti in (0,4,8) and fi in (0,1,5,6,8,11): q['scale']=scale_name
                questions.append(q)
    return dict(questions=questions, scales=scales, questionCategories=categories,
                scaleCategories=['职业心理与支持需求'])

def standards(base):
    scales, questions = [], []
    phq=[q for q in base['questions'] if q.get('scale')=='PHQ-9']
    gad=[q for q in base['questions'] if q.get('scale')=='GAD-7']
    for name,title,items in [('PHQ-2','抑郁症状两题筛查',phq[:2]),('GAD-2','焦虑症状两题筛查',gad[:2]),
                             ('PHQ-8','抑郁症状八题筛查',phq[:8]),('PHQ-4','情绪困扰四题筛查',gad[:2]+phq[:2])]:
        bands=[]
        if name in ('PHQ-2','GAD-2'):
            bands=[dict(min=0,max=2,note='未达到建议扩展筛查的分数；不排除其他问题',level='unknown'),
                   dict(min=3,max=6,note='建议进一步完成完整量表并由专业人员评估；不是诊断',level='unknown')]
        elif name=='PHQ-8':
            bands=[dict(min=a,max=b,note=n+'；仅反映症状程度，非诊断',level='unknown') for a,b,n in [(0,4,'无或极少症状'),(5,9,'轻度症状'),(10,14,'中度症状'),(15,19,'较重症状'),(20,24,'重度症状')]]
        s=dict(id=name,name=name,title=title,displayName=name+' '+title,category='情绪/心理健康',count=len(items),
            version='zh-reference-'+VERSION,status='已启用',scaleType='standard',standard=True,
            source=SOURCES['phq']['url'],license='Public domain; author instruction manual pp. 3, 8.',
            scoringMethod='total',itemMax=3,riskBands=bands,riskMode='standard',aiEnabled=False,
            requiresProfessionalReview=True,scenario='心理健康初筛',
            description='作者开放使用；中文沿用本项目现有PHQ/GAD参考题。保留过去两周时间窗，不代替专业诊断。不建议与同源长版在一次问卷内重复发放。')
        if name=='PHQ-4':
            s['subscales']=[dict(name='PHQ-4-焦虑分量表',itemOrders=[1,2],multiplier=1),dict(name='PHQ-4-抑郁分量表',itemOrders=[3,4],multiplier=1)]
            s['description']+=' 分别呈现两题焦虑和两题抑郁分量表，不将两者合计成疾病风险分。'
        scales.append(s)
        for i,old in enumerate(items,1):
            q=copy.deepcopy(old);q.update(id=f'{name}-{i:02}',code=f'{name}-{i:02}',scale=name,order=i*10,version=s['version'],source=s['source'])
            questions.append(q)

    # Exact simplified-Chinese wording from the public-domain Zuo/Chang form.
    texts=[
      '我发现我自己被一些琐碎的事情弄得很不开心','我感到嘴巴很干','我似乎完全不能积极乐观起来',
      '我感到过呼吸困难（例如： 在没有体力透支的情况下而感到呼吸急促， 喘不过气来）',
      '我似乎没法提起劲来做事情','我对于所处的环境（情况）易于反应过度','我曾有发抖的感觉（例如：腿都站不稳）',
      '我发现很难放松下来','我发现我曾处于非常焦虑的情况下，极想立刻离开这种环境松一口气',
      '我感到我没什么可期待的','我发现我极其容易不开心','我感到时常神经紧张','我感到伤心和郁闷',
      '我发现当我因为某种原因而耽误时间的时候，我变得没有耐性（例如：等电梯，在十字路口等红绿灯或其他处于等待的状态）',
      '我曾有眩晕的感觉','我一度感到我对几乎任何事情都失去了兴趣','我感到自己曾不具备作为人而存在的价值',
      '我感到我曾极容易因为小事而生气','在不是高温或体力透支的情况下，我明显容易出汗（例如：汗手）',
      '没有什么特殊原因的情况下，我感到害怕','我感到生命没有价值','我发现很难让自己安静下来休息','我曾有过吞咽困难的感觉',
      '我似乎没法从我做过的事情中找到乐趣','在没有体力透支的情况下我也能感觉到自己的心跳或心律不正常（例如：感到心跳过快或心律不齐）',
      '我感到消沉和沮丧','我发现我容易烦躁','我感到我曾接近恐慌','我发现当某件事情使我不开心之后，我很难平静下来',
      '我担心我会因为某些琐碎和不熟悉的工作而感到筋疲力竭','我对任何事情都没法充满热情','我发现我很难忍受我做事的时候受到任何干扰',
      '我曾处于神经紧张的状态','我一度感到我很没价值','我曾对阻碍我正在进行的工作的事情感到无法容忍','我曾感到恐惧',
      '对于未来我看不到任何希望','我曾感到生活没有意义','我发现自己变得焦虑不安','我担心自己可能因为惊慌而干蠢事出洋相',
      '我曾感到发抖（例如：手打哆嗦）','我发现很难发挥主动性去做事情']
    short_orders=[22,2,3,4,42,6,41,12,40,10,27,8,13,35,28,31,17,18,25,20,38]
    long_groups={'抑郁':[3,5,10,13,16,17,21,24,26,31,34,37,38,42],
                 '焦虑':[2,4,7,9,15,19,20,23,25,28,30,36,40,41],
                 '压力':[1,6,8,11,12,14,18,22,27,29,32,33,35,39]}
    short_groups={'抑郁':[3,5,10,13,16,17,21],'焦虑':[2,4,7,9,15,19,20],'压力':[1,6,8,11,12,14,18]}
    choices=opts(['此项根本不符合我的情况','此项在某种程度上或是某些时候与我的实际情况相符',
                  '此项在很大程度上或是大部分情况下与我的实际情况相符','此项与我的实际情况非常相符'],[0,1,2,3])
    for size,orders,groups in [(42,list(range(1,43)),long_groups),(21,short_orders,short_groups)]:
        name=f'DASS-{size}'
        s=dict(id=name,name=name,title=f'抑郁焦虑压力量表{size}题版',displayName=f'{name} 抑郁焦虑压力量表',
          category='压力与应激',count=size,version='Zuo-Chang-zh-'+VERSION,status='已启用',standard=True,scaleType='standard',
          source=SOURCES['dass_zh']['url']+' ; '+SOURCES['dass']['url'],license=SOURCES['dass']['use'],
          scoringMethod='subscales',riskMode='standard',riskBands=[],itemMax=3,requiresProfessionalReview=True,
          employeeScoreHidden=True,aiEnabled=False,scenario='压力与情绪专业评估',
          subscales=[dict(name=name+'-'+label+'分量表',itemOrders=indices,multiplier=2 if size==21 else 1) for label,indices in groups.items()],
          description='过去一周；保留官方简体中文原题，抑郁、焦虑、压力分开计分。不设置疾病阈值；不自动向职工解释分数，由具备资质人员评估。'+
            ('21题中文参考版按英文短版对应原题抽取，正式使用前核对适用性。' if size==21 else 'Zuo与Chang官方站点公开的42题译本。'))
        scales.append(s)
        for i,oi in enumerate(orders,1):
            questions.append(question(f'{name}-{i:02}',texts[oi-1],copy.deepcopy(choices),scale=name,
               standard=True,source=SOURCES['dass_zh']['url'],version=s['version'],required=True,order=i*10,
               originalItemNumber=oi,description='请阅读以下各项，然后根据过去一周之内符合您的实际情况，在相应的数字（0，1，2或3）上打勾。您的回答没有对错之分，所以请不要在以下任何一项上花太多时间。'))
    return scales,questions

def build():
    path=DEST/'questionnaire-catalog.json'
    base=json.loads(path.read_text(encoding='utf-8'))
    # Rebuildable: additions have stable IDs and preserve the original six entries.
    base['scales']=[s for s in base['scales'] if s['id'] not in {'PHQ-2','GAD-2','PHQ-8','PHQ-4','DASS-21','DASS-42'}]
    base['questions']=[q for q in base['questions'] if q.get('scale') not in {'PHQ-2','GAD-2','PHQ-8','PHQ-4','DASS-21','DASS-42'}]
    scales,questions=standards(base)
    base['scales']+=scales;base['questions']+=questions
    path.write_text(json.dumps(base,ensure_ascii=False,indent=2)+'\n',encoding='utf-8')
    pack=original_bank()
    scoring_patch=apply_management_rules(pack)
    (DEST/'questionnaire-management-scoring.json').write_text(json.dumps(scoring_patch,ensure_ascii=False,indent=2)+'\n',encoding='utf-8')
    pack['scales']=scales+pack['scales'];pack['questions']=questions+pack['questions']
    pack['sources']=SOURCES;pack['version']=VERSION
    (DEST/'questionnaire-supplement.json').write_text(json.dumps(pack,ensure_ascii=False,indent=2)+'\n',encoding='utf-8')
    summary=dict(version=VERSION,newQuestions=len(pack['questions']),originalQuestions=2160,
      newStandardItems=len(questions),newStandardScales=len(scales),newCustomSurveys=12,
      categories=len(DOMAINS),expectedQuestions=len(base['questions'])+2160,expectedScales=len(base['scales'])+12,
      sources=SOURCES,questionTextSha256=hashlib.sha256('\n'.join(q['text'] for q in pack['questions']).encode()).hexdigest())
    summary['managementScoringVersion']=scoring_patch['version']
    summary['scoredOriginalQuestions']=sum(q.get('scoringRule',{}).get('aggregation') not in (None,'none') for q in pack['questions'])
    (ROOT/'docs/questionnaire-supplement-manifest.json').write_text(json.dumps(summary,ensure_ascii=False,indent=2)+'\n',encoding='utf-8')
    assert len(pack['questions'])==2239
    assert len({q['id'] for q in pack['questions']})==2239
    assert len({q['text'] for q in pack['questions'] if not q.get('standard')})==2160
    counts=Counter(q['scale'] for q in pack['questions'])
    assert all(counts[s['name']]==s['count'] for s in pack['scales'])
    print(json.dumps({k:v for k,v in summary.items() if k!='sources'},ensure_ascii=False))

if __name__=='__main__':build()
