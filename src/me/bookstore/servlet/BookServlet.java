package me.bookstore.servlet;

import me.bookstore.domain.Book;
import me.bookstore.domain.ShoppingCart;
import me.bookstore.service.BookService;
import me.bookstore.web.BookStoreWebUtils;
import me.bookstore.web.CriteriaBook;
import me.bookstore.web.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BookServlet extends HttpServlet {

    private BookService bookService = new BookService();

    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
        bookService.clearShoppingCart(shoppingCart);

        request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request, response);
    }

    protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");

        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ignore) {}

        ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
        bookService.removeItemFromShoppingCart(id, shoppingCart);

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    protected void toCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取商品id
        String idStr = request.getParameter("id");
        int id = -1;
        boolean flag = false;

        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ignore) {}

        if (id > 0) {
            //2.获取购物车对象
            ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);

            //3.调用BookService的addToCart()方法把商品放入到购物车中
            flag = bookService.addToCart(id, shoppingCart);
        }

        if (flag) {
            //4.直接调用getBooks方法
            getBooks(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/jsp/error-1.jsp");
    }

    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");

        int id = -1;
        Book book = null;

        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ignore) {}

        if (id > 0) {
            book = bookService.getBook(id);
        }

        if (book == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/error-1.jsp");
            return;
        }

        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
    }

    protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNoStr = request.getParameter("pageNo");
        String maxPriceStr = request.getParameter("maxPrice");
        String minPriceStr = request.getParameter("minPrice");

        int pageNo = 1;
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;

        try {
            pageNo = Integer.parseInt(pageNoStr);
        } catch (NumberFormatException ignore) {
        }
        try {
            minPrice = Integer.parseInt(minPriceStr);
        } catch (NumberFormatException ignore) {
        }
        try {
            maxPrice = Integer.parseInt(maxPriceStr);
        } catch (NumberFormatException ignore) {
        }

        CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);

        Page<Book> page = bookService.getPage(criteriaBook);

        request.setAttribute("page", page);
        request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("method");
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
