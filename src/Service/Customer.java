package Service;

import Models.Order;
import Models.Product;
import Models.Review;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Customer extends User {
    private Map<Integer, Integer> shoppingCart;
    Scanner scanner = new Scanner(System.in);

    public Customer(String firstName, String lastName, String email, String address, long phoneNumber) {
        super(firstName, lastName, email, address, phoneNumber);
        shoppingCart = new HashMap<>();
    }

    @Override
    public void userActions() {
        System.out.println("Welcome, " + firstName + " " + lastName + "!");
        System.out.println();
        int choice;
        do {
            System.out.println("1. View shopping cart");
            System.out.println("2. Add product to cart");
            System.out.println("3. Empty shopping cart");
            System.out.println("4. View all products");
            System.out.println("5. View products sorted by price");
            System.out.println("6. Place order");
            System.out.println("7. Leave a comment and a review to a product");
            System.out.println("8. Display comments and reviews of a product");
            System.out.println("9. Display personal information");
            System.out.println("0. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    viewShoppingCart();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    emptyShoppingCart();
                    break;
                case 4:
                    Admin.displayAvailableProducts();
                    break;
                case 5:
                    Admin.sortProductsByPrice();
                    break;
                case 6:
                    placeOrder();
                    break;
                case 7:
                    createReview();
                    break;
                case 8:
                    displayReviewsForProduct();
                    break;
                case 9:
                    displayPersonalInformation();
                    break;
                case 0:
                    System.out.println("Exiting user actions menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    //in shoppingcart tinem id-ul produsului si cantitatea acestuia
    public void viewShoppingCart() {
        System.out.println("Shopping Cart:");
        for (Map.Entry<Integer, Integer> entry : shoppingCart.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = Inventory.getProductById(productId);
            if (product != null) {
                System.out.println(product.getName() + " (ID: " + productId + ") - Quantity: " + quantity);
            } else {
                System.out.println("Product with ID " + productId + " not found.");
            }
        }
        System.out.println();
    }

    public void emptyShoppingCart() {
        shoppingCart.clear();
        System.out.println("Shopping cart emptied.");
        System.out.println();
    }

    public void addProduct() {
        Product[] availableProducts = Inventory.getAllProducts();

        System.out.print("Enter the ID of the product you want to add to your cart: ");
        int productId = scanner.nextInt();

            Product productToAdd = null;
            for (Product product : availableProducts) {
                if (product.getProductId() == productId) {
                    productToAdd = product;
                    break;
                }
            }

            if (productToAdd != null) {
                System.out.print("Enter the quantity of the product you want to add: ");
                int quantity = scanner.nextInt();
                int availableQuantity = Inventory.getQuantity(productToAdd);

                if (quantity > 0 && availableQuantity >= quantity) {
                    System.out.println("Product added to cart!");
                    shoppingCart.put(productId, quantity);
                } else {
                    System.out.println("Invalid quantity or insufficient stock.");
                }
            } else {
                System.out.println("Product not found.");
                System.out.println();
            }
        System.out.println();
    }
    public int calculateTotalCost() {
        int totalCost = 0;
        for (Map.Entry<Integer, Integer> entry : shoppingCart.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = Inventory.getProductById(productId);
            if (product != null) {
                totalCost += product.getPrice() * quantity;
            }
        }
        return totalCost;
    }
    private void placeOrder() {
        if (shoppingCart.isEmpty()) {
            System.out.println("Your shopping cart is empty. Cannot place an order.");
            System.out.println();
            return;
        }
        int totalCost = calculateTotalCost();
        Order order = new Order(this, shoppingCart,totalCost);

        //pentru a tine evidenta produselor atunci cand un produs este comandat trebuie sa updatam
        //inventarul
        for (Map.Entry<Integer, Integer> entry : shoppingCart.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = Inventory.getProductById(productId);
            if (product != null) {
                Inventory.decrementProductQuantity(product, quantity);
            }
        }

        System.out.println("Order placed successfully!");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Total Cost: " + totalCost);
        System.out.println();
        emptyShoppingCart();

        Admin.addOrder(order);
    }
    public void createReview() {
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

        Admin.addReview(review);

    }

    public static void displayReviewsForProduct() {
        boolean found = false;

        Review[] reviews = Admin.getAllReviews();
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
    public void displayPersonalInformation() {
        System.out.println("First name: " + firstName);
        System.out.println("Last name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("Phone number: " + phoneNumber);
        System.out.println();
    }
}
