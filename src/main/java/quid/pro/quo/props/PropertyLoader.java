package quid.pro.quo.props;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

/**
 *  Check first whether the file is in the execution dir.
 *  Then loads the file form the jar file.
 * 
 *  <code>
 *  Properties p = PropertyLoader.load( this.getClass(), "this.properties" );
 *  </code>
 */
public class PropertyLoader
{
    // -----------------------------------------------------------------------
    //  Public methods
    
    public static boolean exists( String file )
    {
        String curr = new File( "" ).getAbsolutePath();
        String f = curr + "/" + file;
        return new File( f ).exists();
    }
    
    // -----------------------------------------------------------------------      
    
    public static Properties load( Class caller, String file )
    {
        Properties prop = new Properties();
        
        try {
            
            String curr = new File( "" ).getAbsolutePath();
            
            if ( new File ( curr + "/" + file ).exists() )
            {
                File f = new File( curr + "/" + file );
                prop.load( new FileInputStream( f ) );                
            }
            else if ( file.startsWith( "file:" ) )
            {
                File f = new File( new URL ( file ).toURI());
                prop.load( new FileInputStream( f ) );
            } else
            {
                ClassLoader cl = caller.getClassLoader();
                prop.load( cl.getResourceAsStream( file ) );
            }    
            
        } catch ( Exception e )
        {
            throw new RuntimeException(e);
        }
        
        return prop;
    }
}

