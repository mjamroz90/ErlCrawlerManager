package pl.edu.agh.ecm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 08.09.12
 * Time: 17:52
 * To change this template use File | Settings | File Templates.
 */
//@ManagedBean
//@RequestScoped
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! the client locale is "+ locale.toString());

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate );

        return "home";
    }

//
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Locale locale, Model model){
        return "login";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Locale locale, Model model){
        return "test";
    }


}
