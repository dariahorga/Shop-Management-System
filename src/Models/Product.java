package Models;

public class Product implements Comparable<Product>{
    private int productId;
    private String name;
    private int price;
    private String description;
    private static int productIndex;
    public Product(){

    }

    public int compareTo(Product otherProduct) {

        return Integer.compare(this.price, otherProduct.getPrice());
    }
    public Product(String name, int price, String description)
    {
        productIndex=productIndex+1;
        this.productId=productIndex;
        this.name=name;
        this.price=price;
        this.description=description;

    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice(){
        return price;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
