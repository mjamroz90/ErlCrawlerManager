package pl.edu.agh.ecm.web.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 24.09.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */

@ScriptAssert(lang = "javascript",
              script = "_this.confirmPassword.equals(_this.password)",
              message = "password.mismatch.message" )
public class UserForm {

    private String login;
    private String password;
    private String confirmPassword;
    private String firstname;
    private String lastname;
    private boolean admin;

    @NotEmpty(message="{validation.NotEmpty.message}")
    @Size(min=3, max=45, message="{validation.Size.message}")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @NotEmpty(message="{validation.NotEmpty.message}")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

//    @NotEmpty(message="{validation.NotEmpty.message}")
    @Size(min = 0,max=45, message="{validation.Size.message}")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

//    @NotEmpty(message="{validation.NotEmpty.message}")
    @Size(min = 0, max = 45, message = "{validation.Size.message}")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
