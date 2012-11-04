package pl.edu.agh.ecm.web.controller;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.agh.ecm.crawler.CrawlerConnector;
import pl.edu.agh.ecm.domain.CrawlSession;
import pl.edu.agh.ecm.domain.User;
import pl.edu.agh.ecm.domain.UserDetailsAdapter;
import pl.edu.agh.ecm.service.CrawlSessionService;
import pl.edu.agh.ecm.web.form.CrawlSessionGrid;
import pl.edu.agh.ecm.web.form.Message;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 02.11.12
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/sessions")
public class CrawlSessionController {

    @Autowired
    private CrawlSessionService crawlSessionService;

    @Autowired
    private CrawlerConnector crawlerConnector;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel){
        return "sessions/list";
    }

    @RequestMapping(value = "/listgrid",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public CrawlSessionGrid listGrid(@RequestParam(value = "page", required = false)Integer page,
                                     @RequestParam(value = "rows", required = false)Integer rows,
                                     @RequestParam(value = "sidx", required = false)String sortBy,
                                     @RequestParam(value = "sord", required = false)String order)
    {
        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && orderBy.equals("finished")){
            orderBy = "finished";
        }

        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else
                sort = new Sort(Sort.Direction.ASC, orderBy);
        }

        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = new PageRequest(page - 1, rows, sort);
        } else {
            pageRequest = new PageRequest(page - 1, rows);
        }

        Page<CrawlSession> sessionPage = crawlSessionService.findAllByPage(pageRequest);
        CrawlSessionGrid crawlSessionGrid = new CrawlSessionGrid();
        crawlSessionGrid.setCurrentPage(sessionPage.getNumber()+1);
        crawlSessionGrid.setTotalPages(sessionPage.getTotalPages());
        crawlSessionGrid.setTotalRecords(sessionPage.getTotalElements());
        crawlSessionGrid.setSessionData(Lists.newArrayList(sessionPage.iterator()));
        return crawlSessionGrid;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id")Long id,Model uiModel){

        CrawlSession crawlSession = crawlSessionService.findByIdWithDetail(id);
        return fillSessionModel(crawlSession,uiModel);
    }

    @RequestMapping(value = "/{id}",params = "started",method = RequestMethod.GET)
    public String showStarted(@PathVariable("id")Long id,Model uiModel,Locale locale){

        CrawlSession crawlSession = crawlSessionService.findByIdWithDetail(id);
        uiModel.addAttribute("message",new Message("success",
                messageSource.getMessage("label_session_start_success",new Object[]{},locale)));
        return fillSessionModel(crawlSession,uiModel);
    }

    @RequestMapping(value = "/{id}",params = "stopSession", method = RequestMethod.GET)
    public String stopSession(@PathVariable("id")Long id,Model uiModel,RedirectAttributes attributes,Locale locale){

        CrawlSession crawlSession = crawlSessionService.findByIdWithDetail(id);
        if (!isAllowedToStopSession(crawlSession)){
            return "redirect:/security/access-denied";
        }

        boolean stopResult = crawlerConnector.stopSession(true,crawlSession);
        User loggedUser = ((UserDetailsAdapter)SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getUser();

        if (stopResult == true){
            attributes.addFlashAttribute("message",new Message("success",
                    messageSource.getMessage("label_session_stop_success",new Object[]{},locale)));
            crawlSession.setFinishedBy(loggedUser);
            crawlSession.setFinished(DateTime.now());
            crawlSessionService.save(crawlSession);
            return "redirect:/sessions";
        }
        else{
            uiModel.addAttribute("message",new Message("error",
                    messageSource.getMessage("label_session_stop_failed",new Object[]{},locale)));
            return fillSessionModel(crawlSession,uiModel);
        }

    }

    private String fillSessionModel(CrawlSession crawlSession,Model uiModel){
        uiModel.addAttribute("crawlSession",crawlSession);
        uiModel.addAttribute("allowedToStopSession",isAllowedToStopSession(crawlSession));
        return "sessions/show";
    }

    private boolean isAllowedToStopSession(CrawlSession crawlSession){

        UserDetailsAdapter userDetails = (UserDetailsAdapter)SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User applyingUser = userDetails.getUser();

        User userStarted = crawlSession.getStartedBy();
        if ((userStarted.getId() == applyingUser.getId()) || userStarted.isAllowedToStopSession(applyingUser.getLogin())){
            return true;
        }
        else{
            return false;
        }
    }

}
