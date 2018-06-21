package quid.pro.quo.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Basic SAX parser example.
 */
public class SaxParser
{   
    // -----------------------------------------------------------------------
    //  Public methods     
    
    public static void main( String argv[] ) throws Exception
    {
        String xml = "books.xml";
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        
        ClassLoader cl = SaxParser.class.getClassLoader();
        saxParser.parse( cl.getResourceAsStream( xml ), handler );
    }
    
    // ----------------------------------------------------------       
    
    static DefaultHandler handler = new DefaultHandler() 
    {
	boolean books = false;
	boolean book = false;
	boolean name = false;
	boolean author = false;

	public void startElement(String uri, String localName, 
                String qName, Attributes attributes) throws SAXException
        {
            System.out.println( "startElement = " + qName );
            
            if ( qName.equalsIgnoreCase( "books" ) ) 
            {
                books = true;
            }

            if ( qName.equalsIgnoreCase( "book" ) ) 
            {
                book = true;
            }

            if ( qName.equalsIgnoreCase( "name" ) ) 
            {
                name = true;
            }            

            if ( qName.equalsIgnoreCase( "author" ) ) 
            {
                author = true;
            }                       
	}

	public void endElement(String uri, String localName,
		String qName) throws SAXException
        {
            System.out.println( "endElement = " + qName );
	}

	public void characters(char ch[], int start, int length) throws SAXException
        {
            if (books)
            {
                System.out.println( "books = " + new String(ch, start, length) );
                books = false;
            }

            if (book)
            {
                System.out.println( "book = " + new String(ch, start, length) );
                book = false;
            }

            if (name)
            {
                System.out.println( "name  = " + new String(ch, start, length) );
                name = false;
            }

            if (author)
            {
                System.out.println( "author = " + new String(ch, start, length) );
                author = false;
            }
	}
        
     }; // DefaultHandler
    
}
