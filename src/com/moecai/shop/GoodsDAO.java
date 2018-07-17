package com.moecai.shop;

import java.sql.*;
import java.util.Scanner;

import static com.moecai.shop.PrintUtil.printString;


public class GoodsDAO {
    public GoodsDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shop?useSSL=false&serverTimezone=GMT",
                "root", "");
    }

    public void addGoods() {
        Scanner in = new Scanner(System.in);
        while (true) {
            printString("执行添加商品操作：");
            System.out.println();
            printString("添加商品的名称：");
            String name = in.nextLine();
            System.out.println();
            printString("添加商品的价格：");
            float price = in.nextFloat();
            System.out.println();
            printString("添加商品的数量");
            int num = in.nextInt();
            System.out.println();
            String sql = String.format("Insert into GOODS  values (null,'%s',%f,%d)", name, price, num);
//            System.out.println(sql);
            execute(sql);
            printString("是否继续（y/n）");
            String go = in.next();
            if ("n".equals(go)) {
                break;
            }
        }
    }

    public void updateGoods() {
        Scanner in = new Scanner(System.in);
        Goods goods = new Goods();
        boolean flag = true;
        printString("执行更改商品操作");
        System.out.println();
        printString("输入更改商品名称");
        String name = in.nextLine();
        String sql = String.format("select * from goods where GNAME='%s'", name);
        try (Connection c = getConnection();
             Statement s = c.createStatement()) {
            s.execute(sql);
            ResultSet rs = s.getResultSet();
            if (rs.next()) {
                goods.setName(rs.getString(2));
                goods.setId(rs.getInt(1));
                goods.setPrice(rs.getFloat(3));
                goods.setNum(rs.getInt(4));
            } else {
                System.out.println("找不到该商品");
                flag = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (flag) {
            System.out.printf("商品名称\t商品价格\t商品数量" +
                    "\n%s\t%.2f\t%d\n", goods.getName(), goods.getPrice(), goods.getNum());
            while (true) {
                printString("选择您要更改的内容：");
                printString("1.更改商品名称");
                printString("2.更改商品价格");
                printString("3.更改商品数量");
                int choose = in.nextInt();
                if (choose == 0) {
                    break;
                }
                System.out.println("请输入修改后的值");
                switch (choose) {
                    case 1:
                        String newName = in.nextLine();
                        sql = String.format("update goods set GNAME='%s' where GID=%d", newName, goods.getId());
                        execute(sql);
                        break;
                    case 2:
                        float newPrice = in.nextFloat();
                        sql = String.format("update goods set GPRICE=%f where GID=%d", newPrice, goods.getId());
                        execute(sql);
                        break;
                    case 3:
                        int newNum = in.nextInt();
                        sql = String.format("update goods set GNUM=%d where GID=%d", newNum, goods.getId());
                        execute(sql);
                        break;
                    default:
                        break;
                }
                System.out.println();
                System.out.println("是否继续（y/n)");
                String go = in.next();
                if ("n".equals(go)) {
                    break;
                }
            }
        }
    }


    private void execute(String sql) {
        try (Connection c = getConnection();
             Statement s = c.createStatement()
        ) {
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
