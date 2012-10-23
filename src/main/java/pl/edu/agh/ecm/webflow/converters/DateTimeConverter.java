package pl.edu.agh.ecm.webflow.converters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.binding.convert.converters.StringToObject;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 23.10.12
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
public class DateTimeConverter extends StringToObject{

    public DateTimeConverter(){
        super(DateTime.class);
    }

    @Override
    protected Object toObject(String s, Class aClass) throws Exception {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("H:mm");
        return formatter.parseDateTime(s);
    }

    @Override
    protected String toString(Object o) throws Exception {
        String date = "";
        DateTime dateTime = (DateTime)o;
        return DateTimeFormat.forPattern("H:mm").print(dateTime);
    }
}
