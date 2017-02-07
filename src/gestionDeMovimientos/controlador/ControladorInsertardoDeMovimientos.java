/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionDeMovimientos.controlador;

import DAO.*;
import gestionDeMovimientos.modelo.Empleado;
import gestionDeMovimientos.vista.JDialogInsertadoMovimientos;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author jesus
 */
public class ControladorInsertardoDeMovimientos {

    private ArrayList<Empleado> listaDeEmpleados;
    private JDialogInsertadoMovimientos vistaMovimientos;

    public ControladorInsertardoDeMovimientos(ArrayList listaDeEmpleado) {
        this.listaDeEmpleados = listaDeEmpleado;
        this.vistaMovimientos = new JDialogInsertadoMovimientos(null, true);
        vistaMovimientos.setControladorAnnadirMovimientos(this);
        vistaMovimientos.getjComboBoxNumeroDeEmpleado().removeAllItems();
        for (Empleado e : this.listaDeEmpleados) {
            vistaMovimientos.getjComboBoxNumeroDeEmpleado().addItem(e.getNumeroDeEmpleado());
        }
        vistaMovimientos.setLocationRelativeTo(null);
        vistaMovimientos.setVisible(true);
    }

    //vaciar campos
    /**
     *
     * @param evt
     * @param seleccion
     */
    public boolean VaciarCampos(JTextField campo, boolean flag) {
        if (flag == false) {
            campo.setText("");
            campo.setForeground(Color.black);
            flag = true;
        }
        return flag;
    }

    public boolean VaciarCampos(JTextArea campo, boolean flag) {
        if (flag == false) {
            campo.setText("");
            campo.setForeground(Color.black);
            flag = true;
        }
        return flag;
    }

    public boolean RellenarCampos(String Texto, JTextField campo, boolean flag) {

        campo.setText(Texto);
        campo.setForeground(Color.GRAY);
        flag = false;
        return flag;
    }

    public boolean RellenarCampos(String Texto, JTextArea campo, boolean flag) {

        campo.setText(Texto);
        campo.setForeground(Color.GRAY);
        flag = false;
        return flag;
    }

    public void limitadorNumeros(java.awt.event.KeyEvent evt) {
        char letra = evt.getKeyChar();
        if (!Character.isDigit(letra)) {
            evt.consume();
        }
    }

    public boolean insertarDatosMovimientos() {
        boolean control=true;
        String importeString= vistaMovimientos.getjTextFieldImporte().getText();
        String tipo= ""+vistaMovimientos.getjComboBoxTipo().getSelectedItem().toString();
        String fecha= vistaMovimientos.getjTextFieldFecha().getText();
        String descripcion=vistaMovimientos.getjTextAreaDescripcion().getText();
        String numeroDeEmpleadoTexto=""+vistaMovimientos.getjComboBoxNumeroDeEmpleado().getSelectedItem().toString();
        if(importeString.isEmpty()||tipo.isEmpty()||fecha.isEmpty()||descripcion.isEmpty()|| numeroDeEmpleadoTexto.isEmpty()){
        control=false;
         JOptionPane.showMessageDialog(vistaMovimientos, "Rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                
        }else{
        
        double importe= Double.parseDouble(importeString);
        int numeroDeEmpleado=Integer.parseInt(numeroDeEmpleadoTexto);     
                    control=true;
                    if(!RecogerEmpleadosMovimientos.devolver().insertarMovimiento(importe, tipo, fecha, descripcion, numeroDeEmpleado)){
                    JOptionPane.showMessageDialog(vistaMovimientos, "No se ha podido insertar", "Error", JOptionPane.ERROR_MESSAGE);
                    control=false;
                }else{
                JOptionPane.showMessageDialog(vistaMovimientos, "Se han insertado los datos correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                control=true;}
                   
       
        }
     return control;
    }
}
