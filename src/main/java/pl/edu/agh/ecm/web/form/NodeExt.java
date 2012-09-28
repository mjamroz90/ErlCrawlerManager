package pl.edu.agh.ecm.web.form;

import pl.edu.agh.ecm.domain.Node;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 27.09.12
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */
public class NodeExt {
    private Node node;
    private boolean accessible;

    public NodeExt(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getAddress(){
        return node.getAddress();
    }

    public String getName(){
        return node.getName();
    }

    public boolean getAccessible(){
       return accessible;
    }

    public void setAccessible(boolean accessible){
        this.accessible = accessible;
    }
}
