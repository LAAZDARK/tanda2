package com.laaz.tanda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ActivityAddGroup extends AppCompatActivity {
    EditText editTextFI,editTextNG,editTextNI,editTextCI; //spinnerInter
    Spinner opciones;
    int dia, mes, anio;
    Button buttonAdd;
    String seleccion, intervalo="1",fechaFin="2020/12/24";
    String telefono="5547113677";
    ImageView cargarImageView;
    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);


        opciones = (Spinner) findViewById(R.id.spinnerInter);
        editTextFI = (EditText) findViewById(R.id.editTextFI);
        editTextNG = (EditText) findViewById(R.id.editTextNG);
        editTextNI = (EditText) findViewById(R.id.editTextNI);
        editTextCI = (EditText) findViewById(R.id.editTextCI);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        cargarImageView = (ImageView) findViewById(R.id.cargarImageView);


        cargarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        //Muestra lista de opciones
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ActivityAddGroup.this,R.array.opciones, android.R.layout.simple_spinner_item);
        opciones.setAdapter(adapter);
        opciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccion = (String) opciones.getAdapter().getItem(position);
                Toast.makeText(ActivityAddGroup.this,""+seleccion, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Muestra Calendario
        editTextFI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final Calendar c= Calendar.getInstance();
                    dia=c.get(Calendar.DAY_OF_MONTH);
                    mes=c.get(Calendar.MONTH);
                    anio=c.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAddGroup.this, new DatePickerDialog.OnDateSetListener(){
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            editTextFI.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                        }
                    }
                    ,anio,mes,dia);
                    datePickerDialog.show();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarservicio("http://192.168.1.71/tanda/registro.php");
                Intent intent = new Intent(ActivityAddGroup.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void ejecutarservicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ActivityAddGroup.this, "Grupo Creado", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivityAddGroup.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                String thumb = getStringImagen(bitmap);
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombreg", editTextNG.getText().toString());
                parametros.put("integrantesg", editTextNI.getText().toString());
                parametros.put("fechaInicio", editTextFI.getText().toString());
                parametros.put("intervalo", intervalo);
                parametros.put("fechaFin", fechaFin);
                parametros.put("monto",editTextCI.getText().toString());
                parametros.put("thumb", thumb);
                //parametros.put("telefono", telefono);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ActivityAddGroup.this);
        requestQueue.add(stringRequest);
    }
        public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                cargarImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
