package quid.pro.quo.rand;

/**
 * Random integer and character generator.
 * 
 * <code>randomInt(bound)</code> just a call through to <code>java.util.Random</code>
 * <code>randomCharacter(bound,case)</code>
 * 
 * <code>
 * //lowcase a to c
 * Random.randomCharacter( 3, Random.LOW )
 * 
 * //uppercase A to C
 * Random.randomCharacter( 3, Random.CAP )
 * 
 * //uppercase between A and Z
 * Random.randomCharacter( A_Z, Random.CAP )
 * </code>
 */
public class Random
{
    public static int A_Z = 25;
    public static int CAP = 65;
    public static int LOW = 97;

    public static java.util.Random r = new java.util.Random();
    
    // -----------------------------------------------------------------------
    //  Public methods

    public static void main( String[] args ) throws Exception
    {     
        for ( int i = 0; i < 10; ++i )
        {
            String key = String.valueOf( Random.randomCharacter( 3, Random.LOW ) );
            System.out.println( key );
        }        
    }
      
    // -----------------------------------------------------
    
    public static int randomInt( int _bound )
    {
        return r.nextInt( _bound );
    }

    // -----------------------------------------------------
    
    public static char randomCharacter( int _bound, int _case )
    {
        return (char) ( _case + r.nextInt( _bound ) );
    }
        
}

