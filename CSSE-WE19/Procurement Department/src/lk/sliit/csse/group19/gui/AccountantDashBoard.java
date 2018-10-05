/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19.gui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import lk.sliit.csse.group19.AuthorizedEmployee;
import lk.sliit.csse.group19.ProcurementFacade;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Muhammed Suhail
 */
public class AccountantDashBoard extends javax.swing.JFrame {

    /**
     * Creates new form ManagerDashBoard
     */
    private static boolean status = false;
    private String purchaseId = null;
    private int sumQty = 0;
    private String purcaseOrderID = null;
    private int ReciptID = -1;
    
    ProcurementFacade procurementFacade = new ProcurementFacade();
    Object loggedUser = null;
    
    public AccountantDashBoard() {
        initComponents();
        this.setLocationRelativeTo(null);
        checkStatus();
        loadReciptList();
        UpdatePurchaseListTable();
        this.StatusOfMakePaymentLabel.setText(Boolean.toString(status));
        
    }
    
    public AccountantDashBoard(AuthorizedEmployee user) {
        initComponents();
        this.loggedUser = user;
        this.setLocationRelativeTo(null);
        checkStatus();
        loadReciptList();
        UpdatePurchaseListTable();
        this.StatusOfMakePaymentLabel.setText(Boolean.toString(status));
        
    }
    
    @Override
    public void dispose() {
        System.out.println("Closed");
        super.dispose();
        Login login = new Login();
        login.setVisible(true);
    }
    
    private void checkStatus() {
        if(status == false) {
            this.MakePaymentButton.setEnabled(false);
        } else if(status == true) {
            this.MakePaymentButton.setEnabled(true);
        }
    }
    
    private void loadReciptList() {
        this.ReciptList.removeAll();
        ArrayList<String> reciptList = new ArrayList<>();
        reciptList.add("hi");
        reciptList.add("hello");
        DefaultListModel<String> model = new DefaultListModel<>();
        for(String element: reciptList){
            model.addElement(element);
        }
        this.ReciptList.setModel(model);
    }
    
    private void UpdatePurchaseListTable() {
        try {
            DefaultTableModel table = (DefaultTableModel) this.PurchaseListTable.getModel();
            if (table.getRowCount() != 0) {
                while (table.getRowCount() != 0) {
                    table.removeRow(0);
                }
            }
            Thread th = new Thread() {
                public void run() {
                    JSONArray arr = procurementFacade.getValues("https://jsonplaceholder.typicode.com/posts/");

                    for (int x = 0; x < arr.length(); x++) {
                        //            System.out.println(arr.getJSONObject(x).toString());
                        table.addRow(new Object[]{arr.getJSONObject(x).getInt("id"), arr.getJSONObject(x).getString("title"),
                            arr.getJSONObject(x).getInt("userId"), arr.getJSONObject(x).getString("body")});
                    }
                }
            };
            th.start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
        private void UpdateReciptListTable() {
        try {
            DefaultTableModel table = (DefaultTableModel) this.ReciptItemListTable.getModel();
            if (table.getRowCount() != 0) {
                while (table.getRowCount() != 0) {
                    table.removeRow(0);
                }
            }
            Thread th = new Thread() {
                public void run() {
                    JSONArray arr = procurementFacade.getValues("https://jsonplaceholder.typicode.com/posts/");

                    for (int x = 0; x < arr.length(); x++) {
                        //            System.out.println(arr.getJSONObject(x).toString());
                        table.addRow(new Object[]{arr.getJSONObject(x).getInt("id"), arr.getJSONObject(x).getString("title"),
                            arr.getJSONObject(x).getInt("userId"), arr.getJSONObject(x).getString("body")});
                    }
                }
            };
            th.start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        PurchaseListSelectionPanel = new javax.swing.JPanel();
        SelectPurchaseListComboBox = new javax.swing.JComboBox<>();
        SelectPurchseListLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        PurchaseListTable = new javax.swing.JTable();
        ItemReciptPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ReciptList = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        ReciptItemListTable = new javax.swing.JTable();
        SelectItemReciptLabel = new javax.swing.JLabel();
        ItemReceivedLabel = new javax.swing.JLabel();
        MakePaymentButton = new javax.swing.JButton();
        DisplayStatusLabel = new javax.swing.JLabel();
        StatusOfMakePaymentLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PurchaseListSelectionPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PurchaseListSelectionPanel.setDoubleBuffered(false);

        SelectPurchaseListComboBox.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        SelectPurchaseListComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        SelectPurchaseListComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectPurchaseListComboBoxActionPerformed(evt);
            }
        });

        SelectPurchseListLabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        SelectPurchseListLabel.setText("Select Purchese Order");

        PurchaseListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item", "Quantity", "Supplier", "Initiated Date ", "Expected Date "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PurchaseListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PurchaseListTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(PurchaseListTable);
        if (PurchaseListTable.getColumnModel().getColumnCount() > 0) {
            PurchaseListTable.getColumnModel().getColumn(0).setResizable(false);
            PurchaseListTable.getColumnModel().getColumn(1).setResizable(false);
            PurchaseListTable.getColumnModel().getColumn(2).setResizable(false);
            PurchaseListTable.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout PurchaseListSelectionPanelLayout = new javax.swing.GroupLayout(PurchaseListSelectionPanel);
        PurchaseListSelectionPanel.setLayout(PurchaseListSelectionPanelLayout);
        PurchaseListSelectionPanelLayout.setHorizontalGroup(
            PurchaseListSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PurchaseListSelectionPanelLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(PurchaseListSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PurchaseListSelectionPanelLayout.createSequentialGroup()
                        .addComponent(SelectPurchseListLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SelectPurchaseListComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        PurchaseListSelectionPanelLayout.setVerticalGroup(
            PurchaseListSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PurchaseListSelectionPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(PurchaseListSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SelectPurchseListLabel)
                    .addComponent(SelectPurchaseListComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        ItemReciptPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ReciptList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        ReciptList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ReciptListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(ReciptList);

        ReciptItemListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item", "Quantity", "Supplier", "Received  Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(ReciptItemListTable);
        if (ReciptItemListTable.getColumnModel().getColumnCount() > 0) {
            ReciptItemListTable.getColumnModel().getColumn(0).setResizable(false);
            ReciptItemListTable.getColumnModel().getColumn(1).setResizable(false);
            ReciptItemListTable.getColumnModel().getColumn(2).setResizable(false);
            ReciptItemListTable.getColumnModel().getColumn(3).setResizable(false);
        }

        SelectItemReciptLabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        SelectItemReciptLabel.setText("Select item Recipt");

        ItemReceivedLabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        ItemReceivedLabel.setText("Item Received");

        MakePaymentButton.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        MakePaymentButton.setText("Make Payment");

        DisplayStatusLabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        DisplayStatusLabel.setText("Status");

        StatusOfMakePaymentLabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        StatusOfMakePaymentLabel.setText("Null");

        javax.swing.GroupLayout ItemReciptPanelLayout = new javax.swing.GroupLayout(ItemReciptPanel);
        ItemReciptPanel.setLayout(ItemReciptPanelLayout);
        ItemReciptPanelLayout.setHorizontalGroup(
            ItemReciptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ItemReciptPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(ItemReciptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ItemReciptPanelLayout.createSequentialGroup()
                        .addGroup(ItemReciptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ItemReceivedLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(84, 84, 84)
                        .addGroup(ItemReciptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ItemReciptPanelLayout.createSequentialGroup()
                                .addComponent(DisplayStatusLabel)
                                .addGap(63, 63, 63)
                                .addComponent(StatusOfMakePaymentLabel))
                            .addComponent(MakePaymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(91, 91, 91))
                    .addGroup(ItemReciptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(SelectItemReciptLabel)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        ItemReciptPanelLayout.setVerticalGroup(
            ItemReciptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ItemReciptPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(SelectItemReciptLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ItemReciptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ItemReciptPanelLayout.createSequentialGroup()
                        .addGroup(ItemReciptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DisplayStatusLabel)
                            .addComponent(StatusOfMakePaymentLabel))
                        .addGap(58, 58, 58)
                        .addComponent(MakePaymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addComponent(ItemReceivedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addComponent(PurchaseListSelectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ItemReciptPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PurchaseListSelectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ItemReciptPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectPurchaseListComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectPurchaseListComboBoxActionPerformed
        // TODO add your handling code here:
        this.purchaseId = this.SelectPurchaseListComboBox.getSelectedItem().toString();
    }//GEN-LAST:event_SelectPurchaseListComboBoxActionPerformed

    private void PurchaseListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PurchaseListTableMouseClicked
        // TODO add your handling code here:
        this.ReciptList.removeAll();
        DefaultListModel<String> model = new DefaultListModel<>();

        Thread th = new Thread() {
            public void run() {
                try {
                    JSONArray arr = procurementFacade.getValues("https://jsonplaceholder.typicode.com/posts");

                    for (int x = 0; x < arr.length(); x++) {
//                        System.out.println(arr.getJSONObject(x).toString());
                        model.addElement(Integer.toString(arr.getJSONObject(x).getInt("id")));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    try {
                        e.wait(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ManagerDashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        th.start();

        this.ReciptList.setModel(model);
    }//GEN-LAST:event_PurchaseListTableMouseClicked

    private void ReciptListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ReciptListValueChanged
        // TODO add your handling code here:
        System.out.println(this.ReciptID);
        if (this.ReciptID != -1) {
            try {
                DefaultTableModel table = (DefaultTableModel) this.ReciptItemListTable.getModel();
                if (table.getRowCount() != 0) {
                    while (table.getRowCount() != 0) {
                        table.removeRow(0);
                    }
                }
                Thread th = new Thread() {
                    public void run() {
                        JSONArray arr = procurementFacade.getValues("https://jsonplaceholder.typicode.com/posts/");

                        for (int x = 0; x < arr.length(); x++) {
                            //            System.out.println(arr.getJSONObject(x).toString());
                            table.addRow(new Object[]{arr.getJSONObject(x).getInt("id"), arr.getJSONObject(x).getString("title"),
                                arr.getJSONObject(x).getInt("userId"), arr.getJSONObject(x).getString("body")});
                        }
                    }
                };
                th.start();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.ReciptID = this.ReciptList.getSelectedIndex() + 1;
        System.out.println(this.ReciptID);
    }//GEN-LAST:event_ReciptListValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AccountantDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AccountantDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AccountantDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AccountantDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AccountantDashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DisplayStatusLabel;
    private javax.swing.JLabel ItemReceivedLabel;
    private javax.swing.JPanel ItemReciptPanel;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton MakePaymentButton;
    private javax.swing.JPanel PurchaseListSelectionPanel;
    private javax.swing.JTable PurchaseListTable;
    private javax.swing.JTable ReciptItemListTable;
    private javax.swing.JList<String> ReciptList;
    private javax.swing.JLabel SelectItemReciptLabel;
    private javax.swing.JComboBox<String> SelectPurchaseListComboBox;
    private javax.swing.JLabel SelectPurchseListLabel;
    private javax.swing.JLabel StatusOfMakePaymentLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
