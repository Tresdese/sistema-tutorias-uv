/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.model.pojo;

public class Periodo {

    private int idPeriodo;
    private String nombre;
    private boolean esActual;

    public Periodo() {
    }

    public Periodo(int idPeriodo, String nombre, boolean esActual) {
        this.idPeriodo = idPeriodo;
        this.nombre = nombre;
        this.esActual = esActual;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEsActual() {
        return esActual;
    }

    public void setEsActual(boolean esActual) {
        this.esActual = esActual;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
