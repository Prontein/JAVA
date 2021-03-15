package ru.geekbrains;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class ChatWindow extends JFrame implements Thread.UncaughtExceptionHandler {
    private final int GAME_WIDTH = 600;
    private final int GAME_HEIGHT = 600;
    private final int WINDOW_X = 650;
    private final int WINDOW_Y = 250;

    private final JTextArea messaging = new JTextArea();
    private final JList<String> contactList = new JList<>();
    private final DefaultListModel listModel = new DefaultListModel ();
    private final JPanel messagingPanel = new JPanel(new BorderLayout());
    private final JPanel contactPanel = new JPanel(new BorderLayout());
    private final JLabel contacts = new JLabel("Contact List");

    private final JPanel contactListText = new JPanel();
    private final JPanel onlineAndBlacklist = new JPanel();
    private final JCheckBox contactsCheckStatus = new JCheckBox("Online");
    private final JButton btnBlacklist = new JButton("Blacklist");

    private final JPanel sendMessagePanel = new JPanel(new BorderLayout());
    private final JPanel settingsMessagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JTextField sendMessageField = new JTextField();
    private final JButton btnSendMessage = new JButton("Send");
    private final JButton btnSmile = new JButton("Smile");
    private final JButton btnFont = new JButton("Font");
    private final JButton btnAddFile = new JButton("File");

    private OneServerManager serverManager;

    private JButton btnStart1;
    private JButton btnStart2;
    private JButton btnStart3;

    private AuthForm AuthForm;

    ChatWindow() {

//        Установка параметров окна чата
        setLocation(WINDOW_X, WINDOW_Y);
        setSize(GAME_WIDTH, GAME_HEIGHT);

//        Панель верхнего меню чата

        JMenuBar mainBar = new JMenuBar();
        mainBar.setBackground(new Color(221, 243, 229, 37));
        mainBar.add(createSettingsMenu());
        mainBar.add(createEditMenu());
        mainBar.add(createHelpMenu());
        setJMenuBar(mainBar);


        AuthForm = new AuthForm(this);
        AuthForm.setVisible(true);
        AuthForm.setAlwaysOnTop(true);

//        Часть с текстовым полем и списком контактов

        contactPanel.setPreferredSize(new Dimension(150, 0));
        JScrollPane scrollMessages = new JScrollPane(messaging);
        JScrollPane scrollContacts = new JScrollPane();
        scrollContacts.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

//        String[] users = {"user1", "user2", "user3", "user4", "user2", "user3", "user4", "user5"};
//        contactList.setModel(listModel);
        scrollContacts.setViewportView(contactList);
        contactList.setLayoutOrientation(JList.VERTICAL);

        scrollContacts.setPreferredSize(new Dimension(100, 100));
        messaging.setBackground(new Color(183, 248, 235));
        messaging.setEditable(false);

        contactPanel.add(scrollContacts);
        messagingPanel.add(scrollMessages);
        messagingPanel.add(contactPanel, BorderLayout.EAST);
        contactListText.setBorder(BorderFactory.createEtchedBorder());

        contacts.setFont(new Font("Arial", Font.BOLD, 14));
        contactListText.add(contacts);
        contactPanel.add(contactListText, BorderLayout.NORTH);

        onlineAndBlacklist.add(contactsCheckStatus);
        onlineAndBlacklist.add(btnBlacklist);
        onlineAndBlacklist.setPreferredSize(new Dimension(150, 70));
        onlineAndBlacklist.setBorder(BorderFactory.createEtchedBorder());

        contactPanel.add(onlineAndBlacklist, BorderLayout.SOUTH);

        add(messagingPanel, BorderLayout.CENTER);


//        Нижняя часть отправки сообщений

        try {
            serverManager = new OneServerManager("localhost", 65500, messaging, AuthForm/*, listModel*/);
        } catch (IOException  e) {
            e.printStackTrace();
        }

        sendMessagePanel.setPreferredSize(new Dimension(350, 80));
        settingsMessagePanel.setBackground(new Color(245, 248, 148));
        settingsMessagePanel.add(btnSmile);
        settingsMessagePanel.add(btnFont);
        settingsMessagePanel.add(btnAddFile);

        sendMessagePanel.add(settingsMessagePanel, BorderLayout.NORTH);

        sendMessageField.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sendMessageField.getText().equals("")) {/* Проверка на пустое сообщение*/
                    MessageDTO dto = new MessageDTO();
                    if (privateMessage(sendMessageField.getText())) {
                        dto.setMessageType(MessageType.PRIVATE_MESSAGE);
                    } else {
                        dto.setMessageType(MessageType.PUBLIC_MESSAGE);
                    }
                    dto.setBody(sendMessageField.getText());
                    serverManager.writeMsg(dto.convertToJson());
                    sendMessageField.setText(null);
                }
            }
        });

        sendMessagePanel.add(sendMessageField);
        btnSendMessage.setPreferredSize(new Dimension(120, 40));

        btnSendMessage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sendMessageField.getText().equals("")) {
                    MessageDTO dto = new MessageDTO();
                    if (privateMessage(sendMessageField.getText())) {
                        dto.setMessageType(MessageType.PRIVATE_MESSAGE);
                    } else {
                        dto.setMessageType(MessageType.PUBLIC_MESSAGE);
                    }
                    dto.setBody(sendMessageField.getText());
                    serverManager.writeMsg(dto.convertToJson());
                    sendMessageField.setText(null);
                }
            }
        });

        sendMessagePanel.add(btnSendMessage, BorderLayout.EAST);

        add(sendMessagePanel, BorderLayout.SOUTH);

        setTitle("ICQ 2021");

//      messageService = new MessageService("localhost", 65500, this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    private JMenu createSettingsMenu() {
        JMenu Settings = new JMenu("Settings");
        return Settings;
    }

    private JMenu createEditMenu() {
        JMenu Edit = new JMenu("Edit");
        return Edit;
    }

    private JMenu createHelpMenu() {
        JMenu Help = new JMenu("Help");
        return Help;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        StackTraceElement[] ste = e.getStackTrace();
        JOptionPane.showMessageDialog(this, ste[0].toString(), "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    public void startAuth(String login, String password){
        System.out.println(login);
        System.out.println(password);
        MessageDTO dto = new MessageDTO();
        dto.setLogin(login);
        dto.setPassword(password);
        dto.setMessageType(MessageType.SEND_AUTH_MESSAGE);
        serverManager.writeMsg(dto.convertToJson());
    }

    private boolean privateMessage (String message) {
        String msg = sendMessageField.getText();
        String value = msg.split(" ")[0];
        String privatTeg = "/w";
        return value.equals(privatTeg);
    }


}
