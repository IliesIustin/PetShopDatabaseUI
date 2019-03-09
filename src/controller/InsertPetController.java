package controller;

import connection.dbConnectionInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import shop.Pets;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InsertPetController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPC;
    @FXML
    private TextField txtBreed;
    @FXML
    private TextField txtSpecies;
    @FXML
    private RadioButton rbM;
    @FXML
    private RadioButton rbF;
    @FXML
    private TextField txtAge;
    @FXML
    private ComboBox<String> cbLife;
    private ObservableList<String> list_life = FXCollections.observableArrayList("1","2","3","4","5",
            "6","7","8","9","10","11","12","13","14","15");
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtStock;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cbLife.setItems(list_life);

        final ToggleGroup group = new ToggleGroup();
        rbM.setToggleGroup(group);
        rbM.setSelected(true);
        rbF.setToggleGroup(group);
    }

    public void insertData_P(ActionEvent event){

        String name = txtName.getText();
        String pet_code = txtPC.getText();
        String breed=txtBreed.getText();
        String species=txtSpecies.getText();
        String sex = null;
        if(rbM.isSelected()){
            sex = rbM.getText();
        }
        else if(rbF.isSelected()){
            sex = rbF.getText();
        }
        String age = txtAge.getText();
        String price = txtPrice.getText();
        String stock = txtStock.getText();

        Pets pets = new Pets();
        pets.setName(name);
        pets.setPet_code(pet_code);
        pets.setBreed(breed);
        pets.setBreed_name(species);
        pets.setSex(sex);
        pets.setAge(Integer.parseInt(age));
        pets.setLife(Integer.parseInt(cbLife.getValue()));
        pets.setPrice(Integer.parseInt(price));
        pets.setStock(Integer.parseInt(stock));

        int status = dbConnectionInfo.save_pet(pets);
        if(status > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Animal nou");
            alert.setHeaderText("Info");
            alert.setContentText("Adaugare de animal terminata cu succes !");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Animal nou");
            alert.setHeaderText("Info");
            alert.setContentText("Adaugare de animal terminata cu eroare !");
            alert.showAndWait();
        }
    }
    public void clear_P(){
        txtName.clear();
        txtPC.clear();
        txtBreed.clear();
        txtSpecies.clear();
        rbM.setSelected(true);
        rbF.setSelected(false);
        txtAge.clear();
        cbLife.setValue(null);
        txtPrice.clear();
        txtStock.clear();
    }

}
