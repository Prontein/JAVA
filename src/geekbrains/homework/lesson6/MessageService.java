package ru.geekbrains;

import java.io.IOException;

public class MessageService implements MessageServiceMethods{

        private String host;
        private int port;
        private NetworkHelper networkHelper;
        private MessageProcessor processor;

        public MessageService(String host, int port, MessageProcessor processor) {
            this.host = host;
            this.port = port;
            this.processor = processor;
            connectToServer();
        }

        private void connectToServer() {
            try {
                this.networkHelper = new NetworkHelper(host, port, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void sendMessage(String msg) {
            networkHelper.writeMessage(msg);
        }

        @Override
        public void receiveMessage(String msg) {
            processor.processMessage(msg);
        }

}
