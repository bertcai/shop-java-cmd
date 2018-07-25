package com.moecai.shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static com.moecai.shop.PrintUtil.pressContinue;
import static com.moecai.shop.PrintUtil.printOption;
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

    public void shopping(int sid) {
        GsalesDAO gsalesDAO = new GsalesDAO();
        printOption("购物结算");
        Scanner in = new Scanner(System.in);
        ArrayList<Goods> shoppingCart = new ArrayList<>();
        float total = 0;
        while (true) {
            printString("输入商品关键字：");
            String keyword = in.nextLine();
            String sql = "select * from goods where GNAME like " + "'%" + keyword + "%'";
            ArrayList<Goods> goodsList = getQueryResult(sql);
            printGoodsList(goodsList);
            System.out.println();
            printString("请选择商品：");
            String name = in.nextLine();
            printString("请输入购买数量：");
            int sum = in.nextInt();
            in.nextLine();
            Goods goods = getQueryGoods(name);
            while (goods.getNum() < sum) {
                printString("库存不足，请调整购买数量");
                printString("请输入购买数量：");
                sum = in.nextInt();
                in.nextLine();
            }
            goods.setShopNum(sum);
            shoppingCart.add(goods);
            sql = String.format("update goods set GNUM=%d where GID=%d", goods.getNum() - sum,
                    goods.getId());
            execute(sql);
            System.out.println();
            Gsales gsales = new Gsales(goods.getId(), sid, goods.getShopNum());
            gsalesDAO.shopping(gsales);
            total = printShoppingCart(shoppingCart);
            printString("是否继续(y/n)");
            String go = in.next();
            in.nextLine();
            if ("n".equals(go)) {
                break;
            }
        }
        System.out.println();
        System.out.printf("总计：%.2f\n", total);
        printString("请输入实际缴费金额：");
        float receipt = in.nextFloat();
        in.nextLine();
        System.out.printf("找零：%.2f\n谢谢光临！\n", receipt - total);
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
        goods = getQueryGoods(name);
        String sql = "";
        if (goods != null) {
            System.out.printf("名称\t价格\t数量" +
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

    public void deleteGoods() {
        Scanner in = new Scanner(System.in);
        Goods goods = new Goods();
        boolean flag = true;
        while (true) {
            printString("执行删除商品操作");
            System.out.println();
            printString("输入删除商品名称");
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
                printString("是否确定要删除(y/n)");
                String delete = in.next();
                if ("y".equals(delete)) {
                    sql = String.format("delete from goods where GNAME='%s'", goods.getName());
                    execute(sql);
                }
            }
            printString("是否继续(y/n)");
            String go = in.next();
            if ("n".equals(go)) {
                break;
            }
        }
    }


    public void listAllGoods() {
        printString("显示所有商品");
        String sql = "select * from goods";
        ArrayList<Goods> goodsList = getQueryResult(sql);
        printGoodsList(goodsList);
        pressContinue();
    }

    public void queryGoods() {
        printString("执行查询商品操作");
        System.out.println();
        printString("1.按商品数量升序查询");
        printString("2.按商品价格升序查询");
        printString("3.输入关键字查询商品");
        printString("请选择，输入数字或按0返回上一级菜单");
        String sql = "";
        ArrayList<Goods> goodsList = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int choose = in.nextInt();
        switch (choose) {
            case 1:
                sql = "select * from goods order by GNUM asc";
                goodsList = getQueryResult(sql);
                printGoodsList(goodsList);
                pressContinue();
                break;
            case 2:
                sql = "select * from goods order by GPRICE asc";
                goodsList = getQueryResult(sql);
                printGoodsList(goodsList);
                pressContinue();
                break;
            case 3:
                printString("请输入关键字：");
                String keyword = in.next();
                sql = "select * from goods where GNAME like " + "'%" + keyword + "%'";
                System.out.println(sql);
                goodsList = getQueryResult(sql);
                pressContinue();
                printGoodsList(goodsList);
                break;
            default:
                break;
        }
    }

    private ArrayList<Goods> getQueryResult(String sql) {
        ArrayList<Goods> goodsList = new ArrayList<>();
        try (Connection c = getConnection();
             Statement s = c.createStatement()
        ) {
            s.execute(sql);
            ResultSet rs = s.getResultSet();
            while (rs.next()) {
                Goods goods = new Goods();
                goods.setId(rs.getInt(1));
                goods.setName(rs.getString(2));
                goods.setPrice(rs.getFloat(3));
                goods.setNum(rs.getInt(4));
                goodsList.add(goods);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    private Goods getQueryGoods(String name) {
        Goods goods = new Goods();
        try (Connection c = getConnection();
             Statement s = c.createStatement()
        ) {
            String sql = String.format("select * from goods where GNAME='%s'", name);
            s.execute(sql);
            ResultSet rs = s.getResultSet();
            if (rs.next()) {
                goods.setId(rs.getInt(1));
                goods.setName(rs.getString(2));
                goods.setPrice(rs.getFloat(3));
                goods.setNum(rs.getInt(4));
            } else {
                System.out.println("找不到该商品");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

    private void printGoodsList(ArrayList<Goods> goodsList) {
        System.out.println("名称\t价格\t数量\t备注");
        for (Goods g : goodsList) {
            printGoods(g);
        }
    }

    private void printGoods(Goods goods) {
        if (goods.getNum() > 10) {
            System.out.printf("%s\t%.2f\t%d\n", goods.getName(), goods.getPrice(),
                    goods.getNum());
        } else {
            System.out.printf("%s\t%.2f\t%d\t*该商品已不足10件!\n", goods.getName(),
                    goods.getPrice(), goods.getNum());
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

    private float printShoppingCart(ArrayList<Goods> goodsList) {
        float total = 0f;
        for (Goods g : goodsList) {
            float sum = g.getPrice() * g.getShopNum();
            total += sum;
            System.out.println(
                    String.format("%s\t￥%.2f\t数量%d\t总价%.2f\t", g.getName(), g.getPrice(),
                            g.getShopNum(), sum)
            );
        }
        System.out.println();
        return total;
    }
}
