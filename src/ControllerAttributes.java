
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Copyright (C) 2018 Michał Zieliński
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author Michał Zieliński
 */
public class ControllerAttributes {
    
    private DefaultTableModel model;
    private ViewProduct view;
    private JTable jTableAttributes;
    private String productID;

    public ControllerAttributes(JTable jTableAttributes, DefaultTableModel model, ViewProduct view, String productID) {
        this.model = model;
        this.view = view;
        this.jTableAttributes = jTableAttributes;
        this.productID = productID;
        if (productID != null) {
          fillAttributesTable();
        }
    }

    private void fillAttributesTable() {
        try {
            ResultSet rs = Database.getInstance().attributesQuery(productID);      
            ResultSetMetaData metaData = rs.getMetaData();
            
            // names of columns
            Vector<String> columnNames = new Vector<String>(Arrays.asList(ModelProductAttributes.COLLUMNS));
            
            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                int columnCount = metaData.getColumnCount();
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 2; columnIndex <= 3; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            
            model.setDataVector(data, columnNames);
            view.setColumnWidths();          
            
        } catch (Exception ex) {
            Logger.getLogger(ControllerSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
