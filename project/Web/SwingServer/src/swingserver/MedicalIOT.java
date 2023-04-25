package swingserver;

import java.io.*;
import java.net.*;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class MedicalIOT extends javax.swing.JFrame {

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

    public MedicalIOT() {
        initComponents();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master.herosite.pro:3306/aghosh_health_iot", "aghosh", "farm2020");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void sendToServer(String pl,String ecg) {

        try {
             
            PreparedStatement ps = con.prepareStatement("insert into monitor_data values(null,'" + txtPAtId.getText() + "','" + txtECG.getText() + "' ,'" + txtPulse.getText() + "')");
            ps.execute();
            System.out.println("ECG and Pulse Sensor data inserted...");
            ps = null;
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    java.util.Random r;

    private void onSendData() {

        try {

            r = new Random();
            ArrayList al = new ArrayList();
            al.add(60);
            al.add(62);
            al.add(64);
            al.add(66);
            al.add(68);
            al.add(70);
            al.add(71);
            al.add(72);
            al.add(73);
            al.add(74);
            al.add(75);
            al.add(76);
            al.add(78);
            al.add(79);
            al.add(80);
            al.add(82);
            al.add(84);

            ArrayList a2 = new ArrayList();
            a2.add(0.1);
            a2.add(0.2);
            a2.add(0.3);
            a2.add(0.4);
            a2.add(0.5);
            a2.add(0.6);
            a2.add(0.7);
            a2.add(0.8);
            a2.add(0.9);
            a2.add(1.0);
            a2.add(1.2);
            a2.add(1.4);
            a2.add(1.6);
            a2.add(1.8);
            a2.add(1.9);
            a2.add(2.0);
            a2.add(2.1);
            a2.add(2.2);
            a2.add(2.3);

            int heart = r.nextInt(16);
            int pul = r.nextInt(18);

            int p = (Integer) al.get(heart);
            float ec = Float.valueOf("" + a2.get(pul));

            txtPulse.setText("" + al.get(heart));
            txtECG.setText("" + a2.get(pul));
            
            System.out.println("ECG...!" + ec);
            System.out.println("Heart beat...!" + p);
            
            if (p > 74) {
                sendToServer(""+p,""+ec);
            }
            if (p < 67) {
                sendToServer(""+p,""+ec);
            }
            if (ec < .8) {
                sendToServer(""+p,""+ec);
            }
            if (ec > 1.8) {
               sendToServer(""+p,""+ec);
            }

          

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtECG = new javax.swing.JTextField();
        txtPulse = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtPAtId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblIOT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        txtECG.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        getContentPane().add(txtECG);
        txtECG.setBounds(340, 200, 310, 50);

        txtPulse.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        getContentPane().add(txtPulse);
        txtPulse.setBounds(340, 320, 310, 50);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(920, 570, 120, 40);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Electrocardiography Intervals");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 200, 310, 50);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Pulse Rate");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 330, 240, 40);

        txtPAtId.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtPAtId.setText("p5050");
        getContentPane().add(txtPAtId);
        txtPAtId.setBounds(340, 102, 310, 50);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Patient Id");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 90, 300, 70);

        lblIOT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/iot_health.jpg"))); // NOI18N
        getContentPane().add(lblIOT);
        lblIOT.setBounds(0, 0, 1120, 660);

        setSize(new java.awt.Dimension(1134, 700));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        timer.schedule(new RemindTask(), 4 * 1000, 9 * 3000);

    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(MedicalIOT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MedicalIOT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MedicalIOT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MedicalIOT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MedicalIOT().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblIOT;
    private javax.swing.JTextField txtECG;
    private javax.swing.JTextField txtPAtId;
    private javax.swing.JTextField txtPulse;
    // End of variables declaration//GEN-END:variables
}
