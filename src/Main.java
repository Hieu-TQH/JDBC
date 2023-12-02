import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection conn = Context.getConnection();
        Statement stmt = conn.createStatement();

        String createTableQuery = "CREATE TABLE IF NOT EXISTS produce (" +
                "id INT PRIMARY KEY," +
                "name VARCHAR(50) NOT NULL," +
                "price DECIMAL(10,2) NOT NULL," +
                "quantity INT NOT NULL," +
                "origin VARCHAR(50) NOT NULL" +
                ")";
        stmt.executeUpdate(createTableQuery);

        int choose;
        while (true){
            System.out.println("1.INSERT INTO\n2.SELECT * FROM produce\n3.CALL procedure increase_price\n4.CALL FUNCTION total_value" +
                    "\n5.UPDATE quantity (nếu nhỏ hơn 10)\n6. Transaction\n7.Exit");
            choose = scanner.nextInt(); scanner.nextLine();
            switch (choose){
                case 1:
                    //Sử dụng PrepareStatement để insert
                    PrepareStmt.addNewProduct(conn);
                    break;
                case 2:
                    //ResultSet để select
                    Queries.getAllProduces(conn);
                    break;
                case 3:
                    //CallAbleStatement để gọi procedure
                    System.out.println("List of produce");
                    Queries.getAllProductNames(conn);
                    String productName;
                    double amount;
                    System.out.print("tên sản phẩm muốn tăng giá: ");
                    productName = scanner.nextLine();
                    System.out.print("tăng thêm: ");
                    amount = scanner.nextDouble(); scanner.nextLine();
                    CallAble.increasePrice(conn,productName,amount);
                    System.out.println("Complete.");
                    break;
                case 4:
                    System.out.println("total of value: "+CallAble.getTotalValue(conn));
                    break;
                case 5:
                    Queries.updateQuantityIfLow(conn);
                    System.out.println("Complete.");
                    break;
                case 6:
                    Transaction.performTransaction(conn,scanner);
                    break;
                case 7:
                    //close
                    scanner.close();
                    stmt.close();
                    conn.close();
                    CallAble.close();
                    Queries.close();
                    PrepareStmt.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Không hợp lệ.");
                    break;
            }

        }


    }
}
