package shop;


public class Clients {
    private int client_id;
    private String last_name;
    private String first_name;
    private String cnp;
    private String birth_date;
    private int number;
    private int bill_number;
    private String sale_date;

    public Clients(){
        super();
    }
    public Clients(String last_name,String first_name,int bill_number,String sale_date,int number){
        this.last_name=last_name;
        this.first_name=first_name;
        this.bill_number=bill_number;
        this.sale_date=sale_date;
        this.number=number;
    }
    public Clients(String last_name,String first_name){
        this.last_name=last_name;
        this.first_name=first_name;
    }
    public Clients(String last_name,String first_name,int number){
        this.last_name=last_name;
        this.first_name=first_name;
        this.number=number;
    }
    public Clients(String last_name){
        this.last_name=last_name;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public int getBill_number() {
        return bill_number;
    }

    public void setBill_number(int bill_number) {
        this.bill_number = bill_number;
    }

    public String getSale_date() {
        return sale_date;
    }

    public void setSale_date(String sale_date) {
        this.sale_date = sale_date;
    }

}
