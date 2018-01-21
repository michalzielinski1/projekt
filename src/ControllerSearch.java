
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
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
public class ControllerSearch implements ActionListener {
    
    private JTextField searchJTextField;
    private DefaultTableModel model;
    private ViewSearch view;
    private JCheckBox isAvailableCB;

    public ControllerSearch(JTextField searchJTextField, JCheckBox isAvailableCB, DefaultTableModel model, ViewSearch view) {
        super();
        this.searchJTextField = searchJTextField;
        this.isAvailableCB = isAvailableCB;
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String searchString = searchJTextField.getText();
            boolean onlyAvailable = isAvailableCB.isSelected();
            ResultSet rs = Database.getInstance().searchQuery(searchString, onlyAvailable);      
            ResultSetMetaData metaData = rs.getMetaData();
            int results = 0;

            
            // names of columns
            Vector<String> columnNames = new Vector<String>(Arrays.asList(ModelSearch.COLLUMNS));
            
            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                int columnCount = metaData.getColumnCount();
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                results += 1;
                data.add(vector);
            }
            
            if (results == 0) {
                view.showNoResultsLabel();
            } else {
                view.hideNoResultsLabel();
            }
            
            model.setDataVector(data, columnNames);
            view.setColumnWidths();
            view.setCellRenderers();
            
    
        } catch (Exception ex) {
            Logger.getLogger(ControllerSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
