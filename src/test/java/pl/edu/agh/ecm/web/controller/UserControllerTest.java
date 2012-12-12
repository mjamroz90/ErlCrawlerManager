package pl.edu.agh.ecm.web.controller;

import org.hibernate.type.ListType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import pl.edu.agh.ecm.domain.User;
import pl.edu.agh.ecm.domain.UserDetailsAdapter;
import pl.edu.agh.ecm.service.UserService;
import pl.edu.agh.ecm.web.form.UserAllowToStopSessionForm;
import pl.edu.agh.ecm.web.form.UserEntry;
import pl.edu.agh.ecm.web.form.UserForm;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 06.10.12
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */
public class UserControllerTest extends AbstractControllerTest {

    private final List<User> users = new ArrayList<User>();
    private UserService userService;
    private MessageSource messageSource;

    @Before
    public void initUsers(){
        User user = new User();
        user.setAdmin(false);
        user.setLogin("mjamroz90");
        user.setFirstname("Michal");
        user.setLastname("Jamroz");
        users.add(user);
    }

    @Test
    public void testList() throws Exception {

        userService = Mockito.mock(UserService.class);
        Mockito.when(userService.findAll()).thenReturn(users);

        UserController userController = new UserController();
        ReflectionTestUtils.setField(userController,"userService",userService);

        ExtendedModelMap modelMap = new ExtendedModelMap();
        String result = userController.list(modelMap);

        Assert.assertNotNull(result);
        Assert.assertEquals(result,"users/list");

        List<User> users1 = (List<User>)modelMap.get("users");
        Assert.assertEquals(users1.size(),1);
    }

    @Test
    public void testRegister() throws Exception{

        final User user= new User();
        user.setFirstname("Baba");
        user.setLastname("Jaga");
        user.setLogin("bj");

        userService = Mockito.mock(UserService.class);
        Mockito.when(userService.save(Mockito.isA(User.class),Mockito.isNull(String.class))).
                thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                users.add(user);
                user.setId(1l);
                return user;
            }
        });
        messageSource = Mockito.mock(MessageSource.class);
        Mockito.when(messageSource.getMessage("label_user_registration_success",new Object[]{}, Locale.ENGLISH))
        .thenReturn("success");

        UserController userController = new UserController();
        ReflectionTestUtils.setField(userController,"userService",userService);
        ReflectionTestUtils.setField(userController,"messageSource",messageSource);

        BindingResult bindingResult = new BeanPropertyBindingResult(userController.toUserForm(user),"user");
        ExtendedModelMap modelMap = new ExtendedModelMap();

        HttpServletRequest request = new MockHttpServletRequest();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Locale locale = Locale.ENGLISH;

        String result = userController.register(userController.toUserForm(user),bindingResult,modelMap,
                request,redirectAttributes,locale);

        Assert.assertNotNull(result);
        Assert.assertEquals(result,"redirect:/users/1");
        Assert.assertEquals(users.size(),2);
    }

    @Test
    public void testUpdateUser() throws Exception{

        final Long id=1L;
        final User user = new User("Wacek","Michal","wacek.michal",null);
        userService = Mockito.mock(UserService.class);
        Mockito.when(userService.save(Mockito.isA(User.class),Mockito.isNull(String.class))).
                thenAnswer(new Answer<User>() {
                    @Override
                    public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                        Object[] args = invocationOnMock.getArguments();
                        User user = (User)args[0];
                        if (users.size() > 1){
                            users.remove(1);
                        }
                        users.add(user);
                        user.setId(id);
                        return user;
                    }
                });
        messageSource = Mockito.mock(MessageSource.class);
        Mockito.when(messageSource.getMessage("label_user_update_success",new Object[]{}, Locale.ENGLISH))
                .thenReturn("success");

        UserDetailsAdapter detailsAdapterMock = Mockito.mock(UserDetailsAdapter.class);

        UserController userController = new UserController();
        ReflectionTestUtils.setField(userController,"userService",userService);
        ReflectionTestUtils.setField(userController,"messageSource",messageSource);

        BindingResult bindingResult = new BeanPropertyBindingResult(userController.toUserForm(user),"userForm");
        ExtendedModelMap modelMap = new ExtendedModelMap();
        HttpServletRequest request = new MockHttpServletRequest();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Locale locale = Locale.ENGLISH;

        userService.save(user,null);
        Mockito.when(userService.findById(Mockito.eq(id))).thenReturn(user);
        Mockito.when(detailsAdapterMock.getUser()).thenReturn(user);
        ReflectionTestUtils.setField(userController,"userDetailsAdapter",detailsAdapterMock);

        final User updatedUser = new User("Wacek","Wacek","wacek.wacek",null);
        updatedUser.setId(id);
        String result = userController.updateUser(id,userController.toUserForm(updatedUser),bindingResult,
                modelMap,request,redirectAttributes,locale);
        Assert.assertNotNull(result);
        Assert.assertEquals(result,"redirect:/users/1");
        Assert.assertEquals(users.size(),2);
        User updatedUser1 = users.get(1);
        Assert.assertTrue(updatedUser1.getLogin().equals(updatedUser.getLogin()) &&
                updatedUser1.getFirstname().equals(updatedUser.getFirstname()) &&
                updatedUser1.getLastname().equals(updatedUser.getLastname()) &&
                updatedUser1.getId().equals(id)
        );
    }

    @Test
    public void testAllowToStopSession() throws Exception{

        Long id = 1L;
        UserDetailsAdapter detailsAdapterMock = Mockito.mock(UserDetailsAdapter.class);
        UserController userController = new UserController();
        ReflectionTestUtils.setField(userController,"userDetailsAdapter",detailsAdapterMock);
        final User user = new User("Wacek","Michal","wacek.michal",null);
        user.setId(id);

        userService = Mockito.mock(UserService.class);
        messageSource = Mockito.mock(MessageSource.class);
        ReflectionTestUtils.setField(userController,"userService",userService);
        ReflectionTestUtils.setField(userController,"messageSource",messageSource);

        Mockito.when(userService.findByIdWithDetail(Mockito.eq(id))).thenReturn(user);
        Mockito.when(detailsAdapterMock.getUser()).thenReturn(user);
        Mockito.when(messageSource.getMessage("label_user_adding_allowed_success",new Object[]{}, Locale.ENGLISH))
                .thenReturn("success");

        List<User> sampleUsers = new LinkedList<User>();
        UserAllowToStopSessionForm form = prepareSampleForm(sampleUsers);

        Mockito.when(userService.findByLogin(sampleUsers.get(0).getLogin())).thenReturn(sampleUsers.get(0));
        Mockito.when(userService.findByLogin(sampleUsers.get(1).getLogin())).thenReturn(sampleUsers.get(1));
        Mockito.when(userService.save(user,null)).thenReturn(user);

        ExtendedModelMap modelMap = new ExtendedModelMap();
        HttpServletRequest request = new MockHttpServletRequest();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Locale locale = Locale.ENGLISH;

        String result = userController.allowToStopSession(id,form,modelMap,request,redirectAttributes,locale);
        Assert.assertEquals(user.getAllowedToStopSession().size(),1);
        Assert.assertTrue(user.getAllowedToStopSession().contains(sampleUsers.get(0)));
        Assert.assertEquals(result,"redirect:/users/1/panel");
    }

    @Test
    public void testGiveAdminPermissions() throws Exception{

        Long id = 2L;
        final User user = new User("Wacek","Michal","wacek.michal",null);
        User nearlyAdmin = new User("A","B","A.B",null);
        nearlyAdmin.setAdmin(true);
        nearlyAdmin.setId(2L);
        user.setId(1L);
        user.setAdmin(true);

        UserDetailsAdapter detailsAdapterMock = Mockito.mock(UserDetailsAdapter.class);
        UserController userController = new UserController();
        ReflectionTestUtils.setField(userController,"userDetailsAdapter",detailsAdapterMock);
        userService = Mockito.mock(UserService.class);
        messageSource = Mockito.mock(MessageSource.class);
        ReflectionTestUtils.setField(userController,"userService",userService);
        ReflectionTestUtils.setField(userController,"messageSource",messageSource);
        Mockito.when(detailsAdapterMock.getId()).thenReturn(user.getId());
        Mockito.when(detailsAdapterMock.getUser()).thenReturn(user);
        Mockito.when(messageSource.getMessage("label_user_give_admin_success",new Object[]{},Locale.ENGLISH))
            .thenReturn("success");
        Mockito.when(messageSource.getMessage("label_user_evict_admin_success",new Object[]{},Locale.ENGLISH))
                .thenReturn("success");
        Mockito.when(userService.save(nearlyAdmin,null)).thenReturn(nearlyAdmin);
        Mockito.when(userService.findById(id)).thenReturn(nearlyAdmin);

        ExtendedModelMap modelMap = new ExtendedModelMap();
        HttpServletRequest request = new MockHttpServletRequest();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Locale locale = Locale.ENGLISH;

        String result =
                userController.giveAdminPermissions(id,userController.toUserForm(nearlyAdmin),modelMap,request,redirectAttributes,locale);
        Assert.assertEquals(result,"redirect:/users/"+user.getId()+"/panel");
        Assert.assertEquals(nearlyAdmin.isAdmin(),true);
    }

    private UserAllowToStopSessionForm prepareSampleForm(List<User> sampleUsers){
        UserAllowToStopSessionForm form = new UserAllowToStopSessionForm();
        User user1 = new User("A","B","A.B",null);
        User user2 = new User("C","D","C.D",null);

        form.getUsers().add(new UserEntry("A.B",true));
        form.getUsers().add(new UserEntry("C.D",false));

        sampleUsers.add(user1);
        sampleUsers.add(user2);
        return form;
    }
}
