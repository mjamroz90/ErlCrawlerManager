package pl.edu.agh.ecm.service;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 23.09.12
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public interface EcmUserDetailsService {

    public String findPasswordByUserLogin(String userLogin);

}
