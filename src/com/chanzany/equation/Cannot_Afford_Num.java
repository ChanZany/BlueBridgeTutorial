package com.chanzany.equation;

/**
 * 4颗糖一包 或 7颗一包 两种糖
 * 在这种包装下，最大不能买到的数量是17
 * 在已知两个包装的数量时，求最大不能组合出的数字
 *
 * 解法：数组标识,枚举
 * 原理：方程求解
 *  ax+by != c ，设自然数a，b互质，则不能表示成ax+by（x，y为非负整数）的最大整数是ab-a-b
 */
public class Cannot_Afford_Num {
    public static void main(String[] args) {
        final int N = 1000;
        int a = 4;
        int b = 7;
        System.out.println(a*b-a-b);

    }
}
