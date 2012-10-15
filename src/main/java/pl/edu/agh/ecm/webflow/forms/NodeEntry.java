package pl.edu.agh.ecm.webflow.forms;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 13.10.12
 * Time: 21:03
 * To change this template use File | Settings | File Templates.
 */
public class NodeEntry implements Serializable {

    private String nodeName;
    private boolean used;

    public NodeEntry(){}

    public NodeEntry(String nodeName,boolean  used){
        this.nodeName = nodeName;
        this.used = used;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
