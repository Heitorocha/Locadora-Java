package locadora.app.ui;

import java.awt.*;
import javax.swing.*;
import locadora.exception.LocadoraException;
import locadora.service.Locadora;

public class LoginFrame extends JFrame {

    public LoginFrame(Locadora locadora) {

        setTitle("Locadora");
        setSize(420, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(6, 1, 10, 10));

        JTextField campoUsuario = new JTextField();
        JPasswordField campoSenha = new JPasswordField();

        JButton botaoEntrar = new JButton("Entrar");
        JLabel labelDica = new JLabel(
                "Use seu CPF e a senha 'cliente' ou 'funcionario'.",
                SwingConstants.CENTER);

        painel.add(new JLabel("CPF"));
        painel.add(campoUsuario);

        painel.add(new JLabel("Senha"));
        painel.add(campoSenha);
        painel.add(labelDica);
        painel.add(botaoEntrar);

        botaoEntrar.addActionListener(e -> {

            try {
                locadora.autenticar(
                        campoUsuario.getText(),
                        new String(campoSenha.getPassword()));

                new DashboardFrame(locadora);
                dispose();

            } catch (LocadoraException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Erro de login",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        add(painel);

        setVisible(true);
    }
}