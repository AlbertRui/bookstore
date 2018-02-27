package me.bookstore.dao.impl;

import me.bookstore.dao.Dao;
import me.bookstore.db.JDBCUtils;
import me.bookstore.utils.ReflectionUtils;
import me.bookstore.web.ConnectionContext;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * @author AlbertRui
 * @create 2018-02-25 22:24
 */
@SuppressWarnings("ALL")
public class BaseDao<T> implements Dao<T> {

    private QueryRunner queryRunner = new QueryRunner();

    private Class<T> clazz;

    public BaseDao() {
        clazz = ReflectionUtils.getSuperGenericType(getClass());
    }

    /**
     * 执行 INSERT 操作, 返回插入后的记录的 ID
     *
     * @param sql  : 待执行的 SQL 语句
     * @param args : 填充占位符的可变参数
     * @return 插入新记录的 id
     */
    @Override
    public long insert(String sql, Object... args) {

        long id = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionContext.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
            }

            //执行插入操作
            preparedStatement.executeUpdate();

            //获取生成的主键值
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(resultSet, preparedStatement);
        }
        return id;
    }

    /**
     * 执行 UPDATE 操作, 包括 INSERT(但没有返回值), UPDATE, DELETE
     *
     * @param sql  : 待执行的 SQL 语句
     * @param args : 填充占位符的可变参数
     */
    @Override
    public void update(String sql, Object... args) {
        Connection connection = null;

        try {
            connection = ConnectionContext.getInstance().getConnection();
            queryRunner.update(connection, sql, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行单条记录的查询操作, 返回与记录对应的类的一个对象
     *
     * @param sql  : 待执行的 SQL 语句
     * @param args : 填充占位符的可变参数
     * @return 与记录对应的类的一个对象
     */
    @Override
    public T query(String sql, Object... args) {
        Connection connection = null;

        try {
            connection = ConnectionContext.getInstance().getConnection();
            return queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行多条记录的查询操作, 返回与记录对应的类的一个 List
     *
     * @param sql  : 待执行的 SQL 语句
     * @param args : 填充占位符的可变参数
     * @return 与记录对应的类的一个 List
     */
    @Override
    public List<T> queryForList(String sql, Object... args) {
        Connection connection = null;

        try {
            connection = ConnectionContext.getInstance().getConnection();
            return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行一个属性或值的查询操作, 例如查询某一条记录的一个字段, 或查询某个统计信息, 返回要查询的值
     *
     * @param sql  : 待执行的 SQL 语句
     * @param args : 填充占位符的可变参数
     * @return 执行一个属性或值的查询操作, 例如查询某一条记录的一个字段, 或查询某个统计信息, 返回要查询的值
     */
    @SuppressWarnings("unchecked")
    @Override
    public <V> V getSingleVal(String sql, Object... args) {
        Connection connection = null;

        try {
            connection = ConnectionContext.getInstance().getConnection();
            return (V) queryRunner.query(connection, sql, new ScalarHandler(), args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行批量更新操作
     *
     * @param sql  : 待执行的 SQL 语句
     * @param args : 填充占位符的可变参数
     */
    @Override
    public void batch(String sql, Object[]... args) {
        Connection connection = null;

        try {
            connection = ConnectionContext.getInstance().getConnection();
            queryRunner.batch(connection, sql, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
