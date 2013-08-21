package tietokanta;

import java.util.Calendar;

/* @author mhaanran */
public class Projekti{

    private String projektinNimi;
    private Calendar alkamisPaivaMaara;
    private Calendar loppumisPaivaMaara;
    private float budjetoidutTyotunnit;

    public Projekti() {
    }

    public Projekti(String projektinNimi) {
        this.projektinNimi = projektinNimi;
    }

    public Projekti(String projektinNimi, float budjetoidutTyotunnit, Calendar alkamisPaivaMaara, Calendar loppumisPaivaMaara) {
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

    public Calendar getAlkamisPaivaMaara() {
        return alkamisPaivaMaara;
    }

    public void setAlkamisPaivaMaara(Calendar alkamisPaivaMaara) {
        this.alkamisPaivaMaara = alkamisPaivaMaara;
    }

    public Calendar getLoppumisPaivaMaara() {
        return loppumisPaivaMaara;
    }

    public void setLoppumisPaivaMaara(Calendar loppumisPaivaMaara) {
        this.loppumisPaivaMaara = loppumisPaivaMaara;
    }

    public float getBudjetoidutTyotunnit() {
        return budjetoidutTyotunnit;
    }

    public void setBudjetoidutTyotunnit(float budjetoidutTyotunnit) {
        this.budjetoidutTyotunnit = budjetoidutTyotunnit;
    }
}
