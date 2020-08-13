import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

@ManagedBean(name = "ilet")
@RequestScoped

public class Iletisim {

    
    private String ad;
    private String soyad;
    private String konu;
    private String mesaj;
    private String eposta;

     @ManagedProperty(value = "#{LoginBean}")
    private LoginBean uye;
     
    public LoginBean getUye() {
        return uye;
    }

    public void setUye(LoginBean uye) {
        this.uye = uye;
    }

   
    
    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getKonu() {
        return konu;
    }

    public void setKonu(String konu) {
        this.konu = konu;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    DataSource dataSource;

    public Iletisim() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/addressbook");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getGonder() throws SQLException {
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
                    "SELECT USERID,AD,SOYAD,EPOSTA,KONU,MESAJ " );
            CachedRowSet resultSet1 = new com.sun.rowset.CachedRowSetImpl();
            resultSet1.populate(object1.executeQuery());
            return resultSet1;
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }

    public String gonder() throws SQLException {
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
                    = connection.prepareStatement("INSERT INTO ILETISIM "
                            + "(USERID,AD,SOYAD,EPOSTA,KONU,MESAJ)"
                            + "VALUES ( ?, ?, ?, ?, ?, ? )");

           
            int userid = Integer.parseInt(uye.getUserid());
            
            object2.setInt(1, userid);
            object2.setString(2, getAd());
            object2.setString(3, getSoyad());
            object2.setString(4, getEposta());
            object2.setString(5, getKonu());
            object2.setString(6, getMesaj());
           

            object2.executeUpdate(); // sql cümlesinin çalışması için bunu yazmak şart
            return "index"; // go back to index.xhtml page
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }
}
