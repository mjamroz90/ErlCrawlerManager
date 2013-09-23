package pl.edu.agh.ecm.webflow.converters;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.binding.convert.converters.StringToObject;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 23.10.12
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
public class PeriodTimeConverter extends StringToObject{

    PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
            .appendHours()
            .appendSeparator(":")
            .appendMinutes().minimumPrintedDigits(2)
            .toFormatter();

    public PeriodTimeConverter(){
        super(Period.class);
    }

    @Override
    protected Object toObject(String s, Class aClass) throws Exception {
        Period period = periodFormatter.parsePeriod(s);
        return period;
    }

    @Override
    protected String toString(Object o) throws Exception {
        Period periodTime = (Period)o;
        return periodFormatter.print(periodTime);
    }
}
