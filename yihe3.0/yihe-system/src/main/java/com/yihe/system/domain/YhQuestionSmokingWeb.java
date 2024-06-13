package com.yihe.system.domain;

import com.yihe.common.annotation.Excel;
import com.yihe.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 吸烟问卷对象 yh_question_smoking
 * 
 * @author dengyongbo
 * @date 2022-05-24
 */
public class YhQuestionSmokingWeb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long questionId;

    /** 题目类型(1-单选题2-填空题3-多选题) */
    @Excel(name = "题目类型(1-单选题2-填空题3-多选题)")
    private Long questionType;

    /** 问卷标题 */
    @Excel(name = "问卷标题")
    private String questionTitle;

    /**  */
    @Excel(name = "")
    private String choose1;

    /**  */
    @Excel(name = "")
    private String choose1Value;

    /**  */
    @Excel(name = "")
    private String choose2;

    /**  */
    @Excel(name = "")
    private String choose2Value;

    /**  */
    @Excel(name = "")
    private String choose3;

    /**  */
    @Excel(name = "")
    private String choose3Value;

    /**  */
    @Excel(name = "")
    private String choose4;

    /**  */
    @Excel(name = "")
    private String choose4Value;

    public void setQuestionId(Long questionId)
    {
        this.questionId = questionId;
    }

    public Long getQuestionId()
    {
        return questionId;
    }
    public void setQuestionType(Long questionType)
    {
        this.questionType = questionType;
    }

    public Long getQuestionType()
    {
        return questionType;
    }
    public void setQuestionTitle(String questionTitle)
    {
        this.questionTitle = questionTitle;
    }

    public String getQuestionTitle()
    {
        return questionTitle;
    }
    public void setChoose1(String choose1)
    {
        this.choose1 = choose1;
    }

    public String getChoose1()
    {
        return choose1;
    }
    public void setChoose1Value(String choose1Value)
    {
        this.choose1Value = choose1Value;
    }

    public String getChoose1Value()
    {
        return choose1Value;
    }
    public void setChoose2(String choose2)
    {
        this.choose2 = choose2;
    }

    public String getChoose2()
    {
        return choose2;
    }
    public void setChoose2Value(String choose2Value)
    {
        this.choose2Value = choose2Value;
    }

    public String getChoose2Value()
    {
        return choose2Value;
    }
    public void setChoose3(String choose3)
    {
        this.choose3 = choose3;
    }

    public String getChoose3()
    {
        return choose3;
    }
    public void setChoose3Value(String choose3Value)
    {
        this.choose3Value = choose3Value;
    }

    public String getChoose3Value()
    {
        return choose3Value;
    }
    public void setChoose4(String choose4)
    {
        this.choose4 = choose4;
    }

    public String getChoose4()
    {
        return choose4;
    }
    public void setChoose4Value(String choose4Value)
    {
        this.choose4Value = choose4Value;
    }

    public String getChoose4Value()
    {
        return choose4Value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("questionId", getQuestionId())
            .append("questionType", getQuestionType())
            .append("questionTitle", getQuestionTitle())
            .append("choose1", getChoose1())
            .append("choose1Value", getChoose1Value())
            .append("choose2", getChoose2())
            .append("choose2Value", getChoose2Value())
            .append("choose3", getChoose3())
            .append("choose3Value", getChoose3Value())
            .append("choose4", getChoose4())
            .append("choose4Value", getChoose4Value())
            .toString();
    }
}
