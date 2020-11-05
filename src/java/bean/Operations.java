/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;



import tn.commun.TelnetConnexion;
import tn.commun.filtre_CPU;
import tn.commun.filtre_error;
import tn.commun.filtre_logging;
import tn.persistance.HibernateUtil;
import tn.tunisiana.Switch;
import tn.tunisiana.User;

/**
 *
 * @author rabeb.amouri.stg
 */
@ManagedBean(name = "Operation")
@RequestScoped
public class Operations {

    @ManagedProperty(value = "#{user}")
    private user user;
    private String message;
    private String filtre = "";
    private String text;
    private boolean visible = false;
    private static String log = "";
    private static String pass = "";
    private boolean progress=true;

    public boolean isProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }

    public enum Outcome {

        login, home ,Fond
    }

    /** Creates a new instance of Operations */
    public Operations() {
    }

    // vérifier l'authentification
    public Outcome verif_auth() {


        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        // on récupère le login et le mot de passe dans des variables statiques
        setLog(user.getLogin());
        setPass(user.getPasswd());
        // récupère l'utilisateur correspondant au login et au password dans une liste
        List users = session.createSQLQuery("SELECT * FROM user WHERE login = '" + user.getLogin() + "' AND pass= '" + user.getPasswd() + "'").addEntity(tn.tunisiana.User.class).list();
        session.getTransaction().commit();
        //si la liste n'est pas vide on redirige vesr home
        if (users.size() > 0) {
            return Outcome.home;
        } else {
            // sinon on réinitialise les champs et on affiche un message d'erreur

           
            FacesMessage mess = new FacesMessage(FacesMessage.SEVERITY_ERROR,"        veuillez entrer correctement le login et password!!!","");
            FacesContext.getCurrentInstance().addMessage(null, mess);
            return Outcome.login;
        }


    }
    //methode qui retourne le user entré

    public String affich_user() {
        String g = getLog();
        return g;
    }
    // methode qui retourne le password entré

    public String affich_pass() {
        String g = getPass();
        return g;
    }
    // methode qui mets la variable visible à true

    public void vrai() {
        setVisible(true);
    }
public Outcome retour()
{
    return Outcome.login;
}
    public void listerRepertoire() {

        String[] listefichiers;

        int i;
        File repertoire = new File("C:\\test");
        listefichiers = repertoire.list();
        for (i = 0; i < listefichiers.length; i++) {
            if (listefichiers[i].endsWith(".log") == true) {

                System.out.println(listefichiers[i].replaceFirst(".log", "")); // on choisit la sous chaine - les 5 derniers caracteres ".java" 
            }
        }
    }
    // ajouter un utilisatuer à la base de données

    public void ajouter() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        User u = new User();
        if (user.getLogin().equals("")||user.getPasswd().equals("")){
             FacesMessage mess = new FacesMessage(FacesMessage.SEVERITY_ERROR,"        tous les champs sont obligatoires!!!","");
            FacesContext.getCurrentInstance().addMessage(null, mess);
        
    }
        else{
        u.setLogin(user.getLogin());
        u.setPass(user.getPasswd());



        session.save(u); // enregistrer l'entité u
        session.flush(); // raffraichir la base de données
        session.getTransaction().commit();

        HibernateUtil.getSessionFactory().close();
        user.setLogin("");
        user.setPasswd("");



        message = "Utilsateur ajouté avec succes !!";
        }
    }

    // liste de tous les utilisateurs dans la base de données 
    public List<User> Liste_users() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();


        List<User> s = session.createSQLQuery("SELECT * FROM user ").addEntity(tn.tunisiana.User.class).list();

        session.getTransaction().commit();

        return s;

    }
    // suppression d'un user à partir de son id

    public void supprimer() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String id = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

        Integer n = Integer.parseInt(id);

        User u = (User) session.load(User.class, n);
        session.delete(u);//suppression de l’objet «  u »


        session.flush(); // raffraichir la base de données
        session.getTransaction().commit();



    }
    // commande à envoyer au switch

    public String commande() {
        setVisible(true);
        // récupérer le paramètre adr (adresse ip) de la page one_switch.xhtml
        String adr = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("adr");
        // récupérer le paramètre name (hostname) de la page one_switch.xhtml
        String name = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("name");
        //recupere la commande a executer 
        String com = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("commande");
        //recupere la commande a executer 
        String cate = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("categorie");
        // récupérer la date sous format yyyy-mm-dd
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dat = dateFormat.format(date);

        int heure = date.getHours();
        int min = date.getMinutes();
        // récupération de l'utilisateur et son password
        String utilisateur = affich_user();
        String password = affich_pass();
        //URL du fichier ou on va enregistrer le résultat de la commande
        String fich = "C:\\test\\" + name.trim() + "_" + dat + "_" + heure + "h" + min + ".log";
        // connexion telnet avec le switch et authentification
        TelnetConnexion telnet = new TelnetConnexion(adr, utilisateur, password, fich);
        telnet.sendCommand("terminal length 0");
        // commande a exécuter
        if (com.contentEquals("show process cpu history")) {
            telnet.sendCommand(com);
            telnet.sendCommand(com);
            telnet.disconnect();

        } else {
            telnet.sendCommand(com);
             telnet.disconnect();
        }

        // récupération du contenu du fichier dans une variable de type String
        text = "";
        try {

            text = FileUtils.readFileToString(new File(fich));

        } catch (IOException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
        // aplliquer le filtre correspondant a chaque commande
        if (com.contains("cpu")) {
            filtre = filtre_cpu(fich);
        }
        if (com.contains("error")) {
            filtre = filtre_error(fich);

        }
        if (com.contains("log")) {
            filtre = filtre_log(fich);

        }
        return text;


    }

    // *******les filtres à appliquer
    public String filtre_cpu(String fichier) {


        String v = "";
        filtre_CPU t = new filtre_CPU();// création d'un objet de type fitre_CPU

        v = t.chercher(fichier);// appel de la méthode chercher
        if (v.length() > 0) {
            return v;
        } else {
            return "aucune information critique";
        }
    }

    public String filtre_error(String fichier) {


        String v = "";
        filtre_error t = new filtre_error();// création d'un objet de type fitre_error
        v = t.rechercher(fichier, 20);// appel de la méthode rechercher
        if (v.length() > 0) {
            return v;
        } else {
            return "aucune information critique";
        }
    }

    public String filtre_log(String fichier) {

        String v = "";

        filtre_logging t = new filtre_logging();// création d'un objet de type fitre_logging
        v = t.recherche(fichier); // appel de la méthode recherche
        if (v.length() > 0) {
            return v;
        } else {
            return "aucune information critique";
        }
    }

    // **********executer la commande show logging sur tous les switch de la categorie choisie 
    public String all_log() {

        String output = "";
        Opreation_switch op = new Opreation_switch();
//recuperer la liste des switch selon la categorie
        List<Switch> s = op.Liste();

// parcourir tous les switch de la categorie 
        for (int i = 0; i < s.size(); i++) {
            String name = s.get(i).getHostname();
            String adr = s.get(i).getAdrIP();
            String cate=s.get(i).getCategorie();
            //recuperer la comande a executer
            String com = s.get(i).getLog();
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dat = dateFormat.format(date);

            int heure = date.getHours();
            int min = date.getMinutes();
            // récupération de l'utilisateur et son password
            String utilisateur = affich_user();
            String password = affich_pass();
            String fich = "C:\\test\\" + name.trim() + "_" + dat + "_" + heure + "h" + min + ".log";
// etablir une connexion telnet pour chaque switch
            TelnetConnexion telnet = new TelnetConnexion(adr, utilisateur, password, fich);
            // executer les commande pour chaque switch           
            telnet.sendCommand("terminal length 0");

            telnet.sendCommand(com);
             telnet.disconnect();
            text = "";
            try {
//lecture du fichier log
                text = FileUtils.readFileToString(new File(fich));

            } catch (IOException ex) {
                Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            }
//variable intermediaire pour concerver le resultat de chaque switch
            String v = "";
// appel  au filtre 
            filtre_logging t = new filtre_logging();
            // recuperer le resultat du filtre           
            v = t.recherche(fich);
            // tester si le fichier log presente des info crtiques           
            if (v.length() > 0) {
                output = output + "\n *****Rapport du switch: " + name + " ***** \n" + v;
            } else {
                output = output + "\n *****Rapport du switch: " + name + " ***** \n" + "aucune information ";
            }
        }
        return output;

    }

    public String all_error() {

        String output = "";
        Opreation_switch op = new Opreation_switch();
//recuperer la liste des switch selon la categorie
        List<Switch> s = op.Liste();

// parcourir tous les switch de la categorie 
        for (int i = 0; i < s.size(); i++) {
            String name = s.get(i).getHostname();
            String adr = s.get(i).getAdrIP();
            String cate=s.get(i).getCategorie();
            //recuperer la comande a executer
            String com = s.get(i).getError();
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dat = dateFormat.format(date);

            int heure = date.getHours();
            int min = date.getMinutes();
            // récupération de l'utilisateur et son password
            String utilisateur = affich_user();
            String password = affich_pass();
            String fich = "C:\\test\\" + name.trim() + "_" + dat + "_" + heure + "h" + min + ".log";
// etablir une connexion telnet pour chaque switch
            TelnetConnexion telnet = new TelnetConnexion(adr, utilisateur, password, fich);
            // executer les commande pour chaque switch           
            telnet.sendCommand("terminal length 0");

            telnet.sendCommand(com);
             telnet.disconnect();
            text = "";
            try {
//lecture du fichier log
                text = FileUtils.readFileToString(new File(fich));

            } catch (IOException ex) {
                Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            }
//variable intermediaire pour concerver le resultat de chaque switch
            String v = "";
// appel  au filtre 
            filtre_error t = new filtre_error();
            // recuperer le resultat du filtre           
            v = t.rechercher(fich, 20);
            // tester si le fichier log presente des info crtiques           
            if (v.length() > 0) {
                output = output + "\n *****Rapport du switch: " + name + " ***** \n" + v;
            } else {
                output = output + "\n *****Rapport du switch: " + name + " ***** \n" + "aucune information ";
            }
        }
        return output;

    }

    public String all_cpu() {


        String output = "";
        Opreation_switch op = new Opreation_switch();
//recuperer la liste des switch selon la categorie
        List<Switch> s = op.Liste();

// parcourir tous les switch de la categorie 
        for (int i = 0; i < s.size(); i++) {
            String name = s.get(i).getHostname();
            String adr = s.get(i).getAdrIP();
            String cate= s.get(i).getCategorie();
            //recuperer la comande a executer
            String com = s.get(i).getCpu();
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dat = dateFormat.format(date);

            int heure = date.getHours();
            int min = date.getMinutes();
            // récupération de l'utilisateur et son password
            String utilisateur = affich_user();
            String password = affich_pass();
            String fich = "C:\\test\\" + name.trim() + "_" + dat + "_" + heure + "h" + min + ".log";
// etablir une connexion telnet oiur chaque switch
            TelnetConnexion telnet = new TelnetConnexion(adr, utilisateur, password, fich);
            // executer les commande pour chaque switch           
            telnet.sendCommand("terminal length 0");

            telnet.sendCommand(com);
            telnet.sendCommand(com);
             telnet.disconnect();

            text = "";
            try {
//lecture du fichier log
                text = FileUtils.readFileToString(new File(fich));

            } catch (IOException ex) {
                Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            }
//variable intermediaire pour concerver le resultat de chaque switch
            String v = "";
// appel  au filtre 
            filtre_CPU t = new filtre_CPU();
            // recuperer le resultat du filtre           
            v = t.chercher(fich);
            // tester si le fichier log presente des info crtiques           
            if (v.length() > 0) {
                output = output + "\n *****Rapport du switch: " + name + " ***** \n" + v;
            } else {
                output = output + "\n *****Rapport du switch: " + name + " ***** \n" + "aucune information ";
            }
        }
        return output;

    }
// appliquer toutes les commandes sur tous les filtres
    public String all_command() {

        String cpu = all_cpu();
        String error = all_error();
        String logg = all_log();
       setProgress(false);
       
   return "*****pour la commande cpu ******\n" + cpu + "\n *****pourla commande log *****\n" + logg + "\n *****pourla commande error *****\n" + error;

    }
//****************************

    // ******getters and setters******
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public bean.user getUser() {
        return user;
    }

    public void setUser(bean.user user) {
        this.user = user;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        Operations.pass = pass;
    }

    public static String getLog() {
        return log;
    }

    public static void setLog(String log) {
        Operations.log = log;
    }

    public String getFiltre() {
        return filtre;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setFiltre(String filtre) {
        this.filtre = filtre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
