/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionDeMovimientos.controlador;

import DAO.*;

import gestionDeMovimientos.modelo.Empleado;
import gestionDeMovimientos.modelo.Movimiento;
import gestionDeMovimientos.vista.JFrameVisionadoDeEmpleados;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author jesus
 */
public class ControladorVisionadoDeEmpleados {

    private JFrameVisionadoDeEmpleados vistaVisionadoDeDatos;
    private ArrayList<Empleado> listaDeEmpleado;

    public ControladorVisionadoDeEmpleados(JFrameVisionadoDeEmpleados visionadoDeDatos) {
        this.vistaVisionadoDeDatos = visionadoDeDatos;
        CrearBDD();
        RellenarTabla();
    }

    public void creaColumnas(TableModelNoEditable modelo) {
        modelo.addColumn("Numero de Empleado");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Cantidad de Movimientos");
        modelo.addColumn("Editar");
        modelo.addColumn("Borrar");
    }

    public void RellenarTabla() {
        JTable tablaEmpleados = vistaVisionadoDeDatos.getjTableEmpleados();
        TableModelNoEditable modelo = new TableModelNoEditable();
        tablaEmpleados.setModel(modelo);
        creaColumnas(modelo);

        try {
            this.listaDeEmpleado = RecogerEmpleadosMovimientos.devolver().selectEmpleados();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorVisionadoDeEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object[] columna = new Object[6];
        if (!listaDeEmpleado.isEmpty()) {


            for (Empleado e : listaDeEmpleado) {
                columna[0] = e.getNumeroDeEmpleado();
                columna[1] = e.getNombre();
                columna[2] = e.getApellido();
                columna[3] = "" + e.getListaDeMovimientos().size();
                columna[4] = "Editar" ;
                columna[5] = "Borrar" ;
                modelo.addRow(columna);
            }

            tablaEmpleados.setModel(modelo);
            tablaEmpleados.repaint();
        }

    }

    public void AbrirInsertadoDeEmpleados() {
        ControladorInsertardoDeEmpleados.AbrirInsertadoDeDatos(vistaVisionadoDeDatos);
    }

    public void AbrirInsertadoDeMovimientos() {
       

        new ControladorInsertardoDeMovimientos(this.listaDeEmpleado);

    }
    public void CrearBDD(){
        CreateEInsertBDD.devolver().crearDataBase();
        CreateEInsertBDD.devolver().crearTablaEmpleados();
        CreateEInsertBDD.devolver().crearTablaMovimientos();
        
    }
    public void CerrarConexion(){
        CreateEInsertBDD.devolver().cerrarConexion();
    }
    public void SelectClic(java.awt.event.MouseEvent evt,JTable jTableMiListaPeliculas){
        int fila = jTableMiListaPeliculas.rowAtPoint(evt.getPoint());
        int columna=  jTableMiListaPeliculas.columnAtPoint(evt.getPoint());
        int numeroDeEmpleado= Integer.parseInt(jTableMiListaPeliculas.getValueAt(fila, 0).toString());
        switch (columna){
            case 4:AbrirEditarEmpleado(numeroDeEmpleado);
                break;
            case 5:
                borrarEmpleado(numeroDeEmpleado);
                break;
            default:
                AbrirVerMovimiento(numeroDeEmpleado);
                break;
    }
   
    }
      public void AbrirEditarEmpleado(int numeroDeEmpleado){
         new ControladorEditadoDeEmpleados (numeroDeEmpleado);
    }
     public void borrarEmpleado(int numeroDeEmpleado){
         int resp;
  
               
         resp = JOptionPane.showConfirmDialog(null, "Los Movimientos que tenga tambien se borrarán¿Esta seguro?", "¡Alerta!", JOptionPane.YES_NO_OPTION);
          if(resp==0){
                CreateEInsertBDD.devolver().borrarEmpleado(numeroDeEmpleado);}
        
    }
    public void AbrirVerMovimiento(int numeroDeEmpleadoP) {
        
        ArrayList<Movimiento> listaDeMovimientos = new ArrayList();
        String numeroDeEmpleado = ""+numeroDeEmpleadoP;

        try {
            listaDeMovimientos = RecogerEmpleadosMovimientos.devolver().selectMovimientos();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorVisionadoDeEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (listaDeMovimientos.isEmpty()) {
            JOptionPane.showMessageDialog(vistaVisionadoDeDatos, "No hay Movimientos seleccione otro tipo de fichero o añadelos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            ArrayList<Movimiento> listaDeMovimientosEmpleado = new ArrayList();

            for (Movimiento m : listaDeMovimientos) {
                if (m.getNumeroDeEmpleado().equals(numeroDeEmpleado)) {
                    listaDeMovimientosEmpleado.add(m);
                }
            }
            if (listaDeMovimientosEmpleado.isEmpty()) {
                JOptionPane.showMessageDialog(vistaVisionadoDeDatos, "No hay Movimientos en este empleado seleccione otro tipo de fichero o añadelos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                new ControladorVisionadoDeMovimientos(listaDeMovimientosEmpleado);
            }
        }

    }

}
