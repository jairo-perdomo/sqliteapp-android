package com.example.app01sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app01sqlite.transacciones.Transacciones;

public class ActivityQuery extends AppCompatActivity {
    ActivityIngresar activityIngresar;
    SQLiteConexion conexion;
    EditText id, nombres, apellidos, edad, correo, direccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        /* Llamar a la conexion de BD SQLite */
        conexion = new SQLiteConexion(this, Transacciones.nameDataBase, null, 1);

        Button btnConsulta = (Button) findViewById(R.id.btnBuscar);
        Button btnEliminar = (Button) findViewById(R.id.btnEliminar);
        Button btnActualizar = (Button) findViewById(R.id.btnActualizar);

        id = (EditText) findViewById(R.id.txtId);
        nombres = (EditText) findViewById(R.id.txtNombres);
        apellidos = (EditText) findViewById(R.id.txtApellidos);
        edad = (EditText) findViewById(R.id.txtEdad);
        correo = (EditText) findViewById(R.id.txtCorreo);
        direccion = (EditText) findViewById(R.id.txtDireccion);

        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buscar();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actualizar();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eliminar();
            }
        });
    }

    private void Eliminar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] params = {id.getText().toString()};

        db.delete(Transacciones.tablaPersonas, Transacciones.id + "=?", params);
        Toast.makeText(getApplicationContext(), "Registro Eliminado", Toast.LENGTH_LONG).show();
        clearScreen();
    }

    private void Actualizar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] params = {id.getText().toString()};

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, nombres.getText().toString());
        valores.put(Transacciones.apellidos, apellidos.getText().toString());
        valores.put(Transacciones.edad, edad.getText().toString());
        valores.put(Transacciones.correo, correo.getText().toString());
        valores.put(Transacciones.direccion, direccion.getText().toString());

        db.update(Transacciones.tablaPersonas, valores, Transacciones.id + "=?", params);
        Toast.makeText(getApplicationContext(), "Registro actualizado", Toast.LENGTH_LONG).show();
        clearScreen();

    }

    private void Buscar(){
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] params = {id.getText().toString()};
        String[] fields = {Transacciones.nombres,
                           Transacciones.apellidos,
                           Transacciones.edad,
                           Transacciones.correo,
                           Transacciones.direccion};

        String whereCondition = Transacciones.id + "=?";

        try {
            Cursor cursorForData = db.query(Transacciones.tablaPersonas, fields, whereCondition, params, null, null, null);
            cursorForData.moveToFirst();

            nombres.setText(cursorForData.getString(0));
            apellidos.setText(cursorForData.getString(1));
            edad.setText(cursorForData.getString(2));
            correo.setText(cursorForData.getString(3));
            direccion.setText(cursorForData.getString(4));

            Toast.makeText(getApplicationContext(), "Consultado con exito", Toast.LENGTH_LONG).show();

        } catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Elemento no encontrado", Toast.LENGTH_LONG).show();
            clearScreen();
        }

    }

    private void clearScreen() {
        nombres.setText("");
        apellidos.setText("");
        edad.setText("");
        correo.setText("");
        direccion.setText("");
    }
}