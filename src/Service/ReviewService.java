package Service;

import Models.Review;
import Repository.ReviewRepository;

import java.io.IOException;
import java.util.Scanner;

public class ReviewService {

    private static Scanner scanner = new Scanner(System.in);

    public static void addReview(Review review) throws IOException {
        ReviewRepository.addReview(review);
        System.out.println("Review added successfully.");
        System.out.println();
        AuditService.getInstance().logAction("Review with ID " + review.getReviewId() + " added");
    }

    public static void displayReviewsForProduct() {
        System.out.print("Enter product id: ");
        int productId = scanner.nextInt();

        Review[] reviews = ReviewRepository.getReviewsByProductId(productId);
        if (reviews.length == 0) {
            System.out.println("No reviews found for product with ID " + productId);
            System.out.println();
            return;
        }

        System.out.println("Reviews for product with ID " + productId + ":");
        for (Review review : reviews) {
            System.out.println("Rating: " + review.getRating());
            System.out.println("Comment: " + review.getComment());
            System.out.println("-----------------------");
        }
    }

    public static void createReview() throws IOException {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter comment: ");
        String comment = scanner.nextLine();

        System.out.print("Enter rating (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine();

        Review review = new Review(userId, productId, comment, rating);

        addReview(review);
    }
}
