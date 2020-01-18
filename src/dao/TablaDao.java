/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Columna;
import models.Tabla;
import util.Conexion;
import util.Memory;


public class TablaDao {
    
    public ArrayList<Columna> getListColumn(String tabla){
        ArrayList<Columna> lis=new ArrayList<>();
        String query = "SHOW COLUMNS FROM "+Conexion.basedatos+"."+tabla+";";

        try {
            Statement st = Memory.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                lis.add(new Columna(rs.getString("Field"), rs.getString("Type"), rs.getString("Key")));
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return lis;
    }
    public ArrayList<Tabla> getListTablas(){
        ArrayList<Tabla> lis=new ArrayList<>();
        String query = "SHOW FULL TABLES FROM "+Conexion.basedatos ;
        System.out.println(query);
        try {
            Statement st = Memory.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                
                if (rs.getString(2).equals("BASE TABLE")) {
                    lis.add(new Tabla(rs.getString(1))); 
                }
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return lis;
    }
    
}
