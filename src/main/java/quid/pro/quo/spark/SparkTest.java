package quid.pro.quo.spark;

/*
import java.util.Arrays;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.fpm.AssociationRules;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowth.FreqItemset;
*/

/**
 * 
 */
public class SparkTest
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "spark.." );
        System.setProperty( "hadoop.home.dir", "D:/hadoop/" );
        // needs -> /bin/winutils.exe
              
        String master = "local";
/*        
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName( SparkTest.class.getSimpleName() );
        sparkConf.setMaster( master );
        
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        
        JavaRDD<FPGrowth.FreqItemset<String>> freqItemsets = sc.parallelize(Arrays.asList(
            new FreqItemset<String>(new String[] {"a"}, 15L),
            new FreqItemset<String>(new String[] {"b"}, 35L),
            new FreqItemset<String>(new String[] {"a", "b"}, 12L)
        ));
        
        AssociationRules arules = new AssociationRules().setMinConfidence(0.8);
        JavaRDD<AssociationRules.Rule<String>> results = arules.run(freqItemsets);
             
        StringBuffer sb = new StringBuffer();
        sb.append( "\n****************************\n" );
        
        for (AssociationRules.Rule<String> rule : results.collect())
        {
            sb.append( rule.javaAntecedent() ).append(" => ");
            sb.append( rule.javaConsequent() ).append(", ");
            sb.append(rule.confidence()).append( "\n" );
        }
        
        sb.append( "****************************\n" );
        
        System.out.println(sb.toString());
*/        
    }
    
}
