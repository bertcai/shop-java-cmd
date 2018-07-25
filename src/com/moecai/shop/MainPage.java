package com.moecai.shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static com.moecai.shop.PrintUtil.*;

public class MainPage {
    public static void main(String[] args) {
        new MainPage().start();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shop?useSSL=false&serverTimezone=GMT",
                "root", "");
    }

    private void start() {
        Scanner in = new Scanner(System.in);
        while (true) {
            printMain();
            int choose = in.nextInt();
            if (choose == 0) {
                break;
            }
            switch (choose) {
                case 1:
                    printMaintenance();
                    break;
                case 2:
                    printLogin();
                    break;
                case 3:
                    printGoodsManage();
                    break;
                default:
                    break;
            }
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
        while (true) {
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
            if (choose == 0) {
                break;
            }
            switch (choose) {
                case 1:
                    goodsDAO.addGoods();
                    break;
                case 2:
                    goodsDAO.updateGoods();
                    break;
                case 3:
                    goodsDAO.deleteGoods();
                    break;
                case 4:
                    goodsDAO.listAllGoods();
                    break;
                case 5:
                    goodsDAO.queryGoods();
                    break;
                default:
                    break;
            }
        }
    }

    private void printGoodsManage() {
        GsalesDAO gsalesDAO = new GsalesDAO();
        printString("商超购物管理系统>>商品管理");
        printCutLine();
        printOption("1.列出当日卖出商品列表");
        printOption("2.售货员管理");
        printCutLine();
        printString("请选择，输入数字或者按0返回上一级菜单：");
        Scanner in = new Scanner(System.in);
        int choose = in.nextInt();
        in.nextLine();
        switch (choose) {
            case 1:
                ArrayList<Gsales> gsalesList = gsalesDAO.dailyGsales();
                printDailyGsales(gsalesList);
                pressContinue();
                break;
            case 2:
                printSalesManManage();
                break;
            default:
                break;
        }
    }

    private void printSalesManManage() {
        SalesManPage salesManPage = new SalesManPage();
        while (true) {
            printString("商超购物管理系统>>商品管理>>售货员管理");
            printCutLine();
            printOption("1.添加售货员");
            printOption("2.更改售货员");
            printOption("3.删除售货员");
            printOption("4.显示所有售货员");
            printOption("5.查询售货员");
            printCutLine();
            printString("请选择，输入数字或者按0返回上一级菜单：");
            Scanner in = new Scanner(System.in);
            int choose = in.nextInt();
            in.nextLine();
            if (choose == 0) {
                break;
            }
            switch (choose) {
                case 1:
                    salesManPage.printAddSalesMan();
                    break;
                case 2:
                    salesManPage.printUpdateSalesMan();
                    break;
                case 3:
                    salesManPage.printDeleteSalesMan();
                    break;
                case 4:
                    salesManPage.printSalesManList();
                    break;
                case 5:
                    salesManPage.printQuerySalesManList();
                default:
                    break;
            }
        }
    }

    private void printDailyGsales(ArrayList<Gsales> gsalesList) {
        System.out.println("名称\t价格\t数量\t销量\t备注\n");
        for (Gsales gsales : gsalesList) {
            String gName = gsales.getgName();
            float gPrice = gsales.getgPrice();
            int gNum = gsales.getgNum();
            int allSnum = gsales.getAllSnum();
            if (gsales.getgNum() < 10) {
                System.out.printf("%s\t%.2f\t%d\t%d\t*该商品已不足10件\n",
                        gName, gPrice, gNum, allSnum);
            } else {
                System.out.printf("%s\t%.2f\t%d\t%d\t\n",
                        gName, gPrice, gNum, allSnum);
            }
            System.out.println();
        }
    }

    private void printLogin() {
        Scanner in = new Scanner(System.in);
        GoodsDAO goodsDAO = new GoodsDAO();
        printCutLine();
        System.out.println();
        printOption("欢迎使用商超购物管理系统");
        printOption("1.登录系统");
        printOption("2.退出");
        printCutLine();
        printString("请选择，输入数字：");
        int choose = in.nextInt();
        int sid = -1;
        boolean login = false;
        in.nextLine(); //catch choose 1
        if (choose == 1) {
            int times = 0;
            String name = "";
            String password = "";
            while (times < 3) {
                if (times > 0) {
                    printString("用户名或密码错误！");
                    printString(String.format("您还有%d次登录机会，请重新输入：", 3 - times));
                }
                printString("请输入用户名：");
                name = in.nextLine();
                printString("请输入密码：");
                password = in.nextLine();
                try (Connection c = getConnection();
                     Statement s = c.createStatement()
                ) {
                    String sql = String.format("Select * from SALESMAN where SNAME='%s'", name);
                    s.execute(sql);
                    ResultSet rs = s.getResultSet();
                    if (rs.next()) {
                        login = rs.getString(2).equals(password);
                        sid = rs.getInt(1);
                    } else {
                        login = false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (login) {
                    break;
                }
                times++;
            }
            if (login) {
                goodsDAO.shopping(sid);
            }
        }
    }
}