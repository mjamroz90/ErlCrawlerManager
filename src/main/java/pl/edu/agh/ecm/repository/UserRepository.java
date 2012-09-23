package pl.edu.agh.ecm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.ecm.domain.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 03.09.12
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    @Query("select distinct u from User u left join fetch u.allowedToStopSession a left join fetch " +
            "u.allowingToStopSession b where u.id = :id")
    public User findByIdWithDetail(@Param("id") Long id);

    @Query("select distinct u from User u left join fetch u.allowedToStopSession a left join fetch " +
     "u.allowingToStopSession b where u.login = :name")
    public User findByLoginWithDetail(@Param("name") String name);

    public User findByLogin(String name);

    @Query("select distinct u from User u left join fetch u.allowedToStopSession a left join fetch " +
            " u.allowingToStopSession b ")
    public List<User> findAllWithDetail();

}
