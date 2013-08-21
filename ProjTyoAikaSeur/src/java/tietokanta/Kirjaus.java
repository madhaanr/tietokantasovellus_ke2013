package tietokanta;

import java.util.Calendar;

/* @author mhaanran */
public class Kirjaus {

    private long id;
    private String selitys;
    private float tehdytTunnit;
    private Calendar paivamaara;
    private String kayttajatunnus;
    private String projektinNimi;
    private String tyotehtavanNimi;

    public Kirjaus() {
    }

    public Kirjaus(long id, String selitys, float tehdytTunnit, Calendar paivamaara, String kayttajatunnus, String projektinNimi, String tyotehtavanNimi) {
        this.id = id;
        this.selitys = selitys;
        this.tehdytTunnit = tehdytTunnit;
        this.paivamaara = paivamaara;
        this.kayttajatunnus = kayttajatunnus;
        this.projektinNimi = projektinNimi;
        this.tyotehtavanNimi = tyotehtavanNimi;
    }
   
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSelitys() {
        return selitys;
    }

    public void setSelitys(String selitys) {
        this.selitys = selitys;
    }

    public float getTehdytTunnit() {
        return tehdytTunnit;
    }

    public void setTehdytTunnit(float tehdytTunnit) {
        this.tehdytTunnit = tehdytTunnit;
    }

    public Calendar getPaivamaara() {
        return paivamaara;
    }

    public void setPaivamaara(Calendar paivamaara) {
        this.paivamaara = paivamaara;
    }

    public String getKayttajatunnus() {
        return kayttajatunnus;
    }

    public void setKayttajatunnus(String kayttajatunnus) {
        this.kayttajatunnus = kayttajatunnus;
    }

    public String getProjektinNimi() {
        return projektinNimi;
    }

    public void setProjektinNimi(String projektinNimi) {
        this.projektinNimi = projektinNimi;
    }

    public String getTyotehtavanNimi() {
        return tyotehtavanNimi;
    }

    public void setTyotehtavanNimi(String tyotehtavanNimi) {
        this.tyotehtavanNimi = tyotehtavanNimi;
    }
}
