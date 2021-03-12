package ru.geekbrains;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class NetworkHelper {
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public  NetworkHelper(String address, int port, MessageService messageService) throws IOException {
        this.socket = new Socket(address, port);

        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            while (true) {
                try {
                    String msg = inputStream.readUTF();
                    messageService.receiveMessage(msg);

                } catch (IOException  e ) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void writeMessage (String msg) {
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
}
