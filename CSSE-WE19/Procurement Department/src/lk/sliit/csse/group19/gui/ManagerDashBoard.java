/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lk.sliit.csse.group19.AuthorizedEmployee;
import org.json.JSONArray;
import lk.sliit.csse.group19.ProcurementFacade;
import org.json.JSONException;
import org.json.JSONObject;

/* 
 *
 * @author Muhammed Suhail
 */
public class ManagerDashBoard extends javax.swing.JFrame {

    /**
     * Creates new form AccountDashBoard
     */
    ProcurementFacade procurementFacade = new ProcurementFacade();

    private int supplierSelect = -1;
    private String purcaseOrderID = null;
    private String purchaseOrderStatus = null;
    private JSONObject purchaseOrderSupplier = null;
    private AuthorizedEmployee loggedUser = null;
    private JSONArray tempSupplierList = null;

    public ManagerDashBoard() {
        initComponents();
        this.PaymentButton.doClick();
        this.setLocationRelativeTo(null);
    }

    public ManagerDashBoard(AuthorizedEmployee user) {
        initComponents();
        this.PaymentButton.doClick();
        this.setLocationRelativeTo(null);
        this.loggedUser = user;
        System.out.println(loggedUser);
    }

    @Override
    public void dispose() {
        System.out.println("Closed");
        super.dispose();
        Login login = new Login();
        login.setVisible(true);
    }

    private void loadApproveListTable() {
        try {
            DefaultTableModel table = (DefaultTableModel) this.ApprovelListTable.getModel();
            if (table.getRowCount() != 0) {
                while (table.getRowCount() != 0) {
                    table.removeRow(0);
                }
            }
            Thread th = new Thread() {
                public void run() {
                    JSONArray arr = procurementFacade.getValues("http://localhost:9000/purchaseOrder");

                    JSONObject supplier = null;
                    for (int x = 0; x < arr.length(); x++) {
//                        System.out.println(arr.getJSONObject(x).toString());
//                        System.out.println("supp id"+arr.getJSONObject(x).getInt("supplierId"));
                        if (procurementFacade.getSingleValue("http://localhost:9000/supplier/" + arr.getJSONObject(x).get("supplierId")) == null) {
                            table.addRow(new Object[]{arr.getJSONObject(x).get("id"), arr.getJSONObject(x).get("siteManagerId"),
                                arr.getJSONObject(x).get("status"), "Not Set",
                                arr.getJSONObject(x).get("initiatedDate"), arr.getJSONObject(x).get("expectedDate")});
                        } else {
                            supplier = procurementFacade.getSingleValue("http://localhost:9000/supplier/" + arr.getJSONObject(x).get("supplierId"));
//                            System.out.println("Supplier receives " + supplier.toString());
                            table.addRow(new Object[]{arr.getJSONObject(x).get("id"), arr.getJSONObject(x).get("siteManagerId"),
                                arr.getJSONObject(x).get("status"), supplier.get("name"),
                                arr.getJSONObject(x).get("initiatedDate"), arr.getJSONObject(x).get("expectedDate")});
                        }
                    }
                }
            };
            th.start();
        } catch (Exception e) {

        }
    }

    private void loadItemResultTable() {
        try {
            DefaultTableModel table = (DefaultTableModel) this.ItemResultTable.getModel();
            if (table.getRowCount() != 0) {
                while (table.getRowCount() != 0) {
                    table.removeRow(0);
                }
            }
            Thread th = new Thread() {
                public void run() {
                    JSONArray arr = procurementFacade.getValues("http://localhost:9000/item");

                    for (int x = 0; x < arr.length(); x++) {
                        //            System.out.println(arr.getJSONObject(x).toString());
                        table.addRow(new Object[]{arr.getJSONObject(x).get("id"), arr.getJSONObject(x).get("name"),
                            arr.getJSONObject(x).get("price"), arr.getJSONObject(x).get("itemComment")});
                    }
                }
            };
            th.start();
        } catch (JSONException e) {
//            e.printStackTrace();
        }
    }

    private void loadEmployeeListTable() {
        DefaultTableModel table = (DefaultTableModel) this.EmployeeListTable.getModel();
        if (table.getRowCount() != 0) {
            while (table.getRowCount() != 0) {
                table.removeRow(0);
            }
        }
        Thread th = new Thread() {
            public void run() {
                JSONArray arr = procurementFacade.getValues("http://localhost:9000/authorizedEmployee");

                for (int x = 0; x < arr.length(); x++) {
                    //            System.out.println(arr.getJSONObject(x).toString());
                    table.addRow(new Object[]{arr.getJSONObject(x).get("id"), arr.getJSONObject(x).get("name"),
                        arr.getJSONObject(x).get("type"), arr.getJSONObject(x).get("mobileNumber")});
                }
            }
        };
        th.start();
    }

    private void loadSiteList() {
        this.SiteList.removeAll();
        DefaultListModel<String> model = new DefaultListModel<>();

        Thread th = new Thread() {
            public void run() {
                try {
                    JSONArray arr = procurementFacade.getValues("http://localhost:9000/site");

                    for (int x = 0; x < arr.length(); x++) {
                        //            System.out.println(arr.getJSONObject(x).toString());
                        model.addElement(arr.getJSONObject(x).getString("name") + " - " + arr.getJSONObject(x).getString("address"));
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

        this.SiteList.setModel(model);
    }

    private void loadSupplierList() {
        this.SupplierList.removeAll();
        DefaultListModel<String> model = new DefaultListModel<>();

        Thread th = new Thread() {
            public void run() {
                try {
                    JSONArray arr = procurementFacade.getValues("http://localhost:9000/supplier");

                    for (int x = 0; x < arr.length(); x++) {
//                        System.out.println(arr.getJSONObject(x).toString());
                        model.addElement((String) arr.getJSONObject(x).get("name") + " - " + (String) arr.getJSONObject(x).get("address"));
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

        this.SupplierList.setModel(model);
        this.supplierSelect = -1;
    }

    private void loadItemCategoryComboBox() {
        this.CategoryComboBox.removeAllItems();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        Thread th = new Thread() {
            public void run() {
                try {
                    JSONArray arr = procurementFacade.getValues("http://localhost:9000/item");

                    for (int x = 0; x < arr.length(); x++) {
                        //            System.out.println(arr.getJSONObject(x).toString());
                        model.addElement(arr.getJSONObject(x).getString("name"));
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

        this.CategoryComboBox.setModel(model);
    }

    private void loadSelectSupplierComboBox() {
        this.SelectSupplierComboBoxApproval.removeAllItems();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        Thread th = new Thread() {
            public void run() {
                try {
                    JSONArray arr = procurementFacade.getValues("http://localhost:9000/supplier");

                    for (int x = 0; x < arr.length(); x++) {
//                        System.out.println(arr.getJSONObject(x).toString());
                        model.addElement(arr.getJSONObject(x).getString("name"));
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

        this.SelectSupplierComboBoxApproval.setModel(model);
    }

    private void loadSiteManagerComboBox() {
        this.SiteManagerComboBox.removeAllItems();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        Thread th = new Thread() {
            public void run() {
                try {
                    JSONArray arr = procurementFacade.getValues("https://jsonplaceholder.typicode.com/posts");

                    for (int x = 0; x < arr.length(); x++) {
                        //            System.out.println(arr.getJSONObject(x).toString());
                        model.addElement(arr.getJSONObject(x).getString("title"));
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

        this.SiteManagerComboBox.setModel(model);
    }

    private boolean checkDigit(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainScrollPane = new javax.swing.JScrollPane();
        MainPanel = new javax.swing.JPanel();
        ApprovelPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ApprovelListTable = new javax.swing.JTable();
        SelectSupplierLabelApproval = new javax.swing.JLabel();
        SelectSupplierComboBoxApproval = new javax.swing.JComboBox<>();
        SetSupplierApproveButton = new javax.swing.JButton();
        PurchaseOrderStatusComboBox = new javax.swing.JComboBox<>();
        PurchaseOrderLabel = new javax.swing.JLabel();
        SupplierPanel = new javax.swing.JPanel();
        CurrentSupplierPanel = new javax.swing.JPanel();
        SelectSupplierLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        SupplierList = new javax.swing.JList<>();
        SupplierAddUpdatePanel = new javax.swing.JPanel();
        AddUpdateLabel1 = new javax.swing.JLabel();
        SupplierNameTextField1 = new javax.swing.JTextField();
        SupplierEmailTextField = new javax.swing.JTextField();
        SupplierBankNoTextField = new javax.swing.JTextField();
        SupplierAddressTextField = new javax.swing.JTextField();
        SupplierNameLabel = new javax.swing.JLabel();
        SupplierAddressLabel = new javax.swing.JLabel();
        SupplierBankNoLabel = new javax.swing.JLabel();
        SupplierEmailLabel = new javax.swing.JLabel();
        SupplierPhoneLabel = new javax.swing.JLabel();
        AddNewSupplierButton = new javax.swing.JButton();
        SupplierUpdateButton = new javax.swing.JButton();
        SupplierPhoneNoTextField = new javax.swing.JTextField();
        ItemPanel = new javax.swing.JPanel();
        ItemsScrollPane = new javax.swing.JScrollPane();
        ItemResultTable = new javax.swing.JTable();
        AddNewItemPanel = new javax.swing.JPanel();
        RestrictedLabelItem = new javax.swing.JLabel();
        InformationLabelItem = new javax.swing.JLabel();
        PriceLabelItem = new javax.swing.JLabel();
        CategoryLabelItem = new javax.swing.JLabel();
        ItemNameLabelItem = new javax.swing.JLabel();
        ItemNameTextField = new javax.swing.JTextField();
        CategoryComboBox = new javax.swing.JComboBox<>();
        PriceTextField = new javax.swing.JTextField();
        InformationTextField = new javax.swing.JTextField();
        RestrictedCheckBox = new javax.swing.JCheckBox();
        SubHeaderLabelItem = new javax.swing.JLabel();
        AddItemButton = new javax.swing.JButton();
        SearchItemTextfieldItem = new javax.swing.JTextField();
        SearchItemButtonItem = new javax.swing.JButton();
        SitePanel = new javax.swing.JPanel();
        CurrentSitePanel = new javax.swing.JPanel();
        SelectSiteLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        SiteList = new javax.swing.JList<>();
        SiteAddUpdatePanel = new javax.swing.JPanel();
        AddUpdateLabel = new javax.swing.JLabel();
        SiteNameTextField = new javax.swing.JTextField();
        SiteCurrentCapacityTextField = new javax.swing.JTextField();
        SiteManagerComboBox = new javax.swing.JComboBox<>();
        SiteNameLabel = new javax.swing.JLabel();
        SiteAddressLabel = new javax.swing.JLabel();
        SiteManagerLabel = new javax.swing.JLabel();
        AddNewSiteButton = new javax.swing.JButton();
        SiteUpdateButton = new javax.swing.JButton();
        EmployeePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        EmployeeListTable = new javax.swing.JTable();
        SearchEmployeeTextField = new javax.swing.JTextField();
        SearchEmployeeButton = new javax.swing.JButton();
        PaymentPanel = new javax.swing.JPanel();
        SubPanels = new javax.swing.JLayeredPane();
        PaymentTabber = new javax.swing.JTabbedPane();
        SuccessfulPaymentTabber = new javax.swing.JTabbedPane();
        ManagementTabber = new javax.swing.JTabbedPane();
        PendingTabber = new javax.swing.JTabbedPane();
        HeaderPanel = new javax.swing.JPanel();
        HeaderLabel = new javax.swing.JLabel();
        PaymentButton = new javax.swing.JButton();
        SupplierButton = new javax.swing.JButton();
        SiteButton = new javax.swing.JButton();
        ItemButton = new javax.swing.JButton();
        EmployeeButton = new javax.swing.JButton();
        ApprovalButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainScrollPane.setBackground(new java.awt.Color(0, 0, 255));

        MainPanel.setBackground(new java.awt.Color(255, 255, 255));
        MainPanel.setMinimumSize(new java.awt.Dimension(1280, 720));
        MainPanel.setPreferredSize(new java.awt.Dimension(1280, 720));
        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ApprovelListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Purchase ID", "Site Manager", "Status", "Supplier", "Initiated Date", "Expected Date"
            }
        ));
        ApprovelListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ApprovelListTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(ApprovelListTable);

        SelectSupplierLabelApproval.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        SelectSupplierLabelApproval.setText("Select Supplier");

        SelectSupplierComboBoxApproval.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        SelectSupplierComboBoxApproval.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        SelectSupplierComboBoxApproval.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SelectSupplierComboBoxApprovalItemStateChanged(evt);
            }
        });
        SelectSupplierComboBoxApproval.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                SelectSupplierComboBoxApprovalPropertyChange(evt);
            }
        });

        SetSupplierApproveButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SetSupplierApproveButton.setText("Update Suppiler and Approve");
        SetSupplierApproveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetSupplierApproveButtonActionPerformed(evt);
            }
        });

        PurchaseOrderStatusComboBox.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        PurchaseOrderStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Approved", "Declined" }));
        PurchaseOrderStatusComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PurchaseOrderStatusComboBoxItemStateChanged(evt);
            }
        });
        PurchaseOrderStatusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PurchaseOrderStatusComboBoxActionPerformed(evt);
            }
        });

        PurchaseOrderLabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        PurchaseOrderLabel.setText("Status");

        javax.swing.GroupLayout ApprovelPanelLayout = new javax.swing.GroupLayout(ApprovelPanel);
        ApprovelPanel.setLayout(ApprovelPanelLayout);
        ApprovelPanelLayout.setHorizontalGroup(
            ApprovelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ApprovelPanelLayout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addGroup(ApprovelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SetSupplierApproveButton)
                    .addGroup(ApprovelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ApprovelPanelLayout.createSequentialGroup()
                            .addComponent(PurchaseOrderLabel)
                            .addGap(18, 18, 18)
                            .addComponent(PurchaseOrderStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SelectSupplierLabelApproval)
                            .addGap(18, 18, 18)
                            .addComponent(SelectSupplierComboBoxApproval, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(77, 77, 77))
        );
        ApprovelPanelLayout.setVerticalGroup(
            ApprovelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ApprovelPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(ApprovelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SelectSupplierLabelApproval)
                    .addComponent(PurchaseOrderStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PurchaseOrderLabel)
                    .addComponent(SelectSupplierComboBoxApproval, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SetSupplierApproveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        MainPanel.add(ApprovelPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 1030, 590));

        SelectSupplierLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SelectSupplierLabel.setText("Select Supplier");

        SupplierList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        SupplierList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                SupplierListValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(SupplierList);

        SupplierAddUpdatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        AddUpdateLabel1.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        AddUpdateLabel1.setText("Add / Update Supplier Details");

        SupplierNameTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        SupplierEmailTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        SupplierBankNoTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        SupplierAddressTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        SupplierNameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SupplierNameLabel.setText("Supplier Name");

        SupplierAddressLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SupplierAddressLabel.setText("Address");

        SupplierBankNoLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SupplierBankNoLabel.setText("Bank Account No");

        SupplierEmailLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SupplierEmailLabel.setText("Email");

        SupplierPhoneLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SupplierPhoneLabel.setText("Phone");

        AddNewSupplierButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        AddNewSupplierButton.setText("Add new supplier");
        AddNewSupplierButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNewSupplierButtonActionPerformed(evt);
            }
        });

        SupplierUpdateButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SupplierUpdateButton.setText("Update");
        SupplierUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupplierUpdateButtonActionPerformed(evt);
            }
        });

        SupplierPhoneNoTextField.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        javax.swing.GroupLayout SupplierAddUpdatePanelLayout = new javax.swing.GroupLayout(SupplierAddUpdatePanel);
        SupplierAddUpdatePanel.setLayout(SupplierAddUpdatePanelLayout);
        SupplierAddUpdatePanelLayout.setHorizontalGroup(
            SupplierAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SupplierAddUpdatePanelLayout.createSequentialGroup()
                .addGroup(SupplierAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SupplierAddUpdatePanelLayout.createSequentialGroup()
                        .addContainerGap(304, Short.MAX_VALUE)
                        .addComponent(SupplierUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddNewSupplierButton))
                    .addGroup(SupplierAddUpdatePanelLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(SupplierAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SupplierNameLabel)
                            .addComponent(SupplierAddressLabel)
                            .addComponent(SupplierBankNoLabel)
                            .addComponent(SupplierEmailLabel)
                            .addComponent(SupplierPhoneLabel))
                        .addGap(60, 60, 60)
                        .addGroup(SupplierAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SupplierEmailTextField)
                            .addComponent(SupplierNameTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SupplierAddressTextField)
                            .addComponent(SupplierBankNoTextField)
                            .addComponent(SupplierPhoneNoTextField))))
                .addGap(99, 99, 99))
            .addGroup(SupplierAddUpdatePanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(AddUpdateLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SupplierAddUpdatePanelLayout.setVerticalGroup(
            SupplierAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SupplierAddUpdatePanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(AddUpdateLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(SupplierAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SupplierNameTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SupplierNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SupplierAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SupplierAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SupplierAddressLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SupplierAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SupplierBankNoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SupplierBankNoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SupplierAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SupplierEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SupplierEmailLabel))
                .addGap(18, 18, 18)
                .addGroup(SupplierAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SupplierPhoneNoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SupplierPhoneLabel))
                .addGap(37, 37, 37)
                .addGroup(SupplierAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SupplierUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddNewSupplierButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CurrentSupplierPanelLayout = new javax.swing.GroupLayout(CurrentSupplierPanel);
        CurrentSupplierPanel.setLayout(CurrentSupplierPanelLayout);
        CurrentSupplierPanelLayout.setHorizontalGroup(
            CurrentSupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CurrentSupplierPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(CurrentSupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SelectSupplierLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(SupplierAddUpdatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        CurrentSupplierPanelLayout.setVerticalGroup(
            CurrentSupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CurrentSupplierPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(CurrentSupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SupplierAddUpdatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(CurrentSupplierPanelLayout.createSequentialGroup()
                        .addComponent(SelectSupplierLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout SupplierPanelLayout = new javax.swing.GroupLayout(SupplierPanel);
        SupplierPanel.setLayout(SupplierPanelLayout);
        SupplierPanelLayout.setHorizontalGroup(
            SupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1030, Short.MAX_VALUE)
            .addGroup(SupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SupplierPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(CurrentSupplierPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        SupplierPanelLayout.setVerticalGroup(
            SupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
            .addGroup(SupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(CurrentSupplierPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MainPanel.add(SupplierPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 1030, 590));

        ItemResultTable.setModel(new javax.swing.table.DefaultTableModel(
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
        ItemsScrollPane.setViewportView(ItemResultTable);

        AddNewItemPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        AddNewItemPanel.setToolTipText("");
        AddNewItemPanel.setMaximumSize(new java.awt.Dimension(1010, 220));

        RestrictedLabelItem.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        RestrictedLabelItem.setText("Restricted");

        InformationLabelItem.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        InformationLabelItem.setText("Information");

        PriceLabelItem.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        PriceLabelItem.setText("Price");

        CategoryLabelItem.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        CategoryLabelItem.setText("Category");

        ItemNameLabelItem.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        ItemNameLabelItem.setText("Item Name");

        ItemNameTextField.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        CategoryComboBox.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        CategoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CategoryComboBox.setMaximumSize(new java.awt.Dimension(78, 26));

        PriceTextField.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        InformationTextField.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        RestrictedCheckBox.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        SubHeaderLabelItem.setFont(new java.awt.Font("Segoe UI Symbol", 1, 18)); // NOI18N
        SubHeaderLabelItem.setText("Add New Item");

        AddItemButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        AddItemButton.setText("Add");
        AddItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddItemButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddNewItemPanelLayout = new javax.swing.GroupLayout(AddNewItemPanel);
        AddNewItemPanel.setLayout(AddNewItemPanelLayout);
        AddNewItemPanelLayout.setHorizontalGroup(
            AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddNewItemPanelLayout.createSequentialGroup()
                .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddNewItemPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(SubHeaderLabelItem, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddNewItemPanelLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ItemNameLabelItem)
                            .addComponent(CategoryLabelItem)
                            .addComponent(RestrictedLabelItem))
                        .addGap(40, 40, 40)
                        .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(AddNewItemPanelLayout.createSequentialGroup()
                                .addComponent(RestrictedCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(AddItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(AddNewItemPanelLayout.createSequentialGroup()
                                .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ItemNameTextField)
                                    .addComponent(CategoryComboBox, 0, 250, Short.MAX_VALUE))
                                .addGap(92, 92, 92)
                                .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PriceLabelItem)
                                    .addComponent(InformationLabelItem))
                                .addGap(35, 35, 35)
                                .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(PriceTextField)
                                    .addComponent(InformationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        AddNewItemPanelLayout.setVerticalGroup(
            AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddNewItemPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SubHeaderLabelItem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddNewItemPanelLayout.createSequentialGroup()
                        .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ItemNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ItemNameLabelItem))
                        .addGap(18, 18, 18)
                        .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CategoryLabelItem)))
                    .addGroup(AddNewItemPanelLayout.createSequentialGroup()
                        .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PriceLabelItem))
                        .addGap(18, 18, 18)
                        .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(InformationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(InformationLabelItem))))
                .addGroup(AddNewItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddNewItemPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(AddItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddNewItemPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(RestrictedLabelItem))
                    .addGroup(AddNewItemPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(RestrictedCheckBox)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        SearchItemTextfieldItem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SearchItemTextfieldItem.setText("Search Item");
        SearchItemTextfieldItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchItemTextfieldItemMouseClicked(evt);
            }
        });
        SearchItemTextfieldItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchItemTextfieldItemActionPerformed(evt);
            }
        });

        SearchItemButtonItem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SearchItemButtonItem.setText("Search");

        javax.swing.GroupLayout ItemPanelLayout = new javax.swing.GroupLayout(ItemPanel);
        ItemPanel.setLayout(ItemPanelLayout);
        ItemPanelLayout.setHorizontalGroup(
            ItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ItemPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ItemsScrollPane)
                    .addGroup(ItemPanelLayout.createSequentialGroup()
                        .addComponent(SearchItemTextfieldItem, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchItemButtonItem, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                    .addGroup(ItemPanelLayout.createSequentialGroup()
                        .addComponent(AddNewItemPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ItemPanelLayout.setVerticalGroup(
            ItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ItemPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddNewItemPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ItemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SearchItemTextfieldItem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchItemButtonItem, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ItemsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        MainPanel.add(ItemPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 1030, 590));

        SelectSiteLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SelectSiteLabel.setText("Select Site");

        SiteList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(SiteList);

        SiteAddUpdatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        AddUpdateLabel.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        AddUpdateLabel.setText("Add / Update Site Details");

        SiteNameTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        SiteCurrentCapacityTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        SiteManagerComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SiteManagerComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        SiteNameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SiteNameLabel.setText("Site Name");

        SiteAddressLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SiteAddressLabel.setText("Address");

        SiteManagerLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SiteManagerLabel.setText("Site Manager");

        AddNewSiteButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        AddNewSiteButton.setText("Add new site");

        SiteUpdateButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SiteUpdateButton.setText("Update");

        javax.swing.GroupLayout SiteAddUpdatePanelLayout = new javax.swing.GroupLayout(SiteAddUpdatePanel);
        SiteAddUpdatePanel.setLayout(SiteAddUpdatePanelLayout);
        SiteAddUpdatePanelLayout.setHorizontalGroup(
            SiteAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SiteAddUpdatePanelLayout.createSequentialGroup()
                .addGroup(SiteAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SiteAddUpdatePanelLayout.createSequentialGroup()
                        .addContainerGap(334, Short.MAX_VALUE)
                        .addComponent(SiteUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddNewSiteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SiteAddUpdatePanelLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(SiteAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SiteNameLabel)
                            .addComponent(SiteAddressLabel)
                            .addComponent(SiteManagerLabel))
                        .addGap(87, 87, 87)
                        .addGroup(SiteAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SiteNameTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SiteCurrentCapacityTextField)
                            .addComponent(SiteManagerComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(99, 99, 99))
            .addGroup(SiteAddUpdatePanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(AddUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SiteAddUpdatePanelLayout.setVerticalGroup(
            SiteAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SiteAddUpdatePanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(AddUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(SiteAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SiteNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SiteNameLabel))
                .addGap(18, 18, 18)
                .addGroup(SiteAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SiteCurrentCapacityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SiteAddressLabel))
                .addGap(18, 18, 18)
                .addGroup(SiteAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SiteManagerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SiteManagerLabel))
                .addGap(18, 18, 18)
                .addGroup(SiteAddUpdatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SiteUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddNewSiteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(185, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CurrentSitePanelLayout = new javax.swing.GroupLayout(CurrentSitePanel);
        CurrentSitePanel.setLayout(CurrentSitePanelLayout);
        CurrentSitePanelLayout.setHorizontalGroup(
            CurrentSitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CurrentSitePanelLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(CurrentSitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SelectSiteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(SiteAddUpdatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        CurrentSitePanelLayout.setVerticalGroup(
            CurrentSitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CurrentSitePanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(CurrentSitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SiteAddUpdatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(CurrentSitePanelLayout.createSequentialGroup()
                        .addComponent(SelectSiteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout SitePanelLayout = new javax.swing.GroupLayout(SitePanel);
        SitePanel.setLayout(SitePanelLayout);
        SitePanelLayout.setHorizontalGroup(
            SitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1030, Short.MAX_VALUE)
            .addGroup(SitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SitePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(CurrentSitePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        SitePanelLayout.setVerticalGroup(
            SitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 599, Short.MAX_VALUE)
            .addGroup(SitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SitePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(CurrentSitePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        MainPanel.add(SitePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 1030, 590));

        EmployeeListTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(EmployeeListTable);

        SearchEmployeeTextField.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        SearchEmployeeButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SearchEmployeeButton.setText("Search");

        javax.swing.GroupLayout EmployeePanelLayout = new javax.swing.GroupLayout(EmployeePanel);
        EmployeePanel.setLayout(EmployeePanelLayout);
        EmployeePanelLayout.setHorizontalGroup(
            EmployeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployeePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EmployeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(EmployeePanelLayout.createSequentialGroup()
                        .addComponent(SearchEmployeeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchEmployeeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)))
                .addContainerGap())
        );
        EmployeePanelLayout.setVerticalGroup(
            EmployeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EmployeePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EmployeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SearchEmployeeTextField)
                    .addComponent(SearchEmployeeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
        );

        MainPanel.add(EmployeePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 1030, 590));

        javax.swing.GroupLayout PaymentPanelLayout = new javax.swing.GroupLayout(PaymentPanel);
        PaymentPanel.setLayout(PaymentPanelLayout);
        PaymentPanelLayout.setHorizontalGroup(
            PaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1030, Short.MAX_VALUE)
        );
        PaymentPanelLayout.setVerticalGroup(
            PaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );

        MainPanel.add(PaymentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(229, 110, 1030, 590));

        SubPanels.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PaymentTabber.addTab("tab1", SuccessfulPaymentTabber);
        PaymentTabber.addTab("tab1", ManagementTabber);
        PaymentTabber.addTab("tab1", PendingTabber);

        SubPanels.add(PaymentTabber, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 590));

        MainPanel.add(SubPanels, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 1030, 590));

        HeaderPanel.setBackground(new java.awt.Color(255, 255, 255));

        HeaderLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HeaderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(258, Short.MAX_VALUE))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HeaderLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addContainerGap())
        );

        MainPanel.add(HeaderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 11, 870, 90));

        PaymentButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        PaymentButton.setText("Payments");
        PaymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentButtonActionPerformed(evt);
            }
        });
        MainPanel.add(PaymentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 165, 50));

        SupplierButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SupplierButton.setText("Suppliers");
        SupplierButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupplierButtonActionPerformed(evt);
            }
        });
        MainPanel.add(SupplierButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 165, 48));

        SiteButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        SiteButton.setText("Sites");
        SiteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SiteButtonActionPerformed(evt);
            }
        });
        MainPanel.add(SiteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 165, 50));

        ItemButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ItemButton.setText("Items");
        ItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemButtonActionPerformed(evt);
            }
        });
        MainPanel.add(ItemButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 165, 50));

        EmployeeButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        EmployeeButton.setText("Employees");
        EmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeButtonActionPerformed(evt);
            }
        });
        MainPanel.add(EmployeeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 165, 50));

        ApprovalButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ApprovalButton.setText("Approvals");
        ApprovalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApprovalButtonActionPerformed(evt);
            }
        });
        MainPanel.add(ApprovalButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 165, 50));

        MainScrollPane.setViewportView(MainPanel);

        getContentPane().add(MainScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 730));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SearchItemTextfieldItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchItemTextfieldItemMouseClicked
        // TODO add your handling code here:
        this.SearchItemTextfieldItem.setText("");
    }//GEN-LAST:event_SearchItemTextfieldItemMouseClicked

    private void SearchItemTextfieldItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchItemTextfieldItemActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_SearchItemTextfieldItemActionPerformed

    private void PaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentButtonActionPerformed
        // TODO add your handling code here:
        //        changePanel(0);
        changePanel(0);
    }//GEN-LAST:event_PaymentButtonActionPerformed

    private void SupplierButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupplierButtonActionPerformed
        // TODO add your handling code here:
        changePanel(2);
        loadSupplierList();
        this.AddNewSupplierButton.setVisible(true);
        this.supplierSelect = -1;
        this.SupplierNameTextField1.setText("");
        this.SupplierAddressTextField.setText("");
        this.SupplierAddressTextField.setText("");
        this.SupplierBankNoTextField.setText("");
        this.SupplierEmailTextField.setText("");
        this.SupplierPhoneNoTextField.setText("");
    }//GEN-LAST:event_SupplierButtonActionPerformed

    private void SiteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SiteButtonActionPerformed
        // TODO add your handling code here:
        changePanel(4);
        loadSiteList();
        loadSiteManagerComboBox();
    }//GEN-LAST:event_SiteButtonActionPerformed

    private void ItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemButtonActionPerformed
        // TODO add your handling code here:
        changePanel(1);
        loadItemResultTable();
        loadItemCategoryComboBox();
    }//GEN-LAST:event_ItemButtonActionPerformed

    private void EmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeButtonActionPerformed
        // TODO add your handling code here:
        changePanel(3);
        loadEmployeeListTable();
    }//GEN-LAST:event_EmployeeButtonActionPerformed

    private void ApprovalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApprovalButtonActionPerformed
        // TODO add your handling code here:
        changePanel(5);
        loadApproveListTable();
        loadSelectSupplierComboBox();
        this.purcaseOrderID = null;
        this.purchaseOrderStatus = null;
        this.purchaseOrderSupplier = null;
        try {
            this.tempSupplierList = procurementFacade.getValues("http://localhost:9000/supplier");
        } catch (JSONException e) {
            JOptionPane.showMessageDialog(this, "JSON Error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ApprovalButtonActionPerformed

    private void SetSupplierApproveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetSupplierApproveButtonActionPerformed
        // TODO add your handling code here:
        String val = null;
        try {
            if (this.purcaseOrderID != null) {
                if (this.purchaseOrderStatus != null) {
                    if (this.purchaseOrderSupplier != null) {
                        try {
//                            JSONArray arr = procurementFacade.getValues("http://localhost:9000/supplier");
                            JSONArray arr = this.tempSupplierList;

                            for (int x = 0; x < arr.length(); x++) {
                                //            System.out.println(arr.getJSONObject(x).toString());
                                if (arr.getJSONObject(x).getString("name").equals(this.purchaseOrderSupplier)) {
                                    System.out.println(arr.getJSONObject(x).get("id"));
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            try {
                                e.wait(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ManagerDashBoard.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

//                        val = procurementFacade.setApproveOrder(Integer.parseInt(this.purcaseOrderID), this.purchaseOrderStatus,
//                                "mg1", this.SelectSupplierComboBoxApproval.);
                        System.out.println("return val " + val);
                        if (val != null) {
                            JOptionPane.showMessageDialog(this, "Purchase order details", "Success", JOptionPane.DEFAULT_OPTION);
                            this.purcaseOrderID = null;
                            this.purchaseOrderStatus = null;
                            this.purchaseOrderSupplier = null;
                            this.ApprovalButton.doClick();
                        } else {
                            JOptionPane.showMessageDialog(this, "Select Supplier", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Select Status", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Select Purchase Order", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        procurementFacade.setApproveOrder(Integer.parseInt(this.purcaseOrderID), this.purchaseOrderStatus, 11, this.purchaseOrderSupplier);
    }//GEN-LAST:event_SetSupplierApproveButtonActionPerformed

    private void AddNewSupplierButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNewSupplierButtonActionPerformed
        // TODO add your handling code here:
        String val = null;
        try {
            if (!this.SupplierNameTextField1.getText().isEmpty()) {
                if (!this.SupplierAddressTextField.getText().isEmpty()) {
                    if (!this.SupplierAddressTextField.getText().isEmpty()) {
                        if (!this.SupplierBankNoTextField.getText().isEmpty()
                                && checkDigit(this.SupplierBankNoTextField.getText())) {
                            if (!this.SupplierEmailTextField.getText().isEmpty()
                                    && this.SupplierEmailTextField.getText().contains("@")) {
                                if (!this.SupplierPhoneNoTextField.getText().isEmpty()) {
                                    System.out.println("Validation Pass");
                                    val = procurementFacade.addNewSupplier(this.SupplierNameTextField1.getText(), this.SupplierAddressTextField.getText(),
                                            Integer.parseInt(this.SupplierPhoneNoTextField.getText()), this.SupplierEmailTextField.getText(),
                                            Integer.parseInt(this.SupplierBankNoTextField.getText()));
                                    System.out.println("return val " + val);
                                    if (val != null) {
                                        JOptionPane.showMessageDialog(this, "Supplier Added", "Success", JOptionPane.DEFAULT_OPTION);
                                        this.SupplierNameTextField1.setText("");
                                        this.SupplierAddressTextField.setText("");
                                        this.SupplierAddressTextField.setText("");
                                        this.SupplierBankNoTextField.setText("");
                                        this.SupplierEmailTextField.setText("");
                                        this.SupplierPhoneNoTextField.setText("");
                                        this.SupplierButton.doClick();
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Provide Phone Number");
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Provide Email Address");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Provide Bank Number");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Provide Address");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Provide Address");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Provide Name");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Format", "Warnig", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_AddNewSupplierButtonActionPerformed

    private void AddItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddItemButtonActionPerformed
        // TODO add your handling code here:
        boolean check = false;
        try {
            if (this.ItemNameTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Item Name");
            } else if (this.PriceTextField.getText().isEmpty() && checkDigit(this.PriceTextField.getText())) {
                JOptionPane.showMessageDialog(this, "Enter valid price");
            } else {
                check = true;
            }

            if (check) {
                procurementFacade.postNewItem(this.ItemNameTextField.getText(),
                        Float.parseFloat(this.PriceTextField.getText()),
                        this.SelectSupplierComboBoxApproval.getSelectedItem().toString());
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Fill the requirements");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Provide corrent details");
        }

    }//GEN-LAST:event_AddItemButtonActionPerformed

    private void SupplierUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupplierUpdateButtonActionPerformed
        // TODO add your handling code here:
        String val = null;
        try {
            if (this.supplierSelect != -1) {
                if (!this.SupplierNameTextField1.getText().isEmpty()) {
                    if (!this.SupplierAddressTextField.getText().isEmpty()) {
                        if (!this.SupplierAddressTextField.getText().isEmpty()) {
                            if (!this.SupplierBankNoTextField.getText().isEmpty()
                                    && checkDigit(this.SupplierBankNoTextField.getText())) {
                                if (!this.SupplierEmailTextField.getText().isEmpty()
                                        && this.SupplierEmailTextField.getText().contains("@")) {
                                    if (!this.SupplierPhoneNoTextField.getText().isEmpty()) {
                                        val = procurementFacade.updateSupplierDetails(this.supplierSelect, this.SupplierNameTextField1.getText(), this.SupplierAddressTextField.getText(),
                                                Integer.parseInt(this.SupplierPhoneNoTextField.getText()), this.SupplierEmailTextField.getText(),
                                                Integer.parseInt(this.SupplierBankNoTextField.getText()));
                                        System.out.println("Validation Pass");
                                        System.out.println("return val " + val);
                                        if (val != null) {
                                            JOptionPane.showMessageDialog(this, "Supplier Updated", "Success", JOptionPane.DEFAULT_OPTION);
                                            this.supplierSelect = -1;
                                            this.SupplierNameTextField1.setText("");
                                            this.SupplierAddressTextField.setText("");
                                            this.SupplierAddressTextField.setText("");
                                            this.SupplierBankNoTextField.setText("");
                                            this.SupplierEmailTextField.setText("");
                                            this.SupplierPhoneNoTextField.setText("");
                                            this.SupplierButton.doClick();
                                        }

                                    } else {
                                        JOptionPane.showMessageDialog(this, "Provide Phone Number");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Provide Email Address");
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Provide Bank Number");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Provide Address");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Provide Address");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Provide Name");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select Supplier");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Format", "Warnig", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_SupplierUpdateButtonActionPerformed

    private void SupplierListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_SupplierListValueChanged
        // TODO add your handling code here:
        JSONObject obj;
        try {
//            System.out.println(this.supplierSelect);
            //        if (this.supplierSelect != -1) {
            //            this.AddNewSupplierButton.setVisible(false);
            //        }
            this.supplierSelect = this.SupplierList.getSelectedIndex() + 1;
//            System.out.println(this.supplierSelect);
            if (this.supplierSelect != -1) {
                obj = procurementFacade.getSingleValue("http://localhost:9000/supplier/" + Integer.toString(this.supplierSelect));
                System.out.println(obj.toString());
                this.SupplierNameTextField1.setText(obj.getString("name"));
                this.SupplierAddressTextField.setText(obj.getString("address"));
                this.SupplierBankNoTextField.setText(obj.getString("bankAccountNumber"));
                this.SupplierEmailTextField.setText(obj.getString("email"));
                this.SupplierPhoneNoTextField.setText(obj.getString("phoneNumber"));
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }//GEN-LAST:event_SupplierListValueChanged

    private void ApprovelListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ApprovelListTableMouseClicked
        // TODO add your handling code here:
        try {
            this.purcaseOrderID = this.ApprovelListTable.getModel().getValueAt(this.ApprovelListTable.getSelectedRow(), 0).toString();
        } catch (Exception e) {

        }
//        System.out.println(this.purcaseOrderID);
    }//GEN-LAST:event_ApprovelListTableMouseClicked

    private void PurchaseOrderStatusComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PurchaseOrderStatusComboBoxItemStateChanged
        // TODO add your handling code here:
//        this.purchaseOrderStatus = this.PurchaseOrderStatusComboBox.getSelectedItem().toString();
//        System.out.println(this.purchaseOrderStatus);
    }//GEN-LAST:event_PurchaseOrderStatusComboBoxItemStateChanged

    private void SelectSupplierComboBoxApprovalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SelectSupplierComboBoxApprovalItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_SelectSupplierComboBoxApprovalItemStateChanged

    private void SelectSupplierComboBoxApprovalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_SelectSupplierComboBoxApprovalPropertyChange
        // TODO add your handling code here:
        try {
            this.purchaseOrderSupplier = tempSupplierList.getJSONObject(this.SelectSupplierComboBoxApproval.getSelectedIndex());
            System.out.println(this.purchaseOrderSupplier.getInt("id") + this.purchaseOrderSupplier.getString("name"));
        } catch (NullPointerException e) {
        }
    }//GEN-LAST:event_SelectSupplierComboBoxApprovalPropertyChange

    private void PurchaseOrderStatusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PurchaseOrderStatusComboBoxActionPerformed
        // TODO add your handling code here:
        this.purchaseOrderStatus = this.PurchaseOrderStatusComboBox.getSelectedItem().toString();
        System.out.println(this.purchaseOrderStatus);
    }//GEN-LAST:event_PurchaseOrderStatusComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(ManagerDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManagerDashBoard().setVisible(true);
            }
        });
    }

    private void changePanel(int id) {
        switch (id) {
            case 0:
                this.HeaderLabel.setText("Payment");
                this.PaymentPanel.setVisible(true);
                this.ItemPanel.setVisible(false);
                this.SupplierPanel.setVisible(false);
                this.EmployeePanel.setVisible(false);
                this.SitePanel.setVisible(false);
                this.PaymentTabber.setVisible(false);
                this.ApprovelPanel.setVisible(false);
                break;
            case 1:
                this.HeaderLabel.setText("Item");
                this.PaymentPanel.setVisible(false);
                this.ItemPanel.setVisible(true);
                this.SupplierPanel.setVisible(false);
                this.EmployeePanel.setVisible(false);
                this.SitePanel.setVisible(false);
                this.PaymentTabber.setVisible(false);
                this.ApprovelPanel.setVisible(false);
                break;
            case 2:
                this.HeaderLabel.setText("Supplier");
                this.PaymentPanel.setVisible(false);
                this.ItemPanel.setVisible(false);
                this.SupplierPanel.setVisible(true);
                this.EmployeePanel.setVisible(false);
                this.SitePanel.setVisible(false);
                this.PaymentTabber.setVisible(false);
                this.ApprovelPanel.setVisible(false);
                break;
            case 3:
                this.HeaderLabel.setText("Employee");
                this.PaymentPanel.setVisible(false);
                this.ItemPanel.setVisible(false);
                this.SupplierPanel.setVisible(false);
                this.EmployeePanel.setVisible(true);
                this.SitePanel.setVisible(false);
                this.PaymentTabber.setVisible(false);
                this.ApprovelPanel.setVisible(false);
                break;
            case 4:
                this.HeaderLabel.setText("Site");
                this.PaymentPanel.setVisible(false);
                this.ItemPanel.setVisible(false);
                this.SupplierPanel.setVisible(false);
                this.EmployeePanel.setVisible(false);
                this.SitePanel.setVisible(true);
                this.PaymentTabber.setVisible(false);
                this.ApprovelPanel.setVisible(false);
                break;
            case 5:
                this.HeaderLabel.setText("Approval");
                this.PaymentPanel.setVisible(false);
                this.ItemPanel.setVisible(false);
                this.SupplierPanel.setVisible(false);
                this.EmployeePanel.setVisible(false);
                this.SitePanel.setVisible(false);
                this.PaymentTabber.setVisible(false);
                this.ApprovelPanel.setVisible(true);
                break;
            default:
                System.out.println(id);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddItemButton;
    private javax.swing.JPanel AddNewItemPanel;
    private javax.swing.JButton AddNewSiteButton;
    private javax.swing.JButton AddNewSupplierButton;
    private javax.swing.JLabel AddUpdateLabel;
    private javax.swing.JLabel AddUpdateLabel1;
    private javax.swing.JButton ApprovalButton;
    private javax.swing.JTable ApprovelListTable;
    private javax.swing.JPanel ApprovelPanel;
    private javax.swing.JComboBox<String> CategoryComboBox;
    private javax.swing.JLabel CategoryLabelItem;
    private javax.swing.JPanel CurrentSitePanel;
    private javax.swing.JPanel CurrentSupplierPanel;
    private javax.swing.JButton EmployeeButton;
    private javax.swing.JTable EmployeeListTable;
    private javax.swing.JPanel EmployeePanel;
    private javax.swing.JLabel HeaderLabel;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JLabel InformationLabelItem;
    private javax.swing.JTextField InformationTextField;
    private javax.swing.JButton ItemButton;
    private javax.swing.JLabel ItemNameLabelItem;
    private javax.swing.JTextField ItemNameTextField;
    private javax.swing.JPanel ItemPanel;
    private javax.swing.JTable ItemResultTable;
    private javax.swing.JScrollPane ItemsScrollPane;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JScrollPane MainScrollPane;
    private javax.swing.JTabbedPane ManagementTabber;
    private javax.swing.JButton PaymentButton;
    private javax.swing.JPanel PaymentPanel;
    private javax.swing.JTabbedPane PaymentTabber;
    private javax.swing.JTabbedPane PendingTabber;
    private javax.swing.JLabel PriceLabelItem;
    private javax.swing.JTextField PriceTextField;
    private javax.swing.JLabel PurchaseOrderLabel;
    private javax.swing.JComboBox<String> PurchaseOrderStatusComboBox;
    private javax.swing.JCheckBox RestrictedCheckBox;
    private javax.swing.JLabel RestrictedLabelItem;
    private javax.swing.JButton SearchEmployeeButton;
    private javax.swing.JTextField SearchEmployeeTextField;
    private javax.swing.JButton SearchItemButtonItem;
    private javax.swing.JTextField SearchItemTextfieldItem;
    private javax.swing.JLabel SelectSiteLabel;
    private javax.swing.JComboBox<String> SelectSupplierComboBoxApproval;
    private javax.swing.JLabel SelectSupplierLabel;
    private javax.swing.JLabel SelectSupplierLabelApproval;
    private javax.swing.JButton SetSupplierApproveButton;
    private javax.swing.JPanel SiteAddUpdatePanel;
    private javax.swing.JLabel SiteAddressLabel;
    private javax.swing.JButton SiteButton;
    private javax.swing.JTextField SiteCurrentCapacityTextField;
    private javax.swing.JList<String> SiteList;
    private javax.swing.JComboBox<String> SiteManagerComboBox;
    private javax.swing.JLabel SiteManagerLabel;
    private javax.swing.JLabel SiteNameLabel;
    private javax.swing.JTextField SiteNameTextField;
    private javax.swing.JPanel SitePanel;
    private javax.swing.JButton SiteUpdateButton;
    private javax.swing.JLabel SubHeaderLabelItem;
    private javax.swing.JLayeredPane SubPanels;
    private javax.swing.JTabbedPane SuccessfulPaymentTabber;
    private javax.swing.JPanel SupplierAddUpdatePanel;
    private javax.swing.JLabel SupplierAddressLabel;
    private javax.swing.JTextField SupplierAddressTextField;
    private javax.swing.JLabel SupplierBankNoLabel;
    private javax.swing.JTextField SupplierBankNoTextField;
    private javax.swing.JButton SupplierButton;
    private javax.swing.JLabel SupplierEmailLabel;
    private javax.swing.JTextField SupplierEmailTextField;
    private javax.swing.JList<String> SupplierList;
    private javax.swing.JLabel SupplierNameLabel;
    private javax.swing.JTextField SupplierNameTextField1;
    private javax.swing.JPanel SupplierPanel;
    private javax.swing.JLabel SupplierPhoneLabel;
    private javax.swing.JTextField SupplierPhoneNoTextField;
    private javax.swing.JButton SupplierUpdateButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
