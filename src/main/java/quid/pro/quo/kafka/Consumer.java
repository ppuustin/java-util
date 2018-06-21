package quid.pro.quo.kafka;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 
 */
public class Consumer
{
    // -----------------------------------------------------------------------
    //  Public methods
    
    public static void main( String[] args ) throws Exception
    {          
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "some.url:9091,some.url:9092"); 
        kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
        kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");          
        
        List<String> topics = Arrays.asList( "topic-1" );
        //KafkaConsumer<String, String> consumer = null;
                
        try {
            /*        
            consumer = new KafkaConsumer<String,String>(kafkaProps);
            consumer.subscribe( topics );

            long start = System.currentTimeMillis();
            long end = System.currentTimeMillis();
            long tot = 3*60*1000;
            
            while ( (end-start) < tot )
            {
                ConsumerRecords<String, String> records = consumer.poll(100);
            
                if ( records.count() > 0 )
                {
                    for ( ConsumerRecord<String, String> record : records )
                    {
                        record.value();
                    }              
                }    
                end = System.currentTimeMillis(); 
            }
            
            */  
        } finally {
            //close( consumer );
        }        
    }
    
    // ----------------------------------------------------------------    
/*    
    public void processOffset( KafkaConsumer consumer, 
            ConsumerRecords<String, String> records ) throws Exception
    {
        for ( TopicPartition partition : records.partitions() )
        {
            List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
            for (ConsumerRecord<String, String> record : partitionRecords)
            {
                System.out.println(record.offset() + ": " + record.value());
            }
            long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
            consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
        }                
    }    
*/    
    // ----------------------------------------------------------------    
/*    
    public void close( KafkaConsumer consumer ) throws Exception
    {
        try {
            if ( consumer != null )
                consumer.close(); 
        } catch (Exception e) {}  
    }    
*/    
}
