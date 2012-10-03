package pl.edu.agh.ecm.web.form;

import pl.edu.agh.ecm.domain.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 01.10.12
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class UserCollectionForm {

    private List<User> users;

    public UserCollectionForm(){}

    public UserCollectionForm(List<User> users){
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
