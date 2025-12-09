package com.sistematutoriascomp.sistematutorias.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBaseDatos {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection CONEXION = null;

    private static Properties cargarPropiedades() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static Connection abrirConexionBD() {
        Properties properties = cargarPropiedades();
        String URL_CONEXION = properties.getProperty("db.url") + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String USUARIO = properties.getProperty("db.user");
        String CONTRASENIA = properties.getProperty("db.password");

        try {
            Class.forName(DRIVER);

            if (CONEXION == null || CONEXION.isClosed()) {
                CONEXION = DriverManager.getConnection(URL_CONEXION, USUARIO, CONTRASENIA);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return CONEXION;
    }

    public static void cerrarConexionBD() {
        try {
            if (CONEXION != null && !CONEXION.isClosed()) {
                CONEXION.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CONEXION = null;
        }
    }
}

