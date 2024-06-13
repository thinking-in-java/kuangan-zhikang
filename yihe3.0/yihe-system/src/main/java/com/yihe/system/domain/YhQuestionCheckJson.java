package com.yihe.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yihe.common.annotation.Excel;
import com.yihe.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 用户问卷答案对象 yh_question_check_json
 * 
 * @author yihe
 * @date 2020-04-07
 */
public class YhQuestionCheckJson extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userCheckAnswer;

    /** 暂存下标 */
    @Excel(name = "暂存下标")
    private Long userCheckIndex;

    /** 评估 1 ；暂存 2 */
    @Excel(name = "评估 1 ；暂存 2")
    private Long userCheckTs;

    /** $column.columnComment */
    @Excel(name = "评估 1 ；暂存 2", width = 30, dateFormat = "yyyy-MM-dd")
    private Date userCheckTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserCode(String userCode) 
    {
        this.userCode = userCode;
    }

    public String getUserCode() 
    {
        return userCode;
    }
    public void setUserCheckAnswer(String userCheckAnswer) 
    {
        this.userCheckAnswer = userCheckAnswer;
    }

    public String getUserCheckAnswer() 
    {
        return userCheckAnswer;
    }
    public void setUserCheckIndex(Long userCheckIndex) 
    {
        this.userCheckIndex = userCheckIndex;
    }

    public Long getUserCheckIndex() 
    {
        return userCheckIndex;
    }
    public void setUserCheckTs(Long userCheckTs) 
    {
        this.userCheckTs = userCheckTs;
    }

    public Long getUserCheckTs() 
    {
        return userCheckTs;
    }
    public void setUserCheckTime(Date userCheckTime) 
    {
        this.userCheckTime = userCheckTime;
    }

    public Date getUserCheckTime() 
    {
        return userCheckTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userCode", getUserCode())
            .append("userCheckAnswer", getUserCheckAnswer())
            .append("userCheckIndex", getUserCheckIndex())
            .append("userCheckTs", getUserCheckTs())
            .append("userCheckTime", getUserCheckTime())
            .toString();
    }
}
