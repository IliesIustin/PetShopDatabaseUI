package controller;

import connection.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectController implements Initializable {
    @FXML private TextField txtF;
    @FXML private PasswordField passF;
    @FXML private Button loginButton;
    public void login(ActionEvent event) throws SQLException {
        Connection con = dbConnection.connect();
        PreparedStatement stat = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM inregistrari WHERE username = ? AND password = ? ;";
        try{
            stat = con.prepareStatement(sql);
            stat.setString(1,txtF.getText().toString());
            stat.setString(2, passF.getText().toString());
            rs=stat.executeQuery();
            if(rs.next()){
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/Main/Home.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Acasa");
                stage.show();
                stage.setResizable(false);
                stage.getIcons().add(new Image("/images/corner_icon.png"));
                Stage stage1 = (Stage) loginButton.getScene().getWindow();
                stage1.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Eroare");
                alert.setTitle("Mesaj eroare");
                alert.setContentText("Parola sau username incorecte");
                alert.showAndWait();
            }
        }catch ( Exception e ){
            System.out.println(e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
