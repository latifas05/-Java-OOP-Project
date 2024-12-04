package org.example.petadoplati;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdoptersDAO {
    private static final String url = "jdbc:postgresql://localhost:5432/pet_adoption_system";
    private static final String user = "postgres";
    private static final String password = "12345";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<Adopter> getAllAdopters() {
        List<Adopter> adopters = new ArrayList<>();
        String sql = "SELECT * FROM adopters";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                adopters.add(new Adopter(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("contact")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adopters;
    }

    public int addAdopter(Adopter adopter) {
        String sql = "INSERT INTO adopters (name, contact) VALUES (?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, adopter.getName());
            pstmt.setString(2, adopter.getContact());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }



    public static List<Integer> getAllAdopterIds() {
        List<Integer> adopterIds = new ArrayList<>();
        String sql = "SELECT id FROM adopters";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                adopterIds.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adopterIds;
    }



}
