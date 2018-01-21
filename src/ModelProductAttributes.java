
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

   public class ModelProductAttributes extends DefaultTableModel {
    public static final String[] COLLUMNS = {"Nazwa Cechy","Wartość Cechy"};
    public ModelProductAttributes() {
        super(COLLUMNS,0);
    }
    
    @Override
    public boolean isCellEditable(int i,  int i1){
        return false;
    }
    
    @Override
    public Class getColumnClass(int col) {
                switch (col) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return Double.class;
            default:
                return String.class;
        }
    } 
}
