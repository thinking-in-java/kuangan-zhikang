package com.yihe.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class YhDiseaseAssessTotalReportExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public YhDiseaseAssessTotalReportExample() {
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

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(Integer value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Integer value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Integer value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Integer value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Integer value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Integer value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Integer> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Integer> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Integer value1, Integer value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Integer value1, Integer value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andAgeIsNull() {
            addCriterion("age is null");
            return (Criteria) this;
        }

        public Criteria andAgeIsNotNull() {
            addCriterion("age is not null");
            return (Criteria) this;
        }

        public Criteria andAgeEqualTo(Integer value) {
            addCriterion("age =", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotEqualTo(Integer value) {
            addCriterion("age <>", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThan(Integer value) {
            addCriterion("age >", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThanOrEqualTo(Integer value) {
            addCriterion("age >=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThan(Integer value) {
            addCriterion("age <", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThanOrEqualTo(Integer value) {
            addCriterion("age <=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeIn(List<Integer> values) {
            addCriterion("age in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotIn(List<Integer> values) {
            addCriterion("age not in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeBetween(Integer value1, Integer value2) {
            addCriterion("age between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotBetween(Integer value1, Integer value2) {
            addCriterion("age not between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserOccupationIsNull() {
            addCriterion("user_occupation is null");
            return (Criteria) this;
        }

        public Criteria andUserOccupationIsNotNull() {
            addCriterion("user_occupation is not null");
            return (Criteria) this;
        }

        public Criteria andUserOccupationEqualTo(String value) {
            addCriterion("user_occupation =", value, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserOccupationNotEqualTo(String value) {
            addCriterion("user_occupation <>", value, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserOccupationGreaterThan(String value) {
            addCriterion("user_occupation >", value, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserOccupationGreaterThanOrEqualTo(String value) {
            addCriterion("user_occupation >=", value, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserOccupationLessThan(String value) {
            addCriterion("user_occupation <", value, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserOccupationLessThanOrEqualTo(String value) {
            addCriterion("user_occupation <=", value, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserOccupationLike(String value) {
            addCriterion("user_occupation like", value, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserOccupationNotLike(String value) {
            addCriterion("user_occupation not like", value, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserOccupationIn(List<String> values) {
            addCriterion("user_occupation in", values, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserOccupationNotIn(List<String> values) {
            addCriterion("user_occupation not in", values, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserOccupationBetween(String value1, String value2) {
            addCriterion("user_occupation between", value1, value2, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserOccupationNotBetween(String value1, String value2) {
            addCriterion("user_occupation not between", value1, value2, "userOccupation");
            return (Criteria) this;
        }

        public Criteria andUserJzdIsNull() {
            addCriterion("user_jzd is null");
            return (Criteria) this;
        }

        public Criteria andUserJzdIsNotNull() {
            addCriterion("user_jzd is not null");
            return (Criteria) this;
        }

        public Criteria andUserJzdEqualTo(String value) {
            addCriterion("user_jzd =", value, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzdNotEqualTo(String value) {
            addCriterion("user_jzd <>", value, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzdGreaterThan(String value) {
            addCriterion("user_jzd >", value, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzdGreaterThanOrEqualTo(String value) {
            addCriterion("user_jzd >=", value, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzdLessThan(String value) {
            addCriterion("user_jzd <", value, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzdLessThanOrEqualTo(String value) {
            addCriterion("user_jzd <=", value, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzdLike(String value) {
            addCriterion("user_jzd like", value, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzdNotLike(String value) {
            addCriterion("user_jzd not like", value, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzdIn(List<String> values) {
            addCriterion("user_jzd in", values, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzdNotIn(List<String> values) {
            addCriterion("user_jzd not in", values, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzdBetween(String value1, String value2) {
            addCriterion("user_jzd between", value1, value2, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzdNotBetween(String value1, String value2) {
            addCriterion("user_jzd not between", value1, value2, "userJzd");
            return (Criteria) this;
        }

        public Criteria andUserJzhjIsNull() {
            addCriterion("user_jzhj is null");
            return (Criteria) this;
        }

        public Criteria andUserJzhjIsNotNull() {
            addCriterion("user_jzhj is not null");
            return (Criteria) this;
        }

        public Criteria andUserJzhjEqualTo(String value) {
            addCriterion("user_jzhj =", value, "userJzhj");
            return (Criteria) this;
        }

        public Criteria andUserJzhjNotEqualTo(String value) {
            addCriterion("user_jzhj <>", value, "userJzhj");
            return (Criteria) this;
        }

        public Criteria andUserJzhjGreaterThan(String value) {
            addCriterion("user_jzhj >", value, "userJzhj");
            return (Criteria) this;
        }

        public Criteria andUserJzhjGreaterThanOrEqualTo(String value) {
            addCriterion("user_jzhj >=", value, "userJzhj");
            return (Criteria) this;
        }

        public Criteria andUserJzhjLessThan(String value) {
            addCriterion("user_jzhj <", value, "userJzhj");
            return (Criteria) this;
        }

        public Criteria andUserJzhjLessThanOrEqualTo(String value) {
            addCriterion("user_jzhj <=", value, "userJzhj");
            return (Criteria) this;
        }

        public Criteria andUserJzhjLike(String value) {
            addCriterion("user_jzhj like", value, "userJzhj");
            return (Criteria) this;
        }

        public Criteria andUserJzhjNotLike(String value) {
            addCriterion("user_jzhj not like", value, "userJzhj");
            return (Criteria) this;
        }

        public Criteria andUserJzhjIn(List<String> values) {
            addCriterion("user_jzhj in", values, "userJzhj");
            return (Criteria) this;
        }

        public Criteria andUserJzhjNotIn(List<String> values) {
            addCriterion("user_jzhj not in", values, "userJzhj");
            return (Criteria) this;
        }

        public Criteria andUserJzhjBetween(String value1, String value2) {
            addCriterion("user_jzhj between", value1, value2, "userJzhj");
            return (Criteria) this;
        }

        public Criteria andUserJzhjNotBetween(String value1, String value2) {
            addCriterion("user_jzhj not between", value1, value2, "userJzhj");
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