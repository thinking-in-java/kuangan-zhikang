package com.yihe.common.core.page;

import com.yihe.common.constant.Constants;
import com.yihe.common.utils.ServletUtils;

/**
 * 表格数据处理
 * 
 * @author lll
 */
public class TableSupport
{
    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }

    public static PageDomain getPageDomain1()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.Limit));
        return pageDomain;
    }

    public static PageDomain buildPageRequest1()
    {
        return getPageDomain1();
    }
}
