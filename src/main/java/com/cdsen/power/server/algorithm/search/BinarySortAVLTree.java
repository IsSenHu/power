package com.cdsen.power.server.algorithm.search;

/**
 * 平衡二叉树
 *
 * @author HuSen
 * create on 2020/1/7 14:22
 */
@SuppressWarnings("DuplicatedCode")
public class BinarySortAVLTree {

    /**
     * 左高
     */
    private static final int LH = 1;
    /**
     * 等高
     */
    private static final int EH = 0;
    /**
     * 右高
     */
    private static final int RH = -1;

    private static class BiTNode {
        private int data;
        /**
         * 结点的平衡因子
         */
        private int bf;
        private BiTNode lChild;
        private BiTNode rChild;
    }

    /**
     * 对以node为根的二叉排序树作右旋处理
     *
     * @param parent node的父节点
     * @param node   Node
     */
    private static void rightRotate(BiTNode parent, BiTNode node) {
        boolean tag = parent.lChild == node;
        // 将左孩子结点定义为l
        BiTNode l = node.lChild;
        // 将l的右子树变为node的左子树
        node.lChild = l.rChild;
        // 再将node改成l的右子树
        l.rChild = node;
        // 最后将l替换node成为根结点
        if (tag) {
            parent.lChild = l;
        } else {
            parent.rChild = l;
        }
    }

    /**
     * 对以node为根的二叉排序树作左旋处理
     *
     * @param parent node的父节点
     * @param node Node
     */
    private static void leftRotate(BiTNode parent, BiTNode node) {
        boolean tag = parent.lChild == node;
        BiTNode r = node.rChild;
        node.rChild = r.lChild;
        r.lChild = node;
        if (tag) {
            parent.lChild = r;
        } else {
            parent.rChild = r;
        }
    }

    private static void leftBalance(BiTNode parent, BiTNode t) {
        BiTNode l = t.lChild;
        switch (l.bf) {
            // 新结点插入在T的左孩子的左子树上，要作单右旋处理
            case LH:
                t.bf = l.bf = EH;
                rightRotate(parent, t);
                break;
            // 新结点插入在T的左孩子的右子树上，要作双旋处理
            case RH:
                BiTNode lr = l.rChild;
                switch (lr.bf) {
                    case LH:
                        t.bf = RH;
                        l.bf = EH;
                        break;
                    case EH:
                        t.bf = l.bf = EH;
                        break;
                    case RH:
                        t.bf = EH;
                        l.bf = LH;
                        break;
                }
                lr.bf = EH;
                leftRotate(parent, t.lChild);
                rightRotate(parent, t);
        }
    }

    private static void rightBalance(BiTNode parent, BiTNode t) {
        BiTNode r = t.rChild;
        switch (r.bf) {
            case RH:
                t.bf = r.bf = EH;
                leftRotate(parent, t);
                break;
            case LH:
                BiTNode rl = r.lChild;
                switch (rl.bf) {
                    case RH:
                        t.bf = RH;
                        r.bf = EH;
                        break;
                    case EH:
                        t.bf = r.bf = EH;
                        break;
                    case LH:
                        t.bf = EH;
                        r.bf = LH;
                        break;
                }
                rl.bf = EH;
                rightRotate(parent, t.rChild);
                leftRotate(parent, t);
        }
    }

    public static void main(String[] args) {

    }
}
