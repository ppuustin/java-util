package quid.pro.quo.rbundle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * <code>ResourceBundle</code> reader.
 * Reads xml and properties type of bundles.
 */
public class BundleReader
{
    private ResourceBundle _bundle;
    
    // -----------------------------------------------------------------------
    //  Public methods
    
    public static void main( String args[] )
    { 
        //see: src/main/resources
        BundleReader b = new BundleReader( "TestBundle", Locale.ENGLISH );
        System.out.println( b.getString( "0x000000" ) );
    }
     
    public BundleReader( String bundle, Locale locale )
    {
        //_bundle = ResourceBundle.getBundle( bundle, new XMLResourceBundleControl() );
        _bundle = ResourceBundle.getBundle( bundle, locale );
    }
         
    public String getString( String id )
    {   
        return _bundle.getString( id );
    }
    
    // --------------------------------------------------------------------
    
    public class XMLResourceBundleControl extends ResourceBundle.Control
    {
        public List<String> getFormats(String baseName) 
        {
          return Collections.singletonList("xml");
        }
        
        public ResourceBundle newBundle(String baseName, Locale locale, String format,
            ClassLoader loader, boolean reload) 
                throws IllegalAccessException, InstantiationException, IOException 
        {

          ResourceBundle bundle = null;

          String bundleName = toBundleName(baseName, locale);
          String resourceName = toResourceName(bundleName, format);
          URL url = loader.getResource(resourceName);
          URLConnection connection = url.openConnection();
          if (reload) 
          {
            connection.setUseCaches(false);
          }
          InputStream stream = connection.getInputStream();
          BufferedInputStream bis = new BufferedInputStream(stream);
          bundle = new XMLResourceBundle(bis);
          bis.close();

          return bundle;
        }
    }
    
    // --------------------------------------------------------------------
    
    class XMLResourceBundle extends ResourceBundle
    {
        private Properties props;

        XMLResourceBundle(InputStream stream) throws IOException {
          props = new Properties();
          props.loadFromXML(stream);
        }

        protected Object handleGetObject(String key) {
          return props.getProperty(key);
        }

        public Enumeration<String> getKeys() {
          Set<String> handleKeys = props.stringPropertyNames();
          return Collections.enumeration(handleKeys);
        }
    }    
    
}
