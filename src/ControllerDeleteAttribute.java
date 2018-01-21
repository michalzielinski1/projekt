
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;

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
public class ControllerDeleteAttribute implements ActionListener {
    ModelProductAttributes model;
    ViewProduct view;
    JTable jTableAttributes;

    public ControllerDeleteAttribute(ModelProductAttributes model, ViewProduct view, JTable jTableAttributes) {
        this.model = model;
        this.view = view;
        this.jTableAttributes = jTableAttributes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = jTableAttributes.getSelectedRow();
        if (selectedIndex >= 0 && selectedIndex < model.getRowCount()) model.removeRow(jTableAttributes.getSelectedRow());
    }
    
    

    
}
