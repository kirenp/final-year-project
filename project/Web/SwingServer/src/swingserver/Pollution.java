 
package swingserver;
 
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Pollution extends javax.swing.JFrame {
    Toolkit toolkit;
    Timer timer;
    Connection con;
    int deviceRegId = 1;
    
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
    public Pollution() {
        super("Pollution Simulator");
        initComponents();
         
                        r=new Random();
                       
                        txtCar.setText(r.nextInt(100)+"");
                        txtSul.setText(r.nextInt(100)+"");
                        txtNi.setText(r.nextInt(100)+"");
                        txtCaMono.setText(r.nextInt(100)+"");
                        txtBut.setText(r.nextInt(100)+"");
                         txtMer.setText(r.nextInt(100)+"");
                        txtLead.setText(r.nextInt(100)+"");
                        txtArse.setText(r.nextInt(100)+"");
                        txtOil.setText(r.nextInt(100)+"");
                        
          try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master.herosite.pro:3306/aghosh_soft_pollution", "aghosh", "farm2020");

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

  java.util.Random r;  
    
    void sendToServer(String q,String w)
    {
        
        try
        {
            System.out.println("Server Connected...");
            PreparedStatement ps=con.prepareStatement(q);
            ps.execute();
            System.out.println("Air Pollution data Updated...");
            ps=con.prepareStatement(w);
            ps.execute();
            System.out.println("Water Pollution data Updated...");
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
 private void onSendData() {
		
        try 
                {
                
                    
                        Date d=new Date();
                        String s=d.toString();
                        System.out.println("Date is:"+s);
                        String q="insert into air_pollution_monitoring values(null,curdate(),'"+txtId.getText()+"',"+Integer.parseInt(txtCar.getText())+","+Integer.parseInt(txtSul.getText())+","+Integer.parseInt(txtNi.getText())+","+Integer.parseInt(txtCaMono.getText())+","+Integer.parseInt(txtBut.getText())+")";
                        String q1="insert into water_pollution_monitoring values(null,'"+txtId.getText()+"',"+Integer.parseInt(txtLead.getText())+","+Integer.parseInt(txtArse.getText())+","+Integer.parseInt(txtMer.getText())+","+Integer.parseInt(txtOil.getText())+",curdate())";
                      
                        sendToServer(q,q1);
                        
                        
                        r=new Random();
                       
                        txtCar.setText(r.nextInt(100)+"");
                        txtSul.setText(r.nextInt(100)+"");
                        txtNi.setText(r.nextInt(100)+"");
                        txtMer.setText(r.nextInt(100)+"");
                        txtCaMono.setText(r.nextInt(100)+"");
                        txtBut.setText(r.nextInt(100)+"");
                        
                        txtLead.setText(r.nextInt(100)+"");
                        txtArse.setText(r.nextInt(100)+"");
                        txtOil.setText(r.nextInt(100)+"");
                         
		} catch (Exception ex) 
                {
			System.out.println(ex);
		}
	}
 
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtBut = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCar = new javax.swing.JTextField();
        txtSul = new javax.swing.JTextField();
        txtNi = new javax.swing.JTextField();
        txtCaMono = new javax.swing.JTextField();
        txtLead = new javax.swing.JTextField();
        txtArse = new javax.swing.JTextField();
        txtMer = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtOil = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnStop = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(1120, 240, 120, 40);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Butadiene");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(10, 370, 200, 70);

        txtBut.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtBut.setText("0");
        jPanel2.add(txtBut);
        txtBut.setBounds(220, 380, 190, 50);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Sulfur oxides");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(10, 160, 200, 70);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Lead");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(540, 120, 240, 70);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Arsenic");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(540, 190, 240, 70);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Mercury");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(540, 260, 240, 70);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Nitrogen oxides");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(10, 230, 200, 70);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Carbon monoxide");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(10, 300, 200, 70);

        txtCar.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtCar.setText("0");
        jPanel2.add(txtCar);
        txtCar.setBounds(220, 100, 190, 50);

        txtSul.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtSul.setText("0");
        jPanel2.add(txtSul);
        txtSul.setBounds(220, 170, 190, 50);

        txtNi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtNi.setText("0");
        jPanel2.add(txtNi);
        txtNi.setBounds(220, 240, 190, 50);

        txtCaMono.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtCaMono.setText("0");
        jPanel2.add(txtCaMono);
        txtCaMono.setBounds(220, 310, 190, 50);

        txtLead.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtLead.setText("0");
        jPanel2.add(txtLead);
        txtLead.setBounds(810, 130, 190, 50);

        txtArse.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtArse.setText("0");
        jPanel2.add(txtArse);
        txtArse.setBounds(810, 200, 190, 50);

        txtMer.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtMer.setText("0");
        jPanel2.add(txtMer);
        txtMer.setBounds(810, 270, 190, 50);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Oil");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(540, 330, 240, 70);

        txtOil.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtOil.setText("0");
        jPanel2.add(txtOil);
        txtOil.setBounds(810, 340, 190, 50);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Carbon dioxide");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(10, 90, 200, 70);

        btnStop.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnStop.setText("Stop");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        jPanel2.add(btnStop);
        btnStop.setBounds(1120, 310, 120, 40);

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));
        jPanel3.setLayout(null);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Water Pollution");
        jPanel3.add(jLabel13);
        jLabel13.setBounds(710, 0, 210, 60);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 255, 102));
        jLabel2.setText("Simulator Id");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(260, 20, 140, 30);

        txtId.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel3.add(txtId);
        txtId.setBounds(400, 20, 80, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 255, 153));
        jLabel4.setText("Pollution Simulator");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(1100, 0, 270, 70);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Air Pollution");
        jPanel3.add(jLabel14);
        jLabel14.setBounds(40, 0, 170, 60);

        jPanel2.add(jPanel3);
        jPanel3.setBounds(-20, 20, 1350, 60);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, -10, 1340, 440);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/pollution_air.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(60, 20, 1200, 570);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 430, 1330, 600);

        setSize(new java.awt.Dimension(1338, 1074));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        timer.schedule(new RemindTask(), 4 * 1000,6 * 3000);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed

        timer.cancel();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStopActionPerformed

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
            java.util.logging.Logger.getLogger(Pollution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pollution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pollution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pollution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pollution().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStop;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtArse;
    private javax.swing.JTextField txtBut;
    private javax.swing.JTextField txtCaMono;
    private javax.swing.JTextField txtCar;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtLead;
    private javax.swing.JTextField txtMer;
    private javax.swing.JTextField txtNi;
    private javax.swing.JTextField txtOil;
    private javax.swing.JTextField txtSul;
    // End of variables declaration//GEN-END:variables
}
