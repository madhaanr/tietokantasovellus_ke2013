package tietokanta;

/* @author mhaanran */
public class Kayttajan_Projektit {

    private String projektin_nimi;
    private String kayttajatunnus;

    public Kayttajan_Projektit() {
    }
    
    public Kayttajan_Projektit(String projektin_nimi, String kayttajatunnus) {
        this.projektin_nimi = projektin_nimi;
        this.kayttajatunnus = kayttajatunnus;
    }

    public String getProjektin_nimi() {
        return projektin_nimi;
    }

    public void setProjektin_nimi(String projektin_nimi) {
        this.projektin_nimi = projektin_nimi;
    }

    public String getKayttajatunnus() {
        return kayttajatunnus;
    }

    public void setKayttajatunnus(String kayttajatunnus) {
        this.kayttajatunnus = kayttajatunnus;
    }
    
    
    
}
