package pl.edu.agh.ecm.web.util;

import junit.framework.Assert;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 14.01.13
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class TimeUtilsTest {
    @Test
    public void testGetPeriodTimeAsString() throws Exception {

        Period period = new Period(13,34,0,0);
        String periodAsString = TimeUtils.getTimePeriodAsString(period);
        Assert.assertEquals(periodAsString,"13:34");
    }

    @Test
    public void testGetTimeLongAsPeriod() throws Exception {

        Long timeInMillis = 1234555L;
        Period period = TimeUtils.getTimeLongAsPeriod(timeInMillis);
        Assert.assertEquals(period.getMinutes(),20);
        Assert.assertEquals(period.getSeconds(),34);
        Assert.assertEquals(period.getMillis(),555);
    }

    @Test
    public void testGetTimeLongAsString() throws Exception {

        Long timeInMillis = 1234555L+3600000;
        String time = TimeUtils.getTimeLongAsString(timeInMillis);
        Assert.assertEquals(time,"1:20");
    }

    @Test
    public void testGetDateTimeAsString() throws Exception {

        DateTime dateTime = new DateTime(2013,11,25,12,24,0,0);
        String time = TimeUtils.getDateTimeAsString(dateTime);
        Assert.assertEquals(time,"2013-11-25-12:24");
    }
}
