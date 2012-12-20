package pl.edu.agh.ecm.web.form;

import pl.edu.agh.ecm.domain.Statistics;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 20.12.12
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class StatsGrid  {

    private String nodeName;
    private List<Statistics> statisticsList;

    public StatsGrid(){}

    public StatsGrid(String nodeName){
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<Statistics> getStatisticsList() {
        return statisticsList;
    }

    public void setStatisticsList(List<Statistics> statisticsList) {
        this.statisticsList = statisticsList;
    }
}
