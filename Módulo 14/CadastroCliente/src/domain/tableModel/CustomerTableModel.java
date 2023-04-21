/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.tableModel;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Wesley Gomes
 */
public class CustomerTableModel extends DefaultTableModel {

    public CustomerTableModel() {
        super();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
