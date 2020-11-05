/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.commun;

import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author rabeb.amouri.stg
 */
public class filtre_logging {

    String result = "";

    public String recherche(String fichier) {
        //lecture du fichier texte	
        try {
            // on récupère la date
            Date d = new Date();
            Calendar calendar = new GregorianCalendar();
            String month = null;
            // le jour actuel
            int jour = d.getDate();

            //le mois actuel en chiffres
            int actuel = calendar.get(Calendar.MONTH);

            DateFormatSymbols dfsEN = new DateFormatSymbols(Locale.ENGLISH);
            // on récupère tous les mois version : jun,jul...
            String[] mois = dfsEN.getShortMonths();
            // on cherche le mois correspondant au mois actuel
            for (int i = 0; i < mois.length; i++) {
                if (i == actuel) {
                    month = mois[i];
                }

            }


            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            

// on teste les lignes contenant le mois, le jour et ne contenant pas User
            while ((ligne = br.readLine()) != null) {
                if (ligne.contains(month+" " + jour + " ") && (!(ligne.contains("*****")))) {
                    String[] tokens = ligne.split(" ");


                    if (!(tokens[5].equals("User"))) {
                        result += ligne + "\n";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }


        return result;
    }
	public Matrice[] Decomposition(Matrice M){
       Matrice[] resultat;
       int n=M.getNbcolonne()/8;
       resultat = new Matrice[n];
       Matrice inter= new Matrice(8,8);
       int maxi =7;
       int maxj =7;
       int mini =0;
       int minj=0;
       int nb=0;
       int iint = 0;
       for (int i=mini; i<=maxi;i++){
             for (int j=minj; j<=maxj;j++){
                if (iint==8)
                    iint=0;
                int jint=0;
              inter.matrix[iint][jint]=M.matrix[i][j]; 
                     
              jint++;
              if ((i==maxi)&& (i!=M.getNbligne()-1)){
                  resultat[nb]= inter;
                  nb++;
              
                  inter= new Matrice(8,8);
                  maxi=maxi+8;
                  mini=mini+8;
              }
              if (maxi==M.getNbligne()-1){
                  mini=0;
                  maxi=7;
                  maxj=maxj+8;
                  minj=minj+8;
              }
           }
             iint++;
       }
       System.out.println(nb+1);
       return resultat;
       
   }

    
}
