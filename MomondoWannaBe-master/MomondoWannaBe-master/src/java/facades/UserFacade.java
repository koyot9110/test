package facades;

import deploy.DeploymentConfiguration;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import security.PasswordHash;
/**
 *
 * @author Mato
 */
public class UserFacade {

    private EntityManagerFactory emf;

    public UserFacade() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /*
     Return the Roles if users could be authenticated, otherwise null
     */
    public List<String> authenticateUser(String userName, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = getUserByUserName(userName);
        if (user == null) {
            return null;
        }
        if (!PasswordHash.validatePassword(password, user.getPasswordHash())) {
            return null;
        }
        return user.getRoles();
    }

    public User addUser(User user){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return user;
    }
    
    public List<User> getAllUsers(){
        EntityManager em = getEntityManager();
        List<User> users = new ArrayList<>();
        try{
            Query query = em.createQuery("SELECT u FROM User u");
            users = query.getResultList();
        }finally{
            em.close();
        }
        return users;
    }
    
    public User getUserByUserName(String userName){
        EntityManager em = getEntityManager();
        User user = null;
        try{
            Query query = em.createQuery("SELECT u FROM User u WHERE u.userName = :userName");
            query.setParameter("userName", userName);
            user = (User) query.getSingleResult();
        }catch(NoResultException ex){
            //return null
        }finally{
            em.close();
        }
        return user;
    }
    
    public User getUserByID(int id){
        EntityManager em = getEntityManager();
        User user = null;
        try{
            Query query = em.createQuery("SELECT u FROM User u WHERE u.id = :id");
            query.setParameter("id", id);
            user = (User) query.getSingleResult();
        }catch(NoResultException ex){
            //return null
        }finally{
            em.close();
        }
        return user;
    }
    
    public User editUser(User user){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return user;
    }
    
    public User deleteUserByID(String id){
        EntityManager em = getEntityManager();
        User user = null;
        try{
            Query query = em.createQuery("SELECT u FROM User u WHERE u.id = :id");
            query.setParameter("id", Integer.parseInt(id));
            user = (User) query.getSingleResult();
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return user;
    }
}
