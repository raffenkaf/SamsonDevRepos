package taskPackage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Discipline {
	private StringProperty discipline;
	
	public Discipline(String discipline) {
		this.discipline = new SimpleStringProperty(discipline);
	}
	
	public String getDiscipline() {
		return discipline.getValue();
	}
	
	public StringProperty getDisciplineProperty() {
		return discipline;
	}
}
