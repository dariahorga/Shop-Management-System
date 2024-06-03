package Repository;

import config.DatabaseConfiguration;
import Models.Admin;
import Models.Customer;
import Models.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import static Repository.AdminRepository.addAdmin;
import static Repository.CustomerRepository.addCustomer;

public class UserRepository {
    private static final Connection connection = DatabaseConfiguration.getDatabaseConnection();
    public static void createTable() {
        String createUserTableSql = "CREATE TABLE IF NOT EXISTS User (" +
                "userId INT AUTO_INCREMENT PRIMARY KEY," +
                "firstName VARCHAR(255) NOT NULL," +
                "lastName VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) UNIQUE NOT NULL," +
                "address VARCHAR(255) NOT NULL," +
                "phoneNumber BIGINT NOT NULL" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUserTableSql);
            System.out.println("User tables created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User user) {
        try {
            String insertUserSql = "INSERT INTO User (firstName, lastName, email, address, phoneNumber) " +
                    "VALUES (?, ?, ?, ?, ?)";
            String insertAdminSql = "INSERT INTO Admin (userId, firstName, lastName, email, address, phoneNumber) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            String insertCustomerSql = "INSERT INTO Customer (userId, firstName, lastName, email, address, phoneNumber) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement userStatement = connection.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, user.getFirstName());
            userStatement.setString(2, user.getLastName());
            userStatement.setString(3, user.getEmail());
            userStatement.setString(4, user.getAddress());
            userStatement.setLong(5, user.getPhoneNumber());

            userStatement.executeUpdate();

            ResultSet generatedKeys = userStatement.getGeneratedKeys();
            int userId = 0;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            }

            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                PreparedStatement adminStatement = connection.prepareStatement(insertAdminSql);
                adminStatement.setInt(1, userId);
                adminStatement.setString(2, admin.getFirstName());
                adminStatement.setString(3, admin.getLastName());
                adminStatement.setString(4, admin.getEmail());
                adminStatement.setString(5, admin.getAddress());
                adminStatement.setLong(6, admin.getPhoneNumber());
                adminStatement.executeUpdate();
            } else if (user instanceof Customer) {
                Customer customer = (Customer) user;
                PreparedStatement customerStatement = connection.prepareStatement(insertCustomerSql);
                customerStatement.setInt(1, userId);
                customerStatement.setString(2, customer.getFirstName());
                customerStatement.setString(3, customer.getLastName());
                customerStatement.setString(4, customer.getEmail());
                customerStatement.setString(5, customer.getAddress());
                customerStatement.setLong(6, customer.getPhoneNumber());
                customerStatement.executeUpdate();
            }

            System.out.println("User added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Set<User> getAllUsers() {
        String selectSql = "SELECT * FROM User;";

        Set<User> users = new HashSet<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(selectSql);

            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                long phoneNumber = resultSet.getLong("phoneNumber");
                String userType = resultSet.getString("userType");

                User user = null;
                if (userType.equals("Admin")) {
                    user = new Admin(firstName, lastName, email, address, phoneNumber);
                } else if (userType.equals("Customer")) {
                    user = new Customer(firstName, lastName, email, address, phoneNumber);
                }
                if (user != null) {
                    users.add(user);
                }
            }

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void displayAllUsers() {
        Set<User> users = getAllUsers();

        if (users != null && !users.isEmpty()) {
            System.out.println("All Users:");
            for (User user : users) {
                System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
                System.out.println("Email: " + user.getEmail());
                System.out.println();
            }
        } else {
            System.out.println("No users found.");
        }
    }


    public static void updateUser(User user) {
        try {
            String query = "UPDATE User SET firstName = ?, lastName = ?, address = ?, phoneNumber = ? WHERE email = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getAddress());
            statement.setLong(4, user.getPhoneNumber());
            statement.setString(5, user.getEmail());

            statement.executeUpdate();
            System.out.println("User updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(int userId) {
        try {
            String deleteUserSql = "DELETE FROM User WHERE userId = ?";
            String deleteAdminSql = "DELETE FROM Admin WHERE userId = ?";
            String deleteCustomerSql = "DELETE FROM Customer WHERE userId = ?";

            PreparedStatement userStatement = connection.prepareStatement(deleteUserSql);
            userStatement.setInt(1, userId);
            userStatement.executeUpdate();

            // În funcție de tipul utilizatorului, ștergem din tabelul corespunzător
            // Admin sau Customer
            PreparedStatement adminStatement = connection.prepareStatement(deleteAdminSql);
            adminStatement.setInt(1, userId);
            adminStatement.executeUpdate();

            PreparedStatement customerStatement = connection.prepareStatement(deleteCustomerSql);
            customerStatement.setInt(1, userId);
            customerStatement.executeUpdate();

            System.out.println("User with ID " + userId + " deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

