package pl.edu.agh.ecm.crawler.generated.RemoteManager;


/**
* pl/edu/agh/ecm/crawler/generated/RemoteManager/_RemoteManagerServerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from src/main/idl/RemoteManager.idl
* środa, 26 wrzesień 2012 18:28:29 CEST
*/

public class _RemoteManagerServerStub extends org.omg.CORBA.portable.ObjectImpl implements pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer
{

  public String[][] startCrawlerOnNode (String nodeName, String[][] propertyList)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("startCrawlerOnNode", true);
                $out.write_string (nodeName);
                pl.edu.agh.ecm.crawler.generated.RemoteManager.propListHelper.write ($out, propertyList);
                $in = _invoke ($out);
                String $result[][] = pl.edu.agh.ecm.crawler.generated.RemoteManager.propListHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return startCrawlerOnNode (nodeName, propertyList        );
            } finally {
                _releaseReply ($in);
            }
  } // startCrawlerOnNode

  public pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] startCrawlerOnNodes (pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] nodeProperties)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("startCrawlerOnNodes", true);
                pl.edu.agh.ecm.crawler.generated.RemoteManager.nodesPropertiesHelper.write ($out, nodeProperties);
                $in = _invoke ($out);
                pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty $result[] = pl.edu.agh.ecm.crawler.generated.RemoteManager.nodesPropertiesHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return startCrawlerOnNodes (nodeProperties        );
            } finally {
                _releaseReply ($in);
            }
  } // startCrawlerOnNodes

  public String[][] startSessionOnNode (String nodeName, String[][] propList)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("startSessionOnNode", true);
                $out.write_string (nodeName);
                pl.edu.agh.ecm.crawler.generated.RemoteManager.propListHelper.write ($out, propList);
                $in = _invoke ($out);
                String $result[][] = pl.edu.agh.ecm.crawler.generated.RemoteManager.propListHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return startSessionOnNode (nodeName, propList        );
            } finally {
                _releaseReply ($in);
            }
  } // startSessionOnNode

  public pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] startSessionOnNodes (pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] nodeProperties)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("startSessionOnNodes", true);
                pl.edu.agh.ecm.crawler.generated.RemoteManager.nodesPropertiesHelper.write ($out, nodeProperties);
                $in = _invoke ($out);
                pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty $result[] = pl.edu.agh.ecm.crawler.generated.RemoteManager.nodesPropertiesHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return startSessionOnNodes (nodeProperties        );
            } finally {
                _releaseReply ($in);
            }
  } // startSessionOnNodes

  public boolean pingNode (String nodeName, org.omg.CORBA.StringHolder message)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("pingNode", true);
                $out.write_string (nodeName);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                message.value = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return pingNode (nodeName, message        );
            } finally {
                _releaseReply ($in);
            }
  } // pingNode

  public boolean pingApp (String nodeName, String appName, org.omg.CORBA.StringHolder message)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("pingApp", true);
                $out.write_string (nodeName);
                $out.write_string (appName);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                message.value = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return pingApp (nodeName, appName, message        );
            } finally {
                _releaseReply ($in);
            }
  } // pingApp

  public String[][] stopSessionOnNode (String nodeName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("stopSessionOnNode", true);
                $out.write_string (nodeName);
                $in = _invoke ($out);
                String $result[][] = pl.edu.agh.ecm.crawler.generated.RemoteManager.propListHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return stopSessionOnNode (nodeName        );
            } finally {
                _releaseReply ($in);
            }
  } // stopSessionOnNode

  public pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] stopSessionOnNodes (String[] nodes)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("stopSessionOnNodes", true);
                pl.edu.agh.ecm.crawler.generated.RemoteManager.stringListHelper.write ($out, nodes);
                $in = _invoke ($out);
                pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty $result[] = pl.edu.agh.ecm.crawler.generated.RemoteManager.nodesPropertiesHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return stopSessionOnNodes (nodes        );
            } finally {
                _releaseReply ($in);
            }
  } // stopSessionOnNodes

  public String[][] stopCrawlerOnNode (String nodeName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("stopCrawlerOnNode", true);
                $out.write_string (nodeName);
                $in = _invoke ($out);
                String $result[][] = pl.edu.agh.ecm.crawler.generated.RemoteManager.propListHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return stopCrawlerOnNode (nodeName        );
            } finally {
                _releaseReply ($in);
            }
  } // stopCrawlerOnNode

  public pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty[] stopCrawlerOnNodes (String[] nodes)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("stopCrawlerOnNodes", true);
                pl.edu.agh.ecm.crawler.generated.RemoteManager.stringListHelper.write ($out, nodes);
                $in = _invoke ($out);
                pl.edu.agh.ecm.crawler.generated.RemoteManager.nodeProperty $result[] = pl.edu.agh.ecm.crawler.generated.RemoteManager.nodesPropertiesHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return stopCrawlerOnNodes (nodes        );
            } finally {
                _releaseReply ($in);
            }
  } // stopCrawlerOnNodes

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:RemoteManager/RemoteManagerServer:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _RemoteManagerServerStub