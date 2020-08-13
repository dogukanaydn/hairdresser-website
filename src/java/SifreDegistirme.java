
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

@ManagedBean(name = "SifreDegistirme")
@RequestScoped
public class SifreDegistirme {

    private String sifre1;
    private String sifre2;

    @ManagedProperty(value = "#{LoginBean}")
    private LoginBean uye;

    public LoginBean getUye() {
        return uye;
    }

    public void setUye(LoginBean uye) {
        this.uye = uye;
    }
    
    public String getSifre1() {
        return sifre1;
    }

    public void setSifre1(String sifre1) {
        this.sifre1 = sifre1;
    }

    public String getSifre2() {
        return sifre2;
    }

    public void setSifre2(String sifre2) {
        this.sifre2 = sifre2;
    }

    DataSource dataSource;
    
     public SifreDegistirme() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/addressbook");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }


    public String Hash(String Password) {

        String passwordToHash = Password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;

    }
    
    public String sifreDegistirme() throws SQLException {
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
        if (!sifre1.equals(getSifre2())) {
            try {

                    // create a PreparedStatement to insert a new address book entry
                    PreparedStatement object
                            = connection.prepareStatement("UPDATE UYELER SET SIFRE=? WHERE ID=?");
                    object.setString(1, Hash(getSifre2()));
                    int id=Integer.parseInt(uye.getUserid());
                    object.setInt(2, id);


                    // specify the PreparedStatement's arguments
                    object.executeUpdate();// sql cümlesinin çalışması için bunu yazmak şart

                    return "index"; // go back to index.xhtml page
                }
                catch (Exception e) {

            e.printStackTrace();
        }
                // end try
                finally {
            connection.close(); // return this connection to pool
        
                        
                        }
            
            }
            return "sifreDegistirme";
        }
}
