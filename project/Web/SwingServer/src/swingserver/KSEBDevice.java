 
package swingserver;
 import java.io.*;
import java.net.*;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.*;

public class KSEBDevice extends javax.swing.JFrame {

    
    
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
    
    public KSEBDevice() {
        super("Smart Fab");
        initComponents();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://lapis.co.in:3306/lapisco_kseb","lapisco_user","lapisuser@123");
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        jLabel4.setVisible(false);
    }
    java.util.Random r;  
    
    void sendToServer()
    {
        
        try
        {
            System.out.println("Server Connected...");
            PreparedStatement ps=con.prepareStatement("update lapisco_kseb.load set total=total+"+Integer.parseInt(lblTemp.getText())+" where consumer_no='"+jTextField1.getText()+"' ");
            ps.execute();
            System.out.println("Load data Updated...");
            ps=null;
            ps=con.prepareStatement("SELECT DATEDIFF(CURDATE(), lastdate) from bill where consumer_no='1212' ");
            ResultSet rs=ps.executeQuery();
            int d=0;
            System.out.println("Now checking the bill date...");
            if(rs.next())
            {
                d=rs.getInt(1);
                System.out.println("after bill date..."+d);
            }
            if(d>29)
            {
                lblMsg.setText("Your electricity become disconnected.....because "+d+" days after bill date");
            }
            else
            {
                lblMsg.setText("");
            }
            
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
                        int temp=r.nextInt(50);
                        lblTemp.setText(""+temp);
                        int mois=r.nextInt(10);
                         
                       
                         
                         
                        
                        System.out.println("in function...!");
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
        lblTemp = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lblMsg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(-4144960,true));
        jPanel1.setForeground(new java.awt.Color(-256,true));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18));
        jLabel1.setForeground(new java.awt.Color(-16776961,true));
        jLabel1.setText("Load");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 30, 120, 40);

        lblTemp.setFont(new java.awt.Font("Dialog", 1, 18));
        lblTemp.setText("0.00");
        jPanel1.add(lblTemp);
        lblTemp.setBounds(160, 30, 130, 40);
        jPanel1.add(jTextField1);
        jTextField1.setBounds(340, 30, 180, 40);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 960, 100);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/5.jpg"))); // NOI18N
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 120, 960, 420);

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(1000, 470, 53, 25);

        jButton2.setText("Start");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(1000, 210, 61, 25);

        lblMsg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblMsg.setForeground(new java.awt.Color(255, 0, 51));
        getContentPane().add(lblMsg);
        lblMsg.setBounds(20, 550, 790, 60);

        setSize(new java.awt.Dimension(1103, 687));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
 jLabel4.setVisible(true);
    toolkit = Toolkit.getDefaultToolkit();
    timer = new Timer();
    timer.schedule(new RemindTask(), 4 * 1000,6 * 1000);
    
}//GEN-LAST:event_jButton2ActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    
   System.exit(0);
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
            java.util.logging.Logger.getLogger(KSEBDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KSEBDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KSEBDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KSEBDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new KSEBDevice().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JLabel lblTemp;
    // End of variables declaration//GEN-END:variables
}
