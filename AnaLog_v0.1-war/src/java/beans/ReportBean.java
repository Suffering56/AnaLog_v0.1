package beans;

import components.ProxyLogDataManager;
import entity.ProxyLog;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

public class ReportBean implements Serializable {
    @EJB
    private ProxyLogDataManager logDataManager;
    private List<ProxyLog> selectedDataTable;
    private String reportText;
    
    public List<ProxyLog> getSelectedDataTable() {
        if (selectedDataTable == null) {
            selectedDataTable = logDataManager.getSelectedProxyRecords();
        }
        return selectedDataTable;
    }

    public void setSelectedDataTable(List<ProxyLog> selectedDataTable) {
        this.selectedDataTable = selectedDataTable;
    }
    
    public Object returnToData() {
        logDataManager.setDataViewMode("DEFAULT");
        logDataManager.clearSelectedFields();
        return "GoToData";
    }

    public String getReportText() {
        int reportPartsCounter = getSelectedDataTable().size();
        if (selectedDataTable.isEmpty()) {
            reportText = "Вы не выбрали подотчетных данных";
        } 
        else {
            String[] reportPart = new String[reportPartsCounter];
            String reportParts = "";
            for (int i = 0; i < reportPartsCounter; i++) {
                reportPart[i] = i + ")" + selectedDataTable.get(i).getSessiontimestamp().toGMTString() + " "
                + "сотрудник " + selectedDataTable.get(i).getClientname() + " "
                + "посещал URL-ресурс " + selectedDataTable.get(i).getUrlAddress() + "\n";
                reportParts = reportParts + reportPart[i];
            }
            reportText = "  Начальнику отдела безопасности Территориального Фонда ОМС Оренбургской области. "
                + "Анализ логов прокси-сервера предприятия показал, что: \n"
                + reportParts
                + "Данные действия являются нарушением правил эксплуатации вычислительной техники предприятия и приводят к сбою в работе техники предприятия,"
                + " либо являются неэффективным использованием рабочего времени.";
        }
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }  
}
