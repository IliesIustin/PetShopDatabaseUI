package controller;

import connection.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import shop.Accessories;
import shop.Clients;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SimpleQuery1Controller implements Initializable {
    @FXML
    private TableView<Clients> table1;
    @FXML
    private TableView<Clients> table2;
    @FXML
    private TableView<Accessories> table3;
    @FXML
    private TableColumn<Clients,String> last_name_c1;
    @FXML
    private TableColumn<Clients,String> first_name_c1;
    @FXML
    private TableColumn<Clients,Integer> number_c1;
    @FXML
    private TableColumn<Clients,String> last_name_c2;
    @FXML
    private TableColumn<Clients,String> first_name_c2;
    @FXML
    private TableColumn<Clients,Integer> number_c2;
    @FXML
    private TableColumn<Accessories,String> name_c3;
    @FXML
    private TableColumn<Accessories,Float> sale_c3;
    @FXML
    private TextField txtF_nr_acc;
    @FXML
    private TextField txtF_sale;

    public ObservableList<Clients> data1 = FXCollections.observableArrayList();
    public ObservableList<Clients> data2 = FXCollections.observableArrayList();
    public ObservableList<Accessories> data3 = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    @FXML
    public void execQuery1(){
        this.table1.getItems().clear();
        this.table1.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT C.nume , C.Prenume , COUNT(F.id_factura) AS Nr_Facturi\n" +
                    "FROM clienti C INNER JOIN facturi F ON C.id_client = F.id_client\n" +
                    "GROUP BY C.nume,C.prenume;";
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
        number_c1.setCellValueFactory(new PropertyValueFactory<Clients,Integer>("number"));

        this.table1.setItems(this.data1);
    }
    @FXML
    public void execQuery2(){
        this.table2.getItems().clear();
        this.table2.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT C.nume , C.prenume,SUM(A.numar_accesorii) AS Numar_accesorii\n" +
                    "FROM clienti C INNER JOIN facturi F on (C.id_client = F.id_client)\n" +
                    "               INNER JOIN vanzare_accesorii V on F.id_vanzare_accesorii = V.id_vanzare_accesorii\n" +
                    "               INNER JOIN accesorii_has_vanzare A on V.id_vanzare_accesorii = A.id_vanzare_accesorii\n" +
                    "GROUP BY C.nume , C.prenume\n" +
                    "HAVING (SUM(A.numar_accesorii)>=?);";

            PreparedStatement statement = con.prepareStatement(sql);
            String nr_acc=txtF_nr_acc.getText().toString();
            int nr_acc_int=Integer.parseInt(nr_acc);
            statement.setInt(1,nr_acc_int);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data2.add(new Clients(rs.getString(1),rs.getString(2),rs.getInt(3)));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        last_name_c2.setCellValueFactory(new PropertyValueFactory<Clients,String>("last_name"));
        first_name_c2.setCellValueFactory(new PropertyValueFactory<Clients,String>("first_name"));
        number_c2.setCellValueFactory(new PropertyValueFactory<Clients,Integer>("number"));

        this.table2.setItems(this.data2);
    }
    @FXML
    public void execQuery3(){
        this.table3.getItems().clear();
        this.table3.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT A.producator ,SUM(A.pret) AS total_vanzare\n" +
                    "FROM accesorii A INNER JOIN accesorii_has_vanzare V on A.id_accesoriu = V.id_accesoriu\n" +
                    "                 INNER JOIN vanzare_accesorii VA on V.id_vanzare_accesorii = VA.id_vanzare_accesorii\n" +
                    "GROUP BY A.producator\n" +
                    "HAVING(SUM(A.pret)>?);";
            PreparedStatement statement = con.prepareStatement(sql);
            String str_sale=txtF_sale.getText().toString();
            float sale =Float.parseFloat(str_sale);
            statement.setFloat(1,sale);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data3.add(new Accessories(rs.getString(1),rs.getFloat(2)));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        name_c3.setCellValueFactory(new PropertyValueFactory<Accessories,String>("name"));
        sale_c3.setCellValueFactory(new PropertyValueFactory<Accessories,Float>("number"));

        this.table3.setItems(this.data3);
    }

}
