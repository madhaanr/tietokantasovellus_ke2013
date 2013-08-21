package tietokanta;

/* @author mhaanran */

public class Tyotehtava{
  
    private String tyotehtavanNimi;
    private float budjetoidutTyotunnit;
    private String projektinNimi;
    
    public Tyotehtava() {
    }
    
    public String getTyotehtavanNimi() {
        return tyotehtavanNimi;
    }

    public void setTyotehtavanNimi(String tyotehtavanNimi) {
        this.tyotehtavanNimi = tyotehtavanNimi;
    }
    
    public float getBudjetoidutTyotunnit() {
        return budjetoidutTyotunnit;
    }

    public void setBudjetoidutTyotunnit(float budjetoidutTyotunnit) {
        this.budjetoidutTyotunnit = budjetoidutTyotunnit;
    }

    public String getProjektinNimi() {
        return projektinNimi;
    }

    public void setProjektinNimi(String projektinNimi) {
        this.projektinNimi = projektinNimi;
    }
    
   
}
