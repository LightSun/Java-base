package com.heaven7.java.base.test;

import org.junit.Test;

import java.util.Calendar;

/**
 * @author heaven7
 */
public class TestCalendar {

    @Test
    public void test1(){
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH); // need + 1
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minite = cal.get(Calendar.MINUTE);
        int am_pm = cal.get(Calendar.AM_PM); // 1 = pm , 0 is am.
        System.out.println("month = " + month);
        System.out.println("day = " + day);
        System.out.println("hour = " + hour);
        System.out.println("minite = " + minite);
        System.out.println("am_pm = " + am_pm);
    }
}
