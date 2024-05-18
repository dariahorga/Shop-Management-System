package Service;

import Models.Review;

import java.util.Scanner;


public class ReviewService {

    private static Review[] reviews = new Review[40];
    private static Scanner scanner = new Scanner(System.in);
    public static void addReview(Review review) {
        for (int i = 0; i < reviews.length; i++) {
            if (reviews[i] == null) {
                reviews[i] = review;
                System.out.println("Review added successfully.");
                System.out.println();
                return;
            }
        }
        System.out.println();
    }

    public static Review[] getAllReviews() {
        return reviews;
    }

    public static void displayReviewsForProduct() {
        boolean found = false;

        Review[] reviews = getAllReviews();
        if (reviews.length == 0 || reviews == null) {
            System.out.println("No reviews found.");
            System.out.println();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product id: ");
        int productId = scanner.nextInt();

        for (Review review : reviews) {
            if (review != null && review.getProductId() == productId) {
                found = true;
                System.out.println("Reviews for product with ID " + productId + ":");
                System.out.println("Rating: " + review.getRating());
                System.out.println("Comment: " + review.getComment());
                System.out.println("-----------------------");
                System.out.println();
            }
        }

        if (!found) {
            System.out.println("No reviews found for product with ID " + productId);
            System.out.println();
        }
    }

    public static void createReview() {
        Scanner scanner = new Scanner(System.in);

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
