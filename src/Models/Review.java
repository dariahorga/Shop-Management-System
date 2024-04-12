package Models;

import java.time.LocalDate;

public class Review {
    private int reviewId;
    private int userId;
    private int productId;
    private String comment;
    private int rating;
    private LocalDate reviewDate;
    private static int reviewIndex;

    public Review(int userId, int productId, String comment, int rating) {
        reviewIndex=reviewIndex+1;
        reviewId=reviewIndex;
        this.userId = userId;
        this.productId = productId;
        this.comment = comment;
        this.rating = rating;
        this.reviewDate = LocalDate.now();
    }

    public int getProductId() {
        return productId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

}
