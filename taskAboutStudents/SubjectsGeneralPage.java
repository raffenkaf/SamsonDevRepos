package taskPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SubjectsGeneralPage extends AbstractPage{

	SubjectsGeneralPage(short accessLevel) {
		super(accessLevel);
	}

	@Override
	public void start(Stage subjectGeneralPageStage) throws Exception {
		subjectGeneralPageStage.show();
		subjectGeneralPageStage.setTitle("Students list");
		subjectGeneralPageStage.setResizable(false);
		
		Group root = new Group();
		Scene scene = new Scene(root,800,600,Color.GRAY);
		
		subjectGeneralPageStage.setScene(scene);
		
		ObservableList<Subject> subjects = FXCollections.observableArrayList();
		fillTheTable(subjects, root);
		
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
				subjectGeneralPageStage.close();
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
				subjectGeneralPageStage.close();
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
		
		Button createSubjectBtn;
		createSubjectBtn = new Button();
		createSubjectBtn.setPrefWidth(200);
		createSubjectBtn.setText("Создать дисциплину");
		createSubjectBtn.setStyle("-fx-font: bold 8pt Arial;" );
		createSubjectBtn.setOnAction(new EventHandler<ActionEvent>() {

			
			@Override
			public void handle(ActionEvent event) {
				subjectGeneralPageStage.close();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							new SubjectCreatePage(accessLevel).start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		
		Button subjectModifyingBtn;
		subjectModifyingBtn = new Button();
		subjectModifyingBtn.setPrefWidth(200);
		subjectModifyingBtn.setText("Модифицировать дисциплину");
		subjectModifyingBtn.setStyle("-fx-font: bold 8pt Arial;" );
		subjectModifyingBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				for (Subject iterableSubject : subjects) {
					if(iterableSubject.subjectChB.isSelected()){
						try {
							subjectGeneralPageStage.close();
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									try {
										new SubjectModifyingPage(accessLevel, iterableSubject).start(new Stage());
									}
									catch (Exception e) {
										e.printStackTrace();
									}
																	
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		});
		
		Button deleteSubjectBtn;
		deleteSubjectBtn = new Button();
		deleteSubjectBtn.setPrefWidth(200);
		deleteSubjectBtn.setText("Удалить дисциплину");
		deleteSubjectBtn.setStyle("-fx-font: bold 8pt Arial;" );
		deleteSubjectBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				List<Subject> deleteList = new ArrayList<>(); 
				boolean flag = false;
				for (Subject iterableSubject : subjects) {
					if(iterableSubject.subjectChB.isSelected()){
						flag=true;
						deleteList.add(iterableSubject);
					}
				}
				
				if (flag) {
					try {
						deleteSubjects(deleteList);
						subjectGeneralPageStage.close();
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
					} catch (DBException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		Label underTitleLbl;
		underTitleLbl = new Label();
		underTitleLbl.setText("Список дисциплин");
		underTitleLbl.setStyle("-fx-font: bold 12pt Arial;");
				
		AnchorPane anchorPane=new AnchorPane();
		if (accessLevel==1) {
			anchorPane.getChildren().addAll(titleLable, logOutBtn,goToMainPageBtn, 
					underTitleLbl, createSubjectBtn, deleteSubjectBtn, subjectModifyingBtn);
		}else if (accessLevel==0) {
			anchorPane.getChildren().addAll(titleLable, logOutBtn,goToMainPageBtn);
		}
				
		AnchorPane.setTopAnchor(titleLable, 5.0);
		AnchorPane.setLeftAnchor(titleLable, 120.0);//
		AnchorPane.setTopAnchor(logOutBtn, 10.0);
		AnchorPane.setLeftAnchor(logOutBtn, 670.0);//
		AnchorPane.setTopAnchor(goToMainPageBtn, 10.0);
		AnchorPane.setLeftAnchor(goToMainPageBtn, 40.0);//
		
		AnchorPane.setTopAnchor(underTitleLbl, 70.0);
		AnchorPane.setLeftAnchor(underTitleLbl, 200.0);//
		AnchorPane.setTopAnchor(createSubjectBtn, 80.0);
		AnchorPane.setLeftAnchor(createSubjectBtn, 450.0);//
		AnchorPane.setTopAnchor(subjectModifyingBtn, 100.0);
		AnchorPane.setLeftAnchor(subjectModifyingBtn, 450.0);//
		AnchorPane.setTopAnchor(deleteSubjectBtn, 120.0);
		AnchorPane.setLeftAnchor(deleteSubjectBtn, 450.0);//
		
		
		root.getChildren().add(anchorPane);
		
		TableView<Subject> subjectsTable = new TableView<>();
		subjectsTable.setLayoutX(200);
		subjectsTable.setLayoutY(100);
		subjectsTable.setTableMenuButtonVisible(true);
		subjectsTable.setStyle("-fx-font: 12pt Arial;");
		subjectsTable.setPrefWidth(200);
		subjectsTable.setPrefHeight(400);
		subjectsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		
		TableColumn<Subject,String> subjectsCol = new TableColumn<>("Имя");
		subjectsCol.setPrefWidth(195);
		subjectsCol.setCellValueFactory(
				new PropertyValueFactory<Subject,String>("discipline"));
		subjectsCol.setSortable(false);
		
		subjectsTable.setItems(subjects);
		subjectsTable.getColumns().add(subjectsCol);
		
		root.getChildren().add(subjectsTable);
	}

	protected void deleteSubjects (List<Subject> deleteList) throws DBException{
		Connection conn= getConnection();
		
		Statement stmt=null;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			
			for (Subject deleteSubject : deleteList) {
				sql = "DELETE FROM disciplines WHERE discipline = '"+deleteSubject.getDiscipline()+"'; ";
				stmt.executeUpdate(sql);
			}
		    
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

	private void fillTheTable(ObservableList<Subject> subjects, Group root) throws DBException{
		Connection conn= getConnection();
		
		Statement stmt=null;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM disciplines";
		    ResultSet rs = stmt.executeQuery(sql);
		    int counter = 0; 
		    while(rs.next()){
		    	CheckBox subjCB = new CheckBox();
		    	subjects.add(new Subject(rs.getString("discipline"), subjCB));
		    	subjCB.setLayoutX(166);
		    	subjCB.setLayoutY(138+28*counter++);
		    	if (accessLevel == 1) {
		    		root.getChildren().add(subjCB);
				}
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

}
