package pl.edu.agh.ecm.web.form;

import pl.edu.agh.ecm.domain.CrawlSession;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 02.11.12
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
public class CrawlSessionGrid implements Serializable {

    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<CrawlSession> sessionData;

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

    public List<CrawlSession> getSessionData() {
        return sessionData;
    }

    public void setSessionData(List<CrawlSession> sessionData) {
        this.sessionData = sessionData;
    }
}
