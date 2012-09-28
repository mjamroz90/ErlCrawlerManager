package pl.edu.agh.ecm.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 02.09.12
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ecm_nodes")
public class Node implements Serializable {

    private Long id;
    private String name;
    private String address;
    //private Set<CrawlSession> sessions;

    public Node(){}

    public Node(String name, String address){

        this.name = name;
        this.address = address;
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
    @NotEmpty(message = "{validation.NotEmpty.message}")
    @Size(min=3, max=45, message="{validation.Size.message}")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "name")
    @NotEmpty(message = "{validation.NotEmpty.message}")
    @Size(min=3, max=45, message="{validation.Size.message}")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @Transient
    public String toString(){
       return name + '@' + address;
    }
//    @ManyToMany
//    @JoinTable(name = "ecm_sessions_nodes",
//            joinColumns = @JoinColumn(name = "node_id"),
//            inverseJoinColumns = @JoinColumn(name = "session_id"))
//    public Set<CrawlSession> getSessions() {
//        return sessions;
//    }
//
//    public void setSessions(Set<CrawlSession> sessions) {
//        this.sessions = sessions;
//    }
}
