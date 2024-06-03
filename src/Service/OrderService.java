package Service;

import Models.Customer;
import Models.Inventory;
import Models.Order;
import Models.Product;
import Repository.OrderRepository;

import java.io.IOException;
import java.util.Map;

public class OrderService {

    // Metoda pentru adăugarea unei comenzi
    public static void addOrder(Order order) {
        AuditService.logAction("Order added: " + order.getOrderId());
        OrderRepository.addOrder(order);
    }

    // Metoda pentru afișarea comenzilor
    public static void displayOrders() {
        OrderRepository.getAllOrders().forEach(order -> {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Customer: " + order.getCustomer());
            System.out.println("Total Price: " + order.getTotalPrice());
            System.out.println();
        });
    }

    // Metoda pentru plasarea unei comenzi
    public static void placeOrder(Customer customer) {
        if (customer.getShoppingCart().isEmpty()) {
            System.out.println("Your shopping cart is empty. Cannot place an order.");
            System.out.println();
            return;
        }
        int totalCost = calculateTotalCost(customer.getShoppingCart());

        Order order = new Order(customer, customer.getShoppingCart(), totalCost);

        for (Map.Entry<Integer, Integer> entry : customer.getShoppingCart().entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = Inventory.getProductById(productId);
            if (product != null) {
                Inventory.decrementProductQuantity(product, quantity);
            }
                AuditService.logAction("Order placed : " + order.getOrderId());
        }

        OrderRepository.addOrder(order);

        System.out.println("Order placed successfully!");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Total Cost: " + totalCost);
        System.out.println();

        customer.emptyShoppingCart();
    }

    // Metoda pentru calcularea costului total al comenzii
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
