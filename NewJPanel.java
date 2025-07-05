/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import gui.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySql;

/**
 *
 * @author Asus
 */
public class NewJPanel extends javax.swing.JPanel {
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Creates new form NewJPanel
     */
    public NewJPanel() {
        initComponents();
        loadstudent();
        loadcity();
        loadgender();
        loadcourse();
    }
    public void loadcity() {
        try {
            ResultSet rs1 = MySql.search("SELECT * FROM `city`");
            Vector v = new Vector();
            v.add("select");
            while (rs1.next()) {
                v.add(rs1.getString("name"));
            }
            DefaultComboBoxModel dfc = new DefaultComboBoxModel(v);
            jComboBox1.setModel(dfc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadcourse() {
        try {
            ResultSet rs1 = MySql.search("SELECT * FROM `course`");
            Vector v = new Vector();
            v.add("select");
            while (rs1.next()) {
                v.add(rs1.getString("name"));
            }
            DefaultComboBoxModel dfc = new DefaultComboBoxModel(v);
            jComboBox3.setModel(dfc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadgender() {
        try {
            ResultSet rs1 = MySql.search("SELECT * FROM `gender`");
            Vector v = new Vector();
            v.add("select");
            while (rs1.next()) {
                v.add(rs1.getString("name"));
            }
            DefaultComboBoxModel dfc = new DefaultComboBoxModel(v);
            jComboBox2.setModel(dfc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadstudent() {
        try {
            ResultSet rs = MySql.search("SELECT * FROM `student` INNER JOIN `address` ON `student`.`address_id`=`address`.`id` INNER JOIN `gender` ON `student`.`gender_id`=`gender`.`id` INNER JOIN `city` ON `city`.id=`address`.`city_id` INNER JOIN `course` ON `course`.id=`student`.`course_id` ORDER BY `student`.`id` ASC");
            // ResultSet rs = MySql.search("SELECT DISTINCT `student`.`id`,`student`.`name`,`student`.`address`,`student`.`dob`,`subject`.`name`  FROM `student` INNER JOIN  `subject` ON `subject`.`subno`=`student`.`subno`;");
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector V = new Vector();
                V.add(rs.getString("student.id"));
                V.add(rs.getString("student.name"));
                String address = rs.getString("address.line1") + "," + rs.getString("address.line2") + "," + rs.getString("city.name");
                V.add(address);
                V.add(rs.getString("student.dob"));
                V.add(rs.getString("student.mobile"));
                V.add(rs.getString("course.name"));
                V.add(rs.getString("gender.name"));

                dtm.addRow(V);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadstudent(String text) {
        try {

            ResultSet rs = MySql.search("SELECT DISTINCT `student`.`id`,`student`.`name`,`student`.`mobile`,`student`.`dob`,`course`.`name` AS `course_name`,`gender`.`name`  AS `gender_name`,`student`.`address_id`  AS `addressid`  FROM  `student` INNER JOIN `course` ON `student`.`course_id`=`course`.`id`  INNER JOIN `gender` ON `student`.`gender_id`=`gender`.`id` INNER JOIN `address` ON `student`.`address_id`=`address`.`id` INNER JOIN `city` ON `address`.`city_id`=`city`.`id`  WHERE `student`.`name` LIKE '" + text + "%'  OR  `student`.`mobile` LIKE '" + text + "%'  ORDER BY `student`.`id` ASC");
            rs.next();
            String address_id = rs.getString("addressid");
            ResultSet rs1 = MySql.search("SELECT `address`.`line1`,`address`.`line2`,`address`.`city_id` FROM `address` INNER JOIN `city` ON `address`.`city_id`=`city`.`id`  WHERE `address`.`id`='" + address_id + "'");
            rs1.next();

            String city_id = rs1.getString("address.city_id");
            ResultSet rs2 = MySql.search("SELECT `city`.`id`,`city`.`name` FROM `city` WHERE `id`='" + city_id + "'");
            rs2.next();
            String city_name = rs2.getString("city.name");
            String line1 = rs1.getString("address.line1");
            String line2 = rs1.getString("address.line2");

            ResultSet rs3 = MySql.search("SELECT DISTINCT `student`.`id`,`student`.`name`,`student`.`mobile`,`student`.`dob`,`course`.`name` AS `course_name`,`gender`.`name`  AS `gender_name`,`student`.`address_id`  AS `addressid`  FROM  `student` INNER JOIN `course` ON `student`.`course_id`=`course`.`id`  INNER JOIN `gender` ON `student`.`gender_id`=`gender`.`id` INNER JOIN `address` ON `student`.`address_id`=`address`.`id` INNER JOIN `city` ON `address`.`city_id`=`city`.`id`  WHERE `student`.`name` LIKE '" + text + "%' OR  `student`.`mobile` LIKE '" + text + "%'   ORDER BY `student`.`id` ASC");
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            
            while (rs3.next()) {
                Vector V = new Vector();
                V.add(rs.getString("student.id"));
                V.add(rs.getString("student.name"));

                //String address = rs1.getString("address.line1") + "," + rs1.getString("address.line2") + "," + rs2.getString("city.name");
                String address = line1 + line2 + city_name;
                V.add(address);
                V.add(rs.getString("student.dob"));
                V.add(rs.getString("student.mobile"));
                V.add(rs.getString("course_name"));
                V.add(rs.getString("gender_name"));

                dtm.addRow(V);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetfield() {
        jTextField1.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jDateChooser2.setDate(null);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Name", "Address", "Dob", "mobile", "course", "gender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Name");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setText("DOB");

        jLabel3.setText("Address");

        jLabel7.setText("course");

        jButton2.setText("Register Student");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Update Student");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Search Student");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jLabel8.setText("line1");

        jLabel9.setText("line2");

        jLabel10.setText("city");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton5.setText("clear");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("delete");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("gender");

        jLabel12.setText("mobile");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(388, 388, 388))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(45, 45, 45))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(77, 77, 77)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGap(65, 65, 65)
                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(498, 498, 498))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton5)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6)
                            .addComponent(jButton1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int selectedRow = jTable1.getSelectedRow();
        if (evt.getClickCount() == 2) {
            int r = jTable1.getSelectedRow();

            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Please select student", "warning", JOptionPane.WARNING_MESSAGE);
            } else {

                try {
                    String id = jTable1.getValueAt(selectedRow, 0).toString();
                    ResultSet rs1 = MySql.search("SELECT `address_id`,`dob` FROM `student` WHERE `id`='" + id + "'");
                    rs1.next();
                    Date dob = rs1.getDate("dob");
                    String a_id = rs1.getString("address_id");
                    ResultSet rs2 = MySql.search("SELECT `line1`,`line2`,`city_id` FROM `address` WHERE `id`='" + a_id + "'");
                    rs2.next();
                    String line1 = rs2.getString("line1");
                    String line2 = rs2.getString("line2");
                    String city_id = rs2.getString("city_id");
                    ResultSet rs3 = MySql.search("SELECT `name` FROM `city` WHERE `id`='" + city_id + "'");
                    rs3.next();
                    String city = rs3.getString("name");

                    String name = jTable1.getValueAt(selectedRow, 1).toString();
                    String address = jTable1.getValueAt(selectedRow, 2).toString();
                    String mobile = jTable1.getValueAt(selectedRow, 4).toString();
                    String course = jTable1.getValueAt(selectedRow, 5).toString();
                    String gender = jTable1.getValueAt(selectedRow, 6).toString();

                    jTextField1.setText(name);
                    jTextField5.setText(mobile);
                    jTextField3.setText(address);
                    jComboBox3.setSelectedItem(course);
                    jComboBox2.setSelectedItem(gender);
                    jTextField3.setText(line1);
                    jTextField4.setText(line2);
                    jComboBox1.setSelectedItem(city);
                    jDateChooser2.setDate(dob);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String name = jTextField1.getText();
        String line1 = jTextField3.getText();
        String line2 = jTextField4.getText();
        Date dob = jDateChooser2.getDate();
        String city = jComboBox1.getSelectedItem().toString();
        String gender = jComboBox2.getSelectedItem().toString();

        String course = jComboBox3.getSelectedItem().toString();
        String mobile = jTextField5.getText();

        String address = line1 + line2 + city;

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student name", "warning", JOptionPane.WARNING_MESSAGE);
        } else if (line1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Address", "warning", JOptionPane.WARNING_MESSAGE);
        } else if (line2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Address", "warning", JOptionPane.WARNING_MESSAGE);
        } else if (mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Address", "warning", JOptionPane.WARNING_MESSAGE);
        } else if (dob == null) {
            JOptionPane.showMessageDialog(this, "Please enter DOB", "warning", JOptionPane.WARNING_MESSAGE);
        } else if (course.equals("select")) {
            JOptionPane.showMessageDialog(this, "Please select  subject", "warning", JOptionPane.WARNING_MESSAGE);
        } else if (city.equals("select")) {
            JOptionPane.showMessageDialog(this, "Please select  subject", "warning", JOptionPane.WARNING_MESSAGE);
        } else if (gender.equals("select")) {
            JOptionPane.showMessageDialog(this, "Please select  subject", "warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                ResultSet rs1 = MySql.search("SELECT `id` FROM `city` WHERE `name`='" + city + "'");
                rs1.next();
                String city_id = rs1.getString("id");

                ResultSet rs2 = MySql.search("SELECT `id` FROM `gender` WHERE `name`='" + gender + "'");
                rs2.next();
                String gender_id = rs2.getString("id");

                ResultSet rs4 = MySql.search("SELECT `id` FROM `course` WHERE `name`='" + course + "'");
                rs4.next();
                String course_id = rs4.getString("id");

                MySql.iud("INSERT INTO `address` (`line1`,`line2`,`city_id`) VALUES('" + line1 + "','" + line2 + "','" + city_id + "')");

                ResultSet rs3 = MySql.search("SELECT `id` FROM `address` WHERE `line1`='" + line1 + "' AND `line2`='" + line2 + "'  AND `city_id`='" + city_id + "'");
                rs3.next();
                String addressid = rs3.getString("id");

                ResultSet rs = MySql.search("SELECT LAST_INSERT_ID()");
                rs.next();
                String id = rs.getString(1);

                MySql.iud("INSERT INTO `student`(`name`,`mobile`,`DOB`,`address_id`,`course_id`,`gender_id`) VALUES ('" + name + "','" + mobile + "','" + sdf.format(dob) + "','" + addressid + "','" + course_id + "','" + gender_id + "')");

                resetfield();
                loadstudent();
                jTextField1.grabFocus();

                JOptionPane.showMessageDialog(this, "New student add successfully", "success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String name = jTextField1.getText();
        String line1 = jTextField3.getText();
        String line2 = jTextField4.getText();
        Date dob = jDateChooser2.getDate();
        String city = jComboBox1.getSelectedItem().toString();
        String gender = jComboBox2.getSelectedItem().toString();

        String course = jComboBox3.getSelectedItem().toString();
        String mobile = jTextField5.getText();
        try {
            int selectedRow = jTable1.getSelectedRow();

            String id = jTable1.getValueAt(selectedRow, 0).toString();
            ResultSet rss = MySql.search("SELECT `address_id` FROM `student` WHERE `id`='" + id + "'");
            rss.next();
            String a_id = rss.getString("address_id");

            String address = line1 + line2 + city;

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Student name", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (line1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Address", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (line2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Address", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (mobile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Address", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (dob == null) {
                JOptionPane.showMessageDialog(this, "Please enter DOB", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (course.equals("select")) {
                JOptionPane.showMessageDialog(this, "Please select  subject", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (city.equals("select")) {
                JOptionPane.showMessageDialog(this, "Please select  subject", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (gender.equals("select")) {
                JOptionPane.showMessageDialog(this, "Please select  subject", "warning", JOptionPane.WARNING_MESSAGE);
            } else {

                try {
                    ResultSet rs1 = MySql.search("SELECT `id` FROM `city` WHERE `name`='" + city + "'");
                    rs1.next();
                    String city_id = rs1.getString("id");

                    ResultSet rs2 = MySql.search("SELECT `id` FROM `gender` WHERE `name`='" + gender + "'");
                    rs2.next();
                    String gender_id = rs2.getString("id");

                    ResultSet rs4 = MySql.search("SELECT `id` FROM `course` WHERE `name`='" + course + "'");
                    rs4.next();
                    String course_id = rs4.getString("id");

                    MySql.iud("UPDATE `address` SET `line1`='" + line1 + "' , `line2`='" + line2 + "', `city_id`='" + city_id + "'  WHERE `id`='" + a_id + "'");

                    ResultSet rs3 = MySql.search("SELECT `id` FROM `address` WHERE `line1`='" + line1 + "' AND `line2`='" + line2 + "'  AND `city_id`='" + city_id + "'");
                    rs3.next();
                    String addressid = rs3.getString("id");

                    MySql.iud("UPDATE `student` SET `name`='" + name + "' ,`mobile`='" + mobile + "' ,`address_id`='" + addressid + "',`dob`='" + sdf.format(dob) + "',`course_id`='" + course_id + "',`gender_id`='" + gender_id + "'  WHERE `id`='" + id + "'");
                    loadstudent();
                    resetfield();
                    jTextField1.grabFocus();

                    JOptionPane.showMessageDialog(this, " Student updated successfully", "success", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        String text = jTextField2.getText();
        loadstudent(text);

    }//GEN-LAST:event_jTextField2KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        resetfield();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        int r = jTable1.getSelectedRow();
        if (r == -1) {
            JOptionPane.showMessageDialog(this, "Please select student", "warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String id = jTable1.getValueAt(selectedRow, 0).toString();
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.removeRow(r);
            MySql.iud("DELETE FROM `student` WHERE  `id`='" + id + "' ");

            JOptionPane.showMessageDialog(this, "student removed", "success", JOptionPane.INFORMATION_MESSAGE);

        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        loadstudent();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
