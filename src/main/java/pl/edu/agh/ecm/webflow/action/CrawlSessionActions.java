package pl.edu.agh.ecm.webflow.action;

import org.apache.tiles.context.MapEntry;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.core.collection.ParameterMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import pl.edu.agh.ecm.domain.CrawlSession;
import pl.edu.agh.ecm.domain.Node;
import pl.edu.agh.ecm.service.CrawlSessionService;
import pl.edu.agh.ecm.service.NodeService;
import pl.edu.agh.ecm.webflow.forms.DefineNodesForm;
import pl.edu.agh.ecm.webflow.forms.NodeEntry;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 13.10.12
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class CrawlSessionActions extends MultiAction {

    private CrawlSessionService crawlSessionService;
    private NodeService nodeService;

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public void setCrawlSessionService(CrawlSessionService crawlSessionService) {
        this.crawlSessionService = crawlSessionService;
    }

    public Event isAnySessionStarted(RequestContext requestContext){

        if (crawlSessionService.getRunningSession() == null){
            DefineNodesForm nodesForm = new DefineNodesForm(nodeService.findAll());
            requestContext.getFlowScope().put("defineNodesForm",nodesForm);
            return new Event(this,"stopped");
        }
        else{
            return new Event(this,"running");
        }

    }

    public Event launchCrawler(RequestContext context){
        ParameterMap parameterMap = context.getRequestParameters();
        DefineNodesForm defineNodesForm = (DefineNodesForm)context.getFlowScope().get("defineNodesForm");
        return success();
    }

    public Event putNodesIntoSession(RequestContext context){

        ParameterMap parameterMap = context.getRequestParameters();
        List<NodeEntry> nodeEntries = getNodeEntriesFromParamMap(parameterMap);
        String domainManagerNode = parameterMap.get("domainManagerNode");
        CrawlSession crawlSession = initSessionWithNodes(domainManagerNode,nodeEntries);
        context.getFlowScope().put("crawlSession",crawlSession);
        return success();
    }

    private List<NodeEntry> getNodeEntriesFromParamMap(ParameterMap parameterMap){

        Map<String,String> stringMap = parameterMap.asMap();
        List<NodeEntry> result = new ArrayList<NodeEntry>();
        Pattern nodeNamePattern = Pattern.compile("(nodeEntryList\\[[0-9]+\\])\\.nodeName");

        for (Map.Entry<String,String> entry : stringMap.entrySet()){
            Matcher matcher = nodeNamePattern.matcher(entry.getKey());
            if (matcher.find()){
                String nodeName = entry.getValue();
                String paramNamePrefix = matcher.group(1);
                String nodeUsedStr = stringMap.get(paramNamePrefix+".used");
                Boolean nodeUsed = new Boolean(nodeUsedStr);

                NodeEntry nodeEntry = new NodeEntry(nodeName,nodeUsed);
                result.add(nodeEntry);
            }
        }

        return result;
    }

    private CrawlSession initSessionWithNodes(String domainManagerNode,List<NodeEntry> nodeEntryList){

        CrawlSession crawlSession = new CrawlSession();
        String[] nameAddress = domainManagerNode.split("@");
        Node domainManagerNodeObj = nodeService.findByNameAndAddress(nameAddress[0],nameAddress[1]);
        crawlSession.setDomainManagerNode(domainManagerNodeObj);

        for (NodeEntry nodeEntry : nodeEntryList){
            if (nodeEntry.isUsed()){
                nameAddress = nodeEntry.getNodeName().split("@");
                Node nodeObj = nodeService.findByNameAndAddress(nameAddress[0],nameAddress[1]);
                crawlSession.addNode(nodeObj);
            }
        }

        return crawlSession;
    }
}
