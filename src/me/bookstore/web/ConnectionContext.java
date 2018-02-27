package me.bookstore.web;

import java.sql.Connection;

/**
 * @author AlbertRui
 * @create 2018-02-27 20:24
 */
public class ConnectionContext {

    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    private static ConnectionContext instance = new ConnectionContext();

    private ConnectionContext() {

    }

    public static ConnectionContext getInstance() {
        return instance;
    }

    public void bind(Connection connection) {
        connectionThreadLocal.set(connection);
    }

    public Connection getConnection() {
        return connectionThreadLocal.get();
    }

    public void remove() {
        connectionThreadLocal.remove();
    }
}
