package Models;

import Models.Order;
import Models.Product;
import Models.Review;
import Service.OrderService;
import Service.ProductService;
import Service.ReviewService;

import java.io.IOException;
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
    public void userActions() throws IOException {
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
                    ProductService.displayAvailableProducts();
                    break;
                case 5:
                    ProductService.sortProductsByPrice();
                    break;
                case 6:
                    OrderService.placeOrder(this);
                    break;
                case 7:
                    ReviewService.createReview();
                    break;
                case 8:
                    ReviewService.displayReviewsForProduct();
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
    public void displayPersonalInformation() {
        System.out.println("First name: " + firstName);
        System.out.println("Last name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("Phone number: " + phoneNumber);
        System.out.println();
    }
    public Map<Integer, Integer> getShoppingCart() {
        return shoppingCart;
    }

    @Override
    public int getUserId() {
        return super.getUserId();
    }
}