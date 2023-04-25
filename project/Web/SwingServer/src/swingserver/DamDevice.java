package swingserver;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DamDevice extends javax.swing.JFrame {

    String deviceTypeId = "";
    String status = "";
    Toolkit toolkit;
    Timer timer;
    Connection con;
    int deviceRegId = 1;
    int damValue[]={1500,1550,1600,1800,1900,2000,2100,2110,2130,2210,2230,2400,2410,2460,2500};
     int velValue[]={900,910,920,930,940,950,960,1200};
     Random rr=new Random();
     
    public DamDevice() {
        initComponents();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master.herosite.pro:3306/aghosh_flood_dam", "aghosh", "farm2020");

        } catch (Exception e) {
            System.out.println(e);
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

    void sendToServer() {

        try {
            String s = txtDamId.getText();
            PreparedStatement ps = con.prepareStatement("select * from dam_shutter where dam_id=" + Integer.parseInt(s) + " and shutter_no='" + txtShutter.getText() + "' ");
            ResultSet rs = ps.executeQuery();
            int f = 0;
            if (rs.next()) {

                status = rs.getString(4);
                f = 1;
            }
            System.out.println("Status is:" + status);
            rs.close();
            if (f == 1) {
                if (status.equals("open")) {
                    System.out.println("Status is:if");
                    lblImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/dam_one.jpg")));
                    jLabel3.setText("Command Recieved Status : ON...");
                } else {
                    System.out.println("Status is:else");
                    lblImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/dam_close.jpg")));
                    jLabel3.setText("Command Recieved Status : OFF... ");
                }
            }
            Random r=new Random();
            int d=r.nextInt(13);
            int vl=rr.nextInt(7);
            int cVelo=velValue[vl];
            String v=""+cVelo;
            String dam=""+damValue[d];
             System.out.println("dam level is:" + dam);
            ps = con.prepareStatement("select * from  currentvalue  ");
            rs = ps.executeQuery();
             String stLimit="1200";
            if (rs.next()) {
                stLimit = rs.getString(2);
                 
            }
            rs.close();
            ps=null;
            int ml=Integer.parseInt(stLimit);
            int cLimit=Integer.parseInt(dam);
              ps = con.prepareStatement("update currentvalue set c_waterlevel='"+dam+"',c_velocity='"+v+"' ");
           // ps = con.prepareStatement("update dam_storage_details set current_reading='2400',last_updated_date=curdate() where dam_id=1 ");
            
            ps.executeUpdate();
            /*
            if(cLimit>ml)
            {
                System.out.println("Problem value detected...");
                ps = con.prepareStatement("insert into problem_details_history values(null," + Integer.parseInt(s) + "," + Integer.parseInt(dam) + ",curdate())");
                ps.execute();
            }
            */
           txtCurrent.setText(dam);
            
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
        txtShutter = new javax.swing.JTextField();
        txtDamId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblImg = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCurrent = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Shutter No");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 80, 110, 70);

        txtShutter.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtShutter.setText("2");
        jPanel1.add(txtShutter);
        txtShutter.setBounds(120, 90, 180, 50);

        txtDamId.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDamId.setText("1");
        jPanel1.add(txtDamId);
        txtDamId.setBounds(120, 10, 180, 50);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Dam Id");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 0, 110, 70);

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(840, 20, 120, 40);
        jPanel1.add(lblImg);
        lblImg.setBounds(50, 240, 870, 530);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("st");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(320, 50, 510, 60);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Current Reading");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(10, 170, 170, 70);

        txtCurrent.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCurrent.setText("500");
        jPanel1.add(txtCurrent);
        txtCurrent.setBounds(200, 190, 140, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 980, 790);

        setSize(new java.awt.Dimension(1021, 863));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        //jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/dam_on.jpg")));
        //   lblImg.setText("Shutter On");
        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        timer.schedule(new RemindTask(), 4 * 1000, 6 * 1000);

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
            java.util.logging.Logger.getLogger(DamDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DamDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DamDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DamDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DamDevice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblImg;
    private javax.swing.JTextField txtCurrent;
    private javax.swing.JTextField txtDamId;
    private javax.swing.JTextField txtShutter;
    // End of variables declaration//GEN-END:variables
}
