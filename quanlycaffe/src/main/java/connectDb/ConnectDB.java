package connectDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectDB {
	private static ConnectDB instance;
	private Connection connection;               
	public ConnectDB() {
		String url = "jdbc:sqlserver://localhost:1433;DatabaseName=CoffeShopDatabase;trustServerCertificate=true";
		try {
			connection = DriverManager.getConnection(url,"sa","1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		
		return connection;
	}
	public static ConnectDB getInstance() {
		if (instance == null) {
			synchronized (ConnectDB.class) {
				if (instance ==null) {
					instance = new ConnectDB();
				}
			}	
		}
		return instance;
	}
	public void closeConnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

