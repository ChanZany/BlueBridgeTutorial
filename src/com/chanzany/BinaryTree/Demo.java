package com.chanzany.BinaryTree;

/**
 * 线性结构：数组，vector ,stack 数据元素之间存在一对一的关系
 * 非线性结构（数据元素之间是一对多或者多对多的关系）
 * 树结构：二叉树(平衡二叉树、红黑树)
 * 图结构：
 */

/*常规定义
class BiTree{
    class Node{}

}*/
//递归定义
class BiTree {
    private int v;//value
    private BiTree L;//左子树
    private BiTree R;//右字数

    public BiTree(int x) {
        v = x;
    }

    //以顺序(左边小右边大)的方式添加子树
    public void add(BiTree child) {
        if (child.v < v) {
            if (L == null) L = child;
            else L.add(child);
        } else {
            if (R == null) R = child;
            else R.add(child);
        }
    }

    //中序遍历
    public void mid_trav(){
        if (L!=null)
            L.mid_trav();
        System.out.println(v);
        if (R!=null)
            R.mid_trav();
    }


}

public class Demo {

    public static void main(String[] args) {
        BiTree root = new BiTree(5);
        root.add(new BiTree(4));
        root.add(new BiTree(3));
        root.add(new BiTree(2));
        root.add(new BiTree(1));
        root.mid_trav();
    }
}


