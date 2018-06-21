package quid.pro.quo.icf;

public class ProcessorManager {

    String processors =
        "<processors>" +

            "<processor>" +
                "<processor-name>A</processor-name>" +
                "<processor-class>quid.pro.quo.icf.ProcessorA</processor-class>" +
            "</processor>" +

            "<processor>" +
                "<processor-name>B</processor-name>" +
                "<processor-class>quid.pro.quo.icf.ProcessorB</processor-class>" +
            "</processor>" +

        "</processors>";
	
    ProcessorChain chain = new ProcessorChain( null );

    public ProcessorManager()
    {
        try {

	    //XMLReader xmlReader = new XMLReader( processors );
	    //String [] ss = xmlReader.getSubValues( "processors", "processor" );
                        
            chain.addProcessor( new ProcessorA() );
            chain.addProcessor( new ProcessorB() );

        } catch ( Exception e ) {
            throw new RuntimeException( e );
        }		
    }

    public void process()
    {
        chain.processChain();	
    }
	
}
