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

    public List<User> findByLogin(String firstName);

    public List<User> findByLoginWithDetail(String firstName);

    public List<User> findAll();

    public List<User> findAllWithDetail();

    public User save(User u);

    public Page<User> findAllByPage(Pageable pageable);

}
