package com.yihe.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class YhDicAssessmentScreenExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public YhDicAssessmentScreenExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria implements Serializable {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSidIsNull() {
            addCriterion("sid is null");
            return (Criteria) this;
        }

        public Criteria andSidIsNotNull() {
            addCriterion("sid is not null");
            return (Criteria) this;
        }

        public Criteria andSidEqualTo(Integer value) {
            addCriterion("sid =", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotEqualTo(Integer value) {
            addCriterion("sid <>", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThan(Integer value) {
            addCriterion("sid >", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThanOrEqualTo(Integer value) {
            addCriterion("sid >=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThan(Integer value) {
            addCriterion("sid <", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThanOrEqualTo(Integer value) {
            addCriterion("sid <=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidIn(List<Integer> values) {
            addCriterion("sid in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotIn(List<Integer> values) {
            addCriterion("sid not in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidBetween(Integer value1, Integer value2) {
            addCriterion("sid between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotBetween(Integer value1, Integer value2) {
            addCriterion("sid not between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andExplainIsNull() {
            addCriterion("explain is null");
            return (Criteria) this;
        }

        public Criteria andExplainIsNotNull() {
            addCriterion("explain is not null");
            return (Criteria) this;
        }

        public Criteria andExplainEqualTo(String value) {
            addCriterion("explain =", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotEqualTo(String value) {
            addCriterion("explain <>", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainGreaterThan(String value) {
            addCriterion("explain >", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainGreaterThanOrEqualTo(String value) {
            addCriterion("explain >=", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainLessThan(String value) {
            addCriterion("explain <", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainLessThanOrEqualTo(String value) {
            addCriterion("explain <=", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainLike(String value) {
            addCriterion("explain like", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotLike(String value) {
            addCriterion("explain not like", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainIn(List<String> values) {
            addCriterion("explain in", values, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotIn(List<String> values) {
            addCriterion("explain not in", values, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainBetween(String value1, String value2) {
            addCriterion("explain between", value1, value2, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotBetween(String value1, String value2) {
            addCriterion("explain not between", value1, value2, "explain");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Integer value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Integer value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Integer value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Integer value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Integer value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Integer value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Integer> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Integer> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Integer value1, Integer value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Integer value1, Integer value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andAgestartIsNull() {
            addCriterion("ageStart is null");
            return (Criteria) this;
        }

        public Criteria andAgestartIsNotNull() {
            addCriterion("ageStart is not null");
            return (Criteria) this;
        }

        public Criteria andAgestartEqualTo(Integer value) {
            addCriterion("ageStart =", value, "agestart");
            return (Criteria) this;
        }

        public Criteria andAgestartNotEqualTo(Integer value) {
            addCriterion("ageStart <>", value, "agestart");
            return (Criteria) this;
        }

        public Criteria andAgestartGreaterThan(Integer value) {
            addCriterion("ageStart >", value, "agestart");
            return (Criteria) this;
        }

        public Criteria andAgestartGreaterThanOrEqualTo(Integer value) {
            addCriterion("ageStart >=", value, "agestart");
            return (Criteria) this;
        }

        public Criteria andAgestartLessThan(Integer value) {
            addCriterion("ageStart <", value, "agestart");
            return (Criteria) this;
        }

        public Criteria andAgestartLessThanOrEqualTo(Integer value) {
            addCriterion("ageStart <=", value, "agestart");
            return (Criteria) this;
        }

        public Criteria andAgestartIn(List<Integer> values) {
            addCriterion("ageStart in", values, "agestart");
            return (Criteria) this;
        }

        public Criteria andAgestartNotIn(List<Integer> values) {
            addCriterion("ageStart not in", values, "agestart");
            return (Criteria) this;
        }

        public Criteria andAgestartBetween(Integer value1, Integer value2) {
            addCriterion("ageStart between", value1, value2, "agestart");
            return (Criteria) this;
        }

        public Criteria andAgestartNotBetween(Integer value1, Integer value2) {
            addCriterion("ageStart not between", value1, value2, "agestart");
            return (Criteria) this;
        }

        public Criteria andAgeendIsNull() {
            addCriterion("ageEnd is null");
            return (Criteria) this;
        }

        public Criteria andAgeendIsNotNull() {
            addCriterion("ageEnd is not null");
            return (Criteria) this;
        }

        public Criteria andAgeendEqualTo(Integer value) {
            addCriterion("ageEnd =", value, "ageend");
            return (Criteria) this;
        }

        public Criteria andAgeendNotEqualTo(Integer value) {
            addCriterion("ageEnd <>", value, "ageend");
            return (Criteria) this;
        }

        public Criteria andAgeendGreaterThan(Integer value) {
            addCriterion("ageEnd >", value, "ageend");
            return (Criteria) this;
        }

        public Criteria andAgeendGreaterThanOrEqualTo(Integer value) {
            addCriterion("ageEnd >=", value, "ageend");
            return (Criteria) this;
        }

        public Criteria andAgeendLessThan(Integer value) {
            addCriterion("ageEnd <", value, "ageend");
            return (Criteria) this;
        }

        public Criteria andAgeendLessThanOrEqualTo(Integer value) {
            addCriterion("ageEnd <=", value, "ageend");
            return (Criteria) this;
        }

        public Criteria andAgeendIn(List<Integer> values) {
            addCriterion("ageEnd in", values, "ageend");
            return (Criteria) this;
        }

        public Criteria andAgeendNotIn(List<Integer> values) {
            addCriterion("ageEnd not in", values, "ageend");
            return (Criteria) this;
        }

        public Criteria andAgeendBetween(Integer value1, Integer value2) {
            addCriterion("ageEnd between", value1, value2, "ageend");
            return (Criteria) this;
        }

        public Criteria andAgeendNotBetween(Integer value1, Integer value2) {
            addCriterion("ageEnd not between", value1, value2, "ageend");
            return (Criteria) this;
        }

        public Criteria andWeight1IsNull() {
            addCriterion("weight1 is null");
            return (Criteria) this;
        }

        public Criteria andWeight1IsNotNull() {
            addCriterion("weight1 is not null");
            return (Criteria) this;
        }

        public Criteria andWeight1EqualTo(String value) {
            addCriterion("weight1 =", value, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight1NotEqualTo(String value) {
            addCriterion("weight1 <>", value, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight1GreaterThan(String value) {
            addCriterion("weight1 >", value, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight1GreaterThanOrEqualTo(String value) {
            addCriterion("weight1 >=", value, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight1LessThan(String value) {
            addCriterion("weight1 <", value, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight1LessThanOrEqualTo(String value) {
            addCriterion("weight1 <=", value, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight1Like(String value) {
            addCriterion("weight1 like", value, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight1NotLike(String value) {
            addCriterion("weight1 not like", value, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight1In(List<String> values) {
            addCriterion("weight1 in", values, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight1NotIn(List<String> values) {
            addCriterion("weight1 not in", values, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight1Between(String value1, String value2) {
            addCriterion("weight1 between", value1, value2, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight1NotBetween(String value1, String value2) {
            addCriterion("weight1 not between", value1, value2, "weight1");
            return (Criteria) this;
        }

        public Criteria andWeight2IsNull() {
            addCriterion("weight2 is null");
            return (Criteria) this;
        }

        public Criteria andWeight2IsNotNull() {
            addCriterion("weight2 is not null");
            return (Criteria) this;
        }

        public Criteria andWeight2EqualTo(String value) {
            addCriterion("weight2 =", value, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight2NotEqualTo(String value) {
            addCriterion("weight2 <>", value, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight2GreaterThan(String value) {
            addCriterion("weight2 >", value, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight2GreaterThanOrEqualTo(String value) {
            addCriterion("weight2 >=", value, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight2LessThan(String value) {
            addCriterion("weight2 <", value, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight2LessThanOrEqualTo(String value) {
            addCriterion("weight2 <=", value, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight2Like(String value) {
            addCriterion("weight2 like", value, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight2NotLike(String value) {
            addCriterion("weight2 not like", value, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight2In(List<String> values) {
            addCriterion("weight2 in", values, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight2NotIn(List<String> values) {
            addCriterion("weight2 not in", values, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight2Between(String value1, String value2) {
            addCriterion("weight2 between", value1, value2, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight2NotBetween(String value1, String value2) {
            addCriterion("weight2 not between", value1, value2, "weight2");
            return (Criteria) this;
        }

        public Criteria andWeight3IsNull() {
            addCriterion("weight3 is null");
            return (Criteria) this;
        }

        public Criteria andWeight3IsNotNull() {
            addCriterion("weight3 is not null");
            return (Criteria) this;
        }

        public Criteria andWeight3EqualTo(String value) {
            addCriterion("weight3 =", value, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight3NotEqualTo(String value) {
            addCriterion("weight3 <>", value, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight3GreaterThan(String value) {
            addCriterion("weight3 >", value, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight3GreaterThanOrEqualTo(String value) {
            addCriterion("weight3 >=", value, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight3LessThan(String value) {
            addCriterion("weight3 <", value, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight3LessThanOrEqualTo(String value) {
            addCriterion("weight3 <=", value, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight3Like(String value) {
            addCriterion("weight3 like", value, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight3NotLike(String value) {
            addCriterion("weight3 not like", value, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight3In(List<String> values) {
            addCriterion("weight3 in", values, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight3NotIn(List<String> values) {
            addCriterion("weight3 not in", values, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight3Between(String value1, String value2) {
            addCriterion("weight3 between", value1, value2, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight3NotBetween(String value1, String value2) {
            addCriterion("weight3 not between", value1, value2, "weight3");
            return (Criteria) this;
        }

        public Criteria andWeight4IsNull() {
            addCriterion("weight4 is null");
            return (Criteria) this;
        }

        public Criteria andWeight4IsNotNull() {
            addCriterion("weight4 is not null");
            return (Criteria) this;
        }

        public Criteria andWeight4EqualTo(String value) {
            addCriterion("weight4 =", value, "weight4");
            return (Criteria) this;
        }

        public Criteria andWeight4NotEqualTo(String value) {
            addCriterion("weight4 <>", value, "weight4");
            return (Criteria) this;
        }

        public Criteria andWeight4GreaterThan(String value) {
            addCriterion("weight4 >", value, "weight4");
            return (Criteria) this;
        }

        public Criteria andWeight4GreaterThanOrEqualTo(String value) {
            addCriterion("weight4 >=", value, "weight4");
            return (Criteria) this;
        }

        public Criteria andWeight4LessThan(String value) {
            addCriterion("weight4 <", value, "weight4");
            return (Criteria) this;
        }

        public Criteria andWeight4LessThanOrEqualTo(String value) {
            addCriterion("weight4 <=", value, "weight4");
            return (Criteria) this;
        }

        public Criteria andWeight4Like(String value) {
            addCriterion("weight4 like", value, "weight4");
            return (Criteria) this;
        }

        public Criteria andWeight4NotLike(String value) {
            addCriterion("weight4 not like", value, "weight4");
            return (Criteria) this;
        }

        public Criteria andWeight4In(List<String> values) {
            addCriterion("weight4 in", values, "weight4");
            return (Criteria) this;
        }

        public Criteria andWeight4NotIn(List<String> values) {
            addCriterion("weight4 not in", values, "weight4");
            return (Criteria) this;
        }

        public Criteria andWeight4Between(String value1, String value2) {
            addCriterion("weight4 between", value1, value2, "weight4");
            return (Criteria) this;
        }

        public Criteria andWeight4NotBetween(String value1, String value2) {
            addCriterion("weight4 not between", value1, value2, "weight4");
            return (Criteria) this;
        }

        public Criteria andHypertensionIsNull() {
            addCriterion("hypertension is null");
            return (Criteria) this;
        }

        public Criteria andHypertensionIsNotNull() {
            addCriterion("hypertension is not null");
            return (Criteria) this;
        }

        public Criteria andHypertensionEqualTo(String value) {
            addCriterion("hypertension =", value, "hypertension");
            return (Criteria) this;
        }

        public Criteria andHypertensionNotEqualTo(String value) {
            addCriterion("hypertension <>", value, "hypertension");
            return (Criteria) this;
        }

        public Criteria andHypertensionGreaterThan(String value) {
            addCriterion("hypertension >", value, "hypertension");
            return (Criteria) this;
        }

        public Criteria andHypertensionGreaterThanOrEqualTo(String value) {
            addCriterion("hypertension >=", value, "hypertension");
            return (Criteria) this;
        }

        public Criteria andHypertensionLessThan(String value) {
            addCriterion("hypertension <", value, "hypertension");
            return (Criteria) this;
        }

        public Criteria andHypertensionLessThanOrEqualTo(String value) {
            addCriterion("hypertension <=", value, "hypertension");
            return (Criteria) this;
        }

        public Criteria andHypertensionLike(String value) {
            addCriterion("hypertension like", value, "hypertension");
            return (Criteria) this;
        }

        public Criteria andHypertensionNotLike(String value) {
            addCriterion("hypertension not like", value, "hypertension");
            return (Criteria) this;
        }

        public Criteria andHypertensionIn(List<String> values) {
            addCriterion("hypertension in", values, "hypertension");
            return (Criteria) this;
        }

        public Criteria andHypertensionNotIn(List<String> values) {
            addCriterion("hypertension not in", values, "hypertension");
            return (Criteria) this;
        }

        public Criteria andHypertensionBetween(String value1, String value2) {
            addCriterion("hypertension between", value1, value2, "hypertension");
            return (Criteria) this;
        }

        public Criteria andHypertensionNotBetween(String value1, String value2) {
            addCriterion("hypertension not between", value1, value2, "hypertension");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisIsNull() {
            addCriterion("chronicBronchitis is null");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisIsNotNull() {
            addCriterion("chronicBronchitis is not null");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisEqualTo(String value) {
            addCriterion("chronicBronchitis =", value, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisNotEqualTo(String value) {
            addCriterion("chronicBronchitis <>", value, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisGreaterThan(String value) {
            addCriterion("chronicBronchitis >", value, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisGreaterThanOrEqualTo(String value) {
            addCriterion("chronicBronchitis >=", value, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisLessThan(String value) {
            addCriterion("chronicBronchitis <", value, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisLessThanOrEqualTo(String value) {
            addCriterion("chronicBronchitis <=", value, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisLike(String value) {
            addCriterion("chronicBronchitis like", value, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisNotLike(String value) {
            addCriterion("chronicBronchitis not like", value, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisIn(List<String> values) {
            addCriterion("chronicBronchitis in", values, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisNotIn(List<String> values) {
            addCriterion("chronicBronchitis not in", values, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisBetween(String value1, String value2) {
            addCriterion("chronicBronchitis between", value1, value2, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andChronicbronchitisNotBetween(String value1, String value2) {
            addCriterion("chronicBronchitis not between", value1, value2, "chronicbronchitis");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaIsNull() {
            addCriterion("dyslipidemia is null");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaIsNotNull() {
            addCriterion("dyslipidemia is not null");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaEqualTo(String value) {
            addCriterion("dyslipidemia =", value, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaNotEqualTo(String value) {
            addCriterion("dyslipidemia <>", value, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaGreaterThan(String value) {
            addCriterion("dyslipidemia >", value, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaGreaterThanOrEqualTo(String value) {
            addCriterion("dyslipidemia >=", value, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaLessThan(String value) {
            addCriterion("dyslipidemia <", value, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaLessThanOrEqualTo(String value) {
            addCriterion("dyslipidemia <=", value, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaLike(String value) {
            addCriterion("dyslipidemia like", value, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaNotLike(String value) {
            addCriterion("dyslipidemia not like", value, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaIn(List<String> values) {
            addCriterion("dyslipidemia in", values, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaNotIn(List<String> values) {
            addCriterion("dyslipidemia not in", values, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaBetween(String value1, String value2) {
            addCriterion("dyslipidemia between", value1, value2, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiaNotBetween(String value1, String value2) {
            addCriterion("dyslipidemia not between", value1, value2, "dyslipidemia");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisIsNull() {
            addCriterion("osteoporosis is null");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisIsNotNull() {
            addCriterion("osteoporosis is not null");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisEqualTo(String value) {
            addCriterion("osteoporosis =", value, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisNotEqualTo(String value) {
            addCriterion("osteoporosis <>", value, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisGreaterThan(String value) {
            addCriterion("osteoporosis >", value, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisGreaterThanOrEqualTo(String value) {
            addCriterion("osteoporosis >=", value, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisLessThan(String value) {
            addCriterion("osteoporosis <", value, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisLessThanOrEqualTo(String value) {
            addCriterion("osteoporosis <=", value, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisLike(String value) {
            addCriterion("osteoporosis like", value, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisNotLike(String value) {
            addCriterion("osteoporosis not like", value, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisIn(List<String> values) {
            addCriterion("osteoporosis in", values, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisNotIn(List<String> values) {
            addCriterion("osteoporosis not in", values, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisBetween(String value1, String value2) {
            addCriterion("osteoporosis between", value1, value2, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisNotBetween(String value1, String value2) {
            addCriterion("osteoporosis not between", value1, value2, "osteoporosis");
            return (Criteria) this;
        }

        public Criteria andLungcancerIsNull() {
            addCriterion("lungCancer is null");
            return (Criteria) this;
        }

        public Criteria andLungcancerIsNotNull() {
            addCriterion("lungCancer is not null");
            return (Criteria) this;
        }

        public Criteria andLungcancerEqualTo(String value) {
            addCriterion("lungCancer =", value, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andLungcancerNotEqualTo(String value) {
            addCriterion("lungCancer <>", value, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andLungcancerGreaterThan(String value) {
            addCriterion("lungCancer >", value, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andLungcancerGreaterThanOrEqualTo(String value) {
            addCriterion("lungCancer >=", value, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andLungcancerLessThan(String value) {
            addCriterion("lungCancer <", value, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andLungcancerLessThanOrEqualTo(String value) {
            addCriterion("lungCancer <=", value, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andLungcancerLike(String value) {
            addCriterion("lungCancer like", value, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andLungcancerNotLike(String value) {
            addCriterion("lungCancer not like", value, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andLungcancerIn(List<String> values) {
            addCriterion("lungCancer in", values, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andLungcancerNotIn(List<String> values) {
            addCriterion("lungCancer not in", values, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andLungcancerBetween(String value1, String value2) {
            addCriterion("lungCancer between", value1, value2, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andLungcancerNotBetween(String value1, String value2) {
            addCriterion("lungCancer not between", value1, value2, "lungcancer");
            return (Criteria) this;
        }

        public Criteria andEmphysemaIsNull() {
            addCriterion("emphysema is null");
            return (Criteria) this;
        }

        public Criteria andEmphysemaIsNotNull() {
            addCriterion("emphysema is not null");
            return (Criteria) this;
        }

        public Criteria andEmphysemaEqualTo(String value) {
            addCriterion("emphysema =", value, "emphysema");
            return (Criteria) this;
        }

        public Criteria andEmphysemaNotEqualTo(String value) {
            addCriterion("emphysema <>", value, "emphysema");
            return (Criteria) this;
        }

        public Criteria andEmphysemaGreaterThan(String value) {
            addCriterion("emphysema >", value, "emphysema");
            return (Criteria) this;
        }

        public Criteria andEmphysemaGreaterThanOrEqualTo(String value) {
            addCriterion("emphysema >=", value, "emphysema");
            return (Criteria) this;
        }

        public Criteria andEmphysemaLessThan(String value) {
            addCriterion("emphysema <", value, "emphysema");
            return (Criteria) this;
        }

        public Criteria andEmphysemaLessThanOrEqualTo(String value) {
            addCriterion("emphysema <=", value, "emphysema");
            return (Criteria) this;
        }

        public Criteria andEmphysemaLike(String value) {
            addCriterion("emphysema like", value, "emphysema");
            return (Criteria) this;
        }

        public Criteria andEmphysemaNotLike(String value) {
            addCriterion("emphysema not like", value, "emphysema");
            return (Criteria) this;
        }

        public Criteria andEmphysemaIn(List<String> values) {
            addCriterion("emphysema in", values, "emphysema");
            return (Criteria) this;
        }

        public Criteria andEmphysemaNotIn(List<String> values) {
            addCriterion("emphysema not in", values, "emphysema");
            return (Criteria) this;
        }

        public Criteria andEmphysemaBetween(String value1, String value2) {
            addCriterion("emphysema between", value1, value2, "emphysema");
            return (Criteria) this;
        }

        public Criteria andEmphysemaNotBetween(String value1, String value2) {
            addCriterion("emphysema not between", value1, value2, "emphysema");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseIsNull() {
            addCriterion("coronaryDisease is null");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseIsNotNull() {
            addCriterion("coronaryDisease is not null");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseEqualTo(String value) {
            addCriterion("coronaryDisease =", value, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseNotEqualTo(String value) {
            addCriterion("coronaryDisease <>", value, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseGreaterThan(String value) {
            addCriterion("coronaryDisease >", value, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseGreaterThanOrEqualTo(String value) {
            addCriterion("coronaryDisease >=", value, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseLessThan(String value) {
            addCriterion("coronaryDisease <", value, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseLessThanOrEqualTo(String value) {
            addCriterion("coronaryDisease <=", value, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseLike(String value) {
            addCriterion("coronaryDisease like", value, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseNotLike(String value) {
            addCriterion("coronaryDisease not like", value, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseIn(List<String> values) {
            addCriterion("coronaryDisease in", values, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseNotIn(List<String> values) {
            addCriterion("coronaryDisease not in", values, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseBetween(String value1, String value2) {
            addCriterion("coronaryDisease between", value1, value2, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaseNotBetween(String value1, String value2) {
            addCriterion("coronaryDisease not between", value1, value2, "coronarydisease");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaIsNull() {
            addCriterion("hyperuricemia is null");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaIsNotNull() {
            addCriterion("hyperuricemia is not null");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaEqualTo(String value) {
            addCriterion("hyperuricemia =", value, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaNotEqualTo(String value) {
            addCriterion("hyperuricemia <>", value, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaGreaterThan(String value) {
            addCriterion("hyperuricemia >", value, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaGreaterThanOrEqualTo(String value) {
            addCriterion("hyperuricemia >=", value, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaLessThan(String value) {
            addCriterion("hyperuricemia <", value, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaLessThanOrEqualTo(String value) {
            addCriterion("hyperuricemia <=", value, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaLike(String value) {
            addCriterion("hyperuricemia like", value, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaNotLike(String value) {
            addCriterion("hyperuricemia not like", value, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaIn(List<String> values) {
            addCriterion("hyperuricemia in", values, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaNotIn(List<String> values) {
            addCriterion("hyperuricemia not in", values, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaBetween(String value1, String value2) {
            addCriterion("hyperuricemia between", value1, value2, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andHyperuricemiaNotBetween(String value1, String value2) {
            addCriterion("hyperuricemia not between", value1, value2, "hyperuricemia");
            return (Criteria) this;
        }

        public Criteria andPhthisisIsNull() {
            addCriterion("phthisis is null");
            return (Criteria) this;
        }

        public Criteria andPhthisisIsNotNull() {
            addCriterion("phthisis is not null");
            return (Criteria) this;
        }

        public Criteria andPhthisisEqualTo(String value) {
            addCriterion("phthisis =", value, "phthisis");
            return (Criteria) this;
        }

        public Criteria andPhthisisNotEqualTo(String value) {
            addCriterion("phthisis <>", value, "phthisis");
            return (Criteria) this;
        }

        public Criteria andPhthisisGreaterThan(String value) {
            addCriterion("phthisis >", value, "phthisis");
            return (Criteria) this;
        }

        public Criteria andPhthisisGreaterThanOrEqualTo(String value) {
            addCriterion("phthisis >=", value, "phthisis");
            return (Criteria) this;
        }

        public Criteria andPhthisisLessThan(String value) {
            addCriterion("phthisis <", value, "phthisis");
            return (Criteria) this;
        }

        public Criteria andPhthisisLessThanOrEqualTo(String value) {
            addCriterion("phthisis <=", value, "phthisis");
            return (Criteria) this;
        }

        public Criteria andPhthisisLike(String value) {
            addCriterion("phthisis like", value, "phthisis");
            return (Criteria) this;
        }

        public Criteria andPhthisisNotLike(String value) {
            addCriterion("phthisis not like", value, "phthisis");
            return (Criteria) this;
        }

        public Criteria andPhthisisIn(List<String> values) {
            addCriterion("phthisis in", values, "phthisis");
            return (Criteria) this;
        }

        public Criteria andPhthisisNotIn(List<String> values) {
            addCriterion("phthisis not in", values, "phthisis");
            return (Criteria) this;
        }

        public Criteria andPhthisisBetween(String value1, String value2) {
            addCriterion("phthisis between", value1, value2, "phthisis");
            return (Criteria) this;
        }

        public Criteria andPhthisisNotBetween(String value1, String value2) {
            addCriterion("phthisis not between", value1, value2, "phthisis");
            return (Criteria) this;
        }

        public Criteria andFattyliverIsNull() {
            addCriterion("fattyLiver is null");
            return (Criteria) this;
        }

        public Criteria andFattyliverIsNotNull() {
            addCriterion("fattyLiver is not null");
            return (Criteria) this;
        }

        public Criteria andFattyliverEqualTo(String value) {
            addCriterion("fattyLiver =", value, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andFattyliverNotEqualTo(String value) {
            addCriterion("fattyLiver <>", value, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andFattyliverGreaterThan(String value) {
            addCriterion("fattyLiver >", value, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andFattyliverGreaterThanOrEqualTo(String value) {
            addCriterion("fattyLiver >=", value, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andFattyliverLessThan(String value) {
            addCriterion("fattyLiver <", value, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andFattyliverLessThanOrEqualTo(String value) {
            addCriterion("fattyLiver <=", value, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andFattyliverLike(String value) {
            addCriterion("fattyLiver like", value, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andFattyliverNotLike(String value) {
            addCriterion("fattyLiver not like", value, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andFattyliverIn(List<String> values) {
            addCriterion("fattyLiver in", values, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andFattyliverNotIn(List<String> values) {
            addCriterion("fattyLiver not in", values, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andFattyliverBetween(String value1, String value2) {
            addCriterion("fattyLiver between", value1, value2, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andFattyliverNotBetween(String value1, String value2) {
            addCriterion("fattyLiver not between", value1, value2, "fattyliver");
            return (Criteria) this;
        }

        public Criteria andType2diabetesIsNull() {
            addCriterion("type2Diabetes is null");
            return (Criteria) this;
        }

        public Criteria andType2diabetesIsNotNull() {
            addCriterion("type2Diabetes is not null");
            return (Criteria) this;
        }

        public Criteria andType2diabetesEqualTo(String value) {
            addCriterion("type2Diabetes =", value, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andType2diabetesNotEqualTo(String value) {
            addCriterion("type2Diabetes <>", value, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andType2diabetesGreaterThan(String value) {
            addCriterion("type2Diabetes >", value, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andType2diabetesGreaterThanOrEqualTo(String value) {
            addCriterion("type2Diabetes >=", value, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andType2diabetesLessThan(String value) {
            addCriterion("type2Diabetes <", value, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andType2diabetesLessThanOrEqualTo(String value) {
            addCriterion("type2Diabetes <=", value, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andType2diabetesLike(String value) {
            addCriterion("type2Diabetes like", value, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andType2diabetesNotLike(String value) {
            addCriterion("type2Diabetes not like", value, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andType2diabetesIn(List<String> values) {
            addCriterion("type2Diabetes in", values, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andType2diabetesNotIn(List<String> values) {
            addCriterion("type2Diabetes not in", values, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andType2diabetesBetween(String value1, String value2) {
            addCriterion("type2Diabetes between", value1, value2, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andType2diabetesNotBetween(String value1, String value2) {
            addCriterion("type2Diabetes not between", value1, value2, "type2diabetes");
            return (Criteria) this;
        }

        public Criteria andApoplexyIsNull() {
            addCriterion("apoplexy is null");
            return (Criteria) this;
        }

        public Criteria andApoplexyIsNotNull() {
            addCriterion("apoplexy is not null");
            return (Criteria) this;
        }

        public Criteria andApoplexyEqualTo(String value) {
            addCriterion("apoplexy =", value, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andApoplexyNotEqualTo(String value) {
            addCriterion("apoplexy <>", value, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andApoplexyGreaterThan(String value) {
            addCriterion("apoplexy >", value, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andApoplexyGreaterThanOrEqualTo(String value) {
            addCriterion("apoplexy >=", value, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andApoplexyLessThan(String value) {
            addCriterion("apoplexy <", value, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andApoplexyLessThanOrEqualTo(String value) {
            addCriterion("apoplexy <=", value, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andApoplexyLike(String value) {
            addCriterion("apoplexy like", value, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andApoplexyNotLike(String value) {
            addCriterion("apoplexy not like", value, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andApoplexyIn(List<String> values) {
            addCriterion("apoplexy in", values, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andApoplexyNotIn(List<String> values) {
            addCriterion("apoplexy not in", values, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andApoplexyBetween(String value1, String value2) {
            addCriterion("apoplexy between", value1, value2, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andApoplexyNotBetween(String value1, String value2) {
            addCriterion("apoplexy not between", value1, value2, "apoplexy");
            return (Criteria) this;
        }

        public Criteria andBreastcancerIsNull() {
            addCriterion("breastCancer is null");
            return (Criteria) this;
        }

        public Criteria andBreastcancerIsNotNull() {
            addCriterion("breastCancer is not null");
            return (Criteria) this;
        }

        public Criteria andBreastcancerEqualTo(String value) {
            addCriterion("breastCancer =", value, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andBreastcancerNotEqualTo(String value) {
            addCriterion("breastCancer <>", value, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andBreastcancerGreaterThan(String value) {
            addCriterion("breastCancer >", value, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andBreastcancerGreaterThanOrEqualTo(String value) {
            addCriterion("breastCancer >=", value, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andBreastcancerLessThan(String value) {
            addCriterion("breastCancer <", value, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andBreastcancerLessThanOrEqualTo(String value) {
            addCriterion("breastCancer <=", value, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andBreastcancerLike(String value) {
            addCriterion("breastCancer like", value, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andBreastcancerNotLike(String value) {
            addCriterion("breastCancer not like", value, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andBreastcancerIn(List<String> values) {
            addCriterion("breastCancer in", values, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andBreastcancerNotIn(List<String> values) {
            addCriterion("breastCancer not in", values, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andBreastcancerBetween(String value1, String value2) {
            addCriterion("breastCancer between", value1, value2, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andBreastcancerNotBetween(String value1, String value2) {
            addCriterion("breastCancer not between", value1, value2, "breastcancer");
            return (Criteria) this;
        }

        public Criteria andProstateIsNull() {
            addCriterion("prostate is null");
            return (Criteria) this;
        }

        public Criteria andProstateIsNotNull() {
            addCriterion("prostate is not null");
            return (Criteria) this;
        }

        public Criteria andProstateEqualTo(String value) {
            addCriterion("prostate =", value, "prostate");
            return (Criteria) this;
        }

        public Criteria andProstateNotEqualTo(String value) {
            addCriterion("prostate <>", value, "prostate");
            return (Criteria) this;
        }

        public Criteria andProstateGreaterThan(String value) {
            addCriterion("prostate >", value, "prostate");
            return (Criteria) this;
        }

        public Criteria andProstateGreaterThanOrEqualTo(String value) {
            addCriterion("prostate >=", value, "prostate");
            return (Criteria) this;
        }

        public Criteria andProstateLessThan(String value) {
            addCriterion("prostate <", value, "prostate");
            return (Criteria) this;
        }

        public Criteria andProstateLessThanOrEqualTo(String value) {
            addCriterion("prostate <=", value, "prostate");
            return (Criteria) this;
        }

        public Criteria andProstateLike(String value) {
            addCriterion("prostate like", value, "prostate");
            return (Criteria) this;
        }

        public Criteria andProstateNotLike(String value) {
            addCriterion("prostate not like", value, "prostate");
            return (Criteria) this;
        }

        public Criteria andProstateIn(List<String> values) {
            addCriterion("prostate in", values, "prostate");
            return (Criteria) this;
        }

        public Criteria andProstateNotIn(List<String> values) {
            addCriterion("prostate not in", values, "prostate");
            return (Criteria) this;
        }

        public Criteria andProstateBetween(String value1, String value2) {
            addCriterion("prostate between", value1, value2, "prostate");
            return (Criteria) this;
        }

        public Criteria andProstateNotBetween(String value1, String value2) {
            addCriterion("prostate not between", value1, value2, "prostate");
            return (Criteria) this;
        }

        public Criteria andAcutegoutIsNull() {
            addCriterion("acuteGout is null");
            return (Criteria) this;
        }

        public Criteria andAcutegoutIsNotNull() {
            addCriterion("acuteGout is not null");
            return (Criteria) this;
        }

        public Criteria andAcutegoutEqualTo(String value) {
            addCriterion("acuteGout =", value, "acutegout");
            return (Criteria) this;
        }

        public Criteria andAcutegoutNotEqualTo(String value) {
            addCriterion("acuteGout <>", value, "acutegout");
            return (Criteria) this;
        }

        public Criteria andAcutegoutGreaterThan(String value) {
            addCriterion("acuteGout >", value, "acutegout");
            return (Criteria) this;
        }

        public Criteria andAcutegoutGreaterThanOrEqualTo(String value) {
            addCriterion("acuteGout >=", value, "acutegout");
            return (Criteria) this;
        }

        public Criteria andAcutegoutLessThan(String value) {
            addCriterion("acuteGout <", value, "acutegout");
            return (Criteria) this;
        }

        public Criteria andAcutegoutLessThanOrEqualTo(String value) {
            addCriterion("acuteGout <=", value, "acutegout");
            return (Criteria) this;
        }

        public Criteria andAcutegoutLike(String value) {
            addCriterion("acuteGout like", value, "acutegout");
            return (Criteria) this;
        }

        public Criteria andAcutegoutNotLike(String value) {
            addCriterion("acuteGout not like", value, "acutegout");
            return (Criteria) this;
        }

        public Criteria andAcutegoutIn(List<String> values) {
            addCriterion("acuteGout in", values, "acutegout");
            return (Criteria) this;
        }

        public Criteria andAcutegoutNotIn(List<String> values) {
            addCriterion("acuteGout not in", values, "acutegout");
            return (Criteria) this;
        }

        public Criteria andAcutegoutBetween(String value1, String value2) {
            addCriterion("acuteGout between", value1, value2, "acutegout");
            return (Criteria) this;
        }

        public Criteria andAcutegoutNotBetween(String value1, String value2) {
            addCriterion("acuteGout not between", value1, value2, "acutegout");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteIsNull() {
            addCriterion("goutIsNotAcute is null");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteIsNotNull() {
            addCriterion("goutIsNotAcute is not null");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteEqualTo(String value) {
            addCriterion("goutIsNotAcute =", value, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteNotEqualTo(String value) {
            addCriterion("goutIsNotAcute <>", value, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteGreaterThan(String value) {
            addCriterion("goutIsNotAcute >", value, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteGreaterThanOrEqualTo(String value) {
            addCriterion("goutIsNotAcute >=", value, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteLessThan(String value) {
            addCriterion("goutIsNotAcute <", value, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteLessThanOrEqualTo(String value) {
            addCriterion("goutIsNotAcute <=", value, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteLike(String value) {
            addCriterion("goutIsNotAcute like", value, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteNotLike(String value) {
            addCriterion("goutIsNotAcute not like", value, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteIn(List<String> values) {
            addCriterion("goutIsNotAcute in", values, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteNotIn(List<String> values) {
            addCriterion("goutIsNotAcute not in", values, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteBetween(String value1, String value2) {
            addCriterion("goutIsNotAcute between", value1, value2, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andGoutisnotacuteNotBetween(String value1, String value2) {
            addCriterion("goutIsNotAcute not between", value1, value2, "goutisnotacute");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionIsNull() {
            addCriterion("familialHypertension is null");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionIsNotNull() {
            addCriterion("familialHypertension is not null");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionEqualTo(String value) {
            addCriterion("familialHypertension =", value, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionNotEqualTo(String value) {
            addCriterion("familialHypertension <>", value, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionGreaterThan(String value) {
            addCriterion("familialHypertension >", value, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionGreaterThanOrEqualTo(String value) {
            addCriterion("familialHypertension >=", value, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionLessThan(String value) {
            addCriterion("familialHypertension <", value, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionLessThanOrEqualTo(String value) {
            addCriterion("familialHypertension <=", value, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionLike(String value) {
            addCriterion("familialHypertension like", value, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionNotLike(String value) {
            addCriterion("familialHypertension not like", value, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionIn(List<String> values) {
            addCriterion("familialHypertension in", values, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionNotIn(List<String> values) {
            addCriterion("familialHypertension not in", values, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionBetween(String value1, String value2) {
            addCriterion("familialHypertension between", value1, value2, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamilialhypertensionNotBetween(String value1, String value2) {
            addCriterion("familialHypertension not between", value1, value2, "familialhypertension");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerIsNull() {
            addCriterion("familialLungCancer is null");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerIsNotNull() {
            addCriterion("familialLungCancer is not null");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerEqualTo(String value) {
            addCriterion("familialLungCancer =", value, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerNotEqualTo(String value) {
            addCriterion("familialLungCancer <>", value, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerGreaterThan(String value) {
            addCriterion("familialLungCancer >", value, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerGreaterThanOrEqualTo(String value) {
            addCriterion("familialLungCancer >=", value, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerLessThan(String value) {
            addCriterion("familialLungCancer <", value, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerLessThanOrEqualTo(String value) {
            addCriterion("familialLungCancer <=", value, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerLike(String value) {
            addCriterion("familialLungCancer like", value, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerNotLike(String value) {
            addCriterion("familialLungCancer not like", value, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerIn(List<String> values) {
            addCriterion("familialLungCancer in", values, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerNotIn(List<String> values) {
            addCriterion("familialLungCancer not in", values, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerBetween(String value1, String value2) {
            addCriterion("familialLungCancer between", value1, value2, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamiliallungcancerNotBetween(String value1, String value2) {
            addCriterion("familialLungCancer not between", value1, value2, "familiallungcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseIsNull() {
            addCriterion("familialCoronaryDisease is null");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseIsNotNull() {
            addCriterion("familialCoronaryDisease is not null");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseEqualTo(String value) {
            addCriterion("familialCoronaryDisease =", value, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseNotEqualTo(String value) {
            addCriterion("familialCoronaryDisease <>", value, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseGreaterThan(String value) {
            addCriterion("familialCoronaryDisease >", value, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseGreaterThanOrEqualTo(String value) {
            addCriterion("familialCoronaryDisease >=", value, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseLessThan(String value) {
            addCriterion("familialCoronaryDisease <", value, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseLessThanOrEqualTo(String value) {
            addCriterion("familialCoronaryDisease <=", value, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseLike(String value) {
            addCriterion("familialCoronaryDisease like", value, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseNotLike(String value) {
            addCriterion("familialCoronaryDisease not like", value, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseIn(List<String> values) {
            addCriterion("familialCoronaryDisease in", values, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseNotIn(List<String> values) {
            addCriterion("familialCoronaryDisease not in", values, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseBetween(String value1, String value2) {
            addCriterion("familialCoronaryDisease between", value1, value2, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialcoronarydiseaseNotBetween(String value1, String value2) {
            addCriterion("familialCoronaryDisease not between", value1, value2, "familialcoronarydisease");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyIsNull() {
            addCriterion("familialApoplexy is null");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyIsNotNull() {
            addCriterion("familialApoplexy is not null");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyEqualTo(String value) {
            addCriterion("familialApoplexy =", value, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyNotEqualTo(String value) {
            addCriterion("familialApoplexy <>", value, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyGreaterThan(String value) {
            addCriterion("familialApoplexy >", value, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyGreaterThanOrEqualTo(String value) {
            addCriterion("familialApoplexy >=", value, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyLessThan(String value) {
            addCriterion("familialApoplexy <", value, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyLessThanOrEqualTo(String value) {
            addCriterion("familialApoplexy <=", value, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyLike(String value) {
            addCriterion("familialApoplexy like", value, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyNotLike(String value) {
            addCriterion("familialApoplexy not like", value, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyIn(List<String> values) {
            addCriterion("familialApoplexy in", values, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyNotIn(List<String> values) {
            addCriterion("familialApoplexy not in", values, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyBetween(String value1, String value2) {
            addCriterion("familialApoplexy between", value1, value2, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialapoplexyNotBetween(String value1, String value2) {
            addCriterion("familialApoplexy not between", value1, value2, "familialapoplexy");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerIsNull() {
            addCriterion("familialBreastCancer is null");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerIsNotNull() {
            addCriterion("familialBreastCancer is not null");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerEqualTo(String value) {
            addCriterion("familialBreastCancer =", value, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerNotEqualTo(String value) {
            addCriterion("familialBreastCancer <>", value, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerGreaterThan(String value) {
            addCriterion("familialBreastCancer >", value, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerGreaterThanOrEqualTo(String value) {
            addCriterion("familialBreastCancer >=", value, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerLessThan(String value) {
            addCriterion("familialBreastCancer <", value, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerLessThanOrEqualTo(String value) {
            addCriterion("familialBreastCancer <=", value, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerLike(String value) {
            addCriterion("familialBreastCancer like", value, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerNotLike(String value) {
            addCriterion("familialBreastCancer not like", value, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerIn(List<String> values) {
            addCriterion("familialBreastCancer in", values, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerNotIn(List<String> values) {
            addCriterion("familialBreastCancer not in", values, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerBetween(String value1, String value2) {
            addCriterion("familialBreastCancer between", value1, value2, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialbreastcancerNotBetween(String value1, String value2) {
            addCriterion("familialBreastCancer not between", value1, value2, "familialbreastcancer");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateIsNull() {
            addCriterion("familialProstate is null");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateIsNotNull() {
            addCriterion("familialProstate is not null");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateEqualTo(String value) {
            addCriterion("familialProstate =", value, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateNotEqualTo(String value) {
            addCriterion("familialProstate <>", value, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateGreaterThan(String value) {
            addCriterion("familialProstate >", value, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateGreaterThanOrEqualTo(String value) {
            addCriterion("familialProstate >=", value, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateLessThan(String value) {
            addCriterion("familialProstate <", value, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateLessThanOrEqualTo(String value) {
            addCriterion("familialProstate <=", value, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateLike(String value) {
            addCriterion("familialProstate like", value, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateNotLike(String value) {
            addCriterion("familialProstate not like", value, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateIn(List<String> values) {
            addCriterion("familialProstate in", values, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateNotIn(List<String> values) {
            addCriterion("familialProstate not in", values, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateBetween(String value1, String value2) {
            addCriterion("familialProstate between", value1, value2, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialprostateNotBetween(String value1, String value2) {
            addCriterion("familialProstate not between", value1, value2, "familialprostate");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesIsNull() {
            addCriterion("familialDiabetes is null");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesIsNotNull() {
            addCriterion("familialDiabetes is not null");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesEqualTo(String value) {
            addCriterion("familialDiabetes =", value, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesNotEqualTo(String value) {
            addCriterion("familialDiabetes <>", value, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesGreaterThan(String value) {
            addCriterion("familialDiabetes >", value, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesGreaterThanOrEqualTo(String value) {
            addCriterion("familialDiabetes >=", value, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesLessThan(String value) {
            addCriterion("familialDiabetes <", value, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesLessThanOrEqualTo(String value) {
            addCriterion("familialDiabetes <=", value, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesLike(String value) {
            addCriterion("familialDiabetes like", value, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesNotLike(String value) {
            addCriterion("familialDiabetes not like", value, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesIn(List<String> values) {
            addCriterion("familialDiabetes in", values, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesNotIn(List<String> values) {
            addCriterion("familialDiabetes not in", values, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesBetween(String value1, String value2) {
            addCriterion("familialDiabetes between", value1, value2, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialdiabetesNotBetween(String value1, String value2) {
            addCriterion("familialDiabetes not between", value1, value2, "familialdiabetes");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureIsNull() {
            addCriterion("familialhipFracture is null");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureIsNotNull() {
            addCriterion("familialhipFracture is not null");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureEqualTo(String value) {
            addCriterion("familialhipFracture =", value, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureNotEqualTo(String value) {
            addCriterion("familialhipFracture <>", value, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureGreaterThan(String value) {
            addCriterion("familialhipFracture >", value, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureGreaterThanOrEqualTo(String value) {
            addCriterion("familialhipFracture >=", value, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureLessThan(String value) {
            addCriterion("familialhipFracture <", value, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureLessThanOrEqualTo(String value) {
            addCriterion("familialhipFracture <=", value, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureLike(String value) {
            addCriterion("familialhipFracture like", value, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureNotLike(String value) {
            addCriterion("familialhipFracture not like", value, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureIn(List<String> values) {
            addCriterion("familialhipFracture in", values, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureNotIn(List<String> values) {
            addCriterion("familialhipFracture not in", values, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureBetween(String value1, String value2) {
            addCriterion("familialhipFracture between", value1, value2, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andFamilialhipfractureNotBetween(String value1, String value2) {
            addCriterion("familialhipFracture not between", value1, value2, "familialhipfracture");
            return (Criteria) this;
        }

        public Criteria andSmoke1IsNull() {
            addCriterion("smoke1 is null");
            return (Criteria) this;
        }

        public Criteria andSmoke1IsNotNull() {
            addCriterion("smoke1 is not null");
            return (Criteria) this;
        }

        public Criteria andSmoke1EqualTo(String value) {
            addCriterion("smoke1 =", value, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke1NotEqualTo(String value) {
            addCriterion("smoke1 <>", value, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke1GreaterThan(String value) {
            addCriterion("smoke1 >", value, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke1GreaterThanOrEqualTo(String value) {
            addCriterion("smoke1 >=", value, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke1LessThan(String value) {
            addCriterion("smoke1 <", value, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke1LessThanOrEqualTo(String value) {
            addCriterion("smoke1 <=", value, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke1Like(String value) {
            addCriterion("smoke1 like", value, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke1NotLike(String value) {
            addCriterion("smoke1 not like", value, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke1In(List<String> values) {
            addCriterion("smoke1 in", values, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke1NotIn(List<String> values) {
            addCriterion("smoke1 not in", values, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke1Between(String value1, String value2) {
            addCriterion("smoke1 between", value1, value2, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke1NotBetween(String value1, String value2) {
            addCriterion("smoke1 not between", value1, value2, "smoke1");
            return (Criteria) this;
        }

        public Criteria andSmoke2IsNull() {
            addCriterion("smoke2 is null");
            return (Criteria) this;
        }

        public Criteria andSmoke2IsNotNull() {
            addCriterion("smoke2 is not null");
            return (Criteria) this;
        }

        public Criteria andSmoke2EqualTo(String value) {
            addCriterion("smoke2 =", value, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke2NotEqualTo(String value) {
            addCriterion("smoke2 <>", value, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke2GreaterThan(String value) {
            addCriterion("smoke2 >", value, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke2GreaterThanOrEqualTo(String value) {
            addCriterion("smoke2 >=", value, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke2LessThan(String value) {
            addCriterion("smoke2 <", value, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke2LessThanOrEqualTo(String value) {
            addCriterion("smoke2 <=", value, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke2Like(String value) {
            addCriterion("smoke2 like", value, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke2NotLike(String value) {
            addCriterion("smoke2 not like", value, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke2In(List<String> values) {
            addCriterion("smoke2 in", values, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke2NotIn(List<String> values) {
            addCriterion("smoke2 not in", values, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke2Between(String value1, String value2) {
            addCriterion("smoke2 between", value1, value2, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke2NotBetween(String value1, String value2) {
            addCriterion("smoke2 not between", value1, value2, "smoke2");
            return (Criteria) this;
        }

        public Criteria andSmoke3IsNull() {
            addCriterion("smoke3 is null");
            return (Criteria) this;
        }

        public Criteria andSmoke3IsNotNull() {
            addCriterion("smoke3 is not null");
            return (Criteria) this;
        }

        public Criteria andSmoke3EqualTo(String value) {
            addCriterion("smoke3 =", value, "smoke3");
            return (Criteria) this;
        }

        public Criteria andSmoke3NotEqualTo(String value) {
            addCriterion("smoke3 <>", value, "smoke3");
            return (Criteria) this;
        }

        public Criteria andSmoke3GreaterThan(String value) {
            addCriterion("smoke3 >", value, "smoke3");
            return (Criteria) this;
        }

        public Criteria andSmoke3GreaterThanOrEqualTo(String value) {
            addCriterion("smoke3 >=", value, "smoke3");
            return (Criteria) this;
        }

        public Criteria andSmoke3LessThan(String value) {
            addCriterion("smoke3 <", value, "smoke3");
            return (Criteria) this;
        }

        public Criteria andSmoke3LessThanOrEqualTo(String value) {
            addCriterion("smoke3 <=", value, "smoke3");
            return (Criteria) this;
        }

        public Criteria andSmoke3Like(String value) {
            addCriterion("smoke3 like", value, "smoke3");
            return (Criteria) this;
        }

        public Criteria andSmoke3NotLike(String value) {
            addCriterion("smoke3 not like", value, "smoke3");
            return (Criteria) this;
        }

        public Criteria andSmoke3In(List<String> values) {
            addCriterion("smoke3 in", values, "smoke3");
            return (Criteria) this;
        }

        public Criteria andSmoke3NotIn(List<String> values) {
            addCriterion("smoke3 not in", values, "smoke3");
            return (Criteria) this;
        }

        public Criteria andSmoke3Between(String value1, String value2) {
            addCriterion("smoke3 between", value1, value2, "smoke3");
            return (Criteria) this;
        }

        public Criteria andSmoke3NotBetween(String value1, String value2) {
            addCriterion("smoke3 not between", value1, value2, "smoke3");
            return (Criteria) this;
        }

        public Criteria andDrink1IsNull() {
            addCriterion("drink1 is null");
            return (Criteria) this;
        }

        public Criteria andDrink1IsNotNull() {
            addCriterion("drink1 is not null");
            return (Criteria) this;
        }

        public Criteria andDrink1EqualTo(String value) {
            addCriterion("drink1 =", value, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink1NotEqualTo(String value) {
            addCriterion("drink1 <>", value, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink1GreaterThan(String value) {
            addCriterion("drink1 >", value, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink1GreaterThanOrEqualTo(String value) {
            addCriterion("drink1 >=", value, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink1LessThan(String value) {
            addCriterion("drink1 <", value, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink1LessThanOrEqualTo(String value) {
            addCriterion("drink1 <=", value, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink1Like(String value) {
            addCriterion("drink1 like", value, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink1NotLike(String value) {
            addCriterion("drink1 not like", value, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink1In(List<String> values) {
            addCriterion("drink1 in", values, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink1NotIn(List<String> values) {
            addCriterion("drink1 not in", values, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink1Between(String value1, String value2) {
            addCriterion("drink1 between", value1, value2, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink1NotBetween(String value1, String value2) {
            addCriterion("drink1 not between", value1, value2, "drink1");
            return (Criteria) this;
        }

        public Criteria andDrink2IsNull() {
            addCriterion("drink2 is null");
            return (Criteria) this;
        }

        public Criteria andDrink2IsNotNull() {
            addCriterion("drink2 is not null");
            return (Criteria) this;
        }

        public Criteria andDrink2EqualTo(String value) {
            addCriterion("drink2 =", value, "drink2");
            return (Criteria) this;
        }

        public Criteria andDrink2NotEqualTo(String value) {
            addCriterion("drink2 <>", value, "drink2");
            return (Criteria) this;
        }

        public Criteria andDrink2GreaterThan(String value) {
            addCriterion("drink2 >", value, "drink2");
            return (Criteria) this;
        }

        public Criteria andDrink2GreaterThanOrEqualTo(String value) {
            addCriterion("drink2 >=", value, "drink2");
            return (Criteria) this;
        }

        public Criteria andDrink2LessThan(String value) {
            addCriterion("drink2 <", value, "drink2");
            return (Criteria) this;
        }

        public Criteria andDrink2LessThanOrEqualTo(String value) {
            addCriterion("drink2 <=", value, "drink2");
            return (Criteria) this;
        }

        public Criteria andDrink2Like(String value) {
            addCriterion("drink2 like", value, "drink2");
            return (Criteria) this;
        }

        public Criteria andDrink2NotLike(String value) {
            addCriterion("drink2 not like", value, "drink2");
            return (Criteria) this;
        }

        public Criteria andDrink2In(List<String> values) {
            addCriterion("drink2 in", values, "drink2");
            return (Criteria) this;
        }

        public Criteria andDrink2NotIn(List<String> values) {
            addCriterion("drink2 not in", values, "drink2");
            return (Criteria) this;
        }

        public Criteria andDrink2Between(String value1, String value2) {
            addCriterion("drink2 between", value1, value2, "drink2");
            return (Criteria) this;
        }

        public Criteria andDrink2NotBetween(String value1, String value2) {
            addCriterion("drink2 not between", value1, value2, "drink2");
            return (Criteria) this;
        }

        public Criteria andSport1IsNull() {
            addCriterion("sport1 is null");
            return (Criteria) this;
        }

        public Criteria andSport1IsNotNull() {
            addCriterion("sport1 is not null");
            return (Criteria) this;
        }

        public Criteria andSport1EqualTo(String value) {
            addCriterion("sport1 =", value, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport1NotEqualTo(String value) {
            addCriterion("sport1 <>", value, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport1GreaterThan(String value) {
            addCriterion("sport1 >", value, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport1GreaterThanOrEqualTo(String value) {
            addCriterion("sport1 >=", value, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport1LessThan(String value) {
            addCriterion("sport1 <", value, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport1LessThanOrEqualTo(String value) {
            addCriterion("sport1 <=", value, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport1Like(String value) {
            addCriterion("sport1 like", value, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport1NotLike(String value) {
            addCriterion("sport1 not like", value, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport1In(List<String> values) {
            addCriterion("sport1 in", values, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport1NotIn(List<String> values) {
            addCriterion("sport1 not in", values, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport1Between(String value1, String value2) {
            addCriterion("sport1 between", value1, value2, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport1NotBetween(String value1, String value2) {
            addCriterion("sport1 not between", value1, value2, "sport1");
            return (Criteria) this;
        }

        public Criteria andSport2IsNull() {
            addCriterion("sport2 is null");
            return (Criteria) this;
        }

        public Criteria andSport2IsNotNull() {
            addCriterion("sport2 is not null");
            return (Criteria) this;
        }

        public Criteria andSport2EqualTo(String value) {
            addCriterion("sport2 =", value, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport2NotEqualTo(String value) {
            addCriterion("sport2 <>", value, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport2GreaterThan(String value) {
            addCriterion("sport2 >", value, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport2GreaterThanOrEqualTo(String value) {
            addCriterion("sport2 >=", value, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport2LessThan(String value) {
            addCriterion("sport2 <", value, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport2LessThanOrEqualTo(String value) {
            addCriterion("sport2 <=", value, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport2Like(String value) {
            addCriterion("sport2 like", value, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport2NotLike(String value) {
            addCriterion("sport2 not like", value, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport2In(List<String> values) {
            addCriterion("sport2 in", values, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport2NotIn(List<String> values) {
            addCriterion("sport2 not in", values, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport2Between(String value1, String value2) {
            addCriterion("sport2 between", value1, value2, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport2NotBetween(String value1, String value2) {
            addCriterion("sport2 not between", value1, value2, "sport2");
            return (Criteria) this;
        }

        public Criteria andSport3IsNull() {
            addCriterion("sport3 is null");
            return (Criteria) this;
        }

        public Criteria andSport3IsNotNull() {
            addCriterion("sport3 is not null");
            return (Criteria) this;
        }

        public Criteria andSport3EqualTo(String value) {
            addCriterion("sport3 =", value, "sport3");
            return (Criteria) this;
        }

        public Criteria andSport3NotEqualTo(String value) {
            addCriterion("sport3 <>", value, "sport3");
            return (Criteria) this;
        }

        public Criteria andSport3GreaterThan(String value) {
            addCriterion("sport3 >", value, "sport3");
            return (Criteria) this;
        }

        public Criteria andSport3GreaterThanOrEqualTo(String value) {
            addCriterion("sport3 >=", value, "sport3");
            return (Criteria) this;
        }

        public Criteria andSport3LessThan(String value) {
            addCriterion("sport3 <", value, "sport3");
            return (Criteria) this;
        }

        public Criteria andSport3LessThanOrEqualTo(String value) {
            addCriterion("sport3 <=", value, "sport3");
            return (Criteria) this;
        }

        public Criteria andSport3Like(String value) {
            addCriterion("sport3 like", value, "sport3");
            return (Criteria) this;
        }

        public Criteria andSport3NotLike(String value) {
            addCriterion("sport3 not like", value, "sport3");
            return (Criteria) this;
        }

        public Criteria andSport3In(List<String> values) {
            addCriterion("sport3 in", values, "sport3");
            return (Criteria) this;
        }

        public Criteria andSport3NotIn(List<String> values) {
            addCriterion("sport3 not in", values, "sport3");
            return (Criteria) this;
        }

        public Criteria andSport3Between(String value1, String value2) {
            addCriterion("sport3 between", value1, value2, "sport3");
            return (Criteria) this;
        }

        public Criteria andSport3NotBetween(String value1, String value2) {
            addCriterion("sport3 not between", value1, value2, "sport3");
            return (Criteria) this;
        }

        public Criteria andFood1IsNull() {
            addCriterion("food1 is null");
            return (Criteria) this;
        }

        public Criteria andFood1IsNotNull() {
            addCriterion("food1 is not null");
            return (Criteria) this;
        }

        public Criteria andFood1EqualTo(String value) {
            addCriterion("food1 =", value, "food1");
            return (Criteria) this;
        }

        public Criteria andFood1NotEqualTo(String value) {
            addCriterion("food1 <>", value, "food1");
            return (Criteria) this;
        }

        public Criteria andFood1GreaterThan(String value) {
            addCriterion("food1 >", value, "food1");
            return (Criteria) this;
        }

        public Criteria andFood1GreaterThanOrEqualTo(String value) {
            addCriterion("food1 >=", value, "food1");
            return (Criteria) this;
        }

        public Criteria andFood1LessThan(String value) {
            addCriterion("food1 <", value, "food1");
            return (Criteria) this;
        }

        public Criteria andFood1LessThanOrEqualTo(String value) {
            addCriterion("food1 <=", value, "food1");
            return (Criteria) this;
        }

        public Criteria andFood1Like(String value) {
            addCriterion("food1 like", value, "food1");
            return (Criteria) this;
        }

        public Criteria andFood1NotLike(String value) {
            addCriterion("food1 not like", value, "food1");
            return (Criteria) this;
        }

        public Criteria andFood1In(List<String> values) {
            addCriterion("food1 in", values, "food1");
            return (Criteria) this;
        }

        public Criteria andFood1NotIn(List<String> values) {
            addCriterion("food1 not in", values, "food1");
            return (Criteria) this;
        }

        public Criteria andFood1Between(String value1, String value2) {
            addCriterion("food1 between", value1, value2, "food1");
            return (Criteria) this;
        }

        public Criteria andFood1NotBetween(String value1, String value2) {
            addCriterion("food1 not between", value1, value2, "food1");
            return (Criteria) this;
        }

        public Criteria andFood2IsNull() {
            addCriterion("food2 is null");
            return (Criteria) this;
        }

        public Criteria andFood2IsNotNull() {
            addCriterion("food2 is not null");
            return (Criteria) this;
        }

        public Criteria andFood2EqualTo(String value) {
            addCriterion("food2 =", value, "food2");
            return (Criteria) this;
        }

        public Criteria andFood2NotEqualTo(String value) {
            addCriterion("food2 <>", value, "food2");
            return (Criteria) this;
        }

        public Criteria andFood2GreaterThan(String value) {
            addCriterion("food2 >", value, "food2");
            return (Criteria) this;
        }

        public Criteria andFood2GreaterThanOrEqualTo(String value) {
            addCriterion("food2 >=", value, "food2");
            return (Criteria) this;
        }

        public Criteria andFood2LessThan(String value) {
            addCriterion("food2 <", value, "food2");
            return (Criteria) this;
        }

        public Criteria andFood2LessThanOrEqualTo(String value) {
            addCriterion("food2 <=", value, "food2");
            return (Criteria) this;
        }

        public Criteria andFood2Like(String value) {
            addCriterion("food2 like", value, "food2");
            return (Criteria) this;
        }

        public Criteria andFood2NotLike(String value) {
            addCriterion("food2 not like", value, "food2");
            return (Criteria) this;
        }

        public Criteria andFood2In(List<String> values) {
            addCriterion("food2 in", values, "food2");
            return (Criteria) this;
        }

        public Criteria andFood2NotIn(List<String> values) {
            addCriterion("food2 not in", values, "food2");
            return (Criteria) this;
        }

        public Criteria andFood2Between(String value1, String value2) {
            addCriterion("food2 between", value1, value2, "food2");
            return (Criteria) this;
        }

        public Criteria andFood2NotBetween(String value1, String value2) {
            addCriterion("food2 not between", value1, value2, "food2");
            return (Criteria) this;
        }

        public Criteria andFood3IsNull() {
            addCriterion("food3 is null");
            return (Criteria) this;
        }

        public Criteria andFood3IsNotNull() {
            addCriterion("food3 is not null");
            return (Criteria) this;
        }

        public Criteria andFood3EqualTo(String value) {
            addCriterion("food3 =", value, "food3");
            return (Criteria) this;
        }

        public Criteria andFood3NotEqualTo(String value) {
            addCriterion("food3 <>", value, "food3");
            return (Criteria) this;
        }

        public Criteria andFood3GreaterThan(String value) {
            addCriterion("food3 >", value, "food3");
            return (Criteria) this;
        }

        public Criteria andFood3GreaterThanOrEqualTo(String value) {
            addCriterion("food3 >=", value, "food3");
            return (Criteria) this;
        }

        public Criteria andFood3LessThan(String value) {
            addCriterion("food3 <", value, "food3");
            return (Criteria) this;
        }

        public Criteria andFood3LessThanOrEqualTo(String value) {
            addCriterion("food3 <=", value, "food3");
            return (Criteria) this;
        }

        public Criteria andFood3Like(String value) {
            addCriterion("food3 like", value, "food3");
            return (Criteria) this;
        }

        public Criteria andFood3NotLike(String value) {
            addCriterion("food3 not like", value, "food3");
            return (Criteria) this;
        }

        public Criteria andFood3In(List<String> values) {
            addCriterion("food3 in", values, "food3");
            return (Criteria) this;
        }

        public Criteria andFood3NotIn(List<String> values) {
            addCriterion("food3 not in", values, "food3");
            return (Criteria) this;
        }

        public Criteria andFood3Between(String value1, String value2) {
            addCriterion("food3 between", value1, value2, "food3");
            return (Criteria) this;
        }

        public Criteria andFood3NotBetween(String value1, String value2) {
            addCriterion("food3 not between", value1, value2, "food3");
            return (Criteria) this;
        }

        public Criteria andFood4IsNull() {
            addCriterion("food4 is null");
            return (Criteria) this;
        }

        public Criteria andFood4IsNotNull() {
            addCriterion("food4 is not null");
            return (Criteria) this;
        }

        public Criteria andFood4EqualTo(String value) {
            addCriterion("food4 =", value, "food4");
            return (Criteria) this;
        }

        public Criteria andFood4NotEqualTo(String value) {
            addCriterion("food4 <>", value, "food4");
            return (Criteria) this;
        }

        public Criteria andFood4GreaterThan(String value) {
            addCriterion("food4 >", value, "food4");
            return (Criteria) this;
        }

        public Criteria andFood4GreaterThanOrEqualTo(String value) {
            addCriterion("food4 >=", value, "food4");
            return (Criteria) this;
        }

        public Criteria andFood4LessThan(String value) {
            addCriterion("food4 <", value, "food4");
            return (Criteria) this;
        }

        public Criteria andFood4LessThanOrEqualTo(String value) {
            addCriterion("food4 <=", value, "food4");
            return (Criteria) this;
        }

        public Criteria andFood4Like(String value) {
            addCriterion("food4 like", value, "food4");
            return (Criteria) this;
        }

        public Criteria andFood4NotLike(String value) {
            addCriterion("food4 not like", value, "food4");
            return (Criteria) this;
        }

        public Criteria andFood4In(List<String> values) {
            addCriterion("food4 in", values, "food4");
            return (Criteria) this;
        }

        public Criteria andFood4NotIn(List<String> values) {
            addCriterion("food4 not in", values, "food4");
            return (Criteria) this;
        }

        public Criteria andFood4Between(String value1, String value2) {
            addCriterion("food4 between", value1, value2, "food4");
            return (Criteria) this;
        }

        public Criteria andFood4NotBetween(String value1, String value2) {
            addCriterion("food4 not between", value1, value2, "food4");
            return (Criteria) this;
        }

        public Criteria andPsychology1IsNull() {
            addCriterion("psychology1 is null");
            return (Criteria) this;
        }

        public Criteria andPsychology1IsNotNull() {
            addCriterion("psychology1 is not null");
            return (Criteria) this;
        }

        public Criteria andPsychology1EqualTo(String value) {
            addCriterion("psychology1 =", value, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology1NotEqualTo(String value) {
            addCriterion("psychology1 <>", value, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology1GreaterThan(String value) {
            addCriterion("psychology1 >", value, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology1GreaterThanOrEqualTo(String value) {
            addCriterion("psychology1 >=", value, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology1LessThan(String value) {
            addCriterion("psychology1 <", value, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology1LessThanOrEqualTo(String value) {
            addCriterion("psychology1 <=", value, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology1Like(String value) {
            addCriterion("psychology1 like", value, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology1NotLike(String value) {
            addCriterion("psychology1 not like", value, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology1In(List<String> values) {
            addCriterion("psychology1 in", values, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology1NotIn(List<String> values) {
            addCriterion("psychology1 not in", values, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology1Between(String value1, String value2) {
            addCriterion("psychology1 between", value1, value2, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology1NotBetween(String value1, String value2) {
            addCriterion("psychology1 not between", value1, value2, "psychology1");
            return (Criteria) this;
        }

        public Criteria andPsychology2IsNull() {
            addCriterion("psychology2 is null");
            return (Criteria) this;
        }

        public Criteria andPsychology2IsNotNull() {
            addCriterion("psychology2 is not null");
            return (Criteria) this;
        }

        public Criteria andPsychology2EqualTo(String value) {
            addCriterion("psychology2 =", value, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology2NotEqualTo(String value) {
            addCriterion("psychology2 <>", value, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology2GreaterThan(String value) {
            addCriterion("psychology2 >", value, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology2GreaterThanOrEqualTo(String value) {
            addCriterion("psychology2 >=", value, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology2LessThan(String value) {
            addCriterion("psychology2 <", value, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology2LessThanOrEqualTo(String value) {
            addCriterion("psychology2 <=", value, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology2Like(String value) {
            addCriterion("psychology2 like", value, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology2NotLike(String value) {
            addCriterion("psychology2 not like", value, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology2In(List<String> values) {
            addCriterion("psychology2 in", values, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology2NotIn(List<String> values) {
            addCriterion("psychology2 not in", values, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology2Between(String value1, String value2) {
            addCriterion("psychology2 between", value1, value2, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology2NotBetween(String value1, String value2) {
            addCriterion("psychology2 not between", value1, value2, "psychology2");
            return (Criteria) this;
        }

        public Criteria andPsychology3IsNull() {
            addCriterion("psychology3 is null");
            return (Criteria) this;
        }

        public Criteria andPsychology3IsNotNull() {
            addCriterion("psychology3 is not null");
            return (Criteria) this;
        }

        public Criteria andPsychology3EqualTo(String value) {
            addCriterion("psychology3 =", value, "psychology3");
            return (Criteria) this;
        }

        public Criteria andPsychology3NotEqualTo(String value) {
            addCriterion("psychology3 <>", value, "psychology3");
            return (Criteria) this;
        }

        public Criteria andPsychology3GreaterThan(String value) {
            addCriterion("psychology3 >", value, "psychology3");
            return (Criteria) this;
        }

        public Criteria andPsychology3GreaterThanOrEqualTo(String value) {
            addCriterion("psychology3 >=", value, "psychology3");
            return (Criteria) this;
        }

        public Criteria andPsychology3LessThan(String value) {
            addCriterion("psychology3 <", value, "psychology3");
            return (Criteria) this;
        }

        public Criteria andPsychology3LessThanOrEqualTo(String value) {
            addCriterion("psychology3 <=", value, "psychology3");
            return (Criteria) this;
        }

        public Criteria andPsychology3Like(String value) {
            addCriterion("psychology3 like", value, "psychology3");
            return (Criteria) this;
        }

        public Criteria andPsychology3NotLike(String value) {
            addCriterion("psychology3 not like", value, "psychology3");
            return (Criteria) this;
        }

        public Criteria andPsychology3In(List<String> values) {
            addCriterion("psychology3 in", values, "psychology3");
            return (Criteria) this;
        }

        public Criteria andPsychology3NotIn(List<String> values) {
            addCriterion("psychology3 not in", values, "psychology3");
            return (Criteria) this;
        }

        public Criteria andPsychology3Between(String value1, String value2) {
            addCriterion("psychology3 between", value1, value2, "psychology3");
            return (Criteria) this;
        }

        public Criteria andPsychology3NotBetween(String value1, String value2) {
            addCriterion("psychology3 not between", value1, value2, "psychology3");
            return (Criteria) this;
        }

        public Criteria andSleep1IsNull() {
            addCriterion("sleep1 is null");
            return (Criteria) this;
        }

        public Criteria andSleep1IsNotNull() {
            addCriterion("sleep1 is not null");
            return (Criteria) this;
        }

        public Criteria andSleep1EqualTo(String value) {
            addCriterion("sleep1 =", value, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep1NotEqualTo(String value) {
            addCriterion("sleep1 <>", value, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep1GreaterThan(String value) {
            addCriterion("sleep1 >", value, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep1GreaterThanOrEqualTo(String value) {
            addCriterion("sleep1 >=", value, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep1LessThan(String value) {
            addCriterion("sleep1 <", value, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep1LessThanOrEqualTo(String value) {
            addCriterion("sleep1 <=", value, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep1Like(String value) {
            addCriterion("sleep1 like", value, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep1NotLike(String value) {
            addCriterion("sleep1 not like", value, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep1In(List<String> values) {
            addCriterion("sleep1 in", values, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep1NotIn(List<String> values) {
            addCriterion("sleep1 not in", values, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep1Between(String value1, String value2) {
            addCriterion("sleep1 between", value1, value2, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep1NotBetween(String value1, String value2) {
            addCriterion("sleep1 not between", value1, value2, "sleep1");
            return (Criteria) this;
        }

        public Criteria andSleep2IsNull() {
            addCriterion("sleep2 is null");
            return (Criteria) this;
        }

        public Criteria andSleep2IsNotNull() {
            addCriterion("sleep2 is not null");
            return (Criteria) this;
        }

        public Criteria andSleep2EqualTo(String value) {
            addCriterion("sleep2 =", value, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep2NotEqualTo(String value) {
            addCriterion("sleep2 <>", value, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep2GreaterThan(String value) {
            addCriterion("sleep2 >", value, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep2GreaterThanOrEqualTo(String value) {
            addCriterion("sleep2 >=", value, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep2LessThan(String value) {
            addCriterion("sleep2 <", value, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep2LessThanOrEqualTo(String value) {
            addCriterion("sleep2 <=", value, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep2Like(String value) {
            addCriterion("sleep2 like", value, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep2NotLike(String value) {
            addCriterion("sleep2 not like", value, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep2In(List<String> values) {
            addCriterion("sleep2 in", values, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep2NotIn(List<String> values) {
            addCriterion("sleep2 not in", values, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep2Between(String value1, String value2) {
            addCriterion("sleep2 between", value1, value2, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep2NotBetween(String value1, String value2) {
            addCriterion("sleep2 not between", value1, value2, "sleep2");
            return (Criteria) this;
        }

        public Criteria andSleep3IsNull() {
            addCriterion("sleep3 is null");
            return (Criteria) this;
        }

        public Criteria andSleep3IsNotNull() {
            addCriterion("sleep3 is not null");
            return (Criteria) this;
        }

        public Criteria andSleep3EqualTo(String value) {
            addCriterion("sleep3 =", value, "sleep3");
            return (Criteria) this;
        }

        public Criteria andSleep3NotEqualTo(String value) {
            addCriterion("sleep3 <>", value, "sleep3");
            return (Criteria) this;
        }

        public Criteria andSleep3GreaterThan(String value) {
            addCriterion("sleep3 >", value, "sleep3");
            return (Criteria) this;
        }

        public Criteria andSleep3GreaterThanOrEqualTo(String value) {
            addCriterion("sleep3 >=", value, "sleep3");
            return (Criteria) this;
        }

        public Criteria andSleep3LessThan(String value) {
            addCriterion("sleep3 <", value, "sleep3");
            return (Criteria) this;
        }

        public Criteria andSleep3LessThanOrEqualTo(String value) {
            addCriterion("sleep3 <=", value, "sleep3");
            return (Criteria) this;
        }

        public Criteria andSleep3Like(String value) {
            addCriterion("sleep3 like", value, "sleep3");
            return (Criteria) this;
        }

        public Criteria andSleep3NotLike(String value) {
            addCriterion("sleep3 not like", value, "sleep3");
            return (Criteria) this;
        }

        public Criteria andSleep3In(List<String> values) {
            addCriterion("sleep3 in", values, "sleep3");
            return (Criteria) this;
        }

        public Criteria andSleep3NotIn(List<String> values) {
            addCriterion("sleep3 not in", values, "sleep3");
            return (Criteria) this;
        }

        public Criteria andSleep3Between(String value1, String value2) {
            addCriterion("sleep3 between", value1, value2, "sleep3");
            return (Criteria) this;
        }

        public Criteria andSleep3NotBetween(String value1, String value2) {
            addCriterion("sleep3 not between", value1, value2, "sleep3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1IsNull() {
            addCriterion("coronaryDiseaseRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1IsNotNull() {
            addCriterion("coronaryDiseaseRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1EqualTo(String value) {
            addCriterion("coronaryDiseaseRisk1 =", value, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1NotEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk1 <>", value, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1GreaterThan(String value) {
            addCriterion("coronaryDiseaseRisk1 >", value, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1GreaterThanOrEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk1 >=", value, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1LessThan(String value) {
            addCriterion("coronaryDiseaseRisk1 <", value, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1LessThanOrEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk1 <=", value, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1Like(String value) {
            addCriterion("coronaryDiseaseRisk1 like", value, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1NotLike(String value) {
            addCriterion("coronaryDiseaseRisk1 not like", value, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1In(List<String> values) {
            addCriterion("coronaryDiseaseRisk1 in", values, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1NotIn(List<String> values) {
            addCriterion("coronaryDiseaseRisk1 not in", values, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1Between(String value1, String value2) {
            addCriterion("coronaryDiseaseRisk1 between", value1, value2, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk1NotBetween(String value1, String value2) {
            addCriterion("coronaryDiseaseRisk1 not between", value1, value2, "coronarydiseaserisk1");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2IsNull() {
            addCriterion("coronaryDiseaseRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2IsNotNull() {
            addCriterion("coronaryDiseaseRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2EqualTo(String value) {
            addCriterion("coronaryDiseaseRisk2 =", value, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2NotEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk2 <>", value, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2GreaterThan(String value) {
            addCriterion("coronaryDiseaseRisk2 >", value, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2GreaterThanOrEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk2 >=", value, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2LessThan(String value) {
            addCriterion("coronaryDiseaseRisk2 <", value, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2LessThanOrEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk2 <=", value, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2Like(String value) {
            addCriterion("coronaryDiseaseRisk2 like", value, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2NotLike(String value) {
            addCriterion("coronaryDiseaseRisk2 not like", value, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2In(List<String> values) {
            addCriterion("coronaryDiseaseRisk2 in", values, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2NotIn(List<String> values) {
            addCriterion("coronaryDiseaseRisk2 not in", values, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2Between(String value1, String value2) {
            addCriterion("coronaryDiseaseRisk2 between", value1, value2, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk2NotBetween(String value1, String value2) {
            addCriterion("coronaryDiseaseRisk2 not between", value1, value2, "coronarydiseaserisk2");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3IsNull() {
            addCriterion("coronaryDiseaseRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3IsNotNull() {
            addCriterion("coronaryDiseaseRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3EqualTo(String value) {
            addCriterion("coronaryDiseaseRisk3 =", value, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3NotEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk3 <>", value, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3GreaterThan(String value) {
            addCriterion("coronaryDiseaseRisk3 >", value, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3GreaterThanOrEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk3 >=", value, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3LessThan(String value) {
            addCriterion("coronaryDiseaseRisk3 <", value, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3LessThanOrEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk3 <=", value, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3Like(String value) {
            addCriterion("coronaryDiseaseRisk3 like", value, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3NotLike(String value) {
            addCriterion("coronaryDiseaseRisk3 not like", value, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3In(List<String> values) {
            addCriterion("coronaryDiseaseRisk3 in", values, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3NotIn(List<String> values) {
            addCriterion("coronaryDiseaseRisk3 not in", values, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3Between(String value1, String value2) {
            addCriterion("coronaryDiseaseRisk3 between", value1, value2, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk3NotBetween(String value1, String value2) {
            addCriterion("coronaryDiseaseRisk3 not between", value1, value2, "coronarydiseaserisk3");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4IsNull() {
            addCriterion("coronaryDiseaseRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4IsNotNull() {
            addCriterion("coronaryDiseaseRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4EqualTo(String value) {
            addCriterion("coronaryDiseaseRisk4 =", value, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4NotEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk4 <>", value, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4GreaterThan(String value) {
            addCriterion("coronaryDiseaseRisk4 >", value, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4GreaterThanOrEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk4 >=", value, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4LessThan(String value) {
            addCriterion("coronaryDiseaseRisk4 <", value, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4LessThanOrEqualTo(String value) {
            addCriterion("coronaryDiseaseRisk4 <=", value, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4Like(String value) {
            addCriterion("coronaryDiseaseRisk4 like", value, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4NotLike(String value) {
            addCriterion("coronaryDiseaseRisk4 not like", value, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4In(List<String> values) {
            addCriterion("coronaryDiseaseRisk4 in", values, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4NotIn(List<String> values) {
            addCriterion("coronaryDiseaseRisk4 not in", values, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4Between(String value1, String value2) {
            addCriterion("coronaryDiseaseRisk4 between", value1, value2, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andCoronarydiseaserisk4NotBetween(String value1, String value2) {
            addCriterion("coronaryDiseaseRisk4 not between", value1, value2, "coronarydiseaserisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1IsNull() {
            addCriterion("hypertensionRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1IsNotNull() {
            addCriterion("hypertensionRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1EqualTo(String value) {
            addCriterion("hypertensionRisk1 =", value, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1NotEqualTo(String value) {
            addCriterion("hypertensionRisk1 <>", value, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1GreaterThan(String value) {
            addCriterion("hypertensionRisk1 >", value, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1GreaterThanOrEqualTo(String value) {
            addCriterion("hypertensionRisk1 >=", value, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1LessThan(String value) {
            addCriterion("hypertensionRisk1 <", value, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1LessThanOrEqualTo(String value) {
            addCriterion("hypertensionRisk1 <=", value, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1Like(String value) {
            addCriterion("hypertensionRisk1 like", value, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1NotLike(String value) {
            addCriterion("hypertensionRisk1 not like", value, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1In(List<String> values) {
            addCriterion("hypertensionRisk1 in", values, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1NotIn(List<String> values) {
            addCriterion("hypertensionRisk1 not in", values, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1Between(String value1, String value2) {
            addCriterion("hypertensionRisk1 between", value1, value2, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk1NotBetween(String value1, String value2) {
            addCriterion("hypertensionRisk1 not between", value1, value2, "hypertensionrisk1");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2IsNull() {
            addCriterion("hypertensionRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2IsNotNull() {
            addCriterion("hypertensionRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2EqualTo(String value) {
            addCriterion("hypertensionRisk2 =", value, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2NotEqualTo(String value) {
            addCriterion("hypertensionRisk2 <>", value, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2GreaterThan(String value) {
            addCriterion("hypertensionRisk2 >", value, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2GreaterThanOrEqualTo(String value) {
            addCriterion("hypertensionRisk2 >=", value, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2LessThan(String value) {
            addCriterion("hypertensionRisk2 <", value, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2LessThanOrEqualTo(String value) {
            addCriterion("hypertensionRisk2 <=", value, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2Like(String value) {
            addCriterion("hypertensionRisk2 like", value, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2NotLike(String value) {
            addCriterion("hypertensionRisk2 not like", value, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2In(List<String> values) {
            addCriterion("hypertensionRisk2 in", values, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2NotIn(List<String> values) {
            addCriterion("hypertensionRisk2 not in", values, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2Between(String value1, String value2) {
            addCriterion("hypertensionRisk2 between", value1, value2, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk2NotBetween(String value1, String value2) {
            addCriterion("hypertensionRisk2 not between", value1, value2, "hypertensionrisk2");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3IsNull() {
            addCriterion("hypertensionRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3IsNotNull() {
            addCriterion("hypertensionRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3EqualTo(String value) {
            addCriterion("hypertensionRisk3 =", value, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3NotEqualTo(String value) {
            addCriterion("hypertensionRisk3 <>", value, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3GreaterThan(String value) {
            addCriterion("hypertensionRisk3 >", value, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3GreaterThanOrEqualTo(String value) {
            addCriterion("hypertensionRisk3 >=", value, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3LessThan(String value) {
            addCriterion("hypertensionRisk3 <", value, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3LessThanOrEqualTo(String value) {
            addCriterion("hypertensionRisk3 <=", value, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3Like(String value) {
            addCriterion("hypertensionRisk3 like", value, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3NotLike(String value) {
            addCriterion("hypertensionRisk3 not like", value, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3In(List<String> values) {
            addCriterion("hypertensionRisk3 in", values, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3NotIn(List<String> values) {
            addCriterion("hypertensionRisk3 not in", values, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3Between(String value1, String value2) {
            addCriterion("hypertensionRisk3 between", value1, value2, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk3NotBetween(String value1, String value2) {
            addCriterion("hypertensionRisk3 not between", value1, value2, "hypertensionrisk3");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4IsNull() {
            addCriterion("hypertensionRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4IsNotNull() {
            addCriterion("hypertensionRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4EqualTo(String value) {
            addCriterion("hypertensionRisk4 =", value, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4NotEqualTo(String value) {
            addCriterion("hypertensionRisk4 <>", value, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4GreaterThan(String value) {
            addCriterion("hypertensionRisk4 >", value, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4GreaterThanOrEqualTo(String value) {
            addCriterion("hypertensionRisk4 >=", value, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4LessThan(String value) {
            addCriterion("hypertensionRisk4 <", value, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4LessThanOrEqualTo(String value) {
            addCriterion("hypertensionRisk4 <=", value, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4Like(String value) {
            addCriterion("hypertensionRisk4 like", value, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4NotLike(String value) {
            addCriterion("hypertensionRisk4 not like", value, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4In(List<String> values) {
            addCriterion("hypertensionRisk4 in", values, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4NotIn(List<String> values) {
            addCriterion("hypertensionRisk4 not in", values, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4Between(String value1, String value2) {
            addCriterion("hypertensionRisk4 between", value1, value2, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andHypertensionrisk4NotBetween(String value1, String value2) {
            addCriterion("hypertensionRisk4 not between", value1, value2, "hypertensionrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1IsNull() {
            addCriterion("apoplexyRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1IsNotNull() {
            addCriterion("apoplexyRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1EqualTo(String value) {
            addCriterion("apoplexyRisk1 =", value, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1NotEqualTo(String value) {
            addCriterion("apoplexyRisk1 <>", value, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1GreaterThan(String value) {
            addCriterion("apoplexyRisk1 >", value, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1GreaterThanOrEqualTo(String value) {
            addCriterion("apoplexyRisk1 >=", value, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1LessThan(String value) {
            addCriterion("apoplexyRisk1 <", value, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1LessThanOrEqualTo(String value) {
            addCriterion("apoplexyRisk1 <=", value, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1Like(String value) {
            addCriterion("apoplexyRisk1 like", value, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1NotLike(String value) {
            addCriterion("apoplexyRisk1 not like", value, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1In(List<String> values) {
            addCriterion("apoplexyRisk1 in", values, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1NotIn(List<String> values) {
            addCriterion("apoplexyRisk1 not in", values, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1Between(String value1, String value2) {
            addCriterion("apoplexyRisk1 between", value1, value2, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk1NotBetween(String value1, String value2) {
            addCriterion("apoplexyRisk1 not between", value1, value2, "apoplexyrisk1");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2IsNull() {
            addCriterion("apoplexyRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2IsNotNull() {
            addCriterion("apoplexyRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2EqualTo(String value) {
            addCriterion("apoplexyRisk2 =", value, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2NotEqualTo(String value) {
            addCriterion("apoplexyRisk2 <>", value, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2GreaterThan(String value) {
            addCriterion("apoplexyRisk2 >", value, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2GreaterThanOrEqualTo(String value) {
            addCriterion("apoplexyRisk2 >=", value, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2LessThan(String value) {
            addCriterion("apoplexyRisk2 <", value, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2LessThanOrEqualTo(String value) {
            addCriterion("apoplexyRisk2 <=", value, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2Like(String value) {
            addCriterion("apoplexyRisk2 like", value, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2NotLike(String value) {
            addCriterion("apoplexyRisk2 not like", value, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2In(List<String> values) {
            addCriterion("apoplexyRisk2 in", values, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2NotIn(List<String> values) {
            addCriterion("apoplexyRisk2 not in", values, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2Between(String value1, String value2) {
            addCriterion("apoplexyRisk2 between", value1, value2, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk2NotBetween(String value1, String value2) {
            addCriterion("apoplexyRisk2 not between", value1, value2, "apoplexyrisk2");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3IsNull() {
            addCriterion("apoplexyRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3IsNotNull() {
            addCriterion("apoplexyRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3EqualTo(String value) {
            addCriterion("apoplexyRisk3 =", value, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3NotEqualTo(String value) {
            addCriterion("apoplexyRisk3 <>", value, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3GreaterThan(String value) {
            addCriterion("apoplexyRisk3 >", value, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3GreaterThanOrEqualTo(String value) {
            addCriterion("apoplexyRisk3 >=", value, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3LessThan(String value) {
            addCriterion("apoplexyRisk3 <", value, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3LessThanOrEqualTo(String value) {
            addCriterion("apoplexyRisk3 <=", value, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3Like(String value) {
            addCriterion("apoplexyRisk3 like", value, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3NotLike(String value) {
            addCriterion("apoplexyRisk3 not like", value, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3In(List<String> values) {
            addCriterion("apoplexyRisk3 in", values, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3NotIn(List<String> values) {
            addCriterion("apoplexyRisk3 not in", values, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3Between(String value1, String value2) {
            addCriterion("apoplexyRisk3 between", value1, value2, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk3NotBetween(String value1, String value2) {
            addCriterion("apoplexyRisk3 not between", value1, value2, "apoplexyrisk3");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4IsNull() {
            addCriterion("apoplexyRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4IsNotNull() {
            addCriterion("apoplexyRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4EqualTo(String value) {
            addCriterion("apoplexyRisk4 =", value, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4NotEqualTo(String value) {
            addCriterion("apoplexyRisk4 <>", value, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4GreaterThan(String value) {
            addCriterion("apoplexyRisk4 >", value, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4GreaterThanOrEqualTo(String value) {
            addCriterion("apoplexyRisk4 >=", value, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4LessThan(String value) {
            addCriterion("apoplexyRisk4 <", value, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4LessThanOrEqualTo(String value) {
            addCriterion("apoplexyRisk4 <=", value, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4Like(String value) {
            addCriterion("apoplexyRisk4 like", value, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4NotLike(String value) {
            addCriterion("apoplexyRisk4 not like", value, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4In(List<String> values) {
            addCriterion("apoplexyRisk4 in", values, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4NotIn(List<String> values) {
            addCriterion("apoplexyRisk4 not in", values, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4Between(String value1, String value2) {
            addCriterion("apoplexyRisk4 between", value1, value2, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andApoplexyrisk4NotBetween(String value1, String value2) {
            addCriterion("apoplexyRisk4 not between", value1, value2, "apoplexyrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1IsNull() {
            addCriterion("diabetesRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1IsNotNull() {
            addCriterion("diabetesRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1EqualTo(String value) {
            addCriterion("diabetesRisk1 =", value, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1NotEqualTo(String value) {
            addCriterion("diabetesRisk1 <>", value, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1GreaterThan(String value) {
            addCriterion("diabetesRisk1 >", value, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1GreaterThanOrEqualTo(String value) {
            addCriterion("diabetesRisk1 >=", value, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1LessThan(String value) {
            addCriterion("diabetesRisk1 <", value, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1LessThanOrEqualTo(String value) {
            addCriterion("diabetesRisk1 <=", value, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1Like(String value) {
            addCriterion("diabetesRisk1 like", value, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1NotLike(String value) {
            addCriterion("diabetesRisk1 not like", value, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1In(List<String> values) {
            addCriterion("diabetesRisk1 in", values, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1NotIn(List<String> values) {
            addCriterion("diabetesRisk1 not in", values, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1Between(String value1, String value2) {
            addCriterion("diabetesRisk1 between", value1, value2, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk1NotBetween(String value1, String value2) {
            addCriterion("diabetesRisk1 not between", value1, value2, "diabetesrisk1");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2IsNull() {
            addCriterion("diabetesRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2IsNotNull() {
            addCriterion("diabetesRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2EqualTo(String value) {
            addCriterion("diabetesRisk2 =", value, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2NotEqualTo(String value) {
            addCriterion("diabetesRisk2 <>", value, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2GreaterThan(String value) {
            addCriterion("diabetesRisk2 >", value, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2GreaterThanOrEqualTo(String value) {
            addCriterion("diabetesRisk2 >=", value, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2LessThan(String value) {
            addCriterion("diabetesRisk2 <", value, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2LessThanOrEqualTo(String value) {
            addCriterion("diabetesRisk2 <=", value, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2Like(String value) {
            addCriterion("diabetesRisk2 like", value, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2NotLike(String value) {
            addCriterion("diabetesRisk2 not like", value, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2In(List<String> values) {
            addCriterion("diabetesRisk2 in", values, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2NotIn(List<String> values) {
            addCriterion("diabetesRisk2 not in", values, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2Between(String value1, String value2) {
            addCriterion("diabetesRisk2 between", value1, value2, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk2NotBetween(String value1, String value2) {
            addCriterion("diabetesRisk2 not between", value1, value2, "diabetesrisk2");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3IsNull() {
            addCriterion("diabetesRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3IsNotNull() {
            addCriterion("diabetesRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3EqualTo(String value) {
            addCriterion("diabetesRisk3 =", value, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3NotEqualTo(String value) {
            addCriterion("diabetesRisk3 <>", value, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3GreaterThan(String value) {
            addCriterion("diabetesRisk3 >", value, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3GreaterThanOrEqualTo(String value) {
            addCriterion("diabetesRisk3 >=", value, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3LessThan(String value) {
            addCriterion("diabetesRisk3 <", value, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3LessThanOrEqualTo(String value) {
            addCriterion("diabetesRisk3 <=", value, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3Like(String value) {
            addCriterion("diabetesRisk3 like", value, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3NotLike(String value) {
            addCriterion("diabetesRisk3 not like", value, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3In(List<String> values) {
            addCriterion("diabetesRisk3 in", values, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3NotIn(List<String> values) {
            addCriterion("diabetesRisk3 not in", values, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3Between(String value1, String value2) {
            addCriterion("diabetesRisk3 between", value1, value2, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk3NotBetween(String value1, String value2) {
            addCriterion("diabetesRisk3 not between", value1, value2, "diabetesrisk3");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4IsNull() {
            addCriterion("diabetesRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4IsNotNull() {
            addCriterion("diabetesRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4EqualTo(String value) {
            addCriterion("diabetesRisk4 =", value, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4NotEqualTo(String value) {
            addCriterion("diabetesRisk4 <>", value, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4GreaterThan(String value) {
            addCriterion("diabetesRisk4 >", value, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4GreaterThanOrEqualTo(String value) {
            addCriterion("diabetesRisk4 >=", value, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4LessThan(String value) {
            addCriterion("diabetesRisk4 <", value, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4LessThanOrEqualTo(String value) {
            addCriterion("diabetesRisk4 <=", value, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4Like(String value) {
            addCriterion("diabetesRisk4 like", value, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4NotLike(String value) {
            addCriterion("diabetesRisk4 not like", value, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4In(List<String> values) {
            addCriterion("diabetesRisk4 in", values, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4NotIn(List<String> values) {
            addCriterion("diabetesRisk4 not in", values, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4Between(String value1, String value2) {
            addCriterion("diabetesRisk4 between", value1, value2, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andDiabetesrisk4NotBetween(String value1, String value2) {
            addCriterion("diabetesRisk4 not between", value1, value2, "diabetesrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1IsNull() {
            addCriterion("lungCancerRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1IsNotNull() {
            addCriterion("lungCancerRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1EqualTo(String value) {
            addCriterion("lungCancerRisk1 =", value, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1NotEqualTo(String value) {
            addCriterion("lungCancerRisk1 <>", value, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1GreaterThan(String value) {
            addCriterion("lungCancerRisk1 >", value, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1GreaterThanOrEqualTo(String value) {
            addCriterion("lungCancerRisk1 >=", value, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1LessThan(String value) {
            addCriterion("lungCancerRisk1 <", value, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1LessThanOrEqualTo(String value) {
            addCriterion("lungCancerRisk1 <=", value, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1Like(String value) {
            addCriterion("lungCancerRisk1 like", value, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1NotLike(String value) {
            addCriterion("lungCancerRisk1 not like", value, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1In(List<String> values) {
            addCriterion("lungCancerRisk1 in", values, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1NotIn(List<String> values) {
            addCriterion("lungCancerRisk1 not in", values, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1Between(String value1, String value2) {
            addCriterion("lungCancerRisk1 between", value1, value2, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk1NotBetween(String value1, String value2) {
            addCriterion("lungCancerRisk1 not between", value1, value2, "lungcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2IsNull() {
            addCriterion("lungCancerRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2IsNotNull() {
            addCriterion("lungCancerRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2EqualTo(String value) {
            addCriterion("lungCancerRisk2 =", value, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2NotEqualTo(String value) {
            addCriterion("lungCancerRisk2 <>", value, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2GreaterThan(String value) {
            addCriterion("lungCancerRisk2 >", value, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2GreaterThanOrEqualTo(String value) {
            addCriterion("lungCancerRisk2 >=", value, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2LessThan(String value) {
            addCriterion("lungCancerRisk2 <", value, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2LessThanOrEqualTo(String value) {
            addCriterion("lungCancerRisk2 <=", value, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2Like(String value) {
            addCriterion("lungCancerRisk2 like", value, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2NotLike(String value) {
            addCriterion("lungCancerRisk2 not like", value, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2In(List<String> values) {
            addCriterion("lungCancerRisk2 in", values, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2NotIn(List<String> values) {
            addCriterion("lungCancerRisk2 not in", values, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2Between(String value1, String value2) {
            addCriterion("lungCancerRisk2 between", value1, value2, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk2NotBetween(String value1, String value2) {
            addCriterion("lungCancerRisk2 not between", value1, value2, "lungcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3IsNull() {
            addCriterion("lungCancerRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3IsNotNull() {
            addCriterion("lungCancerRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3EqualTo(String value) {
            addCriterion("lungCancerRisk3 =", value, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3NotEqualTo(String value) {
            addCriterion("lungCancerRisk3 <>", value, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3GreaterThan(String value) {
            addCriterion("lungCancerRisk3 >", value, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3GreaterThanOrEqualTo(String value) {
            addCriterion("lungCancerRisk3 >=", value, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3LessThan(String value) {
            addCriterion("lungCancerRisk3 <", value, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3LessThanOrEqualTo(String value) {
            addCriterion("lungCancerRisk3 <=", value, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3Like(String value) {
            addCriterion("lungCancerRisk3 like", value, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3NotLike(String value) {
            addCriterion("lungCancerRisk3 not like", value, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3In(List<String> values) {
            addCriterion("lungCancerRisk3 in", values, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3NotIn(List<String> values) {
            addCriterion("lungCancerRisk3 not in", values, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3Between(String value1, String value2) {
            addCriterion("lungCancerRisk3 between", value1, value2, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk3NotBetween(String value1, String value2) {
            addCriterion("lungCancerRisk3 not between", value1, value2, "lungcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4IsNull() {
            addCriterion("lungCancerRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4IsNotNull() {
            addCriterion("lungCancerRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4EqualTo(String value) {
            addCriterion("lungCancerRisk4 =", value, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4NotEqualTo(String value) {
            addCriterion("lungCancerRisk4 <>", value, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4GreaterThan(String value) {
            addCriterion("lungCancerRisk4 >", value, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4GreaterThanOrEqualTo(String value) {
            addCriterion("lungCancerRisk4 >=", value, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4LessThan(String value) {
            addCriterion("lungCancerRisk4 <", value, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4LessThanOrEqualTo(String value) {
            addCriterion("lungCancerRisk4 <=", value, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4Like(String value) {
            addCriterion("lungCancerRisk4 like", value, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4NotLike(String value) {
            addCriterion("lungCancerRisk4 not like", value, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4In(List<String> values) {
            addCriterion("lungCancerRisk4 in", values, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4NotIn(List<String> values) {
            addCriterion("lungCancerRisk4 not in", values, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4Between(String value1, String value2) {
            addCriterion("lungCancerRisk4 between", value1, value2, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andLungcancerrisk4NotBetween(String value1, String value2) {
            addCriterion("lungCancerRisk4 not between", value1, value2, "lungcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1IsNull() {
            addCriterion("prostateRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1IsNotNull() {
            addCriterion("prostateRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1EqualTo(String value) {
            addCriterion("prostateRisk1 =", value, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1NotEqualTo(String value) {
            addCriterion("prostateRisk1 <>", value, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1GreaterThan(String value) {
            addCriterion("prostateRisk1 >", value, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1GreaterThanOrEqualTo(String value) {
            addCriterion("prostateRisk1 >=", value, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1LessThan(String value) {
            addCriterion("prostateRisk1 <", value, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1LessThanOrEqualTo(String value) {
            addCriterion("prostateRisk1 <=", value, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1Like(String value) {
            addCriterion("prostateRisk1 like", value, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1NotLike(String value) {
            addCriterion("prostateRisk1 not like", value, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1In(List<String> values) {
            addCriterion("prostateRisk1 in", values, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1NotIn(List<String> values) {
            addCriterion("prostateRisk1 not in", values, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1Between(String value1, String value2) {
            addCriterion("prostateRisk1 between", value1, value2, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk1NotBetween(String value1, String value2) {
            addCriterion("prostateRisk1 not between", value1, value2, "prostaterisk1");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2IsNull() {
            addCriterion("prostateRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2IsNotNull() {
            addCriterion("prostateRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2EqualTo(String value) {
            addCriterion("prostateRisk2 =", value, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2NotEqualTo(String value) {
            addCriterion("prostateRisk2 <>", value, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2GreaterThan(String value) {
            addCriterion("prostateRisk2 >", value, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2GreaterThanOrEqualTo(String value) {
            addCriterion("prostateRisk2 >=", value, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2LessThan(String value) {
            addCriterion("prostateRisk2 <", value, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2LessThanOrEqualTo(String value) {
            addCriterion("prostateRisk2 <=", value, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2Like(String value) {
            addCriterion("prostateRisk2 like", value, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2NotLike(String value) {
            addCriterion("prostateRisk2 not like", value, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2In(List<String> values) {
            addCriterion("prostateRisk2 in", values, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2NotIn(List<String> values) {
            addCriterion("prostateRisk2 not in", values, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2Between(String value1, String value2) {
            addCriterion("prostateRisk2 between", value1, value2, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk2NotBetween(String value1, String value2) {
            addCriterion("prostateRisk2 not between", value1, value2, "prostaterisk2");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3IsNull() {
            addCriterion("prostateRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3IsNotNull() {
            addCriterion("prostateRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3EqualTo(String value) {
            addCriterion("prostateRisk3 =", value, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3NotEqualTo(String value) {
            addCriterion("prostateRisk3 <>", value, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3GreaterThan(String value) {
            addCriterion("prostateRisk3 >", value, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3GreaterThanOrEqualTo(String value) {
            addCriterion("prostateRisk3 >=", value, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3LessThan(String value) {
            addCriterion("prostateRisk3 <", value, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3LessThanOrEqualTo(String value) {
            addCriterion("prostateRisk3 <=", value, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3Like(String value) {
            addCriterion("prostateRisk3 like", value, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3NotLike(String value) {
            addCriterion("prostateRisk3 not like", value, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3In(List<String> values) {
            addCriterion("prostateRisk3 in", values, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3NotIn(List<String> values) {
            addCriterion("prostateRisk3 not in", values, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3Between(String value1, String value2) {
            addCriterion("prostateRisk3 between", value1, value2, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk3NotBetween(String value1, String value2) {
            addCriterion("prostateRisk3 not between", value1, value2, "prostaterisk3");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4IsNull() {
            addCriterion("prostateRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4IsNotNull() {
            addCriterion("prostateRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4EqualTo(String value) {
            addCriterion("prostateRisk4 =", value, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4NotEqualTo(String value) {
            addCriterion("prostateRisk4 <>", value, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4GreaterThan(String value) {
            addCriterion("prostateRisk4 >", value, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4GreaterThanOrEqualTo(String value) {
            addCriterion("prostateRisk4 >=", value, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4LessThan(String value) {
            addCriterion("prostateRisk4 <", value, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4LessThanOrEqualTo(String value) {
            addCriterion("prostateRisk4 <=", value, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4Like(String value) {
            addCriterion("prostateRisk4 like", value, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4NotLike(String value) {
            addCriterion("prostateRisk4 not like", value, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4In(List<String> values) {
            addCriterion("prostateRisk4 in", values, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4NotIn(List<String> values) {
            addCriterion("prostateRisk4 not in", values, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4Between(String value1, String value2) {
            addCriterion("prostateRisk4 between", value1, value2, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andProstaterisk4NotBetween(String value1, String value2) {
            addCriterion("prostateRisk4 not between", value1, value2, "prostaterisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1IsNull() {
            addCriterion("breastCancerRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1IsNotNull() {
            addCriterion("breastCancerRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1EqualTo(String value) {
            addCriterion("breastCancerRisk1 =", value, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1NotEqualTo(String value) {
            addCriterion("breastCancerRisk1 <>", value, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1GreaterThan(String value) {
            addCriterion("breastCancerRisk1 >", value, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1GreaterThanOrEqualTo(String value) {
            addCriterion("breastCancerRisk1 >=", value, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1LessThan(String value) {
            addCriterion("breastCancerRisk1 <", value, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1LessThanOrEqualTo(String value) {
            addCriterion("breastCancerRisk1 <=", value, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1Like(String value) {
            addCriterion("breastCancerRisk1 like", value, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1NotLike(String value) {
            addCriterion("breastCancerRisk1 not like", value, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1In(List<String> values) {
            addCriterion("breastCancerRisk1 in", values, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1NotIn(List<String> values) {
            addCriterion("breastCancerRisk1 not in", values, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1Between(String value1, String value2) {
            addCriterion("breastCancerRisk1 between", value1, value2, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk1NotBetween(String value1, String value2) {
            addCriterion("breastCancerRisk1 not between", value1, value2, "breastcancerrisk1");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2IsNull() {
            addCriterion("breastCancerRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2IsNotNull() {
            addCriterion("breastCancerRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2EqualTo(String value) {
            addCriterion("breastCancerRisk2 =", value, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2NotEqualTo(String value) {
            addCriterion("breastCancerRisk2 <>", value, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2GreaterThan(String value) {
            addCriterion("breastCancerRisk2 >", value, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2GreaterThanOrEqualTo(String value) {
            addCriterion("breastCancerRisk2 >=", value, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2LessThan(String value) {
            addCriterion("breastCancerRisk2 <", value, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2LessThanOrEqualTo(String value) {
            addCriterion("breastCancerRisk2 <=", value, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2Like(String value) {
            addCriterion("breastCancerRisk2 like", value, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2NotLike(String value) {
            addCriterion("breastCancerRisk2 not like", value, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2In(List<String> values) {
            addCriterion("breastCancerRisk2 in", values, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2NotIn(List<String> values) {
            addCriterion("breastCancerRisk2 not in", values, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2Between(String value1, String value2) {
            addCriterion("breastCancerRisk2 between", value1, value2, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk2NotBetween(String value1, String value2) {
            addCriterion("breastCancerRisk2 not between", value1, value2, "breastcancerrisk2");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3IsNull() {
            addCriterion("breastCancerRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3IsNotNull() {
            addCriterion("breastCancerRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3EqualTo(String value) {
            addCriterion("breastCancerRisk3 =", value, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3NotEqualTo(String value) {
            addCriterion("breastCancerRisk3 <>", value, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3GreaterThan(String value) {
            addCriterion("breastCancerRisk3 >", value, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3GreaterThanOrEqualTo(String value) {
            addCriterion("breastCancerRisk3 >=", value, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3LessThan(String value) {
            addCriterion("breastCancerRisk3 <", value, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3LessThanOrEqualTo(String value) {
            addCriterion("breastCancerRisk3 <=", value, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3Like(String value) {
            addCriterion("breastCancerRisk3 like", value, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3NotLike(String value) {
            addCriterion("breastCancerRisk3 not like", value, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3In(List<String> values) {
            addCriterion("breastCancerRisk3 in", values, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3NotIn(List<String> values) {
            addCriterion("breastCancerRisk3 not in", values, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3Between(String value1, String value2) {
            addCriterion("breastCancerRisk3 between", value1, value2, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk3NotBetween(String value1, String value2) {
            addCriterion("breastCancerRisk3 not between", value1, value2, "breastcancerrisk3");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4IsNull() {
            addCriterion("breastCancerRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4IsNotNull() {
            addCriterion("breastCancerRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4EqualTo(String value) {
            addCriterion("breastCancerRisk4 =", value, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4NotEqualTo(String value) {
            addCriterion("breastCancerRisk4 <>", value, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4GreaterThan(String value) {
            addCriterion("breastCancerRisk4 >", value, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4GreaterThanOrEqualTo(String value) {
            addCriterion("breastCancerRisk4 >=", value, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4LessThan(String value) {
            addCriterion("breastCancerRisk4 <", value, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4LessThanOrEqualTo(String value) {
            addCriterion("breastCancerRisk4 <=", value, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4Like(String value) {
            addCriterion("breastCancerRisk4 like", value, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4NotLike(String value) {
            addCriterion("breastCancerRisk4 not like", value, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4In(List<String> values) {
            addCriterion("breastCancerRisk4 in", values, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4NotIn(List<String> values) {
            addCriterion("breastCancerRisk4 not in", values, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4Between(String value1, String value2) {
            addCriterion("breastCancerRisk4 between", value1, value2, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andBreastcancerrisk4NotBetween(String value1, String value2) {
            addCriterion("breastCancerRisk4 not between", value1, value2, "breastcancerrisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1IsNull() {
            addCriterion("dyslipidemiaRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1IsNotNull() {
            addCriterion("dyslipidemiaRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1EqualTo(String value) {
            addCriterion("dyslipidemiaRisk1 =", value, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1NotEqualTo(String value) {
            addCriterion("dyslipidemiaRisk1 <>", value, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1GreaterThan(String value) {
            addCriterion("dyslipidemiaRisk1 >", value, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1GreaterThanOrEqualTo(String value) {
            addCriterion("dyslipidemiaRisk1 >=", value, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1LessThan(String value) {
            addCriterion("dyslipidemiaRisk1 <", value, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1LessThanOrEqualTo(String value) {
            addCriterion("dyslipidemiaRisk1 <=", value, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1Like(String value) {
            addCriterion("dyslipidemiaRisk1 like", value, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1NotLike(String value) {
            addCriterion("dyslipidemiaRisk1 not like", value, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1In(List<String> values) {
            addCriterion("dyslipidemiaRisk1 in", values, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1NotIn(List<String> values) {
            addCriterion("dyslipidemiaRisk1 not in", values, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1Between(String value1, String value2) {
            addCriterion("dyslipidemiaRisk1 between", value1, value2, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk1NotBetween(String value1, String value2) {
            addCriterion("dyslipidemiaRisk1 not between", value1, value2, "dyslipidemiarisk1");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2IsNull() {
            addCriterion("dyslipidemiaRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2IsNotNull() {
            addCriterion("dyslipidemiaRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2EqualTo(String value) {
            addCriterion("dyslipidemiaRisk2 =", value, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2NotEqualTo(String value) {
            addCriterion("dyslipidemiaRisk2 <>", value, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2GreaterThan(String value) {
            addCriterion("dyslipidemiaRisk2 >", value, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2GreaterThanOrEqualTo(String value) {
            addCriterion("dyslipidemiaRisk2 >=", value, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2LessThan(String value) {
            addCriterion("dyslipidemiaRisk2 <", value, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2LessThanOrEqualTo(String value) {
            addCriterion("dyslipidemiaRisk2 <=", value, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2Like(String value) {
            addCriterion("dyslipidemiaRisk2 like", value, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2NotLike(String value) {
            addCriterion("dyslipidemiaRisk2 not like", value, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2In(List<String> values) {
            addCriterion("dyslipidemiaRisk2 in", values, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2NotIn(List<String> values) {
            addCriterion("dyslipidemiaRisk2 not in", values, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2Between(String value1, String value2) {
            addCriterion("dyslipidemiaRisk2 between", value1, value2, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk2NotBetween(String value1, String value2) {
            addCriterion("dyslipidemiaRisk2 not between", value1, value2, "dyslipidemiarisk2");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3IsNull() {
            addCriterion("dyslipidemiaRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3IsNotNull() {
            addCriterion("dyslipidemiaRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3EqualTo(String value) {
            addCriterion("dyslipidemiaRisk3 =", value, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3NotEqualTo(String value) {
            addCriterion("dyslipidemiaRisk3 <>", value, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3GreaterThan(String value) {
            addCriterion("dyslipidemiaRisk3 >", value, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3GreaterThanOrEqualTo(String value) {
            addCriterion("dyslipidemiaRisk3 >=", value, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3LessThan(String value) {
            addCriterion("dyslipidemiaRisk3 <", value, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3LessThanOrEqualTo(String value) {
            addCriterion("dyslipidemiaRisk3 <=", value, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3Like(String value) {
            addCriterion("dyslipidemiaRisk3 like", value, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3NotLike(String value) {
            addCriterion("dyslipidemiaRisk3 not like", value, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3In(List<String> values) {
            addCriterion("dyslipidemiaRisk3 in", values, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3NotIn(List<String> values) {
            addCriterion("dyslipidemiaRisk3 not in", values, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3Between(String value1, String value2) {
            addCriterion("dyslipidemiaRisk3 between", value1, value2, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk3NotBetween(String value1, String value2) {
            addCriterion("dyslipidemiaRisk3 not between", value1, value2, "dyslipidemiarisk3");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4IsNull() {
            addCriterion("dyslipidemiaRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4IsNotNull() {
            addCriterion("dyslipidemiaRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4EqualTo(String value) {
            addCriterion("dyslipidemiaRisk4 =", value, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4NotEqualTo(String value) {
            addCriterion("dyslipidemiaRisk4 <>", value, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4GreaterThan(String value) {
            addCriterion("dyslipidemiaRisk4 >", value, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4GreaterThanOrEqualTo(String value) {
            addCriterion("dyslipidemiaRisk4 >=", value, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4LessThan(String value) {
            addCriterion("dyslipidemiaRisk4 <", value, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4LessThanOrEqualTo(String value) {
            addCriterion("dyslipidemiaRisk4 <=", value, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4Like(String value) {
            addCriterion("dyslipidemiaRisk4 like", value, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4NotLike(String value) {
            addCriterion("dyslipidemiaRisk4 not like", value, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4In(List<String> values) {
            addCriterion("dyslipidemiaRisk4 in", values, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4NotIn(List<String> values) {
            addCriterion("dyslipidemiaRisk4 not in", values, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4Between(String value1, String value2) {
            addCriterion("dyslipidemiaRisk4 between", value1, value2, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andDyslipidemiarisk4NotBetween(String value1, String value2) {
            addCriterion("dyslipidemiaRisk4 not between", value1, value2, "dyslipidemiarisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1IsNull() {
            addCriterion("osteoporosisRisk1 is null");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1IsNotNull() {
            addCriterion("osteoporosisRisk1 is not null");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1EqualTo(String value) {
            addCriterion("osteoporosisRisk1 =", value, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1NotEqualTo(String value) {
            addCriterion("osteoporosisRisk1 <>", value, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1GreaterThan(String value) {
            addCriterion("osteoporosisRisk1 >", value, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1GreaterThanOrEqualTo(String value) {
            addCriterion("osteoporosisRisk1 >=", value, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1LessThan(String value) {
            addCriterion("osteoporosisRisk1 <", value, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1LessThanOrEqualTo(String value) {
            addCriterion("osteoporosisRisk1 <=", value, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1Like(String value) {
            addCriterion("osteoporosisRisk1 like", value, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1NotLike(String value) {
            addCriterion("osteoporosisRisk1 not like", value, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1In(List<String> values) {
            addCriterion("osteoporosisRisk1 in", values, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1NotIn(List<String> values) {
            addCriterion("osteoporosisRisk1 not in", values, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1Between(String value1, String value2) {
            addCriterion("osteoporosisRisk1 between", value1, value2, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk1NotBetween(String value1, String value2) {
            addCriterion("osteoporosisRisk1 not between", value1, value2, "osteoporosisrisk1");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2IsNull() {
            addCriterion("osteoporosisRisk2 is null");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2IsNotNull() {
            addCriterion("osteoporosisRisk2 is not null");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2EqualTo(String value) {
            addCriterion("osteoporosisRisk2 =", value, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2NotEqualTo(String value) {
            addCriterion("osteoporosisRisk2 <>", value, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2GreaterThan(String value) {
            addCriterion("osteoporosisRisk2 >", value, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2GreaterThanOrEqualTo(String value) {
            addCriterion("osteoporosisRisk2 >=", value, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2LessThan(String value) {
            addCriterion("osteoporosisRisk2 <", value, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2LessThanOrEqualTo(String value) {
            addCriterion("osteoporosisRisk2 <=", value, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2Like(String value) {
            addCriterion("osteoporosisRisk2 like", value, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2NotLike(String value) {
            addCriterion("osteoporosisRisk2 not like", value, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2In(List<String> values) {
            addCriterion("osteoporosisRisk2 in", values, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2NotIn(List<String> values) {
            addCriterion("osteoporosisRisk2 not in", values, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2Between(String value1, String value2) {
            addCriterion("osteoporosisRisk2 between", value1, value2, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk2NotBetween(String value1, String value2) {
            addCriterion("osteoporosisRisk2 not between", value1, value2, "osteoporosisrisk2");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3IsNull() {
            addCriterion("osteoporosisRisk3 is null");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3IsNotNull() {
            addCriterion("osteoporosisRisk3 is not null");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3EqualTo(String value) {
            addCriterion("osteoporosisRisk3 =", value, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3NotEqualTo(String value) {
            addCriterion("osteoporosisRisk3 <>", value, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3GreaterThan(String value) {
            addCriterion("osteoporosisRisk3 >", value, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3GreaterThanOrEqualTo(String value) {
            addCriterion("osteoporosisRisk3 >=", value, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3LessThan(String value) {
            addCriterion("osteoporosisRisk3 <", value, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3LessThanOrEqualTo(String value) {
            addCriterion("osteoporosisRisk3 <=", value, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3Like(String value) {
            addCriterion("osteoporosisRisk3 like", value, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3NotLike(String value) {
            addCriterion("osteoporosisRisk3 not like", value, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3In(List<String> values) {
            addCriterion("osteoporosisRisk3 in", values, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3NotIn(List<String> values) {
            addCriterion("osteoporosisRisk3 not in", values, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3Between(String value1, String value2) {
            addCriterion("osteoporosisRisk3 between", value1, value2, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk3NotBetween(String value1, String value2) {
            addCriterion("osteoporosisRisk3 not between", value1, value2, "osteoporosisrisk3");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4IsNull() {
            addCriterion("osteoporosisRisk4 is null");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4IsNotNull() {
            addCriterion("osteoporosisRisk4 is not null");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4EqualTo(String value) {
            addCriterion("osteoporosisRisk4 =", value, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4NotEqualTo(String value) {
            addCriterion("osteoporosisRisk4 <>", value, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4GreaterThan(String value) {
            addCriterion("osteoporosisRisk4 >", value, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4GreaterThanOrEqualTo(String value) {
            addCriterion("osteoporosisRisk4 >=", value, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4LessThan(String value) {
            addCriterion("osteoporosisRisk4 <", value, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4LessThanOrEqualTo(String value) {
            addCriterion("osteoporosisRisk4 <=", value, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4Like(String value) {
            addCriterion("osteoporosisRisk4 like", value, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4NotLike(String value) {
            addCriterion("osteoporosisRisk4 not like", value, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4In(List<String> values) {
            addCriterion("osteoporosisRisk4 in", values, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4NotIn(List<String> values) {
            addCriterion("osteoporosisRisk4 not in", values, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4Between(String value1, String value2) {
            addCriterion("osteoporosisRisk4 between", value1, value2, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andOsteoporosisrisk4NotBetween(String value1, String value2) {
            addCriterion("osteoporosisRisk4 not between", value1, value2, "osteoporosisrisk4");
            return (Criteria) this;
        }

        public Criteria andCreationtimeIsNull() {
            addCriterion("creationTime is null");
            return (Criteria) this;
        }

        public Criteria andCreationtimeIsNotNull() {
            addCriterion("creationTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreationtimeEqualTo(String value) {
            addCriterion("creationTime =", value, "creationtime");
            return (Criteria) this;
        }

        public Criteria andCreationtimeNotEqualTo(String value) {
            addCriterion("creationTime <>", value, "creationtime");
            return (Criteria) this;
        }

        public Criteria andCreationtimeGreaterThan(String value) {
            addCriterion("creationTime >", value, "creationtime");
            return (Criteria) this;
        }

        public Criteria andCreationtimeGreaterThanOrEqualTo(String value) {
            addCriterion("creationTime >=", value, "creationtime");
            return (Criteria) this;
        }

        public Criteria andCreationtimeLessThan(String value) {
            addCriterion("creationTime <", value, "creationtime");
            return (Criteria) this;
        }

        public Criteria andCreationtimeLessThanOrEqualTo(String value) {
            addCriterion("creationTime <=", value, "creationtime");
            return (Criteria) this;
        }

        public Criteria andCreationtimeLike(String value) {
            addCriterion("creationTime like", value, "creationtime");
            return (Criteria) this;
        }

        public Criteria andCreationtimeNotLike(String value) {
            addCriterion("creationTime not like", value, "creationtime");
            return (Criteria) this;
        }

        public Criteria andCreationtimeIn(List<String> values) {
            addCriterion("creationTime in", values, "creationtime");
            return (Criteria) this;
        }

        public Criteria andCreationtimeNotIn(List<String> values) {
            addCriterion("creationTime not in", values, "creationtime");
            return (Criteria) this;
        }

        public Criteria andCreationtimeBetween(String value1, String value2) {
            addCriterion("creationTime between", value1, value2, "creationtime");
            return (Criteria) this;
        }

        public Criteria andCreationtimeNotBetween(String value1, String value2) {
            addCriterion("creationTime not between", value1, value2, "creationtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeIsNull() {
            addCriterion("screeningTime is null");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeIsNotNull() {
            addCriterion("screeningTime is not null");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeEqualTo(String value) {
            addCriterion("screeningTime =", value, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeNotEqualTo(String value) {
            addCriterion("screeningTime <>", value, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeGreaterThan(String value) {
            addCriterion("screeningTime >", value, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeGreaterThanOrEqualTo(String value) {
            addCriterion("screeningTime >=", value, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeLessThan(String value) {
            addCriterion("screeningTime <", value, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeLessThanOrEqualTo(String value) {
            addCriterion("screeningTime <=", value, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeLike(String value) {
            addCriterion("screeningTime like", value, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeNotLike(String value) {
            addCriterion("screeningTime not like", value, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeIn(List<String> values) {
            addCriterion("screeningTime in", values, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeNotIn(List<String> values) {
            addCriterion("screeningTime not in", values, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeBetween(String value1, String value2) {
            addCriterion("screeningTime between", value1, value2, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andScreeningtimeNotBetween(String value1, String value2) {
            addCriterion("screeningTime not between", value1, value2, "screeningtime");
            return (Criteria) this;
        }

        public Criteria andUsercodeIsNull() {
            addCriterion("usercode is null");
            return (Criteria) this;
        }

        public Criteria andUsercodeIsNotNull() {
            addCriterion("usercode is not null");
            return (Criteria) this;
        }

        public Criteria andUsercodeEqualTo(String value) {
            addCriterion("usercode =", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeNotEqualTo(String value) {
            addCriterion("usercode <>", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeGreaterThan(String value) {
            addCriterion("usercode >", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeGreaterThanOrEqualTo(String value) {
            addCriterion("usercode >=", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeLessThan(String value) {
            addCriterion("usercode <", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeLessThanOrEqualTo(String value) {
            addCriterion("usercode <=", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeLike(String value) {
            addCriterion("usercode like", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeNotLike(String value) {
            addCriterion("usercode not like", value, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeIn(List<String> values) {
            addCriterion("usercode in", values, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeNotIn(List<String> values) {
            addCriterion("usercode not in", values, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeBetween(String value1, String value2) {
            addCriterion("usercode between", value1, value2, "usercode");
            return (Criteria) this;
        }

        public Criteria andUsercodeNotBetween(String value1, String value2) {
            addCriterion("usercode not between", value1, value2, "usercode");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}