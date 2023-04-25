package swingserver;

import java.io.*;
import java.net.*;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BusDevice extends javax.swing.JFrame {

    Toolkit toolkit;
    Timer timer;
    Connection con;
    PreparedStatement ps;

    public BusDevice() {
        super("GPS Device");
        initComponents();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master.herosite.pro:3306/aghosh_soft_bus", "aghosh", "farm2020");

        } catch (Exception e) {

        }
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("GPS BUS Postion Upating...!");
            sendSensorData();
            toolkit.beep();
        }
    }

    private void sendSensorData() {
        try {
            String lat = "76.5222";
            String longi = "9.5916";
            String time = java.time.LocalTime.now().toString();

            int f = 0;
            String veNo = txtBus.getText();
            ps = con.prepareStatement("select * from bus_position where bus_no='" + veNo + "'  ");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                f = 1;
                lat=rs.getString(3);
                longi=rs.getString(4);
            }
            rs.close();
            if (f == 0) {

                ps = con.prepareStatement("insert into bus_position values(null,'" + veNo + "','" + lat + "','" + longi + "','" + time + "' )");
                ps.execute();

            } else {
                
                float la = Float.valueOf(lat);
                float lo = Float.valueOf(longi);

                double d1 = la + 0.0001;
                double d2 = lo + 0.0001;
                String l= String.format("%.4f", d1);
                String lg= String.format("%.4f", d2);
                ps = con.prepareStatement("update bus_position set latitude= '" + l + "',longitude='" + lg + "',update_time='" + time + "' where bus_no='" + veNo + "'  ");
                ps.executeUpdate();
            }
             
        } catch (Exception e) {
System.out.print(e);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtBus = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/gps.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(310, 110, 800, 560);

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(60, 380, 100, 30);

        txtBus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(txtBus);
        txtBus.setBounds(60, 302, 210, 40);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(-10, -10, 810, 630);

        setSize(new java.awt.Dimension(812, 664));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

toolkit = Toolkit.getDefaultToolkit();
    timer = new Timer();
    timer.schedule(new RemindTask(), 4 * 1000, 10 * 1000);
        
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(BusDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BusDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BusDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BusDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BusDevice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtBus;
    // End of variables declaration//GEN-END:variables
}
