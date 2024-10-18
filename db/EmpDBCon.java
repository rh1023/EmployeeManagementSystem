//EmpDBCon.java
package crud.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EmpDBCon {
    // データベース接続情報
    private static final String URL = "jdbc:mysql://localhost:3306/employee_manage?serverTimezone=Asia/Tokyo&useSSL=false"; // データベースURL
    private static final String USER = "root"; // データベース接続用ユーザー名
    private static final String PASSWORD = "pass"; // データベース接続用パスワード
    
    // データベース接続を取得するメソッド
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // データベースに接続を試みる
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // 接続に失敗した場合はエラーメッセージを表示
            e.printStackTrace();
        }
        return connection; // 成功時は接続を返し、失敗時はnullを返す
    }
}
