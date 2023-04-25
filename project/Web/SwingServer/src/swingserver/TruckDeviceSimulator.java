package swingserver;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TruckDeviceSimulator extends javax.swing.JFrame {

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

    public TruckDeviceSimulator() {
        super("Pollution Simulator");
        initComponents();
        txtLat.setVisible(false);
        txtLong.setVisible(false);
        txtLat.setText("9.2365");
        txtLong.setText("76.4541");
        r = new Random();

        txtTemp.setText(r.nextInt(100) + "");
        txtHum.setText(r.nextInt(100) + "");
        txtGas.setText(r.nextInt(100) + "");
        txtDis.setText(r.nextInt(100) + "");

        txtLong.setText(r.nextInt(100) + "");

        txtLat.setText(r.nextInt(100) + "");

        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master.herosite.pro:3306/aghosh_soft_food_truck", "aghosh", "farm2020");

        } catch (Exception e) {

        }
    }

    java.util.Random r;

    void sendToServer(String q) {

        try {
            System.out.println("Server Connected...");
            PreparedStatement ps = con.prepareStatement(q);
            ps.execute();
            System.out.println("Vehicle Sensor Data Updated...");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void onSendData() {
        try {
            Date d = new Date();
            String s = d.toString();
            System.out.println("Date is:" + s);
            String q = "insert into sensor_values values(null," + Integer.parseInt(txtTemp.getText()) + ",curdate(),'" + txtTemp.getText() + "','" + txtHum.getText() + "','" + txtGas.getText() + "','" + txtDis.getText() + "','" + txtLat.getText() + "','" + txtLong.getText() + "')";

            sendToServer(q);
            r = new Random();

            txtTemp.setText(r.nextInt(100) + "");
            txtHum.setText(r.nextInt(50) + "");
            txtGas.setText(r.nextInt(100) + "");
            float la = Float.valueOf(txtLat.getText());
            float lo = Float.valueOf(txtLong.getText());

            double d1 = la + 0.0001;
            double d2 = lo + 0.0001;
            String l = String.format("%.4f", d1);
            String lg = String.format("%.4f", d2);
            // txtLat.setText("" + l);
            // txtLong.setText("" + lg);
            int fl = 0;
            String details = "";
            if (Integer.parseInt(txtTemp.getText()) > 15) {
                details = "temparature become high";
                fl = 1;
            }
            if (Integer.parseInt(txtGas.getText()) > 50) {
                details = "poison gas detected";
                fl = 1;
            }
            if (fl == 1) {
                q = "insert into problem_details values(null," + Integer.parseInt(txtId.getText()) + ",1,curdate(),'" + details + "',1)";
                System.out.println(q);
                sendToServer(q);
                System.out.println("Problem details updated...");
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTemp = new javax.swing.JTextField();
        txtHum = new javax.swing.JTextField();
        txtGas = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtDis = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtLat = new javax.swing.JTextField();
        txtLong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnStop = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel2.setLayout(null);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(790, 230, 120, 40);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Humidity");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(40, 270, 200, 70);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Toxic gas");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(40, 340, 200, 70);

        txtTemp.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtTemp.setText("0");
        jPanel2.add(txtTemp);
        txtTemp.setBounds(260, 210, 190, 50);

        txtHum.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtHum.setText("0");
        jPanel2.add(txtHum);
        txtHum.setBounds(260, 280, 190, 50);

        txtGas.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtGas.setText("0");
        jPanel2.add(txtGas);
        txtGas.setBounds(260, 350, 190, 50);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Temparature");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(40, 200, 200, 70);

        jLabel14.setBackground(new java.awt.Color(0, 0, 204));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("Vehicle Sensor Values");
        jPanel2.add(jLabel14);
        jLabel14.setBounds(40, 110, 300, 60);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 153));
        jLabel13.setText("Food Truck Simulator");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(480, 10, 320, 60);

        txtId.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtId.setText("1");
        jPanel2.add(txtId);
        txtId.setBounds(1150, 40, 80, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 51));
        jLabel2.setText("Vehicle Id");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(1020, 40, 140, 30);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, -10, 1340, 440);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel4.setLayout(null);

        txtDis.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtDis.setText("0");
        jPanel4.add(txtDis);
        txtDis.setBounds(550, 30, 190, 50);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Disaster Status");
        jPanel4.add(jLabel10);
        jLabel10.setBounds(340, 20, 200, 70);

        txtLat.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtLat.setText("0");
        jPanel4.add(txtLat);
        txtLat.setBounds(550, 110, 190, 50);

        txtLong.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtLong.setText("0");
        jPanel4.add(txtLong);
        txtLong.setBounds(550, 180, 190, 50);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel4.add(jLabel7);
        jLabel7.setBounds(340, 100, 200, 70);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel4.add(jLabel8);
        jLabel8.setBounds(340, 170, 180, 70);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(300, 70, 790, 260);

        btnStop.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnStop.setText("Stop");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        jPanel1.add(btnStop);
        btnStop.setBounds(1170, 330, 120, 40);

        jButton2.setText("Vehicle GPS Position");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(40, 390, 210, 40);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 430, 1330, 470);

        setSize(new java.awt.Dimension(1338, 943));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        timer.schedule(new RemindTask(), 4 * 1000, 6 * 3000);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed

        timer.cancel();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStopActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        timer.cancel();
        this.dispose();
        new UpdateTruckPosition().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(TruckDeviceSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TruckDeviceSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TruckDeviceSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TruckDeviceSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TruckDeviceSimulator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStop;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txtDis;
    private javax.swing.JTextField txtGas;
    private javax.swing.JTextField txtHum;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtLat;
    private javax.swing.JTextField txtLong;
    private javax.swing.JTextField txtTemp;
    // End of variables declaration//GEN-END:variables
}
