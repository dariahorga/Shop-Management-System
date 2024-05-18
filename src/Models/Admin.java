package Models;

import Service.OrderService;
import Service.ProductService;
import Service.UserService;

import java.util.*;

public class Admin extends User {

    private static List<Order> orders = new ArrayList<>();
    private List<Shipment> shipments = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public Admin(String firstName, String lastName, String email, String address, long phoneNumber) {
        super(firstName, lastName, email, address, phoneNumber);
    }

    @Override
    public void userActions() {
        System.out.println("Welcome, Admin!");
        System.out.println();
        int choice;
        do {
            System.out.println("1. Add user");
            System.out.println("2. View all users");
            System.out.println("3. Delete user");
            System.out.println("4. Add a new product");
            System.out.println("5. View all products");
            System.out.println("6. Delete a product");
            System.out.println("7. Display orders");
            System.out.println("8. Create shipment of an order");
            System.out.println("9. Add a promotion to products over 250");
            System.out.println("0. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    UserService.createUser();
                    break;
                case 2:
                    UserService.viewAllUsers();
                    break;
                case 3:
                    UserService.deleteUser();
                    break;
                case 4:
                    ProductService.addProduct();
                    break;
                case 5:
                    ProductService.displayAvailableProducts();
                    break;
                case 6:
                    ProductService.deleteProduct();
                    break;
                case 7:
                    OrderService.displayOrders();
                    break;
                case 8:
                    createShipment();
                    break;
                case 9:
                    addDiscountForProducts();
                    break;
                case 0:
                    System.out.println("Exiting user actions menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public void createShipment() {
        System.out.print("Enter the order ID to create a shipment: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();

        Order orderToShip = null;
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                orderToShip = order;
                break;
            }
        }

        if (orderToShip != null) {
            Shipment shipment = new Shipment(orderToShip);
            shipments.add(shipment);
            orderToShip.setShipped(true);
            System.out.println("Shipment created successfully for order ID: " + orderId);
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
        System.out.println();
    }

    public void addDiscountForProducts() {
        System.out.print("Enter discount percentage for products over 250 lei: ");
        int discountPercentage = scanner.nextInt();
        scanner.nextLine();

        for (Product product : Inventory.getAllProducts()) {
            if (product.getPrice() > 250) {
                double discountAmount = (discountPercentage / 100.0) * product.getPrice();
                int discountedPrice = (int) (product.getPrice() - discountAmount);
                product.setPrice(discountedPrice);
            }
        }
        System.out.println("Discount applied!");
    }
}
