package recordDailySpending.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import recordDailySpending.dao.PersonDao;
import recordDailySpending.model.Person;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PersonDao personDao;

	public void init() {
		personDao = new PersonDao();
	}

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Person person = new Person();
		person.setEmail(email);
		person.setPassword(password);

		try {
			if (personDao.loginPerson(person)) {
				// System.out.println("========22========");
				response.sendRedirect("dashboard.jsp");
			} else {
				HttpSession session = request.getSession();
				// session.setAttribute("sLoginMessage", "Your E-mail or password not
				// correct!");
				response.sendRedirect("login.jsp");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
