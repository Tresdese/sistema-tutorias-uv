/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sistematutoriascomp.sistematutorias.model.ConexionBaseDatos;
import com.sistematutoriascomp.sistematutorias.model.pojo.AsistenciaRow;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutoria;

public class AsistenciaDAO {

    public static ArrayList<Tutoria> obtenerSesionesPorTutor(int idTutor, int idPeriodo) throws SQLException {
        ArrayList<Tutoria> sesiones = new ArrayList<>();
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "SELECT idTutoria, fecha, hora_inicio FROM tutoria WHERE idTutor = ? AND idPeriodo = ? ORDER BY fecha DESC";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, idTutor);
                ps.setInt(2, idPeriodo);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Tutoria t = new Tutoria();
                    t.setIdTutoria(rs.getInt("idTutoria"));
                    t.setFecha(rs.getDate("fecha").toLocalDate());
                    t.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                    sesiones.add(t);
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return sesiones;
    }

    public static ArrayList<AsistenciaRow> obtenerTutoradosPorTutor(int idTutor, int idPeriodo, int idTutoria) throws SQLException {
        ArrayList<AsistenciaRow> lista = new ArrayList<>();
        Connection conexion = ConexionBaseDatos.abrirConexionBD();

        if (conexion != null) {
            try {
                String consulta = "SELECT t.idTutorado, t.matricula, "
                        + "CONCAT(t.nombre, ' ', t.apellidoPaterno, ' ', t.apellidoMaterno) as nombreC, "
                        + "t.semestre, "
                        + "asi.asistio "
                        + "FROM tutorado t "
                        + "INNER JOIN asignaciontutor a ON t.idTutorado = a.idTutorado "
                        + "LEFT JOIN asistencia asi ON (asi.idTutorado = t.idTutorado AND asi.idTutoria = ?) "
                        + "WHERE a.idTutor = ? AND a.idPeriodo = ?";

                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, idTutoria);
                ps.setInt(2, idTutor);
                ps.setInt(3, idPeriodo);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    boolean estadoAsistencia = rs.getBoolean("asistio");

                    lista.add(new AsistenciaRow(
                            rs.getInt("idTutorado"),
                            rs.getString("matricula"),
                            rs.getString("nombreC"),
                            rs.getInt("semestre"),
                            estadoAsistencia
                    ));
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return lista;
    }

    public static boolean registrarAsistencia(int idTutoria, int idTutorado, boolean asistio) throws SQLException {
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO asistencia (idTutoria, idTutorado, asistio) VALUES (?, ?, ?) "
                        + "ON DUPLICATE KEY UPDATE asistio = VALUES(asistio)";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, idTutoria);
                ps.setInt(2, idTutorado);
                ps.setBoolean(3, asistio);
                return ps.executeUpdate() > 0;
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return false;
    }

    public static boolean existeAsistenciaParaTutoria(int idTutoria) throws SQLException {
        boolean existe = false;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "SELECT COUNT(*) AS total FROM asistencia WHERE idTutoria = ?";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, idTutoria);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    existe = rs.getInt("total") > 0;
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return existe;
    }

}
