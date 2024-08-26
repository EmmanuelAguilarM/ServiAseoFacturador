/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Util.Json;
import Conexiones.Conexion;
import Model.DetalleFacturaDTO;
import Model.FacturaDTO;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author aguilarje
 */
public class FacturaDAO {
    Conexion connect = new Conexion();
    Json jsonD = new Json();
    public int crearFactura(FacturaDTO factura, List<DetalleFacturaDTO> detalleFactura) throws ClassNotFoundException {
        int facturaId = -1;
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL CrearFacturaConDetalles(?, ?, ?, ?, ?)}")) {
            stmt.setDate(1, new java.sql.Date(factura.getFechaEmision().getTime()));
            stmt.setInt(2, factura.getClienteId());
            stmt.setInt(3, factura.getEmpleadoId());            
            stmt.setDouble(4, factura.getTotal());
            stmt.setString(5, jsonD.convertirListaDetalleFacturaAJson(detalleFactura));
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                facturaId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturaId;
    }

    public List<FacturaDTO> obtenerFacturas() throws ClassNotFoundException {
        List<FacturaDTO> facturas = new ArrayList<>();
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL ListarFacturas()}");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                FacturaDTO factura = new FacturaDTO(rs.getInt("factura_id"), rs.getInt("cliente_id"), rs.getInt("empleado_id"), rs.getDate("fecha_emision"), rs.getDouble("total"));
                facturas.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturas;
    }

    public FacturaDTO obtenerFacturaPorId(int id) throws ClassNotFoundException {
        FacturaDTO factura = null;
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL ObtenerDetallesFactura(?)}")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    factura = new FacturaDTO(rs.getInt("factura_id"), rs.getInt("cliente_id"), rs.getInt("empleado_id"), rs.getDate("fecha_emision"), rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factura;
    }
    
    public void validarFactura(int facturaId) throws ClassNotFoundException {
    try (Connection conn = connect.crearConexion();
         CallableStatement stmt = conn.prepareCall("{CALL ValidarFactura(?)}")) {
        stmt.setInt(1, facturaId);
        stmt.execute();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
