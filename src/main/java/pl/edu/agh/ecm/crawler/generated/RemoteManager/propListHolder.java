package pl.edu.agh.ecm.crawler.generated.RemoteManager;


/**
* pl/edu/agh/ecm/crawler/generated/RemoteManager/propListHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from src/main/idl/RemoteManager.idl
* środa, 26 wrzesień 2012 18:28:29 CEST
*/

public final class propListHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[][] = null;

  public propListHolder ()
  {
  }

  public propListHolder (String[][] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = pl.edu.agh.ecm.crawler.generated.RemoteManager.propListHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    pl.edu.agh.ecm.crawler.generated.RemoteManager.propListHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return pl.edu.agh.ecm.crawler.generated.RemoteManager.propListHelper.type ();
  }

}
