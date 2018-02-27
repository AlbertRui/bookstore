package me.bookstore.service;

import me.bookstore.dao.AccountDAO;
import me.bookstore.dao.impl.AccountDaoImpl;
import me.bookstore.domain.Account;

/**
 * @author AlbertRui
 * @create 2018-02-27 12:10
 */
public class AccountService {

    private AccountDAO accountDAO = new AccountDaoImpl();

    public Account getAccount(int accountId) {
        return accountDAO.get(accountId);
    }
}
