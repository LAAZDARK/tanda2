package com.laaz.tanda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String telefono="5547113677";
    FloatingActionButton  fabAddgroup;
    private RecyclerView recyclerViewGroup;
    private RecyclerViewAdaptador adaptadorGrupo;
    List<GrupoModelo> grupo;
    //TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mostrarGrupo("http://192.168.1.71/tanda/mostrarGroup.php?telefono="+telefono+"");

        //textView = (TextView) findViewById(R.id.textView8);

        grupo = new ArrayList<>();

        recyclerViewGroup = findViewById(R.id.recyclerViewGroup);
        recyclerViewGroup.setLayoutManager(new LinearLayoutManager(MainActivity.this));



        fabAddgroup = findViewById(R.id.fab);
        fabAddgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityAddGroup.class);
                startActivity(intent);
            }
        });

    }
    private void mostrarGrupo(String URL){
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        //textView.setText(jsonObject.getString("nombreg"));
                        String nombreg = jsonObject.getString("nombreg");
                        String fechaInicio = jsonObject.getString("fechaInicio");
                        String imageView = jsonObject.getString("thumb");

                        GrupoModelo grupoModelo = new GrupoModelo(nombreg, fechaInicio, imageView);
                        grupo.add(grupoModelo);

                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adaptadorGrupo=new RecyclerViewAdaptador(MainActivity.this,grupo);
                recyclerViewGroup.setAdapter(adaptadorGrupo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    /*public List<GrupoModelo> obtenerGrupo(){
        List<GrupoModelo> grupo = new ArrayList<>();
        grupo.add(new GrupoModelo("Tanda de Trabajooo","1996/12/12",R.drawable.moraine));
        grupo.add(new GrupoModelo("Tanda de Casa","2000/12/12",R.drawable.moraine));
        grupo.add(new GrupoModelo("Tanda de Amigos","2020/12/12",R.drawable.moraine));
        return grupo;
    }*/
}
