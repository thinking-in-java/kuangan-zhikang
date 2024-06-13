package com.yihe.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YhDiseaseAssessReportExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public YhDiseaseAssessReportExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNull() {
            addCriterion("user_code is null");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNotNull() {
            addCriterion("user_code is not null");
            return (Criteria) this;
        }

        public Criteria andUserCodeEqualTo(String value) {
            addCriterion("user_code =", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotEqualTo(String value) {
            addCriterion("user_code <>", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThan(String value) {
            addCriterion("user_code >", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThanOrEqualTo(String value) {
            addCriterion("user_code >=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThan(String value) {
            addCriterion("user_code <", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThanOrEqualTo(String value) {
            addCriterion("user_code <=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLike(String value) {
            addCriterion("user_code like", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotLike(String value) {
            addCriterion("user_code not like", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeIn(List<String> values) {
            addCriterion("user_code in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotIn(List<String> values) {
            addCriterion("user_code not in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeBetween(String value1, String value2) {
            addCriterion("user_code between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotBetween(String value1, String value2) {
            addCriterion("user_code not between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumIsNull() {
            addCriterion("result_serialNum is null");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumIsNotNull() {
            addCriterion("result_serialNum is not null");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumEqualTo(String value) {
            addCriterion("result_serialNum =", value, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumNotEqualTo(String value) {
            addCriterion("result_serialNum <>", value, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumGreaterThan(String value) {
            addCriterion("result_serialNum >", value, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumGreaterThanOrEqualTo(String value) {
            addCriterion("result_serialNum >=", value, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumLessThan(String value) {
            addCriterion("result_serialNum <", value, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumLessThanOrEqualTo(String value) {
            addCriterion("result_serialNum <=", value, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumLike(String value) {
            addCriterion("result_serialNum like", value, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumNotLike(String value) {
            addCriterion("result_serialNum not like", value, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumIn(List<String> values) {
            addCriterion("result_serialNum in", values, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumNotIn(List<String> values) {
            addCriterion("result_serialNum not in", values, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumBetween(String value1, String value2) {
            addCriterion("result_serialNum between", value1, value2, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andResultSerialnumNotBetween(String value1, String value2) {
            addCriterion("result_serialNum not between", value1, value2, "resultSerialnum");
            return (Criteria) this;
        }

        public Criteria andQtimeIsNull() {
            addCriterion("qtime is null");
            return (Criteria) this;
        }

        public Criteria andQtimeIsNotNull() {
            addCriterion("qtime is not null");
            return (Criteria) this;
        }

        public Criteria andQtimeEqualTo(Date value) {
            addCriterion("qtime =", value, "qtime");
            return (Criteria) this;
        }

        public Criteria andQtimeNotEqualTo(Date value) {
            addCriterion("qtime <>", value, "qtime");
            return (Criteria) this;
        }

        public Criteria andQtimeGreaterThan(Date value) {
            addCriterion("qtime >", value, "qtime");
            return (Criteria) this;
        }

        public Criteria andQtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("qtime >=", value, "qtime");
            return (Criteria) this;
        }

        public Criteria andQtimeLessThan(Date value) {
            addCriterion("qtime <", value, "qtime");
            return (Criteria) this;
        }

        public Criteria andQtimeLessThanOrEqualTo(Date value) {
            addCriterion("qtime <=", value, "qtime");
            return (Criteria) this;
        }

        public Criteria andQtimeIn(List<Date> values) {
            addCriterion("qtime in", values, "qtime");
            return (Criteria) this;
        }

        public Criteria andQtimeNotIn(List<Date> values) {
            addCriterion("qtime not in", values, "qtime");
            return (Criteria) this;
        }

        public Criteria andQtimeBetween(Date value1, Date value2) {
            addCriterion("qtime between", value1, value2, "qtime");
            return (Criteria) this;
        }

        public Criteria andQtimeNotBetween(Date value1, Date value2) {
            addCriterion("qtime not between", value1, value2, "qtime");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumIsNull() {
            addCriterion("request_serialNum is null");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumIsNotNull() {
            addCriterion("request_serialNum is not null");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumEqualTo(String value) {
            addCriterion("request_serialNum =", value, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumNotEqualTo(String value) {
            addCriterion("request_serialNum <>", value, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumGreaterThan(String value) {
            addCriterion("request_serialNum >", value, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumGreaterThanOrEqualTo(String value) {
            addCriterion("request_serialNum >=", value, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumLessThan(String value) {
            addCriterion("request_serialNum <", value, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumLessThanOrEqualTo(String value) {
            addCriterion("request_serialNum <=", value, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumLike(String value) {
            addCriterion("request_serialNum like", value, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumNotLike(String value) {
            addCriterion("request_serialNum not like", value, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumIn(List<String> values) {
            addCriterion("request_serialNum in", values, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumNotIn(List<String> values) {
            addCriterion("request_serialNum not in", values, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumBetween(String value1, String value2) {
            addCriterion("request_serialNum between", value1, value2, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andRequestSerialnumNotBetween(String value1, String value2) {
            addCriterion("request_serialNum not between", value1, value2, "requestSerialnum");
            return (Criteria) this;
        }

        public Criteria andDiseasidIsNull() {
            addCriterion("diseasId is null");
            return (Criteria) this;
        }

        public Criteria andDiseasidIsNotNull() {
            addCriterion("diseasId is not null");
            return (Criteria) this;
        }

        public Criteria andDiseasidEqualTo(Integer value) {
            addCriterion("diseasId =", value, "diseasid");
            return (Criteria) this;
        }

        public Criteria andDiseasidNotEqualTo(Integer value) {
            addCriterion("diseasId <>", value, "diseasid");
            return (Criteria) this;
        }

        public Criteria andDiseasidGreaterThan(Integer value) {
            addCriterion("diseasId >", value, "diseasid");
            return (Criteria) this;
        }

        public Criteria andDiseasidGreaterThanOrEqualTo(Integer value) {
            addCriterion("diseasId >=", value, "diseasid");
            return (Criteria) this;
        }

        public Criteria andDiseasidLessThan(Integer value) {
            addCriterion("diseasId <", value, "diseasid");
            return (Criteria) this;
        }

        public Criteria andDiseasidLessThanOrEqualTo(Integer value) {
            addCriterion("diseasId <=", value, "diseasid");
            return (Criteria) this;
        }

        public Criteria andDiseasidIn(List<Integer> values) {
            addCriterion("diseasId in", values, "diseasid");
            return (Criteria) this;
        }

        public Criteria andDiseasidNotIn(List<Integer> values) {
            addCriterion("diseasId not in", values, "diseasid");
            return (Criteria) this;
        }

        public Criteria andDiseasidBetween(Integer value1, Integer value2) {
            addCriterion("diseasId between", value1, value2, "diseasid");
            return (Criteria) this;
        }

        public Criteria andDiseasidNotBetween(Integer value1, Integer value2) {
            addCriterion("diseasId not between", value1, value2, "diseasid");
            return (Criteria) this;
        }

        public Criteria andRiskLevelIsNull() {
            addCriterion("risk_level is null");
            return (Criteria) this;
        }

        public Criteria andRiskLevelIsNotNull() {
            addCriterion("risk_level is not null");
            return (Criteria) this;
        }

        public Criteria andRiskLevelEqualTo(String value) {
            addCriterion("risk_level =", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelNotEqualTo(String value) {
            addCriterion("risk_level <>", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelGreaterThan(String value) {
            addCriterion("risk_level >", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelGreaterThanOrEqualTo(String value) {
            addCriterion("risk_level >=", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelLessThan(String value) {
            addCriterion("risk_level <", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelLessThanOrEqualTo(String value) {
            addCriterion("risk_level <=", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelLike(String value) {
            addCriterion("risk_level like", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelNotLike(String value) {
            addCriterion("risk_level not like", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelIn(List<String> values) {
            addCriterion("risk_level in", values, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelNotIn(List<String> values) {
            addCriterion("risk_level not in", values, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelBetween(String value1, String value2) {
            addCriterion("risk_level between", value1, value2, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelNotBetween(String value1, String value2) {
            addCriterion("risk_level not between", value1, value2, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskDescIsNull() {
            addCriterion("risk_desc is null");
            return (Criteria) this;
        }

        public Criteria andRiskDescIsNotNull() {
            addCriterion("risk_desc is not null");
            return (Criteria) this;
        }

        public Criteria andRiskDescEqualTo(String value) {
            addCriterion("risk_desc =", value, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andRiskDescNotEqualTo(String value) {
            addCriterion("risk_desc <>", value, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andRiskDescGreaterThan(String value) {
            addCriterion("risk_desc >", value, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andRiskDescGreaterThanOrEqualTo(String value) {
            addCriterion("risk_desc >=", value, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andRiskDescLessThan(String value) {
            addCriterion("risk_desc <", value, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andRiskDescLessThanOrEqualTo(String value) {
            addCriterion("risk_desc <=", value, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andRiskDescLike(String value) {
            addCriterion("risk_desc like", value, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andRiskDescNotLike(String value) {
            addCriterion("risk_desc not like", value, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andRiskDescIn(List<String> values) {
            addCriterion("risk_desc in", values, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andRiskDescNotIn(List<String> values) {
            addCriterion("risk_desc not in", values, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andRiskDescBetween(String value1, String value2) {
            addCriterion("risk_desc between", value1, value2, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andRiskDescNotBetween(String value1, String value2) {
            addCriterion("risk_desc not between", value1, value2, "riskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescIsNull() {
            addCriterion("now_risk_desc is null");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescIsNotNull() {
            addCriterion("now_risk_desc is not null");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescEqualTo(String value) {
            addCriterion("now_risk_desc =", value, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescNotEqualTo(String value) {
            addCriterion("now_risk_desc <>", value, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescGreaterThan(String value) {
            addCriterion("now_risk_desc >", value, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescGreaterThanOrEqualTo(String value) {
            addCriterion("now_risk_desc >=", value, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescLessThan(String value) {
            addCriterion("now_risk_desc <", value, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescLessThanOrEqualTo(String value) {
            addCriterion("now_risk_desc <=", value, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescLike(String value) {
            addCriterion("now_risk_desc like", value, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescNotLike(String value) {
            addCriterion("now_risk_desc not like", value, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescIn(List<String> values) {
            addCriterion("now_risk_desc in", values, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescNotIn(List<String> values) {
            addCriterion("now_risk_desc not in", values, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescBetween(String value1, String value2) {
            addCriterion("now_risk_desc between", value1, value2, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskDescNotBetween(String value1, String value2) {
            addCriterion("now_risk_desc not between", value1, value2, "nowRiskDesc");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueIsNull() {
            addCriterion("now_risk_value is null");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueIsNotNull() {
            addCriterion("now_risk_value is not null");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueEqualTo(String value) {
            addCriterion("now_risk_value =", value, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueNotEqualTo(String value) {
            addCriterion("now_risk_value <>", value, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueGreaterThan(String value) {
            addCriterion("now_risk_value >", value, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueGreaterThanOrEqualTo(String value) {
            addCriterion("now_risk_value >=", value, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueLessThan(String value) {
            addCriterion("now_risk_value <", value, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueLessThanOrEqualTo(String value) {
            addCriterion("now_risk_value <=", value, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueLike(String value) {
            addCriterion("now_risk_value like", value, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueNotLike(String value) {
            addCriterion("now_risk_value not like", value, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueIn(List<String> values) {
            addCriterion("now_risk_value in", values, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueNotIn(List<String> values) {
            addCriterion("now_risk_value not in", values, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueBetween(String value1, String value2) {
            addCriterion("now_risk_value between", value1, value2, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskValueNotBetween(String value1, String value2) {
            addCriterion("now_risk_value not between", value1, value2, "nowRiskValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelIsNull() {
            addCriterion("now_risk_level is null");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelIsNotNull() {
            addCriterion("now_risk_level is not null");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelEqualTo(String value) {
            addCriterion("now_risk_level =", value, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelNotEqualTo(String value) {
            addCriterion("now_risk_level <>", value, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelGreaterThan(String value) {
            addCriterion("now_risk_level >", value, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelGreaterThanOrEqualTo(String value) {
            addCriterion("now_risk_level >=", value, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelLessThan(String value) {
            addCriterion("now_risk_level <", value, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelLessThanOrEqualTo(String value) {
            addCriterion("now_risk_level <=", value, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelLike(String value) {
            addCriterion("now_risk_level like", value, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelNotLike(String value) {
            addCriterion("now_risk_level not like", value, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelIn(List<String> values) {
            addCriterion("now_risk_level in", values, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelNotIn(List<String> values) {
            addCriterion("now_risk_level not in", values, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelBetween(String value1, String value2) {
            addCriterion("now_risk_level between", value1, value2, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLevelNotBetween(String value1, String value2) {
            addCriterion("now_risk_level not between", value1, value2, "nowRiskLevel");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueIsNull() {
            addCriterion("now_risk_last_value is null");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueIsNotNull() {
            addCriterion("now_risk_last_value is not null");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueEqualTo(String value) {
            addCriterion("now_risk_last_value =", value, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueNotEqualTo(String value) {
            addCriterion("now_risk_last_value <>", value, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueGreaterThan(String value) {
            addCriterion("now_risk_last_value >", value, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueGreaterThanOrEqualTo(String value) {
            addCriterion("now_risk_last_value >=", value, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueLessThan(String value) {
            addCriterion("now_risk_last_value <", value, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueLessThanOrEqualTo(String value) {
            addCriterion("now_risk_last_value <=", value, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueLike(String value) {
            addCriterion("now_risk_last_value like", value, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueNotLike(String value) {
            addCriterion("now_risk_last_value not like", value, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueIn(List<String> values) {
            addCriterion("now_risk_last_value in", values, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueNotIn(List<String> values) {
            addCriterion("now_risk_last_value not in", values, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueBetween(String value1, String value2) {
            addCriterion("now_risk_last_value between", value1, value2, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andNowRiskLastValueNotBetween(String value1, String value2) {
            addCriterion("now_risk_last_value not between", value1, value2, "nowRiskLastValue");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelIsNull() {
            addCriterion("ideal_risk_level is null");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelIsNotNull() {
            addCriterion("ideal_risk_level is not null");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelEqualTo(String value) {
            addCriterion("ideal_risk_level =", value, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelNotEqualTo(String value) {
            addCriterion("ideal_risk_level <>", value, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelGreaterThan(String value) {
            addCriterion("ideal_risk_level >", value, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelGreaterThanOrEqualTo(String value) {
            addCriterion("ideal_risk_level >=", value, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelLessThan(String value) {
            addCriterion("ideal_risk_level <", value, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelLessThanOrEqualTo(String value) {
            addCriterion("ideal_risk_level <=", value, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelLike(String value) {
            addCriterion("ideal_risk_level like", value, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelNotLike(String value) {
            addCriterion("ideal_risk_level not like", value, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelIn(List<String> values) {
            addCriterion("ideal_risk_level in", values, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelNotIn(List<String> values) {
            addCriterion("ideal_risk_level not in", values, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelBetween(String value1, String value2) {
            addCriterion("ideal_risk_level between", value1, value2, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskLevelNotBetween(String value1, String value2) {
            addCriterion("ideal_risk_level not between", value1, value2, "idealRiskLevel");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescIsNull() {
            addCriterion("ideal_risk_desc is null");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescIsNotNull() {
            addCriterion("ideal_risk_desc is not null");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescEqualTo(String value) {
            addCriterion("ideal_risk_desc =", value, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescNotEqualTo(String value) {
            addCriterion("ideal_risk_desc <>", value, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescGreaterThan(String value) {
            addCriterion("ideal_risk_desc >", value, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescGreaterThanOrEqualTo(String value) {
            addCriterion("ideal_risk_desc >=", value, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescLessThan(String value) {
            addCriterion("ideal_risk_desc <", value, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescLessThanOrEqualTo(String value) {
            addCriterion("ideal_risk_desc <=", value, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescLike(String value) {
            addCriterion("ideal_risk_desc like", value, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescNotLike(String value) {
            addCriterion("ideal_risk_desc not like", value, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescIn(List<String> values) {
            addCriterion("ideal_risk_desc in", values, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescNotIn(List<String> values) {
            addCriterion("ideal_risk_desc not in", values, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescBetween(String value1, String value2) {
            addCriterion("ideal_risk_desc between", value1, value2, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andIdealRiskDescNotBetween(String value1, String value2) {
            addCriterion("ideal_risk_desc not between", value1, value2, "idealRiskDesc");
            return (Criteria) this;
        }

        public Criteria andLastTimeIsNull() {
            addCriterion("last_time is null");
            return (Criteria) this;
        }

        public Criteria andLastTimeIsNotNull() {
            addCriterion("last_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastTimeEqualTo(String value) {
            addCriterion("last_time =", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotEqualTo(String value) {
            addCriterion("last_time <>", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeGreaterThan(String value) {
            addCriterion("last_time >", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeGreaterThanOrEqualTo(String value) {
            addCriterion("last_time >=", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeLessThan(String value) {
            addCriterion("last_time <", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeLessThanOrEqualTo(String value) {
            addCriterion("last_time <=", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeLike(String value) {
            addCriterion("last_time like", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotLike(String value) {
            addCriterion("last_time not like", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeIn(List<String> values) {
            addCriterion("last_time in", values, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotIn(List<String> values) {
            addCriterion("last_time not in", values, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeBetween(String value1, String value2) {
            addCriterion("last_time between", value1, value2, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotBetween(String value1, String value2) {
            addCriterion("last_time not between", value1, value2, "lastTime");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20IsNull() {
            addCriterion("age_forecast_r20 is null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20IsNotNull() {
            addCriterion("age_forecast_r20 is not null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20EqualTo(String value) {
            addCriterion("age_forecast_r20 =", value, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20NotEqualTo(String value) {
            addCriterion("age_forecast_r20 <>", value, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20GreaterThan(String value) {
            addCriterion("age_forecast_r20 >", value, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20GreaterThanOrEqualTo(String value) {
            addCriterion("age_forecast_r20 >=", value, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20LessThan(String value) {
            addCriterion("age_forecast_r20 <", value, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20LessThanOrEqualTo(String value) {
            addCriterion("age_forecast_r20 <=", value, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20Like(String value) {
            addCriterion("age_forecast_r20 like", value, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20NotLike(String value) {
            addCriterion("age_forecast_r20 not like", value, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20In(List<String> values) {
            addCriterion("age_forecast_r20 in", values, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20NotIn(List<String> values) {
            addCriterion("age_forecast_r20 not in", values, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20Between(String value1, String value2) {
            addCriterion("age_forecast_r20 between", value1, value2, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR20NotBetween(String value1, String value2) {
            addCriterion("age_forecast_r20 not between", value1, value2, "ageForecastR20");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30IsNull() {
            addCriterion("age_forecast_r30 is null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30IsNotNull() {
            addCriterion("age_forecast_r30 is not null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30EqualTo(String value) {
            addCriterion("age_forecast_r30 =", value, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30NotEqualTo(String value) {
            addCriterion("age_forecast_r30 <>", value, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30GreaterThan(String value) {
            addCriterion("age_forecast_r30 >", value, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30GreaterThanOrEqualTo(String value) {
            addCriterion("age_forecast_r30 >=", value, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30LessThan(String value) {
            addCriterion("age_forecast_r30 <", value, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30LessThanOrEqualTo(String value) {
            addCriterion("age_forecast_r30 <=", value, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30Like(String value) {
            addCriterion("age_forecast_r30 like", value, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30NotLike(String value) {
            addCriterion("age_forecast_r30 not like", value, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30In(List<String> values) {
            addCriterion("age_forecast_r30 in", values, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30NotIn(List<String> values) {
            addCriterion("age_forecast_r30 not in", values, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30Between(String value1, String value2) {
            addCriterion("age_forecast_r30 between", value1, value2, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR30NotBetween(String value1, String value2) {
            addCriterion("age_forecast_r30 not between", value1, value2, "ageForecastR30");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40IsNull() {
            addCriterion("age_forecast_r40 is null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40IsNotNull() {
            addCriterion("age_forecast_r40 is not null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40EqualTo(String value) {
            addCriterion("age_forecast_r40 =", value, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40NotEqualTo(String value) {
            addCriterion("age_forecast_r40 <>", value, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40GreaterThan(String value) {
            addCriterion("age_forecast_r40 >", value, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40GreaterThanOrEqualTo(String value) {
            addCriterion("age_forecast_r40 >=", value, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40LessThan(String value) {
            addCriterion("age_forecast_r40 <", value, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40LessThanOrEqualTo(String value) {
            addCriterion("age_forecast_r40 <=", value, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40Like(String value) {
            addCriterion("age_forecast_r40 like", value, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40NotLike(String value) {
            addCriterion("age_forecast_r40 not like", value, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40In(List<String> values) {
            addCriterion("age_forecast_r40 in", values, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40NotIn(List<String> values) {
            addCriterion("age_forecast_r40 not in", values, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40Between(String value1, String value2) {
            addCriterion("age_forecast_r40 between", value1, value2, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR40NotBetween(String value1, String value2) {
            addCriterion("age_forecast_r40 not between", value1, value2, "ageForecastR40");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50IsNull() {
            addCriterion("age_forecast_r50 is null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50IsNotNull() {
            addCriterion("age_forecast_r50 is not null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50EqualTo(String value) {
            addCriterion("age_forecast_r50 =", value, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50NotEqualTo(String value) {
            addCriterion("age_forecast_r50 <>", value, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50GreaterThan(String value) {
            addCriterion("age_forecast_r50 >", value, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50GreaterThanOrEqualTo(String value) {
            addCriterion("age_forecast_r50 >=", value, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50LessThan(String value) {
            addCriterion("age_forecast_r50 <", value, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50LessThanOrEqualTo(String value) {
            addCriterion("age_forecast_r50 <=", value, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50Like(String value) {
            addCriterion("age_forecast_r50 like", value, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50NotLike(String value) {
            addCriterion("age_forecast_r50 not like", value, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50In(List<String> values) {
            addCriterion("age_forecast_r50 in", values, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50NotIn(List<String> values) {
            addCriterion("age_forecast_r50 not in", values, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50Between(String value1, String value2) {
            addCriterion("age_forecast_r50 between", value1, value2, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR50NotBetween(String value1, String value2) {
            addCriterion("age_forecast_r50 not between", value1, value2, "ageForecastR50");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60IsNull() {
            addCriterion("age_forecast_r60 is null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60IsNotNull() {
            addCriterion("age_forecast_r60 is not null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60EqualTo(String value) {
            addCriterion("age_forecast_r60 =", value, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60NotEqualTo(String value) {
            addCriterion("age_forecast_r60 <>", value, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60GreaterThan(String value) {
            addCriterion("age_forecast_r60 >", value, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60GreaterThanOrEqualTo(String value) {
            addCriterion("age_forecast_r60 >=", value, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60LessThan(String value) {
            addCriterion("age_forecast_r60 <", value, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60LessThanOrEqualTo(String value) {
            addCriterion("age_forecast_r60 <=", value, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60Like(String value) {
            addCriterion("age_forecast_r60 like", value, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60NotLike(String value) {
            addCriterion("age_forecast_r60 not like", value, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60In(List<String> values) {
            addCriterion("age_forecast_r60 in", values, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60NotIn(List<String> values) {
            addCriterion("age_forecast_r60 not in", values, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60Between(String value1, String value2) {
            addCriterion("age_forecast_r60 between", value1, value2, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR60NotBetween(String value1, String value2) {
            addCriterion("age_forecast_r60 not between", value1, value2, "ageForecastR60");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70IsNull() {
            addCriterion("age_forecast_r70 is null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70IsNotNull() {
            addCriterion("age_forecast_r70 is not null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70EqualTo(String value) {
            addCriterion("age_forecast_r70 =", value, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70NotEqualTo(String value) {
            addCriterion("age_forecast_r70 <>", value, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70GreaterThan(String value) {
            addCriterion("age_forecast_r70 >", value, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70GreaterThanOrEqualTo(String value) {
            addCriterion("age_forecast_r70 >=", value, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70LessThan(String value) {
            addCriterion("age_forecast_r70 <", value, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70LessThanOrEqualTo(String value) {
            addCriterion("age_forecast_r70 <=", value, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70Like(String value) {
            addCriterion("age_forecast_r70 like", value, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70NotLike(String value) {
            addCriterion("age_forecast_r70 not like", value, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70In(List<String> values) {
            addCriterion("age_forecast_r70 in", values, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70NotIn(List<String> values) {
            addCriterion("age_forecast_r70 not in", values, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70Between(String value1, String value2) {
            addCriterion("age_forecast_r70 between", value1, value2, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR70NotBetween(String value1, String value2) {
            addCriterion("age_forecast_r70 not between", value1, value2, "ageForecastR70");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80IsNull() {
            addCriterion("age_forecast_r80 is null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80IsNotNull() {
            addCriterion("age_forecast_r80 is not null");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80EqualTo(String value) {
            addCriterion("age_forecast_r80 =", value, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80NotEqualTo(String value) {
            addCriterion("age_forecast_r80 <>", value, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80GreaterThan(String value) {
            addCriterion("age_forecast_r80 >", value, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80GreaterThanOrEqualTo(String value) {
            addCriterion("age_forecast_r80 >=", value, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80LessThan(String value) {
            addCriterion("age_forecast_r80 <", value, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80LessThanOrEqualTo(String value) {
            addCriterion("age_forecast_r80 <=", value, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80Like(String value) {
            addCriterion("age_forecast_r80 like", value, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80NotLike(String value) {
            addCriterion("age_forecast_r80 not like", value, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80In(List<String> values) {
            addCriterion("age_forecast_r80 in", values, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80NotIn(List<String> values) {
            addCriterion("age_forecast_r80 not in", values, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80Between(String value1, String value2) {
            addCriterion("age_forecast_r80 between", value1, value2, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeForecastR80NotBetween(String value1, String value2) {
            addCriterion("age_forecast_r80 not between", value1, value2, "ageForecastR80");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpIsNull() {
            addCriterion("age_op_forecast_r20_op is null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpIsNotNull() {
            addCriterion("age_op_forecast_r20_op is not null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpEqualTo(String value) {
            addCriterion("age_op_forecast_r20_op =", value, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpNotEqualTo(String value) {
            addCriterion("age_op_forecast_r20_op <>", value, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpGreaterThan(String value) {
            addCriterion("age_op_forecast_r20_op >", value, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpGreaterThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r20_op >=", value, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpLessThan(String value) {
            addCriterion("age_op_forecast_r20_op <", value, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpLessThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r20_op <=", value, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpLike(String value) {
            addCriterion("age_op_forecast_r20_op like", value, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpNotLike(String value) {
            addCriterion("age_op_forecast_r20_op not like", value, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpIn(List<String> values) {
            addCriterion("age_op_forecast_r20_op in", values, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpNotIn(List<String> values) {
            addCriterion("age_op_forecast_r20_op not in", values, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r20_op between", value1, value2, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR20OpNotBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r20_op not between", value1, value2, "ageOpForecastR20Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpIsNull() {
            addCriterion("age_op_forecast_r30_op is null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpIsNotNull() {
            addCriterion("age_op_forecast_r30_op is not null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpEqualTo(String value) {
            addCriterion("age_op_forecast_r30_op =", value, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpNotEqualTo(String value) {
            addCriterion("age_op_forecast_r30_op <>", value, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpGreaterThan(String value) {
            addCriterion("age_op_forecast_r30_op >", value, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpGreaterThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r30_op >=", value, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpLessThan(String value) {
            addCriterion("age_op_forecast_r30_op <", value, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpLessThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r30_op <=", value, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpLike(String value) {
            addCriterion("age_op_forecast_r30_op like", value, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpNotLike(String value) {
            addCriterion("age_op_forecast_r30_op not like", value, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpIn(List<String> values) {
            addCriterion("age_op_forecast_r30_op in", values, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpNotIn(List<String> values) {
            addCriterion("age_op_forecast_r30_op not in", values, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r30_op between", value1, value2, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR30OpNotBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r30_op not between", value1, value2, "ageOpForecastR30Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpIsNull() {
            addCriterion("age_op_forecast_r40_op is null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpIsNotNull() {
            addCriterion("age_op_forecast_r40_op is not null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpEqualTo(String value) {
            addCriterion("age_op_forecast_r40_op =", value, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpNotEqualTo(String value) {
            addCriterion("age_op_forecast_r40_op <>", value, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpGreaterThan(String value) {
            addCriterion("age_op_forecast_r40_op >", value, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpGreaterThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r40_op >=", value, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpLessThan(String value) {
            addCriterion("age_op_forecast_r40_op <", value, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpLessThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r40_op <=", value, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpLike(String value) {
            addCriterion("age_op_forecast_r40_op like", value, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpNotLike(String value) {
            addCriterion("age_op_forecast_r40_op not like", value, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpIn(List<String> values) {
            addCriterion("age_op_forecast_r40_op in", values, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpNotIn(List<String> values) {
            addCriterion("age_op_forecast_r40_op not in", values, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r40_op between", value1, value2, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR40OpNotBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r40_op not between", value1, value2, "ageOpForecastR40Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpIsNull() {
            addCriterion("age_op_forecast_r50_op is null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpIsNotNull() {
            addCriterion("age_op_forecast_r50_op is not null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpEqualTo(String value) {
            addCriterion("age_op_forecast_r50_op =", value, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpNotEqualTo(String value) {
            addCriterion("age_op_forecast_r50_op <>", value, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpGreaterThan(String value) {
            addCriterion("age_op_forecast_r50_op >", value, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpGreaterThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r50_op >=", value, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpLessThan(String value) {
            addCriterion("age_op_forecast_r50_op <", value, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpLessThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r50_op <=", value, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpLike(String value) {
            addCriterion("age_op_forecast_r50_op like", value, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpNotLike(String value) {
            addCriterion("age_op_forecast_r50_op not like", value, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpIn(List<String> values) {
            addCriterion("age_op_forecast_r50_op in", values, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpNotIn(List<String> values) {
            addCriterion("age_op_forecast_r50_op not in", values, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r50_op between", value1, value2, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR50OpNotBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r50_op not between", value1, value2, "ageOpForecastR50Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpIsNull() {
            addCriterion("age_op_forecast_r60_op is null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpIsNotNull() {
            addCriterion("age_op_forecast_r60_op is not null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpEqualTo(String value) {
            addCriterion("age_op_forecast_r60_op =", value, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpNotEqualTo(String value) {
            addCriterion("age_op_forecast_r60_op <>", value, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpGreaterThan(String value) {
            addCriterion("age_op_forecast_r60_op >", value, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpGreaterThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r60_op >=", value, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpLessThan(String value) {
            addCriterion("age_op_forecast_r60_op <", value, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpLessThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r60_op <=", value, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpLike(String value) {
            addCriterion("age_op_forecast_r60_op like", value, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpNotLike(String value) {
            addCriterion("age_op_forecast_r60_op not like", value, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpIn(List<String> values) {
            addCriterion("age_op_forecast_r60_op in", values, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpNotIn(List<String> values) {
            addCriterion("age_op_forecast_r60_op not in", values, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r60_op between", value1, value2, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR60OpNotBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r60_op not between", value1, value2, "ageOpForecastR60Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpIsNull() {
            addCriterion("age_op_forecast_r70_op is null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpIsNotNull() {
            addCriterion("age_op_forecast_r70_op is not null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpEqualTo(String value) {
            addCriterion("age_op_forecast_r70_op =", value, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpNotEqualTo(String value) {
            addCriterion("age_op_forecast_r70_op <>", value, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpGreaterThan(String value) {
            addCriterion("age_op_forecast_r70_op >", value, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpGreaterThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r70_op >=", value, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpLessThan(String value) {
            addCriterion("age_op_forecast_r70_op <", value, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpLessThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r70_op <=", value, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpLike(String value) {
            addCriterion("age_op_forecast_r70_op like", value, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpNotLike(String value) {
            addCriterion("age_op_forecast_r70_op not like", value, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpIn(List<String> values) {
            addCriterion("age_op_forecast_r70_op in", values, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpNotIn(List<String> values) {
            addCriterion("age_op_forecast_r70_op not in", values, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r70_op between", value1, value2, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR70OpNotBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r70_op not between", value1, value2, "ageOpForecastR70Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpIsNull() {
            addCriterion("age_op_forecast_r80_op is null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpIsNotNull() {
            addCriterion("age_op_forecast_r80_op is not null");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpEqualTo(String value) {
            addCriterion("age_op_forecast_r80_op =", value, "ageOpForecastR80Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpNotEqualTo(String value) {
            addCriterion("age_op_forecast_r80_op <>", value, "ageOpForecastR80Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpGreaterThan(String value) {
            addCriterion("age_op_forecast_r80_op >", value, "ageOpForecastR80Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpGreaterThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r80_op >=", value, "ageOpForecastR80Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpLessThan(String value) {
            addCriterion("age_op_forecast_r80_op <", value, "ageOpForecastR80Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpLessThanOrEqualTo(String value) {
            addCriterion("age_op_forecast_r80_op <=", value, "ageOpForecastR80Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpLike(String value) {
            addCriterion("age_op_forecast_r80_op like", value, "ageOpForecastR80Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpNotLike(String value) {
            addCriterion("age_op_forecast_r80_op not like", value, "ageOpForecastR80Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpIn(List<String> values) {
            addCriterion("age_op_forecast_r80_op in", values, "ageOpForecastR80Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpNotIn(List<String> values) {
            addCriterion("age_op_forecast_r80_op not in", values, "ageOpForecastR80Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r80_op between", value1, value2, "ageOpForecastR80Op");
            return (Criteria) this;
        }

        public Criteria andAgeOpForecastR80OpNotBetween(String value1, String value2) {
            addCriterion("age_op_forecast_r80_op not between", value1, value2, "ageOpForecastR80Op");
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