package pl.edu.agh.ecm.service.jpa;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.ecm.test.config.ServiceTestConfig;
import pl.edu.agh.ecm.test.listener.ServiceTestExecutionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 07.10.12
 * Time: 20:40
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@TestExecutionListeners({ServiceTestExecutionListener.class})
@ActiveProfiles("test")
public class AbstractServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

}
