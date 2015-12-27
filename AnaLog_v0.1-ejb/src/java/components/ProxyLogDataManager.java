package components;

import entity.ProxyLog;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ProxyLogDataManager {
    @PersistenceContext (unitName="Analog_v0.1-ejbPU")
    private EntityManager manager;
    private SelectItem[] ipMenuItems;
    private String clientAddress;
    private String dataViewMode;
    private List <ProxyLog> fullProxyLog;
    private List <ProxyLog> filteredProxyLog;
    private List <ProxyLog> sortedProxyLog;
    private String sortMode = "ASC";
    private String query;
    private String searchParam;
    
    public List <ProxyLog> getData() {
        if (dataViewMode.equals("FILTER")) {
            return filterData(clientAddress);
        }
        else if (dataViewMode.equals("DEFAULT")) {
            return manager.createNamedQuery("ProxyLog.findAll").getResultList();
        }
        else if (dataViewMode.equals("SORTING")) {
            return manager.createQuery(query).getResultList();
        }
        else if (dataViewMode.equals("FILTER_+_SORTING")) {
            return manager.createQuery(query).getResultList();
        }
        else if (dataViewMode.equals("SEARCH")) {
            return manager.createNamedQuery("ProxyLog.findByUrlAddress").setParameter("param", searchParam).getResultList();
        }
        else if (dataViewMode.equals("SEARCH_+_SORTING")) {
            return manager.createQuery(query).getResultList();
        }
        else {
            return manager.createNamedQuery("ProxyLog.findAll").getResultList();
        }
    }
    
    public List <ProxyLog> dataSearch(String searchParam) {
        searchParam = "%" + searchParam + "%";
        this.searchParam = searchParam;
        filteredProxyLog = manager.createNamedQuery("ProxyLog.findByUrlAddress").setParameter("param", searchParam).getResultList();
        return filteredProxyLog;
    }
    
    public List <ProxyLog> dataSorting(String sortParam) {
        String filterParam = "";
        sortParam = "ORDER BY p." + sortParam + " " + sortMode;
        if (dataViewMode.equals("FILTER") || dataViewMode.equals("FILTER_+_SORTING")) {
            filterParam = "WHERE p.clientaddress = '" + clientAddress + "' ";
        }
        else if (dataViewMode.equals("SEARCH") || dataViewMode.equals("SEARCH_+_SORTING")) {
            filterParam = "WHERE p.urlAddress LIKE '" + searchParam + "' ";
        }
        
        if (sortMode.equals("ASC")) {
            sortMode = "DESC";
        }
        else {
            sortMode = "ASC";
        }
        
        query = "SELECT p FROM ProxyLog p " + filterParam + sortParam;
        sortedProxyLog = manager.createQuery(query).getResultList();
        return sortedProxyLog;
    }
    
    public List <ProxyLog> filterData(String clientAddress) {
        this.clientAddress = clientAddress;
        filteredProxyLog = manager.createNamedQuery("ProxyLog.findByClientaddress")
                .setParameter("clientaddress", clientAddress).getResultList();
        return filteredProxyLog;
    }
    
    public void setToSelectedById(int id, String selected) {
        manager.createNamedQuery("ProxyLog.setToSelectedById").setParameter("id", id)
                .setParameter("selected", selected).executeUpdate();
    }
    
    public void clearSelectedFields() {
        manager.createNamedQuery("ProxyLog.clearSelectedFields")
                .setParameter("selected", "false").executeUpdate();
    }
    
    public SelectItem[] getIpMenuItems() {
        fullProxyLog = manager.createNamedQuery("ProxyLog.findAll").getResultList();
        if (fullProxyLog.isEmpty()) {
            return null;
        }
        
        Set <String> clientIpSet = new LinkedHashSet <String>();
        for (int i = 0; i < fullProxyLog.size(); i++) {
            clientIpSet.add(fullProxyLog.get(i).getClientaddress());
        }
        
        String[] clientIpArray = clientIpSet.toArray(new String[0]);
        ipMenuItems = new SelectItem[clientIpArray.length];
        for (int i = 0; i < clientIpArray.length; i++) {
            ipMenuItems[i] = new SelectItem(clientIpArray[i]);
        }
        return ipMenuItems;
    }
    
    public List <ProxyLog> getSelectedProxyRecords() {
        List <ProxyLog> result = manager.createNamedQuery("ProxyLog.findBySelected").setParameter("selected", "true").getResultList();
        return result;
    }

    public String getDataViewMode() {
        return dataViewMode;
    }
    
    public void setDataViewMode(String viewMode) {
        this.dataViewMode = viewMode;
    }

    public List<ProxyLog> getSortedProxyLog() {
        return sortedProxyLog;
    }
}
