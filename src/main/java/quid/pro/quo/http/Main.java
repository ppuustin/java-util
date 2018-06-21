package quid.pro.quo.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import sun.misc.BASE64Encoder;

/**
 * Over simplified REST API example.
 */
public class Main
{
    // -----------------------------------------------------------------------
    //  Public methods
    
    public static void main( String[] args ) throws Exception
    { 
        String serviceUrl = "https://some.com/rest/getCoolStuff/";
        String name = "usr";
        String password = "pwd";
        
        // basic authentication usage is assumed here
        String authStr = name + ":" + password;        
        authStr = new BASE64Encoder().encode( authStr.getBytes() );
        
        URL url = new URL( serviceUrl );
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod( "GET" );
        conn.setRequestProperty( "Accept", "application/json" );
        conn.setRequestProperty( "Authorization", "Basic " + authStr );
        
        InputStream i = conn.getInputStream();
        Reader r = new InputStreamReader( i );
        BufferedReader b = new BufferedReader(r);

        String output;

        while ( (output = b.readLine()) != null )
        {
            System.out.println(output);
        }
        
        b.close();
        r.close();
        i.close();
    }
    
}
