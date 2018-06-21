package quid.pro.quo.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Basic DOM functions for reading, querying and transforming.
 */
public class DomParser {

    public Document doc;    
    
    // -----------------------------------------------------------------------
    //  Public methods
    
    public static void main( String[] args ) throws Exception
    {   
        String what = "books";
        String xml = what + ".xml";
        String xslt = what + ".xslt";
        String target = what + ".html";

        DomParser dp = new DomParser( xml );
        //dp.listValues( what );
        //dp.xpath( "//book[@id='2']" );
        dp.xslt( xslt, target );
    }    
    // ----------------------------------------------------------    
    
    public DomParser( String xml )
    {
        try {
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //dbf.setNamespaceAware( true );
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            if ( new File( xml ).exists() )
            {
                File f = new File( xml );
                doc = db.parse( new InputSource( new StringReader( xml ) ) );
            } else 
            {
                ClassLoader cl = DomParser.class.getClassLoader();
                doc = db.parse( cl.getResourceAsStream( xml ) );
            }
            
        } catch ( Exception e ) {
            throw new RuntimeException( e );
        }	
    }

    // ----------------------------------------------------------
    
    public void xslt( String xslt, String target ) throws Exception
    {
        DOMSource domSource = new DOMSource( doc );
        TransformerFactory tf = TransformerFactory.newInstance();
        
        ClassLoader cl = DomParser.class.getClassLoader();
        InputStream is = cl.getResourceAsStream( xslt );
        Transformer t = tf.newTransformer( new StreamSource( is ) );
        
        File books = new File( target );
        StreamResult sr = new StreamResult( books );
        t.transform( domSource, sr );
        
        try ( BufferedReader br = new BufferedReader( new FileReader( books ) ) )
        {
            String line = null;
            while ( (line = br.readLine() ) != null )
            {
                System.out.println( line );
            }            
        }                
    }
    
    // ----------------------------------------------------------
    
    public void xpath( String xPath ) throws Exception
    {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile( xPath );
        
        NodeList nl = (NodeList)expr.evaluate( doc, XPathConstants.NODESET );
        listValues( nl );
    }    

    // ----------------------------------------------------------
    
    public void listValues( String parent ) throws Exception
    {
        Element element = doc.getDocumentElement();
        NodeList nl = getNodeList( element, parent );
        listValues( nl );
    }
     
    // ----------------------------------------------------------
    
    public void listValues( NodeList nl ) throws Exception
    {
        for( int a = 0; a < nl.getLength(); ++a ) {
            Node node = getNode( nl, a );
            if ( isElementNode( node ) ) {
                NodeList children = getChildNodes( node );

                for( int b = 0; b < children.getLength(); ++b ) {
                    Node n2 = getNode( children, b );
                    if ( isElementNode( n2 ) )
                    {
                        System.out.print( getNodeName( n2 ) + " = " );
                        NodeList children2 = getChildNodes( n2 );
                        for( int c = 0; c < children2.getLength(); ++c )
                        {
                            Node n3 = getNode( children2, c );
                            if ( isTextNode(n3) )
                            {
                                System.out.println( getNodeValue(n3) );
                            }
                        } // for c
                    }
                } // for b
            }
        } // for a
    }    

    // ----------------------------------------------------------

    public NodeList getNodeList( Element element, String type ) throws Exception {
        return element.getNodeName().equals( type ) ? element.getChildNodes() : null;
    }

    // ----------------------------------------------------------

    public boolean isAttributeAtIndex( NamedNodeMap nnm, int index ) throws Exception {
        return isAttributeNode( ( Node )nnm.item( index ) );
    }

    // ----------------------------------------------------------

    public boolean isElementAtIndex( NodeList nl, int index ) throws Exception {
        return nl != null && isElementNode( ( Node )nl.item( index ) );
    }

    // ----------------------------------------------------------

    public boolean isTextNode( Node n ) throws Exception {
        return n != null && n.getNodeType() == Node.TEXT_NODE;
    }

    // ----------------------------------------------------------

    public boolean isAttributeNode( Node n ) throws Exception {
        return n != null && n.getNodeType() == Node.ATTRIBUTE_NODE;
    }

    // ----------------------------------------------------------

    public boolean isElementNode( Node n ) throws Exception {
        return n != null && n.getNodeType() == Node.ELEMENT_NODE;
    }

    // ----------------------------------------------------------

    public boolean isValidNodeList( NodeList nl ) throws Exception {
        return nl != null && nl.getLength() > 0;
    }

    // ----------------------------------------------------------

    public boolean isValidNodeList( NamedNodeMap nnm ) throws Exception {
        return nnm != null && nnm.getLength() > 0;
    }

    // ----------------------------------------------------------

    public String getAttribute( NamedNodeMap attrs, String name ) throws Exception {
        Node n;
        for( int a = 0; a < attrs.getLength(); ++a ) {
            n = ( Node )attrs.item( a );
            if ( isAttributeNode( n ) && n.getNodeName().equals( name ) ) {
                    return getNodeValue( n );
            }
        }
        return null;
    }

    // ----------------------------------------------------------

    public NamedNodeMap getAttributes( Node n ) throws Exception {
        return n.getAttributes();
    }

    // ----------------------------------------------------------

    public Node getNode( NodeList nl, String name ) throws Exception {
        for( int a = 0; a < nl.getLength(); ++a ) {
            Node n = getNode( nl, a );
            if ( isElementNode( n ) && n.getNodeName().equals( name ) )
                return n;
        }
        return null;
    }

    // ----------------------------------------------------------

    public NodeList getChildNodes( Node n ) throws Exception {
        return n.getChildNodes();
    }

    // ----------------------------------------------------------

    public Node getChildNode( Node n, String name ) throws Exception {	
        return getNode( getChildNodes( n ), name );
    }

    // ----------------------------------------------------------

    public Node getNode( NodeList nl, int index ) throws Exception {		
        return (Node)nl.item( index );
    }

    // ----------------------------------------------------------

    public String getNodeName( Node n ) throws Exception {
        return n.getNodeName();
    }

    // ----------------------------------------------------------

    public String getNodeValue( Node n ) throws Exception {
        return n.getNodeValue();
    }

    // ----------------------------------------------------------

    public String getNodeItemValue( NamedNodeMap nnm, int index ) throws Exception {
        return getNodeValue( ( Node )nnm.item( index ) );
    }

    // ----------------------------------------------------------

    public void isValid( Object o, String name ) throws Exception {
        if ( o == null )
            throw new RuntimeException( "'" + name + "' not defined." );
    }
	
}