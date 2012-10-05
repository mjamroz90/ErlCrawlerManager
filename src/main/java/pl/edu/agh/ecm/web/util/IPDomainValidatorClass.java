package pl.edu.agh.ecm.web.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 05.10.12
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public class IPDomainValidatorClass implements ConstraintValidator<IPDomain,String> {

    @Override
    public void initialize(IPDomain ipDomain) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if (IPDomainValidatorUtils.validate(s) == IPDomainValidatorUtils.INVALID ){
            return false;
        }

        return true;
    }
}
