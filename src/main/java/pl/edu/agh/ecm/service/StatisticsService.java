package pl.edu.agh.ecm.service;

import pl.edu.agh.ecm.domain.Statistics;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 08.12.12
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public interface StatisticsService {

    public void delete(Statistics statistics);

    public void delete(List<Statistics> statisticsList);

    public Statistics save(Statistics statistics);

    public List<Statistics> findStatisticsByNode(Long nodeId);

    public List<Statistics> findStatisticsBySession(Long sessionId);

    public List<Statistics> findAll();
}
