package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhHealthLectureCategoryApp;
import com.yihe.applet.api.service.YhHealthLectureCategoryAppService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 健康大课堂 Created by dyb on 2020/1/10.
 */
@Controller
@RequestMapping("/api/YhHealthLectureCategory")
@Api(value = "YhHealthLectureCategory控制器", description = "YhHealthLectureCategory管理")
public class YhHealthLectureCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthLectureCategoryController.class);

    @Autowired
    YhHealthLectureCategoryAppService yhHealthLectureCategoryService;

	/*@RequestMapping(value = "/findInfoAll")
	@ResponseBody
	public Result findInfoAll() {
		YhHealthLectureCategoryExample example = new YhHealthLectureCategoryExample();
		example.setOrderByClause("sort_id");
		List<YhHealthLectureCategory> selectByExample = yhHealthLectureCategoryService.selectByExample(example);
		return Result.ok(selectByExample);
	}*/

    /**
     * 查询点赞数
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/findFabulous")
    @ResponseBody
    public Result findFabulous(Integer categoryId) {
        int findFabulousByInfoId = yhHealthLectureCategoryService.findFabulousByInfoId(categoryId);
        return Result.ok(findFabulousByInfoId);
    }

    /**
     * 点赞
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/insertFabulous")
    @ResponseBody
    public Result insertFabulous(Integer categoryId, String usercode) {
        int findFabulousByInfoId = yhHealthLectureCategoryService.insertFabulous(categoryId, usercode);
        return Result.ok(findFabulousByInfoId);
    }

    /**
     * 取消点赞
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/delFabulous")
    @ResponseBody
    public Result delFabulous(Integer categoryId, String usercode) {
        int findFabulousByInfoId = yhHealthLectureCategoryService.deleteFabulous(categoryId, usercode);
        return Result.ok(findFabulousByInfoId);
    }

    /**
     * 查询是否用户已经点赞
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/findFabulousExistence")
    @ResponseBody
    public Result findFabulousExistence(Integer categoryId, String usercode) {
        int findFabulousByInfoId = yhHealthLectureCategoryService.findFabulousExistence(categoryId, usercode);
        return Result.ok(findFabulousByInfoId);
    }

    /**
     * 查询所以大类
     *
     * @return
     */
    @RequestMapping(value = "/findInfoAllByParent")
    @ResponseBody
    public Result findInfoAllByParent(YhHealthLectureCategoryApp yhHealthLectureCategoryApp) {

        yhHealthLectureCategoryApp.setParentId("0");
        List<YhHealthLectureCategoryApp> findInfoAllByParent = yhHealthLectureCategoryService.findInfoAllByParent(yhHealthLectureCategoryApp);

        for (int i = 0; i < findInfoAllByParent.size(); i++) {
            Integer categoryid = findInfoAllByParent.get(i).getCategoryid();


            YhHealthLectureCategoryApp yhHealthLectureCategoryApp2 = new YhHealthLectureCategoryApp();
            yhHealthLectureCategoryApp2.setParentId(String.valueOf(categoryid));
            List<YhHealthLectureCategoryApp> selectByExample2 = yhHealthLectureCategoryService.findInfoAllByParent(yhHealthLectureCategoryApp2);
            if (null != selectByExample2 && selectByExample2.size() > 0) {
                for (int j = 0; j < selectByExample2.size(); j++) {

                    yhHealthLectureCategoryApp2.setParentId(String.valueOf(selectByExample2.get(j).getCategoryid()));
                    List<YhHealthLectureCategoryApp> selectByExample3 = yhHealthLectureCategoryService.findInfoAllByParent(yhHealthLectureCategoryApp2);
                    if (null != selectByExample3 && selectByExample3.size() > 1) {
                        YhHealthLectureCategoryApp quanbu = new YhHealthLectureCategoryApp();
                        quanbu.setCategoryid(selectByExample2.get(j).getCategoryid());
                        quanbu.setName("全部");
                        quanbu.setParentId(selectByExample2.get(j).getParentId());
                        selectByExample3.add(0, quanbu);
                    }
                    selectByExample2.get(j).setTypeList(selectByExample3);
                }
            }

            findInfoAllByParent.get(i).setTypeList(selectByExample2);
        }
        return Result.ok(findInfoAllByParent);
    }

    /**
     * 根据大类查询小类
     */
    @RequestMapping(value = "/findInfoAllByParentByType")
    @ResponseBody
    public Result findInfoAllByParentByType(String type) {
        YhHealthLectureCategoryApp yhHealthLectureCategoryApp = new YhHealthLectureCategoryApp();
        yhHealthLectureCategoryApp.setParentId(type);
        List<YhHealthLectureCategoryApp> findInfoAllByParent = yhHealthLectureCategoryService.findInfoAllByParent(yhHealthLectureCategoryApp);
        return Result.ok(findInfoAllByParent);
    }

}