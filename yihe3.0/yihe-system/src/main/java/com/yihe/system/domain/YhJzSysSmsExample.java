package com.yihe.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YhJzSysSmsExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public YhJzSysSmsExample() {
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

        public Criteria andSmsIdIsNull() {
            addCriterion("sms_id is null");
            return (Criteria) this;
        }

        public Criteria andSmsIdIsNotNull() {
            addCriterion("sms_id is not null");
            return (Criteria) this;
        }

        public Criteria andSmsIdEqualTo(Long value) {
            addCriterion("sms_id =", value, "smsId");
            return (Criteria) this;
        }

        public Criteria andSmsIdNotEqualTo(Long value) {
            addCriterion("sms_id <>", value, "smsId");
            return (Criteria) this;
        }

        public Criteria andSmsIdGreaterThan(Long value) {
            addCriterion("sms_id >", value, "smsId");
            return (Criteria) this;
        }

        public Criteria andSmsIdGreaterThanOrEqualTo(Long value) {
            addCriterion("sms_id >=", value, "smsId");
            return (Criteria) this;
        }

        public Criteria andSmsIdLessThan(Long value) {
            addCriterion("sms_id <", value, "smsId");
            return (Criteria) this;
        }

        public Criteria andSmsIdLessThanOrEqualTo(Long value) {
            addCriterion("sms_id <=", value, "smsId");
            return (Criteria) this;
        }

        public Criteria andSmsIdIn(List<Long> values) {
            addCriterion("sms_id in", values, "smsId");
            return (Criteria) this;
        }

        public Criteria andSmsIdNotIn(List<Long> values) {
            addCriterion("sms_id not in", values, "smsId");
            return (Criteria) this;
        }

        public Criteria andSmsIdBetween(Long value1, Long value2) {
            addCriterion("sms_id between", value1, value2, "smsId");
            return (Criteria) this;
        }

        public Criteria andSmsIdNotBetween(Long value1, Long value2) {
            addCriterion("sms_id not between", value1, value2, "smsId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(String value) {
            addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(String value) {
            addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(String value) {
            addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(String value) {
            addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(String value) {
            addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(String value) {
            addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLike(String value) {
            addCriterion("template_id like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotLike(String value) {
            addCriterion("template_id not like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<String> values) {
            addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<String> values) {
            addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(String value1, String value2) {
            addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(String value1, String value2) {
            addCriterion("template_id not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andSmsnameIsNull() {
            addCriterion("smsname is null");
            return (Criteria) this;
        }

        public Criteria andSmsnameIsNotNull() {
            addCriterion("smsname is not null");
            return (Criteria) this;
        }

        public Criteria andSmsnameEqualTo(String value) {
            addCriterion("smsname =", value, "smsname");
            return (Criteria) this;
        }

        public Criteria andSmsnameNotEqualTo(String value) {
            addCriterion("smsname <>", value, "smsname");
            return (Criteria) this;
        }

        public Criteria andSmsnameGreaterThan(String value) {
            addCriterion("smsname >", value, "smsname");
            return (Criteria) this;
        }

        public Criteria andSmsnameGreaterThanOrEqualTo(String value) {
            addCriterion("smsname >=", value, "smsname");
            return (Criteria) this;
        }

        public Criteria andSmsnameLessThan(String value) {
            addCriterion("smsname <", value, "smsname");
            return (Criteria) this;
        }

        public Criteria andSmsnameLessThanOrEqualTo(String value) {
            addCriterion("smsname <=", value, "smsname");
            return (Criteria) this;
        }

        public Criteria andSmsnameLike(String value) {
            addCriterion("smsname like", value, "smsname");
            return (Criteria) this;
        }

        public Criteria andSmsnameNotLike(String value) {
            addCriterion("smsname not like", value, "smsname");
            return (Criteria) this;
        }

        public Criteria andSmsnameIn(List<String> values) {
            addCriterion("smsname in", values, "smsname");
            return (Criteria) this;
        }

        public Criteria andSmsnameNotIn(List<String> values) {
            addCriterion("smsname not in", values, "smsname");
            return (Criteria) this;
        }

        public Criteria andSmsnameBetween(String value1, String value2) {
            addCriterion("smsname between", value1, value2, "smsname");
            return (Criteria) this;
        }

        public Criteria andSmsnameNotBetween(String value1, String value2) {
            addCriterion("smsname not between", value1, value2, "smsname");
            return (Criteria) this;
        }

        public Criteria andSignnameIsNull() {
            addCriterion("signname is null");
            return (Criteria) this;
        }

        public Criteria andSignnameIsNotNull() {
            addCriterion("signname is not null");
            return (Criteria) this;
        }

        public Criteria andSignnameEqualTo(String value) {
            addCriterion("signname =", value, "signname");
            return (Criteria) this;
        }

        public Criteria andSignnameNotEqualTo(String value) {
            addCriterion("signname <>", value, "signname");
            return (Criteria) this;
        }

        public Criteria andSignnameGreaterThan(String value) {
            addCriterion("signname >", value, "signname");
            return (Criteria) this;
        }

        public Criteria andSignnameGreaterThanOrEqualTo(String value) {
            addCriterion("signname >=", value, "signname");
            return (Criteria) this;
        }

        public Criteria andSignnameLessThan(String value) {
            addCriterion("signname <", value, "signname");
            return (Criteria) this;
        }

        public Criteria andSignnameLessThanOrEqualTo(String value) {
            addCriterion("signname <=", value, "signname");
            return (Criteria) this;
        }

        public Criteria andSignnameLike(String value) {
            addCriterion("signname like", value, "signname");
            return (Criteria) this;
        }

        public Criteria andSignnameNotLike(String value) {
            addCriterion("signname not like", value, "signname");
            return (Criteria) this;
        }

        public Criteria andSignnameIn(List<String> values) {
            addCriterion("signname in", values, "signname");
            return (Criteria) this;
        }

        public Criteria andSignnameNotIn(List<String> values) {
            addCriterion("signname not in", values, "signname");
            return (Criteria) this;
        }

        public Criteria andSignnameBetween(String value1, String value2) {
            addCriterion("signname between", value1, value2, "signname");
            return (Criteria) this;
        }

        public Criteria andSignnameNotBetween(String value1, String value2) {
            addCriterion("signname not between", value1, value2, "signname");
            return (Criteria) this;
        }

        public Criteria andAccesskeyIsNull() {
            addCriterion("accessKey is null");
            return (Criteria) this;
        }

        public Criteria andAccesskeyIsNotNull() {
            addCriterion("accessKey is not null");
            return (Criteria) this;
        }

        public Criteria andAccesskeyEqualTo(String value) {
            addCriterion("accessKey =", value, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeyNotEqualTo(String value) {
            addCriterion("accessKey <>", value, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeyGreaterThan(String value) {
            addCriterion("accessKey >", value, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeyGreaterThanOrEqualTo(String value) {
            addCriterion("accessKey >=", value, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeyLessThan(String value) {
            addCriterion("accessKey <", value, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeyLessThanOrEqualTo(String value) {
            addCriterion("accessKey <=", value, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeyLike(String value) {
            addCriterion("accessKey like", value, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeyNotLike(String value) {
            addCriterion("accessKey not like", value, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeyIn(List<String> values) {
            addCriterion("accessKey in", values, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeyNotIn(List<String> values) {
            addCriterion("accessKey not in", values, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeyBetween(String value1, String value2) {
            addCriterion("accessKey between", value1, value2, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeyNotBetween(String value1, String value2) {
            addCriterion("accessKey not between", value1, value2, "accesskey");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretIsNull() {
            addCriterion("accessKeySecret is null");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretIsNotNull() {
            addCriterion("accessKeySecret is not null");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretEqualTo(String value) {
            addCriterion("accessKeySecret =", value, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretNotEqualTo(String value) {
            addCriterion("accessKeySecret <>", value, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretGreaterThan(String value) {
            addCriterion("accessKeySecret >", value, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretGreaterThanOrEqualTo(String value) {
            addCriterion("accessKeySecret >=", value, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretLessThan(String value) {
            addCriterion("accessKeySecret <", value, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretLessThanOrEqualTo(String value) {
            addCriterion("accessKeySecret <=", value, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretLike(String value) {
            addCriterion("accessKeySecret like", value, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretNotLike(String value) {
            addCriterion("accessKeySecret not like", value, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretIn(List<String> values) {
            addCriterion("accessKeySecret in", values, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretNotIn(List<String> values) {
            addCriterion("accessKeySecret not in", values, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretBetween(String value1, String value2) {
            addCriterion("accessKeySecret between", value1, value2, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andAccesskeysecretNotBetween(String value1, String value2) {
            addCriterion("accessKeySecret not between", value1, value2, "accesskeysecret");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumIsNull() {
            addCriterion("smscode_num is null");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumIsNotNull() {
            addCriterion("smscode_num is not null");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumEqualTo(Integer value) {
            addCriterion("smscode_num =", value, "smscodeNum");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumNotEqualTo(Integer value) {
            addCriterion("smscode_num <>", value, "smscodeNum");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumGreaterThan(Integer value) {
            addCriterion("smscode_num >", value, "smscodeNum");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("smscode_num >=", value, "smscodeNum");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumLessThan(Integer value) {
            addCriterion("smscode_num <", value, "smscodeNum");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumLessThanOrEqualTo(Integer value) {
            addCriterion("smscode_num <=", value, "smscodeNum");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumIn(List<Integer> values) {
            addCriterion("smscode_num in", values, "smscodeNum");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumNotIn(List<Integer> values) {
            addCriterion("smscode_num not in", values, "smscodeNum");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumBetween(Integer value1, Integer value2) {
            addCriterion("smscode_num between", value1, value2, "smscodeNum");
            return (Criteria) this;
        }

        public Criteria andSmscodeNumNotBetween(Integer value1, Integer value2) {
            addCriterion("smscode_num not between", value1, value2, "smscodeNum");
            return (Criteria) this;
        }

        public Criteria andSmsCreateIsNull() {
            addCriterion("sms_create is null");
            return (Criteria) this;
        }

        public Criteria andSmsCreateIsNotNull() {
            addCriterion("sms_create is not null");
            return (Criteria) this;
        }

        public Criteria andSmsCreateEqualTo(Date value) {
            addCriterion("sms_create =", value, "smsCreate");
            return (Criteria) this;
        }

        public Criteria andSmsCreateNotEqualTo(Date value) {
            addCriterion("sms_create <>", value, "smsCreate");
            return (Criteria) this;
        }

        public Criteria andSmsCreateGreaterThan(Date value) {
            addCriterion("sms_create >", value, "smsCreate");
            return (Criteria) this;
        }

        public Criteria andSmsCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("sms_create >=", value, "smsCreate");
            return (Criteria) this;
        }

        public Criteria andSmsCreateLessThan(Date value) {
            addCriterion("sms_create <", value, "smsCreate");
            return (Criteria) this;
        }

        public Criteria andSmsCreateLessThanOrEqualTo(Date value) {
            addCriterion("sms_create <=", value, "smsCreate");
            return (Criteria) this;
        }

        public Criteria andSmsCreateIn(List<Date> values) {
            addCriterion("sms_create in", values, "smsCreate");
            return (Criteria) this;
        }

        public Criteria andSmsCreateNotIn(List<Date> values) {
            addCriterion("sms_create not in", values, "smsCreate");
            return (Criteria) this;
        }

        public Criteria andSmsCreateBetween(Date value1, Date value2) {
            addCriterion("sms_create between", value1, value2, "smsCreate");
            return (Criteria) this;
        }

        public Criteria andSmsCreateNotBetween(Date value1, Date value2) {
            addCriterion("sms_create not between", value1, value2, "smsCreate");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedIsNull() {
            addCriterion("sms_modified is null");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedIsNotNull() {
            addCriterion("sms_modified is not null");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedEqualTo(Date value) {
            addCriterion("sms_modified =", value, "smsModified");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedNotEqualTo(Date value) {
            addCriterion("sms_modified <>", value, "smsModified");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedGreaterThan(Date value) {
            addCriterion("sms_modified >", value, "smsModified");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("sms_modified >=", value, "smsModified");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedLessThan(Date value) {
            addCriterion("sms_modified <", value, "smsModified");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedLessThanOrEqualTo(Date value) {
            addCriterion("sms_modified <=", value, "smsModified");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedIn(List<Date> values) {
            addCriterion("sms_modified in", values, "smsModified");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedNotIn(List<Date> values) {
            addCriterion("sms_modified not in", values, "smsModified");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedBetween(Date value1, Date value2) {
            addCriterion("sms_modified between", value1, value2, "smsModified");
            return (Criteria) this;
        }

        public Criteria andSmsModifiedNotBetween(Date value1, Date value2) {
            addCriterion("sms_modified not between", value1, value2, "smsModified");
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