package components;

import entity.Users;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UsersFacade {

    @PersistenceContext (unitName="Analog_v0.1-ejbPU")
    private EntityManager manager;
    
    public List <Users> findAllUsers() { 
        return manager.createNamedQuery("Users.findAll").getResultList();
    }
    
    public Users persistUser(Users newUser) {
        manager.persist(newUser);
        return newUser;
    }
}
