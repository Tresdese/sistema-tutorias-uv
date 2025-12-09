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
import java.util.HashMap;

import com.sistematutoriascomp.sistematutorias.model.ConexionBaseDatos;
import com.sistematutoriascomp.sistematutorias.model.pojo.ReporteTutoria;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutoria;

public class ReporteTutoriaDAO {

    public static ArrayList<Tutoria> obtenerSesionesPendientes(int idTutor, int idPeriodo) throws SQLException {
          ArrayList<Tutoria> sesiones = new ArrayList<>();
          Connection conexion = ConexionBaseDatos.abrirConexionBD();
          if (conexion != null) {
                try {
             
                     String sql = "SELECT t.idTutoria, t.fecha, t.hora_inicio " +
                                      "FROM tutoria t " +
                                      "LEFT JOIN reportetutoria r ON r.idTutoria = t.idTutoria " +
                                      "WHERE t.idTutor = ? AND t.idPeriodo = ? " +
                                      "AND r.idTutoria IS NULL " +  
                                      "AND EXISTS (SELECT 1 FROM asistencia a WHERE a.idTutoria = t.idTutoria) " + 
                                      "ORDER BY t.fecha, t.hora_inicio";
                                      
                     PreparedStatement ps = conexion.prepareStatement(sql);
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

     public static HashMap<String, Integer> obtenerTotales(int idTutoria) throws SQLException {
          HashMap<String, Integer> totales = new HashMap<>();
          Connection conexion = ConexionBaseDatos.abrirConexionBD();
          
          if (conexion != null) {
                try {
                     String sqlAsist = "SELECT COUNT(*) as total, " +
                                             "SUM(CASE WHEN asistio = 1 THEN 1 ELSE 0 END) as asistentes, " +
                                             "SUM(CASE WHEN asistio = 0 THEN 1 ELSE 0 END) as faltantes " +
                                             "FROM asistencia WHERE idTutoria = ?";
                     PreparedStatement ps = conexion.prepareStatement(sqlAsist);
                     ps.setInt(1, idTutoria);
                     ResultSet rs = ps.executeQuery();
                     
                     if (rs.next()) {
                          totales.put("tutorados", rs.getInt("total"));
                          totales.put("asistentes", rs.getInt("asistentes"));
                          totales.put("faltantes", rs.getInt("faltantes"));
                     }
                     
                     String sqlProb = "SELECT COUNT(*) as total FROM problematica WHERE idTutoria = ?";
                     PreparedStatement ps2 = conexion.prepareStatement(sqlProb);
                     ps2.setInt(1, idTutoria);
                     ResultSet rs2 = ps2.executeQuery();
                     
                     if (rs2.next()) {
                          totales.put("problematicas", rs2.getInt("total"));
                     }
                } finally {
                     ConexionBaseDatos.cerrarConexionBD();
                }
          }
          return totales;
     }

     public static boolean registrarReporte(ReporteTutoria reporte) throws SQLException {
          boolean resultado = false;
          Connection conexion = ConexionBaseDatos.abrirConexionBD();
          if (conexion != null) {
                try {
                     String sql = "INSERT INTO reportetutoria (idTutoria, fechaGeneracion, observaciones, estatus) " +
                                      "VALUES (?, NOW(), ?, ?)";
                     PreparedStatement ps = conexion.prepareStatement(sql);
                     ps.setInt(1, reporte.getIdTutoria());
                     ps.setString(2, reporte.getObservaciones());
                     ps.setString(3, "BORRADOR"); 
                     
                     resultado = (ps.executeUpdate() > 0);
                } finally {
                     ConexionBaseDatos.cerrarConexionBD();
                }
          }
          return resultado;
     }
}