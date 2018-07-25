package com.moecai.shop;

import java.util.Scanner;

public class PrintUtil {
    public static void printCutLine() {
        System.out.println("***********************************");
    }

    public static void printOption(String content) {
        System.out.println("         " + content + "\n");
    }

    public static void printString(String content) {
        System.out.println(content);
    }

    public static void pressContinue() {
        System.out.println("按Enter键继续...");
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
    }
}
