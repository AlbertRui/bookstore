package me.bookstore.service;

import me.bookstore.dao.BookDAO;
import me.bookstore.dao.TradeDAO;
import me.bookstore.dao.TradeItemDAO;
import me.bookstore.dao.UserDAO;
import me.bookstore.dao.impl.BookDaoImpl;
import me.bookstore.dao.impl.TradeDaoImpl;
import me.bookstore.dao.impl.TradeItemDaoImpl;
import me.bookstore.dao.impl.UserDaoImpl;
import me.bookstore.domain.Trade;
import me.bookstore.domain.TradeItem;
import me.bookstore.domain.User;

import java.util.Set;

/**
 * @author AlbertRui
 * @create 2018-02-27 11:44
 */
public class UserService {

    private UserDAO userDAO = new UserDaoImpl();

    private TradeDAO tradeDAO = new TradeDaoImpl();

    private TradeItemDAO tradeItemDAO = new TradeItemDaoImpl();

    private BookDAO bookDAO = new BookDaoImpl();

    public User getUserByUserName(String username) {
        return userDAO.getUser(username);
    }

    public User getUserWithTrades(String username) {
        //调用 UserDAO 的方法获取 User 对象
        User user = userDAO.getUser(username);
        if (user == null) {
            return null;
        }

        //调用 TradeDAO 的方法获取 Trade 的集合，把其装配为 User 的属性
        Set<Trade> trades = tradeDAO.getTradesWithUserId(user.getUserId());

        //调用 TradeItemDAO 的方法获取每一个 Trade 中的 TradeItem 的集合，并把其装配为 Trade 的属性
        if (trades != null) {
            for (Trade trade : trades) {
                Set<TradeItem> tradeItems = tradeItemDAO.getTradeItemsWithTradeId(trade.getTradeId());
                if (tradeItems != null) {
                    for (TradeItem tradeItem : tradeItems) {
                        tradeItem.setBook(bookDAO.getBook(tradeItem.getBookId()));
                    }
                    trade.setItems(tradeItems);
                }
            }
        }
        user.setTrades(trades);
        return user;
    }
}
