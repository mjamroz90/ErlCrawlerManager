package pl.edu.agh.ecm.crawler;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import pl.edu.agh.ecm.crawler.generated.Orber.InitialReference;
import pl.edu.agh.ecm.crawler.generated.Orber.InitialReferences;
import pl.edu.agh.ecm.crawler.generated.Orber.InitialReferencesHelper;
import pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer;
import pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServerHelper;

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
    private int remoteManagerPort;
    private RemoteManagerServer remoteManagerServer = null;
    private ORB orb = null;

    public CrawlerConnector(){}

    public CrawlerConnector(String remoteManagerAddress,int remoteManagerPort){
        try{
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

    public boolean pingNode(String nodeName){
        if (remoteManagerServer != null){
            StringHolder holder = new StringHolder();
            return remoteManagerServer.pingNode(nodeName,holder);
        }
        else{
            return false;
        }

    }

    public String getRemoteManagerAddress() {
        return remoteManagerAddress;
    }

    public void setRemoteManagerAddress(String remoteManagerAddress) {
        this.remoteManagerAddress = remoteManagerAddress;
    }

    public int getRemoteManagerPort() {
        return remoteManagerPort;
    }

    public void setRemoteManagerPort(int remoteManagerPort) {
        this.remoteManagerPort = remoteManagerPort;
    }

    public RemoteManagerServer getRemoteManagerServer() {
        return remoteManagerServer;
    }

    public void setRemoteManagerServer(RemoteManagerServer remoteManagerServer) {
        this.remoteManagerServer = remoteManagerServer;
    }
}
