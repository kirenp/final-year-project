package swingserver;

import java.io.*;
import java.net.*;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SendLoad extends javax.swing.JFrame {

    Toolkit toolkit;
    Timer timer;
    Connection con;
    PreparedStatement ps;
    
    public SendLoad() {
        super("Milk Plant Device");
        initComponents();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master.herosite.pro:3306/aghosh_milk_plant", "aghosh", "farm2020");
            
        } catch (Exception e) {

        }

    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("Triggering...!");
            onSendData();
            toolkit.beep();
        }
    }

    private void sendSensorData(String veNo,String temp, String pres, String co) {
        try {

             ps=con.prepareStatement("insert into sensor_data values(null,'"+veNo+"','"+temp+"','"+pres+"','"+co+"') ");
             ps.execute();
             
        } catch (Exception e) {

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtLoad = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnSend = new javax.swing.JButton();
        lblData = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);
        getContentPane().add(txtLoad);
        txtLoad.setBounds(30, 150, 170, 30);

        jPanel1.setBackground(new java.awt.Color(-1,true));
        jPanel1.setForeground(new java.awt.Color(-1,true));
        jPanel1.setLayout(null);

        btnSend.setText("Start");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
        jPanel1.add(btnSend);
        btnSend.setBounds(260, 190, 75, 25);

        lblData.setFont(new java.awt.Font("Dialog", 1, 18));
        lblData.setText("Sensor data");
        jPanel1.add(lblData);
        lblData.setBounds(10, 20, 580, 100);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(470, 320, 620, 290);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/van.jpg"))); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(-220, -20, 1340, 760);

        setSize(new java.awt.Dimension(1130, 781));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void onSendData() {

        try {
            // get input data for sending
            String input = txtLoad.getText();

            Random r = new Random();
            int temp = r.nextInt(50);
            int pres = r.nextInt(15);
            int co = r.nextInt(20);
 
             if (temp > 35 && pres < 6 && co > 10) {
                String t=""+temp;
                String p=""+pres;
                String c=""+co;
                String vehiclNo="KL43-R-7076";
                
                sendSensorData(vehiclNo,t,p,c);
           }

            lblData.setText("Sensor Data- Temparature :" + temp + " Pressure:" + pres + " Tanker Gas Info:" + co);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed

    toolkit = Toolkit.getDefaultToolkit();
    timer = new Timer();
    timer.schedule(new RemindTask(), 4 * 1000, 6 * 1000);

}//GEN-LAST:event_btnSendActionPerformed

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
            java.util.logging.Logger.getLogger(SendLoad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SendLoad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SendLoad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SendLoad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SendLoad().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblData;
    private javax.swing.JTextField txtLoad;
    // End of variables declaration//GEN-END:variables
}
