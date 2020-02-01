package com.ekarts.dao;

import com.ekarts.dto.Kart;
import com.ekarts.enums.EnumKartType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KartDao {

    /*
     * Llista tots els clients de la base de dades
     *
     */
    public List<Kart> listar() {
        String SQL_SELECT = "SELECT kar_id, kar_name, kar_type, kar_pricePerMinute " + " FROM karts";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Kart kart;
        List<Kart> karts = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("kar_id");
                String name = rs.getString("kar_name");
                EnumKartType type = EnumKartType.valueOf( rs.getString("kar_type"));
                Double pricePerMinute = rs.getDouble("kar_pricePerMinute");

                kart = new Kart(id, name, type, pricePerMinute);
                karts.add(kart);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            DBConnection.close(rs);
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
        return karts;
    }

    /*
     * Recupera un kart a la base de dades segons el seu ID
     *
     */
    public Kart findById(Kart kart) {
        String SQL_SELECT_BY_ID = "SELECT kar_name, kar_type, kar_pricePerMinute "
                + " FROM karts WHERE kar_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, kart.getId());
            rs = stmt.executeQuery();
            rs.absolute(1);// nos posicionamos en el primer registro devuelto

            String name = rs.getString("kar_name");
            EnumKartType type = EnumKartType.valueOf(rs.getString("kar_type"));
            double pricePerMinute = rs.getDouble("kar_pricePerMinute");

            kart.setName(name);
            kart.setKartTypeEnum(type);
            kart.setPricePerMinute(pricePerMinute);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            DBConnection.close(rs);
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
        return kart;
    }

    /*
     * Crea un kart a la base de dades
     *
     */
    public int create(Kart kart) {
        String SQL_INSERT = "INSERT INTO karts(kar_name, kar_type, kar_pricePerMinute) "
                + " VALUES(?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, kart.getName());
            stmt.setString(2, kart.getKartTypeEnum().toString());
            stmt.setDouble(3, kart.getPricePerMinute());
            System.out.println(kart.toString());
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
        return rows;
    }

    /*
     * Modifica un kart de la base de dades
     *
     */
    public int update(Kart kart) {
        String SQL_UPDATE = "UPDATE karts "
                + " SET kar_name=?, kar_type=?, kar_pricePerMinute=? WHERE kar_id=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            int i = 1;
            stmt.setString(i++, kart.getName());
            stmt.setString(i++, kart.getKartTypeEnum().toString());
            stmt.setDouble(i++, kart.getPricePerMinute());
            stmt.setInt(i++, kart.getId());

            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
        return rows;
    }

    /*
     * Esborra un kart de la base de dades
     *
     */
    public int delete(Kart kart) {
        String SQL_DELETE = "DELETE FROM karts WHERE kar_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, kart.getId());
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            DBConnection.close(stmt);
            DBConnection.close(conn);
        }
        return rows;
    }

}

