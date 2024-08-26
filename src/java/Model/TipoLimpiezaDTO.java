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
public class TipoLimpiezaDTO {
    int tipoLimpiezaId;
    String descripcion;
    double precio;

    public TipoLimpiezaDTO(int tipoLimpiezaId, String descripcion, double precio) {
        this.tipoLimpiezaId = tipoLimpiezaId;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getTipoLimpiezaId() {
        return tipoLimpiezaId;
    }

    public void setTipoLimpiezaId(int tipoLimpiezaId) {
        this.tipoLimpiezaId = tipoLimpiezaId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
