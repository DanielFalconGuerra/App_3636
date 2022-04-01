package com.example.a3636.database;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    public Connection CONN()
    {
        final String class_jdbc = "com.mysql.jdbc.Driver";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        try {
            Class.forName(class_jdbc);
            String gURL = "jdbc:mysql://";
            String gIP = "10.0.2.2";
            String gPORT = "3306";
            String gDATABASE = "demo";
            String gUSR = "root";
            String gPSW = "";
            conn = DriverManager.getConnection(gURL + gIP + ":" + gPORT + "/" + gDATABASE, gUSR, gPSW);
        } catch (SQLException se) {
            Log.e("ERROR1", se.getMessage());
            //Toast.makeText(this,se.getMessage(), Toast.LENGTH_LONG).show();
        } catch (ClassNotFoundException e) {
            Log.e("ERROR2", e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR3", e.getMessage());
        }
        return conn;
    }

    private String Query_Version()
    {
        String response = "";

        try {
            Connection ConnexionMySQL = CONN();
            Statement st = ConnexionMySQL.createStatement();

            ResultSet rs = st.executeQuery("SELECT VERSION()");
            //ResultSet rs = st.executeQuery("SHOW VARIABLES LIKE \"%version%\"");

            while (rs.next()) {
                response += rs.getString(1) + "\r\n";
            }
            rs.close();
            ConnexionMySQL.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return response;
    }

    public void DoOnThread()
    {
        Thread thread = new Thread() {
            @Override
            public void run()
            {
                String mResult = Query_Version();
                Log.e("MYSQL Version", mResult);
            }
        };
        thread.start();
    }

    public String MortarRestaurants(){
        //Toast.makeText(this, "Texto de prueba", Toast.LENGTH_SHORT).show();
        String datos = "";
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://10.0.2.2/demo","root" ,"");
            Statement comando=conexion.createStatement();
            ResultSet registro = comando.executeQuery("select * from restaurante where IDRestaurante = 2");
            if (registro.next()) {
                datos = registro.getString("Restaurante");
                //Toast.makeText(this,datos,Toast.LENGTH_LONG).show();
                //tv1.setText(datos);
                //tf2.setText(registro.getString("precio"));

            } else {
                //Toast.makeText(this,registro.getString("Error"),Toast.LENGTH_LONG).show();
                datos = "Error de Conexión";
            }
            conexion.close();
        } catch(SQLException ex){
            //setTitle(ex.toString());
            datos = ex.toString();
        }
        return datos;
    }
}
