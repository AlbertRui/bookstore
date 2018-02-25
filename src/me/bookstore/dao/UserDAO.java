package me.bookstore.dao;

import com.atguigu.bookstore.domain.User;

public interface UserDAO {

	/**
	 * �����û�����ȡ User ����
	 * @param username
	 * @return
	 */
	public abstract User getUser(String username);

}

