package pl.edu.agh.ecm.web.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 26.12.12
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PanelAccess {
}
