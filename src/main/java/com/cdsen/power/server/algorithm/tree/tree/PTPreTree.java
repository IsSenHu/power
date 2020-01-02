package com.cdsen.power.server.algorithm.tree.tree;

import com.alibaba.fastjson.JSON;
import com.cdsen.power.server.algorithm.tree.TreeUtils;
import com.cdsen.power.server.algorithm.tree.struct.PTree;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 双亲表示法加前序遍历生成二叉树
 *
 * @author HuSen
 * create on 2020/1/2 11:03
 */
public class PTPreTree {

    private static PTree<String> pTree = new PTree<>();
    private static int offset;
    private static String[] seqArr = {"A", "B", "D", "G", "#", "#", "H", "#", "#", "#", "C", "E", "#", "I", "#", "#", "F"};
    private static Queue<String> seq = new LinkedBlockingQueue<>(seqArr.length);
    static {
        Collections.addAll(seq, seqArr);
        pTree.setNodes(new ArrayList<>());
        pTree.setR(0);
    }

    public static void main(String[] args) {
        setNode(-1);
        System.out.println(JSON.toJSONString(pTree.getNodes(), true));
    }

    private static void setNode(int parent) {
        // 为空则返回
        String next = getNext();
        if (next == null) {
            return;
        }
        int current = offset;
        offset++;
        // 否则先根结点
        TreeUtils.addNode(next, parent, pTree.getNodes());
        // 再左子页树
        setNode(current);
        // 最后右子叶树
        setNode(current);
    }

    private static String getNext() {
        if (seq.isEmpty()) {
            return null;
        }
        String item = seq.poll();
        return StringUtils.equals(item, "#") ? null : item;
    }
}
