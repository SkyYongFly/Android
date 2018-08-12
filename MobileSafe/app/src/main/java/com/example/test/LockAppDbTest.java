package com.example.test;

import android.test.InstrumentationTestCase;

import com.example.db.LockAppSQLDao;

/**
 * 锁定应用程序信息数据库测试类
 * Created by yzas on 2015/10/10.
 */
public class LockAppDbTest  extends InstrumentationTestCase {

    public void testAdd(){
        LockAppSQLDao dao = new LockAppSQLDao(getInstrumentation().getContext());
        dao.add("com.example.test");


    }

}
