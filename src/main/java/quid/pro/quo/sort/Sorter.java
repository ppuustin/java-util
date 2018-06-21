package quid.pro.quo.sort;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A sorting utility for <code>Map</code> objects.
 * 
 * <code>
 * Map<Integer, Integer> m = new HashMap<>();
 * m.put( 2, 20 );
 * m.put( 1, 10 );
 * m.put( 3, 30 );
 *         
 * System.out.println( m );
 * System.out.println( Sorter.sortByValueAsc(m) );         
 * System.out.println( Sorter.sortByValueDesc(m) );
 * System.out.println( Sorter.sortByKey(m) );
 * </code>
 */
public class Sorter
{    
    // -----------------------------------------------------------------------
    //  Public methods    
    
    public static void main( String[] args ) throws Exception
    {
        Map<Integer, Integer> m = new HashMap<>();
        m.put( 2, 20 );
        m.put( 1, 10 );
        m.put( 3, 30 );
        System.out.println( m );
        System.out.println( Sorter.sortByValueAsc(m) );
        System.out.println( Sorter.sortByValueDesc(m) );
        System.out.println( Sorter.sortByKey(m) );     
    }
    
    // -----------------------------------------------------------------------    

    public static <K,V extends Comparable<? super V>>                    
        SortedSet<Map.Entry<K,V>> sortByValueAsc(Map<K,V> map) 
    {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
            new Comparator<Map.Entry<K,V>>()
            {                    
                @Override 
                public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2)
                {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
                }
            }
        );

        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }     
    
    // -----------------------------------------------------------------------    

    public static <K,V extends Comparable<? super V>>                    
        SortedSet<Map.Entry<K,V>> sortByValueDesc(Map<K,V> map) 
    {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
            new Comparator<Map.Entry<K,V>>()
            {                    
                @Override 
                public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2)
                {
                    int res = e2.getValue().compareTo(e1.getValue());
                    return res != 0 ? res : 1;
                }
            }
        );

        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    } 
                            
    // ----------------------------------------------------------------
    
    public static <K, V extends Comparable<? super V>> Map<K, V> 
        sortByKey( Map<K, V> map )
    {
        TreeSet<K> ts = new TreeSet<K>( map.keySet() );
        
        Map<K, V> result = new LinkedHashMap<>();
        for (K t : ts)
        {
            result.put( t, map.get(t) );
        }
        return result;
    }
}

