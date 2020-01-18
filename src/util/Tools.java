/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.Columna;
import models.Tabla;

/**
 *
 * @author Bruno
 */
public class Tools {

    
    public static void escribircontroller(Tabla tabla){
        File fil = new File(Memory.ruta.getAbsolutePath() + "/controller");
        if (!fil.exists()) {
            fil.mkdirs();
        }
        FileWriter fw ;
        try {
            fw = new FileWriter(fil.getAbsolutePath() + "/reg_"+ulFirst(tabla.getNombreAlias())+".php", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            System.out.println("escribiendo");
            out.println(MController.genRegControlles(tabla)); 
            
            System.out.println("termino");
            out.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
        
        try {
            fw = new FileWriter(fil.getAbsolutePath() + "/udp_"+ulFirst(tabla.getNombreAlias())+".php", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            System.out.println("escribiendo");
            out.println(MController.genUpdateControlles(tabla)); 
            
            System.out.println("termino");
            out.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
        try {
            fw = new FileWriter(fil.getAbsolutePath() + "/del_"+ulFirst(tabla.getNombreAlias())+".php", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            System.out.println("escribiendo");
            out.println(MController.genDelControlles(tabla)); 
            
            System.out.println("termino");
            out.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }
    
    public static void ecribirFileClassConexion() {
        File fil = new File(Memory.ruta.getAbsolutePath() + "/models");
        if (!fil.exists()) {
            fil.mkdirs();
        }
        

        try (FileWriter fw = new FileWriter(fil.getAbsolutePath() + "/Conectar.php", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            System.out.println("escribiendo");
            out.println(genTextClassConexion());
            
            System.out.println("termino");
            out.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }
    
    public static void ecribirFileClassModels(Tabla tabla, boolean conex,boolean extra) {
        File fil = new File(Memory.ruta.getAbsolutePath() + "/models");
        if (!fil.exists()) {
            fil.mkdirs();
        }
        

        try (FileWriter fw = new FileWriter(fil.getAbsolutePath() + "/" + tabla.getNombreAlias() + ".php", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            System.out.println("escribiendo");
            out.println("<?php\n\n");
            if (conex) {
                out.println("require_once 'Conectar.php';\n\n");
            }

            out.println("class " + tabla.getNombreAlias() + "\n"
                    + "{");
            out.println();

            out.println(genTextAtribute(conex,tabla.getColumnas()));
                
            out.println();
            out.println(genTextMethodCONSTRUC(conex)); 
            out.println();
            for (Columna co : tabla.getColumnas()) {
                out.println(genTextMethodGET(co.getFieldAlias()));
                out.println();
                out.println(genTextMethodSET(co.getFieldAlias()));
                out.println();
            }
            if (extra) {
                out.println();
                out.println(genTextMethodGENCODIGO(tabla.getNombre(), tabla.getColumnas()));
                out.println();
                out.println(genTextMethodISERT(tabla.getNombre(), tabla.getColumnas()));
                out.println();
                out.println(genTextMethodOBTENRDATO(tabla.getNombre(), tabla.getColumnas()));
                out.println();
                out.println(genTextMethodACTUALIZAR(tabla.getNombre(), tabla.getColumnas()));
                out.println();
                out.println(genTextMethodELIMINAR(tabla.getNombre(), tabla.getColumnas()));
                out.println();
            }
            
            out.println("}");
            out.close();
            bw.close();
            fw.close();
            System.out.println("termino");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }

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
    public static String ulFirst(String str) {
        if (str.isEmpty()) {
            return str;
        } else {
            return Character.toLowerCase(str.charAt(0)) + str.substring(1);
        }
    }

    public static String genTextAtribute(boolean isConexion, ArrayList<Columna> lista) {
        String m = "";
        int count = 0;
        for (Columna columna : lista) {
            m += "    private $" + columna.getFieldAlias() + ";\n";
        }
        if (isConexion) {
            m += "\n    private $c_conectar;\n";
        }
        return m;
    }

    public static int cantPriKey(ArrayList<Columna> lista) {
        int c = 0;
        for (Columna columna : lista) {
            if (columna.getKey().equals("PRI")) {
                c++;
            }
        }
        return c;
    }

    public static String genTextMethodOBTENRDATO(String table, ArrayList<Columna> lista) {
        String m = "    public function obtenerDatos()\n"
                + "    {\n"
                + "        $sql = \"select * from " + table + " \n"
                + "        where ";
        int countPri = cantPriKey(lista);
        String gur = "";
        int cnt = 0;
        ArrayList<String> lisPri = new ArrayList<>();
        for (Columna columna : lista) {

            //m+=columna.getField()+" = '$this->"+columna.getFieldAlias(); 
            gur = "        $this->" + columna.getFieldAlias() + " = $resultado['" + columna.getField() + "'];\n";
            if (columna.getKey().equals("PRI")) {
                lisPri.add(columna.getField() + " = '$this->" + columna.getFieldAlias() + "'");
            }
        }
        int cLP = 0;
        for (String string : lisPri) {
            m += string;
            cLP++;
            if (countPri > 1) {
                if (lisPri.size() > cLP) {
                    m += " AND ";
                }
            }
        }
        m += "\" ;\n        $resultado = $this->c_conectar->get_Row($sql);\n ";

        m += gur + "        }";

        return m;
    }

    public static String genTextMethodELIMINAR(String table, ArrayList<Columna> lista) {
        String m = "    public function eliminar()\n"
                + "    {\n"
                + "        $sql = \"DELETE FROM " + table + "\n"
                + "                WHERE ";

        ArrayList<Columna> listaW = new ArrayList<>();
        for (Columna columna : lista) {
            if (columna.getKey().equals("PRI")) {
                listaW.add(columna);
            }
        }
        String prepW = "";
        int countW = 0;
        for (Columna columna : listaW) {
            prepW += " " + columna.getField() + " = '$this->" + columna.getFieldAlias() + "' ";
            countW++;
            if (listaW.size() > countW) {
                prepW += " AND ";
            }
        }
        m += prepW + " \" ; \n        return $this->c_conectar->ejecutar_idu($sql);\n    }";

        return m;
    }

    public static String genTextMethodACTUALIZAR(String table, ArrayList<Columna> lista) {
        String m = "    public function actualizar()\n"
                + "    {\n"
                + "        $sql = \"UPDATE " + table + "\n"
                + "                SET ";
        String prepb = "";
        String prepW = "";
        ArrayList<Columna> listaB = new ArrayList<>();
        ArrayList<Columna> listaW = new ArrayList<>();
        for (Columna columna : lista) {
            if (columna.getKey().equals("PRI")) {
                listaW.add(columna);
            } else {
                listaB.add(columna);
            }
        }

        int countW = 0;
        for (Columna columna : listaW) {
            prepW += " " + columna.getField() + " = '$this->" + columna.getFieldAlias() + "' ";
            countW++;
            if (listaW.size() > countW) {
                prepW += " AND ";
            }
        }
        int countB = 0;
        for (Columna columna : listaB) {
            prepb = " " + columna.getField() + " = '$this->" + columna.getFieldAlias() + "'";
            countB++;
            if (listaB.size() > countB) {
                prepb += ", ";
            }
        }
        m += prepb + " WHERE " + prepW + "\" ;\n         return $this->c_conectar->ejecutar_idu($sql);\n    }";

        return m;
    }

    public static String genTextMethodGENCODIGO(String table, ArrayList<Columna> lista) {
        Columna columnaPri = new Columna();
        for (Columna columna : lista) {
            if (columna.getKey().equals("PRI")) {
                columnaPri = columna;
                break;
            }
        }
        String m = "    public function generarCodigo()\n"
                + "    {\n"
                + "        $sql = \"select ifnull(max(" + columnaPri.getField() + ") +1, 1) as codigo from " + table + "\";\n"
                + "        $this->" + columnaPri.getFieldAlias() + " = $this->c_conectar->get_valor_query($sql, \"codigo\");\n"
                + "    }";
        return m;
    }

    public static String genTextMethodISERT(String table, ArrayList<Columna> lista) {
        String m = "    public function insertar()\n    {\n";
        String sql = "        $sql = \"insert into " + table + " values (";
        int count = 0;
        for (Columna columna : lista) {
            count++;
            sql += "'$this->" + columna.getFieldAlias() + "'";
            if (lista.size() > 1) {
                if (count < lista.size()) {
                    sql += ", ";
                }
            }
        }
        sql += ")\";\n";
        m += sql;
        m += "        return $this->c_conectar->ejecutar_idu($sql);\n"
                + "    }";
        return m;
    }

    public static String genTextRequires(boolean one, String... name) {
        String m = "";
        for (String string : name) {
            if (one) {
                m += "require_once '" + string + ".php';\n";
            } else {
                m += "require '" + string + ".php';\n";
            }

        }
        return m;
    }

    public static String genTextMethodSET(String name) {
        String m = "    public function set" + ucFirst(name) + "($" + name + ")\n"
                + "    {\n"
                + "        $this->" + name + " = $" + name + ";\n"
                + "    }";

        return m;
    }

    public static String genTextMethodGET(String name) {
        String m = "    public function get" + ucFirst(name) + "()\n"
                + "    {\n"
                + "        return $this->" + name + ";\n"
                + "    }";

        return m;
    }

    public static String genTextMethodCONSTRUC(boolean connex) {
        String m = "    public function __construct()\n"
                + "    {\n"
                + (connex ? "        $this->c_conectar = Conectar::getInstancia();\n" : "")
                + "    }";

        return m;
    }

    public static String genTextClassConexion() {

        return "<?php\n"
                + "\n"
                + "\n"
                + "class Conectar\n"
                + "{\n"
                + "\n"
                + "    private static $_instancia;\n"
                + "    private $_connection;\n"
                + "    private $_host = \"" + Conexion.servidor + "\";\n"
                + "    private $_user = \"" + Conexion.usuario + "\";\n"
                + "    private $_pass = \"" + Conexion.clave + "\";\n"
                + "    // Almacenar una unica instancia\n"
                + "    private $_db = \"" + Conexion.basedatos + "\";\n"
                + "\n"
                + "    /**\n"
                + "     * constructor de la clase Base de datos\n"
                + "     */\n"
                + "    public function __construct()\n"
                + "    {\n"
                + "        $this->iniciarSession();\n"
                + "        //$this->verificarLogeado();\n"
                + "\n"
                + "        $this->_connection = new mysqli($this->_host, $this->_user, $this->_pass, $this->_db);\n"
                + "        // Manejar error en base de datos\n"
                + "        if (mysqli_connect_error()) {\n"
                + "            trigger_error('Falla en la conexion de base de datos' . mysqli_connect_error(), E_USER_ERROR);\n"
                + "        }\n"
                + "        $this->_connection->query(\"SET NAMES 'utf8'\");\n"
                + "    }\n"
                + "\n"
                + "    private function iniciarSession()\n"
                + "    {\n"
                + "        date_default_timezone_set('America/Lima');\n"
                + "\n"
                + "        if (!isset($_SESSION)) {\n"
                + "            session_start();\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    private function verificarLogeado()\n"
                + "    {\n"
                + "        if (!isset($_SESSION)) {\n"
                + "            $this->iniciarSession();\n"
                + "        }\n"
                + "\n"
                + "        if (!isset($_SESSION['id_empresa'])) {\n"
                + "            var_dump($_SESSION);\n"
                + "            //session_destroy();\n"
                + "            //header(\"location: ../login.php\");\n"
                + "        }\n"
                + "\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     *  Funcion que ejecuta el SQL y retorna un ROW\n"
                + "     *        Esta funcion esta pensada para SQLs,\n"
                + "     *        que retornen unicamente UNA sola línea\n"
                + "     */\n"
                + "    public function get_Row($sql)\n"
                + "    {\n"
                + "\n"
                + "        if (!self::es_string($sql))\n"
                + "            exit();\n"
                + "        $db = conectar::getInstancia();\n"
                + "        $mysqli = $db->getConnection();\n"
                + "        $resultado = $mysqli->query($sql);\n"
                + "        if ($row = $resultado->fetch_assoc()) {\n"
                + "            return $row;\n"
                + "        } else {\n"
                + "            return array();\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     *  Metodo que revisa el String SQL\n"
                + "     */\n"
                + "    private function es_string($sql)\n"
                + "    {\n"
                + "        if (!is_string($sql)) {\n"
                + "            trigger_error('class.conectar.inc: $SQL enviado no es un string: ' . $sql);\n"
                + "            return false;\n"
                + "        }\n"
                + "        return true;\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     * getInstancia Metodo para obtener instancia de base de datos.\n"
                + "     */\n"
                + "    public static function getInstancia()\n"
                + "    {\n"
                + "        if (!isset(self::$_instancia)) {\n"
                + "            self::$_instancia = new self;\n"
                + "        }\n"
                + "        return self::$_instancia;\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     * Metodo para obtener la conexion a la base de datos\n"
                + "     */\n"
                + "    public function getConnection()\n"
                + "    {\n"
                + "        return $this->_connection;\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     * Funcion que ejecuta el SQL y retorna un CURSOR\n"
                + "     *        Esta funcion esta pensada para SQLs,\n"
                + "     *        que retornen multiples lineas (1 o varias)\n"
                + "     */\n"
                + "    public function get_Cursor($sql)\n"
                + "    {\n"
                + "        if (!self::es_string($sql))\n"
                + "            exit();\n"
                + "        $db = conectar::getInstancia();\n"
                + "        $mysqli = $db->getConnection();\n"
                + "        $resultado = $mysqli->query($sql);\n"
                + "        return $resultado; // Este resultado se puede usar así:  while ($row = $resultado->fetch_assoc()){...}\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     * @param $sql\n"
                + "     * @return json\n"
                + "     * Funcion que ejecuta el SQL y retorna un jSon\n"
                + "     * data: [{...}] con N cantidad de registros\n"
                + "     */\n"
                + "    public function get_json_rows($sql)\n"
                + "    {\n"
                + "        if (!self::es_string($sql))\n"
                + "            exit();\n"
                + "        $db = conectar::getInstancia();\n"
                + "        $mysqli = $db->getConnection();\n"
                + "        $resultado = $mysqli->query($sql);\n"
                + "        // Si hay un error en el SQL, este es el error de MySQL\n"
                + "        if (!$resultado) {\n"
                + "            return \"class.conectar.class: error \" . $mysqli->error;\n"
                + "        }\n"
                + "\n"
                + "        $i = 0;\n"
                + "        $registros = array();\n"
                + "        while ($row = $resultado->fetch_assoc()) {\n"
                + "            $registros[$i] = $row;\n"
                + "            $i++;\n"
                + "        };\n"
                + "        return json_encode($registros);\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     * @param $sql\n"
                + "     * @return json\n"
                + "     * Funcion que ejecuta el SQL y retorna un jSon\n"
                + "     * de una sola linea. Ideal para imprimir un\n"
                + "     * Query que solo retorne una linea\n"
                + "     */\n"
                + "    public function get_json_row($sql)\n"
                + "    {\n"
                + "        if (!self::es_string($sql))\n"
                + "            exit();\n"
                + "        $db = conectar::getInstancia();\n"
                + "        $mysqli = $db->getConnection();\n"
                + "        $resultado = $mysqli->query($sql);\n"
                + "        // Si hay un error en el SQL, este es el error de MySQL\n"
                + "        if (!$resultado) {\n"
                + "            return \"class.conectar.class: error \" . $mysqli->error;\n"
                + "        }\n"
                + "        if (!$row = $resultado->fetch_assoc()) {\n"
                + "            return \"{}\";\n"
                + "        }\n"
                + "        return json_encode($row);\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     * @param $sql\n"
                + "     * @param $columna\n"
                + "     * @return valor de celda\n"
                + "     * Funcion que ejecuta el SQL y retorna un valor\n"
                + "     * Ideal para count(*), Sum, cosas que retornen una fila y una columna\n"
                + "     */\n"
                + "    public function get_valor_query($sql, $columna)\n"
                + "    {\n"
                + "        if (!self::es_string($sql, $columna))\n"
                + "            exit();\n"
                + "        $db = conectar::getInstancia();\n"
                + "        $mysqli = $db->getConnection();\n"
                + "        $resultado = $mysqli->query($sql);\n"
                + "        // Si hay un error en el SQL, este es el error de MySQL\n"
                + "        if (!$resultado) {\n"
                + "            return \"class.conectar.class: error \" . $mysqli->error;\n"
                + "        }\n"
                + "        $Valor = NULL;\n"
                + "        //Trae el primer valor del arreglo\n"
                + "        if ($row = $resultado->fetch_assoc()) {\n"
                + "            // $Valor = array_values($row)[0];\n"
                + "            $Valor = $row[$columna];\n"
                + "        }\n"
                + "        return $Valor;\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     * @param $sql\n"
                + "     * @return boolean\n"
                + "     * Funcion que ejecuta el SQL de inserción, actualización y eliminación\n"
                + "     */\n"
                + "    public function ejecutar_idu($sql)\n"
                + "    {\n"
                + "        if (!self::es_string($sql))\n"
                + "            exit();\n"
                + "        $db = conectar::getInstancia();\n"
                + "        $mysqli = $db->getConnection();\n"
                + "        if (!$resultado = $mysqli->query($sql)) {\n"
                + "            die(\"class.conectar.class: error \" . $mysqli->error . \" con \" . $sql . \"<br>\");\n"
                + "            return false;\n"
                + "        } else {\n"
                + "            return $resultado;\n"
                + "        }\n"
                + "\n"
                + "        return $resultado;\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     * @param $aEncryptar\n"
                + "     * @param int $digito\n"
                + "     * @return string\n"
                + "     * Funciones para encryptar y desencryptar data:\n"
                + "     * crypt_blowfish_bydinvaders\n"
                + "     */\n"
                + "    function crypt($aEncryptar, $digito = 7)\n"
                + "    {\n"
                + "        $set_salt = './1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';\n"
                + "        $salt = sprintf('$2a$%02d$', $digito);\n"
                + "        for ($i = 0; $i < 22; $i++) {\n"
                + "            $salt .= $set_salt[mt_rand(0, 22)];\n"
                + "        }\n"
                + "        return crypt($aEncryptar, $salt);\n"
                + "    }\n"
                + "\n"
                + "    function uncrypt($Evaluar, $Contra)\n"
                + "    {\n"
                + "        if (crypt($Evaluar, $Contra) == $Contra)\n"
                + "            return true;\n"
                + "        else\n"
                + "            return false;\n"
                + "    }\n"
                + "\n"
                + "    /**\n"
                + "     * Metodo vacio __close para evitar duplicacion\n"
                + "     */\n"
                + "    private function __close()\n"
                + "    {\n"
                + "\n"
                + "    }\n"
                + "\n"
                + "}";
    }

}
