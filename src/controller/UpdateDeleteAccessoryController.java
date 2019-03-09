package controller;

import connection.dbConnectionInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import shop.Accessories;
import shop.Pets;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class UpdateDeleteAccessoryController implements Initializable {

    @FXML private TextField accessory_code;
    @FXML private TextField name;
    @FXML private TextField maker;
    @FXML private TextField price;
    @FXML private TextField stock;
    @FXML private ImageView iv;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iv.setImage(new Image("/images/pet_toy.png"));
    }

    public void getAccessory(ActionEvent event) throws IOException, ParseException {
        String accessoryCode= accessory_code.getText();
        Accessories accessory = dbConnectionInfo.get_AccessoryCode(accessoryCode);
        name.setText(accessory.getName());
        maker.setText(accessory.getMaker());
        price.setText(Integer.toString(accessory.getPrice()));
        stock.setText(Integer.toString(accessory.getStock()));
    }

    public void updateAccessory(ActionEvent event) throws IOException{
        String txt_name=name.getText();
        String txt_accessory_code= accessory_code.getText();
        String txt_maker=maker.getText();
        String txt_price=price.getText();
        String txt_stock=stock.getText();
        Accessories accessory = new Accessories();
        accessory.setId_accessory(12);
        accessory.setName(txt_name);
        accessory.setAccessory_code(txt_accessory_code);
        accessory.setMaker(txt_maker);
        accessory.setPrice(Integer.parseInt(txt_price));
        accessory.setStock(Integer.parseInt(txt_stock));
        int status = dbConnectionInfo.update_Accessory(accessory);
        if(status > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modificare accesoriu!");
            alert.setHeaderText("Info");
            alert.setContentText("Accesoriu modificat cu succes!");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modificare accesoriu!");
            alert.setHeaderText("Info");
            alert.setContentText("Modificare accesoriu terminata cu eroare!");
            alert.showAndWait();
        }


    }

    public void deleteAccessory(ActionEvent event) throws IOException{
        String accessoryCode= accessory_code.getText();
        int status =dbConnectionInfo.delete_Accessory(accessoryCode);
        if(status > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Stergere accesoriu!");
            alert.setHeaderText("Info");
            alert.setContentText("Accesoriu sters cu succes!");
            alert.showAndWait();

            accessory_code.clear();
            name.clear();
            maker.clear();
            price.clear();
            stock.clear();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Stergere accesoriu!");
            alert.setHeaderText("Info");
            alert.setContentText("Stergere accesoriu terminata cu eroare!");
            alert.showAndWait();
        }

    }
}
