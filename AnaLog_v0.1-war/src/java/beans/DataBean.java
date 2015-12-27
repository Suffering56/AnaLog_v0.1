package beans;

import components.ProxyLogDataManager;
import components.ProxyLogFacade;
import entity.ProxyLog;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import sources.squidloganalyzer.EntryLogHandler;

public class DataBean implements Serializable {
    
    @EJB
    private ProxyLogFacade proxyLogFacade;
    @EJB
    private ProxyLogDataManager logDataManager;
    private List <ProxyLog> table;
    private Object ipMenuSelectedItem;
    private SelectItem[] ipMenuItems;
    private String searchCriterion;
    
    public Object applySearch() {
        logDataManager.setDataViewMode("SEARCH");
        table = logDataManager.dataSearch(searchCriterion);
        searchCriterion = "";
        return null;
    }
  
    public Object applyFilter() {        
        logDataManager.setDataViewMode("FILTER");
        table = logDataManager.filterData(ipMenuSelectedItem.toString());
        return null;
    }
    
    public Object applySort(String param) {
        if (logDataManager.getDataViewMode().equals("FILTER")
                || logDataManager.getDataViewMode().equals("FILTER_+_SORTING")){
            logDataManager.setDataViewMode("FILTER_+_SORTING");
        }
        else if (logDataManager.getDataViewMode().equals("SEARCH")
                || logDataManager.getDataViewMode().equals("SEARCH_+_SORTING")){
            logDataManager.setDataViewMode("SEARCH_+_SORTING");
        }
        else {
            logDataManager.setDataViewMode("SORTING"); 
        }
        table = logDataManager.dataSorting(param);
        return null;
    }

    public Object loadData() throws Exception {
        logDataManager.setDataViewMode("DEFAULT");
        table = logDataManager.getData();
        EntryLogHandler logHandler = new EntryLogHandler(proxyLogFacade, table);
        logHandler.logExtraction();
        table = logDataManager.getData();
        return null;
    }
    
    public Object clearData() {
        logDataManager.setDataViewMode("DEFAULT");
        proxyLogFacade.clearTable();
        table = logDataManager.getData();
        ipMenuItems = new SelectItem[0];
        return null;
    }
    
    public Object pageRefresh() {
        logDataManager.setDataViewMode("DEFAULT");
        logDataManager.clearSelectedFields();
        searchCriterion = "";
        table = logDataManager.getData();
        if (table.isEmpty()) {
            ipMenuItems = new SelectItem[0];
        }
        return null;
    }
    
    public Object toReportClick() {
        logDataManager.clearSelectedFields();
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getSelected().equals("true")) {
                logDataManager.setToSelectedById(table.get(i).getId(), "true");
            }
        }
        logDataManager.setDataViewMode("DEFAULT");
        return "GoToReport";
    }

    public List<ProxyLog> getTable() {
        if (table == null) {
            table = logDataManager.getData();
        }
        return table;
    }

    public void setTable(List<ProxyLog> table) {
        this.table = table;
    }
    
    public SelectItem[] getIpMenuItems() {
        if (ipMenuItems == null) {
            ipMenuItems = logDataManager.getIpMenuItems();
        }
        return ipMenuItems;
    }

    public void setIpMenuItems(SelectItem[] ipMenuItems) {
        this.ipMenuItems = ipMenuItems;
    }
    
    public Object getIpMenuSelectedItem() {
        return ipMenuSelectedItem;
    }

    public void setIpMenuSelectedItem(Object ipMenuSelectedItem) {
        this.ipMenuSelectedItem = ipMenuSelectedItem;
    }

    public String getSearchCriterion() {
        return searchCriterion;
    }

    public void setSearchCriterion(String searchCriterion) {
        this.searchCriterion = searchCriterion;
    }
}
