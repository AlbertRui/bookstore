package me.bookstore.filter;

import me.bookstore.db.JDBCUtils;
import me.bookstore.web.ConnectionContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException {
        Connection connection = null;
        try {
            //1.获取连接
            connection = JDBCUtils.getConnection();
            //2.开启事务
            connection.setAutoCommit(false);
            //3.利用ThreadLocal把连接和当前事物绑定
            ConnectionContext.getInstance().bind(connection);
            //4.把请求传给目标servlet
            chain.doFilter(req, resp);
            //5.提交事务
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //6.回滚事务
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;

            response.sendRedirect(request.getContextPath() + "/jsp/error-1.jsp");
        } finally {
            //7.解除绑定
            ConnectionContext.getInstance().remove();
            //8.关闭连接
            JDBCUtils.release(connection);
        }
    }

    public void init(FilterConfig config) {

    }

}
