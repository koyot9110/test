/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.Airline;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Bancho
 */
public class AirlineFacade {
    
    private EntityManagerFactory emf;

    public AirlineFacade() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<Airline> getAllAirlines(){
        EntityManager em = getEntityManager();
        List<Airline> airlines = new ArrayList<>();
        try{
            Query query = em.createQuery("SELECT a FROM Airline a");
            airlines = query.getResultList();
        }finally{
            em.close();
        }
        return airlines;
    }
    
    public Airline addAirline(Airline airline){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(airline);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return airline;
    }
    
    public Airline getAirlineByName(String airlineName){
        EntityManager em = getEntityManager();
        Airline airline = null;
        try{
            Query query = em.createQuery("SELECT a FROM Airline a WHERE a.name = :name");
            query.setParameter("name", airlineName);
            airline = (Airline) query.getSingleResult();
        }finally{
            em.close();
        }
        return airline;
    }
}
