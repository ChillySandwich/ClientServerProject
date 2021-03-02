import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class databaseConnection {

	public String GetUserId(String username, String password){

		try {
			String databaseUser = "joni1";
			String databaseUserpass = "12345678";
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			String url = "jdbc:postgresql://ictgradschool-joni.ckanwt0uhk0g.us-east-1.rds.amazonaws.com:5432/Logger";
			connection = DriverManager.getConnection(url, databaseUser, databaseUserpass);
			System.out.println("Database connection successful");
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery("select * from users WHERE username = '"+ username +"'");
			
			if (rs.next()) {
				return rs.getString("ID");
			}
			rs.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Login Error: " + e.toString());
		}
		return "0";
	}

	public ArrayList<String> GetTasks(String username){
		ArrayList<String> taskList = new ArrayList<String>();
		try {
			String databaseUser = "joni1";
			String databaseUserpass = "12345678";
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			String url = "jdbc:postgresql://ictgradschool-joni.ckanwt0uhk0g.us-east-1.rds.amazonaws.com:5432/Logger";
			connection = DriverManager.getConnection(url, databaseUser, databaseUserpass);
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery("SELECT * from logtasks WHERE username = '" + username + "'");
			while (rs.next()) {
				taskList.add(rs.getString("task"));
			}
			rs.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Login Error: " + e.toString());
		}
		return taskList;
	}

	public void insertTask(String username, String task){

		try {
			String databaseUser = "joni1";
			String databaseUserpass = "12345678";
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			String url = "jdbc:postgresql://ictgradschool-joni.ckanwt0uhk0g.us-east-1.rds.amazonaws.com:5432/Logger";
			connection = DriverManager.getConnection(url, databaseUser, databaseUserpass);
			Statement s = connection.createStatement();
			s.executeUpdate("INSERT INTO logtasks (task, username, taskid) VALUES('" + task + "', '"+ username+ "', '12')");
			System.out.println("Task added successfully");
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Login Error: " + e.toString());
		}

	}



}