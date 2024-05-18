package Service;

import Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OrderService {

    private static List<Order> orders = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static void addOrder(Order order) {
        orders.add(order);
    }

    public static void displayOrders() {
        System.out.println("Orders:");
        for (Order order : orders) {
            System.out.print(order.getOrderId() + ":  ");
            System.out.println(order.getTotalPrice() + " RON");
            System.out.println("Customer: " + order.getCustomer());
            System.out.println();
        }
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        }
        System.out.println();
    }

    public static void placeOrder(Customer customer) {
        if (customer.getShoppingCart().isEmpty()) {
            System.out.println("Your shopping cart is empty. Cannot place an order.");
            System.out.println();
            return;
        }
        int totalCost = calculateTotalCost(customer.getShoppingCart());

        Shipment.Customer shipmentCustomer = new Shipment.Customer(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber()
        );

        Order order = new Order(shipmentCustomer, customer.getShoppingCart(), totalCost);

        for (Map.Entry<Integer, Integer> entry : customer.getShoppingCart().entrySet()) {
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
        customer.emptyShoppingCart();

        addOrder(order);
    }

    private static int calculateTotalCost(Map<Integer, Integer> shoppingCart) {
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
}
