
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 7765876811740798583L;

    // Simple user database :)
    private String username;
    private String password;
    private boolean loggedIn;
    private boolean IsAdmin;
    private String userid;
    private String ad;
    private String soyad;
    private String telefon;
    private String eposta;

    DataSource dataSource;

    ResultSet rs = null;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public boolean isIsAdmin() {
        return IsAdmin;
    }

    public String getUserid() {
        return userid;
    }

    /**
     * Login operation.
     *
     * @return
     */
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

    public String doLogin() throws SQLException {
        // Get every user from our sample database :)
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

            String UserSql = "Select * from UYELER where EPOSTA=? AND SIFRE=?";
            PreparedStatement UserStatement = connection.prepareStatement(UserSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            UserStatement.setString(1, getUsername());
            UserStatement.setString(2, Hash(getPassword()));

            rs = UserStatement.executeQuery();
            if (!rs.isBeforeFirst()) {
                return "/faces/girisYap.xhtml";

            } else {

                while (rs.next()) {
                    this.userid = rs.getString("ID");

                }
                loggedIn = true;
                return "/faces/index.xhtml?faces-redirect=true";
            }
        } // end try
        catch (Exception e) {

            e.printStackTrace();
        } finally {
            connection.close(); // return this connection to pool
        }

        // Set login ERROR
        FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        // To to login page
        return "girisYap.xhtml";

    }

    public String adminPanel() throws SQLException {

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

            String AdminSql = "Select * from UYELER where EPOSTA=? AND SIFRE=?";
            PreparedStatement AdminStatement = connection.prepareStatement(AdminSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            AdminStatement.setString(1, getUsername());
            AdminStatement.setString(2, Hash(getPassword()));

            rs = AdminStatement.executeQuery();
            if (!rs.isBeforeFirst()) {
                return "/faces/girisYap.xhtml";

            } else {

                while (rs.next()) {
                    this.userid = rs.getString("ID");

                }
                loggedIn = true;
                IsAdmin = true;
                return "/faces/adminPanel.xhtml?faces-redirect=true";
            }
        } // end try
        catch (Exception e) {

            e.printStackTrace();
        } finally {
            connection.close(); // return this connection to pool
        }

        // Set login ERROR
        FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        // To to login page
        return "girisYap.xhtml";

    }

    /**
     * Logout operation.
     *
     * @return
     */
    public void doLogout() throws IOException {
        // Set the paremeter indicating that user is logged in to false
        loggedIn = false;

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        FacesMessage msg = new FacesMessage("Logout success!", "INFO MSG");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");

        // Set logout message
        // return navigationBean.toIndex();
    }

    // ------------------------------
    // Getters & Setters 
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

        try {

            // create a PreparedStatement to insert a new address book entry
            PreparedStatement object
                    = connection.prepareStatement("UPDATE UYELER WHERE SIFRE=?");
            object.setString(1, getPassword());

            // specify the PreparedStatement's arguments
           
            object.executeUpdate();// sql cümlesinin çalışması için bunu yazmak şart

            return "index"; // go back to index.xhtml page
        } // end try
        finally {
            connection.close(); // return this connection to pool
        }
    }
}
