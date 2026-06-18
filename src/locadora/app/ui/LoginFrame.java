package locadora.app.ui;

import locadora.service.Locadora;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame(Locadora locadora) {

        setTitle("Locadora");
        setSize(400,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5,1,10,10));

        JTextField usuario = new JTextField();
        JPasswordField senha = new JPasswordField();

        JButton entrar = new JButton("Entrar");

        panel.add(new JLabel("Usuário"));
        panel.add(usuario);

        panel.add(new JLabel("Senha"));
        panel.add(senha);

        panel.add(entrar);

        entrar.addActionListener(e -> {

            new DashboardFrame(locadora);

            dispose();
        });

        add(panel);

        setVisible(true);
    }
}