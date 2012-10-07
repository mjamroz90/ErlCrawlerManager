package pl.edu.agh.ecm.web.controller;

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
import pl.edu.agh.ecm.service.UserService;
import pl.edu.agh.ecm.web.form.UserForm;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
}
