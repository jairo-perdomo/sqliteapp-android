package com.example.app01sqlite.transacciones;

public class Transacciones {
    /* Tablas */
    public static final String tablaPersonas = "personas";

    /* Campos */
    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = "edad";
    public static final String correo = "correo";
    public static final String direccion = "direccion";


    /* Tablas - CREATE, DROP */
    public static final String createTablePersonas = "CREATE TABLE personas( id INTEGER PRIMARY KEY AUTOINCREMENT, nombres TEXT, apellidos TEXT, edad INTEGER, "+
            "correo TEXT, direccion TEXT)";

    public static final String dropTable = "DROP Table IF EXISTS"+tablaPersonas;

    /* public static final String createTablePersonas = "CREATE TABLE" + tablaPersonas + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + nombres +
            " TEXT," + apellidos + "TEXT," + edad + " INTEGER, " + correo + "TEXT," + direccion + "TEXT)"; */

    /* Creacion del nombre de la base de datos */
    public static final String nameDataBase = "DBCurso";

}
