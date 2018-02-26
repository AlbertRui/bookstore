package me.bookstore.servlet;

import me.bookstore.domain.Book;
import me.bookstore.service.BookService;
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

    protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNoStr = request.getParameter("pageNo");
        String maxPriceStr = request.getParameter("maxPrice");
        String minPriceStr = request.getParameter("minPrice");

        int pageNo = 1;
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;

        try {
            pageNo = Integer.parseInt(pageNoStr);
        } catch (NumberFormatException ignore) {}
        try {
            minPrice = Integer.parseInt(minPriceStr);
        } catch (NumberFormatException ignore) {}
        try {
            maxPrice = Integer.parseInt(maxPriceStr);
        } catch (NumberFormatException ignore) {}

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
