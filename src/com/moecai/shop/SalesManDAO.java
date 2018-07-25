package com.moecai.shop;

import java.sql.*;
import java.util.ArrayList;

public class SalesManDAO {
    public SalesManDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shop?useSSL=false&serverTimezone=GMT",
                "root", "");
    }


    public void addSalesMan(SalesMan salesMan) {
        String sName = salesMan.getsName();
        String sPassword = salesMan.getsPassword();
        String sql = String.format("Insert into salesman  values (null,'%s','%s')", sPassword, sName);
        execute(sql);
    }

    public void updateSalesMan(SalesMan salesMan) {
        int sId = salesMan.getsId();
        String sName = salesMan.getsName();
        String sPassword = salesMan.getsPassword();
        String sql = String.format("update salesman set sName='%s',SPassword='%s' where sid=%d",
                sName, sPassword, sId);
        execute(sql);
    }

    public void deleteSalesMan(SalesMan salesMan) {
        int sId = salesMan.getsId();
        String sql = "delete from salesman where sId=" + sId;
        execute(sql);
    }

    public ArrayList<SalesMan> getSalesManList() {
        ArrayList<SalesMan> salesManList = new ArrayList<>();
        String sql = "select * from salesman";
        try (Connection c = getConnection();
             Statement s = c.createStatement()
        ) {
            s.execute(sql);
            ResultSet rs = s.getResultSet();
            while (rs.next()) {
                int sId = rs.getInt(1);
                String sName = rs.getString(3);
                String sPassword = rs.getString(2);
                SalesMan salesMan = new SalesMan(sId, sName, sPassword);
                salesManList.add(salesMan);
                return salesManList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesManList;
    }

    public ArrayList<SalesMan> querySalesManList(String keyword) {
        ArrayList<SalesMan> salesManList = new ArrayList<>();
        String sql = "select * from salesman where sname like " + "'%" + keyword + "%'";
        try (Connection c = getConnection();
             Statement s = c.createStatement()
        ) {
            s.execute(sql);
            ResultSet rs = s.getResultSet();
            while (rs.next()) {
                int sId = rs.getInt(1);
                String sName = rs.getString(3);
                String sPassword = rs.getString(2);
                SalesMan salesMan = new SalesMan(sId, sName, sPassword);
                salesManList.add(salesMan);
                return salesManList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesManList;
    }

    public SalesMan getSalesMan(String sql) {
        SalesMan salesMan;
        try (Connection c = getConnection();
             Statement s = c.createStatement()
        ) {
            s.execute(sql);
            ResultSet rs = s.getResultSet();
            if (rs.next()) {
                int sId = rs.getInt(1);
                String sName = rs.getString(3);
                String sPassword = rs.getString(2);
                salesMan = new SalesMan(sId, sName, sPassword);
                return salesMan;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
