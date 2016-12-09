package deploy;

import entity.Airline;
import entity.User;
import facades.AirlineFacade;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import security.PasswordHash;

@WebListener
public class DeploymentConfiguration implements ServletContextListener {

    public static String PU_NAME = "3rdSemesterProjectPU"; //USE the RIGHT name here

    public void contextInitialized(ServletContextEvent sce) {
        Map<String, String> env = System.getenv();     //If we are running in the OPENSHIFT environment change the pu-name     
        if (env.keySet().contains("OPENSHIFT_MYSQL_DB_HOST")) {
            PU_NAME = "pu_OPENSHIFT";
            try {
                setUpTables();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private void setUpTables() throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserFacade uf = new UserFacade();
        User user = new User();
        user.setUserName("user");
        user.setPasswordHash(PasswordHash.createHash("test"));
        user.addRole("User");
        uf.addUser(user);
        
        User admin = new User();
        admin.setUserName("admin");
        admin.setPasswordHash(PasswordHash.createHash("test"));
        admin.addRole("User");
        admin.addRole("Admin");
        uf.addUser(admin);
        
        AirlineFacade af = new AirlineFacade();
        Airline a = new Airline();
        a.setName("AngularJS Airline");
        a.setUrl("http://angularairline-plaul.rhcloud.com");
        af.addAirline(a);
        
        a = new Airline();
        a.setName("TimeTravel");
        a.setUrl("http://timetravel-tocvfan.rhcloud.com");
        af.addAirline(a);
        
        a = new Airline();
        a.setName("COS-Group2");
        a.setUrl("http://wildfly-x.cloudapp.net/airline");
        af.addAirline(a);
    }

}
