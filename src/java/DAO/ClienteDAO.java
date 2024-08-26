/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexiones.Conexion;
import Model.ClienteDTO;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author aguilarje
 */
public class ClienteDAO {
    Conexion connect = new Conexion();
    public boolean verificarClientePorEmail(String email) throws ClassNotFoundException, SQLException {
        boolean existe = false;
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL VerificarClientePorEmail(?, ?)}")) {
            stmt.setString(1, email);
            stmt.registerOutParameter(2, java.sql.Types.BOOLEAN);
            stmt.execute();
            existe = stmt.getBoolean(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public void registrarCliente(ClienteDTO cliente) throws ClassNotFoundException {
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL RegistrarCliente(?, ?, ?, ?, ?)}")) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDireccion());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ClienteDTO obtenerClientePorEmail(String email) throws ClassNotFoundException {
        ClienteDTO cliente = null;
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL ObtenerClientePorEmail(?)}")) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new ClienteDTO(
                            rs.getInt("cliente_id"), 
                            rs.getString("nombre"), 
                            rs.getString("email"), 
                            rs.getString("direccion"), 
                            rs.getString("telefono"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
}
