package guiprojectpacman;
import java.sql.DriverManager;
import java.sql.Connection;

public class Config {    
    public Connection mysqlconfig;
    public String url = "jdbc:mysql://localhost:3306/pacman";
    public String id = "root";
    public String password = "";
    public Config(){
        try{
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            this.mysqlconfig=DriverManager.getConnection(url, id, password);
        }catch(Exception e){
            System.err.println("Koneksi ke database gagal ");
            System.out.println(e.getMessage());
        }
    }
}
