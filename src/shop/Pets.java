package shop;

public class Pets {
    private int id_pet;
    private String name;
    private String pet_code;
    private String breed;
    private String breed_name;
    private String sex;
    private int age;
    private int life;
    private int price;
    private int stock;
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId_pet() {
        return id_pet;
    }

    public void setId_pet(int id_pet) {
        this.id_pet = id_pet;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPet_code() {
        return pet_code;
    }

    public void setPet_code(String pet_code) {
        this.pet_code = pet_code;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getBreed_name() {
        return breed_name;
    }

    public void setBreed_name(String breed_name) {
        this.breed_name = breed_name;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
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

    public Pets() {
        super();
    }
    public Pets(int id_pet,String name,String pet_code,String breed,String breed_name,String sex, int age,int life,int price,int stock){
        super();
        this.id_pet=id_pet;
        this.name=name;
        this.pet_code=pet_code;
        this.breed=breed;
        this.breed_name =breed_name;
        this.sex=sex;
        this.age=age;
        this.life=life;
        this.price=price;
        this.stock=stock;
    }
    public Pets(String name,String breed,String pet_code){
        this.name=name;
        this.breed=breed;
        this.pet_code=pet_code;
    }
    public Pets(String name,String breed,String pet_code,int number){
        this.name=name;
        this.breed=breed;
        this.pet_code=pet_code;
        this.number=number;
    }
    public Pets(String name,String breed,String pet_code,int number,int price){
        this.name=name;
        this.breed=breed;
        this.pet_code=pet_code;
        this.number=number;
        this.price=price;
    }
    public Pets(int age,int number){
        this.age=age;
        this.number=number;
    }
    public Pets(String breed){
        this.breed=breed;
    }
}
