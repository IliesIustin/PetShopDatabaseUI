package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import shop.Clients;
import connection.dbConnectionInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FindClientController implements Initializable {
    @FXML
    private TextField txtF_cnp;
    @FXML
    private TextField txtF_last_name;
    @FXML
    private TextField txtF_first_name;
    @FXML
    private TextField txtF_birth_date;
    @FXML
    private Button shoppingButton;
    private static Clients shop_client=new Clients();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtF_last_name.setEditable(false);
        txtF_first_name.setEditable(false);
        txtF_birth_date.setEditable(false);
    }
    public void findClient(){
        String cnpClient= txtF_cnp.getText();
        Clients client = dbConnectionInfo.find_client(cnpClient);
        txtF_last_name.setText(client.getLast_name());
        txtF_first_name.setText(client.getFirst_name());
        txtF_birth_date.setText(client.getBirth_date());
        shop_client=client;

    }
    public static Clients getShop_client(){
        return shop_client;
    }
    public void insertClient() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/main/InsertClients.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Adauga clienti");
        stage.show();
        stage.getIcons().add(new Image("/images/paw.png"));

    }
    public void gotoShopping() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/main/ClientShopping.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Fereastra factura");
        stage.show();
        stage.getIcons().add(new Image("/images/paw.png"));
        Stage stage1 = (Stage) shoppingButton.getScene().getWindow();
        stage1.close();
    }
}
