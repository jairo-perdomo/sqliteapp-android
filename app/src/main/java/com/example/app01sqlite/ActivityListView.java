package com.example.app01sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app01sqlite.tablas.Personas;
import com.example.app01sqlite.transacciones.Transacciones;

import java.util.ArrayList;

public class ActivityListView extends AppCompatActivity {
    /* Variables globales de la Actividad */
    SQLiteConexion conexion;
    ListView vistaListaPersonas;
    ArrayList<Personas> lista;
    ArrayList<String> arregloPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        conexion = new SQLiteConexion(this, Transacciones.nameDataBase, null, 1);
        vistaListaPersonas = (ListView) findViewById(R.id.listaPersonas);

        obtenerListaPersonas();

        ArrayAdapter adaptadorArreglo = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloPersonas);
        vistaListaPersonas.setAdapter(adaptadorArreglo);

        vistaListaPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String informacion = "ID" + lista.get(position).getId() +"\n";
                informacion += "Nombre" + lista.get(position).getNombres();
                Toast.makeText(getApplicationContext(), informacion, Toast.LENGTH_LONG).show();

                Intent compartir = new Intent();
                compartir.setAction(Intent.ACTION_SEND);
                compartir.putExtra(Intent.EXTRA_TEXT, informacion);
                compartir.setType("text/plain");

                Intent share = Intent.createChooser(compartir, null);
                startActivity(share);
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
        fillList();

    }

    private void fillList() {
        arregloPersonas = new ArrayList<>();
        for(int i = 0; i < lista.size(); i++){
            arregloPersonas.add(lista.get(i).getId()+" | "
                    +lista.get(i).getNombres() + " | "
                    +lista.get(i).getApellidos());
        }
    }
}