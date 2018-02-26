package me.bookstore.service;

import me.bookstore.dao.BookDAO;
import me.bookstore.dao.impl.BookDaoImpl;
import me.bookstore.domain.Book;
import me.bookstore.web.CriteriaBook;
import me.bookstore.web.Page;

/**
 * @author AlbertRui
 * @create 2018-02-26 11:18
 */
public class BookService {
    private BookDAO bookDAO = new BookDaoImpl();

    public Page<Book> getPage(CriteriaBook criteriaBook) {
        return bookDAO.getPage(criteriaBook);
    }

    public Book getBook(int id) {
        return bookDAO.getBook(id);
    }
}
