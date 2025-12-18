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
    private static final String SQL_INSERT_TUTORIA = "INSERT INTO tutoria (idTutor, idPeriodo, fecha, hora_inicio) VALUES (?, ?, ?, ?)";
    private static final String SQL_COMPROBAR_TUTORIA_REGISTRADA = "SELECT idTutoria FROM tutoria WHERE idTutor = ? AND fecha = ?";
    private static final String SQL_SUBIR_EVIDENCIA = "UPDATE tutoria SET evidencia = ? WHERE idTutoria = ?";
    private static final String SQL_COMPROBAR_EXISTENCIA_EVIDENCIA = "SELECT idTutoria FROM tutoria WHERE idTutoria = ? AND evidencia IS NOT NULL";
    private static final String SQL_OBTENER_ID_TUTOR = "SELECT idTutor FROM tutoria WHERE idTutoria = ?";

    public static int registrarTutoria(Tutoria tutoria) throws SQLException {
        int resultado = 0;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        if (conexion != null) {
            try {
                PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERT_TUTORIA);
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
                PreparedStatement sentencia = conexion.prepareStatement(SQL_COMPROBAR_TUTORIA_REGISTRADA);
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
                PreparedStatement sentencia = conexion.prepareStatement(SQL_SUBIR_EVIDENCIA);
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
                PreparedStatement sentencia = conexion.prepareStatement(SQL_COMPROBAR_EXISTENCIA_EVIDENCIA);
                sentencia.setInt(1, idTutoria);
                ResultSet resultado = sentencia.executeQuery();
                existe = resultado.next();
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return existe;
    }
    
    public static byte[] obtenerEvidencia(int idTutoria) throws SQLException {
        byte[] evidencia = null;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "SELECT evidencia FROM tutoria WHERE idTutoria = ?";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, idTutoria);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    evidencia = rs.getBytes("evidencia");
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return evidencia;
    }

    public static Integer obtenerIdTutorPorTutoria(int idTutoria) throws SQLException {
        Integer idTutor = null;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        if (conexion != null) {
            try {
                PreparedStatement ps = conexion.prepareStatement(SQL_OBTENER_ID_TUTOR);
                ps.setInt(1, idTutoria);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    idTutor = rs.getInt("idTutor");
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return idTutor;
    }
}