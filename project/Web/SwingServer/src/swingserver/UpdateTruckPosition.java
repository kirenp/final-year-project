 
package swingserver;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;
 
public class UpdateTruckPosition extends javax.swing.JFrame {
  Toolkit toolkit;
    Timer timer;
    Connection con;
    PreparedStatement ps;
String veNo ="1";
  

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("GPS Truck Postion Upating...!");
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
             
             System.out.println("veno..."+veNo);
            ps = con.prepareStatement("select * from truck_position where vehicle_id= " +Integer.parseInt(veNo) + "   ");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                f = 1;
                lat=rs.getString(3);
                longi=rs.getString(4);
            }
            rs.close();
            if (f == 0) {

                ps = con.prepareStatement("insert into truck_position values(null," +Integer.parseInt(veNo) + ",'" + lat + "','" + longi + "')");
                ps.execute();

            } else {
                
                float la = Float.valueOf(lat);
                float lo = Float.valueOf(longi);

                double d1 = la + 0.0001;
                double d2 = lo + 0.0001;
                String l= String.format("%.4f", d1);
                String lg= String.format("%.4f", d2);
                latitude.setText("Current Latutude:"+l);
                 longitude.setText("Current Longitude:"+lg);
                 
                ps = con.prepareStatement("update truck_position set latitude= '" + l + "',longitude='" + lg + "' where vehicle_id='" + veNo + "'  ");
                ps.executeUpdate();
            }
             
        } catch (Exception e) {
System.out.print(e);
        }

    }
    public UpdateTruckPosition() {
        super("GPS Device");
        initComponents();
         
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master.herosite.pro:3306/aghosh_soft_food_truck", "aghosh", "farm2020");

        } catch (Exception e) {

        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtBus = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        latitude = new javax.swing.JLabel();
        longitude = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/gps.jpg"))); // NOI18N

        txtBus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/foodDevice.jpg"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(320, 120, 790, 560);

        latitude.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        latitude.setText("Current Latitude : ");
        jPanel1.add(latitude);
        latitude.setBounds(0, 50, 360, 50);

        longitude.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        longitude.setText("Current Longitude");
        jPanel1.add(longitude);
        longitude.setBounds(510, 50, 310, 60);

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jTextField1.setText("1");
        jPanel1.add(jTextField1);
        jTextField1.setBounds(70, 310, 220, 40);

        jButton3.setText("Submit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(80, 390, 110, 40);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Vehicle Id");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(70, 260, 100, 30);

        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(20, 585, 120, 40);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, -10, 910, 700);

        setSize(new java.awt.Dimension(836, 678));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

      

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        veNo=jTextField1.getText();
        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        timer.schedule(new RemindTask(), 4 * 1000, 10 * 1000);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
 timer.cancel();
        this.dispose();
        new TruckDeviceSimulator().setVisible(true);
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(UpdateTruckPosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateTruckPosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateTruckPosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateTruckPosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateTruckPosition().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel latitude;
    private javax.swing.JLabel longitude;
    private javax.swing.JTextField txtBus;
    // End of variables declaration//GEN-END:variables
}
