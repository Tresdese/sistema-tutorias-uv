package com.sistematutoriascomp.sistematutorias.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import com.sistematutoriascomp.sistematutorias.model.ConexionBaseDatos;
import com.sistematutoriascomp.sistematutorias.model.pojo.FechaTutoria;

public class FechaTutoriaDAO {
    public static ArrayList<FechaTutoria> obtenerFechasPorPeriodo(int idPeriodo) throws SQLException {
        ArrayList<FechaTutoria> fechas = new ArrayList<>();
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        
        if (conexion != null) {
            try {
                String consulta = "SELECT idFechaTutoria, idPeriodo, numeroSesion, fecha, titulo, descripcion " +
                                  "FROM fechatutoria " +
                                  "WHERE idPeriodo = ? " +
                                  "ORDER BY numeroSesion ASC";
                
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, idPeriodo);
                ResultSet resultado = sentencia.executeQuery();
                
                while (resultado.next()) {
                    FechaTutoria fechaT = new FechaTutoria();
                    fechaT.setIdFechaTutoria(resultado.getInt("idFechaTutoria"));
                    fechaT.setIdPeriodo(resultado.getInt("idPeriodo"));
                    fechaT.setNumeroSesion(resultado.getInt("numeroSesion"));
                    fechaT.setFecha(resultado.getDate("fecha").toLocalDate());
                    fechaT.setTitulo(resultado.getString("titulo"));
                    fechaT.setDescripcion(resultado.getString("descripcion"));
                    fechas.add(fechaT);
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return fechas;
    }

    public static boolean validarFechaRegistrada(int idPeriodo, int numeroSesion) throws SQLException {
        boolean existe = false;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        
        if (conexion != null) {
            try {
                String consulta = "SELECT count(*) FROM fechatutoria WHERE idPeriodo = ? AND numeroSesion = ?";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, idPeriodo);
                sentencia.setInt(2, numeroSesion);
                ResultSet resultado = sentencia.executeQuery();
                
                if (resultado.next() && resultado.getInt(1) > 0) {
                    existe = true;
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return existe;
    }

    public static boolean registrarFechaTutoria(FechaTutoria fechaTutoria) throws SQLException {
        boolean resultado = false;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        
        if (conexion != null) {
            try {
                String insercion = "INSERT INTO fechatutoria (idPeriodo, numeroSesion, fecha, titulo, descripcion) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement sentencia = conexion.prepareStatement(insercion);
                sentencia.setInt(1, fechaTutoria.getIdPeriodo());
                sentencia.setInt(2, fechaTutoria.getNumeroSesion());
                sentencia.setDate(3, Date.valueOf(fechaTutoria.getFecha()));
                sentencia.setString(4, fechaTutoria.getTitulo());
                sentencia.setString(5, fechaTutoria.getDescripcion());
                
                resultado = (sentencia.executeUpdate() > 0);
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return resultado;
    }

    public static int obtenerIdPeriodoActual() throws SQLException {
        int idPeriodo = 0;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        
        if (conexion != null) {
            try {
                String consulta = "SELECT idPeriodo FROM periodo WHERE esActual = 1 LIMIT 1";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                ResultSet resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    idPeriodo = resultado.getInt("idPeriodo");
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return idPeriodo;
    }
    
    public static int comprobarSiguienteSesion(int idPeriodo) throws SQLException {
        int siguiente = 1;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        
        if (conexion != null) {
            try {
                String consulta = "SELECT MAX(numeroSesion) FROM fechatutoria WHERE idPeriodo = ?";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, idPeriodo);
                ResultSet resultado = sentencia.executeQuery();
                
                if (resultado.next()) {
                    siguiente = resultado.getInt(1) + 1;
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return siguiente;
    }
}