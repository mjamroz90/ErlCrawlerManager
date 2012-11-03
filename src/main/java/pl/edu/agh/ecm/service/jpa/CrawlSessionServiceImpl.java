package pl.edu.agh.ecm.service.jpa;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.ecm.domain.CrawlSession;
import pl.edu.agh.ecm.repository.CrawlSessionRepository;
import pl.edu.agh.ecm.service.CrawlSessionService;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 04.09.12
 * Time: 18:02
 * To change this template use File | Settings | File Templates.
 */

@Service("crawlSessionService")
@Repository
@Transactional
public class CrawlSessionServiceImpl implements CrawlSessionService {

    @Autowired
    private CrawlSessionRepository crawlSessionRepository;

    @Transactional(readOnly = true)
    public CrawlSession findById(Long id) {
        return crawlSessionRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public CrawlSession findByIdWithDetail(Long id) {
        return crawlSessionRepository.findByIdWithDetail(id);
    }

    @Transactional(readOnly = true)
    public List<CrawlSession> findAll() {
        return Lists.newArrayList(crawlSessionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<CrawlSession> findAllWithDetail() {
        return Lists.newArrayList(crawlSessionRepository.findAllWithDetail());
    }

    @Transactional(readOnly = true)
    public List<CrawlSession> findByUserStartedWithDetail(Long userId) {
        return crawlSessionRepository.findByUserStartedWithDetail(userId);
    }

    @Transactional(readOnly = true)
    public List<CrawlSession> findByUserFinishedWithDetail(Long userId) {
        return crawlSessionRepository.findByUserFinishedWithDetail(userId);
    }

    public CrawlSession save(CrawlSession session) {
        if (session.getStarted() == null){
            session.setStarted(DateTime.now());
        }
        return crawlSessionRepository.save(session);
    }

    @Transactional(readOnly = true)
    public CrawlSession getRunningSession() {

        return crawlSessionRepository.getRunningSession();
    }

    public void delete(CrawlSession crawlSession) {
        crawlSessionRepository.delete(crawlSession);
    }

    @Transactional(readOnly = true)
    public Page<CrawlSession> findAllByPage(Pageable pageable) {
        return crawlSessionRepository.findAll(pageable);
    }
}
