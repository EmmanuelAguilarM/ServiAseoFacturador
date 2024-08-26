/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexiones.Conexion;
import Model.EmpleadoDTO;
import java.sql.SQLException;

/**
 *
 * @author aguilarje
 */
public class LoginDAO extends Conexion{
    
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    
    public boolean verificarEmpleadoPorEmail(String email) throws SQLException, ClassNotFoundException{
        return empleadoDAO.verificarEmpleadoPorEmail(email);
    }
    
    public EmpleadoDTO obtenerEmpleadoPorEmail(String email) throws ClassNotFoundException, SQLException{
        return empleadoDAO.obtenerEmpleadoPorEmail(email);
    }
}
