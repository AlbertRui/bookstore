package me.bookstore.service;

import me.bookstore.dao.UserDAO;
import me.bookstore.dao.impl.UserDaoImpl;
import me.bookstore.domain.User;

/**
 * @author AlbertRui
 * @create 2018-02-27 11:44
 */
public class UserService {

    private UserDAO userDAO = new UserDaoImpl();

    public User getUserByUserName(String username) {
        return userDAO.getUser(username);
    }
}
