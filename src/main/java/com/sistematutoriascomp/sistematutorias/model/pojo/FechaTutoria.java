/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.model.pojo;

import java.time.LocalDate;

/**
 *
 * @author HP
 */
public class FechaTutoria {
    private int idFechaTutoria;
    private int idPeriodo;
    private int numeroSesion;
    private LocalDate fecha;

    public FechaTutoria() {
    }

    public FechaTutoria(int idFechaTutoria, int idPeriodo, int numeroSesion, LocalDate fecha) {
        this.idFechaTutoria = idFechaTutoria;
        this.idPeriodo = idPeriodo;
        this.numeroSesion = numeroSesion;
        this.fecha = fecha;
    }

    public int getIdFechaTutoria() {
        return idFechaTutoria;
    }

    public void setIdFechaTutoria(int idFechaTutoria) {
        this.idFechaTutoria = idFechaTutoria;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getNumeroSesion() {
        return numeroSesion;
    }

    public void setNumeroSesion(int numeroSesion) {
        this.numeroSesion = numeroSesion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    @Override
    public String toString() {
        return "Sesión " + numeroSesion + " - " + fecha.toString();
    }
}
