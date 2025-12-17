package com.sistematutoriascomp.sistematutorias.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.sistematutoriascomp.sistematutorias.model.ConexionBaseDatos;

public abstract class BaseDaoTest {

    protected Connection connection;

    @BeforeAll
    static void setupProperties() {
        System.setProperty("db.url", "jdbc:mysql://localhost:3306/tutoriatestbd");
        System.setProperty("db.user", "root");
        System.setProperty("db.password", "admin");
        System.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        System.setProperty("db.options", "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    }

    @BeforeEach
    void openConnection() throws SQLException {
        connection = ConexionBaseDatos.abrirConexionBD();
    }

    @AfterEach
    void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        ConexionBaseDatos.cerrarConexionBD();
    }

    protected void execute(String sql) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    protected void reopenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = ConexionBaseDatos.abrirConexionBD();
        }
    }
}
