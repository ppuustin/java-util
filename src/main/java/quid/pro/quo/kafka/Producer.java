package quid.pro.quo.kafka;

import java.util.Properties;

/**
 * 
 */
public class Producer
{
    // -----------------------------------------------------------------------
    //  Public methods
    
    public static void main( String[] args ) throws Exception
    {  
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "some.url:9091,some.url:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("compression.type", "none");

        String topic = "topic-1";
        String value = "value";
        
        //KafkaProducer<String, String> producer = null;
        
        try {
            //producer = new KafkaProducer<String, String>( kafkaProps );            
            //ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, value);
            
            //producer.send( r );
            //producer.send( r, new DefaultCallback() );            
            
        } finally {
            //close( producer );
        }
    }

    // ----------------------------------------------------------------    
/*    
    private class DefaultCallback implements Callback
    {
        public void onCompletion( RecordMetadata metadata, Exception e )
        {
            if( e != null )
            {
                e.printStackTrace();
            }
        }
    }    
*/    
    // ----------------------------------------------------------------    
/*    
    public void close( KafkaProducer producer )
    {
        try {
            if ( producer != null )
            {
                producer.close(); 
                producer = null;
            }
                
        } catch (Exception e) {}  
    }    
*/
    
}
