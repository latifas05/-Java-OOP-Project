package org.example.petadoplati;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {
    private final String url = "jdbc:postgresql://localhost:5432/pet_adoption_system";
    private final String user = "postgres";
    private final String password = "12345";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<Pet> getAllPets(String typeFilter) {
        List<Pet> pets = new ArrayList<>();

        String sql = "SELECT * FROM pets";

        if (typeFilter != null && !typeFilter.equals("All")) {
            sql += " WHERE type = ?";
        }

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (typeFilter != null && !typeFilter.equals("All")) {
                stmt.setString(1, typeFilter);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pets.add(new Pet(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("breed"),
                            rs.getString("type"),
                            rs.getInt("age"),
                            rs.getString("description")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }


    public void addPet(Pet pet) {
        String sql = "INSERT INTO pets (name, breed, type, age, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pet.getName());
            pstmt.setString(2, pet.getBreed());
            pstmt.setString(3, pet.getType());
            pstmt.setInt(4, pet.getAge());
            pstmt.setString(5, pet.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePet(Pet pet) {
        String sql = "DELETE FROM pets WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pet.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}