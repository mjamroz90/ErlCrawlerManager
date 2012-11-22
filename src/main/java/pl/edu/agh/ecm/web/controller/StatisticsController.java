package pl.edu.agh.ecm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ecm.domain.Statistics;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 22.11.12
 * Time: 10:48
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/stats")
public class StatisticsController {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public boolean handleStats(@ModelAttribute Statistics stats){
        int a=2;
        return true;
    }

}
