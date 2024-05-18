package Databases;

import config.DatabaseConfiguration;
import Models.User;
import Models.Admin;
import Models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {

    private static UserDatabase instance;

    private UserDatabase() {
    }

    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public void createUser(User user) {
        String query = "INSERT INTO Users (firstName, lastName, email, address, phoneNumber, userType) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getAddress());
            statement.setLong(5, user.getPhoneNumber());

            if (user instanceof Admin) {
                statement.setString(6, "Admin");
            } else if (user instanceof Customer) {
                statement.setString(6, "Customer");
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String userType = resultSet.getString("userType");
                User user = null;
                if ("Admin".equals(userType)) {
                    user = new Admin(
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("email"),
                            resultSet.getString("address"),
                            resultSet.getLong("phoneNumber")
                    );
                } else if ("Customer".equals(userType)) {
                    user = new Customer(
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName"),
                            resultSet.getString("email"),
                            resultSet.getString("address"),
                            resultSet.getLong("phoneNumber")
                    );
                }
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUser(User user) {
        String query = "UPDATE Users SET firstName = ?, lastName = ?, address = ?, phoneNumber = ? WHERE email = ?";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getAddress());
            statement.setLong(4, user.getPhoneNumber());
            statement.setString(5, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String email) {
        String query = "DELETE FROM Users WHERE email = ?";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
