/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author aguilarje
 */
public class EmpleadoDTO {
    int empleadoId;
    String nombre;
    String email;
    String cargo;
    String contrasenia;

    public EmpleadoDTO(int empleadoId, String nombre, String email, String cargo, String contrasenia) {
        this.empleadoId = empleadoId;
        this.nombre = nombre;
        this.email = email;
        this.cargo = cargo;
        this.contrasenia = contrasenia;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
