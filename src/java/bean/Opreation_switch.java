/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.Desktop;
import java.io.File;

import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
@ManagedBean(name = "op_switch")
@RequestScoped
public class Opreation_switch {
    
     @ManagedProperty(value = "#{switch1}")
    private SwitchBean switch1;

    public SwitchBean getSwitch1() {
        return switch1;
    }

    public void setSwitch1(SwitchBean switch1) {
        this.switch1 = switch1;
    }

    
    
    

  

    // liste des switchs selon la categorie passée en paramètre
    public List<Switch> Liste() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        // récuperation de la categorie
 String cat = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("categorie");

        List<Switch> s = session.createSQLQuery("SELECT * FROM switch where categorie like '%"+cat+"%'").addEntity(tn.tunisiana.Switch.class).list();

        session.getTransaction().commit();
       
        return s;
  

    }
    
    // liste toutes les categories présentes dans la table switch
    public List<String> Liste_categorie()
    {
     Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();


        List<String> s = session.createSQLQuery("SELECT DISTINCT categorie FROM switch").list();

        session.getTransaction().commit();
     
        return s;
  

    }
    
    // liste de tous les switchs
     public List<Switch> Liste_all() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
 

        List<Switch> s = session.createSQLQuery("SELECT * FROM switch ").addEntity(tn.tunisiana.Switch.class).list();

        session.getTransaction().commit();
       
        return s;
  

    }


   public String Repertoire() 
   {
  
            File repertoire=new File("C:\\test");
if(         Desktop.isDesktopSupported())
{
  return"ok";

        } 
     return"nn";
   }
    
  
// constructeur
    public Opreation_switch() {
        
    }
    
    
}
