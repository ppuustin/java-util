package quid.pro.quo.icf;

/**
 * A simple 'Intercepting filter pattern' example
 * https://en.wikipedia.org/wiki/Intercepting_filter_pattern
 */
public class Main
{
    // ----------------------------------------------------------------
    
    public static void main( String[] args ) throws Exception
    {  
        System.out.println( "START." );

        ProcessorManager mgr = new ProcessorManager();
        mgr.process();

        System.out.println( "DONE." );
    }
    
}
