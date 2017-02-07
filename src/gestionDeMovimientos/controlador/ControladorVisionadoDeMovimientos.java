/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionDeMovimientos.controlador;

import gestionDeMovimientos.modelo.Movimiento;
import gestionDeMovimientos.vista.JDialogVerMovimientos;
import java.util.ArrayList;

/**
 *
 * @author jesus
 */
public class ControladorVisionadoDeMovimientos {
    private ArrayList<Movimiento> listaDeMovimientos;
    private TableModelNoEditable TableModelNEMovimientos;
    private JDialogVerMovimientos VistaverMovimientos;

    public ControladorVisionadoDeMovimientos(ArrayList listaMovimientosEmpleados) {
        listaDeMovimientos=new ArrayList();
        listaDeMovimientos=listaMovimientosEmpleados;
        
        VistaverMovimientos = new JDialogVerMovimientos(null, true);
        
        VistaverMovimientos.setControladorVerMovimientos(this);
       
        VistaverMovimientos.setLocationRelativeTo(null);
        
        TableModelNEMovimientos = new TableModelNoEditable();
        
        VistaverMovimientos.getjTableMovimientos().setModel(TableModelNEMovimientos);
       
        asignarColumnaVerMovimientos(TableModelNEMovimientos);
        
        listarMovimientos(TableModelNEMovimientos);
        
       
        VistaverMovimientos.setVisible(true);
    }
     public void asignarColumnaVerMovimientos(TableModelNoEditable modeloTabla){
        modeloTabla.addColumn("Numero De Movimiento");
        modeloTabla.addColumn("Importe");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Descripción");
    }

    public void listarMovimientos(TableModelNoEditable modeloTabla){
        
        while(modeloTabla.getRowCount()>0){
            modeloTabla.removeRow(0);
        }
        
        Object[] columna = new Object[5];
       
        
        
            for(Movimiento m: listaDeMovimientos ){
                columna[0] = m.getNumeroDeMovimiento();
                columna[1] = m.getImporte();
                columna[2] = m.getTipo();
                columna[3] = m.getFecha();
                columna[4] = m.getDescripcion();
                modeloTabla.addRow(columna);
            }

    }
    
    
    
    
}
