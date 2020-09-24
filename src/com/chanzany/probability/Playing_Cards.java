package com.chanzany.probability;


import java.util.Scanner;
import java.util.Stack;

/**
 * 给定4张扑克牌，点数1~10
 * 用+-* / 运算，结果刚好是24
 * <p>
 * 思路：
 * 随机产生组合序列，计算==24 Ok
 * 逆波兰表达式： 6*3+4 --> 4 6 3 * +
 */
public class Playing_Cards {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入4个整数;");
            String[] ss = scanner.nextLine().split(" ");
            f(ss);
        }
    }

    private static void f(String[] ss) {
        for (int k = 0; k < 1000*100; k++) {
            String[] buf = new String[7];
            //用户输入的4个数字
            System.arraycopy(ss, 0, buf, 0, 4);
            for (int i = 4; i < 7; i++) buf[i] = random_op();//随机产生的运算符
            shuffle(buf);
            boolean flag = cacula_24(buf);
            if (flag){
                show(buf);
                break;
            }
        }
    }

    private static void show(String[] buf) {
        Stack<String> stk = new Stack<>();

        for (String operator : buf) {
            if (operator.equals("+") || operator.equals("-") ||
                    operator.equals("*") || operator.equals("/")) {
                stk.push("(" + stk.pop() + operator + stk.pop() + ")");
            } else {
                stk.push(operator);
            }
        }
        for (Object o : stk.toArray()) {
            System.out.print(o + " ");
        }
        System.out.println("ok!!!");
    }

    private static boolean cacula_24(String[] data) {
        Stack<String> stk = new Stack<>();
        try {
            for (String operator : data) {
                if (operator.equals("+") || operator.equals("-") ||
                        operator.equals("*") || operator.equals("/")) {
                    int a = Integer.parseInt(stk.pop());
                    int b = Integer.parseInt(stk.pop());
                    stk.push(op(a, b, operator) + "");
                } else {
                    stk.push(operator);
                }
            }
        } catch (Exception e) {
            return false;
        }
        return stk.size() == 1 && stk.pop().equals("24");
    }

    private static int op(int a, int b, String operator) throws Exception {
        if (operator.equals("+")) return (a + b);
        if (operator.equals("-")) return (a - b);
        if (operator.equals("*")) return (a * b);
        if (a % b != 0) throw new Exception("can not divide");
        return a / b;
    }

    private static void shuffle(String[] buf) {
        for (int i = 0; i < buf.length; i++) {
            int j = (int) (Math.random() * buf.length);
            String t = buf[i];
            buf[i] = buf[j];
            buf[j] = t;
        }
    }

    private static String random_op() {
        int n = (int) (Math.random() * 4);
        if (n == 0) return "+";
        if (n == 1) return "-";
        if (n == 2) return "*";
        return "/";
    }
}
