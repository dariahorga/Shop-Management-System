package Repository;

import config.DatabaseConfiguration;
import Models.Customer;

import java.sql.*;
import java.util.Scanner;

import static Service.UserService.scanner;

public class CustomerRepository {
    private static final Connection connection = DatabaseConfiguration.getDatabaseConnection();

    public static void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS Customer (" +
                "customerId INT AUTO_INCREMENT PRIMARY KEY," +
                "userId INT NOT NULL," +
                "firstName VARCHAR(255) NOT NULL," +
                "lastName VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) UNIQUE NOT NULL," +
                "address VARCHAR(255) NOT NULL," +
                "phoneNumber BIGINT NOT NULL" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
            System.out.println("Customers table created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCustomer(Customer customer) {
        try {
            String query = "INSERT INTO Customer (userId, firstName, lastName, email, address, phoneNumber) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customer.getUserId());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getLastName());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getAddress());
            statement.setLong(6, customer.getPhoneNumber());

            statement.executeUpdate();
            System.out.println("Customer added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Customer getCustomerById(int customerId) {
        String selectSql = "SELECT * FROM Customer WHERE customerId = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(selectSql)) {
            stmt.setInt(1, customerId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                long phoneNumber = resultSet.getLong("phoneNumber");

                return new Customer(firstName, lastName, email, address, phoneNumber);
            } else {
                System.out.println("Customer with ID " + customerId + " not found.");
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
