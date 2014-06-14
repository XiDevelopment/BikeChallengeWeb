package persistence;

import org.hibernate.Session;

public class Validation {

	public static boolean checkPassword(String userName, String password,
			Session session) {
		if (password == null) 
			return false;
		
		String pw = (String) session.createQuery(Querys.GET_USER_PW)
				.setParameter("userName", userName).uniqueResult();
		if (pw != null && pw.equals(password))
			return true;
		return false;
	}

}
