package pl.edu.agh.ecm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.ecm.domain.Node;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 06.09.12
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public interface NodeRepository extends CrudRepository<Node,Long> {

    @Query("select distinct n from Node n where n.address = :address")
    public Node findByAddress(@Param("address") String address);

    @Query("select distinct n from Node n where n.name = :name")
    public Node findByName(@Param("name") String name);

}
