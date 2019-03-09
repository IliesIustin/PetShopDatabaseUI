package controller;

import connection.dbConnection;
import connection.dbConnectionInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import shop.Clients;
import shop.Pets;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

public class SimpleQuery2Controller implements Initializable {

    @FXML
    private TableView<Pets> table1;
    @FXML
    private TableView<Clients> table2;
    @FXML
    private TableView<Pets> table3;
    @FXML
    private TableColumn<Pets,String> name_c1;
    @FXML
    private TableColumn<Pets,String> breed_c1;
    @FXML
    private TableColumn<Pets,String> pet_code_c1;
    @FXML
    private TableColumn<Clients,String> last_name_c2;
    @FXML
    private TableColumn<Clients,String> first_name_c2;
    @FXML
    private TableColumn<Pets,String> name_c3;
    @FXML
    private TableColumn<Pets,String> breed_c3;
    @FXML
    private TableColumn<Pets,String> pet_code_c3;
    @FXML
    private TableColumn<Pets,Integer> number_c3;
    @FXML
    private ComboBox<String> cb_animal_name1;
    private ObservableList<String> list_names1 = FXCollections.observableArrayList(dbConnectionInfo.getBreeds());
    @FXML
    private ComboBox<String> cb_client;
    private ObservableList<String> list_clients = FXCollections.observableArrayList(dbConnectionInfo.getClients());
    @FXML
    private ComboBox<String> cb_animal_name2;
    private ObservableList<String> list_names2 = FXCollections.observableArrayList(dbConnectionInfo.getBreeds());
    @FXML
    private ComboBox<String> cb_breed;
    private ObservableList<String> list_breeds = FXCollections.observableArrayList(dbConnectionInfo.getBreeds_name(new String("Rasa")));
    @FXML
    private ComboBox<String> cb_month;
    private ObservableList<String> list_months = FXCollections.observableArrayList("1","2","3","4","5",
            "6","7","8","9","10","11","12");
    @FXML
    private ComboBox<String> cb_year;
    private ObservableList<String> list_years = FXCollections.observableArrayList("2018","2019","2020","2021","2022",
            "2023","2024","2025","2026","2027","2028","2029","2030");
    @FXML
    private RadioButton rb_M;
    @FXML
    private RadioButton rb_F;

    public ObservableList<Pets> data1 = FXCollections.observableArrayList();
    public ObservableList<Clients> data2 = FXCollections.observableArrayList();
    public ObservableList<Pets> data3 = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_animal_name1.setItems(list_names1);
        cb_client.setItems(list_clients);
        cb_animal_name2.setItems(list_names2);
        cb_breed.setItems(list_breeds);
        cb_month.setItems(list_months);
        cb_year.setItems(list_years);


        final ToggleGroup group = new ToggleGroup();
        rb_M.setToggleGroup(group);
        rb_M.setSelected(true);
        rb_F.setToggleGroup(group);
    }
    @FXML
    public void execQuery4(){
        this.table1.getItems().clear();
        this.table1.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT A.nume , A.rasa , A.cod_animal\n" +
                    "FROM animale A INNER JOIN animale_has_vanzare AV on A.id_animal = AV.id_animal\n" +
                    "               INNER JOIN vanzare_animale V on AV.id_vanzare = V.id_vanzare_animal\n" +
                    "               INNER JOIN facturi F on V.id_vanzare_animal = F.id_vanzare_animal\n" +
                    "               INNER JOIN clienti C on F.id_client = C.id_client\n" +
                    "WHERE (C.nume = ? AND A.nume=?)\n" +
                    "GROUP BY A.cod_animal;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,cb_client.getValue());
            statement.setString(2,cb_animal_name1.getValue());
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data1.add(new Pets(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        name_c1.setCellValueFactory(new PropertyValueFactory<Pets,String>("name"));
        breed_c1.setCellValueFactory(new PropertyValueFactory<Pets,String>("breed"));
        pet_code_c1.setCellValueFactory(new PropertyValueFactory<Pets,String>("pet_code"));

        this.table1.setItems(this.data1);
    }
    @FXML
    public void setPet(){
        cb_breed.setPromptText(new String("Rasa"));
        list_breeds=FXCollections.observableArrayList(dbConnectionInfo.getBreeds_name(cb_animal_name2.getValue()));
        cb_breed.setItems(list_breeds);
    }
    @FXML
    public void execQuery5(){
        this.table2.getItems().clear();
        this.table2.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT C.Nume ,C.Prenume\n" +
                    "FROM clienti C INNER JOIN facturi F ON (C.id_client = F.id_client)\n" +
                    "               INNER JOIN vanzare_animale V ON (F.id_vanzare_animal= V.id_vanzare_animal)\n" +
                    "               INNER JOIN animale_has_vanzare AV ON (V.id_vanzare_animal = AV.id_vanzare)\n" +
                    "               INNER JOIN animale A ON (AV.id_animal = A.id_animal)\n" +
                    "WHERE (A.nume = ? AND A.rasa = ?);";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,cb_animal_name2.getValue());
            statement.setString(2,cb_breed.getValue());
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data2.add(new Clients(rs.getString(1),rs.getString(2)));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        last_name_c2.setCellValueFactory(new PropertyValueFactory<Clients,String>("last_name"));
        first_name_c2.setCellValueFactory(new PropertyValueFactory<Clients,String>("first_name"));

        this.table2.setItems(this.data2);
    }

    @FXML
    public void execQuery6(){
        this.table3.getItems().clear();
        this.table3.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT  A.nume ,A.rasa, A.cod_animal, SUM(AV.numar_animale) AS Numar_animale\n" +
                    "FROM animale A INNER JOIN animale_has_vanzare AV ON A.id_animal = AV.id_animal\n" +
                    "               INNER JOIN vanzare_animale V on AV.id_vanzare = V.id_vanzare_animal\n" +
                    "               INNER JOIN facturi F on V.id_vanzare_animal = F.id_vanzare_animal\n" +
                    "WHERE (MONTH(F.data_vanzare)=? AND YEAR(F.data_vanzare)=? AND A.sex= ? )\n" +
                    "GROUP BY A.cod_animal;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(cb_month.getValue()));
            statement.setInt(2,Integer.parseInt(cb_year.getValue()));
            String sex = null;
            if(rb_M.isSelected()){
                sex = rb_M.getText();
            }
            else if(rb_F.isSelected()){
                sex = rb_F.getText();
            }
            statement.setString(3,sex);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data3.add(new Pets(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        name_c3.setCellValueFactory(new PropertyValueFactory<Pets,String>("name"));
        breed_c3.setCellValueFactory(new PropertyValueFactory<Pets,String>("breed"));
        pet_code_c3.setCellValueFactory(new PropertyValueFactory<Pets,String>("pet_code"));
        number_c3.setCellValueFactory(new PropertyValueFactory<Pets,Integer>("number"));

        this.table3.setItems(this.data3);
    }

}
