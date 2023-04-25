 
package swingserver;
  import java.io.*;
import java.net.*;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.*;

public class WaterReadingSimulator extends javax.swing.JFrame {

   Toolkit toolkit;
   Timer timer;
     Connection con;
     
    class RemindTask extends TimerTask 
    {
        @Override
        public void run() 
        {
            System.out.println("Triggering...!");
           onSendData();
            toolkit.beep();
        }
    }
    
    public WaterReadingSimulator() {
        super("Smart Water Device");
        initComponents();
          try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master.herosite.pro:3306/aghosh_soft_water", "aghosh", "farm2020");

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        
    }
  java.util.Random r;  
    
    void sendToServer()
    {
        
        try
        {
            System.out.println("Server Connected...");
            PreparedStatement ps=con.prepareStatement("update loads set total=total+"+Integer.parseInt(txtLoad.getText())+" where consumer_no='"+txtCons.getText()+"' ");
            ps.execute();
            System.out.println("Load data Updated...");
            ps=null;
            
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
 private void onSendData() {
		
        try 
                {
			// get input data for sending
			r=new Random();
                        int temp=r.nextInt(100);
                        txtLoad.setText(""+temp);
                      
                        sendToServer();
 

		} catch (Exception ex) 
                {
			System.out.println(ex);
		}
	}
 
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtLoad = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCons = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/water.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(70, 220, 700, 490);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        txtLoad.setEditable(false);
        txtLoad.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtLoad.setText("0");
        jPanel2.add(txtLoad);
        txtLoad.setBounds(200, 20, 190, 50);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(440, 30, 120, 40);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Usages");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 10, 130, 70);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(70, 120, 700, 90);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Water meter box");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(30, 40, 280, 60);

        txtCons.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCons.setText("1212");
        jPanel1.add(txtCons);
        txtCons.setBounds(340, 50, 240, 40);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, -20, 1210, 790);

        setSize(new java.awt.Dimension(886, 802));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

 toolkit = Toolkit.getDefaultToolkit();
    timer = new Timer();
    timer.schedule(new RemindTask(), 4 * 1000,6 * 3000);

        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(WaterReadingSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WaterReadingSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WaterReadingSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WaterReadingSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WaterReadingSimulator().setVisible(true);
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
    private javax.swing.JTextField txtCons;
    private javax.swing.JTextField txtLoad;
    // End of variables declaration//GEN-END:variables
}
