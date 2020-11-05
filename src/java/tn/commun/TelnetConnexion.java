package tn.commun;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import java.net.SocketException;
import java.util.Date;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.telnet.TelnetClient;

public class TelnetConnexion {

    private TelnetClient telnet = new TelnetClient();
    private InputStream in;
    private PrintStream out;
    private char prompt = '#';
    String sortie = "";
    FileOutputStream fout = null;

    public TelnetConnexion(String adresse, String user, String password, String url) {
        try {
            // se connecter a l'adresse ip spécifiée
            telnet.connect(adresse, 23);

            // Get input and output stream references
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());

            // authentification
            readUntil("Username: ");
            write(user);
            readUntil("Password: ");
            write(password);

            Date maDate = new Date();


        PrintStream out1 =new PrintStream(new FileOutputStream(url));
            String Newligne = System.getProperty("line.separator");
            out1.append(Newligne + "********************Date******************* " + maDate.toString());


            System.setOut(out1);

            // avancer jusqu'au prompt
            readUntil(prompt + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            @SuppressWarnings("unused")
            boolean found = false;
            char ch = (char) in.read();

            while (true) {

                System.out.print(ch);
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String value) {
        try {
            out.println(value);
            out.flush();
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(String command) {
        try {

            write(command);
            readUntil(prompt + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}
