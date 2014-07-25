package taskPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage extends Application implements Runnable{
	// MySQL init
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost/studentsdb";
		static final String USER = "samson";
		static final String PASS = "root";
		
		private Connection getConnection() throws DBException {
			try {
				return DriverManager.getConnection(DB_URL,USER,PASS);
			} catch (SQLException e) {
				throw new DBException("Can't create connection", e);
			}
		}
	//MySQ init ended
	
		Stage primaryStage;
		
		final Text actiontarget = new Text();
		
		
	public static void main(String[] args) {
		launch("123");
	}
		
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.show();
		primaryStage.setTitle("Login");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
        grid.add(actiontarget, 1, 6);

		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		
		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		        actiontarget.setFill(Color.FIREBRICK);
		        actiontarget.setText("Sign in button pressed");
		        try {
					checkUserAndPassword(userTextField.getText(),pwBox.getText(),primaryStage);
				} catch (DBException e1) {
					e1.printStackTrace();
				}
		    }
		});
	}
	
	protected void checkUserAndPassword(String user, String password, Stage primaryStage) throws DBException{
		
		Connection conn= getConnection();
		
		Statement stmt=null;
		
		try {
			
			String passFromDB = "aweeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
			byte accessLevelFromDB=-1;
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT password,access_level FROM users where user_name ="+"'"+user+"'";
		    ResultSet rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	accessLevelFromDB = rs.getByte("access_level");
		       	passFromDB = rs.getString("password");
		    }
		    
		    conn.commit();
		    rs.close();
		    stmt.close();
		    conn.close();
		    
		    if (password.equals(passFromDB)) {
		    	primaryStage.close();
				runMainWindow(accessLevelFromDB);
			}else{
				actiontarget.setText("Pls insert the write user name and password");
			}
		    
		} catch (Exception e) {// Nado dodelat catch
			e.printStackTrace();
		} finally{
			try{
				if (stmt!=null) {
					stmt.close();
				}
			}catch(Exception e){
				//nop
			}
			
			try{
				if (conn!=null) {
					conn.close();
				}
			}catch(Exception e){
				//nop
			}
		}
	}

	private void runMainWindow(byte accessLevel) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					new TitlePage(accessLevel).start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public void run() {
		launch("123");		
	}
}
