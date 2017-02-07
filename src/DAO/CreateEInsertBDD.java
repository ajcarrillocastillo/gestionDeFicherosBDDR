/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jesus
 */
public class CreateEInsertBDD {

    private static Connection connection;
   
   //editar si se prueba en otra bdd
    private static CreateEInsertBDD funcionesBDD = null;
    //direccion de la bdd
    String url = "jdbc:mysql://localhost";
    //usuario de la bdd.
    String usuario = "root";
    //contrasenna de la bddd.
    String password = "";
    /**
     * singleton
     * @return 
     */
    public static CreateEInsertBDD devolver() {

        if (funcionesBDD != null) {
            return funcionesBDD;
        } else {
            funcionesBDD = new CreateEInsertBDD();
            return funcionesBDD;
        }
    }

  /**
     *Realiza la conexion
     * @return Connection
     */
    public Connection realizarConexion() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, usuario, password);
            }
        } catch (SQLException | NullPointerException e) {
           JOptionPane.showMessageDialog(null, "Conecte la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la conexion: " + e);
  
        }
        return connection;
    }
   

    /**
     * annade los drivers: com.mysql.jdbc.Driver
     */
    public void conectarDrivers() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error no se encuentra la clase: "+ex);
            
        }
    }

   /**
    * hace la consulta
    * @param consulta
    * @return 
    */
    public ResultSet ejecutaConsulta(String consulta) {
      
        try {
      
            Statement stat = realizarConexion().createStatement();
            realizarConexion().setAutoCommit(false);
            ResultSet res = stat.executeQuery(consulta);
            realizarConexion().commit();
            realizarConexion().setAutoCommit(true);
            return res;
        } catch (SQLException sqle) {
            try {
                realizarConexion().rollback();
                realizarConexion().setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(CreateEInsertBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("SQLException: " + sqle);
            return null;
        }

    }

   /**
    * cierra la concexion 
    * @return boolean
    */
    public boolean cerrarConexion() {
        try {
            realizarConexion().close();
            return true;
        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex);
            return false;
        }
    }


    /**
     * Si no existe la base de datos Empresa la crea
     *
     * @return
     */
    public boolean crearDataBase() {
        String dataBase = "CREATE DATABASE IF NOT EXISTS Empresa";
        try {
            Statement stat = realizarConexion().createStatement();
           
            this.url = url + "/Empresa";
            int correcto = stat.executeUpdate(dataBase);
            if (correcto > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            
            System.out.println("SQL exception: " + ex);
            return false;
        }
    }

    /**
     * Si no existe la tabla de Empleados la crea
     *
     * @return boolean
     */
    public boolean crearTablaEmpleados() {
        String tablaEmpleados = "CREATE TABLE IF NOT EXISTS `empresa`.`Empleados` (" +
"`numeroDeEmpleado` INT NOT NULL AUTO_INCREMENT ," +
"`nombre` VARCHAR( 40 ) NOT NULL ," +
"`apellidos` VARCHAR( 100 ) NOT NULL ," +
"PRIMARY KEY (  `numeroDeEmpleado` )" +
") ENGINE = INNODB";
        try {
            Statement stat = realizarConexion().createStatement();
            realizarConexion().setAutoCommit(false);
            stat.executeUpdate(tablaEmpleados);
            realizarConexion().commit();
            realizarConexion().setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
          
            try {
                realizarConexion().rollback();
                 realizarConexion().setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(CreateEInsertBDD.class.getName()).log(Level.SEVERE, null, ex1);
            }
               
         
            System.out.println("Error en la consulta SQL: " + ex);
            return false;
        }
    }

  /**
     * Si no existe la tabla de Empleados la crea
     *
     * @return boolean
     */
    public boolean crearTablaMovimientos() {
       	String tablaMovimientos = "CREATE TABLE IF NOT EXISTS `empresa`.`Movimientos` (" +
"`numeroDeMovimiento` INT NOT NULL AUTO_INCREMENT ," +
"`importe` INT NOT NULL ," +
"`tipo` SET(  'Gasto',  'Ingreso' ) NOT NULL ," +
"`fecha` VARCHAR( 10 ) NOT NULL ," +
"`descripcion` VARCHAR( 3000 ) NOT NULL ," +
"`numeroDeEmpleadoMovimiento` INT NOT NULL ," +
"PRIMARY KEY (  `numeroDeMovimiento` ,  `numeroDeEmpleadoMovimiento` )) ENGINE = InnoDB;";
       //algunas bdd no soportan annadir la foreing key en el creado y otras no la soportan porfabor usar una que soporte innoDB para su correcto funcionamiento
        String tablaMovimientosRelacion="ALTER TABLE  `empresa`.`Movimientos` ADD FOREIGN KEY (  `numeroDeEmpleadoMovimiento` ) REFERENCES  `empresa`.`Empleados` (" +
"`numeroDeEmpleado`" +
") ON DELETE CASCADE ON UPDATE CASCADE ;";

        try {
            Statement stat = realizarConexion().createStatement();
            realizarConexion().setAutoCommit(false);
            stat.executeUpdate(tablaMovimientos);
             realizarConexion().commit();
            realizarConexion().setAutoCommit(true);
            Statement state = realizarConexion().createStatement();
            realizarConexion().setAutoCommit(false);
            state.executeUpdate(tablaMovimientosRelacion);
             realizarConexion().commit();
            realizarConexion().setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            
            try {
                realizarConexion().rollback();
                 realizarConexion().setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(CreateEInsertBDD.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println("Error en la consulta SQL: " + ex);
            return false;
        }
    }

   /**
    * inserta el empleado
    * @param nombreEmpleado
    * @param apellido
    * @return boolean true correctamente insertado
    */
    public boolean insertarEmpleado(String nombreEmpleado, String apellido) {
        Statement stat;
        String consultaInsertarTrabajadores = "INSERT INTO `Empresa`.`Empleados` (`nombre`, `apellidos`) VALUES ('" + nombreEmpleado + "', '" + apellido + "');";
        try {
            stat = realizarConexion().createStatement();
            realizarConexion().setAutoCommit(false);
            int resultado = stat.executeUpdate(consultaInsertarTrabajadores);
             realizarConexion().commit();
            realizarConexion().setAutoCommit(true);
          
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            
            try {
                realizarConexion().rollback();
                 realizarConexion().setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(CreateEInsertBDD.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println(ex);
            return false;
        }
    }

    /**
     * inserta movimiento.
     *
     * @param importe
     * @param tipo
     * @param fecha
     * @param descripcion
     * @param numeroDeEmpleado
     * @return  boolean true correctamente insertado
     */
    public boolean insertarMovimiento(double importe, String tipo, String fecha, String descripcion , int numeroDeEmpleado) {
        Statement stat;
        String consultaInsertarClientes = "INSERT INTO `Empresa`.`movimientos` (`importe`, `tipo`, `fecha`, `descripcion`, `numeroDeEmpleadoMovimiento`) VALUES (" + importe + ", '" + tipo + "','" + fecha + "', '" + descripcion + "',"+numeroDeEmpleado+");";
     
        try {
            stat = realizarConexion().createStatement();
            realizarConexion().setAutoCommit(false);
            int resultado = stat.executeUpdate(consultaInsertarClientes);
             realizarConexion().commit();
            realizarConexion().setAutoCommit(true);
           
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            
            try {
                realizarConexion().rollback();
                 realizarConexion().setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(CreateEInsertBDD.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println(ex);
            return false;
        }
    }

    /**
     * actualizamos el empleado
     * @param numeroDeEmpleado
     * @param nombre
     * @param apellidos
     * @return true si es correcto
     */
    public boolean EditarTrabajador(int numeroDeEmpleado, String nombre,String apellidos) {
        Statement stat;
        String updateInstarDNITrabajadorEnClientes = "UPDATE `Empresa`.`Empleados` SET `nombre` = '" + nombre + "',`apellidos`='"+apellidos+"' WHERE `Empleados`.`numeroDeEmpleado` = " + numeroDeEmpleado + ";";
        try {
            stat = realizarConexion().createStatement();
            realizarConexion().setAutoCommit(false);
            int resultado = stat.executeUpdate(updateInstarDNITrabajadorEnClientes);
             realizarConexion().commit();
            realizarConexion().setAutoCommit(true);
         
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            
            try {
                realizarConexion().rollback();
                 realizarConexion().setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(CreateEInsertBDD.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println(ex);
            return false;
        }
    }
    /**
     * borramos el empleado seleccionado
     * @param numeroDeEmpleado
     * @return true correcto
     */
    public boolean borrarEmpleado(int numeroDeEmpleado){
        Statement stat;
        String borrarClienteSentencia = "DELETE FROM `Empresa`.`Empleados` WHERE `Empleados`.`numeroDeEmpleado` = "+numeroDeEmpleado+"";
        try {
            stat = realizarConexion().createStatement();
            realizarConexion().setAutoCommit(false);
            int resultado = stat.executeUpdate(borrarClienteSentencia);
             realizarConexion().commit();
            realizarConexion().setAutoCommit(true);
           
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            
            try {
                realizarConexion().rollback();
                 realizarConexion().setAutoCommit(true);
            } catch (SQLException ex1) {
                Logger.getLogger(CreateEInsertBDD.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println(ex);
            return false;
        }
        
    }
    
}
