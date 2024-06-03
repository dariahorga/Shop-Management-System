package Service;

import Models.*;
import Service.AuditService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ProductService {
    private static final Scanner scanner = new Scanner(System.in);

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
                newProduct = new Footwear(name, price, description, size);
                break;
            case "accessories":
                System.out.print("Enter accessories type: ");
                String accessoriesType = scanner.nextLine();
                newProduct = new Accessories(name, price, description, accessoriesType);
                break;
            case "clothing":
                System.out.print("Enter material: ");
                String material = scanner.nextLine();

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

        try {
            AuditService.getInstance().logAction("Product added: " + newProduct.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("Product added successfully.");
        System.out.println();
    }

    public static void deleteProduct() {
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
        try {
            AuditService.getInstance().logAction("Product deleted: " + productToDelete.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Product[][] categorisedProducts() {
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
        for (Product product : products) {
            if (!product.getName().equals(previousName)) {
                System.out.println("ID: " + product.getProductId());
                System.out.println("Name: " + product.getName());
                System.out.println("Price: " + product.getPrice());
                System.out.println("-----------------------");
                System.out.println();
                previousName = product.getName();
            }
        }
    }
}
