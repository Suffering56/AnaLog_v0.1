package components;

import entity.ProxyLog;
import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ProxyLogFacade {

    @PersistenceContext(unitName="Analog_v0.1-ejbPU")
    private EntityManager manager;
    
    public void persistEntry(ProxyLog entry) {
        manager.persist(entry);
    }
    
    public void clearTable() {
        manager.createNamedQuery("ProxyLog.deleteAll").executeUpdate();
    }
    
    public boolean recordExistCheck(Date timestamp, int duration, String URL, int traffic) {
        return manager.createNamedQuery("ProxyLog.findByTimeDurationUrlTraffic")
                .setParameter("sessiontimestamp", timestamp)
                .setParameter("duration", duration)
                .setParameter("urlAddress", URL)
                .setParameter("traffic", traffic).getResultList().isEmpty();
    }
}
