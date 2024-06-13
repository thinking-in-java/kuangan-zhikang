package com.yihe.web.controller.system;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.common.core.controller.BaseController;
import com.yihe.system.domain.YhHealthLectureCategory;
import com.yihe.system.service.YhHealthLectureCategoryService;
import com.yihe.system.service.YhHealthLectureInfoService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * YhHealthLectureCategorycontroller
 * Created by dong on 2020/3/26.
 */
@Controller
@RequestMapping("/YhHealthLectureCategory")
@Api(value = "YhHealthLectureCategory控制器", description = "YhHealthLectureCategory管理")
public class YhHealthLectureCategoryController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthLectureCategoryController.class);
	
    @Autowired
    YhHealthLectureCategoryService yhHealthLectureCategoryService;
    
    @Autowired
    YhHealthLectureInfoService yhHealthLectureInfoService;
    
    
    
    @ApiOperation(value = "课堂分类修改")
    @RequestMapping(value = "/updatecategory", method = RequestMethod.POST)
	@ResponseBody
    public Result  updatecategory(YhHealthLectureCategory ory) {
    	int i=yhHealthLectureCategoryService.updateByPrimaryKeySelective(ory);
    	return Result.ok(i);
    }
    
    
    @ApiOperation(value = "课堂分类删除")
    @RequestMapping(value = "/deletecategory", method = RequestMethod.POST)
	@ResponseBody
    public Result  deletecategory(int id) {
    	int i=yhHealthLectureCategoryService.deleteByPrimaryKey(id);
        return Result.ok(i);
    }
    
    @ApiOperation(value = "课堂分类删除子类")
    @RequestMapping(value = "/deleteByparentId", method = RequestMethod.POST)
	@ResponseBody
    public Result  deleteByparentId(String id) {
    	int i=yhHealthLectureCategoryService.deleteByPrimaryKey(Integer.valueOf(id));
    	List<YhHealthLectureCategory> list=yhHealthLectureCategoryService.showcategoryparentId(String.valueOf(id));
    	if(list!=null && !list.isEmpty()) {
    		int i1=yhHealthLectureCategoryService.deleteByparentId(id);
    	}
    	
        return Result.ok(i);
    }
    
	
	@ApiOperation(value = "课堂分类查找")
    @RequestMapping(value = "/showcategory", method = RequestMethod.GET)
	@ResponseBody
    public Result  showlistTree() {
    	
    	List<YhHealthLectureCategory> list=yhHealthLectureCategoryService.showcategory();
    	
    	
    	List<YhHealthLectureCategory> yiji=yhHealthLectureCategoryService.showcategoryyiji();
    	
    	for (YhHealthLectureCategory yhHealthLectureCategory : list) {
    		if(yhHealthLectureCategory.getParentId().equals("0")) {
    			yhHealthLectureCategory.setFateast(1);
    		}else {
    			YhHealthLectureCategory ory=yhHealthLectureCategoryService.selectByPrimaryKey(Integer.valueOf(yhHealthLectureCategory.getParentId()));
        		if(ory.getParentId().equals("0")) {
        			yhHealthLectureCategory.setFateast(1);
        		}else {
        			yhHealthLectureCategory.setFateast(0);
        		}
    		}
    		
			int num=yhHealthLectureInfoService.selectBylectureCount(yhHealthLectureCategory.getCategoryid());
			if(num==0) {
				yhHealthLectureCategory.setTimenum(0);
			}else {
				for (YhHealthLectureCategory yhHealthLectureCategory1 : yiji) {
					if(yhHealthLectureCategory.getParentId().equals(String.valueOf(yhHealthLectureCategory1.getCategoryid()))) {
						if(yhHealthLectureCategory1.getTimenum()==null) {
							yhHealthLectureCategory1.setTimenum(0);
						}
						yhHealthLectureCategory1.setTimenum(yhHealthLectureCategory1.getTimenum()+num);
					}
				}
				yhHealthLectureCategory.setTimenum(num);
			}
		}
    	
    	for (YhHealthLectureCategory yhHealthLectureCategory1 : yiji) {
	    	for (YhHealthLectureCategory yhHealthLectureCategory : list) {
	    		if(yhHealthLectureCategory1.getCategoryid()==yhHealthLectureCategory.getCategoryid()) {
	    			if(yhHealthLectureCategory1.getTimenum()!=null) {
	    				yhHealthLectureCategory.setTimenum(yhHealthLectureCategory1.getTimenum());
	    			}
	    			
	    		}
	    	}
    	}
     
    	
        return Result.ok(list);
    }
    
    @ApiOperation(value = "课堂分类增加")
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	@ResponseBody
    public Result  addCategory(String name,int id) {
    	YhHealthLectureCategory cate=new YhHealthLectureCategory();
    	if(id==0) {
    		cate.setName(name);
    		cate.setParentId("0");
    	}else {
    		cate.setName(name);
    		cate.setParentId(String.valueOf(id));
    	}
    	int i=yhHealthLectureCategoryService.insertSelective(cate);
        return Result.ok(i);
    }

}