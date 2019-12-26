package com.cdsen.power.server.algorithm.graphics;

import lombok.Getter;
import lombok.Setter;

/**
 * 无向图网 邻接矩阵实现 邻接表 十字链表 邻接多重表 边集数组
 *
 * 最小生成树
 * 普里姆算法
 * 克鲁斯卡尔算法
 * @author HuSen
 * create on 2019/12/24 10:19
 */
public class UndirectedGraphNet {

    @Getter
    @Setter
    public static class Vertex {
        private String name;
    }

    @Getter
    @Setter
    public static class Edge {
        private int begin;
        private int end;
        private int weight;
    }

    @Getter
    @Setter
    public static class EdgeNode {
        /**
         * 邻接点域，存储该顶点对应的下标
         */
        private int adjvex;
        /**
         * 用于存储权值，对于非网图可以不需要
         */
        private int weight;
        /**
         * 链域，指向下一个邻接点
         */
        private EdgeNode next;
    }

    @Getter
    @Setter
    public static class VertexNode {
        /**
         * 顶点入度
         */
        private int in;
        /**
         * 顶点域，存储顶点信息
         */
        private int data;
        /**
         * 边标头指针
         */
        private EdgeNode firstEdge;
    }

    private Vertex[] vertices;

    private Edge[] edges;

    private int[] pathMatirx;

    private int[] shortPathTable;

    private int[][] pathMatirx_;

    private int[][] shortPathTable_;

    private VertexNode[] adjList;

    /**
     * 顶点数
     */
    private int numVertexes;

    /**
     * 边数
     */
    private int numEdges;

    /**
     * 领接矩阵
     */
    private int[][] arc;

    public UndirectedGraphNet(Vertex[] vertices, int[][] arc) {
        this.vertices = vertices;
        this.arc = arc;
        // 构造edges
    }

    /**
     * 普里姆算法
     * Prim 算法生成最小生成树
     */
    public void miniSpanTreePrim() {
        int min, i, j, k;
        int length = this.vertices.length;
        // 保存相关顶点下标
        int[] adjvex = new int[length];
        // 保存相关顶点间边的权值
        int[] lowcost = new int[length];
        // 初始化第一个权值为0，即V0加入生成树
        lowcost[0] = 0;
        // 初始化第一个顶点下标为0
        adjvex[0] = 0;
        for (i = 1; i < length; i++) {
            // 将V0顶点与之有边的权值存入数组
            lowcost[i] = this.arc[0][i];
            // 初始化都为V0的下标
            adjvex[i] = 0;
        }
        for (i = 1; i < length; i++) {
            min = Integer.MAX_VALUE;
            j = 1; k = 0;
            while (j < length) {
                if (lowcost[j] != 0 && lowcost[j] < min) {
                    min = lowcost[j];
                    k = j;
                }
                j++;
            }
            System.out.println("(" + adjvex[k] + ", " + k + ")");
            // 将当前顶点的权值设为0，表示此顶点已经完成任务
            lowcost[k] = 0;
            for (j = 1; j < length; j++) {
                // 若下标为k顶点各边权值小于此前这些顶点为被加入生成树权值
                if (lowcost[j] != 0 && this.arc[k][j] < lowcost[j]) {
                    // 将较小权值存入lowcost
                    lowcost[j] = this.arc[k][j];
                    // 将下标为k的顶点存入adjvex
                    adjvex[j] = k;
                }
            }
        }
    }

    /**
     * 克鲁斯卡尔算法
     * Kruskal 算法生成最小生成树
     */
    public void miniSpanTreeKruskal() {
        int i, n, m;
        int length = this.vertices.length;
        // 定义一数组用来判断边与边是否形成环路
        int[] parent = new int[length];
        for (i = 0; i < length; i++) {
            // 初始化数组值为0
            parent[i] = 0;
        }
        for (i = 0; i < this.edges.length; i++) {
            n = find(parent, this.edges[i].getBegin());
            m = find(parent, this.edges[i].getEnd());
            // 假如n与m不等，说明此边没有与现有生成树形成环路
            if (n != m) {
                // 将此边的结尾顶点放入下标为起点的parent中，表示此顶点已经在生成树集合中
                parent[n] = m;
                System.out.println(this.edges[i].getBegin() + ", " + this.edges[i].getEnd() + ", " + this.edges[i].getWeight());
            }
        }
    }

    /**
     * 查找连线顶点的尾部下标
     *
     * @param parent 数组
     * @param f 连线顶点的头部下标
     * @return 连线顶点的尾部下标
     */
    private int find(int[] parent, int f) {
        while (parent[f] > 0) {
            f = parent[f];
        }
        return f;
    }

    /**
     * 迪杰斯特拉算法
     * Dijkstra 算法求最短路径
     *
     * @param v0 起点
     */
    private void shortestPathDijkstra(int v0) {
        int v, w, k = 0, min;
        int length = this.vertices.length;
        // final_[w] = 1 表示求得v0至vw的最短路径
        int[] final_ = new int[length];
        // 初始化数据
        for (v = 0; v < length; v++) {
            final_[v] = 0;
            this.shortPathTable[v] = this.arc[v0][v];
            this.pathMatirx[v] = 0;
        }
        // v0至v0的路径为0
        this.shortPathTable[v0] = 0;
        // v0至v0不需要求路径
        final_[v0] = 1;
        //  开始主循环，每次求得v0到某个v顶点的最短路径
        for (v = 1; v < length; v++) {
            min = Integer.MAX_VALUE;
            for (w = 0; w < length; w++) {
                if (final_[w] != 0 && this.shortPathTable[w] < min) {
                    k = w;
                    min = this.shortPathTable[w];
                }
            }
            // 将目前找到的最近顶点置为1
            final_[k] = 1;
            for (w = 0; w < length; w++) {
                int nowMin = min + this.arc[k][w];
                if (final_[w] != 0 && nowMin < this.shortPathTable[w]) {
                    this.shortPathTable[w] = nowMin;
                    this.pathMatirx[w] = k;
                }
            }
        }
    }

    /**
     * Floyd 算法，求网图G中各顶点v到其余顶点w最短路劲p[v][w]及带权长度D[v][w]
     */
    private void shortestPathFloyd() {
        int v, w, k;
        int length = this.vertices.length;
        // 初始化
        for (v = 0; v < length; v++) {
            for (w = 0; w < length; w++) {
                this.shortPathTable_[v][w] = this.arc[v][w];
                this.pathMatirx_[v][w] = w;
            }
        }
        // k代表中转顶点的下标，v代表起始顶点，w代表结束顶点
        for (k = 0; k < length; k++) {
            for (v = 0; v < length; v++) {
                for (w = 0; w < length; w++) {
                    int sum = this.shortPathTable_[v][k] + this.shortPathTable_[k][w];
                    if (this.shortPathTable_[v][w] > sum) {
                        this.shortPathTable_[v][w] = sum;
                        this.pathMatirx_[v][w] = this.pathMatirx_[v][k];
                    }
                }
            }
        }
    }
}
