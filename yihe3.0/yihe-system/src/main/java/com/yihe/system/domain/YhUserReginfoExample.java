package com.yihe.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YhUserReginfoExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public YhUserReginfoExample() {
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

        public Criteria andUserPwdIsNull() {
            addCriterion("user_pwd is null");
            return (Criteria) this;
        }

        public Criteria andUserPwdIsNotNull() {
            addCriterion("user_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andUserPwdEqualTo(String value) {
            addCriterion("user_pwd =", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdNotEqualTo(String value) {
            addCriterion("user_pwd <>", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdGreaterThan(String value) {
            addCriterion("user_pwd >", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdGreaterThanOrEqualTo(String value) {
            addCriterion("user_pwd >=", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdLessThan(String value) {
            addCriterion("user_pwd <", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdLessThanOrEqualTo(String value) {
            addCriterion("user_pwd <=", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdLike(String value) {
            addCriterion("user_pwd like", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdNotLike(String value) {
            addCriterion("user_pwd not like", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdIn(List<String> values) {
            addCriterion("user_pwd in", values, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdNotIn(List<String> values) {
            addCriterion("user_pwd not in", values, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdBetween(String value1, String value2) {
            addCriterion("user_pwd between", value1, value2, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdNotBetween(String value1, String value2) {
            addCriterion("user_pwd not between", value1, value2, "userPwd");
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

        public Criteria andBirthDateIsNull() {
            addCriterion("birth_date is null");
            return (Criteria) this;
        }

        public Criteria andBirthDateIsNotNull() {
            addCriterion("birth_date is not null");
            return (Criteria) this;
        }

        public Criteria andBirthDateEqualTo(Date value) {
            addCriterion("birth_date =", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateNotEqualTo(Date value) {
            addCriterion("birth_date <>", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateGreaterThan(Date value) {
            addCriterion("birth_date >", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateGreaterThanOrEqualTo(Date value) {
            addCriterion("birth_date >=", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateLessThan(Date value) {
            addCriterion("birth_date <", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateLessThanOrEqualTo(Date value) {
            addCriterion("birth_date <=", value, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateIn(List<Date> values) {
            addCriterion("birth_date in", values, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateNotIn(List<Date> values) {
            addCriterion("birth_date not in", values, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateBetween(Date value1, Date value2) {
            addCriterion("birth_date between", value1, value2, "birthDate");
            return (Criteria) this;
        }

        public Criteria andBirthDateNotBetween(Date value1, Date value2) {
            addCriterion("birth_date not between", value1, value2, "birthDate");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIsNull() {
            addCriterion("province_id is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIsNotNull() {
            addCriterion("province_id is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceIdEqualTo(String value) {
            addCriterion("province_id =", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotEqualTo(String value) {
            addCriterion("province_id <>", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdGreaterThan(String value) {
            addCriterion("province_id >", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdGreaterThanOrEqualTo(String value) {
            addCriterion("province_id >=", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLessThan(String value) {
            addCriterion("province_id <", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLessThanOrEqualTo(String value) {
            addCriterion("province_id <=", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLike(String value) {
            addCriterion("province_id like", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotLike(String value) {
            addCriterion("province_id not like", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIn(List<String> values) {
            addCriterion("province_id in", values, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotIn(List<String> values) {
            addCriterion("province_id not in", values, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdBetween(String value1, String value2) {
            addCriterion("province_id between", value1, value2, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotBetween(String value1, String value2) {
            addCriterion("province_id not between", value1, value2, "provinceId");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNull() {
            addCriterion("city_id is null");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNotNull() {
            addCriterion("city_id is not null");
            return (Criteria) this;
        }

        public Criteria andCityIdEqualTo(String value) {
            addCriterion("city_id =", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotEqualTo(String value) {
            addCriterion("city_id <>", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThan(String value) {
            addCriterion("city_id >", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThanOrEqualTo(String value) {
            addCriterion("city_id >=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThan(String value) {
            addCriterion("city_id <", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThanOrEqualTo(String value) {
            addCriterion("city_id <=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLike(String value) {
            addCriterion("city_id like", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotLike(String value) {
            addCriterion("city_id not like", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdIn(List<String> values) {
            addCriterion("city_id in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotIn(List<String> values) {
            addCriterion("city_id not in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdBetween(String value1, String value2) {
            addCriterion("city_id between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotBetween(String value1, String value2) {
            addCriterion("city_id not between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNull() {
            addCriterion("area_id is null");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNotNull() {
            addCriterion("area_id is not null");
            return (Criteria) this;
        }

        public Criteria andAreaIdEqualTo(String value) {
            addCriterion("area_id =", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotEqualTo(String value) {
            addCriterion("area_id <>", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThan(String value) {
            addCriterion("area_id >", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThanOrEqualTo(String value) {
            addCriterion("area_id >=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThan(String value) {
            addCriterion("area_id <", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThanOrEqualTo(String value) {
            addCriterion("area_id <=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLike(String value) {
            addCriterion("area_id like", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotLike(String value) {
            addCriterion("area_id not like", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdIn(List<String> values) {
            addCriterion("area_id in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotIn(List<String> values) {
            addCriterion("area_id not in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdBetween(String value1, String value2) {
            addCriterion("area_id between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotBetween(String value1, String value2) {
            addCriterion("area_id not between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andRegTimeIsNull() {
            addCriterion("reg_time is null");
            return (Criteria) this;
        }

        public Criteria andRegTimeIsNotNull() {
            addCriterion("reg_time is not null");
            return (Criteria) this;
        }

        public Criteria andRegTimeEqualTo(Date value) {
            addCriterion("reg_time =", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeNotEqualTo(Date value) {
            addCriterion("reg_time <>", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeGreaterThan(Date value) {
            addCriterion("reg_time >", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("reg_time >=", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeLessThan(Date value) {
            addCriterion("reg_time <", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeLessThanOrEqualTo(Date value) {
            addCriterion("reg_time <=", value, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeIn(List<Date> values) {
            addCriterion("reg_time in", values, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeNotIn(List<Date> values) {
            addCriterion("reg_time not in", values, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeBetween(Date value1, Date value2) {
            addCriterion("reg_time between", value1, value2, "regTime");
            return (Criteria) this;
        }

        public Criteria andRegTimeNotBetween(Date value1, Date value2) {
            addCriterion("reg_time not between", value1, value2, "regTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimesIsNull() {
            addCriterion("Login_times is null");
            return (Criteria) this;
        }

        public Criteria andLoginTimesIsNotNull() {
            addCriterion("Login_times is not null");
            return (Criteria) this;
        }

        public Criteria andLoginTimesEqualTo(Integer value) {
            addCriterion("Login_times =", value, "loginTimes");
            return (Criteria) this;
        }

        public Criteria andLoginTimesNotEqualTo(Integer value) {
            addCriterion("Login_times <>", value, "loginTimes");
            return (Criteria) this;
        }

        public Criteria andLoginTimesGreaterThan(Integer value) {
            addCriterion("Login_times >", value, "loginTimes");
            return (Criteria) this;
        }

        public Criteria andLoginTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("Login_times >=", value, "loginTimes");
            return (Criteria) this;
        }

        public Criteria andLoginTimesLessThan(Integer value) {
            addCriterion("Login_times <", value, "loginTimes");
            return (Criteria) this;
        }

        public Criteria andLoginTimesLessThanOrEqualTo(Integer value) {
            addCriterion("Login_times <=", value, "loginTimes");
            return (Criteria) this;
        }

        public Criteria andLoginTimesIn(List<Integer> values) {
            addCriterion("Login_times in", values, "loginTimes");
            return (Criteria) this;
        }

        public Criteria andLoginTimesNotIn(List<Integer> values) {
            addCriterion("Login_times not in", values, "loginTimes");
            return (Criteria) this;
        }

        public Criteria andLoginTimesBetween(Integer value1, Integer value2) {
            addCriterion("Login_times between", value1, value2, "loginTimes");
            return (Criteria) this;
        }

        public Criteria andLoginTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("Login_times not between", value1, value2, "loginTimes");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andPostCodeIsNull() {
            addCriterion("post_code is null");
            return (Criteria) this;
        }

        public Criteria andPostCodeIsNotNull() {
            addCriterion("post_code is not null");
            return (Criteria) this;
        }

        public Criteria andPostCodeEqualTo(String value) {
            addCriterion("post_code =", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotEqualTo(String value) {
            addCriterion("post_code <>", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeGreaterThan(String value) {
            addCriterion("post_code >", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeGreaterThanOrEqualTo(String value) {
            addCriterion("post_code >=", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLessThan(String value) {
            addCriterion("post_code <", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLessThanOrEqualTo(String value) {
            addCriterion("post_code <=", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLike(String value) {
            addCriterion("post_code like", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotLike(String value) {
            addCriterion("post_code not like", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeIn(List<String> values) {
            addCriterion("post_code in", values, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotIn(List<String> values) {
            addCriterion("post_code not in", values, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeBetween(String value1, String value2) {
            addCriterion("post_code between", value1, value2, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotBetween(String value1, String value2) {
            addCriterion("post_code not between", value1, value2, "postCode");
            return (Criteria) this;
        }

        public Criteria andTelIsNull() {
            addCriterion("tel is null");
            return (Criteria) this;
        }

        public Criteria andTelIsNotNull() {
            addCriterion("tel is not null");
            return (Criteria) this;
        }

        public Criteria andTelEqualTo(String value) {
            addCriterion("tel =", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotEqualTo(String value) {
            addCriterion("tel <>", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelGreaterThan(String value) {
            addCriterion("tel >", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelGreaterThanOrEqualTo(String value) {
            addCriterion("tel >=", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLessThan(String value) {
            addCriterion("tel <", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLessThanOrEqualTo(String value) {
            addCriterion("tel <=", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLike(String value) {
            addCriterion("tel like", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotLike(String value) {
            addCriterion("tel not like", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelIn(List<String> values) {
            addCriterion("tel in", values, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotIn(List<String> values) {
            addCriterion("tel not in", values, "tel");
            return (Criteria) this;
        }

        public Criteria andTelBetween(String value1, String value2) {
            addCriterion("tel between", value1, value2, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotBetween(String value1, String value2) {
            addCriterion("tel not between", value1, value2, "tel");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andOccupationIsNull() {
            addCriterion("occupation is null");
            return (Criteria) this;
        }

        public Criteria andOccupationIsNotNull() {
            addCriterion("occupation is not null");
            return (Criteria) this;
        }

        public Criteria andOccupationEqualTo(Integer value) {
            addCriterion("occupation =", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationNotEqualTo(Integer value) {
            addCriterion("occupation <>", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationGreaterThan(Integer value) {
            addCriterion("occupation >", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationGreaterThanOrEqualTo(Integer value) {
            addCriterion("occupation >=", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationLessThan(Integer value) {
            addCriterion("occupation <", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationLessThanOrEqualTo(Integer value) {
            addCriterion("occupation <=", value, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationIn(List<Integer> values) {
            addCriterion("occupation in", values, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationNotIn(List<Integer> values) {
            addCriterion("occupation not in", values, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationBetween(Integer value1, Integer value2) {
            addCriterion("occupation between", value1, value2, "occupation");
            return (Criteria) this;
        }

        public Criteria andOccupationNotBetween(Integer value1, Integer value2) {
            addCriterion("occupation not between", value1, value2, "occupation");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundIsNull() {
            addCriterion("ed_background is null");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundIsNotNull() {
            addCriterion("ed_background is not null");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundEqualTo(Integer value) {
            addCriterion("ed_background =", value, "edBackground");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundNotEqualTo(Integer value) {
            addCriterion("ed_background <>", value, "edBackground");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundGreaterThan(Integer value) {
            addCriterion("ed_background >", value, "edBackground");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundGreaterThanOrEqualTo(Integer value) {
            addCriterion("ed_background >=", value, "edBackground");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundLessThan(Integer value) {
            addCriterion("ed_background <", value, "edBackground");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundLessThanOrEqualTo(Integer value) {
            addCriterion("ed_background <=", value, "edBackground");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundIn(List<Integer> values) {
            addCriterion("ed_background in", values, "edBackground");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundNotIn(List<Integer> values) {
            addCriterion("ed_background not in", values, "edBackground");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundBetween(Integer value1, Integer value2) {
            addCriterion("ed_background between", value1, value2, "edBackground");
            return (Criteria) this;
        }

        public Criteria andEdBackgroundNotBetween(Integer value1, Integer value2) {
            addCriterion("ed_background not between", value1, value2, "edBackground");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusIsNull() {
            addCriterion("marital_status is null");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusIsNotNull() {
            addCriterion("marital_status is not null");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusEqualTo(Integer value) {
            addCriterion("marital_status =", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusNotEqualTo(Integer value) {
            addCriterion("marital_status <>", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusGreaterThan(Integer value) {
            addCriterion("marital_status >", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("marital_status >=", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusLessThan(Integer value) {
            addCriterion("marital_status <", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusLessThanOrEqualTo(Integer value) {
            addCriterion("marital_status <=", value, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusIn(List<Integer> values) {
            addCriterion("marital_status in", values, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusNotIn(List<Integer> values) {
            addCriterion("marital_status not in", values, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusBetween(Integer value1, Integer value2) {
            addCriterion("marital_status between", value1, value2, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andMaritalStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("marital_status not between", value1, value2, "maritalStatus");
            return (Criteria) this;
        }

        public Criteria andLockFlagIsNull() {
            addCriterion("lock_flag is null");
            return (Criteria) this;
        }

        public Criteria andLockFlagIsNotNull() {
            addCriterion("lock_flag is not null");
            return (Criteria) this;
        }

        public Criteria andLockFlagEqualTo(Integer value) {
            addCriterion("lock_flag =", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagNotEqualTo(Integer value) {
            addCriterion("lock_flag <>", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagGreaterThan(Integer value) {
            addCriterion("lock_flag >", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_flag >=", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagLessThan(Integer value) {
            addCriterion("lock_flag <", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagLessThanOrEqualTo(Integer value) {
            addCriterion("lock_flag <=", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagIn(List<Integer> values) {
            addCriterion("lock_flag in", values, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagNotIn(List<Integer> values) {
            addCriterion("lock_flag not in", values, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagBetween(Integer value1, Integer value2) {
            addCriterion("lock_flag between", value1, value2, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_flag not between", value1, value2, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andRegSourceIsNull() {
            addCriterion("reg_source is null");
            return (Criteria) this;
        }

        public Criteria andRegSourceIsNotNull() {
            addCriterion("reg_source is not null");
            return (Criteria) this;
        }

        public Criteria andRegSourceEqualTo(Integer value) {
            addCriterion("reg_source =", value, "regSource");
            return (Criteria) this;
        }

        public Criteria andRegSourceNotEqualTo(Integer value) {
            addCriterion("reg_source <>", value, "regSource");
            return (Criteria) this;
        }

        public Criteria andRegSourceGreaterThan(Integer value) {
            addCriterion("reg_source >", value, "regSource");
            return (Criteria) this;
        }

        public Criteria andRegSourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("reg_source >=", value, "regSource");
            return (Criteria) this;
        }

        public Criteria andRegSourceLessThan(Integer value) {
            addCriterion("reg_source <", value, "regSource");
            return (Criteria) this;
        }

        public Criteria andRegSourceLessThanOrEqualTo(Integer value) {
            addCriterion("reg_source <=", value, "regSource");
            return (Criteria) this;
        }

        public Criteria andRegSourceIn(List<Integer> values) {
            addCriterion("reg_source in", values, "regSource");
            return (Criteria) this;
        }

        public Criteria andRegSourceNotIn(List<Integer> values) {
            addCriterion("reg_source not in", values, "regSource");
            return (Criteria) this;
        }

        public Criteria andRegSourceBetween(Integer value1, Integer value2) {
            addCriterion("reg_source between", value1, value2, "regSource");
            return (Criteria) this;
        }

        public Criteria andRegSourceNotBetween(Integer value1, Integer value2) {
            addCriterion("reg_source not between", value1, value2, "regSource");
            return (Criteria) this;
        }

        public Criteria andCertTypeIsNull() {
            addCriterion("Cert_Type is null");
            return (Criteria) this;
        }

        public Criteria andCertTypeIsNotNull() {
            addCriterion("Cert_Type is not null");
            return (Criteria) this;
        }

        public Criteria andCertTypeEqualTo(Integer value) {
            addCriterion("Cert_Type =", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotEqualTo(Integer value) {
            addCriterion("Cert_Type <>", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeGreaterThan(Integer value) {
            addCriterion("Cert_Type >", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("Cert_Type >=", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeLessThan(Integer value) {
            addCriterion("Cert_Type <", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeLessThanOrEqualTo(Integer value) {
            addCriterion("Cert_Type <=", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeIn(List<Integer> values) {
            addCriterion("Cert_Type in", values, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotIn(List<Integer> values) {
            addCriterion("Cert_Type not in", values, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeBetween(Integer value1, Integer value2) {
            addCriterion("Cert_Type between", value1, value2, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("Cert_Type not between", value1, value2, "certType");
            return (Criteria) this;
        }

        public Criteria andCertNoIsNull() {
            addCriterion("Cert_NO is null");
            return (Criteria) this;
        }

        public Criteria andCertNoIsNotNull() {
            addCriterion("Cert_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCertNoEqualTo(String value) {
            addCriterion("Cert_NO =", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotEqualTo(String value) {
            addCriterion("Cert_NO <>", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoGreaterThan(String value) {
            addCriterion("Cert_NO >", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoGreaterThanOrEqualTo(String value) {
            addCriterion("Cert_NO >=", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoLessThan(String value) {
            addCriterion("Cert_NO <", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoLessThanOrEqualTo(String value) {
            addCriterion("Cert_NO <=", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoLike(String value) {
            addCriterion("Cert_NO like", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotLike(String value) {
            addCriterion("Cert_NO not like", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoIn(List<String> values) {
            addCriterion("Cert_NO in", values, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotIn(List<String> values) {
            addCriterion("Cert_NO not in", values, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoBetween(String value1, String value2) {
            addCriterion("Cert_NO between", value1, value2, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotBetween(String value1, String value2) {
            addCriterion("Cert_NO not between", value1, value2, "certNo");
            return (Criteria) this;
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

        public Criteria andBloodTypeIsNull() {
            addCriterion("Blood_Type is null");
            return (Criteria) this;
        }

        public Criteria andBloodTypeIsNotNull() {
            addCriterion("Blood_Type is not null");
            return (Criteria) this;
        }

        public Criteria andBloodTypeEqualTo(Integer value) {
            addCriterion("Blood_Type =", value, "bloodType");
            return (Criteria) this;
        }

        public Criteria andBloodTypeNotEqualTo(Integer value) {
            addCriterion("Blood_Type <>", value, "bloodType");
            return (Criteria) this;
        }

        public Criteria andBloodTypeGreaterThan(Integer value) {
            addCriterion("Blood_Type >", value, "bloodType");
            return (Criteria) this;
        }

        public Criteria andBloodTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("Blood_Type >=", value, "bloodType");
            return (Criteria) this;
        }

        public Criteria andBloodTypeLessThan(Integer value) {
            addCriterion("Blood_Type <", value, "bloodType");
            return (Criteria) this;
        }

        public Criteria andBloodTypeLessThanOrEqualTo(Integer value) {
            addCriterion("Blood_Type <=", value, "bloodType");
            return (Criteria) this;
        }

        public Criteria andBloodTypeIn(List<Integer> values) {
            addCriterion("Blood_Type in", values, "bloodType");
            return (Criteria) this;
        }

        public Criteria andBloodTypeNotIn(List<Integer> values) {
            addCriterion("Blood_Type not in", values, "bloodType");
            return (Criteria) this;
        }

        public Criteria andBloodTypeBetween(Integer value1, Integer value2) {
            addCriterion("Blood_Type between", value1, value2, "bloodType");
            return (Criteria) this;
        }

        public Criteria andBloodTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("Blood_Type not between", value1, value2, "bloodType");
            return (Criteria) this;
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

        public Criteria andLoginTimeIsNull() {
            addCriterion("login_time is null");
            return (Criteria) this;
        }

        public Criteria andLoginTimeIsNotNull() {
            addCriterion("login_time is not null");
            return (Criteria) this;
        }

        public Criteria andLoginTimeEqualTo(Date value) {
            addCriterion("login_time =", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotEqualTo(Date value) {
            addCriterion("login_time <>", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeGreaterThan(Date value) {
            addCriterion("login_time >", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("login_time >=", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeLessThan(Date value) {
            addCriterion("login_time <", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeLessThanOrEqualTo(Date value) {
            addCriterion("login_time <=", value, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeIn(List<Date> values) {
            addCriterion("login_time in", values, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotIn(List<Date> values) {
            addCriterion("login_time not in", values, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeBetween(Date value1, Date value2) {
            addCriterion("login_time between", value1, value2, "loginTime");
            return (Criteria) this;
        }

        public Criteria andLoginTimeNotBetween(Date value1, Date value2) {
            addCriterion("login_time not between", value1, value2, "loginTime");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andIsAssociationIsNull() {
            addCriterion("is_association is null");
            return (Criteria) this;
        }

        public Criteria andIsAssociationIsNotNull() {
            addCriterion("is_association is not null");
            return (Criteria) this;
        }

        public Criteria andIsAssociationEqualTo(Integer value) {
            addCriterion("is_association =", value, "isAssociation");
            return (Criteria) this;
        }

        public Criteria andIsAssociationNotEqualTo(Integer value) {
            addCriterion("is_association <>", value, "isAssociation");
            return (Criteria) this;
        }

        public Criteria andIsAssociationGreaterThan(Integer value) {
            addCriterion("is_association >", value, "isAssociation");
            return (Criteria) this;
        }

        public Criteria andIsAssociationGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_association >=", value, "isAssociation");
            return (Criteria) this;
        }

        public Criteria andIsAssociationLessThan(Integer value) {
            addCriterion("is_association <", value, "isAssociation");
            return (Criteria) this;
        }

        public Criteria andIsAssociationLessThanOrEqualTo(Integer value) {
            addCriterion("is_association <=", value, "isAssociation");
            return (Criteria) this;
        }

        public Criteria andIsAssociationIn(List<Integer> values) {
            addCriterion("is_association in", values, "isAssociation");
            return (Criteria) this;
        }

        public Criteria andIsAssociationNotIn(List<Integer> values) {
            addCriterion("is_association not in", values, "isAssociation");
            return (Criteria) this;
        }

        public Criteria andIsAssociationBetween(Integer value1, Integer value2) {
            addCriterion("is_association between", value1, value2, "isAssociation");
            return (Criteria) this;
        }

        public Criteria andIsAssociationNotBetween(Integer value1, Integer value2) {
            addCriterion("is_association not between", value1, value2, "isAssociation");
            return (Criteria) this;
        }

        public Criteria andAssociationNameIsNull() {
            addCriterion("association_name is null");
            return (Criteria) this;
        }

        public Criteria andAssociationNameIsNotNull() {
            addCriterion("association_name is not null");
            return (Criteria) this;
        }

        public Criteria andAssociationNameEqualTo(String value) {
            addCriterion("association_name =", value, "associationName");
            return (Criteria) this;
        }

        public Criteria andAssociationNameNotEqualTo(String value) {
            addCriterion("association_name <>", value, "associationName");
            return (Criteria) this;
        }

        public Criteria andAssociationNameGreaterThan(String value) {
            addCriterion("association_name >", value, "associationName");
            return (Criteria) this;
        }

        public Criteria andAssociationNameGreaterThanOrEqualTo(String value) {
            addCriterion("association_name >=", value, "associationName");
            return (Criteria) this;
        }

        public Criteria andAssociationNameLessThan(String value) {
            addCriterion("association_name <", value, "associationName");
            return (Criteria) this;
        }

        public Criteria andAssociationNameLessThanOrEqualTo(String value) {
            addCriterion("association_name <=", value, "associationName");
            return (Criteria) this;
        }

        public Criteria andAssociationNameLike(String value) {
            addCriterion("association_name like", value, "associationName");
            return (Criteria) this;
        }

        public Criteria andAssociationNameNotLike(String value) {
            addCriterion("association_name not like", value, "associationName");
            return (Criteria) this;
        }

        public Criteria andAssociationNameIn(List<String> values) {
            addCriterion("association_name in", values, "associationName");
            return (Criteria) this;
        }

        public Criteria andAssociationNameNotIn(List<String> values) {
            addCriterion("association_name not in", values, "associationName");
            return (Criteria) this;
        }

        public Criteria andAssociationNameBetween(String value1, String value2) {
            addCriterion("association_name between", value1, value2, "associationName");
            return (Criteria) this;
        }

        public Criteria andAssociationNameNotBetween(String value1, String value2) {
            addCriterion("association_name not between", value1, value2, "associationName");
            return (Criteria) this;
        }

        public Criteria andIsAloneIsNull() {
            addCriterion("is_alone is null");
            return (Criteria) this;
        }

        public Criteria andIsAloneIsNotNull() {
            addCriterion("is_alone is not null");
            return (Criteria) this;
        }

        public Criteria andIsAloneEqualTo(Integer value) {
            addCriterion("is_alone =", value, "isAlone");
            return (Criteria) this;
        }

        public Criteria andIsAloneNotEqualTo(Integer value) {
            addCriterion("is_alone <>", value, "isAlone");
            return (Criteria) this;
        }

        public Criteria andIsAloneGreaterThan(Integer value) {
            addCriterion("is_alone >", value, "isAlone");
            return (Criteria) this;
        }

        public Criteria andIsAloneGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_alone >=", value, "isAlone");
            return (Criteria) this;
        }

        public Criteria andIsAloneLessThan(Integer value) {
            addCriterion("is_alone <", value, "isAlone");
            return (Criteria) this;
        }

        public Criteria andIsAloneLessThanOrEqualTo(Integer value) {
            addCriterion("is_alone <=", value, "isAlone");
            return (Criteria) this;
        }

        public Criteria andIsAloneIn(List<Integer> values) {
            addCriterion("is_alone in", values, "isAlone");
            return (Criteria) this;
        }

        public Criteria andIsAloneNotIn(List<Integer> values) {
            addCriterion("is_alone not in", values, "isAlone");
            return (Criteria) this;
        }

        public Criteria andIsAloneBetween(Integer value1, Integer value2) {
            addCriterion("is_alone between", value1, value2, "isAlone");
            return (Criteria) this;
        }

        public Criteria andIsAloneNotBetween(Integer value1, Integer value2) {
            addCriterion("is_alone not between", value1, value2, "isAlone");
            return (Criteria) this;
        }

        public Criteria andIsAllergyIsNull() {
            addCriterion("is_allergy is null");
            return (Criteria) this;
        }

        public Criteria andIsAllergyIsNotNull() {
            addCriterion("is_allergy is not null");
            return (Criteria) this;
        }

        public Criteria andIsAllergyEqualTo(Integer value) {
            addCriterion("is_allergy =", value, "isAllergy");
            return (Criteria) this;
        }

        public Criteria andIsAllergyNotEqualTo(Integer value) {
            addCriterion("is_allergy <>", value, "isAllergy");
            return (Criteria) this;
        }

        public Criteria andIsAllergyGreaterThan(Integer value) {
            addCriterion("is_allergy >", value, "isAllergy");
            return (Criteria) this;
        }

        public Criteria andIsAllergyGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_allergy >=", value, "isAllergy");
            return (Criteria) this;
        }

        public Criteria andIsAllergyLessThan(Integer value) {
            addCriterion("is_allergy <", value, "isAllergy");
            return (Criteria) this;
        }

        public Criteria andIsAllergyLessThanOrEqualTo(Integer value) {
            addCriterion("is_allergy <=", value, "isAllergy");
            return (Criteria) this;
        }

        public Criteria andIsAllergyIn(List<Integer> values) {
            addCriterion("is_allergy in", values, "isAllergy");
            return (Criteria) this;
        }

        public Criteria andIsAllergyNotIn(List<Integer> values) {
            addCriterion("is_allergy not in", values, "isAllergy");
            return (Criteria) this;
        }

        public Criteria andIsAllergyBetween(Integer value1, Integer value2) {
            addCriterion("is_allergy between", value1, value2, "isAllergy");
            return (Criteria) this;
        }

        public Criteria andIsAllergyNotBetween(Integer value1, Integer value2) {
            addCriterion("is_allergy not between", value1, value2, "isAllergy");
            return (Criteria) this;
        }

        public Criteria andAllergyNameIsNull() {
            addCriterion("allergy_name is null");
            return (Criteria) this;
        }

        public Criteria andAllergyNameIsNotNull() {
            addCriterion("allergy_name is not null");
            return (Criteria) this;
        }

        public Criteria andAllergyNameEqualTo(String value) {
            addCriterion("allergy_name =", value, "allergyName");
            return (Criteria) this;
        }

        public Criteria andAllergyNameNotEqualTo(String value) {
            addCriterion("allergy_name <>", value, "allergyName");
            return (Criteria) this;
        }

        public Criteria andAllergyNameGreaterThan(String value) {
            addCriterion("allergy_name >", value, "allergyName");
            return (Criteria) this;
        }

        public Criteria andAllergyNameGreaterThanOrEqualTo(String value) {
            addCriterion("allergy_name >=", value, "allergyName");
            return (Criteria) this;
        }

        public Criteria andAllergyNameLessThan(String value) {
            addCriterion("allergy_name <", value, "allergyName");
            return (Criteria) this;
        }

        public Criteria andAllergyNameLessThanOrEqualTo(String value) {
            addCriterion("allergy_name <=", value, "allergyName");
            return (Criteria) this;
        }

        public Criteria andAllergyNameLike(String value) {
            addCriterion("allergy_name like", value, "allergyName");
            return (Criteria) this;
        }

        public Criteria andAllergyNameNotLike(String value) {
            addCriterion("allergy_name not like", value, "allergyName");
            return (Criteria) this;
        }

        public Criteria andAllergyNameIn(List<String> values) {
            addCriterion("allergy_name in", values, "allergyName");
            return (Criteria) this;
        }

        public Criteria andAllergyNameNotIn(List<String> values) {
            addCriterion("allergy_name not in", values, "allergyName");
            return (Criteria) this;
        }

        public Criteria andAllergyNameBetween(String value1, String value2) {
            addCriterion("allergy_name between", value1, value2, "allergyName");
            return (Criteria) this;
        }

        public Criteria andAllergyNameNotBetween(String value1, String value2) {
            addCriterion("allergy_name not between", value1, value2, "allergyName");
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