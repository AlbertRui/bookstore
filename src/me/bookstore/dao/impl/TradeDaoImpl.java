package me.bookstore.dao.impl;

import me.bookstore.dao.TradeDAO;
import me.bookstore.domain.Trade;

import java.util.HashSet;
import java.util.Set;

/**
 * @author AlbertRui
 * @create 2018-02-27 14:15
 */
@SuppressWarnings("JavaDoc")
public class TradeDaoImpl extends BaseDao<Trade> implements TradeDAO {
    /**
     * 向数据表中插入 Trade 对象
     *
     * @param trade
     */
    @Override
    public void insert(Trade trade) {
        String sql = "INSERT INTO trade(userid, tradetime) VALUES(?, ?)";
        long tradeId = insert(sql, trade.getUserId(), trade.getTradeTime());
        trade.setTradeId((int) tradeId);
    }

    /**
     * 根据 userId 获取和其关联的 Trade 的集合
     *
     * @param userId
     * @return
     */
    @Override
    public Set<Trade> getTradesWithUserId(Integer userId) {
        String sql = "SELECT * FROM trade WHERE userid = ?";
        return new HashSet<>(queryForList(sql, userId));
    }
}
