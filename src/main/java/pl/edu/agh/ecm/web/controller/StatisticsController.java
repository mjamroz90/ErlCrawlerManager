package pl.edu.agh.ecm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ecm.domain.Statistics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public boolean handleStats(@ModelAttribute Statistics stats,
                               @RequestParam("nodeName")String nodeName
                               ){

        boolean result = computeHash(stats);
        return result;
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
