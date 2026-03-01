import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin;

    Login() {
        setTitle("Login");
        setSize(300,200);
        setLayout(null);

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(30,30,100,25);
        add(l1);

        txtUser = new JTextField();
        txtUser.setBounds(120,30,120,25);
        add(txtUser);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(30,70,100,25);
        add(l2);

        txtPass = new JPasswordField();
        txtPass.setBounds(120,70,120,25);
        add(txtPass);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(90,110,100,30);
        add(btnLogin);

        btnLogin.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, txtUser.getText());
            pst.setString(2, new String(txtPass.getPassword()));

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                new MainMenu();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
