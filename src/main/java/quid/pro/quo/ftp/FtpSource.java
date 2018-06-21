package quid.pro.quo.ftp;

import java.io.BufferedReader;
import java.io.InputStream;

/*
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
*/

/**
 * An utility to ftp using JSCH library.
 * http://www.jcraft.com/jsch/
 * https://mvnrepository.com/artifact/com.jcraft/jsch
 */
public class FtpSource
{ 
    // ----------------------------------------------------------------
    
    public static void main( String[] args ) throws Exception
    {  
        String dir = "/home/some/dir/";
        
        InputStream is = null;
        BufferedReader br = null;        
        
        //Session session = null;
        //ChannelSftp channel = null;
        
        FtpSource s = new FtpSource();
        
        try {
            /*
            session = s.getSession();
            channel = s.getChannel( session );
            Vector<ChannelSftp.LsEntry> list = channel.ls( dir );
            
            for( ChannelSftp.LsEntry entry : list )
            {                      
                String file = dir + entry.getFilename();
                is = s.getInputStream( channel, file );
                // do something with the file
                // construct ex a reader to read ascii file
                s.close( is, br );
            }           
            */
                    
        } finally {
            //s.close( is, br );
            //s.close( session, channel );
        }        
        
    }
    
    // -----------------------------------------------------------------------
    //  Public methods
    
/*    
    public InputStream getInputStream( ChannelSftp ch, String uri ) throws Exception
    {              
        return ch.get( uri ); 
    }    
*/    
    // --------------------------------------------------------
/*    
    public Session getSession() throws Exception
    {      
        Session session = null; 
        
        String user = "usr";
        String pwd = "pwd";
        String ip = "10.10.10.10";
        String port = "22";
                
        JSch jsch = new JSch();
        session = jsch.getSession( user, ip, Integer.parseInt(port) );
        session.setConfig( "StrictHostKeyChecking", "no" );
        session.setPassword( pwd );
        session.connect();
        return session;
    }    
*/
    
    // --------------------------------------------------------
/*    
    public ChannelSftp getChannel( Session session ) throws Exception
    {                        
        Channel base = session.openChannel( "sftp" );
        base.connect();    
        return (ChannelSftp) base;
    }     
*/    
    // --------------------------------------------------------    
    
/*    
    public void close( Session session, ChannelSftp channel )
    {
        try {
            if ( channel != null )
                channel.exit();    
        } catch (Exception e) {}        

        try {
            if ( session != null )
                session.disconnect();  
        } catch (Exception e) {}
    }    
*/
    
    // --------------------------------------------------------    
    
    public void close( InputStream is, BufferedReader br )
    {
        try {
            if ( is != null )
                is.close();
        } catch (Exception e) {}   
        
        try {
            if ( br != null )
                br.close();
        } catch (Exception e) {}   
    }    
}
