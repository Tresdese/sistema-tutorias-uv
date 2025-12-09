/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sistematutoriascomp.sistematutorias.model.ConexionBaseDatos;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;

/**
 *
 * @author HP
 */
public class AutenticacionDAO {

    public static Tutor verificarSesionTutor(String numeroPersonal, String password) throws SQLException {
          Tutor tutor = null;
          Connection conexion = ConexionBaseDatos.abrirConexionBD();
          
          if (conexion != null) {
                try {
                     String consulta = "SELECT * FROM tutor WHERE numeroDePersonal = ? AND password = ?";
                     PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                     prepararSentencia.setString(1, numeroPersonal);
                     prepararSentencia.setString(2, password);
                     
                     ResultSet resultado = prepararSentencia.executeQuery();
                     
                     if (resultado.next()) {
                          tutor = new Tutor();
                          tutor.setIdTutor(resultado.getInt("idTutor"));
                          tutor.setNumeroDePersonal(resultado.getString("numeroDePersonal"));
                          tutor.setNombre(resultado.getString("nombre"));
                          tutor.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                          tutor.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                          tutor.setCorreo(resultado.getString("correo"));
                     }
                } catch (SQLException ex) {
                     ex.printStackTrace();
                     throw ex; 
                } finally {
                     ConexionBaseDatos.cerrarConexionBD();
                }
          }
          return tutor;
     }
}
