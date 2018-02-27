package me.bookstore.test;

import me.bookstore.dao.BookDAO;
import me.bookstore.dao.impl.BookDaoImpl;
import me.bookstore.db.JDBCUtils;
import me.bookstore.domain.Book;
import me.bookstore.domain.ShoppingCartItem;
import me.bookstore.web.ConnectionContext;
import me.bookstore.web.CriteriaBook;
import me.bookstore.web.Page;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * BookDaoImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class BookDaoTest {

    private BookDAO bookDAO = new BookDaoImpl();

    /**
     * Method: getBook(int id)
     */
    @Test
    public void testGetBook() {
        Connection connection = JDBCUtils.getConnection();
        ConnectionContext.getInstance().bind(connection);
        Book book = bookDAO.getBook(5);
        System.out.println(book);
    }

    /**
     * Method: getPage(CriteriaBook cb)
     */
    @Test
    public void testGetPage() {
        CriteriaBook cb = new CriteriaBook(50, 60, 90);
        Page<Book> page = bookDAO.getPage(cb);

        System.out.println("pageNo:" + page.getPageNo());
        System.out.println("totalPageNumber:" + page.getTotalPageNumber());
        System.out.println("list:" + page.getList());
        System.out.println("prevPage:" + page.getPrevPage());
        System.out.println("nextPage:" + page.getNextPage());
        System.out.println("pageSize:" + page.getPageSize());
    }

    /**
     * Method: getTotalBookNumber(CriteriaBook cb)
     */
    @Test
    public void testGetTotalBookNumber() {
        long num = bookDAO.getTotalBookNumber(new CriteriaBook(0, Integer.MAX_VALUE, 5));
        System.out.println(num);
    }

    /**
     * Method: getPageList(CriteriaBook cb, int pageSize)
     */
    @Test
    public void testGetPageList() {
        List<Book> books = bookDAO.getPageList(new CriteriaBook(0, Integer.MAX_VALUE, 5), 5);
        for (Book book : books) {
            System.out.println(book);
        }
    }

    /**
     * Method: getStoreNumber(Integer id)
     */
    @Test
    public void testGetStoreNumber() {
        int store = bookDAO.getStoreNumber(5);
        System.out.println(store);
    }

    /**
     * Method: batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items)
     */
    @Test
    public void testBatchUpdateStoreNumberAndSalesAmount() {
        Collection<ShoppingCartItem> items = new ArrayList<>();

        Book book = bookDAO.getBook(1);
        ShoppingCartItem sci = new ShoppingCartItem(book);
        sci.setQuantity(10);
        items.add(sci);

        book = bookDAO.getBook(2);
        sci = new ShoppingCartItem(book);
        sci.setQuantity(11);
        items.add(sci);

        book = bookDAO.getBook(3);
        sci = new ShoppingCartItem(book);
        sci.setQuantity(12);
        items.add(sci);

        book = bookDAO.getBook(4);
        sci = new ShoppingCartItem(book);
        sci.setQuantity(14);
        items.add(sci);

        bookDAO.batchUpdateStoreNumberAndSalesAmount(items);
    }

} 
