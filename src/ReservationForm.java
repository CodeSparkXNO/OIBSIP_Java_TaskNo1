import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class ReservationForm extends JFrame implements ActionListener {

    JTextField txtName, txtAge, txtTrainNo, txtTrainName, txtDate, txtSource, txtDest;
    JComboBox<String> genderBox, classBox;
    JButton btnInsert;

    ReservationForm() {
        setTitle("Reservation Form");
        setSize(400,500);
        setLayout(null);

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(30,30,100,25); add(l1);
        txtName = new JTextField();
        txtName.setBounds(150,30,150,25); add(txtName);

        JLabel l2 = new JLabel("Age:");
        l2.setBounds(30,70,100,25); add(l2);
        txtAge = new JTextField();
        txtAge.setBounds(150,70,150,25); add(txtAge);

        JLabel l3 = new JLabel("Gender:");
        l3.setBounds(30,110,100,25); add(l3);
        genderBox = new JComboBox<>(new String[]{"Male","Female"});
        genderBox.setBounds(150,110,150,25); add(genderBox);

        JLabel l4 = new JLabel("Train No:");
        l4.setBounds(30,150,100,25); add(l4);
        txtTrainNo = new JTextField();
        txtTrainNo.setBounds(150,150,150,25); add(txtTrainNo);

        JLabel l5 = new JLabel("Train Name:");
        l5.setBounds(30,190,100,25); add(l5);
        txtTrainName = new JTextField();
        txtTrainName.setBounds(150,190,150,25);
        txtTrainName.setEditable(false);
        add(txtTrainName);

        JLabel l6 = new JLabel("Class:");
        l6.setBounds(30,230,100,25); add(l6);
        classBox = new JComboBox<>(new String[]{"Sleeper","AC","General"});
        classBox.setBounds(150,230,150,25); add(classBox);

        JLabel l7 = new JLabel("Date:");
        l7.setBounds(30,270,100,25); add(l7);
        txtDate = new JTextField();
        txtDate.setBounds(150,270,150,25); add(txtDate);

        JLabel l8 = new JLabel("From:");
        l8.setBounds(30,310,100,25); add(l8);
        txtSource = new JTextField();
        txtSource.setBounds(150,310,150,25); add(txtSource);

        JLabel l9 = new JLabel("To:");
        l9.setBounds(30,350,100,25); add(l9);
        txtDest = new JTextField();
        txtDest.setBounds(150,350,150,25); add(txtDest);

        btnInsert = new JButton("Insert");
        btnInsert.setBounds(130,400,120,30); add(btnInsert);

        btnInsert.addActionListener(this);

        txtTrainNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fetchTrainName();
            }
        });

        setVisible(true);
    }

    void fetchTrainName() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT train_name FROM trains WHERE train_no=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, txtTrainNo.getText());
            ResultSet rs = pst.executeQuery();
            if (rs.next())
                txtTrainName.setText(rs.getString("train_name"));
            else
                txtTrainName.setText("Not Found");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            Connection con = DBConnection.getConnection();
            String pnr = "PNR" + new Random().nextInt(100000);

            String sql = "INSERT INTO reservations VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, pnr);
            pst.setString(2, txtName.getText());
            pst.setInt(3, Integer.parseInt(txtAge.getText()));
            pst.setString(4, genderBox.getSelectedItem().toString());
            pst.setString(5, txtTrainNo.getText());
            pst.setString(6, txtTrainName.getText());
            pst.setString(7, classBox.getSelectedItem().toString());
            pst.setString(8, txtDate.getText());
            pst.setString(9, txtSource.getText());
            pst.setString(10, txtDest.getText());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Reservation Successful\nPNR: " + pnr);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
