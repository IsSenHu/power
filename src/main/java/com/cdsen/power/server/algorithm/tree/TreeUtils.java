package com.cdsen.power.server.algorithm.tree;

import com.cdsen.power.server.algorithm.tree.struct.PTNode;

import java.util.ArrayList;

/**
 * @author HuSen
 * create on 2020/1/2 14:16
 */
public class TreeUtils {

    public static <T> void addNode(T data, int parent, ArrayList<PTNode<T>> nodes) {
        PTNode<T> node = new PTNode<>();
        node.setData(data);
        node.setParent(parent);
        nodes.add(node);
    }
}
