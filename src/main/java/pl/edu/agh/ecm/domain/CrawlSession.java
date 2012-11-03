package pl.edu.agh.ecm.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import pl.edu.agh.ecm.web.util.TimeUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 03.09.12
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ecm_sessions")
public class CrawlSession implements Serializable {

    private Long id;
    private DateTime started;
    private User startedBy;
    private DateTime finished;
    private User finishedBy;
    private Policy policy;
    private Node domainManagerNode;
    private Node remoteManagerServerNode;
    private Set<Node> nodes = new HashSet<Node>();

    public CrawlSession(){}

    public CrawlSession(Node domainManagerNode, Node remoteManagerServerNode, Policy policy, User startedBy){

        this.domainManagerNode = domainManagerNode;
        this.remoteManagerServerNode = remoteManagerServerNode;
        this.policy = policy;
        this.startedBy = startedBy;
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

    @Column(name = "started")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public DateTime getStarted() {
        return started;
    }

    public void setStarted(DateTime started) {
        this.started = started;
    }

    @ManyToOne
    @JoinColumn(name = "started_by")
    public User getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(User startedBy) {
        this.startedBy = startedBy;
    }

    @Column(name = "finished")
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public DateTime getFinished() {
        return finished;
    }

    public void setFinished(DateTime finished) {
        this.finished = finished;
    }

    @ManyToOne
    @JoinColumn(name = "finished_by")
    public User getFinishedBy() {
        return finishedBy;
    }

    public void setFinishedBy(User finishedBy) {
        this.finishedBy = finishedBy;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "policy_id")
    @JsonIgnore(value = true)
    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    @ManyToOne()
    @JoinColumn(name = "domain_manager_node_id")
    @JsonIgnore(value = true)
    public Node getDomainManagerNode() {
        return domainManagerNode;
    }

    public void setDomainManagerNode(Node domainManagerNode) {
        this.domainManagerNode = domainManagerNode;
    }

    @ManyToOne
    @JoinColumn(name = "remote_manager_server_node_id")
    @JsonIgnore(value = true)
    public Node getRemoteManagerServerNode() {
        return remoteManagerServerNode;
    }

    public void setRemoteManagerServerNode(Node remoteManagerServerNode) {
        this.remoteManagerServerNode = remoteManagerServerNode;
    }


    @ManyToMany
    @JoinTable(name = "ecm_sessions_nodes",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "node_id"))
    @JsonIgnore(value = true)
    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node){
        getNodes().add(node);
    }

    @Transient
    public String getStartedAsString(){
        return TimeUtils.getDateTimeAsString(started);
    }

    @Transient
    public String getFinishedAsString(){
        if (finished != null){
            return TimeUtils.getDateTimeAsString(finished);
        }
        else{
            return null;
        }
    }

}
