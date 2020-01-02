package com.cdsen.power.server.algorithm.tree.tree;

import com.alibaba.fastjson.JSON;
import com.cdsen.power.server.algorithm.tree.struct.CTBox;
import com.cdsen.power.server.algorithm.tree.struct.CTNode;
import com.cdsen.power.server.algorithm.tree.struct.CTree;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 孩子表示法加前序遍历生成二叉树
 *
 * @author HuSen
 * create on 2020/1/2 15:22
 */
public class CPreTree {
    private static CTree<String> cTree = new CTree<>();
    private static String[] seqArr = {"A", "B", "D", "G", "#", "#", "H", "#", "#", "#", "C", "E", "#", "I", "#", "#", "F"};
    private static Queue<String> seq = new LinkedBlockingQueue<>(seqArr.length);

    static {
        Collections.addAll(seq, seqArr);
        cTree.setNodes(new ArrayList<>());
        cTree.setR(0);
    }

    public static void main(String[] args) {
        setNode();
        System.out.println(JSON.toJSONString(cTree.getNodes(), true));
    }

    private static CTNode setNode() {
        String next = getNext();
        if (next == null) {
            return null;
        }
        // 如果根结点存在则创建根结点
        CTBox<String> ctBox = new CTBox<>();
        ctBox.setData(next);
        cTree.getNodes().add(ctBox);
        // 记录当前节点所在的位置
        CTNode current = new CTNode();
        current.setChild(cTree.getNodes().size() - 1);
        // 再设置左子树，并返回左子节点的位置信息
        CTNode firstChild = setNode();
        // 设置根结点的第一个节点
        if (firstChild != null) {
            ctBox.setFirstChild(firstChild);
        }
        // 最后设置右子树，并返回右子节点的位置信息
        CTNode nextChild = setNode();
        // 如果第一个节点存在，则设置为下一个节点，否则设置为第一个节点
        if (firstChild != null) {
            firstChild.setNext(nextChild);
        } else if (ctBox.getFirstChild() == null) {
            ctBox.setFirstChild(nextChild);
        }
        return current;
    }

    private static String getNext() {
        if (seq.isEmpty()) {
            return null;
        }
        String item = seq.poll();
        return StringUtils.equals(item, "#") ? null : item;
    }
}
