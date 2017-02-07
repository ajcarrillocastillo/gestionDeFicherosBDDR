/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionDeMovimientos.controlador;


import DAO.CreateEInsertBDD;
import gestionDeMovimientos.vista.JDialogInsertadoDeEmpleados;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author jesus
 */
public class ControladorInsertardoDeEmpleados {

    private final JDialogInsertadoDeEmpleados vistaInsertadoDeDatos;

    public ControladorInsertardoDeEmpleados(JDialogInsertadoDeEmpleados vistaInsertadoDeDatos) {
        this.vistaInsertadoDeDatos = vistaInsertadoDeDatos;
    }


    public boolean VaciarCampos( JTextField campo, boolean flag) {
        if (flag == false) {
            campo.setText("");
            campo.setForeground(Color.black);
            flag = true;
        }
        return flag;
    }
    
     public boolean VaciarCampos( JTextArea campo, boolean flag) {
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


    
    public void limitadorNumeros(java.awt.event.KeyEvent evt){
     char letra=evt.getKeyChar();
        if(!Character.isDigit(letra)){
        evt.consume();
        }
    }


   
      public boolean InsertarEmpleadosBDD() {
        boolean control=false;
        String nombre = vistaInsertadoDeDatos.getjTextFieldNombre().getText();
        String apellido = vistaInsertadoDeDatos.getjTextFieldApellido().getText();
        if( nombre.isEmpty()||apellido.isEmpty())
        {
         control=false;
        JOptionPane.showMessageDialog(vistaInsertadoDeDatos, "Debe rellenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        
        }else{
        
            if(!CreateEInsertBDD.devolver().insertarEmpleado(nombre, apellido)){
                JOptionPane.showMessageDialog(vistaInsertadoDeDatos, "Ya existe el trabajador", "Error", JOptionPane.ERROR_MESSAGE);
                control=false;
            }else{
                JOptionPane.showMessageDialog(vistaInsertadoDeDatos, "Se han insertado los datos correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                control=true;}
        }
        
        return control;
    }

        
    public static void AbrirInsertadoDeDatos(JFrame jFrame) {
        JDialogInsertadoDeEmpleados nuevaVistaInsertadoDeDatos = new JDialogInsertadoDeEmpleados(jFrame, true);
        nuevaVistaInsertadoDeDatos.setLocationRelativeTo(null);
        nuevaVistaInsertadoDeDatos.setVisible(true);
    }
}
