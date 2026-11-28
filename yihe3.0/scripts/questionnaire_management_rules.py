"""Project-defined management indices, not validated clinical cutoffs."""
import copy

VERSION = 'management-v1-20261127'
DIMENSIONS = [
 ('behavior','行为落实',True,[3,2,1,0,None,None]),
 ('understanding','信息理解',True,[3,2,1,0,None,None]),
 ('confidence','行动把握',True,[3,2,1,0,None,None]),
 ('guidance','指导可及性',True,[3,1,0,None,None,None]),
 ('resources','资源条件',True,[1,1,1,1,0,None,None]),
 ('barriers','现实困难',False,[1,1,1,1,1,0,None,None]),
 ('access','咨询可及性',True,[3,1,0,None,None]),
 ('satisfaction','支持满意度',True,[3,2,1,0,None,None,None]),
 ('demand','帮助需求',False,[3,1,0,None,None,None]),
 ('unscored','偏好与自主表达',False,None),
 ('unscored','偏好与自主表达',False,None),
 ('unscored','偏好与自主表达',False,None),
]

def apply(pack):
    changes={'version':VERSION,'questions':[],'scales':[]}
    for q in pack['questions']:
        if not q['id'].startswith('KB27-'): continue
        before=copy.deepcopy(q)
        facet=int(q['id'].rsplit('-',1)[1])-1
        dimension,label,positive,scores=DIMENSIONS[facet]
        aggregation='none' if scores is None else 'sum' if facet in (4,5) else 'single'
        maximum=4 if facet==4 else 5 if facet==5 else 3 if scores else 0
        exclusive={4,5,6} if facet==4 else {5,6,7} if facet==5 else {5,6,7} if facet==9 else set()
        for i,o in enumerate(q['optionItems']):
            excluded=scores is None or scores[i] is None
            o.update(score=0 if excluded else scores[i],excluded=excluded,exclusive=i in exclusive)
        direction='分数越高表示落实或支持越充分' if positive else '分数越高表示困难或帮助需求越多'
        note=('本题仅统计偏好、联系意愿或文字建议，不计分，不根据拒绝联系评价个人。' if scores is None else
              f'本题0–{maximum}分，{direction}。不适用、不愿回答及无法判断的选项不计分且排除分母；多选题互斥选项不可混选。')
        q['scoringRule']=dict(mode='management',version=VERSION,dimension=dimension,dimensionLabel=label,
            higherIsBetter=positive,aggregation=aggregation,maxScore=maximum,note=note)
        q['version']=VERSION
        q['description']=before['description'].split('。仅作服务需求调查')[0]+'。'+note+' 项目自定义管理评分，未经信效度验证，不用于判断疾病或医学风险。'
        changes['questions'].append(dict(id=q['id'],before=before,after=copy.deepcopy(q)))
    for s in pack['scales']:
        if not s['id'].startswith('MINE-SUPPORT-'): continue
        before=copy.deepcopy(s)
        s.update(version=VERSION,scoringMethod='management',itemMax=5,riskBands=[],aiEnabled=False,
            scoringPolicy=dict(version=VERSION,normalization='有效题得分之和 / 有效题满分之和 × 100',minimumCoverage=0.5,
                bandsNote='自定义管理参考：0–25、>25–50、>50–75、>75–100；非临床阈值',
                noCombinedScore=True),
            description='18题自编支持需求调查。行为落实、信息理解、现实困难、咨询可及性、帮助需求分维度计分并换算为0–100；文字建议不计分。不同方向不合并总分；有效计分题不足一半仅显示分数和覆盖率，不分档。不适用、不愿回答排除分母。管理参考，未经信效度验证，不设置临床风险阈值。')
        changes['scales'].append(dict(id=s['id'],before=before,after=copy.deepcopy(s)))
    return changes
