package com.xiaohai.common.utils;

import com.xiaohai.common.daomain.CommentTree;
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
public class TreeUtils {
    TreeUtils() {
    }

    /**
     * 根节点,parentId
     */
    static Integer TREE_PARENT_ID = 0;


    /**
     * 菜单树形结构数据
     *
     * @param list 菜单列表
     * @param type 菜单查询
     * @return List<MenuTree>
     */
    public static List<MenuTree> getTree(List<MenuTree> list, int type) {
        if (list == null) {
            return new ArrayList<>();
        }
        List<MenuTree> treeList = new LinkedList<>();
        for (MenuTree menuTree : list) {
            if (TREE_PARENT_ID.equals(menuTree.getParentId())) {
                MenuTree treeVO = new MenuTree();
                BeanUtils.copyProperties(menuTree, treeVO);
                treeVO.setChildren(getChild(menuTree.getId(), list));
                treeList.add(treeVO);
            } else if (type == 1) {
                treeList.add(menuTree);
            }
        }
        return treeList;
    }

    /**
     * 树形子结构
     *
     * @param id   id
     * @param list 菜单列表
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

    /**
     * 评论树形结构数据
     *
     * @param list 评论列表
     * @return List<CommentTree>
     */
    public static List<CommentTree> getCommentTree(List<CommentTree> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        List<CommentTree> treeList = new LinkedList<>();
        for (CommentTree commentTree : list) {
            if (TREE_PARENT_ID.equals(commentTree.getParentId())) {
                CommentTree treeVO = new CommentTree();
                BeanUtils.copyProperties(commentTree, treeVO);
                treeVO.setCommentTrees(getCommentChild(commentTree.getId(), commentTree.getUsername(), list));
                treeList.add(treeVO);
            }
        }
        return treeList;
    }

    /**
     * 树形子结构
     *
     * @param id   id
     * @param list 评论列表
     * @return List<CommentTree>
     */
    private static List<CommentTree> getCommentChild(Integer id, String username, List<CommentTree> list) {
        List<CommentTree> childrenList = new LinkedList<>();
        for (CommentTree commentTree : list) {
            if (id.equals(commentTree.getParentId())) {
                CommentTree treeVO = new CommentTree();
                BeanUtils.copyProperties(commentTree, treeVO);
                treeVO.setReplyUsername(username);
                childrenList.add(treeVO);
                childrenList.addAll(getCommentChild(commentTree.getId(), commentTree.getUsername(), list));
            }
        }
        return childrenList;
    }
}
