import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PrepareStmt {
    private static PreparedStatement pStmt;
    private static Scanner scanner = new Scanner(System.in);
    public static void addNewProduct(Connection conn) {
        try {
            String insertQuery = "INSERT INTO produce (name, price, quantity, origin) VALUES (?, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertQuery);

            String name;
            double price;
            int quantity;
            String origin;
            System.out.println("Thông tin sản phẩm thêm vào");
            System.out.print("name: "); name = scanner.nextLine();
            System.out.print("price: "); price= scanner.nextDouble(); scanner.nextLine();
            System.out.print("quantity: "); quantity=scanner.nextInt(); scanner.nextLine();
            System.out.print("origin: "); origin=scanner.nextLine();

            pStmt.setString(1, name);
            pStmt.setDouble(2, price);
            pStmt.setInt(3, quantity);
            pStmt.setString(4, origin);

            pStmt.executeUpdate();

            System.out.println("Đã thêm sản phẩm mới vào bảng produce.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void close() throws SQLException{
        if(pStmt==null){
            return;
        }
        scanner.close();
        pStmt.close();
    }

}
