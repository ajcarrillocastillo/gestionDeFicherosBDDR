/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import gestionDeMovimientos.modelo.Empleado;
import gestionDeMovimientos.modelo.Movimiento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author jesus
 */
public class RecogerEmpleadosMovimientos extends CreateEInsertBDD {

   
    private static RecogerEmpleadosMovimientos devolverFunciones = null;

    /**
     * Singleton
     * @return
     */
    public static RecogerEmpleadosMovimientos devolver() {

        if (devolverFunciones != null) {
            return devolverFunciones;
        } else {
            devolverFunciones = new RecogerEmpleadosMovimientos();
            return devolverFunciones;
        }
    }
 

   /**
    * Recogemos los empleados
    * @return ArrayList Empleados
    * @throws SQLException 
    */
    public ArrayList<Empleado> selectEmpleados() throws SQLException {
        ArrayList listaDeEmpleados = new ArrayList();
        Empleado empleado;
        ResultSet recogeEmpleados;
            recogeEmpleados = ejecutaConsulta("SELECT * FROM `Empresa`.`Empleados`"); 
            ArrayList<Movimiento> listaMovimientos=selectMovimientos();
        while (recogeEmpleados.next()) {
            String numeroDeEmpleado=""+recogeEmpleados.getInt("numeroDeEmpleado");
            String nombre=recogeEmpleados.getString("nombre");
            String apellidos=recogeEmpleados.getString("apellidos");
               ArrayList<Movimiento> listaMovimientosComprobada=new ArrayList();
               for(Movimiento m :listaMovimientos){
                   if(m.getNumeroDeEmpleado().equals(numeroDeEmpleado)){
                       listaMovimientosComprobada.add(m);
                   }
               }
            empleado=new Empleado(numeroDeEmpleado, nombre, apellidos, listaMovimientosComprobada);
            listaDeEmpleados.add(empleado);
        }

        return listaDeEmpleados;
    }

    /**
     * Recogemos los movimientos
     * @return Arraylist movimientos
     * @throws SQLException 
     */
    public ArrayList<Movimiento> selectMovimientos() throws SQLException {
        ArrayList listaMovimientos = new ArrayList();
        Movimiento movimiento;
        ResultSet recogeMovimientos;
        recogeMovimientos = ejecutaConsulta("SELECT * FROM `Empresa`.`Movimientos`");

        while (recogeMovimientos.next()) {
            String numeroDeMovimiento=""+recogeMovimientos.getInt("numeroDeMovimiento");
            double importe=recogeMovimientos.getInt("importe");
            String tipo=recogeMovimientos.getString("tipo");
            String fecha=recogeMovimientos.getString("fecha");
            String descripcion=recogeMovimientos.getString("descripcion");
            String numeroDeEmpleado=""+recogeMovimientos.getInt("numeroDeEmpleadoMovimiento");
            movimiento= new Movimiento(numeroDeMovimiento, importe, tipo, fecha, descripcion, numeroDeEmpleado);
            listaMovimientos.add(movimiento);
        }

        return listaMovimientos;
    }

}
