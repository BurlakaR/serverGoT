package com.server.model;

import com.common.TestCommand;
import com.server.communication.SocketManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameRoom {
    int id;
    int number;
    int port;
    ArrayList<Socket> socketsClient = new ArrayList<>();
    SocketManager socketManager;
    ServerSocket forCLient;

    public GameRoom(int id, int number, int port, SocketManager socketManager) {
        this.id = id;
        this.number = number;
        this.port = port;
        this.socketManager = socketManager;
        try {
            forCLient=new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(Socket client) {
        if(socketsClient.size()<number) {
            try {
                forCLient.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connect to Room:"+id);
            socketsClient.add(client);
        }

        if(socketsClient.size()==number){
            //Play start. Maybe new Thread
            socketManager.multipleSend(new TestCommand("Start", " to play!"), socketsClient);
        }
    }

    public int getId() {
        return id;
    }

    public int getPort(){
        return port;
    }
}
