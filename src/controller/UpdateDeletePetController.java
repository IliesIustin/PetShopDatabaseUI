package controller;

import connection.dbConnectionInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import shop.Pets;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class UpdateDeletePetController implements Initializable {

    @FXML private TextField pet_code;
    @FXML private TextField name;
    @FXML private TextField breed;
    @FXML private TextField species;
    @FXML private TextField sex;
    @FXML private TextField age;
    @FXML private TextField life;
    @FXML private TextField price;
    @FXML private TextField stock;
    @FXML private ImageView iv;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iv.setImage(new Image("/images/hamster.png"));
    }

    public void getPet(ActionEvent event) throws IOException, ParseException {
        String petCode= pet_code.getText();
        Pets pet= dbConnectionInfo.get_PetCode(petCode);
        name.setText(pet.getName());
        breed.setText(pet.getBreed());
        species.setText(pet.getBreed_name());
        sex.setText(pet.getSex());
        age.setText(Integer.toString(pet.getAge()));
        life.setText(Integer.toString(pet.getLife()));
        price.setText(Integer.toString(pet.getPrice()));
        stock.setText(Integer.toString(pet.getStock()));
    }

    public void updatePet(ActionEvent event) throws IOException{
        String txt_name=name.getText();
        String txt_pet_code= pet_code.getText();
        String txt_breed=breed.getText();
        String txt_species=species.getText();
        String txt_sex=sex.getText();
        String txt_age=age.getText();
        String txt_life=life.getText();
        String txt_price=price.getText();
        String txt_stock=stock.getText();
        Pets pet = new Pets();
        pet.setId_pet(11);
        pet.setName(txt_name);
        pet.setPet_code(txt_pet_code);
        pet.setBreed(txt_breed);
        pet.setBreed_name(txt_species);
        pet.setSex(txt_sex);
        pet.setAge(Integer.parseInt(txt_age));
        pet.setLife(Integer.parseInt(txt_life));
        pet.setPrice(Integer.parseInt(txt_price));
        pet.setStock(Integer.parseInt(txt_stock));
        int status = dbConnectionInfo.update_Pet(pet);
        if(status > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modificare animal!");
            alert.setHeaderText("Info");
            alert.setContentText("Animal modificat cu succes!");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modificare animal!");
            alert.setHeaderText("Info");
            alert.setContentText("Modificare animal terminata cu eroare!");
            alert.showAndWait();
        }

    }

    public void deletePet(ActionEvent event) throws IOException{
        String petCode= pet_code.getText();
        int status =dbConnectionInfo.delete_Pet(petCode);
        if(status > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Stergere animal!");
            alert.setHeaderText("Info");
            alert.setContentText("Animal sters cu succes!");
            alert.showAndWait();

            pet_code.clear();
            name.clear();
            breed.clear();
            species.clear();
            sex.clear();
            age.clear();
            life.clear();
            price.clear();
            stock.clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Stergere accesoriu!");
            alert.setHeaderText("Info");
            alert.setContentText("Stergere animal terminat cu eroare!");
            alert.showAndWait();
        }

    }
}
