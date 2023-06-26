package cn.itbaizhan.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.ConnectionString;
/*数据库连接*/
public class ConnectionDao {
	public static MongoClient getConnection() {
		MongoClient mongoClient = null;
		try {
			ConnectionString connString = new ConnectionString("mongodb+srv://howard41907:<password>@cluster0.vzqajgx.mongodb.net/");
			mongoClient = MongoClients.create(connString);
			System.out.println("连接成功");
			return mongoClient;
		} catch (Exception ex) {
			System.out.println("连接失败" + ex);
			return null;
		}
	}
}
