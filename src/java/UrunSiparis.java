
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import javax.sql.rowset.CachedRowSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean(name = "siparis")
@SessionScoped
public class UrunSiparis {

    @ManagedProperty(value = "#{LoginBean}")
    private LoginBean uye;

    public LoginBean getUye() {
        return uye;
    }

    public void setUye(LoginBean uye) {
        this.uye = uye;
    }

    private String adet;

    public String getAdet() {
        return adet;
    }

    public void setAdet(String adet) {
        if(!adet.equals(""))
        this.adet = adet;
    }

    DataSource dataSource;

    public UrunSiparis() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/addressbook");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String satınAl1() throws SQLException {

        if (uye.isLoggedIn()) {

            // check whether dataSource was injected by the server
            if (dataSource == null) {
                throw new SQLException("Unable to obtain DataSource");
            }

            // obtain a connection from the connection pool
            Connection connection = dataSource.getConnection();

            // check whether connection was successful
            if (connection == null) {
                throw new SQLException("Unable to connect to DataSource");
            }

            try {

                // create a PreparedStatement to insert a new address book entry
                PreparedStatement object2
                        = connection.prepareStatement("INSERT INTO URUNSIPARIS "
                                + "VALUES ( default,?,'Inoa Saç Boyası','₺95,00', ?)");

                // specify the PreparedStatement's arguments
                
                int id = Integer.parseInt(uye.getUserid());
                object2.setInt(1, id);
                object2.setString(2, getAdet());

                object2.executeUpdate(); // sql cümlesinin çalışması için bunu yazmak şart
                return "index"; // go back to index.xhtml page
            } // end try
            finally {
                connection.close(); // return this connection to pool
            } // end finally

        } else {

            //üye girişe gönder
            return "index"; // go back to index.xhtml page

        }
    }

    public String satınAl2() throws SQLException {

        if (uye.isLoggedIn()) {

            // check whether dataSource was injected by the server
            if (dataSource == null) {
                throw new SQLException("Unable to obtain DataSource");
            }

            // obtain a connection from the connection pool
            Connection connection = dataSource.getConnection();

            // check whether connection was successful
            if (connection == null) {
                throw new SQLException("Unable to connect to DataSource");
            }

            try {

                // create a PreparedStatement to insert a new address book entry
                PreparedStatement object2
                        = connection.prepareStatement("INSERT INTO URUNSIPARIS "
                                + "VALUES ( default,?,'Luocolor Besleyici Jel Saç Boyası','₺60,00', ?)");

                // specify the PreparedStatement's arguments
                
                int id = Integer.parseInt(uye.getUserid());
                object2.setInt(1, id);
                object2.setString(2, getAdet());

                object2.executeUpdate(); // sql cümlesinin çalışması için bunu yazmak şart
                return "index"; // go back to index.xhtml page
            } // end try
            finally {
                connection.close(); // return this connection to pool
            } // end finally

        } else {

            //üye girişe gönder
            return "index"; // go back to index.xhtml page

        }
    }

    public String satınAl3() throws SQLException {

        if (uye.isLoggedIn()) {

            // check whether dataSource was injected by the server
            if (dataSource == null) {
                throw new SQLException("Unable to obtain DataSource");
            }

            // obtain a connection from the connection pool
            Connection connection = dataSource.getConnection();

            // check whether connection was successful
            if (connection == null) {
                throw new SQLException("Unable to connect to DataSource");
            }

            try {

                // create a PreparedStatement to insert a new address book entry
                PreparedStatement object2
                        = connection.prepareStatement("INSERT INTO URUNSIPARIS "
                                + "VALUES ( default,?,'Wahl 8081 Saç Kesme Mak.','₺495,00', ?)");

                // specify the PreparedStatement's arguments
                
                int id = Integer.parseInt(uye.getUserid());
                object2.setInt(1, id);
                object2.setString(2, getAdet());

                object2.executeUpdate(); // sql cümlesinin çalışması için bunu yazmak şart
                return "index"; // go back to index.xhtml page
            } // end try
            finally {
                connection.close(); // return this connection to pool
            } // end finally

        } else {

            //üye girişe gönder
            return "index"; // go back to index.xhtml page

        }
    }

    public String satınAl4() throws SQLException {

        if (uye.isLoggedIn()) {

            // check whether dataSource was injected by the server
            if (dataSource == null) {
                throw new SQLException("Unable to obtain DataSource");
            }

            // obtain a connection from the connection pool
            Connection connection = dataSource.getConnection();

            // check whether connection was successful
            if (connection == null) {
                throw new SQLException("Unable to connect to DataSource");
            }

            try {

                // create a PreparedStatement to insert a new address book entry
                PreparedStatement object2
                        = connection.prepareStatement("INSERT INTO URUNSIPARIS "
                                + "VALUES ( default,?,'Osis Bouncy Curls Yağ Bazlı Bukle Jeli 200 ML','₺50,00', ?)");

                // specify the PreparedStatement's arguments
                
                int id = Integer.parseInt(uye.getUserid());
                object2.setInt(1, id);
                object2.setString(2, getAdet());

                object2.executeUpdate(); // sql cümlesinin çalışması için bunu yazmak şart
                return "index"; // go back to index.xhtml page
            } // end try
            finally {
                connection.close(); // return this connection to pool
            } // end finally

        } else {

            //üye girişe gönder
            return "index"; // go back to index.xhtml page

        }
    }

    public ResultSet getSatınAl1() throws SQLException {
        // check whether dataSource was injected by the server
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        // obtain a connection from the connection pool
        Connection connection = dataSource.getConnection();

        // check whether connection was successful
        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }

        try {
            // sql cümlesi yazmak için PreparedStatement oluşturmalıyız.    
            // create a PreparedStatement to select the records
            PreparedStatement object1 = connection.prepareStatement(
                    "SELECT URUNADI,FIYATI,ADET  ");
            CachedRowSet resultSet1 = new com.sun.rowset.CachedRowSetImpl();
            resultSet1.populate(object1.executeQuery());
            return resultSet1;
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }

    public ResultSet getSatınAl2() throws SQLException {
        // check whether dataSource was injected by the server
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        // obtain a connection from the connection pool
        Connection connection = dataSource.getConnection();

        // check whether connection was successful
        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }

        try {
            // sql cümlesi yazmak için PreparedStatement oluşturmalıyız.    
            // create a PreparedStatement to select the records
            PreparedStatement object1 = connection.prepareStatement(
                    "SELECT URUNADI,FIYATI,ADET  ");
            CachedRowSet resultSet1 = new com.sun.rowset.CachedRowSetImpl();
            resultSet1.populate(object1.executeQuery());
            return resultSet1;
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }

    public ResultSet getSatınAl3() throws SQLException {
        // check whether dataSource was injected by the server
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        // obtain a connection from the connection pool
        Connection connection = dataSource.getConnection();

        // check whether connection was successful
        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }

        try {
            // sql cümlesi yazmak için PreparedStatement oluşturmalıyız.    
            // create a PreparedStatement to select the records
            PreparedStatement object1 = connection.prepareStatement(
                    "SELECT URUNADI,FIYATI,ADET  ");
            CachedRowSet resultSet1 = new com.sun.rowset.CachedRowSetImpl();
            resultSet1.populate(object1.executeQuery());
            return resultSet1;
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }

    public ResultSet getSatınAl4() throws SQLException {
        // check whether dataSource was injected by the server
        if (dataSource == null) {
            throw new SQLException("Unable to obtain DataSource");
        }

        // obtain a connection from the connection pool
        Connection connection = dataSource.getConnection();

        // check whether connection was successful
        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }

        try {
            // sql cümlesi yazmak için PreparedStatement oluşturmalıyız.    
            // create a PreparedStatement to select the records
            PreparedStatement object1 = connection.prepareStatement(
                    "SELECT URUNADI,FIYATI,ADET  ");
            CachedRowSet resultSet1 = new com.sun.rowset.CachedRowSetImpl();
            resultSet1.populate(object1.executeQuery());
            return resultSet1;
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }

}
