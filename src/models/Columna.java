/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Bruno
 */
public class Columna { 
    private String field;
    private String fieldAlias;
    private String type;
    private String key;

    public Columna() {
    }

    public Columna(String field, String fieldAlias, String type, String key) {
        this.field = field;
        this.fieldAlias = fieldAlias;
        this.type = type;
        this.key = key;
    }

    public Columna(String field, String type, String key) {
        this.field = field;
        this.type = type;
        this.key = key;
    }

    public String getFieldAlias() {
        return fieldAlias;
    }

    public void setFieldAlias(String fieldAlias) {
        this.fieldAlias = fieldAlias;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Columna{" + "field=" + field + ", fieldAlias=" + fieldAlias + ", type=" + type + ", key=" + key + '}';
    }
    
}
