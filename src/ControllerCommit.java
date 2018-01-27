
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class ControllerCommit implements ActionListener {
    ModelProduct modelP;
    ModelProductAttributes modelA;
    ViewProduct view;

    public ControllerCommit(ModelProduct modelP, ModelProductAttributes modelA, ViewProduct view) {
        this.modelP = modelP;
        this.modelA = modelA;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(modelP.getProductName().equals("")) {
            view.showErrorDialog("Nazwa jest pusta.");
            return;
        }
        if(view.showYesNoConfirmationDialog("Czy napeweno chcesz dokonać zmian?") )
        {
            try {
                if(modelP.getProductID() == null) //Adding a new product
                {
                    String ID = Database.getInstance().addProduct(modelP.getProductName(), modelP.getQuantity(), modelP.getPrice());
                    for (int i = 0; i < modelA.getRowCount(); i++) {
                        
                        String name = (String)modelA.getValueAt(i, 0);
                        String value = (String)modelA.getValueAt(i, 1);
                        if ((name != null && !name.equals("")) || (value !=null && !value.equals(""))) {
                            Database.getInstance().addAttribute(ID, name, value);
                        }
                    }
                }
                else //Modifying an existing product
                {
                    String ID = modelP.getProductID();
                    Database.getInstance().updateProduct(ID, modelP.getProductName(), modelP.getQuantity(), modelP.getPrice());
                    Database.getInstance().deleteAttrByID(ID);
                    System.out.println(ID);
                    for (int i = 0; i < modelA.getRowCount(); i++) {

                        String name = (String)modelA.getValueAt(i, 0);
                        String value = (String)modelA.getValueAt(i, 1);
                        System.out.println("NAME: " +  name + " VALUE: " + value);
                        if(name != null || value !=null)
                        if ((name != null && !name.equals("")) || (value !=null && !value.equals(""))) {
                            Database.getInstance().addAttribute(ID, name, value);
                            System.out.println(ID + ' ' +  name + ' ' + value);
                        }
                    }  
                }
                view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
            } catch (Exception ex) {
                Logger.getLogger(ControllerSearch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    
}
