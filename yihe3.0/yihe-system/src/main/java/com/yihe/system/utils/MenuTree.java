package com.yihe.system.utils;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yihe.common.core.controller.BaseController;
import com.yihe.system.domain.YhOrgTree;



/**
 * 客户管理树结构
 * @author dong
 *
 */
public class MenuTree extends BaseController {
	 
    private List<YhOrgTree> YhOrgTreeList = new ArrayList<YhOrgTree>();
    public MenuTree(List<YhOrgTree> YhOrgTreeList) {
        this.YhOrgTreeList=YhOrgTreeList;
    }

    //建立树形结构
    public List<YhOrgTree> builTree(String orgcode){
        List<YhOrgTree> treeYhOrgTrees =new  ArrayList<YhOrgTree>();
        for(YhOrgTree YhOrgTreeNode : getRootNode(orgcode)) {
            YhOrgTreeNode=buildChilTree(YhOrgTreeNode);
            YhOrgTreeNode.setNode(YhOrgTreeNode.getChildren().size());
            treeYhOrgTrees.add(YhOrgTreeNode);
        }
        return treeYhOrgTrees;
    }

    //递归，建立子树形结构
    private YhOrgTree buildChilTree(YhOrgTree pNode){
        List<YhOrgTree> chilYhOrgTrees =new  ArrayList<YhOrgTree>();
        for(YhOrgTree YhOrgTreeNode : YhOrgTreeList) {
            if(YhOrgTreeNode.getSuperOrgCode().equals(pNode.getOrgCode())) {
                chilYhOrgTrees.add(buildChilTree(YhOrgTreeNode));
            }
        }
        pNode.setChildren(chilYhOrgTrees);
        return pNode;
    }

    //获取根节点
    private List<YhOrgTree> getRootNode(String orgcode) {         
        List<YhOrgTree> rootYhOrgTreeLists =new  ArrayList<YhOrgTree>();
        for(YhOrgTree YhOrgTreeNode : YhOrgTreeList) {
            if(YhOrgTreeNode.getSuperOrgCode().equals("0") || YhOrgTreeNode.getOrgCode().equals(orgcode)) {
                rootYhOrgTreeLists.add(YhOrgTreeNode);
            }
        }
        return rootYhOrgTreeLists;
    }
    
	//父级list,全部机构list,需要返回的子集
	public  List<YhOrgTree>  getorgbycode(YhOrgTree yhOrgTree,List<YhOrgTree> yhorgtree,List<YhOrgTree> children) {
		
		for (int i = 0; i < yhorgtree.size(); i++) {
			if(yhorgtree.get(i).getSuperOrgCode().equals(yhOrgTree.getOrgCode())) {
				children.add(yhorgtree.get(i));
				return getorgbycode(yhorgtree.get(i), yhorgtree, children);
			}
		}
		return children;
	}
}