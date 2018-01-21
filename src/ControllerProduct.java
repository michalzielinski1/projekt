
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;

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
class ControllerProduct extends Observer implements FocusListener{

    private javax.swing.JFormattedTextField jFormattedTextFieldName;
    private javax.swing.JFormattedTextField jFormattedTextFieldPrice;
    private javax.swing.JFormattedTextField jFormattedTextFieldQuantity;
    private String productID;
    private ModelProduct modelProduct;
    private ViewProduct view;
    
    public ControllerProduct(String productID, JFormattedTextField jFormattedTextFieldName, JFormattedTextField jFormattedTextFieldQuantity, JFormattedTextField jFormattedTextFieldPrice, ModelProduct modelProduct, ViewProduct view) {
        this.productID = productID;
        this.jFormattedTextFieldName = jFormattedTextFieldName;
        this.jFormattedTextFieldQuantity = jFormattedTextFieldQuantity;
        this.jFormattedTextFieldPrice = jFormattedTextFieldPrice;
        this.view = view;
        this.modelProduct = modelProduct;
        this.modelProduct.setProductID(this.productID);
        this.modelProduct.add(this);
        if (productID != null) {
            fillProductInfo();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        String name = jFormattedTextFieldName.getText();
        String priceString = jFormattedTextFieldPrice.getText().replace(",", ".");
        String quantityString = jFormattedTextFieldQuantity.getText();
        if (priceString.length() == 0) {
            priceString = "0.00";
        }
        if (quantityString.length() == 0) {
            quantityString = "0";
        }
        try {
            BigDecimal price = new BigDecimal(priceString);
            BigDecimal Quantity =  new BigDecimal(quantityString);
            modelProduct.setValues(name, price, Quantity);
        } catch (java.lang.NumberFormatException ex)
        {
            ;
        }
        
    }
    
    public void updateView() {
        jFormattedTextFieldName.setText(modelProduct.getProductName());
        jFormattedTextFieldPrice.setText(modelProduct.getPrice().toString().replace(".", ","));
        jFormattedTextFieldQuantity.setText(modelProduct.getQuantity().toString());
    }

    private void fillProductInfo() {
        try {
            ResultSet rs = Database.getInstance().productQuery(productID);      
            ResultSetMetaData metaData = rs.getMetaData();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                int columnCount = metaData.getColumnCount();
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            Vector<Object> res = data.get(0);
            String productName = (String)res.get(1);
            BigDecimal quantity = (BigDecimal)res.get(2);
            BigDecimal price = (BigDecimal)res.get(3);
            
            modelProduct.setPrice(price);
            modelProduct.setQuantity(quantity);
            modelProduct.setProductName(productName);
              
        } catch (Exception ex) {
            Logger.getLogger(ControllerSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
