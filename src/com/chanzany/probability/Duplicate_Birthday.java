package com.chanzany.probability;

/**
 * 30 人的班级，出现生日重复的概率有多大
 * <p>
 * 1. 生日的可取范围为1~365
 * 2. 每次实验对每个人的生日取随机数赋值给记录表（365天）中
 * 3. 如果在对某一天做记录时发现记录表该天的记录已经存在，则出现了重复生日，n+1
 * 4. 重复实验N次
 * 5. 结果就等于 n/N
 * <p>
 * 概率模拟
 */
public class Duplicate_Birthday {

    public static void main(String[] args) {
        final int N = 1000 * 100;//试验次数
        int n = 0;
        for (int i = 0; i < N; i++) {
            int[] birthday = new int[365];

            for (int j = 0; j < 30; j++) {
                int p = (int) (Math.random() * 365);
                if (birthday[p] == 1) {
                    n++;
                    break;
                } else {
                    birthday[p] = 1;
                }
            }
        }

        System.out.println((n+0.0)/N);
    }

}
