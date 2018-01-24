package com.monke.monkeybook.model;

import com.monke.monkeybook.bean.BookShelfBean;
import com.monke.monkeybook.bean.BookSourceBean;
import com.monke.monkeybook.dao.DbHelper;
import com.monke.monkeybook.model.content.DefaultModelImpl;
import com.monke.monkeybook.model.content.ZwduModelImpl;
import com.monke.monkeybook.model.content.GxwztvBookModelImpl;
import com.monke.monkeybook.model.content.LingdiankanshuModelImpl;
import com.monke.monkeybook.model.content.XBQGModelImpl;
import com.monke.monkeybook.model.impl.IStationBookModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GKF on 2017/12/15.
 * 所有书源
 */

public class BookSourceManage {
    public static List<BookSourceBean> selectedBookSource;
    public static List<BookSourceBean> allBookSource;

    public static BookSourceManage getInstance() {
        return new BookSourceManage();
    }

    static List<BookSourceBean> getSelectedBookSource() {
        if (selectedBookSource == null) {
            selectedBookSource = DbHelper.getInstance().getmDaoSession().getBookSourceBeanDao().queryBuilder().list();
        }
        if (selectedBookSource.size() == 0) {
            selectedBookSource = getAllBookSource();
        }
        return selectedBookSource;
    }

    public static List<BookSourceBean> getAllBookSource() {
        if (allBookSource == null) {
            allBookSource = DbHelper.getInstance().getmDaoSession().getBookSourceBeanDao().queryBuilder().list();
        }
        if (allBookSource.size() == 0) {
            allBookSource = saveBookSourceToDb();
        }
        return allBookSource;
    }

    private static List<BookSourceBean> saveBookSourceToDb() {
        List<BookSourceBean> bookSourceList = new ArrayList<>();
        bookSourceList.add(getBookSource(XBQGModelImpl.TAG, XBQGModelImpl.name, 1));
        bookSourceList.add(getBookSource(LingdiankanshuModelImpl.TAG, LingdiankanshuModelImpl.name, 2));
        bookSourceList.add(getBookSource(GxwztvBookModelImpl.TAG, GxwztvBookModelImpl.name, 3));
        bookSourceList.add(getBookSource(ZwduModelImpl.TAG, ZwduModelImpl.name, 4));

        for (BookSourceBean bookSourceBean : bookSourceList) {
            DbHelper.getInstance().getmDaoSession().getBookSourceBeanDao().insertOrReplace(bookSourceBean);
        }
        return bookSourceList;
    }

    static BookSourceBean getBookSource(String bookSourceUrl, String bookSourceName, int serialNumber) {
        BookSourceBean bookSourceBean = new BookSourceBean();
        bookSourceBean.setBookSourceUrl(bookSourceUrl);
        bookSourceBean.setBookSourceName(bookSourceName);
        bookSourceBean.setSerialNumber(serialNumber);
        bookSourceBean.setEnable(true);
        return bookSourceBean;
    }

    static BookSourceBean getBookSource5() {
        BookSourceBean bookSourceBean = new BookSourceBean();
        bookSourceBean.setBookSourceUrl("http://www.23us.so/");
        bookSourceBean.setBookSourceName("顶点小说");
        bookSourceBean.setSerialNumber(5);
        bookSourceBean.setEnable(true);
        bookSourceBean.setRuleSearchUrl("http://zhannei.baidu.com/cse/search?s=8053757951023821596&q=key&p=page");
        return bookSourceBean;
    }

    //获取book source class
    static IStationBookModel getBookSourceModel(String tag) {
        switch (tag) {
            case BookShelfBean.LOCAL_TAG:
                return null;
            case GxwztvBookModelImpl.TAG:
                return GxwztvBookModelImpl.getInstance();
            case LingdiankanshuModelImpl.TAG:
                return LingdiankanshuModelImpl.getInstance();
            case XBQGModelImpl.TAG:
                return XBQGModelImpl.getInstance();
            case ZwduModelImpl.TAG:
                return ZwduModelImpl.getInstance();
            default:
                return DefaultModelImpl.getInstance(tag);
        }
    }
}