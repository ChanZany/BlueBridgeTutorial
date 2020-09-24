package com.chanzany.recursion;

/*
　给定一条标有整点(1, 2, 3, ...)的射线. 定义两个点之间的距离为其下标之差的绝对值.
　　Laharl, Etna, Flonne一开始在这条射线上不同的三个点, 他们希望其中某个人能够到达下标最大的点.
　　**每个角色只能进行下面的3种操作, 且每种操作不能每人不能进行超过一次.**
　　1.移动一定的距离
　　2.把另一个角色高举过头
　　3.将举在头上的角色扔出一段距离
　　每个角色有一个movement range参数, 他们只能移动到没有人的位置, 并且起点和终点的距离不超过movement range.
如果角色A和另一个角色B距离为1, 并且角色B没有被别的角色举起, 那么A就能举起B. 同时, B会移动到A的位置,B原来所占的位置变为没有人的位置.
被举起的角色不能进行任何操作, 举起别人的角色不能移动.
同时, 每个角色还有一个throwing range参数, 即他能把举起的角色扔出的最远的距离.
注意, 一个角色只能被扔到没有别的角色占据的位置. 我们认为一个角色举起另一个同样举起一个角色的角色是允许的.
这种情况下会出现3个人在同一个位置的情况.
根据前面的描述, 这种情况下上面的两个角色不能进行任何操作, 而最下面的角色可以同时扔出上面的两个角色.
你的任务是计算这些角色能够到达的位置的最大下标, 即最大的数字x, 使得存在一个角色能够到达x.

输入共三行, 分别为Laharl, Etna, Floone的信息.
　　每一行有且仅有3个整数, 描述对应角色的初始位置, movement range, throwing range.
　　数据保证3个角色的初始位置两两不相同且所有的数字都在1到10之间.</div>
输出仅有1个整数, 即Laharl, Etna, Flonne之一能到达的最大距离.
样例输入
9 3 3
4 3 1
2 3 3
样例输出
15


分析：
每个角色只能进行下面的3种操作, 且每种操作不能每人不能进行超过一次.
所以可以划分出总共的动作空间如下
move,lift,throw
0,    1,   2, //Laharl的三个动作
3,    4,   5  //Etna的三个动作
6,    7,   8  //LFlonne的三个动作
角色的初始位置, movement range, throwing range.
9 3 3
4 3 1
2 3 3
还需要一个记录数组，标记已经使用过的动作，每次选择动作都只能挑选未被使用的，
1. 搜索0，3，6时，即移动操作的时候，需要枚举他所能到达的所有地方。
    注意可以往前移动也可以往后移动。进行移动的人必须还没有进行过移动，
    因为每种操作只能进行一次，也不能是被举起或举起别人的状态。他所到达的地方必须是个空位置。
2. 搜索1，4，7时，即举起别人的操作的时候，当前进行操作的人的状态不能是正在被举起，也不能是正在举起别人或者是已经进行过
举起别人的操作。他能举起的只有和他距离相差为1的人。如果他旁边的人正在举起其他人，则他可以把这两个人一起举起来，要注意把
最上面的人的位置也要修改一下。如果他旁边的人正在被别人举起，则不能重复举起这个人。
3. 搜索2，5，8时，即抛的操作时，当前进行操作的人头顶必须有人，而且不能是正在被别人举起的状态。
然后进行递归遍历寻找所有可能的动作组合并更新最大 距离
然后打印最大距离即可
*/

import java.util.Scanner;


public class LiftAndThrow {

    //地图的最大长度
    public static final int MAXLEN = 50;
    //坐标轴 标记当前位置是否有人
    public static boolean[] Pos = new boolean[MAXLEN];
    //标记当前操作是否执行过了
    public static boolean[] visit = new boolean[9];
    //最大能够到达的距离
    public static int max;
    //保持三人的状态数组
    static Person[] p;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        p = new Person[3];

        for (int i = 0; i < 3; i++) {
            p[i] = new Person(s.nextInt(), s.nextInt(), s.nextInt(),
                    false, false, -1, false, false);
            Pos[p[i].pos] = true;
        }

        for (int i = 0; i < 9; i++) {
            //第一次操作不能是扔别人 只能是举起或者移动
            if ((i % 3) != 2) {
                visit[i] = true;
                dfs(i, 1);
                visit[i] = false;
            }
        }

        System.out.println(max);

    }


    /**
     * @Param [k 当前执行的操作 , step 当前执行的步数]
     * k可以算出第 n 个人执行的操作，和对应的操作类型 m
     * n=k/3
     * m=k%3;
     * m==0 表示移动
     * m==1 表示举起
     * m==2 表示扔出
     **/
    private static void dfs(int k, int step) {
        int n = k / 3;
        int m = k % 3;

        //移动
        if (m == 0) {
            //如果这个人已经移动过或者正在被举起或者正在举起别人 就不能移动
            if (p[n].hasMoved || p[n].lifted || p[n].lifting) return;

            int i = 1; //i是移动的距离
            //如果已经是最后一步  只需要移动最大距离
            if (step == 9) {
                i = p[n].maxMove;
            } else {
                //找出需要移动的人之前有没有别人  如果有人只需要从离他最近的人的前一个位置开始搜索
                for (int j = 1; j < p[n].pos; j++) {
                    if (Pos[j]) {
                        int l = -(p[n].pos - j - 1);
                        i = Math.min(i, l);
                        //因为要找到离当前需要移动的人最近的人 因此这里不能break
                    }
                }
                //如果他之前的人的前一个位置不能超过他能移动到的最大距离
                i = Math.max(i, -p[n].maxMove);
            }

            for (; i <= p[n].maxMove; i++) {
                //只有当移动的位置附近有人或者移动的距离是最大距离才有意义
                if (Pos[p[n].pos + i - 1] || Pos[p[n].pos + i + 1] || i == p[n].maxMove) {
                    //不能移动到地图之外  移动到的位置不能有人
                    if (p[n].pos + i > 0 && !Pos[p[n].pos + i])
                        //不能移动到原处
                        if (i == 0) continue;

                    //移动
                    Pos[p[n].pos] = false;        //移动前的位置需要标记为没人
                    p[n].pos += i;                //向前走
                    p[n].hasMoved = true;         //标记已经移动过
                    Pos[p[n].pos] = true;         //移动到的位置需要标记为有人
                    max = Math.max(max, p[n].pos);  //更新最大值


                    //继续搜索
                    for (int j = 0; j < 9; j++) {
                        //当前操作没有进行过才搜索
                        if (!visit[j]) {
                            visit[j] = true;
                            dfs(j, step + 1);
                            visit[j] = false;
                        }
                    }

                    //回溯
                    p[n].hasMoved = false;
                    Pos[p[n].pos] = false;
                    p[n].pos -= i;
                    Pos[p[n].pos] = true;

                }

            }

        }
        //举起
        else if (m == 1) {
            //如果当前的人已经举起过别人 或者 已经被举起 或者正在举着别人  直接返回
            if (p[n].hasLifted || p[n].lifted || p[n].lifting) return;

            //遍历三个人
            for (int i = 0; i < 3; i++) {
                //如果当前这个人的附近有人 才可以举起别人
                if (Math.abs(p[i].pos - p[n].pos) == 1) {
                    //如果旁边的这个人已经被别人举起了则不能重复举起
                    if (p[i].lifted) continue;

                    p[n].lifting = true;      //标记当前这个人正在举着别人
                    p[n].lift = i;            //标记当前这个人举着的是哪一个人
                    p[n].hasLifted = true;    //标记这个人已经举过别人了


                    p[i].lifted = true;       //标记被举着的人当前被举着
                    int temp = p[i].pos;      //存储被举着的人被举之前的位置 用于回溯调用
                    Pos[p[i].pos] = false;    //被举着的人直接占用的位置要清空
                    p[i].pos = p[n].pos;      //被举着的人要移动到举人者的位置
                    //如果被举的人还举着别人 那么这个人也要相应地移动过来
                    if (p[i].lifting) {
                        int j = p[i].lift;
                        p[j].pos = p[i].pos;
                    }

                    //继续搜索
                    for (int j = 0; j < 9; j++) {
                        if (!visit[j]) {
                            visit[j] = true;
                            dfs(j, step + 1);
                            visit[j] = false;
                        }
                    }

                    //回溯
                    p[n].lifting = false;
                    p[n].lift = -1;
                    p[n].hasLifted = false;

                    p[i].pos = temp;
                    Pos[p[i].pos] = true;
                    p[i].lifted = false;

                    if (p[i].lifting) {
                        int j = p[i].lift;
                        p[j].pos = p[i].pos;
                    }
                }
            }
        }
        //扔
        else {
            //如果当前的人没有举起人 或者正在被别人举起就返回
            if (!p[n].lifting || p[n].lifted) return;

            int i = 1;
            //如果当前已经是最后一步 只要让这个人向前扔出最大距离
            if (step == 9) {
                i = p[n].maxThrow;
            } else {
                //否则需要从距离他最近的前一个人的前一个位置开始搜索
                for (int j = 1; j < p[n].pos; j++) {
                    if (Pos[j]) {
                        int l = -(p[n].pos - j - 1);
                        i = Math.min(i, l);
                    }
                }
                //距离他最近的前一个人的前一个位置不能超过了扔的距离的最大值
                i = Math.max(i, -p[n].maxThrow);
            }

            for (; i <= p[n].maxThrow; i++) {
                //只有扔到的位置前后有人 或者扔出了最大距离才有意义
                if (Pos[p[n].pos + i - 1] || Pos[p[n].pos + i + 1] || i == p[n].maxThrow) {
                    //扔出的距离不能超出地图  扔到的位置上也不能有人
                    if (p[n].pos + i > 0 && !Pos[p[n].pos + i]) {
                        //如果扔的距离是0 就进行下一次搜索
                        if (i == 0) continue;

                        p[n].lifting = false;                 //扔出去后当前这个人没有举起任何人
                        int j = p[n].lift;
                        p[j].pos += i;                        //更新被扔人的位置
                        p[n].lift = -1;                       //当前这个人没有举起任何人
                        Pos[p[j].pos] = true;                 //被扔的人的新位置被标记为占用
                        p[j].lifted = false;                  //被扔的人被扔之后没有被任何人举起
                        max = Math.max(max, p[j].pos);      //更新最大值


                        //如果被扔的人还举着别人 那么这个人的位置也要同步更新
                        if (p[j].lifting) {
                            int t = p[j].lift;
                            p[t].pos = p[j].pos;
                        }


                        //继续搜索
                        for (int q = 0; q < 9; q++) {
                            if (!visit[q]) {
                                visit[q] = true;
                                dfs(q, step + 1);
                                visit[q] = false;
                            }
                        }

                        //回溯

                        p[n].lifting = true;
                        p[n].lift = j;

                        Pos[p[j].pos] = false;
                        p[j].pos -= i;
                        Pos[p[j].pos] = true;
                        p[j].lifted = true;
                        if (p[j].lifting) {
                            int t = p[j].lift;
                            p[t].pos = p[j].pos;
                        }
                    }
                }
            }
        }

    }

}

class Person {

    //当前位置
    int pos;
    //是否举着别人
    boolean lifting;
    //是否被举着
    boolean lifted;
    //正在举起的人
    int lift;
    //是否移动过了
    boolean hasMoved;
    //是否举起过了
    boolean hasLifted;
    //是否扔过不需要记录 因为只能举起别人一次
    int maxThrow;
    int maxMove;

    public Person(int pos, int maxMove, int maxThrow, boolean lifting, boolean lifted, int lift, boolean hasMoved, boolean hasLifted) {
        this.pos = pos;
        this.lifting = lifting;
        this.lifted = lifted;
        this.lift = lift;
        this.hasMoved = hasMoved;
        this.hasLifted = hasLifted;
        this.maxThrow = maxThrow;
        this.maxMove = maxMove;
    }
}
