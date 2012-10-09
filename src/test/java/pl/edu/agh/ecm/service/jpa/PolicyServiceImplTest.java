package pl.edu.agh.ecm.service.jpa;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.ecm.domain.InitUrl;
import pl.edu.agh.ecm.domain.Policy;
import pl.edu.agh.ecm.domain.User;
import pl.edu.agh.ecm.service.PolicyService;
import pl.edu.agh.ecm.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 09.10.12
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class PolicyServiceImplTest extends AbstractServiceImplTest {

    @Autowired
    PolicyService policyService;

    @Autowired
    UserService userService;

    List<Policy> policies = new ArrayList<Policy>();
    User user = new User("wacek","michal","mjamroz90","haslo");

    @Before
    public void initPolicies(){
        user = userService.save(user,null);
        Policy policy = new Policy(10,250,user);
        InitUrl initUrl1 = new InitUrl("http://www.google.pl",2,3,2400);
        initUrl1.setPolicy(policy);
        InitUrl initUrl2 = new InitUrl("http://www.amazon.com",3,4,2456);
        initUrl2.setPolicy(policy);
        policy.addInitUrl(initUrl1);
        policy.addInitUrl(initUrl2);
        policies.add(policy);
    }

    @Test
    public void testFindById() throws Exception {

        Policy policy = policies.get(0);
        Assert.assertNull(policy.getId());
        policy = policyService.save(policy);
        Assert.assertNotNull(policy.getId());
        Policy foundPolicy = policyService.findById(policy.getId());
        Assert.assertEquals(foundPolicy.getId(),policy.getId());
        Assert.assertEquals(foundPolicy.getBufferSize(),policy.getBufferSize());
        Assert.assertEquals(foundPolicy.getMaxProcessCount(),policy.getMaxProcessCount());
    }

    @Test
    public void testFindByIdWithDetails() throws Exception{

        Policy policy = policies.get(0);
        Assert.assertNull(policy.getId());
        policy = policyService.save(policy);
        Assert.assertNotNull(policy.getId());
        Policy foundPolicy = policyService.findById(policy.getId());
        Assert.assertNotNull(foundPolicy.getInitUrls());
        Assert.assertEquals(foundPolicy.getInitUrls().size(),2);
    }

    @Test
    public void testFindBycreatedBy() throws Exception {

        Policy policy = policies.get(0);
        policy = policyService.save(policy);
        List<Policy> foundPolicies = policyService.findBycreatedBy(user.getId());
        Assert.assertNotNull(foundPolicies);
        Assert.assertEquals(foundPolicies.size(),1);
        Assert.assertEquals(foundPolicies.get(0).getId(),user.getId());
    }

    @Test
    public void testFindAll() throws Exception {

        Policy policy = policies.get(0);
        Policy otherPolicy = new Policy(23,230,user);
        policyService.save(policy);
        policyService.save(otherPolicy);
        List<Policy> policyList = policyService.findAll();
        Assert.assertNotNull(policyList);
        Assert.assertEquals(policyList.size(),2);
        Assert.assertEquals(policyList.get(0).getId(),policy.getId());
        Assert.assertEquals(policyList.get(1).getId(),otherPolicy.getId());
    }

    @Test
    public void testSave() throws Exception {

        List<Policy> policies1 = policyService.findAll();
        Assert.assertEquals(policies1.size(),0);
        Policy policy = policies.get(0);
        policy = policyService.save(policy);
        Assert.assertNotNull(policy.getId());
        List<Policy> newPolicies = policyService.findAll();
        Assert.assertEquals(newPolicies.size(),1);
        Assert.assertEquals(newPolicies.get(0).getInitUrls().size(),2);
        for (InitUrl url : newPolicies.get(0).getInitUrls()){
            Assert.assertNotNull(url.getId());
        }
    }
}
