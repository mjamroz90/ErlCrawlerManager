package pl.edu.agh.ecm.webflow.forms;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.ecm.domain.Node;
import pl.edu.agh.ecm.service.NodeService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 13.10.12
 * Time: 20:48
 * To change this template use File | Settings | File Templates.
 */
public class DefineNodesForm implements Serializable {

    private String domainManagerNode;
    private List<NodeEntry> nodeEntryList = new ArrayList<NodeEntry>();
    private Map<String,String> nodeNames = new HashMap<String, String>();

    public DefineNodesForm(){}

    public DefineNodesForm(List<Node> nodes){
        initNodes(nodes);
    }

    public String getDomainManagerNode() {
        return domainManagerNode;
    }

    public void setDomainManagerNode(String domainManagerNode) {
        this.domainManagerNode = domainManagerNode;
    }

    public List<NodeEntry> getNodeEntryList() {
        return nodeEntryList;
    }

    public void setNodeEntryList(List<NodeEntry> nodeEntryList) {
        this.nodeEntryList = nodeEntryList;
    }

    public Map<String, String> getNodeNames() {
        return nodeNames;
    }

    public void setNodeNames(Map<String, String> nodeNames) {
        this.nodeNames = nodeNames;
    }

    private void initNodes(List<Node> nodes){
        for (Node node : nodes){
            NodeEntry nodeEntry = new NodeEntry();
            nodeEntry.setNodeName(node.toString());
            nodeEntry.setUsed(false);
            nodeEntryList.add(nodeEntry);
            nodeNames.put(node.toString(),node.toString());
        }
    }
}
