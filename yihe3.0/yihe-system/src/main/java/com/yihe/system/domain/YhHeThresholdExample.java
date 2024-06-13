package com.yihe.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class YhHeThresholdExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public YhHeThresholdExample() {
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
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("TYPE =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("TYPE <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("TYPE >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TYPE >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("TYPE <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("TYPE <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("TYPE like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("TYPE not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("TYPE in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("TYPE not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("TYPE not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andLowIsNull() {
            addCriterion("LOW is null");
            return (Criteria) this;
        }

        public Criteria andLowIsNotNull() {
            addCriterion("LOW is not null");
            return (Criteria) this;
        }

        public Criteria andLowEqualTo(String value) {
            addCriterion("LOW =", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowNotEqualTo(String value) {
            addCriterion("LOW <>", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowGreaterThan(String value) {
            addCriterion("LOW >", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowGreaterThanOrEqualTo(String value) {
            addCriterion("LOW >=", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowLessThan(String value) {
            addCriterion("LOW <", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowLessThanOrEqualTo(String value) {
            addCriterion("LOW <=", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowLike(String value) {
            addCriterion("LOW like", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowNotLike(String value) {
            addCriterion("LOW not like", value, "low");
            return (Criteria) this;
        }

        public Criteria andLowIn(List<String> values) {
            addCriterion("LOW in", values, "low");
            return (Criteria) this;
        }

        public Criteria andLowNotIn(List<String> values) {
            addCriterion("LOW not in", values, "low");
            return (Criteria) this;
        }

        public Criteria andLowBetween(String value1, String value2) {
            addCriterion("LOW between", value1, value2, "low");
            return (Criteria) this;
        }

        public Criteria andLowNotBetween(String value1, String value2) {
            addCriterion("LOW not between", value1, value2, "low");
            return (Criteria) this;
        }

        public Criteria andHighIsNull() {
            addCriterion("HIGH is null");
            return (Criteria) this;
        }

        public Criteria andHighIsNotNull() {
            addCriterion("HIGH is not null");
            return (Criteria) this;
        }

        public Criteria andHighEqualTo(String value) {
            addCriterion("HIGH =", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighNotEqualTo(String value) {
            addCriterion("HIGH <>", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighGreaterThan(String value) {
            addCriterion("HIGH >", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighGreaterThanOrEqualTo(String value) {
            addCriterion("HIGH >=", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighLessThan(String value) {
            addCriterion("HIGH <", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighLessThanOrEqualTo(String value) {
            addCriterion("HIGH <=", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighLike(String value) {
            addCriterion("HIGH like", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighNotLike(String value) {
            addCriterion("HIGH not like", value, "high");
            return (Criteria) this;
        }

        public Criteria andHighIn(List<String> values) {
            addCriterion("HIGH in", values, "high");
            return (Criteria) this;
        }

        public Criteria andHighNotIn(List<String> values) {
            addCriterion("HIGH not in", values, "high");
            return (Criteria) this;
        }

        public Criteria andHighBetween(String value1, String value2) {
            addCriterion("HIGH between", value1, value2, "high");
            return (Criteria) this;
        }

        public Criteria andHighNotBetween(String value1, String value2) {
            addCriterion("HIGH not between", value1, value2, "high");
            return (Criteria) this;
        }

        public Criteria andLow1IsNull() {
            addCriterion("LOW1 is null");
            return (Criteria) this;
        }

        public Criteria andLow1IsNotNull() {
            addCriterion("LOW1 is not null");
            return (Criteria) this;
        }

        public Criteria andLow1EqualTo(String value) {
            addCriterion("LOW1 =", value, "low1");
            return (Criteria) this;
        }

        public Criteria andLow1NotEqualTo(String value) {
            addCriterion("LOW1 <>", value, "low1");
            return (Criteria) this;
        }

        public Criteria andLow1GreaterThan(String value) {
            addCriterion("LOW1 >", value, "low1");
            return (Criteria) this;
        }

        public Criteria andLow1GreaterThanOrEqualTo(String value) {
            addCriterion("LOW1 >=", value, "low1");
            return (Criteria) this;
        }

        public Criteria andLow1LessThan(String value) {
            addCriterion("LOW1 <", value, "low1");
            return (Criteria) this;
        }

        public Criteria andLow1LessThanOrEqualTo(String value) {
            addCriterion("LOW1 <=", value, "low1");
            return (Criteria) this;
        }

        public Criteria andLow1Like(String value) {
            addCriterion("LOW1 like", value, "low1");
            return (Criteria) this;
        }

        public Criteria andLow1NotLike(String value) {
            addCriterion("LOW1 not like", value, "low1");
            return (Criteria) this;
        }

        public Criteria andLow1In(List<String> values) {
            addCriterion("LOW1 in", values, "low1");
            return (Criteria) this;
        }

        public Criteria andLow1NotIn(List<String> values) {
            addCriterion("LOW1 not in", values, "low1");
            return (Criteria) this;
        }

        public Criteria andLow1Between(String value1, String value2) {
            addCriterion("LOW1 between", value1, value2, "low1");
            return (Criteria) this;
        }

        public Criteria andLow1NotBetween(String value1, String value2) {
            addCriterion("LOW1 not between", value1, value2, "low1");
            return (Criteria) this;
        }

        public Criteria andHigh1IsNull() {
            addCriterion("HIGH1 is null");
            return (Criteria) this;
        }

        public Criteria andHigh1IsNotNull() {
            addCriterion("HIGH1 is not null");
            return (Criteria) this;
        }

        public Criteria andHigh1EqualTo(String value) {
            addCriterion("HIGH1 =", value, "high1");
            return (Criteria) this;
        }

        public Criteria andHigh1NotEqualTo(String value) {
            addCriterion("HIGH1 <>", value, "high1");
            return (Criteria) this;
        }

        public Criteria andHigh1GreaterThan(String value) {
            addCriterion("HIGH1 >", value, "high1");
            return (Criteria) this;
        }

        public Criteria andHigh1GreaterThanOrEqualTo(String value) {
            addCriterion("HIGH1 >=", value, "high1");
            return (Criteria) this;
        }

        public Criteria andHigh1LessThan(String value) {
            addCriterion("HIGH1 <", value, "high1");
            return (Criteria) this;
        }

        public Criteria andHigh1LessThanOrEqualTo(String value) {
            addCriterion("HIGH1 <=", value, "high1");
            return (Criteria) this;
        }

        public Criteria andHigh1Like(String value) {
            addCriterion("HIGH1 like", value, "high1");
            return (Criteria) this;
        }

        public Criteria andHigh1NotLike(String value) {
            addCriterion("HIGH1 not like", value, "high1");
            return (Criteria) this;
        }

        public Criteria andHigh1In(List<String> values) {
            addCriterion("HIGH1 in", values, "high1");
            return (Criteria) this;
        }

        public Criteria andHigh1NotIn(List<String> values) {
            addCriterion("HIGH1 not in", values, "high1");
            return (Criteria) this;
        }

        public Criteria andHigh1Between(String value1, String value2) {
            addCriterion("HIGH1 between", value1, value2, "high1");
            return (Criteria) this;
        }

        public Criteria andHigh1NotBetween(String value1, String value2) {
            addCriterion("HIGH1 not between", value1, value2, "high1");
            return (Criteria) this;
        }

        public Criteria andLow2IsNull() {
            addCriterion("LOW2 is null");
            return (Criteria) this;
        }

        public Criteria andLow2IsNotNull() {
            addCriterion("LOW2 is not null");
            return (Criteria) this;
        }

        public Criteria andLow2EqualTo(String value) {
            addCriterion("LOW2 =", value, "low2");
            return (Criteria) this;
        }

        public Criteria andLow2NotEqualTo(String value) {
            addCriterion("LOW2 <>", value, "low2");
            return (Criteria) this;
        }

        public Criteria andLow2GreaterThan(String value) {
            addCriterion("LOW2 >", value, "low2");
            return (Criteria) this;
        }

        public Criteria andLow2GreaterThanOrEqualTo(String value) {
            addCriterion("LOW2 >=", value, "low2");
            return (Criteria) this;
        }

        public Criteria andLow2LessThan(String value) {
            addCriterion("LOW2 <", value, "low2");
            return (Criteria) this;
        }

        public Criteria andLow2LessThanOrEqualTo(String value) {
            addCriterion("LOW2 <=", value, "low2");
            return (Criteria) this;
        }

        public Criteria andLow2Like(String value) {
            addCriterion("LOW2 like", value, "low2");
            return (Criteria) this;
        }

        public Criteria andLow2NotLike(String value) {
            addCriterion("LOW2 not like", value, "low2");
            return (Criteria) this;
        }

        public Criteria andLow2In(List<String> values) {
            addCriterion("LOW2 in", values, "low2");
            return (Criteria) this;
        }

        public Criteria andLow2NotIn(List<String> values) {
            addCriterion("LOW2 not in", values, "low2");
            return (Criteria) this;
        }

        public Criteria andLow2Between(String value1, String value2) {
            addCriterion("LOW2 between", value1, value2, "low2");
            return (Criteria) this;
        }

        public Criteria andLow2NotBetween(String value1, String value2) {
            addCriterion("LOW2 not between", value1, value2, "low2");
            return (Criteria) this;
        }

        public Criteria andHigh2IsNull() {
            addCriterion("HIGH2 is null");
            return (Criteria) this;
        }

        public Criteria andHigh2IsNotNull() {
            addCriterion("HIGH2 is not null");
            return (Criteria) this;
        }

        public Criteria andHigh2EqualTo(String value) {
            addCriterion("HIGH2 =", value, "high2");
            return (Criteria) this;
        }

        public Criteria andHigh2NotEqualTo(String value) {
            addCriterion("HIGH2 <>", value, "high2");
            return (Criteria) this;
        }

        public Criteria andHigh2GreaterThan(String value) {
            addCriterion("HIGH2 >", value, "high2");
            return (Criteria) this;
        }

        public Criteria andHigh2GreaterThanOrEqualTo(String value) {
            addCriterion("HIGH2 >=", value, "high2");
            return (Criteria) this;
        }

        public Criteria andHigh2LessThan(String value) {
            addCriterion("HIGH2 <", value, "high2");
            return (Criteria) this;
        }

        public Criteria andHigh2LessThanOrEqualTo(String value) {
            addCriterion("HIGH2 <=", value, "high2");
            return (Criteria) this;
        }

        public Criteria andHigh2Like(String value) {
            addCriterion("HIGH2 like", value, "high2");
            return (Criteria) this;
        }

        public Criteria andHigh2NotLike(String value) {
            addCriterion("HIGH2 not like", value, "high2");
            return (Criteria) this;
        }

        public Criteria andHigh2In(List<String> values) {
            addCriterion("HIGH2 in", values, "high2");
            return (Criteria) this;
        }

        public Criteria andHigh2NotIn(List<String> values) {
            addCriterion("HIGH2 not in", values, "high2");
            return (Criteria) this;
        }

        public Criteria andHigh2Between(String value1, String value2) {
            addCriterion("HIGH2 between", value1, value2, "high2");
            return (Criteria) this;
        }

        public Criteria andHigh2NotBetween(String value1, String value2) {
            addCriterion("HIGH2 not between", value1, value2, "high2");
            return (Criteria) this;
        }

        public Criteria andInvalidLowIsNull() {
            addCriterion("INVALID_LOW is null");
            return (Criteria) this;
        }

        public Criteria andInvalidLowIsNotNull() {
            addCriterion("INVALID_LOW is not null");
            return (Criteria) this;
        }

        public Criteria andInvalidLowEqualTo(String value) {
            addCriterion("INVALID_LOW =", value, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidLowNotEqualTo(String value) {
            addCriterion("INVALID_LOW <>", value, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidLowGreaterThan(String value) {
            addCriterion("INVALID_LOW >", value, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidLowGreaterThanOrEqualTo(String value) {
            addCriterion("INVALID_LOW >=", value, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidLowLessThan(String value) {
            addCriterion("INVALID_LOW <", value, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidLowLessThanOrEqualTo(String value) {
            addCriterion("INVALID_LOW <=", value, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidLowLike(String value) {
            addCriterion("INVALID_LOW like", value, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidLowNotLike(String value) {
            addCriterion("INVALID_LOW not like", value, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidLowIn(List<String> values) {
            addCriterion("INVALID_LOW in", values, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidLowNotIn(List<String> values) {
            addCriterion("INVALID_LOW not in", values, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidLowBetween(String value1, String value2) {
            addCriterion("INVALID_LOW between", value1, value2, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidLowNotBetween(String value1, String value2) {
            addCriterion("INVALID_LOW not between", value1, value2, "invalidLow");
            return (Criteria) this;
        }

        public Criteria andInvalidHighIsNull() {
            addCriterion("INVALID_HIGH is null");
            return (Criteria) this;
        }

        public Criteria andInvalidHighIsNotNull() {
            addCriterion("INVALID_HIGH is not null");
            return (Criteria) this;
        }

        public Criteria andInvalidHighEqualTo(String value) {
            addCriterion("INVALID_HIGH =", value, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andInvalidHighNotEqualTo(String value) {
            addCriterion("INVALID_HIGH <>", value, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andInvalidHighGreaterThan(String value) {
            addCriterion("INVALID_HIGH >", value, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andInvalidHighGreaterThanOrEqualTo(String value) {
            addCriterion("INVALID_HIGH >=", value, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andInvalidHighLessThan(String value) {
            addCriterion("INVALID_HIGH <", value, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andInvalidHighLessThanOrEqualTo(String value) {
            addCriterion("INVALID_HIGH <=", value, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andInvalidHighLike(String value) {
            addCriterion("INVALID_HIGH like", value, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andInvalidHighNotLike(String value) {
            addCriterion("INVALID_HIGH not like", value, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andInvalidHighIn(List<String> values) {
            addCriterion("INVALID_HIGH in", values, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andInvalidHighNotIn(List<String> values) {
            addCriterion("INVALID_HIGH not in", values, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andInvalidHighBetween(String value1, String value2) {
            addCriterion("INVALID_HIGH between", value1, value2, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andInvalidHighNotBetween(String value1, String value2) {
            addCriterion("INVALID_HIGH not between", value1, value2, "invalidHigh");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("DESCRIPTION is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("DESCRIPTION is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("DESCRIPTION =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("DESCRIPTION <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("DESCRIPTION >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("DESCRIPTION <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("DESCRIPTION like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("DESCRIPTION not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("DESCRIPTION in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("DESCRIPTION not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("DESCRIPTION between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("DESCRIPTION not between", value1, value2, "description");
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