package Databases;

import config.DatabaseConfiguration;
import Models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {

    private static ProductDatabase instance;

    private ProductDatabase() {
    }

    public static ProductDatabase getInstance() {
        if (instance == null) {
            instance = new ProductDatabase();
        }
        return instance;
    }

    public void createProduct(Product product) {
        String query = "INSERT INTO Products (name, price, description) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        String query = "SELECT * FROM Products";
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("description")
                );
                product.setProductId(resultSet.getInt("productId"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateProduct(Product product) {
        String query = "UPDATE Products SET name = ?, price = ?, description = ? WHERE productId = ?";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getProductId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        String query = "DELETE FROM Products WHERE productId = ?";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
