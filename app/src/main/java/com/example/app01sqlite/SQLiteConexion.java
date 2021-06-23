package com.example.app01sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.app01sqlite.transacciones.Transacciones; /* Importacion de la clase que tiene las sentencias SQL */
public class SQLiteConexion extends SQLiteOpenHelper {

    public SQLiteConexion(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int version){
        super(context, dbname, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* Crear la tabla usando el método que está en la clase Transacciones */
        db.execSQL(Transacciones.createTablePersonas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Transacciones.dropTable); /* Borrar la base de datos */
        onCreate(db); /* Crear nuevamente la base de datos */
    }
}
