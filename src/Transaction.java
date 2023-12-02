import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Transaction {
    public static void performTransaction(Connection conn, Scanner scanner) {
        try {
            conn.setAutoCommit(false);

            // INSERT
            String perform;
            System.out.print("Thêm sản phẩm mới (nhấn n để bỏ qua, nhấn bất kỳ để tiếp tục). "); perform = scanner.nextLine();
            if(!perform.equals("n"))
                PrepareStmt.addNewProduct(conn);

            // UPDATE - Cập nhật số lượng sản phẩm có số lượng nhỏ hơn chỉ định
            String updateQuery = "UPDATE produce SET quantity = quantity + ? WHERE quantity < ?";
            int lowQuantity, increase;
            System.out.print("thêm số lượng với những sản phẩm còn dưới (0 để bỏ qua): "); lowQuantity=scanner.nextInt(); scanner.nextLine();
            if(lowQuantity!=0){
                System.out.print("số lượng thêm: "); increase = scanner.nextInt(); scanner.nextLine();
                PreparedStatement pStmt = conn.prepareStatement(updateQuery);
                pStmt.setInt(1, increase);
                pStmt.setInt(2, lowQuantity);
                pStmt.executeUpdate();
                System.out.println("updated");
                pStmt.close();
            }

            // DELETE theo tên
            String deleteQuery = "DELETE FROM produce WHERE name =?";
            String name;
            System.out.print("nhập nên sản phẩm muốn xóa (ấn 0 để bỏ qua): ");
            name = scanner.nextLine();
            if(!name.equals("0")){
                PreparedStatement pStmt = conn.prepareStatement(deleteQuery);
                pStmt.setString(1,name);
                pStmt.executeUpdate();
                System.out.println("Deleted.");
                pStmt.close();
            }

            // Commit transaction nếu không có lỗi
            conn.commit();
            System.out.println("Transaction thành công, đã commit dữ liệu.");
        } catch (SQLException e) {
            // Rollback transaction nếu có lỗi
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Transaction không thành công, đã rollback dữ liệu.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); 
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
