package controller;

import connection.dbConnection;
import connection.dbConnectionInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import shop.Accessories;
import shop.Pets;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BasketController implements Initializable {
    @FXML
    private TableView<Pets> table1;
    @FXML
    private TableColumn<Pets,String> pet_name_c;
    @FXML
    private TableColumn<Pets,String> breed_c;
    @FXML
    private TableColumn<Pets,String> pet_code_c;
    @FXML
    private TableColumn<Pets,Integer> pet_number_c;
    @FXML
    private TableColumn<Pets,Integer> pet_price_c;
    @FXML
    private TableView<Accessories> table2;
    @FXML
    private TableColumn<Accessories,String> accessory_name_c;
    @FXML
    private TableColumn<Accessories,String> accessory_code_c;
    @FXML
    private TableColumn<Accessories,Integer> accessory_number_c;
    @FXML
    private TableColumn<Accessories,Integer> accessory_price_c;
    @FXML
    private Button closeButton;
    @FXML
    private TextField pet_code_txt;
    @FXML
    private TextField accessory_code_txt;
    public ObservableList<Pets> data1 = FXCollections.observableArrayList();
    public ObservableList<Accessories> data2 = FXCollections.observableArrayList();
    private int id_pet_sale=0;
    private int id_accessory_sale=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_pet_sale=ClientShoppingController.getId_pet_sale();
        id_accessory_sale=ClientShoppingController.getId_accessory_sale();
    }

    public void viewPetBasket(){
        if(id_pet_sale == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cos de cumparaturi");
            alert.setHeaderText("Info");
            alert.setContentText("Nu exista animale in cosul de cumparaturi!");
            alert.showAndWait();
        }
        else {
        this.table1.getItems().clear();
        this.table1.setItems(null);
        try{
            Connection con = dbConnection.connect();
            String sql = "SELECT A.nume ,A.rasa, A.cod_animal,AV.numar_animale,A.pret*AV.numar_animale AS pret_animale\n" +
                    "FROM animale A INNER JOIN animale_has_vanzare AV on A.id_animal = AV.id_animal\n" +
                    "               INNER JOIN vanzare_animale V on AV.id_vanzare = V.id_vanzare_animal\n" +
                    "WHERE V.id_vanzare_animal=?\n" +
                    "GROUP BY A.cod_animal;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,id_pet_sale);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                data1.add(new Pets(rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getInt(4),rs.getInt(5)));

            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        pet_name_c.setCellValueFactory(new PropertyValueFactory<Pets,String>("name"));
        pet_code_c.setCellValueFactory(new PropertyValueFactory<Pets,String>("pet_code"));
        breed_c.setCellValueFactory(new PropertyValueFactory<Pets,String>("breed"));
        pet_number_c.setCellValueFactory(new PropertyValueFactory<Pets,Integer>("number"));
        pet_price_c.setCellValueFactory(new PropertyValueFactory<Pets,Integer>("price"));

        this.table1.setItems(this.data1);
        }
    }
    public void viewAccessoryBasket() {
        if (id_accessory_sale == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cos de cumparaturi");
            alert.setHeaderText("Info");
            alert.setContentText("Nu exista accesorii in cosul de cumparaturi!");
            alert.showAndWait();
        } else {
            this.table2.getItems().clear();
            this.table2.setItems(null);
            try {
                Connection con = dbConnection.connect();
                String sql = "SELECT A.nume ,A.cod_accesoriu,AV.numar_accesorii,A.pret*AV.numar_accesorii AS pret_accesorii\n" +
                        "FROM accesorii A INNER JOIN accesorii_has_vanzare AV on A.id_accesoriu = AV.id_accesoriu\n" +
                        "                 INNER JOIN vanzare_accesorii V on AV.id_vanzare_accesorii = V.id_vanzare_accesorii\n" +
                        "WHERE V.id_vanzare_accesorii=?\n" +
                        "GROUP BY A.cod_accesoriu;";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, id_accessory_sale);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    data2.add(new Accessories(rs.getString(1), rs.getString(2),
                            rs.getInt(3), rs.getInt(4)));
                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            accessory_name_c.setCellValueFactory(new PropertyValueFactory<Accessories, String>("name"));
            accessory_code_c.setCellValueFactory(new PropertyValueFactory<Accessories, String>("accessory_code"));
            accessory_number_c.setCellValueFactory(new PropertyValueFactory<Accessories, Integer>("number"));
            accessory_price_c.setCellValueFactory(new PropertyValueFactory<Accessories, Integer>("price"));
            this.table2.setItems(this.data2);
        }
    }
    public void remove_pet_basket(){
        String pet_code=pet_code_txt.getText();
        int pet_id=0;
        int number_pets=0;
        try {
            Connection con = dbConnection.connect();
            String sql = "SELECT id_animal FROM animale WHERE cod_animal=?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, pet_code);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                pet_id=rs.getInt(1);
            }
            try{
                Connection con1 = dbConnection.connect();
                String sql1 = "SELECT numar_animale FROM animale_has_vanzare WHERE (id_animal=? AND id_vanzare=?);";
                PreparedStatement statement1 = con1.prepareStatement(sql1);
                statement1.setInt(1,pet_id);
                statement1.setInt(2,id_pet_sale);
                ResultSet rs1 = statement1.executeQuery();
                while (rs1.next()){
                    number_pets=rs1.getInt(1);
                }
                try {
                    Pets pet = dbConnectionInfo.get_PetCode(pet_code);
                    int stock=pet.getStock();
                    int price=pet.getPrice();
                    Connection con2 = dbConnection.connect();
                    String sql2="UPDATE animale SET stoc=? WHERE cod_animal=?;";
                    PreparedStatement statement2 = con2.prepareStatement(sql2);
                    int new_stock=stock+number_pets;
                    statement2.setInt(1,new_stock);
                    statement2.setString(2,pet_code);
                    statement2.executeUpdate();
                    String sql3="UPDATE vanzare_animale SET numar_total_animale=?,taxa_vanzare=?,cost_animale=? WHERE id_vanzare_animal=?;";
                    PreparedStatement statement3= con2.prepareStatement(sql3);
                    int current_pet_number=0;
                    int current_tax=0;
                    int current_pet_price=0;
                    try{

                        Connection con3 = dbConnection.connect();
                        String sql4= "SELECT numar_total_animale,taxa_vanzare,cost_animale FROM vanzare_animale WHERE id_vanzare_animal=?;";
                        PreparedStatement statement4=con3.prepareStatement(sql4);
                        statement4.setInt(1,id_pet_sale);
                        ResultSet rs3=statement4.executeQuery();
                        while (rs3.next()){
                            current_pet_number=rs3.getInt(1);
                            current_tax=rs3.getInt(2);
                            current_pet_price=rs3.getInt(3);
                        }
                    con3.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    int new_pet_number=current_pet_number-number_pets;
                    int new_tax=current_tax-number_pets*10;
                    int new_pet_price=current_pet_price-price*number_pets;
                    statement3.setInt(1,new_pet_number);
                    statement3.setInt(2,new_tax);
                    statement3.setInt(3,new_pet_price);
                    statement3.setInt(4,id_pet_sale);
                    int st=statement3.executeUpdate();
                    String sql5=" DELETE FROM animale_has_vanzare WHERE (id_animal=? AND id_vanzare =?)";
                    PreparedStatement statement5=con2.prepareStatement(sql5);
                    statement5.setInt(1,pet_id);
                    statement5.setInt(2,id_pet_sale);
                    int st1=statement5.executeUpdate();
                    if (st1 > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Cos de cumparaturi");
                        alert.setHeaderText("Info");
                        alert.setContentText("Animal scos din cosul de cumparaturi !");
                        alert.showAndWait();
                        pet_code_txt.clear();
                    }

                con2.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            con1.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void remove_accessory_basket(){
        String accessory_code=accessory_code_txt.getText();
        int accessory_id=0;
        int number_accessories=0;
        try {
            Connection con = dbConnection.connect();
            String sql = "SELECT id_accesoriu FROM accesorii WHERE cod_accesoriu=?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, accessory_code);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                accessory_id=rs.getInt(1);
            }
            try{
                Connection con1 = dbConnection.connect();
                String sql1 = "SELECT numar_accesorii FROM accesorii_has_vanzare WHERE (id_accesoriu=? AND id_vanzare_accesorii=?);";
                PreparedStatement statement1 = con1.prepareStatement(sql1);
                statement1.setInt(1,accessory_id);
                statement1.setInt(2,id_accessory_sale);
                ResultSet rs1 = statement1.executeQuery();
                while (rs1.next()){
                    number_accessories=rs1.getInt(1);
                }
                try {
                    Accessories accessory = dbConnectionInfo.get_AccessoryCode(accessory_code);
                    int stock=accessory.getStock();
                    int price=accessory.getPrice();
                    Connection con2 = dbConnection.connect();
                    String sql2="UPDATE accesorii SET stoc=? WHERE cod_accesoriu=?;";
                    PreparedStatement statement2 = con2.prepareStatement(sql2);
                    int new_stock=stock+number_accessories;
                    statement2.setInt(1,new_stock);
                    statement2.setString(2,accessory_code);
                    statement2.executeUpdate();
                    String sql3="UPDATE vanzare_accesorii SET numar_total_accesorii=?,cost_accesorii=? WHERE id_vanzare_accesorii=?;";
                    PreparedStatement statement3= con2.prepareStatement(sql3);
                    int current_acc_number=0;
                    int current_acc_price=0;
                    try{

                        Connection con3 = dbConnection.connect();
                        String sql4= "SELECT numar_total_accesorii,cost_accesorii FROM vanzare_accesorii WHERE id_vanzare_accesorii=?;";
                        PreparedStatement statement4=con3.prepareStatement(sql4);
                        statement4.setInt(1,id_accessory_sale);
                        ResultSet rs3=statement4.executeQuery();
                        while (rs3.next()){
                            current_acc_number=rs3.getInt(1);
                            current_acc_price=rs3.getInt(2);
                        }
                        con3.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    int new_acc_number=-current_acc_number+number_accessories;
                    int new_acc_price=-current_acc_price+price*number_accessories;
                    statement3.setInt(1,new_acc_number);
                    statement3.setInt(2,new_acc_price);
                    statement3.setInt(3,id_accessory_sale);
                    int st=statement3.executeUpdate();
                    String sql5=" DELETE FROM accesorii_has_vanzare WHERE (id_accesoriu=? AND id_vanzare_accesorii=?)";
                    PreparedStatement statement5=con2.prepareStatement(sql5);
                    statement5.setInt(1,accessory_id);
                    statement5.setInt(2,id_accessory_sale);
                    int st1=statement5.executeUpdate();
                    if (st1 > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Cos de cumparaturi");
                        alert.setHeaderText("Info");
                        alert.setContentText("Accesoriu scos din cosul de cumparaturi !");
                        alert.showAndWait();
                        accessory_code_txt.clear();
                    }

                    con2.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                con1.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void close() throws SQLException {
        Connection con = dbConnection.connect();
        String sql = "DELETE FROM vanzare_animale WHERE numar_total_animale=0; ";
        String sql1 = "DELETE FROM vanzare_accesorii WHERE numar_total_accesorii=0;";
        PreparedStatement statement = con.prepareStatement(sql);
        PreparedStatement statement1 = con.prepareStatement(sql1);
        int st = statement.executeUpdate();
        int st1 = statement1.executeUpdate();
        if(st==1){
            ClientShoppingController.set_pet_sale();
        }
        if(st1==1){
            ClientShoppingController.set_acc_sale();
        }
        Stage stage1 = (Stage) closeButton.getScene().getWindow();
        stage1.close();
    }

}
