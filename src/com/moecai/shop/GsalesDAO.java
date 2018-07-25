package com.moecai.shop;

import java.sql.*;
import java.util.ArrayList;

public class GsalesDAO {
    public GsalesDAO() {
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

    public ArrayList<Gsales> dailyGsales() {
        ArrayList<Gsales> gsalesList = new ArrayList<>();
        String sql = "select gname,gprice,gnum, allSum from goods, (select gid as salesid,sum(snum) as allSum " +
                "from gsales  group by gid)as n where gid = salesid";
        try {
            Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String gname = rs.getString(1);
                float gPrice = rs.getFloat(2);
                int gNum = rs.getInt(3);
                int allSnum = rs.getInt("allSum");
                Gsales gsales = new Gsales(gname, gPrice, gNum, allSnum);
                gsalesList.add(gsales);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gsalesList;
    }

    public boolean shopping(Gsales gsales) {
        boolean bool = false;
        String sql = "INSERT INTO GSALES(GID,SID,SNUM) VALUES(?,?,?)";
        try {
            Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, gsales.getgId());
            ps.setInt(2, gsales.getsId());
            ps.setInt(3, gsales.getsNum());
            int rs = ps.executeUpdate();
            if (rs > 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }
}
