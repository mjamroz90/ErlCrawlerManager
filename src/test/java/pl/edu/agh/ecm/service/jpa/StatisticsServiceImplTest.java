package pl.edu.agh.ecm.service.jpa;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.ecm.domain.*;
import pl.edu.agh.ecm.service.CrawlSessionService;
import pl.edu.agh.ecm.service.NodeService;
import pl.edu.agh.ecm.service.StatisticsService;
import pl.edu.agh.ecm.service.UserService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 12.12.12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */

public class StatisticsServiceImplTest extends AbstractServiceImplTest {

    private Statistics sampleStats;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private CrawlSessionService crawlSessionService;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private UserService userService;

    private Node domainNode;
    private CrawlSession crawlSession;

    @Before
    public void initStats(){
        sampleStats = new Statistics();
        domainNode = new Node("node1","127.0.0.1");
        domainNode = nodeService.save(domainNode);
        User user = new User("A","B","A.B","haslo");
        user = userService.save(user,null);
        crawlSession = new CrawlSession(domainNode,domainNode,new Policy(150,15000,user),user);
        crawlSession = crawlSessionService.save(crawlSession);
        sampleStats.setCrawlSession(crawlSession);
        sampleStats.setNode(domainNode);
    }

    @Test
    public void testDelete() throws Exception {
        Assert.assertNull(sampleStats.getId());
        sampleStats = statisticsService.save(sampleStats);
        Assert.assertNotNull(sampleStats.getId());
        statisticsService.delete(sampleStats);
        sampleStats.setId(null);
        Assert.assertNull(sampleStats.getId());
        Assert.assertEquals(statisticsService.findAll().size(),0);
    }

    @Test
    public void testSave() throws Exception {
        Assert.assertNull(sampleStats.getId());
        Statistics tempStats = sampleStats;
        sampleStats = statisticsService.save(sampleStats);
        Assert.assertNotNull(sampleStats.getId());
        Assert.assertEquals(statisticsService.findAll().size(),1);
        statisticsService.delete(sampleStats);
        sampleStats.setId(null);
        Assert.assertNull(sampleStats.getId());
    }

    @Test
    public void testFindStatisticsByNode() throws Exception {

        Assert.assertNotNull(domainNode.getId());
        sampleStats = statisticsService.save(sampleStats);
        List<Statistics> foundStats = statisticsService.findStatisticsByNode(domainNode.getId());
        Assert.assertEquals(foundStats.size(),1);
        Assert.assertEquals(foundStats.get(0).getId(),sampleStats.getId());
        statisticsService.delete(sampleStats);
        sampleStats.setId(null);
        Assert.assertNull(sampleStats.getId());
    }

    @Test
    public void testFindStatisticsBySession() throws Exception {

        Assert.assertNotNull(domainNode.getId());
        sampleStats = statisticsService.save(sampleStats);
        List<Statistics> foundStats = statisticsService.findStatisticsBySession(crawlSession.getId());
        Assert.assertEquals(foundStats.size(),1);
        Assert.assertEquals(foundStats.get(0).getId(),sampleStats.getId());
        statisticsService.delete(sampleStats);
        sampleStats.setId(null);
        Assert.assertNull(sampleStats.getId());
    }
}
