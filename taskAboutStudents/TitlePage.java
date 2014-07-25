package taskPackage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TitlePage extends AbstractPage{
	
	TitlePage(short accessLevel) {
		super(accessLevel);
	}

	Label lbl;
	Button btn;
	
	

		@Override
	public void start(Stage titleWindowStage) throws Exception {
		
		titleWindowStage.show();
		titleWindowStage.setTitle("Title page");
		titleWindowStage.setResizable(false);
		
		Group root = new Group();
		Scene scene = new Scene(root, 800, 600, Color.GRAY);
		
		titleWindowStage.setScene(scene);
		
		Button titleLable;
		titleLable = new Button();
		titleLable.setText("Система управления студентами и их успеваемостью");
		titleLable.setStyle("-fx-font: bold 12pt Arial;-fx-background-color: white;-fx-border-width: 1px;" );
		titleLable.setPrefSize(550, 10);
				
		Button logOutBtn;
		logOutBtn = new Button();
		logOutBtn.setText("Logout");
		logOutBtn.setUnderline(true);
		logOutBtn.setStyle("-fx-font: bold 8pt Arial;-fx-background-color: gray;"
				+ " -fx-border-width: 0px; -fx-text-fill: blue;" );
		logOutBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				titleWindowStage.close();
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
		
		Button studentsBtn;
		studentsBtn = new Button();
		studentsBtn.setText("Студенты");
		studentsBtn.setUnderline(true);
		studentsBtn.setStyle("-fx-font: bold 10pt Arial;-fx-background-color: gray; "
				+ "-fx-border-width: 0px; -fx-text-fill: blue;" );
		studentsBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				titleWindowStage.close();
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
		
		Button subjectsBtn;
		subjectsBtn = new Button();
		subjectsBtn.setText("Дисциплины");
		subjectsBtn.setUnderline(true);
		subjectsBtn.setStyle("-fx-font: bold 10pt Arial;-fx-background-color: gray; "
				+ "-fx-border-width: 0px; -fx-text-fill: blue;" );
		subjectsBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				titleWindowStage.close();
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
		
		Button semesterBtn;
		semesterBtn = new Button();
		semesterBtn.setText("Семестры");
		semesterBtn.setUnderline(true);
		semesterBtn.setStyle("-fx-font: bold 10pt Arial;-fx-background-color: gray; "
				+ "-fx-border-width: 0px; -fx-text-fill: blue;" );
		semesterBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				titleWindowStage.close();
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
		
		AnchorPane ap=new AnchorPane();
		ap.getChildren().addAll(titleLable, logOutBtn,studentsBtn,subjectsBtn, semesterBtn);
		
		AnchorPane.setTopAnchor(titleLable, 5.0);
		AnchorPane.setLeftAnchor(titleLable, 120.0);
		AnchorPane.setTopAnchor(logOutBtn, 10.0);
		AnchorPane.setLeftAnchor(logOutBtn, 670.0);
		AnchorPane.setTopAnchor(studentsBtn, 50.0);
		AnchorPane.setLeftAnchor(studentsBtn, 200.0);
		AnchorPane.setTopAnchor(subjectsBtn, 50.0);
		AnchorPane.setLeftAnchor(subjectsBtn, 350.0);
		AnchorPane.setTopAnchor(semesterBtn, 50.0);
		AnchorPane.setLeftAnchor(semesterBtn, 500.0);
		
		root.getChildren().add(ap);
	}

	@Override
	public void run() {
		launch("123");		
	}
}
