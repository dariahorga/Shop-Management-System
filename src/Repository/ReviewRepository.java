package Repository;

import Models.Review;
import config.DatabaseConfiguration;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {
    private static final Connection connection = DatabaseConfiguration.getDatabaseConnection();

    public static void createTable() {
        String createReviewTableSql = "CREATE TABLE IF NOT EXISTS Review (" +
                "reviewId INT AUTO_INCREMENT PRIMARY KEY," +
                "userId INT NOT NULL," +
                "productId INT NOT NULL," +
                "comment VARCHAR(255) NOT NULL," +
                "rating INT NOT NULL," +
                "reviewDate DATE NOT NULL" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createReviewTableSql);
            System.out.println("Review table created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addReview(Review review) {
        try {
            String insertReviewSql = "INSERT INTO Review (userId, productId, comment, rating, reviewDate) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(insertReviewSql);
            statement.setInt(1, review.getUserId());
            statement.setInt(2, review.getProductId());
            statement.setString(3, review.getComment());
            statement.setInt(4, review.getRating());
            statement.setDate(5, java.sql.Date.valueOf(review.getReviewDate()));

            statement.executeUpdate();
            System.out.println("Review added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Review[] getReviewsByProductId(int productId) {
        List<Review> reviews = new ArrayList<>();

        try {
            String selectSql = "SELECT * FROM Review WHERE productId = ?";
            PreparedStatement statement = connection.prepareStatement(selectSql);
            statement.setInt(1, productId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int reviewId = resultSet.getInt("reviewId");
                int userId = resultSet.getInt("userId");
                String comment = resultSet.getString("comment");
                int rating = resultSet.getInt("rating");
                LocalDate reviewDate = resultSet.getDate("reviewDate").toLocalDate();

                reviews.add(new Review(userId, productId, comment, rating));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews.toArray(new Review[0]);
    }
}
