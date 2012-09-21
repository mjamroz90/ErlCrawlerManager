package pl.edu.agh.ecm.service;

import pl.edu.agh.ecm.domain.Node;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 06.09.12
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public interface NodeService {

    public Node findById(Long id);

    public List<Node> findAll();

    public List<Node> findByName(String name);

    public List<Node> findByAddress(String address);

    public Node save(Node node);

}
