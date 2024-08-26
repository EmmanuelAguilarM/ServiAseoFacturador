/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexiones.Conexion;
import Model.EmpleadoDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aguilarje
 */
public class EmpleadoDAO{
    
    private static final Logger logger = Logger.getLogger("Model");
    Conexion conn = new Conexion();
    public boolean verificarEmpleadoPorEmail(String email) throws SQLException, ClassNotFoundException{
        boolean existe = false;
        StringBuilder sql = new StringBuilder();
        sql.append("{CALL VerificarEmpleadoPorEmail(?, ?)}");
        try(Connection con = conn.crearConexion()){
            try(CallableStatement stmt = con.prepareCall(sql.toString())){
                stmt.setString(1, email);
                stmt.registerOutParameter(2, java.sql.Types.BOOLEAN);

                stmt.execute();
                existe = stmt.getBoolean(2);
            } catch (SQLException ex){
                StringBuilder error = new StringBuilder();
                error.append("SQLException: ").append(ex.getMessage());
                logger.log(Level.SEVERE, error.toString());
                existe = false;
            }
        } catch (SQLException ex){
            StringBuilder error = new StringBuilder();
                error.append("SQLException: ").append(ex.getMessage());
                logger.log(Level.SEVERE, error.toString());
                existe = false;
        }
        return existe;
    }
    public EmpleadoDTO obtenerEmpleadoPorEmail(String email) throws ClassNotFoundException, SQLException{
        EmpleadoDTO data = null;
        String sql = "{CALL ObtenerEmpleadoPorEmail(?)}";
        try(Connection con = conn.crearConexion()){
            try(CallableStatement stmt = con.prepareCall(sql)){
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    data = new EmpleadoDTO(
                            rs.getInt("empleado_id"), 
                            rs.getString("nombre"), 
                            rs.getString("email"), 
                            rs.getString("cargo"), 
                            rs.getString("contrasenia"));
                } 
            } catch(SQLException ex){
                StringBuilder error = new StringBuilder();
                error.append("SQLException: ").append(ex.getMessage());
                logger.log(Level.SEVERE, error.toString());
            } catch(Exception e){
                StringBuilder error = new StringBuilder();
                error.append("Exception: ").append(e.getMessage());
                logger.log(Level.SEVERE, error.toString());         
            }
        } catch(SQLException ex){
            StringBuilder error = new StringBuilder();
            error.append("SQLException: ").append(ex.getMessage());
            logger.log(Level.SEVERE, error.toString());
        }catch(Exception e){
            StringBuilder error = new StringBuilder();
            error.append("Exception: ").append(e.getMessage());
            logger.log(Level.SEVERE, error.toString());         
        }
        return data;
    }
}
