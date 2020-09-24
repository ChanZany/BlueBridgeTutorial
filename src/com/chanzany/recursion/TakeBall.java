package com.chanzany.recursion;

/**
 * 在 n个球中，任意取出m个(不放回)，求有多少种取法f(n,m)
 *
 * 思路：假设+概率统计
 *
 * 1. 假设从abc...n个球中取出m个球，
 * 1.1 假定取出的结果中分为两种类型-(包含a/不包含a)
 *      包含a的集合个数= 在除开a的(n-1)个球中取出剩余的(m-1)个球
 *      不包含a的集合个数=在除开a的(n-1)个球中取出m个球
 *      也就是 f(n,m)=f(n-1,m-1)+f(n-1,m)
 *
 * 1.2 设置判断条件，
 *      当n==m时，直接返回1(只有一种取法)
 *        n<m ,返回0 不存在
 *        m=0,返回1 只有一种取法
 */
public class TakeBall {

    private static int f(int n, int m) {
        if (n<m) return 0;
        if (n==m) return 1;
        if (m==0) return 1;

        return f(n-1,m-1)+f(n-1,m);
    }

    public static void main(String[] args) {

        int res = f(10,3);
        System.out.println(res);
    }




}
