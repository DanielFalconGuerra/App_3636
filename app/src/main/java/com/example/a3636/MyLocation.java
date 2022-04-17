package com.example.a3636;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MyLocation extends Application {
    private String location="";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String leer(Context context)throws IOException {
        try {

            InputStream archivo = context.getResources().openRawResource(R.raw.locationsave);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(archivo));
            StringBuilder stringBuilder = new StringBuilder();

            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                stringBuilder.append(linea).append("\n");
            }
            archivo.close();
            bufferedReader.close();
            return String.valueOf(stringBuilder);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
