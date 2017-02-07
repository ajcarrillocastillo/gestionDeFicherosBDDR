/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionDeMovimientos.modelo;

import java.io.Serializable;

/**
 *
 * @author jesus
 */
public class Movimiento implements Serializable {

    private String numeroDeMovimiento;
    private double Importe;
    private String tipo;
    private String fecha;
    private String descripcion;
    private String numeroDeEmpleado;

    public Movimiento(String numeroDeMovimiento, double Importe, String tipo, String fecha, String descripcion, String numeroDeEmpleado) {
        this.numeroDeMovimiento = numeroDeMovimiento;
        this.Importe = Importe;
        this.tipo = tipo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.numeroDeEmpleado = numeroDeEmpleado;
    }

    public String getNumeroDeMovimiento() {
        return numeroDeMovimiento;
    }

    public void setNumeroDeMovimiento(String numeroDeMovimiento) {
        this.numeroDeMovimiento = numeroDeMovimiento;
    }

    public double getImporte() {
        return Importe;
    }

    public void setImporte(double Importe) {
        this.Importe = Importe;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumeroDeEmpleado() {
        return numeroDeEmpleado;
    }

    public void setNumeroDeEmpleado(String numeroDeEmpleado) {
        this.numeroDeEmpleado = numeroDeEmpleado;
    }

    @Override
    public String toString() {
        return /*NumeroDeMovimiento:0*/ getNumeroDeMovimiento() + "|"/*Importe:1*/ + getImporte() + "|"/*Fecha:2*/ + getFecha() + "|"/*Descripcion3*/ + getDescripcion() + "|"/*NumeroDeEmpleado:4*/ + getNumeroDeEmpleado();
    }
}
