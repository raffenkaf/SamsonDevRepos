package taskPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TermsGeneralPage extends AbstractPage{
	static boolean flagFirstTime = true;
	static short currentTerm;

	TermsGeneralPage(short accessLevel) {
		super(accessLevel);
	}

	@Override
	public void start(Stage termssGeneralPageStage) throws DBException {
		if (flagFirstTime) {
			currentTerm = minimalTerm();
			flagFirstTime= false;
		}
		short sizeOfCurrentTerm = sizeOfCurrentTerm();
		
		termssGeneralPageStage.show();
		termssGeneralPageStage.setTitle("Students list");
		termssGeneralPageStage.setResizable(false);
		
		Group root = new Group();
		Scene scene = new Scene(root,800,600,Color.GRAY);
		
		termssGeneralPageStage.setScene(scene);
		
		ObservableList<Discipline> terms = FXCollections.observableArrayList();
		fillTheTermsTable(terms);
		
		ObservableList<String> termsNumbers = FXCollections.observableArrayList();
		fillTermsNumbers(termsNumbers);
		
		ChoiceBox<String> termChoice = new ChoiceBox<String>(termsNumbers);
		termChoice.setPrefSize(150, 20);
		termChoice.setStyle("-fx-font: bold 10pt Arial;" );
		
		termChoice.setValue("Семестр "+currentTerm);
				
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
				flagFirstTime = true;
				termssGeneralPageStage.close();
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
				flagFirstTime = true;
				termssGeneralPageStage.close();
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
		
		Button selectTermBtn;
		selectTermBtn = new Button();
		selectTermBtn.setText("Выбрать семестр");
		selectTermBtn.setStyle("-fx-font: bold 10pt Arial;" );
		selectTermBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				currentTerm = Short.parseShort(termChoice.getValue().substring(8));
				termssGeneralPageStage.close();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							new TermsGeneralPage(accessLevel).start(termssGeneralPageStage);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		
		Button createTermBtn;
		createTermBtn = new Button();
		createTermBtn.setText("Cоздать семестр");
		createTermBtn.setPrefWidth(200);
		createTermBtn.setStyle("-fx-font: bold 10pt Arial" );
		createTermBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				termssGeneralPageStage.close();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							new TermCreatePage(accessLevel).start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		
		Button modifyCurrentTermBtn;
		modifyCurrentTermBtn = new Button();
		modifyCurrentTermBtn.setText("Модифицировать семестр");
		modifyCurrentTermBtn.setStyle("-fx-font: bold 10pt Arial;" );
		modifyCurrentTermBtn.setPrefWidth(200);
		modifyCurrentTermBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				termssGeneralPageStage.close();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							new TermModifyPage(accessLevel, currentTerm).start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		
		Button deleteCurrentTermBtn;
		deleteCurrentTermBtn = new Button();
		deleteCurrentTermBtn.setText("Удалить семестр");
		deleteCurrentTermBtn.setStyle("-fx-font: bold 10pt Arial;" );
		deleteCurrentTermBtn.setPrefWidth(200);
		deleteCurrentTermBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					deleteCurrentTerm();
				} catch (DBException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					currentTerm = minimalTerm();
				} catch (DBException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				termssGeneralPageStage.close();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							new TermsGeneralPage(accessLevel).start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		
		Label selectTermLbl;
		selectTermLbl = new Label();
		selectTermLbl.setText("Выбрать семестр");
		selectTermLbl.setStyle("-fx-font:12pt Arial;");
		
		Label sizeOfTermLbl;
		sizeOfTermLbl = new Label();
		sizeOfTermLbl.setText("Длительность семестра: "+sizeOfCurrentTerm+" недели");
		sizeOfTermLbl.setStyle("-fx-font:12pt Arial;");
		
		Label disciplineListOfTermLbl;
		disciplineListOfTermLbl = new Label();
		disciplineListOfTermLbl.setText("Список дисципин "+currentTerm+ " семестра");
		disciplineListOfTermLbl.setStyle("-fx-font:10pt Arial;");
		
		AnchorPane anchorPane=new AnchorPane();
		if (accessLevel==1) {
			anchorPane.getChildren().addAll(titleLable, logOutBtn,goToMainPageBtn, 
					selectTermLbl, sizeOfTermLbl, disciplineListOfTermLbl, 
						deleteCurrentTermBtn, modifyCurrentTermBtn, createTermBtn, selectTermBtn,
							termChoice);
		}else if (accessLevel==0) {
			anchorPane.getChildren().addAll(titleLable, logOutBtn,goToMainPageBtn,
					selectTermLbl, sizeOfTermLbl, disciplineListOfTermLbl, 
						selectTermBtn,
							termChoice);
		}
				
		AnchorPane.setTopAnchor(titleLable, 5.0);
		AnchorPane.setLeftAnchor(titleLable, 120.0);//
		AnchorPane.setTopAnchor(logOutBtn, 10.0);
		AnchorPane.setLeftAnchor(logOutBtn, 670.0);//
		AnchorPane.setTopAnchor(goToMainPageBtn, 10.0);
		AnchorPane.setLeftAnchor(goToMainPageBtn, 40.0);//
		
		AnchorPane.setTopAnchor(selectTermLbl, 60.0);
		AnchorPane.setLeftAnchor(selectTermLbl, 150.0);//
		AnchorPane.setTopAnchor(sizeOfTermLbl, 90.0);
		AnchorPane.setLeftAnchor(sizeOfTermLbl, 150.0);//
		AnchorPane.setTopAnchor(disciplineListOfTermLbl, 120.0);
		AnchorPane.setLeftAnchor(disciplineListOfTermLbl, 150.0);//
		
		AnchorPane.setTopAnchor(createTermBtn, 150.0);
		AnchorPane.setLeftAnchor(createTermBtn, 450.0);//
		AnchorPane.setTopAnchor(modifyCurrentTermBtn, 180.0);
		AnchorPane.setLeftAnchor(modifyCurrentTermBtn, 450.0);//
		AnchorPane.setTopAnchor(deleteCurrentTermBtn, 210.0);
		AnchorPane.setLeftAnchor(deleteCurrentTermBtn, 450.0);//
		AnchorPane.setTopAnchor(selectTermBtn, 55.0);
		AnchorPane.setLeftAnchor(selectTermBtn, 460.0);//
		AnchorPane.setTopAnchor(termChoice, 55.0);
		AnchorPane.setLeftAnchor(termChoice, 300.0);//
		
		root.getChildren().add(anchorPane);
		
		TableView<Discipline> termsTable = new TableView<>();
		termsTable.setLayoutX(150);
		termsTable.setLayoutY(150);
		termsTable.setTableMenuButtonVisible(true);
		termsTable.setStyle("-fx-font: 12pt Arial;");
		termsTable.setPrefWidth(250);
//		termsTable.setPrefHeight(400);
		termsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		TableColumn<Discipline, String> subjectsCol = new TableColumn<>("Наименование дисциплины");
		subjectsCol.setPrefWidth(195);
		subjectsCol.setCellValueFactory( 
				new PropertyValueFactory<Discipline,String>("discipline"));
		subjectsCol.setSortable(false);
		
		termsTable.setItems(terms);
		termsTable.getColumns().add(subjectsCol);
		
		root.getChildren().add(termsTable);
	}

	private void fillTermsNumbers(ObservableList<String> termsNumbers) throws DBException {
		Connection conn= getConnection();
		
		Statement stmt=null;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT term_number FROM terms GROUP BY term_number ";
		    ResultSet rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	termsNumbers.add("Семестр "+rs.getShort("term_number"));
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

	private void fillTheTermsTable(ObservableList<Discipline> terms) throws DBException {
		Connection conn= getConnection();
		
		Statement stmt=null;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT discipline FROM terms where term_number = "+currentTerm;
		    ResultSet rs = stmt.executeQuery(sql);
		    
		    while(rs.next()){
		    	terms.add(new Discipline(rs.getString("discipline")));
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

	private short sizeOfCurrentTerm() throws DBException {
		Connection conn= getConnection();
		Statement stmt=null;
		short result=0;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT number_of_weeks FROM terms where term_number ="+currentTerm;
		    ResultSet rs = stmt.executeQuery(sql);
		    if (rs.next()) {
				result = rs.getShort("number_of_weeks");
			} else{
				result = 0;
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
		return result;
	}

	protected void deleteCurrentTerm() throws DBException {
		Connection conn= getConnection();
		Statement stmt=null;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			sql = "delete FROM terms where term_number ="+currentTerm;
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
	
	private short minimalTerm() throws DBException {
		Connection conn= getConnection();
		Statement stmt=null;
		short result=0;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT min(term_number) FROM terms ";
		    ResultSet rs = stmt.executeQuery(sql);
		    if (rs.next()) {
				result = rs.getShort("min(term_number)");
			} else{
				result = 0;
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
		return result;
	}

}