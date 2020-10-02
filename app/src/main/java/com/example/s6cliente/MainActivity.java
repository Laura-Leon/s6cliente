package com.example.s6cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.s6cliente.model.Usuario;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.UUID;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private BufferedWriter writer;
    private Button bIngresar;
    private EditText euser;
    private EditText econtra;
    private BufferedReader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //referenciar
        euser = findViewById(R.id.euser);
        econtra = findViewById(R.id.econtra);
        bIngresar = findViewById(R.id.bIngresar);
        bIngresar.setOnClickListener(this);

        new Thread(
                ()->{
                    try{
                        Socket socket = new Socket("192.168.0.6",5000);

                        InputStream id = socket.getInputStream();
                        OutputStream out = socket.getOutputStream();

                         reader = new BufferedReader(new InputStreamReader(id));
                        writer = new BufferedWriter( new OutputStreamWriter(out));

                        while(true) {

                            String line = reader.readLine();

                            runOnUiThread(
                                    ()-> {
                                        Log.e("aaa", ""+line);
                                        if (line.contains("ya esta")){
                                            Intent i = new Intent(this,IngresoActivity.class);
                                            startActivity(i);
                                        }

                                    }
                            );




                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        ).start();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bIngresar:
                Gson gson = new Gson();

                String id = UUID.randomUUID().toString();
                String username = euser.getText().toString();
                String contraseña = econtra.getText().toString();
                Usuario obj = new Usuario(id,username,contraseña);
                String json = gson.toJson(obj);
                Log.e(">>>",""+json);
              sendMessage(json);

                break;
        }

    }
    public void sendMessage(String msg){

        new Thread(
                ()-> {
                    try {
                        writer.write(msg+"\n");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
}