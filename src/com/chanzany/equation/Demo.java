package com.chanzany.equation;

public class Demo {
    public static void main(String[] args) {
        //4*x -5*y =7
        final int N = 100;

        /*for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                if (4*x-5*y==7){
                    System.out.println("x="+x+",y="+y);
                }
            }
        }*/

        //ax+by = c ，a=4,b=-5,c=7
        //ax =c-by
        // 1. 求出一个特殊解 x0,y0
        // 2. 求通解:x=x0+bt,y=y0-at
        int x0 =0;
        int y0 =0;
        for (int y = 0; y < N; y++) {
            if ((7+5*y)%4==0){
                x0=(7+5*y)/4;
                y0=y;
                break;
            }
        }

        for (int t = 0; t < N; t++) {
            System.out.println("x="+(x0+5*t)+",y="+(y0+4*t));
        }
    }
}
