package pl.edu.agh.ecm.webflow.forms;

import org.joda.time.DateTime;
import pl.edu.agh.ecm.domain.Policy;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 21.10.12
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public class CrawlSessionForm implements Serializable {

    private List<InitUrlForm> initUrlFormList = new ArrayList<InitUrlForm>();
    private InitUrlForm newInitUrl;
    private DateTime currentTime;
    private PolicyForm policy;

    public PolicyForm getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyForm policy) {
        this.policy = policy;
    }

    public List<InitUrlForm> getInitUrlFormList() {
        return initUrlFormList;
    }

    public void setInitUrlFormList(List<InitUrlForm> initUrlFormList) {
        this.initUrlFormList = initUrlFormList;
    }

    public DateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(DateTime currentTime) {
        this.currentTime = currentTime;
    }

    @Valid
    public InitUrlForm getNewInitUrl() {
        return newInitUrl;
    }

    public void setNewInitUrl(InitUrlForm newInitUrl) {
        this.newInitUrl = newInitUrl;
    }
}
