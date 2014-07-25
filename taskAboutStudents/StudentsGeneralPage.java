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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StudentsGeneralPage extends AbstractPage{
	StudentsGeneralPage(short accessLevel) {
		super(accessLevel);
	}

		@Override
		public void start(Stage studentsGeneralPageStage) throws Exception {
			studentsGeneralPageStage.show();
			studentsGeneralPageStage.setTitle("Students list");
			studentsGeneralPageStage.setResizable(false);
			
			Group root = new Group();
			Scene scene = new Scene(root,800,600,Color.GRAY);
			
			studentsGeneralPageStage.setScene(scene);
			
			ObservableList<Student> students = FXCollections.observableArrayList();
			fillTheTable(students, root);
			
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
					studentsGeneralPageStage.close();
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
					studentsGeneralPageStage.close();
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
			
			Button studentRatingBtn;
			studentRatingBtn = new Button();
			studentRatingBtn.setPrefWidth(300);
			studentRatingBtn.setText("Просмотреть успеваемость выбранного студента");
			studentRatingBtn.setStyle("-fx-font: bold 8pt Arial;" );
			studentRatingBtn.setOnAction(new EventHandler<ActionEvent>() {

				
				@Override
				public void handle(ActionEvent event) {
					studentsGeneralPageStage.close();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								new StudentRatingPage(accessLevel).start(new Stage());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					
				}
			});
			
			Button createStudentBtn;
			createStudentBtn = new Button();
			createStudentBtn.setPrefWidth(150);
			createStudentBtn.setText("Создать студента");
			createStudentBtn.setStyle("-fx-font: bold 8pt Arial;" );
			createStudentBtn.setOnAction(new EventHandler<ActionEvent>() {

				
				@Override
				public void handle(ActionEvent event) {
					studentsGeneralPageStage.close();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								new StudentCreatePage(accessLevel).start(new Stage());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					
				}
			});
			
			Button studentModifyingBtn;
			studentModifyingBtn = new Button();
			studentModifyingBtn.setPrefWidth(300);
			studentModifyingBtn.setText("Модифицировать выбраного студента");
			studentModifyingBtn.setStyle("-fx-font: bold 8pt Arial;" );
			studentModifyingBtn.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					for (Student iterableStudent : students) {
						if(iterableStudent.studentChB.isSelected()){
							try {
								studentsGeneralPageStage.close();
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										try {
											new StudentModifyingPage(accessLevel, iterableStudent).start(new Stage());
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
			
			Button deleteStudentBtn;
			deleteStudentBtn = new Button();
			deleteStudentBtn.setPrefWidth(150);
			deleteStudentBtn.setText("Удалить студента");
			deleteStudentBtn.setStyle("-fx-font: bold 8pt Arial;" );
			deleteStudentBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					List<Student> deleteList = new ArrayList<>(); 
					boolean flag = false;
					for (Student iterableStudent : students) {
						if(iterableStudent.studentChB.isSelected()){
							flag=true;
							deleteList.add(iterableStudent);
						}
					}
					
					if (flag) {
						try {
							deleteStudents(deleteList);
							studentsGeneralPageStage.close();
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
						} catch (DBException e) {
							e.printStackTrace();
						}
					}
				}
			});
					
			AnchorPane anchorPane=new AnchorPane();
			if (accessLevel==1) {
				anchorPane.getChildren().addAll(titleLable, logOutBtn,goToMainPageBtn, 
						studentRatingBtn, createStudentBtn, deleteStudentBtn, studentModifyingBtn);
			}else if (accessLevel==0) {
				anchorPane.getChildren().addAll(titleLable, logOutBtn,goToMainPageBtn, 
						studentRatingBtn);

			}
					
			AnchorPane.setTopAnchor(titleLable, 5.0);
			AnchorPane.setLeftAnchor(titleLable, 120.0);//
			AnchorPane.setTopAnchor(logOutBtn, 10.0);
			AnchorPane.setLeftAnchor(logOutBtn, 670.0);//
			AnchorPane.setTopAnchor(goToMainPageBtn, 10.0);
			AnchorPane.setLeftAnchor(goToMainPageBtn, 40.0);//
			
			AnchorPane.setTopAnchor(studentRatingBtn, 50.0);
			AnchorPane.setLeftAnchor(studentRatingBtn, 120.0);//
			AnchorPane.setTopAnchor(studentModifyingBtn, 80.0);
			AnchorPane.setLeftAnchor(studentModifyingBtn, 120.0);//
			AnchorPane.setTopAnchor(createStudentBtn, 50.0);
			AnchorPane.setLeftAnchor(createStudentBtn, 500.0);//
			AnchorPane.setTopAnchor(deleteStudentBtn, 80.0);
			AnchorPane.setLeftAnchor(deleteStudentBtn, 500.0);//
			
			root.getChildren().add(anchorPane);

			TableView<Student> studentsTable = new TableView<>();
			studentsTable.setLayoutX(140);
			studentsTable.setLayoutY(150);
			studentsTable.setTableMenuButtonVisible(true);
			studentsTable.setStyle("-fx-font: 12pt Arial;");
			studentsTable.setPrefWidth(500);
			studentsTable.setPrefHeight(400);
			studentsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			
			
			TableColumn<Student,String> nameCol = new TableColumn<>("Имя");
			nameCol.setCellValueFactory(
			new PropertyValueFactory<Student,String>("name"));
			nameCol.setSortable(false);
			
			TableColumn<Student,String> surnameCol = new TableColumn<>("Фамилия");
			surnameCol.setCellValueFactory(
			new PropertyValueFactory<Student,String>("surname"));
			
			TableColumn<Student,String> studentGroupCol = new TableColumn<>("Группа");
			studentGroupCol.setCellValueFactory(
			new PropertyValueFactory<Student,String>("studentGroup"));
			
			TableColumn<Student,String> entryDateCol = new TableColumn<>("Дата поступления");
			entryDateCol.setPrefWidth(180);
			
			entryDateCol.setCellValueFactory(
			new PropertyValueFactory<Student,String>("entryDate"));
					
			studentsTable.setItems(students);
			studentsTable.getColumns().addAll(nameCol, surnameCol, studentGroupCol, entryDateCol);
			
			root.getChildren().add(studentsTable);
		}

		private void fillTheTable(ObservableList<Student> students, Group root) throws DBException {
			
			Connection conn= getConnection();
			
			Statement stmt=null;
			try {
				
				Class.forName(JDBC_DRIVER);
				conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				String sql;
				sql = "SELECT * FROM students";
			    ResultSet rs = stmt.executeQuery(sql);
			    
			    int counter = 0; 
			    while(rs.next()){
			    	CheckBox studentChB = new CheckBox();
			    	students.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), 
			    			rs.getString("students_group"), rs.getString("entry_date"), studentChB));
			    	studentChB.setLayoutX(120);
			    	studentChB.setLayoutY(185+28*counter++);
			    	root.getChildren().add(studentChB);
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
		
		
		
		private void deleteStudents(List<Student> deleteList) throws DBException {
			Connection conn= getConnection();
			
			Statement stmt=null;
			try {
				
				Class.forName(JDBC_DRIVER);
				conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				String sql;
				
				for (Student deleteStudent : deleteList) {
					sql = "DELETE FROM students WHERE id = "+deleteStudent.getId()+"; ";
					stmt.executeUpdate(sql);
					sql = "alter table terms drop student_"+deleteStudent.getId()+";";
					stmt.execute(sql);
					conn.commit();
				}
			    
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
