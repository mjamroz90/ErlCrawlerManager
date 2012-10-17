package pl.edu.agh.ecm.webflow.action;

import org.dbunit.dataset.datatype.StringIgnoreCaseDataType;
import pl.edu.agh.ecm.crawler.CrawlerAppsNames;
import pl.edu.agh.ecm.crawler.CrawlerConnector;
import pl.edu.agh.ecm.domain.CrawlSession;
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

    public static String[][] sessionToProperties(CrawlSession crawlSession){
        //TODO
        return new String[0][0];
    }


    public static String[][] sessionNodesToProperties(CrawlSession crawlSession){
        String[][] result = new String[][]
        {
                contactNodesToProperty(crawlSession),
                domainManagerNodeToProperty(crawlSession)
        };
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

}
