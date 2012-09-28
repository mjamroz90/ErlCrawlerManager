package pl.edu.agh.ecm.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 23.09.12
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class JdbcUserDetailsRepository implements UserDetailsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String FIND_PASSWORD_SQL =
            "select password from ecm_users where login = ?";

    @Override
    public String findPasswordByUserLogin(String userLogin) {
        return jdbcTemplate.queryForObject(FIND_PASSWORD_SQL,new Object[]{userLogin}, String.class);
    }
}
