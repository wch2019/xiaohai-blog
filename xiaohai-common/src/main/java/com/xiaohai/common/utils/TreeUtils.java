package com.xiaohai.common.utils;

import com.xiaohai.common.daomain.MenuTree;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 树形结构数据
 *
 * @author wangchenghai
 * @date 2023/03/02 10:34:52
 */
public interface TreeUtils {
    /**
     * 根节点,parentId
     */
    Integer TREE_PARENT_ID = 0;


    /**
     * 菜单树形结构数据
     *
     * @param list 菜单列表
     * @return List<MenuTree>
     */
    static List<MenuTree> getTree(List<MenuTree> list) {
        if(list==null){
            return new ArrayList<>();
        }
        List<MenuTree> treeList = new LinkedList<>();
        for (MenuTree menuTree : list) {
            if (TREE_PARENT_ID.equals(menuTree.getParentId())) {
                MenuTree treeVO = new MenuTree();
                BeanUtils.copyProperties(menuTree, treeVO);
                treeVO.setChildren(getChild(menuTree.getId(), list));
                treeList.add(treeVO);
            }
        }
        return treeList;
    }

    /**
     * 树形子结构
     *
     * @param id        id
     * @param list     菜单列表
     * @return List<MenuTree>
     */
    private static List<MenuTree> getChild(Integer id, List<MenuTree> list) {
        List<MenuTree> childrenList = new LinkedList<>();
        for (MenuTree menuTree : list) {
            if (id.equals(menuTree.getParentId())) {
                MenuTree treeVO = new MenuTree();
                BeanUtils.copyProperties(menuTree, treeVO);
                treeVO.setChildren(getChild(menuTree.getId(), list));
                childrenList.add(treeVO);
            }
        }
        return childrenList;
    }

}
