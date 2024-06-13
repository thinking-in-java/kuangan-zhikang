package com.yihe.system.utils;

import java.util.List;

import com.yihe.system.domain.YhOrgTree;



public class FindChildNodes {
	//父级list,全部机构list,需要返回的子集
		public  List<YhOrgTree>  getorgbycode(YhOrgTree yhOrgTree,List<YhOrgTree> yhorgtree,List<YhOrgTree> children) {
			Boolean havec=false;
			for (int i = 0; i < yhorgtree.size(); i++) {
				if(yhorgtree.get(i).getSuperOrgCode().equals(yhOrgTree.getOrgCode())) {
					havec=false;
						for (int j = 0; j < children.size(); j++) {
							if(children.get(j).getOrgCode().equals(yhorgtree.get(i).getOrgCode())) {
								havec=true;
							}
						}
						if(havec!=true) {
							children.add(yhorgtree.get(i));
						}
					getorgbycode(yhorgtree.get(i), yhorgtree, children);
				}
			}
			return children;
		}
}
