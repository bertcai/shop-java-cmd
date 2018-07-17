package com.moecai.shop;

import java.util.Scanner;

import static com.moecai.shop.PrintUtil.printCutLine;
import static com.moecai.shop.PrintUtil.printOption;

public class MainPage {
    public static void main(String[] args) {
        new MainPage().start();
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        printMain();
        int choose = in.nextInt();
        if (choose == 0) {
        }
        if(choose == 1){
            printMaintenance();
        }
    }

    private void printMain() {
        printCutLine();
        System.out.println();
        printOption("1.商品维护");
        printOption("2.前台收银");
        printOption("3.商品管理");
        printCutLine();
        System.out.println("请选择，输入数字或者按0退出：");
    }

    private void printMaintenance() {
        Scanner in = new Scanner(System.in);
        GoodsDAO goodsDAO = new GoodsDAO();
        printCutLine();
        System.out.println();
        printOption("1.添加商品");
        printOption("2.更改商品");
        printOption("3.删除商品");
        printOption("4.显示所有商品");
        printOption("5.查询商品");
        printCutLine();
        System.out.println("请选择，输入数字或者按0返回上一级菜单：");
        int choose = in.nextInt();
        if(choose == 1){
            goodsDAO.addGoods();
        }
        if(choose==2){
            goodsDAO.updateGoods();
        }
    }
}