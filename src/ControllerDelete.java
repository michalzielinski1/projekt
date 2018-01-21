
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ControllerDelete implements ActionListener {
    
    private DefaultTableModel model;
    private ViewSearch view;
    private JTable jTableSearch;

    public ControllerDelete(JTable jTableSearch, DefaultTableModel model, ViewSearch view) {
        super();
        this.model = model;
        this.view = view;
        this.jTableSearch = jTableSearch;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!jTableSearch.getSelectionModel().isSelectionEmpty() && view.showYesNoConfirmationDialog("Czy napeweno chcesz usunąc ten produkt?") )
        {
            try {
                int row = jTableSearch.getSelectedRow();
                int col = 0;
                String id = jTableSearch.getValueAt(row, col).toString();
                Database.getInstance().deleteAttrByID(id);
                Database.getInstance().deleteRowByID(id);  
                model.removeRow(row);
            } catch (Exception ex) {
                Logger.getLogger(ControllerSearch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }   
}
