package com.yihe.system.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YhUserEnrolinfoExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public YhUserEnrolinfoExample() {
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

        public Criteria andEUserIdIsNull() {
            addCriterion("E_User_Id is null");
            return (Criteria) this;
        }

        public Criteria andEUserIdIsNotNull() {
            addCriterion("E_User_Id is not null");
            return (Criteria) this;
        }

        public Criteria andEUserIdEqualTo(Integer value) {
            addCriterion("E_User_Id =", value, "eUserId");
            return (Criteria) this;
        }

        public Criteria andEUserIdNotEqualTo(Integer value) {
            addCriterion("E_User_Id <>", value, "eUserId");
            return (Criteria) this;
        }

        public Criteria andEUserIdGreaterThan(Integer value) {
            addCriterion("E_User_Id >", value, "eUserId");
            return (Criteria) this;
        }

        public Criteria andEUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("E_User_Id >=", value, "eUserId");
            return (Criteria) this;
        }

        public Criteria andEUserIdLessThan(Integer value) {
            addCriterion("E_User_Id <", value, "eUserId");
            return (Criteria) this;
        }

        public Criteria andEUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("E_User_Id <=", value, "eUserId");
            return (Criteria) this;
        }

        public Criteria andEUserIdIn(List<Integer> values) {
            addCriterion("E_User_Id in", values, "eUserId");
            return (Criteria) this;
        }

        public Criteria andEUserIdNotIn(List<Integer> values) {
            addCriterion("E_User_Id not in", values, "eUserId");
            return (Criteria) this;
        }

        public Criteria andEUserIdBetween(Integer value1, Integer value2) {
            addCriterion("E_User_Id between", value1, value2, "eUserId");
            return (Criteria) this;
        }

        public Criteria andEUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("E_User_Id not between", value1, value2, "eUserId");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentIsNull() {
            addCriterion("Seq_Instrument is null");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentIsNotNull() {
            addCriterion("Seq_Instrument is not null");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentEqualTo(String value) {
            addCriterion("Seq_Instrument =", value, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentNotEqualTo(String value) {
            addCriterion("Seq_Instrument <>", value, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentGreaterThan(String value) {
            addCriterion("Seq_Instrument >", value, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentGreaterThanOrEqualTo(String value) {
            addCriterion("Seq_Instrument >=", value, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentLessThan(String value) {
            addCriterion("Seq_Instrument <", value, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentLessThanOrEqualTo(String value) {
            addCriterion("Seq_Instrument <=", value, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentLike(String value) {
            addCriterion("Seq_Instrument like", value, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentNotLike(String value) {
            addCriterion("Seq_Instrument not like", value, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentIn(List<String> values) {
            addCriterion("Seq_Instrument in", values, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentNotIn(List<String> values) {
            addCriterion("Seq_Instrument not in", values, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentBetween(String value1, String value2) {
            addCriterion("Seq_Instrument between", value1, value2, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andSeqInstrumentNotBetween(String value1, String value2) {
            addCriterion("Seq_Instrument not between", value1, value2, "seqInstrument");
            return (Criteria) this;
        }

        public Criteria andDocRuseridIsNull() {
            addCriterion("Doc_Ruserid is null");
            return (Criteria) this;
        }

        public Criteria andDocRuseridIsNotNull() {
            addCriterion("Doc_Ruserid is not null");
            return (Criteria) this;
        }

        public Criteria andDocRuseridEqualTo(String value) {
            addCriterion("Doc_Ruserid =", value, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andDocRuseridNotEqualTo(String value) {
            addCriterion("Doc_Ruserid <>", value, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andDocRuseridGreaterThan(String value) {
            addCriterion("Doc_Ruserid >", value, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andDocRuseridGreaterThanOrEqualTo(String value) {
            addCriterion("Doc_Ruserid >=", value, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andDocRuseridLessThan(String value) {
            addCriterion("Doc_Ruserid <", value, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andDocRuseridLessThanOrEqualTo(String value) {
            addCriterion("Doc_Ruserid <=", value, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andDocRuseridLike(String value) {
            addCriterion("Doc_Ruserid like", value, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andDocRuseridNotLike(String value) {
            addCriterion("Doc_Ruserid not like", value, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andDocRuseridIn(List<String> values) {
            addCriterion("Doc_Ruserid in", values, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andDocRuseridNotIn(List<String> values) {
            addCriterion("Doc_Ruserid not in", values, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andDocRuseridBetween(String value1, String value2) {
            addCriterion("Doc_Ruserid between", value1, value2, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andDocRuseridNotBetween(String value1, String value2) {
            addCriterion("Doc_Ruserid not between", value1, value2, "docRuserid");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupIsNull() {
            addCriterion("Flag_Dgroup is null");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupIsNotNull() {
            addCriterion("Flag_Dgroup is not null");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupEqualTo(String value) {
            addCriterion("Flag_Dgroup =", value, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupNotEqualTo(String value) {
            addCriterion("Flag_Dgroup <>", value, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupGreaterThan(String value) {
            addCriterion("Flag_Dgroup >", value, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupGreaterThanOrEqualTo(String value) {
            addCriterion("Flag_Dgroup >=", value, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupLessThan(String value) {
            addCriterion("Flag_Dgroup <", value, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupLessThanOrEqualTo(String value) {
            addCriterion("Flag_Dgroup <=", value, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupLike(String value) {
            addCriterion("Flag_Dgroup like", value, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupNotLike(String value) {
            addCriterion("Flag_Dgroup not like", value, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupIn(List<String> values) {
            addCriterion("Flag_Dgroup in", values, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupNotIn(List<String> values) {
            addCriterion("Flag_Dgroup not in", values, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupBetween(String value1, String value2) {
            addCriterion("Flag_Dgroup between", value1, value2, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andFlagDgroupNotBetween(String value1, String value2) {
            addCriterion("Flag_Dgroup not between", value1, value2, "flagDgroup");
            return (Criteria) this;
        }

        public Criteria andSexCodeIsNull() {
            addCriterion("Sex_Code is null");
            return (Criteria) this;
        }

        public Criteria andSexCodeIsNotNull() {
            addCriterion("Sex_Code is not null");
            return (Criteria) this;
        }

        public Criteria andSexCodeEqualTo(Integer value) {
            addCriterion("Sex_Code =", value, "sexCode");
            return (Criteria) this;
        }

        public Criteria andSexCodeNotEqualTo(Integer value) {
            addCriterion("Sex_Code <>", value, "sexCode");
            return (Criteria) this;
        }

        public Criteria andSexCodeGreaterThan(Integer value) {
            addCriterion("Sex_Code >", value, "sexCode");
            return (Criteria) this;
        }

        public Criteria andSexCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("Sex_Code >=", value, "sexCode");
            return (Criteria) this;
        }

        public Criteria andSexCodeLessThan(Integer value) {
            addCriterion("Sex_Code <", value, "sexCode");
            return (Criteria) this;
        }

        public Criteria andSexCodeLessThanOrEqualTo(Integer value) {
            addCriterion("Sex_Code <=", value, "sexCode");
            return (Criteria) this;
        }

        public Criteria andSexCodeIn(List<Integer> values) {
            addCriterion("Sex_Code in", values, "sexCode");
            return (Criteria) this;
        }

        public Criteria andSexCodeNotIn(List<Integer> values) {
            addCriterion("Sex_Code not in", values, "sexCode");
            return (Criteria) this;
        }

        public Criteria andSexCodeBetween(Integer value1, Integer value2) {
            addCriterion("Sex_Code between", value1, value2, "sexCode");
            return (Criteria) this;
        }

        public Criteria andSexCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("Sex_Code not between", value1, value2, "sexCode");
            return (Criteria) this;
        }

        public Criteria andHeightIsNull() {
            addCriterion("Height is null");
            return (Criteria) this;
        }

        public Criteria andHeightIsNotNull() {
            addCriterion("Height is not null");
            return (Criteria) this;
        }

        public Criteria andHeightEqualTo(Float value) {
            addCriterion("Height =", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotEqualTo(Float value) {
            addCriterion("Height <>", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThan(Float value) {
            addCriterion("Height >", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThanOrEqualTo(Float value) {
            addCriterion("Height >=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThan(Float value) {
            addCriterion("Height <", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThanOrEqualTo(Float value) {
            addCriterion("Height <=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightIn(List<Float> values) {
            addCriterion("Height in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotIn(List<Float> values) {
            addCriterion("Height not in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightBetween(Float value1, Float value2) {
            addCriterion("Height between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotBetween(Float value1, Float value2) {
            addCriterion("Height not between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("Weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("Weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(Float value) {
            addCriterion("Weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(Float value) {
            addCriterion("Weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(Float value) {
            addCriterion("Weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(Float value) {
            addCriterion("Weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(Float value) {
            addCriterion("Weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(Float value) {
            addCriterion("Weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<Float> values) {
            addCriterion("Weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<Float> values) {
            addCriterion("Weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(Float value1, Float value2) {
            addCriterion("Weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(Float value1, Float value2) {
            addCriterion("Weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andHwUptimeIsNull() {
            addCriterion("Hw_Uptime is null");
            return (Criteria) this;
        }

        public Criteria andHwUptimeIsNotNull() {
            addCriterion("Hw_Uptime is not null");
            return (Criteria) this;
        }

        public Criteria andHwUptimeEqualTo(Date value) {
            addCriterion("Hw_Uptime =", value, "hwUptime");
            return (Criteria) this;
        }

        public Criteria andHwUptimeNotEqualTo(Date value) {
            addCriterion("Hw_Uptime <>", value, "hwUptime");
            return (Criteria) this;
        }

        public Criteria andHwUptimeGreaterThan(Date value) {
            addCriterion("Hw_Uptime >", value, "hwUptime");
            return (Criteria) this;
        }

        public Criteria andHwUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Hw_Uptime >=", value, "hwUptime");
            return (Criteria) this;
        }

        public Criteria andHwUptimeLessThan(Date value) {
            addCriterion("Hw_Uptime <", value, "hwUptime");
            return (Criteria) this;
        }

        public Criteria andHwUptimeLessThanOrEqualTo(Date value) {
            addCriterion("Hw_Uptime <=", value, "hwUptime");
            return (Criteria) this;
        }

        public Criteria andHwUptimeIn(List<Date> values) {
            addCriterion("Hw_Uptime in", values, "hwUptime");
            return (Criteria) this;
        }

        public Criteria andHwUptimeNotIn(List<Date> values) {
            addCriterion("Hw_Uptime not in", values, "hwUptime");
            return (Criteria) this;
        }

        public Criteria andHwUptimeBetween(Date value1, Date value2) {
            addCriterion("Hw_Uptime between", value1, value2, "hwUptime");
            return (Criteria) this;
        }

        public Criteria andHwUptimeNotBetween(Date value1, Date value2) {
            addCriterion("Hw_Uptime not between", value1, value2, "hwUptime");
            return (Criteria) this;
        }

        public Criteria andWaistcIsNull() {
            addCriterion("Waistc is null");
            return (Criteria) this;
        }

        public Criteria andWaistcIsNotNull() {
            addCriterion("Waistc is not null");
            return (Criteria) this;
        }

        public Criteria andWaistcEqualTo(Float value) {
            addCriterion("Waistc =", value, "waistc");
            return (Criteria) this;
        }

        public Criteria andWaistcNotEqualTo(Float value) {
            addCriterion("Waistc <>", value, "waistc");
            return (Criteria) this;
        }

        public Criteria andWaistcGreaterThan(Float value) {
            addCriterion("Waistc >", value, "waistc");
            return (Criteria) this;
        }

        public Criteria andWaistcGreaterThanOrEqualTo(Float value) {
            addCriterion("Waistc >=", value, "waistc");
            return (Criteria) this;
        }

        public Criteria andWaistcLessThan(Float value) {
            addCriterion("Waistc <", value, "waistc");
            return (Criteria) this;
        }

        public Criteria andWaistcLessThanOrEqualTo(Float value) {
            addCriterion("Waistc <=", value, "waistc");
            return (Criteria) this;
        }

        public Criteria andWaistcIn(List<Float> values) {
            addCriterion("Waistc in", values, "waistc");
            return (Criteria) this;
        }

        public Criteria andWaistcNotIn(List<Float> values) {
            addCriterion("Waistc not in", values, "waistc");
            return (Criteria) this;
        }

        public Criteria andWaistcBetween(Float value1, Float value2) {
            addCriterion("Waistc between", value1, value2, "waistc");
            return (Criteria) this;
        }

        public Criteria andWaistcNotBetween(Float value1, Float value2) {
            addCriterion("Waistc not between", value1, value2, "waistc");
            return (Criteria) this;
        }

        public Criteria andWUptimeIsNull() {
            addCriterion("W_Uptime is null");
            return (Criteria) this;
        }

        public Criteria andWUptimeIsNotNull() {
            addCriterion("W_Uptime is not null");
            return (Criteria) this;
        }

        public Criteria andWUptimeEqualTo(Date value) {
            addCriterion("W_Uptime =", value, "wUptime");
            return (Criteria) this;
        }

        public Criteria andWUptimeNotEqualTo(Date value) {
            addCriterion("W_Uptime <>", value, "wUptime");
            return (Criteria) this;
        }

        public Criteria andWUptimeGreaterThan(Date value) {
            addCriterion("W_Uptime >", value, "wUptime");
            return (Criteria) this;
        }

        public Criteria andWUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("W_Uptime >=", value, "wUptime");
            return (Criteria) this;
        }

        public Criteria andWUptimeLessThan(Date value) {
            addCriterion("W_Uptime <", value, "wUptime");
            return (Criteria) this;
        }

        public Criteria andWUptimeLessThanOrEqualTo(Date value) {
            addCriterion("W_Uptime <=", value, "wUptime");
            return (Criteria) this;
        }

        public Criteria andWUptimeIn(List<Date> values) {
            addCriterion("W_Uptime in", values, "wUptime");
            return (Criteria) this;
        }

        public Criteria andWUptimeNotIn(List<Date> values) {
            addCriterion("W_Uptime not in", values, "wUptime");
            return (Criteria) this;
        }

        public Criteria andWUptimeBetween(Date value1, Date value2) {
            addCriterion("W_Uptime between", value1, value2, "wUptime");
            return (Criteria) this;
        }

        public Criteria andWUptimeNotBetween(Date value1, Date value2) {
            addCriterion("W_Uptime not between", value1, value2, "wUptime");
            return (Criteria) this;
        }

        public Criteria andHipcIsNull() {
            addCriterion("Hipc is null");
            return (Criteria) this;
        }

        public Criteria andHipcIsNotNull() {
            addCriterion("Hipc is not null");
            return (Criteria) this;
        }

        public Criteria andHipcEqualTo(Float value) {
            addCriterion("Hipc =", value, "hipc");
            return (Criteria) this;
        }

        public Criteria andHipcNotEqualTo(Float value) {
            addCriterion("Hipc <>", value, "hipc");
            return (Criteria) this;
        }

        public Criteria andHipcGreaterThan(Float value) {
            addCriterion("Hipc >", value, "hipc");
            return (Criteria) this;
        }

        public Criteria andHipcGreaterThanOrEqualTo(Float value) {
            addCriterion("Hipc >=", value, "hipc");
            return (Criteria) this;
        }

        public Criteria andHipcLessThan(Float value) {
            addCriterion("Hipc <", value, "hipc");
            return (Criteria) this;
        }

        public Criteria andHipcLessThanOrEqualTo(Float value) {
            addCriterion("Hipc <=", value, "hipc");
            return (Criteria) this;
        }

        public Criteria andHipcIn(List<Float> values) {
            addCriterion("Hipc in", values, "hipc");
            return (Criteria) this;
        }

        public Criteria andHipcNotIn(List<Float> values) {
            addCriterion("Hipc not in", values, "hipc");
            return (Criteria) this;
        }

        public Criteria andHipcBetween(Float value1, Float value2) {
            addCriterion("Hipc between", value1, value2, "hipc");
            return (Criteria) this;
        }

        public Criteria andHipcNotBetween(Float value1, Float value2) {
            addCriterion("Hipc not between", value1, value2, "hipc");
            return (Criteria) this;
        }

        public Criteria andHUptimeIsNull() {
            addCriterion("H_Uptime is null");
            return (Criteria) this;
        }

        public Criteria andHUptimeIsNotNull() {
            addCriterion("H_Uptime is not null");
            return (Criteria) this;
        }

        public Criteria andHUptimeEqualTo(Date value) {
            addCriterion("H_Uptime =", value, "hUptime");
            return (Criteria) this;
        }

        public Criteria andHUptimeNotEqualTo(Date value) {
            addCriterion("H_Uptime <>", value, "hUptime");
            return (Criteria) this;
        }

        public Criteria andHUptimeGreaterThan(Date value) {
            addCriterion("H_Uptime >", value, "hUptime");
            return (Criteria) this;
        }

        public Criteria andHUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("H_Uptime >=", value, "hUptime");
            return (Criteria) this;
        }

        public Criteria andHUptimeLessThan(Date value) {
            addCriterion("H_Uptime <", value, "hUptime");
            return (Criteria) this;
        }

        public Criteria andHUptimeLessThanOrEqualTo(Date value) {
            addCriterion("H_Uptime <=", value, "hUptime");
            return (Criteria) this;
        }

        public Criteria andHUptimeIn(List<Date> values) {
            addCriterion("H_Uptime in", values, "hUptime");
            return (Criteria) this;
        }

        public Criteria andHUptimeNotIn(List<Date> values) {
            addCriterion("H_Uptime not in", values, "hUptime");
            return (Criteria) this;
        }

        public Criteria andHUptimeBetween(Date value1, Date value2) {
            addCriterion("H_Uptime between", value1, value2, "hUptime");
            return (Criteria) this;
        }

        public Criteria andHUptimeNotBetween(Date value1, Date value2) {
            addCriterion("H_Uptime not between", value1, value2, "hUptime");
            return (Criteria) this;
        }

        public Criteria andSbpIsNull() {
            addCriterion("Sbp is null");
            return (Criteria) this;
        }

        public Criteria andSbpIsNotNull() {
            addCriterion("Sbp is not null");
            return (Criteria) this;
        }

        public Criteria andSbpEqualTo(Integer value) {
            addCriterion("Sbp =", value, "sbp");
            return (Criteria) this;
        }

        public Criteria andSbpNotEqualTo(Integer value) {
            addCriterion("Sbp <>", value, "sbp");
            return (Criteria) this;
        }

        public Criteria andSbpGreaterThan(Integer value) {
            addCriterion("Sbp >", value, "sbp");
            return (Criteria) this;
        }

        public Criteria andSbpGreaterThanOrEqualTo(Integer value) {
            addCriterion("Sbp >=", value, "sbp");
            return (Criteria) this;
        }

        public Criteria andSbpLessThan(Integer value) {
            addCriterion("Sbp <", value, "sbp");
            return (Criteria) this;
        }

        public Criteria andSbpLessThanOrEqualTo(Integer value) {
            addCriterion("Sbp <=", value, "sbp");
            return (Criteria) this;
        }

        public Criteria andSbpIn(List<Integer> values) {
            addCriterion("Sbp in", values, "sbp");
            return (Criteria) this;
        }

        public Criteria andSbpNotIn(List<Integer> values) {
            addCriterion("Sbp not in", values, "sbp");
            return (Criteria) this;
        }

        public Criteria andSbpBetween(Integer value1, Integer value2) {
            addCriterion("Sbp between", value1, value2, "sbp");
            return (Criteria) this;
        }

        public Criteria andSbpNotBetween(Integer value1, Integer value2) {
            addCriterion("Sbp not between", value1, value2, "sbp");
            return (Criteria) this;
        }

        public Criteria andDbpIsNull() {
            addCriterion("Dbp is null");
            return (Criteria) this;
        }

        public Criteria andDbpIsNotNull() {
            addCriterion("Dbp is not null");
            return (Criteria) this;
        }

        public Criteria andDbpEqualTo(Integer value) {
            addCriterion("Dbp =", value, "dbp");
            return (Criteria) this;
        }

        public Criteria andDbpNotEqualTo(Integer value) {
            addCriterion("Dbp <>", value, "dbp");
            return (Criteria) this;
        }

        public Criteria andDbpGreaterThan(Integer value) {
            addCriterion("Dbp >", value, "dbp");
            return (Criteria) this;
        }

        public Criteria andDbpGreaterThanOrEqualTo(Integer value) {
            addCriterion("Dbp >=", value, "dbp");
            return (Criteria) this;
        }

        public Criteria andDbpLessThan(Integer value) {
            addCriterion("Dbp <", value, "dbp");
            return (Criteria) this;
        }

        public Criteria andDbpLessThanOrEqualTo(Integer value) {
            addCriterion("Dbp <=", value, "dbp");
            return (Criteria) this;
        }

        public Criteria andDbpIn(List<Integer> values) {
            addCriterion("Dbp in", values, "dbp");
            return (Criteria) this;
        }

        public Criteria andDbpNotIn(List<Integer> values) {
            addCriterion("Dbp not in", values, "dbp");
            return (Criteria) this;
        }

        public Criteria andDbpBetween(Integer value1, Integer value2) {
            addCriterion("Dbp between", value1, value2, "dbp");
            return (Criteria) this;
        }

        public Criteria andDbpNotBetween(Integer value1, Integer value2) {
            addCriterion("Dbp not between", value1, value2, "dbp");
            return (Criteria) this;
        }

        public Criteria andSdUptimeIsNull() {
            addCriterion("Sd_Uptime is null");
            return (Criteria) this;
        }

        public Criteria andSdUptimeIsNotNull() {
            addCriterion("Sd_Uptime is not null");
            return (Criteria) this;
        }

        public Criteria andSdUptimeEqualTo(Date value) {
            addCriterion("Sd_Uptime =", value, "sdUptime");
            return (Criteria) this;
        }

        public Criteria andSdUptimeNotEqualTo(Date value) {
            addCriterion("Sd_Uptime <>", value, "sdUptime");
            return (Criteria) this;
        }

        public Criteria andSdUptimeGreaterThan(Date value) {
            addCriterion("Sd_Uptime >", value, "sdUptime");
            return (Criteria) this;
        }

        public Criteria andSdUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Sd_Uptime >=", value, "sdUptime");
            return (Criteria) this;
        }

        public Criteria andSdUptimeLessThan(Date value) {
            addCriterion("Sd_Uptime <", value, "sdUptime");
            return (Criteria) this;
        }

        public Criteria andSdUptimeLessThanOrEqualTo(Date value) {
            addCriterion("Sd_Uptime <=", value, "sdUptime");
            return (Criteria) this;
        }

        public Criteria andSdUptimeIn(List<Date> values) {
            addCriterion("Sd_Uptime in", values, "sdUptime");
            return (Criteria) this;
        }

        public Criteria andSdUptimeNotIn(List<Date> values) {
            addCriterion("Sd_Uptime not in", values, "sdUptime");
            return (Criteria) this;
        }

        public Criteria andSdUptimeBetween(Date value1, Date value2) {
            addCriterion("Sd_Uptime between", value1, value2, "sdUptime");
            return (Criteria) this;
        }

        public Criteria andSdUptimeNotBetween(Date value1, Date value2) {
            addCriterion("Sd_Uptime not between", value1, value2, "sdUptime");
            return (Criteria) this;
        }

        public Criteria andBsIsNull() {
            addCriterion("Bs is null");
            return (Criteria) this;
        }

        public Criteria andBsIsNotNull() {
            addCriterion("Bs is not null");
            return (Criteria) this;
        }

        public Criteria andBsEqualTo(Float value) {
            addCriterion("Bs =", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsNotEqualTo(Float value) {
            addCriterion("Bs <>", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsGreaterThan(Float value) {
            addCriterion("Bs >", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsGreaterThanOrEqualTo(Float value) {
            addCriterion("Bs >=", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsLessThan(Float value) {
            addCriterion("Bs <", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsLessThanOrEqualTo(Float value) {
            addCriterion("Bs <=", value, "bs");
            return (Criteria) this;
        }

        public Criteria andBsIn(List<Float> values) {
            addCriterion("Bs in", values, "bs");
            return (Criteria) this;
        }

        public Criteria andBsNotIn(List<Float> values) {
            addCriterion("Bs not in", values, "bs");
            return (Criteria) this;
        }

        public Criteria andBsBetween(Float value1, Float value2) {
            addCriterion("Bs between", value1, value2, "bs");
            return (Criteria) this;
        }

        public Criteria andBsNotBetween(Float value1, Float value2) {
            addCriterion("Bs not between", value1, value2, "bs");
            return (Criteria) this;
        }

        public Criteria andBsUptimeIsNull() {
            addCriterion("Bs_Uptime is null");
            return (Criteria) this;
        }

        public Criteria andBsUptimeIsNotNull() {
            addCriterion("Bs_Uptime is not null");
            return (Criteria) this;
        }

        public Criteria andBsUptimeEqualTo(Date value) {
            addCriterion("Bs_Uptime =", value, "bsUptime");
            return (Criteria) this;
        }

        public Criteria andBsUptimeNotEqualTo(Date value) {
            addCriterion("Bs_Uptime <>", value, "bsUptime");
            return (Criteria) this;
        }

        public Criteria andBsUptimeGreaterThan(Date value) {
            addCriterion("Bs_Uptime >", value, "bsUptime");
            return (Criteria) this;
        }

        public Criteria andBsUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Bs_Uptime >=", value, "bsUptime");
            return (Criteria) this;
        }

        public Criteria andBsUptimeLessThan(Date value) {
            addCriterion("Bs_Uptime <", value, "bsUptime");
            return (Criteria) this;
        }

        public Criteria andBsUptimeLessThanOrEqualTo(Date value) {
            addCriterion("Bs_Uptime <=", value, "bsUptime");
            return (Criteria) this;
        }

        public Criteria andBsUptimeIn(List<Date> values) {
            addCriterion("Bs_Uptime in", values, "bsUptime");
            return (Criteria) this;
        }

        public Criteria andBsUptimeNotIn(List<Date> values) {
            addCriterion("Bs_Uptime not in", values, "bsUptime");
            return (Criteria) this;
        }

        public Criteria andBsUptimeBetween(Date value1, Date value2) {
            addCriterion("Bs_Uptime between", value1, value2, "bsUptime");
            return (Criteria) this;
        }

        public Criteria andBsUptimeNotBetween(Date value1, Date value2) {
            addCriterion("Bs_Uptime not between", value1, value2, "bsUptime");
            return (Criteria) this;
        }

        public Criteria andCholesterinIsNull() {
            addCriterion("Cholesterin is null");
            return (Criteria) this;
        }

        public Criteria andCholesterinIsNotNull() {
            addCriterion("Cholesterin is not null");
            return (Criteria) this;
        }

        public Criteria andCholesterinEqualTo(Float value) {
            addCriterion("Cholesterin =", value, "cholesterin");
            return (Criteria) this;
        }

        public Criteria andCholesterinNotEqualTo(Float value) {
            addCriterion("Cholesterin <>", value, "cholesterin");
            return (Criteria) this;
        }

        public Criteria andCholesterinGreaterThan(Float value) {
            addCriterion("Cholesterin >", value, "cholesterin");
            return (Criteria) this;
        }

        public Criteria andCholesterinGreaterThanOrEqualTo(Float value) {
            addCriterion("Cholesterin >=", value, "cholesterin");
            return (Criteria) this;
        }

        public Criteria andCholesterinLessThan(Float value) {
            addCriterion("Cholesterin <", value, "cholesterin");
            return (Criteria) this;
        }

        public Criteria andCholesterinLessThanOrEqualTo(Float value) {
            addCriterion("Cholesterin <=", value, "cholesterin");
            return (Criteria) this;
        }

        public Criteria andCholesterinIn(List<Float> values) {
            addCriterion("Cholesterin in", values, "cholesterin");
            return (Criteria) this;
        }

        public Criteria andCholesterinNotIn(List<Float> values) {
            addCriterion("Cholesterin not in", values, "cholesterin");
            return (Criteria) this;
        }

        public Criteria andCholesterinBetween(Float value1, Float value2) {
            addCriterion("Cholesterin between", value1, value2, "cholesterin");
            return (Criteria) this;
        }

        public Criteria andCholesterinNotBetween(Float value1, Float value2) {
            addCriterion("Cholesterin not between", value1, value2, "cholesterin");
            return (Criteria) this;
        }

        public Criteria andTringIsNull() {
            addCriterion("Tring is null");
            return (Criteria) this;
        }

        public Criteria andTringIsNotNull() {
            addCriterion("Tring is not null");
            return (Criteria) this;
        }

        public Criteria andTringEqualTo(Float value) {
            addCriterion("Tring =", value, "tring");
            return (Criteria) this;
        }

        public Criteria andTringNotEqualTo(Float value) {
            addCriterion("Tring <>", value, "tring");
            return (Criteria) this;
        }

        public Criteria andTringGreaterThan(Float value) {
            addCriterion("Tring >", value, "tring");
            return (Criteria) this;
        }

        public Criteria andTringGreaterThanOrEqualTo(Float value) {
            addCriterion("Tring >=", value, "tring");
            return (Criteria) this;
        }

        public Criteria andTringLessThan(Float value) {
            addCriterion("Tring <", value, "tring");
            return (Criteria) this;
        }

        public Criteria andTringLessThanOrEqualTo(Float value) {
            addCriterion("Tring <=", value, "tring");
            return (Criteria) this;
        }

        public Criteria andTringIn(List<Float> values) {
            addCriterion("Tring in", values, "tring");
            return (Criteria) this;
        }

        public Criteria andTringNotIn(List<Float> values) {
            addCriterion("Tring not in", values, "tring");
            return (Criteria) this;
        }

        public Criteria andTringBetween(Float value1, Float value2) {
            addCriterion("Tring between", value1, value2, "tring");
            return (Criteria) this;
        }

        public Criteria andTringNotBetween(Float value1, Float value2) {
            addCriterion("Tring not between", value1, value2, "tring");
            return (Criteria) this;
        }

        public Criteria andHdlIsNull() {
            addCriterion("Hdl is null");
            return (Criteria) this;
        }

        public Criteria andHdlIsNotNull() {
            addCriterion("Hdl is not null");
            return (Criteria) this;
        }

        public Criteria andHdlEqualTo(Float value) {
            addCriterion("Hdl =", value, "hdl");
            return (Criteria) this;
        }

        public Criteria andHdlNotEqualTo(Float value) {
            addCriterion("Hdl <>", value, "hdl");
            return (Criteria) this;
        }

        public Criteria andHdlGreaterThan(Float value) {
            addCriterion("Hdl >", value, "hdl");
            return (Criteria) this;
        }

        public Criteria andHdlGreaterThanOrEqualTo(Float value) {
            addCriterion("Hdl >=", value, "hdl");
            return (Criteria) this;
        }

        public Criteria andHdlLessThan(Float value) {
            addCriterion("Hdl <", value, "hdl");
            return (Criteria) this;
        }

        public Criteria andHdlLessThanOrEqualTo(Float value) {
            addCriterion("Hdl <=", value, "hdl");
            return (Criteria) this;
        }

        public Criteria andHdlIn(List<Float> values) {
            addCriterion("Hdl in", values, "hdl");
            return (Criteria) this;
        }

        public Criteria andHdlNotIn(List<Float> values) {
            addCriterion("Hdl not in", values, "hdl");
            return (Criteria) this;
        }

        public Criteria andHdlBetween(Float value1, Float value2) {
            addCriterion("Hdl between", value1, value2, "hdl");
            return (Criteria) this;
        }

        public Criteria andHdlNotBetween(Float value1, Float value2) {
            addCriterion("Hdl not between", value1, value2, "hdl");
            return (Criteria) this;
        }

        public Criteria andLdlIsNull() {
            addCriterion("Ldl is null");
            return (Criteria) this;
        }

        public Criteria andLdlIsNotNull() {
            addCriterion("Ldl is not null");
            return (Criteria) this;
        }

        public Criteria andLdlEqualTo(Float value) {
            addCriterion("Ldl =", value, "ldl");
            return (Criteria) this;
        }

        public Criteria andLdlNotEqualTo(Float value) {
            addCriterion("Ldl <>", value, "ldl");
            return (Criteria) this;
        }

        public Criteria andLdlGreaterThan(Float value) {
            addCriterion("Ldl >", value, "ldl");
            return (Criteria) this;
        }

        public Criteria andLdlGreaterThanOrEqualTo(Float value) {
            addCriterion("Ldl >=", value, "ldl");
            return (Criteria) this;
        }

        public Criteria andLdlLessThan(Float value) {
            addCriterion("Ldl <", value, "ldl");
            return (Criteria) this;
        }

        public Criteria andLdlLessThanOrEqualTo(Float value) {
            addCriterion("Ldl <=", value, "ldl");
            return (Criteria) this;
        }

        public Criteria andLdlIn(List<Float> values) {
            addCriterion("Ldl in", values, "ldl");
            return (Criteria) this;
        }

        public Criteria andLdlNotIn(List<Float> values) {
            addCriterion("Ldl not in", values, "ldl");
            return (Criteria) this;
        }

        public Criteria andLdlBetween(Float value1, Float value2) {
            addCriterion("Ldl between", value1, value2, "ldl");
            return (Criteria) this;
        }

        public Criteria andLdlNotBetween(Float value1, Float value2) {
            addCriterion("Ldl not between", value1, value2, "ldl");
            return (Criteria) this;
        }

        public Criteria andBfUptimeIsNull() {
            addCriterion("Bf_Uptime is null");
            return (Criteria) this;
        }

        public Criteria andBfUptimeIsNotNull() {
            addCriterion("Bf_Uptime is not null");
            return (Criteria) this;
        }

        public Criteria andBfUptimeEqualTo(Date value) {
            addCriterion("Bf_Uptime =", value, "bfUptime");
            return (Criteria) this;
        }

        public Criteria andBfUptimeNotEqualTo(Date value) {
            addCriterion("Bf_Uptime <>", value, "bfUptime");
            return (Criteria) this;
        }

        public Criteria andBfUptimeGreaterThan(Date value) {
            addCriterion("Bf_Uptime >", value, "bfUptime");
            return (Criteria) this;
        }

        public Criteria andBfUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Bf_Uptime >=", value, "bfUptime");
            return (Criteria) this;
        }

        public Criteria andBfUptimeLessThan(Date value) {
            addCriterion("Bf_Uptime <", value, "bfUptime");
            return (Criteria) this;
        }

        public Criteria andBfUptimeLessThanOrEqualTo(Date value) {
            addCriterion("Bf_Uptime <=", value, "bfUptime");
            return (Criteria) this;
        }

        public Criteria andBfUptimeIn(List<Date> values) {
            addCriterion("Bf_Uptime in", values, "bfUptime");
            return (Criteria) this;
        }

        public Criteria andBfUptimeNotIn(List<Date> values) {
            addCriterion("Bf_Uptime not in", values, "bfUptime");
            return (Criteria) this;
        }

        public Criteria andBfUptimeBetween(Date value1, Date value2) {
            addCriterion("Bf_Uptime between", value1, value2, "bfUptime");
            return (Criteria) this;
        }

        public Criteria andBfUptimeNotBetween(Date value1, Date value2) {
            addCriterion("Bf_Uptime not between", value1, value2, "bfUptime");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeIsNull() {
            addCriterion("Fooddiary_Uptime is null");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeIsNotNull() {
            addCriterion("Fooddiary_Uptime is not null");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeEqualTo(Date value) {
            addCriterion("Fooddiary_Uptime =", value, "fooddiaryUptime");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeNotEqualTo(Date value) {
            addCriterion("Fooddiary_Uptime <>", value, "fooddiaryUptime");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeGreaterThan(Date value) {
            addCriterion("Fooddiary_Uptime >", value, "fooddiaryUptime");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Fooddiary_Uptime >=", value, "fooddiaryUptime");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeLessThan(Date value) {
            addCriterion("Fooddiary_Uptime <", value, "fooddiaryUptime");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeLessThanOrEqualTo(Date value) {
            addCriterion("Fooddiary_Uptime <=", value, "fooddiaryUptime");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeIn(List<Date> values) {
            addCriterion("Fooddiary_Uptime in", values, "fooddiaryUptime");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeNotIn(List<Date> values) {
            addCriterion("Fooddiary_Uptime not in", values, "fooddiaryUptime");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeBetween(Date value1, Date value2) {
            addCriterion("Fooddiary_Uptime between", value1, value2, "fooddiaryUptime");
            return (Criteria) this;
        }

        public Criteria andFooddiaryUptimeNotBetween(Date value1, Date value2) {
            addCriterion("Fooddiary_Uptime not between", value1, value2, "fooddiaryUptime");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeIsNull() {
            addCriterion("sportdiary_uptime is null");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeIsNotNull() {
            addCriterion("sportdiary_uptime is not null");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeEqualTo(Date value) {
            addCriterion("sportdiary_uptime =", value, "sportdiaryUptime");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeNotEqualTo(Date value) {
            addCriterion("sportdiary_uptime <>", value, "sportdiaryUptime");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeGreaterThan(Date value) {
            addCriterion("sportdiary_uptime >", value, "sportdiaryUptime");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sportdiary_uptime >=", value, "sportdiaryUptime");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeLessThan(Date value) {
            addCriterion("sportdiary_uptime <", value, "sportdiaryUptime");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeLessThanOrEqualTo(Date value) {
            addCriterion("sportdiary_uptime <=", value, "sportdiaryUptime");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeIn(List<Date> values) {
            addCriterion("sportdiary_uptime in", values, "sportdiaryUptime");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeNotIn(List<Date> values) {
            addCriterion("sportdiary_uptime not in", values, "sportdiaryUptime");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeBetween(Date value1, Date value2) {
            addCriterion("sportdiary_uptime between", value1, value2, "sportdiaryUptime");
            return (Criteria) this;
        }

        public Criteria andSportdiaryUptimeNotBetween(Date value1, Date value2) {
            addCriterion("sportdiary_uptime not between", value1, value2, "sportdiaryUptime");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeIsNull() {
            addCriterion("Foodmon_Uptime is null");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeIsNotNull() {
            addCriterion("Foodmon_Uptime is not null");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeEqualTo(Date value) {
            addCriterion("Foodmon_Uptime =", value, "foodmonUptime");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeNotEqualTo(Date value) {
            addCriterion("Foodmon_Uptime <>", value, "foodmonUptime");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeGreaterThan(Date value) {
            addCriterion("Foodmon_Uptime >", value, "foodmonUptime");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Foodmon_Uptime >=", value, "foodmonUptime");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeLessThan(Date value) {
            addCriterion("Foodmon_Uptime <", value, "foodmonUptime");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeLessThanOrEqualTo(Date value) {
            addCriterion("Foodmon_Uptime <=", value, "foodmonUptime");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeIn(List<Date> values) {
            addCriterion("Foodmon_Uptime in", values, "foodmonUptime");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeNotIn(List<Date> values) {
            addCriterion("Foodmon_Uptime not in", values, "foodmonUptime");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeBetween(Date value1, Date value2) {
            addCriterion("Foodmon_Uptime between", value1, value2, "foodmonUptime");
            return (Criteria) this;
        }

        public Criteria andFoodmonUptimeNotBetween(Date value1, Date value2) {
            addCriterion("Foodmon_Uptime not between", value1, value2, "foodmonUptime");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeIsNull() {
            addCriterion("Energy_Uptime is null");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeIsNotNull() {
            addCriterion("Energy_Uptime is not null");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeEqualTo(Date value) {
            addCriterion("Energy_Uptime =", value, "energyUptime");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeNotEqualTo(Date value) {
            addCriterion("Energy_Uptime <>", value, "energyUptime");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeGreaterThan(Date value) {
            addCriterion("Energy_Uptime >", value, "energyUptime");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Energy_Uptime >=", value, "energyUptime");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeLessThan(Date value) {
            addCriterion("Energy_Uptime <", value, "energyUptime");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeLessThanOrEqualTo(Date value) {
            addCriterion("Energy_Uptime <=", value, "energyUptime");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeIn(List<Date> values) {
            addCriterion("Energy_Uptime in", values, "energyUptime");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeNotIn(List<Date> values) {
            addCriterion("Energy_Uptime not in", values, "energyUptime");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeBetween(Date value1, Date value2) {
            addCriterion("Energy_Uptime between", value1, value2, "energyUptime");
            return (Criteria) this;
        }

        public Criteria andEnergyUptimeNotBetween(Date value1, Date value2) {
            addCriterion("Energy_Uptime not between", value1, value2, "energyUptime");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeIsNull() {
            addCriterion("Lifestyle_Uptime is null");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeIsNotNull() {
            addCriterion("Lifestyle_Uptime is not null");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeEqualTo(Date value) {
            addCriterion("Lifestyle_Uptime =", value, "lifestyleUptime");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeNotEqualTo(Date value) {
            addCriterion("Lifestyle_Uptime <>", value, "lifestyleUptime");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeGreaterThan(Date value) {
            addCriterion("Lifestyle_Uptime >", value, "lifestyleUptime");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Lifestyle_Uptime >=", value, "lifestyleUptime");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeLessThan(Date value) {
            addCriterion("Lifestyle_Uptime <", value, "lifestyleUptime");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeLessThanOrEqualTo(Date value) {
            addCriterion("Lifestyle_Uptime <=", value, "lifestyleUptime");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeIn(List<Date> values) {
            addCriterion("Lifestyle_Uptime in", values, "lifestyleUptime");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeNotIn(List<Date> values) {
            addCriterion("Lifestyle_Uptime not in", values, "lifestyleUptime");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeBetween(Date value1, Date value2) {
            addCriterion("Lifestyle_Uptime between", value1, value2, "lifestyleUptime");
            return (Criteria) this;
        }

        public Criteria andLifestyleUptimeNotBetween(Date value1, Date value2) {
            addCriterion("Lifestyle_Uptime not between", value1, value2, "lifestyleUptime");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringIsNull() {
            addCriterion("Health_Monitoring is null");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringIsNotNull() {
            addCriterion("Health_Monitoring is not null");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringEqualTo(Date value) {
            addCriterion("Health_Monitoring =", value, "healthMonitoring");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringNotEqualTo(Date value) {
            addCriterion("Health_Monitoring <>", value, "healthMonitoring");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringGreaterThan(Date value) {
            addCriterion("Health_Monitoring >", value, "healthMonitoring");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringGreaterThanOrEqualTo(Date value) {
            addCriterion("Health_Monitoring >=", value, "healthMonitoring");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringLessThan(Date value) {
            addCriterion("Health_Monitoring <", value, "healthMonitoring");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringLessThanOrEqualTo(Date value) {
            addCriterion("Health_Monitoring <=", value, "healthMonitoring");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringIn(List<Date> values) {
            addCriterion("Health_Monitoring in", values, "healthMonitoring");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringNotIn(List<Date> values) {
            addCriterion("Health_Monitoring not in", values, "healthMonitoring");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringBetween(Date value1, Date value2) {
            addCriterion("Health_Monitoring between", value1, value2, "healthMonitoring");
            return (Criteria) this;
        }

        public Criteria andHealthMonitoringNotBetween(Date value1, Date value2) {
            addCriterion("Health_Monitoring not between", value1, value2, "healthMonitoring");
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

        public Criteria andEvaluateflagIsNull() {
            addCriterion("evaluateFlag is null");
            return (Criteria) this;
        }

        public Criteria andEvaluateflagIsNotNull() {
            addCriterion("evaluateFlag is not null");
            return (Criteria) this;
        }

        public Criteria andEvaluateflagEqualTo(Integer value) {
            addCriterion("evaluateFlag =", value, "evaluateflag");
            return (Criteria) this;
        }

        public Criteria andEvaluateflagNotEqualTo(Integer value) {
            addCriterion("evaluateFlag <>", value, "evaluateflag");
            return (Criteria) this;
        }

        public Criteria andEvaluateflagGreaterThan(Integer value) {
            addCriterion("evaluateFlag >", value, "evaluateflag");
            return (Criteria) this;
        }

        public Criteria andEvaluateflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("evaluateFlag >=", value, "evaluateflag");
            return (Criteria) this;
        }

        public Criteria andEvaluateflagLessThan(Integer value) {
            addCriterion("evaluateFlag <", value, "evaluateflag");
            return (Criteria) this;
        }

        public Criteria andEvaluateflagLessThanOrEqualTo(Integer value) {
            addCriterion("evaluateFlag <=", value, "evaluateflag");
            return (Criteria) this;
        }

        public Criteria andEvaluateflagIn(List<Integer> values) {
            addCriterion("evaluateFlag in", values, "evaluateflag");
            return (Criteria) this;
        }

        public Criteria andEvaluateflagNotIn(List<Integer> values) {
            addCriterion("evaluateFlag not in", values, "evaluateflag");
            return (Criteria) this;
        }

        public Criteria andEvaluateflagBetween(Integer value1, Integer value2) {
            addCriterion("evaluateFlag between", value1, value2, "evaluateflag");
            return (Criteria) this;
        }

        public Criteria andEvaluateflagNotBetween(Integer value1, Integer value2) {
            addCriterion("evaluateFlag not between", value1, value2, "evaluateflag");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeIsNull() {
            addCriterion("L_Ventricular_Hype is null");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeIsNotNull() {
            addCriterion("L_Ventricular_Hype is not null");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeEqualTo(Integer value) {
            addCriterion("L_Ventricular_Hype =", value, "lVentricularHype");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeNotEqualTo(Integer value) {
            addCriterion("L_Ventricular_Hype <>", value, "lVentricularHype");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeGreaterThan(Integer value) {
            addCriterion("L_Ventricular_Hype >", value, "lVentricularHype");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("L_Ventricular_Hype >=", value, "lVentricularHype");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeLessThan(Integer value) {
            addCriterion("L_Ventricular_Hype <", value, "lVentricularHype");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeLessThanOrEqualTo(Integer value) {
            addCriterion("L_Ventricular_Hype <=", value, "lVentricularHype");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeIn(List<Integer> values) {
            addCriterion("L_Ventricular_Hype in", values, "lVentricularHype");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeNotIn(List<Integer> values) {
            addCriterion("L_Ventricular_Hype not in", values, "lVentricularHype");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeBetween(Integer value1, Integer value2) {
            addCriterion("L_Ventricular_Hype between", value1, value2, "lVentricularHype");
            return (Criteria) this;
        }

        public Criteria andLVentricularHypeNotBetween(Integer value1, Integer value2) {
            addCriterion("L_Ventricular_Hype not between", value1, value2, "lVentricularHype");
            return (Criteria) this;
        }

        public Criteria andMeasureCardIsNull() {
            addCriterion("measure_card is null");
            return (Criteria) this;
        }

        public Criteria andMeasureCardIsNotNull() {
            addCriterion("measure_card is not null");
            return (Criteria) this;
        }

        public Criteria andMeasureCardEqualTo(String value) {
            addCriterion("measure_card =", value, "measureCard");
            return (Criteria) this;
        }

        public Criteria andMeasureCardNotEqualTo(String value) {
            addCriterion("measure_card <>", value, "measureCard");
            return (Criteria) this;
        }

        public Criteria andMeasureCardGreaterThan(String value) {
            addCriterion("measure_card >", value, "measureCard");
            return (Criteria) this;
        }

        public Criteria andMeasureCardGreaterThanOrEqualTo(String value) {
            addCriterion("measure_card >=", value, "measureCard");
            return (Criteria) this;
        }

        public Criteria andMeasureCardLessThan(String value) {
            addCriterion("measure_card <", value, "measureCard");
            return (Criteria) this;
        }

        public Criteria andMeasureCardLessThanOrEqualTo(String value) {
            addCriterion("measure_card <=", value, "measureCard");
            return (Criteria) this;
        }

        public Criteria andMeasureCardLike(String value) {
            addCriterion("measure_card like", value, "measureCard");
            return (Criteria) this;
        }

        public Criteria andMeasureCardNotLike(String value) {
            addCriterion("measure_card not like", value, "measureCard");
            return (Criteria) this;
        }

        public Criteria andMeasureCardIn(List<String> values) {
            addCriterion("measure_card in", values, "measureCard");
            return (Criteria) this;
        }

        public Criteria andMeasureCardNotIn(List<String> values) {
            addCriterion("measure_card not in", values, "measureCard");
            return (Criteria) this;
        }

        public Criteria andMeasureCardBetween(String value1, String value2) {
            addCriterion("measure_card between", value1, value2, "measureCard");
            return (Criteria) this;
        }

        public Criteria andMeasureCardNotBetween(String value1, String value2) {
            addCriterion("measure_card not between", value1, value2, "measureCard");
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