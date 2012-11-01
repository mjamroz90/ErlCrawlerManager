package pl.edu.agh.ecm.webflow.action;

import org.joda.time.DateTime;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.core.collection.ParameterMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import pl.edu.agh.ecm.crawler.CrawlerConnector;
import pl.edu.agh.ecm.domain.*;
import pl.edu.agh.ecm.service.CrawlSessionService;
import pl.edu.agh.ecm.service.NodeService;
import pl.edu.agh.ecm.webflow.forms.*;
import pl.edu.agh.ecm.webflow.validators.PolicyFormValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private CrawlerConnector crawlerConnector;
    private PolicyFormValidator policyFormValidator;

    public void setPolicyFormValidator(PolicyFormValidator policyFormValidator) {
        this.policyFormValidator = policyFormValidator;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public void setCrawlSessionService(CrawlSessionService crawlSessionService) {
        this.crawlSessionService = crawlSessionService;
    }

    public void setCrawlerConnector(CrawlerConnector crawlerConnector){
        this.crawlerConnector = crawlerConnector;
    }

    public DefineNodesForm initDefineNodesForm(){

        DefineNodesForm form = new DefineNodesForm(nodeService.findAll());
        return form;
    }

    public Event validatePolicyForm(CrawlSessionForm crawlSessionForm, MessageContext messageContext){
        PolicyForm policyForm = crawlSessionForm.getPolicy();
        if (policyFormValidator.validatePolicyForm(policyForm,messageContext) == true){
            return success();
        }
        else{
            return error();
        }
    }

    public Event startCrawlSession(RequestContext requestContext){
       CrawlSession crawlSession = (CrawlSession)requestContext.getFlowScope().get("crawlSession");
        return success();
    }

    public Event validateDefineNodesForm(DefineNodesForm defineNodesForm,MessageContext messageContext){

        boolean foundMarkedNode = false;
        for (NodeEntry nodeEntry : defineNodesForm.getNodeEntryList()){
            if (nodeEntry.isUsed()){
                foundMarkedNode = true;
            }
        }
        if (!foundMarkedNode){
            messageContext.addMessage(new MessageBuilder().error().source("nodeEntryList")
                    .code("label_session_node_unmarked").build());
            return error();
        }
        else{
            return success();
        }
    }

    public CrawlSessionForm initCrawlSessionForm(){
        CrawlSessionForm form = new CrawlSessionForm();
        form.setNewInitUrl(new InitUrlForm());
        form.setPolicy(new PolicyForm());
        form.setCurrentTime(DateTime.now());
        return form;
    }

    public Event isAnySessionStarted(RequestContext requestContext){

        if (crawlSessionService.getRunningSession() == null){
            return new Event(this,"stopped");
        }
        else{
            return new Event(this,"running");
        }

    }

    public Event prepareSessionObject(RequestContext context){

        CrawlSessionForm crawlSessionForm = (CrawlSessionForm)context.getFlowScope().get("crawlSessionForm");
        CrawlSession crawlSession = (CrawlSession)context.getFlowScope().get("crawlSession");
        fillCrawlSessionObject(crawlSessionForm,crawlSession);
        context.getFlowScope().put("crawlSession",crawlSession);
        return success();
    }

    public void addInitUrlToCrawlSession(CrawlSessionForm crawlSessionForm){

        InitUrlForm initUrlForm = crawlSessionForm.getNewInitUrl();
        crawlSessionForm.getInitUrlFormList().add(
                new InitUrlForm(initUrlForm.getAddress(),initUrlForm.getWidth(),
                        initUrlForm.getDepth(),initUrlForm.getValidityDate()
                ));
    }

    public void putNodesIntoSession(DefineNodesForm defineNodesForm,RequestContext context){
        CrawlSession crawlSession = initSessionWithNodes(defineNodesForm.getDomainManagerNode(),
                defineNodesForm.getNodeEntryList());
        context.getFlowScope().put("crawlSession",crawlSession);
    }

    public Event launchCrawler(RequestContext context){
        CrawlSession crawlSession =(CrawlSession)context.getFlowScope().get("crawlSession",CrawlSession.class);
        //W tym momencie, w obiekcie sesji mamy pola domainManagerNode, oraz nodes
        String[][] firstConfig = ActionUtils.sessionNodesToProperties(crawlSession);
        StartCrawlerResults crawlerResults = startCrawlerOnNode(firstConfig,crawlSession);
        context.getFlowScope().put("appsView",crawlerResults);
        return success();
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

    private StartCrawlerResults startCrawlerOnNode(String[][] properties,CrawlSession crawlSession){

        StartCrawlerResults crawlerResults = new StartCrawlerResults();
        Set<Node> nodeList = crawlSession.getNodes();
        for (Node node : nodeList){
            String nodeName = node.toString();
            String[][] result = crawlerConnector.startCrawlerOnNode(nodeName,properties);
            StartAppsResultOnNode resultOnNode =
                    ActionUtils.getStartAppsResultOnNode(nodeName,
                            result,crawlerConnector);
            crawlerResults.addStartAppsResultOnNode(resultOnNode);
        }
        crawlerResults.setCanGoToNextStep(crawlSession.getDomainManagerNode().toString());
        return crawlerResults;
    }

    private void fillCrawlSessionObject(CrawlSessionForm crawlSessionForm,CrawlSession session){

        PolicyForm policyForm = crawlSessionForm.getPolicy();
        User currentUser = ((UserDetailsAdapter)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Policy sessionPolicy = new Policy(policyForm.getMaxProcessCount(),
                policyForm.getBufferSize(),currentUser);

        Long time = policyForm.getDefaultValidityDate().toStandardDuration().getMillis();
        sessionPolicy.setDefaultValidityTime(time);
        List<InitUrlForm> initUrlFormList = crawlSessionForm.getInitUrlFormList();
        for (InitUrlForm initUrlForm : initUrlFormList){
            InitUrl initUrl = getInitUrlFromForm(initUrlForm);
            initUrl.setPolicy(sessionPolicy);
            sessionPolicy.addInitUrl(initUrl);
        }
        session.setStartedBy(currentUser);
        session.setPolicy(sessionPolicy);
    }

    private InitUrl getInitUrlFromForm(InitUrlForm initUrlForm){

        InitUrl initUrl = new InitUrl();
        initUrl.setAddress(initUrlForm.getAddress());
        initUrl.setWidth(initUrlForm.getWidth());
        initUrl.setDepth(initUrlForm.getDepth());
        Long time = initUrlForm.getValidityDate().toStandardDuration().getMillis();
        initUrl.setValidityTime(time);
        return initUrl;
    }
}
