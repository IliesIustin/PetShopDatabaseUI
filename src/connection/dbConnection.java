package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dbConnection {
    private static final String username = "root";
    private static final String password = "imaculat";
    private static final String url = "jdbc:mysql://localhost:3306/pet_shop";
    public static Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,username,password);
            return conn;

        }catch (ClassNotFoundException | SQLException e){
            Logger.getLogger(dbConnection.class.getName()).log(Level.SEVERE,null,e);
        }
        return null;
    }
}
