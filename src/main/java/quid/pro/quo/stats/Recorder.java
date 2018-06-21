
package quid.pro.quo.stats;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import quid.pro.quo.sort.Sorter;
import quid.pro.quo.unit.UnitFormat;

/**
 */
public class Recorder
{
    Map<String, Map<String, Long>> allStats = new HashMap<String, Map<String, Long>>();

    boolean PRINT;

    // -----------------------------------------------------------------------
    //  Public methods
    
    public static void main( String[] args ) throws Exception
    {    
        Recorder r = new Recorder( false );
        String f = "C:/Temp";
        fileStats( f, r );
        r.print();
    }
    
    // ----------------------------------------------------------------
    
    public static void fileStats( String file, Recorder r ) throws Exception
    {
        File fil = new File( file );
        if ( fil.isDirectory() )
        {
            r.dirStat( fil );
            
            File [] files = fil.listFiles();
            for ( File f : files )
            {  
                fileStats( f.getAbsolutePath(), r );
            }
            
        } else {
            r.fileStat( fil );            
            r.recordFileSize( fil.length() );
            
        }
    }
    
    long minFile = Long.MAX_VALUE;
    long maxFile = 0l;
    long fileSizesTot = 0; 
    long filesTot = 0;

    // ----------------------------------------------------------------   
    
    public Recorder( boolean PRINT )
    {
        this.PRINT = PRINT;
    }

    // ----------------------------------------------------------------     
    
    public void recordFileSize( long next )
    {            
        minFile = min( next, minFile );
        maxFile = max( next, maxFile );
        this.fileSizesTot = fileSizesTot + next;
    }
    
    // ----------------------------------------------------------------        

    public void dirStat( File dir )
    {
        count( "dirs", dir.getAbsolutePath() );
    }

    // ----------------------------------------------------------------        

    public void fileStat( File file )
    {
        this.filesTot++;
        count( "files", file.getAbsolutePath() );
    }

    // ----------------------------------------------------------------        

    public void fileLen( File file )
    {

    }    
    
    // ----------------------------------------------------------------                

    protected long min( long what, long whatMin )
    {
        return what < whatMin ? what : whatMin;
    }

    protected long max( long what, long whatMax )
    {            
        return what > whatMax ? what : whatMax;            
    }      

    // ----------------------------------------------------------------        

    public void count( String key, String what )
    {
        count( key, what, 1L );
    }
    
    // ----------------------------------------------------------------        

    public void count( String key, String what, Long inc )
    {
        Map<String, Long> stats = null;
        if ( allStats.containsKey(key) )
        {
            stats = allStats.get(key);
        } else {
            stats = new HashMap<String, Long>();
        }
        count( stats, what, inc );
        allStats.put( key, stats );
    }        

    // ----------------------------------------------------------------

    public void count( Map<String, Long> stats, String key, Long inc )
    {
        if ( stats.containsKey(key) )
        {
            Long count = stats.get(key);
            stats.put( key, count + inc );
        } else
        {
            stats.put( key, inc );
        }
    }
   

    public void print()
    {        
        long fileSizeAvg = filesTot != 0 ? (fileSizesTot/filesTot) : 0;
        System.out.println( 
            "\nfiles  tot: " + filesTot + " (nbr)" +
            "\nsize   avg: " + UnitFormat.toBinSuffix(fileSizeAvg) + " (bytes) " +
                    " min: " + UnitFormat.toBinSuffix(minFile) + 
                    " max: " + UnitFormat.toBinSuffix(maxFile) +
            "\nsize   tot: " + UnitFormat.toBinSuffix(fileSizesTot) + " (bytes)"
        );  
        
        for ( Map.Entry<String, Map<String, Long>> e : allStats.entrySet() ) 
        {
            String k = e.getKey();
            Map<String, Long> v = e.getValue();

            SortedSet<Map.Entry<String,Long>> sorted 
                = Sorter.sortByValueDesc( v );

            long totStats = 0;
            long totStat = 0;

            StringBuilder b = new StringBuilder();

            for ( Map.Entry<String,Long> e2 : sorted )
            {
                String k2 = e2.getKey();
                Long v2 = e2.getValue();
                b.append( k2 ).append( " " ).append( v2 ).append( "\n" );

                ++totStat;
                totStats = totStats + v2;
            }       

            System.out.println( "---------------------" );
            System.out.println( "stat type:  " + k );                
            System.out.println( "stat count: " + v.keySet().size() );
            System.out.println( "stat tot: " + totStats );                
            System.out.println( "stat avg: " + totStats/totStat );
            System.out.println( "---------------------" );
        }            
    }

}
