package database;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;

/* @author mhaanran */
@Entity
public class Projekti implements Serializable{

    @Id
    private String projektinNimi;
    
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date alkamisPaivaMaara;
    
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date loppumisPaivaMaara;
    
    @Column
    private float budjetoidutTyotunnit;

    public Projekti() {
    }
    
    public String getProjektinNimi() {
        return projektinNimi;
    }

    public void setProjektinNimi(String projektinNimi) {
        this.projektinNimi = projektinNimi;
    }

    public Date getAlkamisPaivaMaara() {
        return alkamisPaivaMaara;
    }

    public void setAlkamisPaivaMaara(Date alkamisPaivaMaara) {
        this.alkamisPaivaMaara = alkamisPaivaMaara;
    }

    public Date getLoppumisPaivaMaara() {
        return loppumisPaivaMaara;
    }

    public void setLoppumisPaivaMaara(Date loppumisPaivaMaara) {
        this.loppumisPaivaMaara = loppumisPaivaMaara;
    }

    public float getBudjetoidutTyotunnit() {
        return budjetoidutTyotunnit;
    }

    public void setBudjetoidutTyotunnit(float budjetoidutTyotunnit) {
        this.budjetoidutTyotunnit = budjetoidutTyotunnit;
    }
}