package com.yihe.system.utils;

import com.yihe.system.domain.YhOrgTreeVojo;

import java.util.ArrayList;
import java.util.List;


/**
 * 客户管理树结构
 *
 * @author dong
 */
public class MenuTreeVojo {
    private List<YhOrgTreeVojo> YhOrgTreeList = new ArrayList<YhOrgTreeVojo>();

    public MenuTreeVojo(List<YhOrgTreeVojo> YhOrgTreeList) {
        this.YhOrgTreeList = YhOrgTreeList;
    }

    //建立树形结构
    public List<YhOrgTreeVojo> builTree(String orgcode) {
        List<YhOrgTreeVojo> treeYhOrgTrees = new ArrayList<YhOrgTreeVojo>();
        for (YhOrgTreeVojo YhOrgTreeNode : getRootNode(orgcode)) {
            YhOrgTreeNode = buildChilTree(YhOrgTreeNode);
            treeYhOrgTrees.add(YhOrgTreeNode);
        }
        return treeYhOrgTrees;
    }

    //递归，建立子树形结构
    private YhOrgTreeVojo buildChilTree(YhOrgTreeVojo pNode) {
        List<YhOrgTreeVojo> chilYhOrgTrees = new ArrayList<YhOrgTreeVojo>();
        for (YhOrgTreeVojo YhOrgTreeNode : YhOrgTreeList) {
            if (YhOrgTreeNode.getSuperOrgCode().equals(pNode.getId())) {
                chilYhOrgTrees.add(buildChilTree(YhOrgTreeNode));
            }
        }
        pNode.setChildren(chilYhOrgTrees);
        return pNode;
    }


    //获取根节点
    private List<YhOrgTreeVojo> getRootNode(String orgcode) {
        List<YhOrgTreeVojo> rootYhOrgTreeLists = new ArrayList<YhOrgTreeVojo>();
        for (YhOrgTreeVojo YhOrgTreeNode : YhOrgTreeList) {
            if (YhOrgTreeNode.getId().equals(orgcode)) {
                rootYhOrgTreeLists.add(YhOrgTreeNode);
            }
        }
        return rootYhOrgTreeLists;
    }

    //建立树形结构
    public List<YhOrgTreeVojo> builTree() {
        List<YhOrgTreeVojo> treeYhOrgTrees = new ArrayList<YhOrgTreeVojo>();
        for (YhOrgTreeVojo YhOrgTreeNode : getRootNode()) {
            YhOrgTreeNode = buildChilTree(YhOrgTreeNode);
            treeYhOrgTrees.add(YhOrgTreeNode);
        }
        return treeYhOrgTrees;
    }


    //获取根节点
    private List<YhOrgTreeVojo> getRootNode() {
        List<YhOrgTreeVojo> rootYhOrgTreeLists = new ArrayList<YhOrgTreeVojo>();
        for (YhOrgTreeVojo YhOrgTreeNode : YhOrgTreeList) {
            String superOrgCode = YhOrgTreeNode.getSuperOrgCode();
            Boolean falg = true;
            for (YhOrgTreeVojo YhOrgTreeNode2 : YhOrgTreeList) {
                if (superOrgCode.equals(YhOrgTreeNode2.getId())) {
                    falg = false;

                }
            }
            if (falg) {
                rootYhOrgTreeLists.add(YhOrgTreeNode);
            }

        }

        return rootYhOrgTreeLists;
    }

}