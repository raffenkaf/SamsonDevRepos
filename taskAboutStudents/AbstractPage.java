package taskPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

abstract public class AbstractPage extends Application implements Runnable{
	// MySQL init
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/studentsdb";
	static final String USER = "samson";
	static final String PASS = "root";
		
	public Connection getConnection() throws DBException {
		try {
			return DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (SQLException e) {
			throw new DBException("Can't create connection", e);
		}
	}
	//MySQL init ended
	
	final short accessLevel;

	AbstractPage(short accessLevel){
		this.accessLevel = accessLevel;
	}
	
	public short getAccessLevel() {
		return accessLevel;
	}
	
	@Override
	public void run() {
		launch("123");
	}

	
}
