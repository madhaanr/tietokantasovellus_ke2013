package database;

import java.sql.*;

/* @author mhaanran */
public class TietokantaYhteys {

    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/projtyoaika;create=true";
    static final String kayttaja = "marko";
    static final String salasana = "marko";
    

    public TietokantaYhteys() {
        
    }
    public Connection luoTietokantaYhteys() throws SQLException {
        
        try {
            Class.forName(JDBC_DRIVER).newInstance();              
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return DriverManager.getConnection(DB_URL, kayttaja, salasana);
    }
    public boolean onkoKayttajaOlemassa(String kayttajatunnus,String salasana) throws SQLException {
        Connection conn=luoTietokantaYhteys();
        PreparedStatement prep = null;
        try {           
            prep = conn.prepareStatement("SELECT * FROM KAYTTAJA WHERE KAYTTAJATUNNUS=? AND SALASANA=?");
            prep.setString(1, kayttajatunnus);
            prep.setString(2, salasana);
            ResultSet resultset = prep.executeQuery();
            if(resultset.next()) {
                conn.close();
                return true;
            }   
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
     /**
     * Tarkistetaan onko käyttäjällä rooli projektipäällikkö vai työnte-
     * kijä. Vain projektipäällikkö voi lisätä projekteja ja työtehtäviä.
     * @param kayttajatunnus 
     * @return palautetaan true jos rooli on projektipäällikkö muuten false.
     */
    public boolean mikaRooli(String kayttajatunnus) throws SQLException {
        Connection conn=luoTietokantaYhteys();
        PreparedStatement prep = null;
        try {           
            prep = conn.prepareStatement("SELECT * FROM KAYTTAJA WHERE KAYTTAJATUNNUS=?");
            prep.setString(1, kayttajatunnus);
            ResultSet resultset = prep.executeQuery();
            if(resultset.next()) {
                int rooli = resultset.getInt("ROOLI");
                if(rooli>=1) {
                    conn.close();
                    return true;
                }
            }   
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
