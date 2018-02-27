package me.bookstore.test;

import me.bookstore.dao.UserDAO;
import me.bookstore.dao.impl.UserDaoImpl;
import me.bookstore.domain.User;
import org.junit.Test;

/**
 * UserDaoImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>February 27, 2018</pre>
 */
public class UserDaoTest {

    private UserDAO userDAO = new UserDaoImpl();

    /**
     * Method: getUser(String username)
     */
    @Test
    public void testGetUser() {
        User user = userDAO.getUser("Tom");
        System.out.println(user);
    }

}
