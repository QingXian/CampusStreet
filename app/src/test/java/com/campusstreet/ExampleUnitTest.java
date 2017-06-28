package com.campusstreet;

import android.util.Log;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void time_comparator() {
        String time = "2017/6/28 19:16:00";
        time = time.replaceAll("/", "-");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String dataStrNew = CurrentTime.format(curDate);
        try {
            c1.setTime(CurrentTime.parse(time));
            c2.setTime(CurrentTime.parse(dataStrNew));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = c1.compareTo(c2);
        if (result == 0) {
            System.out.println("ExampleUnitTest.time_comparator : " +"==");
        } else if (result < 0) {
            System.out.println("ExampleUnitTest.time_comparator : " +"<<");
        } else {
            System.out.println("ExampleUnitTest.time_comparator : " +">>");
        }
    }
}