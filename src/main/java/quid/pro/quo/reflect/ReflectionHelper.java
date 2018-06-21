package quid.pro.quo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * A simple helper for reflection purposes.
 * See main method for an example.
 */
public class ReflectionHelper
{    
    // -----------------------------------------------------------------------
    //  Public methods

    public static void main( String[] args ) throws Exception
    {
        String c = "quid.pro.quo.txt.StringFormat";
        Object o = newInstance( c );
        
        System.out.println( o.getClass().getName() );
        
        Object o2 = invoke( o, "format",
                new Class[]{ String.class, Object[].class }, 
                new Object[]{ "ab{0}{1}", new Object[]{ "c","d" } } );
        
        System.out.println( o2 );
    }
    
    // ----------------------------------------------------------------

    public static Object newInstance( String name ) throws Exception
    {
        return newInstance( name, new Class[]{}, new Object[]{} );
    }
    
    // ----------------------------------------------------------------

    public static Object newInstance( String name, Class [] types, Object [] args ) throws Exception
    {
        Class <?> _class = Class.forName( name );
        Constructor <?> c = _class.getConstructor( types );
        return c.newInstance(args);
    }

    // ----------------------------------------------------------------

    public static Object invoke( Object o, String method ) throws Exception
    {
        return invoke( o, method, new Class[]{}, new Object[]{} );
    }
    
    // ----------------------------------------------------------------

    public static Object invoke( Object o, String method, Class [] types, Object [] args ) throws Exception
    {
        Method m = o.getClass().getDeclaredMethod( method, types );
        m.setAccessible( true );
        return m.invoke( o, args );
    } 
}
