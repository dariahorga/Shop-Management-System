package Repository;

import config.DatabaseConfiguration;
import Models.Product;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ProductRepository {
    private static final Connection connection = DatabaseConfiguration.getDatabaseConnection();

    public static void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS Product (" +
                "productId INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "price INT NOT NULL," +
                "description TEXT" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
            System.out.println("Product table created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Set<Product> getAllProducts() {
        String selectSql = "SELECT * FROM Product;";

        Set<Product> products = new HashSet<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(selectSql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                String description = resultSet.getString("description");

                Product product = new Product(name, price, description);
                products.add(product);
            }

            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addProduct(Product product) {
        try {
            String query = "INSERT INTO Product (name, price, description) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.executeUpdate();
            System.out.println("Product added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProduct(Product product) {
        try {
            String query = "UPDATE Product SET name = ?, price = ?, description = ? WHERE productID = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getProductId());
            statement.executeUpdate();
            System.out.println("Product updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(int productId) {
        try {
            String query = "DELETE FROM Product WHERE productID = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            statement.executeUpdate();
            System.out.println("Product deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

