import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {
    private static Statement stmt;
    private static ResultSet rs;
    public static void getAllProductNames(Connection conn) {
        try {
            stmt = conn.createStatement();
            String selectQuery = "SELECT name FROM produce";
            rs = stmt.executeQuery(selectQuery);

            while (rs.next()) {
                String productName = rs.getString("name");
                System.out.println("Product name: " + productName);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllProduces(Connection conn){
        try{
            stmt = conn.createStatement();
            String sql = "SELECT * FROM produce";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String origin = rs.getString("origin");

                System.out.println("ID: " + id + ", Name: " + name + ", Price: " + price + ", Quantity: " + quantity + ", Origin: " + origin);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateQuantityIfLow(Connection conn) {
        try {
            String updateQuery = "UPDATE produce SET quantity = quantity + 10 WHERE quantity < 10";
            stmt = conn.createStatement();
            int rowsAffected = stmt.executeUpdate(updateQuery);

            if (rowsAffected > 0) {
                System.out.println("Các sản phẩm có số lượng nhỏ hơn 10 đã tăng thêm 10");
            } else {
                System.out.println("Không có sản phẩm nào có số lượng nhỏ hơn 10");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() throws SQLException{
        if(rs == null){
            return;
        }
        rs.close();
        stmt.close();
    }
}
