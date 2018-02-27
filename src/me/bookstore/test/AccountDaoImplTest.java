package me.bookstore.test;

import me.bookstore.dao.AccountDAO;
import me.bookstore.dao.impl.AccountDaoImpl;
import me.bookstore.domain.Account;
import org.junit.Test;

/**
 * AccountDaoImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>February 27, 2018</pre>
 */
public class AccountDaoImplTest {

    private AccountDAO accountDAO = new AccountDaoImpl();

    /**
     * Method: get(Integer accountId)
     */
    @Test
    public void testGet() {
        Account account = accountDAO.get(1);
        System.out.println(account);
    }

    /**
     * Method: updateBalance(Integer accountId, float amount)
     */
    @Test
    public void testUpdateBalance() {
        accountDAO.updateBalance(1, 50);
        testGet();
    }

} 
