package hu.petrik.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class EtlapDb {
    Connection conn;

    public EtlapDb() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");

    }

    public List<Etlap> getEtlap() throws SQLException {
        List<Etlap> etelek = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM etlap;";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()){
            int id = result.getInt("id");
            String nev = result.getString("nev");
            String leiras = result.getString("leiras");
            int ar = result.getInt("ar");
            String kategoria = result.getString("kategoria");
            Etlap etel = new Etlap(id, nev, leiras, ar, kategoria);
            etelek.add(etel);
        }
        return etelek;
    }
    public int etelHozzaadasa(String nev, String leiras, int ar, String kategoria) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria) VALUES (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nev);
        stmt.setString(2, leiras);
        stmt.setInt(3, ar);
        stmt.setString(4, kategoria);
        return stmt.executeUpdate();
    }

    public boolean etelTorlese(int id) throws SQLException {
        String sql = "DELETE FROM etlap WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public int novelesOsszes(double emelendoOsszeg) throws SQLException {
        String sql;

        if (emelendoOsszeg < 2) {
            sql = "UPDATE etlap SET ar = ar * ?";
        } else {
            sql = "UPDATE etlap SET ar = ar + ?";
        }

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, emelendoOsszeg);
        return stmt.executeUpdate();
    }
    public int noveles(double emelendoOszzeg, int id) throws SQLException {
        String sql;

        if (emelendoOszzeg < 2) {
            sql = "UPDATE etlap SET ar = ar * ? WHERE id = ?";
        } else {
            sql = "UPDATE etlap SET ar = ar + ? WHERE id = ?";
        }

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, emelendoOszzeg);
        stmt.setInt(2, id);
        return stmt.executeUpdate();
    }
}
