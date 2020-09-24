package com.chanzany.equation;

import java.math.BigInteger;

/***
 * 当N=3时得到的是水仙花数，153 = 1^3+5^3+3^3
 * 当N=4时，1634满足条件：1^4+6^4+3^4+4^4=1634
 * 求N=21时，所有满足条件的花朵数
 *
 * 暴力破解--搜索次序
 * 1. 遍历0-9 在21位数中的出现次序得到 多个a[10]
 * 2. 对于每个a[10]进行验证
 *      2.1 求得a[10]对应的数值 bn=k0*a[0]+k1*a[1]+...+k9*a[9]
 *      2.2 统计bn中每位数的出现次数，得到b[10]
 *      2.3 对比a[10]与b[10]，相等则打印，不等则跳出循环，对下一个a[10]进行验证
 * 小技巧:由于每次循环都要计算0~9的21次方，所以提前将结果计算出并缓存在循环外部得到base[10]=[k0,k1,k2...k9]
 */
public class flowerNum_N {
    private static BigInteger[] base = new BigInteger[10]; //每个数字的21次方
    private static BigInteger calcu_21(int n){
        BigInteger a = BigInteger.ONE;
        for (int i = 0; i < 21; i++) {
            a = a.multiply(BigInteger.valueOf(n));
        }
        return a;
    }

    //测试在a中的分配结果是否满足条件k1*a^21+k2*b^21+....=abcdefg....
    private static void test(int[] a ){
        BigInteger bn = BigInteger.ZERO;
        //计算a中每个整数21次方的总和，如122902==>3(a[i])*2^21(base[i])+1*1+1*9==>bn==>s
        for (int i = 0; i < a.length; i++) {
            bn = bn.add(base[i].multiply(BigInteger.valueOf(a[i])));
        }
        String s = bn.toString();
        if (s.length()!=21) return;

        int[] b = new int[10];
        //s=>b[]
        for (int i = 0; i < s.length(); i++) {
            b[s.charAt(i)-'0']++;
        }
        //对比b[i]与a[i]
        for (int i = 0; i < 10; i++) {
            if (a[i] != b[i]) return;
        }
        System.out.println(bn);
    }

    //处理第k个，还有sum个名额，枚举每个整数(0~9)在sum长的数据中出现的次数
    private static void  f(int[] a ,int k,int sum){
        if (sum==0){
            test(a);
            return;
        }
        if (k==a.length-1){
            a[k] = sum;
//            f(a,k+1,0);
            test(a);
            return;
        }
        for (int i = 0; i < sum; i++) { //i 为尝试的出现次数
            a[k] = i; //尝试
            f(a,k+1,sum-i);
            a[k]=0; //回溯
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < base.length; i++) {
            base[i] = calcu_21(i);
        }
        int[] a = new int[10]; //每个数字求几次
        f(a,0,21); //统计a中的k元素在总共21次中出现了几次
    }

}
