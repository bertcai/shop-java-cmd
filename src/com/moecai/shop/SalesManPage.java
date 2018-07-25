package com.moecai.shop;

import java.util.ArrayList;
import java.util.Scanner;

import static com.moecai.shop.PrintUtil.pressContinue;
import static com.moecai.shop.PrintUtil.printString;

public class SalesManPage {
    private SalesManDAO salesManDAO;
    private Scanner in = new Scanner(System.in);


    public SalesManPage() {
        salesManDAO = new SalesManDAO();
    }

    public void printAddSalesMan() {
        while (true) {
            printString("执行添加售货员操作：");
            printString("添加售货员姓名：");
            String sName = in.nextLine();
            printString("添加售货员密码：");
            String sPassword = in.nextLine();
            SalesMan salesMan = new SalesMan(sName, sPassword);
            salesManDAO.addSalesMan(salesMan);
            System.out.println("是否继续(y/n)");
            String go = in.nextLine();
            if ("n".equals(go)) {
                break;
            }
        }
    }

    public void printUpdateSalesMan() {
        SalesMan salesMan;
        while (true) {
            printString("执行更改售货员操作：");
            printString("输入更改的收货员的姓名：");
            String sName = in.nextLine();
            String sql = String.format("select * from salesman where sname='%s'", sName);
            salesMan = salesManDAO.getSalesMan(sql);
            if (salesMan == null) {
                System.out.println("该售货员不存在！");
                continue;
            }
            System.out.printf("售货员名\t密码\n");
            System.out.printf("%s\t%s\n", salesMan.getsName(), salesMan.getsPassword());
            printString("选择您要更改的内容：");
            printString("1.更改售货员姓名：");
            printString("2.更改售货员密码：");
            int choose = in.nextInt();
            in.nextLine();
            if (choose == 1) {
                System.out.println("请输入新的姓名：");
                String str = in.nextLine();
                salesMan.setsName(str);
                salesManDAO.updateSalesMan(salesMan);
                System.out.println();
            } else if (choose == 2) {
                System.out.println("请输入新的密码：");
                String str = in.nextLine();
                salesMan.setsPassword(str);
                salesManDAO.updateSalesMan(salesMan);
                System.out.println();
            }
            System.out.println("是否继续(y/n)");
            String go = in.nextLine();
            if ("n".equals(go)) {
                break;
            }
        }
    }

    public void printDeleteSalesMan() {
        while (true) {
            SalesMan salesMan;
            printString("执行删除售货员操作：");
            printString("输入删除的售货员姓名：");
            String sName = in.nextLine();
            String sql = String.format("select * from salesman where sname='%s'", sName);
            salesMan = salesManDAO.getSalesMan(sql);
            if (salesMan == null) {
                System.out.println("该售货员不存在！");
                continue;
            }
            System.out.printf("售货员名\t密码\n");
            System.out.printf("%s\t%s\n", salesMan.getsName(), salesMan.getsPassword());
            printString("是否确定要删除(y/n)");
            String del = in.nextLine();
            if ("y".equals(del)) {
                salesManDAO.deleteSalesMan(salesMan);
                System.out.println("删除成功！");
            }
            printString("是否继续(y/n)");
            String go = in.nextLine();
            if ("n".equals(go)) {
                break;
            }
        }
    }

    public void printSalesManList() {
        printString("执行显示所有售货员操作");
        System.out.println();
        System.out.println("售货员名\t密码\n");
        ArrayList<SalesMan> salesManArrayList =
                salesManDAO.getSalesManList();
        for (SalesMan salesMan : salesManArrayList) {
            System.out.printf("%s\t%s\n\n", salesMan.getsName(), salesMan.getsPassword());
        }
        pressContinue();
    }

    public void printQuerySalesManList() {
        while (true) {
            printString("执行查询售货员操作：\n");
            printString("输入要查询的售货员姓名关键字：");
            String keyword = in.nextLine();
            ArrayList<SalesMan> salesManArrayList =
                    salesManDAO.querySalesManList(keyword);
            for (SalesMan salesMan : salesManArrayList) {
                System.out.printf("%s\t%s\n\n", salesMan.getsName(), salesMan.getsPassword());
            }
            System.out.println("是否继续(y/n)");
            String go = in.nextLine();
            if ("n".equals(go)) {
                break;
            }
        }
    }
}
