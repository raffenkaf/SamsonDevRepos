package taskPackage;

import java.sql.Connection;
import java.sql.Statement;

public class SubjectModifyingPage extends SubjectCreatePage{
	Subject modifyingSubject;
	
	SubjectModifyingPage(short accessLevel, Subject subject) {
		super(accessLevel);
		this.modifyingSubject = subject;
	}

	public void fillModifyingFields() {
		super.disciplineTextField.setText(modifyingSubject.getDiscipline());
		super.underTitleLbl.setText("Для модификации введите новые значения и нажмите применить"); 
		super.createSubjectBtn.setText("Применить");
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
					
					sql = "update disciplines set discipline= '"+disciplineTextField.getText()+"' "
							+ "where discipline ='"+modifyingSubject.getDiscipline()+"';";  
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
