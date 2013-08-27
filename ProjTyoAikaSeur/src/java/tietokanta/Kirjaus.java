package tietokanta;

import java.sql.Date;

/* @author mhaanran */
public class Kirjaus {

    private String selitys;
    private float tehdytTunnit;
    private Date paivamaara;
    private String kayttajatunnus;
    private String projektinNimi;
    private String tyotehtavanNimi;

    public Kirjaus() {
    }

    public Kirjaus(Date paivamaara, float tehdytTunnit, String selitys, String kayttajatunnus, String projektinNimi, String tyotehtavanNimi) {
        
        this.selitys = selitys;
        this.tehdytTunnit = tehdytTunnit;
        this.paivamaara = paivamaara;
        this.kayttajatunnus = kayttajatunnus;
        this.projektinNimi = projektinNimi;
        this.tyotehtavanNimi = tyotehtavanNimi;
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

    public Date getPaivamaara() {
        return paivamaara;
    }

    public void setPaivamaara(Date paivamaara) {
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
