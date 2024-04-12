package Models;

public class Accessories extends Product {

    private String type;
    public Accessories(){

    }
    public Accessories(String name, int price, String description, String type){
        super(name,price,description);
        this.type=type;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
