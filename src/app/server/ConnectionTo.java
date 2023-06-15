package app.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*資料庫連線*/
public class ConnectionTo {
	public static Connection getConnection() {
		Connection conn=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.print("");
		}catch(ClassNotFoundException ex){
			System.out.println("載入驅動失敗");
		}
		try{
			String url="jdbc:mysql://localhost:3306/test?user=root&password=t1234";
		    conn=DriverManager.getConnection(url);
			System.out.print("連線成功");
			return conn;
		}catch(SQLException ex){
			System.out.println("連線失敗"+ex);
			return null;
		}

	}

}
