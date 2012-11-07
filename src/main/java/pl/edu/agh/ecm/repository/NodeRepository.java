package pl.edu.agh.ecm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.ecm.domain.Node;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 06.09.12
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public interface NodeRepository extends PagingAndSortingRepository<Node,Long> {

    @Query("select distinct n from Node n where n.address = :address")
    public List<Node> findByAddress(@Param("address") String address);

    @Query("select distinct n from Node n where n.name = :name")
    public List<Node> findByName(@Param("name") String name);

    @Query("select distinct n from Node n where n.name = :name and n.address = :address")
    public Node findByNameAndAddress(@Param("name")String name,@Param("address") String address);

}
