package Models;

public class Footwear extends Product {

    private int size;
    public Footwear() {
    }

    public Footwear(String name, int price, String description,int size) {

        super(name, price, description);
        this.size=size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
