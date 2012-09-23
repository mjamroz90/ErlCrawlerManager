package pl.edu.agh.ecm.web.controller;

import com.google.common.collect.Lists;
import com.sun.corba.se.pept.transport.ContactInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.webflow.execution.RequestContext;
import pl.edu.agh.ecm.domain.User;
import pl.edu.agh.ecm.service.UserService;
import pl.edu.agh.ecm.web.form.Message;
import pl.edu.agh.ecm.web.form.UserGrid;
import pl.edu.agh.ecm.web.util.UrlUtil;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 10.09.12
 * Time: 21:01
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/users")
public class UserController {

    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel){

        logger.info("Start Listing");

        List<User> users = userService.findAll();
        uiModel.addAttribute("users",users);

        logger.info("Size of contact list + " + users.size());

        return "users/list";
    }

    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public UserGrid listGrid(@RequestParam(value = "page", required = false)Integer page,
                                @RequestParam(value = "rows",required = false)Integer rows,
                                @RequestParam(value = "sidx", required = false)String sortBy,
                                @RequestParam(value = "sord", required = false)String order)
    {

        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && orderBy.equals("firstName")){
            orderBy = "firstName";
        }

        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else
                sort = new Sort(Sort.Direction.ASC, orderBy);
        }

        // Constructs page request for current page
        // Note: page number for Spring Data JPA starts with 0, while jqGrid starts with 1
        PageRequest pageRequest = null;

        if (sort != null) {
            pageRequest = new PageRequest(page - 1, rows, sort);
        } else {
            pageRequest = new PageRequest(page - 1, rows);
        }

        Page<User> userPage = userService.findAllByPage(pageRequest);
        UserGrid userGrid = new UserGrid();
        userGrid.setCurrentPage(userPage.getNumber()+1);
        userGrid.setTotalPages(userPage.getTotalPages());
        userGrid.setTotalRecords(userPage.getTotalElements());
        userGrid.setUserData(Lists.newArrayList(userPage.iterator()));

        return userGrid;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid User user, BindingResult bindingResult, Model uiModel,
                           HttpServletRequest request, RedirectAttributes redirectAttributes, Locale locale){

        Map<String,Object> modelMap = uiModel.asMap();
        if (bindingResult.hasErrors()){
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("label_user_registration_failure", new Object[]{}, locale)));
            uiModel.addAttribute("user", user);
            return "users/register";
        }

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message",new Message("success",
                messageSource.getMessage("label_user_registration_success",new Object[]{},locale)));

        user.setAdmin(false);
        userService.save(user,user.getPassword());
        return "redirect:/users/"+ UrlUtil.encodeUrlPathSegment(user.getId().toString(),request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id")Long id,Model uiModel){

        User user = userService.findByIdWithDetail(id);
        uiModel.addAttribute("user",user);
        return "users/show";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model uiModel){
        return "users/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model uiModel){
        User user = new User();
        uiModel.addAttribute("user",user);
        return "users/register";
    }

}
