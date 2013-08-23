package tietokanta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* @author mhaanran */
public class TietokantaYhteys {

    //users postgresql parametrit
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/mhaanran";
    static final String tietokantaKayttaja = "mhaanran";
    static final String tietokantaSalasana = "955ef66881899b2c";
    //java db parametrit
//    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
//    static final String DB_URL = "jdbc:derby://localhost:1527/projtyoaika;create=true";
//    static final String tietokantaKayttaja = "marko";
//    static final String tietokantaSalasana = "marko";
    
    public TietokantaYhteys() {       
    }
    
    public Connection luoTietokantaYhteys() {
        
        try {
            Class.forName(JDBC_DRIVER).newInstance();  
            return DriverManager.getConnection(DB_URL, tietokantaKayttaja, tietokantaSalasana);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }             
    }
    public String haeKayttajanNimi(String kayttajatunnus)  {
        Connection conn=luoTietokantaYhteys();
        PreparedStatement prep = null;
        try {  
            prep = conn.prepareStatement("SELECT * FROM KAYTTAJA WHERE KAYTTAJATUNNUS=?");
            prep.setString(1, kayttajatunnus);
            ResultSet resultset = prep.executeQuery();
            if(resultset.next()) {
                String knimi = resultset.getString("NIMI");
                resultset.close();
                prep.close();
                conn.close();
                return knimi;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    public boolean onkoKayttajaOlemassa(String kayttajatunnus,String salasana){
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
    public boolean mikaRooli(String kayttajatunnus)  {
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
    
    public List<Projekti> getProjektit()  {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep = null;
        ArrayList<Projekti> lista = new ArrayList();
        try {
           prep = conn.prepareStatement("SELECT * FROM PROJEKTI");
           ResultSet resultset = prep.executeQuery();
           while(resultset.next()) {
               String projektinnimi=resultset.getString("PROJEKTIN_NIMI");
               Float budjetoidutTyotunnit=resultset.getFloat("TYOTUNTIBUDJETTI");
               Date alkamisPaivaMaara=resultset.getDate("ALKAMISPAIVAMAARA");
               Date loppumisPaivaMaara=resultset.getDate("LOPPUMISPAIVAMAARA");
               Projekti projekti = new Projekti(projektinnimi,budjetoidutTyotunnit,alkamisPaivaMaara,loppumisPaivaMaara);
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
    public List<Kayttaja> getKayttajat() {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep = null;
        ArrayList<Kayttaja> lista = new ArrayList();
        try {
           prep = conn.prepareStatement("SELECT * FROM KAYTTAJA");
           ResultSet resultset = prep.executeQuery();
           while(resultset.next()) {
               String kayttajatunnus=resultset.getString("KAYTTAJATUNNUS");
               String salasana=resultset.getString("SALASANA");
               String nimi=resultset.getString("NIMI");
               int rooli=resultset.getInt("ROOLI");
               boolean rooliBoolean=false;
               if(rooli>=1) {
                   rooliBoolean=true;
               }
               Kayttaja kayttaja = new Kayttaja(kayttajatunnus,salasana,nimi,rooliBoolean);
               lista.add(kayttaja);
           }       
           resultset.close();
           prep.close();
           conn.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
    public List<Tyotehtava> getTyotehtavat(String projektinNimi)  {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep = null;
        ArrayList<Tyotehtava> lista = new ArrayList();
        try {
           prep = conn.prepareStatement("SELECT * FROM TYOTEHTAVA WHERE PROJEKTIN_NIMI=?");
           prep.setString(1, projektinNimi);
           ResultSet resultset = prep.executeQuery();
           while(resultset.next()) {
               String tyotehtavanNimi=resultset.getString("TYOTEHTAVAN_NIMI");
               Float budjetoidutTyotunnit=resultset.getFloat("BUDJETOIDUT_TYOTUNNIT");
               Tyotehtava tyotehtava = new Tyotehtava(tyotehtavanNimi,budjetoidutTyotunnit,projektinNimi);
               lista.add(tyotehtava);
           }       
           resultset.close();
           prep.close();
           conn.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public void lisaaProjekti(Projekti projekti) {
        Connection conn=luoTietokantaYhteys();
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("INSERT INTO PROJEKTI (PROJEKTIN_NIMI,"
                    + "TYOTUNTIBUDJETTI,ALKAMISPAIVAMAARA,LOPPUMISPAIVAMAARA) "
                    + "VALUES (?,?,?,?)");
            prep.setString(1, projekti.getProjektinNimi());
            prep.setFloat(2, projekti.getBudjetoidutTyotunnit());
            prep.setDate(3, projekti.getAlkamisPaivaMaara());
            prep.setDate(4, projekti.getLoppumisPaivaMaara());
            prep.executeUpdate();
            prep.close();
            conn.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    public boolean onkoProjektiOlemassa(String projektinNimi) {
        Connection conn=luoTietokantaYhteys();
        if(conn==null) {
            return false;
        }
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
            try {
                prep.close();
                conn.close();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(TietokantaYhteys.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;    
    }

    public void poistaProjekti(String projektinNimi) {
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
    
    public void lisaaTyotehtava(Tyotehtava tyotehtava) {
        Connection conn=luoTietokantaYhteys();
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("INSERT INTO TYOTEHTAVA (TYOTEHTAVAN_NIMI,"
                    + "BUDJETOIDUT_TYOTUNNIT,PROJEKTIN_NIMI) "
                    + "VALUES (?,?,?)");
            prep.setString(1, tyotehtava.getTyotehtavanNimi());
            prep.setFloat(2, tyotehtava.getBudjetoidutTyotunnit());
            prep.setString(3, tyotehtava.getProjektinNimi());      
            prep.executeUpdate();
            prep.close();
            conn.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void poistaTyotehtava(String tyotehtavanNimi) {
        Connection conn=luoTietokantaYhteys();
        PreparedStatement prep = null;
        try {
            prep=conn.prepareStatement("DELETE FROM TYOTEHTAVA WHERE TYOTEHTAVAN_NIMI=?");
            prep.setString(1, tyotehtavanNimi);
            prep.executeUpdate();
            prep.close();
            conn.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    public boolean onkoTyotehtavaOlemassa(String tyotehtavanNimi) {
        Connection conn=luoTietokantaYhteys();
        if(conn==null) {
            return false;
        }
        PreparedStatement prep = null;
        if(!tyotehtavanNimi.isEmpty()) {      
            try {
                prep = conn.prepareStatement("SELECT * FROM PROJEKTI WHERE PROJEKTIN_NIMI=?");
                prep.setString(1, tyotehtavanNimi);
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
            try {
                prep.close();
                conn.close();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(TietokantaYhteys.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;    
    }

}
