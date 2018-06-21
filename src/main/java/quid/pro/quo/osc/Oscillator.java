package quid.pro.quo.osc;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * A wave generator.
 */
public class Oscillator
{  
    // --------------------------------------------------------------
    
    public static void main(final String[] args) throws Exception
    {          
        String dir = "D:/testsig/";
        
        SHAPE shape = SHAPE.TRI;
        long freq = 1000;
        long sampleRate = freq * 288;
        long cycles = (sampleRate/freq) * 20;        
        
        Oscillator osc = new Oscillator( shape, sampleRate, freq );
        
        String file = dir + shape.toString().toLowerCase() + "_" + freq + "_hz.csv";
        
        osc.toFile(osc, file, cycles);

        for ( int i = 0; i < cycles; ++i )
        {
            //System.out.println( osc.nextSample() );
        }      
    }
    
    // --------------------------------------------------------------
    
    public void toFile( Oscillator osc, String file, long cycles ) throws Exception
    { 
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream( file ), "utf-8")))
        {         
            long timestamp = 0;
                
            for ( int i = 0; i < cycles; ++i )
            {
                String sample = new Double( osc.nextSample() + 1 ).toString();

                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd kk:mm:ss" );
                String t = sdf.format( new Date(timestamp) );
                
                writer.write( t + "," + sample );
                writer.write("\n");
                
                timestamp = timestamp + 1000*60*5;               
            }
        }
        
        System.out.println( "wrote: " + file );
    }
    
    // --------------------------------------------------------------

    public enum SHAPE
    {
        SIN, SQU, SAW, TRI, PUL, WHI
    }    
    
    public SHAPE shape;
    
    long sampleRate;
    long samplesPerPeriod;    
    long sampleNumber;
   
    // --------------------------------------------------------------    
    
    public Oscillator( SHAPE shape, long sampleRate, long frequency )
    {
        this.shape = shape;
        this.sampleRate = sampleRate;         
        this.samplesPerPeriod = (long)(sampleRate / frequency);
    }
        
    // --------------------------------------------------------------    
    
    public double nextSample()
    {
        double sample;  
        double samplePct = sampleNumber / (double) samplesPerPeriod;
        
        switch (shape)
        {
          default:
          case SIN:
            sample = Math.sin(2.0 * Math.PI * samplePct);
            break;

          case SQU:
            sample = -1.0;              
            if (sampleNumber < (samplesPerPeriod / 2))
            {
              sample = 1.0;
            }
            break;

          case SAW:
            sample = 2.0 * (samplePct - Math.floor(samplePct + 0.5));
            break;

          case TRI:
            sample = Math.abs(Math.round(samplePct) - samplePct);
            break;
              
          case PUL:
            sample = 0.0;              
            if (sampleNumber < (samplesPerPeriod * 0.1))
            {
              sample = 1.0;
            }
            break;              
              
          case WHI:
            sample = new Random().nextDouble();
            break;                         
        }
        
        sampleNumber = (sampleNumber + 1) % samplesPerPeriod;
        return sample;
    }  
}
