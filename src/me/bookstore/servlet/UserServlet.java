package me.bookstore.servlet;

import me.bookstore.domain.User;
import me.bookstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		获取 username 请求参数的值
        String username = request.getParameter("username");

//		调用 UserService 的 getUser(username) 获取User 对象：要求 trades 是被装配好的，而且每一个 Trrade 对象的 items 也被装配好
        User user = userService.getUserWithTrades(username);

//		把 User 对象放入到 request 中
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/error-1.jsp");
            return;
        }

        request.setAttribute("user", user);

//		转发页面到 /WEB-INF/pages/trades.jsp
        request.getRequestDispatcher("/WEB-INF/pages/trades.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
