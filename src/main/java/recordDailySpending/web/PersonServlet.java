package recordDailySpending.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import recordDailySpending.dao.PersonDao;
import recordDailySpending.model.Person;

@WebServlet("/personServlet")
public class PersonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PersonDao personDao;
	
	public void init() {
		personDao = new PersonDao();
	}
       
    public PersonServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        Person person = new Person();
        person.setName(name);
        person.setGender(gender);
        person.setEmail(email);
        person.setPhone(phone);


        try {
        	personDao.registerPerson(person);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
