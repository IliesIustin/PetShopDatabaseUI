package controller;

import connection.dbConnection;
import connection.dbConnectionInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import shop.Accessories;
import shop.Clients;
import shop.Pets;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ComplexQueryController implements Initializable {
    @FXML
    private TableView<Clients> table1;
    @FXML
    private TableView<Clients> table2;
    @FXML
    private TableView<Pets> table3;
    @FXML
    private TableView<Accessories> table4;
    @FXML
    private TableColumn<Clients,String> last_name_c1;
    @FXML
    private TableColumn<Clients,String> first_name_c1;
    @FXML
    private TableColumn<Clients,Integer> notice_bill_c1;
    @FXML
    private TableColumn<Clients,String> last_name_c2;
    @FXML
    private TableColumn<Clients,String> first_name_c2;
    @FXML
    private TableColumn<Pets,Integer> month_c3;
    @FXML
    private TableColumn<Pets,Integer> number_bills_c3;
    @FXML
    private TableColumn<Accessories,String> maker_c4;
    @FXML
    private TableColumn<Accessories,Integer> accs_sold_c4;
    @FXML
    private ComboBox<String> cb_animal_name;
    private ObservableList<String> list_breeds = FXCollections.observableArrayList(dbConnectionInfo.getBreeds());
    @FXML
    private ComboBox<String> cb_year;
    private ObservableList<String> list_years = FXCollections.observableArrayList("2018","2019","2020","2021","2022",
            "2023","2024","2025","2026","2027","2028","2029","2030");

    public ObservableList<Clients> data1 = FXCollections.observableArrayList();
    public ObservableList<Clients> data2 = FXCollections.observableArrayList();
    public ObservableList<Pets> data3 = FXCollections.observableArrayList();
    public ObservableList<Accessories> data4 = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_animal_name.setItems(list_breeds);
        cb_year.setItems(list_years);
    }
    @FXML
    public void execQuery7(){
        this.table1.getItems().clear();
        this.table1.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT C.nume,C.prenume,F1.numar_aviz\n" +
                    "FROM clienti C INNER JOIN facturi F1 on C.id_client = F1.id_client\n" +
                    "WHERE F1.Total>(\n" +
                    "                SELECT AVG(F.Total)\n" +
                    "                FROM facturi F)\n" +
                    "GROUP BY F1.numar_aviz;";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data1.add(new Clients(rs.getString(1),rs.getString(2),rs.getInt(3)));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        last_name_c1.setCellValueFactory(new PropertyValueFactory<Clients,String>("last_name"));
        first_name_c1.setCellValueFactory(new PropertyValueFactory<Clients,String>("first_name"));
        notice_bill_c1.setCellValueFactory(new PropertyValueFactory<Clients,Integer>("number"));

        this.table1.setItems(this.data1);
    }
    @FXML
    public void execQuery8(){
        this.table2.getItems().clear();
        this.table2.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT C.nume , C.prenume,A.nume,A.rasa,A.cod_animal,A.varsta\n" +
                    "FROM clienti C INNER JOIN facturi F on C.id_client = F.id_client\n" +
                    "             INNER JOIN vanzare_animale V on F.id_vanzare_animal = V.id_vanzare_animal\n" +
                    "             INNER JOIN animale_has_vanzare AV on V.id_vanzare_animal = AV.id_vanzare\n" +
                    "             INNER JOIN animale A on AV.id_animal = A.id_animal\n" +
                    "WHERE A.varsta = (SELECT MIN(A1.varsta) AS Cel_mai_tanar_animal FROM animale A1 WHERE (A1.nume = ?) AND\n" +
                    "                 A.nume=A1.nume);";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,cb_animal_name.getValue());
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
    public void execQuery9(){
        this.table3.getItems().clear();
        this.table3.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT luna , MAX(Numar) AS Numar_facturi\n" +
                    "FROM (SELECT MONTH(data_vanzare) AS luna, COUNT(id_factura) AS Numar FROM facturi\n" +
                    "      WHERE YEAR(data_vanzare)=?\n" +
                    "      GROUP BY MONTH(data_vanzare)\n" +
                    "      ORDER BY luna DESC ) AS V;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(cb_year.getValue()));
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data3.add(new Pets(rs.getInt(1),rs.getInt(2)));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        month_c3.setCellValueFactory(new PropertyValueFactory<Pets,Integer>("age"));
        number_bills_c3.setCellValueFactory(new PropertyValueFactory<Pets,Integer>("number"));

        this.table3.setItems(this.data3);
    }
    @FXML
    public void execQuery10(){
        this.table4.getItems().clear();
        this.table4.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT A.producator ,MAX(numar_accesorii) AS accesorii_vandute\n" +
                    "FROM(\n" +
                    "SELECT COUNT(V.id_accesoriu) AS numar_accesorii , A.producator\n" +
                    "FROM accesorii A INNER JOIN accesorii_has_vanzare V on A.id_accesoriu = V.id_accesoriu\n" +
                    "                 INNER JOIN vanzare_accesorii VA on V.id_vanzare_accesorii = VA.id_vanzare_accesorii\n" +
                    "GROUP BY A.producator) AS A;";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data4.add(new Accessories(rs.getString(1),rs.getInt(2)));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        maker_c4.setCellValueFactory(new PropertyValueFactory<Accessories,String>("maker"));
        accs_sold_c4.setCellValueFactory(new PropertyValueFactory<Accessories,Integer>("number"));

        this.table4.setItems(this.data4);
    }
}
