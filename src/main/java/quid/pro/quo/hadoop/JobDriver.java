package quid.pro.quo.hadoop;

/*
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

*/

/**
 */
public class JobDriver
//public class JobDriver extends Configured implements Tool
{  
    // -----------------------------------------------------------------------
    
    public static void main( String[] args ) throws Exception
    {              
        try
        {
            //int exitCode = ToolRunner.run( new JobDriver(), args );
            int exitCode = 0;
            
            if ( exitCode != 0 )
            {
                String msg = String.join( " ", "error: ", args[0] );
                System.out.println( msg );
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
            System.exit( 1 );
        }
    }
    
    // -----------------------------------------------------------------------
    
/*    
    @Override
    public int run( String[] args ) throws Exception
    {      
        Job job = this.getJob( args[0], args[2] );
    
        //serial
        return job.waitForCompletion( true ) ? 0 : 1;
       
        //parallel
        //job.submit();
        //return 0;
    }     
*/    
    
/*
   // ----------------------------------------------------------------
    
    public Job getJob( String in, String out ) throws Exception
    {
        Configuration conf = new Configuration();
        
        Job job = Job.getInstance( conf, "word count" );
        job.setJarByClass( JobExecutor.class );
        
        job.setMapperClass( TokenizerMapper.class );
        //job.setCombinerClass();
        job.setReducerClass( IntSumReducer.class );
              
        job.setOutputKeyClass( Text.class );
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath( job, new Path(in) );
        FileOutputFormat.setOutputPath( job, new Path(out) );
    
        return job;    
    }    
*/
     
    // ----------------------------------------------------------------
/*    
    public static class TokenizerMapper
       extends Mapper<Object, Text, Text, IntWritable>
    {        
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map( Object key, Text value, Context context ) 
                throws IOException, InterruptedException 
        {                  
            StringTokenizer itr = new StringTokenizer( value.toString() );
            while ( itr.hasMoreTokens() )
            {
              word.set( itr.nextToken() );
              context.write( word, one );
            }
        } // map
    
    }  // mapper   
*/
    
/*

    // ----------------------------------------------------------------
    
    public static class IntSumReducer
       extends Reducer<Text, IntWritable, Text, IntWritable> 
    {
        private IntWritable result = new IntWritable();
        
        public void reduce(Text key, Iterable<IntWritable> values, Context context) 
                throws IOException, InterruptedException 
        {                                 
            int sum = 0;
            for ( IntWritable val : values )
            {
              sum += val.get();
            }
            result.set( sum );
            context.write( key, result );                      
            
        } // reduce
                
    } // reducer    
    
*/    
    
}
