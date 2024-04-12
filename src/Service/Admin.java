package Service;

import Models.*;

import java.util.*;

public class Admin extends User {

    private static  List<User> users = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static  List<Shipment> shipments = new ArrayList<>();
    private static Review[] reviews = new Review[40];
    private static Scanner scanner = new Scanner(System.in);

    public Admin( String firstName, String lastName, String email, String address, long phoneNumber) {
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
                    createUser();
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    addProduct();
                    break;
                case 5:
                    displayAvailableProducts();
                    break;
                case 6:
                    deleteProduct();
                    break;
                case 7:
                    displayOrders();
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

   //creare user de la tastatura, adaugare in lista, stergere un user, afisare useri
    public void createUser() {
        System.out.println("Adding a new customer...");
        System.out.println();

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter phone number: ");
        long phoneNumber = scanner.nextLong();

        User newUser = new Customer(firstName, lastName, email, address, phoneNumber);

        users.add(newUser);
        System.out.println("Adding a new user...");
        System.out.println();
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public void viewAllUsers() {
        System.out.println("All Users:");
        for (User user : users) {
            System.out.println(user.getUserId() + ": " + user.getFirstName() + " " + user.getLastName());
        }
        if (users.isEmpty()) {
            System.out.println("No users found.");
        }
        System.out.println();
    }
    public void deleteUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Deleting a user...");
        System.out.print("Enter user ID to delete: ");
        int userIdToDelete = scanner.nextInt();

        User userToDelete = null;
        for (User user : users) {
            if (user.getUserId() == userIdToDelete) {
                userToDelete = user;
                break;
            }
        }

        if (userToDelete != null) {
            users.remove(userToDelete);
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User with ID " + userIdToDelete + " not found.");
        }
        System.out.println();
    }

    //adaugare de produse de la tastatura, stergere produs, afisare produse pe categorii
    //afisare produse dupa pret
    public static void addProduct() {
        System.out.println("Adding a new product...");

        System.out.print("Enter product type (footwear, accessories, clothing): ");
        String type = scanner.nextLine();

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter product price: ");
        int price = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter product description: ");
        String description = scanner.nextLine();

        Product newProduct;
        switch (type.toLowerCase()) {
            case "footwear":
                System.out.print("Enter size number: ");
                int size = scanner.nextInt();
                scanner.nextLine();
                newProduct = new Footwear(name, price, description,size);
                break;
            case "accessories":
                System.out.print("Enter accessories type: ");
                String accessories_type = scanner.nextLine();
                newProduct = new Accessories(name, price, description,accessories_type);
                break;
            case "clothing":
                System.out.print("Enter material: ");
                String material= scanner.nextLine();

                System.out.print("Enter size number: ");
                int size_ = scanner.nextInt();
                scanner.nextLine();
                newProduct = new Clothing(name, price, description, material, size_);
                break;
            default:
                System.out.println();
                System.out.println("Invalid product type. Product not added.");
                System.out.println();
                return;
        }

        System.out.print("Enter number of products: ");
        int numberOfProducts = scanner.nextInt();
        scanner.nextLine();

        Inventory.addProductInventory(newProduct, numberOfProducts);

        System.out.println();
        System.out.println("Product added successfully.");
        System.out.println();
    }

    public static void deleteProduct()
    {
        System.out.println("Enter product id:");
        int productId = scanner.nextInt();
        scanner.nextLine();

        Product productToDelete = null;
        for (Product product : Inventory.getAllProducts()) {
            if (product.getProductId() == productId) {
                productToDelete = product;
                break;
            }
        }
        if (productToDelete != null) {
            Inventory.deleteProductInventory(productToDelete);
            System.out.println("Product deleted successfully.");
            System.out.println();
        } else {
            System.out.println("Product with ID " + productId + " not found.");
            System.out.println();
        }
    }

    public static Product[][]categorisedProducts() {
        Product[][] categorizedProducts = new Product[3][];

        Product[] products = Inventory.getAllProducts();

        int clothingCount = 0;
        int footwearCount = 0;
        int accessoriesCount = 0;
        for (Product product : products) {
            if (product instanceof Clothing) {
                clothingCount++;
            } else if (product instanceof Footwear) {
                footwearCount++;
            } else if (product instanceof Accessories) {
                accessoriesCount++;
            }
        }

        categorizedProducts[0] = new Product[clothingCount];
        categorizedProducts[1] = new Product[footwearCount];
        categorizedProducts[2] = new Product[accessoriesCount];

        int clothingIndex = 0;
        int footwearIndex = 0;
        int accessoriesIndex = 0;

        for (Product product : products) {
            if (product instanceof Clothing) {
                categorizedProducts[0][clothingIndex++] = product;
            } else if (product instanceof Footwear) {
                categorizedProducts[1][footwearIndex++] = product;
            } else if (product instanceof Accessories) {
                categorizedProducts[2][accessoriesIndex++] = product;
            }
        }

        return categorizedProducts;
    }
    public static void displayAvailableProducts() {
        Product[][] categorizedProducts = categorisedProducts();

        System.out.println("Clothing:");
        if (categorizedProducts[0].length == 0)
            System.out.println("No products available");
        System.out.println();
        for (Product product : categorizedProducts[0]) {
            if (product != null) {
                System.out.println("ID: " + product.getProductId());
                System.out.println("Name: " + product.getName());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Description: " + product.getDescription());
                Clothing clothingProduct = (Clothing) product;
                System.out.println("Size: " + clothingProduct.getSize());
                System.out.println("-----------------------");
                System.out.println();
            }
        }

        System.out.println("Footwear:");
        if (categorizedProducts[1].length == 0)
            System.out.println("No products available");
        System.out.println();
        for (Product product : categorizedProducts[1]) {
            if (product != null) {
                System.out.println("ID: " + product.getProductId());
                System.out.println("Name: " + product.getName());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Description: " + product.getDescription());
                Footwear footwearProduct = (Footwear) product;
                System.out.println("Size: " + footwearProduct.getSize());
                System.out.println("-----------------------");
                System.out.println();
            }
        }

        System.out.println("Accessories:");
        if (categorizedProducts[2].length == 0)
            System.out.println("No products available");
        System.out.println();
        for (Product product : categorizedProducts[2]) {
            if (product != null) {
                System.out.println("ID: " + product.getProductId());
                System.out.println("Name: " + product.getName());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Description: " + product.getDescription());
                System.out.println("-----------------------");
            }
        }
        System.out.println();
    }

    public static void sortProductsByPrice() {
        Product[] products = Inventory.getAllProducts();

        Arrays.sort(products);

        System.out.println("Products sorted by price:");
        String previousName = null;
        for (Product product : products)
        {
            if (!product.getName().equals(previousName))
            {System.out.println("ID: " + product.getProductId());
            System.out.println("Name: " + product.getName());
            System.out.println("Price: " + product.getPrice());
            System.out.println("-----------------------");
            System.out.println();
            previousName = product.getName();
            }
        }
    }

    public static void addOrder(Order order){
        orders.add(order);
    }
    public static void displayOrders(){
        System.out.println("Orders:");
        for (Order order : orders) {
            System.out.print(order.getOrderId() +":  ");
            System.out.println(order.getTotalPrice() + " RON");
            System.out.println("Customer: " + order.getCustomer());
            System.out.println();
        }
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        }
        System.out.println();
    }

    public static void createShipment() {

        System.out.print("Enter the order ID to create a shipment: ");
        int orderId = scanner.nextInt();

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

        orderToShip.setShipped(true);
        System.out.println();
    }


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

    public void addDiscountForProducts() {

        System.out.print("Enter discount percentage for products over 250 lei: ");
        System.out.println();
        int discountPercentage = scanner.nextInt();
        scanner.nextLine();

        for (Product product : Inventory.getAllProducts()) {
            if (product.getPrice() > 250) {
                int discountAmount = (int) ((discountPercentage / 100) * product.getPrice());
                int discountedPrice = product.getPrice() - discountAmount;

                product.setPrice(discountedPrice);
            }
        }
        System.out.println("Discount applied!");
    }

}
