package me.bookstore.test;

import me.bookstore.dao.TradeItemDAO;
import me.bookstore.dao.impl.TradeItemDaoImpl;
import me.bookstore.domain.TradeItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * TradeItemDaoImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>February 27, 2018</pre>
 */
public class TradeItemDaoTest {

    private TradeItemDAO tradeItemDAO = new TradeItemDaoImpl();

    /**
     * Method: batchSave(Collection<TradeItem> items)
     */
    @Test
    public void testBatchSave() {
        Collection<TradeItem> items = new ArrayList<>();
        items.add(new TradeItem(null, 1, 10, 16));
        items.add(new TradeItem(null, 2, 10, 16));
        items.add(new TradeItem(null, 3, 10, 16));
        items.add(new TradeItem(null, 4, 10, 16));
        items.add(new TradeItem(null, 5, 10, 16));

        tradeItemDAO.batchSave(items);
    }

    /**
     * Method: getTradeItemsWithTradeId(Integer tradeId)
     */
    @Test
    public void testGetTradeItemsWithTradeId() throws Exception {
        Set<TradeItem> tradeItems = tradeItemDAO.getTradeItemsWithTradeId(13);
        for (TradeItem tradeItem : tradeItems) {
            System.out.println(tradeItem);
        }
    }

} 
