package com.yihe.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YhDiseaseRiskResultExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public YhDiseaseRiskResultExample() {
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

        public Criteria andDiseaseidIsNull() {
            addCriterion("diseaseId is null");
            return (Criteria) this;
        }

        public Criteria andDiseaseidIsNotNull() {
            addCriterion("diseaseId is not null");
            return (Criteria) this;
        }

        public Criteria andDiseaseidEqualTo(String value) {
            addCriterion("diseaseId =", value, "diseaseid");
            return (Criteria) this;
        }

        public Criteria andDiseaseidNotEqualTo(String value) {
            addCriterion("diseaseId <>", value, "diseaseid");
            return (Criteria) this;
        }

        public Criteria andDiseaseidGreaterThan(String value) {
            addCriterion("diseaseId >", value, "diseaseid");
            return (Criteria) this;
        }

        public Criteria andDiseaseidGreaterThanOrEqualTo(String value) {
            addCriterion("diseaseId >=", value, "diseaseid");
            return (Criteria) this;
        }

        public Criteria andDiseaseidLessThan(String value) {
            addCriterion("diseaseId <", value, "diseaseid");
            return (Criteria) this;
        }

        public Criteria andDiseaseidLessThanOrEqualTo(String value) {
            addCriterion("diseaseId <=", value, "diseaseid");
            return (Criteria) this;
        }

        public Criteria andDiseaseidLike(String value) {
            addCriterion("diseaseId like", value, "diseaseid");
            return (Criteria) this;
        }

        public Criteria andDiseaseidNotLike(String value) {
            addCriterion("diseaseId not like", value, "diseaseid");
            return (Criteria) this;
        }

        public Criteria andDiseaseidIn(List<String> values) {
            addCriterion("diseaseId in", values, "diseaseid");
            return (Criteria) this;
        }

        public Criteria andDiseaseidNotIn(List<String> values) {
            addCriterion("diseaseId not in", values, "diseaseid");
            return (Criteria) this;
        }

        public Criteria andDiseaseidBetween(String value1, String value2) {
            addCriterion("diseaseId between", value1, value2, "diseaseid");
            return (Criteria) this;
        }

        public Criteria andDiseaseidNotBetween(String value1, String value2) {
            addCriterion("diseaseId not between", value1, value2, "diseaseid");
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

        public Criteria andRequestSerializeIsNull() {
            addCriterion("request_serialize is null");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeIsNotNull() {
            addCriterion("request_serialize is not null");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeEqualTo(String value) {
            addCriterion("request_serialize =", value, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeNotEqualTo(String value) {
            addCriterion("request_serialize <>", value, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeGreaterThan(String value) {
            addCriterion("request_serialize >", value, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeGreaterThanOrEqualTo(String value) {
            addCriterion("request_serialize >=", value, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeLessThan(String value) {
            addCriterion("request_serialize <", value, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeLessThanOrEqualTo(String value) {
            addCriterion("request_serialize <=", value, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeLike(String value) {
            addCriterion("request_serialize like", value, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeNotLike(String value) {
            addCriterion("request_serialize not like", value, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeIn(List<String> values) {
            addCriterion("request_serialize in", values, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeNotIn(List<String> values) {
            addCriterion("request_serialize not in", values, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeBetween(String value1, String value2) {
            addCriterion("request_serialize between", value1, value2, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andRequestSerializeNotBetween(String value1, String value2) {
            addCriterion("request_serialize not between", value1, value2, "requestSerialize");
            return (Criteria) this;
        }

        public Criteria andQnameIsNull() {
            addCriterion("qname is null");
            return (Criteria) this;
        }

        public Criteria andQnameIsNotNull() {
            addCriterion("qname is not null");
            return (Criteria) this;
        }

        public Criteria andQnameEqualTo(String value) {
            addCriterion("qname =", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameNotEqualTo(String value) {
            addCriterion("qname <>", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameGreaterThan(String value) {
            addCriterion("qname >", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameGreaterThanOrEqualTo(String value) {
            addCriterion("qname >=", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameLessThan(String value) {
            addCriterion("qname <", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameLessThanOrEqualTo(String value) {
            addCriterion("qname <=", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameLike(String value) {
            addCriterion("qname like", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameNotLike(String value) {
            addCriterion("qname not like", value, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameIn(List<String> values) {
            addCriterion("qname in", values, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameNotIn(List<String> values) {
            addCriterion("qname not in", values, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameBetween(String value1, String value2) {
            addCriterion("qname between", value1, value2, "qname");
            return (Criteria) this;
        }

        public Criteria andQnameNotBetween(String value1, String value2) {
            addCriterion("qname not between", value1, value2, "qname");
            return (Criteria) this;
        }

        public Criteria andQcodeIsNull() {
            addCriterion("qcode is null");
            return (Criteria) this;
        }

        public Criteria andQcodeIsNotNull() {
            addCriterion("qcode is not null");
            return (Criteria) this;
        }

        public Criteria andQcodeEqualTo(String value) {
            addCriterion("qcode =", value, "qcode");
            return (Criteria) this;
        }

        public Criteria andQcodeNotEqualTo(String value) {
            addCriterion("qcode <>", value, "qcode");
            return (Criteria) this;
        }

        public Criteria andQcodeGreaterThan(String value) {
            addCriterion("qcode >", value, "qcode");
            return (Criteria) this;
        }

        public Criteria andQcodeGreaterThanOrEqualTo(String value) {
            addCriterion("qcode >=", value, "qcode");
            return (Criteria) this;
        }

        public Criteria andQcodeLessThan(String value) {
            addCriterion("qcode <", value, "qcode");
            return (Criteria) this;
        }

        public Criteria andQcodeLessThanOrEqualTo(String value) {
            addCriterion("qcode <=", value, "qcode");
            return (Criteria) this;
        }

        public Criteria andQcodeLike(String value) {
            addCriterion("qcode like", value, "qcode");
            return (Criteria) this;
        }

        public Criteria andQcodeNotLike(String value) {
            addCriterion("qcode not like", value, "qcode");
            return (Criteria) this;
        }

        public Criteria andQcodeIn(List<String> values) {
            addCriterion("qcode in", values, "qcode");
            return (Criteria) this;
        }

        public Criteria andQcodeNotIn(List<String> values) {
            addCriterion("qcode not in", values, "qcode");
            return (Criteria) this;
        }

        public Criteria andQcodeBetween(String value1, String value2) {
            addCriterion("qcode between", value1, value2, "qcode");
            return (Criteria) this;
        }

        public Criteria andQcodeNotBetween(String value1, String value2) {
            addCriterion("qcode not between", value1, value2, "qcode");
            return (Criteria) this;
        }

        public Criteria andQvalueIsNull() {
            addCriterion("qvalue is null");
            return (Criteria) this;
        }

        public Criteria andQvalueIsNotNull() {
            addCriterion("qvalue is not null");
            return (Criteria) this;
        }

        public Criteria andQvalueEqualTo(String value) {
            addCriterion("qvalue =", value, "qvalue");
            return (Criteria) this;
        }

        public Criteria andQvalueNotEqualTo(String value) {
            addCriterion("qvalue <>", value, "qvalue");
            return (Criteria) this;
        }

        public Criteria andQvalueGreaterThan(String value) {
            addCriterion("qvalue >", value, "qvalue");
            return (Criteria) this;
        }

        public Criteria andQvalueGreaterThanOrEqualTo(String value) {
            addCriterion("qvalue >=", value, "qvalue");
            return (Criteria) this;
        }

        public Criteria andQvalueLessThan(String value) {
            addCriterion("qvalue <", value, "qvalue");
            return (Criteria) this;
        }

        public Criteria andQvalueLessThanOrEqualTo(String value) {
            addCriterion("qvalue <=", value, "qvalue");
            return (Criteria) this;
        }

        public Criteria andQvalueLike(String value) {
            addCriterion("qvalue like", value, "qvalue");
            return (Criteria) this;
        }

        public Criteria andQvalueNotLike(String value) {
            addCriterion("qvalue not like", value, "qvalue");
            return (Criteria) this;
        }

        public Criteria andQvalueIn(List<String> values) {
            addCriterion("qvalue in", values, "qvalue");
            return (Criteria) this;
        }

        public Criteria andQvalueNotIn(List<String> values) {
            addCriterion("qvalue not in", values, "qvalue");
            return (Criteria) this;
        }

        public Criteria andQvalueBetween(String value1, String value2) {
            addCriterion("qvalue between", value1, value2, "qvalue");
            return (Criteria) this;
        }

        public Criteria andQvalueNotBetween(String value1, String value2) {
            addCriterion("qvalue not between", value1, value2, "qvalue");
            return (Criteria) this;
        }

        public Criteria andLastValueIsNull() {
            addCriterion("last_value is null");
            return (Criteria) this;
        }

        public Criteria andLastValueIsNotNull() {
            addCriterion("last_value is not null");
            return (Criteria) this;
        }

        public Criteria andLastValueEqualTo(String value) {
            addCriterion("last_value =", value, "lastValue");
            return (Criteria) this;
        }

        public Criteria andLastValueNotEqualTo(String value) {
            addCriterion("last_value <>", value, "lastValue");
            return (Criteria) this;
        }

        public Criteria andLastValueGreaterThan(String value) {
            addCriterion("last_value >", value, "lastValue");
            return (Criteria) this;
        }

        public Criteria andLastValueGreaterThanOrEqualTo(String value) {
            addCriterion("last_value >=", value, "lastValue");
            return (Criteria) this;
        }

        public Criteria andLastValueLessThan(String value) {
            addCriterion("last_value <", value, "lastValue");
            return (Criteria) this;
        }

        public Criteria andLastValueLessThanOrEqualTo(String value) {
            addCriterion("last_value <=", value, "lastValue");
            return (Criteria) this;
        }

        public Criteria andLastValueLike(String value) {
            addCriterion("last_value like", value, "lastValue");
            return (Criteria) this;
        }

        public Criteria andLastValueNotLike(String value) {
            addCriterion("last_value not like", value, "lastValue");
            return (Criteria) this;
        }

        public Criteria andLastValueIn(List<String> values) {
            addCriterion("last_value in", values, "lastValue");
            return (Criteria) this;
        }

        public Criteria andLastValueNotIn(List<String> values) {
            addCriterion("last_value not in", values, "lastValue");
            return (Criteria) this;
        }

        public Criteria andLastValueBetween(String value1, String value2) {
            addCriterion("last_value between", value1, value2, "lastValue");
            return (Criteria) this;
        }

        public Criteria andLastValueNotBetween(String value1, String value2) {
            addCriterion("last_value not between", value1, value2, "lastValue");
            return (Criteria) this;
        }

        public Criteria andChangesIsNull() {
            addCriterion("changes is null");
            return (Criteria) this;
        }

        public Criteria andChangesIsNotNull() {
            addCriterion("changes is not null");
            return (Criteria) this;
        }

        public Criteria andChangesEqualTo(String value) {
            addCriterion("changes =", value, "changes");
            return (Criteria) this;
        }

        public Criteria andChangesNotEqualTo(String value) {
            addCriterion("changes <>", value, "changes");
            return (Criteria) this;
        }

        public Criteria andChangesGreaterThan(String value) {
            addCriterion("changes >", value, "changes");
            return (Criteria) this;
        }

        public Criteria andChangesGreaterThanOrEqualTo(String value) {
            addCriterion("changes >=", value, "changes");
            return (Criteria) this;
        }

        public Criteria andChangesLessThan(String value) {
            addCriterion("changes <", value, "changes");
            return (Criteria) this;
        }

        public Criteria andChangesLessThanOrEqualTo(String value) {
            addCriterion("changes <=", value, "changes");
            return (Criteria) this;
        }

        public Criteria andChangesLike(String value) {
            addCriterion("changes like", value, "changes");
            return (Criteria) this;
        }

        public Criteria andChangesNotLike(String value) {
            addCriterion("changes not like", value, "changes");
            return (Criteria) this;
        }

        public Criteria andChangesIn(List<String> values) {
            addCriterion("changes in", values, "changes");
            return (Criteria) this;
        }

        public Criteria andChangesNotIn(List<String> values) {
            addCriterion("changes not in", values, "changes");
            return (Criteria) this;
        }

        public Criteria andChangesBetween(String value1, String value2) {
            addCriterion("changes between", value1, value2, "changes");
            return (Criteria) this;
        }

        public Criteria andChangesNotBetween(String value1, String value2) {
            addCriterion("changes not between", value1, value2, "changes");
            return (Criteria) this;
        }

        public Criteria andOptimalIsNull() {
            addCriterion("optimal is null");
            return (Criteria) this;
        }

        public Criteria andOptimalIsNotNull() {
            addCriterion("optimal is not null");
            return (Criteria) this;
        }

        public Criteria andOptimalEqualTo(String value) {
            addCriterion("optimal =", value, "optimal");
            return (Criteria) this;
        }

        public Criteria andOptimalNotEqualTo(String value) {
            addCriterion("optimal <>", value, "optimal");
            return (Criteria) this;
        }

        public Criteria andOptimalGreaterThan(String value) {
            addCriterion("optimal >", value, "optimal");
            return (Criteria) this;
        }

        public Criteria andOptimalGreaterThanOrEqualTo(String value) {
            addCriterion("optimal >=", value, "optimal");
            return (Criteria) this;
        }

        public Criteria andOptimalLessThan(String value) {
            addCriterion("optimal <", value, "optimal");
            return (Criteria) this;
        }

        public Criteria andOptimalLessThanOrEqualTo(String value) {
            addCriterion("optimal <=", value, "optimal");
            return (Criteria) this;
        }

        public Criteria andOptimalLike(String value) {
            addCriterion("optimal like", value, "optimal");
            return (Criteria) this;
        }

        public Criteria andOptimalNotLike(String value) {
            addCriterion("optimal not like", value, "optimal");
            return (Criteria) this;
        }

        public Criteria andOptimalIn(List<String> values) {
            addCriterion("optimal in", values, "optimal");
            return (Criteria) this;
        }

        public Criteria andOptimalNotIn(List<String> values) {
            addCriterion("optimal not in", values, "optimal");
            return (Criteria) this;
        }

        public Criteria andOptimalBetween(String value1, String value2) {
            addCriterion("optimal between", value1, value2, "optimal");
            return (Criteria) this;
        }

        public Criteria andOptimalNotBetween(String value1, String value2) {
            addCriterion("optimal not between", value1, value2, "optimal");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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

        public Criteria andIsradarIsNull() {
            addCriterion("isRadar is null");
            return (Criteria) this;
        }

        public Criteria andIsradarIsNotNull() {
            addCriterion("isRadar is not null");
            return (Criteria) this;
        }

        public Criteria andIsradarEqualTo(Integer value) {
            addCriterion("isRadar =", value, "isradar");
            return (Criteria) this;
        }

        public Criteria andIsradarNotEqualTo(Integer value) {
            addCriterion("isRadar <>", value, "isradar");
            return (Criteria) this;
        }

        public Criteria andIsradarGreaterThan(Integer value) {
            addCriterion("isRadar >", value, "isradar");
            return (Criteria) this;
        }

        public Criteria andIsradarGreaterThanOrEqualTo(Integer value) {
            addCriterion("isRadar >=", value, "isradar");
            return (Criteria) this;
        }

        public Criteria andIsradarLessThan(Integer value) {
            addCriterion("isRadar <", value, "isradar");
            return (Criteria) this;
        }

        public Criteria andIsradarLessThanOrEqualTo(Integer value) {
            addCriterion("isRadar <=", value, "isradar");
            return (Criteria) this;
        }

        public Criteria andIsradarIn(List<Integer> values) {
            addCriterion("isRadar in", values, "isradar");
            return (Criteria) this;
        }

        public Criteria andIsradarNotIn(List<Integer> values) {
            addCriterion("isRadar not in", values, "isradar");
            return (Criteria) this;
        }

        public Criteria andIsradarBetween(Integer value1, Integer value2) {
            addCriterion("isRadar between", value1, value2, "isradar");
            return (Criteria) this;
        }

        public Criteria andIsradarNotBetween(Integer value1, Integer value2) {
            addCriterion("isRadar not between", value1, value2, "isradar");
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