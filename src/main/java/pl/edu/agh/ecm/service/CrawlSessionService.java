package pl.edu.agh.ecm.service;

import pl.edu.agh.ecm.domain.CrawlSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 03.09.12
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
 */
public interface CrawlSessionService {

    public CrawlSession findById(Long id);

    public CrawlSession findByIdWithDetail(Long id);

    public List<CrawlSession> findAll();

    public List<CrawlSession> findAllWithDetail();

    public List<CrawlSession> findByUserStartedWithDetail(Long userId);

    public List<CrawlSession> findByUserFinishedWithDetail(Long userId);

    public CrawlSession save(CrawlSession session);

    public CrawlSession getRunningSession();
}
