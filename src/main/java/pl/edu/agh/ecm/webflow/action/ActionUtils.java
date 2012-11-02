package pl.edu.agh.ecm.webflow.action;

import org.dbunit.dataset.datatype.StringIgnoreCaseDataType;
import org.hibernate.type.ListType;
import org.joda.time.DateTime;
import pl.edu.agh.ecm.crawler.CrawlerAppsNames;
import pl.edu.agh.ecm.crawler.CrawlerConnector;
import pl.edu.agh.ecm.domain.CrawlSession;
import pl.edu.agh.ecm.domain.InitUrl;
import pl.edu.agh.ecm.domain.Node;
import pl.edu.agh.ecm.webflow.forms.StartAppsResultOnNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 17.10.12
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public class ActionUtils {

    private static Integer triggerTime = 3000;

    public static String[] maxProcessCountToProperty(CrawlSession crawlSession){
        String[] result = new String[]{"max_process_count",crawlSession.getPolicy().getMaxProcessCount().toString()};
        return result;
    }

    public static String[] bufferSizeToProperty(CrawlSession crawlSession){
        String[] result = new String[]{"buffer_size",crawlSession.getPolicy().getBufferSize().toString()};
        return result;
    }

    public static String[] defaultValidityTimeToProperty(CrawlSession crawlSession){
        String[] result = new String[]{"default_validity_time",crawlSession.getPolicy().getDefaultValidityTime().toString()};
        return result;
    }

    public static String[] remoteManagerNodeToProperty(CrawlSession crawlSession){
        String[] result = new String[]{"remote_manager_server_node",crawlSession.getRemoteManagerServerNode().toString()};
        return result;
    }

    public static String[] sessionIdToProperty(CrawlSession crawlSession){
        String[] result = new String[]{"session_id", crawlSession.getId().toString()};
        return result;
    }

    public static String[] triggerTimeToProperty(){
        String[] result = new String[]{"trigger_time",triggerTime.toString()};
        return result;
    }

    public static String[] initUrlsToProperty(CrawlSession crawlSession,boolean domainManagerNodeConf){
        if (!domainManagerNodeConf){
            return new String[]{"init_urls","[]"};
        }
        Set<InitUrl> initUrlList = crawlSession.getPolicy().getInitUrls();
        int counter = 0,initUrlSize = initUrlList.size();
        StringBuffer stringBuffer = new StringBuffer("[");
        for(InitUrl initUrl : initUrlList){
            String initUrlStr = initUrlToString(initUrl);   //[{..,..},{..,..},{..,..}]
            stringBuffer.append(initUrlStr);
            if (counter != initUrlSize-1){
                stringBuffer.append(",");
            }
            counter++;
        }
        stringBuffer.append("]");
        String[] result = new String[]{"init_urls",stringBuffer.toString()};
        return  result;
    }

    public static String[] contactNodesToProperty(CrawlSession crawlSession){

        List<Node> nodeList = new ArrayList<Node>(crawlSession.getNodes());

        StringBuffer nodeListString = new StringBuffer();
        for (int i=0; i< nodeList.size(); i++){
           Node node = nodeList.get(i);
           nodeListString.append("'"+node.toString()+"'");
           if (i != nodeList.size()-1){
                nodeListString.append(", ");
           }
        }
        String[] result = new String[]{"contact_nodes","["+nodeListString.toString()+"]"};
        return result;
    }

    public static String[] domainManagerNodeToProperty(CrawlSession crawlSession){
        String[] result = new String[]{"domain_manager_node",crawlSession.getDomainManagerNode().toString()};
        return result;
    }

    public static String[][] sessionToProperties(CrawlSession crawlSession,boolean domainManagerNodeConf){
        String[][] result = new String[][]
        {
            contactNodesToProperty(crawlSession),
            domainManagerNodeToProperty(crawlSession),
            maxProcessCountToProperty(crawlSession),
            defaultValidityTimeToProperty(crawlSession),
            bufferSizeToProperty(crawlSession),
            initUrlsToProperty(crawlSession,domainManagerNodeConf),
            //triggerTimeToProperty(),
            sessionIdToProperty(crawlSession),
            remoteManagerNodeToProperty(crawlSession)
        };

        return result;
    }


    public static String[][] sessionNodesToProperties(CrawlSession crawlSession){
        String[][] result = new String[][]
        {
                contactNodesToProperty(crawlSession),
                domainManagerNodeToProperty(crawlSession),
                remoteManagerNodeToProperty(crawlSession)
        };
        return result;
    }

    public static StartAppsResultOnNode getStartSchedulerResultOnNode(String nodeName,String[][] startSessionResults){
        StartAppsResultOnNode result = new StartAppsResultOnNode(nodeName);
        for (String[] singleResult : startSessionResults){
            if (singleResult[0].equals(CrawlerAppsNames.schedulerApp)){
                if (singleResult[1].equals("ok")){
                    result.setSchedulerStarted(true);
                }
            }
        }
        return result;
    }

    public static StartAppsResultOnNode getStartAppsResultOnNode(String nodeName,
                                                                 String[][] properties,CrawlerConnector crawlerConnector){

        StartAppsResultOnNode result = new StartAppsResultOnNode(nodeName);
        result.setSessionManagerStarted(true);

        for (String[] property : properties){
            if (property[0].equals(CrawlerAppsNames.cacheApp)){
                if (property[1].equals("ok")){
                    result.setCacheStarted(true);
                }
                else{
                    result.setCacheStarted(crawlerConnector.pingApp(nodeName,CrawlerAppsNames.cacheApp));
                }
            }
            else if (property[0].equals(CrawlerAppsNames.crawlEventApp)){
                if (property[1].equals("ok")){
                    result.setCrawlEventStarted(true);
                }
                else{
                    result.setCrawlEventStarted(crawlerConnector.pingApp(nodeName,CrawlerAppsNames.crawlEventApp));
                }
            }
            else if (property[0].equals(CrawlerAppsNames.domainManagerApp)){
                if (property[1].equals("ok")){
                    result.setDomainManagerStarted(true);
                }
                else{
                    result.setDomainManagerStarted(crawlerConnector.pingApp(nodeName,CrawlerAppsNames.domainManagerApp));
                }
            }
            else if (property[0].equals(CrawlerAppsNames.sessionManagerApp)){
                if (property[1].equals("error")){
                    result.setSessionManagerStarted(false);
                }
            }
        }

        return result;
    }

    private static String initUrlToString(InitUrl initUrl){
        StringBuffer buffer = new StringBuffer("[");
        String address = initUrl.getAddress();
        buffer.append(getProperty("init_url","\""+address+"\"")); //[{init_url,val}
        buffer.append(",");
        buffer.append(getProperty("width",initUrl.getWidth().toString()));
        buffer.append(","); //[{init_url,val},{width,val},
        buffer.append(getProperty("depth",initUrl.getDepth().toString()));
        buffer.append(","); //[{init_url,val},{width,val},{depth,val},
        buffer.append(getProperty("validity_time",initUrl.getValidityTime().toString()));
        buffer.append("]"); //[{init_url,val},{width,val},{depth,val},{validity_time,val}]
        return buffer.toString();
    }
    private static String getProperty(String key,String value){
        return "{"+key+","+value+"}";
    }

}
