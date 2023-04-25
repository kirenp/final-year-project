package swingserver;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.DefaultComboBoxModel;

public class TollAndSpeedDevice extends javax.swing.JFrame {

    String lat[], lon[], tollId[], longi[], fineLat[], fineLon[], fineAmount[], fineLimit[],fineKey[];
    String deviceTypeId = "";
    int totalTollCount = 0;
    String fKey="";
    
    int fineIndex = 0;
    int fineCount = 0;
    String status = "";
    Toolkit toolkit;
    Timer timer;
    Connection con;
    int deviceRegId = 1;
    int speedValue[] = {40, 50, 60, 65, 70, 75, 80, 85, 90, 95, 100, 110, 120, 130, 140};
    int damFlowValue[] = {80, 120, 160, 180, 190, 200, 210, 220, 230, 240, 250, 300, 340, 360, 400, 500};

    public TollAndSpeedDevice() {
        super("Vehicle Device");
        initComponents();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://master.herosite.pro:3306/aghosh_toll_application", "aghosh", "farm2020");

        } catch (Exception e) {
            System.out.println(e);
        }
        getLatitudeAndLongitude();
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

            PreparedStatement ps;
            ResultSet rs;

            Random r = new Random();
            int d = r.nextInt(13);
            int tollIndex = r.nextInt(totalTollCount);
            System.out.println("current toll index is:" + tollIndex);
            String currentTollId = tollId[tollIndex];
            String currentTollPlace = lon[tollIndex];
            ComboItem ci = (ComboItem) jComboBox1.getSelectedItem();
            String vtId = ci.getValue();
            System.out.println("Vehile is:" + ci.getKey());

            System.out.println("current vehicle id:" + vtId);
            lblTollPlace.setText("Current Vehicle Toll Place is: " + currentTollPlace);

            lblGPS.setText("GPS Latitude:" + lat[tollIndex] + " and Longitude:" + longi[tollIndex]);

            int si = r.nextInt(fineCount);
            txtLimit.setText(fineLimit[si]);
            String s = txtLimit.getText();
            ps = null;
            System.out.println("select * from toll_place_amount where idtoll_place=" + currentTollId + " and idvehicle='" + vtId + "' ");
            ps = con.prepareStatement("select * from toll_place_amount where idtoll_place=" + currentTollId + " and idvehicle='" + vtId + "' ");
            rs = ps.executeQuery();
            String am = "";
            if (rs.next()) {
                am = rs.getString(4);
            }
            System.out.println("Toll amount is:" + am);
            String speed = "" + speedValue[d];
            System.out.println("current speed is:" + speed);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String da = dtf.format(now);
            System.out.println(dtf.format(now));
            int fl = 0;
            int currentSpeed = Integer.parseInt(speed);
            lblCurrentSpeed.setText("Current Vehicle Speed:" + speed);
            ps = null;
            if (Integer.parseInt(txtLimit.getText()) < currentSpeed) {
                ps = con.prepareStatement("insert into user_fine values (null,'" + txtVehicleNo.getText() + "' , '"+fineKey[si]+"','" + da + "','pending','" + speed + "' )");
                ps.executeUpdate();
                fl = 1;
                lblSpeed.setText("Over Speed Fine Activated...");
                
            } else {
                System.out.println("Normal Speed");
                lblSpeed.setText("");
            }
            if (fl == 1) {
                ps = null;
                ps = con.prepareStatement("update wallet set amount=amount-" + Integer.parseInt(am) + " where vehicle_no='" + txtVehicleNo.getText() + "' ");
                ps.executeUpdate();

                ps = null;
                ps = con.prepareStatement("insert into user_toll_payments values(null,'" + txtVehicleNo.getText() + "','" + da + "','" + currentTollId + "' ,'" + am + "') ");
                ps.executeUpdate();
                System.out.println("Fine deducted...!");
            }

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

    void getLatitudeAndLongitude() {
        try {
            String s = txtLimit.getText();
            PreparedStatement ps;
            ResultSet rs;

            Random r = new Random();
            int d = r.nextInt(13);
            String speed = "" + speedValue[d];
            System.out.println("current speed is:" + speed);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String da = dtf.format(now);
            System.out.println(dtf.format(now));

            ps = null;
            ps = con.prepareStatement("select * from toll_place");
            rs = ps.executeQuery();

            rs.last();

            System.out.println("Row size:");
            int size = rs.getRow();
            totalTollCount = size;
            lat = new String[size];
            lon = new String[size];
            longi = new String[size];
            tollId = new String[size];
            System.out.println("Row size:" + size);

            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                tollId[i] = String.valueOf(rs.getInt(1));
                lat[i] = String.valueOf(rs.getString(3));
                longi[i] = String.valueOf(rs.getString(4));
                lon[i] = String.valueOf(rs.getString(5));
                i = i + 1;
            }
            for (int j = 0; j < lon.length; j++) {
                System.out.println("Total toll is:" + lon[j]);
            }

            rs.close();

            ps = null;
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            ArrayList al = new ArrayList();
            ps = con.prepareStatement("select * from vehicle");
            rs = ps.executeQuery();
            while (rs.next()) {
                model.addElement(new ComboItem(rs.getString(2), "" + rs.getInt(1)));
            }
            rs.close();

            jComboBox1.setModel(model);
            i = 0;
            ps = con.prepareStatement("select * from fine");
            rs = ps.executeQuery();
            rs.last();

            System.out.println("Speed Row size:");
            size = rs.getRow();
            fineCount = size;
            rs.beforeFirst();
            fineAmount = new String[size];
            fineLat = new String[size];
            fineLon = new String[size];
            fineLimit = new String[size];
            fineKey = new String[size];
            while (rs.next()) {
                fineKey[i] = String.valueOf(rs.getInt(1));
                fineAmount[i] = String.valueOf(rs.getString(3));
                fineLat[i] = String.valueOf(rs.getString(4));
                fineLon[i] = String.valueOf(rs.getString(5));
                fineLimit[i] = String.valueOf(rs.getString(6));

                i = i + 1;
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtLimit = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblSpeed = new javax.swing.JLabel();
        lblImg = new javax.swing.JLabel();
        lblCurrentSpeed = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtVehicleNo = new javax.swing.JTextField();
        lblTollPlace = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        lblGPS = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        txtLimit.setEditable(false);
        txtLimit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtLimit.setText("80");
        jPanel1.add(txtLimit);
        txtLimit.setBounds(180, 20, 180, 50);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Speed Limit");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 10, 150, 70);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(1060, 60, 120, 40);

        lblSpeed.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblSpeed.setForeground(new java.awt.Color(255, 0, 51));
        jPanel1.add(lblSpeed);
        lblSpeed.setBounds(40, 600, 940, 80);

        lblImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swingserver/toll.jpg"))); // NOI18N
        jPanel1.add(lblImg);
        lblImg.setBounds(10, 230, 1240, 1000);

        lblCurrentSpeed.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblCurrentSpeed.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(lblCurrentSpeed);
        lblCurrentSpeed.setBounds(410, 20, 510, 60);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Vehicle Number");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(370, 80, 170, 70);

        txtVehicleNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtVehicleNo.setText("KL44C4759");
        jPanel1.add(txtVehicleNo);
        txtVehicleNo.setBounds(550, 100, 170, 30);

        lblTollPlace.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTollPlace.setForeground(new java.awt.Color(0, 51, 255));
        lblTollPlace.setText("Current Toll Place");
        jPanel1.add(lblTollPlace);
        lblTollPlace.setBounds(20, 140, 400, 60);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Vehicle Type");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 90, 140, 50);

        jComboBox1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(180, 100, 120, 30);

        lblGPS.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblGPS.setForeground(new java.awt.Color(255, 0, 51));
        jPanel1.add(lblGPS);
        lblGPS.setBounds(450, 140, 740, 60);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(-10, -10, 1240, 840);

        setSize(new java.awt.Dimension(1227, 863));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        timer.schedule(new RemindTask(), 10 * 1000, 10 * 1000);

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
            java.util.logging.Logger.getLogger(TollAndSpeedDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TollAndSpeedDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TollAndSpeedDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TollAndSpeedDevice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TollAndSpeedDevice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCurrentSpeed;
    private javax.swing.JLabel lblGPS;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblSpeed;
    private javax.swing.JLabel lblTollPlace;
    private javax.swing.JTextField txtLimit;
    private javax.swing.JTextField txtVehicleNo;
    // End of variables declaration//GEN-END:variables
}
