package me.bookstore.servlet;

import com.google.gson.Gson;
import me.bookstore.domain.Account;
import me.bookstore.domain.Book;
import me.bookstore.domain.ShoppingCart;
import me.bookstore.domain.ShoppingCartItem;
import me.bookstore.domain.User;
import me.bookstore.service.AccountService;
import me.bookstore.service.BookService;
import me.bookstore.service.UserService;
import me.bookstore.web.BookStoreWebUtils;
import me.bookstore.web.CriteriaBook;
import me.bookstore.web.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class BookServlet extends HttpServlet {

    private BookService bookService = new BookService();

    private UserService userService = new UserService();

    private AccountService accountService = new AccountService();

    protected void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 简单验证: 验证表单域的值是否符合基本的规范: 是否为空, 是否可以转为 int 类型, 是否是一个 email. 不需要进行查询
        //数据库或调用任何的业务方法.
        String username = request.getParameter("username");
        String accountId = request.getParameter("accountId");

        StringBuffer errors = validateFormField(username, accountId);

        //表单验证通过。
        if (errors.toString().equals("")) {
            errors = validateUser(username, accountId);

            //用户名和账号验证通过
            if (errors.toString().equals("")) {
                errors = validateBookStoreNumber(request);

                //库存验证通过
                if (errors.toString().equals("")) {
                    errors = validateBalance(request, accountId);
                }
            }
        }

        if (!errors.toString().equals("")) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
            return;
        }

        //验证通过执行具体的逻辑操作
        bookService.cash(BookStoreWebUtils.getShoppingCart(request), username, accountId);
        response.sendRedirect(request.getContextPath() + "/jsp/success.jsp");
    }

    /**
     * 验证余额是否充足
     *
     * @param request
     * @param accountId
     * @return
     */
    private StringBuffer validateBalance(HttpServletRequest request, String accountId) {

        StringBuffer errors = new StringBuffer("");
        ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);

        Account account = accountService.getAccount(Integer.parseInt(accountId));
        if (cart.getTotalMoney() > account.getBalance()) {
            errors.append("余额不足!");
        }

        return errors;
    }

    /**
     * 验证库存是否充足
     *
     * @param request
     * @return
     */
    private StringBuffer validateBookStoreNumber(HttpServletRequest request) {

        StringBuffer errors = new StringBuffer("");
        ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);

        for (ShoppingCartItem sci : cart.getItems()) {
            int quantity = sci.getQuantity();
            int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();

            if (quantity > storeNumber) {
                errors.append(sci.getBook().getTitle()).append("库存不足<br>");
            }
        }

        return errors;
    }

    /**
     * 验证用户名和账号是否匹配
     *
     * @param username
     * @param accountId
     * @return
     */
    private StringBuffer validateUser(String username, String accountId) {
        boolean flag = true;
        User user = userService.getUserByUserName(username);
        if (user != null) {
            if (!accountId.trim().equals("" + user.getAccountId())) {
                flag = false;
            }
        }

        StringBuffer errors = new StringBuffer("");
        if (!flag) {
            errors.append("用户名和账号不匹配");
        }

        return errors;
    }

    /**
     * 验证表单域是否符合基本的规则: 是否为空.
     *
     * @param username
     * @param accountId
     * @return
     */
    private StringBuffer validateFormField(String username, String accountId) {
        StringBuffer errors = new StringBuffer("");

        if (username == null || username.trim().equals("")) {
            errors.append("用户名不能为空<br>");
        }

        if (accountId == null || accountId.trim().equals("")) {
            errors.append("账号不能为空");
        }

        return errors;
    }

    protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //4. 在 updateItemQuantity 方法中, 获取 quanity, id, 再获取购物车对象, 调用 service 的方法做修改
        String idStr = request.getParameter("id");
        String quantityStr = request.getParameter("quantity");

        int id = -1;
        int quantity = -1;
        try {
            id = Integer.parseInt(idStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (Exception ignore) {
        }

        ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
        if (id > 0 && quantity > 0) {
            bookService.updateItemQuantity(id, quantity, shoppingCart);
        }

        //5. 传回 JSON 数据: bookNumber:xx, totalMoney
        Map<String, Object> result = new HashMap<>();
        result.put("bookNumber", shoppingCart.getBookNumber());
        result.put("totalMoney", shoppingCart.getTotalMoney());

        Gson gson = new Gson();
        String gsonStr = gson.toJson(result);
        response.setContentType("text/javascript");
        response.getWriter().print(gsonStr);
    }

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
        } catch (NumberFormatException ignore) {
        }

        ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
        bookService.removeItemFromShoppingCart(id, shoppingCart);

        if (shoppingCart.isEmpty()) {
            request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    protected void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
    }


    protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取商品id
        String idStr = request.getParameter("id");
        int id = -1;
        boolean flag = false;

        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ignore) {
        }

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
        } catch (NumberFormatException ignore) {
        }

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String methodName = request.getParameter("method");
        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }
}
