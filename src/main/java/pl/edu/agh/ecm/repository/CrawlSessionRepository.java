package pl.edu.agh.ecm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.ecm.domain.CrawlSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 03.09.12
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */
public interface CrawlSessionRepository extends PagingAndSortingRepository<CrawlSession,Long> {

    @Query("select distinct s from CrawlSession s left join fetch s.nodes n " +
            "left join fetch s.policy p left join fetch p.initUrls i where s.id = :id ")
    public CrawlSession findByIdWithDetail(@Param("id")Long id);

    @Query("select distinct s from CrawlSession s left join fetch s.nodes n order by s.finished")
    public List<CrawlSession> findAllWithDetail();

    @Query("select distinct s from CrawlSession s left join fetch s.nodes n where s.startedBy.id = :userId")
    public List<CrawlSession> findByUserStartedWithDetail(@Param("userId") Long userId);

    @Query("select distinct s from CrawlSession s left join fetch s.nodes n where s.finishedBy.id = :userId")
    public List<CrawlSession> findByUserFinishedWithDetail(@Param("userId") Long userId);

    @Query("select distinct s from CrawlSession s where s.finished is null ")
    public CrawlSession getRunningSession();
}
