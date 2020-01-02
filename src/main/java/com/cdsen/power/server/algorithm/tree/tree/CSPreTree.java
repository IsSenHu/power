package com.cdsen.power.server.algorithm.tree.tree;

import com.alibaba.fastjson.JSON;
import com.cdsen.power.server.algorithm.tree.struct.CSNode;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 兄弟节点表示法加前序遍历生成二叉树
 * 也就是二叉链表
 *
 * @author HuSen
 * create on 2020/1/2 15:49
 */
public class CSPreTree {

    private static String[] seqArr = {"A", "B", "D", "G", "#", "#", "H", "#", "#", "#", "C", "E", "#", "I", "#", "#", "F"};
    private static Queue<String> seq = new LinkedBlockingQueue<>(seqArr.length);

    static {
        Collections.addAll(seq, seqArr);
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(setNode(), true));
    }

    private static CSNode<String> setNode() {
        String next = getNext();
        if (next == null) {
            return null;
        }
        // 根结点存在添加根结点
        CSNode<String> csNode = new CSNode<>();
        csNode.setData(next);
        // 然后再左子叶树
        CSNode<String> firstChild = setNode();
        csNode.setFirstChild(firstChild);
        // 然后再右子叶树
        CSNode<String> rightSib = setNode();
        csNode.setRightSib(rightSib);
        return csNode;
    }

    private static String getNext() {
        if (seq.isEmpty()) {
            return null;
        }
        String item = seq.poll();
        return StringUtils.equals(item, "#") ? null : item;
    }
}
