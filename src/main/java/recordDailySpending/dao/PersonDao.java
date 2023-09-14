package recordDailySpending.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import recordDailySpending.model.Person;
import recordDailySpending.utils.JDBCUtils;

public class PersonDao {
	public int registerPerson(Person person) throws ClassNotFoundException {
		String INSERT_SQL = "INSERT INTO persons" + " (name, email, phone, password, gender) VALUES "
				+ " (?, ?, ?, ?, ?);";

		int result = 0;

		try (Connection connection = JDBCUtils.getConnection();

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
			JDBCUtils.printSQLException(e);
		}
		return result;
	}

	public boolean loginPerson(Person person) throws ClassNotFoundException {
		boolean status = false;

		String SELECT_SQL = "SELECT * FROM persons WHERE email = ? AND password = ?;";

		Class.forName("com.mysql.jdbc.Driver");

		try (Connection connection = JDBCUtils.getConnection();

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
			JDBCUtils.printSQLException(e);
		}
		return status;
	}

}
