
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

import javax.sql.rowset.CachedRowSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean(name = "IsBasvuru")
@RequestScoped
public class IsBasvuru {

    private int id;
    private String ad;
    private String soyad;
    private String telefon;
    private String eposta;
    private String dogum_tarihi;
    private String pozisyon;
    private String cinsiyet;
    private String adres;
    private String egitim_durumu;
    private String is_deneyimi;

    @ManagedProperty(value = "#{LoginBean}")
    private LoginBean uye;

    public LoginBean getUye() {
        return uye;
    }

    public void setUye(LoginBean uye) {
        this.uye = uye;
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

    public String getDogum_tarihi() {
        return dogum_tarihi;
    }

    public void setDogum_tarihi(String dogum_tarihi) {
        this.dogum_tarihi = dogum_tarihi;
    }

    public String getPozisyon() {
        return pozisyon;
    }

    public void setPozisyon(String pozisyon) {
        this.pozisyon = pozisyon;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getEgitim_durumu() {
        return egitim_durumu;
    }

    public void setEgitim_durumu(String egitim_durumu) {
        this.egitim_durumu = egitim_durumu;
    }

    public String getIs_deneyimi() {
        return is_deneyimi;
    }

    public void setIs_deneyimi(String is_deneyimi) {
        this.is_deneyimi = is_deneyimi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    DataSource dataSource;

    public IsBasvuru() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/addressbook");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getBasvur() throws SQLException {
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
                    "SELECT USERID,AD,SOYAD,TELEFON,EPOSTA,DOGUM_TARIHI,POZISYON,CINSIYET,ADRES,EGITIM_DURUMU,IS_DENEYIMI");
            CachedRowSet resultSet1 = new com.sun.rowset.CachedRowSetImpl();
            resultSet1.populate(object1.executeQuery());
            return resultSet1;
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }

    public String basvur() throws SQLException {
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
                    = connection.prepareStatement("INSERT INTO ISBASVURUSU "
                            + "(USERID,AD,SOYAD,TELEFON,EPOSTA,DOGUM_TARIHI,POZISYON,CINSIYET,ADRES,EGITIM_DURUMU,IS_DENEYIMI)"
                            + "VALUES ( ?,?, ?, ?,?, ?, ?, ?, ?, ?, ?)");

            // specify the PreparedStatement's arguments
            int userid = Integer.parseInt(uye.getUserid());
            object2.setInt(1, userid);
            object2.setString(2, getAd());
            object2.setString(3, getSoyad());
            object2.setString(4, getTelefon());
            object2.setString(5, getEposta());
            object2.setString(6, getDogum_tarihi());
            object2.setString(7, getPozisyon());
            object2.setString(8, getCinsiyet());
            object2.setString(9, getAdres());
            object2.setString(10, getEgitim_durumu());
            object2.setString(11, getIs_deneyimi());

            object2.executeUpdate(); // sql cümlesinin çalışması için bunu yazmak şart
            return "index"; // go back to index.xhtml page
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }

    public List<IsBasvuru> Listele() throws SQLException {
        List<IsBasvuru> Liste = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/addressbook");
        } catch (NamingException e) {
            e.printStackTrace();
        }

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
            ResultSet rS = null;
            String sql = "Select * from ISBASVURUSU";
            PreparedStatement ps = connection.prepareStatement(sql);
            rS = ps.executeQuery();

            while (rS.next()) {
                IsBasvuru object1 = new IsBasvuru();

                object1.setId(rS.getInt("ID"));
                object1.setAd(rS.getString("AD"));
                object1.setSoyad(rS.getString("SOYAD"));
                object1.setTelefon(rS.getString("TELEFON"));
                object1.setEposta(rS.getString("EPOSTA"));
                object1.setDogum_tarihi(rS.getString("DOGUM_TARIHI"));
                object1.setPozisyon(rS.getString("POZISYON"));
                object1.setCinsiyet(rS.getString("CINSIYET"));
                object1.setAdres(rS.getString("ADRES"));
                object1.setEgitim_durumu(rS.getString("EGITIM_DURUMU"));
                object1.setIs_deneyimi(rS.getString("IS_DENEYIMI"));

                Liste.add(object1);
            }

        } // end try // end try
        catch (Exception e) {
            Hata ht = new Hata();
            ht.setHata(e.getMessage());
            e.printStackTrace();
        } finally {
            connection.close(); // return this connection to pool
        }
        return Liste;

    }

    public String onayla(IsBasvuru IB) throws SQLException {
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
                    = connection.prepareStatement("INSERT INTO ONAYBASVURU "
                            + "(AD,SOYAD,TELEFON,EPOSTA,DOGUM_TARIHI,POZISYON,CINSIYET,ADRES,EGITIM_DURUMU,IS_DENEYIMI)"
                            + "VALUES (?, ?, ?,?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement object3
                    = connection.prepareStatement("DELETE FROM ISBASVURUSU WHERE ID=?");
            object3.setInt(1, IB.getId());
            // specify the PreparedStatement's arguments

            object2.setString(1, IB.getAd());
            object2.setString(2, IB.getSoyad());
            object2.setString(3, IB.getTelefon());
            object2.setString(4, IB.getEposta());
            object2.setString(5, IB.getDogum_tarihi());
            object2.setString(6, IB.getPozisyon());
            object2.setString(7, IB.getCinsiyet());
            object2.setString(8, IB.getAdres());
            object2.setString(9, IB.getEgitim_durumu());
            object2.setString(10, IB.getIs_deneyimi());

            object2.executeUpdate();
            object3.executeUpdate();// sql cümlesinin çalışması için bunu yazmak şart
            return "basvurular"; // go back to index.xhtml page
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }

    public List<IsBasvuru> OnayListele() throws SQLException {
        List<IsBasvuru> Liste = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/addressbook");
        } catch (NamingException e) {
            e.printStackTrace();
        }

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
            ResultSet rS = null;
            String sql = "Select * from ONAYBASVURU";
            PreparedStatement ps = connection.prepareStatement(sql);
            rS = ps.executeQuery();

            while (rS.next()) {
                IsBasvuru object1 = new IsBasvuru();
                // object1.userid(rS.getString("USERID"));
                object1.setAd(rS.getString("AD"));
                object1.setSoyad(rS.getString("SOYAD"));
                object1.setTelefon(rS.getString("TELEFON"));
                object1.setEposta(rS.getString("EPOSTA"));
                object1.setDogum_tarihi(rS.getString("DOGUM_TARIHI"));
                object1.setPozisyon(rS.getString("POZISYON"));
                object1.setCinsiyet(rS.getString("CINSIYET"));
                object1.setAdres(rS.getString("ADRES"));
                object1.setEgitim_durumu(rS.getString("EGITIM_DURUMU"));
                object1.setIs_deneyimi(rS.getString("IS_DENEYIMI"));

                Liste.add(object1);
            }

        } // end try // end try
        catch (Exception e) {
            Hata ht = new Hata();
            ht.setHata(e.getMessage());
            e.printStackTrace();
        } finally {
            connection.close(); // return this connection to pool
        }
        return Liste;

    }

    public String reddet(IsBasvuru IB) throws SQLException {
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
                    = connection.prepareStatement("INSERT INTO REDBASVURU "
                            + "(AD,SOYAD,TELEFON,EPOSTA,DOGUM_TARIHI,POZISYON,CINSIYET,ADRES,EGITIM_DURUMU,IS_DENEYIMI)"
                            + "VALUES (?, ?, ?,?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement object3
                    = connection.prepareStatement("DELETE FROM ISBASVURUSU WHERE ID=?");
            object3.setInt(1, IB.getId());
            // specify the PreparedStatement's arguments

            object2.setString(1, getAd());
            object2.setString(2, getSoyad());
            object2.setString(3, getTelefon());
            object2.setString(4, getEposta());
            object2.setString(5, getDogum_tarihi());
            object2.setString(6, getPozisyon());
            object2.setString(7, getCinsiyet());
            object2.setString(8, getAdres());
            object2.setString(9, getEgitim_durumu());
            object2.setString(10, getIs_deneyimi());

            object2.executeUpdate();
            object3.executeUpdate();// sql cümlesinin çalışması için bunu yazmak şart
            return "basvurular"; // go back to index.xhtml page
        } // end try
        finally {
            connection.close(); // return this connection to pool
        } // end finally
    }

    public List<IsBasvuru> RedListele() throws SQLException {
        List<IsBasvuru> Liste = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/addressbook");
        } catch (NamingException e) {
            e.printStackTrace();
        }

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
            ResultSet rS = null;
            String sql = "Select * from REDBASVURU";
            PreparedStatement ps = connection.prepareStatement(sql);
            rS = ps.executeQuery();

            while (rS.next()) {
                IsBasvuru object1 = new IsBasvuru();
                // object1.userid(rS.getString("USERID"));
                object1.setAd(rS.getString("AD"));
                object1.setSoyad(rS.getString("SOYAD"));
                object1.setTelefon(rS.getString("TELEFON"));
                object1.setEposta(rS.getString("EPOSTA"));
                object1.setDogum_tarihi(rS.getString("DOGUM_TARIHI"));
                object1.setPozisyon(rS.getString("POZISYON"));
                object1.setCinsiyet(rS.getString("CINSIYET"));
                object1.setAdres(rS.getString("ADRES"));
                object1.setEgitim_durumu(rS.getString("EGITIM_DURUMU"));
                object1.setIs_deneyimi(rS.getString("IS_DENEYIMI"));

                Liste.add(object1);
            }

        } // end try // end try
        catch (Exception e) {
            Hata ht = new Hata();
            ht.setHata(e.getMessage());
            e.printStackTrace();
        } finally {
            connection.close(); // return this connection to pool
        }
        return Liste;

    }
}
