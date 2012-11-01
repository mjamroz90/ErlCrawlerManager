package pl.edu.agh.ecm.web.util;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 01.11.12
 * Time: 15:39
 * To change this template use File | Settings | File Templates.
 */
public class TimeUtils {

    private static PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
            .appendHours()
            .appendSeparator(":")
            .appendMinutes().minimumPrintedDigits(2)
            .toFormatter();

    public static String getPeriodTimeAsString(Period period){
          String result ="";
          if (period != null){
              result = periodFormatter.print(period);
          }
          return result;
    }

    public static Period getTimeLongAsPeriod(Long timePeriod){
        Duration duration = new Duration(timePeriod);
        return duration.toPeriod();
    }

    public static String getTimeLongAsString(Long timePeriod){
        return getPeriodTimeAsString(getTimeLongAsPeriod(timePeriod));
    }
}
