package pl.edu.agh.ecm.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 02.09.12
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ecm_policies")
public class Policy implements Serializable {

    private Long id;
    private String initUrl;
    private Integer width;
    private Integer depth;
    private User createdBy;
    private Integer validityTime;
    private CrawlParams crawlParams;

    public Policy(){}

    public Policy(String initUrl,int width, int depth, User createdBy){

        this.initUrl = initUrl;
        this.width = width;
        this.depth = depth;
        this.createdBy = createdBy;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "init_url")
    @NotEmpty(message = "Init Address {validation.NotEmpty.message}")
    @URL(message = "")
    public String getInitUrl() {
        return initUrl;
    }

    public void setInitUrl(String initUrl) {
        this.initUrl = initUrl;
    }

    @Column(name = "width")
    @NotNull(message = "Crawl width {validation.NotEmpty.message}")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 0)
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Column(name = "depth")
    @NotNull(message = "Crawl depth {validation.NotEmpty.message}")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 0)
    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    @ManyToOne
    @JoinColumn(name = "created_by")
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "validity_time")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    public Integer getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Integer validityTime) {
        this.validityTime = validityTime;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crawl_params_id")
    public CrawlParams getCrawlParams() {
        return crawlParams;
    }

    public void setCrawlParams(CrawlParams crawlParams) {
        this.crawlParams = crawlParams;
    }
}
