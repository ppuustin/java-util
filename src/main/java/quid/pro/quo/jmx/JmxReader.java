package quid.pro.quo.jmx;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import quid.pro.quo.unit.UnitFormat;

/**
 * Simple JMX metric poller.
 */
public class JmxReader
{              
    // -----------------------------------------------------------------------
    //  Public methods

    public static void main( String[] args ) throws Exception
    {        
        int time = 1;
        long intervalSecs = 10;
        
        String port = "9011";
        String url = "10.10.10.10:" + port;        
        String jmxUrl = "service:jmx:rmi:///jndi/rmi://" + url + "/jmxrmi" ;        
                
        String [] objs = {            
            "java.lang:type=Memory",
            "java.lang:type=OperatingSystem"
        };

        String [][] attrs = { 
            { "HeapMemoryUsage" },
            { "ProcessCpuLoad" }            
        };   
        
        JMXConnector jmxc = null;
                
        try {
            
            jmxc = getJMXConnector( jmxUrl );
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();            
            poll( mbsc, time, intervalSecs, objs, attrs );
                       
        } finally {
            try {
                if ( jmxc != null )
                    jmxc.close();                
            } catch (Exception e) {}          
        }
                
    }
    
    // ----------------------------------------------------------------
    
    public static void poll( MBeanServerConnection mbsc, long timeMins, long intervalSecs, 
            String [] objs, String [][] attrs ) throws Exception
    {           
        long sleep = intervalSecs * 1000;
        long count = (timeMins * 60 * 1000)/sleep;
        
        for ( long a = 0; a < count; ++a )
        {
            StringBuilder sb = new StringBuilder();
            
            for ( int b = 0; b < objs.length; ++b )
            {
                ObjectName on = new ObjectName( objs[b] );
                AttributeList al = mbsc.getAttributes( on, attrs[b] );
                
                Long prev = new Long(0);
                for ( int c = 0; c < al.size(); ++c )
                {           
                    Attribute at = (Attribute)al.get(c);
                    
                    Object o = at.getValue();
                    
                    if ( o instanceof Double )
                    {
                        Double val = (Double)at.getValue();                           
                        System.out.println( on + "  " + UnitFormat.toBinSuffix(val) );  
                        
                        sb.append( val ).append( ";" );
                    } else if ( o instanceof Long )
                    { 
                        Long val = (Long)at.getValue();
                        Double dval = ((Long)((val-prev)/intervalSecs)).doubleValue();
                        System.out.println( on + "  " + UnitFormat.toBinSuffix( dval ) );  
                        
                        sb.append( val ).append( ";" );
                        prev = val;
                        
                    } else if ( o instanceof CompositeDataSupport ) 
                    {
                        CompositeDataSupport cds = (CompositeDataSupport)at.getValue();
                        Long val = (Long)cds.get( "used" );
                        
                        sb.append( val ).append( ";" );
                        
                        System.out.println( on + "  " + UnitFormat.toBinSuffix(val) );   
                        
                    } else {
                        System.out.println( "unknown type: " + o.getClass().getName() );   
                    }
                }          
            }
            
            sb.append( "\n" );
            
            Thread.sleep( sleep );
        }
                
    }

    // ----------------------------------------------------------------    
    
    public static JMXConnector getJMXConnector( String jmxUrl  ) throws Exception
    {  
        JMXServiceURL serviceUrl = new JMXServiceURL( jmxUrl );
        return JMXConnectorFactory.connect( serviceUrl ); 
    }      
    
}