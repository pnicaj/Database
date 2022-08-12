import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author Peter Nicaj
 * 
 */

public class professor {
        private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
        
        private String ID;
        private String name;
        private String deptName;
        
        public professor(){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://10.23.0.2/csc230peter?user=csc230peter&password=Redman58");
            
            
                preparedStatement = connect.prepareStatement("select * from professor where id = ?");
                preparedStatement.setString(1, ID);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, deptName);
                preparedStatement.executeUpdate();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
        public professor(String ID, String name, String deptName){
            this.ID = ID;
            this.name = name;
            this.deptName = deptName;
        }
        
        public static void main (String[] args) throws SQLException, Exception {
            Scanner keyboard = new Scanner(System.in);
            String input;
            
            System.out.println("what method would you like to perform? "
                    + "1 = Create or Insert, 2 = Read, 3 - Update, 4 = Delete");
            
            professor db = new professor();
            
            input = keyboard.nextLine();
            int num = Integer.parseInt(input);
            
            switch(num){
                case 1:{
                    String id;
                    System.out.print("What is the ID of the professor? ");
                    id = keyboard.next();
                    
                    String name;
                    System.out.print("what is the Name of the professor? ");
                    name = keyboard.next();
                    
                    String dept;
                    System.out.print("What is the Department the professor works in? ");
                    dept = keyboard.next();
                    
                    db.create(id, name, dept);
                    
                    break;
                }
                case 2:{
                    try {
			db.read();
		} catch (Exception e) {
			System.out.println(e);
		}
                    
                    break;
                }
                case 3:{
                    db.update();
                    
                    break;
                }
                case 4:{
                    String id;
                    System.out.print("type the ID number of the professor wanting to be deleted? ");
                    id = keyboard.next();
                    
                    db.delete(id);
                    break;
                }
            }
            db.readDataBase();
	}
        
        public void create(String profID, String profName, String dept_Name) throws Exception{
            try{
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://10.23.0.2/csc230peter?user=csc230peter&password=Redman58");
            
            
                preparedStatement = connect.prepareStatement("insert into professor(ID,name,dept_name)values (?,?,?)");
                preparedStatement.setString(1, profID);
                preparedStatement.setString(2, profName);
                preparedStatement.setString(3, dept_Name);
                preparedStatement.executeUpdate();
                System.out.println("Insert Added\n");
            
            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
        
        public void read() throws Exception{
            try{
                Scanner keyboard = new Scanner(System.in);
                
                String id;
                System.out.print("type the ID number of the professor that would like to read: ");
                id = keyboard.next();
                System.out.println("");
                    
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://10.23.0.2/csc230peter?user=csc230peter&password=Redman58");
                
		preparedStatement = connect.prepareStatement("select * from professor where ID = ?");
	        preparedStatement.setString(1, id);
                resultSet = preparedStatement.executeQuery();
                writeResultSet(resultSet);
                System.out.println("Information Read\n");
            }catch(Exception e){
                System.out.println(e);
            }finally {
			close();
            }
        }
               
        public void update(){
            try{
                
                Scanner keyboard = new Scanner(System.in);
                String id;
                System.out.print("type the ID number of the professor that would like to be edited: ");
                id = keyboard.next();
                    
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://10.23.0.2/csc230peter?user=csc230peter&password=Redman58");
                
                int type;
                System.out.print("What field would like to be edited. 1 = ID, 2 = name, 3 = dept Name\n");    
                type = keyboard.nextInt();
                
                    switch(type){
                        case 1: {
                         String idChange;
                         System.out.println("What would you like to change id to? ");
                         idChange = keyboard.next();
                         
                         preparedStatement = connect.prepareStatement("update professor set ID = ? where ID = ?");
                         preparedStatement.setString(1, idChange);
                         preparedStatement.setString(2, id);
                         preparedStatement.executeUpdate();
                         System.out.println("Update Made");
                         break; 
                        }
                        case 2: {
                         String nameChange;
                         System.out.println("What would you like to change the professor's name to? ");
                         nameChange = keyboard.next();
                         
                         preparedStatement = connect.prepareStatement("update professor set name = ? where ID = ?");
                         preparedStatement.setString(1, nameChange);
                         preparedStatement.setString(2, id);
                         preparedStatement.executeUpdate();
                         System.out.println("Update Made");

                         break; 
                        }
                        case 3: {
                         String deptChange;
                         System.out.println("What would you like to change the dept to? ");
                         deptChange = keyboard.next();
                         
                         preparedStatement = connect.prepareStatement("update professor set dept_name = ? where ID = ?");
                         preparedStatement.setString(1, deptChange);
                         preparedStatement.setString(2, id);
                         preparedStatement.executeUpdate();
                         System.out.println("Update Made");
                     
                         break;
                        }
                    }
                
            } catch (Exception e){
                System.out.println(e);
            } finally{
                close();
            }
        }
        
        public void delete(String profID){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://10.23.0.2/csc230peter?user=csc230peter&password=Redman58");
                preparedStatement = connect.prepareStatement("delete from professor where ID= ?");
                preparedStatement.setString(1, profID);
                preparedStatement.executeUpdate();
                System.out.println("Delete Made\n");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
	
        public void readDataBase() throws Exception {
            try {
		// This will load the MySQL driver, each DB has its own driver
                Class.forName("com.mysql.jdbc.Driver");
			
                // Setup the connection with the DB
                connect = DriverManager.getConnection("jdbc:mysql://10.23.0.2/csc230peter?user=csc230peter&password=Redman58");

                // Statements allow to issue SQL queries to the database
		statement = connect.createStatement();
			
                // Result set get the result of the SQL query
                resultSet = statement.executeQuery("select * from professor");
		writeResultSet(resultSet);


		preparedStatement = connect.prepareStatement("select * from professor");
		resultSet = preparedStatement.executeQuery();
		writeResultSet(resultSet);

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			close();
		}
        }
        
	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			System.out.println("ID: " + resultSet.getString("ID"));
			System.out.println("Name: " + resultSet.getString("name"));
			System.out.println("Dept: " + resultSet.getString("dept_name"));
			System.out.println();
		}
	}

	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

                }
	}
    
}
