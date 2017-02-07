
package gestionDeMovimientos.controlador;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jesus
 */
public class TableModelNoEditable extends DefaultTableModel {

 @Override
 public boolean isCellEditable (int row, int column)
    {
        
       
        return false;
    }
}