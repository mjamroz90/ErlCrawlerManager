package pl.edu.agh.ecm.web.controller;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.ecm.test.config.ControllerTestConfig;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 06.10.12
 * Time: 18:25
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ControllerTestConfig.class})
@ActiveProfiles("test")
public class AbstractControllerTest {

}
