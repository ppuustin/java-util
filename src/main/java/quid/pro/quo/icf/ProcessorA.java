package quid.pro.quo.icf;

public class ProcessorA implements Processor {

    public void process()
    {
        System.out.println( this.getClass().getName() );
    }
	
}
