/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.enterprise.context.SessionScoped;

/**
 *
 * @author rabeb.amouri.stg
 */


import javax.faces.bean.ManagedBean;


/**
 *
 * @author rabeb.amouri.stg
 */
@ManagedBean(name="user")

@SessionScoped
public class user {

    private String login, passwd ;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    /** Creates a new instance of UserBean */
    public user() {
    }
}
