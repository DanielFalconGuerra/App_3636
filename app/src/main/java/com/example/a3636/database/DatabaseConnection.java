package com.example.a3636.database;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//10.0.2.2

public class DatabaseConnection {
    private final String ip = "192.168.0.34";
    private final String user = "daniel";
    private final String pss = "1234";
    private final String db = "demo2";
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
            String queries = "select IDHorario1Dia" + day + ", IDHorario2Dia" + day + " from horario_restaurante " +
                    "                       where IDRestaurante = " + idRestaurant + " and IDDia" + day + " = " + day;
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

    public String[] loginCheck(String userToTest){
        String hash = "";
        String IDType = "";
        String[] userData = new String[2];
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select PasswordUser, IDTipo from usuarios where Usuario=?");
            ps.setString(1, userToTest);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                hash = rs.getString(1);
                IDType = rs.getString(2);
                if(!hash.equals("")){
                    userData[0] = hash;
                    userData[1] = IDType;
                    return userData;
                }
            }
            conexion.close();
        } catch(SQLException ex){
            userData[0] = ex.toString();
            return userData;
        }
        return userData;
    }
    //Regitrar usuario: INSERT INTO usuarios (IDUsuario, PasswordUser, Usuario, Correo, Nombre, IDTipo) VALUES ('7', '$2a$10$tcvz8N8906cbkUWxTsw/wu/FYHeVR7sjduLAxWkmj0U1vZ8l2aoj6', 'Daniel', 'daniel@gmail.com', 'Daniel Falcon', '1');

    public boolean checkIfTheUserAlreadyExists(String userToTest){
        boolean existingUser = false;
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select Usuario from usuarios where Usuario=?");
            ps.setString(1, userToTest);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String receivedUser = rs.getString(1);
                if(!receivedUser.equals("")){
                    existingUser = true;
                    return existingUser;
                }
            }
            conexion.close();
        } catch(SQLException ex){
            return existingUser;
        }
        return existingUser;
    }

    public String getLastIDUser(){
        String lastIDUser = "";
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select max(IDUsuario) from usuarios");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                lastIDUser = rs.getString(1);
                if(!lastIDUser.equals("")){
                    return lastIDUser;
                }
            }
            conexion.close();
        } catch(SQLException ex){
            lastIDUser = ex.getMessage();
            return lastIDUser;
        }
        return lastIDUser;
    }

    public String getIDUser(String userName){
        String IDUser = "";
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select IDUsuario from usuarios where Usuario=?");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                IDUser = rs.getString(1);
                if(!IDUser.equals("")){
                    return IDUser;
                }
            }
            conexion.close();
        } catch(SQLException ex){
            IDUser = ex.getMessage();
            return IDUser;
        }
        return IDUser;
    }

    public String createNewUser(String IDUsuario, String Password, String Usuario, String Correo, String Nombre, String IDTipo, byte[] image){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO usuarios (IDUsuario, PasswordUser, Usuario, Correo, Nombre, IDTipo, Image) " +
                                                                    "VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, IDUsuario);
            ps.setString(2, Password);
            ps.setString(3, Usuario);
            ps.setString(4, Correo);
            ps.setString(5, Nombre);
            ps.setString(6, IDTipo);
            ps.setBytes(7, image);
            ps.execute();
            conexion.close();
            return "Usuario creado con éxito";
        } catch(SQLException ex){
            return "Error, intentelo más tarde o seleccione una imagen diferente";
            //return ex.getMessage();
        }
    }

    public byte[] getImage(String ID) {
        String bytesImages = "";
        ResultSet rs = null;
        byte[] input = null;
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select Image from usuarios where IDUsuario=?");
            ps.setString(1, ID);
            rs = ps.executeQuery();
            if (rs.next()){
                input = rs.getBytes(1);
                //Bitmap bitmap = BitmapFactory.decodeByteArray(input, 0, input.length);
                return input;
                }
            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public String[] getDataUser(String ID){
        String dataUser[] = new String[3];
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select PasswordUser, Correo, Nombre from usuarios where IDUsuario=?");
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                dataUser[0] = rs.getString(1);
                dataUser[1] = rs.getString(2);
                dataUser[2] = rs.getString(3);
                return dataUser;
            }
            conexion.close();
        } catch(SQLException ex){
            dataUser[0] = ex.getMessage();
            return dataUser;
        }
        return dataUser;
    }

    public String updateUserWithPassword(String Password, String Usuario, String Correo, String Nombre, byte[] image){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            conexion.setAutoCommit(false);
            Statement st = conexion.createStatement();
            String queries[] = new String[4];
            queries[0] = "update usuarios set PasswordUser='"+Password+"' where Usuario='"+Usuario+"'";
            queries[1] = "update usuarios set Correo='"+Correo+"' where Usuario='"+Usuario+"'";
            queries[2] = "update usuarios set Nombre='"+Nombre+"' where Usuario'="+Usuario+"'";
            st.addBatch(queries[0]);
            st.addBatch(queries[1]);
            st.addBatch(queries[2]);
            st.executeBatch();
            conexion.commit();
            return "Usuario actualizado con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }

    public String updateMailUser(String Correo, String Usuario){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            conexion.setAutoCommit(false);
            Statement st = conexion.createStatement();
            st.addBatch("update usuarios set Correo='"+Correo+"' where Usuario='"+Usuario+"'");
            st.executeBatch();
            conexion.commit();
            return "Correo actualizado con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }

    public String updateNameUser(String Nombre, String Usuario){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            conexion.setAutoCommit(false);
            Statement st = conexion.createStatement();
            st.addBatch("update usuarios set Nombre='"+Nombre+"' where Usuario='"+Usuario+"'");
            st.executeBatch();
            conexion.commit();
            return "Nombre actualizado con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }

    public String updatePasswordUser(String Password, String Usuario){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            conexion.setAutoCommit(false);
            Statement st = conexion.createStatement();
            st.addBatch("update usuarios set PasswordUser='"+Password+"' where Usuario='"+Usuario+"'");
            st.executeBatch();
            conexion.commit();
            return "Password actualizado con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }

    public String updateImageUser(byte[] image, String Usuario){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("update usuarios set Image=? where Usuario=?");
            ps.setBytes(1, image);
            ps.setString(2, Usuario);
            ps.executeUpdate();
            conexion.close();
        } catch(SQLException ex){
            return "Error, intentelo más tarde o seleccione una imagen diferente";
        }
        return null;
    }

//select fecha_inicial, fecha_final, informacion, IDRes from notificacionesres where fecha_inicial BETWEEN '2022/04/18' and '2022/04/18';
//select fecha_inicial, fecha_final, informacion, IDRes from notificacionesres where fecha_inicial >= '2022/04/18' and fecha_final <= '2022/04/30';
//select fecha_inicial, fecha_final, informacion, IDRes from notificacionesres where fecha_inicial >= '2022/04/16'
//select count(fecha_inicial) as cantidad_notificaciones, fecha_inicial, fecha_final, informacion, IDRes from notificacionesres where fecha_inicial >= '2022/04/16'
//select count(fecha_inicial) as cantidad_notificaciones from notificacionesres where fecha_inicial >= date_add('2022-04-19', INTERVAL -7 DAY) and fecha_final <= date_add('2022-04-19', INTERVAL 7 DAY)
    public String getNumberNotifications(String date){
        String numberNotifications = "";
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select count(fecha_inicial) as cantidad_notificaciones from notificacionesres where fecha_inicial >=date_add(?, INTERVAL -7 DAY) and fecha_final <= date_add(?, INTERVAL 7 DAY)");
            ps.setString(1, date);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                numberNotifications = rs.getString(1);
                return numberNotifications;
            }
            conexion.close();
        } catch(SQLException ex){
            numberNotifications = ex.getMessage();
            return numberNotifications;
        }
        return numberNotifications;
    }

    public String[] getIDNotifications(String date){
        String[] idsRetorned = new String[32];
        int count = 0;
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select IDNotificacion from notificacionesres " +
                    "where fecha_inicial >= date_add(?, INTERVAL -7 DAY) and fecha_final <= date_add(?, INTERVAL 7 DAY)");
            ps.setString(1, date);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                idsRetorned[count] = rs.getString("IDNotificacion");
                count++;
            }
            conexion.close();
            return idsRetorned;

        } catch(SQLException ex){
            idsRetorned[0] = ex.getMessage();
            return idsRetorned;
        }
    }

    public String[] getNotifications(String IDNotification){
        String dataNotifications[] = new String[4];
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select fecha_inicial, fecha_final, informacion, IDRes from notificacionesres " +
                                                                    "where IDNotificacion=?");
            ps.setString(1, IDNotification);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                dataNotifications[0] = rs.getString(1);
                dataNotifications[1] = rs.getString(2);
                dataNotifications[2] = rs.getString(3);
                dataNotifications[3] = rs.getString(4);
                return dataNotifications;
            }
            conexion.close();
        } catch(SQLException ex){
            dataNotifications[0] = ex.getMessage();
            return dataNotifications;
        }
        return dataNotifications;
    }

    public String getRestaurant(String ID){
        String nameRestaurant = "";
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select Restaurante from restaurante where IDRestaurante=?");
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                nameRestaurant = rs.getString(1);
                return nameRestaurant;
            }
            conexion.close();
        } catch(SQLException ex){
            nameRestaurant = ex.getMessage();
            return nameRestaurant;
        }
        return nameRestaurant;
    }

    public ArrayList<String[]> getRestaurantInformationByUserAdmin(String IDUserAdmin){
        ArrayList<String[]> dataAllRestaurant = new ArrayList<String[]>();
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select IDRestaurante, Direccion, Restaurante, Descripcion, NumTel from " +
                                                                    "restaurante where IDUsuarioAdmin=?");
            ps.setString(1, IDUserAdmin);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String dataRestaurant[] = new String[5];
                dataRestaurant[0] = rs.getString(1);
                dataRestaurant[1] = rs.getString(2);
                dataRestaurant[2] = rs.getString(3);
                dataRestaurant[3] = rs.getString(4);
                dataRestaurant[4] = rs.getString(5);
                dataAllRestaurant.add(dataRestaurant);
            }
            conexion.close();
            return dataAllRestaurant;
        } catch(SQLException ex){
            return dataAllRestaurant;
        }
    }

    public String[] getHorariesByID(String IDRestaurante){
        String horaries[] = new String[21];
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select * from horario_restaurante where IDRestaurante=?");
            ps.setString(1, IDRestaurante);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                horaries[0] = rs.getString("IDDia1");
                horaries[1] = rs.getString("IDHorario1Dia1");
                horaries[2] = rs.getString("IDHorario2Dia1");
                horaries[3] = rs.getString("IDDia2");
                horaries[4] = rs.getString("IDHorario1Dia2");
                horaries[5] = rs.getString("IDHorario2Dia2");
                horaries[6] = rs.getString("IDDia3");
                horaries[7] = rs.getString("IDHorario1Dia3");
                horaries[8] = rs.getString("IDHorario2Dia3");
                horaries[9] = rs.getString("IDDia4");
                horaries[10] = rs.getString("IDHorario1Dia4");
                horaries[11] = rs.getString("IDHorario2Dia4");
                horaries[12] = rs.getString("IDDia5");
                horaries[13] = rs.getString("IDHorario1Dia5");
                horaries[14] = rs.getString("IDHorario2Dia5");
                horaries[15] = rs.getString("IDDia6");
                horaries[16] = rs.getString("IDHorario1Dia6");
                horaries[17] = rs.getString("IDHorario2Dia6");
                horaries[18] = rs.getString("IDDia7");
                horaries[19] = rs.getString("IDHorario1Dia7");
                horaries[20] = rs.getString("IDHorario2Dia7");
            }
            conexion.close();
        } catch(SQLException ex){
            horaries[0] = ex.toString();
        }
        return horaries;
    }

    public String updateNameRestaurant(String Name, String IDRestaurant){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            conexion.setAutoCommit(false);
            Statement st = conexion.createStatement();
            st.addBatch("update restaurante set Restaurante='"+Name+"' where IDRestaurante='"+IDRestaurant+"'");
            st.executeBatch();
            conexion.commit();
            return "Campos actualizados con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }

    public String updateAddressRestaurant(String Address, String IDRestaurant){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            conexion.setAutoCommit(false);
            Statement st = conexion.createStatement();
            st.addBatch("update restaurante set Direccion='"+Address+"' where IDRestaurante='"+IDRestaurant+"'");
            st.executeBatch();
            conexion.commit();
            return "Campos actualizados con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }

    public String updateDescriptionRestaurant(String Description, String IDRestaurant){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            conexion.setAutoCommit(false);
            Statement st = conexion.createStatement();
            st.addBatch("update restaurante set Descripcion='"+Description+"' where IDRestaurante='"+IDRestaurant+"'");
            st.executeBatch();
            conexion.commit();
            return "Campos actualizados con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }

    public String updatePhoneRestaurant(String Phone, String IDRestaurant){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            conexion.setAutoCommit(false);
            Statement st = conexion.createStatement();
            st.addBatch("update restaurante set NumTel='"+Phone+"' where IDRestaurante='"+IDRestaurant+"'");
            st.executeBatch();
            conexion.commit();
            return "Campos actualizados con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }

    public String updateHoraryRestaurantByID(String Field, String Hour, String IDRestaurant){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            conexion.setAutoCommit(false);
            Statement st = conexion.createStatement();
            st.addBatch("update  horario_restaurante  set "+Field+"='"+Hour+"' where IDRestaurante='"+IDRestaurant+"'");
            st.executeBatch();
            conexion.commit();
            return "Campos actualizados con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }

    public String updateDayClosedRestaurantByID(String Field, String IDRestaurant){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            conexion.setAutoCommit(false);
            Statement st = conexion.createStatement();
            st.addBatch("update  horario_restaurante  set "+Field+"=8 where IDRestaurante='"+IDRestaurant+"'");
            st.executeBatch();
            conexion.commit();
            return "Campos actualizados con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }

    public String[] getUserBusiness(){
        String[] usersBusiness = new String[2];
        int count = 0;
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("select Usuario from usuarios where IDTipo=3");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                usersBusiness[count] = rs.getString(1);
                count++;
            }
            conexion.close();
        } catch(SQLException ex){
            usersBusiness[0] = ex.getMessage();
            return usersBusiness;
        }
        return usersBusiness;
    }

    public String registerRestaurant(String name, String address, String description, String phone, String city, String IDUsuarioAdmin){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("insert into restaurante (Restaurante, Direccion, Descripcion, NumTel, Estatus, IDCiudad, " +
                    "IDUsuarioAdmin) values (?,?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, description);
            ps.setString(4, phone);
            ps.setString(5, "Activo");
            ps.setString(6, city);
            ps.setString(7, IDUsuarioAdmin);
            ps.execute();
            conexion.close();
            return "Restaurante Registrado con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }

    public String createPromotion(String IDRestaurant, String startDate, String finalDate, String information){
        try {
            Connection conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db,user ,pss);
            PreparedStatement ps = conexion.prepareStatement("insert into  notificacionesres  (fecha_inicial, fecha_final, informacion, IDRes) values (?,?,?,?)");
            ps.setString(1, startDate);
            ps.setString(2, finalDate);
            ps.setString(3, information);
            ps.setString(4, IDRestaurant);
            ps.execute();
            conexion.close();
            return "Promoción Registrada con éxito";
        } catch(SQLException ex){
            return ex.getMessage();
        }
    }
}
