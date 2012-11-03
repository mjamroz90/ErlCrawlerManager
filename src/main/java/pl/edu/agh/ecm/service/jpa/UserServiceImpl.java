package pl.edu.agh.ecm.service.jpa;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.ecm.domain.User;
import pl.edu.agh.ecm.domain.UserDetailsAdapter;
import pl.edu.agh.ecm.repository.UserRepository;
import pl.edu.agh.ecm.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 03.09.12
 * Time: 20:01
 * To change this template use File | Settings | File Templates.
 */

@Service("userService")
@Repository
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SaltSource saltSource;

    @PersistenceContext
    EntityManager em;

    @Transactional(readOnly = true)
    public User findById(Long id) {
      return  userRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public User findByIdWithDetail(Long id) {
        return userRepository.findByIdWithDetail(id);
    }

    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional(readOnly = true)
    public User findByLoginWithDetail(String login) {
        return userRepository.findByLoginWithDetail(login);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<User> findAllWithDetail() {
        return Lists.newArrayList(userRepository.findAllWithDetail());
    }

    public User save(User u,String password) {
        if (password != null){
           Object salt = saltSource.getSalt(new UserDetailsAdapter(u));
           String encPassword = passwordEncoder.encodePassword(password,salt);
           u.setPassword(encPassword);
        }
        return userRepository.save(u);
    }

    @Transactional(readOnly = true)
    public Page<User> findAllByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<User> findAllNonAdmins() {
        return userRepository.findAllNonAdmins();
    }

}
