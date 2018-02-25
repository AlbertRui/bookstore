package me.bookstore.test;

import me.bookstore.dao.impl.BaseDao;
import me.bookstore.dao.impl.BookDaoImpl;
import me.bookstore.domain.Book;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

/**
 * BaseDao Tester.
 */
public class BaseDaoTest {

    private BaseDao<Book> bookDaoImpl = new BookDaoImpl();

    /**
     * Method: insert(String sql, Object... args)
     */
    @Test
    public void testInsert() {
        String sql = "INSERT INTO trade(userid, tradetime) VALUES(?, ?)";
        long id = bookDaoImpl.insert(sql, 1, new Date(new java.util.Date().getTime()));
        System.out.println(id);
    }

    /**
     * Method: update(String sql, Object... args)
     */
    @Test
    public void testUpdate() {
        String sql = "UPDATE mybooks SET Title = ? WHERE id = ?";
        bookDaoImpl.update(sql, "JavaScript", 4);
    }

    /**
     * Method: query(String sql, Object... args)
     */
    @Test
    public void testQuery() {
        String sql = "SELECT id, author, title, price, publishingDate, " +
                "salesAmount, storeNumber, remark FROM mybooks WHERE id = ?";

        Book book = bookDaoImpl.query(sql, 4);
        System.out.println(book);
    }

    /**
     * Method: queryForList(String sql, Object... args)
     */
    @Test
    public void testQueryForList() {
        String sql = "SELECT id, author, title, price, publishingDate, " +
                "salesAmount, storeNumber, remark FROM mybooks WHERE id < ?";

        List<Book> books = bookDaoImpl.queryForList(sql, 4);
        for (Book book : books) {
            System.out.print(book);
        }
    }

    /**
     * Method: getSingleVal(String sql, Object... args)
     */
    @Test
    public void testGetSingleVal() {
        String sql = "SELECT count(id) FROM mybooks";

        long count = bookDaoImpl.getSingleVal(sql);
        System.out.println(count);
    }

    /**
     * Method: batch(String sql, Object[]... args)
     */
    @Test
    public void testBatch() {
        String sql = "UPDATE mybooks SET salesAmount = ?, storeNumber = ? WHERE id = ?";

        bookDaoImpl.batch(sql, new Object[]{1, 1, 1}, new Object[]{2, 2, 2}, new Object[]{3, 3, 3});
    }

} 
