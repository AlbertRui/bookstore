package me.bookstore.dao.impl;

import me.bookstore.dao.UserDAO;
import me.bookstore.domain.User;

/**
 * @author AlbertRui
 * @create 2018-02-27 10:25
 */
public class UserDaoImpl extends BaseDao<User> implements UserDAO{
    /**
     * 根据用户名获取 User 对象
     *
     * @param username
     * @return
     */
    @SuppressWarnings("JavaDoc")
    @Override
    public User getUser(String username) {
        String sql = "SELECT userid, username, accountid FROM userinfo WHERE username = ?";
        return query(sql, username);
    }
}
