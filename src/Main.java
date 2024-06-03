import Models.*;
import Repository.*;
import Models.Admin;
import Models.Customer;
import Models.User;
import Service.OrderService;
import config.DatabaseConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static Models.Order.customer;

public class Main {

        public static void main(String[] args) throws IOException {
            DatabaseConfiguration.getDatabaseConnection();

            OrderRepository.createTable();
            CustomerRepository.createTable();
            AdminRepository.createTable();
            UserRepository.createTable();
            ReviewRepository.createTable();

            Admin admin1 = new Admin("John", "Doe", "john.doe@example.com", "123 Main St", 1234567890);
            Admin admin2 = new Admin("Alice", "Smith", "alice.smith@example.com", "456 Oak St", 234567);
            Customer customer1 = new Customer("Jane", "Doe", "jane.doe@example.com", "456 Elm St", 987654);
            Customer customer2 = new Customer("Bob", "Johnson", "bob.johnson@example.com", "789 Pine St", 87654);

            UserRepository.addUser(admin1);
            UserRepository.addUser(admin2);
            UserRepository.addUser(customer1);
            UserRepository.addUser(customer2);

            Clothing product1 = new Clothing("Rochie midi decupata", 150, "Rochie midi confectionata cu tesatura din amestec de viscoza, cu guler rotund, fara maneca, cu decupaje laterale cu funde", "bumbac", 36);
            Clothing product2 = new Clothing("Rochie midi decupata", 150, "Rochie midi confectionata cu tesatura din amestec de viscoza, cu guler rotund, fara maneca, cu decupaje laterale cu funde", "bumbac", 38);
            Clothing product3 = new Clothing("Rochie midi decupata", 150, "Rochie midi confectionata cu tesatura din amestec de viscoza, cu guler rotund, fara maneca, cu decupaje laterale cu funde", "bumbac", 40);
            Clothing product4 = new Clothing("Fusta-pantalon cu cute", 70, "Fusta-pantalon cu talie inalta. Fermoar ascuns in cusatura.", "bumbac", 36);
            Clothing product5 = new Clothing("Camasa satinata", 100, "Camasa cu guler si rever. Decolteu  in V. Maneca lunga cu manseta.", "bumbac", 38);
            Footwear product6 = new Footwear("Botine joase", 290, "Pantofi tip ghete plate din piele.", 36);
            Footwear product7 = new Footwear("Botine joase", 290, "Pantofi tip ghete plate din piele.", 37);
            Footwear product8 = new Footwear("Botine joase", 290, "Pantofi tip ghete plate din piele.", 39);
            Footwear product9 = new Footwear("Balerini din piele", 190, "Pantofi fara toc tip balerini Mary Jane din piele. Inchidere  in fata prin intermediul unei barete cu catarama", 36);
            Accessories product10 = new Accessories("Fular cu franjuri", 60, "Fular confectionat din lana 100% cu franjuri la capete", "fulare si esarfe");

            Inventory.addProductInventory(product1, 5);
            Inventory.addProductInventory(product2, 2);
            Inventory.addProductInventory(product3, 4);
            Inventory.addProductInventory(product4, 2);
            Inventory.addProductInventory(product5, 1);
            Inventory.addProductInventory(product6, 9);
            Inventory.addProductInventory(product7, 4);
            Inventory.addProductInventory(product8, 5);
            Inventory.addProductInventory(product9, 6);
            Inventory.addProductInventory(product10, 10);

            Map<Integer, Integer> products = new HashMap<>();
            products.put(6, 1);


            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome!");
            System.out.println("Who are you? Please enter 'admin' or 'customer':");
            String userType = scanner.nextLine();

            if (userType.equalsIgnoreCase("admin")) {
                admin1.userActions();
            } else if (userType.equalsIgnoreCase("customer")) {
                customer1.userActions();
            } else {
                System.out.println("Invalid user type.");
            }

        }
}