package com.chanzany.recursion;

/**
 * 求n个元素的全排列
 * abc acb bac bca cab cba
 *
 * ABC-->(0,0)ABC-->(1,1)ABC-->(2,2)ABC
 *               -->(1,2)ACB-->(2,2)ACB
 *    -->(0,1)BAC-->(1,1)BAC-->(2,2)BAC
 *               -->(1,2)BCA-->(2,2)BCA
 *    -->(0,2)CBA->(1,1)CBA-->(2,2)CBA
 *              -->(1,2)CAB-->(2,2)CAB
 */
public class Generate_full_permutation {

    // k:当前的交换位置（关注点），与其后的元素交换
    private static void f(char[] data,int k) {

        if (k==data.length){
            System.out.println(String.copyValueOf(data));
        }

        for (int i = k; i < data.length; i++) {
            //试探：数据位置交换 abc-->bac
            {char tmp = data[i];data[i] = data[k];data[k] = tmp;}
            //递归 bac-->cab
            f(data,k+1);
            //回溯
            {char tmp = data[i];data[i] = data[k];data[k] = tmp;}
        }

    }

    public static void main(String[] args) {
        char[] data = "ABC".toCharArray();

        f(data,0);

    }


}
