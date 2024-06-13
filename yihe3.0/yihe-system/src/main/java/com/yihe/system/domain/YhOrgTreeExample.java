package com.yihe.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YhOrgTreeExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public YhOrgTreeExample() {
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

        public Criteria andOrgCodeIsNull() {
            addCriterion("Org_Code is null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIsNotNull() {
            addCriterion("Org_Code is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeEqualTo(String value) {
            addCriterion("Org_Code =", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotEqualTo(String value) {
            addCriterion("Org_Code <>", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThan(String value) {
            addCriterion("Org_Code >", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("Org_Code >=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThan(String value) {
            addCriterion("Org_Code <", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThanOrEqualTo(String value) {
            addCriterion("Org_Code <=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLike(String value) {
            addCriterion("Org_Code like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotLike(String value) {
            addCriterion("Org_Code not like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIn(List<String> values) {
            addCriterion("Org_Code in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotIn(List<String> values) {
            addCriterion("Org_Code not in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeBetween(String value1, String value2) {
            addCriterion("Org_Code between", value1, value2, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotBetween(String value1, String value2) {
            addCriterion("Org_Code not between", value1, value2, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNull() {
            addCriterion("Org_Name is null");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNotNull() {
            addCriterion("Org_Name is not null");
            return (Criteria) this;
        }

        public Criteria andOrgNameEqualTo(String value) {
            addCriterion("Org_Name =", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotEqualTo(String value) {
            addCriterion("Org_Name <>", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThan(String value) {
            addCriterion("Org_Name >", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("Org_Name >=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThan(String value) {
            addCriterion("Org_Name <", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThanOrEqualTo(String value) {
            addCriterion("Org_Name <=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLike(String value) {
            addCriterion("Org_Name like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotLike(String value) {
            addCriterion("Org_Name not like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameIn(List<String> values) {
            addCriterion("Org_Name in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotIn(List<String> values) {
            addCriterion("Org_Name not in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameBetween(String value1, String value2) {
            addCriterion("Org_Name between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotBetween(String value1, String value2) {
            addCriterion("Org_Name not between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeIsNull() {
            addCriterion("Super_Org_Code is null");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeIsNotNull() {
            addCriterion("Super_Org_Code is not null");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeEqualTo(String value) {
            addCriterion("Super_Org_Code =", value, "superOrgCode");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeNotEqualTo(String value) {
            addCriterion("Super_Org_Code <>", value, "superOrgCode");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeGreaterThan(String value) {
            addCriterion("Super_Org_Code >", value, "superOrgCode");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("Super_Org_Code >=", value, "superOrgCode");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeLessThan(String value) {
            addCriterion("Super_Org_Code <", value, "superOrgCode");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeLessThanOrEqualTo(String value) {
            addCriterion("Super_Org_Code <=", value, "superOrgCode");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeLike(String value) {
            addCriterion("Super_Org_Code like", value, "superOrgCode");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeNotLike(String value) {
            addCriterion("Super_Org_Code not like", value, "superOrgCode");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeIn(List<String> values) {
            addCriterion("Super_Org_Code in", values, "superOrgCode");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeNotIn(List<String> values) {
            addCriterion("Super_Org_Code not in", values, "superOrgCode");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeBetween(String value1, String value2) {
            addCriterion("Super_Org_Code between", value1, value2, "superOrgCode");
            return (Criteria) this;
        }

        public Criteria andSuperOrgCodeNotBetween(String value1, String value2) {
            addCriterion("Super_Org_Code not between", value1, value2, "superOrgCode");
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

        public Criteria andAddDateIsNull() {
            addCriterion("Add_Date is null");
            return (Criteria) this;
        }

        public Criteria andAddDateIsNotNull() {
            addCriterion("Add_Date is not null");
            return (Criteria) this;
        }

        public Criteria andAddDateEqualTo(Date value) {
            addCriterion("Add_Date =", value, "addDate");
            return (Criteria) this;
        }

        public Criteria andAddDateNotEqualTo(Date value) {
            addCriterion("Add_Date <>", value, "addDate");
            return (Criteria) this;
        }

        public Criteria andAddDateGreaterThan(Date value) {
            addCriterion("Add_Date >", value, "addDate");
            return (Criteria) this;
        }

        public Criteria andAddDateGreaterThanOrEqualTo(Date value) {
            addCriterion("Add_Date >=", value, "addDate");
            return (Criteria) this;
        }

        public Criteria andAddDateLessThan(Date value) {
            addCriterion("Add_Date <", value, "addDate");
            return (Criteria) this;
        }

        public Criteria andAddDateLessThanOrEqualTo(Date value) {
            addCriterion("Add_Date <=", value, "addDate");
            return (Criteria) this;
        }

        public Criteria andAddDateIn(List<Date> values) {
            addCriterion("Add_Date in", values, "addDate");
            return (Criteria) this;
        }

        public Criteria andAddDateNotIn(List<Date> values) {
            addCriterion("Add_Date not in", values, "addDate");
            return (Criteria) this;
        }

        public Criteria andAddDateBetween(Date value1, Date value2) {
            addCriterion("Add_Date between", value1, value2, "addDate");
            return (Criteria) this;
        }

        public Criteria andAddDateNotBetween(Date value1, Date value2) {
            addCriterion("Add_Date not between", value1, value2, "addDate");
            return (Criteria) this;
        }

        public Criteria andClassNumberIsNull() {
            addCriterion("Class_Number is null");
            return (Criteria) this;
        }

        public Criteria andClassNumberIsNotNull() {
            addCriterion("Class_Number is not null");
            return (Criteria) this;
        }

        public Criteria andClassNumberEqualTo(Integer value) {
            addCriterion("Class_Number =", value, "classNumber");
            return (Criteria) this;
        }

        public Criteria andClassNumberNotEqualTo(Integer value) {
            addCriterion("Class_Number <>", value, "classNumber");
            return (Criteria) this;
        }

        public Criteria andClassNumberGreaterThan(Integer value) {
            addCriterion("Class_Number >", value, "classNumber");
            return (Criteria) this;
        }

        public Criteria andClassNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("Class_Number >=", value, "classNumber");
            return (Criteria) this;
        }

        public Criteria andClassNumberLessThan(Integer value) {
            addCriterion("Class_Number <", value, "classNumber");
            return (Criteria) this;
        }

        public Criteria andClassNumberLessThanOrEqualTo(Integer value) {
            addCriterion("Class_Number <=", value, "classNumber");
            return (Criteria) this;
        }

        public Criteria andClassNumberIn(List<Integer> values) {
            addCriterion("Class_Number in", values, "classNumber");
            return (Criteria) this;
        }

        public Criteria andClassNumberNotIn(List<Integer> values) {
            addCriterion("Class_Number not in", values, "classNumber");
            return (Criteria) this;
        }

        public Criteria andClassNumberBetween(Integer value1, Integer value2) {
            addCriterion("Class_Number between", value1, value2, "classNumber");
            return (Criteria) this;
        }

        public Criteria andClassNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("Class_Number not between", value1, value2, "classNumber");
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

        public Criteria andDocdorCodeIsNull() {
            addCriterion("Docdor_Code is null");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeIsNotNull() {
            addCriterion("Docdor_Code is not null");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeEqualTo(String value) {
            addCriterion("Docdor_Code =", value, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeNotEqualTo(String value) {
            addCriterion("Docdor_Code <>", value, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeGreaterThan(String value) {
            addCriterion("Docdor_Code >", value, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("Docdor_Code >=", value, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeLessThan(String value) {
            addCriterion("Docdor_Code <", value, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeLessThanOrEqualTo(String value) {
            addCriterion("Docdor_Code <=", value, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeLike(String value) {
            addCriterion("Docdor_Code like", value, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeNotLike(String value) {
            addCriterion("Docdor_Code not like", value, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeIn(List<String> values) {
            addCriterion("Docdor_Code in", values, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeNotIn(List<String> values) {
            addCriterion("Docdor_Code not in", values, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeBetween(String value1, String value2) {
            addCriterion("Docdor_Code between", value1, value2, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andDocdorCodeNotBetween(String value1, String value2) {
            addCriterion("Docdor_Code not between", value1, value2, "docdorCode");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdIsNull() {
            addCriterion("org_tree_upload_id is null");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdIsNotNull() {
            addCriterion("org_tree_upload_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdEqualTo(Integer value) {
            addCriterion("org_tree_upload_id =", value, "orgTreeUploadId");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdNotEqualTo(Integer value) {
            addCriterion("org_tree_upload_id <>", value, "orgTreeUploadId");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdGreaterThan(Integer value) {
            addCriterion("org_tree_upload_id >", value, "orgTreeUploadId");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("org_tree_upload_id >=", value, "orgTreeUploadId");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdLessThan(Integer value) {
            addCriterion("org_tree_upload_id <", value, "orgTreeUploadId");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdLessThanOrEqualTo(Integer value) {
            addCriterion("org_tree_upload_id <=", value, "orgTreeUploadId");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdIn(List<Integer> values) {
            addCriterion("org_tree_upload_id in", values, "orgTreeUploadId");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdNotIn(List<Integer> values) {
            addCriterion("org_tree_upload_id not in", values, "orgTreeUploadId");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdBetween(Integer value1, Integer value2) {
            addCriterion("org_tree_upload_id between", value1, value2, "orgTreeUploadId");
            return (Criteria) this;
        }

        public Criteria andOrgTreeUploadIdNotBetween(Integer value1, Integer value2) {
            addCriterion("org_tree_upload_id not between", value1, value2, "orgTreeUploadId");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeIsNull() {
            addCriterion("org_customer_code is null");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeIsNotNull() {
            addCriterion("org_customer_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeEqualTo(String value) {
            addCriterion("org_customer_code =", value, "orgCustomerCode");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeNotEqualTo(String value) {
            addCriterion("org_customer_code <>", value, "orgCustomerCode");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeGreaterThan(String value) {
            addCriterion("org_customer_code >", value, "orgCustomerCode");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeGreaterThanOrEqualTo(String value) {
            addCriterion("org_customer_code >=", value, "orgCustomerCode");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeLessThan(String value) {
            addCriterion("org_customer_code <", value, "orgCustomerCode");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeLessThanOrEqualTo(String value) {
            addCriterion("org_customer_code <=", value, "orgCustomerCode");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeLike(String value) {
            addCriterion("org_customer_code like", value, "orgCustomerCode");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeNotLike(String value) {
            addCriterion("org_customer_code not like", value, "orgCustomerCode");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeIn(List<String> values) {
            addCriterion("org_customer_code in", values, "orgCustomerCode");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeNotIn(List<String> values) {
            addCriterion("org_customer_code not in", values, "orgCustomerCode");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeBetween(String value1, String value2) {
            addCriterion("org_customer_code between", value1, value2, "orgCustomerCode");
            return (Criteria) this;
        }

        public Criteria andOrgCustomerCodeNotBetween(String value1, String value2) {
            addCriterion("org_customer_code not between", value1, value2, "orgCustomerCode");
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