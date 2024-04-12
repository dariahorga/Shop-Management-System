package Service;

import Models.Product;

import java.util.*;

public class Inventory {

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
