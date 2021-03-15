package ru.geekbrains;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String user;
    private SuperServer superServer;

    public ClientHandler(Socket socket, SuperServer superServer) {
        try {
            this.superServer = superServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Установлено соединение с пользователем ");

            new Thread(() -> {
                authenticate();
                readMessages();
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(MessageDTO dto) {
        try {
            out.writeUTF(dto.convertToJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readMessages() {
        try {
            while (true) {
                String msg = in.readUTF();
                MessageDTO dto = MessageDTO.convertFromJson(msg);
                dto.setFrom(user);

                switch (dto.getMessageType()) {
                    case SEND_AUTH_MESSAGE -> authenticate();
                    case PUBLIC_MESSAGE -> superServer.broadcastMessage(dto);
                    case PRIVATE_MESSAGE -> superServer.sendPrivate(dto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void authenticate() {
        System.out.println("Пожалуйста авторизуйтесь!");
        try {
            while (true) {
                String authMessage = in.readUTF();

                MessageDTO dto = MessageDTO.convertFromJson(authMessage);
                String username = superServer.getAuthService().getUsernameAndPass(dto.getLogin(), dto.getPassword());
                MessageDTO send = new MessageDTO();
                if (username == null) {
                    send.setMessageType(MessageType.ERROR_MESSAGE);
                    send.setBody("Неверный догин или пароль!");

                    sendMessage(send);
                } else {
                    send.setMessageType(MessageType.AUTH_CONFIRM);

                    user = username;
                    superServer.subscribe(this);
                    System.out.println("Успешная авторизация");
                    sendMessage(send);
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeHandler() {
        try {
            superServer.unsubscribe(this);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUser() {
        return user;
    }

}
