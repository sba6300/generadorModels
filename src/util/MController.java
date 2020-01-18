/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import models.Columna;
import models.Tabla;

/**
 *
 * @author Bruno
 */
public class MController {
    
    public static String genDelControlles(Tabla tabla){
        String mo="<?php\n\n   "+
                Tools.genTextRequires(false, "../models/"+tabla.getNombreAlias());
        mo+="  $"+Tools.ulFirst(tabla.getNombreAlias())+"=new "+tabla.getNombreAlias()+"();\n\n";
        
        String delT="";
        ArrayList<Columna> listaC=new ArrayList<>();
        for (Columna columna : tabla.getColumnas()) {
            if (columna.getKey().equals("PRI")) {
                listaC.add(columna);
            }
        }
        for (Columna columna : listaC) {
            delT+="$"+Tools.ulFirst(tabla.getNombreAlias())+"->set"+Tools.ucFirst(columna.getFieldAlias())+"(filter_input(INPUT_GET, 'input_"+columna.getFieldAlias()+"'));\n";
        }
        mo+="\n\n" +delT+"\n\n\n $"+Tools.ulFirst(tabla.getNombreAlias())+"->eliminar();";
        return mo;
    }
    
    public static String genRegControlles(Tabla tabla){
        String mo="<?php\n\n   "+
                Tools.genTextRequires(false, "../models/"+tabla.getNombreAlias());
        mo+="  $"+Tools.ulFirst(tabla.getNombreAlias())+"=new "+tabla.getNombreAlias()+"();\n\n";
        
        String delT="";
        for (Columna columna : tabla.getColumnas()) {
            delT+="$"+Tools.ulFirst(tabla.getNombreAlias())+"->set"+Tools.ucFirst(columna.getFieldAlias())+"(filter_input(INPUT_POST, 'input_"+columna.getFieldAlias()+"'));\n";
        }
        mo+="\n\n" +delT+"\n\n\n $"+Tools.ulFirst(tabla.getNombreAlias())+"->insertar();";
                
        return mo;
    }
    public static String genUpdateControlles(Tabla tabla){
        String mo="<?php\n\n   "+
                Tools.genTextRequires(false, "../models/"+tabla.getNombreAlias());
        mo+="  $"+Tools.ulFirst(tabla.getNombreAlias())+"=new "+tabla.getNombreAlias()+"();\n\n";
        
        String delT="";
        for (Columna columna : tabla.getColumnas()) {
            delT+="$"+Tools.ulFirst(tabla.getNombreAlias())+"->set"+Tools.ucFirst(columna.getFieldAlias())+"(filter_input(INPUT_POST, 'input_"+columna.getFieldAlias()+"'));\n";
        }
        mo+="\n\n" +delT+"\n\n\n $"+Tools.ulFirst(tabla.getNombreAlias())+"->actualizar();";
                
        return mo;
    }
}
