package com.cdsen.power.server.algorithm.search;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Scanner;

/**
 * 二叉排序树，又称二叉查找树。它或者是一棵空树，或者是具有下列性质的二叉树
 * 1.若它的左子树不为空，则左子树上所有结点的值均小于它的根结点的值
 * 2.若它的右子树不为空，则右子树上所有结点的值均大于它的根结点的值
 * 3.它的左右子树也分别为二叉排序树
 *
 * @author HuSen
 * create on 2020/1/7 10:24
 */
@Data
public class BinarySortTree {

    @Data
    private static class BiTNode {
        private int data;
        private BiTNode lChild;
        private BiTNode rChild;

        public BiTNode(int data) {
            this.data = data;
        }
    }

    private BiTNode root;

    private static BinarySortTree binarySortTree = new BinarySortTree();

    private static void insert(int newData) {
        if (binarySortTree.root == null) {
            binarySortTree.root = new BiTNode(newData);
        } else {
            insertTo(binarySortTree.root, newData);
        }
    }

    private static void delete(BiTNode parent, BiTNode node, int deleteData) {
        if (node == null) {
            return;
        }
        // 找到关键字所在的结点
        if (node.data == deleteData) {
            deleteFrom(parent, node);
        } else if (deleteData < node.data) {
            delete(node, node.lChild, deleteData);
        } else {
            delete(node, node.rChild, deleteData);
        }
    }

    private static BiTNode find(int key) {
        return findFrom(binarySortTree.root, key);
    }

    private static BiTNode findFrom(BiTNode node, int key) {
        if (node == null) {
            return null;
        } else if (node.data == key) {
            return node;
        } else if (key < node.data) {
            return findFrom(node.lChild, key);
        } else {
            return findFrom(node.rChild, key);
        }
    }

    private static void deleteFrom(BiTNode parent, BiTNode node) {
        if (node.rChild == null && node.lChild == null) {
            // 叶子结点直接删除
            if (parent != null) {
                if (parent.lChild == node) {
                    parent.lChild = null;
                } else {
                    parent.rChild = null;
                }
            } else {
                binarySortTree.root = null;
            }
        } else if (node.lChild != null && node.rChild == null) {
            // 只有一个左子叶树
            if (parent != null) {
                if (parent.lChild == node) {
                    parent.lChild = node.lChild;
                } else {
                    parent.rChild = node.lChild;
                }
            } else {
                binarySortTree.root = node.lChild;
            }
        } else if (node.lChild == null) {
            if (parent != null) {
                // 只有一个右子树
                if (parent.lChild == node) {
                    parent.lChild = node.rChild;
                } else {
                    parent.rChild = node.rChild;
                }
            } else {
                binarySortTree.root = node.rChild;
            }
        } else {
            // 左右子树都有
            // 转左，然后向右到尽头（找待删结点的前驱），最后一个不为空的就是直接前驱结点
            BiTNode q = node;
            BiTNode s = node.lChild;
            while (s.rChild != null) {
                q = s;
                s = s.rChild;
            }
            // s指向被删结点的直接前驱
            node.data  = s.data;
            if (q != node) {
                // 重接q的右子树
                q.rChild = s.lChild;
            } else {
                // 重接P的左子树
                q.lChild = s.lChild;
            }
        }
    }

    private static void insertTo(BiTNode root, int newData) {
        if (newData < root.data) {
            if (root.lChild == null) {
                root.lChild = new BiTNode(newData);
            } else {
                insertTo(root.lChild, newData);
            }
        } else {
            if (root.rChild == null) {
                root.rChild = new BiTNode(newData);
            } else {
                insertTo(root.rChild, newData);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入一个整数");
            int anInt = scanner.nextInt();
            if (anInt == -1) {
                break;
            }
            insert(anInt);
        }
        System.out.println(JSON.toJSONString(binarySortTree, true));
        System.out.println("请输入你要删除的元素");
        int anInt = scanner.nextInt();
        delete(null, binarySortTree.root, anInt);
        System.out.println(JSON.toJSONString(binarySortTree, true));
        System.out.println("请输入你要查找的元素");
        int find = scanner.nextInt();
        System.out.println(find(find));
    }
}
