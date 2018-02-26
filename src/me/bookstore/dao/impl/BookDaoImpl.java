package me.bookstore.dao.impl;

import me.bookstore.dao.BookDAO;
import me.bookstore.domain.Book;
import me.bookstore.domain.ShoppingCartItem;
import me.bookstore.web.CriteriaBook;
import me.bookstore.web.Page;

import java.util.Collection;
import java.util.List;

/**
 * @author AlbertRui
 * @create 2018-02-26 0:15
 */
@SuppressWarnings("JavaDoc")
public class BookDaoImpl extends BaseDao<Book> implements BookDAO {
    /**
     * 根据 id 获取指定 Book 对象
     *
     * @param id
     * @return
     */
    @Override
    public Book getBook(int id) {
        String sql = "SELECT id, author, title, price, publishingDate, " +
                "salesAmount, storeNumber, remark FROM mybooks WHERE id = ?";
        return query(sql, id);
    }

    /**
     * 根据传入的 CriteriaBook 对象返回对应的 Page 对象
     *
     * @param cb
     * @return
     */
    @Override
    public Page<Book> getPage(CriteriaBook cb) {
        Page<Book> page = new Page<>(cb.getPageNo());

        page.setTotalItemNumber(getTotalBookNumber(cb));
        //校验 pageNo 的合法性
        cb.setPageNo(page.getPageNo());
        page.setList(getPageList(cb, 3));

        return page;
    }

    /**
     * 根据传入的 CriteriaBook 对象返回其对应的记录数
     *
     * @param cb
     * @return
     */
    @Override
    public long getTotalBookNumber(CriteriaBook cb) {
        String sql = "SELECT count(id) FROM mybooks WHERE price>= ? AND price <= ?";
        return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());
    }

    /**
     * 根据传入的 CriteriaBook 和 pageSize 返回当前页对应的 List
     *
     * @param cb
     * @param pageSize
     * @return
     */
    @Override
    public List<Book> getPageList(CriteriaBook cb, int pageSize) {
        String sql = "SELECT id, author, title, price, publishingDate, " +
                "salesAmount, storeNumber, remark FROM mybooks WHERE " +
                "price>= ? AND price <= ? LIMIT ?, ?";
        return queryForList(sql, cb.getMinPrice(), cb.getMaxPrice(), (cb.getPageNo() - 1) * pageSize, pageSize);
    }

    /**
     * 返回指定 id 的 book 的 storeNumber(库存) 字段的值
     *
     * @param id
     * @return
     */
    @Override
    public int getStoreNumber(Integer id) {
        String sql = "SELECT storeNumber FROM mybooks WHERE id = ?";
        return getSingleVal(sql, id);
    }

    /**
     * 根据传入的 ShoppingCartItem 的集合,
     * 批量更新 books 数据表的 storenumber 和 salesnumber 字段的值
     *
     * @param items
     */
    @Override
    public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {

    }
}
