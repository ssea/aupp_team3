package recordDailySpending.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import recordDailySpending.model.Person;

public class PersonDao {
	public int registerPerson(Person person) throws ClassNotFoundException {
		String INSERT_SQL = 	"INSERT INTO persons" 									+
								" (name, email, phone, password, gender) VALUES " 		+ 
								" (?, ?, ?, ?, ?);";

		int result = 0;

		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/rds_db?useSSL=false", "root", "");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
			preparedStatement.setString(1, person.getName());
			preparedStatement.setString(2, person.getEmail());
			preparedStatement.setString(3, person.getPhone());
			preparedStatement.setString(4, person.getPassword());
			preparedStatement.setString(5, person.getGender());

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return result;
	}
	
	
	public boolean loginPerson(Person person) throws ClassNotFoundException {
		boolean status = false;
//		String SELECT_SQL = 	"SELECT * FROM persons" +
//								" WHERE email ='" + person.getEmail() + "' AND password ='" + person.getPassword() + "';";
		
		String SELECT_SQL = 	"SELECT * FROM persons WHERE email = ? AND password = ?;";

		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/rds_db?useSSL=false", "root", "");

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {
				preparedStatement.setString(1, person.getEmail());
				preparedStatement.setString(2, person.getPassword());

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet result = preparedStatement.executeQuery();
			status = result.next();
			
			// Step 4: Print result
			System.out.println(result);

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
		}
		return status;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
