package connection;

import shop.Accessories;
import shop.Clients;
import shop.Pets;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dbConnectionInfo {
    private static final String username = "root";
    private static final String password = "imaculat";
    private static final String url = "jdbc:mysql://localhost:3306/pet_shop";

    public static Connection connect() {
        try {
            int i; // does nothing
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;

        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(dbConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static int save_pet(Pets pets) {
        int st = 0;
        int new_id = ThreadLocalRandom.current().nextInt(0, 500 + 1);
        try {
            String sql = "INSERT INTO animale(id_animal,nume,cod_animal,rasa,specie,sex,varsta,ani_viata,pret,stoc) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?);";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setInt(1, new_id);
            stat.setString(2, pets.getName());
            stat.setString(3, pets.getPet_code());
            stat.setString(4, pets.getBreed());
            stat.setString(5, pets.getBreed_name());
            stat.setString(6, pets.getSex());
            stat.setInt(7, pets.getAge());
            stat.setInt(8, pets.getLife());
            stat.setInt(9, pets.getPrice());
            stat.setInt(10, pets.getStock());
            st = stat.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }

    public static int save_accessory(Accessories accessories) {
        int st = 0;
        int new_id = ThreadLocalRandom.current().nextInt(500, 1000 + 1);
        try {
            String sql = "INSERT INTO accesorii(id_accesoriu,nume,cod_accesoriu,producator,pret,stoc) " +
                    "VALUES(?,?,?,?,?,?);";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setInt(1, new_id);
            stat.setString(2, accessories.getName());
            stat.setString(3, accessories.getAccessory_code());
            stat.setString(4, accessories.getMaker());
            stat.setInt(5, accessories.getPrice());
            stat.setInt(6, accessories.getStock());
            st = stat.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }

    public static int update_Pet(Pets pets) {
        int st = 0;
        try {
            String sql = "UPDATE animale SET nume=?,rasa=?,specie=?,sex=?,varsta=?,ani_viata=?,pret=?,stoc=? WHERE " +
                    "cod_animal=? ;";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setString(1, pets.getName());
            stat.setString(2, pets.getBreed());
            stat.setString(3, pets.getBreed_name());
            stat.setString(4, pets.getSex());
            stat.setInt(5, pets.getAge());
            stat.setInt(6, pets.getLife());
            stat.setInt(7, pets.getPrice());
            stat.setInt(8, pets.getStock());
            stat.setString(9, pets.getPet_code());
            st = stat.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }

    public static int update_Accessory(Accessories accessories) {
        int st = 0;
        try {
            String sql = "UPDATE accesorii SET nume=?,producator=?,pret=?,stoc=? WHERE " +
                    "cod_accesoriu=? ;";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setString(1, accessories.getName());
            stat.setString(2, accessories.getMaker());
            stat.setInt(3, accessories.getPrice());
            stat.setInt(4, accessories.getStock());
            stat.setString(5, accessories.getAccessory_code());
            st = stat.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }

    public static int delete_Pet(String petCode) {
        int st = 0;
        try {
            String sql = "DELETE FROM animale WHERE cod_animal=?;";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setString(1, petCode);
            st = stat.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }

    public static int delete_Accessory(String accessoryCode) {
        int st = 0;
        try {
            String sql = "DELETE FROM accesorii WHERE cod_accesoriu=?;";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setString(1, accessoryCode);
            st = stat.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }

    public static Pets get_PetCode(String petCode) {
        Pets pet = new Pets();
        try {
            String sql = "SELECT * FROM animale WHERE cod_animal=?;";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setString(1, petCode);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                pet.setId_pet(rs.getInt(1));
                pet.setName(rs.getString(2));
                pet.setPet_code(rs.getString(3));
                pet.setBreed(rs.getString(4));
                pet.setBreed_name(rs.getString(5));
                pet.setSex(rs.getString(6));
                pet.setAge(rs.getInt(7));
                pet.setLife(rs.getInt(8));
                pet.setPrice(rs.getInt(9));
                pet.setStock(rs.getInt(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pet;
    }

    public static Accessories get_AccessoryCode(String accessoryCode) {
        Accessories accessory = new Accessories();
        try {
            String sql = "SELECT * FROM accesorii WHERE cod_accesoriu=?;";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setString(1, accessoryCode);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                accessory.setId_accessory(rs.getInt(1));
                accessory.setName(rs.getString(2));
                accessory.setAccessory_code(rs.getString(3));
                accessory.setMaker(rs.getString(4));
                accessory.setPrice(rs.getInt(5));
                accessory.setStock(rs.getInt(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accessory;
    }

    public static ArrayList<String> getBreeds() {
        ArrayList<String> breeds = new ArrayList<>();
        try {
            Connection con = dbConnection.connect();
            String sql = "SELECT DISTINCT nume FROM animale;";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Pets pet = new Pets(rs.getString(1));
                breeds.add(pet.getBreed());
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return breeds;
    }

    public static ArrayList<String> getClients() {
        ArrayList<String> clients = new ArrayList<>();
        try {
            Connection con = dbConnection.connect();
            String sql = "SELECT nume FROM clienti;";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Clients client = new Clients(rs.getString(1));
                clients.add(client.getLast_name());
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }

    public static ArrayList<String> getBreeds_name(String name) {
        ArrayList<String> breeds_name = new ArrayList<>();
        try {
            Connection con = dbConnection.connect();
            String sql = "SELECT DISTINCT rasa FROM animale WHERE nume=?;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Pets pet = new Pets(rs.getString(1));
                breeds_name.add(pet.getBreed());
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return breeds_name;
    }

    public static Clients find_client(String clientCnp) {
        Clients client = new Clients();
        try {
            String sql = "SELECT * FROM clienti WHERE cnp=?;";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setString(1, clientCnp);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                client.setClient_id(rs.getInt(1));
                client.setLast_name(rs.getString(2));
                client.setFirst_name(rs.getString(3));
                client.setCnp(rs.getString(4));
                client.setBirth_date(rs.getString(5));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public static int save_client(Clients client) {
        int st = 0;
        int new_id = ThreadLocalRandom.current().nextInt(10000, 10500 + 1);
        try {
            String sql = "INSERT INTO clienti(id_client,nume,prenume,cnp,data_nasterii) " +
                    "VALUES(?,?,?,?,?);";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setInt(1, new_id);
            stat.setString(2, client.getLast_name());
            stat.setString(3, client.getFirst_name());
            stat.setString(4, client.getCnp());
            stat.setString(5, client.getBirth_date());
            st = stat.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }

    public static int prepare_pet_sale() {
        int st = 0;
        int new_id = ThreadLocalRandom.current().nextInt(10500, 11000 + 1);
        try {
            String sql = "INSERT INTO vanzare_animale(id_vanzare_animal,numar_total_animale,taxa_vanzare,cost_animale) " +
                    "VALUES(?,?,?,?);";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            int zero = 0;
            stat = con.prepareStatement(sql);
            stat.setInt(1, new_id);
            stat.setInt(2, zero);
            stat.setInt(3, zero);
            stat.setInt(4, zero);
            st = stat.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new_id;
    }

    public static int prepare_accessory_sale() {
        int st = 0;
        int new_id = ThreadLocalRandom.current().nextInt(11000, 11500 + 1);
        try {
            String sql1 = "INSERT INTO vanzare_accesorii(id_vanzare_accesorii,numar_total_accesorii,cost_accesorii) " +
                    "VALUES(?,?,?);";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            int zero = 0;
            stat = con.prepareStatement(sql1);
            stat.setInt(1, new_id);
            stat.setInt(2, zero);
            stat.setInt(3, zero);
            st = stat.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new_id;
    }

    public static int buy_pet(int id_pet_sale, int id_pet, int number,int pet_price) {
        int st = 0;
        Pets pet = new Pets();
        try {
            String sql = "SELECT stoc FROM animale WHERE id_animal=?;";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setInt(1, id_pet);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                pet.setStock(rs.getInt(1));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(pet.getStock()>=number) {
            try {
                String sql = "UPDATE animale SET stoc=? WHERE " +
                        "id_animal=? ;";
                PreparedStatement stat1;
                Connection con = dbConnectionInfo.connect();
                stat1 = con.prepareStatement(sql);
                int new_pet_stock=pet.getStock()-number;
                stat1.setInt(1, new_pet_stock);
                stat1.setInt(2, id_pet);
                st = stat1.executeUpdate();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int st0=0;
            try {
                int current_number=0;
                String sql = "SELECT numar_animale FROM animale_has_vanzare WHERE (id_animal=? AND id_vanzare=?) ;";
                PreparedStatement stat1;
                Connection con = dbConnectionInfo.connect();
                stat1 = con.prepareStatement(sql);
                stat1.setInt(1,id_pet);
                stat1.setInt(2,id_pet_sale);
                ResultSet rs0=stat1.executeQuery();
                if (rs0.next()){
                    current_number=rs0.getInt(1);
                    String sql1 = "UPDATE animale_has_vanzare SET numar_animale=? WHERE (id_animal=? AND id_vanzare=?) ;";
                    PreparedStatement stat2;
                    stat2 = con.prepareStatement(sql1);
                    int new_pet_number=current_number+number;
                    stat2.setInt(1, new_pet_number);
                    stat2.setInt(2, id_pet);
                    stat2.setInt(3, id_pet_sale);
                    st0 = stat2.executeUpdate();
                    con.close();
                }
                else {
                    try {
                        String sql1 = "INSERT INTO animale_has_vanzare(id_animal,id_vanzare,numar_animale) " +
                                "VALUES(?,?,?);";
                        Connection con1 = dbConnectionInfo.connect();
                        PreparedStatement stat;
                        stat = con1.prepareStatement(sql1);
                        stat.setInt(1, id_pet);
                        stat.setInt(2, id_pet_sale);
                        stat.setInt(3, number);
                        st = stat.executeUpdate();
                        con1.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                int current_pet_number = 0;
                int current_sale_tax = 0;
                int current_pet_cost = 0;
                String sql1 = "SELECT * FROM vanzare_animale WHERE id_vanzare_animal=?;";
                Connection con = dbConnectionInfo.connect();
                PreparedStatement stat;
                stat = con.prepareStatement(sql1);
                stat.setInt(1, id_pet_sale);
                ResultSet rs = stat.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    current_pet_number = rs.getInt(2);
                    current_sale_tax = rs.getInt(3);
                    current_pet_cost = rs.getInt(4);
                }
                try {
                    String sql2 = "UPDATE vanzare_animale SET numar_total_animale=?,taxa_vanzare=?,cost_animale=? WHERE " +
                            "id_vanzare_animal=? ;";
                    PreparedStatement stat1;
                    stat1 = con.prepareStatement(sql2);
                    int new_pet_number = current_pet_number + number;
                    int new_tax = current_sale_tax + 10 * number;
                    int new_pet_cost = current_pet_cost + number * pet_price;
                    stat1.setInt(1, new_pet_number);
                    stat1.setInt(2, new_tax);
                    stat1.setInt(3, new_pet_cost);
                    stat1.setInt(4, id_pet_sale);
                    st = stat1.executeUpdate();
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            st=0;
        }
        return st;
    }
    public static int buy_accessory(int id_accessory_sale, int id_accessory, int number,int accessory_price) {
        int st = 0;
        Accessories accessory = new Accessories();
        try {
            String sql = "SELECT stoc FROM accesorii WHERE id_accesoriu=?;";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setInt(1, id_accessory);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                accessory.setStock(rs.getInt(1));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(accessory.getStock()>=number) {
            try {
                String sql = "UPDATE accesorii SET stoc=? WHERE " +
                        "id_accesoriu=? ;";
                PreparedStatement stat1;
                Connection con = dbConnectionInfo.connect();
                stat1 = con.prepareStatement(sql);
                int new_accessory_stock=accessory.getStock()-number;
                stat1.setInt(1, new_accessory_stock);
                stat1.setInt(2, id_accessory);
                st = stat1.executeUpdate();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                int current_number=0;
                String sql = "SELECT numar_accesorii FROM accesorii_has_vanzare WHERE (id_accesoriu=? AND id_vanzare_accesorii=?) ;";
                PreparedStatement stat1;
                Connection con = dbConnectionInfo.connect();
                stat1 = con.prepareStatement(sql);
                stat1.setInt(1,id_accessory);
                stat1.setInt(2,id_accessory_sale);
                ResultSet rs0=stat1.executeQuery();
                if (rs0.next()){
                    current_number=rs0.getInt(1);
                    String sql1 = "UPDATE accesorii_has_vanzare SET numar_accesorii=? WHERE (id_accesoriu=? AND id_vanzare_accesorii=?) ;";
                    PreparedStatement stat2;
                    stat2 = con.prepareStatement(sql1);
                    int new_acc_number=current_number+number;
                    stat2.setInt(1, new_acc_number);
                    stat2.setInt(2, id_accessory);
                    stat2.setInt(3, id_accessory_sale);
                    int st0 = stat2.executeUpdate();
                    con.close();
                }
                else {
                    try {
                        String sql1 = "INSERT INTO accesorii_has_vanzare(id_accesoriu,id_vanzare_accesorii,numar_accesorii) " +
                                "VALUES(?,?,?);";
                        Connection con1 = dbConnectionInfo.connect();
                        PreparedStatement stat;
                        stat = con1.prepareStatement(sql1);
                        stat.setInt(1, id_accessory);
                        stat.setInt(2, id_accessory_sale);
                        stat.setInt(3, number);
                        st = stat.executeUpdate();
                        con1.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                int current_accessory_number = 0;
                int current_accessory_cost = 0;
                String sql1 = "SELECT * FROM vanzare_accesorii WHERE id_vanzare_accesorii=?;";
                Connection con = dbConnectionInfo.connect();
                PreparedStatement stat;
                stat = con.prepareStatement(sql1);
                stat.setInt(1, id_accessory_sale);
                ResultSet rs = stat.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    current_accessory_number = rs.getInt(2);
                    current_accessory_cost = rs.getInt(3);
                }
                try {
                    String sql2 = "UPDATE vanzare_accesorii SET numar_total_accesorii=?,cost_accesorii=? WHERE " +
                            "id_vanzare_accesorii=? ;";
                    PreparedStatement stat1;
                    stat1 = con.prepareStatement(sql2);
                    int new_accessory_number = current_accessory_number + number;
                    int new_accessory_cost = current_accessory_cost + number * accessory_price;
                    stat1.setInt(1, new_accessory_number);
                    stat1.setInt(2, new_accessory_cost);
                    stat1.setInt(3, id_accessory_sale);
                    st = stat1.executeUpdate();
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            st=0;
        }
        return st;
    }
    public static int doBill(int id_client,int id_pet_sale,int id_accessory_sale){
        ZoneId zonedId = ZoneId.of( "America/Montreal" );
        LocalDate today_ld = LocalDate.now( zonedId );
        String today = today_ld.toString();
        int tax=0;
        int pet_total=0;
        int accessory_total=0;
        int st=0;
        try {
            String sql = "SELECT taxa_vanzare,cost_animale FROM vanzare_animale WHERE id_vanzare_animal=?;";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setInt(1, id_pet_sale);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                tax=rs.getInt(1);
                pet_total=rs.getInt(2);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String sql = "SELECT cost_accesorii FROM vanzare_accesorii WHERE id_vanzare_accesorii=?;";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;
            stat = con.prepareStatement(sql);
            stat.setInt(1, id_accessory_sale);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                accessory_total=rs.getInt(1);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int new_id = ThreadLocalRandom.current().nextInt(13000, 14000 + 1);
        int new_aviz = 980000 + ThreadLocalRandom.current().nextInt(100, 9999 + 1);
        try {
            String sql1 = "INSERT INTO facturi(id_factura,id_vanzare_animal,id_vanzare_accesorii,id_client,data_vanzare,numar_aviz,total) " +
                    "VALUES(?,?,?,?,?,?,?);";
            Connection con = dbConnectionInfo.connect();
            PreparedStatement stat;

            stat = con.prepareStatement(sql1);
            stat.setInt(1, new_id);
            if(id_pet_sale==0) {
                String no_pet_sale=null;
                stat.setString(2, no_pet_sale);
            }else{
                stat.setInt(2,id_pet_sale);
            }
            if(id_accessory_sale==0) {
                String no_accessory_sale=null;
                stat.setString(3, no_accessory_sale);
            }else{
                stat.setInt(3,id_accessory_sale);
            }
            stat.setInt(4, id_client);
            stat.setString(5,today);
            stat.setInt(6,new_aviz);
            int Total=pet_total+accessory_total+tax;
            stat.setInt(7,Total);
            st = stat.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }
}