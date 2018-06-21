package quid.pro.quo.sql;

import java.util.Properties;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * This is a factory for creating a <code>DataSource</code>
 * for different relational db vendors.
 * Add to classpath the needed drivers and uncomment the respective code.
 * 
 * http://mvnrepository.com/artifact/postgresql/postgresql
 * http://mvnrepository.com/artifact/ojdbc/ojdbc
 * http://mvnrepository.com/artifact/org.hsqldb/hsqldb
 * http://mvnrepository.com/artifact/net.sf.ucanaccess/ucanaccess
 */
public class DataSourceFactory
{
    static Properties props = new Properties();
    
    static String DATASOURCE_JNDI = "datasource.jndi";

    static String ORACLE_DRV = "oracle.driver-class-name";
    static String ORACLE_URL = "oracle.url";
    static String ORACLE_USR = "oracle.username";
    static String ORACLE_PWD = "oracle.password";

    static String MYSQL_DRV = "mysql.driver-class-name";
    static String MYSQL_URL = "mysql.url";
    static String MYSQL_USR = "mysql.username";
    static String MYSQL_PWD = "mysql.password";    
    
    static String PGRE_DRV = "pgre.driver-class-name";
    static String PGRE_SRV = "pgre.server-name";
    static String PGRE_DS = "pgre.datasource-name";
    static String PGRE_DB = "pgre.database-name";
    static String PGRE_USR = "pgre.username";
    static String PGRE_PWD = "pgre.username";
    
    static 
    {
        //load props form file
        //FileInputStream fis = new FileInputStream("db.properties");
        //props.load(fis);
        
        //or just harcode
        props.put( DATASOURCE_JNDI , "java:comp/env/jdbc/dataSource" );
        
        props.put( ORACLE_DRV , "oracle.jdbc.OracleDriver" );
        props.put( ORACLE_URL , "jdbc:oracle:thin:@someurl:1521:somepid" );
        props.put( ORACLE_USR, "usr" );
        props.put( ORACLE_PWD, "pwd" );    
        
        props.put( MYSQL_DRV, "com.mysql.jdbc.Driver" );  
        props.put( MYSQL_URL, "jdbc:mysql://localhost:3306/UserDB" );
        props.put( MYSQL_USR, "usr" );
        props.put( MYSQL_PWD, "pwd" );         
        
        props.put( PGRE_DRV, "org.postgresql.Driver" );
        props.put( PGRE_SRV, "someurl:1234" );
        props.put( PGRE_DS, "datasource" );  
        props.put( PGRE_DB, "somedb" );
        props.put( PGRE_USR, "usr" );
        props.put( PGRE_PWD, "pwd" );
              
    }
    
    // -----------------------------------------------------------------------
    //  Public methods 
    
    public static DataSource getDataSource()
    {
        DataSource dataSource;
            
        try {
        
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup( 
                    props.getProperty( DATASOURCE_JNDI ) );
            
        } catch ( Exception e ) {
            throw new RuntimeException(e);
        }
        
        return dataSource;
    }
        
    // -----------------------------------------------------------------------  
        
    public static DataSource getOracleDataSource()
    {   
        /* 
        OracleDataSource oracleDS;
        
        //https://community.oracle.com/thread/617281
        try {
            oracleDS = new OracleDataSource();
            oracleDS.setURL( props.getProperty( ORACLE_URL ) );
            oracleDS.setUser( props.getProperty( ORACLE_USR ) );
            oracleDS.setPassword( props.getProperty( ORACLE_PWD ) );
        } catch ( Exception e ) {
            throw new RuntimeException(e);
        }    

        return oracleDS;
        */ 
        return null;        
        
    }    
    
    // -----------------------------------------------------------------------  
    
    public static DataSource getMySQLDataSource()
    {  
        try {
            Class.forName( props.getProperty( MYSQL_DRV ) );
        } catch ( Exception e ) { throw new RuntimeException(e); }
        
        /* 
        MysqlDataSource mysqlDS = null;
        try {
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL( props.getProperty( MYSQL_URL ) );
            mysqlDS.setUser( props.getProperty( MYSQL_USR ) );
            mysqlDS.setPassword( props.getProperty( MYSQL_PWD ) );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mysqlDS;
        */ 
        return null;
    }
    
    // -----------------------------------------------------------------------  
        
    public static DataSource getPGDataSource()
    {
        try {
            Class.forName( props.getProperty( PGRE_DRV ) );
        } catch ( Exception e ) { throw new RuntimeException(e); }
        
        /*
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName( props.getProperty( PGRE_DS ) );
        source.setServerName( props.getProperty( PGRE_SRV ) );
        source.setDatabaseName( props.getProperty( PGRE_DB ) );
        source.setUser( props.getProperty( PGRE_USR ) );
        source.setPassword( props.getProperty( PGRE_PWD ) );
        source.setMaxConnections(5);
        return source;
        */
        
        return null;
    }       
    
}
