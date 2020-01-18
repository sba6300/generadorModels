/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Bruno
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String name="id__productos_salidas";
        
        String []frag=name.split("_");
        String result="";
        int cout=0;
        for (String string : frag) {
            
            if(frag.length==1){
                result+=sinS(string);
            }else if(cout==frag.length-1){
                 result+=ucFirst(sinS(string));
            }else if (cout>0) {
                result+=ucFirst(string);
            }else{
                result+=string;
            }
            cout++;
        }
        System.out.println(result);
        
    }
    public static String sinS(String str){
        String res=str.toLowerCase();
        
        if (res.charAt(res.length()-1)=='s') {
            res= res.substring(0, res.length()-1);
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
    
}
