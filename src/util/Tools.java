/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import models.Columna;

/**
 *
 * @author Bruno
 */
public class Tools {

    public static String formatText(String name) {
        String[] frag = name.split("_");
        String result = "";
        int cout = 0;
        for (String string : frag) {

            if (frag.length == 1) {
                result += sinS(string);
            } else if (cout == frag.length - 1) {
                result += ucFirst(sinS(string));
            } else if (cout > 0) {
                result += ucFirst(string);
            } else {
                result += string;
            }
            cout++;
        }
        return result;
    }

    public static String sinS(String str) {
        String res = str.toLowerCase();

        if (res.charAt(res.length() - 1) == 's') {
            res = res.substring(0, res.length() - 1);
        }
        return res;
    }

    public static String ucFirst(String str) {
        if (str.isEmpty()) {
            return str;
        } else {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
    }
    
    public static String genTextAtribute(boolean isConexion,ArrayList<Columna> lista){
        String m="" ;
        int count=0;
        for (Columna columna : lista) {
            m+="private $"+columna.getFieldAlias()+";\n";
        }
        if (isConexion) {
            m+="private $c_conectar;\n";
        }
        return m;
    }
    public static int cantPriKey(ArrayList<Columna> lista){
        int c=0;
        for (Columna columna : lista) {
            if (columna.getKey().equals("PRI")) { 
                c++;
            }
        }
        return c;
    }
    public static String genTextMethodOBTENRDATO(String table,ArrayList<Columna> lista){
        String m="    public function obtenerDatos()\n" +
                "    {\n" +
                "        $sql = \"select * from "+table+" \n" +
                "        where ";
        int countPri=cantPriKey(lista);
        String gur="";
        int cnt=0;
        ArrayList<String> lisPri=new ArrayList<>();
        for (Columna columna : lista) {
            
            //m+=columna.getField()+" = '$this->"+columna.getFieldAlias(); 
            gur="\"        $this->"+columna.getFieldAlias()+" = $resultado['"+columna.getField()+"'];\n\"";
            if (columna.getKey().equals("PRI")) {
               lisPri.add(columna.getField()+" = '$this->"+columna.getFieldAlias()+"'\n");
            }
        }
        int cLP=0;
        for (String string : lisPri) {
            m+=string; 
            cLP++;
            if (countPri>1) {
                if (lisPri.size()>cLP) {
                    m+= " AND "; 
                }
            }
        }
        m+="\";\n        $resultado = $this->c_conectar->get_Row($sql);\\n";
        
        m+=gur+"}";
        
              
        return m;
    }
    
    public static String genTextMethodISERT(String table,ArrayList<Columna> lista){
        String m="    public function insertar()\n    {\n" ;
        String sql="        $sql = \"insert into "+table+" values (";
        int count=0;
        for (Columna columna : lista) {
            count++;
            sql+="'$this->"+columna.getFieldAlias()+"'";
            if (lista.size()>1) {
                if (count<lista.size()) {
                    sql+=", ";
                }
            }
        }
        sql+=")\";\n";
        m+=sql;
        m+="        return $this->c_conectar->ejecutar_idu($sql);\n" +
        "    }"; 
        return m;
    }
    
    
    public static String genTextMethodSET(String name){
        String m="    public function set"+ucFirst(name)+"($"+name+")\n" +
            "    {\n" +
            "        $this->"+name+" = $"+name+";\n" +
            "    }";
        
        return m;
    }
    public static String genTextMethodGET(String name){
        String m="    public function get"+ucFirst(name)+"()\n" + 
            "    {\n" +
            "        return $this->"+name+";\n" +
            "    }";
        
        return m;
    }
}
