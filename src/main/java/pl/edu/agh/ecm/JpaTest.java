package pl.edu.agh.ecm;

import org.joda.time.DateTime;
import org.springframework.context.support.GenericXmlApplicationContext;
import pl.edu.agh.ecm.domain.*;
import pl.edu.agh.ecm.service.CrawlSessionService;
import pl.edu.agh.ecm.service.NodeService;
import pl.edu.agh.ecm.service.PolicyService;
import pl.edu.agh.ecm.service.UserService;

import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 04.09.12
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 */
public class JpaTest {

    public static void main(String[] args){

        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:datasource-tx-jpa.xml");
        ctx.refresh();

        //User u = prepareUser();
        UserService userService = ctx.getBean("userService", UserService.class);
        CrawlSessionService sessionService = ctx.getBean("crawlSessionService", CrawlSessionService.class);
        NodeService nodeService = ctx.getBean("nodeService", NodeService.class);
        PolicyService policyService  = ctx.getBean("policyService", PolicyService.class);

        //u = userService.save(u);

//        List<User> users = userService.findAll();
//        List<User> usersWithDetails = userService.findAllWithDetail();

        //List<Node> nodes = prepareNodes();
        //saveNodes(nodes, nodeService);

        List<Node> foundNodes = nodeService.findAll();
        List<Node> nodesFoundByName =  nodeService.findByName("wacek");
        //CrawlSession crawlSession = prepareSession(nodes,userService.findAll().get(0));

        //saveUsers(userService);
        List<User> allUsers = userService.findAll();
        List<User> allUsersWithDetail = userService.findAllWithDetail();
        List<User> usersByLogin = userService.findByLogin("mjamroz");
        List<User> usersByLoginWithDetail = userService.findByLoginWithDetail("mjamroz");

        //savePolicies(policyService, userService);
        List<Policy> allPolicies = policyService.findAll();
        List<Policy> foundByUrl = policyService.findByinitUrl("http://www.google.pl");
        List<Policy> foundCreatedBy = policyService.findBycreatedBy(usersByLogin.get(0).getId());

        //sessionService.save(crawlSession);
        CrawlSession crawlSession = prepareSession(foundNodes,allUsers.get(0));
        //sessionService.save(crawlSession);

        List<CrawlSession> allSessions = sessionService.findAll();
        List<CrawlSession> allSessionsWithDetail = sessionService.findAllWithDetail();
        List<CrawlSession> sessionsByStarted = sessionService.findByUserStartedWithDetail(allUsers.get(0).getId());
    }

    public static User saveUsers(UserService service){

        User u = new User("Michal", "Jamroz","mjamroz", "albeniz909");
        u.setAdmin(true);
        service.save(u);

        User u1 = new User("wacek", "jakistam", "wjakistam", "haslo");
        u1.setAdmin(false);
        service.save(u1);

        User u2 = new User("kamila", "jakastam", "kjakastam", "haslokam");
        u2.setAdmin(false);
        service.save(u2);

        u.addAllowedToStopSession(u1);
        u.addAllowedToStopSession(u2);
        u.addAllowingToStopSession(u1);
        service.save(u);

        return u;
    }


    public static void saveNodes(List<Node> nodes, NodeService service){

        try{
            for (Node node : nodes){
                  service.save(node);
            }
        }
        catch (Exception e){}

    }

    public static void savePolicies(PolicyService policyService,UserService userService){

        User created = userService.findAll().get(0);
        CrawlParams crawlParams = new CrawlParams(25, 1000);

        Policy policy = new Policy("http://www.google.pl",2,3,created);
        policy.setCrawlParams(crawlParams);
        Policy policy1 = new Policy("http://www.nowyekran.pl",3,4,created);
        policy1.setCrawlParams(new CrawlParams(25,1233));
        Policy policy2 = new Policy("http://www.prokapitalizm.pl",1,3,created);
        policy2.setCrawlParams(new CrawlParams(25,12334));

        policyService.save(policy);
        policyService.save(policy1);
        policyService.save(policy2);
    }

    public static void listUsers(List<User> users){

            System.out.println("");
            System.out.println("Listing contacts without details:");
            for (User contact: users) {
                System.out.println(contact);
                System.out.println();
            }
    }

    public static CrawlSession prepareSession(List<Node> nodes,User startedBy)
    {
        CrawlSession crawlSession = new CrawlSession(nodes.get(0),nodes.get(1),
                preparePolicy(startedBy),startedBy);

        crawlSession.setNodes(new HashSet<Node>(nodes));
        crawlSession.setStarted(new DateTime(Calendar.getInstance().getTime()));
        return crawlSession;
    }

    public static Policy preparePolicy(User startedBy)
    {
        Policy policy = new Policy("http://www.google.pl",2,3,startedBy);
        policy.setCrawlParams(new CrawlParams(20,20));

        return policy;
    }

    public static List<Node> prepareNodes(){
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node("michal","127.0.0.1"));
        nodes.add(new Node("wacek","backrub2.iisg.agh.edu.pl"));
        nodes.add(new Node("wluk", "backrub2.iisg.agh.edu.pl"));
        return nodes;
    }

}
