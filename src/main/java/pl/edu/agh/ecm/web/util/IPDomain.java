package pl.edu.agh.ecm.web.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 05.10.12
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IPDomainValidatorClass.class)
public @interface IPDomain {

    String message() default "{ipdomain.validation.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
