/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author aguilarje
 */

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Faces {
    public void mensajes(FacesMessage.Severity severity, String titulo, String mensaje) {
        FacesMessage msg = new FacesMessage(severity, titulo, mensaje);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
