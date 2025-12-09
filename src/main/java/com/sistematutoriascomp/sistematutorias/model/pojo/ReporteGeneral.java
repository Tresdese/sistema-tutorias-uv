package com.sistematutoriascomp.sistematutorias.model.pojo;

import java.math.BigDecimal;

public class ReporteGeneral {
    private int idReporteGeneral;
    private int idPeriodo;
    private String nombrePeriodo;
    private int idCoordinador;
    private java.time.LocalDateTime fechaGeneracion;
    private String estado;
    private int totalTutorados;
    private int totalEstudiantesRiesgo;
    private int totalTutores;
    private BigDecimal porcentajeAsistencia;
    private int totalProblematicas;
    private String observaciones;

    public ReporteGeneral() {
    }

    public ReporteGeneral(int idReporteGeneral, int idPeriodo, String nombrePeriodo, int idCoordinador, 
                         java.time.LocalDateTime fechaGeneracion, String estado, 
                         int totalTutorados, int totalEstudiantesRiesgo, 
                         int totalTutores, BigDecimal porcentajeAsistencia, 
                         int totalProblematicas, String observaciones) {
        this.idReporteGeneral = idReporteGeneral;
        this.idPeriodo = idPeriodo;
        this.nombrePeriodo = nombrePeriodo;
        this.idCoordinador = idCoordinador;
        this.fechaGeneracion = fechaGeneracion;
        this.estado = estado;
        this.totalTutorados = totalTutorados;
        this.totalEstudiantesRiesgo = totalEstudiantesRiesgo;
        this.totalTutores = totalTutores;
        this.porcentajeAsistencia = porcentajeAsistencia;
        this.totalProblematicas = totalProblematicas;
        this.observaciones = observaciones;
    }

    public int getIdReporteGeneral() {
        return idReporteGeneral;
    }

    public void setIdReporteGeneral(int idReporteGeneral) {
        this.idReporteGeneral = idReporteGeneral;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public int getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(int idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public java.time.LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(java.time.LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTotalTutorados() {
        return totalTutorados;
    }

    public void setTotalTutorados(int totalTutorados) {
        this.totalTutorados = totalTutorados;
    }

    public int getTotalEstudiantesRiesgo() {
        return totalEstudiantesRiesgo;
    }

    public void setTotalEstudiantesRiesgo(int totalEstudiantesRiesgo) {
        this.totalEstudiantesRiesgo = totalEstudiantesRiesgo;
    }

    public int getTotalTutores() {
        return totalTutores;
    }

    public void setTotalTutores(int totalTutores) {
        this.totalTutores = totalTutores;
    }

    public java.math.BigDecimal getPorcentajeAsistencia() {
        return porcentajeAsistencia;
    }

    public void setPorcentajeAsistencia(java.math.BigDecimal porcentajeAsistencia) {
        this.porcentajeAsistencia = porcentajeAsistencia;
    }

    public int getTotalProblematicas() {
        return totalProblematicas;
    }

    public void setTotalProblematicas(int totalProblematicas) {
        this.totalProblematicas = totalProblematicas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
