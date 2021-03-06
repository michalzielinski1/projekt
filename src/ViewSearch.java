
import java.awt.Component;
import java.io.File;
import java.text.DecimalFormat;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;

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
public class ViewSearch extends javax.swing.JFrame {

    /**
     * Creates new form ViewSearch
     */
    public ViewSearch() {
        initComponents();
        //Create table model
        ModelSearch model = new ModelSearch();
        jTableSearch.setModel(model);
        //Create search controllerSearch
        ControllerSearch controllerSearch = new ControllerSearch(jTextFieldSearch, jCheckBoxIsAvailable, model, this);
        ControllerDelete controllerDelete = new ControllerDelete(jTableSearch, model, this);
        ControllerEdit controllerEdit = new ControllerEdit(jTableSearch, model, this);
        ControllerAdd controllerAdd = new ControllerAdd(jTableSearch, model, this);
        ControllerGenerateReport controllerGenerateReport = new ControllerGenerateReport(this, model);
        jButtonSearch.addActionListener(controllerSearch);
        jButtonDeleteProduct.addActionListener(controllerDelete);
        jButtonEditProduct.addActionListener(controllerEdit);
        jButtonAddProduct.addActionListener(controllerAdd);
        jButtonGenerateReport.addActionListener(controllerGenerateReport);
        
        //Visual adjustments
        jTableSearch.setAutoCreateRowSorter(true); 
        jTableSearch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setColumnWidths();
        setCellRenderers();
        this.setVisible(true);
    }
    
    public boolean showYesNoConfirmationDialog(String s) {
        int dialogResult = JOptionPane.showConfirmDialog(this, s, "Proszę potwierdzić operację", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }
    
    public File showFileDialog() {
        jFileChooser1.setDialogType(JFileChooser.SAVE_DIALOG);
        jFileChooser1.setSelectedFile(new File("raport"));
        jFileChooser1.setFileFilter(new FileNameExtensionFilter("PDF file","PDF"));
        if(jFileChooser1.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            String filename = jFileChooser1.getSelectedFile().toString();
            if (!filename.endsWith(".pdf"))  filename += ".pdf";
            return new File(filename);                 
        }
        else 
        { 
            return null;
        }
    }
    
    public void setColumnWidths() {
        jTableSearch.getColumnModel().getColumn(0).setMaxWidth(100);
        jTableSearch.getColumnModel().getColumn(1).setPreferredWidth(400);
        jTableSearch.getColumnModel().getColumn(2).setMaxWidth(100);
        jTableSearch.getColumnModel().getColumn(3).setPreferredWidth(200);
    }
    
    public void setCellRenderers() {
        jTableSearch.getColumnModel().getColumn(3).setCellRenderer(new DecimalFormatRenderer());
    }

    void showNoResultsLabel() {
        jLabelEmpty.setText("ŻADEN PRODUKT NIE SPEŁNIA KRYTERIÓW WYSZUKIWANIA");
    }

    void hideNoResultsLabel() {
        jLabelEmpty.setText("");
    }
    
    
    static class DecimalFormatRenderer extends DefaultTableCellRenderer {
      private static final DecimalFormat formatter = new DecimalFormat("###,###,###,###,##0.00" );
 
      public Component getTableCellRendererComponent(
         JTable table, Object value, boolean isSelected,
         boolean hasFocus, int row, int column) {
 
         // First format the cell value as required
 
         value = formatter.format((Number)value);
 
            // And pass it on to parent class
 
         return super.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, column );
      }
    }
    
    public void setDisabled() {
        this.setEnabled(false);
    }
    
    public void setEnabled() {
        this.setEnabled(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jCheckBoxIsAvailable = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSearch = new javax.swing.JTable();
        jButtonAddProduct = new javax.swing.JButton();
        jButtonEditProduct = new javax.swing.JButton();
        jButtonDeleteProduct = new javax.swing.JButton();
        jButtonGenerateReport = new javax.swing.JButton();
        jLabelEmpty = new javax.swing.JLabel();

        jFileChooser1.setApproveButtonText("");
        jFileChooser1.setDialogTitle("Wybierz lokalizacje pliku");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonSearch.setText("Szukaj");

        jCheckBoxIsAvailable.setText("Tylko dostępne");

        jTableSearch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableSearch);

        jButtonAddProduct.setText("Dodaj Produkt");

        jButtonEditProduct.setText("Edytuj Produkt");

        jButtonDeleteProduct.setText("Usuń Produkt");

        jButtonGenerateReport.setText("Generuj Raport");

        jLabelEmpty.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelEmpty.setForeground(new java.awt.Color(150, 0, 0));
        jLabelEmpty.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelEmpty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBoxIsAvailable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAddProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEditProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonDeleteProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonGenerateReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxIsAvailable)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelEmpty)
                        .addGap(5, 5, 5)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAddProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEditProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDeleteProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonGenerateReport)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddProduct;
    private javax.swing.JButton jButtonDeleteProduct;
    private javax.swing.JButton jButtonEditProduct;
    private javax.swing.JButton jButtonGenerateReport;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JCheckBox jCheckBoxIsAvailable;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabelEmpty;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSearch;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables


}
