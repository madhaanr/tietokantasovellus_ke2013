package tietokanta;

import java.sql.Date;
import java.util.Calendar;

/* @author mhaanran */
public class Projekti{

    private String projektinNimi;
    private Date alkamisPaivaMaara;
    private Date loppumisPaivaMaara;
    private float budjetoidutTyotunnit;

    public Projekti() {
    }

    public Projekti(String projektinNimi) {
        this.projektinNimi = projektinNimi;
    }

    public Projekti(String projektinNimi, float budjetoidutTyotunnit, Date alkamisPaivaMaara, Date loppumisPaivaMaara) {
        this.projektinNimi = projektinNimi;
        this.alkamisPaivaMaara = alkamisPaivaMaara;
        this.loppumisPaivaMaara = loppumisPaivaMaara;
        this.budjetoidutTyotunnit = budjetoidutTyotunnit;
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
