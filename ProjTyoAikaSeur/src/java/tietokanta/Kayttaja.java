package tietokanta;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/* @author mhaanran */
public class Kayttaja {
       
    private String kayttajatunnus;    
    private String salasana;
    private String nimi;  
    private boolean rooli;

    public Kayttaja() {  
    }

    public Kayttaja(String kayttajatunnus, String salasana) {
        this.kayttajatunnus = kayttajatunnus;
        this.salasana = salasana;
    }

    public Kayttaja(String kayttajatunnus, String salasana, String nimi, boolean rooli) {
        this.kayttajatunnus = kayttajatunnus;
        this.salasana = salasana;
        this.nimi = nimi;
        this.rooli = rooli;
    }  

    public String getKayttajatunnus() {
        return kayttajatunnus;
    }

    public void setKayttajatunnus(String kayttajatunnus) {
        this.kayttajatunnus = kayttajatunnus;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public boolean isRooli() {
        return rooli;
    }

    public void setRooli(boolean rooli) {
        this.rooli = rooli;
    }
    
    
}
