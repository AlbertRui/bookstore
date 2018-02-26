package me.bookstore.service;

import me.bookstore.dao.BookDAO;
import me.bookstore.dao.impl.BookDaoImpl;
import me.bookstore.domain.Book;
import me.bookstore.domain.ShoppingCart;
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
}
