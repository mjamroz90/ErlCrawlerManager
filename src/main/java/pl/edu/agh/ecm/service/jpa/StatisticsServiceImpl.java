package pl.edu.agh.ecm.service.jpa;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.ecm.domain.Statistics;
import pl.edu.agh.ecm.repository.StatisticsRepository;
import pl.edu.agh.ecm.service.StatisticsService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 08.12.12
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */

@Service("statisticsService")
@Repository
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public void delete(Statistics statistics) {
        statisticsRepository.delete(statistics);
    }

    public void delete(List<Statistics> statisticsList) {
        statisticsRepository.delete(statisticsList);
    }

    public Statistics save(Statistics statistics) {
        if (statistics.getReported() == null){
            statistics.setReported(DateTime.now());
        }
        return statisticsRepository.save(statistics);
    }

    @Transactional(readOnly = true)
    public List<Statistics> findStatisticsByNode(Long nodeId) {
        return statisticsRepository.findStatisticsByNode(nodeId);
    }

    public List<Statistics> findStatisticsBySession(Long sessionId) {
        return statisticsRepository.findStatisticsBySession(sessionId);
    }

    public List<Statistics> findAll() {
       return Lists.newArrayList(statisticsRepository.findAll());
    }
}
