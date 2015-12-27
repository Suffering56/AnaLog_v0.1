package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Suffering
 */
@Entity
@Table(name = "PROXYLOG")
@NamedQueries({
    @NamedQuery(name = "ProxyLog.findByTimeDurationUrlTraffic", query = "SELECT p FROM ProxyLog p WHERE "
        + "p.sessiontimestamp = :sessiontimestamp AND p.duration = :duration AND p.urlAddress = :urlAddress AND p.traffic = :traffic"),
    @NamedQuery(name = "ProxyLog.deleteAll", query = "DELETE FROM ProxyLog"),
    
    @NamedQuery(name = "ProxyLog.setToSelectedById", query = "UPDATE ProxyLog p SET p.selected = :selected WHERE p.id = :id"),
    @NamedQuery(name = "ProxyLog.clearSelectedFields", query = "UPDATE ProxyLog p SET p.selected = :selected"),
    @NamedQuery(name = "ProxyLog.findByUrlAddress", query = "SELECT p FROM ProxyLog p WHERE p.urlAddress LIKE :param"),
    
    @NamedQuery(name = "ProxyLog.findAll", query = "SELECT p FROM ProxyLog p"),
    @NamedQuery(name = "ProxyLog.findById", query = "SELECT p FROM ProxyLog p WHERE p.id = :id"),
    @NamedQuery(name = "ProxyLog.findBySessiontimestamp", query = "SELECT p FROM ProxyLog p WHERE p.sessiontimestamp = :sessiontimestamp"),
    @NamedQuery(name = "ProxyLog.findByClientaddress", query = "SELECT p FROM ProxyLog p WHERE p.clientaddress = :clientaddress"),
    @NamedQuery(name = "ProxyLog.findByClientname", query = "SELECT p FROM ProxyLog p WHERE p.clientname = :clientname"),
//    @NamedQuery(name = "ProxyLog.findByUrlAddress", query = "SELECT p FROM ProxyLog p WHERE p.urlAddress = :urlAddress"),
    @NamedQuery(name = "ProxyLog.findByDuration", query = "SELECT p FROM ProxyLog p WHERE p.duration = :duration"),
    @NamedQuery(name = "ProxyLog.findByTraffic", query = "SELECT p FROM ProxyLog p WHERE p.traffic = :traffic"),
    @NamedQuery(name = "ProxyLog.findByContenttype", query = "SELECT p FROM ProxyLog p WHERE p.contenttype = :contenttype"),
    @NamedQuery(name = "ProxyLog.findBySelected", query = "SELECT p FROM ProxyLog p WHERE p.selected = :selected")})
public class ProxyLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;

    @Column(name = "SESSIONTIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessiontimestamp;

    @Size(max = 50)
    @Column(name = "CLIENTADDRESS")
    private String clientaddress;

    @Size(max = 50)
    @Column(name = "CLIENTNAME")
    private String clientname;

    @Size(max = 255)
    @Column(name = "URL_ADDRESS")
    private String urlAddress;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "TRAFFIC")
    private Integer traffic;

    @Size(max = 100)
    @Column(name = "CONTENTTYPE")
    private String contenttype;

    @Column(name = "SELECTED")
    private String selected;

    public ProxyLog() {
    }

    public ProxyLog(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSessiontimestamp() {
        return sessiontimestamp;
    }

    public void setSessiontimestamp(Date sessiontimestamp) {
        this.sessiontimestamp = sessiontimestamp;
    }

    public String getClientaddress() {
        return clientaddress;
    }

    public void setClientaddress(String clientaddress) {
        this.clientaddress = clientaddress;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTraffic() {
        return traffic;
    }

    public void setTraffic(Integer traffic) {
        this.traffic = traffic;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProxyLog)) {
            return false;
        }
        ProxyLog other = (ProxyLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProxyLog[ id=" + id + " ]";
    }
}
