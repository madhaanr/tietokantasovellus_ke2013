package database;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/* @author mhaanran */
public class Rekisteri {

    private EntityManagerFactory emf = null;

    public Rekisteri() {
        emf=Persistence.createEntityManagerFactory("ProjTyoAikaSeurPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean onkoKayttajaOlemassa(String kayttajatunnus,String salasana) {
        EntityManager em = getEntityManager(); 
        Kayttaja kayttaja = em.find(Kayttaja.class,kayttajatunnus);
        if(kayttaja!=null&&kayttaja.getSalasana().equals(salasana)) {          
            return true;
        }
        return false;
    }
    
    public List<Kayttaja> getKayttajat() {
        EntityManager em = getEntityManager();
        return em.createQuery("SELECT u FROM Kayttaja u").getResultList();
    }

    public void lisaaKayttaja(Kayttaja kayttaja) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(kayttaja);
        em.getTransaction().commit();
    }
    
    public List<Projekti> getProjektit() {
        EntityManager em = getEntityManager();
        return em.createQuery("SELECT u FROM Projekti u").getResultList();
    }
    public void lisaaProjekti(Projekti projekti) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(projekti);
        em.getTransaction().commit();
    }
       
}
