package quid.pro.quo.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Simple time formatting utility.
 * 
 * (1) using default format e.x. for timing code
 *     <code>
 *     long start = System.currentTimeMillis();
 *     Thread.sleep( 1000 );
 *     long end = System.currentTimeMillis();      
 *     System.out.println( toLocalTime( end-start ) );
 *     </code>
 * > 00:00:01
 * 
 * (2) timestamp -> string
 *     <code>
 *     String str = "2017-02-10T12:00:00.000+02:00:00";
 *     long times = TimeUtils.toTimestamp( ISO_DATETIME_FORMAT, str );
 *     </code>
 * 
 * (3) string -> timestamp
 *     <code>
 *     long ts = System.currentTimeMillis();
 *     String str = toTimeString( ISO_DATETIME_FORMAT, ts )
 *     </code>
 */
public class TimeUtils
{
    public static String ISO_DATE_FORMAT = "yyyy-MM-dd";
    public static String ISO_DATETIME_FORMAT = "yyyy-MM-dd'T'kk:mm:ss.SSS";    
    
    // -----------------------------------------------------------------------
    //  Public methods
    
    public static void main( String[] args ) throws Exception
    {                    
        long start = System.currentTimeMillis();
        Thread.sleep( 1000 );
        long end = System.currentTimeMillis();
            
        System.out.println( toLocalTime( end-start ) );
    }
    
    // -----------------------------------------------------------------------
    
    public static String toTimeString( String format, long timestamp ) throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat( format );
        return sdf.format( new Date(timestamp) );
    }
    
    // -----------------------------------------------------------------------
    
    public static String toLocalTime( long timestamp ) throws Exception
    {   
        return String.format( "%tT", timestamp - TimeZone.getDefault().getRawOffset() );
    }
        
    // -----------------------------------------------------------------------
    
    public static long toTimestamp( String format, String time ) throws Exception
    {      
        Date date = new SimpleDateFormat(
                format, Locale.ENGLISH ).parse( time );
        return date.getTime();
    }
    
}
