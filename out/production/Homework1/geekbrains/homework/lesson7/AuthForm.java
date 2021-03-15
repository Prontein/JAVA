package ru.geekbrains;

import ru.geekbrains.ChatWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthForm extends JFrame {
    private final int SETTINGS_WIDTH = 350;
    private final int SETTINGS_HEIGHT = 300;
    private ChatWindow ChatWindow;
    private final JLabel userLabel, passLabel;
    private final JTextField nameField;
    private final JButton loginBtn;
    private final JTextField passField;

    AuthForm (ChatWindow ChatWindow){
        this.ChatWindow = ChatWindow;
        setSize(SETTINGS_WIDTH, SETTINGS_HEIGHT);
        Rectangle authFormBounds = ChatWindow.getBounds();
        int settingsX = (int) authFormBounds.getCenterX() - SETTINGS_WIDTH / 2;
        int settingsY = (int) authFormBounds.getCenterY() - SETTINGS_HEIGHT / 2;
        setLocation(settingsX, settingsY);
        setResizable(false);
        setTitle("Authorization");


        userLabel = new JLabel("Login");
        passLabel = new JLabel("Password");
        nameField = new JTextField();
        passField = new JTextField();
        loginBtn = new JButton("Login");

        userLabel.setBounds(20, 70, 200, 30);
        passLabel.setBounds(20, 110, 200, 30);
        nameField.setBounds(100, 70, 200, 30);
        passField.setBounds(100, 110, 200, 30);
        loginBtn.setBounds(120, 160, 100, 30);

        add(userLabel);
        add(nameField);
        add(passLabel);
        add(passField);
        add(loginBtn);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = nameField.getText();
                String password = passField.getText();
                if (login.equals("") || password.equals("")) return;

                ChatWindow.startAuth(login, password);

            }
        });

        setLayout(null);
        setVisible(true);



    }
}
