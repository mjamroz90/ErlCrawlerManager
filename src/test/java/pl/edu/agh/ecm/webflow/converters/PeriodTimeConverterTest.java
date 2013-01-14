package pl.edu.agh.ecm.webflow.converters;

import junit.framework.Assert;
import org.joda.time.Period;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 14.01.13
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class PeriodTimeConverterTest extends PeriodTimeConverter{

    private PeriodTimeConverter converter = new PeriodTimeConverter();

    @Test
    public void toObjectTest() throws Exception{

        String time = "20:34";
        Period period = (Period)converter.toObject(time, Period.class);
        Assert.assertEquals(period.getHours(),20);
        Assert.assertEquals(period.getMinutes(),34);
    }

    @Test
    public void toStringTest() throws Exception{

        Period period = new Period(13,34,0,0);
        String periodAsString = converter.toString(period);
        Assert.assertEquals(periodAsString,"13:34");

    }
}
