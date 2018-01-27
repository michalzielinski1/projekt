
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Vector;
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
public class ControllerGenerateReport implements ActionListener{

    ViewSearch view;
    ModelSearch model;

    public ControllerGenerateReport(ViewSearch view, ModelSearch model) {
        this.view = view;
        this.model = model;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        File file = view.showFileDialog();
        if(file != null){
            try {
                createPdf(file);
            } catch (IOException ex) {
                Logger.getLogger(ControllerGenerateReport.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(ControllerGenerateReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void createPdf(File dest) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4, 10,10,10,10);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Paragraph p = new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 18));
        document.add(p);
        float[] columnWidths = {1.5f, 5f, 1.5f, 3f, 7f};
        PdfPTable table = new PdfPTable(columnWidths);
        

        table.setTotalWidth(PageSize.A4.getWidth()*0.9f);
        table.setLockedWidth(true);
        
        table.addCell(strToHeader("ID"));
        table.addCell(strToHeader("Nazwa Produktu"));
        table.addCell(strToHeader("Dost"));
        table.addCell(strToHeader("Cena"));
        table.addCell(strToHeader("Cechy produktu"));
        table.setHeaderRows(1);
        
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###,##0.00");
        
        for (int i = 0; i < model.getRowCount(); i++) {
            Font font = FontFactory.getFont(FontFactory.COURIER);
            String ID = model.getValueAt(i, 0).toString();
            String name = model.getValueAt(i, 1).toString();
            String quantity = model.getValueAt(i, 2).toString();
            String price = model.getValueAt(i, 3).toString();
            price = df.format(Double.parseDouble(price));
            
           
            table.addCell(strToText(ID));
            table.addCell(strToText(name));
            table.addCell(strToText(quantity));
            table.addCell(strToText(price));
            PdfPTable tableAtt = new PdfPTable(2);
            Vector<Vector<Object>> v = getAttributes(ID);
            for (int j = 0; j < v.size(); j++) {
                Vector<Object> vx = v.get(j);
                String n = vx.get(0).toString();
                String va = vx.get(1).toString();
                tableAtt.addCell(strToText(n));
                tableAtt.addCell(strToText(va));
            }
            table.addCell(tableAtt);
        }
        document.add(table);
        document.close();
    }
    
    private Phrase strToHeader(String s) {
        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 13f);
        Phrase p = new Phrase(s, font);
        return p;
    }
    
    private Phrase strToText(String s) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10f);
        Phrase p = new Phrase(s, font);
        return p;
    }
    
    private Vector<Vector<Object>> getAttributes(String productID) {
        try {
            ResultSet rs = Database.getInstance().attributesQuery(productID);      
            ResultSetMetaData metaData = rs.getMetaData();
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
            Statement s = rs.getStatement();
            rs.close();
            s.close();
            return data;   
        } catch (Exception ex) {
            Logger.getLogger(ControllerSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
