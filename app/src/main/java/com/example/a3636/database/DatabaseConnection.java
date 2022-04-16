package com.example.a3636.database;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.example.a3636.Login;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class DatabaseConnection {
    final String ip = "192.168.0.34";
    final String user = "daniel";
    final String pss = "1234";
    final String db = "demo";
    public Connection CONN()
    {
        //10.0.2.2
        final String class_jdbc = "com.mysql.jdbc.Driver";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        try {
            Class.forName(class_jdbc);
            String gURL = "jdbc:mysql://";
            String gIP = ip;
            String gPORT = "3306";
            String gDATABASE = db;
            String gUSR = user;
            String gPSW = pss;
            conn = DriverManager.getConnection(gURL + gIP + ":" + gPORT + "/" + gDATABASE, gUSR, gPSW);
        } catch (SQLException se) {
            Log.e("ERROR1", se.getMessage());
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
    public String[] getTypesOfFood(){
        String typesOfFood [] = new String[14];
        int numberTypes = 0;
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            Statement command=conexion.createStatement();
            ResultSet types = command.executeQuery("select * from tipocomida");
            while (types.next()) {
                typesOfFood[numberTypes] = types.getString("Tipocomida");
                numberTypes++;
            }
            conexion.close();
        } catch(SQLException ex){
            typesOfFood[numberTypes] = ex.toString();
        }
        return typesOfFood;
    }

    public String[] getHoraries(){
        String horaries [] = new String[50];
        int numberHours = 0;
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            Statement command=conexion.createStatement();
            ResultSet hours = command.executeQuery("select * from horarios");
            while (hours.next()) {
                horaries[numberHours] = hours.getString("Horario");
                numberHours++;
            }
            conexion.close();
        } catch(SQLException ex){
            horaries[numberHours] = ex.toString();
        }
        return horaries;
    }

    public String[][] getTypesFoodAndIDs(){
        String idFood [][] = new String[14][2];
        int columnNumber = 0;
        int rowsNumber = 0;
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            Statement command=conexion.createStatement();
            ResultSet hours = command.executeQuery("select * from tipocomida");
            while (hours.next()) {
                String idFoodObtained = hours.getString("IDComida");
                String typeFoodObtained = hours.getString("Tipocomida");
                idFood[rowsNumber][columnNumber] = idFoodObtained;
                idFood[rowsNumber][++columnNumber] = typeFoodObtained;
                rowsNumber++;
                columnNumber= 0;
            }
            conexion.close();
        } catch(SQLException ex){
            idFood[0][0] = ex.toString();
        }
        return idFood;
    }
    public String getDayClosedRestaurant(String idRestaurant, String day){
        String IDDay = "";
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            Statement command=conexion.createStatement();
            String queries = "select IDDia" + day + " from horario_restaurante where IDRestaurante = " + idRestaurant;
            ResultSet hours = command.executeQuery(queries);
            while (hours.next()) {
                IDDay = hours.getString("IDDia"+day);
            }
            conexion.close();
        } catch(SQLException ex){
            IDDay = ex.toString();
        }
        return IDDay;
    }

    public String[] getScheduleOfRestaurant(String idRestaurant, String day){
        String schedule[] = new String[2];
        schedule[0] = "";
        schedule[1] = "";
        int rowsNumber = 0;
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            Statement command=conexion.createStatement();
            String queries = "select IDHorario1Dia" + day + ", IDHorario2Dia" + day + " from horario_restaurante where IDRestaurante = " + idRestaurant + " and IDDia" + day + " = " + day;
            ResultSet hours = command.executeQuery(queries);
            while (hours.next()) {
                String idScheduleOne = hours.getString("IDHorario1Dia"+day);
                String idScheduleTwo = hours.getString("IDHorario2Dia"+day);
                schedule[rowsNumber] = idScheduleOne;
                schedule[++rowsNumber] = idScheduleTwo;
            }
            conexion.close();
        } catch(SQLException ex){
            schedule[0] = ex.toString();
        }
        return schedule;
    }

    public String[][] getTypesFoodEachRestaurant(){
        String idRestaurant [][] = new String[3][3];
        int columnNumber = 0;
        int rowsNumber = 0;
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            Statement command=conexion.createStatement();
            ResultSet hours = command.executeQuery("select * from tiporestur");
            while (hours.next()) {
                String typeFoodReceived_1 = hours.getString("IDTipoComida1");
                String typeFoodReceived_2 = hours.getString("IDTipoComida2");
                String typeFoodReceived_3 = hours.getString("IDTipoComida3");
                idRestaurant[rowsNumber][columnNumber] = typeFoodReceived_1;
                idRestaurant[rowsNumber][++columnNumber] = typeFoodReceived_2;
                idRestaurant[rowsNumber][++columnNumber] = typeFoodReceived_3;
                rowsNumber++;
                columnNumber= 0;
            }
            conexion.close();
        } catch(SQLException ex){
            idRestaurant[0][0] = ex.toString();
        }
        return idRestaurant;
    }

    public String getIDRestaurant(String IDTipoRe){
        String idRestaurant = "";
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            Statement command=conexion.createStatement();
            ResultSet hours = command.executeQuery("select IDRestaurante from tiporestur where IDTipoRe = " + IDTipoRe);
            while (hours.next()) {
                idRestaurant = hours.getString("IDRestaurante");

            }
            conexion.close();
        } catch(SQLException ex){
            return ex.toString();
        }
        return idRestaurant;
    }

    public String getSchedule(String scheduleReceived){
        String scheduleToSend = "";
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            Statement command=conexion.createStatement();
            ResultSet hours = command.executeQuery("select Horario from horarios where IDHorario = " + scheduleReceived);
            while (hours.next()) {
                scheduleToSend = hours.getString("Horario");
            }
            conexion.close();
        } catch(SQLException ex){
            return ex.toString();
        }
        return scheduleToSend;
    }

    public String loginCheck(String userToTest, String password){
        String result = "";
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select Correo from usuarios where Usuario=? and Password=?");
            ps.setString(1, userToTest);
            ps.setString(2, password);
            ResultSet sr = ps.executeQuery();
            if(sr.next()){
                result = sr.getString(1);
                if(!result.equals("")){
                    result = "OK";
                }
            }
            conexion.close();
        } catch(SQLException ex){
            return ex.toString();
        }
        return result;
    }
}
