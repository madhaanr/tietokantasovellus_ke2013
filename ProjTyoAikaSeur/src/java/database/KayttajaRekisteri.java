package database;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/* @author mhaanran */
public class KayttajaRekisteri {

    private EntityManagerFactory emf = null;

    public KayttajaRekisteri() {
        emf=Persistence.createEntityManagerFactory("ProjTyoAikaSeurPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Kayttaja> getKayttajat() {
        EntityManager em = getEntityManager();
        return em.createQuery("SELECT u FROM Kayttajat u").getResultList();
    }

    public void lisaaKayttaja(Kayttaja kayttaja) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(kayttaja);
        em.getTransaction().commit();
    }
       
}
