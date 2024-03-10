package com.example.monopolyjavafx;

import java.io.IOException;
import java.net.Socket;

public class ClientApplication {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
    }
}
