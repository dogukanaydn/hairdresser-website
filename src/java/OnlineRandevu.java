import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import javax.sql.rowset.CachedRowSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean( name="online" )
@SessionScoped
public class OnlineRandevu {
    private String ad;
    private String soyad;
    private String telefon;
    private String eposta;
    private String userid;
    private String hizmet;
    private String tarih;
    private String saat;
    private String isekli;
    private String sube;
    
    DataSource dataSource;

    @ManagedProperty(value = "#{LoginBean}")
    private LoginBean uye;

    public LoginBean getUye() {
        return uye;
    }

    public void setUye(LoginBean uye) {
        this.uye = uye;
    }
    public String getSube() {
        return sube;
    }

    public void setSube(String sube) {
        this.sube = sube;
    }

    public String getIsekli() {
        return isekli;
    }

    public void setIsekli(String isekli) {
        this.isekli = isekli;
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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getHizmet() {
        return hizmet;
    }

    public void setHizmet(String hizmet) {
        this.hizmet = hizmet;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }
    
    public OnlineRandevu() {
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
                    "SELECT USERID,AD, SOYAD, TELEFON, EPOSTA, ILETISIM SEKLI,SUBE,TARIH,SAAT,HIZMET  ");
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
                    = connection.prepareStatement("INSERT INTO ONLINERANDEVU "
                            + "(USERID,AD,SOYAD,TELEFON,EPOSTA,ILETISIM_SEKLI,SUBE,TARIH,SAAT,HIZMET)"
                            + "VALUES ( ?,?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // specify the PreparedStatement's arguments
             int userid = Integer.parseInt(uye.getUserid());
            object2.setInt(1, userid);
            object2.setString(2, getAd());
            object2.setString(3, getSoyad());
            object2.setString(4, getTelefon());
            object2.setString(5, getEposta());
            object2.setString(6, getIsekli());
            object2.setString(7, getSube());
            object2.setString(8, getTarih());
            object2.setString(9, getSaat());
            object2.setString(10, getHizmet());
    

            object2.executeUpdate(); // sql cümlesinin çalışması için bunu yazmak şart
            return "index"; // go back to index.xhtml page
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }
    

}
