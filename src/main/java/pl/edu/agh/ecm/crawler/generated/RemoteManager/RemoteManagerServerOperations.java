package pl.edu.agh.ecm.crawler.generated.RemoteManager;


/**
* pl/edu/agh/ecm/crawler/generated/RemoteManager/RemoteManagerServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from src/main/idl/RemoteManager.idl
* środa, 26 wrzesień 2012 18:28:29 CEST
*/

public interface RemoteManagerServerOperations 
{
  String[][] startCrawlerOnNode (String nodeName, String[][] propertyList);
  pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] startCrawlerOnNodes (pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] nodeProperties);
  String[][] startSessionOnNode (String nodeName, String[][] propList);
  pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] startSessionOnNodes (pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] nodeProperties);
  boolean pingNode (String nodeName, org.omg.CORBA.StringHolder message);
  boolean pingApp (String nodeName, String appName, org.omg.CORBA.StringHolder message);
  String[][] stopSessionOnNode (String nodeName);
  pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] stopSessionOnNodes (String[] nodes);
  String[][] stopCrawlerOnNode (String nodeName);
  pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] stopCrawlerOnNodes (String[] nodes);
} // interface RemoteManagerServerOperations
