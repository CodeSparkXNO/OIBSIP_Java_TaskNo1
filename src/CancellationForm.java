import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CancellationForm extends JFrame implements ActionListener {

    JTextField txtPNR;
    JTextArea details;
    JButton btnSearch, btnCancel;

    CancellationForm() {
        setTitle("Cancellation Form");
        setSize(400,400);
        setLayout(null);

        JLabel l1 = new JLabel("Enter PNR:");
        l1.setBounds(30,30,100,25); add(l1);

        txtPNR = new JTextField();
        txtPNR.setBounds(130,30,150,25); add(txtPNR);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(290,30,80,25); add(btnSearch);

        details = new JTextArea();
        details.setBounds(30,80,340,180);
        add(details);

        btnCancel = new JButton("OK (Cancel Ticket)");
        btnCancel.setBounds(110,280,170,30); add(btnCancel);

        btnSearch.addActionListener(this);
        btnCancel.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        try {
            Connection con = DBConnection.getConnection();

            if (e.getSource() == btnSearch) {
                String sql = "SELECT * FROM reservations WHERE pnr=?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, txtPNR.getText());

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    details.setText(
                        "Name: " + rs.getString("name") +
                        "\nTrain: " + rs.getString("train_name") +
                        "\nDate: " + rs.getString("journey_date") +
                        "\nFrom: " + rs.getString("source") +
                        "\nTo: " + rs.getString("destination")
                    );
                } else {
                    details.setText("PNR Not Found");
                }
            }

            if (e.getSource() == btnCancel) {
                String sql = "DELETE FROM reservations WHERE pnr=?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, txtPNR.getText());

                int rows = pst.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Ticket Cancelled Successfully");
                    details.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid PNR");
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
