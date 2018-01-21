
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
public class ModelProduct {
    private String productID;
    private String productName;
    private BigDecimal quantity;
    private BigDecimal price;
    private List<Observer> observers = new ArrayList<>();

    public ModelProduct(String productID, String productName, BigDecimal quantity, BigDecimal price) {
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public ModelProduct() {
        this.productID = null;
        this.productName = "";
        this.quantity = new BigDecimal(0);
        this.price = new BigDecimal(0.0);
    }
    
    public void add(Observer o) {
        observers.add(o);
    }
    
    public void setValues(String name, BigDecimal price,  BigDecimal quantity) {
        this.productName = name;
        this.price = price;
        this.quantity = quantity;
        execute();
    }
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
        execute();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        execute();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        execute();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        execute();
    }
    
    private void execute() {
        for (Observer observer : observers) {
            observer.updateView();
        }
    }
    
           
    
}
