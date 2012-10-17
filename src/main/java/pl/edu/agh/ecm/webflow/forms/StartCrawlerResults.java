package pl.edu.agh.ecm.webflow.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 17.10.12
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public class StartCrawlerResults implements Serializable {

    private boolean canGoToNextStep;
    private List<StartAppsResultOnNode> startAppsResultOnNodes = new ArrayList<StartAppsResultOnNode>();

    public void addStartAppsResultOnNode(StartAppsResultOnNode resultOnNode){
        startAppsResultOnNodes.add(resultOnNode);
    }

    public List<StartAppsResultOnNode> getStartAppsResultOnNodes() {
        return startAppsResultOnNodes;
    }

    public void setStartAppsResultOnNodes(List<StartAppsResultOnNode> startAppsResultOnNodes) {
        this.startAppsResultOnNodes = startAppsResultOnNodes;
    }

    public void setCanGoToNextStep(String domainManagerNodeName){

        canGoToNextStep = true;
        for (StartAppsResultOnNode resultOnNode : startAppsResultOnNodes){
            if (!resultOnNode.isCacheStarted() || !resultOnNode.isCrawlEventStarted()){
                canGoToNextStep = false;
                return;
            }
            if (resultOnNode.getNodeName().equals(domainManagerNodeName)){
                if (!resultOnNode.isDomainManagerStarted()){
                    canGoToNextStep = false;
                    return;
                }
            }
        }
    }

    public boolean isCanGoToNextStep() {
        return canGoToNextStep;
    }

    public void setCanGoToNextStep(boolean canGoToNextStep) {
        this.canGoToNextStep = canGoToNextStep;
    }
}
