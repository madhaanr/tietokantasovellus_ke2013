package tietokanta;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/* @author mhaanran */
@Entity
public class Tyotehtava implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @Column
    private String tyotehtavanNimi;
    
    @Column
    private float tehdytTunnit;
    
    @Column
    private float budjetoidutTunnit;
    
    @Column
    private String selitys;

    public Tyotehtava() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTyotehtavanNimi() {
        return tyotehtavanNimi;
    }

    public void setTyotehtavanNimi(String tyotehtavanNimi) {
        this.tyotehtavanNimi = tyotehtavanNimi;
    }

    public float getTehdytTunnit() {
        return tehdytTunnit;
    }

    public void setTehdytTunnit(float tehdytTunnit) {
        this.tehdytTunnit = tehdytTunnit;
    }

    public float getBudjetoidutTunnit() {
        return budjetoidutTunnit;
    }

    public void setBudjetoidutTunnit(float budjetoidutTunnit) {
        this.budjetoidutTunnit = budjetoidutTunnit;
    }

    public String getSelitys() {
        return selitys;
    }

    public void setSelitys(String selitys) {
        this.selitys = selitys;
    }
    
    
}
