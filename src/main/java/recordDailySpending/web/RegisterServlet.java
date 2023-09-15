package recordDailySpending.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import recordDailySpending.dao.PersonDao;
import recordDailySpending.model.Person;
import recordDailySpending.utils.EmailUtility;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PersonDao personDao;

	private String host;
	private String port;
	private String user;
	private String pass;

	public void init() {
		personDao = new PersonDao();

		// reads SMTP server setting from web.xml file
		ServletContext context = getServletContext();
		host = context.getInitParameter("host");
		port = context.getInitParameter("port");
		user = context.getInitParameter("user");
		pass = context.getInitParameter("pass");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		register(request, response);
		sendEmail(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
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

		try {
			int result = personDao.registerPerson(person);
			if (result == 1) {
				request.setAttribute("NOTIFICATION", "User Registered Successfully!");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		RequestDispatcher dispatcher = request.getRequestDispatcher("register/register.jsp");
//		dispatcher.forward(request, response);
	}

	private void sendEmail(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// reads form fields
		String recipient = "phengdavid979@gmail.com";
		String subject = "Registeration verification";
		String content = "Pls fill this code: 554876";

		String resultMessage = "";

		try {
			EmailUtility.sendEmail(host, port, user, pass, recipient, subject, content);
			resultMessage = "The e-mail was sent successfully";
		} catch (Exception ex) {
			ex.printStackTrace();
			resultMessage = "There were an error: " + ex.getMessage();
		} finally {
			request.setAttribute("Message", resultMessage);
			RequestDispatcher dispatcher = request.getRequestDispatcher("register/verification.jsp");
			dispatcher.forward(request, response);
		}
	}

}
