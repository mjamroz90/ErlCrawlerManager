package pl.edu.agh.ecm.crawler;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.format.annotation.NumberFormat;
import pl.edu.agh.ecm.crawler.generated.Orber.InitialReference;
import pl.edu.agh.ecm.crawler.generated.Orber.InitialReferences;
import pl.edu.agh.ecm.crawler.generated.Orber.InitialReferencesHelper;
import pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer;
import pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServerHelper;
import pl.edu.agh.ecm.domain.CrawlSession;
import pl.edu.agh.ecm.domain.Node;
import pl.edu.agh.ecm.web.util.IPDomain;

import javax.validation.constraints.NotNull;
import java.net.Authenticator;
import java.net.SocketPermission;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 26.09.12
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
public class CrawlerConnector {

    private String remoteManagerAddress;
    private String remoteNodeName;
    private int remoteManagerPort;
    private RemoteManagerServer remoteManagerServer = null;
    private ORB orb = null;

    public CrawlerConnector(){}

    public CrawlerConnector(String remoteManagerAddress,String remoteNodeName,int remoteManagerPort){
        try{
            this.remoteManagerAddress = remoteManagerAddress;
            this.remoteManagerPort = remoteManagerPort;
            this.remoteNodeName = remoteNodeName;
            initManagerServer(remoteManagerAddress,remoteManagerPort);
        }
        catch (Exception e)
        {}
    }

    public void initManagerServer(String remoteManagerAddress,int remoteManagerPort) throws InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName,
            NotFound, CannotProceed {
        String[] args = {remoteManagerAddress,Integer.toString(remoteManagerPort)};

        orb = org.omg.CORBA.ORB.init(args,null);
        InitialReference initialReference = new InitialReference();
        String str = initialReference.stringified_ior(remoteManagerAddress,remoteManagerPort);

        org.omg.CORBA.Object obj = orb.string_to_object(str);
        InitialReferences initialReferences = InitialReferencesHelper.narrow(obj);

        NamingContext namingContext = NamingContextHelper.narrow(initialReferences.get("NameService"));
        NameComponent[] nameComponents = {new NameComponent("RemoteCrawlServer","")};

        org.omg.CORBA.Object nameCompRef = namingContext.resolve(nameComponents);
        remoteManagerServer = RemoteManagerServerHelper.narrow(nameCompRef);
    }

    public boolean initManagerServer(){
        try{
            initManagerServer(this.remoteManagerAddress,this.remoteManagerPort);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean pingNode(String nodeName){
        if (remoteManagerServer != null){
            StringHolder holder = new StringHolder();
            return remoteManagerServer.pingNode(nodeName,holder);
        }
        else{
            return false;
        }

    }

    public String[][] startCrawlerOnNode(String nodeName,String[][] properties){

        try{
            String[][] result = remoteManagerServer.startCrawlerOnNode(nodeName,properties);
            return result;
        }
        catch (Exception e){
            return new String[][]{
                    {CrawlerAppsNames.crawlEventApp,"error"},
                    {CrawlerAppsNames.domainManagerApp,"error"},
                    {CrawlerAppsNames.cacheApp,"error"},
                    {CrawlerAppsNames.sessionManagerApp,"error"}
            };
        }
    }

    public String[][] startSessionOnNode(String nodeName,String[][] properties){
        try{
            String[][] result = remoteManagerServer.startSessionOnNode(nodeName,properties);
            return result;
        }
        catch (Exception e){
            return new String[][]{
                    {CrawlerAppsNames.schedulerApp,"error"}
            };
        }
    }

    public boolean pingApp(String nodeName,String appName){

        try{
            StringHolder holder = new StringHolder();
            return remoteManagerServer.pingApp(nodeName,appName,holder);
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean stopSession(boolean crawlerAlso,CrawlSession crawlSession){
        try{
            for (Node node : crawlSession.getNodes()){
                StringHolder holder = new StringHolder();
                boolean pingResult = remoteManagerServer.pingApp(node.toString(),CrawlerAppsNames.schedulerApp,holder);
                if (pingResult == true){
                    remoteManagerServer.stopSessionOnNode(node.toString());
                }
                if (crawlerAlso){
                    remoteManagerServer.stopCrawlerOnNode(node.toString());
                }
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @NotEmpty(message = "{validation.NotEmpty.message}")
    @IPDomain(message = "{ipdomain.validation.message}")
    public String getRemoteManagerAddress() {
        return remoteManagerAddress;
    }

    public void setRemoteManagerAddress(String remoteManagerAddress) {
        this.remoteManagerAddress = remoteManagerAddress;
    }

    @NotNull(message = "{validation.NotEmpty.message}")
    public int getRemoteManagerPort() {
        return remoteManagerPort;
    }

    public void setRemoteManagerPort(int remoteManagerPort) {
        this.remoteManagerPort = remoteManagerPort;
    }

    public String getRemoteNodeName() {
        return remoteNodeName;
    }

    public void setRemoteNodeName(String remoteNodeName) {
        this.remoteNodeName = remoteNodeName;
    }

    public RemoteManagerServer getRemoteManagerServer() {
        return remoteManagerServer;
    }

    public void setRemoteManagerServer(RemoteManagerServer remoteManagerServer) {
        this.remoteManagerServer = remoteManagerServer;
    }
}
