import Models.Accessories;
import Models.Clothing;
import Models.Footwear;
import Models.Order;
import Service.Inventory;
import Service.Customer;
import Service.Admin;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
            Admin admin = new Admin( "Admin", "1", "admin@yahoo.com", "Admin Address", 1234567890);
            Customer customer = new Customer( "Daria", "Horga", "dariahorga@yahoo.com", "123 Street", 765334);

            Admin.addUser(admin);
            Admin.addUser(customer);

            Clothing product1 = new Clothing("Rochie midi decupata", 150, "Rochie midi confectionata cu tesatura din amestec de viscoza, cu guler rotund, fara maneca, cu decupaje laterale cu funde","bumbac", 36);
            Clothing product2 = new Clothing("Rochie midi decupata", 150, "Rochie midi confectionata cu tesatura din amestec de viscoza, cu guler rotund, fara maneca, cu decupaje laterale cu funde","bumbac", 38);
            Clothing product3 = new Clothing("Rochie midi decupata", 150, "Rochie midi confectionata cu tesatura din amestec de viscoza, cu guler rotund, fara maneca, cu decupaje laterale cu funde","bumbac", 40);
            Clothing product4 = new Clothing( "Fusta-pantalon cu cute",70, "Fusta-pantalon cu talie inalta. Fermoar ascuns in cusatura.","bumbac", 36);
            Clothing product5 = new Clothing( "Camasa satinata",100, "Camasa cu guler si rever. Decolteu  in V. Maneca lunga cu manseta.","bumbac",38);
            Footwear product6 = new Footwear("Botine joase", 290, "Pantofi tip ghete plate din piele.",36);
            Footwear product7 = new Footwear("Botine joase", 290, "Pantofi tip ghete plate din piele.",37);
            Footwear product8 = new Footwear("Botine joase", 290, "Pantofi tip ghete plate din piele.",39);
            Footwear product9 = new Footwear("Balerini din piele", 190, "Pantofi fara toc tip balerini Mary Jane din piele. Inchidere  in fata prin intermediul unei barete cu catarama",36);
            Accessories product10 = new Accessories("Fular cu franjuri",60,"Fular confectionat din lana 100% cu franjuri la capete", "fulare si esarfe");

            Inventory.addProductInventory(product1,5);
            Inventory.addProductInventory(product2,2);
            Inventory.addProductInventory(product3,4);
            Inventory.addProductInventory(product4,2);
            Inventory.addProductInventory(product5,1);
            Inventory.addProductInventory(product6,9);
            Inventory.addProductInventory(product7,4);
            Inventory.addProductInventory(product8,5);
            Inventory.addProductInventory(product9,6);
            Inventory.addProductInventory(product10,10);

            Map<Integer, Integer> products = new HashMap<>();
            products.put(6, 1);

            Order order = new Order(customer,products, 290);
            Admin.addOrder(order);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome!");
            System.out.println("Who are you? Please enter 'admin' or 'customer':");
            String userType = scanner.nextLine();

            if (userType.equalsIgnoreCase("admin")) {
                admin.userActions();
            } else if (userType.equalsIgnoreCase("customer")) {
                customer.userActions();
            } else {
                System.out.println("Invalid user type.");
            }

        }
}