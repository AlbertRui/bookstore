package me.bookstore.web;

import java.util.List;

/**
 * 封装翻页信息
 *
 * @author AlbertRui
 * @create 2018-02-25 20:34
 */
@SuppressWarnings("JavaDoc")
public class Page<T> {
    //当前第几页
    private int pageNo;

    //当前页的List
    private List<T> list;

    //每页显示多少条记录
    private int pageSize = 3;

    //共有多少条记录
    private long totalItemNumber;

    //构造器中需要对pageNo进行初始化
    public Page(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 校验页码
     */
    public int getPageNo() {
        if (pageNo < 0) {
            pageNo = 1;
        }
        if (pageNo > getTotalPageNumber()) {
            pageNo = getTotalPageNumber();
        }
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 获取总页数
     */
    public int getTotalPageNumber() {
        int totalPageNumber = (int) (totalItemNumber / pageSize);
        if (totalItemNumber % pageSize != 0) {
            totalPageNumber++;
        }
        return totalPageNumber;
    }

    public void setTotalItemNumber(long totalItemNumber) {
        this.totalItemNumber = totalItemNumber;
    }

    /**
     * 有没有下一页
     *
     * @return
     */
    public boolean isHasNext() {
        return getPageNo() < getTotalPageNumber();
    }

    /**
     * 有没有上一页
     *
     * @return
     */
    public boolean isHasPrev() {
        return getPageNo() > 1;
    }

    /**
     * 返回上一页
     *
     * @return
     */
    public int getPrevPage() {
        if (isHasPrev()) {
            return getPageNo() - 1;
        }
        return getPageNo();
    }

    /**
     * 返回下一页
     *
     * @return
     */
    public int getNextPage() {
        if (isHasNext()) {
            return getPageNo() + 1;
        }
        return getPageNo();
    }
}
