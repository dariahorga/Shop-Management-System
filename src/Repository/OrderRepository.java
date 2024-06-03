package Repository;

import Models.Customer;
import config.DatabaseConfiguration;
import Models.Order;
import Models.Shipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrderRepository {
    private static final Connection connection = DatabaseConfiguration.getDatabaseConnection();

    public static void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS Orders (" +
                "orderId INT AUTO_INCREMENT PRIMARY KEY," +
                "customerId INT," +
                "isShipped BOOLEAN," +
                "totalPrice INT" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
            System.out.println("Orders table created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Set<Order> getAllOrders() {
        String selectSql = "SELECT * FROM Orders;";

        Set<Order> orders = new HashSet<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(selectSql);

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customerId");
                boolean isShipped = resultSet.getBoolean("isShipped");
                int totalPrice = resultSet.getInt("totalPrice");

                Customer customer = CustomerRepository.getCustomerById(customerId);

                Order order = new Order(customer, null, totalPrice);
                order.setShipped(isShipped);
                orders.add(order);
            }

            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addOrder(Order order) {
        try {
            String query = "INSERT INTO Orders (customerId, isShipped, totalPrice) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, order.getCustomerId());
            statement.setBoolean(2, order.isShipped());
            statement.setInt(3, order.getTotalPrice());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                System.out.println("Order added successfully with ID: " + orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOrder(int orderId) {
        try {
            String query = "DELETE FROM Orders WHERE orderId = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            statement.executeUpdate();
            System.out.println("Order deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
