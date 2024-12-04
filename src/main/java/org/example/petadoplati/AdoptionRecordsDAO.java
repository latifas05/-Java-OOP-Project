package org.example.petadoplati;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdoptionRecordsDAO {
    private static final String url = "jdbc:postgresql://localhost:5432/pet_adoption_system";
    private static final String user = "postgres";
    private static final String password = "12345";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static List<AdoptionRecord> getAllRecords() {
        List<AdoptionRecord> records = new ArrayList<>();
        String sql = "SELECT ar.id AS adoption_id, ar.adopter_id, ar.pet_id, " +
                "a.name AS adopter_name, a.contact AS adopter_contact, " +
                "p.name AS pet_name, p.type AS pet_type, ar.adoption_date " +
                "FROM adoption_records ar " +
                "JOIN adopters a ON ar.adopter_id = a.id " +
                "JOIN pets p ON ar.pet_id = p.id";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                records.add(new AdoptionRecord(
                        rs.getInt("adoption_id"),
                        rs.getInt("adopter_id"),
                        rs.getInt("pet_id"),
                        rs.getString("adopter_name"),
                        rs.getString("adopter_contact"),
                        rs.getString("pet_name"),
                        rs.getString("pet_type"),
                        rs.getDate("adoption_date").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void addAdoptionRecord(AdoptionRecord record) {
        if (!doesAdopterExist(record.getAdopterId())) {
            System.out.println("Adopter with ID " + record.getAdopterId() + " does not exist.");
            return;
        }

        String sql = "INSERT INTO adoption_records (adopter_id, pet_id, adopter_name, adopter_contact, pet_name, pet_type, adoption_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set all parameters
            pstmt.setInt(1, record.getAdopterId());
            pstmt.setInt(2, record.getPetId());
            pstmt.setString(3, record.getAdopterName());
            pstmt.setString(4, record.getAdopterContact());
            pstmt.setString(5, record.getPetName());
            pstmt.setString(6, record.getPetType());
            pstmt.setDate(7, Date.valueOf(record.getAdoptionDate()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean doesAdopterExist(int adopterId) {
        String sql = "SELECT 1 FROM adopters WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, adopterId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getPetName(int petId) {
        String sql = "SELECT name FROM pets WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, petId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPetType(int petId) {
        String sql = "SELECT type FROM pets WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, petId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
