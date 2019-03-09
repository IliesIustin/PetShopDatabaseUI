package controller;

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
import shop.Pets;
import connection.dbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class PetController implements Initializable {

    @FXML
    private TableView<Pets> table;
    @FXML
    private TableColumn<Pets,String> name_c;
    @FXML
    private TableColumn<Pets,String> pet_code_c;
    @FXML
    private TableColumn<Pets,String> breed_c;
    @FXML
    private TableColumn<Pets,String> breed_name_c;
    @FXML
    private TableColumn<Pets,String> sex_c;
    @FXML
    private TableColumn<Pets,Integer> age_c;
    @FXML
    private TableColumn<Pets,Integer> life_c;
    @FXML
    private TableColumn<Pets,Integer> price_c;
    @FXML
    private TableColumn<Pets,Integer> stock_c;
    public ObservableList<Pets> data = FXCollections.observableArrayList();
    @FXML
    public void viewPets(){
        this.table.getItems().clear();
        this.table.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT * FROM animale;";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data.add(new Pets(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5),
                        rs.getString(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10)));

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        name_c.setCellValueFactory(new PropertyValueFactory<Pets,String>("name"));
        pet_code_c.setCellValueFactory(new PropertyValueFactory<Pets,String>("pet_code"));
        breed_c.setCellValueFactory(new PropertyValueFactory<Pets,String>("breed"));
        breed_name_c.setCellValueFactory(new PropertyValueFactory<Pets,String>("breed_name"));
        sex_c.setCellValueFactory(new PropertyValueFactory<Pets,String>("sex"));
        age_c.setCellValueFactory(new PropertyValueFactory<Pets,Integer>("age"));
        life_c.setCellValueFactory(new PropertyValueFactory<Pets,Integer>("life"));
        price_c.setCellValueFactory(new PropertyValueFactory<Pets,Integer>("price"));
        stock_c.setCellValueFactory(new PropertyValueFactory<Pets,Integer>("stock"));

        this.table.setItems(this.data);
    }

    public void insertPet() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/main/InsertPets.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Adauga un animal");
        stage.show();
        stage.getIcons().add(new Image("/images/paw.png"));
    }
    public void updateDeletePet() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/main/UpdateDeletePet.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sterge sau adauga un animal");
        stage.show();
        stage.getIcons().add(new Image("/images/paw.png"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
