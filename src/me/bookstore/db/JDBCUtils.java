package me.bookstore.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import me.bookstore.exception.DBException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC 的工具类
 */
@SuppressWarnings("ALL")
public class JDBCUtils {

    private static DataSource dataSource;

    static {
        dataSource = new ComboPooledDataSource("javawebapp");
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("数据库连接错误!");
        }
    }

    /**
     * 关闭数据库连接
     *
     * @param connection
     */
    public static void release(Connection connection) {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("数据库连接错误!");
        }
    }

    /**
     * 关闭数据库连接
     *
     * @param rs
     * @param statement
     */
    public static void release(ResultSet rs, Statement statement) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("数据库连接错误!");
        }
    }

    /**
     * 关闭连接
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void release(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("数据库连接错误！！！");
        }
    }

}
