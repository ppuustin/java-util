package quid.pro.quo.kafka;

/**
 * http://howtoprogram.xyz/2016/06/04/write-apache-kafka-custom-partitioner/
 */
public class Partitioner
{
    
}
/*

import java.util.Map;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class Partitioner implements Partitioner
{
    @Override
    public void configure(Map<String, ?> map)
    {
    }

    @Override
    public int partition(String string, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster clstr)
    {
        return 0;
    }

    @Override
    public void close()
    {
    }
 
    public int partition(Object key, int a_numPartitions)
    {
        int partition = 0;
        String stringKey = (String) key;
        int offset = stringKey.lastIndexOf('.');
        if (offset > 0) {
           partition = Integer.parseInt( stringKey.substring(offset+1)) % a_numPartitions;
        }
        return partition;
    }   
}
*/

