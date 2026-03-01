import javax.swing.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {

    JButton btnReserve, btnCancel;

    MainMenu() {
        setTitle("Main Menu");
        setSize(300,200);
        setLayout(null);

        btnReserve = new JButton("New Reservation");
        btnReserve.setBounds(60,40,170,30);
        add(btnReserve);

        btnCancel = new JButton("Cancel Ticket");
        btnCancel.setBounds(60,90,170,30);
        add(btnCancel);

        btnReserve.addActionListener(this);
        btnCancel.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnReserve)
            new ReservationForm();

        if (e.getSource() == btnCancel)
            new CancellationForm();
    }
}
