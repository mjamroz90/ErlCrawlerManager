package pl.edu.agh.ecm.service.jpa;

import junit.framework.Assert;
import org.hibernate.type.ListType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.ecm.domain.CrawlSession;
import pl.edu.agh.ecm.domain.Node;
import pl.edu.agh.ecm.domain.Policy;
import pl.edu.agh.ecm.domain.User;
import pl.edu.agh.ecm.service.CrawlSessionService;
import pl.edu.agh.ecm.service.NodeService;
import pl.edu.agh.ecm.service.PolicyService;
import pl.edu.agh.ecm.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 09.10.12
 * Time: 21:13
 * To change this template use File | Settings | File Templates.
 */
public class CrawlSessionServiceImplTest extends AbstractServiceImplTest {

    @Autowired
    CrawlSessionService crawlSessionService;

    @Autowired
    NodeService nodeService;

    @Autowired
    UserService userService;

    @Autowired
    PolicyService policyService;

    List<CrawlSession> crawlSessions = new ArrayList<CrawlSession>();
    User user;
    Node node1;
    Node node2;
    Policy policy;

    @Before
    public void initSession(){

        node1 = new Node("node1","127.0.0.1");
        node2 = new Node("node2","127.0.0.2");
        user = new User("Michal","Jamroz","mjamroz90","haslo");
        user = userService.save(user,null);

        policy = new Policy(25,250,user);
        node1 = nodeService.save(node1);
        node2 = nodeService.save(node2);
        CrawlSession crawlSession = new CrawlSession(node1,node2,policy,user);
        crawlSession.addNode(node1);
        crawlSession.addNode(node2);
        crawlSessions.add(crawlSession);
    }

    @Test
    public void testFindById() throws Exception {

        CrawlSession crawlSession = crawlSessionService.save(crawlSessions.get(0));
    }

    @Test
    public void testFindByIdWithDetail() throws Exception {

    }

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testFindAllWithDetail() throws Exception {

    }

    @Test
    public void testFindByUserStartedWithDetail() throws Exception {

    }

    @Test
    public void testFindByUserFinishedWithDetail() throws Exception {

    }

    @Test
    public void testSave() throws Exception {

        List<CrawlSession> crawlSessionList = crawlSessionService.findAll();
        Assert.assertEquals(crawlSessionList.size(),0);

        CrawlSession crawlSession = crawlSessionService.save(crawlSessions.get(0));
        List<CrawlSession> crawlSessionList1 = crawlSessionService.findAll();
        Assert.assertNotNull(crawlSessionList1);
        Assert.assertEquals(crawlSessionList1.size(),1);
        Assert.assertEquals(crawlSessionList1.get(0).getId(),crawlSession.getId());

        Assert.assertEquals(crawlSessionList1.get(0).getDomainManagerNode().getId(),node1.getId());
        Assert.assertEquals(crawlSessionList1.get(0).getRemoteManagerServerNode().getId(),node2.getId());

        Assert.assertEquals(crawlSessionList1.get(0).getStartedBy().getId(),user.getId());
        for (Node node : crawlSessionList1.get(0).getNodes()){
            Assert.assertNotNull(node.getId());
        }
    }
}
