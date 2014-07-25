package taskPackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Student {
	public CheckBox studentChB;
	private IntegerProperty id;
	private StringProperty name;
	private StringProperty surname;
	private StringProperty studentGroup;
	private StringProperty entryDate;
	
	public Student(int id, String name, String surname,String studentGroup,String entryDate, CheckBox studentChB) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.surname = new SimpleStringProperty(surname);
		this.studentGroup = new SimpleStringProperty(studentGroup);
		this.entryDate=new SimpleStringProperty(entryDate);
		this.studentChB = studentChB;
	}

	public IntegerProperty getIdProperty() {
		return id;
	}
	
	public StringProperty getNameProperty() {
		return name;
	}
	
	public StringProperty getSurnameProperty() {
		return surname;
	}
	
	public StringProperty getStudentGroupProperty() {
		return studentGroup;
	}
	
	public StringProperty getEntryDateProperty() {
		return entryDate;
	}
	
	public int getId() {
		return id.getValue();
	}
	
	public String getName() {
		return name.getValue();
	}
	
	public String getSurname() {
		return surname.getValue();
	}
	
	public String getStudentGroup() {
		return studentGroup.getValue();
	}
	
	public String getEntryDate() {
		return entryDate.getValue();
	}
}
