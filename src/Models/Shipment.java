package Models;

import java.util.*;

public class Shipment {
    private int shipmentId;
    private Order order;
    private Date shippingDate;
    private int shipmentIndex;

    public Shipment( Order order) {
        shipmentIndex=shipmentIndex+1;
        this.shipmentId = shipmentIndex;
        this.order = order;
        this.shippingDate = null;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public Order getOrder() {
        return order;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public static class Admin extends User {

        private static List<User> users = new ArrayList<>();
        private static List<Order> orders = new ArrayList<>();
        private static  List<Shipment> shipments = new ArrayList<>();
        private static Review[] reviews = new Review[40];
        static Scanner scanner = new Scanner(System.in);

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

    public static class Customer extends User {
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

    public static class Inventory {

        private static final Map<Product, Integer> productsInventory =new HashMap<>();;
        public static Map<Product, Integer> getProductsInventory() {
            return productsInventory;
        }

        public static void addProductInventory(Product product, int quantity) {
            productsInventory.put(product, quantity);
        }

        public static void deleteProductInventory(Product product) {
            productsInventory.remove(product);
        }

        //metoda folosita la sortarea produselor; returnam produslele sub forma unui array
        public static Product[] getAllProducts() {
            Product[] productsArray = new Product[productsInventory.size()];
            int index = 0;
            for (Product product : productsInventory.keySet()) {
                productsArray[index++] = product;
            }
            return productsArray;
        }
        public static void decrementProductQuantity(Product product, int quantityToDecrement) {
            if (productsInventory.containsKey(product)) {
                int currentQuantity = productsInventory.get(product);
                if (currentQuantity >= quantityToDecrement) {
                    int newQuantity = currentQuantity - quantityToDecrement;
                    productsInventory.put(product, newQuantity);
                } else {
                    System.out.println("Insufficient quantity to decrement.");
                }
            } else {
                System.out.println("Product not found in inventory.");
            }
        }

        public static Product getProductById(int productId) {
            for (Product product : productsInventory.keySet()) {
                if (product.getProductId() == productId) {
                    return product;
                }
            }
            return null;
        }

        public static int getQuantity(Product product) {
                return productsInventory.get(product);
        }
    }

    public abstract static class User {
        protected int userId;
        protected String firstName;
        protected String lastName;
        protected String email;
        protected String address;
        protected long phoneNumber;
        private static int userIndex;

        public User(){

        }
        public User(String firstName, String lastName, String email,String address, long phoneNumber){
            userIndex=userIndex+1;
            this.userId=userIndex;
            this.firstName=firstName;
            this.lastName=lastName;
            this.address=address;
            this.email=email;
            this.phoneNumber=phoneNumber;
        }

        public int getUserId(){
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address)
        {
            this.address=address;
        }

        public long getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(long phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public abstract void userActions();

    }
}
