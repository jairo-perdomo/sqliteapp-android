package com.example.app01sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app01sqlite.tablas.Personas;
import com.example.app01sqlite.transacciones.Transacciones;

import java.util.ArrayList;

public class ActivityCombo extends AppCompatActivity {

    SQLiteConexion conexion;
    Spinner comboPersonas;
    EditText txtnombre, txtapellidos, txtcorreo;
    ArrayList<String> listaPersonas;
    ArrayList<Personas> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);

        conexion = new SQLiteConexion(this, Transacciones.nameDataBase, null, 1);
        comboPersonas = (Spinner) findViewById(R.id.comboPersona);
        txtnombre = (EditText) findViewById(R.id.txtnombres);
        txtapellidos = (EditText) findViewById(R.id.txtapellidos);
        txtcorreo = (EditText) findViewById(R.id.txtcorreo);

        obtenerListaPersonas();

        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaPersonas);
        comboPersonas.setAdapter(arrayAdapter);

        comboPersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtnombre.setText(lista.get(position).getNombres());
                txtapellidos.setText(lista.get(position).getApellidos());
                txtcorreo.setText(lista.get(position).getCorreo());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void obtenerListaPersonas(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas listaPersonas;
        lista = new ArrayList<>();

        Cursor cursorConsulta = db.rawQuery("SELECT * FROM " + Transacciones.tablaPersonas, null);

        while (cursorConsulta.moveToNext()){
            listaPersonas = new Personas();
            listaPersonas.setId(cursorConsulta.getInt(0));
            listaPersonas.setNombres(cursorConsulta.getString(1));
            listaPersonas.setApellidos(cursorConsulta.getString(2));
            listaPersonas.setEdad(cursorConsulta.getInt(3));
            listaPersonas.setCorreo(cursorConsulta.getString(4));
            listaPersonas.setDireccion(cursorConsulta.getString(5));

            lista.add(listaPersonas);
        }

        cursorConsulta.close();
        llenarCombo();

    }

    private void llenarCombo() {
        listaPersonas = new ArrayList<String>();
        for(int i = 0; i< lista.size(); i++){
            listaPersonas.add(lista.get(i).getId() + " | "
            +lista.get(i).getNombres() + " | "
            +lista.get(i).getApellidos());
        }
    }
}