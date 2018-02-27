package me.bookstore.service;

import me.bookstore.dao.AccountDAO;
import me.bookstore.dao.BookDAO;
import me.bookstore.dao.TradeDAO;
import me.bookstore.dao.TradeItemDAO;
import me.bookstore.dao.UserDAO;
import me.bookstore.dao.impl.AccountDaoImpl;
import me.bookstore.dao.impl.BookDaoImpl;
import me.bookstore.dao.impl.TradeDaoImpl;
import me.bookstore.dao.impl.TradeItemDaoImpl;
import me.bookstore.dao.impl.UserDaoImpl;
import me.bookstore.domain.Account;
import me.bookstore.domain.Book;
import me.bookstore.domain.ShoppingCart;
import me.bookstore.domain.ShoppingCartItem;
import me.bookstore.domain.Trade;
import me.bookstore.domain.TradeItem;
import me.bookstore.web.CriteriaBook;
import me.bookstore.web.Page;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author AlbertRui
 * @create 2018-02-26 11:18
 */
public class BookService {

    private BookDAO bookDAO = new BookDaoImpl();

    private AccountDAO accountDAO = new AccountDaoImpl();

    private TradeDAO tradeDAO = new TradeDaoImpl();

    private UserDAO userDAO = new UserDaoImpl();

    private TradeItemDAO tradeItemDAO = new TradeItemDaoImpl();

    public Page<Book> getPage(CriteriaBook criteriaBook) {
        return bookDAO.getPage(criteriaBook);
    }

    public Book getBook(int id) {
        return bookDAO.getBook(id);
    }

    public boolean addToCart(int id, ShoppingCart shoppingCart) {
        Book book = bookDAO.getBook(id);

        if (book != null) {
            shoppingCart.addBook(book);
            return true;
        }

        return false;
    }

    public void removeItemFromShoppingCart(int id, ShoppingCart shoppingCart) {
        shoppingCart.removeItem(id);
    }

    public void clearShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.clear();
    }

    public void updateItemQuantity(int id, int quantity, ShoppingCart shoppingCart) {
        shoppingCart.updateItemQuantity(id, quantity);
    }

    public void cash(ShoppingCart shoppingCart, String username, String accountId) {
        //1. 更新 mybooks 数据表相关记录的 salesamount 和 storenumber
        bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
        //2. 更新 account 数据表的 balance
        accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
        //3. 向 trade 数据表插入一条记录
        Trade trade = new Trade();
        trade.setTradeTime(new Date(new java.util.Date().getTime()));
        trade.setUserId(userDAO.getUser(username).getUserId());
        tradeDAO.insert(trade);
        //4. 向 tradeitem 数据表插入 n 条记录
        Collection<TradeItem> items = new ArrayList<>();
        for(ShoppingCartItem sci: shoppingCart.getItems()){
            TradeItem tradeItem = new TradeItem();

            tradeItem.setBookId(sci.getBook().getId());
            tradeItem.setQuantity(sci.getQuantity());
            tradeItem.setTradeId(trade.getTradeId());

            items.add(tradeItem);
        }
        tradeItemDAO.batchSave(items);
        //5. 清空购物车
        shoppingCart.clear();
    }
}
