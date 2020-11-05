/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tunisiana;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rabeb.amouri.stg
 */
@Entity
@Table(name = "switch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Switch.findAll", query = "SELECT s FROM Switch s"),
    @NamedQuery(name = "Switch.findById", query = "SELECT s FROM Switch s WHERE s.id = :id"),
    @NamedQuery(name = "Switch.findByHostname", query = "SELECT s FROM Switch s WHERE s.hostname = :hostname"),
    @NamedQuery(name = "Switch.findByAdrIP", query = "SELECT s FROM Switch s WHERE s.adrIP = :adrIP"),
    @NamedQuery(name = "Switch.findBySite", query = "SELECT s FROM Switch s WHERE s.site = :site"),
    @NamedQuery(name = "Switch.findByCategorie", query = "SELECT s FROM Switch s WHERE s.categorie = :categorie"),
    @NamedQuery(name = "Switch.findByLog", query = "SELECT s FROM Switch s WHERE s.log = :log"),
    @NamedQuery(name = "Switch.findByCpu", query = "SELECT s FROM Switch s WHERE s.cpu = :cpu"),
    @NamedQuery(name = "Switch.findByError", query = "SELECT s FROM Switch s WHERE s.error = :error")})
public class Switch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hostname")
    private String hostname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "adrIP")
    private String adrIP;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "site")
    private String site;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "categorie")
    private String categorie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "log")
    private String log;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cpu")
    private String cpu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "error")
    private String error;

    public Switch() {
    }

    public Switch(Integer id) {
        this.id = id;
    }

    public Switch(Integer id, String hostname, String adrIP, String site, String categorie, String log, String cpu, String error) {
        this.id = id;
        this.hostname = hostname;
        this.adrIP = adrIP;
        this.site = site;
        this.categorie = categorie;
        this.log = log;
        this.cpu = cpu;
        this.error = error;
    }
     public Switch( String hostname, String adrIP, String site, String categorie, String log, String cpu, String error) {
      
        this.hostname = hostname;
        this.adrIP = adrIP;
        this.site = site;
        this.categorie = categorie;
        this.log = log;
        this.cpu = cpu;
        this.error = error;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getAdrIP() {
        return adrIP;
    }

    public void setAdrIP(String adrIP) {
        this.adrIP = adrIP;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Switch)) {
            return false;
        }
        Switch other = (Switch) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tn.tunisiana.Switch[ id=" + id + " ]";
    }
    
}
