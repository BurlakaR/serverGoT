package com.server.communication;

import com.common.Message;
import com.common.Port;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

@Component
public class SocketManager {
    static int port=10000;
    ServerSocket server;
    ArrayList<ServerSocket> sockets = new ArrayList<>();
    ArrayList<Socket> socketsClient = new ArrayList<>();
    int clientcounter=0, messagecounter=0;


    @Autowired
    Bridge bridge;

    @PostConstruct
    public void init() throws IOException {
        server=new ServerSocket(port);
        for(int i=0;i<6;i++){
            sockets.add(new ServerSocket(port+i+1));
        }
        connect();
        listenStart();
    }

    public void connect(){
        new Thread(()->{
            Socket client;
            for(int i=0; i<6; i++){
                try {
                    client=server.accept();
                    bridge.send(client, new Port(port+i+1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void listenStart(){
        for (ServerSocket socket:
             sockets) {
            new Thread(()->{
                try {
                    Socket client=socket.accept();
                    socketsClient.add(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }



    public void send(Message message, int client){
        bridge.send(socketsClient.get(client), message);
    }

    public Message receive(int client){
        return (Message)bridge.receive(socketsClient.get(client));
    }

    public void multipleSend(Message message){
        for (Socket s:
             socketsClient) {
            bridge.send(s, message);
        }
    }

    public ArrayList<Message> mupltipleReceive(){
        messagecounter=0;
        ArrayList<Message> buf=new ArrayList<>();
        for (Socket s:
             socketsClient) {
            buf.add(new Message());
        }
        for(clientcounter=0;clientcounter<getClientNumber(); clientcounter++) {
            new Thread(()->{
                buf.add(clientcounter-1,receive(clientcounter-1));
                messagecounter++;
            }).start();
        }
        while(!read());
        return buf;
    }


    public int getClientNumber(){
        return socketsClient.size();
    }

    public boolean read(){
        return messagecounter==clientcounter;
    }

}
