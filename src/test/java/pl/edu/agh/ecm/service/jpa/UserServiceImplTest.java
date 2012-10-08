package pl.edu.agh.ecm.service.jpa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.ecm.domain.User;
import pl.edu.agh.ecm.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 04.09.12
 * Time: 20:15
 * To change this template use File | Settings | File Templates.
 */

public class UserServiceImplTest extends AbstractServiceImplTest {

    @Autowired
    UserService userService;

    private List<User> users = new ArrayList<User>();

    @Before
    public void initUsers(){
        User user = new User("Michal","Jamroz","mjamroz90","haslo");
        user.setAdmin(false);
        users.add(user);
    }

    @Test
    public void testFindById() throws Exception {

        User user = users.get(0);
        user = userService.save(user,null);
        User newUser = userService.findById(user.getId());
        Assert.assertEquals(newUser.getId(),user.getId());
        Assert.assertEquals(newUser.getLogin(),user.getLogin());

    }

    @Test
    public void testFindByIdWithDetail() throws Exception {

        User user = users.get(0);
        user = userService.save(user,null);
        User newUser = userService.findByIdWithDetail(user.getId());
        Assert.assertNotNull(newUser.getAllowedToStopSession());
        Assert.assertNotNull(newUser.getAllowingToStopSession());
        Assert.assertEquals(newUser.getId(),user.getId());
        Assert.assertEquals(newUser.getLogin(),user.getLogin());

    }

    @Test
    public void testFindByLogin() throws Exception {

        User user = users.get(0);
        user = userService.save(user,null);
        User newUser = userService.findByLogin(user.getLogin());
        Assert.assertEquals(newUser.getLogin(),user.getLogin());
        Assert.assertEquals(newUser.getId(),user.getId());
    }

    @Test
    public void testFindByLoginWithDetail() throws Exception {

        User user = users.get(0);
        user = userService.save(user,null);
        User newUser = userService.findByLoginWithDetail(user.getLogin());
        Assert.assertEquals(newUser.getLogin(),user.getLogin());
        Assert.assertEquals(newUser.getId(),user.getId());
        Assert.assertNotNull(newUser.getAllowedToStopSession());
        Assert.assertNotNull(newUser.getAllowingToStopSession());
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> foundUsers = userService.findAll();
        Assert.assertEquals(foundUsers.size(),0);
        User savedUser = userService.save(users.get(0),null);
        Assert.assertNotNull(savedUser.getId());

        User otherUser = new User("Michal1","Jamroz1","mjamroz901","haslo1");
        userService.save(otherUser,null);
        savedUser.addAllowedToStopSession(otherUser);
        savedUser = userService.save(savedUser,null);

        Assert.assertNotNull(otherUser.getId());
        Assert.assertEquals(savedUser.getAllowedToStopSession().size(),1);
        Assert.assertTrue(savedUser.isAllowedToStopSession(otherUser.getLogin()));

        List<User> newFoundUsers = userService.findAll();
        Assert.assertNotNull(newFoundUsers);
        Assert.assertEquals(newFoundUsers.size(),2);
    }

//    @Test
//    public void testFindAllWithDetail() throws Exception {
//
//    }

    @Test
    public void testSave() throws Exception {

        User userToSave = users.get(0);
        Assert.assertNull(userToSave.getId());
        Assert.assertEquals(0,userService.findAll().size());
        User savedUser = userService.save(userToSave,null);
        Assert.assertNotNull(savedUser.getId());
        Assert.assertEquals(userService.findAll().size(),1);
    }
}
