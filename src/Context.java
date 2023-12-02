import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Context {
    private static final String dbURL = "jdbc:mysql://localhost:3306/mydb";
    private static final String userName = "root";
    private static final String pass = "mkdaythim";

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(dbURL, userName, pass);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
