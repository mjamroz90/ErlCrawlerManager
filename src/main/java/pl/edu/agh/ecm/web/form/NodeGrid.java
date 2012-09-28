package pl.edu.agh.ecm.web.form;

import pl.edu.agh.ecm.domain.Node;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 27.09.12
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public class NodeGrid {

    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<NodeExt> nodeData;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<NodeExt> getNodeData(){
        return nodeData;
    }

    public void setNodeData(List<NodeExt> nodeData) {
        this.nodeData = nodeData;
    }
}
