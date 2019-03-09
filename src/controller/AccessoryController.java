package controller;

import connection.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import shop.Accessories;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AccessoryController implements Initializable {
    @FXML
    private TableView<Accessories> table;
    @FXML
    private TableColumn<Accessories,String> name_c;
    @FXML
    private TableColumn<Accessories,String> accessory_code_c;
    @FXML
    private TableColumn<Accessories,String> maker_c;
    @FXML
    private TableColumn<Accessories,Integer> price_c;
    @FXML
    private TableColumn<Accessories,Integer> stock_c;
    public ObservableList<Accessories> data = FXCollections.observableArrayList();
    public void viewAccessories(){
        this.table.getItems().clear();
        this.table.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT * FROM accesorii;";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data.add(new Accessories(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getInt(5),rs.getInt(6)));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        name_c.setCellValueFactory(new PropertyValueFactory<Accessories,String>("name"));
        accessory_code_c.setCellValueFactory(new PropertyValueFactory<Accessories,String>("accessory_code"));
        maker_c.setCellValueFactory(new PropertyValueFactory<Accessories,String>("maker"));
        price_c.setCellValueFactory(new PropertyValueFactory<Accessories,Integer>("price"));
        stock_c.setCellValueFactory(new PropertyValueFactory<Accessories,Integer>("stock"));
        this.table.setItems(this.data);
    }

    public void insertAccessory() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/main/InsertAccessories.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Adauga un accesoriu");
        stage.show();
        stage.getIcons().add(new Image("/images/paw.png"));
    }
    public void updateDeleteAccessory() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/main/UpdateDeleteAccessory.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sterge sau adauga un accesoriu");
        stage.show();
        stage.getIcons().add(new Image("/images/paw.png"));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
