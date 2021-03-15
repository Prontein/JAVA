package ru.geekbrains;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


public class SuperServer {

    private AuthService authService;
    private List<ClientHandler> clientHandlers;

    public SuperServer () {
        try (ServerSocket serverSocket = new ServerSocket(65500)) {
            System.out.println("Server started!");

            authService = new AuthServiceManager();
            authService.start();
            clientHandlers = new LinkedList<>();

            while (true) {
                System.out.println("Ожидание подключение клиента...");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(socket, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void broadcastMessage(MessageDTO dto) {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(dto);
        }
    }

    public synchronized void subscribe(ClientHandler c) {
        clientHandlers.add(c);
//        MessageDTO dto = new MessageDTO();
//        dto.setMessageType(MessageType.ONLINE_STATUS);
//        dto.setBody(c.getUser());
//        for (ClientHandler clientHandler : clientHandlers) {
//            clientHandler.sendMessage(dto);
//        }


    }

    public synchronized void unsubscribe(ClientHandler c) {
        clientHandlers.remove(c);
    }

    public AuthService getAuthService() {
        return authService;
    }
    public synchronized void sendPrivate(MessageDTO dto) {
        String msg = dto.getBody();
        String privatUsername = msg.split(" ")[1];
        String arr[] = msg.split(" ", 3); // Разделяю строку на массив , чтобы убрать из тела сообщения /w username

        String firstWord = arr[0];
        String theRest = arr[2];
        dto.setBody(theRest);
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.getUser().equals(privatUsername))
                clientHandler.sendMessage(dto);
        }
    }

    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }
}



