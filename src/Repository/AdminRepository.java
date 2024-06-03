package Repository;

import config.DatabaseConfiguration;
import Models.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AdminRepository {
    private static final Connection connection = DatabaseConfiguration.getDatabaseConnection();

    public static void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS Admin (" +
                "adminId INT AUTO_INCREMENT PRIMARY KEY," +
                "userId INT NOT NULL," +
                "firstName VARCHAR(255) NOT NULL," +
                "lastName VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) UNIQUE NOT NULL," +
                "address VARCHAR(255) NOT NULL," +
                "phoneNumber BIGINT NOT NULL" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
            System.out.println("Admins table created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAdmin(Admin admin) {
        try {
            String query = "INSERT INTO Admin (userId, firstName, lastName, email, address, phoneNumber) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, admin.getUserId());
            statement.setString(2, admin.getFirstName());
            statement.setString(3, admin.getLastName());
            statement.setString(4, admin.getEmail());
            statement.setString(5, admin.getAddress());
            statement.setLong(6, admin.getPhoneNumber());

            statement.executeUpdate();
            System.out.println("Admin added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Admin getAdminById(int adminId) {
        String selectSql = "SELECT * FROM Admin WHERE adminId = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(selectSql)) {
            stmt.setInt(1, adminId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                long phoneNumber = resultSet.getLong("phoneNumber");

                return new Admin(firstName, lastName, email, address, phoneNumber);
            } else {
                System.out.println("Admin with ID " + adminId + " not found.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteUser() {
        System.out.println("Deleting a user...");
        System.out.print("Enter user ID to delete: ");
        Scanner scanner = null;
        int userIdToDelete = scanner.nextInt();
        scanner.nextLine();

        UserRepository.deleteUser(userIdToDelete);
        System.out.println();
    }
}
