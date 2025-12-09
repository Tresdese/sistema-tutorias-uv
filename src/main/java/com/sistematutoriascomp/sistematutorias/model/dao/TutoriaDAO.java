/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import com.sistematutoriascomp.sistematutorias.model.ConexionBaseDatos;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutoria;

public class TutoriaDAO {

    public static int registrarTutoria(Tutoria tutoria) throws SQLException {
        int resultado = 0;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO tutoria (idTutor, idPeriodo, fecha, hora_inicio) VALUES (?, ?, ?, ?)";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, tutoria.getIdTutor());
                sentencia.setInt(2, tutoria.getIdPeriodo());
                sentencia.setDate(3, Date.valueOf(tutoria.getFecha()));
                sentencia.setTime(4, Time.valueOf(tutoria.getHoraInicio()));
                resultado = sentencia.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return resultado;
    }

    public static boolean comprobarTutoriaRegistrada(int idTutor, java.time.LocalDate fecha) throws SQLException {
        boolean registrada = false;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "SELECT idTutoria FROM tutoria WHERE idTutor = ? AND fecha = ?";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, idTutor);
                sentencia.setDate(2, Date.valueOf(fecha));
                ResultSet resultado = sentencia.executeQuery();
                registrada = resultado.next();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return registrada;
    }

    public static boolean subirEvidencia(int idTutoria, byte[] evidencia) throws SQLException {
        boolean resultado = false;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "UPDATE tutoria SET evidencia = ? WHERE idTutoria = ?";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setBytes(1, evidencia);
                sentencia.setInt(2, idTutoria);

                int filasAfectadas = sentencia.executeUpdate();
                resultado = (filasAfectadas > 0);
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return resultado;
    }

    public static boolean comprobarExistenciaEvidencia(int idTutoria) throws SQLException {
        boolean existe = false;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "SELECT idTutoria FROM tutoria WHERE idTutoria = ? AND evidencia IS NOT NULL";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, idTutoria);
                ResultSet resultado = sentencia.executeQuery();
                existe = resultado.next();
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return existe;
    }
}

