package quid.pro.quo.icf;

public class ProcessorB implements Processor {

    public void process()
    {
        System.out.println( this.getClass().getName() );
    }
	
}
