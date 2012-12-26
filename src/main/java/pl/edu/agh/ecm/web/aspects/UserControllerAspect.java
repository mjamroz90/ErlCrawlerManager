package pl.edu.agh.ecm.web.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.edu.agh.ecm.domain.User;
import pl.edu.agh.ecm.domain.UserDetailsAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 03.11.12
 * Time: 21:25
 * To change this template use File | Settings | File Templates.
 */

@Aspect
public class UserControllerAspect {

    private final String accessDeniedPage = "redirect:/security/access-denied";

    @Pointcut("execution(@pl.edu.agh.ecm.web.util.PanelAccess " +
            "* pl.edu.agh.ecm.web.controller.UserController.*(Long,..))")
    public void methodExecution(){}

    @Around("methodExecution()")
    public Object checkPermissionToStop(ProceedingJoinPoint pjp){

        Object[] args = pjp.getArgs();
        Long id = (Long)args[0];
        UserDetailsAdapter detailsAdapter =
                    (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userAttempting = detailsAdapter.getUser();

        try{
            if (userAttempting.getId().equals(id) || userAttempting.isAdmin()){
                return pjp.proceed();
            }
            else{
                return accessDeniedPage;
            }
        }
        catch (Throwable throwable)
        {
            return accessDeniedPage;
        }
    }
}
