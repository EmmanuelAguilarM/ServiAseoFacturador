/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexiones;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author aguilarje
 */
public class Conexion {
    private static final Logger logger = Logger.getLogger("Conexiones");
    public Connection connection = null;
    public Connection crearConexion() throws ClassNotFoundException{
        Connection conn = null;
        StringBuilder bd = new StringBuilder();
        bd.append("jdbc:mysql://localhost:3306/serviaseodb?useSSL=false"); // URL
        String usuario = "root"; // Usuario
        String password = ""; // Password
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(bd.toString(), usuario, password);
        } catch (SQLException ex){
            StringBuilder error = new StringBuilder();
            error.append("SQLException: ").append(ex.getMessage());
            logger.log(Level.SEVERE, error.toString());
            error.delete(0, error.length());
            error.append("SQLState: ").append(ex.getSQLState());
            logger.log(Level.SEVERE, error.toString());
            error.delete(0, error.length());
            error.append("VendorError: ").append(ex.getErrorCode());
            logger.log(Level.SEVERE, error.toString());
        }
        return conn;
    }
    
    public void cerrarConexion(){
        try{
            if(connection != null){
                if(!connection.isClosed()){
                    connection.close();
                }
            }
        } catch (SQLException ex){
            StringBuilder error = new StringBuilder();
            error.append("SQLException: ").append(ex.getMessage());
            logger.log(Level.SEVERE, error.toString());
        }
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if(connection == null || connection.isClosed()){
            connection = crearConexion();
        }
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
}
