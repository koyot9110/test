/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.TicketRequest;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Bancho
 */
public class TicketRequestFacade {
    
    private EntityManagerFactory emf;

    public TicketRequestFacade() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    
    public TicketRequest addTicketRequest(TicketRequest ticketRequest){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(ticketRequest);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return ticketRequest;
    }
}
