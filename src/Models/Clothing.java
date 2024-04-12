package Models;

public class Clothing extends Product {
    private String material;
    private int size;

    public Clothing() {
    }

    public Clothing(String name, int price, String description, String material, int size) {
        super(name, price, description);
        this.material = material;
        this.size = size;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
