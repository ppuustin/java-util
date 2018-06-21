package quid.pro.quo.icf;

import java.util.Vector;

public class ProcessorChain {

    private Vector<Processor> myProcessors;

    public ProcessorChain( Vector<Processor> pcs )
    {
        myProcessors = pcs != null ? pcs : new Vector<Processor>();
    }

    public void processChain( /* req, response */ )
    {	
        for ( Processor processor : myProcessors )
        {
            processor.process();
        }
    }

    public void addProcessor( Processor processor )
    {
        myProcessors.add( processor );
    }
	
}
