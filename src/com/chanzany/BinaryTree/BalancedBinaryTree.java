package com.chanzany.BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 在二叉树的基础上添加上树的平衡机制
 * 通过旋转的方式(切换根节点)控制根节点左右子树的深度
 * LL型-->向右转
 * RR型-->向左转
 * LR型-->先对左子树左转再整体右转
 * RL型-->先对右子树右转再整体左转
 * 详见RelatedFig
 */
public class BalancedBinaryTree {
    private int v;
    private BalancedBinaryTree L;
    private BalancedBinaryTree R;
    private int balance = 0;//记录左右子树的深度差距

    public BalancedBinaryTree(int v) {
        this.v = v;
    }

    private void calculateBalance() {
        int LH = L == null ? 0 : L.getHeight();
        int RH = R == null ? 0 : R.getHeight();
        balance = LH - RH;
    }

    public BalancedBinaryTree add(BalancedBinaryTree tree) {
        BalancedBinaryTree root = this;
        if (tree.v < v) {
            if (L == null) L = tree;
            else L = L.add(tree);
        } else {
            if (R == null) R = tree;
            else R = R.add(tree);
        }
        //计算平衡基准并根据基准的情况判断当前子树的型(RR/RL/LL/LR)并通过相应规则进行调整
        calculateBalance();
        if (balance > 2) {
            if (L.getBalance() > 0) root = adjustLL();
            else root = adjustLR();
        }
        if (balance < -2) {
            if (R.getBalance() < 0) root = adjustRR();
            else root = adjustRL();
        }

        return root;
    }

    public int getHeight() {
        int H = 2;
        int LH = L == null ? 0 : L.getHeight();
        int RH = R == null ? 0 : R.getHeight();
        return H + Math.max(LH, RH);
    }

    public int getBalance() {
        return this.balance;
    }

    private BalancedBinaryTree adjustLL() {
        BalancedBinaryTree root = this.L;
        this.L = root.R;
        root.R = this;
        return root;
    }

    private BalancedBinaryTree adjustRR() {
        BalancedBinaryTree root = this.R;
        this.R = root.L;
        root.L = this;
        return root;
    }

    private BalancedBinaryTree adjustLR() {
        this.L = this.L.adjustRR();
        return this.adjustLL();
    }

    private BalancedBinaryTree adjustRL() {
        this.R = this.R.adjustLL();
        return this.adjustRR();
    }

    //中序遍历
    public void mid_trav(){
        if (L!=null)
            L.mid_trav();
        System.out.println(v);
        if (R!=null)
            R.mid_trav();
    }
    /**
     * 层序遍历
     */
    public void sequenceErgodic() {
        Queue<BalancedBinaryTree> queue = new LinkedList<>();
        BalancedBinaryTree temp = null;
        queue.add(this);
        while(!queue.isEmpty()) {
            temp = queue.poll();
            temp.calculateBalance();
            System.out.println("当前节点值：" + temp.v + ", BF：" + temp.balance);
            if(temp.L != null) {
                queue.add(temp.L);
            }
            if(temp.R != null) {
                queue.add(temp.R);
            }
        }
    }
}

class BalancedBinaryTreeTest {
    public static void main(String[] args) {
        BalancedBinaryTree root = new BalancedBinaryTree(6);
        BalancedBinaryTree root1 = root.add(new BalancedBinaryTree(5));
        BalancedBinaryTree root2 = root1.add(new BalancedBinaryTree(4));
        BalancedBinaryTree root3 = root2.add(new BalancedBinaryTree(3));
        BalancedBinaryTree root4 = root3.add(new BalancedBinaryTree(2));
        BalancedBinaryTree root5 = root4.add(new BalancedBinaryTree(1));

       root5.sequenceErgodic();
    }
}