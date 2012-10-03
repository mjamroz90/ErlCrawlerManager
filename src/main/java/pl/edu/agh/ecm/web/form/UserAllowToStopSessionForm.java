package pl.edu.agh.ecm.web.form;

import org.apache.commons.collections.KeyValue;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import pl.edu.agh.ecm.domain.User;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 02.10.12
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class UserAllowToStopSessionForm {

    private List<UserEntry> users = new ArrayList<UserEntry>();

    public UserAllowToStopSessionForm()
    {}

    public UserAllowToStopSessionForm(List<User> users){
        for (User u : users){
            UserEntry keyValue =
                    new UserEntry(u.getLogin(),false);
            this.users.add(keyValue);
        }
    }

    public List<UserEntry> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntry> users) {
        this.users = users;
    }

}
