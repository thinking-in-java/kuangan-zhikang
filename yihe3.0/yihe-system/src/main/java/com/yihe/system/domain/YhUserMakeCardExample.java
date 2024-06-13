package com.yihe.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YhUserMakeCardExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public YhUserMakeCardExample() {
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

        public Criteria andEnrolCardIdIsNull() {
            addCriterion("Enrol_Card_Id is null");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdIsNotNull() {
            addCriterion("Enrol_Card_Id is not null");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdEqualTo(String value) {
            addCriterion("Enrol_Card_Id =", value, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdNotEqualTo(String value) {
            addCriterion("Enrol_Card_Id <>", value, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdGreaterThan(String value) {
            addCriterion("Enrol_Card_Id >", value, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdGreaterThanOrEqualTo(String value) {
            addCriterion("Enrol_Card_Id >=", value, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdLessThan(String value) {
            addCriterion("Enrol_Card_Id <", value, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdLessThanOrEqualTo(String value) {
            addCriterion("Enrol_Card_Id <=", value, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdLike(String value) {
            addCriterion("Enrol_Card_Id like", value, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdNotLike(String value) {
            addCriterion("Enrol_Card_Id not like", value, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdIn(List<String> values) {
            addCriterion("Enrol_Card_Id in", values, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdNotIn(List<String> values) {
            addCriterion("Enrol_Card_Id not in", values, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdBetween(String value1, String value2) {
            addCriterion("Enrol_Card_Id between", value1, value2, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardIdNotBetween(String value1, String value2) {
            addCriterion("Enrol_Card_Id not between", value1, value2, "enrolCardId");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeIsNull() {
            addCriterion("Enrol_Card_Code is null");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeIsNotNull() {
            addCriterion("Enrol_Card_Code is not null");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeEqualTo(String value) {
            addCriterion("Enrol_Card_Code =", value, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeNotEqualTo(String value) {
            addCriterion("Enrol_Card_Code <>", value, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeGreaterThan(String value) {
            addCriterion("Enrol_Card_Code >", value, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeGreaterThanOrEqualTo(String value) {
            addCriterion("Enrol_Card_Code >=", value, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeLessThan(String value) {
            addCriterion("Enrol_Card_Code <", value, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeLessThanOrEqualTo(String value) {
            addCriterion("Enrol_Card_Code <=", value, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeLike(String value) {
            addCriterion("Enrol_Card_Code like", value, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeNotLike(String value) {
            addCriterion("Enrol_Card_Code not like", value, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeIn(List<String> values) {
            addCriterion("Enrol_Card_Code in", values, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeNotIn(List<String> values) {
            addCriterion("Enrol_Card_Code not in", values, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeBetween(String value1, String value2) {
            addCriterion("Enrol_Card_Code between", value1, value2, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolCardCodeNotBetween(String value1, String value2) {
            addCriterion("Enrol_Card_Code not between", value1, value2, "enrolCardCode");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeIsNull() {
            addCriterion("Enrol_Time is null");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeIsNotNull() {
            addCriterion("Enrol_Time is not null");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeEqualTo(Date value) {
            addCriterion("Enrol_Time =", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeNotEqualTo(Date value) {
            addCriterion("Enrol_Time <>", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeGreaterThan(Date value) {
            addCriterion("Enrol_Time >", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Enrol_Time >=", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeLessThan(Date value) {
            addCriterion("Enrol_Time <", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeLessThanOrEqualTo(Date value) {
            addCriterion("Enrol_Time <=", value, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeIn(List<Date> values) {
            addCriterion("Enrol_Time in", values, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeNotIn(List<Date> values) {
            addCriterion("Enrol_Time not in", values, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeBetween(Date value1, Date value2) {
            addCriterion("Enrol_Time between", value1, value2, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolTimeNotBetween(Date value1, Date value2) {
            addCriterion("Enrol_Time not between", value1, value2, "enrolTime");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdIsNull() {
            addCriterion("Enrol_User_Id is null");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdIsNotNull() {
            addCriterion("Enrol_User_Id is not null");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdEqualTo(String value) {
            addCriterion("Enrol_User_Id =", value, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdNotEqualTo(String value) {
            addCriterion("Enrol_User_Id <>", value, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdGreaterThan(String value) {
            addCriterion("Enrol_User_Id >", value, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("Enrol_User_Id >=", value, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdLessThan(String value) {
            addCriterion("Enrol_User_Id <", value, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdLessThanOrEqualTo(String value) {
            addCriterion("Enrol_User_Id <=", value, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdLike(String value) {
            addCriterion("Enrol_User_Id like", value, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdNotLike(String value) {
            addCriterion("Enrol_User_Id not like", value, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdIn(List<String> values) {
            addCriterion("Enrol_User_Id in", values, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdNotIn(List<String> values) {
            addCriterion("Enrol_User_Id not in", values, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdBetween(String value1, String value2) {
            addCriterion("Enrol_User_Id between", value1, value2, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andEnrolUserIdNotBetween(String value1, String value2) {
            addCriterion("Enrol_User_Id not between", value1, value2, "enrolUserId");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNull() {
            addCriterion("Card_Type is null");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNotNull() {
            addCriterion("Card_Type is not null");
            return (Criteria) this;
        }

        public Criteria andCardTypeEqualTo(Integer value) {
            addCriterion("Card_Type =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(Integer value) {
            addCriterion("Card_Type <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(Integer value) {
            addCriterion("Card_Type >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("Card_Type >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(Integer value) {
            addCriterion("Card_Type <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(Integer value) {
            addCriterion("Card_Type <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<Integer> values) {
            addCriterion("Card_Type in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<Integer> values) {
            addCriterion("Card_Type not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(Integer value1, Integer value2) {
            addCriterion("Card_Type between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("Card_Type not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNull() {
            addCriterion("Begin_Time is null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNotNull() {
            addCriterion("Begin_Time is not null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeEqualTo(Date value) {
            addCriterion("Begin_Time =", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotEqualTo(Date value) {
            addCriterion("Begin_Time <>", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThan(Date value) {
            addCriterion("Begin_Time >", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Begin_Time >=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThan(Date value) {
            addCriterion("Begin_Time <", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThanOrEqualTo(Date value) {
            addCriterion("Begin_Time <=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIn(List<Date> values) {
            addCriterion("Begin_Time in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotIn(List<Date> values) {
            addCriterion("Begin_Time not in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeBetween(Date value1, Date value2) {
            addCriterion("Begin_Time between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotBetween(Date value1, Date value2) {
            addCriterion("Begin_Time not between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("End_Time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("End_Time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("End_Time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("End_Time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("End_Time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("End_Time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("End_Time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("End_Time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("End_Time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("End_Time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("End_Time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("End_Time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNull() {
            addCriterion("Org_Id is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("Org_Id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(String value) {
            addCriterion("Org_Id =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(String value) {
            addCriterion("Org_Id <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(String value) {
            addCriterion("Org_Id >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("Org_Id >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(String value) {
            addCriterion("Org_Id <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(String value) {
            addCriterion("Org_Id <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLike(String value) {
            addCriterion("Org_Id like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotLike(String value) {
            addCriterion("Org_Id not like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<String> values) {
            addCriterion("Org_Id in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<String> values) {
            addCriterion("Org_Id not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(String value1, String value2) {
            addCriterion("Org_Id between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(String value1, String value2) {
            addCriterion("Org_Id not between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNull() {
            addCriterion("Is_Del is null");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNotNull() {
            addCriterion("Is_Del is not null");
            return (Criteria) this;
        }

        public Criteria andIsDelEqualTo(Integer value) {
            addCriterion("Is_Del =", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotEqualTo(Integer value) {
            addCriterion("Is_Del <>", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThan(Integer value) {
            addCriterion("Is_Del >", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThanOrEqualTo(Integer value) {
            addCriterion("Is_Del >=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThan(Integer value) {
            addCriterion("Is_Del <", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThanOrEqualTo(Integer value) {
            addCriterion("Is_Del <=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelIn(List<Integer> values) {
            addCriterion("Is_Del in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotIn(List<Integer> values) {
            addCriterion("Is_Del not in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelBetween(Integer value1, Integer value2) {
            addCriterion("Is_Del between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotBetween(Integer value1, Integer value2) {
            addCriterion("Is_Del not between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNull() {
            addCriterion("User_Code is null");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNotNull() {
            addCriterion("User_Code is not null");
            return (Criteria) this;
        }

        public Criteria andUserCodeEqualTo(String value) {
            addCriterion("User_Code =", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotEqualTo(String value) {
            addCriterion("User_Code <>", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThan(String value) {
            addCriterion("User_Code >", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThanOrEqualTo(String value) {
            addCriterion("User_Code >=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThan(String value) {
            addCriterion("User_Code <", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThanOrEqualTo(String value) {
            addCriterion("User_Code <=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLike(String value) {
            addCriterion("User_Code like", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotLike(String value) {
            addCriterion("User_Code not like", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeIn(List<String> values) {
            addCriterion("User_Code in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotIn(List<String> values) {
            addCriterion("User_Code not in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeBetween(String value1, String value2) {
            addCriterion("User_Code between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotBetween(String value1, String value2) {
            addCriterion("User_Code not between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("User_Type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("User_Type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(Integer value) {
            addCriterion("User_Type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(Integer value) {
            addCriterion("User_Type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(Integer value) {
            addCriterion("User_Type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("User_Type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(Integer value) {
            addCriterion("User_Type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(Integer value) {
            addCriterion("User_Type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<Integer> values) {
            addCriterion("User_Type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<Integer> values) {
            addCriterion("User_Type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(Integer value1, Integer value2) {
            addCriterion("User_Type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("User_Type not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andDoctorIsNull() {
            addCriterion("doctor is null");
            return (Criteria) this;
        }

        public Criteria andDoctorIsNotNull() {
            addCriterion("doctor is not null");
            return (Criteria) this;
        }

        public Criteria andDoctorEqualTo(Integer value) {
            addCriterion("doctor =", value, "doctor");
            return (Criteria) this;
        }

        public Criteria andDoctorNotEqualTo(Integer value) {
            addCriterion("doctor <>", value, "doctor");
            return (Criteria) this;
        }

        public Criteria andDoctorGreaterThan(Integer value) {
            addCriterion("doctor >", value, "doctor");
            return (Criteria) this;
        }

        public Criteria andDoctorGreaterThanOrEqualTo(Integer value) {
            addCriterion("doctor >=", value, "doctor");
            return (Criteria) this;
        }

        public Criteria andDoctorLessThan(Integer value) {
            addCriterion("doctor <", value, "doctor");
            return (Criteria) this;
        }

        public Criteria andDoctorLessThanOrEqualTo(Integer value) {
            addCriterion("doctor <=", value, "doctor");
            return (Criteria) this;
        }

        public Criteria andDoctorIn(List<Integer> values) {
            addCriterion("doctor in", values, "doctor");
            return (Criteria) this;
        }

        public Criteria andDoctorNotIn(List<Integer> values) {
            addCriterion("doctor not in", values, "doctor");
            return (Criteria) this;
        }

        public Criteria andDoctorBetween(Integer value1, Integer value2) {
            addCriterion("doctor between", value1, value2, "doctor");
            return (Criteria) this;
        }

        public Criteria andDoctorNotBetween(Integer value1, Integer value2) {
            addCriterion("doctor not between", value1, value2, "doctor");
            return (Criteria) this;
        }

        public Criteria andEvAmountIsNull() {
            addCriterion("ev_amount is null");
            return (Criteria) this;
        }

        public Criteria andEvAmountIsNotNull() {
            addCriterion("ev_amount is not null");
            return (Criteria) this;
        }

        public Criteria andEvAmountEqualTo(Integer value) {
            addCriterion("ev_amount =", value, "evAmount");
            return (Criteria) this;
        }

        public Criteria andEvAmountNotEqualTo(Integer value) {
            addCriterion("ev_amount <>", value, "evAmount");
            return (Criteria) this;
        }

        public Criteria andEvAmountGreaterThan(Integer value) {
            addCriterion("ev_amount >", value, "evAmount");
            return (Criteria) this;
        }

        public Criteria andEvAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("ev_amount >=", value, "evAmount");
            return (Criteria) this;
        }

        public Criteria andEvAmountLessThan(Integer value) {
            addCriterion("ev_amount <", value, "evAmount");
            return (Criteria) this;
        }

        public Criteria andEvAmountLessThanOrEqualTo(Integer value) {
            addCriterion("ev_amount <=", value, "evAmount");
            return (Criteria) this;
        }

        public Criteria andEvAmountIn(List<Integer> values) {
            addCriterion("ev_amount in", values, "evAmount");
            return (Criteria) this;
        }

        public Criteria andEvAmountNotIn(List<Integer> values) {
            addCriterion("ev_amount not in", values, "evAmount");
            return (Criteria) this;
        }

        public Criteria andEvAmountBetween(Integer value1, Integer value2) {
            addCriterion("ev_amount between", value1, value2, "evAmount");
            return (Criteria) this;
        }

        public Criteria andEvAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("ev_amount not between", value1, value2, "evAmount");
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