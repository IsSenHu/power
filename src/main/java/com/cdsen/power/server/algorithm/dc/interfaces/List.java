package com.cdsen.power.server.algorithm.dc.interfaces;

/**
 * 线性表接口定义
 *
 * @author HuSen
 * create on 2019/12/17 17:39
 */
public interface List<T> {

    /**
     * 若线性表为空，返回true，否则返回false
     *
     * @return 线性表是否为空
     */
    boolean listEmpty();

    /**
     * 将线性表清空
     */
    void clearList();

    /**
     * 将线性表中的第i个位置元素返回
     *
     * @param i 位置
     * @return 第i个位置的元素
     */
    T getElem(int i);

    /**
     * 在线性表L中查找与给定值e相等的元素，如果查找成功，返回该元素在表中序号表示成功；否则，返回0表示失败
     *
     * @param e 查找的元素
     * @return 查找元素的序号
     */
    int locateElem(T e);

    /**
     * 在线性表中的第i个位置插入新元素e
     *
     * @param i 位置
     * @param e 新元素
     */
    void listInsert(int i, T e);

    /**
     * 删除线性表中的第i个元素，并返回该元素
     *
     * @param i 位置
     * @return 删除的元素
     */
    T listDelete(int i);

    /**
     * 返回线性表的元素个数
     *
     * @return 线性表的元素个数
     */
    int listLength();
}
