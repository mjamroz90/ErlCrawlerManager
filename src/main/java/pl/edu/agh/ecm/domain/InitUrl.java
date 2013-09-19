package pl.edu.agh.ecm.domain;


import pl.edu.agh.ecm.web.util.TimeUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 09.10.12
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ecm_init_urls")
public class InitUrl implements Serializable {

    private Long id;
    private String address;
    private Integer width;
    private Integer depth;
    private Integer subDomainBreadth;
    private Integer subDomainDepth;
    private Long validityTime;
    private Long subDomainValidityTime;
    private Policy policy;

    public InitUrl(){}

    public InitUrl(String address,Integer width, Integer depth,Long validityTime){

        this.address = address;
        this.width = width;
        this.depth = depth;
        this.validityTime = validityTime;
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

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "width")
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Column(name = "depth")
    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    @Column(name = "validity_time")
    public Long getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Long validityTime) {
        this.validityTime = validityTime;
    }

    @Column(name = "subdomain_breadth")
    public Integer getSubDomainBreadth() {
        return subDomainBreadth;
    }

    public void setSubDomainBreadth(Integer subDomainBreadth) {
        this.subDomainBreadth = subDomainBreadth;
    }

    @Column(name = "subdomain_depth")
    public Integer getSubDomainDepth() {
        return subDomainDepth;
    }

    public void setSubDomainDepth(Integer subDomainDepth) {
        this.subDomainDepth = subDomainDepth;
    }

    @Column(name = "subdomain_validity_time")
    public Long getSubDomainValidityTime() {
        return subDomainValidityTime;
    }

    public void setSubDomainValidityTime(Long subDomainValidityTime) {
        this.subDomainValidityTime = subDomainValidityTime;
    }

    @ManyToOne
    @JoinColumn(name = "policy_id")
    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    @Transient
    public String getValidityTimeAsString(){
        return TimeUtils.getTimeLongAsString(validityTime);
    }

    @Transient
    public String getSubDomainValidityTimeAsString(){
        return TimeUtils.getTimeLongAsString(subDomainValidityTime);
    }

    @Override
    public String toString(){
        return String.format("address : %s, width/subdomain-width : %s/%s, depth/subdomain-depth : %s/%s, " +
                "valid before/subdomain-valid before : %s/%s",
                address,width,subDomainBreadth,depth,subDomainDepth,getValidityTimeAsString(),getSubDomainValidityTimeAsString());

    }
}
