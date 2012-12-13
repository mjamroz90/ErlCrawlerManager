package pl.edu.agh.ecm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.ecm.domain.Statistics;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 08.12.12
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */

public interface StatisticsRepository extends CrudRepository<Statistics,Long> {

    @Query("select distinct s from Statistics s where s.node.id = :nodeId")
    public List<Statistics> findStatisticsByNode(@Param("nodeId")Long nodeId);

    @Query("select distinct s from Statistics s where s.crawlSession.id = :sessionId")
    public List<Statistics> findStatisticsBySession(@Param("sessionId")Long sessionId);

}
