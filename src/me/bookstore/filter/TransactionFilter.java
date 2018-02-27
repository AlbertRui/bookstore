package me.bookstore.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class TransactionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        try {
            //1.获取连接
            //2.开启事务
            //3.利用ThreadLocal把连接和当前事物绑定
            //4.把请求装给目标servlet
            chain.doFilter(req, resp);
            //5.提交事务
        } catch (Exception e) {
            e.printStackTrace();
            //6.回滚事务
        } finally {
            //7.关闭连接
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
