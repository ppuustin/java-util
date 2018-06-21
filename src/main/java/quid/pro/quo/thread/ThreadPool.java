package quid.pro.quo.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
    int threads = 10;
    int wait_secs = 10; 

    ThreadPool tp = new ThreadPool();
    ThreadPoolExecutor pool = tp.getThreadPoolExecutor( threads, threads );

    for ( int i = 0; i < threads; ++i )
    {            
        pool.execute( new <? implements Runnable> );
    }

    tp.shutdown( pool, wait_secs*1000 );
    tp.shutdownNow( pool, wait_secs/2 );
        
 */
public class ThreadPool implements Runnable
{
    static boolean PRINT = true;
 
    String name;    
    
    // -----------------------------------------------------------------------
    //  Public methods
    
    public static void main( String[] args ) throws Exception
    {     
        int threads = 10;
        int wait_secs = 10; 

        ThreadPool tp = new ThreadPool( "" );
        ThreadPoolExecutor pool = tp.getThreadPoolExecutor( threads, threads );

        for ( int i = 0; i < threads; ++i )
        {            
            //pool.execute( new <? implements Runnable> );
            pool.execute( new ThreadPool( new Integer(i).toString() ) );
        }

        tp.shutdown( pool, wait_secs*1000 );
        tp.shutdownNow( pool, wait_secs/2 );
    }

    // ------------------------------------------------------------
    
    public ThreadPool( String name )
    {
        this.name = name;
    }

    // ------------------------------------------------------------    
    
    @Override
    public void run()
    {
        System.out.println( "executing " + name + " ..." );
    }
    
    // ------------------------------------------------------------
    
    public ThreadPoolExecutor getThreadPoolExecutor( int corePoolSize, int maximumPoolSize ) throws Exception
    {       
        RejectedExecutionHandlerImpl handler = new RejectedExecutionHandlerImpl();
        ThreadFactory factory = Executors.defaultThreadFactory();
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(corePoolSize);
        
        return new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize, 10, TimeUnit.SECONDS, queue, factory, handler );
        
    }
    
    // ------------------------------------------------------------
    
    public void shutdown( ThreadPoolExecutor pool, long wait ) throws Exception
    { 
        if (PRINT) System.err.println( "shutdown ..." );
        Thread.sleep( wait );        
        pool.shutdown();
    }
    
    // ------------------------------------------------------------
    
    public void shutdownNow( ThreadPoolExecutor pool, long wait ) throws Exception
    { 
        System.err.println( "awaitTermination ..." );
        
        if ( !pool.awaitTermination( wait, TimeUnit.SECONDS ) ) 
        {
            if (PRINT) System.err.println( "shutdownNow, cancel ongoing ..." );
            pool.shutdownNow();
        }
                
        do {
            if (PRINT) System.err.println( "terminating ..." );
        } while ( !pool.awaitTermination( wait*2, TimeUnit.SECONDS ) );
    }
    
    
}
