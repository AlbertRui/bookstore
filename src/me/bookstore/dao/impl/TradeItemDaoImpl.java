package me.bookstore.dao.impl;

import me.bookstore.dao.TradeItemDAO;
import me.bookstore.domain.TradeItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author AlbertRui
 * @create 2018-02-27 14:46
 */
@SuppressWarnings("JavaDoc")
public class TradeItemDaoImpl extends BaseDao<TradeItem> implements TradeItemDAO{
    /**
     * 批量保存 TradeItem 对象
     *
     * @param items
     */
    @Override
    public void batchSave(Collection<TradeItem> items) {
        String sql = "INSERT INTO tradeitem(bookid, quantity, tradeid) VALUES(?, ?, ?)";

        Object[][] args = new Object[items.size()][3];
        List<TradeItem> tradeItems = new ArrayList<>(items);
        for (int i = 0; i < items.size(); i++) {
            args[i][0] = tradeItems.get(i).getBookId();
            args[i][1] = tradeItems.get(i).getQuantity();
            args[i][2] = tradeItems.get(i).getTradeId();
        }

        batch(sql, args);
    }

    /**
     * 根据 tradeId 获取和其关联的 TradeItem 的集合
     *
     * @param tradeId
     * @return
     */
    @Override
    public Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId) {
        String sql = "SELECT itemId tradeItemId, bookId, " +
                "quantity, tradeId FROM tradeitem WHERE tradeid = ?";
        return new HashSet<>(queryForList(sql,tradeId));
    }
}
