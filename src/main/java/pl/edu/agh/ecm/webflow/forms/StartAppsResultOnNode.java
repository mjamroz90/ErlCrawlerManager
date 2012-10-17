package pl.edu.agh.ecm.webflow.forms;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 17.10.12
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
public class StartAppsResultOnNode implements Serializable {

    private String nodeName;
    private boolean cacheStarted;
    private boolean domainManagerStarted;
    private boolean crawlEventStarted;
    private boolean sessionManagerStarted;

    public StartAppsResultOnNode(){}

    public StartAppsResultOnNode(String nodeName){
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public boolean isCacheStarted() {
        return cacheStarted;
    }

    public void setCacheStarted(boolean cacheStarted) {
        this.cacheStarted = cacheStarted;
    }

    public boolean isDomainManagerStarted() {
        return domainManagerStarted;
    }

    public void setDomainManagerStarted(boolean domainManagerStarted) {
        this.domainManagerStarted = domainManagerStarted;
    }

    public boolean isCrawlEventStarted() {
        return crawlEventStarted;
    }

    public void setCrawlEventStarted(boolean crawlEventStarted) {
        this.crawlEventStarted = crawlEventStarted;
    }

    public boolean isSessionManagerStarted() {
        return sessionManagerStarted;
    }

    public void setSessionManagerStarted(boolean sessionManagerStarted) {
        this.sessionManagerStarted = sessionManagerStarted;
    }
}
