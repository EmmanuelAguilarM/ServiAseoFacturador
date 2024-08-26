/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexiones.Conexion;
import Model.TipoLimpiezaDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aguilarje
 */
public class TipoLimpiezaDAO {
    Conexion connect = new Conexion();
    public List<TipoLimpiezaDTO> obtenerTiposDeLimpieza() throws ClassNotFoundException {
        List<TipoLimpiezaDTO> tiposLimpieza = new ArrayList<>();
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL ObtenerTiposLimpieza()}");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TipoLimpiezaDTO tipoLimpieza = new TipoLimpiezaDTO(
                        rs.getInt("tipo_limpieza_id"), 
                        rs.getString("descripcion"), 
                        rs.getDouble("precio"));
                tiposLimpieza.add(tipoLimpieza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiposLimpieza;
    }

    public void agregarTipoDeLimpieza(TipoLimpiezaDTO tipoLimpieza) throws ClassNotFoundException {
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL AgregarTipoLimpieza(?, ?)}")) {
            stmt.setString(1, tipoLimpieza.getDescripcion());
            stmt.setDouble(2, tipoLimpieza.getPrecio());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarTipoDeLimpieza(int id) throws ClassNotFoundException {
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL EliminarTipoLimpieza(?)}")) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
