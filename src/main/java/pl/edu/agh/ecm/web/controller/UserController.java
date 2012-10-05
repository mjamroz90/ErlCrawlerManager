package pl.edu.agh.ecm.web.controller;

import com.google.common.collect.Lists;
import com.sun.corba.se.pept.transport.ContactInfo;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import org.apache.commons.collections.list.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.webflow.execution.RequestContext;
import pl.edu.agh.ecm.crawler.CrawlerConnector;
import pl.edu.agh.ecm.domain.User;
import pl.edu.agh.ecm.domain.UserDetailsAdapter;
import pl.edu.agh.ecm.service.UserService;
import pl.edu.agh.ecm.web.form.*;
import pl.edu.agh.ecm.web.util.UrlUtil;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
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

    @Autowired
    private CrawlerConnector crawlerConnector;

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
    public String register(@ModelAttribute("user") @Valid UserForm userForm, BindingResult bindingResult, Model uiModel,
                           HttpServletRequest request, RedirectAttributes redirectAttributes, Locale locale){

        validateUserLogin(userForm.getLogin(),bindingResult);
        convertPasswordError(bindingResult);

        if (bindingResult.hasErrors()){
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("label_user_registration_failure", new Object[]{}, locale)));
            uiModel.addAttribute("userForm", userForm);
            return "users/register";
        }

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message",new Message("success",
                messageSource.getMessage("label_user_registration_success",new Object[]{},locale)));

        User user = toUser(userForm);
        //TODO: pamiętac o constraint unique(firstname, lastname).
        userService.save(user,user.getPassword());
        return "redirect:/users/"+ UrlUtil.encodeUrlPathSegment(user.getId().toString(),request);
    }

    @RequestMapping(value = "/{id}/panel",method = RequestMethod.GET)
    public String showPanel(@PathVariable("id")Long id, Model uiModel){

        return preparePanelUiModel(id,uiModel);
    }

    @RequestMapping(value = "/{id}/panel",params = "updateUser",method = RequestMethod.POST)
    public String updateUser(@PathVariable("id")Long id,
                             @ModelAttribute("userForm") @Valid UserForm userForm,BindingResult bindingResult,
                             Model uiModel,HttpServletRequest request, RedirectAttributes redirectAttributes,Locale locale){

        validateUserLogin(userForm.getLogin(),bindingResult,id);
        convertPasswordError(bindingResult);

        if (bindingResult.hasErrors()){
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("label_user_update_failed", new Object[]{}, locale)));
            preparePanelUiModel(id,uiModel,userForm,null);
            return "users/panel";
        }

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message",new Message("success",
                messageSource.getMessage("label_user_update_success",new Object[]{},locale)));

        User user = toUser(userForm);
        user.setId(id);
        userService.save(user,user.getPassword());
        return "redirect:/users/"+ UrlUtil.encodeUrlPathSegment(user.getId().toString(),request);
    }

    @RequestMapping(value = "/{id}/panel",params = "allowToStopSession", method = RequestMethod.POST)
    public String allowToStopSession(@PathVariable("id")Long id, @ModelAttribute("userStopSessionForm")
        UserAllowToStopSessionForm userStopSessionForm,Model uiModel,HttpServletRequest request,
                                          RedirectAttributes redirectAttributes,Locale locale)
    {
        User user = userService.findByIdWithDetail(id);
        user = addAllowedToStopSession(userStopSessionForm,user);
        userService.save(user,null);

        redirectAttributes.addFlashAttribute("message",new Message("success",
                messageSource.getMessage("label_user_adding_allowed_success",new Object[]{},locale)));
        return "redirect:/users/"+UrlUtil.encodeUrlPathSegment(user.getId().toString(),request)+"/panel";
    }

    @RequestMapping(value = "/{id}/panel", params = "giveAdminPermissions", method = RequestMethod.POST)
    public String giveAdminPermissions(@PathVariable("id")Long id, @ModelAttribute("userForm")UserForm userForm,
                                       Model uiModel,HttpServletRequest request, RedirectAttributes redirectAttributes,Locale locale){

        User user = userService.findById(id);
        user.setAdmin(userForm.isAdmin());
        userService.save(user,null);
        Message displayedMessage;
        if (user.isAdmin()){
            displayedMessage = new Message("success",
                    messageSource.getMessage("label_user_give_admin_success",new String[]{user.getLogin()},locale));
        }
        else{
            displayedMessage =  new Message("success",
                    messageSource.getMessage("label_user_evict_admin_success",new String[]{user.getLogin()},locale));
        }
        redirectAttributes.addFlashAttribute("message",displayedMessage);

        UserDetailsAdapter loggedUser = (UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "redirect:/users/"+UrlUtil.encodeUrlPathSegment(loggedUser.getId().toString(),request)+"/panel";
    }

    @RequestMapping(value = "/{id}/panel", params = "setConnectorParams", method = RequestMethod.POST)
    public String setConnectorParams(@PathVariable("id")Long id, @ModelAttribute("crawlerConnector") @Valid
    CrawlerConnector connector,BindingResult result,Model uiModel,HttpServletRequest request, RedirectAttributes attributes,Locale locale)
    {
        if (result.hasErrors()){
            preparePanelUiModel(id,uiModel,null,connector);
            uiModel.addAttribute("connectorMessage",new Message("error",
                    messageSource.getMessage("label_connector_settings_failure",new Object[]{},locale)));
            return "users/panel";
        }

        crawlerConnector.setRemoteManagerAddress(connector.getRemoteManagerAddress());
        crawlerConnector.setRemoteManagerPort(connector.getRemoteManagerPort());
        crawlerConnector.initManagerServer();

        return "redirect:/users/"+UrlUtil.encodeUrlPathSegment(id.toString(),request)+"/panel";
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
        UserForm user = new UserForm();
        uiModel.addAttribute("user",user);
        return "users/register";
    }


    private User toUser(UserForm userForm){
        User user = new User();
        user.setPassword(userForm.getPassword());
        user.setFirstname(userForm.getFirstname());
        user.setLastname(userForm.getLastname());
        user.setLogin(userForm.getLogin());
        user.setAdmin(false);
        return user;
    }

    private UserForm toUserForm(User user){

        UserForm userForm = new UserForm();
        userForm.setFirstname(user.getFirstname());
        userForm.setPassword(user.getPassword());
        userForm.setLastname(user.getLastname());
        userForm.setLogin(user.getLogin());
        userForm.setAdmin(user.isAdmin());
        return userForm;
    }

    private void validateUserLogin(String userLogin, Errors errors){
        if (userService.findByLogin(userLogin) != null){
            errors.rejectValue("login", "label_user_duplicate_login", new String[]{userLogin}, null);
        }
    }

    private void validateUserLogin(String userLogin, Errors errors,Long id){
        User user;
        if ((user = userService.findByLogin(userLogin)) != null){
            if (user.getId() != id){
                errors.rejectValue("login", "label_user_duplicate_login", new String[]{userLogin}, null);
            }
        }
    }

    private void convertPasswordError(BindingResult bindingResult){

        for (ObjectError error : bindingResult.getGlobalErrors()){
            String msg = error.getDefaultMessage();
            if (msg.equals("password.mismatch.message")){
                if (!bindingResult.hasFieldErrors("password")){
                    bindingResult.rejectValue("password","label_user_mismatch_password");
                }
            }
        }
    }

    private User addAllowedToStopSession(UserAllowToStopSessionForm form,User user){

        for (UserEntry keyValue : form.getUsers()){
            if (keyValue.isAllowed()){
                User allowedUser = userService.findByLogin(keyValue.getLogin());
                user.addAllowedToStopSession(allowedUser);
            }
        }
        return user;
    }

    private List<User> getNotAllowedToStopSession(User user){
        List<User> allUsers = userService.findAll();
        List<User> result = new ArrayList<User>();
        for (User u : allUsers){
            if (!user.isAllowedToStopSession(u.getLogin()) && !(u.getLogin().equals(user.getLogin()))){
                result.add(u);
            }
        }
        return result;
    }

    private String preparePanelUiModel(Long id, Model uiModel){
        return preparePanelUiModel(id,uiModel,null,null);
    }

    private String preparePanelUiModel(Long id,Model uiModel, UserForm userForm,CrawlerConnector connector){

        User user = userService.findByIdWithDetail(id);
        List<User> notAllowedToStopSession = getNotAllowedToStopSession(user);
        List<User> nonAdminUsers = userService.findAllNonAdmins();

        UserAllowToStopSessionForm userStopSessionForm = new UserAllowToStopSessionForm(notAllowedToStopSession);
        UserCollectionForm collectionForm = new UserCollectionForm(nonAdminUsers);
        if (userForm != null){
            uiModel.addAttribute("userForm", userForm);
        }
        else{
            uiModel.addAttribute("userForm", toUserForm(user));
        }
        uiModel.addAttribute("user",user);
        uiModel.addAttribute("userStopSessionForm",userStopSessionForm);
        uiModel.addAttribute("userCollectionForm",collectionForm);
        if (connector == null){
            CrawlerConnector newConnector = new CrawlerConnector();
            newConnector.setRemoteManagerAddress(crawlerConnector.getRemoteManagerAddress());
            newConnector.setRemoteManagerPort(crawlerConnector.getRemoteManagerPort());
            uiModel.addAttribute("crawlerConnector", newConnector);
        }
        else{
            uiModel.addAttribute("crawlerConnector",connector);
        }
        return "users/panel";
    }
}
