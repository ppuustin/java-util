package quid.pro.quo.txt;

import java.text.MessageFormat;

/**
 */
public class StringFormat
{
    public static String format( String text, Object[] args )
    {
        MessageFormat format = new MessageFormat( text );
        return format.format( args );
    }    
}
