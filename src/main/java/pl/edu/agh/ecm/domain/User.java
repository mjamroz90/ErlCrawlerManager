package pl.edu.agh.ecm.domain;

import com.google.common.base.Optional;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.security.auth.Subject;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 02.09.12
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ecm_users")
public class User implements Serializable {

    private Long id;
    private String login;
    private String password;
    private boolean admin;
    private String firstname;
    private String lastname;
    private Set<User> allowedToStopSession = new HashSet<User>();
    private Set<User> allowingToStopSession = new HashSet<User>();

    public User(){}

    public User(String firstname, String lastname, String login, String password){

        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    @NotEmpty(message="{validation.NotEmpty.message}")
    @Size(min=3, max=45, message="{validation.Size.message}")
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @NotEmpty(message="{validation.NotEmpty.message}")
    @Column(name = "password")
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "is_admin")
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Column(name = "firstname")
    @Size(min = 0,max=45, message="{validation.Size.message}")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname")
    @Size(min = 0, max = 45, message = "{validation.Size.message}")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ecm_stop_session_permissions",
            joinColumns = @JoinColumn(name = "allowing_user_id"),
            inverseJoinColumns = @JoinColumn(name = "allowed_user_id"))
    @JsonIgnore(value = true)
    public Set<User> getAllowedToStopSession() {
        return allowedToStopSession;
    }

    public void setAllowedToStopSession(Set<User> allowedToStopSession) {
        this.allowedToStopSession = allowedToStopSession;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ecm_stop_session_permissions",
               joinColumns = @JoinColumn(name = "allowed_user_id"),
               inverseJoinColumns = @JoinColumn(name = "allowing_user_id")
               )
    @JsonIgnore(value = true)
    public Set<User> getAllowingToStopSession() {
        return allowingToStopSession;
    }

    public void setAllowingToStopSession(Set<User> allowingToStopSession) {
        this.allowingToStopSession = allowingToStopSession;
    }

    public void addAllowedToStopSession(User u){
        getAllowedToStopSession().add(u);
    }

    public void addAllowingToStopSession(User u){
        getAllowingToStopSession().add(u);
    }

    @Override
    @Transient
    public String toString(){
        StringBuffer buff = new StringBuffer(login);
        if (firstname != null && firstname.length() > 0 && lastname != null && lastname.length() > 0){
            buff.append(" , ");
            buff.append(firstname);
            buff.append(" ");
            buff.append(lastname);
        }
        return buff.toString();
    }

    @Transient
    public boolean isAllowedToStopSession(String login){

        for (User u : allowedToStopSession){
            if (u.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }
}
