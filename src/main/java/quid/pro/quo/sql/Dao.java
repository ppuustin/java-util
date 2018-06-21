package quid.pro.quo.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 * A simple example dao to read form a relational db table.
 */
public class Dao
{
    protected DataSource dataSource = null;  

    // -----------------------------------------------------------------------
    //  Public methods     
            
    public static void main( String[] args ) throws Exception
    {
        Dao dao = new Dao( DataSourceFactory.getPGDataSource() );         
        dao.readTable( "someValue" );
    }
    
    // -----------------------------------------------------------------------  
    
    public Dao ( DataSource dataSource )
    {
        this.dataSource = dataSource;
    }    
    
    // -----------------------------------------------------------------------      
        
    public void readTable( String val ) throws Exception
    {        
        String SQL = "SELECT col1, col2 FROM my_schema.my_table WHERE col3 = ?";
        
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            
            //connection.setAutoCommit(false);
            statement = connection.prepareStatement( SQL, 
                    ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
            statement.setString(1, val);
            
            resultSet = statement.executeQuery();
            
            while ( resultSet.next() )
            {   
                System.out.println( "--------------------" );
                System.out.println( "col1 = " + resultSet.getString(1) );
                System.out.println( "col2 = " + resultSet.getString(2) );                
            }
            
            //commit( connection );
            
        } catch ( Exception e ) {
            //rollback( connection );
            throw new RuntimeException( SQL, e );
        } finally {
            close( connection, statement, resultSet );
        }
        
    }    
    
    // -----------------------------------------------------------------------
    //  Protected methods

    protected void rollback( Connection connection )
    {
        try {
            if ( connection != null )
                connection.rollback();
        } catch ( Exception e ) {}
    } 
    
    // ---------------------------------------------------------------

    protected void commit( Connection connection )
    {
        try {
            if ( connection != null )
                connection.commit();
        } catch ( Exception e ) {}
    }    
    
    // ---------------------------------------------------------------

    protected void close( Connection connection, Statement statement, ResultSet resultSet )
    {
        close( statement, resultSet );

        try {
            if ( connection != null )
                connection.close();
        } catch ( Exception e ) {}
        
    }    
    
    // ---------------------------------------------------------------

    protected void close( Statement statement, ResultSet resultSet )
    {
        try {
            if ( resultSet != null )
                resultSet.close();
        } catch ( Exception e ) {}

        try {
            if ( statement != null )
                statement.close();
        } catch ( Exception e ) {}
    }    
    
    
}
