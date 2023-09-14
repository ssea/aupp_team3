package recordDailySpending.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import recordDailySpending.dao.PersonDao;
import recordDailySpending.model.Person;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PersonDao personDao;

	public void init() {
		personDao = new PersonDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		register(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");


		Person person = new Person();
		person.setName(name);
		person.setGender(gender);
		person.setEmail(email);
		person.setPhone(phone);
		person.setPassword(password);

//		try {
//			personDao.registerPerson(person);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		response.sendRedirect("login.jsp");
		
		try {
			int result = personDao.registerPerson(person);
			if(result == 1) {
				request.setAttribute("NOTIFICATION", "User Registered Successfully!");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("register/register.jsp");
		dispatcher.forward(request, response);
		
//		String firstName = request.getParameter("firstName");
//		String lastName = request.getParameter("lastName");
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//
//		User employee = new User();
//		employee.setFirstName(firstName);
//		employee.setLastName(lastName);
//		employee.setUsername(username);
//		employee.setPassword(password);
//
//		try {
//			int result = userDao.registerEmployee(employee);
//			if(result == 1) {
//				request.setAttribute("NOTIFICATION", "User Registered Successfully!");
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		RequestDispatcher dispatcher = request.getRequestDispatcher("register/register.jsp");
//		dispatcher.forward(request, response);
	}

}
