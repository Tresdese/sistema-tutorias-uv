/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.model.pojo;

import java.time.LocalDateTime;

/**
 *
 * @author HP
 */
public class ReporteTutoria {
    private int idReporteTutoria;
    private int idTutoria;          
    private LocalDateTime fechaGeneracion;
    private String observaciones;

    public ReporteTutoria() {
    }

    public ReporteTutoria(int idReporteTutoria, int idTutoria, LocalDateTime fechaGeneracion, String observaciones) {
        this.idReporteTutoria = idReporteTutoria;
        this.idTutoria = idTutoria;
        this.fechaGeneracion = fechaGeneracion;
        this.observaciones = observaciones;
    }

    public int getIdReporteTutoria() {
        return idReporteTutoria;
    }

    public void setIdReporteTutoria(int idReporteTutoria) {
        this.idReporteTutoria = idReporteTutoria;
    }

    public int getIdTutoria() {
        return idTutoria;
    }

    public void setIdTutoria(int idTutoria) {
        this.idTutoria = idTutoria;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
}
