package ru.geekbrains;

import javax.swing.*;
import java.io.*;
import java.net.Socket;


public class OneServerManager  {

    private String host;
    private int port;
    private Socket socket;
    private JTextArea messaging;
    private DataInputStream in;
    private DataOutputStream out;
    private AuthForm AuthForm;
    private DefaultListModel listModel ;
    private String user;

    public  OneServerManager(String host, int port, JTextArea messaging, AuthForm AuthForm/*, DefaultListModel listModel*/) throws IOException {
//        this.listModel = listModel;
        this.host = host;
        this.port = port;
        this.socket = new Socket(host, port);
        this.messaging = messaging;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.AuthForm = AuthForm;


        new Thread(() -> {
            try {
                while (true) {

                    String message = in.readUTF();
                    MessageDTO dto = MessageDTO.convertFromJson(message);
                    switch (dto.getMessageType()) {

                        case PUBLIC_MESSAGE, PRIVATE_MESSAGE ->
                                readDTOMsg(dto);

                        case AUTH_CONFIRM -> AuthForm.setVisible(false);
//                        case ONLINE_STATUS -> {
//                            user = dto.getBody();
//                            listModel.addElement(user);
//                        }

                    }

                    System.out.println("Клиент получил :" + message);

                    Thread.sleep(200);

                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    private void readDTOMsg (MessageDTO dto) {
        if (dto.getMessageType() == MessageType.PRIVATE_MESSAGE) {
            messaging.append("Личное сообщение от пользователя: " + dto.getFrom() + "\n" + dto.getBody() + System.lineSeparator());

        } else
        messaging.append("Сообщение от пользователя: " + dto.getFrom() + "\n" + dto.getBody() + System.lineSeparator());
    }

    public synchronized void writeMsg (String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}






