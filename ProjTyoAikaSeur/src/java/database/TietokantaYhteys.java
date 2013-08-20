package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/* @author mhaanran */
public class TietokantaYhteys {

    //users postgresql parametrit
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/mhaanran";
    static final String kayttaja = "mhaanran";
    static final String salasana = "955ef66881899b2c";
    //java db parametrit
//    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
//    static final String DB_URL = "jdbc:derby://localhost:1527/projtyoaika;create=true";
//    static final String kayttaja = "marko";
//    static final String salasana = "marko";
    
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
                resultset.close();
                prep.close();
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
                    return true;
                }
                resultset.close();
                prep.close();
                conn.close();
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
    
    public List<Projekti> getProjektit() throws SQLException {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep = null;
        ArrayList<Projekti> lista = new ArrayList();
        try {
           prep = conn.prepareStatement("SELECT * FROM PROJEKTI");
           ResultSet resultset = prep.executeQuery();
           while(resultset.next()) {
               String projektinNimi=resultset.getString("PROJEKTIN_NIMI");
               Projekti projekti = new Projekti(projektinNimi);
               lista.add(projekti);
           }       
           resultset.close();
           prep.close();
           conn.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public void lisaaProjekti(Projekti projekti) throws SQLException {
        Connection conn=luoTietokantaYhteys();
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("INSERT INTO PROJEKTI (PROJEKTIN_NIMI,TYOTUNTIBUDJETTI,ALKAMISPAIVAMAARA,LOPPUMISPAIVAMAARA) "
                    + "VALUES (?,?,?,?)");
            prep.setString(1, projekti.getProjektinNimi());
            prep.setInt(2, 0);
            prep.setDate(3, null);
            prep.setDate(4, null);
            prep.executeUpdate();
            prep.close();
            conn.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    public boolean onkoProjektiOlemassa(String projektinNimi) throws SQLException {
        Connection conn=luoTietokantaYhteys();
        PreparedStatement prep = null;
        if(!projektinNimi.isEmpty()) {      
            try {
                prep = conn.prepareStatement("SELECT * FROM PROJEKTI WHERE PROJEKTIN_NIMI=?");
                prep.setString(1, projektinNimi);
                ResultSet resultset = prep.executeQuery();            
                if(resultset.next()) {
                    resultset.close();
                    prep.close();
                    conn.close();
                    return false;
                }
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
        if(prep!=null) {
            prep.close();
        }
        conn.close();
        return true;
    }

    public void poistaProjekti(String projektinNimi) throws SQLException {
        Connection conn=luoTietokantaYhteys();
        PreparedStatement prep = null;
        try {
            prep=conn.prepareStatement("DELETE FROM PROJEKTI WHERE PROJEKTIN_NIMI=?");
            prep.setString(1, projektinNimi);
            prep.executeUpdate();
            prep.close();
            conn.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

}
