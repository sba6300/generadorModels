/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.DriverManager;

public class Conexion {

    public static String servidor;
    public static  String basedatos;
    public static String usuario;
    public static String clave;
    public static String puerto;
    Tools tools=new Tools();
    public Conexion() {
        
    }

    public  boolean Conectar() {
        final String url = "jdbc:mysql://" + this.servidor + ":" + this.puerto + "/" + this.basedatos;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Memory.connection = DriverManager.getConnection(url, this.usuario, this.clave);
             
            return true;
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
           
        }
        return false;
    }
}
