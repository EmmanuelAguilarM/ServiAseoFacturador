/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.LoginDAO;
import Model.EmpleadoDTO;
import Util.Faces;
import Util.Security;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
//import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aguilarje
 */
@Named(value = "loginMB")
@SessionScoped
public class LoginMB extends Faces implements Serializable {
    private String emailEmpleado;
    private String passEmpleado;
    private boolean loggedIn;
    
    LoginDAO loginDAO = new LoginDAO();
    Security security = new Security();
    
    public String login() throws SQLException, ClassNotFoundException, IOException{
        //boolean existe = loginDAO.verificarEmpleadoPorEmail(emailEmpleado);
        EmpleadoDTO empleado = loginDAO.obtenerEmpleadoPorEmail(emailEmpleado);
        if(empleado != null){
            if(security.verificarContrasenia(passEmpleado, empleado.getContrasenia())){
                loggedIn = true;
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
                session.setAttribute("empleado", empleado);
                mensajes(FacesMessage.SEVERITY_INFO, "Acceso a sistema correcto!", "Bienvenido!");
                //facesContext.getExternalContext().redirect("xhtml/home.xhtml");
                return "/xhtml/home?faces-redirect=true";
            } else {
                loggedIn = false;
                mensajes(FacesMessage.SEVERITY_WARN, "Acceso Sistema!", "Credenciales incorrectas");
                return "";
            }
        } else {
            loggedIn = false;
            mensajes(FacesMessage.SEVERITY_WARN, "Acceso Sistema!", "El usuario que intenta ingresar no existe en el sistema");
            return "";
        }
    }
    
     public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true";
    }

    public String getEmailEmpleado() {
        return emailEmpleado;
    }

    public void setEmailEmpleado(String emailEmpleado) {
        this.emailEmpleado = emailEmpleado;
    }

    public String getPassEmpleado() {
        return passEmpleado;
    }

    public void setPassEmpleado(String passEmpleado) {
        this.passEmpleado = passEmpleado;
    }
    
    
}
