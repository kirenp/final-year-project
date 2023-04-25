package swingserver;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class DeviceStatusUpdate extends javax.swing.JFrame {

    String deviceTypeId = "";
    String status = "";

    public DeviceStatusUpdate() {
        initComponents();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master.herosite.pro:3306/aghosh_smart_energy_system", "aghosh", "farm2020");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    Toolkit toolkit;
    Timer timer;
    Connection con;
    int deviceRegId = 1;

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("Triggering...!");
            onSendData();
            toolkit.beep();
        }
    }

    void sendToServer() {

        try {
            String s = txtUserId.getText();
            PreparedStatement ps = con.prepareStatement("select * from  user_device where user_id='" + s + "' ");
            ResultSet rs = ps.executeQuery();
            int f = 0;
            if (rs.next()) {
System.out.println("in if:");
                deviceTypeId = rs.getString(3);
                status = rs.getString(4);
                f = 1;
            }
            System.out.println("status:"+status);
            rs.close();
            if (f == 1) {
                if (status.equals("on")) {
                    jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/on.jpg")));
                    jLabel3.setText("All Light On");
                } else {
                    jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/off.jpg")));
                    jLabel3.setText("Light Status is: Off");
                }
            }
            ps = con.prepareStatement("delete from  user_device where user_id='" + s + "' ");
            ps.execute();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    java.util.Random r;

    private void onSendData() {

        try {

            sendToServer();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtUserId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Device Simulator");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 40, 220, 50);

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(470, 50, 100, 30);

        txtUserId.setText("sam");
        jPanel1.add(txtUserId);
        txtUserId.setBounds(1010, 40, 180, 40);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(660, 42, 250, 40);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(-10, -10, 1230, 110);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(null);
        jPanel2.add(jLabel2);
        jLabel2.setBounds(50, 20, 1100, 670);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 90, 1230, 720);

        setSize(new java.awt.Dimension(1232, 829));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

     //   jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/1.jpg")));
    //    jLabel3.setText("All Light On");

          toolkit = Toolkit.getDefaultToolkit();
           timer = new Timer();
           timer.schedule(new RemindTask(), 4 * 1000, 6 * 1000);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        JOptionPane.showMessageDialog(this, "Simulator Started...");
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseClicked

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
            java.util.logging.Logger.getLogger(DeviceStatusUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DeviceStatusUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DeviceStatusUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeviceStatusUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DeviceStatusUpdate().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtUserId;
    // End of variables declaration//GEN-END:variables
}
