package controller;

import connection.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import shop.Clients;
import shop.Pets;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    private AnchorPane rootPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private TableView<Clients> table;
    @FXML
    private TableColumn<Clients,String> client_last_name_c;
    @FXML
    private TableColumn<Clients,String> client_first_name_c;
    @FXML
    private TableColumn<Clients,Integer> bill_number_c;
    @FXML
    private TableColumn<Clients,String> sale_date_c;
    @FXML
    private TableColumn<Clients,Integer> total_c;
    public ObservableList<Clients> data = FXCollections.observableArrayList();

    @FXML
    public void goHome(ActionEvent event) throws IOException{
        GridPane pane = FXMLLoader.load(getClass().getResource("/main/Home.fxml"));
        gridPane.getChildren().setAll(pane);

    }
    @FXML
    public void gotoAccessories(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/Access_accessories.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    @FXML
    public void gotoPets(ActionEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/Access_pets.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    @FXML
    public void gotoSimpleQuery1(ActionEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/SimpleQuery1.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    public void gotoSimpleQuery2(ActionEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/SimpleQuery2.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    public void gotoComplexQuery(ActionEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/main/ComplexQuery.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    public void makeBill() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/main/FindClient.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Meniu clienti");
        stage.show();
        stage.getIcons().add(new Image("/images/paw.png"));
    }
    public void viewBills(){
        this.table.getItems().clear();
        this.table.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT C.nume,C.prenume,F.numar_aviz,F.data_vanzare,F.total\n" +
                    "FROM clienti C INNER JOIN facturi f on C.id_client = F.id_client\n" +
                    "GROUP BY F.numar_aviz\n" +
                    "ORDER BY C.nume;";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data.add(new Clients(rs.getString(1),rs.getString(2),rs.getInt(3),
                        rs.getString(4), rs.getInt(5)));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        client_last_name_c.setCellValueFactory(new PropertyValueFactory<Clients,String>("last_name"));
        client_first_name_c.setCellValueFactory(new PropertyValueFactory<Clients,String>("first_name"));
        bill_number_c.setCellValueFactory(new PropertyValueFactory<Clients,Integer>("bill_number"));
        sale_date_c.setCellValueFactory(new PropertyValueFactory<Clients,String>("sale_date"));
        total_c.setCellValueFactory(new PropertyValueFactory<Clients,Integer>("number"));

        this.table.setItems(this.data);
    }
    @FXML
    public void Quit(ActionEvent event){
        System.exit(1);
    }
    @FXML
    public void informationAlert(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("Put some info here!");
        alert.showAndWait();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
