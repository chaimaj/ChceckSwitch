/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.commun;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 *
 * @author rabeb.amouri.stg
 */
public class filtre_error {

    public String rechercher(String fichier, int seuil) {

        String result = "";
        int j;
        Vector v = new Vector();
        //lecture du fichier texte	
        try {


            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            int c = 0;


            while ((ligne = br.readLine()) != null) {
            //On teste si la ligne n'est pas vide
                if ((ligne.length() > 0)) {
                    // On teste si la ligne commence par 'G' 'T' ou 'P'
                    if (ligne.charAt(0) == 'G' || ligne.charAt(0) == 'T' || ((ligne.charAt(0) == 'P') && (!(ligne.contains("Port"))))) {
                        String[] s = new String[20];
                        // on sépare la ligne selon les espaces et on mets dans un tableau tokens
                        String[] tokens = ligne.split(" ");
                        // On élimine les éléments vide du tableau et on enregistre nos valeurs dans s
                        j = 0;
                        for (int i = 0; i < tokens.length; i++) {
                            if (!(tokens[i].equals(""))) {
                                s[j] = tokens[i];
                                j++;
                            }
                        }

                        int k = 1;
                        //On teste si on a un élément qui dépasse le seuil et on enregistre son tableau correspondant dans un vecteur
                        boolean test = false;
                        while ((k < j) && (!(test))) {

                            if (Integer.parseInt(s[k]) > seuil) {

                                test = true;

                            } else {
                                k++;
                            }
                        }
                        if (test) {

                            v.add(ligne);
                        }
                    }
                    if (ligne.contains("Port")) {
                        v.add(ligne);
                    }

                }
            }
            for (int i = 0; i < v.size(); i++) {
                result += v.elementAt(i) + "\n";
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }
}
