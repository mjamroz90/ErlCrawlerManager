package pl.edu.agh.ecm.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.agh.ecm.web.form.Message;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 22.09.12
 * Time: 22:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/security")
public class SecurityController {

    final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/loginfail")
    public String loginFail(Model uiModel,Locale locale){

        uiModel.addAttribute("message",new Message("error",
                messageSource.getMessage("label_user_login_failure", new Object[]{},locale)));
        return "users/login";
    }
}
