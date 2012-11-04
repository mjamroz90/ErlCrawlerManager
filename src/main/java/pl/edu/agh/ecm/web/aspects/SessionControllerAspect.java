package pl.edu.agh.ecm.web.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 03.11.12
 * Time: 21:25
 * To change this template use File | Settings | File Templates.
 */

//@Aspect
//public class SessionControllerAspect {
//
//    @Pointcut("within(@org.springframework.stereotype.Controller *)")
//    public void controllerBean(){}
//
//    @Pointcut("execution(* pl.edu.agh.ecm.web.controller.CrawlSessionController.stopSession(..)) &&" + "args(id,..)")
//    public void methodExecution(){}
//
//    @Before("controllerBean() && methodExecution(id)")
//    public void checkPermissionToStop(Long id){
//
//    }
//}
