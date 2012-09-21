package pl.edu.agh.ecm.service.jpa;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.ecm.service.UserService;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 04.09.12
 * Time: 20:15
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:datasource-tx-jpa-test.xml"})
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void testFindById() throws Exception {

    }

    @Test
    public void testFindByIdWithDetail() throws Exception {

    }

    @Test
    public void testFindByLogin() throws Exception {

    }

    @Test
    public void testFindByLoginWithDetail() throws Exception {

    }

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testFindAllWithDetail() throws Exception {

    }

    @Test
    public void testSave() throws Exception {

    }
}
