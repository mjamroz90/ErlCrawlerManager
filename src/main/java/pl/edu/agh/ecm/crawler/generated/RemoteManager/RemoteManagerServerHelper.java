package pl.edu.agh.ecm.crawler.generated.RemoteManager;


/**
* pl/edu/agh/ecm/crawler/generated/RemoteManager/RemoteManagerServerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from src/main/idl/RemoteManager.idl
* środa, 26 wrzesień 2012 18:28:29 CEST
*/

abstract public class RemoteManagerServerHelper
{
  private static String  _id = "IDL:RemoteManager/RemoteManagerServer:1.0";

  public static void insert (org.omg.CORBA.Any a, pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServerHelper.id (), "RemoteManagerServer");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_RemoteManagerServerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer)
      return (pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      pl.edu.agh.ecm.crawler.generated.RemoteManager._RemoteManagerServerStub stub = new pl.edu.agh.ecm.crawler.generated.RemoteManager._RemoteManagerServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer)
      return (pl.edu.agh.ecm.crawler.generated.RemoteManager.RemoteManagerServer)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      pl.edu.agh.ecm.crawler.generated.RemoteManager._RemoteManagerServerStub stub = new pl.edu.agh.ecm.crawler.generated.RemoteManager._RemoteManagerServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}