package pl.edu.agh.ecm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.ecm.domain.Policy;
import pl.edu.agh.ecm.domain.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 06.09.12
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
public interface PolicyRepository extends CrudRepository<Policy,Long> {

    @Query("select distinct p from Policy p fetch all properties where p.createdBy.id = :userId")
    public List<Policy> findBycreatedBy(@Param("userId") Long userId);

    @Query("select distinct p from Policy p left join fetch p.initUrls a where p.id = :id")
    public Policy findByIdWithDetail(@Param("id")Long id );

    @Query("select distinct p from Policy p left join fetch p.initUrls a")
    public List<Policy> findAllWithDetails();
}
