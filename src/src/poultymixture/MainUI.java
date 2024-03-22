/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poultymixture;
import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

import org.jacop.constraints.PrimitiveConstraint;
import org.jacop.core.Domain;
import org.jacop.core.Store;
import org.jacop.core.Var;
import org.jacop.constraints.*;
import org.jacop.core.IntVar;
import org.jacop.floats.constraints.LinearFloat;
import org.jacop.floats.core.FloatDomain;

import org.jacop.floats.core.FloatVar;
import org.jacop.floats.search.SplitSelectFloat;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.PrintOutListener;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;
import org.jacop.search.SimpleSolutionListener;
import org.jacop.search.SolutionListener;

/**
 *
 * @author Yasser
 */
public class MainUI extends javax.swing.JFrame{


    
    /**
     * Creates new form MainUI
     */
    int elementLabelRowIndex=1;
    int elementLabelColIndex=4;
    int dataRowIndex=3;
    int dataColIndex=4;
    int nameRowIndex=3;
    int nameColIndex=1;
    int elementCount=45;
    int materialCount=60;
    
    int weightAccuracy=0;
    int elementAccuracy=0;
    int timeoutSeconds=0;
    String filename;
    protected long seed = 29091981;//29091981
    
    boolean searching=false;
    Thread solverThread=null;
    Thread updateThread=null;
    ArrayList<SolutionInstance> solutions;
    
    int factor=1;
    Store store;
    
    IntVar []weights;
    IntVar []substance;
    IntVar [][]scales;
    IntVar []weightByPrice;
    IntVar objective;
    int lastObjective=-1;
    int solutionCount=0;
    public MainUI() {
        
        initComponents();
        loadData();
    }
    
    public void loadData(){
        filename="/data/data.xls";
        try {
            InputStream excelFile = MainUI.class.getResourceAsStream(filename);
            Workbook workbook=new HSSFWorkbook(excelFile);
            excelFile.close();
          //  Workbook workbook=new XSSFWorkbook(excelFile);
            Sheet sheet=workbook.getSheetAt(0);

            DefaultTableModel model=(DefaultTableModel)tableData.getModel();
            DefaultTableModel elementsModel=(DefaultTableModel)tableElements.getModel();
            DefaultTableModel materialsModel=(DefaultTableModel)tableMaterials.getModel();
            model.addColumn("اسم المادة");
         //   tableData.getColumnModel().getColumn(0).setWidth(100);
            model.addColumn("رقمها الدولي");
            model.addColumn("المادة الجافة");
         //   tableData.getColumnModel().getColumn(1).setWidth(100);
            Row elementLabels=sheet.getRow(elementLabelRowIndex);
            for(int col=0;col<elementCount;col++){
                model.addColumn(elementLabels.getCell(col+elementLabelColIndex).getStringCellValue());
              //  tableData.getColumnModel().getColumn(col).setPreferredWidth(15);
                elementsModel.addRow(new Object[]{elementLabels.getCell(col+elementLabelColIndex).getStringCellValue(),(double)0});
            }
            
            for(int row=0;row<materialCount;row++){
                Object[] objects=new Object[elementCount+3];
                Row dataRow=sheet.getRow(row+dataRowIndex);
                double min=0;
                double max=0;
                min=dataRow.getCell(elementCount+elementLabelColIndex+1).getNumericCellValue();
                max=dataRow.getCell(elementCount+elementLabelColIndex+2).getNumericCellValue();
                
                
                objects[0]=dataRow.getCell(1).getStringCellValue();
                objects[1]=dataRow.getCell(2).getStringCellValue();
                objects[2]=dataRow.getCell(3).getNumericCellValue();
                materialsModel.addRow(new Object[]{dataRow.getCell(1).getStringCellValue(),(double)0,dataRow.getCell(elementCount+elementLabelColIndex).getNumericCellValue(),false,min,max});
                for(int col=0;col<elementCount;col++){
                    objects[col+3]=dataRow.getCell(col+dataColIndex).getNumericCellValue();
                }
                model.addRow(objects);
            }
          //
            
            workbook.close();
            excelFile.close();
        } catch (Exception ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotalPrice = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblWeight = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableMaterials = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        lblKgPrice = new javax.swing.JLabel();
        checkIncludeAll = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        lblSolutionCount = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableElements = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnSolve = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnResetExcept5andMaterialsSelection = new javax.swing.JButton();
        btnSavePricesAndAmounts = new javax.swing.JButton();
        btnStopSearch = new javax.swing.JButton();
        btnManualUpdate = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableData = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        spinOptionWeightAccuracy = new javax.swing.JSpinner();
        spinOptionEelemntPercentAccuracy = new javax.swing.JSpinner();
        txtOptionTimeoutLimit = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtOptionMaxKgPrice = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("التكلفة الكلية:");

        lblTotalPrice.setForeground(new java.awt.Color(255, 102, 0));
        lblTotalPrice.setText("ل.س");

        jLabel3.setText("وزن الخلطة:");

        lblWeight.setText("كغ");

        tableMaterials.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "اسم المادة", "الكمية", "سعر الكيلو", "متوفر؟", "حد ادنى", "حد اعلى"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableMaterials);
        if (tableMaterials.getColumnModel().getColumnCount() > 0) {
            tableMaterials.getColumnModel().getColumn(0).setMinWidth(300);
            tableMaterials.getColumnModel().getColumn(0).setPreferredWidth(300);
        }

        jLabel5.setText("سعر الكيلو:");

        lblKgPrice.setForeground(new java.awt.Color(0, 102, 204));
        lblKgPrice.setText("ل.س");

        checkIncludeAll.setText("كل المواد مشمولة");
        checkIncludeAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkIncludeAllActionPerformed(evt);
            }
        });

        jLabel11.setText("عدد الحلول:");

        lblSolutionCount.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblKgPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(lblSolutionCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(checkIncludeAll)
                        .addGap(18, 18, 18)
                        .addComponent(lblWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTotalPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblTotalPrice)
                    .addComponent(jLabel5)
                    .addComponent(lblKgPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblWeight)
                    .addComponent(checkIncludeAll)
                    .addComponent(jLabel11)
                    .addComponent(lblSolutionCount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("ادخل قيم المواد الاهم ليتم موازنة الخلطة النهائية");

        jLabel8.setText("كافة الحقول الفارغة ستحسب تلقائيا");

        tableElements.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "اسم العنصر", "الكمية"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableElements);
        if (tableElements.getColumnModel().getColumnCount() > 0) {
            tableElements.getColumnModel().getColumn(0).setMinWidth(100);
            tableElements.getColumnModel().getColumn(0).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("من الممكن ان لا يوجد حل للمسالة عندها يجب تغيير ");

        jLabel10.setText("بعض كميات العناصر المطلوبة, و ممكن تواجد اكثر من حل");

        btnSolve.setText("ابحث عن افضل حل ممكن");
        btnSolve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolveActionPerformed(evt);
            }
        });

        btnPrint.setText("اطبع الحل الناتج");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnClear.setText("ابدا مسالة جديدة");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnResetExcept5andMaterialsSelection.setText("مسالة جديدة+حافظ على المواد المختارة");
        btnResetExcept5andMaterialsSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetExcept5andMaterialsSelectionActionPerformed(evt);
            }
        });

        btnSavePricesAndAmounts.setText("احفظ الاسعار و حدود الكميات المسموحة");
        btnSavePricesAndAmounts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavePricesAndAmountsActionPerformed(evt);
            }
        });

        btnStopSearch.setText("اوقف البحث");
        btnStopSearch.setEnabled(false);
        btnStopSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopSearchActionPerformed(evt);
            }
        });

        btnManualUpdate.setText("تحديث يدوي");
        btnManualUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManualUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSolve, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnResetExcept5andMaterialsSelection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSavePricesAndAmounts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnStopSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnManualUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSolve, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStopSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResetExcept5andMaterialsSelection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSavePricesAndAmounts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnManualUpdate)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("تشكيل خلطة", jPanel1);

        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableData.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tableData);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1153, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("جدول المعطيات", jPanel2);

        jLabel2.setText("الحد الاعلى لوقت البحث (بالثانية) :");

        jLabel4.setText("دقة الوزن النهائي للخلطة (كغ) من اصل 1000 : ");

        jLabel6.setText("دقة القيم المدخلة للعناصر (نسبة %) :");

        spinOptionWeightAccuracy.setModel(new javax.swing.SpinnerNumberModel(3, 0, 5, 1));

        spinOptionEelemntPercentAccuracy.setModel(new javax.swing.SpinnerNumberModel(2, 0, 10, 1));

        txtOptionTimeoutLimit.setText("0");

        jLabel12.setText("القيمة 0 معناها لا يوجد حد زمني, عندها يجب ايقاف البرانامج بشكل يدوي او الانتظار حتى ايجاد كافة الحلول");

        jLabel13.setText("زيادة او نقصان, اي كم كغ مسموح كهامش حول قيمة الخلطة المسموح بها 1000كغ");

        jLabel14.setText("تمثل نسبة مئوية من اصل قيمة اي عنصر, مثلا 2% خطا مسموح بالنسبة لقيمة البروتين المطلوبة");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("الحد الاعلى لسعر الكيلو غرام");

        txtOptionMaxKgPrice.setText("2000");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(340, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinOptionWeightAccuracy, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOptionTimeoutLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtOptionMaxKgPrice, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spinOptionEelemntPercentAccuracy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOptionTimeoutLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spinOptionWeightAccuracy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(spinOptionEelemntPercentAccuracy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtOptionMaxKgPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 332, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("خيارات الحل", jPanel6);

        fileMenu.setText("File");

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        editMenu.setText("Edit");
        jMenuBar1.add(editMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setText("about");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkIncludeAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkIncludeAllActionPerformed
        DefaultTableModel model=(DefaultTableModel)tableMaterials.getModel();
        if(checkIncludeAll.isSelected()){ 
            for(int i=0;i<model.getRowCount();i++)
                model.setValueAt(true, i, 3);
        }
        else
            for(int i=0;i<model.getRowCount();i++)
                model.setValueAt(false, i, 3); 
    }//GEN-LAST:event_checkIncludeAllActionPerformed

    private void btnSolveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolveActionPerformed
        if(searching==true){
            JOptionPane.showMessageDialog(this, "You Must Stop Searching First");
            return;
        }
        searching=true;
        solutionCount=0;
        weightAccuracy=(int)spinOptionWeightAccuracy.getValue();
        elementAccuracy=(int)spinOptionEelemntPercentAccuracy.getValue();
        if(!txtOptionTimeoutLimit.getText().isEmpty())
            timeoutSeconds=Integer.parseInt(txtOptionTimeoutLimit.getText());
     //   solutions=new ArrayList<SolutionInstance>();
        btnSolve.setEnabled(false);
        btnPrint.setEnabled(false);
        btnClear.setEnabled(false);
        btnResetExcept5andMaterialsSelection.setEnabled(false);
        btnSavePricesAndAmounts.setEnabled(false);
        btnStopSearch.setEnabled(true);     
        
        solverThread=new Thread(){
            public void run(){
                
                createModel(); 
            }
        };
        solverThread.start();

    }//GEN-LAST:event_btnSolveActionPerformed
    
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        DefaultTableModel modelMaterials=(DefaultTableModel)tableMaterials.getModel();
        DefaultTableModel modelElements=(DefaultTableModel)tableElements.getModel();
        lblKgPrice.setText("");
        lblTotalPrice.setText("");
        lblWeight.setText("");
        lblSolutionCount.setText("");
        checkIncludeAll.setSelected(false);
        for(int i=0;i<materialCount;i++){
            modelMaterials.setValueAt(0d, i, 1);
            modelMaterials.setValueAt(false, i, 3);
        }
        for(int j=0;j<elementCount;j++){
            modelElements.setValueAt(0d, j, 1);
        }
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnResetExcept5andMaterialsSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetExcept5andMaterialsSelectionActionPerformed
        DefaultTableModel modelMaterials=(DefaultTableModel)tableMaterials.getModel();
        DefaultTableModel modelElements=(DefaultTableModel)tableElements.getModel();
        lblKgPrice.setText("");
        lblTotalPrice.setText("");
        lblWeight.setText("");
        lblSolutionCount.setText("");
        checkIncludeAll.setSelected(false);
        for(int i=0;i<materialCount;i++)
            modelMaterials.setValueAt(0d, i, 1);
        
        for(int j=0;j<elementCount;j++){
            modelElements.setValueAt(0d, j, 1);
        }
        lastObjective=-1;
    }//GEN-LAST:event_btnResetExcept5andMaterialsSelectionActionPerformed

    private void btnSavePricesAndAmountsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePricesAndAmountsActionPerformed
        InputStream excelFile = MainUI.class.getResourceAsStream(filename);
        Workbook workbook;
        try {
            workbook = new HSSFWorkbook(excelFile);
            excelFile.close();
            //  Workbook workbook=new XSSFWorkbook(excelFile);
            Sheet sheet=workbook.getSheetAt(0);
            for(int i=0;i<materialCount;i++){
                DefaultTableModel model=(DefaultTableModel)tableMaterials.getModel();
                double price=(double)model.getValueAt(i,2);
                double min=0;
                double max=0;
                if(model.getValueAt(i, 4)!=null)
                    min=(double)model.getValueAt(i, 4);
                if(model.getValueAt(i, 5)!=null)
                    max=(double)model.getValueAt(i, 5);
                Row dataRow=sheet.getRow(i+dataRowIndex);
                Cell minCell=dataRow.createCell(elementCount+elementLabelColIndex+1, CellType.NUMERIC);
                Cell maxCell=dataRow.createCell(elementCount+elementLabelColIndex+2,CellType.NUMERIC);
                Cell priceCell=dataRow.createCell(elementCount+elementLabelColIndex,CellType.NUMERIC);
                
                minCell.setCellValue(min);
                maxCell.setCellValue(max);
                priceCell.setCellValue(price);
            }
            FileOutputStream fs=new FileOutputStream(this.getClass().getResource(filename).getPath());
            workbook.write(fs);
            workbook.close();
            fs.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "couldnt write the content back");
            return;
        }
        JOptionPane.showMessageDialog(this, "content updated successfully");
    }//GEN-LAST:event_btnSavePricesAndAmountsActionPerformed

    private void btnStopSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopSearchActionPerformed
        if(searching){
            try{
                searching=false;      
                lastObjective=-1;
                btnSolve.setEnabled(true);
                btnPrint.setEnabled(true);
                btnClear.setEnabled(true);
                btnResetExcept5andMaterialsSelection.setEnabled(true);
                btnSavePricesAndAmounts.setEnabled(true);
                btnStopSearch.setEnabled(false);
                solverThread.stop();
            }
            catch(Exception e){
                System.out.println("thread killing error");
            }
        }
    }//GEN-LAST:event_btnStopSearchActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        (new About()).setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
       // this.dispatchEvent(new java.awt.event.WindowEvent(this, java.awt.event.WindowEvent.WINDOW_CLOSING));
        this.dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        DefaultTableModel subModel=new DefaultTableModel();
        DefaultTableModel materialsModel=(DefaultTableModel)tableMaterials.getModel();
        DefaultTableModel elementsModel=(DefaultTableModel)tableElements.getModel();
            
      //  subModel.addColumn("اسم المادة");
        subModel.addColumn("name");
      //  subModel.addColumn("الوزن");
        subModel.addColumn("weight");
      //  subModel.addColumn("السعر الافرادي");
        subModel.addColumn("price");
        //element model will remain the same
        for(int i=0;i<materialCount;i++){
            if((boolean)materialsModel.getValueAt(i, 3)==true){
                String name=(String)materialsModel.getValueAt(i, 0);
                String weight=((double)materialsModel.getValueAt(i, 1))+"";
                String price=((double)materialsModel.getValueAt(i, 2))+"";
                subModel.addRow(new Object[]{name,weight,price});
            }
        }
        DefaultTableModel combinedModel=new DefaultTableModel();
        
        combinedModel.addColumn("name");
        combinedModel.addColumn("weight");
        combinedModel.addColumn("price");
        combinedModel.addColumn("elementName");
        combinedModel.addColumn("elementValue");
        combinedModel.addColumn("totalWeight");
        combinedModel.addColumn("totalPrice");
        combinedModel.addColumn("pricePerKg");
        
        if(subModel.getRowCount()>elementsModel.getRowCount()){
            Object row[];
            for(int i=0;i<subModel.getRowCount();i++){
                if(i>=elementsModel.getRowCount()){
                    row=new Object[]{(String)subModel.getValueAt(i, 0),(String)subModel.getValueAt(i, 1)
                                    ,(String)subModel.getValueAt(i, 2),"",""};
                }
                else
                    row=new Object[]{(String)subModel.getValueAt(i, 0),(String)subModel.getValueAt(i, 1)
                                    ,(String)subModel.getValueAt(i, 2),(String)elementsModel.getValueAt(i, 0)
                                    ,((double)elementsModel.getValueAt(i, 1))+""};
                combinedModel.addRow(row);
            }
        }
        else{
            Object row[];
            for(int i=0;i<elementsModel.getRowCount();i++){
                if(i>=subModel.getRowCount()){
                    row=new Object[]{"","","",(String)elementsModel.getValueAt(i, 0),((double)elementsModel.getValueAt(i, 1))+""};
                }
                else
                    row=new Object[]{(String)subModel.getValueAt(i, 0),(String)subModel.getValueAt(i, 1)
                                    ,(String)subModel.getValueAt(i, 2),(String)elementsModel.getValueAt(i, 0)
                                    ,((double)elementsModel.getValueAt(i, 1))+""};
                combinedModel.addRow(row);
            }
        }
        
        JasperPrint jasperPrint = null;
        try{
            JasperCompileManager.compileReportToFile("reports/report.jrxml","reports/report.jasper");
            HashMap map=new HashMap();
            map.put("totalWeight",lblWeight.getText()+"كغ");
            map.put("totalPrice", lblTotalPrice.getText()+" ل.س");
            map.put("pricePerKg", lblKgPrice.getText()+"ل.س");
            jasperPrint = JasperFillManager.fillReport("reports/report.jasper", map,
                    new JRTableModelDataSource(combinedModel));
            JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
            jasperViewer.setVisible(true);
            jasperViewer.setTitle("طباعة الخلطة");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
  
        
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnManualUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManualUpdateActionPerformed

            DefaultTableModel materialsModel=(DefaultTableModel)tableMaterials.getModel();
            DefaultTableModel elementsModel=(DefaultTableModel)tableElements.getModel();
            DefaultTableModel dataModel=(DefaultTableModel)tableData.getModel();
            double weight=0;
            double pricePerKg=0;
            double totalPrice=0;
             double []current_weights=new double[materialCount];
             double []current_substances=new double[elementCount];
             for(int i=0;i<materialCount;i++){
                 current_weights[i]=(double)materialsModel.getValueAt(i, 1);
                 weight+=current_weights[i];
                 totalPrice+=current_weights[i]*(double)materialsModel.getValueAt(i, 2);
             }
             pricePerKg=totalPrice/weight;
             for(int j=0;j<elementCount;j++){
                     double elementSum=0;
                     for(int i=0;i<materialCount;i++)
                         elementSum+=current_weights[i]*(double)dataModel.getValueAt(i,j+3);
                     elementsModel.setValueAt(elementSum/weight, j, 1);
             }
             lblKgPrice.setText(""+pricePerKg);
             lblTotalPrice.setText(""+totalPrice);
             lblWeight.setText(""+weight);
             lblSolutionCount.setText(""+solutionCount);
                 // TODO add your handling code here:
    }//GEN-LAST:event_btnManualUpdateActionPerformed
    
    private void getSolutionUpdates(){
         int price=objective.value();
         if(price!=lastObjective){ //update GUI
            solutionCount++;
            DefaultTableModel materialsModel=(DefaultTableModel)tableMaterials.getModel();
            DefaultTableModel elementsModel=(DefaultTableModel)tableElements.getModel();
            DefaultTableModel dataModel=(DefaultTableModel)tableData.getModel();
            double weight=0;
            double pricePerKg=0;
            double totalPrice=0;
             lastObjective=price;
            double []current_weights=new double[materialCount];
            double []current_substances=new double[elementCount];
            for(int i=0;i<materialCount;i++){
                 int w=weights[i].value();
                 current_weights[i]=(double)w/(double)factor;
                 weight+=current_weights[i];
                 totalPrice+=current_weights[i]*(double)materialsModel.getValueAt(i, 2);
                 materialsModel.setValueAt(current_weights[i], i, 1);
             }
             pricePerKg=totalPrice/weight;
             for(int j=0;j<elementCount;j++){
                     double elementSum=0;
                     for(int i=0;i<materialCount;i++)
                         elementSum+=current_weights[i]*(double)dataModel.getValueAt(i,j+3);
                     elementsModel.setValueAt(elementSum/weight, j, 1);
             }
             lblKgPrice.setText(""+pricePerKg);
             lblTotalPrice.setText(""+totalPrice);
             lblWeight.setText(""+weight);
             lblSolutionCount.setText(""+solutionCount);
         }
    }
    
     private void createModel(){
        store=new Store();
        weights=new IntVar[materialCount];
        weightByPrice=new IntVar[materialCount];
        
        int [][]data=new int[elementCount][materialCount];
        int []prices=new int[materialCount];
        
        int maxWeight=1000;
        int maxElementValue=10000;
        int maxKgPrice=Integer.parseInt(txtOptionMaxKgPrice.getText());
        
        DefaultTableModel modelMaterials=(DefaultTableModel)tableMaterials.getModel();
        DefaultTableModel modelElements=(DefaultTableModel)tableElements.getModel();
        DefaultTableModel dataModel=(DefaultTableModel)tableData.getModel();
        
        
        for(int i=0;i<materialCount;i++){
            if(modelMaterials.getValueAt(i, 2)!=null)
                prices[i]=(int)(double)modelMaterials.getValueAt(i, 2);//*factor
        
            if((boolean)modelMaterials.getValueAt(i, 3)==true){
                double min=0;
                double max=maxWeight;
                if(modelMaterials.getValueAt(i, 4)!=null)
                     min=(double)modelMaterials.getValueAt(i, 4);
                if(modelMaterials.getValueAt(i, 5)!=null)
                     max=(double)modelMaterials.getValueAt(i, 5);
                if(max<min)
                    max=maxWeight;
                min=min*(double)factor;
                max=max*(double)factor;
                weights[i]=new IntVar(store,"weight_"+i,(int)min,(int)max);
            }
            else
                weights[i]=new IntVar(store,"weight_"+i,0,0);
        }
        //get data
        for(int i=0;i<elementCount;i++){
            for(int j=0;j<materialCount;j++){ //*************************************
                double scaleValue=(double)dataModel.getValueAt(j,3+i);//*(double)factor;
                data[i][j]=(int)scaleValue;//scaleValue/1000; //average weight!
          
            }
        }
        for(int i=0;i<elementCount;i++){
            double elementVal=0;
            
            if(modelElements.getValueAt(i, 1)!=null)
                elementVal=(double)modelElements.getValueAt(i, 1);    
            if(elementVal!=0){
                double min=/*(double)factor**/(double)factor*(double)(maxWeight-weightAccuracy)*(elementVal-elementVal*(double)elementAccuracy/100.0d);
                double max=/*(double)factor**/(double)factor*(double)(maxWeight+weightAccuracy)*(elementVal+elementVal*(double)elementAccuracy/100.0d);
           //     substances_lb[i]=new IntVar(store,"sub_min_"+i,(int)min,(int)min);
           //     substances_ub[i]=new IntVar(store,"sub_max_"+i,(int)max,(int)max);
                
                Constraint element_lb_cons= new LinearInt(store, weights, data[i],">=", (int)min);
                Constraint element_ub_cons=new LinearInt(store, weights, data[i],"<=", (int)max);
                store.impose(element_lb_cons);
                store.impose(element_ub_cons);  
            }
        }
        IntVar weightSumMax=new IntVar(store,"weightSumMax",factor*(maxWeight+weightAccuracy),factor*(maxWeight+weightAccuracy));
        IntVar weightSumMin=new IntVar(store,"weightSumMin",factor*(maxWeight-weightAccuracy),factor*(maxWeight-weightAccuracy));
        Constraint weights_sum_cons=new SumInt(store,weights, "<=", weightSumMax);
        Constraint weights_sum_cons2=new SumInt(store,weights, ">=",weightSumMin);
       
        store.impose(weights_sum_cons);
        store.impose(weights_sum_cons2);
        for(int i=0;i<materialCount;i++){
            weightByPrice[i]=new IntVar(store,"weightByPrice_"+i,0,maxWeight*maxKgPrice*factor);
            Constraint wC=new XmulCeqZ(weights[i], prices[i], weightByPrice[i]);
            store.impose(wC);
        }
        
        objective=new IntVar(store,"Objective_",0, maxKgPrice*factor*maxWeight);
        Constraint obj_cos=new SumInt(store,weightByPrice,"==",objective);
        store.impose(obj_cos);
        
        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(weights, null,new IndomainMin<IntVar>());

        label.setPrintInfo(false);
        if(timeoutSeconds>0)
            label.setTimeOut(timeoutSeconds);
        label.setSolutionListener(new CostListener<IntVar>());
        
        boolean result = label.labeling(store, select,objective);
        while(result){
            getSolutionUpdates();
            result = label.labeling(store, select,objective);
        }
       
        double bestPrice=100000;
        int solutionCount=0;
        SolutionInstance solution;
        searching=false;
        
        btnSolve.setEnabled(true);
        btnPrint.setEnabled(true);
        btnClear.setEnabled(true);
        btnResetExcept5andMaterialsSelection.setEnabled(true);
        btnSavePricesAndAmounts.setEnabled(true);
        btnStopSearch.setEnabled(false);

        JOptionPane.showMessageDialog(this, "No More Solutions to Find");
        
    }
    public class CostListener<T extends Var> extends SimpleSolutionListener<T> {
        public boolean executeAfterSolution(Search<T> search,SelectChoicePoint<T> select) {
             boolean returnCode = super.executeAfterSolution(search,select);
             getSolutionUpdates();
            return returnCode;
        }
    }
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
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnManualUpdate;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnResetExcept5andMaterialsSelection;
    private javax.swing.JButton btnSavePricesAndAmounts;
    private javax.swing.JButton btnSolve;
    private javax.swing.JButton btnStopSearch;
    private javax.swing.JCheckBox checkIncludeAll;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblKgPrice;
    private javax.swing.JLabel lblSolutionCount;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JLabel lblWeight;
    private javax.swing.JSpinner spinOptionEelemntPercentAccuracy;
    private javax.swing.JSpinner spinOptionWeightAccuracy;
    private javax.swing.JTable tableData;
    private javax.swing.JTable tableElements;
    private javax.swing.JTable tableMaterials;
    private javax.swing.JTextField txtOptionMaxKgPrice;
    private javax.swing.JTextField txtOptionTimeoutLimit;
    // End of variables declaration//GEN-END:variables


}
