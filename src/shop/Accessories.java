package shop;

public class Accessories {
    private int id_accessory;
    private String name;
    private String accessory_code;
    private String maker;
    private int price;
    private int stock;
    private float number;

    public Accessories(){
        super();
    }
    public Accessories(int id_accessory,String name,String accessory_code,String maker, int price,int stock){
        this.id_accessory=id_accessory;
        this.name=name;
        this.accessory_code=accessory_code;
        this.maker=maker;
        this.price=price;
        this.stock=stock;
    }
    public Accessories(String name,String accessory_code,int number,int price){
        this.name=name;
        this.accessory_code=accessory_code;
        this.number=number;
        this.price=price;
    }
    public Accessories(String name,float number){
        this.name=name;
        this.number=number;
    }
    public Accessories(String maker,int number){
        this.maker=maker;
        this.number=number;
    }
    public int getId_accessory() {
        return id_accessory;
    }

    public void setId_accessory(int id_accessory) {
        this.id_accessory = id_accessory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getAccessory_code() {
        return accessory_code;
    }

    public void setAccessory_code(String accesory_code) {
        this.accessory_code = accesory_code;
    }


    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }
}
