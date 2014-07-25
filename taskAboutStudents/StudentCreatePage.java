package taskPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StudentCreatePage extends AbstractPage{
	static boolean warningLableVisible = false;
	public TextField surnameTextField;
	public TextField nameTextField;
	public TextField studentGroupTextField;
	public TextField entryDateTextField;
	public Label underTitleLbl; 
	public Button createStudentBtn;
		
	StudentCreatePage(short accessLevel) {
		super(accessLevel);
	}

	@Override
	public void start(Stage createStudentPageStage) throws Exception {
		createStudentPageStage.show();
		createStudentPageStage.setTitle("Students list");
		createStudentPageStage.setResizable(false);
		
		AnchorPane anchorPane=new AnchorPane();
		
		Group root = new Group();
		Scene scene = new Scene(root,800,600,Color.GRAY);
		
		createStudentPageStage.setScene(scene);
		
		Button titleLable;
		titleLable = new Button();
		titleLable.setText("Система управления студентами и их успеваемостью");
		titleLable.setStyle("-fx-font: bold 12pt Arial;-fx-background-color: white;-fx-border-width: 1px;" );
		titleLable.setPrefSize(550, 10);
		
		Button logOutBtn;
		logOutBtn = new Button();
		logOutBtn.setText("Logout");
		logOutBtn.setUnderline(true);
		logOutBtn.setStyle("-fx-font: bold 8pt Arial;-fx-background-color: gray; "
				+ "-fx-border-width: 0px; -fx-text-fill: blue;" );
		logOutBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				createStudentPageStage.close();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							new LoginPage().start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		
		Button goToMainPageBtn;
		goToMainPageBtn = new Button();
		goToMainPageBtn.setText("На главную");
		goToMainPageBtn.setUnderline(true);
		goToMainPageBtn.setStyle("-fx-font: bold 8pt Arial;-fx-background-color: gray; "
				+ "-fx-border-width: 0px; -fx-text-fill: blue;" );
		goToMainPageBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				createStudentPageStage.close();
				warningLableVisible = false;
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
		});	
		
		Button backPageBtn;
		backPageBtn = new Button();
		backPageBtn.setText("Назад");
		backPageBtn.setUnderline(true);
		backPageBtn.setStyle("-fx-font: bold 8pt Arial;-fx-background-color: gray; "
				+ "-fx-border-width: 0px; -fx-text-fill: blue;" );
		backPageBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				warningLableVisible = false;
				createStudentPageStage.close();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							new StudentsGeneralPage(accessLevel).start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		
		Label surnameLbl;
		surnameLbl = new Label();
		surnameLbl.setText("Фамилия");
		surnameLbl.setStyle("-fx-font:10pt Arial;");
		
		Label nameLbl;
		nameLbl = new Label();
		nameLbl.setText("Имя");
		nameLbl.setStyle("-fx-font: 10pt Arial;");
		
		Label studentGroupLbl;
		studentGroupLbl = new Label();
		studentGroupLbl.setText("Группа");
		studentGroupLbl.setStyle("-fx-font: 10pt Arial;");
		
		Label entryDateLbl;
		entryDateLbl = new Label();
		entryDateLbl.setText("Дата поступления");
		entryDateLbl.setStyle("-fx-font: 10pt Arial;");
		
		Label warningLbl;
		warningLbl = new Label();
		warningLbl.setText("Должно не быть пустых полей");
		warningLbl.setStyle("-fx-font: 10pt Arial;-fx-text-fill:red;");
		warningLbl.setVisible(warningLableVisible);
				
		surnameTextField = new TextField();
		surnameTextField.setPrefSize(200, 15);
		
		nameTextField = new TextField();
		nameTextField.setPrefSize(200, 15);
		
		studentGroupTextField = new TextField();
		studentGroupTextField.setPrefSize(200, 15);
		
		entryDateTextField = new TextField();
		entryDateTextField.setPrefSize(200, 15);
		
		underTitleLbl = new Label();
		underTitleLbl.setText("Для создания введите значения в пустые поля и нажмите создать");
		underTitleLbl.setStyle("-fx-font: bold 12pt Arial;");
		
		createStudentBtn = new Button();
		createStudentBtn.setText("Создать");
		createStudentBtn.setStyle("-fx-font: bold 10pt Arial;" );
		createStudentBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					boolean emptyFieldsIsNotExist=true;
					if (surnameTextField.getText().equals("") || nameTextField.getText().equals("") || 
							entryDateTextField.getText().equals("") || studentGroupTextField.getText().equals("")) {
						emptyFieldsIsNotExist = false;
					}
					
					if (!emptyFieldsIsNotExist) {
						createStudentPageStage.close();
						Platform.runLater(new Runnable() {
						
							@Override
							public void run() {
								try {
									warningLableVisible = true;
									new StudentCreatePage(accessLevel).start(new Stage());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								};
							}
						});						
					}else{
						warningLableVisible = false;
						updateStudentAndTermsTables();
						createStudentPageStage.close();
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									new StudentsGeneralPage(accessLevel).start(new Stage());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				} catch (DBException e) {
					e.printStackTrace();
				}				
			}
		});
		fillModifyingFields();
		anchorPane.getChildren().addAll(titleLable, logOutBtn,goToMainPageBtn,backPageBtn,
				underTitleLbl,createStudentBtn,surnameTextField,nameTextField,
					studentGroupTextField,entryDateTextField, 
						warningLbl,	surnameLbl, nameLbl, studentGroupLbl, entryDateLbl);
						
		AnchorPane.setTopAnchor(titleLable, 5.0);
		AnchorPane.setLeftAnchor(titleLable, 120.0);//
		AnchorPane.setTopAnchor(logOutBtn, 10.0);
		AnchorPane.setLeftAnchor(logOutBtn, 670.0);//
		AnchorPane.setTopAnchor(goToMainPageBtn, 10.0);
		AnchorPane.setLeftAnchor(goToMainPageBtn, 40.0);//
		AnchorPane.setTopAnchor(backPageBtn, 10.0);
		AnchorPane.setLeftAnchor(backPageBtn, 0.0);//
		
		AnchorPane.setTopAnchor(surnameLbl, 105.0);
		AnchorPane.setLeftAnchor(surnameLbl, 170.0);//
		AnchorPane.setTopAnchor(nameLbl, 155.0);
		AnchorPane.setLeftAnchor(nameLbl, 199.0);//
		AnchorPane.setTopAnchor(studentGroupLbl, 205.0);
		AnchorPane.setLeftAnchor(studentGroupLbl, 182.0);//
		AnchorPane.setTopAnchor(entryDateLbl, 255.0);
		AnchorPane.setLeftAnchor(entryDateLbl, 115.0);//
		
		AnchorPane.setTopAnchor(underTitleLbl, 50.0);
		AnchorPane.setLeftAnchor(underTitleLbl, 130.0);//
		AnchorPane.setTopAnchor(surnameTextField, 100.0);
		AnchorPane.setLeftAnchor(surnameTextField, 250.0);//
		AnchorPane.setTopAnchor(nameTextField, 150.0);
		AnchorPane.setLeftAnchor(nameTextField, 251.0);//
		AnchorPane.setTopAnchor(studentGroupTextField, 200.0);
		AnchorPane.setLeftAnchor(studentGroupTextField, 250.0);//
		AnchorPane.setTopAnchor(entryDateTextField, 250.0);
		AnchorPane.setLeftAnchor(entryDateTextField, 250.0);//
		AnchorPane.setTopAnchor(createStudentBtn, 290.0);
		AnchorPane.setLeftAnchor(createStudentBtn, 250.0);//
		AnchorPane.setTopAnchor(warningLbl, 330.0);
		AnchorPane.setLeftAnchor(warningLbl, 250.0);//
		
		root.getChildren().add(anchorPane);
		
	}
	
	protected void updateStudentAndTermsTables() throws DBException {
		Connection conn= getConnection();
		
		Statement stmt=null;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			
			sql = "insert into students(name,surname,students_group,entry_date) values ('" +
					nameTextField.getText() + "' , '"+ surnameTextField.getText()+"' , '" + 
						studentGroupTextField.getText() + "' , '" + entryDateTextField.getText() +"');"; 
			stmt.execute(sql);
			
			sql = "select max(id) from students; ";
			ResultSet rs =stmt.executeQuery(sql);
			
			if (rs.next()) {
				int studentID = rs.getInt("max(id)");
				sql = "alter table terms add student_"+studentID+" tinyint default 4;";
				stmt.execute(sql);	
			}
						
			conn.commit();
			
			rs.close();
			stmt.close();
		    conn.close();
		    			    
		    		    
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
	
	public void fillModifyingFields(){
		//NOP
	}
			
}
