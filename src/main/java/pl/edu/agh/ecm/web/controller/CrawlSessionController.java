package pl.edu.agh.ecm.web.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ecm.domain.CrawlSession;
import pl.edu.agh.ecm.service.CrawlSessionService;
import pl.edu.agh.ecm.web.form.CrawlSessionGrid;

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
        uiModel.addAttribute("crawlSession",crawlSession);
        return "sessions/show";
    }

}
