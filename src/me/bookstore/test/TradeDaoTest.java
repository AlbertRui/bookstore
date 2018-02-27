package me.bookstore.test;

import me.bookstore.dao.TradeDAO;
import me.bookstore.dao.impl.TradeDaoImpl;
import me.bookstore.domain.Trade;
import org.junit.Test;

import java.sql.Date;
import java.util.Set;

public class TradeDaoTest {

    private TradeDAO tradeDAO = new TradeDaoImpl();

    @Test
    public void insert() {
        Trade trade = new Trade();
        trade.setUserId(2);
        trade.setTradeTime(new Date(new java.util.Date().getTime()));

        tradeDAO.insert(trade);
    }

    @Test
    public void getTradesWithUserId() {
        Set<Trade> trades = tradeDAO.getTradesWithUserId(1);
        for (Trade trade : trades) {
            System.out.println(trade);
        }
    }
}