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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TermCreatePage extends AbstractPage{

	TermCreatePage(short accessLevel) {
		super(accessLevel);
		// TODO Auto-generated constructor stub
	}

	static boolean warningLableVisible = false;
	public TextField weeksNumberTextField;
	public Label underTitleLbl;
	public Button createSubjectBtn;

	@Override
	public void start(Stage createTermPageStage) throws Exception {
		createTermPageStage.show();
		createTermPageStage.setTitle("Students list");
		createTermPageStage.setResizable(false);
		
		AnchorPane anchorPane=new AnchorPane();
		
		Group root = new Group();
		Scene scene = new Scene(root,800,600,Color.GRAY);
		
		createTermPageStage.setScene(scene);
		
		ListView<String> disciplinesLV;
		disciplinesLV = new ListView<String>();
		disciplinesLV.setPrefSize(200, 250);
		disciplinesLV.setStyle("-fx-font: bold 10pt Arial;");
		disciplinesLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
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
				createTermPageStage.close();
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
				createTermPageStage.close();
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
				createTermPageStage.close();
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
		
		Label warningLbl;
		warningLbl = new Label();
		warningLbl.setText("Должно не быть пустых полей и выбран хотя бы 1 предмет");
		warningLbl.setStyle("-fx-font: 10pt Arial;");
		warningLbl.setVisible(warningLableVisible);
		
		Label weeksNumberLbl;
		weeksNumberLbl = new Label();
		weeksNumberLbl.setText("Количество недель");
		weeksNumberLbl.setStyle("-fx-font: 10pt Arial;");
		
		Label disciplineLbl;
		disciplineLbl = new Label();
		disciplineLbl.setText("Дисциплины в семестре");
		disciplineLbl.setStyle("-fx-font: 10pt Arial;");
		
		underTitleLbl = new Label();
		underTitleLbl.setText("Для создания семестра выберите предеты и заполните пустое поле и нажмите создать");
		underTitleLbl.setStyle("-fx-font: bold 10pt Arial;");
		
		createSubjectBtn = new Button();
		createSubjectBtn.setText("Создать");
		createSubjectBtn.setStyle("-fx-font: bold 10pt Arial;" );
		createSubjectBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					boolean emptyFieldsIsNotExist=true;
					if (weeksNumberTextField.getText().equals("") || disciplinesLV.getSelectionModel().getSelectedItem() == null) {
						emptyFieldsIsNotExist = false;
					}
					
					if (!emptyFieldsIsNotExist) {
						createTermPageStage.close();
						Platform.runLater(new Runnable() {
						
							@Override
							public void run() {
								try {
									warningLableVisible = true;
									new TermCreatePage(accessLevel).start(new Stage());
								} catch (Exception e) {
									e.printStackTrace();
								};
							}
						});						
					}else{
						warningLableVisible = false;
						updateTermsTable(disciplinesLV);
						createTermPageStage.close();
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
				} catch (DBException e) {
					e.printStackTrace();
				}				
			}
		});
		
		weeksNumberTextField = new TextField();
		weeksNumberTextField.setPrefSize(200, 15);
		
		ObservableList<String> disciplines = FXCollections.observableArrayList();
		fillDisciplines(disciplines);
		
		disciplinesLV.getItems().addAll(disciplines);
		
		fillModifyingFields();// For adding methods and change variables later(TermModifyingPage). 
		
		
		
		anchorPane.getChildren().addAll(titleLable, logOutBtn, goToMainPageBtn, backPageBtn,
				underTitleLbl,createSubjectBtn,
					weeksNumberTextField, 
						warningLbl,disciplineLbl,weeksNumberLbl,
							disciplinesLV);
						
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
		
		AnchorPane.setTopAnchor(weeksNumberLbl, 85.0);
		AnchorPane.setLeftAnchor(weeksNumberLbl, 110.0);//
		AnchorPane.setTopAnchor(disciplineLbl, 155.0);
		AnchorPane.setLeftAnchor(disciplineLbl, 80.0);//
		
		AnchorPane.setTopAnchor(weeksNumberTextField, 80.0);
		AnchorPane.setLeftAnchor(weeksNumberTextField, 250.0);//
		AnchorPane.setTopAnchor(createSubjectBtn, 110.0);
		AnchorPane.setLeftAnchor(createSubjectBtn, 250.0);//
		AnchorPane.setTopAnchor(warningLbl, 420.0);
		AnchorPane.setLeftAnchor(warningLbl, 250.0);//
		AnchorPane.setTopAnchor(disciplinesLV, 150.0);
		AnchorPane.setLeftAnchor(disciplinesLV, 250.0);//
		
		root.getChildren().add(anchorPane);
		
	}

	private void fillDisciplines(ObservableList<String> disciplines) throws DBException {
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
		    
		    while(rs.next()){
		    	disciplines.add(rs.getString("discipline"));
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
	
	public void fillModifyingFields() throws DBException{
		//NOP		
	}

	protected void updateTermsTable(ListView<String> disciplinesLV) throws DBException{
		short creatingTermNumber = 1;
		Connection conn= getConnection();
		
		Statement stmt=null;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			
			sql = "select max(term_number) from terms"; 
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				creatingTermNumber = rs.getShort("max(term_number)");
				creatingTermNumber++;
			}
			
			ObservableList<String> selectedDisciplines = FXCollections.observableArrayList();
			selectedDisciplines = disciplinesLV.getSelectionModel().getSelectedItems();
			for (String selectedElement : selectedDisciplines) {
				sql = "INSERT into terms(term_number,discipline,number_of_weeks) values ("+
						creatingTermNumber+",'"+selectedElement+"',"+(Short.parseShort(weeksNumberTextField.getText())+");"); 
				stmt.execute(sql);
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

}
