package tietokanta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tieokantayhteyden luominen ja tietokanta kyselyt tapahtuvat tämän
 * luokan metodeilla.
 * @author mhaanran
 */
public class TietokantaYhteys {

    //users postgresql parametrit
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/mhaanran";
    static final String tietokantaKayttaja = "mhaanran";
    static final String tietokantaSalasana = "955ef66881899b2c";

    public TietokantaYhteys() {
    }

    
    /**
     * Luo tietokanta yhteyden.
     * @return Palauttaa tiekantayhteyden kysyjälle.
     */
    public Connection luoTietokantaYhteys() {

        try {
            Class.forName(JDBC_DRIVER).newInstance();
            return DriverManager.getConnection(DB_URL, tietokantaKayttaja, tietokantaSalasana);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getKayttajanNimi(String kayttajatunnus) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("SELECT * FROM KAYTTAJA WHERE KAYTTAJATUNNUS=?");
            prep.setString(1, kayttajatunnus);
            ResultSet resultset = prep.executeQuery();
            if (resultset.next()) {
                String knimi = resultset.getString("NIMI");
                suljeYhteydet(resultset, prep, conn);
                return knimi;
            }
            suljeYhteydet(resultset, prep, conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean onkoKayttajaJaSalasanaOikein(String kayttajatunnus, String salasana) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("SELECT * FROM KAYTTAJA WHERE KAYTTAJATUNNUS=? AND SALASANA=?");
            prep.setString(1, kayttajatunnus);
            prep.setString(2, salasana);
            ResultSet resultset = prep.executeQuery();
            if (resultset.next()) {
                suljeYhteydet(resultset, prep, conn);
                return true;
            }
            suljeYhteydet(resultset, prep, conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    /**
     * Tarkistaa onko käyttäjä olemassa tiekannassa.
     * @param kayttajatunnus Etsii käyttäjää primary keyn kayttajatunnus avulla.
     * @return palauttaa true jos käyttäjätunnus löytyy.
     */
    public boolean onkoKayttajaOlemassa(String kayttajatunnus) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("SELECT * FROM KAYTTAJA WHERE KAYTTAJATUNNUS=?");
            prep.setString(1, kayttajatunnus);
            ResultSet resultset = prep.executeQuery();
            if (resultset.next()) {
                suljeYhteydet(resultset, prep, conn);
                return true;
            }
            suljeYhteydet(resultset, prep, conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Tarkistetaan onko käyttäjällä rooli projektipäällikkö vai työntekijä.
     * Vain projektipäällikkö voi lisätä projekteja ja työtehtäviä.
     * @param kayttajatunnus Etsii käyttäjätunnuksella käyttäjän ja sitten 
     * tarkistaa roolin.
     * @return palautetaan true jos rooli on projektipäällikkö muuten false.
     */
    public boolean mikaRooli(String kayttajatunnus) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("SELECT * FROM KAYTTAJA WHERE KAYTTAJATUNNUS=?");
            prep.setString(1, kayttajatunnus);
            ResultSet resultset = prep.executeQuery();

            if (resultset.next()) {
                int rooli = resultset.getInt("ROOLI");
                if (rooli >= 1) {
                    return true;
                }
                suljeYhteydet(resultset, prep, conn);
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

    public Projekti getProjekti(String projektinNimi) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        Projekti projekti=null;
        try {
            prep = conn.prepareStatement("SELECT * FROM PROJEKTI WHERE PROJEKTIN_NIMI=?");
            prep.setString(1, projektinNimi);
            ResultSet resultset = prep.executeQuery();
            while (resultset.next()) {
                String projektinnimi = resultset.getString("PROJEKTIN_NIMI");
                Float budjetoidutTyotunnit = resultset.getFloat("TYOTUNTIBUDJETTI");
                Date alkamisPaivaMaara = resultset.getDate("ALKAMISPAIVAMAARA");
                Date loppumisPaivaMaara = resultset.getDate("LOPPUMISPAIVAMAARA");
                projekti = new Projekti(projektinnimi, budjetoidutTyotunnit, alkamisPaivaMaara, loppumisPaivaMaara);
            }
            suljeYhteydet(resultset, prep, conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return projekti;
    }

    public List<Projekti> getProjektit() {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        ArrayList<Projekti> lista = new ArrayList();
        try {
            prep = conn.prepareStatement("SELECT * FROM PROJEKTI ORDER BY PROJEKTIN_NIMI");
            ResultSet resultset = prep.executeQuery();
            while (resultset.next()) {
                String projektinnimi = resultset.getString("PROJEKTIN_NIMI");
                Float budjetoidutTyotunnit = resultset.getFloat("TYOTUNTIBUDJETTI");
                Date alkamisPaivaMaara = resultset.getDate("ALKAMISPAIVAMAARA");
                Date loppumisPaivaMaara = resultset.getDate("LOPPUMISPAIVAMAARA");
                Projekti projekti = new Projekti(projektinnimi, budjetoidutTyotunnit, alkamisPaivaMaara, loppumisPaivaMaara);
                lista.add(projekti);
            }
            suljeYhteydet(resultset, prep, conn);
        } catch (SQLException ex) {
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
            while (resultset.next()) {
                String kayttajatunnus = resultset.getString("KAYTTAJATUNNUS");
                String salasana = resultset.getString("SALASANA");
                String nimi = resultset.getString("NIMI");
                int rooli = resultset.getInt("ROOLI");
                boolean rooliBoolean = false;
                if (rooli >= 1) {
                    rooliBoolean = true;
                }
                Kayttaja kayttaja = new Kayttaja(kayttajatunnus, salasana, nimi, rooliBoolean);
                lista.add(kayttaja);
            }
            suljeYhteydet(resultset, prep, conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<Tyotehtava> getTyotehtavat(String projektinNimi) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep = null;
        ArrayList<Tyotehtava> lista = new ArrayList();
        try {
            prep = conn.prepareStatement("SELECT * FROM TYOTEHTAVA WHERE PROJEKTIN_NIMI=?");
            prep.setString(1, projektinNimi);
            ResultSet resultset = prep.executeQuery();
            while (resultset.next()) {
                String tyotehtavanNimi = resultset.getString("TYOTEHTAVAN_NIMI");
                Float budjetoidutTyotunnit = resultset.getFloat("BUDJETOIDUT_TYOTUNNIT");
                Tyotehtava tyotehtava = new Tyotehtava(tyotehtavanNimi, budjetoidutTyotunnit, projektinNimi);
                lista.add(tyotehtava);
            }
            suljeYhteydet(resultset, prep, conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Kirjaus> getKirjaukset(String kayttajatunnus, String projektinNimi) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep = null;
        ArrayList<Kirjaus> lista = new ArrayList();
        try {
            prep = conn.prepareStatement("SELECT * FROM KIRJAUS WHERE KAYTTAJATUNNUS=? AND PROJEKTIN_NIMI=? ORDER BY PAIVAMAARA,TYOTEHTAVAN_NIMI");
            prep.setString(1, kayttajatunnus);
            prep.setString(2, projektinNimi);
            ResultSet resultset = prep.executeQuery();
            while (resultset.next()) {
                String tyotehtavanNimi = resultset.getString("TYOTEHTAVAN_NIMI");
                String selitys = resultset.getString("SELITYS");
                float tehdytTunnit = resultset.getFloat("TEHDYT_TYOTUNNIT");
                Date paivamaara = resultset.getDate("PAIVAMAARA");
                System.out.println(tyotehtavanNimi);
                Kirjaus kirjaus = new Kirjaus(paivamaara, tehdytTunnit, selitys, kayttajatunnus, projektinNimi, tyotehtavanNimi);
                lista.add(kirjaus);
            }
            suljeYhteydet(resultset, prep, conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Kirjaus> kausiRaportti(Date alkamisPaivamaara, Date loppumisPaivamaara) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        ArrayList<Kirjaus> lista = new ArrayList();
        try {
            prep = conn.prepareStatement("SELECT * FROM KIRJAUS WHERE paivamaara between ? and ? ORDER BY PROJEKTIN_NIMI,PAIVAMAARA,TYOTEHTAVAN_NIMI");
            prep.setDate(1, alkamisPaivamaara);
            prep.setDate(2, loppumisPaivamaara);
            ResultSet resultset = prep.executeQuery();
            while (resultset.next()) {
                String tyotehtavanNimi = resultset.getString("TYOTEHTAVAN_NIMI");
                String selitys = resultset.getString("SELITYS");
                float tehdytTunnit = resultset.getFloat("TEHDYT_TYOTUNNIT");
                Date paivamaara = resultset.getDate("PAIVAMAARA");
                String kayttajatunnus = resultset.getString("KAYTTAJATUNNUS");
                String projektinNimi = resultset.getString("PROJEKTIN_NIMI");
                Kirjaus kirjaus = new Kirjaus(paivamaara, tehdytTunnit, selitys, kayttajatunnus, projektinNimi, tyotehtavanNimi);
                lista.add(kirjaus);
            }
            suljeYhteydet(resultset, prep, conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Kirjaus> kausiRaportti(String kayttajatunnus, Date alkamisPaivamaara, Date loppumisPaivamaara) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        ArrayList<Kirjaus> lista = new ArrayList();
        try {
            prep = conn.prepareStatement("SELECT * FROM KIRJAUS WHERE KAYTTAJATUNNUS=? and paivamaara between ? and ? ORDER BY PROJEKTIN_NIMI,PAIVAMAARA,TYOTEHTAVAN_NIMI");
            prep.setString(1, kayttajatunnus);
            prep.setDate(2, alkamisPaivamaara);
            prep.setDate(3, loppumisPaivamaara);
            ResultSet resultset = prep.executeQuery();
            while (resultset.next()) {
                String tyotehtavanNimi = resultset.getString("TYOTEHTAVAN_NIMI");
                String selitys = resultset.getString("SELITYS");
                float tehdytTunnit = resultset.getFloat("TEHDYT_TYOTUNNIT");
                Date paivamaara = resultset.getDate("PAIVAMAARA");
                String projektinNimi = resultset.getString("PROJEKTIN_NIMI");
                Kirjaus kirjaus = new Kirjaus(paivamaara, tehdytTunnit, selitys, kayttajatunnus, projektinNimi, tyotehtavanNimi);
                lista.add(kirjaus);
            }
            suljeYhteydet(resultset, prep, conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Kirjaus> tyontekijaRaportti() {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        ArrayList<Kirjaus> lista = new ArrayList();

        return lista;
    }

    public void lisaaProjekti(Projekti projekti) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void muokkaaProjektia(Projekti projekti) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("UPDATE PROJEKTI SET TYOTUNTIBUDJETTI=?, ALKAMISPAIVAMAARA=?, LOPPUMISPAIVAMAARA=? WHERE PROJEKTIN_NIMI=?");
            prep.setFloat(1, projekti.getBudjetoidutTyotunnit());
            prep.setDate(2, projekti.getAlkamisPaivaMaara());
            prep.setDate(3, projekti.getLoppumisPaivaMaara());
            prep.setString(4, projekti.getProjektinNimi());
            prep.executeUpdate();
            prep.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean onkoProjektiOlemassa(String projektinNimi) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        if (!projektinNimi.isEmpty()) {
            try {
                prep = conn.prepareStatement("SELECT * FROM PROJEKTI WHERE PROJEKTIN_NIMI=?");
                prep.setString(1, projektinNimi);
                ResultSet resultset = prep.executeQuery();
                if (resultset.next()) {
                    suljeYhteydet(resultset, prep, conn);
                    return true;
                } else {
                    suljeYhteydet(resultset, prep, conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public void poistaProjekti(String projektinNimi) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("DELETE FROM PROJEKTI WHERE PROJEKTIN_NIMI=?");
            prep.setString(1, projektinNimi);
            prep.executeUpdate();
            prep.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void lisaaTyotehtava(Tyotehtava tyotehtava) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void poistaTyotehtava(String tyotehtavanNimi, String projektinNimi) {
        Connection conn = luoTietokantaYhteys();
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("DELETE FROM TYOTEHTAVA WHERE TYOTEHTAVAN_NIMI=? AND PROJEKTIN_NIMI=?");
            prep.setString(1, tyotehtavanNimi);
            prep.setString(2, projektinNimi);
            prep.executeUpdate();
            prep.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean onkoTyotehtavaOlemassa(String tyotehtavanNimi, String projektinNimi) {
        Connection conn = luoTietokantaYhteys();
        if (tyotehtavanNimi != null) {
            try {
                PreparedStatement prep = conn.prepareStatement("SELECT * FROM TYOTEHTAVA WHERE TYOTEHTAVAN_NIMI=? AND PROJEKTIN_NIMI=?");
                prep.setString(1, tyotehtavanNimi);
                prep.setString(2, projektinNimi);
                ResultSet resultset = prep.executeQuery();
                if (resultset.next()) {
                    suljeYhteydet(resultset, prep, conn);
                    return true;
                }
                suljeYhteydet(resultset, prep, conn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public void lisaaTyontekijaProjektiin(String projektinNimi, String kayttajatunnus) {
        Connection conn = luoTietokantaYhteys();
        try {
            if (onkoProjektiOlemassa(projektinNimi) && onkoKayttajaOlemassa(kayttajatunnus)) {
                PreparedStatement prep = conn.prepareStatement("INSERT INTO KAYTTAJAN_PROJEKTIT VALUES (?,?)");
                prep.setString(1, projektinNimi);
                prep.setString(2, kayttajatunnus);
                prep.executeUpdate();
                prep.close();
            }
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void poistaTyontekijaProjektista(String projektinNimi, String kayttajatunnus) {
        Connection conn = luoTietokantaYhteys();
        try {
            if (onkoProjektiOlemassa(projektinNimi) && onkoKayttajaOlemassa(kayttajatunnus)) {
                PreparedStatement prep = conn.prepareStatement("DELETE FROM KAYTTAJAN_PROJEKTIT WHERE PROJEKTIN_NIMI=? AND KAYTTAJATUNNUS=?");
                prep.setString(1, projektinNimi);
                prep.setString(2, kayttajatunnus);
                prep.executeUpdate();
                prep.close();
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void lisaaTyotuntiKirjaus(Kirjaus kirjaus) {
        Connection conn = luoTietokantaYhteys();
        try {
            PreparedStatement prep = conn.prepareStatement("INSERT INTO KIRJAUS VALUES (?,?,?,?,?,?)");
            prep.setDate(1, kirjaus.getPaivamaara());
            prep.setFloat(2, kirjaus.getTehdytTunnit());
            prep.setString(3, kirjaus.getSelitys());
            prep.setString(4, kirjaus.getKayttajatunnus());
            prep.setString(5, kirjaus.getProjektinNimi());
            prep.setString(6, kirjaus.getTyotehtavanNimi());
            prep.executeUpdate();
            prep.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void poistaTyotuntiKirjaus(Kirjaus kirjaus) {
        Connection conn = luoTietokantaYhteys();
        try {
            PreparedStatement prep = conn.prepareStatement("DELETE FROM KIRJAUS WHERE PAIVAMAARA=? "
                    + "AND KAYTTAJATUNNUS=? AND PROJEKTIN_NIMI=? AND TYOTEHTAVAN_NIMI=?");
            prep.setDate(1, kirjaus.getPaivamaara());
            prep.setString(2, kirjaus.getKayttajatunnus());
            prep.setString(3, kirjaus.getProjektinNimi());
            prep.setString(4, kirjaus.getTyotehtavanNimi());
            prep.executeUpdate();
            prep.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<String> getProjektinTyontekijat(String projektinNimi) {
        Connection conn = luoTietokantaYhteys();
        ArrayList<String> lista = new ArrayList();
        try {
            if (onkoProjektiOlemassa(projektinNimi)) {
                PreparedStatement prep = conn.prepareStatement("SELECT * FROM KAYTTAJAN_PROJEKTIT WHERE PROJEKTIN_NIMI=?");
                prep.setString(1, projektinNimi);
                ResultSet resultset = prep.executeQuery();
                while (resultset.next()) {
                    String kayttajatunnus = resultset.getString("KAYTTAJATUNNUS");
                    lista.add(kayttajatunnus);
                }
                suljeYhteydet(resultset, prep, conn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<String> getProjektinTyotehtavat(String projektinNimi) {
        Connection conn = luoTietokantaYhteys();
        ArrayList<String> lista = new ArrayList();
        try {
            if (onkoProjektiOlemassa(projektinNimi)) {
                PreparedStatement prep = conn.prepareStatement("SELECT * FROM TYOTEHTAVA WHERE PROJEKTIN_NIMI=?");
                prep.setString(1, projektinNimi);
                ResultSet resultset = prep.executeQuery();
                while (resultset.next()) {
                    String kayttajatunnus = resultset.getString("TYOTEHTAVAN_NIMI");
                    lista.add(kayttajatunnus);
                }
                suljeYhteydet(resultset, prep, conn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<String> getKayttajanProjektit(String kayttajatunnus) {
        Connection conn = luoTietokantaYhteys();
        ArrayList<String> lista = new ArrayList();
        try {
            if (onkoKayttajaOlemassa(kayttajatunnus)) {
                PreparedStatement prep = conn.prepareStatement("SELECT * FROM KAYTTAJAN_PROJEKTIT WHERE KAYTTAJATUNNUS=?");
                prep.setString(1, kayttajatunnus);
                ResultSet resultset = prep.executeQuery();
                while (resultset.next()) {
                    String projektinNimi = resultset.getString("PROJEKTIN_NIMI");
                    lista.add(projektinNimi);
                }
                suljeYhteydet(resultset, prep, conn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    private void suljeYhteydet(ResultSet resultset, PreparedStatement prep, Connection conn) throws SQLException {
        resultset.close();
        prep.close();
        conn.close();
    }
}
