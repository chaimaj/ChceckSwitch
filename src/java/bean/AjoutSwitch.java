/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.Session;
import tn.persistance.HibernateUtil;
import tn.tunisiana.Switch;

/**
 *
 * @author rabeb.amouri.stg
 */
@ManagedBean(name = "ajout")
@RequestScoped
public class AjoutSwitch {

    @ManagedProperty(value = "#{switch1}")
    private SwitchBean switch1;
    private static final String IPADDRESS_PATTERN =  //format d'une adresse ip
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    private String message;
    private String categorie;

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SwitchBean getSwitch1() {
        return switch1;
    }

    public void setSwitch1(SwitchBean switch1) {
        this.switch1 = switch1;
    }

    /** Creates a new instance of AjoutSwitch */
    public AjoutSwitch() {
    }

    public void ajouter() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        Switch s = new Switch();

        if (switch1.getAddrip().equals("") || switch1.getCpu().equals("") || switch1.getError().equals("") || switch1.getHostname().equals("") || switch1.getSite().equals("") || switch1.getLog().equals("")) {
            FacesMessage mess = new FacesMessage(FacesMessage.SEVERITY_FATAL, "        tous les champs sont obligatoires!!!", "");
            FacesContext.getCurrentInstance().addMessage(null, mess);
            

        } else if (verif_ip(switch1.getAddrip()) == false) {
            FacesMessage mess = new FacesMessage(FacesMessage.SEVERITY_FATAL, "        Le champ adresse IP n'est pas correcte!!!", "");
            FacesContext.getCurrentInstance().addMessage(null, mess);
          

        } else {
            s.setAdrIP(switch1.getAddrip().trim());
            s.setCategorie(this.categorie);
            s.setCpu(switch1.getCpu());
            s.setError(switch1.getError());
            s.setHostname(switch1.getHostname().trim());
            s.setSite(switch1.getSite());
            s.setLog(switch1.getLog());


            session.save(s);
            session.flush(); // raffraichir la base de données
            session.getTransaction().commit();

            HibernateUtil.getSessionFactory().close();

            switch1.setHostname("");
            switch1.setCategorie("");
            switch1.setAddrip("");
            switch1.setCpu("");
            switch1.setError("");
            switch1.setSite("");
            switch1.setLog("");

            message = "Switch ajouté avec succes !!";
        }
    }

    public void ajouter_categorie() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        Switch s = new Switch();
        if (switch1.getAddrip().equals("") || switch1.getCpu().equals("") || switch1.getCategorie().equals("") || switch1.getError().equals("") || switch1.getHostname().equals("") || switch1.getSite().equals("") || switch1.getLog().equals("")) {
            FacesMessage mess = new FacesMessage(FacesMessage.SEVERITY_ERROR, "        tous les champs sont obligatoires!!!", "");
            FacesContext.getCurrentInstance().addMessage(null, mess);
            switch1.setHostname("");
            switch1.setCategorie("");
            switch1.setAddrip("");
            switch1.setCpu("");
            switch1.setError("");
            switch1.setSite("");
            switch1.setLog("");
        } else if (verif_ip(switch1.getAddrip()) == false) {
            FacesMessage mess = new FacesMessage(FacesMessage.SEVERITY_ERROR, "        Le champ adresse IP n'est pas correcte!!!", "");
            FacesContext.getCurrentInstance().addMessage(null, mess);
            switch1.setHostname("");
            switch1.setCategorie("");
            switch1.setAddrip("");
            switch1.setCpu("");
            switch1.setError("");
            switch1.setSite("");
            switch1.setLog("");

        } else {
            s.setAdrIP(switch1.getAddrip().trim());
            s.setCategorie(switch1.getCategorie());
            s.setCpu(switch1.getCpu());
            s.setError(switch1.getError());
            s.setHostname(switch1.getHostname().trim());
            s.setSite(switch1.getSite());
            s.setLog(switch1.getLog());

            session.save(s);
            session.flush(); // raffraichir la base de données
            session.getTransaction().commit();

            HibernateUtil.getSessionFactory().close();

            switch1.setHostname("");
            switch1.setCategorie("");
            switch1.setAddrip("");
            switch1.setCpu("");
            switch1.setError("");
            switch1.setSite("");
            switch1.setLog("");

            message = "Switch ajouté avec succes !!";
        }
    }

    public boolean verif_ip(String adresse) {
        String adr = adresse.trim();


        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(adr);
        return matcher.matches();

    }

    public void supprimer() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String id = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("identifiant");

        Integer n = Integer.parseInt(id);

        Switch sw = (Switch) session.load(Switch.class, n);
        session.delete(sw);//suppression de l’objet «  sw »


        session.flush(); // raffraichir la base de données
        session.getTransaction().commit();



    }
}
