package com.newangels.gen.util;

import com.newangels.gen.base.BaseUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * List转换tree工具类
 * <p>
 * 示例
 * ListToTreeUtil.listToTree(list, (m) -> "-1".equals(m.get("PID")), "ID", "PID", true);
 * ListToTreeUtil.listToTree(list, (m) -> "-1".equals(m.get("PID")), (m) -> m.get("ID"), (n) -> n.get("PID"), true);
 *
 * @author: 唐 亮
 * @date: 2021/12/27 23:59
 * @since: 1.0
 */
public class ListToTreeUtil {
    /**
     * 并发除法阈值，容器大于此值走并发流提高性能
     */
    private static final int FILL_PALL = 500;
    /**
     * 默认是否进行深拷贝，false不进行深拷贝
     */
    private static final boolean DEFAULT_DEEP = false;

    /**
     * list转换为tree结构数据
     *
     * @param list      数据
     * @param isRoot    判断为根节点的函数
     * @param idFun     id函数
     * @param pidFun    上级id函数
     * @param deepClone 是否进行深克隆，默认为否
     */
    public static List<Map<String, Object>> listToTree(List<Map<String, Object>> list, Predicate<Map<String, Object>> isRoot, Function<Map<String, Object>, ?> idFun, Function<Map<String, Object>, ?> pidFun, boolean deepClone) {
        if (Objects.isNull(list) || Objects.isNull(isRoot) || Objects.isNull(idFun) || Objects.isNull(pidFun)) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> cloneList = list;
        if (deepClone) {
            cloneList = (List<Map<String, Object>>) BaseUtils.deepClone(list);
        }
        //方法复杂度为O(n2)数据量超过一百条时走并发流优化性能
        List<Map<String, Object>> children;
        if (list.size() > FILL_PALL) {
            children = Collections.synchronizedList(new ArrayList<>());
            List<Map<String, Object>> finalCloneList = Collections.synchronizedList(cloneList);
            cloneList.parallelStream()
                    .filter(isRoot).forEachOrdered(s -> {
                children.add(s);
                fillChild(s, finalCloneList, idFun, pidFun);
            });
        } else {
            children = new ArrayList<>();
            for (Map<String, Object> child : cloneList) {
                if (isRoot.test(child)) {
                    children.add(child);
                    fillChild(child, cloneList, idFun, pidFun);
                }
            }
        }

        return children;
    }

    /**
     * list转换为tree结构数据
     *
     * @param list      数据
     * @param isRoot    判断为根节点的函数
     * @param idField   id字段
     * @param pidField  上级id字段
     * @param deepClone 是否进行深克隆，默认为否
     */
    public static List<Map<String, Object>> listToTree(List<Map<String, Object>> list, Predicate<Map<String, Object>> isRoot, String idField, String pidField, boolean deepClone) {
        return listToTree(list, isRoot, (m) -> m.get(idField), (n) -> n.get(pidField), deepClone);
    }

    public static List<Map<String, Object>> listToTree(List<Map<String, Object>> list, Predicate<Map<String, Object>> isRoot, String idField, String pidField) {
        return listToTree(list, isRoot, idField, pidField, DEFAULT_DEEP);
    }

    public static List<Map<String, Object>> listToTree(List<Map<String, Object>> list, Predicate<Map<String, Object>> isRoot, Function<Map<String, Object>, ?> idFun, Function<Map<String, Object>, ?> pidFun) {
        return listToTree(list, isRoot, idFun, pidFun, DEFAULT_DEEP);
    }

    /**
     * 递归生成树节点
     *
     * @param child  每个元素
     * @param list   数据集
     * @param idFun  id函数
     * @param pidFun 上级id函数
     */
    private static void fillChild(Map<String, Object> child, List<Map<String, Object>> list, Function<Map<String, Object>, ?> idFun, Function<Map<String, Object>, ?> pidFun) {
        List<Map<String, Object>> children;
        if (list.size() > FILL_PALL) {
            children = Collections.synchronizedList(new ArrayList<>());
            list.parallelStream().filter(s -> idFun.apply(child).equals(pidFun.apply(s))).forEachOrdered(s -> {
                children.add(s);
                fillChild(s, list, idFun, pidFun);
            });
        } else {
            children = new ArrayList<>();
            for (Map<String, Object> childDiv : list) {
                if (idFun.apply(child).equals(pidFun.apply(childDiv))) {
                    children.add(childDiv);
                    fillChild(childDiv, list, idFun, pidFun);
                }
            }
        }

        child.put("children", children);
    }
}
