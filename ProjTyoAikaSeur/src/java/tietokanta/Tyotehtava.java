package tietokanta;

/* @author mhaanran */

public class Tyotehtava{
  
    private String tyotehtavanNimi;
    private float budjetoidutTyotunnit;
    private String projektinNimi;
    
    public Tyotehtava() {
    }

    public Tyotehtava(String tyotehtavanNimi, float budjetoidutTyotunnit, String projektinNimi) {
        this.tyotehtavanNimi = tyotehtavanNimi;
        this.budjetoidutTyotunnit = budjetoidutTyotunnit;
        this.projektinNimi = projektinNimi;
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
