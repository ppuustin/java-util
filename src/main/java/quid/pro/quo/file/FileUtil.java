package quid.pro.quo.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import static quid.pro.quo.time.TimeUtils.toLocalTime;

/**
 * This is a misc purposes file utility. 
 * 
 * Copies files and directories recursively
 * from 'from' directory/file to 'to' directory/file.
 * 
 * Initializes readers/writers.
 */
public class FileUtil
{
    static int KB = 1024;
    static int BUFFER_SIZE = KB * 128;
    static int count = 0;
    
    // -----------------------------------------------------------------------
    //  Public methods

    public static void main( String[] args ) throws Exception
    {
        String from = "D:/from/dir";
        String to = "D:/to/dir";
        
        long start = System.currentTimeMillis();
        
        FileUtil fu = new FileUtil();
        //fu.copyFile( from, to );
        //fu.compare( "D:/some/large/file.txt" );
        //fu.deleteTree( "D:/some/dir" );
        
        long end = System.currentTimeMillis();
        
        System.out.println( toLocalTime( end-start ) );        
    }    
    
    // -----------------------------------------------------------------------
      
    public void deleteTree( String dir ) throws Exception
    {   
        File file = new File( dir );
        
        if ( file.isDirectory() )
        {
            File [] files = file.listFiles();
            for ( File f : files )
            {
                deleteTree( f.getAbsolutePath() );
            } 
        } else {
            
            if ( ++count % 1000 == 0 )
                System.out.println( "delete count: " + count );
            
            file.delete();
        }
                
    }      
    
    // -----------------------------------------------------------------------

    public void copyFile( String fromFile, String toFile ) throws Exception
    {
        copyFile( new File( fromFile ), new File( toFile ) );
    }    

    // -----------------------------------------------------------------------

    public void copyFile( File fromFile, File toFile ) throws Exception
    {
        if ( fromFile.isDirectory() )
        {
            if ( !toFile.exists() )
            {
                toFile.mkdirs();
            }

            String files [] = fromFile.list();

            for ( String f : files )
            {
                copyFile( new File( fromFile, f ), new File( toFile, f ) );
            }
        }
        else
        {
            if ( ++count % 100 == 0 )
                System.out.println( "copy count: " + count );
            
            doCopy( fromFile, toFile );
        }
        
    }    
           
    // -----------------------------------------------------------------
 
    public static BufferedReader getBufferedReader( String file ) throws Exception
    {
        Reader r = null;
                
        if ( file.endsWith( ".gz" ) )
        {
            InputStream fileStream = new FileInputStream(file);
            InputStream gzipStream = new GZIPInputStream(fileStream);
            r = new InputStreamReader( gzipStream );
        } else {
            File f = new File( file );
            r = new java.io.FileReader( f );
        }
        
        return new BufferedReader( r );
    }
    
    // -----------------------------------------------------------------
 
    public static BufferedWriter getBufferedWriter( String file, boolean append ) throws Exception
    {
        File f = new File( file );
        if ( !f.exists() ) f.getParentFile().mkdirs();
        return new BufferedWriter( new FileWriter( f.getAbsoluteFile(), append ) );
    }    
    
    // -----------------------------------------------------------------
        
    public static List<String> getLines( BufferedReader b ) throws Exception
    {
        List<String> lines = new ArrayList<>();
        
        try {
            
            String line = null;
            while ( (line = b.readLine()) != null )
            {
                lines.add(line);
            }

        } finally
        {
            close(b);
        }

        return lines;
        
    }    
   
    // --------------------------------------------------------

    public void compare( String file ) throws Exception
    {
        System.out.println( "FileInputStream ..." );    
        long start = System.currentTimeMillis();         
        testFis(file);
        long diff = System.currentTimeMillis() - start;  
        System.out.println( "FileInputStream diff: " + diff );
        
        System.out.println( "Scanner ..." );
        start = System.currentTimeMillis();
        testScanner(file);
        diff = System.currentTimeMillis() - start;  
        System.out.println( "Scanner diff: " + diff );
                     
        System.out.println( "BufferedInputStream ..." );
        start = System.currentTimeMillis();
        testBis(file);       
        diff = System.currentTimeMillis() - start;  
        System.out.println( "BufferedInputStream diff: " + diff );
        
        System.out.println( "MappedByteBuffer ..." );
        start = System.currentTimeMillis();
        testMbb(file);
        diff = System.currentTimeMillis() - start;  
        System.out.println( "MappedByteBuffer diff: " + diff );              
    }
    
    // -----------------------------------------------------------------------
    //  Private methods

    private void testFis( String file ) throws Exception
    {
        FileInputStream fis = new FileInputStream( file ); 
        int _byte = -1;
        while( ( _byte = fis.read() ) >= 0 )
        {      
        } 
        fis.close();      
    }
    
    // --------------------------------------------------------

    private void testScanner( String file ) throws Exception
    {
        FileInputStream fis = new FileInputStream( file ); 
        Scanner sc = new Scanner( fis, "UTF-8" );
        
        while ( sc.hasNextLine() )
        {
            //String line = sc.nextLine();
        }
        if ( sc.ioException() != null ) 
        {
            throw sc.ioException();
        }
        sc.close();
        fis.close();     
    }
    
    // --------------------------------------------------------

    private void testBis( String file ) throws Exception
    {
        FileInputStream fis = new FileInputStream( file ); 
        BufferedInputStream bis = new BufferedInputStream( fis );
        int _byte = 0;        
        while( ( _byte = bis.read() ) >= 0 )
        {   
        }
        bis.close();
        fis.close();    
    }
    
    // --------------------------------------------------------

    private void testMbb( String file ) throws Exception
    {
        RandomAccessFile raf = new RandomAccessFile( file, "rw" );
        MappedByteBuffer buffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, Integer.MAX_VALUE - 1 );
        long len = raf.length();
        for ( int i = 0; i < len; ++i )
	{
            char c = (char) buffer.get(i);
        }  
        raf.close();
        
        //FileChannel fc = new RandomAccessFile(file, "r").getChannel();
        //MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        //fc.close();
        
/*        
        RandomAccessFile raf = new RandomAccessFile( file, "rw" );
	MappedByteBuffer mbb = raf.getChannel().map(
                FileChannel.MapMode.READ_WRITE, 0, Integer.MAX_VALUE - 1 );
        long len = raf.length();
                
        StringBuilder b = new StringBuilder();
        
        for ( int i = 0; i < len; ++i )
	{
            char c = (char) mbb.get(i);
            b.append(c);
            if ( c == '\n' )
            {
                String line = b.toString();
                System.out.println( line );
                b.delete( 0, b.length() );
            }
        }   
*/        
    }    
    
    
    private void doCopy( File fromFile, File toFile ) throws Exception
    {
        InputStream in = null;
        OutputStream out = null;

        try
        {
            in = new FileInputStream( fromFile );
            out = new FileOutputStream( toFile );

            redirectStream( in, out );
        }
        finally
        {
            close( in, out );
        }
    }

    // -----------------------------------------------------------------------

    private void redirectStream( InputStream in, OutputStream out ) throws Exception
    {
        byte[] buffer = new byte[BUFFER_SIZE];

        int len;
        while( (len = in.read( buffer )) > 0 )
        {
            out.write( buffer, 0, len );
        }
    }     
    
    // --------------------------------------------------------

    private void close( InputStream in, OutputStream out )
    {
        try
        {
            if( in != null )
                in.close();
        } catch( Exception e ) {}

        try
        {
            if( out != null )
                out.close();
        } catch( Exception e ) {}
    }     
    
   // --------------------------------------------------------    
    
    private static void close( Reader r )
    {
        try {
            if ( r != null )
                r.close();
        } catch ( Exception e ) {}           
    }    
    
}
