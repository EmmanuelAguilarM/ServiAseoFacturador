/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author aguilarje
 */
public class Security {
    public static String hashPassword(String password) {
        try {
            // Crea una instancia de MessageDigest para MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Convierte la contraseña en bytes y actualiza el digest
            md.update(password.getBytes());

            // Completa el hash
            byte[] digest = md.digest();

            // Convierte el array de bytes a una cadena hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean verificarContrasenia(String inputPassword, String storedHash) {
        // Genera el hash MD5 de la contraseña proporcionada por el usuario
        String hashedInput = hashPassword(inputPassword);

        // Compara el hash generado con el hash almacenado
        return hashedInput.equals(storedHash);
    }
}
