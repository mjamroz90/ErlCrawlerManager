package pl.edu.agh.ecm.webflow.action;

import org.joda.time.DateTime;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import pl.edu.agh.ecm.web.form.Message;
import pl.edu.agh.ecm.web.util.TimeUtils;
import pl.edu.agh.ecm.webflow.forms.*;
import pl.edu.agh.ecm.webflow.validators.PolicyFormValidator;

import java.util.*;
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
    private MessageSource messageSource;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

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
       crawlSession.setFinishedBy(null);
       crawlSession.setFinished(null);
       crawlSession = crawlSessionService.save(crawlSession);
       StartCrawlerResults startSessionResults = startSessionOnNodes(crawlSession);
       if (startSessionResults.isSessionStartedSuccessfully()){
            requestContext.getFlowScope().put("sessionId", crawlSession.getId());
            return success();
       }
       else{
            Locale locale = LocaleContextHolder.getLocale();
            requestContext.getViewScope().put("message",new Message("error",
                    messageSource.getMessage("label_session_start_failure",new Object[]{},locale )));
            if (crawlSession.getId() == null){
                crawlSessionService.delete(crawlSession);
            }
            else{
                CrawlSession originalSession = (CrawlSession)requestContext.getFlowScope().get("originalCrawlSesion");
                crawlSessionService.save(originalSession);
            }
            return error();
       }
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

    public CrawlSessionForm initCrawlSessionForm(RequestContext context){
        CrawlSession crawlSession = (CrawlSession)context.getFlowScope().get("crawlSession");
        CrawlSessionForm form = null;
        if (crawlSession.getId() != null){
            form = fillCrawlSessionForm(crawlSession);
        }
        else{
            form = new CrawlSessionForm();
            form.setNewInitUrl(new InitUrlForm());
            form.setPolicy(new PolicyForm());
            form.setCurrentTime(DateTime.now());
        }
        return form;
    }

    public Event isAnySessionStarted(RequestContext requestContext){

        CrawlSession crawlSession;
        ParameterMap parameterMap = requestContext.getRequestParameters();
        if ((crawlSession = getSessionStarted(requestContext)) != null){
            requestContext.getFlowScope().put("sessionId",crawlSession.getId());
            return new Event(this,"running");
        }
        else if (parameterMap.contains("resumedSessionId")){
            Long sessionId = parameterMap.getLong("resumedSessionId");
            crawlSession = crawlSessionService.findByIdWithDetail(sessionId);
            requestContext.getFlowScope().put("originalCrawlSession",crawlSession);
            requestContext.getFlowScope().put("crawlSession",crawlSession);
            requestContext.getFlowScope().put("resumedSession",true);
            return new Event(this,"resumed");
        }
        else{
            return new Event(this,"stopped");
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
        CrawlSession startedSession;
        if ((startedSession = getSessionStarted(context)) != null){
            context.getFlowScope().put("sessionId", startedSession.getId());
            return new Event(this,"running");
        }
        CrawlSession crawlSession =(CrawlSession)context.getFlowScope().get("crawlSession",CrawlSession.class);
        //W tym momencie, w obiekcie sesji mamy pola domainManagerNode, oraz nodes
        String[][] firstConfig = ActionUtils.sessionNodesToProperties(crawlSession,crawlerConnector);
        StartCrawlerResults crawlerResults = startCrawlerOnNode(firstConfig,crawlSession);
        context.getFlowScope().put("appsView",crawlerResults);
        return success();
    }

    public void clearNodes(RequestContext context){
        CrawlSession crawlSession = (CrawlSession)context.getFlowScope().get("crawlSession");
        crawlSession.setDomainManagerNode(null);
        crawlSession.getNodes().clear();
    }

    public void clearSessionParams(RequestContext context){
        CrawlSessionForm crawlSessionForm = (CrawlSessionForm)context.getFlowScope().get("crawlSessionForm");
        CrawlSession crawlSession = (CrawlSession)context.getFlowScope().get("crawlSession");
        crawlSessionForm.clearAll();
        crawlSession.setPolicy(new Policy());
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

    private StartCrawlerResults startSessionOnNodes(CrawlSession crawlSession){
        String[][] domainManagerConf = ActionUtils.sessionToProperties(crawlSession,true,crawlerConnector);
        String[][] ordinaryConf = ActionUtils.sessionToProperties(crawlSession,false,crawlerConnector);
        Set<Node> nodeList = crawlSession.getNodes();
        StartCrawlerResults result = new StartCrawlerResults();

        for (Node node : nodeList){
            String nodeName = node.toString();
            String[][] startSessionresult = null;
            if (nodeName.equals(crawlSession.getDomainManagerNode().toString())){
                startSessionresult = crawlerConnector.startSessionOnNode(nodeName,domainManagerConf);
            }
            else{
                startSessionresult = crawlerConnector.startSessionOnNode(nodeName,ordinaryConf);
            }
            StartAppsResultOnNode resultOnNode = ActionUtils.getStartSchedulerResultOnNode(nodeName,startSessionresult);
            result.addStartAppsResultOnNode(resultOnNode);
        }
        result.setSessionStartStatus();
        return result;
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
        //session.setRemoteManagerServerNode(remoteManagerNode);
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

    private CrawlSession getSessionStarted(RequestContext context){
        CrawlSession crawlSession = crawlSessionService.getRunningSession();
        return crawlSession;
    }

    //uzywana tylko w przypadku wznawiania sesji
    private CrawlSessionForm fillCrawlSessionForm(CrawlSession crawlSession){
        CrawlSessionForm crawlSessionForm = new CrawlSessionForm();
        PolicyForm policyForm = new PolicyForm();
        policyForm.setBufferSize(crawlSession.getPolicy().getBufferSize());
        policyForm.setDefaultValidityDate(TimeUtils.getTimeLongAsPeriod(crawlSession.getPolicy().getDefaultValidityTime()));
        policyForm.setMaxProcessCount(crawlSession.getPolicy().getMaxProcessCount());

        List<InitUrlForm> initUrlFormList = new ArrayList<InitUrlForm>();
        for (InitUrl initUrl : crawlSession.getPolicy().getInitUrls()){
            InitUrlForm initUrlForm = new InitUrlForm();
            initUrlForm.setAddress(initUrl.getAddress());
            initUrlForm.setDepth(initUrl.getDepth());
            initUrlForm.setWidth(initUrl.getWidth());
            initUrlForm.setValidityDate(TimeUtils.getTimeLongAsPeriod(initUrl.getValidityTime()));

            initUrlFormList.add(initUrlForm);
        }

        crawlSessionForm.setInitUrlFormList(initUrlFormList);
        crawlSessionForm.setNewInitUrl(new InitUrlForm());
        crawlSessionForm.setPolicy(policyForm);

        return crawlSessionForm;
    }
}
