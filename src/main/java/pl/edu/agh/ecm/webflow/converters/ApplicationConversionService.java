package pl.edu.agh.ecm.webflow.converters;

import org.springframework.binding.convert.service.DefaultConversionService;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 23.10.12
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationConversionService extends DefaultConversionService {

    @Override
    protected void addDefaultConverters(){
        super.addDefaultConverters();
        addConverter(new DateTimeConverter());
    }

}
