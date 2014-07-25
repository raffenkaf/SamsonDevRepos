package taskPackage;

import java.sql.Connection;
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

public class SubjectCreatePage extends AbstractPage{
	
	static boolean warningLableVisible = false;
	public TextField disciplineTextField;
	public Label underTitleLbl; 
	public Button createSubjectBtn;

	SubjectCreatePage(short accessLevel) {
		super(accessLevel);
	}

	@Override
	public void start(Stage createSubjectPageStage) throws Exception {
		createSubjectPageStage.show();
		createSubjectPageStage.setTitle("Students list");
		createSubjectPageStage.setResizable(false);
		
		AnchorPane anchorPane=new AnchorPane();
		
		Group root = new Group();
		Scene scene = new Scene(root,800,600,Color.GRAY);
		
		createSubjectPageStage.setScene(scene);
		
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
				createSubjectPageStage.close();
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
				createSubjectPageStage.close();
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
				createSubjectPageStage.close();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							new SubjectsGeneralPage(accessLevel).start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		
		Label warningLbl;
		warningLbl = new Label();
		warningLbl.setText("Должно не быть пустых полей");
		warningLbl.setStyle("-fx-font: 10pt Arial;");
		warningLbl.setVisible(warningLableVisible);
		
		Label disciplineLbl;
		disciplineLbl = new Label();
		disciplineLbl.setText("Название предмета");
		disciplineLbl.setStyle("-fx-font: 10pt Arial;");
		
		underTitleLbl = new Label();
		underTitleLbl.setText("Для создания дисциплины заполните пустое поле и нажмите создать");
		underTitleLbl.setStyle("-fx-font: bold 12pt Arial;");
		
		createSubjectBtn = new Button();
		createSubjectBtn.setText("Создать");
		createSubjectBtn.setStyle("-fx-font: bold 10pt Arial;" );
		createSubjectBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					boolean emptyFieldsIsNotExist=true;
					if (disciplineTextField.getText().equals("")) {
						emptyFieldsIsNotExist = false;
					}
					
					if (!emptyFieldsIsNotExist) {
						createSubjectPageStage.close();
						Platform.runLater(new Runnable() {
						
							@Override
							public void run() {
								try {
									warningLableVisible = true;
									new SubjectCreatePage(accessLevel).start(new Stage());
								} catch (Exception e) {
									e.printStackTrace();
								};
							}
						});						
					}else{
						warningLableVisible = false;
						updateSubjectsTable();
						createSubjectPageStage.close();
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									new SubjectsGeneralPage(accessLevel).start(new Stage());
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
		
		disciplineTextField = new TextField();
		disciplineTextField.setPrefSize(200, 15);
		
		fillModifyingFields();
		
		anchorPane.getChildren().addAll(titleLable, logOutBtn, goToMainPageBtn, backPageBtn,
				underTitleLbl,createSubjectBtn,disciplineTextField, warningLbl,disciplineLbl );
						
		AnchorPane.setTopAnchor(titleLable, 5.0);
		AnchorPane.setLeftAnchor(titleLable, 120.0);//
		AnchorPane.setTopAnchor(logOutBtn, 10.0);
		AnchorPane.setLeftAnchor(logOutBtn, 670.0);//
		AnchorPane.setTopAnchor(goToMainPageBtn, 10.0);
		AnchorPane.setLeftAnchor(goToMainPageBtn, 40.0);//
		AnchorPane.setTopAnchor(backPageBtn, 10.0);
		AnchorPane.setLeftAnchor(backPageBtn, 0.0);//
		AnchorPane.setTopAnchor(underTitleLbl, 50.0);
		AnchorPane.setLeftAnchor(underTitleLbl, 130.0);//
		
		AnchorPane.setTopAnchor(disciplineLbl, 85.0);
		AnchorPane.setLeftAnchor(disciplineLbl, 110.0);//
		
		AnchorPane.setTopAnchor(disciplineTextField, 80.0);
		AnchorPane.setLeftAnchor(disciplineTextField, 250.0);//
		AnchorPane.setTopAnchor(createSubjectBtn, 110.0);
		AnchorPane.setLeftAnchor(createSubjectBtn, 250.0);//
		AnchorPane.setTopAnchor(warningLbl, 140.0);
		AnchorPane.setLeftAnchor(warningLbl, 250.0);//
		
		root.getChildren().add(anchorPane);
		
	}

	public void fillModifyingFields() {
		//NOP		
	}

	protected void updateSubjectsTable() throws DBException{
		Connection conn= getConnection();
		
		Statement stmt=null;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			
			sql = "insert into disciplines(discipline) values ('" +disciplineTextField.getText() +"');"; 
			stmt.execute(sql);
			
			conn.commit();
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

}
