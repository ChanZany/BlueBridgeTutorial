package com.chanzany.probability;

/**
 * 博弈问题：
 * f(局面 x)->胜负?||胜负概率?
 * for(对我所有可能的走法){
 * 试着走一步-->局面 y
 * f(y) -->flag
 * if(flag为负) return 胜
 * 回溯，恢复局面
 * }
 * 尝试了所有可能的走法都没有胜利的希望，就输掉了这场博弈
 * return 负
 * <p>
 * 取球博弈：
 * 两个人玩取球的游戏。
 * 一共有N个球，每人轮流取球，每次可取集合{n1,n2,n3}中的任何一个数目。
 * 如果无法继续取球，则游戏结束。
 * 此时，持有奇数个球的一方获胜。
 * 如果两人都是奇数，则为平局。
 * 假设双方都采用最聪明的取法，
 * 第一个取球的人一定能赢吗？
 * 试编程解决这个问题。
 * 输入格式：
 * 第一行3个正整数n1 n2 n3，空格分开，表示每次可取的数目 (0<n1,n2,n3<100)
 * 第二行5个正整数x1 x2 ... x5，空格分开，表示5局的初始球数(0<xi<1000)
 * 输出格式：
 * 一行5个字符，空格分开。分别表示每局先取球的人能否获胜。
 * 能获胜则输出+，
 * 次之，如有办法逼平对手，输出0，
 * 无论如何都会输，则输出-
 * 例如，输入：
 * 1 2 3
 * 1 2 3 4 5
 * 程序应该输出：
 * + 0 + 0 -
 */

public class FetchGame {
    private static final int[] arr = new int[]{1, 3, 7, 8};


    //局面：n 所剩球的数目, arr 可以取的数量集合
    //使用缓存提高效率
    private static boolean f(int n, int[] arr) {
        for (int fetchNum : arr) {
            if (n >= fetchNum && !f(n - fetchNum, arr)) return true;
        }
        return false;
    }

    //优化
    /*取球博弈这个问题思路比较明确就是将两个人所有可能取的情况进行遍历，最后看两个人手中球数量奇偶。
    遍历的话可采用深度优先搜索,不过直接深度优先搜索肯定不行，
    因为如果有1000个球那么每次有三种取法，一共差不多就有3的几百次方。这个数据简直是天文数字。
    因此需要进行记忆化存储，将原先算过的数据进行保存。但是要保存所有数据也是不现实的，
    因为我们需要保存的结果是当剩下n个球，自己有m个球，别人有k个球时候的输赢。那么需要一个三维数组a[n][m][k]，
    而n,m,k最大为1000，就需要1000M左右内存也不现实。
    怎么办呢？ 还是看题目要求，是要我们判断最后两个人球的奇偶，也就是说我们只要管奇偶性就可，
    也就是说当剩下300个球，自己3个球，别人8个球的情况和当剩下300个球，自己5个球，别人2个球这种情况最后输赢是一样的。
    因为自己球再多也是奇数，别人球再少也是偶数，而可选是球总数一样所以最后奇偶性也是一样的，输赢也一样。*/
    public static int[][][] cash = new int[1001][2][2];
    public static int f(int rest, int havenum, int othernum) {
        if (rest < 1) {
            if (havenum % 2 != 0 && othernum % 2 == 0)
                return 2;
            if (havenum % 2 != 0 && othernum % 2 != 0)
                return 1;
            if (havenum % 2 == 0 && othernum % 2 == 0)
                return 1;
            return -1;
        }
        boolean equalflag = false;
        for (int sel : arr) {
            if (sel > rest)
                continue;
            int result;
            if (cash[rest - sel][othernum % 2][(havenum + sel) % 2] != 0)
                result = cash[rest - sel][othernum % 2][(havenum + sel) % 2];
            else {
                result = f(rest - sel, othernum, havenum + sel);
                cash[rest - sel][othernum % 2][(havenum + sel) % 2] = result;
            }
            if (result == -1) //对方输，我方赢
                return 2;
            if (result == 1) //平局
                equalflag = true;
        }

        if (equalflag)
            return 1;
        else
            return -1;
    }

    public static void main(String[] args) {
//        System.out.println(f(4, arr));
//        int[] b = { 1, 2, 3, 4, 5, 900 };
        int[] b = { 10};
        for (int total : b) {
            char ch = 0;
            switch (f(total, 0, 0)) {
                case -1:
                    ch = '-';
                    break;
                case 1:
                    ch = '0';
                    break;
                case 2:
                    ch = '+';
                    break;
            }
            System.out.print(ch);
        }
    }


}
