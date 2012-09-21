package pl.edu.agh.ecm.service.jpa;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.ecm.domain.Node;
import pl.edu.agh.ecm.repository.NodeRepository;
import pl.edu.agh.ecm.service.NodeService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 06.09.12
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */

@Service("nodeService")
@Repository
@Transactional
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeRepository nodeRepository;

    @Transactional(readOnly = true)
    public Node findById(Long id) {
        return nodeRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Node> findAll() {
        return Lists.newArrayList(nodeRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<Node> findByName(String name) {
        return Lists.newArrayList(nodeRepository.findByName(name));
    }

    @Transactional(readOnly = true)
    public List<Node> findByAddress(String address) {
        return Lists.newArrayList(nodeRepository.findByAddress(address));
    }

    public Node save(Node node) {
        return nodeRepository.save(node);
    }
}
