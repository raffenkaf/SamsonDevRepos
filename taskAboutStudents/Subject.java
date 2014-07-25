package taskPackage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Subject {
	public CheckBox subjectChB;
	private StringProperty discipline;
	
	public Subject(String discipline, CheckBox checkBox) {
		this.discipline = new SimpleStringProperty(discipline);
		this.subjectChB = checkBox;
	}
	
	public String getDiscipline() {
		return discipline.getValue();
	}
	
	public StringProperty getDisciplineProperty() {
		return discipline;
	}
}
