package pl.edu.agh.ecm.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 22.11.12
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ecm_statistics")
public class Statistics {

    private Long id;
    private CrawlSession crawlSession;
    private Node node;
    private int totalProcessedSitesNum;
    private double meanSiteProcessingNum;
    private double meanProcessorUsage;
    private double memoryUsage;
    private int totalAddressesFetchedNum;
    private double meanAddressesNumPerSite;
    private double partAddressesNumPerSite;
    private String hashValue;
    private DateTime reported;

    public Statistics(CrawlSession crawlSession){
        this.crawlSession = crawlSession;
    }

    public Statistics(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "crawl_session")
    public CrawlSession getCrawlSession() {
        return crawlSession;
    }

    public void setCrawlSession(CrawlSession crawlSession) {
        this.crawlSession = crawlSession;
    }

    @Column(name = "total_processed_sites_num")
    public int getTotalProcessedSitesNum() {
        return totalProcessedSitesNum;
    }

    public void setTotalProcessedSitesNum(int totalProcessedSitesNum) {
        this.totalProcessedSitesNum = totalProcessedSitesNum;
    }

    @Column(name = "mean_site_processing_num")
    public double getMeanSiteProcessingNum() {
        return meanSiteProcessingNum;
    }

    public void setMeanSiteProcessingNum(double meanSiteProcessingNum) {
        this.meanSiteProcessingNum = meanSiteProcessingNum;
    }

    @Column(name = "processor_usage")
    public double getMeanProcessorUsage() {
        return meanProcessorUsage;
    }

    public void setMeanProcessorUsage(double meanProcessorUsage) {
        this.meanProcessorUsage = meanProcessorUsage;
    }

    @Column(name = "memory_usage")
    public double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    @Column(name = "total_addresses_fetched_num")
    public int getTotalAddressesFetchedNum() {
        return totalAddressesFetchedNum;
    }

    public void setTotalAddressesFetchedNum(int totalAddressesFetchedNum) {
        this.totalAddressesFetchedNum = totalAddressesFetchedNum;
    }

    @Column(name = "mean_addresses_num_per_site")
    public double getMeanAddressesNumPerSite() {
        return meanAddressesNumPerSite;
    }

    public void setMeanAddressesNumPerSite(double meanAddressesNumPerSite) {
        this.meanAddressesNumPerSite = meanAddressesNumPerSite;
    }

    @Column(name = "part_addresses_num_per_site")
    public double getPartAddressesNumPerSite() {
        return partAddressesNumPerSite;
    }

    public void setPartAddressesNumPerSite(double partAddressesNumPerSite) {
        this.partAddressesNumPerSite = partAddressesNumPerSite;
    }

    @Column(name = "reportd")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public DateTime getReported() {
        return reported;
    }

    public void setReported(DateTime reported) {
        this.reported = reported;
    }

    @ManyToOne
    @JoinColumn(name = "node")
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Transient
    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }
}
