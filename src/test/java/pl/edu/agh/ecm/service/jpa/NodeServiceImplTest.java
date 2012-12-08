package pl.edu.agh.ecm.service.jpa;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.ecm.domain.Node;
import pl.edu.agh.ecm.service.NodeService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 08.12.12
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
public class NodeServiceImplTest extends AbstractServiceImplTest {

    @Autowired
    private NodeService nodeService;

    private Node node1;
    private Node node2;

    @Before
    public void init(){
        node1 = new Node("node1","127.0.0.1");
        node2 = new Node("node2","127.0.0.2");
        nodeService.save(node1);
        nodeService.save(node2);
    }

    @Test
    public void testFindById() throws Exception {
        Node foundNode = nodeService.findById(node1.getId());
        Assert.assertNotNull(foundNode);
        Assert.assertEquals(foundNode.getId(),node1.getId());
        Assert.assertEquals(foundNode.getName(),node1.getName());
        Assert.assertEquals(foundNode.getAddress(),node1.getAddress());
    }

    @Test
    public void testFindAll() throws Exception {
        List<Node> foundNodes = nodeService.findAll();
        Assert.assertEquals(foundNodes.size(),2);
        for (Node foundNode : foundNodes){
            Assert.assertNotNull(foundNode);
            Assert.assertTrue(foundNode.equals(node1) || foundNode.equals(node2));
        }
    }

    @Test
    public void testFindByName() throws Exception {
        List<Node> foundNodes = nodeService.findByName("node1");
        Assert.assertEquals(foundNodes.size(),1);
        Assert.assertTrue(foundNodes.get(0).equals(node1));
    }

    @Test
    public void testFindByAddress() throws Exception {
        List<Node> foundNodes = nodeService.findByAddress("127.0.0.2");
        Assert.assertEquals(foundNodes.size(),1);
        Assert.assertTrue(foundNodes.get(0).equals(node2));
    }

    @Test
    public void testFindByNameAndAddress() throws Exception {

        Node foundNode = nodeService.findByNameAndAddress("node1","127.0.0.1");
        Assert.assertNotNull(foundNode);
        Assert.assertEquals(foundNode.getId(),node1.getId());
        Node foundNode1 = nodeService.findByNameAndAddress("node2","127.0.0.2");
        Assert.assertNotNull(foundNode1);
        Assert.assertEquals(foundNode1.getId(),node2.getId());
    }

    @Test
    public void testFindByPage() throws Exception {

    }

    @Test
    public void testSave() throws Exception {
        List<Node> foundNodes = nodeService.findAll();
        Assert.assertEquals(foundNodes.size(),2);
        for (Node foundNode : foundNodes){
            Assert.assertNotNull(foundNode);
            Assert.assertTrue(foundNode.equals(node1) || foundNode.equals(node2));
        }
    }

    @Test
    public void testRemove() throws Exception {
        nodeService.remove(node1);
        List<Node> foundNodes = nodeService.findAll();
        Assert.assertEquals(foundNodes.size(),1);
        Assert.assertTrue(foundNodes.get(0).equals(node2));
    }
}
