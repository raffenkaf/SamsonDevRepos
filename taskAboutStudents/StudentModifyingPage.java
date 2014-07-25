package taskPackage;

import java.sql.Connection;
import java.sql.Statement;

public class StudentModifyingPage extends StudentCreatePage{
	Student modifyingStudent;

	StudentModifyingPage(short accessLevel, Student modifyingStudent) throws DBException {
		super(accessLevel);
		this.modifyingStudent = modifyingStudent;
	}
	
	public void fillModifyingFields() {
		super.surnameTextField.setText(modifyingStudent.getSurname());
		super.nameTextField.setText(modifyingStudent.getName());
		super.studentGroupTextField.setText(modifyingStudent.getStudentGroup());
		super.entryDateTextField.setText(modifyingStudent.getEntryDate());
		super.underTitleLbl.setText("Для модификации введите новые значения и нажмите применить"); 
		super.createStudentBtn.setText("Применить");
	}

	protected void updateStudentAndTermsTables() throws DBException {
		Connection conn= getConnection();
		
		Statement stmt=null;
		try {
			
			Class.forName(JDBC_DRIVER);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String sql;
			
			sql = "update students set name= '"+nameTextField.getText()+
						"', surname ='"+surnameTextField.getText()+"', "
							+ "students_group= '"+studentGroupTextField.getText() +"', entry_date = '"+
								entryDateTextField.getText()+"' where id ="+modifyingStudent.getId(); 
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
