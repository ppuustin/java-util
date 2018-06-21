package quid.pro.quo.unit;

import java.text.DecimalFormat;

/**
 *  Transforms double numbers to smaller decimal
 *  format strings with an appropriate unit suffix.
 *   <code>
 *   double veryBigValue = 1.525681992487E12;
 *   System.out.println( UnitFormat.toBinSuffix(veryBigValue) ); 
 *   > 1,39 T
 *   </code>
 */
public class UnitFormat
{
    // -----------------------------------------------------------------------
    //  Public methods

    public static void main( String[] args ) throws Exception
    {
        double veryBigValue = 1.525681992487E12;
        System.out.println( UnitFormat.toDecSuffix(veryBigValue) );  
        System.out.println( UnitFormat.toBinSuffix(veryBigValue) );  
    }
    
    // ----------------------------------------------------------------

    public static String toDecSuffix( double value )
    {
        return toSuffix( value, 1000 );
    }

    // ----------------------------------------------------------------

    public static String toBinSuffix( double value )
    {
        return toSuffix( value, 1024 );
    }
    
    // -----------------------------------------------------------------------
    //  Private methods    
    
    private static String toSuffix( double value, long system )
    {
        long K = 1 * system;
        long M = K * system;
        long G = M * system;
        long T = G * system;
        long P = T * system;
        long E = P * system;

        if (value <  K)                return decFormat( value     ) + "";
        if (value >= K && value < M)   return decFormat( value / K ) + " K";
        if (value >= M && value < G)   return decFormat( value / M ) + " M";
        if (value >= G && value < T)   return decFormat( value / G ) + " G";
        if (value >= T && value < P)   return decFormat( value / T ) + " T";
        if (value >= P && value < E)   return decFormat( value / P ) + " P";
        if (value >= E)                return decFormat( value / E ) + " E";

        return "MEH?";
    }

    // ----------------------------------------------------------------
    
    private static String decFormat( double d )
    {
       return new DecimalFormat( "#.##" ).format(d);
    }    
}
