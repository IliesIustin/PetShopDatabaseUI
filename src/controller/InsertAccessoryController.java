package controller;

import connection.dbConnectionInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import shop.Accessories;
import shop.Pets;

import java.net.URL;
import java.util.ResourceBundle;

public class InsertAccessoryController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAC;
    @FXML
    private TextField txtMaker;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtStock;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void insertData_A(ActionEvent event){

        String name = txtName.getText();
        String accessory_code = txtAC.getText();
        String maker = txtMaker.getText();
        String price = txtPrice.getText();
        String stock = txtStock.getText();

        Accessories accessories = new Accessories();
        accessories.setName(name);
        accessories.setAccessory_code(accessory_code);
        accessories.setMaker(maker);
        accessories.setPrice(Integer.parseInt(price));
        accessories.setStock(Integer.parseInt(stock));

        int status = dbConnectionInfo.save_accessory(accessories);
        if(status > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Accesoriu nou");
            alert.setHeaderText("Info");
            alert.setContentText("Adaugare de accesoriu terminata cu succes !");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Accesoriu nou");
            alert.setHeaderText("Info");
            alert.setContentText("Adaugare de accesoriu terminata cu eroare !");
            alert.showAndWait();
        }
    }
    public void clear_A(){
        txtName.clear();
        txtAC.clear();
        txtMaker.clear();
        txtPrice.clear();
        txtStock.clear();
    }

}
