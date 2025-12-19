/*
 * Autor: Hernandez Romero Jarly
 * Ultima modificación hecha por: Delgado Santiago Darlington
 * Versión: 4.0
 */
package com.sistematutoriascomp.sistematutorias.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConexionBaseDatos {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection CONEXION = null;
    private static final Logger LOGGER = LogManager.getLogger(ConexionBaseDatos.class);

    private static Properties cargarPropiedades() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            LOGGER.error("Error al cargar archivo de propiedades de BD", e);
            System.err.println("Error al cargar propiedades de BD: " + e.getMessage());
        }

        String urlSistema = System.getProperty("db.url");
        String usuarioSistema = System.getProperty("db.user");
        String contraseniaSistema = System.getProperty("db.password");
        String driverSistema = System.getProperty("db.driver");
        String opcionesSistema = System.getProperty("db.options");

        if (urlSistema != null) {
            properties.setProperty("db.url", urlSistema);
        }
        if (usuarioSistema != null) {
            properties.setProperty("db.user", usuarioSistema);
        }
        if (contraseniaSistema != null) {
            properties.setProperty("db.password", contraseniaSistema);
        }
        if (driverSistema != null) {
            properties.setProperty("db.driver", driverSistema);
        }
        if (opcionesSistema != null) {
            properties.setProperty("db.options", opcionesSistema);
        }

        return properties;
    }

    public static Connection abrirConexionBD() {
        Properties properties = cargarPropiedades();
        String URL_CONEXION = properties.getProperty("db.url");
        String USUARIO = properties.getProperty("db.user");
        String CONTRASENIA = properties.getProperty("db.password");
        String DRIVER_CONFIG = properties.getProperty("db.driver", DRIVER);

        String opcionesPersonalizadas = properties.getProperty("db.options");
        if (URL_CONEXION != null && opcionesPersonalizadas != null && !opcionesPersonalizadas.isBlank()) {
            String separador = URL_CONEXION.contains("?") ? "&" : "?";
            URL_CONEXION = URL_CONEXION + separador + opcionesPersonalizadas;
        } else if (URL_CONEXION != null && URL_CONEXION.startsWith("jdbc:mysql") && !URL_CONEXION.contains("?")) {
            URL_CONEXION = URL_CONEXION + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        }

        try {
            Class.forName(DRIVER_CONFIG);

            if (CONEXION == null || CONEXION.isClosed()) {
                CONEXION = DriverManager.getConnection(URL_CONEXION, USUARIO, CONTRASENIA);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error("Driver JDBC no encontrado: {}", DRIVER_CONFIG, e);
            System.err.println("Driver JDBC no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("Error al abrir conexión a BD", e);
            System.err.println("Error al abrir conexión a BD: " + e.getMessage());
        }

        return CONEXION;
    }

    public static void cerrarConexionBD() {
        try {
            if (CONEXION != null && !CONEXION.isClosed()) {
                CONEXION.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Error al cerrar conexión a BD", e);
            System.err.println("Error al cerrar conexión a BD: " + e.getMessage());
        } finally {
            CONEXION = null;
        }
    }
}
