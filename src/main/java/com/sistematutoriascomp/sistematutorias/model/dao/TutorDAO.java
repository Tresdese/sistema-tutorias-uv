package com.sistematutoriascomp.sistematutorias.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sistematutoriascomp.sistematutorias.model.ConexionBaseDatos;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;

public class TutorDAO {

    private static final String SQL_INSERT = "INSERT INTO tutor (numeroDePersonal, nombre, apellidoPaterno, apellidoMaterno, correo, password, idRol, esActivo, idCarrera) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE tutor SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, password = ?, idRol = ?, esActivo = ?, idCarrera = ? WHERE numeroDePersonal = ?";
    private static final String SQL_DELETE = "DELETE FROM tutor WHERE idTutor = ?";
    private static final String SQL_SELECT_BY_STAFF_NUMBER = "SELECT * FROM tutor WHERE numeroDePersonal = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM tutor";

    public boolean insertarTutor(Tutor tutor) throws SQLException {
        boolean resultado = false;
        try (Connection connection = ConexionBaseDatos.abrirConexionBD()) {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
                statement.setString(1, tutor.getNumeroDePersonal());
                statement.setString(2, tutor.getNombre());
                statement.setString(3, tutor.getApellidoPaterno());
                statement.setString(4, tutor.getApellidoMaterno());
                statement.setString(5, tutor.getCorreo());
                statement.setString(6, tutor.getPassword());
                statement.setInt(7, tutor.getIdRol());
                statement.setBoolean(8, tutor.esActivo());
                statement.setInt(9, tutor.getIdCarrera());
                resultado = statement.executeUpdate() > 0;
            }
        }
        return resultado;
    }

    public boolean updateTutor(Tutor tutor) throws SQLException {
        boolean resultado = false;
        try (Connection connection = ConexionBaseDatos.abrirConexionBD()) {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
                statement.setString(1, tutor.getNombre());
                statement.setString(2, tutor.getApellidoPaterno());
                statement.setString(3, tutor.getApellidoMaterno());
                statement.setString(4, tutor.getCorreo());
                statement.setString(5, tutor.getPassword());
                statement.setInt(6, tutor.getIdRol());
                statement.setBoolean(7, tutor.esActivo());
                statement.setInt(8, tutor.getIdCarrera());
                statement.setString(9, tutor.getNumeroDePersonal());
                resultado = statement.executeUpdate() > 0;
            }
        }
        return resultado;
    }

    public boolean deleteTutor(int idTutor) throws SQLException {
        boolean resultado = false;
        try (Connection connection = ConexionBaseDatos.abrirConexionBD()) {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
                statement.setInt(1, idTutor);
                resultado = statement.executeUpdate() > 0;
            }
        }
        return resultado;
    }

    public Tutor searchTutorByStaffNumber(String numeroDePersonal) throws SQLException {
        try (Connection connection = ConexionBaseDatos.abrirConexionBD()) {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_STAFF_NUMBER);
                statement.setString(1, numeroDePersonal);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToTutor(resultSet);
                    }
                }
            }
        }
        return null;
    }

    public int obtenerIdPorNombre(String nombreTutor) throws SQLException {
        int idTutor = -1; 
        Connection conexion = ConexionBaseDatos.abrirConexionBD();

        if (conexion != null) {
            try {
                String consulta = "SELECT idTutor FROM tutor WHERE nombre = ?";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setString(1, nombreTutor);
                ResultSet resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    idTutor = resultado.getInt("idTutor");
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return idTutor;
    }

    public List<Tutor> getAllTutors() throws SQLException {
        List<Tutor> tutores = new ArrayList<>();
        try (Connection connection = ConexionBaseDatos.abrirConexionBD()) {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        tutores.add(mapResultSetToTutor(resultSet));
                    }
                }
            }
        }
        return tutores;
    }

    private Tutor mapResultSetToTutor(ResultSet resultSet) throws SQLException {
        Tutor tutor = new Tutor();
        tutor.setIdTutor(resultSet.getInt("idTutor"));
        tutor.setNumeroDePersonal(resultSet.getString("numeroDePersonal"));
        tutor.setNombre(resultSet.getString("nombre"));
        tutor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
        tutor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
        tutor.setCorreo(resultSet.getString("correo"));
        tutor.setPassword(resultSet.getString("password"));
        tutor.setIdRol(resultSet.getInt("idRol"));
        tutor.setActivo(resultSet.getBoolean("esActivo"));
        tutor.setIdCarrera(resultSet.getInt("idCarrera"));
        return tutor;
    }
}
