/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author rabeb.amouri.stg
 */
@ManagedBean(name="switch1")
@RequestScoped
public class SwitchBean {
      private String id,addrip, hostname, site, categorie,log,cpu,error;

    public String getCategorie() {
        return categorie;
    }

    public String getAddrip() {
        return addrip;
    }

    public void setAddrip(String addrip) {
        this.addrip = addrip;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
        

    /** Creates a new instance of Switch */
    public SwitchBean() {
      
    }
}
