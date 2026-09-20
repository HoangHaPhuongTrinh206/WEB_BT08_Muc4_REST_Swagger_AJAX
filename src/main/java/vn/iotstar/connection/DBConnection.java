package vn.iotstar.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private final String serverName = "localhost";
    private final String instance = "SQLEXPRESS"; // Tên instance SQL Server đang chạy
    private final String dbName = "Lap_Trinh_WEB"; // Đúng tên database đang dùng
    private final String userID = "sa"; // Tài khoản đăng nhập SQL của bạn
    private final String password = "PTrinh@20061999"; // Mật khẩu SQL hiện tại của bạn

    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + serverName + "\\" + instance
                + ";databaseName=" + dbName
                + ";encrypt=false;trustServerCertificate=true";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }
}