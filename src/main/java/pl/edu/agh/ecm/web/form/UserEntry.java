package pl.edu.agh.ecm.web.form;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 02.10.12
 * Time: 20:12
 * To change this template use File | Settings | File Templates.
 */
public class UserEntry{
    private String login;
    private boolean allowed;

    public UserEntry()
    {}

    public UserEntry(String login, boolean allowed){
        this.login = login;
        this.allowed = allowed;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
}
