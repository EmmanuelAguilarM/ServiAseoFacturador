/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexiones.Conexion;
import Model.ProductoDTO;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author aguilarje
 */
public class ProductoDAO {
    Conexion connect = new Conexion();
    public List<ProductoDTO> obtenerProductos() throws ClassNotFoundException {
        List<ProductoDTO> productos = new ArrayList<>();
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL obtenerProductos()}");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProductoDTO producto = new ProductoDTO(rs.getInt("producto_id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDouble("precio"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public void agregarProducto(ProductoDTO producto) throws ClassNotFoundException {
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL agregarProducto(?, ?, ?)}")) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarProducto(int id) throws ClassNotFoundException {
        try (Connection conn = connect.crearConexion();
             CallableStatement stmt = conn.prepareCall("{CALL eliminar_producto(?)}")) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
