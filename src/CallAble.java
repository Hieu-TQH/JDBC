import java.sql.*;

public class CallAble {
    private static CallableStatement cs;
    public static void increasePrice(Connection conn,String productName, double amount) {
        try{
            // Tạo CallableStatement để gọi procedure increase_price
            String sql = "{CALL increase_price(?, ?)}";
            cs = conn.prepareCall(sql);

            // Thiết lập giá trị cho tham số đầu vào và INOUT
            cs.setString(1, productName);
            cs.setDouble(2, amount);

            // Thực thi stored procedure
            cs.execute();

            // Lấy giá trị mới của p_amount
            double newAmount = cs.getDouble(2);
            System.out.println("New price amount for " + productName + ": " + newAmount);

            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getTotalValue(Connection conn) {
        double totalValue = 0.0;

        try{
            // Gọi function total_value()
            String callFunction = "{ ? = call total_value() }";
            cs = conn.prepareCall(callFunction);

            // Thiết lập kiểu dữ liệu trả về
            cs.registerOutParameter(1, Types.DECIMAL);

            cs.execute();
            totalValue = cs.getDouble(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalValue;
    }

    public static void close() throws SQLException{
        if(cs==null){
            return;
        }
        cs.close();
    }

}
