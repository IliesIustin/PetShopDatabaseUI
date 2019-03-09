package controller;

import connection.dbConnection;
import connection.dbConnectionInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import shop.Accessories;
import shop.Clients;
import shop.Pets;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientShoppingController implements Initializable {

    @FXML
    private TextField txt_pet_code;
    @FXML
    private TextField txt_pet_name;
    @FXML
    private TextField txt_breed;
    @FXML
    private TextField txt_breed_name;
    @FXML
    private TextField txt_sex;
    @FXML
    private TextField txt_age;
    @FXML
    private TextField txt_life;
    @FXML
    private TextField txt_pet_price;
    @FXML
    private TextField txt_pet_stock;
    @FXML
    private TextField txt_accessory_code;
    @FXML
    private TextField txt_accessory_name;
    @FXML
    private TextField txt_maker;
    @FXML
    private TextField txt_accessory_price;
    @FXML
    private TextField txt_accessory_stock;
    @FXML
    private TextField txt_pet_number;
    @FXML
    private TextField txt_accessory_number;
    @FXML
    private Label last_name_label;
    @FXML
    private Label first_name_label;
    @FXML
    private Label cnp_label;
    @FXML
    private Label birth_date_label;
    @FXML
    private Button billButton;
    private static boolean isPetSale;
    private static boolean isAccessorySale;
    private static int id_pet_sale=0;
    private static int id_accessory_sale=0;
    private int id_pet=0;
    private int id_accessory=0;
    private int id_client=0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Clients client = FindClientController.getShop_client();
        last_name_label.setText(client.getLast_name());
        first_name_label.setText(client.getFirst_name());
        cnp_label.setText(client.getCnp());
        birth_date_label.setText(client.getBirth_date());
        id_client=client.getClient_id();
        isAccessorySale=false;
        isPetSale=false;

        txt_pet_name.setEditable(false);
        txt_breed_name.setEditable(false);
        txt_breed.setEditable(false);
        txt_sex.setEditable(false);
        txt_age.setEditable(false);
        txt_life.setEditable(false);
        txt_pet_price.setEditable(false);
        txt_pet_stock.setEditable(false);
        txt_accessory_name.setEditable(false);
        txt_maker.setEditable(false);
        txt_accessory_price.setEditable(false);
        txt_accessory_stock.setEditable(false);
    }
    public void getPetSale(){
        if(txt_pet_code.getText()==null||txt_pet_code.getText()==""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Vanzare animal");
            alert.setHeaderText("Info");
            alert.setContentText("Cod animal lipsa!");
            alert.showAndWait();
        } else {
            String petCode = txt_pet_code.getText();
            Pets pet = dbConnectionInfo.get_PetCode(petCode);
            if(pet.getName()==null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Vanzare animal");
                alert.setHeaderText("Info");
                alert.setContentText("Animalul nu exista in baza de date!");
                alert.showAndWait();
            }
            else {
                id_pet=pet.getId_pet();
                txt_pet_name.setText(pet.getName());
                txt_breed.setText(pet.getBreed());
                txt_breed_name.setText(pet.getBreed_name());
                txt_sex.setText(pet.getSex());
                txt_age.setText(Integer.toString(pet.getAge()));
                txt_life.setText(Integer.toString(pet.getLife()));
                txt_pet_price.setText(Integer.toString(pet.getPrice()));
                txt_pet_stock.setText(Integer.toString(pet.getStock()));
            }
        }
    }
    public void getAccessorySale(){
        if(txt_accessory_stock.getText()==null||txt_accessory_stock.getText()==""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Vanzare accesoriu");
            alert.setHeaderText("Info");
            alert.setContentText("Cod accesoriu lipsa!");
            alert.showAndWait();
        }else {
            String accessoryCode = txt_accessory_code.getText();
            Accessories accessory = dbConnectionInfo.get_AccessoryCode(accessoryCode);
            if(accessory.getName()==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Vanzare accesoriu");
                alert.setHeaderText("Info");
                alert.setContentText("Accesoriul nu exista in baza de date!");
                alert.showAndWait();
            }else {
                txt_accessory_name.setText(accessory.getName());
                txt_maker.setText(accessory.getMaker());
                txt_accessory_price.setText(Integer.toString(accessory.getPrice()));
                txt_accessory_stock.setText(Integer.toString(accessory.getStock()));
                id_accessory = accessory.getId_accessory();
            }
        }
    }
    public static int getId_pet_sale(){
        return id_pet_sale;
    }
    public static int getId_accessory_sale(){
        return id_accessory_sale;
    }
    public static void set_pet_sale(){ isPetSale=false; }
    public static void set_acc_sale(){ isAccessorySale=false; }

    public void sellPet(){
        if(!isPetSale){
            id_pet_sale = dbConnectionInfo.prepare_pet_sale();
            isPetSale=true;
        }
        if(txt_pet_number.getText()==null||txt_pet_number.getText()==""|| txt_pet_number.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Vanzare animal");
            alert.setHeaderText("Info");
            alert.setContentText("Va rugam scrieti numarul de animale!");
            alert.showAndWait();
        }else {
            String str_pet_number = txt_pet_number.getText();
            int pet_number = Integer.parseInt(str_pet_number);
            String str_pet_price = txt_pet_price.getText();
            int pet_price = Integer.parseInt(str_pet_price);
            int status = dbConnectionInfo.buy_pet(id_pet_sale, id_pet, pet_number, pet_price);
            if (status == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Vanzare animal");
                alert.setHeaderText("Info");
                alert.setContentText("Stoc insuficient !");
                alert.showAndWait();
            } else if (status > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Vanzare animal");
                alert.setHeaderText("Info");
                alert.setContentText("Animal adaugat cu succes in cosul de cumparaturi!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Vanzare animal");
                alert.setHeaderText("Info");
                alert.setContentText("Erroare in cumpararea animalului !");
                alert.showAndWait();
            }
            txt_pet_code.clear();
            txt_pet_name.clear();
            txt_breed.clear();
            txt_breed_name.clear();
            txt_sex.clear();
            txt_age.clear();
            txt_life.clear();
            txt_pet_price.clear();
            txt_pet_stock.clear();
            txt_pet_number.clear();
        }
    }
    public void sellAccessory(){
        if(!isAccessorySale){
            id_accessory_sale = dbConnectionInfo.prepare_accessory_sale();
            isAccessorySale=true;
        }
        if(txt_accessory_number.getText()==null||txt_accessory_number.getText()==""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Vanzare accesorii");
            alert.setHeaderText("Info");
            alert.setContentText("Va rugam scrieti numarul de accesorii!");
            alert.showAndWait();
        }else {
            String str_accessory_number = txt_accessory_number.getText();
            int accessory_number = Integer.parseInt(str_accessory_number);
            String str_accessory_price = txt_accessory_price.getText();
            int accessory_price = Integer.parseInt(str_accessory_price);
            int status = dbConnectionInfo.buy_accessory(id_accessory_sale, id_accessory, accessory_number, accessory_price);
            if (status == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Vanzare accesorii");
                alert.setHeaderText("Info");
                alert.setContentText("Stoc insuficient!");
                alert.showAndWait();
            } else if (status > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Vanzare accesorii");
                alert.setHeaderText("Info");
                alert.setContentText("Accesoriu adaugat cu succes in cosul de cumparaturi!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Vanzare accesorii");
                alert.setHeaderText("Info");
                alert.setContentText("Eroare in cumpararea accesoriului !");
                alert.showAndWait();
            }
            txt_accessory_code.clear();
            txt_accessory_name.clear();
            txt_maker.clear();
            txt_accessory_price.clear();
            txt_accessory_stock.clear();
            txt_accessory_number.clear();
        }
    }
    public void viewBasket() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/main/ViewBasketShopping.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Cosul de cumparaturi");
        stage.show();
        stage.getIcons().add(new Image("/images/dog_basket.png"));
    }
    public void finishBill() throws SQLException {
        if(!isPetSale){ id_pet_sale=0; }
        if(!isAccessorySale){ id_accessory_sale=0; }

        if(id_pet_sale==0 && id_accessory_sale==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Finalizare factura");
            alert.setHeaderText("Info");
            alert.setContentText("Cosul de cumparaturi este gol! " +
                    " Nu se pot emite facturi! ");
            alert.showAndWait();
        }else {
            int status = dbConnectionInfo.doBill(id_client, id_pet_sale, id_accessory_sale);
            if (status > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Finalizare factura");
                alert.setHeaderText("Info");
                alert.setContentText("Factura finalizata cu succes!");
                alert.showAndWait();
                Stage stage1 = (Stage) billButton.getScene().getWindow();
                stage1.close();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Finalizare factura");
                alert.setHeaderText("Info");
                alert.setContentText("Eroare in finisarea facturii!");
                alert.showAndWait();
            }
        }
    }

}
