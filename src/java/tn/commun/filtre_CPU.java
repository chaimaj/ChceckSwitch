/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.commun;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Vector;

/**
 *
 * @author rabeb.amouri.stg
 */
public class filtre_CPU {

    int[] t1 = new int[100]; // valeurs de la courbe 1 (last 60 seconds)
    int[] t2 = new int[100]; // valeurs de la courbe 2 (last 60 minutes)
    int[] t3 = new int[100]; // valeurs de la courbe 3 (last 72 hours)
    int seuil = 20;
    String result = "";

// vérifier si une chaine ne contient que des espaces
    public boolean verif(String chaine) {

        boolean v = false;
        int i = 0;
        while ((i < chaine.length()) && v == false) {
            char c = chaine.charAt(i);
            i++;
            if (!(c == ' ')) {
                v = true;
            }
        }
        return v;
    }
// Rechecher les lignes correspondant aux valeurs des éléments de la courbes

    public String chercher(String fichier) {

        String chaine = "";



        Vector<String> v = new Vector();
        //lecture du fichier texte	
        try {

            // lecture du fichier
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;


            while ((ligne = br.readLine()) != null) {
                // test si la ligne n'est pas vide
                if (ligne.length() > 0) {
                    //tester si la ligne représente les valeurs de la courbe
                    if ((ligne.substring(0, 5).equals("     "))) {
                        if ((!(ligne.contains("               0    5"))) && verif(ligne) && (!(ligne.charAt(15) == 'C')) && (!(ligne.contains("* ="))) && (!(ligne.contains("CPU%"))) && (ligne.substring(0, 5).equals("     "))) {

                            v.add(ligne);

                        }
                    }

                }
            }

// sauvegarder le résultat dans une matrice de test
            chaine = matrice(v);

            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return chaine;

    }
// détection des piques

    public String pique(int[] t, String s) {
        String chaine = "";
        for (int i = 0; i < 100; i++) {
            if (t[i] >= seuil) {
                chaine = chaine + " a l'instant " + i + "  " + s + "a atteint une valeur de " + t[i] + "\n";
            }
        }
        return chaine;
    }

    public String matrice(Vector<String> v) {
        // variables intermédiares
        int x1;
        int x;

// positions de lecture
        int pos1 = 0;
        int pos2 = 0;
        for (int j = 0; j < v.size(); j++) {

            if (v.elementAt(j).contains("..")) {
                if (pos1 == 0) {
                    pos1 = j;
                } else if (pos2 == 0) {
                    pos2 = j;
                }
            }
        }

        // tester si les valeurs de chaque courbe sont supérieurs ou inférieurs a 10 (sur une
        // ou sur deux lignes)
        if (pos1 == 1) {


            for (int i = 5; i < v.elementAt(pos1).length(); i++) {
                char c = v.elementAt(pos1 - 1).charAt(i);
                if (c == ' ') {
                    x = 0;

                } else {
                    x = Integer.parseInt(c + "");


                }

                t1[i - 4] = x;
            }
            if (pos2 == 3) {

                for (int i = 5; i < v.elementAt(pos2).length(); i++) {
                    char c = v.elementAt(pos2 - 1).charAt(i);
                    if (c == ' ') {
                        x = 0;

                    } else {
                        x = Integer.parseInt(c + "");
                    }

                    t2[i - 5] = x;
                }
                if (v.size() == 7) {
                    for (int i = 5; i < v.elementAt(pos2 + 2).length(); i++) {

                        char c = v.elementAt(pos2 + 1).charAt(i);
                        char c2 = v.elementAt(pos2 + 2).charAt(i);
                        if (c == ' ') {
                            x = 0;
                        } else {
                            x = (Integer.parseInt(c + "")) * 10;
                        }
                        if (c2 == ' ') {
                            x1 = 0;
                        } else {
                            x1 = (Integer.parseInt(c2 + ""));
                        }

                        t3[i - 5] = x + x1;
                    }

                }
                if (v.size() == 6) {

                    for (int i = 5; i < v.elementAt(pos2 + 1).length(); i++) {
                        char c = v.elementAt(pos2 + 1).charAt(i);
                        if (c == ' ') {
                            x = 0;
                        } else {

                            x = (Integer.parseInt(c + ""));
                        }
                        t3[i - 5] = x;
                    }
                }
            }
            if (pos2 == 4) {
                for (int i = 5; i < v.elementAt(pos2).length(); i++) {
                    char c2 = v.elementAt(pos2 - 1).charAt(i);
                    char c = v.elementAt(pos2 - 2).charAt(i);
                    if (c == ' ') {
                        x = 0;
                    } else {
                        x = (Integer.parseInt(c + "")) * 10;
                    }
                    if (c2 == ' ') {
                        x1 = 0;
                    } else {
                        x1 = (Integer.parseInt(c2 + ""));
                    }

                    t2[i - 5] = x + x1;
                }
                if (v.size() == 8) {
                    for (int i = 5; i < v.elementAt(pos2 + 1).length(); i++) {
                        char c = v.elementAt(pos2 + 1).charAt(i);
                        char c2 = v.elementAt(pos2 + 2).charAt(i);
                        if (c == ' ') {
                            x = 0;
                        } else {
                            x = (Integer.parseInt(c + "")) * 10;
                        }
                        if (c2 == ' ') {
                            x1 = 0;
                        } else {
                            x1 = (Integer.parseInt(c2 + ""));
                        }


                        t3[i - 5] = x + x1;
                    }
                }

                if (v.size() == 7) {
                    for (int i = 5; i < v.elementAt(pos2 + 1).length(); i++) {
                        char c = v.elementAt(pos2 + 1).charAt(i);
                        if (c == ' ') {
                            x = 0;
                        } else {

                            x = (Integer.parseInt(c + ""));
                        }


                        t3[i - 5] = x;
                    }
                }
            }
        }

        if (pos1 == 2) {
            for (int i = 5; i < v.elementAt(pos1).length(); i++) {
                char c2 = v.elementAt(pos1 - 1).charAt(i);
                char c = v.elementAt(pos1 - 2).charAt(i);
                if (c == ' ') {
                    x = 0;
                } else {
                    x = (Integer.parseInt(c + "")) * 10;
                }
                if (c2 == ' ') {
                    x1 = 0;
                } else {
                    x1 = (Integer.parseInt(c2 + ""));
                }

                t1[i - 5] = x + x1;
            }
            if (pos2 == 4) {
                for (int i = 5; i < v.elementAt(pos2).length(); i++) {
                    char c = v.elementAt(pos2 - 1).charAt(i);
                    if (c == ' ') {
                        x = 0;
                    } else {

                        x = (Integer.parseInt(c + ""));
                    }


                    t2[i - 5] = x;
                }
                if (v.size() == 8) {
                    for (int i = 5; i < v.elementAt(pos2 + 1).length(); i++) {
                        char c = v.elementAt(pos2 + 1).charAt(i);
                        char c2 = v.elementAt(pos2 + 2).charAt(i);
                        if (c == ' ') {
                            x = 0;
                        } else {
                            x = (Integer.parseInt(c + "")) * 10;
                        }
                        if (c2 == ' ') {
                            x1 = 0;
                        } else {
                            x1 = (Integer.parseInt(c2 + ""));
                        }

                        t3[i - 5] = x + x1;
                    }
                }
                if (v.size() == 7) {
                    for (int i = 5; i < v.elementAt(pos2 + 1).length(); i++) {
                        char c = v.elementAt(pos2 + 1).charAt(i);
                        if (c == ' ') {
                            x = 0;
                        } else {

                            x = (Integer.parseInt(c + ""));
                        }


                        t3[i - 5] = x;
                    }
                }
            }
            if (pos2 == 5) {
                for (int i = 5; i < v.elementAt(pos2).length(); i++) {
                    char c2 = v.elementAt(pos2 - 1).charAt(i);
                    char c = v.elementAt(pos2 - 2).charAt(i);
                    if (c == ' ') {
                        x = 0;
                    } else {
                        x = (Integer.parseInt(c + "")) * 10;
                    }
                    if (c2 == ' ') {
                        x1 = 0;
                    } else {
                        x1 = (Integer.parseInt(c2 + ""));
                    }

                    t2[i - 5] = x + x1;
                }
                if (v.size() == 9) {
                    for (int i = 5; i < v.elementAt(pos2 + 1).length(); i++) {
                        char c = v.elementAt(pos2 + 1).charAt(i);
                        char c2 = v.elementAt(pos2 + 2).charAt(i);
                        if (c == ' ') {
                            x = 0;
                        } else {
                            x = (Integer.parseInt(c + "")) * 10;
                        }
                        if (c2 == ' ') {
                            x1 = 0;
                        } else {
                            x1 = (Integer.parseInt(c2 + ""));
                        }

                        t3[i - 5] = x + x1;
                    }
                }
                if (v.size() == 8) {
                    for (int i = 5; i < v.elementAt(pos2 + 1).length(); i++) {
                        char c = v.elementAt(pos2 + 1).charAt(i);
                        if (c == ' ') {
                            x = 0;
                        } else {

                            x = (Integer.parseInt(c + ""));
                        }


                        t3[i - 5] = x;
                    }
                }
            }


        }

        result += (pique(t1, "seconde") + pique(t2, "minute") + pique(t3, "heure"));
        System.out.println(result);
        return result;

    }
}
