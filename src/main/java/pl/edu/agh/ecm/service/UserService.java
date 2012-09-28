package pl.edu.agh.ecm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.agh.ecm.domain.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 03.09.12
 * Time: 19:38
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    public User findById(Long id);

    public User findByIdWithDetail(Long id);

    public User findByLogin(String login);

    public User findByLoginWithDetail(String login);

    public List<User> findAll();

    public List<User> findAllWithDetail();

    public User save(User u,String password);

    public Page<User> findAllByPage(Pageable pageable);

    public List<User> findAllNonAdmins();
}
