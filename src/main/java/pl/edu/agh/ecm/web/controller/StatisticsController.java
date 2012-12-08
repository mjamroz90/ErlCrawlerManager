package pl.edu.agh.ecm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ecm.domain.CrawlSession;
import pl.edu.agh.ecm.domain.Node;
import pl.edu.agh.ecm.domain.Statistics;
import pl.edu.agh.ecm.service.CrawlSessionService;
import pl.edu.agh.ecm.service.NodeService;
import pl.edu.agh.ecm.service.StatisticsService;

import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 22.11.12
 * Time: 10:48
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/stats")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private CrawlSessionService crawlSessionService;

    @Autowired
    private NodeService nodeService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public boolean handleStats(@ModelAttribute Statistics stats,
                               @RequestParam("nodeName")String nodeName,
                               @RequestParam("test")Boolean test
                               ){

        boolean result = computeHash(stats);
        if (!result){
            return false;
        }
        String[] nodeNameParts = nodeName.split("@");
        Node node = nodeService.findByNameAndAddress(nodeNameParts[0].substring(1),
                nodeNameParts[1].substring(0,nodeNameParts[1].length()-1));
        stats.setNode(node);

        if (!test){
            CrawlSession currentSession = crawlSessionService.getRunningSession();
            stats.setCrawlSession(currentSession);
        }
        statisticsService.save(stats);

        return true;
    }

    private boolean computeHash(Statistics statistics){

        double sum = statistics.getMeanAddressesNumPerSite()+statistics.getTotalProcessedSitesNum()+
                statistics.getMemoryUsage()+statistics.getMeanSiteProcessingNum()+
                statistics.getMeanProcessorUsage()+statistics.getTotalAddressesFetchedNum()+statistics.getPartAddressesNumPerSite();

        byte[] sumBytes = new byte[8];
        ByteBuffer.wrap(sumBytes).putLong((long) sum);
        byte[] passBytes = "ecm".getBytes();
        int startIndx = 0;
        while (sumBytes[startIndx] == 0){
            startIndx++;
        }
        int[] sumBytesUnsigned = new int[8-startIndx];
        for (int i=startIndx; i<8; i++){
            sumBytesUnsigned[i-startIndx] = (int)sumBytes[i] & 0xff;
        }

        byte[] result = new byte[sumBytesUnsigned.length*passBytes.length];
        int global=0;
        for (int i=0; i< sumBytesUnsigned.length; i++){
            int currentByte = sumBytesUnsigned[i];
            for (byte passByte : passBytes){
                result[global] = (byte)(((int)passByte)^currentByte);
                global++;
            }
        }
        BigInteger hashVal = new BigInteger(statistics.getHashValue());
        BigInteger finalValue = new BigInteger(1,result);

        return hashVal.equals(finalValue);
    }
}
