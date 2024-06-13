package com.yihe.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class YhEventEnrollExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public YhEventEnrollExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andEventNameIsNull() {
            addCriterion("event_name is null");
            return (Criteria) this;
        }

        public Criteria andEventNameIsNotNull() {
            addCriterion("event_name is not null");
            return (Criteria) this;
        }

        public Criteria andEventNameEqualTo(String value) {
            addCriterion("event_name =", value, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventNameNotEqualTo(String value) {
            addCriterion("event_name <>", value, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventNameGreaterThan(String value) {
            addCriterion("event_name >", value, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventNameGreaterThanOrEqualTo(String value) {
            addCriterion("event_name >=", value, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventNameLessThan(String value) {
            addCriterion("event_name <", value, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventNameLessThanOrEqualTo(String value) {
            addCriterion("event_name <=", value, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventNameLike(String value) {
            addCriterion("event_name like", value, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventNameNotLike(String value) {
            addCriterion("event_name not like", value, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventNameIn(List<String> values) {
            addCriterion("event_name in", values, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventNameNotIn(List<String> values) {
            addCriterion("event_name not in", values, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventNameBetween(String value1, String value2) {
            addCriterion("event_name between", value1, value2, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventNameNotBetween(String value1, String value2) {
            addCriterion("event_name not between", value1, value2, "eventName");
            return (Criteria) this;
        }

        public Criteria andEventContentIsNull() {
            addCriterion("event_content is null");
            return (Criteria) this;
        }

        public Criteria andEventContentIsNotNull() {
            addCriterion("event_content is not null");
            return (Criteria) this;
        }

        public Criteria andEventContentEqualTo(String value) {
            addCriterion("event_content =", value, "eventContent");
            return (Criteria) this;
        }

        public Criteria andEventContentNotEqualTo(String value) {
            addCriterion("event_content <>", value, "eventContent");
            return (Criteria) this;
        }

        public Criteria andEventContentGreaterThan(String value) {
            addCriterion("event_content >", value, "eventContent");
            return (Criteria) this;
        }

        public Criteria andEventContentGreaterThanOrEqualTo(String value) {
            addCriterion("event_content >=", value, "eventContent");
            return (Criteria) this;
        }

        public Criteria andEventContentLessThan(String value) {
            addCriterion("event_content <", value, "eventContent");
            return (Criteria) this;
        }

        public Criteria andEventContentLessThanOrEqualTo(String value) {
            addCriterion("event_content <=", value, "eventContent");
            return (Criteria) this;
        }

        public Criteria andEventContentLike(String value) {
            addCriterion("event_content like", value, "eventContent");
            return (Criteria) this;
        }

        public Criteria andEventContentNotLike(String value) {
            addCriterion("event_content not like", value, "eventContent");
            return (Criteria) this;
        }

        public Criteria andEventContentIn(List<String> values) {
            addCriterion("event_content in", values, "eventContent");
            return (Criteria) this;
        }

        public Criteria andEventContentNotIn(List<String> values) {
            addCriterion("event_content not in", values, "eventContent");
            return (Criteria) this;
        }

        public Criteria andEventContentBetween(String value1, String value2) {
            addCriterion("event_content between", value1, value2, "eventContent");
            return (Criteria) this;
        }

        public Criteria andEventContentNotBetween(String value1, String value2) {
            addCriterion("event_content not between", value1, value2, "eventContent");
            return (Criteria) this;
        }

        public Criteria andProviderIsNull() {
            addCriterion("provider is null");
            return (Criteria) this;
        }

        public Criteria andProviderIsNotNull() {
            addCriterion("provider is not null");
            return (Criteria) this;
        }

        public Criteria andProviderEqualTo(String value) {
            addCriterion("provider =", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotEqualTo(String value) {
            addCriterion("provider <>", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderGreaterThan(String value) {
            addCriterion("provider >", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderGreaterThanOrEqualTo(String value) {
            addCriterion("provider >=", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderLessThan(String value) {
            addCriterion("provider <", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderLessThanOrEqualTo(String value) {
            addCriterion("provider <=", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderLike(String value) {
            addCriterion("provider like", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotLike(String value) {
            addCriterion("provider not like", value, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderIn(List<String> values) {
            addCriterion("provider in", values, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotIn(List<String> values) {
            addCriterion("provider not in", values, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderBetween(String value1, String value2) {
            addCriterion("provider between", value1, value2, "provider");
            return (Criteria) this;
        }

        public Criteria andProviderNotBetween(String value1, String value2) {
            addCriterion("provider not between", value1, value2, "provider");
            return (Criteria) this;
        }

        public Criteria andPublicDateIsNull() {
            addCriterion("public_date is null");
            return (Criteria) this;
        }

        public Criteria andPublicDateIsNotNull() {
            addCriterion("public_date is not null");
            return (Criteria) this;
        }

        public Criteria andPublicDateEqualTo(Date value) {
            addCriterionForJDBCDate("public_date =", value, "publicDate");
            return (Criteria) this;
        }

        public Criteria andPublicDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("public_date <>", value, "publicDate");
            return (Criteria) this;
        }

        public Criteria andPublicDateGreaterThan(Date value) {
            addCriterionForJDBCDate("public_date >", value, "publicDate");
            return (Criteria) this;
        }

        public Criteria andPublicDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("public_date >=", value, "publicDate");
            return (Criteria) this;
        }

        public Criteria andPublicDateLessThan(Date value) {
            addCriterionForJDBCDate("public_date <", value, "publicDate");
            return (Criteria) this;
        }

        public Criteria andPublicDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("public_date <=", value, "publicDate");
            return (Criteria) this;
        }

        public Criteria andPublicDateIn(List<Date> values) {
            addCriterionForJDBCDate("public_date in", values, "publicDate");
            return (Criteria) this;
        }

        public Criteria andPublicDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("public_date not in", values, "publicDate");
            return (Criteria) this;
        }

        public Criteria andPublicDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("public_date between", value1, value2, "publicDate");
            return (Criteria) this;
        }

        public Criteria andPublicDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("public_date not between", value1, value2, "publicDate");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateIsNull() {
            addCriterion("register_end_date is null");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateIsNotNull() {
            addCriterion("register_end_date is not null");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("register_end_date =", value, "registerEndDate");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("register_end_date <>", value, "registerEndDate");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("register_end_date >", value, "registerEndDate");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("register_end_date >=", value, "registerEndDate");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateLessThan(Date value) {
            addCriterionForJDBCDate("register_end_date <", value, "registerEndDate");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("register_end_date <=", value, "registerEndDate");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("register_end_date in", values, "registerEndDate");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("register_end_date not in", values, "registerEndDate");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("register_end_date between", value1, value2, "registerEndDate");
            return (Criteria) this;
        }

        public Criteria andRegisterEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("register_end_date not between", value1, value2, "registerEndDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterionForJDBCDate("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(Integer value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(Integer value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(Integer value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(Integer value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(Integer value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<Integer> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<Integer> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(Integer value1, Integer value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("total not between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEventRuleIsNull() {
            addCriterion("event_rule is null");
            return (Criteria) this;
        }

        public Criteria andEventRuleIsNotNull() {
            addCriterion("event_rule is not null");
            return (Criteria) this;
        }

        public Criteria andEventRuleEqualTo(String value) {
            addCriterion("event_rule =", value, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventRuleNotEqualTo(String value) {
            addCriterion("event_rule <>", value, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventRuleGreaterThan(String value) {
            addCriterion("event_rule >", value, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventRuleGreaterThanOrEqualTo(String value) {
            addCriterion("event_rule >=", value, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventRuleLessThan(String value) {
            addCriterion("event_rule <", value, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventRuleLessThanOrEqualTo(String value) {
            addCriterion("event_rule <=", value, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventRuleLike(String value) {
            addCriterion("event_rule like", value, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventRuleNotLike(String value) {
            addCriterion("event_rule not like", value, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventRuleIn(List<String> values) {
            addCriterion("event_rule in", values, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventRuleNotIn(List<String> values) {
            addCriterion("event_rule not in", values, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventRuleBetween(String value1, String value2) {
            addCriterion("event_rule between", value1, value2, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventRuleNotBetween(String value1, String value2) {
            addCriterion("event_rule not between", value1, value2, "eventRule");
            return (Criteria) this;
        }

        public Criteria andEventAddrIsNull() {
            addCriterion("event_addr is null");
            return (Criteria) this;
        }

        public Criteria andEventAddrIsNotNull() {
            addCriterion("event_addr is not null");
            return (Criteria) this;
        }

        public Criteria andEventAddrEqualTo(String value) {
            addCriterion("event_addr =", value, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventAddrNotEqualTo(String value) {
            addCriterion("event_addr <>", value, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventAddrGreaterThan(String value) {
            addCriterion("event_addr >", value, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventAddrGreaterThanOrEqualTo(String value) {
            addCriterion("event_addr >=", value, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventAddrLessThan(String value) {
            addCriterion("event_addr <", value, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventAddrLessThanOrEqualTo(String value) {
            addCriterion("event_addr <=", value, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventAddrLike(String value) {
            addCriterion("event_addr like", value, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventAddrNotLike(String value) {
            addCriterion("event_addr not like", value, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventAddrIn(List<String> values) {
            addCriterion("event_addr in", values, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventAddrNotIn(List<String> values) {
            addCriterion("event_addr not in", values, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventAddrBetween(String value1, String value2) {
            addCriterion("event_addr between", value1, value2, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventAddrNotBetween(String value1, String value2) {
            addCriterion("event_addr not between", value1, value2, "eventAddr");
            return (Criteria) this;
        }

        public Criteria andEventImgIsNull() {
            addCriterion("event_img is null");
            return (Criteria) this;
        }

        public Criteria andEventImgIsNotNull() {
            addCriterion("event_img is not null");
            return (Criteria) this;
        }

        public Criteria andEventImgEqualTo(String value) {
            addCriterion("event_img =", value, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventImgNotEqualTo(String value) {
            addCriterion("event_img <>", value, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventImgGreaterThan(String value) {
            addCriterion("event_img >", value, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventImgGreaterThanOrEqualTo(String value) {
            addCriterion("event_img >=", value, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventImgLessThan(String value) {
            addCriterion("event_img <", value, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventImgLessThanOrEqualTo(String value) {
            addCriterion("event_img <=", value, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventImgLike(String value) {
            addCriterion("event_img like", value, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventImgNotLike(String value) {
            addCriterion("event_img not like", value, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventImgIn(List<String> values) {
            addCriterion("event_img in", values, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventImgNotIn(List<String> values) {
            addCriterion("event_img not in", values, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventImgBetween(String value1, String value2) {
            addCriterion("event_img between", value1, value2, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventImgNotBetween(String value1, String value2) {
            addCriterion("event_img not between", value1, value2, "eventImg");
            return (Criteria) this;
        }

        public Criteria andEventTypeIsNull() {
            addCriterion("event_type is null");
            return (Criteria) this;
        }

        public Criteria andEventTypeIsNotNull() {
            addCriterion("event_type is not null");
            return (Criteria) this;
        }

        public Criteria andEventTypeEqualTo(Integer value) {
            addCriterion("event_type =", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeNotEqualTo(Integer value) {
            addCriterion("event_type <>", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeGreaterThan(Integer value) {
            addCriterion("event_type >", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("event_type >=", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeLessThan(Integer value) {
            addCriterion("event_type <", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeLessThanOrEqualTo(Integer value) {
            addCriterion("event_type <=", value, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeIn(List<Integer> values) {
            addCriterion("event_type in", values, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeNotIn(List<Integer> values) {
            addCriterion("event_type not in", values, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeBetween(Integer value1, Integer value2) {
            addCriterion("event_type between", value1, value2, "eventType");
            return (Criteria) this;
        }

        public Criteria andEventTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("event_type not between", value1, value2, "eventType");
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