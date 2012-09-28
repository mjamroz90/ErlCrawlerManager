package pl.edu.agh.ecm.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.ecm.domain.User;
import pl.edu.agh.ecm.domain.UserDetailsAdapter;
import pl.edu.agh.ecm.repository.UserDetailsRepository;
import pl.edu.agh.ecm.service.UserService;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 23.09.12
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */

@Service("userDetailsService")
@Transactional(readOnly = true)
public class UserDetailsServiceAdapter implements UserDetailsService {

    @Autowired
    UserService userService;
    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {

        User user = userService.findByLogin(userLogin);
        if (user == null){
            throw new UsernameNotFoundException("No such user: "+ userLogin);
        }
        UserDetailsAdapter userDetailsAdapter = new UserDetailsAdapter(user);
        String password = userDetailsRepository.findPasswordByUserLogin(userLogin);
        userDetailsAdapter.setPassword(password);

        return userDetailsAdapter;
    }
}
