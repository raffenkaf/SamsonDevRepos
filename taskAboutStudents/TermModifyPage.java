package taskPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class TermModifyPage extends TermCreatePage{
	short modifyingTermNumber;

	TermModifyPage(short accessLevel, short modifyingTermNumber) {
		super(accessLevel);
		this.modifyingTermNumber=modifyingTermNumber;
	}

	public void fillModifyingFields() throws DBException {
		super.weeksNumberTextField.setText(""+getWeeksNumber());
		super.underTitleLbl.setText("Для модификации введите новое значения и нажмите применить"); 
		super.createSubjectBtn.setText("Применить");
	}

	private short getWeeksNumber() throws DBException {
		short weeksNumber=0;
		Connection conn= getConnection();
		Statement stmt=null;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			
			sql = "select number_of_weeks from terms where term_number="+modifyingTermNumber+" group by term_number;"; 
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				weeksNumber = rs.getShort("number_of_weeks");
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
		return weeksNumber;
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
			
			sql = "delete from terms where term_number ="+creatingTermNumber+";"; 
			stmt.execute(sql);
			
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
