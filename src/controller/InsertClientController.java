package controller;

import connection.dbConnectionInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shop.Clients;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InsertClientController implements Initializable {
    @FXML
    private TextField txtF_last_name;
    @FXML
    private TextField txtF_first_name;
    @FXML
    private TextField txtF_cnp;
    @FXML
    private DatePicker dp_birth_date;
    @FXML
    private Button clientButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void insert_Client(ActionEvent event){

        String last_name = txtF_last_name.getText();
        String first_name = txtF_first_name.getText();
        String cnp =txtF_cnp.getText();
        LocalDate dateBirth=dp_birth_date.getValue();
        String birth_date = dateBirth.toString();

        Clients client = new Clients();
        client.setLast_name(last_name);
        client.setFirst_name(first_name);
        client.setCnp(cnp);
        client.setBirth_date(birth_date);
        int status = dbConnectionInfo.save_client(client);
        if(status > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Client nou");
            alert.setHeaderText("Info");
            alert.setContentText("Client adaugat cu succes!");
            alert.showAndWait();
            Stage stage1 = (Stage) clientButton.getScene().getWindow();
            stage1.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Client nou ");
            alert.setHeaderText("Info");
            alert.setContentText("Eroare aduagare client!");
            alert.showAndWait();
        }

    }

}
