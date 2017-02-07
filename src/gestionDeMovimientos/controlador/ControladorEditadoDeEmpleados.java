/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionDeMovimientos.controlador;

import DAO.*;
import gestionDeMovimientos.vista.JDialogEditadoEmpleado;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author jesus
 */
public class ControladorEditadoDeEmpleados {

    private int numeroDeEmpleado;
    private JDialogEditadoEmpleado vistaEditadoEmpleado;

    public ControladorEditadoDeEmpleados(int numeroDeEmpleado) {
        this.numeroDeEmpleado = numeroDeEmpleado;
        this.vistaEditadoEmpleado = new JDialogEditadoEmpleado(null, true);
        vistaEditadoEmpleado.setControladorEditadoDeEmpleados(this);
        vistaEditadoEmpleado.getjLabelNumeroDeEmpleado().setText(""+numeroDeEmpleado);

        
        vistaEditadoEmpleado.setLocationRelativeTo(null);
        vistaEditadoEmpleado.setVisible(true);
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

    public boolean EditarDatosEmpleados() {
        boolean control=true;
        String nombre= vistaEditadoEmpleado.getjTextFieldNombre().getText();
        String apellido= vistaEditadoEmpleado.getjTextFieldApellido().getText();
       
        if(nombre.isEmpty()|| apellido.isEmpty()){
        control=false;
         JOptionPane.showMessageDialog(vistaEditadoEmpleado, "Rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                
        }else{ 
                    control=true;
                    if(!RecogerEmpleadosMovimientos.devolver().EditarTrabajador(numeroDeEmpleado, nombre, apellido)){
                    JOptionPane.showMessageDialog(vistaEditadoEmpleado, "No se ha podido insertar", "Error", JOptionPane.ERROR_MESSAGE);
                    control=false;
                }else{
                JOptionPane.showMessageDialog(vistaEditadoEmpleado, "Se han insertado los datos correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                control=true;}
                   
       
        }
     return control;
    }
}
