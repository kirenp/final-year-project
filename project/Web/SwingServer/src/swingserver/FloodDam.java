package swingserver;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FloodDam extends javax.swing.JFrame {
    
    String deviceTypeId = "";
    String status = "";
    Toolkit toolkit;
    Timer timer;
    Connection con;
    int deviceRegId = 1;
    int damValue[] = {1500, 1550, 1600, 1800, 1900, 2000, 2100, 2110, 2130, 2210, 2230, 2400, 2410, 2460, 2500};
    int damFlowValue[] = {80, 120, 160, 180, 190, 200, 210, 220, 230, 240, 250, 300, 340, 360, 400, 500};
    
    public FloodDam() {
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
            PreparedStatement ps;
            ResultSet rs;
            
            Random r = new Random();
            int d = r.nextInt(13);
            String dam = "" + damValue[d];
            System.out.println("dam level is:" + dam);
            System.out.println("dam flow is:" + damFlowValue[d]);
            String stLimit = "1800";
            
            ps = null;
            int ml = Integer.parseInt(stLimit);
            int cLimit = Integer.parseInt(dam);
            ps = con.prepareStatement("update currentvalue set c_waterlevel='" + dam + "', c_velocity='" + damFlowValue[d] + "'   ");
            
            ps.executeUpdate();
            
            txtCurrent.setText(dam);
            jLabel3.setText("Dam Current Water Flow:" + damFlowValue[d]);
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

        txtDamId.setEditable(false);
        txtDamId.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDamId.setText("1");
        jPanel1.add(txtDamId);
        txtDamId.setBounds(120, 10, 180, 50);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Dam Id");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 0, 110, 70);

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
        jPanel1.add(jLabel3);
        jLabel3.setBounds(200, 100, 510, 60);

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
            java.util.logging.Logger.getLogger(FloodDam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FloodDam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FloodDam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FloodDam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FloodDam().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblImg;
    private javax.swing.JTextField txtCurrent;
    private javax.swing.JTextField txtDamId;
    // End of variables declaration//GEN-END:variables
}
