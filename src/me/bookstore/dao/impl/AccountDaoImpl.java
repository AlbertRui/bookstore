package me.bookstore.dao.impl;

import me.bookstore.dao.AccountDAO;
import me.bookstore.domain.Account;

/**
 * @author AlbertRui
 * @create 2018-02-27 10:01
 */
@SuppressWarnings("JavaDoc")
public class AccountDaoImpl extends BaseDao<Account> implements AccountDAO{
    /**
     * 根据 accountId 获取对应的 Account 对象
     *
     * @param accountId
     * @return
     */
    @Override
    public Account get(Integer accountId) {
        String sql = "SELECT accountId, balance FROM account WHERE accountId = ?";
        return query(sql, accountId);
    }

    /**
     * 根据传入的 accountId, amount 更新指定账户的余额: 扣除 amount 指定的钱数
     *
     * @param accountId
     * @param amount
     */
    @Override
    public void updateBalance(Integer accountId, float amount) {
        String sql = "UPDATE account SET balance = balance - ? WHERE accountId = ?";
        update(sql, amount, accountId);
    }
}
